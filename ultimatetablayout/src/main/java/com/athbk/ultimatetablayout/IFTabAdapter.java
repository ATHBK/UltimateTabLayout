package com.athbk.ultimatetablayout;

/**
 * Created by athbk on 8/25/17.
 */

public interface IFTabAdapter {

    String getTitle(int position);

    int getIcon(int position);

    boolean isEnableBadge(int position);
}
