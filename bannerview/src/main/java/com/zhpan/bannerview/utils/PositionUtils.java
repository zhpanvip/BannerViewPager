package com.zhpan.bannerview.utils;

public class PositionUtils {

    public static int getRealPosition(boolean isCanLoop, int position, int pageSize) {
        return isCanLoop ? (position - 1 + pageSize) % pageSize : (position + pageSize) % pageSize;
    }
}
