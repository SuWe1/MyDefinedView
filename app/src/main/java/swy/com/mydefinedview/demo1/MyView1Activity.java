package swy.com.mydefinedview.demo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import swy.com.mydefinedview.R;

/**
 * Created by Swy on 2017/7/7.
 */

public class MyView1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myview1);
    }
}
