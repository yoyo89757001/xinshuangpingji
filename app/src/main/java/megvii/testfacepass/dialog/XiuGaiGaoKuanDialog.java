package megvii.testfacepass.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import megvii.testfacepass.R;
import megvii.testfacepass.dialogall.XiuGaiListener;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class XiuGaiGaoKuanDialog extends Dialog {
    private XiuGaiListener xiuGaiListener;
    private TextView title2;
    private ImageView k1,k2,g1,g2;
    private Button b1;
    private int type=0;
    private TextView jiudianname,idid;
    private static boolean isT=true;
    private Context context;
    public XiuGaiGaoKuanDialog(Context context,XiuGaiListener xiuGaiListener) {
        super(context, R.style.dialog_style3);
        this.xiuGaiListener=xiuGaiListener;
        this.context=context;
        setCustomDialog();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.xiugaidialog3, null);

        jiudianname= (TextView) mView.findViewById(R.id.xiangce);
        idid= (TextView)mView.findViewById(R.id.idid);
        title2= (TextView) mView.findViewById(R.id.title2);
        k1= (ImageView) mView. findViewById(R.id.k_jian);
        k2= (ImageView) mView.findViewById(R.id.k_jia);
        g1= (ImageView) mView. findViewById(R.id.g_jian);
        g2= (ImageView) mView.findViewById(R.id.g_jia);
        b1= (Button)mView. findViewById(R.id.queren);
        k1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiuGaiListener.setKG(-1,0,type);
                isT=false;
            }
        });
        k1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isT=true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isT){
                            SystemClock.sleep(120);
                            xiuGaiListener.setKG(-10,0,type);
                        }
                    }
                }).start();

                return false;
            }
        });

        k2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiuGaiListener.setKG(1,0,type);
                isT=false;
            }
        });
        k2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isT=true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isT){
                            SystemClock.sleep(120);
                            xiuGaiListener.setKG(10,0,type);
                        }
                    }
                }).start();

                return false;
            }
        });

        g1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiuGaiListener.setKG(0,-1,type);
                isT=false;
            }
        });
        g1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isT=true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isT){
                            SystemClock.sleep(120);
                            xiuGaiListener.setKG(0,-10,type);
                        }
                    }
                }).start();

                return false;
            }
        });

        g2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiuGaiListener.setKG(0,1,type);
                isT=false;
            }
        });
        g2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isT=true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isT){
                            SystemClock.sleep(120);
                            xiuGaiListener.setKG(0,10,type);
                        }
                    }
                }).start();

                return false;
            }
        });


        super.setContentView(mView);
    }

    public void setContents(String title,String kuan, String gao,int type){
        if (title2!=null)
        title2.setText(title);
       if (kuan!=null)
           idid.setText(kuan);
        if (gao!=null)
            jiudianname.setText(gao);
        this.type=type;

    }

    public String getGao(){
        return jiudianname.getText().toString().trim();
    }

    public String getKuan(){
        return idid.getText().toString().trim();
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

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnQueRenListener(View.OnClickListener listener){
        b1.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
//    public void setQuXiaoListener(View.OnClickListener listener){
//        b2.setOnClickListener(listener);
//    }


}
