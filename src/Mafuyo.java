import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Mafuyo extends JFrame{

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
        String path="src/imgs/mafuyo.png";
        this.setSize(getWidth(path), getHeight(path));
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        MouseEventListener mouseListener = new MouseEventListener(this);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g; //强转成2D
        String path="src/imgs/mafuyo.png";
        ImageIcon ii1 = new ImageIcon(path);
        g2.drawImage(ii1.getImage(), 0, 0, getWidth(path),getHeight(path),null);
    }

    class MouseEventListener implements MouseInputListener {

        Point origin;
        //鼠标拖拽想要移动的目标组件
        Mafuyo frame;

        public MouseEventListener(Mafuyo frame) {
            this.frame = frame;
            origin = new Point();
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        /**
         * 记录鼠标按下时的点
         */
        @Override
        public void mousePressed(MouseEvent e) {
            origin.x = e.getX();
            origin.y = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {}

        /**
         * 鼠标移进标题栏时，设置鼠标图标为移动图标
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }

        /**
         * 鼠标移出标题栏时，设置鼠标图标为默认指针
         */
        @Override
        public void mouseExited(MouseEvent e) {
            this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        /**
         * 鼠标在标题栏拖拽时，设置窗口的坐标位置
         * 窗口新的坐标位置 = 移动前坐标位置+（鼠标指针当前坐标-鼠标按下时指针的位置）
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            Point p = this.frame.getLocation();
            this.frame.setLocation(
                    p.x + (e.getX() - origin.x),
                    p.y + (e.getY() - origin.y));
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

    }

    public static void main(String[] args) {
        new Mafuyo();
    }
}