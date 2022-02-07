package com.example.zadaca_lv12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private FloatingActionButton mainFAB;
    private FloatingActionButton callFAB;
    private FloatingActionButton mailFAB;
    private FloatingActionButton alarmFAB;
    private FloatingActionButton cameraFAB;
    private FloatingActionButton dialogFAB;

    private boolean isFabOpen = false;
    private View view;

    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFAB = findViewById(R.id.mainFAB);
        callFAB = findViewById(R.id.callFAB);
        mailFAB = findViewById(R.id.mailFAB);
        alarmFAB = findViewById(R.id.alarmFAB);
        cameraFAB = findViewById(R.id.cameraFAB);
        dialogFAB = findViewById(R.id.dialogFAB);

        view = findViewById(R.id.bg_fab);
        setFabs();
    }

    private void setFabs() {
        mainFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFabOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
            }
        });

        callFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call();
            }
        });

        mailFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("almedina_alibasic@hotmail.com", "Razvoj mobilnih aplikacija");
            }
        });

        alarmFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlarm("Wake Up!", 12, 0);
            }
        });

        cameraFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePhoto();
            }
        });

        dialogFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        DatePickerFragment dialog = new DatePickerFragment();
        dialog.show(getSupportFragmentManager(), "MainActivity");
    }

    private void Call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        startActivity(intent);
    }

    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeEmail(String addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void openMenu() {
        isFabOpen = true;

        mainFAB.animate().rotation(135);

        callFAB.setVisibility(View.VISIBLE);
        callFAB.animate().translationX(-180).rotation(0);

        mailFAB.setVisibility(View.VISIBLE);
        mailFAB.animate().translationX(-360).rotation(0);

        dialogFAB.setVisibility(View.VISIBLE);
        dialogFAB.animate().translationY(-180).rotation(0);

        cameraFAB.setVisibility(View.VISIBLE);
        cameraFAB.animate().translationX(360).rotation(0);

        alarmFAB.setVisibility(View.VISIBLE);
        alarmFAB.animate().translationX(180).rotation(0);

        view.setAlpha(1f);
    }

    private void closeMenu() {
        isFabOpen = false;

        callFAB.animate().translationX(0).rotation(90);
        mailFAB.animate().translationX(0).rotation(90);
        alarmFAB.animate().translationX(0).rotation(90);
        cameraFAB.animate().translationX(0).rotation(90);
        dialogFAB.animate().translationY(0).rotation(90);

        mainFAB.animate().rotation(0);

        view.setAlpha(0);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = findViewById(R.id.dateText);
        textView.setText(currentDateString);
    }
}
