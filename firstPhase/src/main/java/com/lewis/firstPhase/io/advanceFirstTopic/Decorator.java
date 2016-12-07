package com.lewis.firstPhase.io.advanceFirstTopic;

/**
 * Created by zhangminghua on 2016/12/7.
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

      public abstract String transform(String str);
}
