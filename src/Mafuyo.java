import crawlers.Moodle;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mafuyo extends JFrame{

    String imagepath;

    Maindialog MafuyoNoHanashi;

    Simpledialog Mafuyowait;

    Moodledialog MafuyoMoodle;

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
        return sourceImg.getHeight();
    }

    public Mafuyo() {
        imagepath="src/imgs/mafuyo.png";
        this.setSize(getWidth(imagepath), getHeight(imagepath));
        this.setLocation(1000,500);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        MouseEventListener mouseListener = new MouseEventListener(this);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setType(Type.UTILITY);
        tray();
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        while(true){
            waitms(4000);
            imagepath="src/imgs/mafuyo2.png";
            this.repaint();
            waitms(300);
            imagepath="src/imgs/mafuyo.png";
            this.repaint();
        }
    }

    //设置托盘程序
    public void tray(){
        Mafuyo frame=this;
        SystemTray tray = SystemTray.getSystemTray();
        ImageIcon icon = new ImageIcon("src/imgs/icon.png");

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
        String path="src/imgs/mafuyo.png";
        int width=getWidth(path);
        int height=getHeight(path);
        ImageIcon ii1 = new ImageIcon(imagepath);
        g.drawImage(ii1.getImage(), 0, 0, width, height,null);
    }

    class MouseEventListener implements MouseInputListener {

        Point origin;
        //鼠标拖拽想要移动的目标组件
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
                if(MafuyoNoHanashi==null){
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
                            }
                        }
                    });
                    dtimer.start();
                }
            }
        }

        /**
         * 记录鼠标按下时的点
         */
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
            new Moodle();
        }
    }

    //过滤moodle的新消息
    public String getMoodleNews(){
        String result="";
        String html=readFile("src/html/moodle.html");
        //匹配作业
        result+="需要留意的作业有   ";
        String shtml=html.substring(html.indexOf("course_list"),html.indexOf("id=\"sb-2\""));
        Pattern homeworkPattern=Pattern.compile(".*>(.*?)</a></h.*?留意");
        Matcher homeworkMatcher=homeworkPattern.matcher(shtml);
        while(homeworkMatcher.find()) {
            String tmp=homeworkMatcher.group(1);
            result+=tmp;
            int num=11-tmp.length()%11;
            for(int i=0;i<num;i++){
                result+=" ";
            }
        }
        return result;
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
        }
    }

    //打开网页
    public void OpenMoodle(){
        if(MafuyoNoHanashi!=null){
            MafuyoNoHanashi.dispose();
        }
        Mafuyo frame=this;
        Point p = this.getLocation();
        imagepath="src/imgs/mafuyo1.png";
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
                        wtimer.stop();
                        wtimer=null;
                        Mafuyowait.dispose();
                        Mafuyowait=null;
                        if(MafuyoMoodle==null){
                            MafuyoMoodle=new Moodledialog(p.x+140,p.y-190, getMoodleNews(), frame);
                            MafuyoMoodle.setAlwaysOnTop(true);
                            imagepath="src/imgs/mafuyo.png";
                            frame.repaint();
                        }
                    }
                }
            });
            wtimer.start();
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
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        new Mafuyo();
    }
}
