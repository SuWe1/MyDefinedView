package swy.com.mydefinedview.demo5;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import swy.com.mydefinedview.R;

/**
 * Created by Swy on 2018/4/10.
 */

public class SingleChoiceThemeView extends LinearLayout {

    private View backgroundView;
    private ImageView clickImg;
    private TextView nameTv;
    private String mName;
    private int mColor;
    private boolean mChoice;

    public SingleChoiceThemeView(Context context) {
        super(context);
    }

    public SingleChoiceThemeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public SingleChoiceThemeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SingleChoiceThemeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_setting_single_choice_theme, this);
        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.SingleChoiceThemeView);
        mName = t.getString(R.styleable.SingleChoiceThemeView_android_name);
        mColor = t.getResourceId(R.styleable.SingleChoiceThemeView_view_background,0);
        mChoice = t.getBoolean(R.styleable.SingleChoiceThemeView_choice, false);
        t.recycle();
        setOrientation(VERTICAL);
        initView();
    }

    private void initView(){
        backgroundView = findViewById(R.id.view_background);
        clickImg = (ImageView) findViewById(R.id.iv_color_icon);
        nameTv = (TextView) findViewById(R.id.tv_name);

        if (mColor!=0){
            backgroundView.setBackgroundResource(mColor);
        }
        if (!TextUtils.isEmpty(mName)){
            nameTv.setText(mName);
        }
        clickImg.setVisibility(mChoice ? VISIBLE : GONE);
    }

    public boolean isChoice() {
        return mChoice;
    }

    public void setChoice(boolean mChoice) {
        this.mChoice = mChoice;
        clickImg.setVisibility(mChoice ? VISIBLE : GONE);
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
        if (!TextUtils.isEmpty(mName)){
            nameTv.setText(mName);
        }
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
        if (mColor!=0){
            backgroundView.setBackgroundResource(mColor);
        }
    }
}
