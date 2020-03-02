package com.geektech.newtodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.geektech.newtodoapp.ui.home.HomeFragment;
import com.geektech.newtodoapp.ui.slideshow.NotesFragment;

public class SizeActivity extends AppCompatActivity {
RadioGroup radioGroup;
RadioButton rb14,rb22,rb28;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
        radioGroup=findViewById(R.id.radiogroupforsize);
        rb14=findViewById(R.id.sp14);
        rb22=findViewById(R.id.sp22);
        rb28=findViewById(R.id.sp28);

    }
    public void onSizeClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.sp14:
                intent.putExtra(NotesFragment.EXTRA_KEY_TEST, 14);
                break;
            case R.id.sp22:
                intent.putExtra(NotesFragment.EXTRA_KEY_TEST, 22);
                break;
            case R.id.sp28:
                intent.putExtra(NotesFragment.EXTRA_KEY_TEST, 28);
                break;
            default:
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }

}
