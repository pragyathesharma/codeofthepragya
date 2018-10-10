package com.example.sharma.q3game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import static android.view.MotionEvent.ACTION_UP;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    //Code from this program has been used from "Beginning Android Games" by Mario Zechner
    //Review SurfaceView, Canvas, continue

    GameSurface gameSurface;
    int timeLeft;

    boolean globalRunning, enemySpeedUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSurface = new GameSurface(this);
        setContentView(gameSurface);
        globalRunning = true;
        timeLeft = 30;



    }

    @Override
    protected void onPause(){
        super.onPause();
        gameSurface.pause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == ACTION_UP)
        {
            if(enemySpeedUp)
                enemySpeedUp=false;
            else enemySpeedUp= true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameSurface.resume();
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }




    //----------------------------GameSurface Below This Line--------------------------
    public class GameSurface extends SurfaceView implements Runnable, SensorEventListener {

        Thread gameThread;
        SurfaceHolder holder;
        volatile boolean running = false;
        boolean hit, hitOnce, enemyOnScreen, drawBoom;
        Bitmap enemy, boom;
        Rect playerRect, enemyRect;
        Paint paintProperty, starPaint;
        int enemyx, enemyy, screenWidth, screenHeight, value, value2, score;
        long startTime, hitTime;
        SensorManager sensorManager;
        Sensor accelerometerSensor;
        MediaPlayer sfx, music, ding;

        public GameSurface(Context context) {
            super(context);

            holder=getHolder();
            hit = false;
            enemy = BitmapFactory.decodeResource(getResources(),R.drawable.enemy);
            boom = BitmapFactory.decodeResource(getResources(),R.drawable.boom);
            score = 0;
            hitTime = 0;
            Display screenDisplay = getWindowManager().getDefaultDisplay();
            Point sizeOfScreen = new Point();
            screenDisplay.getSize(sizeOfScreen);
            screenWidth=sizeOfScreen.x;
            screenHeight=sizeOfScreen.y;
            value = screenWidth/2;
            value2 = 1300;
            Log.d("tagg", screenHeight+"");
            paintProperty= new Paint();
            paintProperty.setTextSize(100);
            paintProperty.setARGB(255, 255, 255, 255);
            starPaint = new Paint(paintProperty);
            playerRect = new Rect(value-64, value2-64, value+64, value2+64);
            enemyx = (int)((Math.random()*800)+20);
            enemyy = -450;
            enemyRect = new Rect(enemyx, enemyy, enemyx+300, enemyy+400);
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            accelerometerSensor = sensorManager.getDefaultSensor((Sensor.TYPE_ACCELEROMETER));
            sensorManager.registerListener(this, accelerometerSensor, sensorManager.SENSOR_DELAY_FASTEST);
            startTime = (System.currentTimeMillis());
            sfx = new MediaPlayer().create(MainActivity.this,R.raw.crash);
            ding = new MediaPlayer().create(MainActivity.this, R.raw.ding);
            music = new MediaPlayer().create(MainActivity.this, R.raw.cycle);
            music.start();
            music.setLooping(true);

        }

        @Override
        public void run() {
            while (running == true){
                globalRunning= true;

                if (holder.getSurface().isValid() == false)
                    continue;

                Canvas canvas= holder.lockCanvas();

                canvas.drawRGB(0,0,0);


                    for (int i = 0; i < 80; i++) {
                        starPaint.setAlpha((int) (Math.random() * 200));
                        canvas.drawCircle((int) (Math.random() * screenWidth), (int) (Math.random() * screenHeight), (int) ((Math.random() * 10) + 2), starPaint);
                    }

                canvas.drawBitmap(enemy, enemyx,enemyy, null);

                if(drawBoom)
                {
                    Log.d("calc", timeLeft-hitTime+"");

                        canvas.drawBitmap(boom, value-64, value2-64, null);

                }
                else{
                    canvas.drawCircle(value, value2, 64, paintProperty);

                }


                enemyRect = new Rect(enemyx, enemyy, enemyx+300, enemyy+400);


                long timeLeft = 30 - (System.currentTimeMillis() - startTime) / 1000;
                Log.d("time", timeLeft+"");

                if (timeLeft > 0) {
                    if(timeLeft>9)
                        canvas.drawText("00:"+timeLeft,50,150,paintProperty);
                    else canvas.drawText("00:0"+timeLeft,50,150,paintProperty);
                    if((timeLeft-hitTime)<1)
                        drawBoom = true;
                    else drawBoom = false;
                    if(!(enemyRect.intersect(playerRect)))
                    {
                        hit = false;

                    }
                    if (enemyRect.intersect(playerRect)) {
                        sfx.start();
                        hitTime=timeLeft;
                        hit = true;

                        if(score>0)
                            score--;
                        enemyx = (int)((Math.random()*800)+20);
                        enemyy = -450;


                    }
                    else if (enemyy<screenHeight-300) {
                        if(enemySpeedUp)
                            enemyy+=20;
                        else enemyy += 10;
                    }
                    else if(enemyy>=screenHeight-300) {
                        score++;
                        ding.start();
                        enemyx = (int)((Math.random()*800)+20);
                        enemyy = -450;
                        hitTime=0;
                    }
                }
                if (timeLeft <= 0) {
                    canvas.drawText("Game Over", 50, 150, paintProperty);
                    starPaint.setARGB(1, 255, 0, 0);
                    sensorManager.unregisterListener(this, accelerometerSensor);
                }

                if(enemyy>0 && enemyy<screenHeight-300)
                    enemyOnScreen=true;
                else enemyOnScreen=false;

                canvas.drawText(score+"", 890, 150, paintProperty);
                holder.unlockCanvasAndPost(canvas);
                Log.d("yeah", hit+"");
            }
        }

        public void resume(){
            running=true;
            globalRunning = true;
            gameThread=new Thread(this);
            gameThread.start();

        }

        public void pause() {
            running = false;
            globalRunning = false;
            while (true) {
                try {
                    gameThread.join();
                } catch (InterruptedException e) {
                }
            }
        }


        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            int xPos = (int)(sensorEvent.values[0]);
            int yPos = (int)(sensorEvent.values[1]);
            Log.d("tagg", value2+"");
            // Log.d("tag", "x: "+sensorEvent.values[0]);
            if(xPos<0 && value < screenWidth-70) {
                if(xPos<-3)
                    value+=10;
                value += 5;

            }
            else if(xPos>1 && value > 70)
            {
                if(xPos>3)
                    value-=10;
                value -= 5;

            }
            if(yPos<0 && value2 > 70) {
                value2-=10;
            }
            else if(yPos>1 && value2 < screenHeight-330)
            {
                if(yPos>3)
                    value2+=10;
                value2 += 5;

            }
            playerRect = new Rect(value-64, value2-64, value+64, value2+64);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }


    }//GameSurface



}//Activity

