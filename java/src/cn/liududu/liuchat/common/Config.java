package cn.liududu.liuchat.common;

import java.io.File;

public class Config {
    public static final String serveHost = "127.0.0.4";
    public static final int udpPort = 6000;

    public static final int broadcastPort = 2334;
    public static final String broadcastGroup = "233.0.0.1";

    public static final int fileTransferPort = 2335;
    public static final int fileClientPort = 2336;
//    public static final String fileSaveLocation = "./resource/file_recv/";
    public static final String fileSaveLocation = System.getProperty("user.dir") + "/file_recv";

    public static final String defaultAvator = "./img/avator.png";
    public static final int msgQueueSize = 10;
    public static final int maxDatagramPacketSize = 2048;
    public static final String groupName = "群聊";

    public static final String splitChar = "\\";
}
