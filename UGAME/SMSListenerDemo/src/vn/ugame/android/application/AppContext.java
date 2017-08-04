package vn.ugame.android.application;

import vn.ugame.model.SMSModel;

public class AppContext {
    private SMSModel smsModel;
    private static AppContext instance;

    public SMSModel getSmsModel() {
	return smsModel;
    }

    public static AppContext getInstance() {
	if (instance == null) {
	    instance = new AppContext();
	}
	return instance;
    }

    private AppContext() {
	smsModel = new SMSModel();
    }

}
