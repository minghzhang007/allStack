package com.lewis.firstPhase.multiThreadUp;

import com.lewis.firstPhase.io.advanceSecondTopic.StartEndIndexPair;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangminghua on 2017/1/17.
 */
public final class RandomAccessFileUtil {
    private RandomAccessFileUtil(){}

    public static List<StartEndIndexPair> getStartEndIndexPairs(RandomAccessFile raf, int threadNum) {
        List<StartEndIndexPair> startEndIndexPairs = new ArrayList<>();
        try {
            long length = raf.length();
            long lengthPerThread = length / threadNum;
            long lastEndIndex = lengthPerThread;
            //确定每个线程读取到startIndex和endIndex
            for (int i = 0; i < threadNum; i++) {
                if (i == 0) {
                    raf.seek(lastEndIndex);
                    while (raf.readByte() != '\n') {
                        lastEndIndex++;
                    }
                    startEndIndexPairs.add(new StartEndIndexPair(0, lastEndIndex));
                } else {
                    long tmpStartIndex = lastEndIndex + 1;
                    long tmpEndIndex = tmpStartIndex + lengthPerThread;
                    if (tmpEndIndex > length) {
                        tmpEndIndex = length - 1;
                    }
                    raf.seek(tmpEndIndex);
                    while (raf.readByte() != '\n') {
                        tmpEndIndex++;
                    }
                    startEndIndexPairs.add(new StartEndIndexPair(tmpStartIndex, tmpEndIndex));
                    lastEndIndex = tmpEndIndex;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startEndIndexPairs;
    }
}
