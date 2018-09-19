package megvii.testfacepass.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.ButterKnife;
import megvii.testfacepass.R;

public class DengJiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deng_ji);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);


    }
}
