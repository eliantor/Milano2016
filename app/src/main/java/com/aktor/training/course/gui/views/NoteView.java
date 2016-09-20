package com.aktor.training.course.gui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.icu.text.SimpleDateFormat;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aktor.training.course.R;

import java.sql.Date;

/**
 * Created by aktor on 19/09/16.
 */

public class NoteView extends LinearLayout {


    private ImageView mImage;
    private SwitchCompat mButton;
    private TextView mTitle;
    private TextView mSubtitle;

    public NoteView(Context context) {
        super(context);
        init(context);
    }

    public NoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttributes(context,attrs);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.NoteView, 0, 0);
        boolean aBoolean = arr.getBoolean(R.styleable.NoteView_interactive, true);
        setInteractive(aBoolean);

        arr.recycle();
    }

    public NoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttributes(context,attrs);
    }

    @TargetApi(21)
    public NoteView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        initAttributes(context,attrs);
    }


    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.view_note, this, true);
        mImage = (ImageView) findViewById(R.id._src_img_);
        mImage.setImageResource(R.mipmap.ic_launcher);
        mTitle = (TextView) findViewById(R.id._txt_title_);
        mSubtitle = (TextView) findViewById(R.id._txt_subtitle_);
        mButton = (SwitchCompat) findViewById(R.id._btn_action);
    }

    public void setInteractive(boolean interactive){
        mButton.setVisibility(interactive? View.VISIBLE:View.INVISIBLE);
    }


    public void setDone(boolean isDone){
        mButton.setChecked(isDone);
    }

    public void setDate(Date date){
        String format = SimpleDateFormat.getDateTimeInstance().format(date);
        mSubtitle.setText(format);
    }


}
