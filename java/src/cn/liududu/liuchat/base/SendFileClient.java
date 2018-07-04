package cn.liududu.liuchat.base;

import cn.liududu.liuchat.common.Config;
import cn.liududu.liuchat.common.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class SendFileClient extends Thread{
    private static  String SERVERIP = "127.0.1.1";
    private static final int SERVERPORT = Config.fileTransferPort;
    private static final int CLIENTPORT = Config.fileClientPort;
    private String fileName;

    public SendFileClient(String serverIp,String fileName) {
        this.fileName = fileName;
        SERVERIP = serverIp;
    }


    @Override
    public  void run() {

        //TODO Auto-generated method stub
        //用来接受传输过来的字符
        byte[] buf = new byte[100];
//        try {
//            System.out.println("sleeping");
//            sleep(3000);
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            Socket so = new Socket();
            //建立连接
            so.connect(new InetSocketAddress(SERVERIP,SERVERPORT));
            System.out.println(so.getRemoteSocketAddress());
            InputStream is = so.getInputStream();
            BufferedInputStream isbuf = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(is);
            System.out.println("文件名：" + fileName);
            //接收传输来的文件
            Logger.log(Config.fileSaveLocation + fileName);
            FileOutputStream fos = new FileOutputStream(Config.fileSaveLocation + "/" + fileName);
            BufferedOutputStream fosbuf = new BufferedOutputStream(fos);
            int data;
//            data = is.read();
            int size = 0;
//            fos.write(data);
//            System.out.println(data);
            while((size = dis.read(buf)) != -1)
            {
//                System.out.println(data);
                System.out.println("buffer write");
                fosbuf.write(buf,0,size);
            }
            System.err.println(size);
            fosbuf.flush();
            Logger.log("close connect");
            data = 100;
            while(data > 0) data--;
            is.close();
            so.close();
            fos.close();
        }catch (IOException e) {
            //TODO Auto-generated catch block
            System.out.println("file IO exception");

            e.printStackTrace();
        }
    }
}
