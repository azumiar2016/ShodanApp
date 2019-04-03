package prototype.shodanappprototype;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class UseCameraToLogInActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        mScannerView = new ZXingScannerView(this);

        setContentView(mScannerView);

        mScannerView.setResultHandler(this);

        mScannerView.startCamera();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        // Prints the scan format (qrcode, pdf417 etc.)

        // QR-koodista saatu merkkijono asetetaan ApiKeyksi AppConstantteihin
        AppConstants.KEY_QR_CODE = rawResult.getText().toString();
        SaveAppConstant(AppConstants.KEY_QR_CODE,"apikey");
        Intent intent = new Intent(this,SearchActivity.class);
        this.startActivity(intent);
        finish();
    }

    public void SaveAppConstant(String ApiKey, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ApiKey);
        editor.putString(key, json);
        editor.commit();    // Voisi olla Apply, mutta koska tehdää vain kerran yksi arvo, niin parempi, että se tehdään heti eikä taustalla
    }
}

