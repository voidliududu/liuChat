package cn.liududu.liuchat.listener;

import cn.liududu.liuchat.base.FileTransferService;
import cn.liududu.liuchat.base.IpMsgLayer;
import cn.liududu.liuchat.common.Logger;

import java.io.File;

public class FileTransferRequest {
    public void AcceptRequest(String ip,String fileInfo) {
        FileTransferService fts = new FileTransferService();
        fts.setService(FileTransferService.CLIENT);
        fts.setServerip(ip);
        fts.setFileName(fileInfo);
        Logger.log("向文件服务发起连接");
        fts.start();
    }

    public void DeclineRequest(File file) {
//        IpMsgLayer.getInstance().getPh().declineFileTransferRequest();
    }
}
