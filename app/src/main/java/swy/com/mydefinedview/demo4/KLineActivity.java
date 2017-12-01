package swy.com.mydefinedview.demo4;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import swy.com.mydefinedview.R;

/**
 * Created by Swy on 2017/12/1.
 * 绘制简单K线
 */

public class KLineActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.kline_layout);
    }
}
