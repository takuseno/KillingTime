package jp.gr.java_conf.androtaku.killingtime;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by takuma on 2014/08/02.
 */
public class DrawPrepare {

    int dispWidth,dispHeight;
    private float xPosition = 0;
    private float animSpeed;
    private Paint numberPaint;

    public DrawPrepare(int dispWidth,int dispHeight){
        this.dispWidth = dispWidth;
        this.dispHeight = dispHeight;
        xPosition = dispWidth/2;
        animSpeed = dispWidth*0.03f;
        numberPaint = new Paint();
        numberPaint.setColor(Color.BLACK);
        numberPaint.setAntiAlias(true);
        numberPaint.setTextSize(dispWidth*0.8f);
    }

    public void drawPrepare(Canvas canvas){
        canvas.drawText("3 2 1",xPosition,
                (dispHeight/2) + ((numberPaint.getFontMetrics().bottom - numberPaint.getFontMetrics().top)/4),
                numberPaint);

        xPosition -= animSpeed;
    }

    public boolean isEnded(){
        if(xPosition + numberPaint.measureText("3 2 1") < 0){
            return true;
        }
        return false;
    }

    public void init(){
        xPosition = dispWidth/2;
    }
}
