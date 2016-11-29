package com.lewis.multiThreadPattern.ProducerConsumer;

import com.lewis.multiThreadPattern.TwoPhaseTermination.AbsTerminatableThread;
import java.io.*;
import java.text.Normalizer;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Administrator on 2016/11/29.
 * 模式角色：Producer-Consumer.Producer
 */
public class AttachmentProcessor {

    private final String attach_score_base_dir = "/home/viscent/tmp/attachments/";

    //模式角色：Producer-Consumer.Channel
    private final Channel<File> channel = new BlockingQueueChannel<>(new ArrayBlockingQueue<File>(200));

    //模式角色：Producer-Consumer.Consumer
    private final AbsTerminatableThread indexingThread = new AbsTerminatableThread() {

        @Override
        protected void doRun() throws Exception {
            File file = null;
            file = channel.take();
            try {
                indexFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                terminationToken.reservations.decrementAndGet();
            }
        }

        //根据指定文件生成全文搜索所需的索引文件
        private void indexFile(File file) throws Exception {
            Random random = new Random();
            //模拟生成全文搜索所需的索引文件
            Thread.sleep(random.nextInt(100));
        }
    };

    public void init() {
        indexingThread.start();
    }

    public void shutdown() {
        indexingThread.terminate();
    }

    public void saveAttachment(InputStream in, String documentId, String originalFileName) throws IOException {
        File file = saveAsFile(in, documentId, originalFileName);
        try {
            channel.put(file);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        indexingThread.terminationToken.reservations.incrementAndGet();
    }

    private File saveAsFile(InputStream in, String documentId, String originalFileName) throws IOException {
        String dirName = attach_score_base_dir + documentId;
        File dir = new File(dirName);
        dir.mkdirs();
        File file = new File(dirName + '/' + Normalizer.normalize(originalFileName, Normalizer.Form.NFC));
        //防止目录跨越攻击
        if (!dirName.equals(file.getCanonicalFile().getParent())) {
            throw new SecurityException("Invalid originalFileName:" + originalFileName);
        }
        BufferedOutputStream bos = null;
        BufferedInputStream bis = new BufferedInputStream(in);
        byte[] buf = new byte[2048];
        int len = -1;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            while ((len = bis.read()) > 0) {
                bos.write(buf, 0, len);
            }
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return file;
    }

}
