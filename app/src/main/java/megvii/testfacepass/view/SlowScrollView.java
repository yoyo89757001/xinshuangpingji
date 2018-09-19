package megvii.testfacepass.view;

import android.content.Context;
import android.util.AttributeSet;

import android.widget.ScrollView;




public class SlowScrollView extends ScrollView {

    public SlowScrollView(Context context) {
        super(context);
    }

    public SlowScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public SlowScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void fling(int velocityX) {
        //重写fling方法，将速度除以三，减缓其滑动速度
        super.fling(velocityX / 3);
    }
}