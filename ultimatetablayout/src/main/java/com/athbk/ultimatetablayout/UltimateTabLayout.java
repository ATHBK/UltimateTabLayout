package com.athbk.ultimatetablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by athbk on 8/17/17.
 */

public class UltimateTabLayout extends FrameLayout implements ViewPager.OnPageChangeListener{

    private final int DEFAULT_COLOR_UNDER_LINE = Color.parseColor("#000000");
    private final int DEFAULT_TAB_STYLE = 2; // style fixed.
    private final int DEFAULT_PADDING = 16;
    private final int DEFAULT_SIZE_ICON = 50;
    private final int DEFAULT_WIDTH_UNDER_LINE = 5;
    /**
     *  = 1: style sliding.
     *  = 2: style fixed.
     */
    private int tabStyle;

    private int tabTextSize;
    private int tabTextColor;

    private boolean tabUnderLineShow;
    private int tabUnderLineColor;

    private int tabWidth;
    private int tabHeight;

    private float tabPaddingTop;
    private float tabPaddingBottom;
    private float tabPaddingLeft;
    private float tabPaddingRight;

    private int tabPositionIcon;
    private float tabPaddingIcon;

    private float tabHeightIcon;
    private float tabWidthIcon;

    private int tabOrientation;

    private ViewPager viewPager;
    private IFTabAdapter tabAdapterIF;

    private Paint mPaintUnderLine;

    private ViewGroup containerView;

    private Context context;

    private int currentPosition = 0;
    private float positionOffSet = 0;

    private int current = 0;


    public UltimateTabLayout(Context context) {
        super(context);
        this.context = context;
        init(context, null, 0);
    }

    public UltimateTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs, 0);
    }

    public UltimateTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs, defStyleAttr);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.current = position;
        this.positionOffSet = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        if (currentPosition == position) return;

        View beforeChildView = containerView.getChildAt(currentPosition);
        beforeChildView.setSelected(false);

        this.currentPosition = position;
        View currentChildView = containerView.getChildAt(currentPosition);
        currentChildView.setSelected(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!tabUnderLineShow) return;
        int count = containerView.getChildCount();

        View currentChildView = containerView.getChildAt(current);

        float left = currentChildView.getLeft();
        float right = currentChildView.getRight();
        int width = currentChildView.getWidth();
        int height = currentChildView.getHeight();

