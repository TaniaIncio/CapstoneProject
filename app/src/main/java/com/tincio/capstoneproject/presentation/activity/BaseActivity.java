package com.tincio.capstoneproject.presentation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

   // @BindView(R.id.toolbar) Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // animationActivity();
        setContentView(getLayoutId());
        setupWindowAnimations();
        bindViews();
    }

    protected void bindViews(){
        ButterKnife.bind(this);
    }

    /*protected void setupToolbar(){
        if(toolbar!= null){
            setSupportActionBar(toolbar);
        }
    }*/
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }

    protected abstract int getLayoutId();
}
