package ua.com.oyster.hotpotato;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by linkbe on 8/30/14.
 */
public class PotatoActivity extends Activity {

    private static final String TAG = "PotatoActivity";
    private PotatoView mPotatoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.main);

        final RelativeLayout frame = (RelativeLayout) findViewById(R.id.frame);
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.potato);
        mPotatoView = new PotatoView(this, bitmap);

        frame.addView(mPotatoView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mPotatoView.movePotato()) {
                    mPotatoView.postInvalidate();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        Log.i(TAG, "InterruptedException");
                    }
                }
                PotatoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PotatoActivity.this.potatoOut();
                    }
                });
            }
        }).start();

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void potatoOut() {
        Toast.makeText(PotatoActivity.this, "go fuck yourself", Toast.LENGTH_LONG)
                .show();
    }
}
