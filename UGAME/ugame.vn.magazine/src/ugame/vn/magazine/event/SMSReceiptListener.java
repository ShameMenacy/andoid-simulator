package ugame.vn.magazine.event;

import ugame.vn.magazine.context.SMSContext;
import ugame.vn.magazine.model.SMSModel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiptListener extends BroadcastReceiver {

	private static final String SMSGateway = "+8046";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle bundle = intent.getExtras();
			String phoneAddress = null, smsBody = null;
			if (bundle != null) {
				try {
					Object[] pdus = (Object[]) bundle.get("pdus");
					SMSModel smsModel = SMSContext.getInstance().getSmsModel();
					for (int i = 0; i < pdus.length; i++) {
						SmsMessage message = SmsMessage
								.createFromPdu((byte[]) pdus[i]);
						String smsPhone = message.getOriginatingAddress();
						if (SMSGateway.contains(smsPhone)) {
							phoneAddress = smsPhone;
							smsBody = message.getMessageBody();
						}
					}
					smsModel.setPhoneAddress(phoneAddress);
					smsModel.setSmsBody(smsBody);
				} catch (Exception ex) {
					Log.e("Exception caught", ex.getMessage());
				}
			}
		}
	}
}