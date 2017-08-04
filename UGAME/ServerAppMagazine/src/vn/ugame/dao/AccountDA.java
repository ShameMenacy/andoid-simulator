/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import vn.ugame.entity.Account;
import vn.ugame.entity.EntityFactory;
import vn.ugame.exception.DataConnectionFailed;
import vn.ugame.exception.InvalidEntityClass;


/**
 *
 * @author TruongDV
 */
public class AccountDA  extends AbstractDA{
   public void addAccount(Account account) throws DataConnectionFailed, SQLException{
       int accountId = createNewId("Account");
       Connection conn = ConnectionFactory.getInstance().createDBConnection();
       CallableStatement stmt = conn.prepareCall("call addAccount(?,?,?)");
       stmt.setInt(1, accountId);
       stmt.setString(2, account.getPhoneNumber());
       stmt.setInt(3, account.getActiveCode()); 
       stmt.execute();
       stmt.close();
       conn.close();
   }
   
   public Account findAccountByPhoneNumber(String phoneNumber) throws DataConnectionFailed, SQLException, InvalidEntityClass{
       Connection conn = ConnectionFactory.getInstance().createDBConnection();
       CallableStatement stmt = conn.prepareCall("call findAccountByPhoneNumber(?)");
       stmt.setString(1, phoneNumber);
       ResultSet rs = stmt.executeQuery();       
       EntityFactory entityFactory = EntityFactory.createEntityFactory();
       Account account = null;
       if(rs.next()){
           account = entityFactory.createEntity(rs,Account.class);
       }
       stmt.close();
       conn.close();
       return account;
   }
   
   public void activeAccount(String phoneNumber) throws SQLException, DataConnectionFailed{
       Connection conn  = ConnectionFactory.getInstance().createDBConnection();
       CallableStatement stmt = conn.prepareCall("call activeAccount(?)");
       stmt.setString(1, phoneNumber);
       stmt.execute();
       stmt.close();
       conn.close();       
   }
}
