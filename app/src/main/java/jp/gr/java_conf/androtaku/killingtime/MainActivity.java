package jp.gr.java_conf.androtaku.killingtime;

import android.app.ActionBar;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;


public class MainActivity extends Activity {

    KillingTimeView killingTimeView = null;
    LinearLayout linearLayout;
    MediaPlayer bgm;

    boolean initialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        killingTimeView = new KillingTimeView(this);
        linearLayout.addView(killingTimeView);
        bgm = MediaPlayer.create(this, R.raw.bgm);
        bgm.setLooping(true);

    }

    @Override
    public void onPause(){
        super.onPause();
        killingTimeView.stopThread();
        bgm.stop();
        try{
            bgm.prepare();
        }catch(IOException e){
            e.printStackTrace();
        }
        initialized = true;
    }

    @Override
    public void onResume(){
        super.onResume();
        bgm.start();
        if(killingTimeView != null && initialized) {
            //killingTimeView.startThread();
        }
    }
}
