package swy.com.mydefinedview.demo1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Swy on 2017/7/7.
 */

public class PopupView extends View {
    private int mWidth;
    private int mHeight;
    private int mRectWidth;
    private int mRectHeight;

    Paint mPaint =new Paint();

    Path mPath =new Path();
    private boolean change=false;

    public PopupView(Context context) {
        super(context);
    }

    public PopupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidate();
            }
        });
    }

    public PopupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode==MeasureSpec.EXACTLY){
            mWidth=widthSize;
            mRectWidth= (int) (widthSize*0.8);
        }
        if (heightMode==MeasureSpec.EXACTLY){
            mHeight=heightSize;
            mRectHeight= (int) (heightSize*0.8);
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (change){
            mPaint.setColor(Color.YELLOW);
        }
        change=!change;
        mPaint.setColor(Color.parseColor("#2C97DE"));
        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawRoundRect(new RectF(0,0,mRectWidth,mRectHeight),10,10, mPaint);

        mPath.moveTo(mRectWidth/2-30,mRectHeight);
        mPath.lineTo(mRectWidth/2,mRectHeight+20);
        mPath.lineTo(mRectWidth/2+30,mRectHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }


}
