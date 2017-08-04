/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.facade;

import java.sql.SQLException;
import java.util.Random;
import vn.ugame.dao.AccountDA;
import vn.ugame.entity.Account;
import vn.ugame.exception.DataConnectionFailed;
import vn.ugame.exception.InvalidEntityClass;

/**
 *
 * @author TruongDV
 */
public class AccountFacade {

    public void addAccount(String phoneNumber) throws DataConnectionFailed, SQLException {
        AccountDA accountDA = new AccountDA();

        char[] chars = "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        int activeCode = Integer.parseInt(sb.toString());
        Account account = new Account();
        account.setPhoneNumber(phoneNumber);
        account.setActiveCode(activeCode);
        accountDA.addAccount(account);
    }

    public Account findAccountByPhoneNumber(String phoneNumber) throws DataConnectionFailed, SQLException, InvalidEntityClass {
        AccountDA accountDA = new AccountDA();
        return accountDA.findAccountByPhoneNumber(phoneNumber);
    }
    
    public void activeAccount(String phoneNumber) throws SQLException, DataConnectionFailed{
        AccountDA accountDA = new AccountDA();
        accountDA.activeAccount(phoneNumber);
    }
}
