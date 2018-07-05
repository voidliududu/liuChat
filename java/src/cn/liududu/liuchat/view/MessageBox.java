package cn.liududu.liuchat.view;

import cn.liududu.liuchat.common.Config;
import cn.liududu.liuchat.common.Theme;
import cn.liududu.liuchat.entity.Message;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

public class MessageBox extends JPanel {
    private String from;
    private String text;
    private Date date;
    private int type;
    public static final int TEXT = 0;
    public static final int IMG = 1;

    public MessageBox(Message m, boolean isself) {
        super();
        this.from = m.getFrom();
        this.text = m.getText();
        this.date = m.getDate();
        this.type = m.getType();
        String west,east,north,south;
        int layoutFlag;

        if (isself) {
            west = "East";
            east = "West";
            north = "South";
            south = "North";
            layoutFlag = FlowLayout.RIGHT;
        } else {
            west = "West";
            east = "East";
            north = "North";
            south = "South";
            layoutFlag = FlowLayout.LEFT;
        }


        this.setLayout(new BorderLayout());
        this.setBackground(Color.decode(Theme.MessageBubbleBackgroundColor));
//        this.setPreferredSize(new Dimension(540,50));
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel mainMessageBox = new JPanel();
        mainMessageBox.setBackground(Color.decode(Theme.MessageBubbleBackgroundColor));
//        mainMessageBox.setPreferredSize(new Dimension(300,40));
        mainMessageBox.setLayout(new BorderLayout());
        JPanel blankBar = new JPanel();
        blankBar.setBackground(Color.decode(Theme.MessageBubbleBackgroundColor));
//        blankBar.setPreferredSize(new Dimension(240,40));

        //用户信息面板
        JPanel userPanel = new JPanel();
        userPanel.setBackground(Color.decode(Theme.MessageBubbleBackgroundColor));
//        userPanel.setPreferredSize(new Dimension(300,20));
        userPanel.setLayout(new BorderLayout());

        ImageIcon avator = new ImageIcon(Config.defaultAvator);
        avator.setImage(avator.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        JLabel iconLabel = new JLabel(avator);
        iconLabel.setPreferredSize(new Dimension(20,20));
        userPanel.add(iconLabel,west);
        JPanel nameLabelPanel = new JPanel(new FlowLayout(layoutFlag));
        nameLabelPanel.setBackground(Color.decode(Theme.MessageBubbleBackgroundColor));
        JLabel nameLabel = new JLabel("  " + this.from + "  ");
        nameLabelPanel.add(nameLabel);
        userPanel.add(nameLabelPanel,"Center");

        //消息面板
        JPanel msgPanel = new JPanel();
        msgPanel.setLayout(new BorderLayout());
//        msgPanel.setPreferredSize(new Dimension(300,20));

        JPanel blkBar = new JPanel();
        blkBar.setBackground(Color.decode(Theme.MessageBubbleBackgroundColor));
        blkBar.setPreferredSize(new Dimension(30,20));
        msgPanel.add(blkBar,west);
        JPanel textPanel = new JPanel(new FlowLayout(layoutFlag));
//        textPanel.setPreferredSize(new Dimension(270,25));
//        textPanel.setBackground(Color.decode(Theme.MessageBubbleColor));
        JLabel msgBubble;
        try {
            if (this.type == MessageBox.TEXT) {
                msgBubble = new JLabel(handleMsgText(this.text));
            } else {
                msgBubble = new JLabel();
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] imageByte = decoder.decodeBuffer(this.text);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageByte);
                BufferedImage bufImage = ImageIO.read(inputStream);
                int height = bufImage.getHeight();
                int width = bufImage.getWidth();
                int scale = 1;
                if (width > 100) {
                    scale = width / 100;
                }
                ImageIcon icon = new ImageIcon(bufImage.getScaledInstance(width / scale,height /scale,Image.SCALE_SMOOTH));
                msgBubble.setIcon(icon);
            }
            msgBubble.setOpaque(true);
            msgBubble.setBackground(Color.decode(Theme.MessageBubbleColor));
            textPanel.add(msgBubble);
            msgPanel.add(textPanel, east);

            mainMessageBox.add(msgPanel, south);
            mainMessageBox.add(userPanel, north);

            this.add(mainMessageBox, west);
            this.add(blankBar, east);
        } catch (IOException e) {

        }
        //旧版本
//        JPanel leftContent = new JPanel();
//        BorderLayout bl = new BorderLayout();
//        bl.setHgap(3);
//        this.setLayout(bl);
//        leftContent.setLayout(new BorderLayout(3,3));
//        leftContent.add(new JLabel(this.from),"North");
//        leftContent.add(new JLabel(this.text),"South");
//
//        ImageIcon avator = new ImageIcon("./resource/img/stadium.png");
//        avator.setImage(avator.getImage().getScaledInstance(36,36, Image.SCALE_DEFAULT));
//        JLabel iconLabel = new JLabel(avator);
//        iconLabel.setPreferredSize(new Dimension(38,38));
//        this.add(iconLabel,"West");
//        this.add(leftContent,"Center");
    }

    public MessageBox(String from, String text, Date date) {
        super();
        this.from = from;
        this.text = text;
        this.date = date;
        this.setLayout(new BorderLayout());

        this.setPreferredSize(new Dimension(540,40));

        JPanel leftContent = new JPanel();
        leftContent.setLayout(new BorderLayout());
        leftContent.add(new JLabel(this.from),"North");
        leftContent.add(new JLabel(this.text),"South");
        this.add(leftContent,"Center");
    }
    private String handleMsgText(String text) {
        if (type == TEXT) {
            String tmpString = text;
            String result = "<html><body><p style='padding:3px'>";
            while (tmpString.length() > 20) {
                result = result + tmpString.substring(0, 20) + "<br/>";
                tmpString = tmpString.substring(20, tmpString.length());
            }
            return result + tmpString + "</p></body></html>";
        }else{
            String tmpString = text;
            String result = "<html><img src=\"data:image/png;base64,";
            result = result + text + "\" max-width=\"70px\" max-height=\"50px\"></html>";
            System.out.println(result);
            return result;
        }
    }
}
