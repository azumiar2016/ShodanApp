package prototype.shodanappprototype;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fooock.shodan.model.host.HostReport;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    TextView text;
    EditText searchText;
    HostReport reportHostData;
    String SearchString;
    int FoundData;
    FoundDevice device;
    ArrayList list;
    CustomListAdapter adapter;
    SearchThread mysearch;
    ListView listView;
    MyMenu menu;
    Intent intent;
    static ArrayList<FoundDevice> ArrayOfDevices = new ArrayList<FoundDevice>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Käynnistetään aluksi GettingStartedActivity, joka ohjeistaa ohjelman käytössä
        // Muutataan siten, että vain ekalla käynnistyksellä!!!!!!!!
        intent = new Intent(this,GettingStartedActivity.class);
        startActivity(intent);

        //Jos ei olla kirjauduttu sisään niin avataan käynnistyksen yhteydessä UseCameraToLogInActivity
        if (AppConstants.KEY_QR_CODE.equals("")) {
          intent  = new Intent(this, UseCameraToLogInActivity.class);
            startActivity(intent);
        }


        searchText = (EditText)findViewById(R.id.SearchText);
        text = (TextView)findViewById(R.id.SearchInfoText);
        list = new ArrayList();
        menu = new MyMenu(this);
        mysearch = null;
        //Asetetaan hakunappi
        Button SearchButton = (Button)findViewById(R.id.SearchButton);

        SearchButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                //  ConnectionHandler connectionHandler = new ConnectionHandler(searchText.getText().toString(),SearchActivity.this);
                SearchString = searchText.getText().toString();
                if (AppConstants.KEY_QR_CODE.equals("")) {
                    text.setText(getString(R.string.Have_to_Login_first));
                } else {

                    //Käynnistetään SearchThreadi
                    StartSearchThread();
                    // FoundData sisältää löydettyjen laitteiden määrän
                    String homo = Integer.toString(FoundData);
                    // Tulostetaan näkyviin löydettyjen laitteiden määrä ja kerrotaan, että ilmaisella tilillä näkee vain 100 tulosta
                    String x = homo + getString(R.string.Found_results);
                    text.setText(x);

                    // Jos tehdyllä haulla saadaan tuloksia, luodaan lista ja laitetaan se ListViewiin adapterin avulla
                    if (reportHostData != null && list != null) {
                        adapter = new CustomListAdapter(SearchActivity.this, list);
                        listView = (ListView) findViewById(R.id.DeviceList);
                        listView.setAdapter(adapter);

                    }
                    //Asetetaan listviewin itemeille OnClickListeneri, jolla avataan InformationOfResult-activity, joka näyttää valitusta laitteesta lisätietoja
                    try{
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent myIntent = new Intent(SearchActivity.this, InformationOfResult.class);
                                myIntent.putExtra("deviceInformation", position); //Optional parameters
                                SearchActivity.this.startActivity(myIntent);
                                Toast.makeText(SearchActivity.this, list.get(position).toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (NullPointerException e){

                    }


                }
            }
        });



    }
        // Funktio, jota käytetään SearchThreadin käynnistykseen sekä sieltä saatujen tietojen hallinnointiin
    public void StartSearchThread(){
        mysearch = new SearchThread(SearchString,SearchActivity.this);
        mysearch.run();
        FoundData = mysearch.GetAnswer;
        reportHostData = mysearch.GetHostReport;
        list = mysearch.ListOfIPS;
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





    }
