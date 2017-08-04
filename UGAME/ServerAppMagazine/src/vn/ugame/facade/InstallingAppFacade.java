/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.facade;

import java.sql.SQLException;
import vn.ugame.dao.InstallingAppDA;
import vn.ugame.exception.DataConnectionFailed;

/**
 *
 * @author TruongDV
 */
public class InstallingAppFacade {

    public void addInstallingApp(int accountId, String packageName) throws DataConnectionFailed, SQLException {
        InstallingAppDA installingAppDA = new InstallingAppDA();
        installingAppDA.addInstallingApp(accountId, packageName);
    }

    public void deleteUserInstallingAppByAccount(int accountId) throws DataConnectionFailed, SQLException {
        InstallingAppDA installingAppDA = new InstallingAppDA();
        installingAppDA.deleteUserInstallingAppByAccount(accountId);
    }
}
