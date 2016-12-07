package com.lewis.firstPhase.io.advanceFirstTopic;

/**
 * Created by zhangminghua on 2016/12/7.
 */
public class DetailCompoment implements ICompoment {
    @Override
    public String display(String str) {
        System.out.println("原来内容："+str);
        return str;
    }
}
