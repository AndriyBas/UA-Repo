package ua.com.oyster.hotpotato;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by linkbe on 8/30/14.
 */
public class PotatoView extends View {

    private Paint backgrouPaint;
    private Potato mPotato;
    private Thread mDrawingThread;
    private Rect mBorder;

    private Context mContext;

    private int downX, downY, upX, upY, directionX, directionY;
    Matrix matrix = new Matrix();

    //This is huck!!
    private int startFlag = 0;
    private int rotation = 0;



    public PotatoView(Context context, Bitmap bitmap) {
        super(context);
        mContext = context;

        backgrouPaint = new Paint();
        backgrouPaint.setColor(Color.DKGRAY);
        backgrouPaint.setStyle(Paint.Style.FILL);


        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        mBorder = new Rect(0, 0, width + 300, height + 300);

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

                    isPotatoOnMap();
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
            mPotato.setX(this.getWidth() - 200);
            mPotato.setY(this.getHeight() - 400);
            startFlag = 1;

        }

        matrix.postTranslate(-mPotato.getImage().getWidth() / 2, -mPotato.getImage().getHeight() / 2);
        matrix.postRotate(rotation);
        rotation += 4;
        matrix.postTranslate(mPotato.getX() - mPotato.getImage().getWidth() / 2,
                mPotato.getY() - mPotato.getImage().getHeight() / 2);
        canvas.drawBitmap(mPotato.getImage(), matrix, null);
        matrix.reset();
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

            directionX = (upX - downX) / 10;
            directionY = (upY - downY) / 10;

        }

        invalidate();
        return true;
    }

    public void isPotatoOnMap() {


        if (!mBorder.contains(mPotato.getX(), mPotato.getY()))

            if (!mDrawingThread.isInterrupted()) {
                mDrawingThread.interrupt();

                final AlertDialog.Builder alert = new AlertDialog.Builder(this.mContext);
                alert.setTitle("Go Fuck Yourself!");
                alert.setMessage("Game Over");
                alert.setPositiveButton("OK", null);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        PotatoView.this.post(new Runnable() {
                            @Override
                            public void run() {
                                alert.show();
                                mPotato.setX(PotatoView.this.getWidth() - 200);
                                mPotato.setY(PotatoView.this.getHeight() - 400);
                                directionX = 0;
                                directionY = 0;
                                rotate();
                            }
                        });

                    }
                }).start();
            }
    }
}
