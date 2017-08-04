package ugame.vn.magazine;

import ugame.vn.magazine.context.SMSContext;
import ugame.vn.magazine.model.UserSettingModel;
import ugame.vn.magazine.utility.PhoneData;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	private boolean access;
	private UserSettingModel userSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			userSetting = SMSContext.getInstance().getUserSettingModel();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("e", e.getMessage(), e);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// funct get emei + check file exist
		// PhoneData phoneData = new PhoneData(getApplicationContext());
		// String emei = phoneData.getIMIENumber();

		checkAccess();
		if (this.access == false) {
			Intent invalidWayIntent = new Intent(getApplicationContext(),
					InvalidWay.class);
			startActivity(invalidWayIntent);
		} else {
			Intent userFilter = new Intent(getApplicationContext(),
					UserFilterActivity.class);
			startActivity(userFilter);
		}
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
		alert.setTitle("Exit Confirm!");
		alert.setMessage("Are you really want to exits?");
		alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		alert.setPositiveButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert.create().show();
	};

	public void checkAccess() {
		Log.i("i","start check access");		
		PhoneData phoneData = new PhoneData(getApplicationContext());
		if (phoneData.checkFileExitance() == true) {
			String imei = phoneData.getIMIENumber();
			String fileData = phoneData.readFileData();
			if (fileData != null && fileData.contains(imei)) {
				this.access = true;
				String userInfo[] = fileData.split("\n");
				String imagePhone = userInfo[0];
				String tempPhoneNumber = userInfo[1];
				String tempActiveCode = userInfo[2];
				int activeCode = Integer.parseInt(tempActiveCode);
				Log.i("i","active code: "+activeCode);
				Log.i("i","phone number: "+tempPhoneNumber);
				userSetting.setPhoneNumber(tempPhoneNumber);
				userSetting.setActiveCode(activeCode);
			}
		} else {
			this.access = false;
		}
	}

}
