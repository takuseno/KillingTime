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
    Bitmap back,back_clicked,retry,retry_clicked;
    public final int NONE_CLICKED = 0;
    public final int BACK_CLICKED = 1;
    public final int RETRY_CLICKED = 2;
    public int clickType = NONE_CLICKED;

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
        back = BitmapFactory.decodeResource(context.getResources(),R.drawable.back);
        back_clicked = BitmapFactory.decodeResource(context.getResources(),R.drawable.back_clicked);
        retry = BitmapFactory.decodeResource(context.getResources(),R.drawable.retry);
        retry_clicked = BitmapFactory.decodeResource(context.getResources(),R.drawable.retry_clicked);
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

        Rect src = new Rect(0,0,back.getWidth(),back.getHeight());

        Rect backRect = new Rect((int)((dispWidth/4) - (dispWidth*0.1f)),(int)(dispHeight - (dispWidth*0.3f)),
                (int)((dispWidth/4) + (dispWidth*0.1f)),(int)(dispHeight - (dispWidth*0.1f)));
        if(clickType == BACK_CLICKED){
            canvas.drawBitmap(back_clicked, src, backRect, null);
        }
        else {
            canvas.drawBitmap(back, src, backRect, null);
        }

        Rect retryRect = new Rect((int)((3*dispWidth/4) - (dispWidth*0.1f)),(int)(dispHeight - (dispWidth*0.3f)),
                (int)((3*dispWidth/4) + (dispWidth*0.1f)),(int)(dispHeight - (dispWidth*0.1f)));
        if(clickType == RETRY_CLICKED){
            canvas.drawBitmap(retry_clicked, src, retryRect, null);
        }
        else {
            canvas.drawBitmap(retry, src, retryRect, null);
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
