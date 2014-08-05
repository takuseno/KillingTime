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

    private Paint greenPaint,redPaint,strokePaint,wordPaint;

    public DrawPlaying(Context context, int dispWidth, int dispHeight){
        this.dispWidth = dispWidth;
        this.dispHeight = dispHeight;

        greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(true);
        greenPaint.setStyle(Paint.Style.FILL);
        greenPaint.setStrokeWidth(dispWidth*0.01f);

        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(true);
        redPaint.setStyle(Paint.Style.FILL);
        redPaint.setStrokeWidth(dispWidth*0.01f);

        strokePaint = new Paint();
        strokePaint.setColor(Color.BLACK);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(dispWidth*0.01f);

        wordPaint = new Paint();
        wordPaint.setColor(Color.BLACK);
        wordPaint.setAntiAlias(true);
        wordPaint.setTextSize(dispWidth*0.3f);
    }

    public void drawPlaying(Canvas canvas){
        canvas.save();

        canvas.scale(size,size,dispWidth/2,dispHeight/2);
        if(showType == HIMA) {
            canvas.drawCircle(dispWidth / 2, dispHeight / 2, dispWidth * 0.2f, strokePaint);
            canvas.drawText("暇",(dispWidth/2) - (wordPaint.measureText("暇")/2),
                    (dispHeight/2) - ((wordPaint.getFontMetrics().bottom + wordPaint.getFontMetrics().top)/2),
                    wordPaint);
        }
        else if(showType == ISOGASI){
            canvas.drawCircle(dispWidth / 2, dispHeight / 2, dispWidth * 0.2f, strokePaint);
            canvas.drawText("忙",(dispWidth/2) - (wordPaint.measureText("忙")/2),
                    (dispHeight/2) - ((wordPaint.getFontMetrics().bottom + wordPaint.getFontMetrics().top)/2),
                    wordPaint);
        }
        else if(showType == HIMA_CLICKED){

            canvas.drawCircle(dispWidth / 2, dispHeight / 2, dispWidth * 0.2f,greenPaint);
            canvas.drawCircle(dispWidth / 2, dispHeight / 2, dispWidth * 0.2f, strokePaint);
            canvas.drawText("暇",(dispWidth/2) - (wordPaint.measureText("暇")/2),
                    (dispHeight/2) - ((wordPaint.getFontMetrics().bottom + wordPaint.getFontMetrics().top)/2),
                    wordPaint);
        }
        else if(showType == ISOGASI_CLICKED){
            canvas.drawCircle(dispWidth / 2, dispHeight / 2, dispWidth * 0.2f, redPaint);
            canvas.drawCircle(dispWidth / 2, dispHeight / 2, dispWidth * 0.2f, strokePaint);
            canvas.drawText("忙",(dispWidth/2) - (wordPaint.measureText("忙")/2),
                    (dispHeight/2) - ((wordPaint.getFontMetrics().bottom + wordPaint.getFontMetrics().top)/2),
                    wordPaint);
        }
        canvas.restore();

        Paint scorePaint = new Paint();
        scorePaint.setTextSize(dispWidth*0.1f);
        scorePaint.setAntiAlias(true);
        scorePaint.setColor(Color.BLACK);
        canvas.drawText("得点:" + String.valueOf(score),0,
                scorePaint.getFontMetrics().bottom - scorePaint.getFontMetrics().top,scorePaint);

        Paint lifePaint = new Paint();
        lifePaint.setTextSize(dispWidth*0.1f);
        lifePaint.setAntiAlias(true);
        lifePaint.setColor(Color.BLACK);
        canvas.drawText(String.valueOf("命:" + life),dispWidth - lifePaint.measureText(String.valueOf("命:" + life)),
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
        kanjiAnimType = OCCUR;
    }
}
