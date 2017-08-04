package ugame.vn.magazine;

import java.io.IOException;

import ugame.vn.magazine.context.SMSContext;
import ugame.vn.magazine.event.ClientSocketHandler;
import ugame.vn.magazine.event.ConnectionErrorListener;
import ugame.vn.magazine.event.MessageListener;
import ugame.vn.magazine.event.SMSEventListener;
import ugame.vn.magazine.model.ErrorMessage;
import ugame.vn.magazine.model.LoginMessage;
import ugame.vn.magazine.model.Message;
import ugame.vn.magazine.model.MessageType;
import ugame.vn.magazine.model.SMSModel;
import ugame.vn.magazine.model.SuccessfulMessage;
import ugame.vn.magazine.utility.PhoneData;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Activation extends Activity {

	private EditText editTextActiveCode;
	private Button buttonActivation;
	private SMSModel smsModel;
	private String phoneNumber;
	private int activeCode;
	private boolean isConnected = true;

	private ClientSocketHandler clientSocketHandler;

	private OnClickListener activationOnclClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			sendLoginToServer();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			smsModel = SMSContext.getInstance().getSmsModel();
		} catch (Exception ex) {
			Log.e("error", ex.getMessage());
			isConnected = false;
			connectionAlert();

		}

		smsModel.setConnectionError(new ConnectionErrorListener() {

			@Override
			public void onConnectionError(Object obj) {
				connectionAlert();
			}
		});
		setContentView(R.layout.activity_activation);
		editTextActiveCode = (EditText) findViewById(R.id.activeCode);
		buttonActivation = (Button) findViewById(R.id.btnActive);
		// process send login data to server
		buttonActivation.setOnClickListener(activationOnclClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activation, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		smsModel.addSMSChangeEventListener(new SMSEventListener() {

			@Override
			public void onReceiptSMS(Object obj) {
				activeCode = Integer.parseInt(smsModel.getSmsBody().toString());
				editTextActiveCode.setText("" + activeCode);
				sendLoginToServer();
			}
		});
	}

	private void sendLoginToServer() {
		try {
			listenMessageFromServer();
		} catch (Exception e) {
			Log.e("error", e.getMessage());
			isConnected = false;
			connectionAlert();
		}
		String tempActiveCode = editTextActiveCode.getText().toString();
		if (tempActiveCode == null
				|| tempActiveCode.trim().equalsIgnoreCase("")) {
			showAlert("Incorrection!", "Active code must have 4 digit!",
					"Confirm");
			return;
		}
		phoneNumber = smsModel.getPhoneNumber();
		activeCode = Integer.parseInt(editTextActiveCode.getText().toString());
		if (activeCode == (int) activeCode) {
			if (1000 > activeCode || activeCode > 9999) {
				showAlert("Incorrection!", "Active code must have 4 digit!",
						"Confirm");
				return;
			} else {
				try {
					LoginMessage loginMessage = null;
					loginMessage = new LoginMessage(
							smsModel.getClientSocketHandler());
					// declare entity of LoginMessage
					loginMessage.setPhoneNumber(phoneNumber);
					loginMessage.setActiveCode(activeCode);
					clientSocketHandler.setMsg(loginMessage);
					clientSocketHandler.sendMessage();
				} catch (Exception e) {
					Log.e("error", e.getMessage());
					isConnected = false;
					connectionAlert();
				}
			}
		} else {
			showAlert("Incorrection!", "Active code la 4 so int", "Confirm");
			return;
		}
	}

	private void listenMessageFromServer() throws IOException {
		clientSocketHandler = smsModel.getClientSocketHandler();
		clientSocketHandler.listen();

		clientSocketHandler.setMesasgeListener(new MessageListener() {

			@Override
			public void onMessage(ClientSocketHandler handler, Message msg) {
				MessageType type = msg.getMsgType();
				switch (type) {
				case ERROR_MESSAGE:
					ErrorMessage errorMessage = (ErrorMessage) msg;
					return;
				case SUCCESSFUL_MESSAGE:
					SuccessfulMessage successfulMessage = (SuccessfulMessage) msg;
					// write info to data file on phone
					PhoneData phoneData = new PhoneData(getApplicationContext());
					String data = "";

					data += phoneData.getIMIENumber() + "\n";
					data += phoneNumber + "\n";
					data += activeCode;
					phoneData.writeFileData(data);

					Intent mainItent = new Intent(getApplicationContext(),
							MainActivity.class);
					mainItent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(mainItent);
					return;
				default:
					break;
				}
			}
		});
	}

	private void showAlert(String title, String alertMessage,
			String contentButton) {
		AlertDialog.Builder alert = new AlertDialog.Builder(Activation.this);
		alert.setTitle(title);
		alert.setMessage(alertMessage);
		alert.setNeutralButton(contentButton,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alert.create().show();

	}

	private void connectionAlert() {
		Log.i("info", "initiate connection error");
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("This app requires a network connection but no connection was detected.");
		alert.setMessage("Connection Fail");
		alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				isConnected = smsModel.resetConnection();

			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alert.create().show();
		// return isConnected;
	}
}
