package com.szstudio.syllabusroom;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    TextView tvForgotPassword, tvSignUp;
    TextInputEditText tietUsername,tietPassword;
    CheckBox cbShowHidePassword;
    AppCompatButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvForgotPassword=findViewById(R.id.tvLoginForgotPassword);
        tvSignUp=findViewById(R.id.tvLoginSignUp);
        tietUsername=findViewById(R.id.tietLoginPassword);
        tietPassword=findViewById(R.id.tietLoginPassword);
        btnLogin=findViewById(R.id.btnLoginLoginButton);
        cbShowHidePassword=findViewById(R.id.cbLoginShowHidePassword);
        tietPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tietUsername.getText().toString().isEmpty())
                {
                    tietUsername.setError("Username Required");
                }
                else if(tietUsername.getText().toString().length()<8){
                    tietUsername.setError("At Least 8 Characters Required");
                }
                else if(tietPassword.getText().toString().isEmpty())
                {
                    tietPassword.setError("Password Required");
                }
                else if(tietPassword.getText().toString().length()<8)
                {
                    tietPassword.setError("At Least 8 Characters Required");
                }
                else{
                    Intent i=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                }
            }
        });
        cbShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cbShowHidePassword.isChecked()){
                    tietPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    tietPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
    }
}