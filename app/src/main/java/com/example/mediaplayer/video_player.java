package com.example.mediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class video_player extends AppCompatActivity {
    private SurfaceView surfaceview;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    Button button;
    Thread progress;
    CircleImageView circleImageView;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        surfaceview=findViewById(R.id.play);
        circleImageView=findViewById(R.id.image);
        button=findViewById(R.id.button);
        seekBar=findViewById(R.id.seekbar);
        mediaPlayer=MediaPlayer.create(this,R.raw.tiger);
        surfaceview.setKeepScreenOn(true);
        SurfaceHolder surfaceHolder= surfaceview.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                mediaPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    button.setText("Play");
                }
                else
                {
                    mediaPlayer.start();
                    button.setText("Pause");
                }
            }
        });


        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Toast.makeText(video_player.this, "Video is ready to play", Toast.LENGTH_SHORT).show();
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        //mediaPlayer.seekTo(i);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mediaPlayer.seekTo(seekBar.getProgress());

                    }
                });

                progress =new Thread()
                {
                    @Override
                    public void run() {
                        int current_position= 0;
                        while(current_position<mediaPlayer.getDuration())
                        {
                            current_position=mediaPlayer.getCurrentPosition();
                            seekBar.setProgress(current_position);
                        }

                    }
                };
                progress.start();
                if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration())
                {
                    seekBar.setProgress(0);
                }

            }
        });


        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    circleImageView.setBackgroundResource(R.drawable.ic_pause);
                }
                else
                {

                    mediaPlayer.start();
                    circleImageView.setBackgroundResource(R.drawable.ic_play);

                }

            }
        });



    }
}