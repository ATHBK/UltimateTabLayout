package com.athbk.ultimatetablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by tranquanghung on 8/28/17.
 */

public class HorizontalSlingTabView extends HorizontalScrollView implements ViewPager.OnPageChangeListener{

    private TabModel tabModel;

    private ViewPager viewPager;

    private Paint mPaintUnderLine;

    private Context context;

    private int currentPosition = 0;
    private float positionOffSet = 0;

    private int current = 0;

    private int mScrollState = 0;

    private LinearLayout containerView;

    private OnClickTabListener onClickTabListener;


    public HorizontalSlingTabView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalSlingTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalSlingTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;

        LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        containerView = new LinearLayout(context);
        containerView.setOrientation(LinearLayout.HORIZONTAL);
        containerView.setLayoutParams(parentParams);

        setLayoutParams(parentParams);
        this.setFillViewport(true);
        this.setHorizontalScrollBarEnabled(false);

        setWillNotDraw(false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int tabStripChildCount = containerView.getChildCount();
        if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
            return;
        }
        View selectedTitle = containerView.getChildAt(position);
        int extraOffset = (selectedTitle != null) ? (int) (positionOffset * selectedTitle.getWidth()) : 0;
        scrollToTab(position, extraOffset);

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

        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            scrollToTab(position, 0);
        }

        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        this.mScrollState = state;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (!tabModel.isTabUnderLineShow()) return;
            int count = containerView.getChildCount();

            View currentChildView = containerView.getChildAt(current);

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
                tabView.setHeight(tabModel.getTabHeight());
                tabView.setWidth(tabModel.getTabWidth());
                tabView.setTabResourceFont(tabModel.getTabResourceFont());
                if (tabAdapterIF.isEnableBadge(i)) {
                    tabView.setStyleBadge(tabModel.getTabStyleBadge());
                }
                else {
                    tabView.setStyleBadge(0);
                }
                tabView.setBadgeSize(tabModel.getTabBadgeSize());
                tabView.setCurrentPos(i);
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
                containerView.addView(tabView);
            }
            this.addView(containerView);
            View childView = containerView.getChildAt(0);
            childView.setSelected(true);
            requestLayout();

        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = containerView.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = containerView.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= (int) DeviceDimensionsHelper.convertDpToPixel(16f * 2, context);
            }

            scrollTo(targetScrollX, 0);
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
