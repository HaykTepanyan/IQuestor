package com.example.iquestor;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    Button aboutUsBtn;
    Button howToPlayBtn;
    Button playBtn;
    Button resetProgressBtn;


    TextView auTextBtn;
    TextView auCreatorBtn;
    TextView auTeacherBtn;
    TextView auMailBtn;
    TextView auPhoneBtn;

    TextView htpChangeLangBtn;
    TextView htpChangeLangGuideBtn;
    TextView htpControlSoundsBtn;
    TextView htpControlSoundsGuideBtn;
    TextView htpAccBtn;
    TextView htpAccGuideBtn;
    TextView htpAboutBtn;
    TextView htpAboutGuideBtn;

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

    private void updateUI() {
        aboutUsBtn = findViewById(R.id.about_us_btn);
        howToPlayBtn = findViewById(R.id.how_to_play_btn);
        playBtn = findViewById(R.id.play_btn);
        resetProgressBtn = findViewById(R.id.reset_btn);
        aboutUsBtn.setText(getString(R.string.act_main_about_us_res));
        howToPlayBtn.setText(getString(R.string.act_main_htp_res));
        playBtn.setText(getString(R.string.act_main_play_res));
        resetProgressBtn.setText(getString(R.string.act_main_reset_progress_res));

        auMailBtn = findViewById(R.id.about_us_mail_text);
        auCreatorBtn = findViewById(R.id.about_us_creator_text);
        auTeacherBtn = findViewById(R.id.about_us_teacher_text);
        auPhoneBtn = findViewById(R.id.about_us_phone_text);
        auTextBtn = findViewById(R.id.about_us_text);
        auMailBtn.setText(getString(R.string.about_us_mail_res));
        auCreatorBtn.setText(getString(R.string.about_us_creator_res));
        auTeacherBtn.setText(getString(R.string.about_us_teacher_res));
        auPhoneBtn.setText(getString(R.string.about_us_phone_res));
        auTextBtn.setText(getString(R.string.about_us_text_res));

        htpChangeLangBtn = findViewById(R.id.htp_change_lang_text);
        htpChangeLangGuideBtn = findViewById(R.id.htp_change_lang_guide);
        htpAccBtn = findViewById(R.id.htp_change_acc_text);
        htpAccGuideBtn = findViewById(R.id.htp_change_acc_guide);
        htpControlSoundsBtn = findViewById(R.id.htp_change_volume_text);
        htpControlSoundsGuideBtn = findViewById(R.id.htp_change_volume_guide);
        htpAboutBtn = findViewById(R.id.htp_about_us_text);
        htpAboutGuideBtn = findViewById(R.id.htp_about_us_guide);
        htpChangeLangBtn.setText(getString(R.string.about_us_mail_res));
        htpChangeLangGuideBtn.setText(getString(R.string.about_us_creator_res));
        htpAccBtn.setText(getString(R.string.about_us_teacher_res));
        htpAccGuideBtn.setText(getString(R.string.about_us_phone_res));
        htpControlSoundsBtn.setText(getString(R.string.about_us_mail_res));
        htpControlSoundsGuideBtn.setText(getString(R.string.about_us_creator_res));
        htpAboutBtn.setText(getString(R.string.about_us_teacher_res));
        htpAboutGuideBtn.setText(getString(R.string.about_us_phone_res));


        setLocale(getCurrentLanguage());
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private void saveLanguage(String languageCode) {
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

    private String getCurrentLanguage() {
        Configuration configuration = getResources().getConfiguration();
        return configuration.locale.getLanguage();
    }
}
