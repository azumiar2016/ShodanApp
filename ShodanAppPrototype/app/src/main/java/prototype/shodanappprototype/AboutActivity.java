package prototype.shodanappprototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

//Sisältää ainoastaan About-tiedot ohjelmasta
public class AboutActivity extends AppCompatActivity {
    MyMenu menu;
    Button GettingStarted_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        menu = new MyMenu(this);
        GettingStarted_Button = (Button)findViewById(R.id.GettingStarted_button);


        GettingStarted_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(AboutActivity.this, GettingStartedActivity.class);
                Bundle extras = new Bundle();
                // kerrotaan GettingStarted-activitylle, että tullaan About-activitystä
                extras.putString("EXTRAS_Activity","About");
                myIntent.putExtras(extras);
                AboutActivity.this.startActivity(myIntent);

            }
        });
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
        // login/logout kuva muuttuu tilojen mukaan
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
