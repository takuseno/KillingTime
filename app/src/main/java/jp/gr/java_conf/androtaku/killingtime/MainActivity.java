package jp.gr.java_conf.androtaku.killingtime;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;


import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends BaseGameActivity {

    KillingTimeView killingTimeView = null;
    LinearLayout linearLayout,adLayout;
    AdView adView;

    boolean initialized = false;

    SharedPreferences prefs = null;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        setRequestedClients(BaseGameActivity.CLIENT_ALL);

        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        killingTimeView = new KillingTimeView(this);
        linearLayout.addView(killingTimeView);

        adLayout = (LinearLayout)findViewById(R.id.adlayout);

        adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("CEDA3A470BE0172E6E4DB4E139205B82")
                .addTestDevice("857A812B83EDE2C982622E53AD099F5B")
                .build();
        adView.loadAd(adRequest);

        beginUserInitiatedSignIn();
    }

    public void showGuide(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        beginUserInitiatedSignIn();
                    }
                })
                .setTitle("遊び方")
                .setMessage("”暇”と”忙”がランダムに飛び出てきます。”忙”を押すか、”暇”を押しそこねるとミスになります。ミスを3回するとゲームオーバーです。さあ暇を潰しましょう！");
        builder.create().show();
        editor.putBoolean("guide",false);
        editor.commit();
    }

    @Override
    public void onPause(){
        super.onPause();
        EasyTracker.getInstance(this).activityStop(this);
        killingTimeView.stopThread();
        killingTimeView.stopMusic();
        initialized = true;
        adView.pause();
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
        }
        if(prefs.getBoolean("music",true)) {
            killingTimeView.startMusic();
        }
        if(killingTimeView != null && initialized) {
            killingTimeView.startThread();
        }
        adView.resume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        adView.destroy();
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
