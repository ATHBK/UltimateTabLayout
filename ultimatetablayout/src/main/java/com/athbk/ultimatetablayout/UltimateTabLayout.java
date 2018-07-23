package com.athbk.ultimatetablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by athbk on 8/17/17.
 */

public class UltimateTabLayout extends FrameLayout {

    private final int DEFAULT_COLOR_UNDER_LINE = Color.parseColor("#000000");
    private final int DEFAULT_TAB_STYLE = 2; // style fixed.
    private final int DEFAULT_PADDING = 16;
    private final int DEFAULT_SIZE_ICON = 50;
    private final int DEFAULT_HEIGHT_UNDER_LINE = 5;

    public final static int VERTICAL = 0;
    public final static int HORIZONTAL = 1;

    /**
     *  = 1: style sliding.
     *  = 2: style fixed.
     */
    private int tabStyle;

    private float tabTextSize;
    private int tabTextColor;

    private boolean tabUnderLineShow;
    private int tabUnderLineColor;
    private float heightUnderLine;

    private float tabWidth;
    private float tabHeight;

    private float tabPaddingTop;
    private float tabPaddingBottom;
    private float tabPaddingLeft;
    private float tabPaddingRight;

    private int tabPositionIcon;
    private float tabPaddingIcon;

    private float tabHeightIcon;
    private float tabWidthIcon;

    private int tabOrientation;

    private String tabResourceFont; // update version 1.2.4
    private int styleBadge = 0; // update version 1.2.7 // 0: none, 1: no-number, 2: number
    private int tabBadgeSize = 10; // default

    private Paint mPaintUnderLine;

    private Context context;

    private OnClickTabListener onClickTabListener;

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

    private void init(Context context, AttributeSet attrs, int def){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.UltimateTabLayout, def, 0);
        tabStyle = ta.getInt(R.styleable.UltimateTabLayout_tab_style, DEFAULT_TAB_STYLE);
        tabTextSize = ta.getFloat(R.styleable.UltimateTabLayout_tab_text_size, 14f);
        tabTextColor = ta.getResourceId(R.styleable.UltimateTabLayout_tab_text_color, R.color.tab_color_selected_default);

        tabUnderLineShow = ta.getBoolean(R.styleable.UltimateTabLayout_tab_under_line_show, true);
        tabUnderLineColor = ta.getColor(R.styleable.UltimateTabLayout_tab_under_line_color, DEFAULT_COLOR_UNDER_LINE);
        heightUnderLine = ta.getDimension(R.styleable.UltimateTabLayout_tab_height_under_line, DEFAULT_HEIGHT_UNDER_LINE);

        tabHeight = ta.getDimension(R.styleable.UltimateTabLayout_tab_height, -1);
        tabWidth = ta.getDimension(R.styleable.UltimateTabLayout_tab_width, -1);

        tabPaddingLeft = ta.getDimension(R.styleable.UltimateTabLayout_tab_padding_left, DEFAULT_PADDING);
        tabPaddingRight = ta.getDimension(R.styleable.UltimateTabLayout_tab_padding_right, DEFAULT_PADDING);
        tabPaddingTop = ta.getDimension(R.styleable.UltimateTabLayout_tab_padding_top, DEFAULT_PADDING);
        tabPaddingBottom = ta.getDimension(R.styleable.UltimateTabLayout_tab_padding_bottom, DEFAULT_PADDING);

        tabPositionIcon = ta.getInt(R.styleable.UltimateTabLayout_tab_position_icon, 0);
        tabPaddingIcon = ta.getDimension(R.styleable.UltimateTabLayout_tab_padding_icon, DEFAULT_PADDING);

        tabWidthIcon = ta.getDimension(R.styleable.UltimateTabLayout_tab_width_icon, DEFAULT_SIZE_ICON);
        tabHeightIcon = ta.getDimension(R.styleable.UltimateTabLayout_tab_height_icon, DEFAULT_SIZE_ICON);

        tabOrientation = ta.getInt(R.styleable.UltimateTabLayout_tab_orientation, HORIZONTAL);

        tabResourceFont = ta.getString(R.styleable.UltimateTabLayout_tab_resource_font);

