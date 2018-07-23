package com.athbk.ultimatetablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by tranquanghung on 8/28/17.
 */

public class FixTabView extends LinearLayout implements ViewPager.OnPageChangeListener {

    private TabModel tabModel;

    private ViewPager viewPager;

    private Paint mPaintUnderLine;

    private Context context;

    private int currentPosition = 0;
    private float positionOffSet = 0;

    private int current = 0;

    private OnClickTabListener onClickTabListener;


    public FixTabView(Context context) {
        super(context);
        this.context = context;
        setWillNotDraw(false);
    }

    public FixTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setWillNotDraw(false);
    }

    public FixTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setWillNotDraw(false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int tabStripChildCount = getChildCount();
        if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
            return;
        }
        this.current = position;
        this.positionOffSet = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        if (currentPosition == position) return;

        View beforeChildView = getChildAt(currentPosition);
        beforeChildView.setSelected(false);

        this.currentPosition = position;
        View currentChildView = getChildAt(currentPosition);
        currentChildView.setSelected(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (!tabModel.isTabUnderLineShow() || tabModel.getTabOrientation() == UltimateTabLayout.VERTICAL) return;
            int count = getChildCount();

            View currentChildView = getChildAt(current);

            float left = currentChildView.getLeft();
            float right = currentChildView.getRight();
            int width = currentChildView.getWidth();
            int height = currentChildView.getHeight();

//        Log.e("TAG", "Left: " + left + "/right: " + right + "/width: " + width + "/height: " + height);
            if (positionOffSet > 0f && current < count - 1) {
                final float nextTabLeft = left + width;
                left = positionOffSet * nextTabLeft + (1f - positionOffSet) * left;
                right = left + width;
            }

            canvas.drawRect(left, height - tabModel.getTabHeightUnderLine(), right, height, mPaintUnderLine);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    public void setViewPager(final ViewPager viewPager, IFTabAdapter tabAdapterIF){
        try {

            this.viewPager = viewPager;

            this.viewPager.addOnPageChangeListener(this);
            int count = viewPager.getAdapter().getCount();


            if (tabModel.getTabOrientation() == 0) {
                setOrientation(LinearLayout.VERTICAL);
            } else {
                setOrientation(LinearLayout.HORIZONTAL);
            }

            for (int i = 0; i < count; i++) {
                TabView tabView = new TabView(context);
                tabView.setTitle(tabAdapterIF.getTitle(i));
                tabView.setIcon(tabAdapterIF.getIcon(i));
                tabView.setHeightIcon((int) tabModel.getTabHeightIcon());
                tabView.setWidthIcon((int) tabModel.getTabWidthIcon());
                tabView.setTextSize(tabModel.getTabTextSize());
                tabView.setTextColor(tabModel.getTabTextColor());
                tabView.setPaddingIcon((int) tabModel.getTabPaddingIcon());
                tabView.setPaddingLeft((int) tabModel.getTabPaddingLeft());
                tabView.setPaddingRight((int) tabModel.getTabPaddingRight());
                tabView.setPaddingTop((int) tabModel.getTabPaddingTop());
                tabView.setPaddingBottom((int) tabModel.getTabPaddingBottom());
                tabView.setPositionIcon(tabModel.getTabPositionIcon());
                tabView.setHeight((int)tabModel.getTabHeight());
                tabView.setWidth((int)tabModel.getTabWidth());
                tabView.setTabResourceFont(tabModel.getTabResourceFont());
                tabView.setCurrentPos(i);
                if (tabAdapterIF.isEnableBadge(i)) {
                    tabView.setStyleBadge(tabModel.getTabStyleBadge());
                }
                else {
                    tabView.setStyleBadge(0);
                }
                tabView.setBadgeSize(tabModel.getTabBadgeSize());
                tabView.setOnClickTabListener(new OnClickTabListener() {
                    @Override
                    public void onClickTab(int currentPos) {
                        if (onClickTabListener == null) {
                            viewPager.setCurrentItem(currentPos);
                        }
                        else {
                            onClickTabListener.onClickTab(currentPos);
                        }

                    }
                });
                tabView.init(context);
                addView(tabView);
            }

            View childView = getChildAt(0);
            childView.setSelected(true);
            requestLayout();

        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    public void setPaintUnderLine(Paint mPaintUnderLine) {
        this.mPaintUnderLine = mPaintUnderLine;
    }

    public void setTabModel(TabModel tabModel) {
        this.tabModel = tabModel;
    }

    public void setOnClickTabListener(OnClickTabListener onClickTabListener) {
        this.onClickTabListener = onClickTabListener;
    }

    public void setNumberBadge(int tabPosition, int count){
        try {
            TabView childView = (TabView) getChildAt(tabPosition);
            childView.setNumberBadge(count);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
