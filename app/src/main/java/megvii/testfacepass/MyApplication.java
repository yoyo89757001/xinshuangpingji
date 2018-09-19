package megvii.testfacepass;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.util.Log;
import android.view.WindowManager;

import com.tencent.bugly.Bugly;
import com.yatoooon.screenadaptation.ScreenAdapterTools;


import io.objectbox.Box;
import io.objectbox.BoxStore;
import megvii.facepass.FacePassHandler;
import megvii.testfacepass.beans.ChengShiIDBean;
import megvii.testfacepass.beans.MyObjectBox;
import megvii.testfacepass.dialogall.CommonData;
import megvii.testfacepass.dialogall.CommonDialogService;
import megvii.testfacepass.dialogall.ToastUtils;
import megvii.testfacepass.utils.FileUtil;


/**
 * Created by Administrator on 2018/8/3.
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    public  FacePassHandler facePassHandler=null;
    private static BoxStore mBoxStore;
    public static MyApplication myApplication;
    private Box<ChengShiIDBean> chengShiIDBeanBox;


    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        mBoxStore = MyObjectBox.builder().androidContext(this).build();

        Bugly.init(getApplicationContext(), "acd60de457", false);
        //适配
        ScreenAdapterTools.init(this);
      Log.d("MyApplication","机器码"+ FileUtil.getSerialNumber(this) == null ? FileUtil.getIMSI() : FileUtil.getSerialNumber(this));
        //全局dialog
        this.registerActivityLifecycleCallbacks(this);//注册
        CommonData.applicationContext = this;
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager  = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        CommonData.ScreenWidth = metric.widthPixels; // 屏幕宽度（像素）
        Intent dialogservice = new Intent(this, CommonDialogService.class);
        startService(dialogservice);



    }



    public BoxStore getBoxStore(){
        return mBoxStore;
    }

    public FacePassHandler getFacePassHandler() {

        return facePassHandler;
    }

    public void setFacePassHandler(FacePassHandler facePassHandler1){
        facePassHandler=facePassHandler1;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if(activity.getParent()!=null){
            CommonData.mNowContext = activity.getParent();
        }else
            CommonData.mNowContext = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(activity.getParent()!=null){
            CommonData.mNowContext = activity.getParent();
        }else
            CommonData.mNowContext = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ToastUtils.getInstances().cancel();
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
