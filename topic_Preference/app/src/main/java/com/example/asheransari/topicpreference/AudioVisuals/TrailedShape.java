package com.example.asheransari.topicpreference.AudioVisuals;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.util.Log;

import java.util.LinkedList;

/**
 * Created by asher.ansari on 2/2/2017.
 */

abstract class TrailedShape {
    private static float sViewCenterX, sViewCenterY;
    private static float sMinSize;

    final String LOG_TAG = TrailedShape.class.getSimpleName();
    private final float mMultiplier;

    private final Path mTrailPath;
    private final LinkedList<TrailPoint> mTraiLList;

    private Paint mPaint;
    private Paint mTrailPaint;

    private float mShapeRadiusFromCenter;

    TrailedShape(float multiplier) {
        this.mMultiplier = multiplier;
        this.mTrailPath = new Path();///this is use for Straight Line Segments...
        this.mTraiLList = new LinkedList<>();

        this.mPaint = new Paint();
        this.mTrailPaint = new Paint();

        mPaint.setStyle(Paint.Style.FILL);
        mTrailPaint.setStyle(Paint.Style.STROKE);
        mTrailPaint.setStrokeWidth(5);
        mTrailPaint.setStrokeJoin(Paint.Join.ROUND);
        mTrailPaint.setStrokeCap(Paint.Cap.ROUND);
        mTrailPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    static void setMinSize(float minSize) {
        TrailedShape.sMinSize = minSize;
    }

    static void setViewCenterX(float viewCenterX) {
        TrailedShape.sViewCenterX = viewCenterX;
    }

    static void setsViewCenterY(float sViewCenterY) {
        TrailedShape.sViewCenterY = sViewCenterY;
    }

    void draw(Canvas canvas, float currentFreqAve, double currentAngle) {

        float currentSize = sMinSize + mMultiplier * currentFreqAve;

        float shapeCurrentX = calcLocationInAnimationX(mShapeRadiusFromCenter, currentAngle);
        float shapeCenterY = calcLocationInAnimationY(mShapeRadiusFromCenter, currentAngle);

        float trailX = calcLocationInAnimationX((mShapeRadiusFromCenter + currentSize - sMinSize), currentAngle);
        float trailY = calcLocationInAnimationY((mShapeRadiusFromCenter + currentSize - sMinSize), currentAngle);

        mTrailPath.rewind();

        mTraiLList.add(new TrailPoint(trailX, trailY, currentAngle));

        while (currentAngle - mTraiLList.peekFirst().theta > 2 * Math.PI) {
            Log.e(LOG_TAG, "in While Condition where condition (currentAngle - mTraiLList.peekFirst().theta>2 * Math.PI) = (" + currentAngle + " - " + mTraiLList.peekFirst().theta + "> 2 *" + Math.PI + " = " + (currentAngle - mTraiLList.peekFirst().theta > 2 * Math.PI));
            mTraiLList.poll();
        }
        mTrailPath.moveTo(mTraiLList.peekFirst().x, mTraiLList.peekFirst().y);//TODO: 1 yeh cheez apke shapes ko loop me Rakh rhe hai..

        for (TrailPoint trailPoint : mTraiLList) {
            mTrailPath.lineTo(trailPoint.x, trailPoint.y);
        }

        canvas.drawPath(mTrailPath, mTrailPaint);

        drawThisShape(shapeCurrentX, shapeCenterY, currentSize, canvas, mPaint);

    }


    protected abstract void drawThisShape(float shapeCenterX, float shapeCenterY, float currentSize, Canvas canvas, Paint paint);
//    protected abstract void drawThisShape(float shapeCenterX, float shapeCenterY, float currentSize, Canvas canvas, Paint paint);

    void restartTrail() {
        mTraiLList.clear();
    }

    private float calcLocationInAnimationX(float radiusFromCenter, double currentAngle) {
        return (float) (sViewCenterX + Math.cos(currentAngle) * radiusFromCenter);
    }

    private float calcLocationInAnimationY(float radiusFromCenter, double CurrentAngle) {
        return (float) (sViewCenterY + Math.sin(CurrentAngle) * radiusFromCenter);
    }

    void setShapeColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    void setTrailColor(@ColorInt int color) {
        mTrailPaint.setColor(color);
    }

    void setShapeRadiusFromCenter(float mShapeRadiusFromCenter) {
        this.mShapeRadiusFromCenter = mShapeRadiusFromCenter;
    }


    private class TrailPoint {

        final float x;
        final float y;
        final double theta;

        TrailPoint(float x, float y, double angle) {
            this.x = x;
            this.y = y;
            this.theta = angle;
        }
    }


}


