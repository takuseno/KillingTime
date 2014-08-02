package jp.gr.java_conf.androtaku.killingtime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by takuma on 2014/08/01.
 */
public class DrawPlaying {
    private Bitmap hima,isogasi,hima_clicked,isogasi_clicked;

    public final int HIMA = 0;
    public final int ISOGASI = 1;
    public final int HIMA_CLICKED = 2;
    public final int ISOGASI_CLICKED = 3;
    private int showType = HIMA;
    private int kanijiAnimCounter = 0;
    private static final int OCCUR = 2;
    private static final int DISMISS = 1;
    private int kanjiAnimType = OCCUR;
    private float kanjiAnimSpeed = 1.0f;
    private float size = 1.0f;
    private int dispWidth,dispHeight;

    private boolean touched = false;

    private int himaCounter = 0;
    private int life = 3;
    private int score = 0;

    public DrawPlaying(Context context, int dispWidth, int dispHeight){
        hima = BitmapFactory.decodeResource(context.getResources(), R.drawable.killingtime);
        isogasi = BitmapFactory.decodeResource(context.getResources(),R.drawable.busy);
        hima_clicked = BitmapFactory.decodeResource(context.getResources(),R.drawable.killingtime_clicked);
        isogasi_clicked = BitmapFactory.decodeResource(context.getResources(),R.drawable.busy_clicked);
        this.dispWidth = dispWidth;
        this.dispHeight = dispHeight;
    }

    public void drawPlaying(Canvas canvas){
        canvas.save();
        Rect dst = new Rect((dispWidth/2) - (int)(dispWidth*0.2f),(dispHeight/2) - (int)(dispWidth*0.2f),
                (dispWidth/2) + (int)(dispWidth*0.2f),(dispHeight/2) + (int)(dispWidth*0.2f));

        canvas.scale(size,size,dispWidth/2,dispHeight/2);
        if(showType == HIMA) {
            Rect src = new Rect(0,0,hima.getWidth(),hima.getHeight());
            canvas.drawBitmap(hima, src, dst, null);
        }
        else if(showType == ISOGASI){
            Rect src = new Rect(0,0,isogasi.getWidth(),isogasi.getHeight());
            canvas.drawBitmap(isogasi,src,dst,null);
        }
        else if(showType == HIMA_CLICKED){
            Rect src = new Rect(0,0,hima_clicked.getWidth(),hima_clicked.getHeight());
            canvas.drawBitmap(hima_clicked,src,dst,null);
        }
        else if(showType == ISOGASI_CLICKED){
            Rect src = new Rect(0,0,isogasi_clicked.getWidth(),isogasi_clicked.getHeight());
            canvas.drawBitmap(isogasi_clicked,src,dst,null);
        }
        canvas.restore();

        Paint scorePaint = new Paint();
        scorePaint.setTextSize(dispWidth*0.1f);
        scorePaint.setAntiAlias(true);
        scorePaint.setColor(Color.BLACK);
        canvas.drawText("SCORE:" + String.valueOf(score),0,
                scorePaint.getFontMetrics().bottom - scorePaint.getFontMetrics().top,scorePaint);

        Paint lifePaint = new Paint();
        lifePaint.setTextSize(dispWidth*0.1f);
        lifePaint.setAntiAlias(true);
        lifePaint.setColor(Color.BLACK);
        canvas.drawText(String.valueOf(life),dispWidth - lifePaint.measureText(String.valueOf(life)),
                lifePaint.getFontMetrics().bottom - lifePaint.getFontMetrics().top,lifePaint);
    }

    public void kanjiAnimation(){
        switch (kanjiAnimType){
            case OCCUR:
                if(kanijiAnimCounter == 0){
                    size = 0;

                }
                else if(kanijiAnimCounter < 20/kanjiAnimSpeed){
                    size += 1.2/(20/kanjiAnimSpeed);
                }
                else if(kanijiAnimCounter < 30/kanjiAnimSpeed){
                    size -= 0.2/(10/kanjiAnimSpeed);
                }
                else if(kanijiAnimCounter > 30/kanjiAnimSpeed){
                    size = 1.0f;
                    kanijiAnimCounter = 0;
                    kanjiAnimType = DISMISS;
                }
                break;

            case DISMISS:
                if(kanijiAnimCounter == 0){
                    size = 1.0f;
                }
                else if(kanijiAnimCounter < 20/kanjiAnimSpeed){
                    size -= 1.0/(20/kanjiAnimSpeed);
                }
                else if(kanijiAnimCounter > 20/kanjiAnimSpeed){
                    size = 0;
                    kanijiAnimCounter = 0;
                    if(showType == HIMA){
                        life--;
                    }
                    touched = false;
                    Random rdm = new Random();
                    int random = rdm.nextInt(10);
                    if(random < 6){
                        showType = HIMA;
                    }
                    else{
                        showType = ISOGASI;
                    }

                    kanjiAnimType = OCCUR;
                }
                break;
        }
        kanijiAnimCounter++;
    }

    public void touchKanji(){
        if(showType == HIMA && !touched){
            showType = HIMA_CLICKED;
            touched = true;
            himaCounter++;
            score += 10*kanjiAnimSpeed;
            if(himaCounter%10 == 0) {
                kanjiAnimSpeed += 0.1f;
            }
        }
        else if(showType == ISOGASI && !touched){
            showType = ISOGASI_CLICKED;
            touched = true;
            life--;
        }
    }

    public int getScore(){
        return score;
    }

    public boolean isGameOver(){
        if(life == 0){
            return true;
        }

        return false;
    }

    public void init(){
        kanjiAnimSpeed = 1.0f;
        himaCounter = 0;
        life = 3;
        touched = false;
        score = 0;
        kanijiAnimCounter = 0;
        size = 0;
        showType = HIMA;
    }
}
