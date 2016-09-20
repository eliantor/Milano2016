package com.aktor.training.course;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SoundEffectConstants;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.ImageView;

/**
 * Created by aktor on 20/09/16.
 */
public class ImageSwitch extends ImageView implements InteractiveCheckable {

    private static final int[] CHECKED = {android.R.attr.checked, R.attr.radio};
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private boolean mChecked;
    private boolean mBroadcasting;
    private InteractiveCheckable.OnCheckedChangeListener mListener;
    private boolean mRadio;

    public ImageSwitch(Context context) {
        super(context);
        setChecked(false);
        init(null);
    }

    public ImageSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, CHECKED);
        init(a);
        a.recycle();
    }



    public ImageSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, CHECKED, defStyleAttr, 0);
        init(a);
        a.recycle();
    }

    @TargetApi(21)
    public ImageSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, CHECKED, defStyleAttr, defStyleRes);
        init(a);
        a.recycle();
    }


    @SuppressLint({"ResourceType"})
    private void init(TypedArray a) {
        setFocusable(true);
        setClickable(true);
        boolean isRadio = a.getBoolean(1, false);
        setRadio(isRadio);
        setChecked(a != null && a.getBoolean(0, false));
    }

    public void setRadio(boolean radio){
        mRadio = radio;
    }

    @Override
    public void setOnCheckedChangeListener(InteractiveCheckable.OnCheckedChangeListener listener) {
        this.mListener = listener;
    }


    @Override
    public boolean performClick() {
        toggle();
        playSoundEffect(SoundEffectConstants.CLICK);
        return super.performClick();
    }


    @Override
    public void setChecked(boolean checked) {
        setCheckedInternal(checked, true);
    }

    public void setCheckedSilently(boolean checked) {
        setCheckedInternal(checked, false);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        if (mRadio && mChecked) return;
        setChecked(!mChecked);
    }

    private void setCheckedInternal(boolean checked, boolean broadcast) {
        if (checked != mChecked) {
            mChecked = checked;
            refreshDrawableState();
            invalidate();
            //todo dispatch accessibility events
            if (!broadcast) return;
            if (!mBroadcasting) {
                mBroadcasting = true;
                if (mListener != null) mListener.onCheckedChanged(this, mChecked);
                mBroadcasting = false;
            }
        }
    }

    @Override
    @TargetApi(21)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable d = getDrawable();
        if (d != null) {
            d.jumpToCurrentState();
        }
        invalidate();
    }


    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (mChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, mChecked);
    }

    /*
      STATE MANAGEMENT
     */

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setChecked(ss.checked == 1);
    }

    @TargetApi(14)
    @Override
    public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setChecked(mChecked);
        event.setClassName(ImageSwitch.class.getName());
    }

    @TargetApi(14)
    @Override
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setCheckable(true);
        info.setClassName(ImageSwitch.class.getName());
        info.setChecked(mChecked);
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel source) {
                        return new SavedState(source);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
        private final int checked;

        private SavedState(Parcel source) {
            super(source);
            this.checked = source.readInt();
        }

        SavedState(Parcelable superState, boolean state) {
            super(superState);
            checked = state ? 1 : 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(checked);
        }
    }
}