        styleBadge = ta.getInt(R.styleable.UltimateTabLayout_tab_badge, styleBadge);
        int defaultSizeBadge = 10;
        if (styleBadge == 1){
            defaultSizeBadge = (int) DeviceDimensionsHelper.convertDpToPixel(9, context);
        }
        tabBadgeSize = (int) ta.getDimension(R.styleable.UltimateTabLayout_tab_badge_size, defaultSizeBadge);

        mPaintUnderLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintUnderLine.setColor(tabUnderLineColor);

        ta.recycle();

//        setWillNotDraw(false);
    }

    public void setViewPager(final ViewPager viewPager, IFTabAdapter tabAdapterIF){
        TabModel tabModel = new TabModel.Builder(tabUnderLineShow, (int) heightUnderLine, tabOrientation)
                .setTabHeight((int)tabHeight)
                .setTabHeightIcon(tabHeightIcon)
                .setTabPaddingBottom(tabPaddingBottom)
                .setTabPaddingLeft(tabPaddingLeft)
                .setTabPaddingRight(tabPaddingRight)
                .setTabPaddingTop(tabPaddingTop)
                .setTabPaddingIcon(tabPaddingIcon)
                .setTabWidth((int)tabWidth)
                .setTabWidthIcon(tabWidthIcon)
                .setTabPositionIcon(tabPositionIcon)
                .setTabTextColor(tabTextColor)
                .setTabTextSize(tabTextSize)
                .setTabResourceFont(tabResourceFont)
                .setTabStyleBadge(styleBadge)
                .setTabBadgeSize(tabBadgeSize)
                .build();


        if (tabStyle == 1){
            if (tabOrientation == VERTICAL){
                VerticalSlingTabView verticalSlingTabView = new VerticalSlingTabView(context);
                verticalSlingTabView.setPaintUnderLine(mPaintUnderLine);
                verticalSlingTabView.setTabModel(tabModel);
                verticalSlingTabView.setViewPager(viewPager, tabAdapterIF);
                if (onClickTabListener != null){
                    verticalSlingTabView.setOnClickTabListener(onClickTabListener);
                }
                this.addView(verticalSlingTabView);
            }
            else {
                HorizontalSlingTabView horizontalSlingTabView = new HorizontalSlingTabView(context);
                horizontalSlingTabView.setPaintUnderLine(mPaintUnderLine);
                horizontalSlingTabView.setTabModel(tabModel);
                horizontalSlingTabView.setViewPager(viewPager, tabAdapterIF);
                if (onClickTabListener != null){
                    horizontalSlingTabView.setOnClickTabListener(onClickTabListener);
                }
                this.addView(horizontalSlingTabView);
            }
        }
        else {
            FixTabView fixTabView = new FixTabView(context);
            fixTabView.setPaintUnderLine(mPaintUnderLine);
            fixTabView.setTabModel(tabModel);
            fixTabView.setViewPager(viewPager, tabAdapterIF);
            if (onClickTabListener != null){
                fixTabView.setOnClickTabListener(onClickTabListener);
            }
            this.addView(fixTabView);
        }

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

    public void setHeightUnderLine(float heightUnderLine) {
        this.heightUnderLine = heightUnderLine;
    }

    public void setTabResourceFont(String tabResourceFont) {
        this.tabResourceFont = tabResourceFont;
    }

    public void setOnClickTabListener(OnClickTabListener onClickTabListener) {
        this.onClickTabListener = onClickTabListener;
    }

    public void setStyleBadge(int style){
        this.styleBadge = style;
    }

    public void setTabEnableBadge(int[] arrays){

    }

    public void setNumberBadge(int tabPosition, int count){
        if (tabStyle == 1) {
            if (tabOrientation == VERTICAL) {
                VerticalSlingTabView tabView = (VerticalSlingTabView) getChildAt(0);
                tabView.setNumberBadge(tabPosition, count);
            } else {
                HorizontalSlingTabView tabView = (HorizontalSlingTabView) getChildAt(0);
                tabView.setNumberBadge(tabPosition, count);
            }
        }
        else {
            FixTabView tabView = (FixTabView) getChildAt(0);
            tabView.setNumberBadge(tabPosition, count);
        }
    }
}
