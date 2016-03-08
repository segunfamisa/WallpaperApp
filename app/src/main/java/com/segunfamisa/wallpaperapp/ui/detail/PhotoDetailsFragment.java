package com.segunfamisa.wallpaperapp.ui.detail;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.segunfamisa.wallpaperapp.R;
import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.services.DownloadPhotoIntentService;
import com.segunfamisa.wallpaperapp.ui.base.BaseFragment;
import com.segunfamisa.wallpaperapp.utils.AppUtils;
import com.segunfamisa.wallpaperapp.utils.DialogUtils;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment to show photo details
 */
public class PhotoDetailsFragment extends BaseFragment implements View.OnClickListener{

    private Photo mPhoto;
    private static final String ARG_PHOTO = "arg_photo";
    private static final int REQUEST_STORAGE = 100;
    private static final int REQUEST_WALLPAPER = 200;
    private Bitmap bitmap;

    @Inject
    PhotoDetailsPresenter mPresenter;

    @Bind(R.id.imageview_photo)
    ImageView mImagePhoto;

    @Bind(R.id.text_photo_date)
    TextView mTextDate;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab_options)
    FloatingActionMenu fabMenu;

    @Bind(R.id.menu_item_download)
    FloatingActionButton mFabDownload;

    @Bind(R.id.menu_item_set_wallpaper)
    FloatingActionButton mFabSetWallpaper;

    Drawable mBackDrawable;

    private ProgressDialog mDialogSetWallpaper;

    private IntentFilter mIntentFilter;

    private final BroadcastReceiver mWallpaperSetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase(DownloadPhotoIntentService.ACTION_DONE)) {
                if(mDialogSetWallpaper != null && mDialogSetWallpaper.isShowing()) {
                    mDialogSetWallpaper.dismiss();
                }
                //show toast.
                Toast.makeText(getContext(), "Wallpaper updated!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * Gets new instance of the photo details fragment
     * @param photo
     * @return
     */
    public static Fragment newInstance(Photo photo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PHOTO, Parcels.wrap(photo));

        Fragment frag = new PhotoDetailsFragment();
        frag.setArguments(bundle);
        return frag;
    }

    public PhotoDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mWallpaperSetReceiver);
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDialogSetWallpaper = new ProgressDialog(getActivity());

        mIntentFilter = new IntentFilter(DownloadPhotoIntentService.ACTION_DONE);
        getActivity().registerReceiver(mWallpaperSetReceiver, mIntentFilter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_details, container, false);
        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        mPhoto = Parcels.unwrap(args.getParcelable(ARG_PHOTO));

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBackDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_action_nav_back);
        toolbar.setNavigationIcon(mBackDrawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });

        if(mPhoto != null) {
            Glide.with(getContext())
                    .load(mPhoto.getPhotoUrls().getRegular())
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            bitmap = ((GlideBitmapDrawable) resource).getBitmap();

                            if (bitmap != null) {
                                mImagePhoto.setImageBitmap(bitmap);
                                setViewColors(bitmap);
                                fabMenu.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        mFabDownload.setOnClickListener(this);
        mFabSetWallpaper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mFabDownload) {
            //check permission for storage
            if(checkForStoragePermission()) {
                //start download service
                Intent downloadIntent = DownloadPhotoIntentService.getCallingIntentForDownload(getContext(), mPhoto);
                getActivity().startService(downloadIntent);
            }
        } else if (v == mFabSetWallpaper) {
            //check permission for storage
            if(checkForStoragePermission()) {
                mDialogSetWallpaper.setMessage("Setting wallpaper, please wait...");
                mDialogSetWallpaper.setCancelable(true);
                mDialogSetWallpaper.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if(AppUtils.isServiceRunning(getContext(), DownloadPhotoIntentService.class)) {
                            //interrupt the service
                            DownloadPhotoIntentService.interrupt();
                        }
                    }
                });
                mDialogSetWallpaper.show();

                //start download service
                Intent downloadIntent = DownloadPhotoIntentService.getCallingIntentForSetWallPaper(getContext(), mPhoto);
                getActivity().startService(downloadIntent);
            }
        }
    }

    /**
     * Sets the necessary colors on the viwes
     * @param bitmap {@code Bitmap} bitmap of the image
     */
    private void setViewColors(Bitmap bitmap) {
        Palette palette = Palette.from(bitmap).generate();

        Palette.Swatch s = palette.getVibrantSwatch();
        if(palette != null) {
            if (s == null) {
                s = palette.getDarkVibrantSwatch();
            }
            if (s == null) {
                s = palette.getLightVibrantSwatch();
            }
            if (s == null) {
                s = palette.getMutedSwatch();
            }
            if (s != null) {
                mBackDrawable.setColorFilter(s.getRgb(), PorterDuff.Mode.MULTIPLY);
                fabMenu.setMenuButtonColorNormal(palette.getVibrantColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)));

                mFabDownload.setColorNormal(palette.getLightVibrantColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)));
                mFabDownload.setColorPressed(palette.getDarkVibrantColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark)));

                mFabSetWallpaper.setColorNormal(palette.getMutedColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)));
                mFabSetWallpaper.setColorPressed(palette.getDarkMutedColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark)));
            }
        }
    }

    /**
     * Checks for storage permisssion
     * @return true if the permission has been granted, false otherwise
     */
    private boolean checkForStoragePermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //check if you should show the request rationale
                DialogUtils.createSimpleOkDialog(getContext(), getString(R.string.dialog_title_required_permission), "To download " +
                                "image, you need to allow " + getString(R.string.app_name) + " to access your external storage",
                        getString(R.string.dialog_ok_button_text), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showInstalledAppDetails(getContext(), getContext().getPackageName());
                            }
                        }).show();
            } else {
                //requeest for permission
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
            }

            return false;
        } else {
            //go ahead and do whatever it is you want to do.
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Method to launch the app info settings page
     * @param context
     * @param packageName
     */
    public static void showInstalledAppDetails(Context context, String packageName) {
        if (context == null) {
            return;
        }
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);

    }
}
