package prototype.shodanappprototype;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;

import java.util.Locale;

public class MyMenu {
    Activity current_activity;
    private static String ln;
    private String finnish = "fi";
    private String swedish = "sv";
    private String english = "en";

    public MyMenu(Activity activity) {
        current_activity = activity;
        // Asetetaan kieli defaultiksi, jos sitä ei ole asetettu vielä
        if(ln == null)ln = Locale.getDefault().getLanguage();
    }





    public void SelectItem(int id) {
        switch (id) {
            case (R.id.en_language):
                Locale locale = new Locale(english);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                current_activity.getBaseContext().getResources().updateConfiguration(config,
                        current_activity.getBaseContext().getResources().getDisplayMetrics());
                ln = english;
                current_activity.recreate();
                break;

            case (R.id.fi_language):
                locale = new Locale(finnish);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                current_activity.getBaseContext().getResources().updateConfiguration(config,
                        current_activity.getBaseContext().getResources().getDisplayMetrics());
                ln = finnish;
                current_activity.recreate();
                break;

            case (R.id.sv_language):
                locale = new Locale(swedish);
                Locale.setDefault(locale);
                config = new Configuration();
                config.locale = locale;
                current_activity.getBaseContext().getResources().updateConfiguration(config,
                        current_activity.getBaseContext().getResources().getDisplayMetrics());
                ln = swedish;
                current_activity.recreate();
                break;
            case (R.id.LogOut):
              Intent intent1 = new Intent(current_activity, LoginActivity.class);
               if(AppConstants.KEY_QR_CODE != null) {
                   AppConstants.KEY_QR_CODE = null;
               }

                current_activity.startActivity(intent1);

                break;
            case (R.id.favourites):
                intent1 = new Intent(current_activity, FavouritesActivity.class);
                current_activity.startActivity(intent1);
                break;

            case (R.id.Search):
                intent1 = new Intent(current_activity, SearchActivity.class);
                current_activity.startActivity(intent1);
                break;

            case (R.id.main):
                intent1 = new Intent(current_activity, MainActivity.class);
                current_activity.startActivity(intent1);
                break;
           /* case (R.id.Settings):
                intent1 = new Intent(current_activity,SettingsActivity.class);
                current_activity.startActivity(intent1);
                break;
                */
            case (R.id.About):
                intent1 = new Intent(current_activity, AboutActivity.class);
                current_activity.startActivity(intent1);
                break;

        }

    }
}
