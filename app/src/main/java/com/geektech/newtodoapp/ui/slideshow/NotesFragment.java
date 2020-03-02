package com.geektech.newtodoapp.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.geektech.newtodoapp.R;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class NotesFragment extends Fragment {

    public static final String EXTRA_KEY_TEST = "sometext";
    public static final int REQUEST_CODE_94 = 94;
    int size;

    private SlideshowViewModel slideshowViewModel;
    EditText editText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_slideshow, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_94 && resultCode == RESULT_OK) {
            size = data.getExtras().getInt(EXTRA_KEY_TEST);
           editText.setTextSize(size);
            editText.notify();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG", "onPause");
        save();
    }


    public void save() {
        String text = editText.getText().toString();
        File folder = new File(Environment.getExternalStorageDirectory(), "TodoApp");
        folder.mkdir();
        File file = new File(folder, "note.txt");
        try {
            FileUtils.writeStringToFile(file, text, "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}