package prototype.shodanappprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ExtraInformationOfDevice extends AppCompatActivity {
    TextView html,uptime,product,version,port,link,os,timestamp,info;
    int i;
    String PreviousActivity;
    MyMenu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_information_of_device);
        Intent intent = getIntent();
        menu = new MyMenu(this);
        Bundle extras = intent.getExtras();
        i = extras.getInt("EXTRA_index",0);
        PreviousActivity = extras.getString("EXTRAS_Activity");
        setViews();
        setData();

    }

    public void setViews(){
        html = findViewById(R.id.html_data);
        uptime = findViewById(R.id.uptime_data);
        product = findViewById(R.id.product_data);
        version = findViewById(R.id.version_data);
        port = findViewById(R.id.port_data);
        link = findViewById(R.id.link_data);
        os = findViewById(R.id.os_data);
        timestamp = findViewById(R.id.timestamp_data);
        info = findViewById(R.id.Information_box);

    }
    // Paljon erilaista tietoa löydetystä laitteesta
    // täällä voitais hyödyntää ihan vaan founddevice-listaa banner listan sijaan
    public void setData() {
        if (PreviousActivity.equals("Search")){
            html.setText(DataHandler.getInstance().getList().get(i).getHtml());
            //  uptime.setText(DataHandler.getInstance().getList().get(i).getUptime());
            product.setText(DataHandler.getInstance().getList().get(i).getProduct());
            version.setText(DataHandler.getInstance().getList().get(i).getVersion());
            //  port.setText(DataHandler.getInstance().getList().get(i).getPort());
            link.setText(DataHandler.getInstance().getList().get(i).getLink());
            os.setText(DataHandler.getInstance().getList().get(i).getOs());
            timestamp.setText(DataHandler.getInstance().getList().get(i).getTimestamp());
            info.setText(DataHandler.getInstance().getList().get(i).getInfo());

        }else if(PreviousActivity.equals("Favourites")){

            html.setText(DataHandler.getInstance().getFavouriteslist().get(i).getHtml());
            //  uptime.setText(DataHandler.getInstance().getFavouriteslist().get(i).getHtml());
            product.setText(DataHandler.getInstance().getFavouriteslist().get(i).getProduct());
            version.setText(DataHandler.getInstance().getFavouriteslist().get(i).getVersion());
            //  port.setText(DataHandler.getInstance().getFavouriteslist().get(i).getHtml());
            link.setText(DataHandler.getInstance().getFavouriteslist().get(i).getLink());
            os.setText(DataHandler.getInstance().getFavouriteslist().get(i).getOs());
            timestamp.setText(DataHandler.getInstance().getFavouriteslist().get(i).getTimestamp());
            info.setText(DataHandler.getInstance().getFavouriteslist().get(i).getInfo());

        }

    }

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
