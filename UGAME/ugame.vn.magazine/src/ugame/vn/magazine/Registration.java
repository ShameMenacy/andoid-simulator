package ugame.vn.magazine;

import ugame.vn.magazine.context.SMSContext;
import ugame.vn.magazine.event.ConnectionErrorListener;
import ugame.vn.magazine.model.RegisterMessage;
import ugame.vn.magazine.model.SMSModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class Registration extends Activity {
	private EditText editTextPhoneNumber;
	private TextView buttonRegistration;

	private SMSModel smsModel;
	private String phoneNumber;
	private boolean isConnected = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		ViewGroup layoutForm = (ViewGroup) findViewById(R.id.layout_form);
		editTextPhoneNumber = (EditText) layoutForm
				.findViewById(R.id.editText_registration_phoneNumber);
		buttonRegistration = (TextView) layoutForm
				.findViewById(R.id.button_registration_Continue);

		buttonRegistration
				.setOnClickListener(buttonRegistrationOnClickListener);

		try {
			smsModel = SMSContext.getInstance().getSmsModel();
		} catch (Exception ex) {
			Log.e("error", ex.getMessage());
		}

		smsModel.setConnectionError(new ConnectionErrorListener() {
			@Override
			public void onConnectionError(Object obj) {
				isConnected = false;
				connectionAlert();
			}
		});

		try {
			smsModel.connect();
		} catch (Exception ex) {
			isConnected = false;
			Log.e("error", ex.getMessage());
			connectionAlert();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	// Event when register
	private OnClickListener buttonRegistrationOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (isConnected == false) {
				connectionAlert();
				return;
			}
			smsModel.getClientSocketHandler();
			phoneNumber = editTextPhoneNumber.getText().toString();
			if (phoneNumber.length() >= 10) {
				smsModel.setPhoneNumber(phoneNumber);
				sendPhoneNumberToServer();
				Intent activeIntent = new Intent(getApplicationContext(),
						Activation.class);
				startActivity(activeIntent);
			} else {
				showAlert("Incorrected", "Phone number incorrected", "Confirm");
				return;
			}
		}
	};

	// recomend change name to register.
	private void sendPhoneNumberToServer() {
		try {
			RegisterMessage message = new RegisterMessage(
					smsModel.getClientSocketHandler());
			message.setPhoneNumber(phoneNumber);
			smsModel.getClientSocketHandler().setMsg(message);
			smsModel.getClientSocketHandler().sendMessage();
		} catch (Exception ex) {
			Log.e("error", ex.getMessage());
		}
	}

	private void connectionAlert() {
		Log.i("info", "initiate connection error");
		AlertDialog.Builder alert = new AlertDialog.Builder(Registration.this);
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

	private void showAlert(String title, String alertMessage,
			String contentButton) {
		AlertDialog.Builder alert = new AlertDialog.Builder(Registration.this);
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

}
