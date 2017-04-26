package crawlers;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.dtools.ini.*;

import java.io.*;
import java.util.*;

public class Wlan {

    public Wlan(){
        connectToNet();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LoginTo();
    }

    public void LoginTo(){
        HttpClient client = new DefaultHttpClient();
        //读取账户和密码
        IniFile ini=new BasicIniFile();
        IniFileReader inir=new IniFileReader(ini, new File("inis/wlan.ini"));
        try {
            inir.read();
            IniSection iniSection=ini.getSection(1);
            IniItem iniItem=iniSection.getItem("username");
            String username=iniItem.getValue();
            iniItem=iniSection.getItem("password");
            String password=iniItem.getValue();
            ini=null;
            inir=null;
            System.gc();
            HttpPost post = new HttpPost("http://p.nju.edu.cn/portal_io/login");
            List<NameValuePair> formParams = new ArrayList<>();
            formParams.add(new BasicNameValuePair("username", username));
            formParams.add(new BasicNameValuePair("password", password));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
            post.setEntity(entity);
            client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client=null;
        System.gc();
    }

    public void connectToNet(){
        //读取wlan名
        File src=new File("inis/wlan.ini");
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
            isr=null;
            bufferedReader=null;
            content=null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        src=null;
        System.gc();
    }

    public void connectToWlan(String Wlanname){
        try {
            String cmdStr = "cmd /c netsh wlan connect name="+Wlanname;
            Runtime.getRuntime().exec(cmdStr);
            cmdStr=null;
            System.gc();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
