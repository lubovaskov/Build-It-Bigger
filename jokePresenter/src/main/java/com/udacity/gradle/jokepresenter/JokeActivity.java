package com.udacity.gradle.jokepresenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static String JOKE_TEXT_EXTRA_NAME = "joke_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvJoke = findViewById(R.id.text_view_joke);

        Intent intent = getIntent();
        if (intent != null) {
            String jokeText = intent.getStringExtra(JOKE_TEXT_EXTRA_NAME);
            if (jokeText == null) {
                tvJoke.setText(R.string.no_joke_text);
            } else {
                tvJoke.setText(jokeText);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case (android.R.id.home):
            default:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
