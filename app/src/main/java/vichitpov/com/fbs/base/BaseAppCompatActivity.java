package vichitpov.com.fbs.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;


/**
 * Created by VichitPov on 2/25/18.
 */

@SuppressLint("Registered")
public class BaseAppCompatActivity extends LocalizationActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
