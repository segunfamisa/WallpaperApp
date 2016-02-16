package com.segunfamisa.wallpaperapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Custom RecyclerView with Empty View
 *
 * Created by segun.famisa on 16/02/2016.
 */
public class RecyclerView extends android.support.v7.widget.RecyclerView {

    @Nullable View emptyView;

    public RecyclerView(Context context) {
        super(context);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void checkIfEmpty(){
        if(emptyView != null){
            if(getAdapter() != null){
                emptyView.setVisibility(getAdapter().getItemCount() > 0 ? View.GONE : View.VISIBLE);
            }
        }
    }

    final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if(oldAdapter != null){
            oldAdapter.unregisterAdapterDataObserver(observer);
        }

        if(adapter != null){
            adapter.registerAdapterDataObserver(observer);
        }

        super.setAdapter(adapter);
        checkIfEmpty();
    }

    @Override
    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }

        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        super.swapAdapter(adapter, removeAndRecycleExistingViews);
        checkIfEmpty();
    }

    /**
     * This method is used to set an empty view
     * @param emptyView
     */
    public void setEmptyView(@Nullable View emptyView) {
        if (this.emptyView != null) {
            this.emptyView.setVisibility(GONE);
        }

        this.emptyView = emptyView;
        checkIfEmpty();
    }

    /**
     * This method is used to get an empty view attached to a recyclerview
     * @return the empty view
     */
    public View getEmptyView(){
        return this.emptyView;
    }

}

