/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import vn.ugame.exception.DataConnectionFailed;

/**
 *
 * @author robo8590
 */
public class AbstractDA {

    public int createNewId(String tableName) throws DataConnectionFailed, SQLException {
        int lastestId = findLastestId(tableName);
        return lastestId + 1;
    }

    public int findLastestId(String tableName) throws DataConnectionFailed, SQLException {
        Connection conn = ConnectionFactory.getInstance().createDBConnection();
        int lastestId = 0;
        String query = "call findLastestID(?, ?)";
        CallableStatement stmt = conn.prepareCall(query);
        stmt.registerOutParameter(2, Types.INTEGER);
        stmt.setString(1, tableName);
        stmt.executeQuery();
        if (stmt.getInt(2) != -1) {
            lastestId = stmt.getInt(2);
        }
        conn.close();
        return lastestId;
    }
    
}
