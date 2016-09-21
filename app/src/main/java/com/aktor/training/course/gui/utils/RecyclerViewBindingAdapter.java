package com.aktor.training.course.gui.utils;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aktor on 19/09/16.
 */

public abstract class RecyclerViewBindingAdapter
        extends RecyclerView.Adapter<BindingViewHolder> {

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(getLayout(parent,viewType),parent,false);
        BindingViewHolder holder = new BindingViewHolder(view);
        return holder;
    }

    public abstract int getLayout(ViewGroup parent, int viewType);


    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        ViewDataBinding bindings = holder.getBindings();
        bind(bindings,position);
        bindings.executePendingBindings();
    }

    public abstract void bind(ViewDataBinding binding,int position);


}
