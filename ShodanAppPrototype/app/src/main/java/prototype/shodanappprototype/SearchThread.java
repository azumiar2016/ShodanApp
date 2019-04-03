package prototype.shodanappprototype;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.fooock.shodan.model.banner.Banner;
import com.fooock.shodan.model.host.HostReport;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
//This list will contain found devices


public class SearchThread extends Thread {

    ArrayList<String> ListOfIPS;
    ArrayList<FoundDevice> FoundDevices;
    ArrayList<Banner> DeviceList;  // Banner-luokasta löytyy tarvittavat tiedot
    private FoundDevice foundDevice;
    private String newText;
    private Activity m_activity;
    HostReport GetHostReport;
    private ConnectionHandler connectionHandler;
    private boolean deviceSearchMade = false;
    String ip,country,server,name,html,uptime,product,version,port,link,os,timestamp,info;



    public int GetAnswer;

    public SearchThread(String text, Activity activity) {

        newText = text;
        m_activity = activity;

        //First search devices, then put this true, then use search bar as "Search from results" with filter style, then if done search again make it as deviceSearch


    }
    // Käynnistetään Säie ja varmistetaan vielä, että hakuun ei ole laitettu tyhjää merkkijonoa
    public void run() {
        if (!deviceSearchMade && newText.length() > 0) {
            //Käytetään ConnectionHandleriä, jolla otetaan yhteys ShodanApiin
            connectionHandler = new ConnectionHandler(newText, m_activity);
            GetAnswer = connectionHandler.ConnectToShodanFacetReport();
            GetHostReport = connectionHandler.ConnectToShodanHostReport();
            CreateListDeviceList();


        } /*else {
            //Tarkoitus käyttää hakujen filtteröintiin, vielä tekemättä
            if (newText.length() > 1) {

                //katkaise hakumerkkijono valilyöntien kohdalta
                // ja muodosta taulukko

                String searchArray[] = newText.split("\\s");


                //reset array list


                //   ArrayOfDevices = new ArrayList<String>();

                //loop all devices
                for (int i = 0; i < DeviceList.size(); i++) {
                    try {

                        //get single article object
                        //  foundDevice.toString() = ArrayOfDevices.get(i);

                        //get article title
                        //note: change this string to lower case to avoid case sensitive issue
                       String title = DeviceList.get(i).getIpStr();

                        boolean trigger = true;

                        //if title contains the search string...
                        //note: change this string to lower case too
                        for (int i2 = 0; i2 < searchArray.length; i2++) {
                            if (title.contains(searchArray[i2].toLowerCase()) && trigger == true) {
                                trigger = true;

                            } else {
                                trigger = false;
                            }
                        }
                        if (trigger == true) {
                        //    ArrayOfDevices.add(foundDevice);
                        }

                    } catch (final Exception e) {
                        Log.e("DEBUG", "Json parsing error: " + e.getMessage());
                    }
                }


            }

        }*/

    }

    public int ReturnAnswer() {
        return GetAnswer;
    }
    //Luodaan tarvittavia listoja
    public void CreateListDeviceList() {
    //    ArrayOfDevices = new ArrayList<>();
    //    Temp = new ArrayList<>();
        ListOfIPS = new ArrayList<>();
        DeviceList = new ArrayList<>();
        FoundDevices = new ArrayList<FoundDevice>();

        int i = 0;
        try {
            if (GetHostReport.getBanners() != null) {

                while (i < GetHostReport.getBanners().size()) {
                    DeviceList.add(GetHostReport.getBanners().get(i));
                    setFoundDevices(i);

                 //   index = i;
                   // foundDevice = new FoundDevice(index); // tämä kesken, tehdään samalla tavalla kuin ip-osoite

                    ListOfIPS.add(DeviceList.get(i).getIpStr());
                    DataHandler.getInstance().setList(DeviceList);
                    i++;
                }

            }
        } catch (NullPointerException e){

        }

    }

    public void setFoundDevices(int i){
        // InformationOfDevice-activityyn ja listan luontiin SearchActivityssä
        country = DeviceList.get(i).getLocation().toString();
        server = DeviceList.get(i).getIsp();
        name = DeviceList.get(i).getTitle();
        ip = DeviceList.get(i).getIpStr();
        // nämä menevät extrainformation activityyn, vähän rajattu
        html = DeviceList.get(i).getHtml();
        uptime = String.valueOf(DeviceList.get(i).getUptime());
        product = DeviceList.get(i).getProduct();
        version = DeviceList.get(i).getVersion();
        port = String.valueOf(DeviceList.get(i).getPort());
        link = DeviceList.get(i).getLink();
        os = DeviceList.get(i).getOs();
        timestamp = DeviceList.get(i).getTimestamp();
        info = DeviceList.get(i).getInfo();
        // tehdään foundDevice
        foundDevice = new FoundDevice(country,server,name,ip,html,uptime,product,version,port,link,os,timestamp,info);

        FoundDevices.add(foundDevice);
        // laitetaan lista myös datahandleriin, joka helpottaa myöhemmin sen käyttöä eri activityissä
        DataHandler.getInstance().setFoundDevices(FoundDevices);

    }
}






