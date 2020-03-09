package com.geektech.newtodoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {

    private EditText editPhone;
    private EditText editCode;
    private TextView timerText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    String verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        editPhone= findViewById(R.id.editPhone);
        editCode=findViewById(R.id.editCode);
        timerText=findViewById(R.id.timer);
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("TAG", "onVerificationCompleted");
                signIn(phoneAuthCredential);
            }


            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("TAG", "onVerificationFailed" + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verification=s;
                new CountDownTimer(60000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timerText.setText("timer"+ millisUntilFinished/1000);

                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(getApplicationContext(),"timer is finished", Toast.LENGTH_LONG).show();

                    }
                }.start();
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);

            }
        };
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PhoneActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PhoneActivity.this,MainActivity.class));
                } else {
                    Toast.makeText(PhoneActivity.this, "Ошибка" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onClickContinue(View view) {
        if(editPhone.getText().toString().matches("")){
            Toast.makeText(getApplicationContext(),"Enter your number", Toast.LENGTH_LONG).show();
        }else{
            String phone = editPhone.getText().toString().trim();
            LinearLayout numberField= findViewById(R.id.numberField);
            LinearLayout codeField=findViewById(R.id.codeField);
            numberField.setVisibility(View.INVISIBLE);
            codeField.setVisibility(View.VISIBLE);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phone,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    callbacks);}

    }

    public void onClickCode(View view) {
        String code =editCode.getText().toString().trim();
        if(TextUtils.isEmpty(code)){
            editCode.setError("Cannot be empty");
            return;
        }
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verification, code);
        signIn(phoneAuthCredential);
    }
}
