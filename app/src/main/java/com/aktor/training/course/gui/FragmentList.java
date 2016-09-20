package com.aktor.training.course.gui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aktor.training.course.R;
import com.aktor.training.course.databinding.ListFragmentBinding;
import com.aktor.training.course.gui.utils.RecyclerViewBindingAdapter;

/**
 * Created by aktor on 19/09/16.
 */

public class FragmentList extends Fragment {

    private Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        mAdapter = new Adapter();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.setLayoutManager(manager);
        binding.setAdapter(mAdapter);
        return binding.getRoot();
    }


    private class Adapter extends RecyclerViewBindingAdapter{


        @Override
        public int getLayout(ViewGroup parent, int viewType) {
            return 0;
        }

        @Override
        public void bind(ViewDataBinding binding, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
