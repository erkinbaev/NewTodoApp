package com.geektech.newtodoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.MemoryFile;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.MemoryHandler;

public class ProfileActivity extends AppCompatActivity {
    EditText accountName;
    ImageView avatar;
    //private Uri avatarUri;
    String image;
    Button save;
    String profileName;

   private static final int GALLERY_REQUEST=94;
   private static final String LAST_NAME="";
   SharedPreferences sharedPreferences;
   SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        avatar = findViewById(R.id.avatar);
        accountName = findViewById(R.id.account_name);
        save=findViewById(R.id.save_changes);
        sharedPreferences = getSharedPreferences("avatar", MODE_PRIVATE);
        String av=sharedPreferences.getString("avatar", "");
        Glide.with(this).load(av).into(avatar);
         image = sharedPreferences.getString("image", "");
        avatar.setImageURI(Uri.parse(image));
        prefs=PreferenceManager.getDefaultSharedPreferences(this);
        String s =prefs.getString("accountName", "");
        accountName.setText(s);

//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


    }
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode== GALLERY_REQUEST && resultCode == RESULT_OK) {
           try{
               //assert data != null;
               Uri selectedAvatar=data.getData();
              // assert selectedAvatar != null;
               String uri=selectedAvatar.toString();
               InputStream imageStream=getContentResolver().openInputStream(selectedAvatar);
               Bitmap selectedImage=BitmapFactory.decodeStream(imageStream);
               sharedPreferences.edit().putString("avatar", uri).apply();
               avatar.setImageBitmap(selectedImage);
               Glide.with(this).load(uri).into(avatar);

           } catch (IOException exception){
               exception.printStackTrace();
           }

           //avatarUri = data.getData();
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("image", String.valueOf(avatarUri));
//            editor.apply();
//
//            avatar.setImageURI(avatarUri);
//             avatar.invalidate();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    public void onSave(View view) {
        profileName=accountName.getText().toString();
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("accountName", String.valueOf(accountName));
        editor.apply();
    }
}
