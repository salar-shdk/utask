package com.example.salar.cevent;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by salar on 5/12/18.
 */

public class FontedEditText extends EditText {

    String[] list = {"fonts/IRANSANS.TTF","fonts/Arista-Pro-Thin-trial.ttf","fonts/Exo2.0-ExtraLight.otf"};
    public static int IRANSANS = 0,ARTISTA_PRO_THIN =1,EXO_2 = 2;

    public FontedEditText(Context context) {
        super(context);
    }

    public FontedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FontedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FontedEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void setTypeface() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), list[2]);
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
