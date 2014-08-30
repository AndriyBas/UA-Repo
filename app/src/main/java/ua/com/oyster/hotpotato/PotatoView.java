package ua.com.oyster.hotpotato;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by linkbe on 8/30/14.
 */
public class PotatoView extends View {

    private Paint backgrouPaint;
    private Potato mPotato;

    private int downX, downY, upX, upY;

    //This is huck!!
    private int startFlag = 0;


    public PotatoView(Context context) {
        super(context);

        backgrouPaint = new Paint();
        backgrouPaint.setColor(Color.WHITE);
        backgrouPaint.setStyle(Paint.Style.FILL);


        Resources res = this.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.potato);
        mPotato = new Potato(0, 0, bitmap);

        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), backgrouPaint);


        //ToDo fix this huck
        if (startFlag == 0) {
            mPotato.setX(this.getWidth() / 3);
            mPotato.setY(this.getHeight() / 3);
            startFlag = 1;
        }

        canvas.drawBitmap(mPotato.getImage(), mPotato.getX(), mPotato.getY(), new Paint());
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
        return Math.abs(mPotato.getX()) < this.getWidth() || Math.abs(mPotato.getY()) < this.getHeight();
    }

}
