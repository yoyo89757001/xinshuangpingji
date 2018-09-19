package megvii.testfacepass.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;



import megvii.facepass.types.FacePassImageRotation;
import megvii.testfacepass.R;
import megvii.testfacepass.utils.SettingVar;

/**
 * Created by wangzhiqiang on 2017/11/22.
 */

public class SettingActivity extends Activity implements View.OnClickListener {

    LinearLayout settingLayout;
    Button okButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        okButton = (Button) findViewById(R.id.ok);
        cancelButton = (Button) findViewById(R.id.cancel);
        okButton.setOnClickListener(SettingActivity.this);
        cancelButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                SettingVar.isSettingAvailable = true;
                SharedPreferences preferences = getSharedPreferences(SettingVar.SharedPrefrence, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("cameraFacingFront", SettingVar.cameraFacingFront);
                editor.putBoolean("isSettingAvailable", SettingVar.isSettingAvailable);
                editor.putBoolean("isCross", SettingVar.isCross);
                editor.putInt("faceRotation", SettingVar.faceRotation);
                editor.putInt("cameraPreviewRotation", SettingVar.cameraPreviewRotation);
                editor.apply();
//                Intent intent = new Intent(SettingActivity.this, SheZhiActivity.class);
//                startActivity(intent);
                SettingActivity.this.finish();
                break;
            case R.id.cancel:
                SettingVar.isSettingAvailable = false;
                SharedPreferences preference = getSharedPreferences(SettingVar.SharedPrefrence, Context.MODE_PRIVATE);
                SharedPreferences.Editor edito = preference.edit();
                edito.putBoolean("cameraFacingFront", true);
                edito.putBoolean("isSettingAvailable", false);
                edito.putBoolean("isCross", false);
                edito.putInt("faceRotation", FacePassImageRotation.DEG270);
                edito.putInt("cameraPreviewRotation", 90);
                edito.apply();
//                Intent intent1 = new Intent(SettingActivity.this, SheZhiActivity.class);
//                startActivity(intent1);
                SettingActivity.this.finish();
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.front:
                if (checked) {
                    SettingVar.cameraFacingFront = true;
                }
                break;
            case R.id.back:
                if (checked) {
                    SettingVar.cameraFacingFront = false;
                }
                break;
        }
    }

    public void onCameraRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.camera0:
                if (checked) {
                    SettingVar.cameraPreviewRotation = 0;
                }
                break;
            case R.id.camera90:
                if (checked) {
                    SettingVar.cameraPreviewRotation = 90;
                }
                break;
            case R.id.camera180:
                if (checked) {
                    SettingVar.cameraPreviewRotation = 180;
                }
                break;
            case R.id.camera270:
                if (checked) {
                    SettingVar.cameraPreviewRotation = 270;
                }
                break;
        }
    }

    public void onCross(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.YES:
                if (checked) {
                    SettingVar.isCross = true;
                }
                break;
            case R.id.NO:
                if (checked) {
                    SettingVar.isCross = false;
                }
                break;
        }
    }

    public void onFaceRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.face0:
                if (checked) {
                    SettingVar.faceRotation = FacePassImageRotation.DEG0;
                }
                break;
            case R.id.face90:
                if (checked) {
                    SettingVar.faceRotation = FacePassImageRotation.DEG90;
                }
                break;
            case R.id.face180:
                if (checked) {
                    SettingVar.faceRotation = FacePassImageRotation.DEG180;
                }
                break;
            case R.id.face270:
                if (checked) {
                    SettingVar.faceRotation = FacePassImageRotation.DEG270;
                }
                break;
        }
    }

}
