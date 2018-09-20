package megvii.testfacepass.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import megvii.testfacepass.MyApplication;
import megvii.testfacepass.R;
import megvii.testfacepass.beans.BaoCunBean;
import megvii.testfacepass.beans.UserInfoBena;
import megvii.testfacepass.cookies.CookiesManager;
import megvii.testfacepass.dialog.ChengGongDialog;
import megvii.testfacepass.dialog.JiaZaiDialog;
import megvii.testfacepass.dialog.TongGuoDialog;
import megvii.testfacepass.utils.GsonUtil;
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

public class DengJiActivity extends AppCompatActivity {
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.e1)
    EditText e1;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.e2)
    EditText e2;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.e3)
    EditText e3;
    @BindView(R.id.t4)
    TextView t4;
    @BindView(R.id.e4)
    EditText e4;
    @BindView(R.id.t5)
    TextView t5;
    @BindView(R.id.e5)
    EditText e5;
    @BindView(R.id.t6)
    TextView t6;
    @BindView(R.id.e6)
    EditText e6;
    @BindView(R.id.t7)
    TextView t7;
    @BindView(R.id.e7)
    EditText e7;
    @BindView(R.id.t8)
    TextView t8;
    @BindView(R.id.e8)
    EditText e8;
    @BindView(R.id.tijiao)
    Button tijiao;
    private UserInfoBena userInfoBena = null;
    private OkHttpClient okHttpClient=null;
    public static final int TIMEOUT = 1000 * 30;
    private JiaZaiDialog jiaZaiDialog = null;
    private Box<BaoCunBean> baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deng_ji);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);//订阅

        userInfoBena = getIntent().getParcelableExtra("xinxi");
        Log.d("DengJiActivity", userInfoBena.getPartyName() + "dddd");
        baoCunBeanDao = MyApplication.myApplication.getBoxStore().boxFor(BaoCunBean.class);
        baoCunBean = baoCunBeanDao.get(123456L);
        if (userInfoBena!=null){
            t1.setText(userInfoBena.getPartyName());
        }


        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jiaZaiDialog=new JiaZaiDialog(DengJiActivity.this);
                jiaZaiDialog.setCanceledOnTouchOutside(false);
                jiaZaiDialog.setText("数据请求中...");
                jiaZaiDialog.show();

                denglu();

            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(String event) {
        if (event.equals("guanbi")){
            finish();
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
                        Toast tastyToast= TastyToast.makeText(DengJiActivity.this,"登陆后台出错!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
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
                    Log.d("LingShiFangKeActivity", "登陆"+ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    int code=jsonObject.get("code").getAsInt();
                    if (code==0){
                        zhiliang(userInfoBena.getScanPhoto());
                    }


                }catch (Exception e){

                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }


    private void zhiliang(String xianchengzhaoPath){
       // Log.d("DengJiActivity", ((System.currentTimeMillis() + 31536000000L) + "").substring(0, 10)+"hhhh");
        //Log.d("DengJiActivity", (System.currentTimeMillis() + "").substring(0, 10)+"hhhh");

        File file2 = new File(xianchengzhaoPath);
        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
        String file2Name =System.currentTimeMillis()+"testFile2.jpg";
//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";
        MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                /* 底下是上传了两个文件 */
                //  .addFormDataPart("image_1" , file1Name , fileBody1)
                /* 上传一个普通的String参数 */
                .addFormDataPart("photo" , file2Name , fileBody2)
                 .addFormDataPart("name" , "访客"+(System.currentTimeMillis()+"").substring(9,13))
                .addFormDataPart("subject_type" , "1")
                .addFormDataPart("start_time", (System.currentTimeMillis()+"").substring(0,10))
                .addFormDataPart("end_time" , ((System.currentTimeMillis()+31536000000L)+"").substring(0,10))
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(mBody)
                .url(baoCunBean.getHoutaiDiZhi() + "/subject/file");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求识别失败"+e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //通过
                        final ChengGongDialog dialog=new ChengGongDialog(DengJiActivity.this);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setDate();
                        dialog.setOnPositiveListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                if (jiaZaiDialog!=null){
                                    jiaZaiDialog.dismiss();
                                    jiaZaiDialog=null;
                                }
                                EventBus.getDefault().post("guanbi");
                            }
                        });
                        dialog.show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss=body.string();
                    Log.d("AllConnects", "入库"+ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    int code=jsonObject.get("code").getAsInt();
                    if (code==0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //通过
                                final ChengGongDialog dialog=new ChengGongDialog(DengJiActivity.this);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setOnPositiveListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        if (jiaZaiDialog!=null){
                                            jiaZaiDialog.dismiss();
                                            jiaZaiDialog=null;
                                        }
                                        EventBus.getDefault().post("guanbi");
                                    }
                                });
                                dialog.show();
                            }
                        });

                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //通过
                                final ChengGongDialog dialog=new ChengGongDialog(DengJiActivity.this);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setDate();
                                dialog.setOnPositiveListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        if (jiaZaiDialog!=null){
                                            jiaZaiDialog.dismiss();
                                            jiaZaiDialog=null;
                                        }
                                        EventBus.getDefault().post("guanbi");
                                    }
                                });
                                dialog.show();
                            }
                        });
                    }

                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //通过
                            final ChengGongDialog dialog=new ChengGongDialog(DengJiActivity.this);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setDate();
                            dialog.setOnPositiveListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    if (jiaZaiDialog!=null){
                                        jiaZaiDialog.dismiss();
                                        jiaZaiDialog=null;
                                    }
                                    EventBus.getDefault().post("guanbi");
                                }
                            });
                            dialog.show();
                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);//解除订阅
        super.onDestroy();
    }
}
