package com.example.asheransari.topicpreference;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceFragmentCompat;


public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

// TODO: 3 adding setting using pref_visualizer.xml----||||
//       es se hamare pas pref_visualizer.xml ke pori story Uth ke Yeha a rhe hai...
//            ab hum settingActivity.xml me jaen ge sub ko remove krdenge RelativeLayout ko..
//              ur usko jaga hum waha pe fragment Dal Denge...
//                  and then hum ne style me ek style add ki hai 'preferenceTheme' ke name  se..
//                      agar hum yeh nhe lagaen ge to wo error de ga...
        addPreferencesFromResource(R.xml.pref_visualizer);

    }
}
