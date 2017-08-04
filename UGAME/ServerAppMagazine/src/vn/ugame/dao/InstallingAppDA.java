/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import vn.ugame.exception.DataConnectionFailed;

/**
 *
 * @author TruongDV
 */
public class InstallingAppDA extends AbstractDA {

    public void addInstallingApp(int accountId, String packageName) throws DataConnectionFailed, SQLException {
        int installingAppId = createNewId("InstallingApp");
        Connection conn = ConnectionFactory.getInstance().createDBConnection();
        CallableStatement stmt = conn.prepareCall("call addInstallingApp(?,?,?)");
        stmt.setInt(1, installingAppId);
        stmt.setInt(2, accountId);
        stmt.setString(3, packageName);
        stmt.execute();
        stmt.close();
        conn.close();
    }
    
    public void deleteUserInstallingAppByAccount(int accountId) throws DataConnectionFailed, SQLException{
        Connection conn = ConnectionFactory.getInstance().createDBConnection();
        CallableStatement stmt = conn.prepareCall("call deleteUserInstallingAppByAccount(?)");
        stmt.setInt(1, accountId);
        stmt.execute();
        stmt.close();
        conn.close();
    }
}
