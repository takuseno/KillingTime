package jp.gr.java_conf.androtaku.killingtime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by takuma on 2014/08/02.
 */
public class DrawResult {

    private int dispWidth,dispHeight;

    public DrawResult(Context context,int dispWidth,int dispHeight){
        this.dispWidth = dispWidth;
        this.dispHeight = dispHeight;
    }

    public void drawResult(Canvas canvas,int score){
        Paint resultPaint = new Paint();
        resultPaint.setColor(Color.BLACK);
        resultPaint.setAntiAlias(true);
        resultPaint.setTextSize(dispWidth*0.3f);
        canvas.drawText("結果",(dispWidth/2) - (resultPaint.measureText("結果")/2),
                (dispHeight/2) - ((resultPaint.getFontMetrics().bottom - resultPaint.getFontMetrics().top)/2),
                resultPaint);

        Paint scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        scorePaint.setAntiAlias(true);
        scorePaint.setTextSize(dispWidth*0.2f);
        canvas.drawText(String.valueOf(score) + "点",(dispWidth/2) - (scorePaint.measureText(String.valueOf(score) + "点")/2),
                (dispHeight/2) + ((scorePaint.getFontMetrics().bottom - scorePaint.getFontMetrics().top)/2),
                scorePaint);
    }
}
