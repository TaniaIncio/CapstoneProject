package com.tincio.capstoneproject.presentation.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tincio.capstoneproject.R;

import butterknife.BindView;
import butterknife.OnClick;

public class OnboardingActivity extends BaseActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.btn_enter) Button btnEnter;

    @Override
    protected int getLayoutId() {
        setupWindowAnimations();
        return R.layout.activity_onboarding;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager, true);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_onboarding, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getResources().getStringArray(R.array.array_onboarding)[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);
            return rootView;
        }
    }

    /**Metodos**/
    @OnClick(R.id.btn_enter)
    public void goLogin(){
        Intent intent =new Intent(getApplicationContext(), MapsActivity.class);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedView = btnEnter;
            String transitionName = getString(R.string.square_blue_name);
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(OnboardingActivity.this, sharedView, transitionName);
            startActivity(intent, transitionActivityOptions.toBundle());

           // startActivity(intent);//, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(intent);
        }*/
        startActivity(intent);
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "";
                case 1:
                    return "";
                case 2:
                    return "";
            }
            return null;
        }
    }
}
