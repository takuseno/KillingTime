package jp.gr.java_conf.androtaku.killingtime;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.example.games.basegameutils.GameHelper;

import java.io.IOException;
import java.util.Random;

/**
 * Created by takuma on 2014/07/30.
 */
public class KillingTimeView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    SurfaceHolder holder = null;
    private Thread thread = null;
    public boolean initialized = false;
    private int dispWidth,dispHeight;

    private static final int START = 0;
    private static final int PLAYING = 1;
    private static final int RESULT = 2;
    private int viewMode = START;
    MediaPlayer bgm;

    Context context;

    DrawPlaying drawPlaying;
    DrawStart drawStart;
    DrawResult drawResult;
    DrawPrepare drawPrepare;
    InterstitialAd interstitial;
    AdRequest adRequest;
    BaseGameActivity baseGameActivity;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public KillingTimeView(Context context){
        super(context);
        getHolder().addCallback(this);
        this.context = context;
        interstitial = new InterstitialAd(context);
        interstitial.setAdUnitId("ca-app-pub-2444235792602347/7135917918");
        adRequest = new AdRequest.Builder()
                .addTestDevice("CEDA3A470BE0172E6E4DB4E139205B82")
                .addTestDevice("857A812B83EDE2C982622E53AD099F5B")
                .build();
        interstitial.loadAd(adRequest);

        bgm = MediaPlayer.create(context, R.raw.bgm);
        bgm.setLooping(true);

        prefs = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        editor = prefs.edit();

        baseGameActivity = (BaseGameActivity)context;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        this.holder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){
        drawStart = new DrawStart(context,width,height);
        dispWidth = width;
        dispHeight = height;
        initialized = true;
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        thread = null;
        initialized = false;
    }

    @Override
    public void run(){
        while(thread != null){
            long t1 = System.currentTimeMillis();

            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            switch (viewMode){
                case START:

                    drawStart.drawStart(canvas);
                    break;

                case PLAYING:
                    if(!drawPrepare.isEnded()){
                        drawPrepare.drawPrepare(canvas);
                    }
                    else {
                        drawPlaying.kanjiAnimation();
                        drawPlaying.drawPlaying(canvas);
                        if (drawPlaying.isGameOver()) {
                            drawResult = new DrawResult(context,dispWidth,dispHeight,drawPlaying.getScore());
                            viewMode = RESULT;
                            Random rdm = new Random();
                            if(rdm.nextInt(5) == 1){
                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        interstitial.show();
                                    }
                                });
                            }
                        }
                    }
                    break;

                case RESULT:
                    drawResult.drawResult(canvas);
                    break;

                default:
                    break;
            }
            holder.unlockCanvasAndPost(canvas);

            long t2 = System.currentTimeMillis();
            if(t2 - t1 < 16){
                try{
                    Thread.sleep(16-(t2-t1));
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                switch (viewMode){
                    case START:
                        if(drawStart.touchMusic(event.getX(),event.getY())){
                            if(prefs.getBoolean("music",true)){
                                stopMusic();
                                editor.putBoolean("music",false);
                                editor.commit();
                            }
                            else{
                                startMusic();
                                editor.putBoolean("music",true);
                                editor.commit();
                            }
                        }
                        else if(drawStart.touchRank(event.getX(),event.getY())){
                            drawStart.clickType = drawStart.LB_CLICKED;
                        }
                        else {
                            drawPlaying = new DrawPlaying(context, dispWidth, dispHeight);
                            drawPrepare = new DrawPrepare(dispWidth, dispHeight);
                            viewMode = PLAYING;
                        }
                        break;

                    case PLAYING:
                        if(drawPrepare.isEnded()) {
                            if(event.getX() < dispWidth/2 + (dispWidth*0.3f) && event.getX() > dispWidth/2 - (dispWidth*0.3f)){
                                if(event.getY() < dispHeight/2 + (dispWidth*0.3f) && event.getY() > dispHeight/2 - (dispWidth*0.3f)){
                                    drawPlaying.touchKanji();
                                }
                            }
                        }
                        break;

                    case RESULT:
                        if(drawResult.isBack(event.getX(),event.getY())){
                            drawResult.clickType = drawResult.BACK_CLICKED;
                            interstitial.loadAd(adRequest);
                        }
                        else if(drawResult.isRetry(event.getX(),event.getY())){
                            drawResult.clickType = drawResult.RETRY_CLICKED;
                            interstitial.loadAd(adRequest);
                        }
                        break;

                    default:
                }
                break;

            case MotionEvent.ACTION_UP:
                switch (viewMode){
                    case START:
                        if(drawStart.touchRank(event.getX(),event.getY())){
                            if(baseGameActivity.getGameHelper().isSignedIn()){
                                baseGameActivity.startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(baseGameActivity.getGameHelper().getApiClient()),5001);
                                drawStart.clickType = drawStart.NONE_CLICK;
                            }
                        }
                        break;

                    case RESULT:
                        if(drawResult.isBack(event.getX(),event.getY())){
                            drawStart = new DrawStart(context,dispWidth,dispHeight);
                            viewMode = START;
                        }
                        else if(drawResult.isRetry(event.getX(),event.getY())){
                            drawPlaying.init();
                            drawPrepare.init();
                            viewMode = PLAYING;
                        }
                        else{
                            drawResult.clickType = drawResult.NONE_CLICKED;
                        }
                        break;

                    default:
                }
                break;

            case MotionEvent.ACTION_MOVE:
                switch (viewMode){
                    case START:
                        if(!drawStart.touchRank(event.getX(),event.getY())){
                            drawStart.clickType = drawStart.NONE_CLICK;
                        }
                        break;

                    case RESULT:
                        if(!drawResult.isBack(event.getX(),event.getY())
                                && !drawResult.isRetry(event.getX(),event.getY())){
                            drawResult.clickType = drawResult.NONE_CLICKED;
                        }
                        break;

                    default:
                }
                break;



            default:
        }
        return  true;
    }

    public void stopThread(){
        thread = null;
    }

    public void startThread(){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stopMusic(){
        bgm.stop();
        try{
            bgm.prepare();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void startMusic(){
        bgm.start();
    }
}
