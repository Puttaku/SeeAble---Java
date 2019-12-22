package com.puttaku.project.seeable.app;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
public class SquareLayout extends AppCompatButton {
    public SquareLayout(Context context) {
        super(context);
    }
    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int WidthSpec, int HeightSpec){
        int newWidthSpec = HeightSpec ;

        super.onMeasure(newWidthSpec, HeightSpec);
    }
}
