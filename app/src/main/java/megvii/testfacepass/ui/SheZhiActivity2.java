package megvii.testfacepass.ui;

import android.app.Activity;


import android.content.Intent;


import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import android.widget.Toast;



import com.sdsmdg.tastytoast.TastyToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import megvii.testfacepass.MyApplication;
import megvii.testfacepass.R;
import megvii.testfacepass.beans.BaoCunBean;
import megvii.testfacepass.beans.ChengShiIDBean;
import megvii.testfacepass.beans.JsonBean;
import megvii.testfacepass.beans.Subject;
import megvii.testfacepass.dialog.BangDingDialog;
import megvii.testfacepass.dialog.XiuGaiDiZhiDialog;
import megvii.testfacepass.dialog.XiuGaiHuoTiFZDialog;
import megvii.testfacepass.dialog.XiuGaiRuKuFZDialog;
import megvii.testfacepass.dialog.XiuGaiSBFZDialog;
import megvii.testfacepass.dialog.YuYingDialog;

import megvii.testfacepass.utils.FaceInit;


import megvii.testfacepass.utils.RestartAPPTool;
import megvii.testfacepass.utils.SettingVar;


public class SheZhiActivity2 extends Activity {
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl4)
    RelativeLayout rl4;
    @BindView(R.id.rl6)
    RelativeLayout rl6;
    @BindView(R.id.rl7)
    RelativeLayout rl7;
    @BindView(R.id.switchs)
    Switch switchs;
    @BindView(R.id.rl5)
    RelativeLayout rl5;
    @BindView(R.id.rl9)
    RelativeLayout rl9;

    private BangDingDialog bangDingDialog=null;
    private int cameraRotation;
    private static final String group_name = "face-pass-test-x";
    private Box<BaoCunBean> baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi2);
        ButterKnife.bind(this);
        //ScreenAdapterTools.getInstance().reset(this);//如果希望android7.0分屏也适配的话,加上这句
        //在setContentView();后面加上适配语句
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        baoCunBeanDao = MyApplication.myApplication.getBoxStore().boxFor(BaoCunBean.class);
        baoCunBean = baoCunBeanDao.get(123456L);
        if (baoCunBean == null) {
            baoCunBean = new BaoCunBean();
            baoCunBean.setId(123456L);
            baoCunBean.setHoutaiDiZhi("http://192.168.2.187");
            baoCunBean.setShibieFaceSize(60);
            baoCunBean.setShibieFaZhi(70);
            baoCunBean.setRuKuFaceSize(50);
            baoCunBean.setRuKuMoHuDu(0.6f);
            baoCunBean.setHuoTiFZ(70);
            baoCunBean.setHuoTi(true);
            baoCunBean.setDangqianShiJian("d");
            baoCunBean.setTianQi(false);
            baoCunBeanDao.put(baoCunBean);
        }

        EventBus.getDefault().register(this);//订阅
        switchs.setChecked(true);
        switchs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    baoCunBean.setHuoTi(true);
                    baoCunBeanDao.put(baoCunBean);
                    Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "活体验证已开启", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                } else {
                    baoCunBean.setHuoTi(false);
                    baoCunBeanDao.put(baoCunBean);
                    Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "活体验证已关闭", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                }

            }
        });


        if (SettingVar.isSettingAvailable) {
            cameraRotation = SettingVar.faceRotation;
        }

    }


    @OnClick({R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.rl6, R.id.rl7, R.id.rl9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl1:
                final XiuGaiDiZhiDialog diZhiDialog = new XiuGaiDiZhiDialog(SheZhiActivity2.this);
                diZhiDialog.setCanceledOnTouchOutside(false);
                diZhiDialog.setContents(baoCunBean.getHoutaiDiZhi(), null);
                diZhiDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baoCunBean.setHoutaiDiZhi(diZhiDialog.getUrl());
                        baoCunBean.setZhanghao(diZhiDialog.getZhangHao());
                        baoCunBean.setMima(diZhiDialog.getMiMa());
                        baoCunBeanDao.put(baoCunBean);
                        diZhiDialog.dismiss();
                    }
                });
                diZhiDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diZhiDialog.dismiss();
                    }
                });
                diZhiDialog.show();

                break;
            case R.id.rl2:
                 bangDingDialog = new BangDingDialog(SheZhiActivity2.this);
                bangDingDialog.setCanceledOnTouchOutside(false);
                bangDingDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FaceInit init=new FaceInit(SheZhiActivity2.this);
                        init.init(bangDingDialog.getZhuCeMa(),baoCunBean.getHoutaiDiZhi());
                        bangDingDialog.jiazai();
                    }
                });
                bangDingDialog.setCanceledOnTouchOutside(false);
                bangDingDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bangDingDialog.dismiss();
                    }
                });
                bangDingDialog.show();
                break;
            case R.id.rl3:
                YuYingDialog yuYingDialog = new YuYingDialog(SheZhiActivity2.this);
                yuYingDialog.show();
                break;
            case R.id.rl4:
                //识别阀值
                final XiuGaiSBFZDialog sbfzDialog = new XiuGaiSBFZDialog(SheZhiActivity2.this);
                sbfzDialog.setContents(baoCunBean.getShibieFaZhi() + "", baoCunBean.getShibieFaceSize() + "");
                sbfzDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            baoCunBean.setShibieFaZhi(Integer.valueOf(sbfzDialog.getFZ()));
                            baoCunBean.setShibieFaceSize(Integer.valueOf(sbfzDialog.getFaceSize()));
                            baoCunBeanDao.put(baoCunBean);
                            sbfzDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                sbfzDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sbfzDialog.dismiss();
                    }
                });
                sbfzDialog.show();
                break;
            case R.id.rl5:
                final XiuGaiHuoTiFZDialog huoTiFZDialog = new XiuGaiHuoTiFZDialog(SheZhiActivity2.this);
                huoTiFZDialog.setContents(baoCunBean.getHuoTiFZ() + "", null);
                huoTiFZDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            baoCunBean.setHuoTiFZ(Integer.valueOf(huoTiFZDialog.getFaZhi()));
                            baoCunBeanDao.put(baoCunBean);
                            huoTiFZDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                huoTiFZDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        huoTiFZDialog.dismiss();
                    }
                });
                huoTiFZDialog.show();

                break;
            case R.id.rl6:

                startActivity(new Intent(SheZhiActivity2.this, SettingActivity.class));

                break;
            case R.id.rl7:
                final XiuGaiRuKuFZDialog dialog = new XiuGaiRuKuFZDialog(SheZhiActivity2.this);
                dialog.setContents(baoCunBean.getRuKuFaceSize() + "", baoCunBean.getRuKuMoHuDu() + "");
                dialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            baoCunBean.setRuKuFaceSize(Integer.valueOf(dialog.getFZ()));
                            baoCunBean.setRuKuMoHuDu(Float.valueOf(dialog.getMoHuDu()));
                            baoCunBeanDao.put(baoCunBean);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
