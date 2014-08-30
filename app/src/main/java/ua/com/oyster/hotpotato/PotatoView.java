package ua.com.oyster.hotpotato;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by linkbe on 8/30/14.
 */
public class PotatoView extends View {

    private Paint backgrouPaint = new Paint();


    public PotatoView(Context context) {
        super(context);

        backgrouPaint.setColor(Color.WHITE);
        backgrouPaint.setStyle(Paint.Style.FILL);

        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), backgrouPaint );

        int color = 0x8f000000;
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(100, 100, 10, paint);

    }
}
