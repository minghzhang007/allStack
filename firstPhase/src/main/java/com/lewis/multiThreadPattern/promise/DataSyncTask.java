package com.lewis.multiThreadPattern.promise;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2016/11/28.
 */
public class DataSyncTask implements Runnable {

    private final Map<String,String> taskParameters;

    public DataSyncTask(Map<String, String> taskParameters) {
        this.taskParameters = taskParameters;
    }

    @Override
    public void run() {
        String ftpServer = taskParameters.get("server");
        String usename = taskParameters.get("usename");
        String password = taskParameters.get("password");
        //先开始初始化FTP客户端实例
        Future<FTPClientUtil> ftpClientUtilPromise = FTPClientUtil.newInstance(ftpServer, usename, password);
        //查询数据库生成本地文件
        generateFilesFromDB();
        FTPClientUtil ftpClientUtil = null;
        try {
            ftpClientUtil = ftpClientUtilPromise.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        //上传文件
        uploadFiles(ftpClientUtil);
    }

    private void uploadFiles(FTPClientUtil ftpClientUtil) {
        Set<File> files = retrieveGeneratedFiles();
        for (File file : files) {
            try {
                ftpClientUtil.upload(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Set<File> retrieveGeneratedFiles() {
        Set<File> files = new HashSet<>();
        
        return files;
    }

    private void generateFilesFromDB(){
        //省略其他代码
    }
}
