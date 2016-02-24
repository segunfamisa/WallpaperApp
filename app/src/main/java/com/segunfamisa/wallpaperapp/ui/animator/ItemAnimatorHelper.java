package com.segunfamisa.wallpaperapp.ui.animator;

import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;

/**
 * Helper class to generate {@code RecyclerView.ItemAnimator}
 *
 * Created by segun.famisa on 24/02/2016.
 */
public class ItemAnimatorHelper {

    public static RecyclerView.ItemAnimator scaleIn() {
        ScaleInAnimator animator = new ScaleInAnimator(new DecelerateInterpolator(1.2f));
        animator.setAddDuration(600);
        return animator;
    }
}
