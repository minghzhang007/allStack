package com.lewis.firstPhase.io.advanceFirstTopic;

/**
 * Created by zhangminghua on 2016/12/7.
 */
public class LowerDecorator extends Decorator{
    public LowerDecorator(ICompoment compoment) {
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
        System.out.println("invoke lowerDecorator....");
        return str.toLowerCase();
    }
}
