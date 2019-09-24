package com.example.moviecatalog3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.moviecatalog3.localdata.SharedPreference;
import com.example.moviecatalog3.receiver.DailyAlarm;
import com.example.moviecatalog3.receiver.ReleaseAlarm;
import com.example.moviecatalog3.reminder.Alarm;

public class Language extends AppCompatActivity {
    RadioGroup radioGroup;
    private SharedPreference sharedPreference;
    Switch dailyAlarm, switchRelease;

    private final static int REQUEST_CODE_DAILY = 0;
    private final static int REQUEST_CODE_RELEASE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
         sharedPreference = new SharedPreference(this);

        radioGroup = findViewById(R.id.rb_group);
        dailyAlarm = findViewById(R.id.switchDaily);
        switchRelease = findViewById(R.id.switchReminder);
        if (sharedPreference.getReferences("lang").toLowerCase().trim().equals("id")) {
            RadioButton rbBahasa = radioGroup.findViewById(R.id.rb_bahasa);
            rbBahasa.setChecked(true);
        } else {
            RadioButton rbEnglish = radioGroup.findViewById(R.id.rb_english);
            rbEnglish.setChecked(true);
        }
        dailyAlarm.setChecked(sharedPreference.getAlarmRelease("daily"));
        switchRelease.setChecked(sharedPreference.getAlarmRelease("release"));
        dailyAlarm.setOnCheckedChangeListener((compoundButton, b) -> {
            sharedPreference.setPrefAlarmDaily(b);
            if (b) {
                Alarm.setAlarmManager(Language.this, 7, 0, 0, REQUEST_CODE_DAILY, DailyAlarm.class);
            } else {
                Alarm.cancelNotification(Language.this, REQUEST_CODE_DAILY, DailyAlarm.class);
            }
        });

        switchRelease.setOnCheckedChangeListener((compoundButton, b) -> {
            sharedPreference.setPrefAlarmRelease(b);
            if (b) {
                Alarm.setAlarmManager(Language.this, 8, 0, 0, REQUEST_CODE_RELEASE, ReleaseAlarm.class);
            } else {
                Alarm.cancelNotification(Language.this, REQUEST_CODE_RELEASE, ReleaseAlarm.class);
            }
        });

        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_bahasa:
                    sharedPreference.setPref("id", "id");
                    sendLang("in");
                    break;
                case R.id.rb_english:
                    sharedPreference.setPref("en-US", "en");
                    sendLang("en");
                    break;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    private void sendLang(String lang) {
        Intent gotomain = new Intent(this, MainActivity.class);
        gotomain.putExtra("lang", lang);
        startActivity(gotomain);
        finish();
    }

}

