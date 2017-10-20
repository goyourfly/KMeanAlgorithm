package com.goyourfly.kmeanalgorithm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by gaoyufei on 2017/10/20.
 */

public class KMeanView extends View {

    // Random point
    List<Point> points = new ArrayList<>();
    Paint pointPaint = new Paint();
    Paint kPointPaint = new Paint();
    Paint linePaint = new Paint();

    // The k point
    List<Point> kPoint = new ArrayList<>();

    HashMap<Integer,List<Point>> tempMap = new HashMap<>();

    public KMeanView(Context context) {
        super(context);
    }

    public KMeanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KMeanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public KMeanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    // Make random point
    void init() {
        Random random = new Random();
        Point screenSize = getScreenSize();

        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(10);
        pointPaint.setStyle(Paint.Style.FILL);

        kPointPaint.setColor(Color.RED);
        kPointPaint.setStrokeWidth(20);
        kPointPaint.setStyle(Paint.Style.FILL);

        linePaint.setColor(Color.DKGRAY);
        linePaint.setStrokeWidth(2);

        kPoint.clear();
//        kPoint.add(new Point(random.nextInt(screenSize.x),random.nextInt(screenSize.y)));
//        kPoint.add(new Point(random.nextInt(screenSize.x),random.nextInt(screenSize.y)));
//        kPoint.add(new Point(random.nextInt(screenSize.x),random.nextInt(screenSize.y)));
//        kPoint.add(new Point(random.nextInt(screenSize.x),random.nextInt(screenSize.y)));
//        kPoint.add(new Point(random.nextInt(screenSize.x),random.nextInt(screenSize.y)));
//        kPoint.add(new Point(random.nextInt(screenSize.x),random.nextInt(screenSize.y)));
//        kPoint.add(new Point(0,0));
//        kPoint.add(new Point(0,0));
//        kPoint.add(new Point(0,0));
//        kPoint.add(new Point(0,0));
//        kPoint.add(new Point(0,0));
        kPoint.add(new Point(0,0));

        points.clear();
        for (int i = 0; i < 400; i++) {
            points.add(new Point(random.nextInt(screenSize.x), random.nextInt(screenSize.y)));
        }


        tempMap.clear();
        for (int i  = 0; i < kPoint.size(); i++){
            tempMap.put(i,new ArrayList<Point>());
        }
    }

    Point getScreenSize() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return new Point(width, height);
    }

    public void reset(){
        init();
    }

    public void addKPoint(){
        kPoint.add(new Point(0,0));
        tempMap.clear();
        for (int i  = 0; i < kPoint.size(); i++){
            tempMap.put(i,new ArrayList<Point>());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        for (List<Point> list : tempMap.values()){
            list.clear();
        }

        // draw line
        for (int i = 0; i < points.size(); i++){
            Point point = points.get(i);
            Point nearestPoint = null;
            int nearestDistance = Integer.MAX_VALUE;
            int index = 0;
            for (int j = 0; j < kPoint.size(); j++){
                int distance = distance(point,kPoint.get(j));
                if(distance < nearestDistance){
                    index = j;
                    nearestDistance = distance;
                    nearestPoint = kPoint.get(j);
                }
            }
            if (nearestPoint != null) {
                List<Point> tempPoint = tempMap.get(index);
                tempPoint.add(point);
                canvas.drawLine(nearestPoint.x,nearestPoint.y,point.x,point.y,linePaint);
            }
        }

        // Draw the point
        for (Point point : points) {
            canvas.drawPoint(point.x, point.y, pointPaint);
        }
        for (Point point : kPoint){
            canvas.drawPoint(point.x, point.y, kPointPaint);
        }

        // Calculate the new kPoint
        for (int i = 0; i < tempMap.size(); i++){
            int x = 0;
            int y = 0;
            List<Point> points = tempMap.get(i);
            if(points.isEmpty())
                continue;
            for (Point point : points){
                x += point.x;
                y += point.y;
            }
            x = x/points.size();
            y = y/points.size();
            kPoint.get(i).set(x,y);
        }
        invalidate();
    }

    int distance(Point point,Point kPoint){
        return (int) Math.sqrt(Math.pow(point.x - kPoint.x, 2) + Math.pow(point.y - kPoint.y, 2));
    }
}
