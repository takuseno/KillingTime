package jp.gr.java_conf.androtaku.killingtime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by takuma on 2014/07/30.
 */
public class KillingTimeView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    SurfaceHolder holder = null;
    private boolean isActive;
    private Thread thread;
    private long t1,t2;
    private Bitmap hima;
    private int dispWidth,dispHeight;

    float size = 1.0f;
    boolean touched = false;

    public KillingTimeView(Context context){
        super(context);
        getHolder().addCallback(this);
        hima = BitmapFactory.decodeResource(getResources(),R.drawable.killingtime);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        this.holder = holder;
        isActive = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){
        dispWidth = width;
        dispHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        isActive = false;
        thread = null;
    }

    @Override
    public void run(){
        while(isActive){
            t1 = System.currentTimeMillis();

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);

            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.save();
            canvas.scale(size,size,dispWidth/2 - 150,dispHeight/2 - 150);
            canvas.drawBitmap(hima, dispWidth / 2 - 150, dispHeight / 2 - 150, null);
            canvas.restore();
            if (touched && size > 0){
                size -= 0.1;
            }
            holder.unlockCanvasAndPost(canvas);
            t2 = System.currentTimeMillis();
            if(t2 - t1 < 16){
                try{
                    Thread.sleep(16-(t2-t1));
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touched = true;
                break;

            default:
        }
        return  true;
    }
}
