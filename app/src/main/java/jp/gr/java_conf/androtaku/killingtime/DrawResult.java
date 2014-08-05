package jp.gr.java_conf.androtaku.killingtime;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by takuma on 2014/08/02.
 */
public class DrawResult {

    private int dispWidth,dispHeight;
    private int score;
    public final int NONE_CLICKED = 0;
    public final int BACK_CLICKED = 1;
    public final int RETRY_CLICKED = 2;
    public int clickType = NONE_CLICKED;

    private Paint paint,strokePaint,wordPaint;

    public DrawResult(Context context,int dispWidth,int dispHeight,int score){
        this.dispWidth = dispWidth;
        this.dispHeight = dispHeight;
        SharedPreferences prefs = context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        this.score = score;
        if(score > prefs.getInt("best_score",0)){
            editor.putInt("best_score",score);
            editor.commit();
        }

        wordPaint = new Paint();
        wordPaint.setColor(Color.BLACK);
        wordPaint.setAntiAlias(true);
        wordPaint.setTextSize(dispWidth*0.15f);

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(dispWidth*0.01f);

        strokePaint = new Paint();
        strokePaint.setColor(Color.BLACK);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(dispWidth*0.01f);
    }

    public void drawResult(Canvas canvas){
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

        if(clickType == BACK_CLICKED){
            canvas.drawCircle(dispWidth / 4, dispHeight - (dispWidth*0.2f), dispWidth * 0.1f, paint);
            canvas.drawCircle(dispWidth / 4, dispHeight - (dispWidth*0.2f), dispWidth * 0.1f, strokePaint);
            canvas.drawText("戻",(dispWidth/4) - (wordPaint.measureText("戻")/2),
                    dispHeight - ((wordPaint.getFontMetrics().bottom + wordPaint.getFontMetrics().top)/2) - (dispWidth*0.2f),
                    wordPaint);
        }
        else {
            canvas.drawCircle(dispWidth / 4, dispHeight - (dispWidth*0.2f), dispWidth * 0.1f, strokePaint);
            canvas.drawText("戻",(dispWidth/4) - (wordPaint.measureText("戻")/2),
                    dispHeight - ((wordPaint.getFontMetrics().bottom + wordPaint.getFontMetrics().top)/2) - (dispWidth*0.2f),
                    wordPaint);
        }

        if(clickType == RETRY_CLICKED){
            canvas.drawCircle(3*dispWidth / 4, dispHeight - (dispWidth*0.2f), dispWidth * 0.1f, paint);
            canvas.drawCircle(3*dispWidth / 4, dispHeight - (dispWidth*0.2f), dispWidth * 0.1f, strokePaint);
            canvas.drawText("再",(3 * dispWidth / 4) - (wordPaint.measureText("再")/2),
                    dispHeight - ((wordPaint.getFontMetrics().bottom + wordPaint.getFontMetrics().top)/2) - (dispWidth*0.2f),
                    wordPaint);
        }
        else {
            canvas.drawCircle(3*dispWidth / 4, dispHeight - (dispWidth*0.2f), dispWidth * 0.1f, strokePaint);
            canvas.drawText("再",(3 * dispWidth / 4) - (wordPaint.measureText("再")/2),
                    dispHeight - ((wordPaint.getFontMetrics().bottom + wordPaint.getFontMetrics().top)/2) - (dispWidth*0.2f),
                    wordPaint);
        }
    }

    public boolean isBack(float x,float y){
        if(x > (dispWidth/4) - (dispWidth*0.1f) && x < (dispWidth/4) + (dispWidth*0.1f)
                && y > dispHeight - (dispWidth*0.3f) && y < dispHeight - (dispWidth*0.1f)){
            return true;
        }
        return false;
    }

    public boolean isRetry(float x,float y){
        if(x > (3*dispWidth/4) - (dispWidth*0.1f) && x < (3*dispWidth/4) + (dispWidth*0.1f)
                && y > dispHeight - (dispWidth*0.3f) && y < dispHeight - (dispWidth*0.1f)){
            return true;
        }
        return false;
    }
}
