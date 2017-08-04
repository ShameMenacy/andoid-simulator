/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.ugame.message;

/**
 *
 * @author TruongDV
 */
public enum MessageType {

    UNKNOW_MESSAGE(0),
    REGISTER_MESSAGE(1),
    ACCOUNT_MESSAGE(2),
    ERROR_MESSAGE(3),
    LOGIN_MESSAGE(4),
    SUCCESSFUL_MESSAGE(5),
    QUESTION_MESSAGE(6),
    USER_ANSWER_MESSAGE(7);

    public static MessageType getMessageType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private int value;

    private MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static MessageType getMessageType(int value) {
        switch (value) {
            case 1:
                return REGISTER_MESSAGE;
            case 2:
                return ACCOUNT_MESSAGE;
            case 3:
                return ERROR_MESSAGE;
            case 4:
                return LOGIN_MESSAGE;
            case 5:
                return SUCCESSFUL_MESSAGE;
            case 6:
                return QUESTION_MESSAGE;
            case 7:
                return USER_ANSWER_MESSAGE;
            default:
                return UNKNOW_MESSAGE;
        }
    }
};
