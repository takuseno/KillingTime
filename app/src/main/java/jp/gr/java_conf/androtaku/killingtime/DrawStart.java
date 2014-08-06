package jp.gr.java_conf.androtaku.killingtime;

import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by takuma on 2014/08/01.
 */
public class DrawStart {
    private int dispWidth,dispHeight;
    SharedPreferences prefs;
    Paint musicPaint,greenPaint,redPaint,circlePaint;

    public DrawStart(Context context,int dispWidth,int dispHeight){
        this.dispWidth = dispWidth;
        this.dispHeight = dispHeight;
        prefs = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        musicPaint = new Paint();
        musicPaint.setColor(Color.BLACK);
        musicPaint.setAntiAlias(true);
        musicPaint.setTextSize(dispWidth*0.1f);

        circlePaint = new Paint();
        circlePaint.setColor(Color.BLACK);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(dispWidth*0.01f);

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
    }

    public void drawStart(Canvas canvas){
        Paint logoPaint = new Paint();
        logoPaint.setColor(Color.BLACK);
        logoPaint.setAntiAlias(true);
        logoPaint.setTextSize(dispWidth*0.3f);
        canvas.drawText("暇潰し",(dispWidth/2) - (logoPaint.measureText("暇潰し")/2),
                (dispHeight/2) - ((logoPaint.getFontMetrics().bottom - logoPaint.getFontMetrics().top)/2),
                logoPaint);

        Paint touchPaint = new Paint();
        touchPaint.setColor(Color.BLACK);
        touchPaint.setAntiAlias(true);
        touchPaint.setTextSize(dispWidth*0.05f);
        canvas.drawText("タッチしてスタート",(dispWidth/2) - (touchPaint.measureText("タッチしてスタート")/2),
                (dispHeight/2) - (touchPaint.getFontMetrics().bottom - touchPaint.getFontMetrics().top),
                touchPaint);

        Paint bestPaint = new Paint();
        bestPaint.setColor(Color.BLACK);
        bestPaint.setAntiAlias(true);
        bestPaint.setTextSize(dispWidth*0.08f);
        canvas.drawText("最高得点:" + String.valueOf(prefs.getInt("best_score",0)),
                (dispWidth/2) - (bestPaint.measureText("最高得点:" + String.valueOf(prefs.getInt("best_score",0)))/2),
                dispHeight - (bestPaint.getFontMetrics().bottom - bestPaint.getFontMetrics().top),
                bestPaint);


        if(prefs.getBoolean("music",true)) {
            canvas.drawCircle(musicPaint.measureText("音") / 2,
                    (musicPaint.getFontMetrics().bottom - musicPaint.getFontMetrics().top) / 1.5f,
                    dispWidth * 0.1f, greenPaint);
        }
        else{
            canvas.drawCircle(musicPaint.measureText("音") / 2,
                    (musicPaint.getFontMetrics().bottom - musicPaint.getFontMetrics().top) / 1.5f,
                    dispWidth * 0.1f, redPaint);
        }

        canvas.drawCircle(musicPaint.measureText("音") / 2,
                (musicPaint.getFontMetrics().bottom - musicPaint.getFontMetrics().top) / 1.5f,
                dispWidth * 0.1f, circlePaint);

        canvas.drawText("音",0,
                musicPaint.getFontMetrics().bottom - musicPaint.getFontMetrics().top,
                musicPaint);
    }

    public boolean touchMusic(float x,float y){
        if(x > 0 && x < (musicPaint.measureText("音")/2) + (dispWidth*0.1f)){
            if(y > 0 && y < (musicPaint.getFontMetrics().bottom - musicPaint.getFontMetrics().top)/1.5f + (dispWidth*0.1f)){
                return true;
            }
        }
        return false;
    }
}
