/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.facade;

import java.sql.SQLException;
import java.util.List;
import vn.ugame.dao.QuestionDA;
import vn.ugame.entity.Answer;
import vn.ugame.entity.Question;
import vn.ugame.exception.DataConnectionFailed;
import vn.ugame.exception.InvalidEntityClass;

/**
 *
 * @author TruongDV
 */
public class QuestionFacade {

    public List<Question> getAllQuestion() throws DataConnectionFailed, SQLException, InvalidEntityClass {
        QuestionDA questionDA = new QuestionDA();
        return questionDA.getAllQuestion();
    }

    public List<Answer> findAnswerByQuestionId(int questionId) throws DataConnectionFailed, SQLException, InvalidEntityClass {
        QuestionDA questionDA = new QuestionDA();
        return questionDA.findAnswerByQuestionId(questionId);
    }
    
    public void addUserAnswer(int accountId,int answerId) throws DataConnectionFailed, SQLException{
        QuestionDA questionDA = new QuestionDA();
        questionDA.addUserAnswer(accountId, accountId);
    }
    
    public void deleteUserAnswer(int accountId) throws DataConnectionFailed, SQLException{
        QuestionDA questionDA = new QuestionDA();
        questionDA.deleteUserAnswer(accountId);
    }
}
