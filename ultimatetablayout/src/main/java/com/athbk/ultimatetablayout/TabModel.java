package com.athbk.ultimatetablayout;

/**
 * Created by tranquanghung on 8/28/17.
 */

public class TabModel {

    private int tabTextSize;
    private int tabTextColor;

    private boolean tabUnderLineShow;
    private int tabHeightUnderLine;

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

    public TabModel(Builder builder){
        this.tabTextSize = builder.tabTextSize;
        this.tabTextColor = builder.tabTextColor;

        this.tabUnderLineShow = builder.tabUnderLineShow;
        this.tabHeightUnderLine = builder.tabHeightUnderLine;

        this.tabWidth = builder.tabWidth;
        this.tabHeight = builder.tabHeight;

        this.tabPaddingTop = builder.tabPaddingTop;
        this.tabPaddingBottom = builder.tabPaddingBottom;
        this.tabPaddingLeft = builder.tabPaddingLeft;
        this.tabPaddingRight = builder.tabPaddingRight;

        this.tabPositionIcon = builder.tabPositionIcon;
        this.tabPaddingIcon = builder.tabPaddingIcon;

        this.tabHeightIcon = builder.tabHeightIcon;
        this.tabWidthIcon = builder.tabWidthIcon;

        this.tabOrientation = builder.tabOrientation;
    }

    public static class Builder {
        private int tabTextSize;
        private int tabTextColor;

        private boolean tabUnderLineShow;
        private int tabHeightUnderLine;

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

        public Builder(boolean tabUnderLineShow, int tabHeightUnderLine, int tabOrientation) {
            this.tabUnderLineShow = tabUnderLineShow;
            this.tabHeightUnderLine = tabHeightUnderLine;
            this.tabOrientation = tabOrientation;
        }

        public Builder setTabTextSize(int tabTextSize){
            this.tabTextSize = tabTextSize;
            return this;
        }

        public Builder setTabTextColor(int tabTextColor){
            this.tabTextColor = tabTextColor;
            return this;
        }

        public Builder setTabWidth(int tabWidth){
            this.tabWidth = tabWidth;
            return this;
        }

        public Builder setTabHeight(int tabHeight){
            this.tabHeight = tabHeight;
            return this;
        }

        public Builder setTabPaddingTop(float tabPaddingTop){
            this.tabPaddingTop = tabPaddingTop;
            return this;
        }

        public Builder setTabPaddingLeft(float tabPaddingLeft){
            this.tabPaddingLeft = tabPaddingLeft;
            return this;
        }

        public Builder setTabPaddingRight(float tabPaddingRight){
            this.tabPaddingRight = tabPaddingRight;
            return this;
        }

        public Builder setTabPaddingBottom(float tabPaddingBottom){
            this.tabPaddingBottom = tabPaddingBottom;
            return this;
        }

        public Builder setTabPositionIcon(int tabPositionIcon){
            this.tabPositionIcon = tabPositionIcon;
            return this;
        }

        public Builder setTabPaddingIcon(float tabPaddingIcon){
            this.tabPaddingIcon = tabPaddingIcon;
            return this;
        }

        public Builder setTabHeightIcon(float tabHeightIcon){
            this.tabHeightIcon = tabHeightIcon;
            return this;
        }

        public Builder setTabWidthIcon(float tabWidthIcon){
            this.tabWidthIcon = tabWidthIcon;
            return this;
        }

        public TabModel build(){
            return new TabModel(this);
        }
    }


    public int getTabTextSize() {
        return tabTextSize;
    }

    public int getTabTextColor() {
        return tabTextColor;
    }

    public boolean isTabUnderLineShow() {
        return tabUnderLineShow;
    }

    public int getTabHeightUnderLine() {
        return tabHeightUnderLine;
    }

    public int getTabWidth() {
        return tabWidth;
    }

    public int getTabHeight() {
        return tabHeight;
    }

    public float getTabPaddingTop() {
        return tabPaddingTop;
    }

    public float getTabPaddingBottom() {
        return tabPaddingBottom;
    }

    public float getTabPaddingLeft() {
        return tabPaddingLeft;
    }

    public float getTabPaddingRight() {
        return tabPaddingRight;
    }

    public int getTabPositionIcon() {
        return tabPositionIcon;
    }

    public float getTabPaddingIcon() {
        return tabPaddingIcon;
    }

    public float getTabHeightIcon() {
        return tabHeightIcon;
    }

    public float getTabWidthIcon() {
        return tabWidthIcon;
    }

    public int getTabOrientation() {
        return tabOrientation;
    }
}
