package jp.gr.java_conf.androtaku.killingtime;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    KillingTimeView killingTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        killingTimeView = new KillingTimeView(this);
        setContentView(killingTimeView);
    }

    @Override
    public void onPause(){
        super.onPause();
        killingTimeView.stopThread();
    }
}
