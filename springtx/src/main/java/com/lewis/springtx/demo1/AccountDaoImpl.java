package com.lewis.springtx.demo1;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by Administrator on 2017/2/5.
 */
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {

    public void transferIn(String in, int money) {
        String sql ="update account set money = money + ? where name = ?";
        this.getJdbcTemplate().update(sql,money,in);
    }

    public void transferOut(String out, int money) {
        String sql ="update account set money = money - ? where name = ?";
        this.getJdbcTemplate().update(sql,money,out);
    }
}
