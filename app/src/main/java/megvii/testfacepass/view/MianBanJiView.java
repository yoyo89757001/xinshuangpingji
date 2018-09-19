package megvii.testfacepass.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;


import android.util.AttributeSet;

import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import megvii.testfacepass.utils.DensityUtil;


public class MianBanJiView extends View{
    private int type=0;
    private Paint paintSaoMiao = new Paint();
    private Paint paintYuanHuan = new Paint();
    private Paint paintYuanHuan2 = new Paint();
    private Paint paintBitmap = new Paint();
    private Paint kuangPaint = new Paint();
    private Paint tanhaoPaint = new Paint();
    private Paint yuanPaint = new Paint();
    private Paint kuangPaint2 = new Paint();
    private Paint tishiPaint = new Paint();
    private Paint tishiZiPaint = new Paint();
    private Paint ziPaint = new Paint();
    private Paint ziPaint2 = new Paint();
    private Paint kuangPaint3 = new Paint();
    private int viewW=0,viewH=0;
    private ChuShiHuaListion chuShiHuaListion;
    private boolean isStart=false;
    private ValueAnimator animator=null;
    private int jiaodu=0;
    private RectF rectF=new RectF();
    private RectF rectF2=new RectF();
    private RectF rectBitmap=new RectF();
    private RectF tishiRect=new RectF();
    private RectF rectF3=new RectF();
    private RectF tanhao1=new RectF();
    private RectF tanhao2=new RectF();
    private String time;
    private float ziWith;
    private int dingshi=0;
    private int lingshi;
    private final Timer timer = new Timer();
    private TimerTask task;
    private float yuanBanJing=1;
    private boolean isTiShi=false;
    // 声波的圆圈集合
    private List<Circle> mRipples;
    // 圆圈扩散的速度
    private int mSpeed=4;
    private Context context;
    // 圆圈之间的密度
    private int mDensity=40;
    private int sqrtNumber;
    private Bitmap bitmap=null;
    private String wenhou="";
    private String name;

    public MianBanJiView(Context context) {
        super(context);
        this.context=context;
        initData();
    }

    public void setChuShiHuaListion(ChuShiHuaListion chuShiHuaListion){
        this.chuShiHuaListion=chuShiHuaListion;
    }

