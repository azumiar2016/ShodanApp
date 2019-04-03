package prototype.shodanappprototype;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;



import com.google.gson.Gson;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    Button LogInButton,UseCameraButton;
    EditText ApiKeyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LogInButton = (Button)findViewById(R.id.LogInButton);
        UseCameraButton = (Button)findViewById(R.id.OpenCameraButton);
        ApiKeyText = (EditText)findViewById(R.id.ApikeyEditText);



        LogInButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

               if (ApiKeyText.getText().length()>10){
                   AppConstants.KEY_QR_CODE = ApiKeyText.getText().toString();
                   SaveAppConstant(AppConstants.KEY_QR_CODE,"apikey");
                   Intent intent = new Intent(LoginActivity.this,SearchActivity.class);
                   startActivity(intent);
                   finish();
               }


            }
        });

        UseCameraButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                AskPermission();


            }
        });
    }
    // Tallennetaan ApiKey, jota voidaan käyttää seuraavalla käynnistyksellä
    public void SaveAppConstant(String ApiKey, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ApiKey);
        editor.putString(key, json);
        editor.commit();
    }
    // Kysyy kameran oikeuksia ja kun ne annetaan avaa UseCameraToLogin-activityn
    public void AskPermission(){
    String[] permissions = {Manifest.permission.CAMERA};
    if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[0]) == PackageManager.PERMISSION_GRANTED){
       startCamera();
    }else{
        ActivityCompat.requestPermissions(LoginActivity.this,permissions,1);
    }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        AskPermission();

    }

    public void startCamera(){
        Intent intent = new Intent(LoginActivity.this,UseCameraToLogInActivity.class);
        startActivity(intent);
        finish();
    }

}

