import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Mdialog extends JDialog{

    String imagepath;

    String hanashi;

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

    public Mdialog(int x, int y, String hanashi, Mafuyo main){
        this.hanashi=hanashi;
        imagepath="src/imgs/dialog1.png";
        this.setSize(getWidth(imagepath), getHeight(imagepath));
        this.setLocation(x, y);
        this.setUndecorated(true);
        this.setLayout(null);
        JButton mail=new JButton("查看邮箱");
        mail.setBounds(20,46,56,20);
        mail.setBorder(null);
        mail.setBackground(Color.white);
        mail.setFocusPainted(false);
        mail.setFont(this.getFont());
        JButton mailconf=new JButton("登陆邮箱");
        mailconf.setBounds(82,46,56,20);
        mailconf.setBorder(null);
        mailconf.setBackground(Color.white);
        mailconf.setFocusPainted(false);
        mailconf.setFont(this.getFont());
        this.add(mail);
        this.add(mailconf);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setVisible(true);
        mail.repaint();
        mailconf.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width=getWidth(imagepath);
        int height=getHeight(imagepath);
        ImageIcon ii1 = new ImageIcon(imagepath);
        g.drawImage(ii1.getImage(), 0, 0, width, height,null);
        drawLineString(g);
    }

    //换行显示对话
    public void drawLineString(Graphics g){
        int length=hanashi.length();
        int row=(length-1)/11;
        for(int i=0;i<row;i++){
            g.drawString(hanashi.substring(11*i,11*i+11), 24, 38+15*i);
        }
        g.drawString(hanashi.substring(11*row,length), 24, 38+15*row);
    }
}
