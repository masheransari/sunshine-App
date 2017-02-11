package com.example.asheransari.topicpreference.AudioVisuals;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.asheransari.topicpreference.R;

/**
 * Created by asher.ansari on 2/2/2017.
 */

public class VisualizerView extends View {

    private static final float SEGMENT_SIZE = 100.f;
    private static final float BASS_SEGMENT_SIZE = 10.f / SEGMENT_SIZE;
    private static final float MID_SEGMENT_SIZE = 30.f / SEGMENT_SIZE;
    private static final float TREBLE_SEGMENT_SIZE = 60.f / SEGMENT_SIZE;

    private static final float MIN_SIZE_DEFAULT = 50;

    private static final float BASS_MULTIPLIER = 1.5f;
    private static final float MID_MULTIPLIER = 3;
    private static final float TERBLE_MULTIPLIER = 5;

    private static final float REVOLUTION_PER_SECOND = .3f;

    private static final float RADIUS_BASS = 20 / 100.f;
    private static final float RADIUS_MID = 60 / 1000.f;
    private static final float RADIUS_TREBLE = 90 / 100.f;

    private final TrailedShape mBassCircle;
    private final TrailedShape mMidSquare;
    private final TrailedShape mTrebleTriangle;

    private byte[] bytes;

    private long mStartTime;
    private float bass;
    private float mid;
    private float treble;

    private boolean showBass;
    private boolean showMid;
    private boolean showTreble;

    final String LOG_TAG = VisualizerView.class.getSimpleName();

    @ColorInt
    private int backgroundColor;

    public VisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bytes = null;
        TrailedShape.setMinSize(MIN_SIZE_DEFAULT);

        mBassCircle = new TrailedShape(BASS_MULTIPLIER) {
            @Override
            protected void drawThisShape(float shapeCenterX, float shapeCenterY, float currentSize, Canvas canvas, Paint paint) {
                canvas.drawCircle(shapeCenterX, shapeCenterY, currentSize, paint);
            }
        };

        mMidSquare = new TrailedShape(MID_MULTIPLIER) {
            @Override
            protected void drawThisShape(float shapeCenterX, float shapeCenterY, float currentSize, Canvas canvas, Paint paint) {
                canvas.drawRect(shapeCenterX - currentSize, shapeCenterY - currentSize, shapeCenterX + currentSize, shapeCenterY + currentSize, paint);
            }
        };

        mTrebleTriangle = new TrailedShape(TERBLE_MULTIPLIER) {
            @Override
            protected void drawThisShape(float shapeCenterX, float shapeCenterY, float currentSize, Canvas canvas, Paint paint) {
                Path trianglePath = new Path();
                trianglePath.moveTo(shapeCenterX, shapeCenterY - currentSize);
                trianglePath.lineTo(shapeCenterX + currentSize, shapeCenterY + currentSize / 2);
                trianglePath.lineTo(shapeCenterX - currentSize, shapeCenterY + currentSize / 2);
                trianglePath.lineTo(shapeCenterX, shapeCenterY - currentSize);
                canvas.drawPath(trianglePath, paint);
            }
        };
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int righy, int bottom) {
        super.onLayout(changed, left, top, left, righy);
        mStartTime = SystemClock.uptimeMillis();
        float viewCenterX = getWidth()/2.f;
        float viewCenterY = getWidth()/2.f;
        float shortSide = viewCenterX < viewCenterY ? viewCenterX : viewCenterY;
        Log.e(LOG_TAG,"shortSide = "+shortSide);
        TrailedShape.setViewCenterX(viewCenterX);
        TrailedShape.setsViewCenterY(viewCenterY);

        mBassCircle.setShapeRadiusFromCenter(shortSide * RADIUS_BASS);
        mMidSquare.setShapeRadiusFromCenter(shortSide * RADIUS_MID);
        mTrebleTriangle.setShapeRadiusFromCenter(shortSide * RADIUS_TREBLE);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if (bytes == null){
            return;
        }
        double currentAngelRadians = calcCurrentAngle();

        canvas.drawColor(backgroundColor);
        if (showBass){
            mBassCircle.draw(canvas,bass,currentAngelRadians);
        }
        if (showMid){
            mMidSquare.draw(canvas,mid,currentAngelRadians);
        }
        if (showTreble){
            mTrebleTriangle.draw(canvas,treble,currentAngelRadians);
        }
        invalidate();
    }

    private double calcCurrentAngle(){
        long elapsedTime = SystemClock.uptimeMillis() - mStartTime;
        float revolution = elapsedTime * REVOLUTION_PER_SECOND / 1000;
        return revolution *2 *Math.PI;
    }

    public void updateFFT(byte[] bytesData){
        bytes = bytesData;

        float bassTotal = 0;
        for (int i=0; i<bytesData.length * BASS_SEGMENT_SIZE; i++)
        {
            bassTotal += Math.abs(bytesData[i]);
        }
        bass = bassTotal / (bytesData.length * BASS_SEGMENT_SIZE);

        float midTotal = 0;
        for (int i= (int)(bytesData.length * BASS_SEGMENT_SIZE); i<bytesData.length * MID_SEGMENT_SIZE; i++){
            midTotal += Math.abs(bytesData[i]);
        }
        mid = midTotal / (bytesData.length * MID_SEGMENT_SIZE);

        float trebleTotal =0;
        for (int i=(int)(bytesData.length * MID_SEGMENT_SIZE);i<bytesData.length;i++){
            trebleTotal += Math.abs(bytesData[i]);
        }
        treble = trebleTotal / (bytesData.length * TREBLE_SEGMENT_SIZE);

        invalidate();
    }

    public void restart(){
        mBassCircle.restartTrail();
        mMidSquare.restartTrail();
        mTrebleTriangle.restartTrail();
    }

    public void setShowBass(boolean showBass){
        this.showBass = showBass;
    }
    public void setShowMid(boolean showMid){
        this.showMid = showMid;
    }

    public void setShowTreble(boolean showTreble) {
        this.showTreble = showTreble;
    }
    public void setMinSizeScale(float scale)
    {
        TrailedShape.setMinSize(scale);
    }

    public void setColor(String newColorKey){
        @ColorInt
         int shapeColor;

        @ColorInt
                int trailColor;

        if (newColorKey.equals(getContext().getString(R.string.pref_color_blue_value)))
        {
            shapeColor = ContextCompat.getColor(getContext(),R.color.shapeBlue);
            trailColor = ContextCompat.getColor(getContext(),R.color.trailBlue);
            backgroundColor = ContextCompat.getColor(getContext(),R.color.backgroundBlue);
        }
        else if (newColorKey.equals(R.string.pref_color_green_value))
        {
            shapeColor = ContextCompat.getColor(getContext(),R.color.shapeGreen);
            trailColor = ContextCompat.getColor(getContext(),R.color.trailGreen);
            backgroundColor = ContextCompat.getColor(getContext(),R.color.backgroundGreen);
        }
        else{
            shapeColor = ContextCompat.getColor(getContext(),R.color.shapeRed);
            trailColor = ContextCompat.getColor(getContext(),R.color.trailRed);
            backgroundColor = ContextCompat.getColor(getContext(),R.color.backgroundRed);
        }

        mBassCircle.setShapeColor(shapeColor);
        mMidSquare.setShapeColor(shapeColor);
        mTrebleTriangle.setShapeColor(shapeColor);

        mBassCircle.setTrailColor(trailColor);
        mMidSquare.setTrailColor(trailColor);
        mTrebleTriangle.setTrailColor(trailColor);
    }


}
