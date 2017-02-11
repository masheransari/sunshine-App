package com.example.asheransari.topicpreference;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asheransari.topicpreference.AudioVisuals.AudioInputReader;
import com.example.asheransari.topicpreference.AudioVisuals.VisualizerView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE = 88;
    private VisualizerView mVisualizerView;
    private AudioInputReader mAudioInputReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizer);

        mVisualizerView = (VisualizerView)findViewById(R.id.activity_visualizer);
//        defaultSetup();
        setupSharedPreferences();
        setupPermission();
    }

    private void setupSharedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mVisualizerView.setShowBass(sharedPreferences.getBoolean(String.valueOf(R.string.pref_show_bass_key),getResources().getBoolean(R.bool.pref_show_bass_default)));
        mVisualizerView.setShowBass(sharedPreferences.getBoolean(getString(R.string.pref_show_bass_key),getResources().getBoolean(R.bool.pref_show_bass_default)));
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        // COMPLETED (4) Use resources here instead of the hard coded string and boolean
//        mVisualizerView.setShowBass(sharedPreferences.getBoolean(getString(R.string.pref_show_bass_key),
//                getResources().getBoolean(R.bool.pref_show_bass_default)));
        mVisualizerView.setShowMid(true);
        mVisualizerView.setShowTreble(true);
        mVisualizerView.setMinSizeScale(1);
//        SharedPreferences.Editor editor = shareP
        mVisualizerView.setColor(getString(R.string.pref_color_red_value));
//        TODO imp1:  yeha hum register krenge apki shared pereference Changes ko..
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
//        this hum es liye kr rhe hai because hum ne esko implements kia hai SharedPreferences.onSharedPreferenceChanged
//        ke 7....
//        TODO destroyTechniques: ab hume esko destroy bhe krna hai to hum es trha onDestory() me kren ge
//        esko UnRegistrere...
    }

    private void defaultSetup(){
        mVisualizerView.setShowBass(false);
        mVisualizerView.setShowMid(true);
        mVisualizerView.setShowTreble(true);
        mVisualizerView.setMinSizeScale(2);
//        SharedPreferences.Editor editor = shareP
        mVisualizerView.setColor(getString(R.string.pref_color_red_value));
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (mAudioInputReader !=null){
            mAudioInputReader.shutDown(isFinishing());
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        if (mAudioInputReader !=null){
            mAudioInputReader.restart();
        }
    }

    private void setupPermission(){
        String[] permissionsWeNeed = new String[]{Manifest.permission.RECORD_AUDIO};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionsWeNeed, MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE);
        }
        else{
            mAudioInputReader = new AudioInputReader(mVisualizerView,this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permission[], @NonNull int[] grantResults){
        switch (requestCode){
            case MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE:
            {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mAudioInputReader = new AudioInputReader(mVisualizerView,this);
                }
                else{
                    Toast.makeText(this, "Permission For audio not granted. Visualizer Can't run", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.visualizer_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_setting){
            Intent startSettingActivity = new Intent(this, settingActivity.class);
            startActivity(startSettingActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
//    s apki key hai.. jo press ki gai hai...
//        ab hum ne yeh to bata diya hai ke change pe yeh krna hai,, magar esko registered bhe to krna hai na..
//        to esko hum register kren ge.. Ser TODO imp: [imp1]
        if (s.equals(getString(R.string.pref_show_bass_key))) {
            mVisualizerView.setShowBass(sharedPreferences.getBoolean(s,getResources().getBoolean(R.bool.pref_show_bass_default)));
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
//        TODO SharedPreferences.unregisterOnSharedPreferenceChangeListener:
//        hum ne esko unregisterd kr diya hai,...

        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
