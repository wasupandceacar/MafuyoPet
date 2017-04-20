import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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

    public static void main(String[] args) {
        new Mafuyo();
    }
}
