package com.tincio.capstoneproject.presentation.animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;

import com.tincio.capstoneproject.presentation.util.FeatureDevice;

/**
 * Created by juan on 3/07/2017.
 */

public final class  ViewAnimation {

    public enum Property{
        translationY,
        translationX,
        alpha,
        rotation
    }
    public static void animationTranslation(final View view, Property propertyAnimation, long duration, float displacement){
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, propertyAnimation.toString(), displacement);
        animation.setDuration(duration);
        animation.setInterpolator(new BounceInterpolator());
        animation.start();
    }

    public static void animationAlpha(final View view, Property propertyAnimation, float start, float end, int duration){
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, propertyAnimation.toString(), start, end);
        animation.setDuration(duration);
        animation.start();
    }


    public static void rotationView(View view){
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.5f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe(Property.rotation.toString(), kf0, kf1, kf2);
        ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation);
        rotationAnim.setDuration(5000);
        rotationAnim.start();
    }

}
