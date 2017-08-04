/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.module;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import vn.ugame.entity.Account;
import vn.ugame.exception.DataConnectionFailed;
import vn.ugame.exception.InvalidEntityClass;
import vn.ugame.facade.AccountFacade;
import vn.ugame.main.ClientSocketHandler;
import vn.ugame.message.RegisterMessage;

/**
 *
 * @author TruongDV
 */
public class RegisterModule implements Module {

    private ClientSocketHandler clientHandler;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientSocketHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientSocketHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public RegisterModule(ClientSocketHandler client) throws IOException {
        this.clientHandler = client;
        this.inputStream = client.getClientSocket().getInputStream();
        this.outputStream = client.getClientSocket().getOutputStream();
    }

    @Override
    public void execute() throws IOException, DataConnectionFailed, SQLException, InvalidEntityClass {
        register();
    }

    public void register() throws IOException, DataConnectionFailed, SQLException, InvalidEntityClass {
        RegisterMessage msg = new RegisterMessage();
        msg.setDataInputStream(inputStream);
        msg.readMessage();
        String phoneNumber = msg.getPhoneNumber();
        if (phoneNumber != null && !"".equals(phoneNumber)) {
            AccountFacade accountFacade = new AccountFacade();
            Account account = accountFacade.findAccountByPhoneNumber(phoneNumber);
            if (account == null) {
                accountFacade.addAccount(phoneNumber);
                account = accountFacade.findAccountByPhoneNumber(phoneNumber);
                int activeCode = account.getActiveCode();
                // sendActiveCode(phoneNumber, activeCode);                
            } else {
                int activeCode = account.getActiveCode();
//                sendActiveCode(phoneNumber, activeCode);
            }
        }
    }

    public void sendActiveCode(String phoneNumber, int activeCode) {
        String mtseq = "111";
        String moid = "1262";
        String moseq = "11223";
        String src = "8046";
        String dest = phoneNumber;
        String cmdcode = "ACTIVE";
        String msgtype = "0";
        String msgtitle = "";
        String msgbody = activeCode + "";
        String mttotalseg = "1";
        String mtsegref = "1";
        String procresult = "1";
        String cpId = "59";
        String operation = "";
        String username = "vngame";
        String password = "vngame_ili1l";

        if (phoneNumber.matches("(097|098|0163|0164|0165|0166|0167|0168|0169).*")) {
            operation = "VTE";
        } else if (phoneNumber.matches("(090|093|0120|0121|0122|0126|0128).*")) {
            operation = "MBI";
        } else if (phoneNumber.matches("(091|094|0123|0124|0125|0127|0129).*")) {
            operation = "VNA";
        } else {
            return;
        }

        dest = phoneNumber.replaceFirst("0", "84");

        //int mtStatus = sendMT("111", "1267", "12345", "8746", "84936194388", "GOLD", "0", "", "Cam on ban da nap Gold thanh cong", "1", "1", "1", "59", "MBI", "vngame", "vngame_ili1l");
//        int mtStatus = sendMT(mtseq, moid, moseq, src, dest, cmdcode, msgtype, msgtitle, msgbody, mttotalseg, mtsegref, procresult, cpId, operation, username, password);
    }
//    private static int sendMT(java.lang.String mtseq, java.lang.String moid, java.lang.String moseq, java.lang.String src, java.lang.String dest, java.lang.String cmdcode, java.lang.String msgtype, java.lang.String msgtitle, java.lang.String msgbody, java.lang.String mttotalseg, java.lang.String mtseqref, java.lang.String procresult, java.lang.String cpid, java.lang.String operation, java.lang.String username, java.lang.String password) {
//        org.tempuri.Service service = new org.tempuri.Service();
//        org.tempuri.ServiceSoap port = service.getServiceSoap();
//        return port.sendMT(mtseq, moid, moseq, src, dest, cmdcode, msgtype, msgtitle, msgbody, mttotalseg, mtseqref, procresult, cpid, operation, username, password);
//    }
}
