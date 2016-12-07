package com.lewis.firstPhase.io.advanceFirstTopic;

/**
 * Created by zhangminghua on 2016/12/7.
 */
public class ExtendOrSplitDecorator extends Decorator {
    public ExtendOrSplitDecorator(ICompoment compoment) {
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
        System.out.println("invoke ExtendOrSplitDecorator....");
        if (str != null) {
            if (str.length() > 10) {
                return str.substring(0,10);
            }else{
                int repeatCount = 10 -str.length();
                StringBuilder sb = new StringBuilder(str);
                for (int i = 0; i < repeatCount; i++) {
                    sb.append("!");
                }
                return sb.toString();
            }
        }
        return null;
    }
}
