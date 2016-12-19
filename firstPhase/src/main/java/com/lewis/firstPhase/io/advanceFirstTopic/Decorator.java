package com.lewis.firstPhase.io.advanceFirstTopic;

/**
 * Created by zhangminghua on 2016/12/7.
 * 装饰器的基类
 */
public abstract class Decorator implements ICompoment{

      protected ICompoment compoment;

      public Decorator(ICompoment compoment) {
            this.compoment = compoment;
      }

      @Override
      public String display(String str) {
            return compoment.display(str);
      }
      //实现装饰的方法
      public abstract String transform(String str);
}
