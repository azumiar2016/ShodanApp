package prototype.shodanappprototype;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    Button Search_btn,Favourites_btn,Info_btn,Login_btn;
    MyMenu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        String apikey;
        apikey = getAppConstantKey("apikey");
        AppConstants.KEY_QR_CODE = apikey;

        if (isFirstRun) {
            //show start activity

            startActivity(new Intent(MainActivity.this, GettingStartedActivity.class));

            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).apply();

        }
        else if (AppConstants.KEY_QR_CODE == null ){

            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }







        menu = new MyMenu(this);
        Search_btn = (Button)findViewById(R.id.search_button);
        Favourites_btn = (Button)findViewById(R.id.favourites_button);
        Info_btn = (Button)findViewById(R.id.about_button);
        Login_btn = (Button)findViewById(R.id.login_button);



        Search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Favourites_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(MainActivity.this,FavouritesActivity.class);
                startActivity(intent);
                finish();

            }
        });

        Info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
                finish();

            }
        });

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });






    }
    // tarkista ip-listojen tarpellisuus, korvaa esim favouriteslist.get(ip) tms.
    @Override
    protected void onStart(){
        super.onStart();


        ArrayList<String> faviplist = new ArrayList<>();
        faviplist = getArrayList("iplist");
        DataHandler.getInstance().setIpList(faviplist);

        ArrayList<FoundDevice> favouriteslist = new ArrayList<>();
        favouriteslist = getObjectList("favouriteslist");
        DataHandler.getInstance().setFavouriteslist(favouriteslist);
    }

    //  Luodaan menu activityyn
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu );
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        menu.SelectItem(item.getItemId());
        try{
            if(item.getItemId()==R.id.LogOut && item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_lock_black_24dp).getConstantState()) ){
                item.setIcon(R.drawable.ic_lock_open_black_24dp);
            }else if(item.getItemId()==R.id.LogOut){
                item.setIcon(R.drawable.ic_lock_black_24dp);
            }

        }catch (Exception e){

        }
        return true;
    }
    // Käynnistyksen yhteydessä palautetaan listat, sekä apikey
    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, "");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<FoundDevice> getObjectList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, "");
        Type type = new TypeToken<ArrayList<FoundDevice>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public String getAppConstantKey(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, "");
        Type type = new TypeToken<String>() {}.getType();
        return gson.fromJson(json, type);
    }
}
