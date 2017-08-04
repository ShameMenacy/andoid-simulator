package ugame.vn.magazine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InvalidWay extends Activity {
	Button buttonConfirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invalid_way);

		buttonConfirm = (Button) findViewById(R.id.button_invalidway_confirm);
		buttonConfirm.setOnClickListener(buttonConfirmOnclickListener);
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder alert = new AlertDialog.Builder(InvalidWay.this);
		alert.setTitle("Exit Confirm!");
		alert.setMessage("Are you really want to exits?");
		alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent mainItent = new Intent(getApplicationContext(),
						MainActivity.class);
				mainItent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(mainItent);
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

	// Event buttonConfirm onclick
	OnClickListener buttonConfirmOnclickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// Open Registration Activity
			Intent intent = new Intent(getApplicationContext(),
					Registration.class);
			startActivity(intent);
		}
	};
}
