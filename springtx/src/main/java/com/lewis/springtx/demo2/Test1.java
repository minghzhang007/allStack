package com.lewis.springtx.demo2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/2/5.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:springContext2.xml")
public class Test1 {

    @Resource(name="transactionProxyFactoryBean")
    private AccountService accountService;

    @Test
    public void demo1(){
        accountService.transfer("a","b",200);
    }


}
