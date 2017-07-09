package com.tincio.capstoneproject.presentation.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by juan on 3/07/2017.
 */

public final class FeatureDevice {

    protected static Context context;

    public static class Builder{
        public Point getSizeScreen(){
            WindowManager wm = (WindowManager)    context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
        /*cuando estoy en un acititidad Point point = new Point();
        context.getWindowManager().getDefaultDisplay().getRealSize(point);*/
            Point point = new Point();
            display.getRealSize(point);
            return point;
        }

        public int getWidthScreen(){
            return getSizeScreen().x;
        }

        public int getHeightScreen(){
            return getSizeScreen().y;
        }
    }
    public static Builder with(Context mcontext){
        Builder builder = new Builder();
        context = mcontext;
        return builder;
    }

}
