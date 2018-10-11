package megvii.testfacepass.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import megvii.testfacepass.R;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class ChengGongDialog extends Dialog {
    private Button a1;
    private ImageView imageView;
    private TextView textView;


    public ChengGongDialog(Context context) {
        super(context, R.style.dialog_style2);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.queren_tongguo2, null);
        a1=mView.findViewById(R.id.xiayibu);
        imageView=mView.findViewById(R.id.ff22);
        textView=mView.findViewById(R.id.fgggg);
        //获得当前窗体
        Window window = ChengGongDialog.this.getWindow();
        //重新设置
        WindowManager.LayoutParams lp = ChengGongDialog.this.getWindow().getAttributes();
        window .setGravity(Gravity.CENTER);
       //  lp.x = -220; // 新位置X坐标
        //lp.y = 100; // 新位置Y坐标
       // lp.width = 340; // 宽度
       // lp.height = 300; // 高度
        //   lp.alpha = 0.7f; // 透明度
        // dialog.onWindowAttributesChanged(lp);
        //(当Window的Attributes改变时系统会调用此函数)
        window .setAttributes(lp);
        super.setContentView(mView);

    }

    public void setDate(){
        imageView.setBackgroundResource(R.drawable.cha);
        textView.setText("登记失败");
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
    public void setOnPositiveListener(View.OnClickListener listener){
        a1.setOnClickListener(listener);
    }
//    /**
//     * 取消键监听器
//     * @param listener
//     */
//    public void setOnQuXiaoListener(View.OnClickListener listener){
//        quxiao.setOnClickListener(listener);
//    }
}
