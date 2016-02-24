package com.segunfamisa.wallpaperapp.ui.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;

/**
 * Created by segun.famisa on 24/02/2016.
 */
public class ScaleInAnimator extends BaseItemAnimator {

    private final int offsetDelay = 200;
    private final Interpolator mInterpolator;

    public ScaleInAnimator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    @Override
    protected void preAnimateAdd(RecyclerView.ViewHolder holder) {
        ViewCompat.setScaleX(holder.itemView, 0);
        ViewCompat.setScaleY(holder.itemView, 0);
        ViewCompat.setAlpha(holder.itemView, 0);
    }

    @Override
    protected ViewPropertyAnimatorCompat onAnimatedAdd(RecyclerView.ViewHolder holder) {
        return ViewCompat.animate(holder.itemView)
                .scaleX(1)
                .scaleY(1)
                .alpha(1)
                .setInterpolator(mInterpolator)
                .setStartDelay(offsetDelay * holder.getLayoutPosition());
    }
}
