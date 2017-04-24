package crawlers;

import org.dtools.ini.*;

import java.io.*;

public class Wlan {

    public Wlan(){
        //读取wlan名
        File src=new File("src/inis/wlanname.ini");
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(src), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String content;
            while((content = bufferedReader.readLine() )!=null){
                if(content.startsWith("name=")){
                    String wlanname=content.substring(5, content.length());
                    connectToWlan(wlanname);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectToWlan(String Wlanname){
        try {
            String cmdStr = "cmd /c netsh wlan connect name="+Wlanname;
            Runtime.getRuntime().exec(cmdStr);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Wlan();
    }
}
