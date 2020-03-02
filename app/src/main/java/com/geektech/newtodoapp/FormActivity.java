package com.geektech.newtodoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.geektech.newtodoapp.models.Work;

public class FormActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDesc;
    Work myWork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle=findViewById(R.id.editTitle);
        editDesc=findViewById(R.id.editDesc);
        edit();
    }
    public void edit(){
        myWork= (Work) getIntent().getSerializableExtra("Work");
        if (myWork!=null){
            editDesc.setText(myWork.getDesc());
            editTitle.setText(myWork.getTitle());

        }
    }
    public void onClick(View view) {
        String title=editTitle.getText().toString().trim();
        String desc=editDesc.getText().toString().trim();
        Work work =new Work(title,desc);





        if (editTitle.getText().toString().matches("") || editDesc.getText().toString().matches("") ){
            Toast.makeText(getApplicationContext(),"enter some text", Toast.LENGTH_LONG).show();
        } else if (myWork!=null){
            myWork.setTitle(title);
            myWork.setDesc(desc);
            App.getDataBase().workDao().update(myWork);

        }else  {myWork=new Work(title, desc);
        App.getDataBase().workDao().insert(work);
       } finish();
    }


}
