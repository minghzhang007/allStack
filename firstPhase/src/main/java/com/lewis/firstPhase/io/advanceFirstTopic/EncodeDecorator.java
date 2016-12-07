package com.lewis.firstPhase.io.advanceFirstTopic;

/**
 * Created by zhangminghua on 2016/12/7.
 */
public class EncodeDecorator extends Decorator {

    public EncodeDecorator(ICompoment compoment) {
        super(compoment);
    }

    @Override
    public String display(String str) {
        String display = super.display(str);
        return transform(display);
    }

    @Override
    public String transform(String str) {
        System.out.println("invoke EncodeDecorator....");
       return EnDecodeUtil.encodeDecode(str);
    }
}
