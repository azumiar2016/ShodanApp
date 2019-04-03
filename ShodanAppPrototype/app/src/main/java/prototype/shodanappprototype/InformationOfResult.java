package prototype.shodanappprototype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import com.google.gson.reflect.TypeToken;


// Activity Löydetyn tuloksen tietojen näyttöön
public class InformationOfResult extends AppCompatActivity {
    TextView Ip, Server, Country, HostName;
    String ipString,serverString,countryString,hostNameString;

    String html,uptime,product,version,port,link,os,timestamp,info; // Favouritesin tietojen laittoon
    MyMenu menu;
    Button take_me_to_site_button,Show_more_info,favourites_btn;
    int i;
    //String for previousactivity, different ways to create data
    String PreviousActivity;
    ArrayList<FoundDevice> favourites;
    ArrayList<String> FavouritesIpList;
    FoundDevice favouriteDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_of_result);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

            i = extras.getInt("EXTRA_index",0);
            PreviousActivity = extras.getString("EXTRAS_Activity");



        take_me_to_site_button = findViewById(R.id.TakeToSite);
        Show_more_info = findViewById(R.id.Show_more_info);
        favourites_btn = findViewById(R.id.AddToFavourites_btn);
        //Tarkistetaan onko laite jo Favouritena, jos tullaan muualta kuin Search-activitysta, niin voidaan olettaa, että laite on jo listalla

        if (PreviousActivity.equals("Search")){
            try {
                if(DataHandler.getInstance().getIpList().contains(DataHandler.getInstance().getList().get(i).getIpStr())) {
                    favourites_btn.setVisibility(View.GONE);
                }
            }catch (NullPointerException n){

            }

        }
        // Laitteen on pakko olla favourites-listalla koska tullaan favourites-activitystä
        else if(PreviousActivity.equals("Favourites")){
            favourites_btn.setVisibility(View.GONE);
        }



        Ip = findViewById(R.id.ipaddress);
        Server = findViewById(R.id.Server);
        Country = findViewById(R.id.Country);
        HostName = findViewById(R.id.HostName);
        setData();
        menu = new MyMenu(this);


        take_me_to_site_button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (PreviousActivity.equals("Search")){

                    String url ="http://" + DataHandler.getInstance().getList().get(i).getIpStr();
                    // String url = "www.google.fi";
                    // final String myPackage = getPackageName();
                    //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url + myPackage));
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(browserIntent);
                    //  startActivity(intent);
                }else if (PreviousActivity.equals("Favourites")){
                    String url ="http://" + DataHandler.getInstance().getFavouriteslist().get(i).getIp();
                    // String url = "www.google.fi";
                    // final String myPackage = getPackageName();
                    //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url + myPackage));
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(browserIntent);

                }


            }
        });


        Show_more_info.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(InformationOfResult.this, ExtraInformationOfDevice.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRAS_Activity",PreviousActivity);
                extras.putInt("EXTRA_index",i);
                myIntent.putExtras(extras);
                InformationOfResult.this.startActivity(myIntent);


            }
        });


        favourites_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //Jos Favourite listaa ei vielä ole niin se luodaan, eli jos yhtään favouritea ei ole vielä valittu


                if(DataHandler.getInstance().getFavouriteslist()== null && DataHandler.getInstance().getIpList() == null ){
                    favourites = new ArrayList<>();
                    FavouritesIpList = new ArrayList<>();
                    DataHandler.getInstance().setIpList(FavouritesIpList);
                    DataHandler.getInstance().setFavouriteslist(favourites); // lista
                }
                // Luodaan FoundDevice-olio, jolle annetaan arvoksi indeksi, jota sitten myöhemmin voidaan käyttää favouritelistaa avatessa, jotta päädytään oikeeseen laitteeseen

                setFoundDevice();
               DataHandler.getInstance().addtofavourites(favouriteDevice);
               DataHandler.getInstance().addToIpList(DataHandler.getInstance().getList().get(i).getIpStr());
               saveArrayList(DataHandler.getInstance().getIpList(),"iplist");
               saveObjectList(DataHandler.getInstance().getFavouriteslist(),"favouriteslist");
               //Favourite-nappi katoaa, kun sitä kerran painettu, tarkoitus muuttaa vielä niin, että se muuttuu Unfavourite-napiksi ja poistaa listalta.
               favourites_btn.setVisibility(View.GONE);


            }
        });

    }
    // Asetetaan erilaiset datat
    public void setData() {
        if (PreviousActivity.equals("Search")){
            html = DataHandler.getInstance().getList().get(i).getHtml();
            uptime = "jotain";
            product = DataHandler.getInstance().getList().get(i).getProduct();
            version = DataHandler.getInstance().getList().get(i).getVersion();
            port = "jokuportti";
            link = DataHandler.getInstance().getList().get(i).getLink();
            os = DataHandler.getInstance().getList().get(i).getOs();
            timestamp = DataHandler.getInstance().getList().get(i).getTimestamp();
            info = DataHandler.getInstance().getList().get(i).getInfo();

            ipString = DataHandler.getInstance().getList().get(i).getIpStr();    // haetaan ip-osoite
            serverString = DataHandler.getInstance().getList().get(i).getIsp();  // haetaan internet service provider
            hostNameString = DataHandler.getInstance().getList().get(i).getTitle();  // haetaan hostname
            countryString = getCountry();                                           // käytetään erillistä funktiota hakemaan maan nimi
            Ip.setText(ipString);
            Server.setText(serverString);
            HostName.setText(hostNameString);
            Country.setText(countryString);
        }
        else if (PreviousActivity.equals("Favourites") ){


            Ip.setText(DataHandler.getInstance().getFavouriteslist().get(i).getIp());
            Server.setText(DataHandler.getInstance().getFavouriteslist().get(i).getServer());
            HostName.setText(DataHandler.getInstance().getFavouriteslist().get(i).getHostName());
            Country.setText(DataHandler.getInstance().getFavouriteslist().get(i).getCountry());

        }

    }
    //Luodaan FoundDevice-olio joka lisätään favourites listaan
    public void setFoundDevice(){

        favouriteDevice = new FoundDevice(countryString, serverString, hostNameString,ipString,
                html,  uptime, product, version, port, link, os, timestamp, info);



    }

    //Lisätään menu activityyn
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
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
    // haetaan Geocoderilla maan nimi longituden ja latituden avulla
    public String getCountry(){
        String country = "Something went wrong"; // jos jostain syystä jotain mennyt pieleen
             String latitudeAndlongitude =  DataHandler.getInstance().getList().get(i).getLocation().toString(); // sisältää koko Stringin mikä saadaan tiedoista
             String[] separateLeft = latitudeAndlongitude.split("latitude=");
             String[] separateRight = separateLeft[1].split(",");
             String latitude = separateRight[0]; // latitude saatu Stringinä


             separateLeft = latitudeAndlongitude.split("longitude=");
             separateRight = separateLeft[1].split(",");
             String longitude = separateRight[0]; // Longitude saatu Stringinä

        // Kokeillaan muuttaa stringit doubleiksi, sekä sen jälkeen hakea niillä geocoderilla maan nimi
            try{
                double doubleLatitude = Double.parseDouble(latitude);
                double doubleLongitude = Double.parseDouble(longitude);

                Geocoder geocoder = new Geocoder(InformationOfResult.this, Locale.getDefault());

                country = geocoder.getFromLocation(doubleLatitude,doubleLongitude,1).get(0).getCountryName();

            }catch (Exception e){
                // Stringejä ei voinut muuttaa doubleiksi tai sitten doublen arvot ovat jostain syystä sellaisia ettei geocoder osaa niillä hakea
                // on myös mahdollista että kyseinen lokaatio on esimerkiksi "unknown" jolloin muuttaminen on tietysti mahdotonta
            }


        return country;  // palautetaan maan nimi
    }
    //Tallennetaan listat seuraavaa käynnistystä varten
    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public void saveObjectList(ArrayList<FoundDevice> deviceslist , String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(deviceslist);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }
}




