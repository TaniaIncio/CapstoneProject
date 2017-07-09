package com.tincio.capstoneproject.presentation.animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by juan on 3/07/2017.
 */

public final class  ViewAnimation {


    public static void animationTranslation(final View view, String propertyAnimation, long duration, float displacement){
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, propertyAnimation, displacement);
        animation.setDuration(duration);
        animation.setInterpolator(new BounceInterpolator());
        animation.start();

    }

    public static void animationAlpha(final View view, String propertyAnimation, float start, float end, int duration){
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, propertyAnimation, start, end);
        animation.setDuration(duration);
        animation.start();
        //view.animate().alpha(start).setDuration(4000);
    }

}
