package ua.com.oyster.hotpotato;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by linkbe on 8/30/14.
 */
public class PotatoView extends View {

    private Paint backgrouPaint;
    private Potato mPotato;
    private Thread mDrawingThread;

    private int downX, downY, upX, upY, dX, dY, directionX, directionY;
    Matrix matrix = new Matrix();

    //This is huck!!
    private int startFlag = 0;
    private int rotation = 5;


    public PotatoView(Context context) {
        super(context);

        backgrouPaint = new Paint();
        backgrouPaint.setColor(Color.WHITE);
        backgrouPaint.setStyle(Paint.Style.FILL);


        Resources res = this.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.potato);
        mPotato = new Potato(0, 0, bitmap);
        rotate();
    }

    private void rotate() {
        mDrawingThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {


                    try {
                        Thread.sleep(10);
                        PotatoView.this.post(new Runnable() {
                            @Override
                            public void run() {
                                mPotato.setX(mPotato.getX() + directionX);
                                mPotato.setY(mPotato.getY() + directionY);
                                invalidate();
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mDrawingThread.start();
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

        matrix.postTranslate(-mPotato.getImage().getWidth() / 2, -mPotato.getImage().getHeight() / 2);
        matrix.postRotate(rotation);
        rotation += 3;
        matrix.postTranslate(mPotato.getX() - mPotato.getImage().getWidth() / 2,
                mPotato.getY() - mPotato.getImage().getHeight() / 2);
        canvas.drawBitmap(mPotato.getImage(), matrix, null);
        matrix.reset();

//        canvas.drawBitmap(mPotato.getImage(), mPotato.getX() - mPotato.getImage().getWidth()  / 2,
//                mPotato.getY() - mPotato.getImage().getHeight() / 2, null);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = (int) event.getX();
            downY = (int) event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //Attentoin! HardCode detected!
            mPotato.setX((int) event.getX() + 200);
            mPotato.setY((int) event.getY() + 200);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            upX = (int) event.getX();
            upY = (int) event.getY();

            dX = upX - downX;
            dY = upY - downY;

            if (dX > 0) {
                if (dY > 0) {
                    directionX = 10;
                    directionY = 10;
                } else {
                    directionX = 10;
                    directionY = -10;
                }
            } else {
                if (dY > 0) {
                    directionX = -10;
                    directionY = 10;
                } else {
                    directionX = -10;
                    directionY = -10;
                }
            }

//
//            if (Math.abs(downX - upX) > 100 || Math.abs(downY - upY) > 100) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while (potatoOnMap()) {
//                            try {
//                                Thread.sleep(100);
//                                PotatoView.this.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        mPotato.setX(mPotato.getX() + directionX);
//                                        mPotato.setY(mPotato.getY() + directionY);
//                                        invalidate();
//                                    }
//                                });
//
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }).start();
//            }


        }

        invalidate();
        return true;
    }

    public boolean potatoOnMap() {
        return Math.abs(mPotato.getX()) < this.getWidth() || Math.abs(mPotato.getY()) < this.getHeight();
    }

}
