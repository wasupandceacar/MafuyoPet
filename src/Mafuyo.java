import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Mafuyo extends JFrame{

    String imagepath;

    Mdialog MafuyoNoHanashi;

    //不关注对话的计时器
    Timer dtimer;

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
                    MafuyoNoHanashi=new Mdialog(p.x+145,p.y-90,"前辈，怎么了？");
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
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

    }

    public static void main(String[] args) {
        new Mafuyo();
    }
}
