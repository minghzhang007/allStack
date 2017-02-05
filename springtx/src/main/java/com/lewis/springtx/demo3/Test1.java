package com.lewis.springtx.demo3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/2/5.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:springContext3.xml")
public class Test1 {

    @Resource(name="accountService")
    private AccountService accountService;

    @Test
    public void demo1(){
        accountService.transfer("a","b",200);
    }


}
