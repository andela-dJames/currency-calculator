package com.andela.currencycalculator.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Oluwatosin on 12/22/2015.
 */
public class DisplayText extends TextView {
    public DisplayText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf"));
    }

    @Override
    public boolean isInEditMode() {
        return super.isInEditMode();
    }

    @Override
    public boolean isClickable() {
        return super.isClickable();
    }
}
