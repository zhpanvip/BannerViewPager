package com.example.zhpan.circleviewpager.transform;

public interface TransformerStyle {
    int NONE = 0;
    int DEPTH = 1 << 1;
    int STACK = 1 << 2;
    int ACCORDION = 1 << 3;
    int ROTATE = 1 << 4;
    int SCALE_IN = 1 << 5;
}
