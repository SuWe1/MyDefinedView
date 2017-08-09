package swy.com.mydefinedview.demo3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import swy.com.mydefinedview.R;

/**
 * Created by Swy on 2017/8/9.
 */

public class DemoThreeActivity extends AppCompatActivity {

    DemoThreeView demoThreeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo3_main_layout);
        demoThreeView= (DemoThreeView) findViewById(R.id.smileView);
        demoThreeView.setNum(20,60);
    }
}
