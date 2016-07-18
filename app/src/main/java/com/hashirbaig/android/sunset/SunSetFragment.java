package com.hashirbaig.android.sunset;

import android.animation.Animator;
import android.animation.AnimatorSet;
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
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class SunSetFragment extends Fragment{

    private View mSceneView;
    private View mSunView;
    private View mSkyView;
    private View mSunRayView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;
    private int mSunLow;
    private int mSunHigh;

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
        mSunRayView = v.findViewById(R.id.sun_rays);

        mDefaultSun = new PointF(mSunView.getTranslationX(), mSunView.getTranslationY());

        mTouched = false;

        mBlueSkyColor = getResources().getColor(R.color.blue_sky);
        mSunsetSkyColor = getResources().getColor(R.color.sunset_sky);
        mNightSkyColor = getResources().getColor(R.color.night_sky);
        mSunLow = getResources().getColor(R.color.bright_sun);
        mSunHigh = getResources().getColor(R.color.sun_high);

        sunPulseAnimation();
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTouched) {
                    sunSetAnimation();
                } else {
                    sunRiseAnimation();
                }
                mTouched = !mTouched;
            }
        });

        return v;
    }

    public void sunPulseAnimation() {
        ObjectAnimator sunPulseAnimator = ObjectAnimator
                .ofFloat(mSunRayView, "rotation", 1, 10)
                .setDuration(500);
        sunPulseAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        sunPulseAnimator.setInterpolator(new LinearInterpolator());
        sunPulseAnimator.start();
    }

    public void sunRiseAnimation() {
        float sunStartY = mSkyView.getHeight();
        float sunStopY = mSunView.getTop();

        ObjectAnimator sunAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunStartY, sunStopY)
                .setDuration(3000);
        sunAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator skyColorAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor)
                .setDuration(3000);
        skyColorAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator skyNightAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        skyNightAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(skyNightAnimator)
                .before(sunAnimator)
                .before(skyColorAnimator);

        animatorSet.start();
    }

    public void sunSetAnimation() {
        float sunStartY = mSunView.getTop();
        float sunStopY = mSkyView.getHeight();

        ObjectAnimator sunAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunStartY, sunStopY)
                .setDuration(3000);
        sunAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator skyColorAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        skyColorAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator skyNightAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(3000);
        skyNightAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(sunAnimator)
                .with(skyColorAnimator)
                .before(skyNightAnimator);

        animatorSet.start();
    }
}
