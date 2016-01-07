package com.jash.mediaplayerdemo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    private DrawRunnable runnable;
    private MediaPlayer player;
    private SurfaceView surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        VideoView video = (VideoView) findViewById(R.id.video);
//        video.setVideoURI(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "a.3gp")));
//        video.setVideoURI(Uri.parse("http://qiubai-video.qiushibaike.com/TZDB76UN2DJ5FYV2_3g.mp4"));
//        video.setMediaController(new MediaController(this));
//        video.start();
////        video.getCurrentPosition();
////        video.getDuration();
////        video.seekTo(1000);
////        video.pause();
        surface = (SurfaceView) findViewById(R.id.surface);
        surface.getHolder().addCallback(this);
//        runnable = new DrawRunnable(surface.getHolder());
//        surface.setOnTouchListener(runnable);
        player = new MediaPlayer();
        try {
//            player.setDataSource(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "a.3gp").getAbsolutePath());
            player.setDataSource("http://qiubai-video.qiushibaike.com/AIHMO0BAC1CMPAR6_3g.mp4");
            player.setOnPreparedListener(this);
            player.prepareAsync();
//            player.start();
//            player.setDisplay(surface.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player.setDisplay(holder);
//        ViewGroup.LayoutParams params = surface.getLayoutParams();
//        params.width = player.getVideoWidth();
//        params.height = player.getVideoHeight();
//        surface.setLayoutParams(params);
//        new Thread(runnable).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        runnable.setRun(false);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        ViewGroup.LayoutParams params = surface.getLayoutParams();
        params.width = player.getVideoWidth();
        params.height = player.getVideoHeight();
        surface.setLayoutParams(params);

    }

    public static class DrawRunnable implements Runnable, View.OnTouchListener {
        private SurfaceHolder holder;
        private boolean isRun;
        private Paint paint;
        private Path path;

        public DrawRunnable(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
            paint = new Paint();
            path = new Path();
            paint.setColor(0xffffffff);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
        }

        public void setRun(boolean run) {
            isRun = run;
        }

        @Override
        public void run() {
            while (isRun){
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(0xff000000);
                    canvas.drawPath(path, paint);
                    holder.unlockCanvasAndPost(canvas);

                }
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    path.lineTo(event.getX(), event.getY());
                    break;
            }
            return true;
        }
    }
}
