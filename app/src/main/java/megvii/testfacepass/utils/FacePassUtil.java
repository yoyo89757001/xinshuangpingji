package megvii.testfacepass.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;

import megvii.facepass.FacePassException;
import megvii.facepass.FacePassHandler;
import megvii.facepass.types.FacePassConfig;
import megvii.facepass.types.FacePassModel;
import megvii.facepass.types.FacePassPose;
import megvii.testfacepass.MyApplication;
import megvii.testfacepass.R;
import megvii.testfacepass.beans.BaoCunBean;



public class FacePassUtil {
  private   FacePassModel trackModel;
  private     FacePassModel poseModel;
 private    FacePassModel blurModel;
private     FacePassModel livenessModel;
   private    FacePassModel searchModel;
   private FacePassModel detectModel;
  private     FacePassModel ageGenderModel;
    /* SDK 实例对象 */

 private    FacePassHandler mFacePassHandler;  /* 人脸识别Group */
    private  final String group_name = "face-pass-test-x";
    private  boolean isLocalGroupExist = false;

    public  void init(final Activity activity , final Context context, final int cameraRotation, final BaoCunBean baoCunBean){

            new Thread() {
                @Override
                public void run() {
                    while (!activity.isFinishing()) {
                        if (FacePassHandler.isAvailable()) {
                            /* FacePass SDK 所需模型， 模型在assets目录下 */
                            trackModel = FacePassModel.initModel(context.getAssets(), "tracker.DT1.4.1.dingding.20180315.megface2.9.bin");
                            poseModel = FacePassModel.initModel(context.getAssets(), "pose.alfa.tiny.170515.bin");
                            blurModel = FacePassModel.initModel(context.getAssets(), "blurness.v5.l2rsmall.bin");
                            livenessModel = FacePassModel.initModel(context.getAssets(), "panorama.facepass.offline.180312.bin");
                            searchModel = FacePassModel.initModel(context.getAssets(), "feat.small.facepass.v2.9.bin");
                            detectModel = FacePassModel.initModel(context.getAssets(), "detector.mobile.v5.fast.bin");
                            ageGenderModel = FacePassModel.initModel(context.getAssets(), "age_gender.bin");
                            /* SDK 配置 */
                            float searchThreshold = baoCunBean.getShibieFaZhi();
                            float livenessThreshold = baoCunBean.getHuoTiFZ();
                            boolean livenessEnabled = baoCunBean.isHuoTi();
                            int faceMinThreshold =baoCunBean.getShibieFaceSize();
                            FacePassPose poseThreshold = new FacePassPose(26f, 26f, 26f);
                            float blurThreshold = 0.4f;
                            float lowBrightnessThreshold = 70f;
                            float highBrightnessThreshold = 210f;
                            float brightnessSTDThreshold = 60f;
                            int retryCount = 2;
                            int rotation = cameraRotation;
                            String fileRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                            FacePassConfig config;
                            try {

                                /* 填入所需要的配置 */
                                config = new FacePassConfig(searchThreshold, livenessThreshold, livenessEnabled,
                                        faceMinThreshold, poseThreshold, blurThreshold,
                                        lowBrightnessThreshold, highBrightnessThreshold, brightnessSTDThreshold,
                                        retryCount, rotation, fileRootPath,
                                        trackModel, poseModel, blurModel, livenessModel, searchModel, detectModel, ageGenderModel);
                                /* 创建SDK实例 */
                                mFacePassHandler = new FacePassHandler(config);
                                MyApplication.myApplication.setFacePassHandler(mFacePassHandler);
                                try {

                                    boolean a=  mFacePassHandler.createLocalGroup(group_name);

                                } catch (FacePassException e) {
                                    e.printStackTrace();
                                }

//                                float searchThreshold2 = 75f;
//                                float livenessThreshold2 = 48f;
//                                boolean livenessEnabled2 = true;
                                int faceMinThreshold2 = baoCunBean.getRuKuFaceSize();
                                float blurThreshold2 = baoCunBean.getRuKuMoHuDu();
                                float lowBrightnessThreshold2 = 70f;
                                float highBrightnessThreshold2 = 210f;
                                float brightnessSTDThreshold2 = 60f;
                                FacePassConfig config1=new FacePassConfig(faceMinThreshold2,20f,20f,20f,blurThreshold2,
                                        lowBrightnessThreshold2,highBrightnessThreshold2,brightnessSTDThreshold2);

                                Log.d("YanShiActivity", "设置入库质量配置" + mFacePassHandler.setAddFaceConfig(config1));

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast tastyToast= TastyToast.makeText(context,"识别模块初始化成功",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                        tastyToast.setGravity(Gravity.CENTER,0,0);
                                        tastyToast.show();
                                       MyApplication.myApplication.setFacePassHandler(mFacePassHandler);
                                        try {
                                            mFacePassHandler.compare(BitmapFactory.decodeResource(context.getResources(), R.drawable.fugui),
                                                    BitmapFactory.decodeResource(context.getResources(), R.drawable.fugui),false);
                                        } catch (FacePassException e) {
                                            e.printStackTrace();
                                        }
                                        EventBus.getDefault().post("mFacePassHandler");
                                    }
                                });

                                checkGroup(activity,context);


                            } catch (FacePassException e) {
                                e.printStackTrace();

                                return;
                            }
                            return;
                        }
                        try {
                            /* 如果SDK初始化未完成则需等待 */
                            sleep(500);
                            Log.d("FacePassUtil", "初始化中");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }


    private  void checkGroup(Activity activity, final Context context) {
        if (mFacePassHandler == null) {
            return;
        }
        String[] localGroups = mFacePassHandler.getLocalGroups();
        isLocalGroupExist = false;
        if (localGroups == null || localGroups.length == 0) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast tastyToast= TastyToast.makeText(context,"还没创建识别组",TastyToast.LENGTH_LONG,TastyToast.INFO);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();
                }
            });
            return;
        }
        for (String group : localGroups) {
            if (group_name.equals(group)) {

                isLocalGroupExist = true;
            }
        }
        if (!isLocalGroupExist) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast tastyToast= TastyToast.makeText(context,"还没创建识别组",TastyToast.LENGTH_LONG,TastyToast.INFO);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();
                }
            });
        }
    }


}
