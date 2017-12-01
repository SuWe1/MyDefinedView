package swy.com.mydefinedview.demo4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Swy on 2017/12/1.
 */

public class KLineView extends View {

    /**
     * 画笔
     */
    private Paint mPaint;

    private float baseData;

    /**
     * list of time
     */
    private ArrayList<String> times;
    /**
     * list of price
     */
    private ArrayList<Float> prices;

    /**
     * max price
     */
    private float maxPrice;
    /**
     * min price
     */
    private float minPrice = Float.MAX_VALUE;

    public KLineView(Context context) {
        super(context);
        init();
    }

    public KLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public KLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewHeight = getHeight();
        int viewWidth = getWidth();
        //获取每个计算单位的距离
        float item = viewHeight / 410f;
        drawLines(canvas,viewWidth,item);
        drawTimes(canvas,viewWidth,item);
        //先画阴影再画折线
        drawBrokenLine(canvas, viewWidth, item, "#504F76DB", Paint.Style.FILL);
        drawBrokenLine(canvas, viewWidth, item, "#4F76DB", Paint.Style.STROKE);
    }

    private void  init(){
        mPaint = new Paint();
        createTestData();
    }
    /**
     * create the test data
     */
    private void createTestData() {
        baseData = 3120.50f;
        try {
            times = new ArrayList<>();
            prices = new ArrayList<>();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat
                    ("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse("2017-01-01 09:30:00");
            for (int i = 0; i < 240; i++) {
                if (i == 120) {
                    date = dateFormat.parse("2017-01-01 13:00:00");
                }
                date.setTime(date.getTime() + 60 * 1000);
                times.add(formatTime(dateFormat.format(date)));

                float tmp;
                if (i == 0) tmp = (float) (baseData + 5 - Math.random() * 10);
                else tmp = (float) (prices.get(i - 1) + 5 - Math.random() * 10);
                tmp = formatPrice(tmp);
                if (tmp > maxPrice) {
                    maxPrice = tmp;
                }
                if (tmp < minPrice) {
                    minPrice = tmp;
                }
                prices.add(tmp);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * format the prices data
     *
     * @param price price
     * @return format price
     */
    private float formatPrice(float price) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return Float.parseFloat(df.format(price));
    }

    /**
     * format time
     *
     * @param time timeStr
     * @return format time
     */
    private String formatTime(String time) {
        return time.substring(11, 16);
    }

    /**
     *绘制五条基线
     * @param canvas
     * @param item 每个单位的距离
     */
    private void drawLines(Canvas canvas,int viewWidth,float item){
        mPaint.setColor(Color.parseColor("#AAAAAA"));
        mPaint.setStrokeWidth(0f);
        canvas.drawLine(0,item*10,viewWidth,item*10,mPaint);
        canvas.drawLine(0,item*30,viewWidth,item*30,mPaint);
        //中间虚线
        drawDashLine(canvas,0,item*190,viewWidth,item*190);
        canvas.drawLine(0,item*360,viewWidth,item*360,mPaint);
        canvas.drawLine(0,item*380,viewWidth,item*380,mPaint);
        canvas.drawLine(viewWidth / 2.0f, item * 10, viewWidth / 2.0f, item * 380, mPaint);
    }

    /**
     * 绘制虚线
     * @param canvas
     * @param x
     * @param y
     * @param endX
     * @param endY
     */
    private void drawDashLine(Canvas canvas,float x, float y, float endX, float endY){
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        PathEffect pathEffect=new DashPathEffect(new float[]{8,8},0);
        paint.setPathEffect(pathEffect);
        paint.setColor(Color.parseColor("#AAAAAA"));
        paint.setStyle(Paint.Style.STROKE);
        Path path=new Path();
        path.moveTo(x,y);
        path.lineTo(endX,endY);
        canvas.drawPath(path,paint);
    }

    /**
     * 绘制时间
     * @param canvas
     * @param viewWidth
     * @param item
     */
    private void drawTimes(Canvas canvas,int viewWidth,float item){
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8f,getResources().getDisplayMetrics()));
        mPaint.setColor(Color.parseColor("#999999"));
        float textWidth = mPaint.measureText("09:30");
        canvas.drawText("09:30",item*10,-mPaint.getFontMetrics().top+item*380,mPaint);
        canvas.drawText("11:30", viewWidth / 2.0f - textWidth / 2.0f, -mPaint.getFontMetrics()
                .top + item * 380, mPaint);
        canvas.drawText("15:00", viewWidth - textWidth - item * 10, -mPaint.getFontMetrics().top
                + item * 380, mPaint);
    }

    /**
     * 画折线
     * @param canvas
     * @param viewWidth
     * @param item
     * @param color
     * @param style
     */
    private void drawBrokenLine(Canvas canvas, int viewWidth, float item, String color, Paint
            .Style style) {
        Path path = new Path();
        Paint paint = new Paint();
        float xItem = viewWidth / 2.0f / 120f;

        // get biggest  difference value, it will be calculated proportion
        float yCount = maxPrice - baseData > baseData - minPrice ? maxPrice - baseData : baseData
                - minPrice;
        //get one item height
        float yItem = 330 * item / yCount / 2.0f;

        //set path start point,item * 195 is baseData's y point.
        path.moveTo(0, item * 195);
        //set other points
        for (int i = 0; i < times.size(); i++) {
            path.lineTo(xItem * (i + 1), item * 195 + yItem * (baseData - prices.get(i)));
        }
        //if draw shadow, we should add 3 points to draw a complete graphics.
        //if draw lines, we should let lines bold.
        if (Paint.Style.FILL == style) {
            path.lineTo(viewWidth, item * 380);
            path.lineTo(0, item * 380);
            path.lineTo(0, item * 195);
            path.close();
        } else {
            paint.setStrokeWidth(2f);
        }
        paint.setColor(Color.parseColor(color));
        paint.setAntiAlias(true);
        paint.setStyle(style);
        canvas.drawPath(path, paint);
    }
}
