package com.geektech.newtodoapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.IOException;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.geektech.newtodoapp.R.color.colorAccent;
import static com.geektech.newtodoapp.R.color.error_color_material_dark;
import static com.geektech.newtodoapp.R.drawable.ic_launcher_foreground;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    private final int RC_WRITE_EXTERNAL = 101;
    private AppBarConfiguration mAppBarConfiguration;
    EditText notes;
    ImageView imageView;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    Log.e("ololo", image );
        //showImage();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, PhoneActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, FormActivity.class), 100);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Intent intent=getIntent();
String name=intent.getStringExtra(EXTRA_NAME);
TextView nameView=findViewById(R.id.nickname);

//nameView.setText(name);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        // notes=findViewById(R.id.notes);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @AfterPermissionGranted(RC_WRITE_EXTERNAL)
    private void initFile(String content) {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (EasyPermissions.hasPermissions(this, permission)) {
            File folder = new File(Environment.getExternalStorageDirectory(), "TodoApp");
            folder.mkdirs();
            File file = new File(folder, "note.txt");
            try {
                file.createNewFile();
//                FileOutputStream fos = new FileOutputStream(file);
//                fos.write(content.getBytes());
//                fos.close();
//                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            EasyPermissions.requestPermissions(this, "Разреши!", RC_WRITE_EXTERNAL, permission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        item.getItemId(R.id.action_clear);

        switch (item.getItemId()) {
            case R.id.size_redactor:
                Intent intent = new Intent(this, SizeActivity.class);
                startActivity(intent);
                break;
            case R.id.signOut:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Signing Out").setMessage("do you wants signing out?").setNegativeButton("No cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Yes of course", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this, PhoneActivity.class));

                    }
                }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void onClickImage(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
//    public void showImage() {
//        try {
//            SharedPreferences sharedPreferences= getSharedPreferences("image",MODE_PRIVATE);
//            imageView = findViewById(R.id.imageView);
//            Uri a = Uri.parse(sharedPreferences.getString("image",""));
//            imageView.setImageURI(a);
//            Log.e("arara", a+"" );
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }
//
//
//        }
    }

//    @Override
//    public void onBackPressed() {
//        notes=findViewById(R.id.editText);
//        initFile(notes.getText().toString());
//        super.onBackPressed();
//    }

