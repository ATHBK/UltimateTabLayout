package com.athbk.ultimatetablayout;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by athbk on 8/25/17.
 */

public class TabView extends LinearLayout {


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

    private int textSize;
    private int textColor;

    private int heightIcon;
    private int widthIcon;

    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;

    private int paddingIcon;

    private ImageView ivIcon;
    private TextView tvTitle;


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
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

        if (icon != 0){
            ivIcon = new ImageView(context);
            ivIcon.setImageResource(icon);
            LayoutParams params = new LayoutParams((int)widthIcon, (int)heightIcon);
            ivIcon.setLayoutParams(params);
        }

        if (!TextUtils.isEmpty(title)){
            tvTitle = new TextView(context);
            tvTitle.setText(title);
            tvTitle.setTextColor(textColor);
            tvTitle.setTextSize(textSize);
        }


        switch (positionIcon){
            case 0:
                setOrientation(HORIZONTAL);
                this.addSubView(ivIcon);
                this.addSubView(tvTitle);
                tvTitle.setPadding(paddingIcon, 0, 0, 0);
                break;
            case 1:
                setOrientation(HORIZONTAL);
                this.addSubView(tvTitle);
                this.addSubView(ivIcon);
                tvTitle.setPadding(0, 0, paddingIcon, 0);

                break;
            case 2:
                setOrientation(VERTICAL);
                this.addSubView(ivIcon);
                this.addSubView(tvTitle);
                tvTitle.setPadding(0, paddingIcon, 0, 0);

                break;
            case 3:
                setOrientation(VERTICAL);
                this.addSubView(tvTitle);
                this.addSubView(ivIcon);
                tvTitle.setPadding(0, 0, 0, paddingIcon);
                break;
        }
//        requestLayout();
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

    public void setTextSize(int textSize) {
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
}
