package jp.gr.java_conf.androtaku.killingtime;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by takuma on 2014/08/01.
 */
public class DrawStart {
    private int dispWidth,dispHeight;

    public DrawStart(Context context,int dispWidth,int dispHeight){
        this.dispWidth = dispWidth;
        this.dispHeight = dispHeight;
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
    }

    public void touchStart(){

    }
}
