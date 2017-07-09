package com.tincio.capstoneproject.presentation.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.transition.Explode;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.tincio.capstoneproject.R;
import com.tincio.capstoneproject.data.database.PichangaDB;
import com.tincio.capstoneproject.data.service.SoccerFieldRepository;
import com.tincio.capstoneproject.presentation.animation.ViewAnimation;
import com.tincio.capstoneproject.presentation.presenter.MainPresenter;
import com.tincio.capstoneproject.presentation.util.FeatureDevice;
import com.tincio.capstoneproject.presentation.view.MainView;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.txt_name) TextView txtName;
    @BindView(R.id.img_main) ImageView imgMain;

    final int TIME_ANIMATION = 2000;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        animation();
    }

    private void animation(){
        ViewAnimation.animationAlpha(this.txtName, ViewAnimation.Property.alpha, 0f, 1f, TIME_ANIMATION);
        ViewAnimation.animationTranslation(this.imgMain, ViewAnimation.Property.translationY, 1000, FeatureDevice.with(this).getHeightScreen()/2);
        new showAnimation().execute();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void responseSoccerField(String response) {
        if(response.isEmpty()){
            Intent intent = new Intent(getApplicationContext(), OnboardingActivity.class);
        //    ActivityOptions options = ActivityOptions
           //         .makeSceneTransitionAnimation(this);
            // start the new activity

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            }else{
                startActivity(intent);
            }
        }

    }

    public class showAnimation extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(TIME_ANIMATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getDataFields();
        }
    }

    private void getDataFields(){
        PichangaDB db = new PichangaDB(getApplicationContext());
        MainPresenter presenter = new MainPresenter(db, this);
        presenter.getSoccerField();
    }

}
