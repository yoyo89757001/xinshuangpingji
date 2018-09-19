package megvii.testfacepass.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangjingyi on 28/03/2017.
 */

public class MyFaceView extends View {
    private List<String> ids;
    private List<String> yaws;
    private List<String> pitchs;
    private List<String> rolls;
    private List<String> blurs;
    private List<String> ages;
    private List<String> genders;
    private List<Rect> rect;
    private Paint paint = new Paint();
    private Paint idPaint = new Paint();
    private Paint attributePaint = new Paint();
    private Paint backPaint = new Paint();

    private void initData() {
        ids = new ArrayList<String>();
        yaws = new ArrayList<>();
        pitchs = new ArrayList<>();
        rolls = new ArrayList<>();
        blurs = new ArrayList<>();
        rect = new ArrayList<Rect>();
        ages = new ArrayList<>();
        genders = new ArrayList<>();
        paint.setARGB(122, 255, 255, 255);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10.0f);

        backPaint.setARGB(122, 255, 255, 255);
        backPaint.setStyle(Paint.Style.FILL);

        idPaint.setARGB(255, 80, 80, 80);
        idPaint.setTextSize(40);

        attributePaint.setARGB(255, 80, 80, 80);
        attributePaint.setTextSize(25);
    }

    public MyFaceView(Context context) {
        super(context);
        initData();
    }

    public MyFaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public MyFaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    public void addId(String label) {
        ids.add(label);
    }

    public void addYaw(String label) {
        yaws.add(label);
    }
    public void addPitch(String label) {
        pitchs.add(label);
    }
    public void addRoll(String label) {
        rolls.add(label);
    }
    public void addBlur(String label) {
        blurs.add(label);
    }
    public void addAge(String label) {
        ages.add(label);
    }
    public void addGenders(String label) {
        genders.add(label);
    }

    public void addRect(RectF rect) {
        Rect buffer = new Rect();
        buffer.left = (int) rect.left;
        buffer.top = (int) rect.top;
        buffer.right = (int) rect.right;
        buffer.bottom = (int) rect.bottom;
        this.rect.add(buffer);
    }

    public void clear() {
        rect.clear();
        ids.clear();
        yaws.clear();
        rolls.clear();
        blurs.clear();
        pitchs.clear();
        ages.clear();
        genders.clear();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {

        for (int i = 0; i < rect.size(); i++) {
            Rect r = rect.get(i);
            canvas.drawRect(r, paint);
            canvas.drawRect(r.right+5, r.top - 5, r.right + ids.get(i).length() * 25, r.top + 170 + 33 * 2, backPaint);
            canvas.drawText(ids.get(i), r.right + 5, r.top + 30, idPaint);
            canvas.drawText(yaws.get(i), r.right + 5, r.top + 60, attributePaint);
            canvas.drawText(pitchs.get(i), r.right + 5, r.top + 93, attributePaint);
            canvas.drawText(rolls.get(i), r.right + 5, r.top + 126, attributePaint);
            canvas.drawText(blurs.get(i), r.right + 5, r.top + 159, attributePaint);
            canvas.drawText(ages.get(i), r.right + 5, r.top + 159 + 33, attributePaint);
            canvas.drawText(genders.get(i), r.right + 5, r.top + 159 + 33 * 2, attributePaint);
        }
        this.clear();

            }catch (Exception e){
            Log.d("FaceView", e.getMessage());
        }
    }
}
