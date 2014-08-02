package jp.gr.java_conf.androtaku.killingtime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by takuma on 2014/08/01.
 */
public class DrawKanji {
    private Bitmap hima,isogasi,hima_clicked,isogasi_clicked;

    public static final int HIMA = 0;
    public static final int ISOGASI = 1;
    public static final int HIMA_CLICKED = 2;
    public final static int ISOGASI_CLICKED = 3;
    public final static int NOTHING = 4;
    private int showType = HIMA;
    private int kanijiAnimCounter = 0;
    private static final int OCCUR = 2;
    private static final int DISMISS = 1;
    private int kanjiAnimType = OCCUR;
    private float kanjiAnimSpeed = 1.0f;
    private float size = 1.0f;
    private int dispWidth,dispHeight;

    private boolean touched = false;

    public DrawKanji(Context context,int dispWidth,int dispHeight){
        hima = BitmapFactory.decodeResource(context.getResources(), R.drawable.killingtime);
        isogasi = BitmapFactory.decodeResource(context.getResources(),R.drawable.busy);
        hima_clicked = BitmapFactory.decodeResource(context.getResources(),R.drawable.killingtime_clicked);
        isogasi_clicked = BitmapFactory.decodeResource(context.getResources(),R.drawable.busy_clicked);
        this.dispWidth = dispWidth;
        this.dispHeight = dispHeight;
    }

    public void drawKanji(Canvas canvas){
        canvas.save();
        Rect dst = new Rect((dispWidth/2) - (int)(dispWidth*0.2f),(dispHeight/2) - (int)(dispWidth*0.2f),
                (dispWidth/2) + (int)(dispWidth*0.2f),(dispHeight/2) + (int)(dispWidth*0.2f));

        canvas.scale(size,size,dispWidth/2,dispHeight/2);
        if(showType == HIMA) {
            Rect src = new Rect(0,0,hima.getWidth(),hima.getHeight());
            canvas.drawBitmap(hima, src, dst, null);
        }
        else if(showType == ISOGASI){
            Rect src = new Rect(0,0,isogasi.getWidth(),isogasi.getHeight());
            canvas.drawBitmap(isogasi,src,dst,null);
        }
        else if(showType == HIMA_CLICKED){
            Rect src = new Rect(0,0,hima_clicked.getWidth(),hima_clicked.getHeight());
            canvas.drawBitmap(hima_clicked,src,dst,null);
        }
        else if(showType == ISOGASI_CLICKED){
            Rect src = new Rect(0,0,isogasi_clicked.getWidth(),isogasi_clicked.getHeight());
            canvas.drawBitmap(isogasi_clicked,src,dst,null);
        }
        canvas.restore();
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
                    this.kanjiAnimType = DISMISS;
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
                    touched = false;
                    Random rdm = new Random();
                    int random = rdm.nextInt(10);
                    if(random < 6){
                        showType = HIMA;
                    }
                    else{
                        showType = ISOGASI;
                    }

                    this.kanjiAnimType = OCCUR;
                }
                break;
        }
        kanijiAnimCounter++;
    }

    public int touchKanji(){
        if(showType == HIMA && !touched){
            showType = HIMA_CLICKED;
            touched = true;
            return HIMA;

        }
        else if(showType == ISOGASI && !touched){
            showType = ISOGASI_CLICKED;
            touched = true;
            return ISOGASI;
        }
        return NOTHING;
    }

    public void accelarateSpeed(){
        kanjiAnimSpeed += 0.1f;
    }

    public float getSpeed(){
        return kanjiAnimSpeed;
    }
}
