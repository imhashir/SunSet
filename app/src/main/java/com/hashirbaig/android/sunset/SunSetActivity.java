package com.hashirbaig.android.sunset;

import android.support.v4.app.Fragment;

public class SunSetActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return SunSetFragment.newInstance();
    }
}
