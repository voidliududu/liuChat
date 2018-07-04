package cn.liududu.liuchat.base;

import cn.liududu.liuchat.common.Config;
import cn.liududu.liuchat.common.Logger;
import cn.liududu.liuchat.view.MainWindow;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SendFileServer implements Runnable {
    //服务器监听端口
    private static final int MONITORPORT = Config.fileTransferPort;
    private  File file;
    private Socket s;
    private static ServerSocket ss;
    public static boolean trigger = true;
    public SendFileServer(File file,Socket s) {
       this.file = file;
       this.s = s;
       trigger = true;
    }

    public static void close() {
        trigger = false;
        try {
            ss.close();
            Logger.log("文件传输服务已关闭");
        } catch (IOException e) {
            Logger.log("文件服务tcp套接字关闭错误");
        }
    }
    public static void server(File file)
    {
        try {
            //创建服务器socket
            ss = new ServerSocket(MONITORPORT);
            boolean flag = true;
            while (flag && trigger)
            {
                //接收到一个客户端连接之后，创建一个新的线程进行服务
                //并将联通的socket传给该线程
                Socket s = ss.accept();
                System.out.println(s.getRemoteSocketAddress());
                Socket m = s;
                new Thread(new SendFileServer(file,m)).start();
                flag = false;
            }

        } catch (IOException e) {
            //TODO Auto-generated catch block
            JOptionPane.showMessageDialog(MainWindow.getInstance(),"端口被占用或当前有文件正在传输");
//            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //TODO Auto-generated method stub
        byte[] buf = new byte[100];
        OutputStream os = null;
        FileInputStream fins = null;
        try {
            os = s.getOutputStream();
            BufferedOutputStream osbuf = new BufferedOutputStream(os);
            //文件路径
            String filePath = file.getPath();
            //文件名
            String fileName = file.getName();

            System.out.println("将文件名:" + filePath + fileName + "传输过去");
//            //先将文件名传输过去
//            os.write(fileName.getBytes());
            System.out.println("开始传输文件");
            //将文件传输过去
            //获取文件
            fins = new FileInputStream(filePath);
            int data;
            //通过fins读取文件，并通过os将文件传输
            while (-1 != (data = fins.read()))
            {
                System.out.println(data);
                os.write(data);
            }
            System.out.println("文件传输结束");
        } catch (IOException e) {

            //TODO Auto-generated catch block

            e.printStackTrace();

        } finally
        {
            try {
                if (fins != null) fins.close();
                if (os != null) os.close();
                this.s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
