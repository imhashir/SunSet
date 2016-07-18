package com.hashirbaig.android.sunset;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SunSetFragment extends Fragment{

    private View mSceneView;
    private View mSunView;
    private View mSkyView;

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

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });

        return v;
    }

    public void startAnimation() {
        float sunStartY = mSunView.getTop();
        float sunStopY = mSkyView.getHeight();

        ObjectAnimator sunAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunStartY, sunStopY)
                .setDuration(3000);
        sunAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        sunAnimator.setRepeatMode(ObjectAnimator.REVERSE);

        sunAnimator.start();
    }
}
