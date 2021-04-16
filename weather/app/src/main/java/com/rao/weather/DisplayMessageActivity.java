package com.rao.weather;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(DisplayMessageActivity.this);
        textView.setTextSize(40);
        textView.setText("adfdsfsa");

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.content);
        layout.addView(textView);
    }
}