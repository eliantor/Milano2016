package com.aktor.training.course.gui.utils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by aktor on 19/09/16.
 */

public class BindingViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding mBindings;

    public BindingViewHolder(View itemView) {
        super(itemView);
        mBindings = DataBindingUtil.bind(itemView);
    }

    public <T extends ViewDataBinding> T getBindings(){
        return (T)mBindings;
    }
}
