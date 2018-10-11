package megvii.testfacepass.ui;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.YuvImage;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import io.objectbox.Box;
import megvii.facepass.FacePassException;
import megvii.facepass.FacePassHandler;
import megvii.facepass.types.FacePassAddFaceResult;
import megvii.facepass.types.FacePassDetectionResult;
import megvii.facepass.types.FacePassFace;
import megvii.facepass.types.FacePassImage;
import megvii.facepass.types.FacePassImageRotation;
import megvii.facepass.types.FacePassImageType;
import megvii.facepass.types.FacePassRecognitionResult;
import megvii.facepass.types.FacePassRecognitionResultType;
import megvii.testfacepass.MyApplication;
import megvii.testfacepass.R;
import megvii.testfacepass.beans.BaoCunBean;
import megvii.testfacepass.beans.Subject;
import megvii.testfacepass.beans.UserInfoBena;
import megvii.testfacepass.camera.CameraManager;
import megvii.testfacepass.camera.CameraPreview;
import megvii.testfacepass.camera.CameraPreviewData;
import megvii.testfacepass.cookies.CookiesManager;
import megvii.testfacepass.dialog.RenGongDialog;
import megvii.testfacepass.dialog.TongGuoDialog;
import megvii.testfacepass.utils.GsonUtil;
import megvii.testfacepass.utils.SettingVar;
import megvii.testfacepass.view.FaceView;
import megvii.testfacepass.view.FileUtil;
import megvii.testfacepass.view.GlideCircleTransform;
import megvii.testfacepass.view.GlideRoundTransform;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class RActivity extends Activity implements CameraManager.CameraListener {


    /* 程序所需权限 ：相机 文件存储 网络访问 */
    private static final int PERMISSIONS_REQUEST = 1;
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String PERMISSION_READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String PERMISSION_INTERNET = Manifest.permission.INTERNET;
    private static final String PERMISSION_ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE;
    private ImageView imageView;

    private RequestOptions myOptions = new RequestOptions()
            .fitCenter()
            .error(R.drawable.erroy_bg)
            .transform(new GlideCircleTransform(MyApplication.myApplication, 2, Color.parseColor("#ffffffff")));
    // .transform(new GlideRoundTransform(MainActivity.this,10));

    private RequestOptions myOptions2 = new RequestOptions()
            .fitCenter()
            .error(R.drawable.erroy_bg)
            //   .transform(new GlideCircleTransform(MyApplication.myApplication, 2, Color.parseColor("#ffffffff")));
            .transform(new GlideRoundTransform(RActivity.this, 20));

    private RelativeLayout relativeLayout;
    private final Timer timer = new Timer();
    private TimerTask task;
    public static final int TIMEOUT = 1000 * 30;
    private String xianchengzhaoPath = null;
    private static final String DEBUG_TAG = "FacePassDemo";
    private static boolean isLink = true;
    private static final String group_name = "face-pass-test-x";
    private UserInfoBena userInfoBena = null;
    /* SDK 实例对象 */
    private FacePassHandler mFacePassHandler;
    /* 相机实例 */
    private CameraManager manager;
    /* 显示人脸位置角度信息 */
    // private XiuGaiGaoKuanDialog dialog = null;
    /* 相机预览界面 */
    private CameraPreview cameraView;

    /* 在预览界面圈出人脸 */
    private FaceView faceView;
    /* 相机是否使用前置摄像头 */
    private static boolean cameraFacingFront = true;
    private boolean isDL=false;
    private int cameraRotation;
    private static final int cameraWidth = 1080;
    private static final int cameraHeight = 720;
    RecognizeThread mRecognizeThread;

    private int heightPixels;
    private int widthPixels;
    int screenState = 0;// 0 横 1 竖
    ArrayBlockingQueue<byte[]> mDetectResultQueue;
    ArrayBlockingQueue<FacePassImage> mFeedFrameQueue;
    /*recognize thread*/
    FeedFrameThread mFeedFrameThread;
    private int dw, dh;
    private Box<BaoCunBean> baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;
    // private TodayBean todayBean = null;
    private IntentFilter intentFilter;
    private TimeChangeReceiver timeChangeReceiver;
    private WeakHandler mHandler;
    private TextView tishi;
    private OkHttpClient okHttpClient=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mImageCache = new FaceImageCache();
        mDetectResultQueue = new ArrayBlockingQueue<byte[]>(5);
        mFeedFrameQueue = new ArrayBlockingQueue<FacePassImage>(1);
        // todayBeanBox = MyApplication.myApplication.getBoxStore().boxFor(TodayBean.class);
        // todayBean = todayBeanBox.get(123456L);
        // initAndroidHandler();
        // isOne = true;

        baoCunBeanDao = MyApplication.myApplication.getBoxStore().boxFor(BaoCunBean.class);
        baoCunBean = baoCunBeanDao.get(123456L);
        isLink=true;
        //每分钟的广播
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);//每分钟变化
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);//设置了系统时区
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);//设置了系统时间
        timeChangeReceiver = new TimeChangeReceiver();
        registerReceiver(timeChangeReceiver, intentFilter);
        //linkedBlockingQueue = new LinkedBlockingQueue<>();

        EventBus.getDefault().register(this);//订阅
        /* 初始化界面 */

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        dw = dm.widthPixels;
        dh = dm.heightPixels;

        userInfoBena=new UserInfoBena();
        userInfoBena.setPartyName("");

        initView();

        mFeedFrameThread = new FeedFrameThread();
        mFeedFrameThread.start();

       // mRecognizeThread = new RecognizeThread();
       // mRecognizeThread.start();

        mHandler = new WeakHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 111:
                        //弹窗
                        Subject bean = (Subject) msg.obj;

                }
                return false;
            }
        });

        mFacePassHandler = MyApplication.myApplication.getFacePassHandler();
        if (baoCunBean.getZhanghao()==null || baoCunBean.getMima()==null){
            Toast tastyToast = TastyToast.makeText(RActivity.this, "请先设置主机账号和密码", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }else {
            denglu();
        }
    }


    /***
     *保存bitmap对象到文件中
     * @param bm
     * @param path
     * @param quality
     * @return
     */
    public void saveBitmap2File2(Bitmap bm, final String path, int quality) {
        if (null == bm || bm.isRecycled()) {
            Log.d("InFoActivity", "回收|空");
            return;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();

            xianchengzhaoPath = path;
            userInfoBena.setScanPhoto(xianchengzhaoPath);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (!bm.isRecycled()) {
                bm.recycle();
            }
            bm = null;
        }
    }



    /* 判断程序是否有所需权限 android22以上需要自申请权限 */
    private boolean hasPermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(PERMISSION_READ_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(PERMISSION_WRITE_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(PERMISSION_INTERNET) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(PERMISSION_ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onResume() {

        /* 打开相机 */
        if (hasPermission()) {
            manager.open(getWindowManager(), cameraFacingFront, cameraWidth, cameraHeight);
        }
        adaptFrameLayout();
        super.onResume();
    }


    /* 相机回调函数 */
    @Override
    public void onPictureTaken(CameraPreviewData cameraPreviewData) {
        /* 如果SDK实例还未创建，则跳过 */
        if (mFacePassHandler == null) {
            return;
        }
        /* 将相机预览帧转成SDK算法所需帧的格式 FacePassImage */
        FacePassImage image;
        try {
            image = new FacePassImage(cameraPreviewData.nv21Data, cameraPreviewData.width, cameraPreviewData.height, cameraRotation, FacePassImageType.NV21);
        } catch (FacePassException e) {
            e.printStackTrace();
            return;
        }
        mFeedFrameQueue.offer(image);

    }

    private class FeedFrameThread extends Thread {
        boolean isIterrupt;

        @Override
        public void run() {
            while (!isIterrupt) {
                try {

                    FacePassImage image = mFeedFrameQueue.take();
                    /* 将每一帧FacePassImage 送入SDK算法， 并得到返回结果 */
                    FacePassDetectionResult detectionResult = null;
                    detectionResult = mFacePassHandler.feedFrame(image);
                    if (detectionResult == null || detectionResult.faceList.length == 0) {
                        //没人脸的时候

                        faceView.clear();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tishi.setVisibility(View.GONE);
                                faceView.invalidate();
                            }
                        });
                    } else {
                        //拿陌生人图片
                        showFacePassFace(detectionResult.faceList, image);
                        //   Log.d("FeedFrameThread", "detectionResult.images.length:" + image.width+"  "+image.height);
                    }


//                    /*离线模式，将识别到人脸的，message不为空的result添加到处理队列中*/
//                    if (detectionResult != null && detectionResult.message.length != 0) {
//                        mDetectResultQueue.offer(detectionResult.message);
//                        // Log.d(DEBUG_TAG, "1 mDetectResultQueue.size = " + mDetectResultQueue.size());
//                    }
                    //     }

                } catch (InterruptedException | FacePassException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void interrupt() {
            isIterrupt = true;
            super.interrupt();
        }
    }


    private class RecognizeThread extends Thread {

        boolean isInterrupt;

        @Override
        public void run() {
            while (!isInterrupt) {
                try {

                    byte[] detectionResult = mDetectResultQueue.take();
                    FacePassRecognitionResult[] recognizeResult = mFacePassHandler.recognize(group_name, detectionResult);
                    Log.d("RecognizeThread", "识别线程");
                    if (recognizeResult != null && recognizeResult.length > 0) {
                        for (FacePassRecognitionResult result : recognizeResult) {
                            //String faceToken = new String(result.faceToken);
                            if (FacePassRecognitionResultType.RECOG_OK == result.facePassRecognitionResultType) {
                                //识别的

                            } else {

                                //未识别的
                                // ConcurrentHashMap 建议用他去重
                                //   detectionResult.faceList
                                Log.d("RecognizeThread", "未识别的" + result.trackId);
                            }

                        }
                    }

                } catch (InterruptedException | FacePassException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void interrupt() {
            isInterrupt = true;
            super.interrupt();
        }

    }


    private void adaptFrameLayout() {
        SettingVar.isButtonInvisible = false;
        SettingVar.iscameraNeedConfig = false;
    }


    private void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        int windowRotation = ((WindowManager) (getApplicationContext().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getRotation() * 90;
        if (windowRotation == 0) {
            cameraRotation = FacePassImageRotation.DEG90;
        } else if (windowRotation == 90) {
            cameraRotation = FacePassImageRotation.DEG0;
        } else if (windowRotation == 270) {
            cameraRotation = FacePassImageRotation.DEG180;
        } else {
            cameraRotation = FacePassImageRotation.DEG270;
        }
//        Log.i(DEBUG_TAG, "cameraRation: " + cameraRotation);
        cameraFacingFront = true;
        SharedPreferences preferences = getSharedPreferences(SettingVar.SharedPrefrence, Context.MODE_PRIVATE);
        SettingVar.isSettingAvailable = preferences.getBoolean("isSettingAvailable", SettingVar.isSettingAvailable);
        SettingVar.isCross = preferences.getBoolean("isCross", SettingVar.isCross);
        SettingVar.faceRotation = preferences.getInt("faceRotation", SettingVar.faceRotation);
        SettingVar.cameraPreviewRotation = preferences.getInt("cameraPreviewRotation", SettingVar.cameraPreviewRotation);
        SettingVar.cameraFacingFront = preferences.getBoolean("cameraFacingFront", SettingVar.cameraFacingFront);
        if (SettingVar.isSettingAvailable) {
            cameraRotation = SettingVar.faceRotation;
            cameraFacingFront = SettingVar.cameraFacingFront;
        }
        //  Log.i("orientation", String.valueOf(windowRotation));
        final int mCurrentOrientation = getResources().getConfiguration().orientation;

        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            screenState = 1;
        } else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            screenState = 0;
        }

        setContentView(R.layout.activity_rengong);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
      //  ButterKnife.bind(this);
        AssetManager mgr = getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Univers LT 57 Condensed.ttf");
        Typeface tf2 = Typeface.createFromAsset(mgr, "fonts/hua.ttf");
        Typeface tf3 = Typeface.createFromAsset(mgr, "fonts/kai.ttf");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        heightPixels = displayMetrics.heightPixels;
        widthPixels = displayMetrics.widthPixels;
        SettingVar.mHeight = heightPixels;
        SettingVar.mWidth = widthPixels;
        SettingVar.cameraSettingOk = false;
        faceView = findViewById(R.id.fcview);
        manager = new CameraManager();
        cameraView = findViewById(R.id.preview);
        manager.setPreviewDisplay(cameraView);
        imageView=findViewById(R.id.im1);
        /* 注册相机回调函数 */
        manager.setListener(this);
        tishi = findViewById(R.id.tishi);


    }

    @Override
    protected void onStop() {
        SettingVar.isButtonInvisible = false;
        mDetectResultQueue.clear();
        mFeedFrameQueue.clear();
        if (manager != null) {
            manager.release();
        }

        super.onStop();
    }

    @Override
    protected void onRestart() {
        faceView.clear();
        faceView.invalidate();

        //  if (shipingView!=null)
        // shipingView.start();
        super.onRestart();
    }

    @Override
    public void onStart() {
        super.onStart();
        //marqueeView.startFlipping();
    }


    @Override
    protected void onDestroy() {

        if (mFeedFrameQueue != null) {
            mFeedFrameQueue.clear();
        }
        if (mFeedFrameThread != null) {
            mFeedFrameThread.isIterrupt = true;
            mFeedFrameThread.interrupt();
        }

        if (mRecognizeThread != null) {
            mRecognizeThread.isInterrupt = true;
            mRecognizeThread.interrupt();
        }

        unregisterReceiver(timeChangeReceiver);

        EventBus.getDefault().unregister(this);//解除订阅

        if (manager != null) {
            manager.release();
        }

//        if (mFacePassHandler != null) {
//            mFacePassHandler.release();
//        }

        timer.cancel();
        if (task != null)
            task.cancel();

        super.onDestroy();
    }


    private void showFacePassFace(FacePassFace[] detectResult, final FacePassImage imageee) {
        final FacePassFace[] result = detectResult;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                faceView.clear();
                for (FacePassFace face : result) {

                    boolean mirror = cameraFacingFront; /* 前摄像头时mirror为true */
//                    StringBuilder faceIdString = new StringBuilder();
//                    faceIdString.append("ID = ").append(face.trackId);
//                    SpannableString faceViewString = new SpannableString(faceIdString);
//                    faceViewString.setSpan(new TypefaceSpan("fonts/kai"), 0, faceViewString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    StringBuilder faceRollString = new StringBuilder();
//                    faceRollString.append("旋转: ").append((int) face.pose.roll).append("°");
//                    StringBuilder facePitchString = new StringBuilder();
//                    facePitchString.append("上下: ").append((int) face.pose.pitch).append("°");
//                    StringBuilder faceYawString = new StringBuilder();
//                    faceYawString.append("左右: ").append((int) face.pose.yaw).append("°");
//                    StringBuilder faceBlurString = new StringBuilder();
//                    faceBlurString.append("模糊: ").append(String.format("%.2f", face.blur));
//                    StringBuilder faceAgeString = new StringBuilder();
//                    faceAgeString.append("年龄: ").append(face.age);
//                    StringBuilder faceGenderString = new StringBuilder();
//
//                    switch (face.gender) {
//                        case 0:
//                            faceGenderString.append("性别: 男");
//                            break;
//                        case 1:
//                            faceGenderString.append("性别: 女");
//                            break;
//                        default:
//                            faceGenderString.append("性别: ?");
//                    }
//
                    Matrix mat = new Matrix();
                    int w = cameraView.getMeasuredWidth();
                    int h = cameraView.getMeasuredHeight();

                    int cameraHeight = manager.getCameraheight();
                    int cameraWidth = manager.getCameraWidth();

                    float left = 0;
                    float top = 0;
                    float right = 0;
                    float bottom = 0;
                    switch (cameraRotation) {
                        case 0:
                            left = face.rect.left;
                            top = face.rect.top;
                            right = face.rect.right;
                            bottom = face.rect.bottom;
                            mat.setScale(mirror ? -1 : 1, 1);
                            mat.postTranslate(mirror ? (float) cameraWidth : 0f, 0f);
                            mat.postScale((float) w / (float) cameraWidth, (float) h / (float) cameraHeight);
                            break;
                        case 90:
                            mat.setScale(mirror ? -1 : 1, 1);
                            mat.postTranslate(mirror ? (float) cameraHeight : 0f, 0f);
                            mat.postScale((float) w / (float) cameraHeight, (float) h / (float) cameraWidth);
                            left = face.rect.top;
                            top = cameraWidth - face.rect.right;
                            right = face.rect.bottom;
                            bottom = cameraWidth - face.rect.left;

                            //北京面板机特有方向
//                            left =cameraHeight-face.rect.bottom;
//                            top = face.rect.left;
//                            right =cameraHeight-face.rect.top;
//                            bottom =face.rect.right;

                            break;
                        case 180:
                            mat.setScale(1, mirror ? -1 : 1);
                            mat.postTranslate(0f, mirror ? (float) cameraHeight : 0f);
                            mat.postScale((float) w / (float) cameraWidth, (float) h / (float) cameraHeight);
                            left = face.rect.right;
                            top = face.rect.bottom;
                            right = face.rect.left;
                            bottom = face.rect.top;
                            break;
                        case 270:
                            mat.setScale(mirror ? -1 : 1, 1);
                            mat.postTranslate(mirror ? (float) cameraHeight : 0f, 0f);
                            mat.postScale((float) w / (float) cameraHeight, (float) h / (float) cameraWidth);
                            left = cameraHeight - face.rect.bottom;
                            top = face.rect.left;
                            right = cameraHeight - face.rect.top;
                            bottom = face.rect.right;
                    }

                    RectF drect = new RectF();
                    RectF srect = new RectF(left, top, right, bottom);
                    //头像加宽加高点
                    final RectF srect2 = new RectF(face.rect.left - 100 < 0 ? 0 : face.rect.left - 100, face.rect.top - 340 < 0 ? 0 : face.rect.top - 340,
                            face.rect.right + 100 > imageee.width ? imageee.width : face.rect.right + 100, face.rect.bottom + 120 > imageee.height ? imageee.height : face.rect.bottom + 120);

                    float pitch = face.pose.pitch;
                    float roll = face.pose.roll;
                    float yaw = face.pose.yaw;

                    if (pitch < 15 && pitch > -15 && roll < 15 && roll > -15 && yaw < 15 && yaw > -15 && face.blur < 0.2) {
                        if ( isDL&&isLink) {
                            isLink = false;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //获取图片
                                    try {
                                        int ww = imageee.width;
                                        int hh = imageee.height;
                                        final YuvImage image2 = new YuvImage(imageee.image, ImageFormat.NV21, ww, hh, null);
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        image2.compressToJpeg(new Rect(0, 0, ww, hh), 100, stream);
                                        final Bitmap bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                                        stream.close();
                                        int x1, y1, x2, y2 = 0;
                                        x1 = (int) srect2.left;
                                        y1 = (int) srect2.top;
                                        //宽高不是坐标
                                        x2 = (srect2.left + (srect2.right - srect2.left)) > bitmap.getWidth() ? (int) (bitmap.getWidth() - srect2.left) : (int) (srect2.right - srect2.left);
                                        y2 = (srect2.top + (srect2.bottom - srect2.top)) > bitmap.getHeight() ? (int) (bitmap.getHeight() - srect2.top) : (int) (srect2.bottom - srect2.top);

                                        final Bitmap oneBitmap1 = Bitmap.createBitmap(bitmap, x1, y1, x2, y2);
                                            String fn = "ccccc.jpg";
                                            FileUtil.isExists(FileUtil.PATH, fn);
                                            saveBitmap2File2(oneBitmap1.copy(Bitmap.Config.ARGB_8888, false), FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn, 100);
                                            //质量判断
                                        zhiliang(xianchengzhaoPath);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                    imageView.setImageBitmap(oneBitmap1);
                                            }
                                        });


                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        isLink = true;
                                    }

                                }
                            }).start();

                        }

                        //提示
                        tishi.setVisibility(View.VISIBLE);
                        tishi.setText("抓拍中,请稍后...");
                    } else {
                        tishi.setVisibility(View.VISIBLE);
                        tishi.setText("请正对镜头");
                    }


                    mat.mapRect(drect, srect);
                    faceView.addRect(drect);
//                    faceView.addId(faceIdString.toString());
//                    faceView.addRoll(faceRollString.toString());
//                    faceView.addPitch(facePitchString.toString());
//                    faceView.addYaw(faceYawString.toString());
//                    faceView.addBlur(faceBlurString.toString());
//                    faceView.addAge(faceAgeString.toString());
//                    faceView.addGenders(faceGenderString.toString());
                }
                faceView.invalidate();
            }
        });

    }


    private static final int REQUEST_CODE_CHOOSE_PICK = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //从相册选取照片后读取地址
            case REQUEST_CODE_CHOOSE_PICK:
                if (resultCode == RESULT_OK) {

                }
                break;
        }
    }





    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(String event) {
        if (event.equals("mFacePassHandler")) {
            mFacePassHandler = MyApplication.myApplication.getFacePassHandler();
            Toast tastyToast = TastyToast.makeText(RActivity.this, event, TastyToast.LENGTH_LONG, TastyToast.INFO);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
        if (event.equals("guanbi")){
            finish();
        }
    }


    class TimeChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case Intent.ACTION_TIME_TICK:


                    break;
                case Intent.ACTION_TIME_CHANGED:
                    //设置了系统时间
                    // Toast.makeText(context, "system time changed", Toast.LENGTH_SHORT).show();
                    break;
                case Intent.ACTION_TIMEZONE_CHANGED:
                    //设置了系统时区的action
                    //  Toast.makeText(context, "system time zone changed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void denglu(){
        okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(new CookiesManager())
                .build();

        RequestBody body = new FormBody.Builder()
                .add("username",baoCunBean.getZhanghao())
                .add("password",baoCunBean.getMima())
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                 .header("user-agent", "Koala Admin")
                .post(body)
                .url(baoCunBean.getHoutaiDiZhi() + "/auth/login");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast tastyToast= TastyToast.makeText(RActivity.this,"登陆后台出错!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
                Log.d("AllConnects", "请求识别失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss=body.string();
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                   int code= jsonObject.get("code").getAsInt();
                   if (code==0){
                    isDL=true;
                   }else {
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               Toast tastyToast= TastyToast.makeText(RActivity.this,"登陆后台出错,无法进行后续任务",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                               tastyToast.setGravity(Gravity.CENTER,0,0);
                               tastyToast.show();
                           }
                       });
                   }

                }catch (Exception e){

                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }


    private void zhiliang(String xianchengzhaoPath){
        int code=-1;
        try {
            FacePassAddFaceResult result= mFacePassHandler.addFace(BitmapFactory.decodeFile(xianchengzhaoPath));
            code=result.result;
        } catch (FacePassException e) {
           isLink=true;
        }
        if (code==0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //通过
                                final RenGongDialog dialog=new RenGongDialog(RActivity.this);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setOnPositiveListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        Intent intent=new Intent(RActivity.this,DengJiActivity.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putParcelable("xinxi",userInfoBena);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                                dialog.show();
                            }
                        });

                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tishi.setText("入库质量未达标");
                            }
                        });
                        isLink=true;
                    }
    }



}
