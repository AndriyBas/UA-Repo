package ua.com.oyster.hotpotato;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by linkbe on 8/30/14.
 */
public class PotatoActivity extends Activity {

    private PotatoView mPotatoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPotatoView = new PotatoView(this);
        setContentView(mPotatoView);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
