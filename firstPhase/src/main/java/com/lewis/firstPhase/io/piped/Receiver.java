package com.lewis.firstPhase.io.piped;

import java.io.IOException;
import java.io.PipedInputStream;

/**
 * Created by Administrator on 2016/12/16.
 */
public class Receiver extends Thread {

    private PipedInputStream in = new PipedInputStream();

    public PipedInputStream getInputStream(){
        return in;
    }

    @Override
    public void run() {
        readMessageOnce();
    }

    public void readMessageOnce(){
        byte[] buffer = new byte[2048];
        try {
            int len = in.read(buffer);
            System.out.println(new String(buffer,0,len));
            in.close();
        } catch (Exception e) {

        }
    }

    public void readMessageContinued(){
        int total = 0;
        while (true) {
            byte[] buff = new byte[1024];
            try {
                int len = in.read(buff);
                total +=len;
                System.out.println(new String(buff,0,len));
                if (total > 1024) {
                    break;
                }
            } catch (Exception e) {

            }

        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
