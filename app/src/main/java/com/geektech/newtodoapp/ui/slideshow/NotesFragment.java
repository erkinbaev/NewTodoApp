package com.geektech.newtodoapp.ui.slideshow;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.geektech.newtodoapp.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class NotesFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    EditText editText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_slideshow, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText=view.findViewById(R.id.editText);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG", "onPause");
        save();
    }


    public void save(){
String text=editText.getText().toString();
        File folder = new File(Environment.getExternalStorageDirectory(),"TodoApp");
        folder.mkdir();
        File file = new File(folder, "note.txt");
        try {
            FileUtils.writeStringToFile(file, text,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
