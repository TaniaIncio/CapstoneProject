package com.tincio.capstoneproject.presentation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.tincio.capstoneproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

   // @BindView(R.id.toolbar) Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
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
    protected abstract int getLayoutId();
}
