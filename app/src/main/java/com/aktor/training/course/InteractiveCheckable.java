package com.aktor.training.course;

import android.view.View;
import android.widget.Checkable;

/**
 * Created by aktor on 20/09/16.
 */
public interface InteractiveCheckable extends Checkable {

    interface OnCheckedChangeListener {
        void onCheckedChanged(View view, boolean checked);
    }

    void setOnCheckedChangeListener(OnCheckedChangeListener listener);

    int getId();
}