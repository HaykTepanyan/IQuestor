package com.example.iquestor;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class PlayActivity extends AppCompatActivity {

    ImageButton changeLangBtn;
    ImageButton logoutBtn;
    private AlertDialog Dialog;
    private AlertDialog.Builder Builder;
    private MediaPlayer mediaPlayer;
    private ImageButton playButton;
    private boolean isPlaying = false;
    private int currentSituationIndex = 1;
    TextView once_upon;
    TextView ans_a;
    TextView ans_b;
    TextView ans_c;

    TextView next_sit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

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

                Builder = new AlertDialog.Builder(PlayActivity.this);
                Builder.setMessage("Do you want to log out?");
                Builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(PlayActivity.this, LoginActivity.class));
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

        next_sit = findViewById(R.id.next_sit_button);
        once_upon = findViewById(R.id.situation_text_view);
        ans_a = findViewById(R.id.version1_text_view);
        ans_b = findViewById(R.id.version2_text_view);
        ans_c = findViewById(R.id.version3_text_view);


        next_sit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSituation(currentSituationIndex);
            }
        });

        ans_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(1);
                currentSituationIndex++;
            }
        });
        ans_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(2);
                currentSituationIndex++;
            }
        });
        ans_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(3);
                currentSituationIndex++;
            }
        });
    }

    private void updateSituation(int situationIndex) {
        once_upon = findViewById(R.id.situation_text_view);
        ans_a = findViewById(R.id.version1_text_view);
        ans_b = findViewById(R.id.version2_text_view);
        ans_c = findViewById(R.id.version3_text_view);
        next_sit = findViewById(R.id.next_sit_button);
        if (situationIndex == 0) {
            // Если это начало игры, показываем начальный текст
            once_upon.setText(R.string.startGameStory);
            ans_a.setVisibility(View.INVISIBLE);
            ans_b.setVisibility(View.INVISIBLE);
            ans_c.setVisibility(View.GONE);
            next_sit.setVisibility(View.VISIBLE);
        } else {
            // Получение массивов из ресурсов
            String[] scenarios = getResources().getStringArray(getResources().getIdentifier("sit", "array", getPackageName()));
            String[] options = getResources().getStringArray(getResources().getIdentifier("choices" + situationIndex, "array", getPackageName()));
            String[] stories = getResources().getStringArray(getResources().getIdentifier("stories" + situationIndex, "array", getPackageName()));
            // Отображение сценария
            once_upon.setText(scenarios[situationIndex - 1]);
            // Отображение вариантов выбора
            ans_a.setVisibility(View.VISIBLE);
            ans_b.setVisibility(View.VISIBLE);
            ans_c.setVisibility(View.VISIBLE);
            next_sit.setVisibility(View.GONE);
            ans_a.setText(options[0]);
            ans_b.setText(options[1]);
            ans_c.setText(options[2]);
            // Обновление текущего индекса ситуации
            currentSituationIndex = situationIndex;
        }
    }



    private void handleOptionClick(int optionIndex) {
        // Получение массива историй из ресурсов
        String[] stories = getResources().getStringArray(getResources().getIdentifier("stories" + currentSituationIndex, "array", getPackageName()));

        // Отображение истории в зависимости от выбора
        String storyResult = stories[optionIndex - 1];
        once_upon.setText(storyResult);
        ans_a.setVisibility(View.INVISIBLE);
        ans_b.setVisibility(View.INVISIBLE);
        ans_c.setVisibility(View.GONE);
        next_sit.setVisibility(View.VISIBLE);
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
