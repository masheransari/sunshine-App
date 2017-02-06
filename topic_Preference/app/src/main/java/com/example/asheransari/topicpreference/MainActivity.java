package com.example.asheransari.topicpreference;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asheransari.topicpreference.AudioVisuals.AudioInputReader;
import com.example.asheransari.topicpreference.AudioVisuals.VisualizerView;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE = 88;
    private VisualizerView mVisualizerView;
    private AudioInputReader mAudioInputReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizer);

        mVisualizerView = (VisualizerView)findViewById(R.id.activity_visualizer);
        defaultSetup();
        setupPermission();
    }

    private void defaultSetup(){
        mVisualizerView.setShowBass(true);
        mVisualizerView.setShowMid(true);
        mVisualizerView.setShowTreble(true);
        mVisualizerView.setMinSizeScale(2);
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

}
