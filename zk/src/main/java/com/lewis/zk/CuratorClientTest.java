package com.lewis.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

/**
 * Created by zhangminghua on 2016/12/29.
 */
public class CuratorClientTest {
    private CuratorFramework client;

    public CuratorClientTest(String connectingString) {
        client = CuratorFrameworkFactory.builder().connectString(connectingString).sessionTimeoutMs(60000)
                .retryPolicy(new RetryNTimes(3,5000)).namespace("boh").build();
        client.start();
    }

    public void createNode(String path,byte[] data){
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeClient(){
        if (client != null) {
            client.close();
        }
    }

    public static void main(String[] args) {
        CuratorClientTest clientTest = new CuratorClientTest("127.0.0.1:2181");
        clientTest.createNode("/switch/priceSwitch1","priceSwtich1".getBytes());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            clientTest.closeClient();
        }
    }
}
