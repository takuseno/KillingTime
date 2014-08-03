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

    public DrawStart(Context context,int dispWidth,int dispHeight){
        this.dispWidth = dispWidth;
        this.dispHeight = dispHeight;
        prefs = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
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
    }

    public void touchStart(){

    }
}
