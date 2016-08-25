package highwin.zgs.parallaxwithrecyclerview.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

/**
 * User: zgsHighwin
 * Email: 799174081@qq.com Or 799174081@gmail.com
 * Description: 为RecyclerView添加视差的效果
 * Create-Time: 2016/8/25 8:49
 */
public class ParallaxRecyclerView extends RecyclerView {

    private int mHeaderHeight;
    private int mHeight;
    private ImageView mImageView;
    private ParallaxAnimatorUpdateListener mAnimator;
    private ValueAnimator mValueAnimator;
    private float mDownY;

    public ParallaxRecyclerView(Context context) {
        this(context, null);
    }

    public ParallaxRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /**
     * 通过监听拿到的imageView,获取到ImageView的宽高
     *
     * @param imageView
     */
    public void setImageView(ImageView imageView) {
        mImageView = imageView;
        mHeaderHeight = imageView.getHeight();
        Drawable background = imageView.getDrawable();
        mHeight = background.getIntrinsicHeight() * 2 / 3; //拿到在资源文件里面图片的实际的高度
        Log.d("ParallaxRecyclerView", "mHeaderHeight\t" + mHeaderHeight + "\tmHeight\t" + mHeight);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (MotionEventCompat.getActionMasked(e)) {
            case MotionEvent.ACTION_DOWN:
                mDownY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mValueAnimator != null) {
                    mValueAnimator.removeAllUpdateListeners();
                }

                float moveY = e.getY();
                float deltaY = moveY - mDownY;
                if (deltaY > 0) {
                    int newHeight = (int) (mImageView.getHeight() + deltaY / 3);
                    if (newHeight > mHeight) {
                        newHeight = mHeight;
                    }
                    refreahUI(newHeight);
                }
                mDownY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                resetAnimator();
                break;
        }
        return super.onTouchEvent(e);
    }

    /**
     * 回弹动画
     */
    private void resetAnimator() {
        mValueAnimator = ValueAnimator.ofInt(1);
        mAnimator = new ParallaxAnimatorUpdateListener();
        mValueAnimator.addUpdateListener(mAnimator);
        mValueAnimator.setInterpolator(new OvershootInterpolator(8));
        mValueAnimator.setDuration(5000);
        mValueAnimator.start();
    }

    /**
     * 刷新头部View的UI
     *
     * @param newHeight
     */
    private void refreahUI(int newHeight) {
        mImageView.getLayoutParams().height = newHeight;
        mImageView.requestLayout();
    }

    /**
     * 初始化属性动画监听
     */
    private class ParallaxAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float animatedFraction = animation.getAnimatedFraction(); //分度值
            Integer evaluate = evaluate(animatedFraction, mImageView.getHeight(), mHeaderHeight);
            refreahUI((int) evaluate);
        }
    }

    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (startInt + fraction * (endValue - startInt));
    }

}
