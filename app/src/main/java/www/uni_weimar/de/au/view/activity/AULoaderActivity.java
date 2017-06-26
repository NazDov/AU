package www.uni_weimar.de.au.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.application.AUApplicationConfiguration;

/**
 * Created by ndovhuy on 26.06.2017.
 */

public class AULoaderActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.au_screen_loader);
        AUApplicationConfiguration.initSystemMainMenuComponents(this);
    }
}
