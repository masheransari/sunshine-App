package com.example.asheransari.topicpreference.AudioVisuals;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Build;

import com.example.asheransari.topicpreference.R;

/**
 * Created by asher.ansari on 2/2/2017.
 */

public class AudioInputReader {
    private final VisualizerView mVisualizerView;
    private final Context mContext;
    private MediaPlayer mPlayer;
    private Visualizer mVisualizer;

    public AudioInputReader(VisualizerView view,Context context)
    {
        this.mVisualizerView = view;
        this.mContext = context;
        initVisualizer();
    }
    private void initVisualizer(){
        mPlayer = MediaPlayer.create(mContext, R.raw.htmlthesong);
        mPlayer.setLooping(true);

        mVisualizer = new Visualizer(mPlayer.getAudioSessionId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mVisualizer.setMeasurementMode(Visualizer.MEASUREMENT_MODE_PEAK_RMS);
            mVisualizer.setScalingMode(Visualizer.SCALING_MODE_NORMALIZED);
        }
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[0]);
        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int i) {

            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int i) {
                if(mVisualizer!=null && mVisualizer.getEnabled())
                {
                    mVisualizerView.updateFFT(bytes);
                }
            }
        },
        Visualizer.getMaxCaptureRate(),false,true);

        mVisualizer.setEnabled(true);
        mPlayer.start();
    }

    public void shutDown(Boolean isFinished)
    {
        if (mPlayer != null){
            mPlayer.pause();
            if (isFinished){
                mVisualizer.release();
                mPlayer.release();
                mPlayer = null;
                mVisualizer = null;
            }
        }
        if (mVisualizer != null){
            mVisualizer.setEnabled(false);
        }
    }

    public void restart(){
        if (mPlayer != null){
            mPlayer.start();
        }

        mVisualizer.setEnabled(true);
        mVisualizerView.restart();
    }
}
