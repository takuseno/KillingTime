package jp.gr.java_conf.androtaku.killingtime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by takuma on 2014/07/30.
 */
public class KillingTimeView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    SurfaceHolder holder = null;
    private Thread thread = null;
    private int dispWidth,dispHeight;

    private static final int START = 0;
    private static final int PLAYING = 1;
    private static final int RESULT = 2;
    private int viewMode = START;

    private int himaCounter;
    private int isogasiCounter;
    private int score;

    Context context;

    DrawKanji drawKanji;
    DrawStart drawStart;
    DrawPlayingScore drawPlayingScore;
    DrawResult drawResult;

    MediaPlayer bgm;

    public KillingTimeView(Context context){
        super(context);
        getHolder().addCallback(this);
        this.context = context;
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

        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }

        bgm = MediaPlayer.create(getContext(),R.raw.bgm);
        bgm.setLooping(true);
        bgm.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        thread = null;
        bgm.stop();
    }

    @Override
    public void run(){
        while(thread != null){
            long t1 = System.currentTimeMillis();

            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            switch (viewMode){
                case START:
                    drawKanji = new DrawKanji(context,dispWidth,dispHeight);
                    drawPlayingScore = new DrawPlayingScore(dispWidth,dispHeight);
                    drawResult = new DrawResult(context,dispWidth,dispHeight);
                    drawStart.drawStart(canvas);
                    break;

                case PLAYING:
                    drawKanji.kanjiAnimation();
                    drawKanji.drawKanji(canvas);
                    drawPlayingScore.drawPlayingScore(canvas,score);
                    break;

                case RESULT:
                    drawResult.drawResult(canvas,score);
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
                        himaCounter = 0;
                        isogasiCounter = 0;
                        score = 0;
                        viewMode = PLAYING;
                        break;

                    case PLAYING:
                        int judge = drawKanji.touchKanji();
                        if(judge == drawKanji.HIMA){
                            himaCounter++;
                            score += 10*drawKanji.getSpeed();
                            if(himaCounter%10 == 0) {
                                drawKanji.accelarateSpeed();
                            }
                        }
                        else if(judge == drawKanji.ISOGASI){
                            isogasiCounter++;
                            if(isogasiCounter > 3){
                                viewMode = RESULT;
                            }
                        }
                        break;

                    case RESULT:

                        break;

                    default:
                        break;
                }

                break;

            default:
        }
        return  true;
    }

    public void stopThread(){
        thread = null;
    }
}
