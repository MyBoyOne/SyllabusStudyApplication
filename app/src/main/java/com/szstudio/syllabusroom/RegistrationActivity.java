package com.szstudio.syllabusroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends AppCompatActivity {
    TextInputEditText tietName, tietMobileNo, tietEmialId, tietUsername, tietPassword, tietConfirmPassword;
    CheckBox cbShowHidePassword;
    AppCompatButton btnSignUp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        tietName=findViewById(R.id.tietRegistrationName);
        tietMobileNo=findViewById(R.id.tietRegistrationMobileNo);
        tietEmialId=findViewById(R.id.tietRegistrationEmailId);
        tietUsername=findViewById(R.id.tietRegistrationUsername);
       tietPassword=findViewById(R.id.tietRegistrationPassword);
        tietConfirmPassword=findViewById(R.id.tietRegistrationConfirmPassword);
        cbShowHidePassword=findViewById(R.id.cbRegistrationShowHidePassword);
        btnSignUp=findViewById(R.id.btnRegistrationSignUp);
        tietConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
       tietPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tietName.getText().toString().isEmpty()){
                    tietName.setError("Name Required");
                }
                else if(tietName.getText().toString().length()<8){
                    tietName.setError("At least 8 characters required");
                }
                else if(tietMobileNo.getText().toString().length()!=10)
                {
                    tietMobileNo.setError("Mobile no. must be 10 numbers");
                }
                else if(tietEmialId.getText().toString().isEmpty())
                {
                    tietEmialId.setError("Email ID required");
                }
                else  if(tietUsername.getText().toString().isEmpty())
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
                else if(!tietConfirmPassword.getText().toString().equals(tietPassword.getText().toString()))
                {
                    tietConfirmPassword.setError("Confirm Password Does not match");
                }
                else{

                    progressDialog = new ProgressDialog(RegistrationActivity.this);
                    progressDialog.setTitle("Registering Details");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();




                    userDetails();
                }
            }

            private void userDetails() {
                AsyncHttpClient client=new AsyncHttpClient();
                RequestParams params=new RequestParams();
                params.put("name", tietName.getText().toString());
                params.put("mobileno", tietMobileNo.getText().toString());
                params.put("emailid", tietEmialId.getText().toString());
                params.put("username", tietUsername.getText().toString());
                params.put("password", tietPassword.getText().toString());

                client.post("http://192.168.0.103:80/SyllabusRoomAPI/userregisterdetails.php/",params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Log.d("RegistrationActivity", "HTTP Status Code: " + statusCode);
                            // Rest of the code
                            if (response.has("success")) {
                                String status = response.getString("success");
                                if ("1".equals(status)) {
                                    runOnUiThread(() -> {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(RegistrationActivity.this, HomeActivity.class);
                                        startActivity(i);
                                    });
                                } else {
                                    runOnUiThread(() -> {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                    });
                                }
                            } else {
                                runOnUiThread(() -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegistrationActivity.this, "Unexpected response from server", Toast.LENGTH_SHORT).show();
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            runOnUiThread(() -> {
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                });
            }
        });
        cbShowHidePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cbShowHidePassword.isChecked())
                {
                   tietPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                   tietConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                   tietPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                   tietConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}