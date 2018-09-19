package megvii.testfacepass.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import io.objectbox.Box;
import megvii.testfacepass.MyApplication;
import megvii.testfacepass.R;
import megvii.testfacepass.beans.BaoCunBean;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class YuYingDialog extends Dialog implements View.OnFocusChangeListener, View.OnClickListener {
    private SeekBar seekBar,seekBar2,seekBar3;
    private LinearLayout l11,l22,l33;
    private Button l1,l2;
    private Box<BaoCunBean> baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;

    public YuYingDialog(Context context) {
        super(context, R.style.dialog_style2);
        Window window =  this.getWindow();
        if ( window != null) {
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.gravity = Gravity.CENTER;//设置dialog 在布局中的位置
            }
        }
        baoCunBeanDao = MyApplication.myApplication.getBoxStore().boxFor(BaoCunBean.class);
        baoCunBean=baoCunBeanDao.get(123456L);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.queren_ll4, null);
        seekBar= (SeekBar) mView.findViewById(R.id.seekBar);
        l11= (LinearLayout) mView.findViewById(R.id.l1);
        l22= (LinearLayout) mView.findViewById(R.id.l2);
        l33= (LinearLayout) mView.findViewById(R.id.l3);
        seekBar.setMax(4);
        seekBar.setProgress(baoCunBean.getBoyingren());
        seekBar.setOnFocusChangeListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               baoCunBean.setBoyingren(progress);
               baoCunBeanDao.put(baoCunBean);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2= (SeekBar) mView.findViewById(R.id.seekBar2);
        seekBar2.setMax(9);
        seekBar2.setProgress(baoCunBean.getYusu());
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                baoCunBean.setYusu(progress);
                baoCunBeanDao.put(baoCunBean);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2.setOnFocusChangeListener(this);
        seekBar3= (SeekBar) mView.findViewById(R.id.seekBar3);
        seekBar3.setMax(9);
        seekBar3.setProgress(baoCunBean.getYudiao());
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                baoCunBean.setYudiao(progress);
                baoCunBeanDao.put(baoCunBean);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar3.setOnFocusChangeListener(this);
        l1= (Button)mView. findViewById(R.id.queren);
        l1.setOnClickListener(this);
        l1.setOnFocusChangeListener(this);

        super.setContentView(mView);


    }



    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.seekBar:

                if (hasFocus){
                    chongzhi();
                    l11.setBackgroundResource(R.color.huise33);
                    l1.setBackgroundResource(R.drawable.zidonghuoqu2);
                }
                break;
            case R.id.seekBar2:

                if (hasFocus){
                    chongzhi();
                    l22.setBackgroundResource(R.color.huise33);
                    l1.setBackgroundResource(R.drawable.zidonghuoqu2);
                }
                break;
            case R.id.seekBar3:

                if (hasFocus){
                    chongzhi();
                    l33.setBackgroundResource(R.color.huise33);
                    l1.setBackgroundResource(R.drawable.zidonghuoqu2);

                }

                break;

            case R.id.queren:
                chongzhi();
                l1.setBackgroundResource(R.drawable.zidonghuoqu1);

                break;

        }



    }

    private void chongzhi(){
        l11.setBackgroundResource(R.color.white);
        l22.setBackgroundResource(R.color.white);
        l33.setBackgroundResource(R.color.white);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.queren:

                this.dismiss();

                break;

        }

    }

//    /**
//     * 确定键监听器
//     * @param listener
//     */
//    public void setOnPositiveListener(View.OnClickListener listener){
//        positiveButton.setOnClickListener(listener);
//    }
//    /**
//     * 取消键监听器
//     * @param listener
//     */
//    public void setOnQuXiaoListener(View.OnClickListener listener){
//        quxiao.setOnClickListener(listener);
//    }
}
