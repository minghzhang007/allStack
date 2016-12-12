package com.lewis.firstPhase.nio;

import java.nio.CharBuffer;

/**
 * Created by zhangminghua on 2016/12/9.
 */
public class BufferFillDrain {

    private static int index = 0;


    public static void main(String[] args) {
        doWork();
    }

    private static void doWork() {
        CharBuffer charBuffer = CharBuffer.allocate(100);
        while (fillBuffer(charBuffer)) {
            charBuffer.flip();
            drainBuffer(charBuffer);
            charBuffer.clear();
        }
    }

    private static void drainBuffer(CharBuffer charBuffer) {
        while (charBuffer.hasRemaining()) {
            System.out.print(charBuffer.get());
        }
        System.out.println();
    }

    private static boolean fillBuffer(CharBuffer charBuffer) {
        if (index >= strings.length) {
            return false;
        }
        String str = strings[index++];
        for (int i = 0; i < str.length(); i++) {
            charBuffer.put(str.charAt(i));
        }
        return true;
    }


    private static String [] strings = {
            "A random string value",
            "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees",
            "Opening act for the Monkees: Jimi Hendrix",
            "'Scuse me while I kiss this fly",
            "Help Me! Help Me!",
    };
}
