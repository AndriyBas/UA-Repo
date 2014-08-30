package ua.com.oyster.hotpotato;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by linkbe on 8/30/14.
 */
public class PotatoView extends View {

//    private Paint backgrouPaint;
    private Potato mPotato;

    private int downX, downY, upX, upY;

    //This is huck!!
    private int startFlag = 0;


    final private DisplayMetrics mDisplayMetrics;
    final private int mDisplayWidth;
    final private int mDisplayHeight;
    final private int mBitmapWidthAndHeight, mBitmapWidthAndHeightAdj;
    final private Paint mPainter = new Paint();


    public PotatoView(Activity activity, Bitmap bitmap) {
        super(activity);

//        backgrouPaint = new Paint();
//        backgrouPaint.setColor(Color.WHITE);
//        backgrouPaint.setStyle(Paint.Style.FILL);




        mBitmapWidthAndHeight = getResources().getDimensionPixelSize(
                R.dimen.image_height);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
                mBitmapWidthAndHeight, mBitmapWidthAndHeight, false);

        mBitmapWidthAndHeightAdj = mBitmapWidthAndHeight + 20;

        mDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(mDisplayMetrics);
        mDisplayWidth = mDisplayMetrics.widthPixels;
        mDisplayHeight = mDisplayMetrics.heightPixels;

        Random r = new Random();
        int x = r.nextInt(mDisplayWidth);
        int y = r.nextInt(mDisplayHeight);

        mPotato = new Potato(x, y, scaledBitmap);

        mPotato.changeVelocity(r.nextInt(10), r.nextInt(10));
//        float dy = (float) r.nextInt(mDisplayHeight) / mDisplayHeight;
//        dy *= r.nextInt(2) == 1 ? STEP : -1 * STEP;
//        float dx = (float) r.nextInt(mDisplayWidth) / mDisplayWidth;
//        dx *= r.nextInt(2) == 1 ? STEP : -1 * STEP;
//        mDxDy = new Coords(dx, dy);

        mPainter.setAntiAlias(true);


        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), backgrouPaint);
//
//
//        //ToDo fix this huck
//        if (startFlag == 0) {
//            mPotato.setX(this.getWidth() / 3);
//            mPotato.setY(this.getHeight() / 3);
//            startFlag = 1;
//        }

        canvas.drawBitmap(mPotato.getImage(), mPotato.getX(), mPotato.getY(), mPainter);
    }


  @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = (int) event.getX();
            downY = (int) event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mPotato.setX((int) event.getX() - 180);
            mPotato.setY((int) event.getY() - 180);

//            downX = (int) event.getX();
//            downY = (int) event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            upX = (int) event.getX();
            upY = (int) event.getY();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (potatoOnMap()) {
                        try {
                            Thread.sleep(10);
                            PotatoView.this.post(new Runnable() {
                                @Override
                                public void run() {
                                    mPotato.setX(mPotato.getX() + (upX - downX) / 10);
                                    mPotato.setY(mPotato.getY() + (upY - downY) / 10);
                                    invalidate();
                                }
                            });

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }

        invalidate();
        return true;
    }


    public boolean potatoOnMap() {
        if (mPotato.getX() < 0 - mBitmapWidthAndHeightAdj
                || mPotato.getY() > mDisplayHeight + mBitmapWidthAndHeightAdj
                || mPotato.getX() < 0 - mBitmapWidthAndHeightAdj
                || mPotato.getY() > mDisplayWidth + mBitmapWidthAndHeightAdj) {
            return false;
        } else {
            return true;
        }
//        return Math.abs(mPotato.getX()) < this.getWidth() || Math.abs(mPotato.getY()) < this.getHeight();
    }


    public boolean movePotato() {
        mPotato.move();
        return potatoOnMap();
    }



}
