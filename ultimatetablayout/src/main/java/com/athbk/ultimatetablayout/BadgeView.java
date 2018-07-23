package com.athbk.ultimatetablayout;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ATHBK on 23,July,2018
 */
public class BadgeView extends android.support.v7.widget.AppCompatTextView {

    private int styleBadge = 0; // 0 : none, 1: no-number, 2: number
    private int styleSize;

    public BadgeView(Context context) {
        super(context);
        init(context);
    }

    public BadgeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BadgeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public BadgeView(Context context, int styleBadge, int styleSize){
        super(context);
        this.styleBadge = styleBadge;
        this.styleSize = styleSize;
        init(context);
    }

    private void init(Context context){
        int px1 = (int) DeviceDimensionsHelper.convertDpToPixel(1, context);
        int px5 = (int) DeviceDimensionsHelper.convertDpToPixel(5, context);
        int px6 = (int) DeviceDimensionsHelper.convertDpToPixel(6, context);
        int px10 = (int) DeviceDimensionsHelper.convertDpToPixel(10, context);

        if (styleBadge == 1){
            setBackgroundResource(R.drawable.icon_badge_circle);

            if (styleSize > 0){
                px10 = styleSize;
            }
            FrameLayout.LayoutParams badgeParams = new FrameLayout.LayoutParams(px10, px10);
            badgeParams.gravity = Gravity.TOP | Gravity.RIGHT;
            badgeParams.setMargins(0,2, 2, 0);
            setLayoutParams(badgeParams);
            setPadding(px6, px1, px6, px1);

        }
        else {
            // set max leng
            int maxLength = 2;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            setFilters(fArray);

            setBackgroundResource(R.drawable.icon_badge);
            setTextAppearance(context, R.style.TextAppearance_AppCompat_Small);
            setTextColor(getResources().getColor(android.R.color.white));
            if (styleSize == 0){
                styleSize = 2;
            }
            setTextSize(TypedValue.COMPLEX_UNIT_SP, styleSize);
            setText("0");

            FrameLayout.LayoutParams badgeParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            badgeParams.gravity = Gravity.TOP | Gravity.RIGHT;
            badgeParams.width = badgeParams.height;
            badgeParams.setMargins(30,0, 0, 0);

            setLayoutParams(badgeParams);
            setPadding(px5, px1, px5, px1);
        }

    }


    public void setNumberBadge(int number){
        if (number > 0){
            if (styleBadge == 1) {
                setBackgroundResource(R.drawable.icon_badge_circle);
            }
            else {
                setBackgroundResource(R.drawable.icon_badge);
                setText(String.valueOf(number));
            }
            setVisibility(VISIBLE);
        }
        else {
            setBackgroundResource(R.drawable.icon_transparecy);
            setText("");
            setVisibility(GONE);
        }
    }
}
