package com.athbk.ultimatetablayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by athbk on 8/25/17.
 */

public class TabView extends LinearLayout implements View.OnClickListener {


    private String title;

    @DrawableRes
    private int icon = 0;

    /** position of icon and title
     *  = 0 : left
     *  = 1: right
     *  = 2: top
     *  = 3: bottom
     */
    private int positionIcon;

    private float textSize;
    private int textColor;

    private int heightIcon;
    private int widthIcon;

    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;

    private int paddingIcon;

    private int height;
    private int width;

    private int styleBadge = 0; // 0: none, 1: no-number, 2: number
    private int badgeSize = 0;

    private ImageView ivIcon;
    private FrameLayout badgeLayout;
    private BadgeView badgeView;
    private TextView tvTitle;

    private String tabResourceFont;

    private int currentPos;

    private OnClickTabListener onClickTabListener;

    public TabView(Context context) {
        super(context);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context){
        if (width == -1){
            width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        if (height == -1){
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        LayoutParams layoutParams = new LayoutParams(width, height);

        if (width == 0 || height == 0) {
            layoutParams.weight = 1;
        }


        if (icon != 0){
            ivIcon = new ImageView(context);
            ivIcon.setImageResource(icon);
            LayoutParams params = new LayoutParams(widthIcon, heightIcon);
            ivIcon.setLayoutParams(params);
            if (styleBadge != 0) {
                initBadgeView(context);
            }

        }

        if (!TextUtils.isEmpty(title)){
            tvTitle = new TextView(context);
            tvTitle.setText(title);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvTitle.setTextColor(getResources().getColorStateList(textColor, context.getTheme()));
            }
            else {
                tvTitle.setTextColor(getResources().getColorStateList(textColor));
            }
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            ViewGroup.LayoutParams textLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvTitle.setLayoutParams(textLayoutParams);
        }

        if (!TextUtils.isEmpty(tabResourceFont) && tvTitle != null){
            try {
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), tabResourceFont);
                tvTitle.setTypeface(typeface);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }


        switch (positionIcon){
            case 0:
                setOrientation(HORIZONTAL);
                this.addSubViewIcon();
                this.addSubView(tvTitle);
                if (tvTitle!=null) {
                    tvTitle.setPadding(paddingIcon, 0, 0, 0);
                }
                break;
            case 1:
                setOrientation(HORIZONTAL);
                this.addSubView(tvTitle);
                this.addSubViewIcon();
                if (tvTitle!=null) {
                    tvTitle.setPadding(0, 0, paddingIcon, 0);
                }
                break;
            case 2:
                setOrientation(VERTICAL);
                this.addSubViewIcon();
                this.addSubView(tvTitle);
                if (tvTitle!=null) {
                    tvTitle.setPadding(0, paddingIcon, 0, 0);
                }
                break;
            case 3:
                setOrientation(VERTICAL);
                this.addSubView(tvTitle);
                this.addSubViewIcon();
                if (tvTitle!=null) {
                    tvTitle.setPadding(0, 0, 0, paddingIcon);
                }
                break;
        }

        this.setOnClickListener(this);

        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
//        requestLayout();
    }

    private void initBadgeView(Context context){
        int px4 = (int) DeviceDimensionsHelper.convertDpToPixel(4, context);
        int px12 = (int) DeviceDimensionsHelper.convertDpToPixel(12, context);

        badgeLayout = new FrameLayout(context);
//        badgeLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        badgeLayout.setLayoutParams(layoutParams);
//        badgeLayout.setPadding(px12, px4, px12, px4);

        badgeView = new BadgeView(context, styleBadge, badgeSize);

        if (styleBadge == 1){

        }
        else if (styleBadge == 2){
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.TOP | Gravity.RIGHT;
            params.setMargins(30,0, 0, 0);
            badgeView.setLayoutParams(params);

        }
        badgeLayout.addView(ivIcon);
        badgeLayout.addView(badgeView);
    }

    private void addSubViewIcon(){
        if (styleBadge == 0) {
            this.addSubView(ivIcon);
        }
        else {
            this.addSubView(badgeLayout);
        }
    }

    private void addSubView(View view){
        if (view != null){
            this.addView(view);
        }
    }

    /**
     *
     * @param title : title of tab view
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @param icon : icon of tab view
     */
    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setPositionIcon(int positionIcon) {
        this.positionIcon = positionIcon;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setHeightIcon(int heightIcon) {
        this.heightIcon = heightIcon;
    }

    public void setWidthIcon(int widthIcon) {
        this.widthIcon = widthIcon;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public void setPaddingIcon(int paddingIcon) {
        this.paddingIcon = paddingIcon;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTabResourceFont(String tabResourceFont) {
        this.tabResourceFont = tabResourceFont;
    }

    public void setOnClickTabListener(OnClickTabListener onClickTabListener) {
        this.onClickTabListener = onClickTabListener;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void setStyleBadge(int styleBadge) {
        this.styleBadge = styleBadge;
    }

    public void setBadgeSize(int badgeSize) {
        this.badgeSize = badgeSize;
    }

    public void setNumberBadge(int count){
        if (badgeView != null) badgeView.setNumberBadge(count);
    }

    @Override
    public void onClick(View view) {
        if (onClickTabListener != null){
            onClickTabListener.onClickTab(currentPos);
        }
    }
}
