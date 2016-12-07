package com.lewis.firstPhase.io.advanceFirstTopic;

/**
 * Created by zhangminghua on 2016/12/7.
 */
public class Main {
    public static void main(String[] args) {
        ReverseDecorator reverseDecorator = new ReverseDecorator(new UpperDecorator(new DetailCompoment()));
        String display = reverseDecorator.display("wo shi zhongguo ren.");
        System.out.println(display);

        ExtendOrSplitDecorator decorator = new ExtendOrSplitDecorator(new LowerDecorator(new DetailCompoment()));
        String display1 = decorator.display("I Love");
        System.out.println(display1);

        EncodeDecorator decorator1 = new EncodeDecorator(new ReverseDecorator(new LowerDecorator(new DetailCompoment())));
        String display2 = decorator1.display("顶级机密：1941年12月 日本偷袭珍珠港！ 银行密码是:1234ADC");
        System.out.println(display2);
        System.out.println("++++++++++");
        EncodeDecorator decorator2 = new EncodeDecorator(new LowerDecorator(new ReverseDecorator(new DetailCompoment())));
        String display3 = decorator2.display("顶级机密：1941年12月 日本偷袭珍珠港！ 银行密码是:1234ADC");
        System.out.println(display3);

        System.out.println("============");
        System.out.println("============");
        DecodeDecorator decodeDecorator = new DecodeDecorator(decorator1);
        String display4 = decodeDecorator.display("顶级机密：1941年12月 日本偷袭珍珠港！ 银行密码是:1234ADC");
        System.out.println(display3);
    }
}
