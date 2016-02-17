package com.segunfamisa.wallpaperapp.ui.widget.transitions;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;


/**
 * Created by segun.famisa on 17/02/2016.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class PhotoTransition extends TransitionSet {

    public PhotoTransition() {
        init();
    }

    public PhotoTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds())
                .addTransition(new ChangeTransform())
                .addTransition(new ChangeImageTransform());
    }
}
