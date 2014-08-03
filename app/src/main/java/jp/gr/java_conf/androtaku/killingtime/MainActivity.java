package jp.gr.java_conf.androtaku.killingtime;

import android.app.ActionBar;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends Activity {

    KillingTimeView killingTimeView;
    MediaPlayer bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        killingTimeView = new KillingTimeView(this);
        setContentView(killingTimeView);
        bgm = MediaPlayer.create(this, R.raw.bgm);
        bgm.setLooping(true);
    }

    @Override
    public void onPause(){
        super.onPause();
        killingTimeView.stopThread();
        bgm.stop();
    }

    @Override
    public void onResume(){
        super.onResume();
        bgm.start();
    }
}
