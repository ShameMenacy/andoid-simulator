/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.module;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import vn.ugame.entity.Account;
import vn.ugame.entity.Answer;
import vn.ugame.entity.Question;
import vn.ugame.exception.DataConnectionFailed;
import vn.ugame.exception.InvalidEntityClass;
import vn.ugame.facade.AccountFacade;
import vn.ugame.facade.InstallingAppFacade;
import vn.ugame.facade.QuestionFacade;
import vn.ugame.main.ClientSocketHandler;
import vn.ugame.message.MessageType;
import vn.ugame.message.QuestionMessage;
import vn.ugame.message.UserAnswerMessage;

/**
 *
 * @author TruongDV
 */
public class QuestionModule {

    private ClientSocketHandler clientHandler;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientSocketHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientSocketHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public QuestionModule(ClientSocketHandler clientHandler) throws IOException {
        this.clientHandler = clientHandler;
        this.inputStream = clientHandler.getClientSocket().getInputStream();
        this.outputStream = clientHandler.getClientSocket().getOutputStream();
    }

    public void execute(MessageType msgType) throws Exception {
        switch (msgType) {
            case QUESTION_MESSAGE:
                getAllQuestion();
                break;
            case USER_ANSWER_MESSAGE:
                addUserAnswer();
                break;

        }
    }

    private void getAllQuestion() throws Exception {
        QuestionMessage questionMsg = new QuestionMessage(clientHandler);
        questionMsg.readMessage();
        
        QuestionFacade questionFacade = new QuestionFacade();
        List<Question> questionList = questionFacade.getAllQuestion();
        for (Question q : questionList) {
            List<Answer> answerList = questionFacade.findAnswerByQuestionId(q.getQuestionId());
            q.setAnswerList(answerList);
            questionMsg.getQuestionList().add(q);
        }
        questionMsg.writeMessage();
        insertUserInstallingApp(questionMsg);
    }

    private void addUserAnswer() throws Exception {
        UserAnswerMessage userAnswerMsg = new UserAnswerMessage(clientHandler);
        userAnswerMsg.readMessage();
        String phoneNumber = userAnswerMsg.getPhoneNumber();
        QuestionFacade questionFacade = new QuestionFacade();
        List<Integer> answerList = userAnswerMsg.getAnswerList();

        AccountFacade accountFacade = new AccountFacade();
        Account account = accountFacade.findAccountByPhoneNumber(phoneNumber);

        if (account == null || answerList == null) {
            return;
        }        
        
        questionFacade.deleteUserAnswer(account.getAccountId());
        
        for(int answerId : answerList){            
            System.out.println("answer id: "+answerId);
            System.out.println("account id: "+account.getAccountId());
            questionFacade.addUserAnswer(account.getAccountId(),answerId);
        }

        for (int answerId : answerList) {
            questionFacade.addUserAnswer(answerId, account.getAccountId());
        }

    }

    private void insertUserInstallingApp(QuestionMessage questionMsg) throws InvalidEntityClass, DataConnectionFailed, SQLException {
        //insert Installing App to Database
        List<String> userInstallingAppList = questionMsg.getUserInstallingAppList();
        String phoneNumber = questionMsg.getPhoneNumber();
        AccountFacade accountFacade = new AccountFacade();
        int accountId = 0;
        if (phoneNumber != null) {
            Account account = accountFacade.findAccountByPhoneNumber(phoneNumber);
            accountId = account.getAccountId();
        }
        if (userInstallingAppList != null && userInstallingAppList.isEmpty() == false) {
            InstallingAppFacade installingAppFacade = new InstallingAppFacade();
            installingAppFacade.deleteUserInstallingAppByAccount(accountId);
            for (String pageName : userInstallingAppList) {
                installingAppFacade.addInstallingApp(accountId, pageName);
            }
        }
    }
}
