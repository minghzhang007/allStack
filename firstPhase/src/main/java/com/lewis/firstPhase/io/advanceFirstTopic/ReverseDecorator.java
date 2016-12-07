package com.lewis.firstPhase.io.advanceFirstTopic;

/**
 * Created by zhangminghua on 2016/12/7.
 */
public class ReverseDecorator extends Decorator {

    public ReverseDecorator(ICompoment compoment) {
        super(compoment);
    }

    @Override
    public String display(String str) {
        String display = super.display(str);
        String transform = transform(display);
        return transform;
    }

    @Override
    public String transform(String str) {
        System.out.println("invoke ReverseDecorator....");
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }

}
