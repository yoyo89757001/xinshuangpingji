package megvii.testfacepass.dialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import megvii.testfacepass.R;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class JiaZaiDialog extends Dialog {
    private TextView a1;
    private ProgressBar progressBar;

    public JiaZaiDialog(Context context) {
        super(context, R.style.dialog_style);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.queren_ll2, null);
        a1= (TextView) mView.findViewById(R.id.a1);
        progressBar= (ProgressBar) mView.findViewById(R.id.progressBar);
        //获得当前窗体
        Window window = JiaZaiDialog.this.getWindow();
        //重新设置
        WindowManager.LayoutParams lp = JiaZaiDialog.this.getWindow().getAttributes();
        window .setGravity(Gravity.CENTER | Gravity.TOP);
        // lp.x = 100; // 新位置X坐标
        lp.y = 100; // 新位置Y坐标
        lp.width = 300; // 宽度
        lp.height = 300; // 高度
        //   lp.alpha = 0.7f; // 透明度
        // dialog.onWindowAttributesChanged(lp);
        //(当Window的Attributes改变时系统会调用此函数)
        window .setAttributes(lp);
        super.setContentView(mView);


    }

    public void setProgressBar(){
        progressBar.setVisibility(View.GONE);
        a1.setTextSize(34);
        a1.setBackgroundColor(Color.parseColor("#ff6600"));
    }


    public void setText(String s){
        a1.setText(s);
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
