package prototype.shodanappprototype;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class CustomListAdapter2 extends ArrayAdapter<FoundDevice> {

    private Context context;
    private List<FoundDevice> foundDevices;


    public CustomListAdapter2(Context context, List<FoundDevice> foundDevices) {
        super(context, R.layout.founddevice_list_item, foundDevices);
        this.context = context;
        this.foundDevices = foundDevices;

    }
    // listassa näytetään ip ja palvelin
    private class ViewHolder {
        TextView deviceIp;
        TextView deviceServer;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.founddevice_list_item, null);
            holder = new ViewHolder();
            holder.deviceIp = (TextView) convertView
                    .findViewById(R.id.txt_ip);
            holder.deviceServer = (TextView) convertView
                    .findViewById(R.id.txt_server);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // asetetaan arvot
        holder.deviceIp.setText(foundDevices.get(position).getIp());
        holder.deviceServer.setText( foundDevices.get(position).getServer());



        return convertView;
    }


    }



