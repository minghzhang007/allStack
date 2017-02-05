package com.lewis.springtx.demo2;

/**
 * Created by Administrator on 2017/2/5.
 */
public interface AccountDao {

    void transferIn(String in, int money);

    void transferOut(String out, int money);
}