//        Log.e("TAG", "Left: " + left + "/right: " + right + "/width: " + width + "/height: " + height);
        if (positionOffSet > 0f && current < count - 1){
            final float nextTabLeft = left + width;
            left = positionOffSet * nextTabLeft + (1f - positionOffSet) * left;
            right = left + width;
        }

        canvas.drawRect(left, height - DEFAULT_WIDTH_UNDER_LINE, right, height, mPaintUnderLine);
    }

    private void init(Context context, AttributeSet attrs, int def){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.UltimateTabLayout, def, 0);
        tabStyle = ta.getInt(R.styleable.UltimateTabLayout_tab_style, DEFAULT_TAB_STYLE);
        tabTextSize = ta.getInt(R.styleable.UltimateTabLayout_tab_text_size, 14);
        tabTextColor = ta.getResourceId(R.styleable.UltimateTabLayout_tab_text_color, R.color.tab_color_selected_default);
        tabUnderLineShow = ta.getBoolean(R.styleable.UltimateTabLayout_tab_under_line_show, true);
        tabUnderLineColor = ta.getColor(R.styleable.UltimateTabLayout_tab_under_line_color, DEFAULT_COLOR_UNDER_LINE);

        tabHeight = ta.getInt(R.styleable.UltimateTabLayout_tab_height, 0);
        tabWidth = ta.getInt(R.styleable.UltimateTabLayout_tab_width, 0);

        tabPaddingLeft = ta.getDimension(R.styleable.UltimateTabLayout_tab_padding_left, DEFAULT_PADDING);
        tabPaddingRight = ta.getDimension(R.styleable.UltimateTabLayout_tab_padding_right, DEFAULT_PADDING);
        tabPaddingTop = ta.getDimension(R.styleable.UltimateTabLayout_tab_padding_top, DEFAULT_PADDING);
        tabPaddingBottom = ta.getDimension(R.styleable.UltimateTabLayout_tab_padding_bottom, DEFAULT_PADDING);

        tabPositionIcon = ta.getInt(R.styleable.UltimateTabLayout_tab_position_icon, 0);
        tabPaddingIcon = ta.getDimension(R.styleable.UltimateTabLayout_tab_padding_icon, DEFAULT_PADDING);

        tabWidthIcon = ta.getDimension(R.styleable.UltimateTabLayout_tab_width_icon, DEFAULT_SIZE_ICON);
        tabHeightIcon = ta.getDimension(R.styleable.UltimateTabLayout_tab_height_icon, DEFAULT_SIZE_ICON);

        tabOrientation = ta.getInt(R.styleable.UltimateTabLayout_tab_orientation, 0);

        mPaintUnderLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintUnderLine.setColor(tabUnderLineColor);

        ta.recycle();

        setWillNotDraw(false);
    }

    public void setViewPager(ViewPager viewPager, IFTabAdapter tabAdapterIF){
        this.viewPager = viewPager;
        this.tabAdapterIF = tabAdapterIF;

        this.viewPager.addOnPageChangeListener(this);
        int count = viewPager.getAdapter().getCount();

        if (tabStyle == 1){
            containerView = new HorizontalScrollView(context);
        }
        else {
            containerView = new LinearLayout(context);
            ((LinearLayout)containerView).setOrientation(LinearLayout.HORIZONTAL);
        }

        for (int i=0; i<count; i++){
            TabView tabView = new TabView(context);
            tabView.setTitle(tabAdapterIF.getTitle(i));
            tabView.setIcon(tabAdapterIF.getIcon(i));
            tabView.setHeightIcon((int)tabHeightIcon);
            tabView.setWidthIcon((int)tabWidthIcon);
            tabView.setTextSize(tabTextSize);
            tabView.setTextColor(tabTextColor);
            tabView.setPaddingIcon((int)tabPaddingIcon);
            tabView.setPaddingLeft((int)tabPaddingLeft);
            tabView.setPaddingRight((int)tabPaddingRight);
            tabView.setPaddingTop((int)tabPaddingTop);
            tabView.setPaddingBottom((int)tabPaddingBottom);
            tabView.setPositionIcon(tabPositionIcon);
            tabView.init(context);
            containerView.addView(tabView);
        }

        this.addView(containerView);
        View childView = containerView.getChildAt(0);
        childView.setSelected(true);
        requestLayout();
    }

    public void setTabStyle(int tabStyle) {
        this.tabStyle = tabStyle;
    }

    public void setTabTextSize(int tabTextSize) {
        this.tabTextSize = tabTextSize;
    }

    public void setTabTextColor(int tabTextColor) {
        this.tabTextColor = tabTextColor;
    }

    public void setTabUnderLineShow(boolean tabUnderLineShow) {
        this.tabUnderLineShow = tabUnderLineShow;
    }

    public void setTabUnderLineColor(int tabUnderLineColor) {
        this.tabUnderLineColor = tabUnderLineColor;
    }

    public void setTabWidth(int tabWidth) {
        this.tabWidth = tabWidth;
    }

    public void setTabHeight(int tabHeight) {
        this.tabHeight = tabHeight;
    }

    public void setTabPaddingTop(float tabPaddingTop) {
        this.tabPaddingTop = tabPaddingTop;
    }

    public void setTabPaddingBottom(float tabPaddingBottom) {
        this.tabPaddingBottom = tabPaddingBottom;
    }

    public void setTabPaddingLeft(float tabPaddingLeft) {
        this.tabPaddingLeft = tabPaddingLeft;
    }

    public void setTabPaddingRight(float tabPaddingRight) {
        this.tabPaddingRight = tabPaddingRight;
    }

    public void setTabPositionIcon(int tabPositionIcon) {
        this.tabPositionIcon = tabPositionIcon;
    }

    public void setTabPaddingIcon(float tabPaddingIcon) {
        this.tabPaddingIcon = tabPaddingIcon;
    }

    public void setTabHeightIcon(float tabHeightIcon) {
        this.tabHeightIcon = tabHeightIcon;
    }

    public void setTabWidthIcon(float tabWidthIcon) {
        this.tabWidthIcon = tabWidthIcon;
    }

    public void setTabOrientation(int tabOrientation) {
        this.tabOrientation = tabOrientation;
    }
}
