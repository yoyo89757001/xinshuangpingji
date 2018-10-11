package megvii.testfacepass.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import megvii.testfacepass.MyApplication;
import megvii.testfacepass.R;
import megvii.testfacepass.adapter.PopupWindowAdapter;
import megvii.testfacepass.beans.BaoCunBean;
import megvii.testfacepass.beans.UserInfoBena;
import megvii.testfacepass.cookies.CookiesManager;
import megvii.testfacepass.dialog.ChengGongDialog;
import megvii.testfacepass.dialog.JiaZaiDialog;
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
    TextView e4;
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
    private PopupWindow popupWindow=null;
    private List<String> stringList=new ArrayList<>();
    private String shiyou;
    private String lingshiName=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deng_ji);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);//订阅
        stringList.add("其它");
        stringList.add("面试");
        stringList.add("商务");
        stringList.add("亲友");
        stringList.add("快递");

        userInfoBena = getIntent().getParcelableExtra("xinxi");
        Log.d("DengJiActivity", userInfoBena.getPartyName() + "dddd");
        baoCunBeanDao = MyApplication.myApplication.getBoxStore().boxFor(BaoCunBean.class);
        baoCunBean = baoCunBeanDao.get(123456L);
        if (userInfoBena!=null){
            e1.setText(userInfoBena.getPartyName());
        }

        lingshiName=userInfoBena.getPartyName();


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

        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View contentView = LayoutInflater.from(DengJiActivity.this).inflate(R.layout.xiangmu_po_item, null);
                popupWindow=new PopupWindow(contentView,200, 660);
                ListView listView= (ListView) contentView.findViewById(R.id.dddddd);
                PopupWindowAdapter adapter=new PopupWindowAdapter(DengJiActivity.this,stringList);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        e4.setText(stringList.get(position));
                        popupWindow.dismiss();
                    }
                });
                listView.setAdapter(adapter);

                popupWindow.setFocusable(true);//获取焦点
                popupWindow.setOutsideTouchable(true);//获取外部触摸事件
                popupWindow.setTouchable(true);//能够响应触摸事件
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
                popupWindow.showAsDropDown(e4,0,0);
                Log.d("DengJiActivity", "点击");
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
                        switch (e4.getText().toString().trim()){
                            case "其它":
                                shiyou="其它";
                                break;
                            case "面试":
                                shiyou="面试";
                                break;
                                case "商务":
                                    shiyou="商务";
                                break;
                            case "亲友":
                                shiyou="亲友";
                                break;
                            case "快递":
                                shiyou="快递";
                                break;

                        }

                        if (e1.getText().toString().trim().equals("")){
                            zhiliang(userInfoBena.getScanPhoto(),lingshiName);
                        }else {
                            lingshiName=e1.getText().toString().trim();
                            zhiliang(userInfoBena.getScanPhoto(),lingshiName);
                        }

                    }


                }catch (Exception e){

                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }


    private void zhiliang(String xianchengzhaoPath, final String namess){
       // Log.d("DengJiActivity", ((System.currentTimeMillis() + 31536000000L) + "").substring(0, 10)+"hhhh");
        //Log.d("DengJiActivity", (System.currentTimeMillis() + "").substring(0, 10)+"hhhh");
        Log.d("DengJiActivity", (namess.equals("")?"访客"+(System.currentTimeMillis()+"").substring(9,13):userInfoBena.getPartyName()));
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
//                 .addFormDataPart("name" , namess.equals("")?"访客"+(System.currentTimeMillis()+"").substring(9,13):userInfoBena.getPartyName())
//                .addFormDataPart("subject_type" , "1")
//                .addFormDataPart("purpose" ,shiyou+"")
//                .addFormDataPart("phone" , e2.getText().toString().trim())
//                .addFormDataPart("come_from" , e3.getText().toString().trim())
//                .addFormDataPart("interviewee" , e5.getText().toString().trim())
//                .addFormDataPart("description" , "被访楼层:"+e8.getText().toString().trim())
//                .addFormDataPart("remark" , "手机:"+e6.getText().toString().trim())
//                .addFormDataPart("start_time", (System.currentTimeMillis()+"").substring(0,10))
//                .addFormDataPart("end_time" , ((System.currentTimeMillis()+86400000L)+"").substring(0,10))
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(mBody)
                .url(baoCunBean.getHoutaiDiZhi() + "/subject/photo");
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
                    if (code==0) {

                        chuangjian(jsonObject.get("data").getAsJsonObject().get("id").getAsLong(), namess);

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


    private void chuangjian(long id, String namess){
       // Log.d("DengJiActivity", namess+" GGGGGGG");
     //   Log.d("DengJiActivity", namess.equals("") ? "访客" + (System.currentTimeMillis() + "").substring(9, 13) : userInfoBena.getPartyName());
        final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        JSONArray jsonArray= new JSONArray();
        jsonArray.put(id);
        try {
            json.put("name" , namess.equals("")?"访客"+(System.currentTimeMillis()+"").substring(9,13):namess);
            json.put("subject_type" , "1");
          //  json.put("department" ,"来访事由:"+shiyou);
            json.put("photo_ids" ,jsonArray);
            json.put("phone" , e2.getText().toString().trim());
           // json.put("department" , "来访人公司:"+e3.getText().toString().trim());
            json.put("interviewee" , e5.getText().toString().trim());
            json.put("description" , "被访事由:"+shiyou+" 被访楼层:"+e8.getText().toString().trim());
            json.put("remark" , "手机:"+e6.getText().toString().trim());
            json.put("start_time", (System.currentTimeMillis()+"").substring(0,10));
            json.put("end_time" , ((System.currentTimeMillis()+86400000L)+"").substring(0,10));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, json.toString());

        Request.Builder requestBuilder = new Request.Builder()
             //   .header("user-agent", "Koala Admin")
                .post(requestBody)
                .url(baoCunBean.getHoutaiDiZhi() + "/subject");
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
                    Log.d("LingShiFangKeActivity", "创建"+ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    int code=jsonObject.get("code").getAsInt();
                    if (code==0) {

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
