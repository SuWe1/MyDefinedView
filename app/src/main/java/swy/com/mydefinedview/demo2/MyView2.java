package swy.com.mydefinedview.demo2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import swy.com.mydefinedview.R;

/**
 * Created by Swy on 2017/7/11.
 */

public class MyView2 extends View {

    private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath=new Path();

    public MyView2(Context context) {
        super(context);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


//        mPath.addArc(200, 200, 400, 400, -225, 225);
//        mPath.arcTo(400, 200, 600, 400, -180, 225, false);
//        mPath.lineTo(400, 542);

//        mPath.setFillType(Path.FillType.EVEN_ODD);
//        //lineTO绝对位置   rLinTo相对位置 进行移动量的移动
//        mPath.moveTo(300,100);
//        mPath.lineTo(200,200);
//        mPath.rLineTo(100,-50);
//        mPath.rLineTo(200,100);
//        mPath.rLineTo(-50,50);
//        mPath.close();
//
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(5);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        canvas.drawPoint(50,50,mPaint);
//        float[] points = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
//        canvas.drawLines(points, mPaint);
//        canvas.drawPath(mPath,mPaint);

//        String text="hello ysw";
//        mPaint.setTextSize(18);
//        canvas.drawText(text, 100, 25, mPaint);
//        mPaint.setTextSize(36);
//        canvas.drawText(text, 100, 70, mPaint);
//        mPaint.setTextSize(60);
//        canvas.drawText(text, 100, 145, mPaint);
//        mPaint.setTextSize(84);
//        canvas.drawText(text, 100, 240, mPaint);

        //7.26
//        Shader shader=new LinearGradient(100,500,500,500, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
//        Shader shader = new RadialGradient(300, 300, 200, Color.parseColor("#E91E63"),Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
//        Shader shader = new SweepGradient(300, 300, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"));
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.vayne_face);
        Shader shader1=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Bitmap bitmap1=BitmapFactory.decodeResource(getResources(),R.mipmap.avatar);
        Shader shader2=new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Shader shader= new ComposeShader(shader1,shader2, PorterDuff.Mode.SRC_OVER);
        mPaint.setShader(shader);
        canvas.drawCircle(300,300,300,mPaint);
    }

}
