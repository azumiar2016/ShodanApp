package prototype.shodanappprototype;
import java.util.ArrayList;

public class FoundDevice {
    // jaetaan tietoja vähän erilleen, nämä tiedot käytetään InformationOfResult-activityssä
    private String Country,Server,HostName,Ip;
    // Nämä tiedot ExtraInformationOfDevice-activityssä
    private String html,uptime,product,version,port,link,os,timestamp,info;









    public FoundDevice(String Country, String Server, String HostName, String Ip,
                       String html, String uptime, String product, String version, String port, String link, String os, String timestamp, String info
    ){
        //basic information for InformationOfResult-activityyn
        this.Country = Country;
        this.Server = Server;
        this.HostName = HostName;
        this.Ip = Ip;
        //Extra information for ExtraInformationOfResult-activityyn
        this.html = html;
        this.uptime = uptime;
        this.product = product;
        this.version = version;
        this.port = port;
        this.link = link;
        this.os = os;
        this.timestamp = timestamp;
        this.info = info;


    }



    public void setHostName(String Name){
        this.HostName = Name;
    }
    public void setIp(String Ip){
        this.Ip = Ip;
    }
    public void setCountry(String Country){
        this.Country = Country;
    }




    public String getHostName(){
        return HostName;
    }

    public String getIp(){
        return Ip;
    }

    public String getCountry(){
        return Country;
    }

    public String getServer(){
        return Server;}

    public String getHtml() {
        return html;
    }

    public String getUptime() {
        return uptime;
    }

    public String getProduct() {
        return product;
    }

    public String getVersion() {
        return version;
    }

    public String getPort() {
        return port;
    }

    public String getLink() {
        return link;
    }

    public String getOs() {
        return os;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getInfo() {
        return info;
    }





}
