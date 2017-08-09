package swy.com.mydefinedview.demo3;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import swy.com.mydefinedview.R;

/**
 * Created by Swy on 2017/8/9.
 */

public class DemoThreeView extends LinearLayout implements Animator.AnimatorListener {

    //表情之间的分割线
    private int dividerMargin=20;
    private int defaultBottom=70;

    private String defaultLike="喜欢";
    private String defaultDisLike ="无感";

    private int defaultTextColor= Color.WHITE;
    private String defaultShadow="#7F484848";
    private int defaultGravity= Gravity.CENTER_HORIZONTAL;
    private int defaultSize=dip2px(getContext(),25);


    private float count;
    //好评 差评
    private int like=10;
    private int disLike=20;
    private float fLike, fDisLike;//好评差评百分比

    private ImageView imgLike;
    private ImageView imgDisLike;
    private TextView likeNumTv, likeTextTv, disLikeNumTv, disLikeTextTV;
    private LinearLayout likeContent, disLikeContent, likeParent, disLikeParent;//两端是半圆形的的矩形
    private AnimationDrawable animLike, animDisLike;//笑脸使用帧动画
    private ValueAnimator animatorBack;//背景拉伸动画

    private int faceType;//表情类型 0笑 1哭

    private boolean isClose=false;//判断是否收起动画

    public DemoThreeView(Context context) {
        this(context,null);
    }

