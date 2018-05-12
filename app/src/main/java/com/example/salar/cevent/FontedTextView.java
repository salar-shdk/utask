package com.example.salar.cevent;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by salar on 7/28/17.
 */

public class FontedTextView extends TextView {
    String[] list = {"fonts/IRANSANS.TTF","fonts/Arista-Pro-Thin-trial.ttf","fonts/Exo2.0-ExtraLight.otf"};
    public static int IRANSANS = 0,ARTISTA_PRO_THIN =1,EXO_2 = 2;
    public FontedTextView(Context context) {
        super(context);
        setTypeface();
    }

    public FontedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface();
    }

    public FontedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FontedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setTypeface();
    }

    private void setTypeface() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), list[0]);
        setTypeface(tf);
    }

    public void setTypeface(int i) {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), list[i]);
        setTypeface(tf);
    }
    public void setTypefaceBold(int i){
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), list[i]);
        setTypeface(tf,Typeface.BOLD);
    }
}