    public MianBanJiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initData();
    }

    public MianBanJiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initData();
    }

    public void setTime(String time){
        this.time=time;
    }

    public void setBitmap(Bitmap bitmap,String name){
        this.bitmap=bitmap;
        this.name=name;
    }

    private void initData(){
        paintSaoMiao.setColor(Color.parseColor("#661b37d6"));
        paintSaoMiao.setStyle(Paint.Style.FILL);
        paintSaoMiao.setStrokeWidth(14.0f);

        kuangPaint.setColor(Color.parseColor("#ffffff"));
        kuangPaint.setStyle(Paint.Style.STROKE);
        kuangPaint.setAntiAlias(true);
        kuangPaint.setStrokeWidth(2.0f);

        tanhaoPaint.setColor(Color.parseColor("#f3E1F108"));
        tanhaoPaint.setStyle(Paint.Style.FILL);
        tanhaoPaint.setAntiAlias(true);

        kuangPaint2.setColor(Color.parseColor("#69ffffff"));
        kuangPaint2.setStyle(Paint.Style.FILL);

        tishiPaint.setColor(Color.parseColor("#aaffffff"));
        tishiPaint.setStyle(Paint.Style.FILL);

        yuanPaint.setColor(Color.parseColor("#69FF4081"));
        yuanPaint.setStyle(Paint.Style.FILL);

        kuangPaint3.setColor(Color.parseColor("#69000000"));
        kuangPaint3.setStyle(Paint.Style.FILL);

        ziPaint.setColor(Color.parseColor("#ffffff"));
        ziPaint.setStyle(Paint.Style.FILL);
        ziPaint.setAntiAlias(true);
        ziPaint.setTextSize(50);

        ziPaint2.setColor(Color.parseColor("#ffffff"));
        ziPaint2.setStyle(Paint.Style.FILL);
        ziPaint2.setAntiAlias(true);
        ziPaint2.setTextSize(20);

        paintYuanHuan.setStyle(Paint.Style.FILL);
        paintYuanHuan.setColor(Color.parseColor("#69ffffff"));
        paintYuanHuan.setStrokeCap(Paint.Cap.ROUND);
        paintYuanHuan.setAntiAlias(true);

        paintYuanHuan2.setStrokeWidth(2f);
        paintYuanHuan2.setStyle(Paint.Style.FILL);
        paintYuanHuan2.setColor(Color.parseColor("#111111"));
//        paintYuanHuan2.setStrokeCap(Paint.Cap.ROUND);
        paintYuanHuan2.setAntiAlias(true);
      //  PorterDuffXfermode xfermode=new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
      //  paintYuanHuan2.setXfermode(xfermode);

        // 添加第一个圆圈
        mRipples = new ArrayList<>();
        Circle c = new Circle(100, 255);
        mRipples.add(c);

      //  paintBitmap.setARGB(255, 255, 255, 255);
      //  paintBitmap.setStyle(Paint.Style.FILL);
        //  paint.setStrokeWidth(10.0f);
       // PorterDuffXfermode xfermode2=new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        //paintBitmap.setXfermode(xfermode2);



    }

    /**
     * 圆到宽度
     *
     * @param canvas
     */
    private void drawInCircle(Canvas canvas) {
        //canvas.save();

        // 处理每个圆的宽度和透明度
        for (int i = 0; i < mRipples.size(); i++) {
            Circle c = mRipples.get(i);
            paintYuanHuan.setAlpha(c.alpha);// （透明）0~255（不透明）
            canvas.drawCircle(viewW / 2, (viewH / 2)+100, c.width - paintYuanHuan.getStrokeWidth(), paintYuanHuan);

            // 当圆超出View的宽度后删除
            if (c.width > viewW / 2) {
              //  mRipples.remove(i);
            } else {
                // 计算不透明的数值，这里有个小知识，就是如果不加上double的话，255除以一个任意比它大的数都将是0
                double alpha = 255 - c.width* (255 / ((double) viewW / 2));
              //  Log.d("MianBanJiView", "alpha:" + alpha);
                c.alpha = (int) alpha;
                // 修改这个值控制速度
                c.width += mSpeed;
            }
        }


        // 里面添加圆
        int size=mRipples.size();
        if (size>0) {
            // 控制第二个圆出来的间距
            if (mRipples.get(size - 1).width > DensityUtil.dip2px(context, mDensity)) {
                mRipples.add(new Circle(0, 255));
                if (size>10){
                    mRipples.remove(0);
                }
            }
        }
      //  Log.d("MianBanJiView", "mRipples.size():" + mRipples.size());
      //  invalidate();

     //  canvas.restore();
    }


    /**
     * 圆到对角线
     *
     * @param canvas
     */
    private void drawOutCircle(Canvas canvas) {
        canvas.save();

        // 使用勾股定律求得一个外切正方形中心点离角的距离
        sqrtNumber = (int) (Math.sqrt(viewW * viewW + ((viewH -200) * (viewH-200)) / 2));

        // 变大
        for (int i = 0; i < mRipples.size(); i++) {

            // 启动圆圈
            Circle c = mRipples.get(i);
            paintYuanHuan.setAlpha(c.alpha);// （透明）0~255（不透明）
            canvas.drawCircle(viewW / 2, (viewH / 2)+100, c.width - paintYuanHuan.getStrokeWidth(), paintYuanHuan);

            // 当圆超出对角线后删掉
            if (c.width > sqrtNumber) {
                mRipples.remove(i);
            } else {
                // 计算不透明的度数
                double degree = 255 - c.width * (255 / (double) sqrtNumber);
                c.alpha = (int) degree;
                c.width += mSpeed;
            }
        }

        // 里面添加圆
        if (mRipples.size() > 0) {
            // 控制第二个圆出来的间距
            if (mRipples.get(mRipples.size() - 1).width >DensityUtil.dip2px(context, mDensity)) {
                mRipples.add(new Circle(0, 255));
            }
        }
        invalidate();
        canvas.restore();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewW=getMeasuredWidth();
        viewH=getMeasuredHeight();
        chuShiHuaListion.initFilsh();

    }

    public void start(){
        if (animator==null) {
            animator=new ValueAnimator();
            animator = ValueAnimator.ofFloat(200, viewH - 30);
            //动画时长，让进度条在CountDown时间内正好从0-360走完，
            animator.setDuration(2600);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setInterpolator(new LinearInterpolator());//匀速
            animator.setRepeatCount(-1);//表示不循环，-1表示无限循环
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    /**
                     * 这里我们已经知道ValueAnimator只是对值做动画运算，而不是针对控件的，因为我们设置的区间值为0-1.0f
                     * 所以animation.getAnimatedValue()得到的值也是在[0.0-1.0]区间，而我们在画进度条弧度时，设置的当前角度为360*currentAngle，
                     * 因此，当我们的区间值变为1.0的时候弧度刚好转了360度
                     */
                    jiaodu = Float.valueOf (animation.getAnimatedValue().toString()).intValue();
                    MianBanJiView.this.invalidate();

                }
            });
            animator.start();
        }

        rectF.set(10,50,viewW-10,150);
        rectF2.set(0,50,viewW,150);
        rectF3.set(0,0,viewW,200);
        rectBitmap.set(viewW / 2-150,(viewH / 2)-50,viewW / 2+150,(viewH / 2)+250);
        tanhao1.set(viewW/2-26,(viewH/2+100)-viewW/3+50,viewW/2+26,(viewH/2+100)+viewW/3-140);
        tanhao2.set(viewW/2-26,(viewH/2+100)+viewW/3-122,viewW/2+26,(viewH/2+100)+viewW/3-70);
        tishiRect.set(0,viewH-140,viewW,viewH-40);
        ziWith=ziPaint.measureText(time);

        isStart=true;
    }

    public void setType(final int type2) {
        mRipples.clear();
        Circle c = new Circle(100, 255);
        mRipples.add(c);

        this.type = type2;
            //启动定时器或重置定时器
            if (task != null) {
                task.cancel();
                //timer.cancel();
                task = new TimerTask() {
                    @Override
                    public void run() {
                        type = 0;
                    }
                };
                timer.schedule(task, 6000);
            } else {
                task = new TimerTask() {
                    @Override
                    public void run() {
                        type = 0;
                    }
                };
                timer.schedule(task, 6000);
            }

        //动画
        SpringSystem springSystem3 = SpringSystem.create();
        final Spring spring3 = springSystem3.createSpring();
        //两个参数分别是弹力系数和阻力系数
        spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(100, 3));
        // 添加弹簧监听器
        spring3.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                // value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
                //  Log.d("kkkk", "value:" + value);
                //基于Y轴的弹簧阻尼动画
                //	helper.itemView.setTranslationY(value);
                // 对图片的伸缩动画
                //float scale = 1f - (value * 0.5f);
                // view_dk.setScaleX(value);
                //view_dk.setScaleY(value);
                float v = (float) spring.getCurrentValue();
                if (v < 0.7f) {
                    yuanBanJing = 0.7f;
                } else {
                    yuanBanJing = v;
                }

            }
        });
        // 设置动画结束值
        spring3.setEndValue(1f);
        //判断时间
        int t= Integer.parseInt((time.split(" ")[1]).split(":")[0]);
        if (t<10){
            wenhou="早上好!";
        }else if (t < 12){
            wenhou="上午好!";
        }else if (t < 14){
            wenhou="中午好!";
        }else if (t<18){
            wenhou="下午好!";
        }else {
            wenhou="晚上好!";
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isStart){
            //获取到了宽高开始绘制

            switch (type){
                case 0:
                    //没人模式
                    ziPaint.setColor(Color.parseColor("#ffffff"));
                    kuangPaint2.setColor(Color.parseColor("#69ffffff"));
                    rectF3.set(0,0,viewW,200);
                    canvas.drawRect(rectF3,kuangPaint3);
                  //  canvas.drawRect(rectF,kuangPaint);
                    canvas.drawRect(rectF2,kuangPaint2);
                    canvas.drawText(time,(viewW/2)-(ziWith/2),120,ziPaint);
                    canvas.drawLine(0,jiaodu,viewW,jiaodu,paintSaoMiao);


                    break;
                case 1:
                    //识别模式
                 //   drawOutCircle(canvas);
                    drawInCircle(canvas);
                    ziPaint.setColor(Color.parseColor("#ffffff"));
                    kuangPaint2.setColor(Color.parseColor("#69ffffff"));
                    rectF3.set(0,0,viewW,200);
                    canvas.drawRect(rectF3,kuangPaint3);
                    //  canvas.drawRect(rectF,kuangPaint);
                    canvas.drawRect(rectF2,kuangPaint2);
                    float ss= ziPaint.measureText(wenhou+" 欢饮您 "+name);
                    canvas.drawText(wenhou+" 欢饮您 "+name,(viewW/2)-(ss/2),120,ziPaint);
                    float ss2= ziPaint.measureText("刷脸成功");
                    ziPaint.setColor(Color.parseColor("#0d2cf9"));
                    canvas.drawText("刷脸成功",(viewW/2)-(ss2/2),300,ziPaint);
                    canvas.drawBitmap(bitmap,null,rectBitmap,null);


                    break;
                case 2:
                    //未识别模式
                    rectF3.set(0,0,viewW,viewH);
                    canvas.drawRect(rectF3,kuangPaint3);
                    kuangPaint2.setColor(Color.parseColor("#eeffffff"));
                    canvas.drawRect(rectF2,kuangPaint2);
                    ziPaint.setColor(Color.parseColor("#FF4081"));
                    float sss=ziPaint.measureText("无权限，请联系工作人员!!!");
                    canvas.drawText("无权限，请联系工作人员!!!",(viewW/2)-(sss/2),120,ziPaint);
                    canvas.drawLine(0,jiaodu,viewW,jiaodu,paintSaoMiao);

                    // 缩放动画会影响其他的界面，所以保存画布状态
                    canvas.save();
                    canvas.scale(yuanBanJing, yuanBanJing,viewW/2,(viewH/2+100));
                    canvas.drawCircle(viewW/2,(viewH/2+100),viewW/3,yuanPaint);
                    canvas.drawRect(tanhao1,tanhaoPaint);
                    canvas.drawRect(tanhao2,tanhaoPaint);
                    canvas.restore();

                    break;

            }



            float pp= ziPaint2.measureText("瑞瞳智能科技");
            canvas.drawText("瑞瞳智能科技",viewW-pp-10,viewH-20,ziPaint2);
            if (isTiShi){
                canvas.drawRect(tishiRect,tishiPaint);
                ziPaint.setColor(Color.parseColor("#FF4081"));
                float ss=ziPaint.measureText("请正视摄像头!");
                canvas.drawText("请正视摄像头!",(viewW/2)-(ss/2),viewH-70,ziPaint);
            }

        }
    }
    public void desoure(){
        timer.cancel();
        if (task != null)
            task.cancel();
    }

    public void tishi(boolean is){
           isTiShi=is;
    }

    class Circle {
        Circle(int width, int alpha) {
            this.width = width;
            this.alpha = alpha;
        }

        int width;

        int alpha;
    }

}
