package jp.gr.java_conf.androtaku.killingtime;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by takuma on 2014/08/01.
 */
public class DrawPlayingScore {

    private int dispWidth,dispHeight;

    public DrawPlayingScore(int dispWidth,int dispHeight){
        this.dispWidth = dispWidth;
        this.dispHeight = dispHeight;
    }

    public void drawPlayingScore(Canvas canvas,int score){
        Paint scorePaint = new Paint();
        scorePaint.setTextSize(dispWidth*0.1f);
        scorePaint.setAntiAlias(true);
        scorePaint.setColor(Color.BLACK);
        canvas.drawText("SCORE:" + String.valueOf(score),0,scorePaint.getFontMetrics().bottom - scorePaint.getFontMetrics().top,scorePaint);
    }
}
