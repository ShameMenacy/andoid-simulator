/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vn.ugame.entity.Answer;
import vn.ugame.entity.EntityFactory;
import vn.ugame.entity.Question;
import vn.ugame.exception.DataConnectionFailed;
import vn.ugame.exception.InvalidEntityClass;

/**
 *
 * @author TruongDV
 */
public class QuestionDA extends AbstractDA {

    public List<Question> getAllQuestion() throws DataConnectionFailed, SQLException, InvalidEntityClass {
        Connection conn = ConnectionFactory.getInstance().createDBConnection();
        CallableStatement stmt = conn.prepareCall("call getAllQuestion()");
        ResultSet rs = stmt.executeQuery();
        EntityFactory entityFactory = EntityFactory.createEntityFactory();
        List<Question> questionList = new ArrayList<>();
        while (rs.next()) {
            Question question = entityFactory.createEntity(rs, Question.class);
            questionList.add(question);
        }
        rs.close();
        conn.close();
        return questionList;
    }

    public List<Answer> findAnswerByQuestionId(int questionId) throws DataConnectionFailed, SQLException, InvalidEntityClass {
        Connection conn = ConnectionFactory.getInstance().createDBConnection();
        CallableStatement stmt = conn.prepareCall("call findAnswerByQuestionId(?)");
        stmt.setInt(1, questionId);
        ResultSet resultSet = stmt.executeQuery();
        List<Answer> answerList = new ArrayList<>();
        EntityFactory entityFactory = EntityFactory.createEntityFactory();
        while (resultSet.next()) {
            Answer answer = entityFactory.createEntity(resultSet, Answer.class);
            answerList.add(answer);
        }
        resultSet.close();
        conn.close();
        return answerList;
    }

    public void addUserAnswer(int accountId, int answerId) throws DataConnectionFailed, SQLException {
        Connection conn = ConnectionFactory.getInstance().createDBConnection();
        CallableStatement stmt = conn.prepareCall("call addUserAnswer(?,?)");
        stmt.setInt(1, accountId);
        stmt.setInt(2, answerId);
        stmt.execute();
        stmt.close();
        conn.close();
    }

    public void deleteUserAnswer(int accountId) throws DataConnectionFailed, SQLException {
        Connection conn = ConnectionFactory.getInstance().createDBConnection();
        CallableStatement stmt = conn.prepareCall("call deleteUserAnswerByAccountId(?)");
        stmt.setInt(1, accountId);
        stmt.execute();
        stmt.close();
        conn.close();
    }
}
