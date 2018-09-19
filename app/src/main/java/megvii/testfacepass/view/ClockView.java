package megvii.testfacepass.view;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class ClockView extends View {
    private Paint mPaintRing;// 圆环画笔
    private Paint mPbeiJing;// 背景画笔
    private Paint mPaintDegree;// 刻度/圆心画笔
    private Paint mPaintText;// 文字画笔
    private float strokeWidthText = 1;// 文字画笔厚度
    private float strokeWidthRing = 2;// 圆环画笔厚度
    private float radius = 0;// 圆环半径

    //用于初始化时间的角度设置
    private float hourDegree = 0;// 时针角度
    private float minuteDegree = 0;// 分针角度
    private float secondDegree = 0;// 秒针角度

    private Date mCurrentDate=null;// 用户设置时间,默认为当前时间


    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                long times=mCurrentDate.getTime();
                mCurrentDate.setTime(times+1000);
                setDate(mCurrentDate);
                handler.sendEmptyMessageDelayed(0,1000);
            }
        }
    };

    public void  crean(){
        handler.removeMessages(0);

    }

    public ClockView(Context context) {
        super(context);
        initPaint();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int min = Math.min(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));// 防止圆形变形,宽高度必须相等
//        setMeasuredDimension(min, min);// 重新设置宽高
//    }

    /**
     * 根据默认宽度测量宽度
     *
     * @param measureSpec
     * @return
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 100;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 根据默认高度测量高度
     *
     * @param measureSpec
     * @return
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private void initPaint() {
        mCurrentDate=new Date();
        setDate(mCurrentDate);
        // 初始化圆环画笔
        mPaintRing = new Paint();
        mPaintRing.setColor(Color.WHITE);
        mPaintRing.setStyle(Paint.Style.STROKE);
        mPaintRing.setStrokeWidth(4);
        mPaintRing.setAntiAlias(true);

        // 初始化圆背景画笔
        mPbeiJing = new Paint();
        mPbeiJing.setColor(Color.WHITE);
        mPbeiJing.setStyle(Paint.Style.FILL);
        mPbeiJing.setStrokeWidth(strokeWidthRing);
        mPbeiJing.setAntiAlias(true);

        // 初始化刻度画笔
        mPaintDegree = new Paint();
        mPaintDegree.setColor(Color.BLACK);
        mPaintDegree.setStyle(Paint.Style.FILL);
        mPaintDegree.setAntiAlias(true);
        // 初始化文字画笔
        mPaintText = new Paint();
        mPaintText.setColor(Color.BLACK);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(strokeWidthText);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //初始化半径,由于圆环的厚度也要占据一定的宽度,因此需要减除厚度值,这样才能保证圆环
        radius = Math.min(getWidth() / 2, getHeight() / 2) - strokeWidthRing;
        //背景
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPbeiJing);
        // 1.画圆环
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaintRing);
        // 2.画刻度
        drawMark(canvas);
        // 3.画圆心
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 5, mPaintDegree);
        // 4.移动坐标中心到画布中心
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.save();
        // 5.画时针
        drawHourMark(hourDegree, canvas);
        // 6.画分针
        drawMinuteMark(minuteDegree, canvas);
        // 7.画秒针
        drawSecondMark(secondDegree, canvas);
        // 8.画时间文字
       // mPaintText.setTextSize(30);
       // mPaintText.setTextAlign(Paint.Align.CENTER);
      //  canvas.drawText(XDate.fmtDate(mCurrentDate,"HH:mm:ss"), 0, -20, mPaintText);

    }

    /**
     * 画时针
     *
     * @param degree
     * @param canvas
     */
    private void drawHourMark(float degree, Canvas canvas) {
        canvas.rotate(degree);// 旋转度数
        mPaintDegree.setStrokeWidth(6);
        canvas.drawLine(0, 0, 0, -radius / 2.8f, mPaintDegree);
        canvas.restore();
        canvas.save();
    }

    /**
     * 画分针
     *
     * @param degree
     * @param canvas
     */
    private void drawMinuteMark(float degree, Canvas canvas) {
        canvas.rotate(degree);// 旋转度数
        mPaintDegree.setStrokeWidth(3);
        canvas.drawLine(0, 0, 0, -radius / 2.5f, mPaintDegree);
        canvas.restore();
        canvas.save();
    }

    /**
     * 画秒针
     *
     * @param degree
     * @param canvas
     */
    private void drawSecondMark(float degree, Canvas canvas) {
        canvas.rotate(degree);// 旋转度数
        mPaintDegree.setStrokeWidth(2);
        canvas.drawLine(0, 0, 0, -radius / 2f, mPaintDegree);
        canvas.restore();
        canvas.save();
    }

    /**
     * 画刻度
     */
    private void drawMark(Canvas canvas) {
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                mPaintDegree.setStrokeWidth(strokeWidthRing);
                canvas.drawLine(getWidth() / 2, strokeWidthRing, getWidth() / 2, 18, mPaintDegree);
                mPaintText.setTextAlign(Paint.Align.CENTER);
                mPaintText.setTextSize(12);
                if (i / 5 == 0) {
                    canvas.drawText("12", getWidth() / 2, 30, mPaintText);
                } else {
                    canvas.drawText(i / 5 + "", getWidth() / 2, 30, mPaintText);
                }
            } else {
                mPaintDegree.setStrokeWidth(2);
                canvas.drawLine(getWidth() / 2, strokeWidthRing, getWidth() / 2, 8, mPaintDegree);
            }
            canvas.rotate(6, getWidth() / 2, getHeight() / 2);
        }
    }

    /**
     * 设置时间
     *
     * @param date
     */
    public void setDate(@NonNull Date date) {
        mCurrentDate=date;
        float hourDegree = getHourDegree(getHours(date), getMinutes(date), getSeconds(date));
        float minuteDegree = getMinuteDegree(getMinutes(date), getSeconds(date));
        float secondDegree = getSecondDegree(getSeconds(date));
        rotate(hourDegree, minuteDegree, secondDegree);
    }

    /**
     * 设置时间戳
     *
     * @param timeMills
     */
    public void setTimeMills(long timeMills) {
        Date date = new Date();
        date.setTime(timeMills);
        setDate(date);
    }

    /**
     * 开始时间旋转
     */
    public void start(){
        handler.sendEmptyMessageDelayed(0,1000);
    }



    /**
     * 旋转
     */
    private  void rotate(float hourDegree, float minuteDegree, float secondDegree) {
        this.hourDegree = hourDegree;
        this.minuteDegree = minuteDegree;
        this.secondDegree = secondDegree;
        invalidate();
    }


    /**
     * 根据小时获取角度
     *
     * @param hour
     * @return
     */
    private float getHourDegree(int hour, int minute, int second) {
        float hourDegree = (hour + minute / 60.0f + second / 3600.0f) * 30;
        return hourDegree;
    }

    /**
     * 根据分钟获取角度
     *
     * @param minute
     * @return
     */
    private float getMinuteDegree(int minute, int second) {
        return (minute + second / 60.0f) * 6;
    }

    /**
     * 根据秒钟获取角度
     *
     * @param second
     * @return
     */
    private float getSecondDegree(int second) {
        return second * 6;
    }

    /**
     * 获取时间信息
     *
     * @param date
     * @return
     */
    private HashMap<Integer, Integer> getTime(Date date) {
        @SuppressLint("UseSparseArrays")
        HashMap<Integer, Integer> time = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        time.put(Calendar.HOUR, calendar.get(Calendar.HOUR));//12小时制
        time.put(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        time.put(Calendar.SECOND, calendar.get(Calendar.SECOND));
        return time;
    }

    /**
     * 获取当天中小时
     *
     * @param date
     * @return
     */
    private int getHours(Date date) {
        return getTime(date).get(Calendar.HOUR);
    }

    /**
     * 获取分钟
     *
     * @param date
     * @return
     */
    private int getMinutes(Date date) {
        return getTime(date).get(Calendar.MINUTE);
    }

    /**
     * 获取秒钟
     *
     * @param date
     * @return
     */
    private int getSeconds(Date date) {
        return getTime(date).get(Calendar.SECOND);
    }
}
