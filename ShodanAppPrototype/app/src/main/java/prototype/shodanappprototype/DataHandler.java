package prototype.shodanappprototype;



import com.fooock.shodan.model.banner.Banner;


import java.util.ArrayList;
import java.util.Map;

public class DataHandler {
    // saattaa sisältää jotain ylimääräistä
    private static DataHandler handler;
    private ArrayList<FoundDevice> favouriteslist;
    private ArrayList<Banner> list;
    private ArrayList<String> IpList;
    private ArrayList<FoundDevice> FoundDevices;


    private DataHandler() {
    }

    public static DataHandler getInstance() {
        if (handler == null)
            handler = new DataHandler();
        return handler;
    }

    public ArrayList<FoundDevice> getFavouriteslist() {
        return favouriteslist;
    }

    public void setFavouriteslist(ArrayList<FoundDevice> Arraylist) {
        this.favouriteslist = Arraylist;
    }

    public void addtofavourites(FoundDevice device) {
        this.favouriteslist.add(device);
    }

    // Banner-lista sisältää kaiken tiedon laitteista mitä shodan-api antaa
    public ArrayList<Banner> getList() {
        return list;
    }

    public void setList(ArrayList<Banner> Arraylist) {
        this.list = Arraylist;
    }

    // tarkista onko tarpeellinen
    public ArrayList<String> getIpList() {
        return IpList;
    }

    public void setIpList(ArrayList<String> ipList) {
        IpList = ipList;
    }

    public void addToIpList(String s) {
        IpList.add(s);
    }

    // ***************************
    // Sisältää kaikki laitteet, jotka löydetään haulla
    public ArrayList<FoundDevice> getFoundDevices() {
        return FoundDevices;
    }

    public void setFoundDevices(ArrayList<FoundDevice> Arraylist) {
        this.FoundDevices = Arraylist;
    }

    public void addtoFoundDevices(FoundDevice device) {
        this.FoundDevices.add(device);
    }


}