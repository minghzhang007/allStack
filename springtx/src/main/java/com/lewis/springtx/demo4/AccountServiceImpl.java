package com.lewis.springtx.demo4;


import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/2/5.
 */

/**
 * @Transaction:
 * propagation:事务的传播行为
 * isolation:事务的隔离级别
 * readOnly:是否只读事务
 * rollbackFor:发生哪些异常回滚事务
 * noRollbackFor:发生哪些异常不回滚事务
 */
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ,readOnly = false)
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;


    public void transfer(String out, String in, int money) {
        accountDao.transferOut(out, money);
        //int i = 1 / 0;
        accountDao.transferIn(in, money);
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


}
