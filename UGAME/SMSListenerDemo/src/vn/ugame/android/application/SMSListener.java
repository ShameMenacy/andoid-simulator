package vn.ugame.android.application;

import vn.ugame.model.SMSModel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
	if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
	    Bundle bundle = intent.getExtras();
	    if (bundle != null) {
		try {
		    Object[] pdus = (Object[]) bundle.get("pdus");
		    SMSModel smsModel = AppContext.getInstance().getSmsModel();
		    String sms = "";
		    for (int i = 0; i < pdus.length; i++) {
			SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[i]);
			String from = message.getOriginatingAddress();
			String content = message.getMessageBody();
			sms = sms + from + "\n";
			sms = sms + content + "\n\n";
		    }
		    smsModel.setSms(sms);
		} catch (Exception ex) {
		    Log.e("Exception caught", ex.getMessage());
		}
	    }
	}
    }
}
