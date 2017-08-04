/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.module;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import vn.ugame.entity.Account;
import vn.ugame.message.ErrorMessage;
import vn.ugame.message.LoginMessage;
import vn.ugame.message.SuccessfulMessage;
import vn.ugame.facade.AccountFacade;
import vn.ugame.main.ClientSocketHandler;   

/**
 *
 * @author TruongDV
 */
public class LoginModule implements Module {
    
    private ClientSocketHandler clientHandler;
    private InputStream inputStream;
    private OutputStream outputStream;
    
    public ClientSocketHandler getClientHandler() {
        return clientHandler;
    }
    
    public void setClientHandler(ClientSocketHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
    
    public LoginModule(ClientSocketHandler clientHandler) throws IOException {
        this.clientHandler = clientHandler;
        this.inputStream = clientHandler.getClientSocket().getInputStream();
        this.outputStream = clientHandler.getClientSocket().getOutputStream();
    }
    
    @Override
    public void execute() {
        login();
    }
    
    public void login() {
        try {
            LoginMessage loginMessage = new LoginMessage();
            InputStream input = clientHandler.getClientSocket().getInputStream();
            loginMessage.setDataInputStream(input);
            loginMessage.readMessage();
            String phoneNumber = loginMessage.getPhoneNumber();
            int activeCode = loginMessage.getActiveCode();
            
            AccountFacade accountFacade = new AccountFacade();
            Account account = accountFacade.findAccountByPhoneNumber(phoneNumber);
            
            if (account == null) {
                ErrorMessage errorMsg = new ErrorMessage();
                errorMsg.setDataOutPutStream(outputStream);
                errorMsg.setMessage("This phone number hasn't actived yet!");
                errorMsg.writeMessage();
                return;
            }
            
            if (account.getActiveCode() != activeCode) {
                ErrorMessage errorMsg = new ErrorMessage();
                errorMsg.setDataOutPutStream(outputStream);
                errorMsg.setMessage("The active code is incorrect !");
                errorMsg.writeMessage();
                return;
            }
            
            if (account.getStatus() == 0) {
                accountFacade.activeAccount(phoneNumber);
            }
            
            SuccessfulMessage successMsg = new SuccessfulMessage();
            successMsg.setDataOutPutStream(outputStream);
            successMsg.setMessage("Welcome to app magazine!");
            successMsg.writeMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
    }
}
