package cn.liududu.liuchat.listener;

import cn.liududu.liuchat.base.IpMsgLayer;
import cn.liududu.liuchat.common.Config;
import cn.liududu.liuchat.common.ImageEncoder;
import cn.liududu.liuchat.common.Logger;
import cn.liududu.liuchat.entity.Message;
import cn.liududu.liuchat.entity.User;
import cn.liududu.liuchat.view.MainWindow;
import cn.liududu.liuchat.view.MessageBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImageButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (MainWindow.getInstance().getSessionUser() == null) {
            JOptionPane.showMessageDialog(MainWindow.getInstance(),"不能群发图片");
            return;
        }
        JFileChooser jfc = new JFileChooser(new File("."));
        int flag = jfc.showOpenDialog(MainWindow.getInstance());
        if (flag == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            IpMsgLayer.getInstance().getPh().sendImage(User.getCurrentUser(),MainWindow.getInstance().getSessionUser(),file);
            String text = ImageEncoder.encodeImgageToBase64(file);
//            System.out.println(text);
            MainWindow mv =  MainWindow.getInstance();
            Message msg =  new Message(User.getCurrentUser().getName(),MainWindow.getInstance().getSessionUser().getIpAddr().toString(),text,Message.IMG);
//            Message msg = new Message(User.getCurrentUser().getName(),User.getCurrentUser().getIpAddr().toString(),text,Message.IMG);
            Logger.log("消息地址 " + msg.getAddress());
            mv.getChatFrame().getMw().getMessageBoard(msg.getAddress().split("/")[1]).addMessage(new MessageBox(msg,true));
//            mv.getChatFrame().getMw().getMessageBoard(Config.broadcastGroup).addMessage(new MessageBox(msg,true));
        }
    }
}
