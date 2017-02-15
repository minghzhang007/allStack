package AQS;

import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * Created by Administrator on 2017/2/15.
 */
public class Father {
    int num = 7;
    Father(){
        show();
        System.out.println("Father Constructor:"+num);
    }

    void show(){
        System.out.println("Father show :"+num);
    }
}

class Child extends Father{
    int num = 8;
    Child(){
        System.out.println("Child Constructor:"+num);
    }
    void show(){
        System.out.println("Child show :"+num);
    }
}

class Demo{
    public static void main(String[] args) {
        Child child = new Child();
        child.show();
    }
}
