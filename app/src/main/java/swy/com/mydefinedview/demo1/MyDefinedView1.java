package swy.com.mydefinedview.demo1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import swy.com.mydefinedview.R;

/**
 * Created by Swy on 2017/7/7.
 */

public class MyDefinedView1 extends View {
    private String text;
    private int textColor;
    private int textSize;

    //绘制范围
    private Rect mBound;
    private Paint mPaint;

    public MyDefinedView1(Context context) {
        super(context);
    }

    public MyDefinedView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDefinedView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.view1,defStyleAttr,0);
        int indexCount=a.getIndexCount();
        for (int i=0;i<indexCount;i++){
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.view1_text:
                    text=a.getString(attr);
                    break;
                case R.styleable.view1_textColor:
                    //默认为黑色
                    textColor=a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.view1_textSize:
                    textSize=a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
            }
        }
        // 最后一定要调用recycle
        a.recycle();
        mPaint=new Paint();
        mPaint.setTextSize( textSize);
        mBound=new Rect();
        mPaint.getTextBounds(text,0,text.length(),mBound);
    }

    public MyDefinedView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //背景
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mPaint.setColor(textColor);
        canvas.drawText(text,getWidth()/2-mBound.width()/2,getHeight()/2+mBound.height()/2,mPaint);
    }
}
