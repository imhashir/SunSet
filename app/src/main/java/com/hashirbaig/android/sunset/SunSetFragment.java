package com.hashirbaig.android.sunset;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

public class SunSetFragment extends Fragment{

    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private boolean mTouched;

    private PointF mDefaultSun;

    public static SunSetFragment newInstance() {
        return new SunSetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sun_set, container, false);
        mSceneView = v;
        mSunView = v.findViewById(R.id.sun);
        mSkyView = v.findViewById(R.id.sky);

        mDefaultSun = new PointF(mSunView.getTranslationX(), mSunView.getTranslationY());

        mTouched = false;

        mBlueSkyColor = getResources().getColor(R.color.blue_sky);
        mSunsetSkyColor = getResources().getColor(R.color.sunset_sky);
        mNightSkyColor = getResources().getColor(R.color.night_sky);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTouched) {
                    startAnimation();
                } else {
                    reset();
                }
                mTouched = !mTouched;
            }
        });

        return v;
    }

    private void reset() {
        mSunView.setTranslationX(mDefaultSun.x);
        mSunView.setTranslationY(mDefaultSun.y);

        mSkyView.setBackgroundColor(mBlueSkyColor);
    }

    public void startAnimation() {
        float sunStartY = mSunView.getTop();
        float sunStopY = mSkyView.getHeight();

        ObjectAnimator sunAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunStartY, sunStopY)
                .setDuration(3000);
        //sunAnimator.setRepeatCount(1);
        //sunAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        sunAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator skyColorAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        skyColorAnimator.setEvaluator(new ArgbEvaluator());

        sunAnimator.start();
        skyColorAnimator.start();
    }
}