//            case R.id.rl8:
//                //选择城市
//                if (options1Items.size() > 0 && options2Items.size() > 0 && options3Items.size() > 0) {
//                    showPickerView();
//                } else {
//                    Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, "城市数据准备中...请稍后", TastyToast.LENGTH_LONG, TastyToast.INFO);
//                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
//                    tastyToast.show();
//                }
//                break;

            case R.id.rl9:

                startActivity(new Intent(SheZhiActivity2.this, YuLanActivity.class));

                break;


        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
        RestartAPPTool.restartAPP(SheZhiActivity2.this);
      //  startActivity(new Intent(SheZhiActivity2.this, MainActivity2.class));
    }

//    //绑定
//    private void link_uplodexiazai(String zhucema){
//        //	final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        OkHttpClient okHttpClient= new OkHttpClient();
//        //RequestBody requestBody = RequestBody.create(JSON, json);
//        RequestBody body = new FormBody.Builder()
//                .add("registerCode",zhucema)
//                .add("machineCode", FileUtil.getSerialNumber(this) == null ? FileUtil.getIMSI() : FileUtil.getSerialNumber(this))
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
////				.header("Content-Type", "application/json")
////				.header("user-agent","Koala Admin")
//                //.post(requestBody)
//                //.get()
//                .post(body)
//                .url(baoCunBean.getHoutaiDiZhi()+"/app/machineSave");
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求失败"+e.getMessage());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast tastyToast= TastyToast.makeText(SheZhiActivity.this,"请求失败，请检查地址和网络",TastyToast.LENGTH_LONG,TastyToast.ERROR);
//                        tastyToast.setGravity(Gravity.CENTER,0,0);
//                        tastyToast.show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                Log.d("AllConnects", "请求成功"+call.request().toString());
//                //获得返回体
//                try{
//
//                    ResponseBody body = response.body();
//                    String ss=body.string().trim();
//                    Log.d("AllConnects", "注册码"+ss);
//					final JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//					Gson gson=new Gson();
//					runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast tastyToast= TastyToast.makeText(SheZhiActivity.this,jsonObject.get("message").getAsString(),TastyToast.LENGTH_LONG,TastyToast.INFO);
//                            tastyToast.setGravity(Gravity.CENTER,0,0);
//                            tastyToast.show();
//
//                        }
//                    });
//					//final HuiYiInFoBean renShu=gson.fromJson(jsonObject,HuiYiInFoBean.class);
//
//
//                }catch (Exception e){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast tastyToast= TastyToast.makeText(SheZhiActivity.this,"返回数据异常",TastyToast.LENGTH_LONG,TastyToast.ERROR);
//                            tastyToast.setGravity(Gravity.CENTER,0,0);
//                            tastyToast.show();
//
//                        }
//                    });
//                    Log.d("WebsocketPushMsg", e.getMessage()+"ttttt");
//                }
//
//            }
//        });
//    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(String event) {
        if (bangDingDialog!=null && bangDingDialog.isShowing()){
            bangDingDialog.setContents(event);
        }else {
            Toast tastyToast = TastyToast.makeText(SheZhiActivity2.this, event, TastyToast.LENGTH_LONG, TastyToast.INFO);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
    }

