import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.io.IOUtils;
import org.dtools.ini.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Moodle {

    public Moodle(Mafuyo frame){
        String url="http://218.94.159.99/login/index.php";
        WebClient MoodleClient=new WebClient();
        MoodleClient.getOptions().setCssEnabled(true);
        MoodleClient.getOptions().setJavaScriptEnabled(true);
        Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        //读取账户和密码
        IniFile ini=new BasicIniFile();
        IniFileReader inir=new IniFileReader(ini, new File("inis/moodle.ini"));
        try {
            inir.read();
            IniSection iniSection=ini.getSection(0);
            IniItem iniItem=iniSection.getItem("username");
            String username=iniItem.getValue();
            iniItem=iniSection.getItem("password");
            String password=iniItem.getValue();
            ini=null;
            inir=null;
            System.gc();
            HtmlPage LoginPage=MoodleClient.getPage(url);
            HtmlElement usernameEle = LoginPage.getElementByName("username");
            HtmlElement passwordEle = (HtmlElement)LoginPage.getElementById("password");
            usernameEle.focus();
            usernameEle.type(username);
            passwordEle.focus();
            passwordEle.type(password);
            HtmlElement button=(HtmlElement)LoginPage.getElementById("loginbtn");
            HtmlPage MoodlePage=button.click();
            try {
                saveFile(MoodlePage,"html/moodle.html");
            } catch (Exception e) {
                frame.HandleException(e);
            }
        } catch (IOException e) {
            frame.HandleException(e);
        }
        MoodleClient=null;
        System.gc();
    }

    public static void saveFile(HtmlPage page, String file) throws Exception {
        InputStream is = page.getWebResponse().getContentAsStream();
        FileOutputStream output = new FileOutputStream(file);
        IOUtils.copy(is, output);
        output.close();
        is=null;
        output=null;
        System.gc();
    }
}
