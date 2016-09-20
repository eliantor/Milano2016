package com.aktor.training.course.gui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aktor.training.course.R;

/**
 * Created by aktor on 19/09/16.
 */

public class NoteViewV2 extends ViewGroup {

    private ImageView mImage;
    private SwitchCompat mButton;
    private TextView mTitle;
    private TextView mSubtitle;

    public NoteViewV2(Context context) {
        super(context);
        init(context);
    }

    public NoteViewV2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NoteViewV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public NoteViewV2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_note_v2, this, true);
        mImage = (ImageView) findViewById(R.id._src_img_);
        mImage.setImageResource(R.mipmap.ic_launcher);
        mTitle = (TextView) findViewById(R.id._txt_title_);
        mSubtitle = (TextView) findViewById(R.id._txt_subtitle_);
        mButton = (SwitchCompat) findViewById(R.id._btn_action);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthEaten = getPaddingLeft()+getPaddingRight();
        int heightEaten =getPaddingTop()+getPaddingBottom();

        int width = 0;
        int height = 0;

        measureChildWithMargins(mImage,
                widthMeasureSpec,
                widthEaten,
                heightMeasureSpec,
                heightEaten);

        width+=mImage.getMeasuredWidth();
        widthEaten+=mImage.getMeasuredWidth();
        height = Math.max(mImage.getMeasuredHeight(),height);

        measureChildWithMargins(mButton,widthMeasureSpec,widthEaten,heightMeasureSpec,heightEaten);
        width+=mButton.getMeasuredWidth();
        widthEaten+=mButton.getMeasuredWidth();
        height = Math.max(mButton.getMeasuredHeight(),height);

        int queryHeight = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        measureChildWithMargins(mTitle,widthMeasureSpec,
                widthEaten,queryHeight,heightEaten);
        measureChildWithMargins(mSubtitle,widthMeasureSpec,
                widthEaten,queryHeight,heightEaten);
        width+=Math.max(mTitle.getMeasuredWidth(),mSubtitle.getMeasuredWidth());
        height=Math.max(mTitle.getMeasuredHeight()+mSubtitle.getMeasuredHeight(),height);

        int hmode = MeasureSpec.getMode(heightMeasureSpec);
        if (hmode==MeasureSpec.EXACTLY){
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else if (hmode == MeasureSpec.AT_MOST){
            height = Math.min(height,MeasureSpec.getSize(heightMeasureSpec));
        }
        setMeasuredDimension(width+getPaddingLeft()+getPaddingRight(),height+getPaddingTop()+getPaddingBottom());
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                lp.leftMargin + lp.rightMargin + widthUsed, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                lp.topMargin + lp.bottomMargin
                        + heightUsed, lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int vspace = getMeasuredHeight()-(getPaddingBottom()+getPaddingTop());

        int imageRight = left+mImage.getMeasuredWidth();
        int imageTop = (vspace-mImage.getMeasuredHeight())/2;
        imageTop+=getPaddingTop();
        int imageBottom=imageTop+mImage.getMeasuredHeight();

        mImage.layout(left,imageTop,imageRight,imageBottom);

        int right = getMeasuredWidth()-getPaddingRight();
        int buttonLeft = right-mButton.getMeasuredWidth();
        int buttonTop = (vspace-mButton.getMeasuredHeight())/2;
        buttonTop +=getPaddingTop();
        int buttonBottom  = buttonTop+mButton.getMeasuredHeight();

        mButton.layout(buttonLeft,buttonTop,right,buttonBottom);
        int titleHeight = mTitle.getMeasuredHeight();
        int subHeight = mSubtitle.getMeasuredHeight();
        int center = (vspace/2)+getPaddingTop();
        mTitle.layout(imageRight,center-titleHeight,imageRight+mTitle.getMeasuredWidth(),center);
        mSubtitle.layout(imageRight,center,imageRight+mSubtitle.getMeasuredWidth(),center+subHeight);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p instanceof LayoutParams){
            return new LayoutParams(p);
        } else if (p instanceof MarginLayoutParams){
            return new LayoutParams((MarginLayoutParams)p);
        } else {
            return new LayoutParams(p);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {

        return p instanceof LayoutParams;
    }


    public static class LayoutParams extends ViewGroup.MarginLayoutParams{
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

}