    public DemoThreeView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public DemoThreeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
       this(context,attrs,defStyleAttr,0);
    }

    public DemoThreeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
        bindListener();
    }

    private void initView(){
        removeAllViews();
        setOrientation(HORIZONTAL);
        setGravity(defaultGravity | Gravity.BOTTOM);
        setBackgroundColor(Color.TRANSPARENT);//刚开始透明

        //设置评论百分比和好评差评数据
        float count=disLike+like;
        fLike=like/count;
        fDisLike =disLike/count;
        like= (int) (fLike*100);
        disLike= (int) (fDisLike *100);

        //初始化图片
        imgLike=new ImageView(getContext());
        //添加动画资源  获得帧动画
        imgLike.setBackgroundResource(R.drawable.animation_like);
        animLike= (AnimationDrawable) imgLike.getBackground();
        //初始化文本
        likeNumTv =new TextView(getContext());
        likeNumTv.setText(like+"%");
        likeNumTv.setTextColor(defaultTextColor);//设置文字颜色
        TextPaint likeNumPaint= likeNumTv.getPaint();
        likeNumPaint.setFakeBoldText(true);//文字加粗
        likeNumTv.setTextSize(20f);
        likeTextTv =new TextView(getContext());
        likeTextTv.setText(defaultLike);
        likeTextTv.setTextColor(defaultTextColor);

        imgDisLike =new ImageView(getContext());
        imgDisLike.setBackgroundResource(R.drawable.animation_dislike);
        animDisLike = (AnimationDrawable) imgDisLike.getBackground();
        disLikeNumTv =new TextView(getContext());
        disLikeNumTv.setTextColor(defaultTextColor);
        disLikeNumTv.setTextSize(20f);
        disLikeNumTv.setText(disLike+"%");
        TextPaint disNumPaint= disLikeNumTv.getPaint();
        disNumPaint.setFakeBoldText(true);
        disLikeTextTV =new TextView(getContext());
        disLikeTextTV.setTextColor(defaultTextColor);
        disLikeTextTV.setText(defaultDisLike);

        //初始化布局 点赞效果不包括顶上的文本
        likeContent =new LinearLayout(getContext());
        disLikeContent =new LinearLayout(getContext());
        LinearLayout.LayoutParams backParams=new LayoutParams(defaultSize,defaultSize);
        likeContent.addView(imgLike,backParams);
        disLikeContent.addView(imgDisLike,backParams);
        //未点击状态背景为白色
        likeContent.setBackgroundResource(R.drawable.white_background);
        disLikeContent.setBackgroundResource(R.drawable.white_background);

        //单列总布局 包括文本信息
        likeParent =new LinearLayout(getContext());
        disLikeParent =new LinearLayout(getContext());
        likeParent.setOrientation(VERTICAL);
        disLikeParent.setOrientation(VERTICAL);
        likeParent.setGravity(Gravity.CENTER_HORIZONTAL);
        disLikeParent.setGravity(Gravity.CENTER_HORIZONTAL);
        likeParent.setBackgroundColor(Color.TRANSPARENT);
        disLikeParent.setBackgroundColor(Color.TRANSPARENT);


        //添加文字图片放进一列
        LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,0);//顶上文本相距10dp
        params.gravity=Gravity.CENTER;
        likeParent.setGravity(Gravity.CENTER_HORIZONTAL);
        disLikeParent.setGravity(Gravity.CENTER_HORIZONTAL);
        likeParent.addView(likeNumTv,params);
        likeParent.addView(likeTextTv,params);
        likeParent.addView(likeContent,params);

        disLikeParent.addView(disLikeNumTv,params);
        disLikeParent.addView(disLikeTextTV,params);
        disLikeParent.addView(disLikeContent,params);

        //每个表情之间的分割线
        ImageView imgDividing=new ImageView(getContext());
        LayoutParams dividingParams=new LayoutParams(3,80);
        imgDividing.setBackground(new ColorDrawable(Color.GRAY));
        dividingParams.setMargins(dividerMargin,10,dividerMargin,defaultBottom+20);
        dividingParams.gravity=Gravity.BOTTOM;

        LayoutParams faceParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        faceParams.setMargins(30,20,30,defaultBottom);
        faceParams.gravity=Gravity.BOTTOM;
        addView(likeParent,faceParams);
        addView(imgDividing,dividingParams);
        addView(disLikeParent,faceParams);

        //默认隐藏文字
        setTextVisibility(View.GONE);
    }

    public void setTextVisibility(int v){
        likeNumTv.setVisibility(v);
        likeTextTv.setVisibility(v);
        disLikeNumTv.setVisibility(v);
        disLikeTextTV.setVisibility(v);
    }


    private void bindListener(){
        imgLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                faceType=0;//设置动画对象
                animUp();
                setTextVisibility(VISIBLE);//滑动过程显示文字
                //切换背景
                setBackgroundColor(Color.parseColor(defaultShadow));
                likeContent.setBackgroundResource(R.drawable.yellow_background);
                disLikeContent.setBackgroundResource(R.drawable.white_background);
                //重置帧动画
                imgDisLike.setBackground(null);
                imgDisLike.setBackgroundResource(R.drawable.animation_dislike);
                animDisLike= (AnimationDrawable) imgDisLike.getBackground();
            }
        });
        imgDisLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                faceType=1;
                animUp();
                setTextVisibility(VISIBLE);

                setBackgroundColor(Color.parseColor(defaultShadow));
                likeContent.setBackgroundResource(R.drawable.white_background);
                disLikeContent.setBackgroundResource(R.drawable.yellow_background);

                imgLike.setBackground(null);
                imgLike.setBackgroundResource(R.drawable.animation_like);
                animLike= (AnimationDrawable) imgLike.getBackground();
            }
        });
    }

    /**
     * 背景拉伸动画
     */
    public void animUp(){
        //动画过程不能打点击
        imgDisLike.setClickable(false);
        imgLike.setClickable(false);

        int max=Math.max(like*4,disLike*4);
        animatorBack=ValueAnimator.ofInt(5,max);
        animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int margin= (int) animation.getAnimatedValue();
                LayoutParams paramsFace= (LayoutParams) imgLike.getLayoutParams();
                paramsFace.bottomMargin=margin;

                if (margin<=like*4)
                    imgLike.setLayoutParams(paramsFace);
                if (margin<=disLike*4)
                    imgDisLike.setLayoutParams(paramsFace);
            }
        });
        isClose=false;
        animatorBack.addListener(this);
        animatorBack.setDuration(500);
        animatorBack.start();
    }

    /**
     * 背景回收动画
     */
    public void animDown(){
        int max=Math.max(like*4,disLike*4);
        animatorBack=ValueAnimator.ofInt(max,5);
        animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int margin=(int) animation.getAnimatedValue();
                LayoutParams paramsFace= (LayoutParams) imgLike.getLayoutParams();
                paramsFace.bottomMargin= margin;

                if (margin<=like*4)
                    imgLike.setLayoutParams(paramsFace);
                if (margin<=disLike*4)
                    imgDisLike.setLayoutParams(paramsFace);
            }
        });
        animatorBack.addListener(this);
        animatorBack.setDuration(500);
        animatorBack.start();
    }



    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        //重置帧动画
        animLike.stop();
        animDisLike.stop();

        //关闭时不执行帧动画
        if (isClose){
            imgDisLike.setClickable(true);
            imgLike.setClickable(true);
            //隐藏文字
            setTextVisibility(GONE);
            //恢复透明
            setBackgroundColor(Color.TRANSPARENT);
            return;
        }
        isClose=true;
        if (faceType==0){
            animLike.start();
            objectY(imgLike);
        }else {
            animDisLike.start();
            objectX(imgDisLike);
        }
    }

    /**
     *到达顶部后的表情扭动效果 上下扭动
     * @param view
     */
    private void objectY(View view){
        ObjectAnimator animator=ObjectAnimator.ofFloat(view,TRANSLATION_Y,-10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f,0);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(500);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animDown();//执行回弹动画
            }
        });
    }

    /**
     * 到达顶部后的表情扭动效果 左右扭动
     * @param view
     */
    private void objectX(View view){
        ObjectAnimator animator=ObjectAnimator.ofFloat(view,TRANSLATION_X,-10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f,0);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(1500);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animDown();//执行回弹动画
            }
        });
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    public void setNum(int like , int disLike){
        count=like+disLike;
        fLike=like/count;
        fDisLike=disLike/count;
        this.like= (int) (fLike*100);
        this.disLike= (int) (fDisLike*100);
        setLikePer(like);
        setDisLikePer(disLike);
    }

    /**
     * 设置好评百分比
     * @param like
     */
    public void setLikePer(int like) {
        likeNumTv.setText(like + "%");
    }

    /**
     * 设置差评百分比
     * @param disLike
     */
    public void setDisLikePer(int disLike) {
        disLikeNumTv.setText(disLike + "%");
    }

    public DemoThreeView setDefalutBottom(int defalutBottom) {
        this.defaultBottom = defalutBottom;
        return this;
    }

    public void notifyChange(){
        initView();
        bindListener();
    }

    public DemoThreeView setDividerMargin(int dividerMargin) {
        this.dividerMargin = dividerMargin;
        return this;
    }

    public void setDefaultLike(String defaultLike) {
        this.defaultLike = defaultLike;
    }

    public void setDefaultDisLike(String defaultDisLike) {
        this.defaultDisLike = defaultDisLike;
    }

    public void setDefaultGravity(int defaultGravity) {
        this.defaultGravity = defaultGravity;
    }

    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    //dp转px
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //px转dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
