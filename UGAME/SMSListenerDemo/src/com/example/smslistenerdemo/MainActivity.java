package com.example.smslistenerdemo;

import vn.ugame.android.application.AppContext;
import vn.ugame.model.PropertyChangedListener;
import vn.ugame.model.SMSModel;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private SMSModel smsModel;
    private TextView smsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	smsTextView = (TextView) findViewById(R.id.message_text_view);
	smsModel = AppContext.getInstance().getSmsModel();
	smsModel.addSMSChangedListener(new PropertyChangedListener() {

	    @Override
	    public void onPropertyChanged(Object sender) {
		smsTextView.setText(smsModel.getSms());
	    }
	});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }
}
