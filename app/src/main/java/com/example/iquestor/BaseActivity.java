package com.example.iquestor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    int optionCurrentIndex;
    TextView once_upon;
    TextView ans_a;
    TextView ans_b;
    TextView ans_c;

    TextView next_sit;

    Button aboutUsBtn;
    Button howToPlayBtn;
    Button playBtn;
    Button resetProgressBtn;

    ImageView rankImageView;
    TextView rankTextView;
    int rank;
    TextView auTextBtn;
    TextView auMailBtn;
    int situation_Index;
    TextView htpChangeLangBtn;
    TextView htpChangeLangGuideBtn;
    TextView htpControlSoundsBtn;
    TextView htpControlSoundsGuideBtn;
    TextView htpAccBtn;
    TextView htpAccGuideBtn;
    TextView htpAboutBtn;
    TextView htpAboutGuideBtn;
    private int currentSituationIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        loadSavedLanguage();
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        updateUI();
    }

    public void updateUI() {
        aboutUsBtn = findViewById(R.id.about_us_btn);
        howToPlayBtn = findViewById(R.id.how_to_play_btn);
        playBtn = findViewById(R.id.play_btn);
        setTextSafely(aboutUsBtn, R.string.act_main_about_us_res);
        setTextSafely(howToPlayBtn, R.string.act_main_htp_res);
        setTextSafely(playBtn, R.string.act_main_play_res);

        auMailBtn = findViewById(R.id.about_us_mail_text);
        auTextBtn = findViewById(R.id.about_us_text);
        setTextSafely(auMailBtn, R.string.about_us_mail_res);
        setTextSafely(auTextBtn, R.string.about_us_text_res);

        htpChangeLangBtn = findViewById(R.id.htp_change_lang_text);
        htpChangeLangGuideBtn = findViewById(R.id.htp_change_lang_guide);
        htpAccBtn = findViewById(R.id.htp_change_acc_text);
        htpAccGuideBtn = findViewById(R.id.htp_change_acc_guide);
        htpControlSoundsBtn = findViewById(R.id.htp_change_volume_text);
        htpControlSoundsGuideBtn = findViewById(R.id.htp_change_volume_guide);
        htpAboutBtn = findViewById(R.id.htp_about_us_text);
        htpAboutGuideBtn = findViewById(R.id.htp_about_us_guide);
        setTextSafely(htpChangeLangBtn, R.string.htp_ht_change_lang_res);
        setTextSafely(htpChangeLangGuideBtn, R.string.htp_ht_change_lang_text_res);
        setTextSafely(htpAccBtn, R.string.htp_ht_change_acc_res);
        setTextSafely(htpAccGuideBtn, R.string.htp_ht_change_acc_text_res);
        setTextSafely(htpControlSoundsBtn, R.string.htp_ht_control_sounds_res);
        setTextSafely(htpControlSoundsGuideBtn, R.string.htp_ht_control_sounds_text_res);
        setTextSafely(htpAboutBtn, R.string.htp_ht_about_game_res);
        setTextSafely(htpAboutGuideBtn, R.string.htp_ht_about_game_text_res);

        TextView next_sit = findViewById(R.id.next_sit_button);
        setTextSafely(next_sit, R.string.nextSitRes);

        TextView finish_game = findViewById(R.id.finish_game_btn);
        setTextSafely(finish_game, R.string.nextSitRes1);

        setLocale(getCurrentLanguage());
    }

    public void updateStory(int situationIndex) {
        currentSituationIndex = situation_Index;
        once_upon = findViewById(R.id.situation_text_view);
        ans_a = findViewById(R.id.version1_text_view);
        ans_b = findViewById(R.id.version2_text_view);
        ans_c = findViewById(R.id.version3_text_view);
        next_sit = findViewById(R.id.next_sit_button);
        rankTextView = findViewById(R.id.ranktext);
        String[] ranks = getResources().getStringArray(R.array.ranks);
        String[] ranks1 = getResources().getStringArray(R.array.ranks1);
        rankTextView.setText(ranks1[0]);
        if (situationIndex == 0) {
            // Если это начало игры, показываем начальный текст
            setTextSafely(once_upon, R.string.startGameStory);

            ans_a.setVisibility(View.INVISIBLE);
            ans_b.setVisibility(View.INVISIBLE);
            ans_c.setVisibility(View.GONE);
            next_sit.setVisibility(View.VISIBLE);
        } else {
            rankTextView.setText(ranks1[rank]);
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
            situation_Index = situationIndex;
        }
    }

    void handleOptionClick(int optionIndex) {
        currentSituationIndex = situation_Index;
        TextView finish_game = findViewById(R.id.finish_game_btn);
        String[] ranks = getResources().getStringArray(R.array.ranks);
        String currentLanguage = getCurrentLanguage();
        switch (currentSituationIndex) {
            case 1:
                if (optionIndex == 1) {
                    rank = Math.min(rank + 1, ranks.length - 1);
                }
                break;
            case 2:
                if (optionIndex == 2) {
                    rank = Math.min(rank + 1, ranks.length - 1);
                }
                break;
            case 3:
                if (optionIndex == 1) {
                    rank = Math.min(rank + 1, ranks.length - 1);
                }
                break;
            case 4:
                if (optionIndex == 2) {
                    rank = Math.max(rank - 1, 0);
                }
                break;
            case 5:
                if (optionIndex == 2) {
                    rank = Math.min(rank + 1, ranks.length - 1);
                }
                break;
            case 6:
                if (optionIndex == 2 || optionIndex == 3) {
                    rank = Math.min(rank + 1, ranks.length - 1);
                }
                break;
            case 7:
                if (optionIndex == 1) {
                    rank = Math.max(rank - 1, 0);
                } else if (optionIndex == 2) {
                    rank = Math.min(rank + 2, ranks.length - 1);
                }
                break;
            case 8:
                if (optionIndex == 2) {
                    rank = Math.min(rank + 1, ranks.length - 1);
                } else if (optionIndex == 1) {
                    rank = Math.max(rank - 2, 0);
                }
                break;
            case 9:
                if (optionIndex == 2) {
                    rank = Math.min(rank + 1, ranks.length - 1);
                } else if (optionIndex == 1) {
                    rank = Math.max(rank - 1, 0);
                }
                break;
            case 10:
                if (optionIndex == 1) {
                    rank = Math.min(rank + 1, ranks.length - 1);
                } else if (optionIndex == 2) {
                    rank = Math.max(rank - 1, 0);
                }
                break;
            case 11:
                if (optionIndex == 1 || optionIndex == 2) {
                    rank = Math.min(rank + 1, ranks.length - 1);
                }
                break;
            case 12:
                if (optionIndex == 1) {
                    rank = Math.min(rank + 1, ranks.length - 1);
                }
                break;
            case 13:
                if (optionIndex == 2) {
                    rank = Math.min(rank + (ranks.length - rank), ranks.length - 1);
                }
                break;
            default:
                break;
        }

        String[] ranks1 = getResources().getStringArray(R.array.ranks1);
        rankTextView.setText(ranks1[rank]);

        if ((currentSituationIndex == 1 && optionIndex == 3) || (currentSituationIndex == 2 && (optionIndex == 1 || optionIndex == 3)) || (currentSituationIndex == 3 && optionIndex == 2) || (currentSituationIndex == 4 && optionIndex == 2) || (currentSituationIndex == 5 && (optionIndex == 1 || optionIndex == 3))) {
            // Получение массива историй из ресурсов
            String[] stories = getResources().getStringArray(getResources().getIdentifier("stories" + currentSituationIndex, "array", getPackageName()));
            // Отображение истории в зависимости от выбора
            String storyResult;
            if (currentLanguage.equals("ru")) {
                storyResult = stories[optionIndex - 1].replace("{звание}", String.valueOf(ranks[rank]));
            } else {
                storyResult = stories[optionIndex - 1].replace("{rank}", String.valueOf(ranks[rank]));
            }
            optionCurrentIndex = optionIndex - 1;
            once_upon.setText(storyResult);
            ans_a.setVisibility(View.INVISIBLE);
            ans_b.setVisibility(View.INVISIBLE);
            ans_c.setVisibility(View.GONE);
            next_sit.setVisibility(View.GONE);
            finish_game.setVisibility(View.VISIBLE);
            finish_game.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(String.valueOf(PlayActivity.class)));
                    finish();
                }
            });

        } else if ((currentSituationIndex == 6 && optionIndex == 1) || (currentSituationIndex == 7 && optionIndex == 3) || (currentSituationIndex == 8 && optionIndex == 3) || (currentSituationIndex == 9 && optionIndex == 3) || (currentSituationIndex == 12 && (optionIndex == 2 || optionIndex == 3))) {
            // Получение массива историй из ресурсов
            String[] stories = getResources().getStringArray(getResources().getIdentifier("stories" + currentSituationIndex, "array", getPackageName()));
            // Отображение истории в зависимости от выбора
            String storyResult;
            if (currentLanguage.equals("ru")) {
                storyResult = stories[optionIndex - 1].replace("{звание}", String.valueOf(ranks[rank]));
            } else {
                storyResult = stories[optionIndex - 1].replace("{rank}", String.valueOf(ranks[rank]));
            }
            optionCurrentIndex = optionIndex - 1;
            once_upon.setText(storyResult);
            ans_a.setVisibility(View.INVISIBLE);
            ans_b.setVisibility(View.INVISIBLE);
            ans_c.setVisibility(View.GONE);
            next_sit.setVisibility(View.GONE);
            finish_game.setVisibility(View.VISIBLE);
            finish_game.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(String.valueOf(PlayActivity.class)));
                    finish();
                }
            });
        } else if (currentSituationIndex == 13 && (optionIndex == 1 || optionIndex == 3 || optionIndex == 2)) {
            // Получение массива историй из ресурсов
            String[] stories = getResources().getStringArray(getResources().getIdentifier("stories" + currentSituationIndex, "array", getPackageName()));
            // Отображение истории в зависимости от выбора
            String storyResult;
            if (currentLanguage.equals("ru")) {
                storyResult = stories[optionIndex - 1].replace("{звание}", String.valueOf(ranks[rank]));
            } else {
                storyResult = stories[optionIndex - 1].replace("{rank}", String.valueOf(ranks[rank]));
            }
            optionCurrentIndex = optionIndex - 1;
            once_upon.setText(storyResult);
            ans_a.setVisibility(View.INVISIBLE);
            ans_b.setVisibility(View.INVISIBLE);
            ans_c.setVisibility(View.GONE);
            next_sit.setVisibility(View.GONE);
            finish_game.setVisibility(View.VISIBLE);
            finish_game.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(String.valueOf(PlayActivity.class)));
                    finish();
                }
            });
        } else {
            // Получение массива историй из ресурсов
            String[] stories = getResources().getStringArray(getResources().getIdentifier("stories" + currentSituationIndex, "array", getPackageName()));

            // Отображение истории в зависимости от выбора
            String storyResult;
            if (currentLanguage.equals("ru")) {
                storyResult = stories[optionIndex - 1].replace("{звание}", String.valueOf(ranks[rank]));
            } else {
                storyResult = stories[optionIndex - 1].replace("{rank}", String.valueOf(ranks[rank]));
            }
            optionCurrentIndex = optionIndex - 1;
            once_upon.setText(storyResult);
            ans_a.setVisibility(View.INVISIBLE);
            ans_b.setVisibility(View.INVISIBLE);
            ans_c.setVisibility(View.GONE);
            next_sit.setVisibility(View.VISIBLE);
            finish_game.setVisibility(View.GONE);
            situation_Index++;
        }


    }

    void nextBtnVisible() {
        // Получение массива историй из ресурсов
        String[] stories = getResources().getStringArray(getResources().getIdentifier("stories" + currentSituationIndex, "array", getPackageName()));
        String[] ranks = getResources().getStringArray(R.array.ranks);
        // Отображение истории в зависимости от выбора
        String currentLanguage = getCurrentLanguage();
        String storyResult;
        if (currentLanguage.equals("ru")) {
            storyResult = stories[optionCurrentIndex].replace("{звание}", String.valueOf(ranks[rank]));
        } else {
            storyResult = stories[optionCurrentIndex].replace("{rank}", String.valueOf(ranks[rank]));
        }
        String[] ranks1 = getResources().getStringArray(R.array.ranks1);
        rankTextView.setText(ranks1[rank]);
        once_upon.setText(storyResult);
        ans_a.setVisibility(View.INVISIBLE);
        ans_b.setVisibility(View.INVISIBLE);
        ans_c.setVisibility(View.GONE);
        next_sit.setVisibility(View.VISIBLE);

    }

    void finishBtnVisible() {
        TextView finish_btn = findViewById(R.id.finish_game_btn);
        // Получение массива историй из ресурсов
        String[] stories = getResources().getStringArray(getResources().getIdentifier("stories" + currentSituationIndex, "array", getPackageName()));

        // Отображение истории в зависимости от выбора
        String storyResult = stories[optionCurrentIndex];
        String[] ranks1 = getResources().getStringArray(R.array.ranks1);
        rankTextView.setText(ranks1[rank]);
        once_upon.setText(storyResult);
        ans_a.setVisibility(View.INVISIBLE);
        ans_b.setVisibility(View.INVISIBLE);
        ans_c.setVisibility(View.GONE);
        next_sit.setVisibility(View.GONE);
        finish_btn.setVisibility(View.VISIBLE);

    }

    private void setTextSafely(TextView textView, int stringResId) {
        if (textView != null) {
            textView.setText(getString(stringResId));
        }
    }


    public void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

    }

    public void saveLanguage(String languageCode) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", languageCode);
        editor.apply();
    }

    private void loadSavedLanguage() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedLanguage = preferences.getString("language", "");

        if (!savedLanguage.isEmpty()) {
            setLocale(savedLanguage);
        }
    }

    public String getCurrentLanguage() {
        Configuration configuration = getResources().getConfiguration();
        return configuration.locale.getLanguage();
    }
}