package com.lewis.firstPhase.io.piped;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by Administrator on 2016/12/16.
 */
public class PipedStreamTest {
    public static void main(String[] args) {
        Sender t1 = new Sender();

        Receiver t2 = new Receiver();

        PipedOutputStream outputStream = t1.getOutputStream();
        PipedInputStream inputStream = t2.getInputStream();
        try {
            inputStream.connect(outputStream);
            t1.start();
            t2.start();
        } catch (Exception e) {

        }

    }
}
