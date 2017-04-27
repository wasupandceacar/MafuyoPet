import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import crawlers.Moodle;
import crawlers.Wlan;
import org.dtools.ini.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Mafuyo extends JFrame{

    ImageIcon ii;
    ImageIcon ii0;
    ImageIcon ii1;
    ImageIcon ii2;

    AudioStream MafuyoNoKoe;

    int width;
    int height;

    Maindialog MafuyoNoHanashi;

    Simpledialog Mafuyowait;

    Moodledialog MafuyoMoodle;

    Exedialog MafuyoExe;

    Moodle moodle;

    Wlan wlan;

    //是否播放声音flag
    boolean koeflag;

    //不关注对话的计时器
    Timer dtimer;

    //等待的计时器
    Timer wtimer;

    //等待事件
    public void waitms(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getWidth(String path){
        File picture = new File(path);
        BufferedImage sourceImg = null;
        try {
            sourceImg = ImageIO.read(new FileInputStream(picture));
        } catch (IOException e) {
            e.printStackTrace();
        }
        picture=null;
        System.gc();
        return sourceImg.getWidth();
    }

    public int getHeight(String path){
        File picture = new File(path);
        BufferedImage sourceImg = null;
        try {
            sourceImg = ImageIO.read(new FileInputStream(picture));
        } catch (IOException e) {
            e.printStackTrace();
        }
        picture=null;
        System.gc();
        return sourceImg.getHeight();
    }

    public Mafuyo() {
        ii0 = new ImageIcon("imgs/mafuyo.png");
        ii1 = new ImageIcon("imgs/mafuyo1.png");
        ii2 = new ImageIcon("imgs/mafuyo2.png");
        ii=ii0;
        width=getWidth("imgs/mafuyo.png");
        height=getHeight("imgs/mafuyo.png");
        this.setSize(width, height);
        this.setLocation(1000,500);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        MouseEventListener mouseListener = new MouseEventListener(this);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setType(Type.UTILITY);
        tray();
        LoadConfig();
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        while(true){
            waitms(4000);
            ii=ii2;
            this.repaint();
            waitms(300);
            ii=ii0;
            this.repaint();
        }
    }

    //设置托盘程序
    public void tray(){
        Mafuyo frame=this;
        SystemTray tray = SystemTray.getSystemTray();
        ImageIcon icon = new ImageIcon("imgs/icon.png");

        PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
        final MenuItem exit = new MenuItem("退出");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        pop.add(exit);
        TrayIcon trayIcon = new TrayIcon(icon.getImage(),"真冬", pop);//实例化托盘图标
        trayIcon.setImageAutoSize(true);
        //为托盘图标监听点击事件
        trayIcon.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if(e.getClickCount()== 1)//鼠标双击图标
                {
                    frame.setExtendedState(JFrame.NORMAL);//设置状态为正常
                    frame.setVisible(true);//显示主窗体
                }
            }
        });
        try
        {
            tray.add(trayIcon); // 将托盘图标添加到系统的托盘实例中
        }
        catch(AWTException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(ii.getImage(), 0, 0, width, height,null);
    }

    class MouseEventListener implements MouseInputListener {

        Point origin;

        Mafuyo frame;
        PopupMenu Menu;
        MenuItem exit;

        public MouseEventListener(Mafuyo frame) {
            this.frame = frame;
            origin = new Point();
            Menu=new PopupMenu();
            exit=new MenuItem("退出");
            Menu.add(exit);
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            this.frame.add(Menu);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON3){
                Menu.show(frame,e.getX(),e.getY());
            }
            if(e.getButton()==MouseEvent.BUTTON1){
                if(MafuyoMoodle==null&&Mafuyowait==null&&MafuyoExe==null){
                    if(MafuyoNoHanashi==null){
                        PlayKoe("cv/senbai.wav");
                        Point p = this.frame.getLocation();
                        MafuyoNoHanashi=new Maindialog(p.x+145,p.y-90,"前辈，怎么了？", frame);
                        MafuyoNoHanashi.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent e) {

                            }

                            @Override
                            public void mousePressed(MouseEvent e) {

                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {

                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                if(dtimer!=null){
                                    dtimer.stop();
                                    dtimer=null;
                                }
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                if(dtimer==null){
                                    dtimer=new Timer(6000, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if(MafuyoNoHanashi!=null){
                                                MafuyoNoHanashi.dispose();
                                                MafuyoNoHanashi=null;
                                            }
                                        }
                                    });
                                    dtimer.start();
                                }
                            }
                        });
                        MafuyoNoHanashi.setAlwaysOnTop(true);
                    }else{
                        PlayKoe("cv/ichiban.wav");
                    }
                    if(dtimer!=null){
                        dtimer.stop();
                        dtimer=null;
                    }
                    if(dtimer==null){
                        dtimer=new Timer(6000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(MafuyoNoHanashi!=null){
                                    MafuyoNoHanashi.dispose();
                                    MafuyoNoHanashi=null;
                                    System.gc();
                                }
                            }
                        });
                        dtimer.start();
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            origin.x = e.getX();
            origin.y = e.getY();
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point p = this.frame.getLocation();
            this.frame.setLocation(
                    p.x + (e.getX() - origin.x),
                    p.y + (e.getY() - origin.y));
            if(MafuyoNoHanashi!=null){
                MafuyoNoHanashi.setLocation(
                        p.x + (e.getX() - origin.x) + 145,
                        p.y + (e.getY() - origin.y) -90);
            }
            if(Mafuyowait!=null){
                Mafuyowait.setLocation(
                        p.x + (e.getX() - origin.x) + 145,
                        p.y + (e.getY() - origin.y) -90);
            }
            if(MafuyoMoodle!=null){
                MafuyoMoodle.setLocation(
                        p.x + (e.getX() - origin.x) + 140,
                        p.y + (e.getY() - origin.y) -190);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

    }

    //抓取moodle的线程
    class MoodleThread extends Thread {
        @Override
        public void run() {
            moodle=new Moodle();
        }
    }

    //连接网络线程
    class WlanThread extends Thread {
        @Override
        public void run() {
            wlan=new Wlan();
        }
    }

    //过滤moodle的新消息
    public String getMoodleNews(){
        String html=readFile("html/moodle.html");
        //匹配作业
        String homework="需要留意的作业有   ";
        String topic="讨论区有新帖子    ";
        String shtml=html.substring(html.indexOf("course_list"),html.indexOf("id=\"sb-2\""));
        boolean hflag=true;
        boolean tflag=true;
        while(shtml.contains("course_title")) {
            int start=shtml.indexOf("course_title");
            int end=shtml.indexOf("</div></div><div class=\"box flush\">");
            String check=shtml.substring(start, end);
            if(check.contains("您有需要留意的作业")){
                int ssend=check.indexOf("</a></h2>");
                String tmp=check.substring(start, ssend);
                tmp=tmp.substring(tmp.lastIndexOf(">")+1, tmp.length());
                homework+=tmp;
                int num=11-tmp.length()%11;
                for(int i=0;i<num;i++){
                    homework+=" ";
                }
                hflag=false;
            }
            if(check.contains("新讨论区帖子")){
                int ssend=check.indexOf("</a></h2>");
                String tmp=check.substring(start, ssend);
                tmp=tmp.substring(tmp.lastIndexOf(">")+1, tmp.length());
                topic+=tmp;
                int num=11-tmp.length()%11;
                for(int i=0;i<num;i++){
                    topic+=" ";
                }
                tflag=false;
            }
            shtml=shtml.substring(end+36, shtml.length());
        }
        if(hflag){
            homework+="无          ";
        }
        if(tflag){
            topic+="无          ";
        }
        html=null;
        shtml=null;
        System.gc();
        return homework+"           "+topic;
    }

    public void OpenBrowser(String path){
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(MafuyoMoodle!=null){
            MafuyoMoodle.dispose();
            MafuyoMoodle=null;
            System.gc();
        }
    }

    //打开网页
    public void OpenMoodle(){
        if(MafuyoNoHanashi!=null){
            MafuyoNoHanashi.dispose();
            MafuyoNoHanashi=null;
        }
        Mafuyo frame=this;
        Point p = this.getLocation();
        ii=ii1;
        this.repaint();
        if(Mafuyowait==null){
            Mafuyowait=new Simpledialog(p.x+145,p.y-90,"请稍等哦。");
            Mafuyowait.setAlwaysOnTop(true);
        }
        if(wtimer!=null){
            wtimer.stop();
            wtimer=null;
        }
        MoodleThread mt=new MoodleThread();
        mt.start();
        if(wtimer==null){
            wtimer=new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(mt.getState()== Thread.State.TERMINATED){
                        moodle=null;
                        wtimer.stop();
                        wtimer=null;
                        Mafuyowait.dispose();
                        Mafuyowait=null;
                        System.gc();
                        if(MafuyoMoodle==null){
                            frame.PlayKoe("cv/yokatadesune.wav");
                            MafuyoMoodle=new Moodledialog(p.x+140,p.y-190, getMoodleNews(), frame);
                            MafuyoMoodle.setAlwaysOnTop(true);
                            ii=ii0;
                            frame.repaint();
                        }
                    }
                }
            });
            wtimer.start();
        }
    }

    //自动连接校园网
    public void AutoLoginToWlan(){
        if(MafuyoNoHanashi!=null){
            MafuyoNoHanashi.dispose();
            MafuyoNoHanashi=null;
        }
        Point p = this.getLocation();
        ii=ii1;
        this.repaint();
        if(Mafuyowait==null){
            Mafuyowait=new Simpledialog(p.x+145,p.y-90,"请稍等哦。");
            Mafuyowait.setAlwaysOnTop(true);
        }
        if(wtimer!=null){
            wtimer.stop();
            wtimer=null;
        }
        WlanThread wt=new WlanThread();
        wt.start();
        if(wtimer==null){
            wtimer=new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(wt.getState()== Thread.State.TERMINATED){
                        wlan=null;
                        System.gc();
                        wtimer.stop();
                        wtimer=null;
                        Mafuyowait.dispose();
                        Mafuyowait=null;
                        PlayKoe("cv/yokatadesune.wav");
                        Mafuyowait=new Simpledialog(p.x+145,p.y-90,"已经连接到校园网。  请尽情地使用吧。");
                        Mafuyowait.setAlwaysOnTop(true);
                        wtimer=new Timer(4000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                wtimer.stop();
                                wtimer=null;
                                Mafuyowait.dispose();
                                Mafuyowait = null;
                                System.gc();
                            }
                        });
                        wtimer.start();
                    }
                }
            });
            wtimer.start();
        }
    }

    public void CloseMoodle(){
        if(MafuyoMoodle!=null){
            MafuyoMoodle.dispose();
            MafuyoMoodle=null;
            System.gc();
        }
    }

    //列出exe
    public void ListExe(){
        if(MafuyoNoHanashi!=null){
            MafuyoNoHanashi.dispose();
            MafuyoNoHanashi=null;
        }
        if(MafuyoExe==null){
            Point p = this.getLocation();
            MafuyoExe=new Exedialog(p.x+140,p.y-190, "能使用的exe有", this);
            MafuyoExe.setAlwaysOnTop(true);
        }
    }

    //读取文件
    public String readFile(String path){
        File src=new File(path);
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(src), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String content;
            while((content = bufferedReader.readLine() )!=null){
                stringBuilder.append(content);
            }
            src=null;
            isr=null;
            bufferedReader=null;
            content=null;
            System.gc();
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //打开exe
    public void OpenExe(String path){
        try {
            Runtime.getRuntime().exec(path);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //返回
    public void Back(){
        if(MafuyoMoodle!=null){
            MafuyoMoodle.dispose();
            MafuyoMoodle=null;
            System.gc();
        }
        if(MafuyoExe!=null){
            MafuyoExe.dispose();
            MafuyoExe=null;
            System.gc();
        }
        if(MafuyoNoHanashi==null){
            Point p = this.getLocation();
            MafuyoNoHanashi=new Maindialog(p.x+145,p.y-90,"前辈，怎么了？", this);
            MafuyoNoHanashi.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if(dtimer!=null){
                        dtimer.stop();
                        dtimer=null;
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(dtimer==null){
                        dtimer=new Timer(6000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(MafuyoNoHanashi!=null){
                                    MafuyoNoHanashi.dispose();
                                    MafuyoNoHanashi=null;
                                }
                            }
                        });
                        dtimer.start();
                    }
                }
            });
            MafuyoNoHanashi.setAlwaysOnTop(true);
        }
    }

    //播放声音
    public void PlayKoe(String path){
        if(koeflag){
            try {
                FileInputStream koe=new FileInputStream(path);
                MafuyoNoKoe=new AudioStream(koe);
                AudioPlayer.player.start(MafuyoNoKoe);
                koe=null;
                MafuyoNoKoe=null;
                System.gc();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    //载入设置
    public void LoadConfig(){
        IniFile ini=new BasicIniFile();
        IniFileReader inir=new IniFileReader(ini, new File("inis/config.ini"));
        try {
            inir.read();
            IniSection iniSection=ini.getSection(0);
            IniItem iniItem=iniSection.getItem("flag");
            String flag=iniItem.getValue();
            ini=null;
            inir=null;
            System.gc();
            if(flag.equals("1")){
                koeflag=true;
            }else if(flag.equals("0")){
                koeflag=false;
            }else{

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("Mafuyo");
        new Mafuyo();
    }
}
