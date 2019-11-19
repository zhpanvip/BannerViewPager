package com.zhpan.bannerview.utils;

import com.zhpan.bannerview.constants.PageStyle;

public class PositionUtils {

    public static int toUnrealPosition(boolean isCanLoop, int position, int pageSize, int pageStyle) {
        if (isCanLoop) {
            if (pageStyle == PageStyle.NORMAL) {
                return (position < pageSize) ? (++position) : pageSize;
            } else {
                return (position < pageSize) ? position + 2 : pageSize + 1;
            }
        } else {
            return position;
        }
    }

    public static int getRealPosition(boolean isCanLoop, int position, int pageSize) {
        int realPosition;
        if (pageSize <= 0)
            return 0;
        if (isCanLoop) {
            realPosition = (position - 1 + pageSize) % pageSize;
        } else {
            realPosition = (position + pageSize) % pageSize;
        }
        if (realPosition < 0)
            realPosition += pageSize;
        return realPosition;
    }

    public static int getRealPosition(boolean isCanLoop, int position, int pageSize, int pageStyle) {
        if (isCanLoop) {
            if (pageStyle == PageStyle.NORMAL) {
                if (position == 0) {
                    return pageSize - 1;
                } else if (position == pageSize + 1) {
                    return 0;
                } else {
                    return --position;
                }
            } else {
                if (position == 0) {
                    return pageSize == 1 ? 0 : pageSize - 2;
                } else if (position == 1) {
                    return pageSize - 1;
                } else if (position == pageSize + 3) {
                    return 1;
                } else if (position == pageSize + 2) {
                    return 0;
                } else {
                    return position - 2;
                }
            }

        } else {
            return position;
        }
    }

}
