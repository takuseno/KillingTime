package jp.gr.java_conf.androtaku.killingtime;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;


import com.google.analytics.tracking.android.EasyTracker;

import jp.basicinc.gamefeat.android.sdk.controller.GameFeatAppController;
import jp.basicinc.gamefeat.android.sdk.view.GameFeatBannerView;
import jp.basicinc.gamefeat.android.sdk.view.GameFeatWallButtonView;

public class MainActivity extends BaseGameActivity {

    KillingTimeView killingTimeView = null;
    LinearLayout linearLayout;

    boolean initialized = false;

    SharedPreferences prefs = null;
    SharedPreferences.Editor editor;

    GameFeatAppController gfAppController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        killingTimeView = new KillingTimeView(this);
        linearLayout.addView(killingTimeView);

        gfAppController = new GameFeatAppController();

        GameFeatBannerView bannerView = (GameFeatBannerView)findViewById(R.id.adView);
        bannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gfAppController.show(MainActivity.this);
            }
        });


    }

    public void showGuide(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putBoolean("guide",false);
                        editor.commit();
                    }
                })
                .setTitle("遊び方")
                .setMessage("”暇”と”忙”がランダムに飛び出てきます。”忙”を押すか、”暇”を押しそこねるとミスになります。ミスを3回するとゲームオーバーです。さあ暇を潰しましょう！");
        builder.create().show();

    }

    @Override
    public void onPause(){
        super.onPause();
        EasyTracker.getInstance(this).activityStop(this);
        killingTimeView.stopThread();
        killingTimeView.stopMusic();
        initialized = true;
    }

    @Override
    public void onResume(){
        super.onResume();
        EasyTracker.getInstance(this).activityStart(this);
        if(prefs == null){
            prefs = getSharedPreferences("preferences",MODE_PRIVATE);
            editor = prefs.edit();
            if(prefs.getBoolean("guide",true)){
                showGuide();
            }
            if(prefs.getBoolean("SignIn",false)){
                beginUserInitiatedSignIn();
            }
        }
        if(prefs.getBoolean("music",true)) {
            killingTimeView.startMusic();
        }
        if(killingTimeView != null && initialized && killingTimeView.initialized) {
            killingTimeView.startThread();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onStart(){
        super.onStart();
        gfAppController.activateGF(MainActivity.this, false, false, false);
    }

    @Override
    public void onSignInSucceeded(){
        Log.i("signIn", "success");
        Games.Achievements.unlock(getGameHelper().getApiClient(),getString(R.string.first_achievement));
    }

    @Override
    public void onSignInFailed(){
        Log.i("signIn","failed");
    }
}
