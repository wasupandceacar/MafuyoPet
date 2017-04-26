import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class Exedialog extends JDialog {

    String imagepath;

    String hanashi;

    List<String> path;
    List<String> name;
    List<JButton> button;

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

    public Exedialog(int x, int y, String hanashi, Mafuyo frame){
        this.hanashi=hanashi;
        imagepath="imgs/dialog2.png";
        this.setSize(getWidth(imagepath), getHeight(imagepath));
        this.setLocation(x, y);
        this.setUndecorated(true);
        this.setLayout(null);
        readExe();
        addButton(frame);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setVisible(true);
        buttonRepaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width=getWidth(imagepath);
        int height=getHeight(imagepath);
        ImageIcon ii1 = new ImageIcon(imagepath);
        g.drawImage(ii1.getImage(), 0, 0, width, height,null);
        drawLineString(g);
        ii1=null;
        System.gc();
    }

    //换行显示对话
    public void drawLineString(Graphics g){
        int length=hanashi.length();
        int row=(length-1)/11;
        for(int i=0;i<row;i++){
            g.drawString(hanashi.substring(11*i,11*i+11), 29, 38+15*i);
        }
        g.drawString(hanashi.substring(11*row,length), 29, 38+15*row);
    }

    public void readExe(){
        //读取exe及路径
        File src=new File("inis/exe.ini");
        path=new ArrayList<>();
        name=new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(src), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            String content;
            while((content = bufferedReader.readLine())!=null){
                if(content.startsWith("directory=")){
                    path.add(content.substring(10));
                }
                if(content.startsWith("name=")){
                    name.add(content.substring(5));
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

    public void addButton(Mafuyo frame){
        button=new ArrayList<>();
        int len=path.size();
        for(int i=0;i<len;i++){
            String tmpname=name.get(i);
            JButton tmpbu=new JButton(tmpname.substring(0, tmpname.length()-4));
            tmpbu.setBounds(29,46+20*i,100,20);
            tmpbu.setBorder(null);
            tmpbu.setHorizontalAlignment(SwingConstants.LEFT);
            tmpbu.setBackground(Color.white);
            tmpbu.setFocusPainted(false);
            tmpbu.setFont(this.getFont());
            int finalI = i;
            tmpbu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.OpenExe(path.get(finalI)+"/"+tmpname);
                }
            });
            this.add(tmpbu);
            button.add(tmpbu);
        }
        JButton back=new JButton("返回");
        back.setBounds(29,46+20*len,100,20);
        back.setBorder(null);
        back.setHorizontalAlignment(SwingConstants.LEFT);
        back.setBackground(Color.white);
        back.setFocusPainted(false);
        back.setFont(this.getFont());
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.Back();
            }
        });
        this.add(back);
        button.add(back);
    }

    public void buttonRepaint(){
        int len=button.size();
        for(int i=0;i<len;i++){
            button.get(i).repaint();
        }
    }
}
