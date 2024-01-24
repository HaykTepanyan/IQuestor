package com.example.iquestor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;

public class AboutUsActivity extends AppCompatActivity {

    ImageButton logoutBtn, languageBtn;

    private AlertDialog Dialog;
    private AlertDialog.Builder Builder;
    private MediaPlayer mediaPlayer;
    private ImageButton playButton;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


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

        languageBtn = findViewById(R.id.language_btn);
        languageBtn.setOnClickListener((v)-> showMenu());

        logoutBtn = findViewById(R.id.log_out_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Builder = new AlertDialog.Builder(AboutUsActivity.this);
                Builder.setMessage("Do you want to log out?");
                Builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(AboutUsActivity.this, LoginActivity.class));
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

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(AboutUsActivity.this, languageBtn);
        popupMenu.getMenu().add("English");
        popupMenu.getMenu().add("Russian");
        popupMenu.show();
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