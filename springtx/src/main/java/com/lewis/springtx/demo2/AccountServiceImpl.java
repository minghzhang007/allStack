package com.lewis.springtx.demo2;


/**
 * Created by Administrator on 2017/2/5.
 */
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;


    public void transfer(String out, String in, int money) {
        accountDao.transferOut(out, money);
        int i = 1 / 0;
        accountDao.transferIn(in, money);
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


}
