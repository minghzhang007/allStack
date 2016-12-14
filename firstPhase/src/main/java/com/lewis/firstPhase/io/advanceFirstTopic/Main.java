package com.lewis.firstPhase.io.advanceFirstTopic;

import java.io.*;

/**
 * Created by zhangminghua on 2016/12/7.
 */
public class Main {
    public static void main(String[] args) {
        //将输入内容转为大写，再反转
        ReverseDecorator reverseDecorator = new ReverseDecorator(new UpperDecorator(new DetailCompoment()));
        String display = reverseDecorator.display("wo shi zhongguo ren.");
        System.out.println(display);

        //将输入内容转为小写，在裁剪或者扩展
        ExtendOrSplitDecorator decorator = new ExtendOrSplitDecorator(new LowerDecorator(new DetailCompoment()));
        String display1 = decorator.display("I Love");
        System.out.println(display1);

        //将输入内容转为小写，再反转，然后加密
        EncodeDecorator decorator1 = new EncodeDecorator(new ReverseDecorator(new LowerDecorator(new DetailCompoment())));
        String display2 = decorator1.display("顶级机密：1941年12月 日本偷袭珍珠港！ 银行密码是:1234ADC");
        System.out.println(display2);
        System.out.println("++++++++++");
        //将输入内容先反转、再转为小写，然后加密
        EncodeDecorator decorator2 = new EncodeDecorator(new LowerDecorator(new ReverseDecorator(new DetailCompoment())));
        String display3 = decorator2.display("顶级机密：1941年12月 日本偷袭珍珠港！ 银行密码是:1234ADC");
        System.out.println(display3);

        System.out.println("============");
        //对上面的加密内容，进行解密
        DecodeDecorator decodeDecorator = new DecodeDecorator(decorator1);
        String display4 = decodeDecorator.display("顶级机密：1941年12月 日本偷袭珍珠港！ 银行密码是:1234ADC");
        System.out.println(display4);

        try {
            //这里FileInputStream 相当于组件对象，BufferedInputStream这个装饰器装饰了FileInputStream对象
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("fileName")));
            byte[] buff = new byte[1024];
            bis.read(buff);
            System.out.println(new String(buff));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
