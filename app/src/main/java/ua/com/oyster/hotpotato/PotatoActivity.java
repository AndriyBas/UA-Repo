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

        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.potato);
        mPotatoView = new PotatoView(this, bitmap);
        setContentView(mPotatoView);
    }

}
