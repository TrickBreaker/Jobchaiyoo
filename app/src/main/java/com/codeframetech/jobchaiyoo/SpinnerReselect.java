package com.codeframetech.jobchaiyoo;

import android.content.Context;
import android.util.AttributeSet;


public class SpinnerReselect extends android.support.v7.widget.AppCompatSpinner {

    OnItemSelectedListener listener;
    int prevPos = -1;
    public SpinnerReselect(Context context) {
        super(context);
    }

    public SpinnerReselect(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinnerReselect(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        if (position == getSelectedItemPosition() && prevPos == position) {
            getOnItemSelectedListener().onItemSelected(null, null, position, 0);
        }
        prevPos = position;
    }


}

