package com.lewis.multiThreadPattern.promise;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2016/11/28.
 */
public class FTPClientUtil {
    private final FTPClient ftp = new FTPClient();

    private final Map<String, Boolean> dirCreateMap = new HashMap<>();

    private FTPClientUtil() {

    }

    //模式角色：Promise.Promisor.compute
    public static Future<FTPClientUtil> newInstance(final String ftpServer, final String usename, final String password) {
        Callable<FTPClientUtil> callable = new Callable<FTPClientUtil>() {
            @Override
            public FTPClientUtil call() throws Exception {
                FTPClientUtil ftpClientUtil = new FTPClientUtil();
                ftpClientUtil.init(ftpServer, usename, password);
                return ftpClientUtil;
            }
        };
        //task 相当于模式角色：Promise.Promise
        FutureTask<FTPClientUtil> task = new FutureTask<>(callable);
        //下面这行代码与本案例的实际角色并不一致，这是为了讨论方便。下面新建的线程相当于模式角色：Promise.TaskExecutor
        new Thread(task).start();
        return task;
    }

    private void init(String ftpServer, String usename, String password) throws Exception {
        FTPClientConfig config = new FTPClientConfig();
        ftp.configure(config);
        int reply;
        ftp.connect(ftpServer);
        System.out.println(ftp.getReplyString());
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new RuntimeException("FTP server refused connection.");
        }
        boolean isOK = ftp.login(usename, password);
        if (isOK) {
            System.out.println(ftp.getReplyString());
        } else {
            throw new RuntimeException("failed to login." + ftp.getReplyString());
        }
        reply = ftp.cwd("~/subspsync");
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new RuntimeException("failed to change working directory.reply:" + reply);
        } else {
            System.out.println(ftp.getReplyString());
        }
        ftp.setFileType(FTP.ASCII_FILE_TYPE);
    }

    public void upload(File file) throws Exception {
        InputStream dataIn = new BufferedInputStream(new FileInputStream(file));
        boolean isOK;
        String dirName = file.getParentFile().getName();
        String fileName = dirName + "/" + file.getName();
        ByteArrayInputStream checkFileInputStream = new ByteArrayInputStream("".getBytes());
        try {
            if (!dirCreateMap.containsKey(dirName)) {
                ftp.makeDirectory(dirName);
                dirCreateMap.put(dirName, null);
            }
            try {
                isOK = ftp.storeFile(fileName, dataIn);
            } catch (IOException e) {
                throw new RuntimeException("failed to upload " + file, e);
            }
            if (isOK) {
                ftp.storeFile(fileName + ".c", checkFileInputStream);
            } else {
                throw new RuntimeException("failed to upload " + file + " ,reply:" + ftp.getReplyString());
            }

        } finally {
            dataIn.close();
        }
    }

    public void disconnect(){
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


