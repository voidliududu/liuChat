package cn.liududu.liuchat.entity;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;

public class User {
    public static User me;
    private String name;
    private InetAddress ipAddr;
    private int status;
    public static final int ONLINE = 0;
    public static final int BUSY = 1;
    public static final int OFFLINE = 2;
    public Avator avator;
    public User(String name,InetAddress ipAddr) {
        this.name = name;
        this.ipAddr = ipAddr;
        status = ONLINE;
        avator = new Avator(this);
    }

    public static User getCurrentUser() {
        return me;
    }

    public static void setCurrentUser(User user) {
        me = user;
    }

    public String getName() {
        return name;
    }

    public InetAddress getIpAddr() {
        return ipAddr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

class Avator extends JLabel {
    private int aColor;
    public Avator(User user) {
        aColor = user.hashCode();
        this.setSize(20,20);
    }
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        int x0=getSize().width/2;
		int y0=getSize().height/2;
		int r = x0 > y0? y0:x0;
		graphics.setColor(new Color(aColor));
        graphics.fillOval(x0-r,y0-r,r*2,r*2);
    }
}