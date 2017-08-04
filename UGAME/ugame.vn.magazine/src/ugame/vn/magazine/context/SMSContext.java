package ugame.vn.magazine.context;

import java.io.IOException;
import java.net.UnknownHostException;

import android.util.Log;

import ugame.vn.magazine.model.SMSModel;
import ugame.vn.magazine.model.UserSettingModel;

public class SMSContext {
	private static SMSContext instance;	
	private SMSModel smsModel;
	private UserSettingModel userSettingModel;

	public SMSModel getSmsModel() {
		return smsModel;
	}
	
	public UserSettingModel getUserSettingModel(){
		return userSettingModel;
	}

	public static SMSContext getInstance() throws Exception {
		if (instance == null) {
			instance = new SMSContext();
		}
		return instance;
	}

	public SMSContext() throws Exception {
		smsModel = new SMSModel();		
		userSettingModel = new UserSettingModel();

	}

	public void Connect()throws Exception {
		smsModel.connect();
	}

}
