package com.lewis.firstPhase.io.advanceFirstTopic;

/**
 * Created by zhangminghua on 2016/12/7.
 */
public class DecodeDecorator extends Decorator {

    public DecodeDecorator(ICompoment compoment) {
        super(compoment);
    }

    @Override
    public String display(String str) {
        String display = super.display(str);
        return transform(display);
    }

    @Override
    public String transform(String str) {
        System.out.println("invoke DecodeDecorator...");
        return EnDecodeUtil.encodeDecode(str);
    }
}
