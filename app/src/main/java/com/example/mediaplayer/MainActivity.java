package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.security.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button pause;
    Button play;
    Button Video;
    SeekBar seekBar;
    Thread updateseek;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pause=findViewById(R.id.pause);
        play=findViewById(R.id.play);
        seekBar=findViewById(R.id.seek);
        Video=findViewById(R.id.video);
        mediaPlayer= MediaPlayer.create(this,R.raw.music);
        //mediaPlayer.start();



        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                //pause.setVisibility(View.GONE);
                //play.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //play.setVisibility(View.GONE);
               // pause.setVisibility(View.VISIBLE);
                mediaPlayer.start();
            }
        });


        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //Toast.makeText(MainActivity.this, "File is ready to play", Toast.LENGTH_SHORT).show();
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        //mediaPlayer.seekTo(progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mediaPlayer.seekTo(seekBar.getProgress());

                    }
                });




                updateseek =new Thread(){
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
                updateseek.start();
                if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration())
                {
                    seekBar.setProgress(0);
                }
            }
        });
        Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,video_player.class);
                startActivity(intent);
                mediaPlayer.pause();
            }
        });
    }
}