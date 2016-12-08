package com.lewis.sort;

/**
 * Created by Administrator on 2016/12/4.
 */
public class BinaryUtil {

    /**
     * 将一个int拆成4个byte组成的数组
     * 【第一个byte 高8位】
     * 【第二个byte 次高8位】
     * 【第三个byte 次低8位】
     * 【第四个byte 低8位】
     *
     * @return 4个byte组成的数组
     */
    public static byte[] int2ByteArray(int i) {
        byte[] bytes = new byte[4];
        //第一个byte 高8位
        bytes[0] = (byte) ((i >> 24) & 0xff);
        //第二个byte 次高8位
        bytes[1] = (byte) ((i >> 16) & 0xff);
        //第三个byte 次低8位
        bytes[2] = (byte) ((i >> 8) & 0xff);
        //第四个byte 低8位
        bytes[3] = (byte) ((i) & 0xff);
        return bytes;
    }

    /**
     * 将byte数组的byte按照从高位到低位的次序排练，行程一个int
     *
     * @param bytes
     * @return
     */
    public static int byteArray2Int(byte[] bytes) {
        int retInt = 0;
        int v0 = (bytes[0] << 24) & 0xff000000;
        int v1 = (bytes[1] << 16) & 0x00ff0000;
        int v2 = (bytes[2] << 8) & 0x0000ff00;
        int v3 = (bytes[3] << 0) & 0x000000ff;
        retInt = v0 | v1 | v2 | v3;
        return retInt;
    }

    public static String getFormatBinaryString(int i) {
        return getWholeBinaryBits(Integer.toBinaryString(i));
    }

    public static byte[] reverseBytes(byte[] bytes) {
        byte[] reverseBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            reverseBytes[i] = bytes[bytes.length - 1 - i];
        }
        return reverseBytes;
    }

    public static int reverseInt(int i) {
        byte[] bytes = int2ByteArray(i);
        byte[] reverseBytes = reverseBytes(bytes);
        int reverseInt = byteArray2Int(reverseBytes);
        return reverseInt;
    }

    public static void printByteEleAsBinary(byte ...bytes){
        System.out.println("+++++++++++");
        for (int i = 0; i < bytes.length; i++) {
            System.out.println(BinaryUtil.getFormatBinaryString(bytes[i]));
        }
        System.out.println("------------");
    }

    public static String getWholeBinaryBits(String str) {
        StringBuilder sb = new StringBuilder();
        if (str != null && str.length() > 0) {
            char[] chars = str.toCharArray();
            int insertLength = 32-chars.length;
            int batchIndex = 0;
            for (int i = 0; i < insertLength; i++) {
                if (batchIndex == 4) {
                    sb.append(" ");
                    batchIndex = 0;
                }
                sb.append("0");
                batchIndex++;
            }

            StringBuilder builder= new StringBuilder();
            int index = 0;
            if (chars != null && chars.length > 0) {
                for (int i = chars.length - 1; i >= 0; i--) {
                    if (index == 4) {
                        builder.append(" ");
                        index = 0;
                    }
                    builder.append(chars[i]);
                    index++;
                }
            }
            String s = builder.reverse().toString();
            if (s != null && s.length() > 0) {
                String[] split = s.split(" ");
                String s1 = split[0];
                if (s1.length() == 4) {
                    sb.append(" ");
                }
            }
            System.out.println("s ="+s);
            sb.append(s);
        }
        return sb.toString();
    }

}
