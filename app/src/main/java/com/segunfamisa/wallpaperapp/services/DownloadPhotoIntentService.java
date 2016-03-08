package com.segunfamisa.wallpaperapp.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.segunfamisa.wallpaperapp.R;
import com.segunfamisa.wallpaperapp.data.DataManager;
import com.segunfamisa.wallpaperapp.data.api.PhotoService;
import com.segunfamisa.wallpaperapp.data.model.Photo;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * {@code IntentService} to download a photo
 *
 * Created by segun.famisa on 24/02/2016.
 */
public class DownloadPhotoIntentService extends IntentService {
    private static String ARG_PHOTO = "arg_photo";
    private static String ARG_ACTION = "arg_action";
    private static final int ACTION_DOWNLOAD = 100;
    private static final int ACTION_SET_WALLPAPER = 200;

    public static final String FILTER_SET_WALLPAPER = "com.segunfamisa.wallpaperapp.SetWallPaper";
    public static final String ACTION_DONE = "com.segunfamisa.wallpaperapp.DownloadPhoto.Done";
    public static final String ACTION_ERROR = "com.segunfamisa.wallpaperapp.DownloadPhoto.Error";
    public static final String EXTRA_FILENAME = "extra_filename";
    public static final String EXTRA_ERROR = "extra_error";

    private Photo mPhoto;
    private int mAction;

    private static Intent getCallingIntent(Context context, Photo photo) {
        Intent intent = new Intent(context, DownloadPhotoIntentService.class);
        intent.putExtra(ARG_PHOTO, Parcels.wrap(photo));
        return intent;
    }

    /**
     * Gets the appropriat intent for calling the download photo service
     * @param context {@link Context} to use
     * @param photo {@link Photo} to use
     * @return {@link Intent}
     */
    public static Intent getCallingIntentForDownload(Context context, Photo photo) {
        Intent intent = getCallingIntent(context, photo);
        intent.putExtra(ARG_ACTION, ACTION_DOWNLOAD);
        return intent;
    }

    /**
     * Gets the appropriate calling intent for setting wallpaper
     * @param context {@link Context} to use
     * @param photo {@link Photo} to use
     * @return {@link Intent}
     */
    public static Intent getCallingIntentForSetWallPaper(Context context, Photo photo) {
        Intent intent = getCallingIntent(context, photo);
        intent.putExtra(ARG_ACTION, ACTION_SET_WALLPAPER);
        return intent;
    }

    /**
     * Constructor
     */
    public DownloadPhotoIntentService() {
        super(DownloadPhotoIntentService.class.getName());
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadPhotoIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent.hasExtra(ARG_PHOTO)) {
            mPhoto = Parcels.unwrap(intent.getParcelableExtra(ARG_PHOTO));
            mAction = intent.getIntExtra(ARG_ACTION, ACTION_DOWNLOAD);

            if(mPhoto != null) {
                //download photo
                doDownload(mPhoto);
            }
        }
    }

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    private void setupNotification() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Saving photo...")
            .setSmallIcon(R.drawable.ic_notification_download);
    }

    private void doDownload(final Photo photo) {
        setupNotification();
        final long time = System.currentTimeMillis();
        mBuilder.setProgress(0, 0, true);
        mNotificationManager.notify((int) time, mBuilder.build());

        try {
            OkHttpClient client = new OkHttpClient();

            final Request req = new Request.Builder()
                    .url(photo.getPhotoUrls().getRegular())
                    .build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mBuilder.setProgress(0, 0, false);
                    mBuilder.setContentTitle(getString(R.string.notif_title_save_error));
                    mNotificationManager.notify((int) time, mBuilder.build());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = response.body().byteStream();
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    File file = getOutputMediaFile(photo.getId());

                    mBuilder.setProgress(0, 0, false);
                    mBuilder.setContentTitle(getString(R.string.app_name));
                    if(bmp != null && file != null && saveFile(bmp, file)) {
                        mBuilder.setContentText(getString(R.string.notif_title_save_successful));

                        if (mAction == ACTION_SET_WALLPAPER) {
                            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                            wallpaperManager.setBitmap(bmp);

                            sendSetWallpaperResult(file.getAbsolutePath());
                        }
                    } else {
                        mBuilder.setContentText(getString(R.string.notif_title_save_error));
                    }

                    mNotificationManager.notify((int) time, mBuilder.build());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Sends a broadcast that the download is  done, sends the filepath as extra
     * @param fileName
     */
    private void sendSetWallpaperResult(String fileName) {
        Intent resultIntent = new Intent();
        resultIntent.setAction(ACTION_DONE);
        resultIntent.putExtra(EXTRA_FILENAME, fileName);
        sendBroadcast(resultIntent);
    }

    /**
     * Saves the bitmap into a file
     *
     * @param bitmap
     * @param file
     * @return true if it was saved successfully, false otherwise.
     */
    private boolean saveFile(Bitmap bitmap, File file) {
        if(file != null && bitmap != null) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } finally {
                try {
                    if(out != null) {
                        out.close();
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Create a File for saving an image or video
     *
     * @param photoId Photo Id
     */
    private File getOutputMediaFile(String photoId){
        //check storage state
        if(Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)){
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), getString(R.string.app_name));
            // Create the storage directory if it does not exist
            if (! mediaStorageDir.exists()){
                boolean created = mediaStorageDir.mkdirs();
                boolean isDirectory = mediaStorageDir.isDirectory();
                if (! (created || isDirectory)){

                    return null;
                }
            }
            // Create a media file name
            return new File(mediaStorageDir.getPath() + File.separator +
                    photoId + ".jpg");
        }
        return null;
    }
}