//    private void initJsonData() {//解析数据
//
//        /**
//         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
//         * 关键逻辑在于循环体
//         *
//         * */
//        String JsonData = FileUtil.getJson(this, "province.json");//获取assets目录下的json文件数据
//
//        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
//
//        /**
//         * 添加省份数据
//         *
//         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
//         * PickerView会通过getPickerViewText方法获取字符串显示出来。
//         */
//        options1Items = jsonBean;
//
//        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
//            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
//            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
//
//            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
//                String CityName = jsonBean.get(i).getCityList().get(c).getName();
//                CityList.add(CityName);//添加城市
//
//                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
//
//                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
//                if (jsonBean.get(i).getCityList().get(c).getArea() == null
//                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
//                    City_AreaList.add("");
//                } else {
//
//                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
//                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);
//
//                        City_AreaList.add(AreaName);//添加该城市所有地区数据
//                    }
//                }
//                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
//            }
//
//            /**
//             * 添加城市数据
//             */
//            options2Items.add(CityList);
//
//            /**
//             * 添加地区数据
//             */
//            options3Items.add(Province_AreaList);
//        }
//    }
//
//    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
//        ArrayList<JsonBean> detail = new ArrayList<>();
//        try {
//            JSONArray data = new JSONArray(result);
//            Gson gson = new Gson();
//            for (int i = 0; i < data.length(); i++) {
//                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
//                detail.add(entity);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//        return detail;
//    }






//    public static class UsbBroadCastReceiver extends BroadcastReceiver {
//
//        public UsbBroadCastReceiver() {
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            if (intent.getAction() != null && intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
//                usbPath = intent.getData().getPath();
//                List<String> sss = FileUtil.getMountPathList();
//                int size = sss.size();
//                for (int i = 0; i < size; i++) {
//
//                    if (sss.get(i).contains(usbPath)) {
//                        usbPath = sss.get(i);
//                    }
//
//                }
//
//                Log.d("UsbBroadCastReceiver", usbPath);
//            }
//
//
//        }
//    }


}
