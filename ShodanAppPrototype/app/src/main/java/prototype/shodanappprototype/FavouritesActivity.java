package prototype.shodanappprototype;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fooock.shodan.model.banner.Banner;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    CustomListAdapter adapter;
    CustomListAdapter2 adapter2;
    ArrayList<FoundDevice> ListForFavourites2;
    ListView listView;
    MyMenu menu;
    ArrayList<String> ListForFavourites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        menu = new MyMenu(this);
        // Jos lista on olemassa niin se asetetaan ListViewiin
        if ( DataHandler.getInstance().getFavouriteslist() != null){
            adapter2 = new CustomListAdapter2(FavouritesActivity.this , DataHandler.getInstance().getFavouriteslist());
            listView = (ListView) findViewById(R.id.ListViewForFavourites);

            listView.setAdapter(adapter2);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(FavouritesActivity.this, InformationOfResult.class);
                    Bundle extras = new Bundle();
                    // kerrotaan informationOfResult-activitylle, että tullaan favourite-activitystä
                    extras.putString("EXTRAS_Activity","Favourites");
                    extras.putInt("EXTRA_index",position);
                    myIntent.putExtras(extras);
                    FavouritesActivity.this.startActivity(myIntent);
            //        Toast.makeText(FavouritesActivity.this, ListForFavourites.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }






    }

    // Lisätään menu activityyn
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
