package com.example.iquestor;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MainActivity extends BaseActivity {
    ImageButton logoutBtn, languageBtn;
    Button aboutUsBtn;
    Button howToPlayBtn;
    Button playBtn;
    Button resetProgressBtn;
    private AlertDialog Dialog;
    private AlertDialog.Builder Builder;
    private MediaPlayer mediaPlayer;
    private ImageButton playButton;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutUsBtn = findViewById(R.id.about_us_btn);
        aboutUsBtn.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, AboutUsActivity.class)));

        howToPlayBtn = findViewById(R.id.how_to_play_btn);
        howToPlayBtn.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, HowToPlayActivity.class)));

        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        playButton = findViewById(R.id.volume_up_btn);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playButton.setImageResource(R.drawable.baseline_volume_off_24);
                    isPlaying = false;
                } else {
                    mediaPlayer.start();
                    playButton.setImageResource(R.drawable.baseline_volume_up_24);
                    isPlaying = true;
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.start();

        logoutBtn = findViewById(R.id.log_out_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Builder = new AlertDialog.Builder(MainActivity.this);
                Builder.setMessage("Do you want to log out?");
                Builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });

                Builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                Dialog = Builder.create();
                Dialog.show();
                Dialog.setCanceledOnTouchOutside(false);

            }
        });


    }

    protected void onPause(){
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            isPlaying = true;
            mediaPlayer.pause();
        }else{
            isPlaying = false;
        }
    }

    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.start();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}