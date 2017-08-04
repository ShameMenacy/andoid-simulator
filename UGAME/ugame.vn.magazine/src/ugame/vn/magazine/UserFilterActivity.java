package ugame.vn.magazine;

import java.util.ArrayList;
import java.util.List;

import ugame.vn.magazine.context.SMSContext;
import ugame.vn.magazine.entity.Answer;
import ugame.vn.magazine.entity.Question;
import ugame.vn.magazine.event.ClientSocketHandler;
import ugame.vn.magazine.event.ConnectionErrorListener;
import ugame.vn.magazine.event.MessageListener;
import ugame.vn.magazine.model.Message;
import ugame.vn.magazine.model.MessageType;
import ugame.vn.magazine.model.QuestionMessage;
import ugame.vn.magazine.model.SMSModel;
import ugame.vn.magazine.model.UserAnswerMessage;
import ugame.vn.magazine.model.UserSettingModel;
import ugame.vn.magazine.utility.PhoneData;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class UserFilterActivity extends Activity {

	private SMSModel smsModel;
	private int CURRENT_QUESTION = 1;
	private static int NUMBER_ITEM_IN_PORTRAIT = 3;
	private static int NUMBER_ITEM_IN_LANDSCAPE = 4;
	private UserSettingModel userSettingModel;
	private boolean isConnected = true;

	TableLayout tableLayout_AnswerContent;
	RelativeLayout layout_Continue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_user_filter);

			tableLayout_AnswerContent = (TableLayout) findViewById(R.id.UserFilter_TableLayout_AnswerContent);
			layout_Continue = (RelativeLayout) findViewById(R.id.layout_continue);

			smsModel = SMSContext.getInstance().getSmsModel();
			userSettingModel = SMSContext.getInstance().getUserSettingModel();
			smsModel.setConnectionError(new ConnectionErrorListener() {

				@Override
				public void onConnectionError(Object obj) {
					connectionAlert();
				}
			});

			smsModel.connect();
			smsModel.getClientSocketHandler().listen();
			smsModel.getClientSocketHandler().setMesasgeListener(
					new MessageListener() {
						@Override
						public void onMessage(ClientSocketHandler handler,
								Message msg) {
							MessageType type = msg.getMsgType();
							switch (type) {
							case QUESTION_MESSAGE:
								QuestionMessage questionMsg = (QuestionMessage) msg;
								userSettingModel.setQuestionList(questionMsg
										.getQuestionList());
								break;
							default:
								break;
							}
						}
					});

			if (userSettingModel.getQuestionList() == null
					|| userSettingModel.getQuestionList().isEmpty()) {
				sendRequestGetMessageToServer();
			}
		} catch (Exception e) {
		  Log.e("e",e.getMessage(),e);
		  isConnected = false;
		  connectionAlert();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("info", isConnected + "");
		if (isConnected) {
			Log.i("info", "initData");
			initTableData();
		}

		layout_Continue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadAnswer();
			}
		});
	}

	private void initTableData() {
		checkQuestion();
		int margin = (int) getResources().getDimension(
				R.dimen.margin_MEDIUM_10dp);
		int displayNumber = NUMBER_ITEM_IN_PORTRAIT;
		int orientation = getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			displayNumber = NUMBER_ITEM_IN_LANDSCAPE;
		}

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		float width;
		float height;
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			display.getSize(size);
			width = size.x;
			height = size.y;
		} else {
			width = display.getWidth();
			height = display.getHeight();
		}

		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tableLayout_AnswerContent
				.getLayoutParams();
		int itemMargins = (displayNumber - 1) * margin * 2;
		int layoutMargins = lp.leftMargin + lp.rightMargin;
		width = width - itemMargins - layoutMargins;
		float itemSize = width / displayNumber;
		while (userSettingModel.getQuestionList() == null
				|| userSettingModel.getQuestionList().isEmpty()) {
			continue;
		}
		tableLayout_AnswerContent.removeAllViews();
		TableRow row = null;

		int itemLength = 0;
		List<Answer> answerList = new ArrayList<Answer>();
		Question question;
		switch (CURRENT_QUESTION) {
		case (1):
			question = userSettingModel.getQuestionList().get(0);
			answerList = question.getAnswerList();
			this.setTitle(question.getQuestionContent());
			break;
		case (2):
			question = userSettingModel.getQuestionList().get(1);
			answerList = question.getAnswerList();
			this.setTitle(question.getQuestionContent());
			break;
		case (3):
			question = userSettingModel.getQuestionList().get(2);
			answerList = question.getAnswerList();
			this.setTitle(question.getQuestionContent());
			break;
		default:
			break;
		}

		if (answerList != null) {
			itemLength = answerList.size();
		}

		for (int i = 0; i < itemLength; i++) {
			if (i % displayNumber == 0) {
				row = new TableRow(getApplicationContext());
				TableLayout.LayoutParams params = new TableLayout.LayoutParams(
						TableLayout.LayoutParams.MATCH_PARENT,
						TableLayout.LayoutParams.WRAP_CONTENT);
				params.setMargins(0, margin, 0, margin);
				row.setLayoutParams(params);
				tableLayout_AnswerContent.addView(row);
			}
			View itemView = getLayoutInflater().inflate(
					R.layout.view_userfilter_answeritem, null, false);
			Answer answer = answerList.get(i);
			itemView.setId(answer.getAnswerId());
			itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					boolean isSelected = v.isSelected();
					v.setSelected(!isSelected);
					if (!isSelected) {
						userSettingModel.addAnswer(v.getId());
					} else {
						userSettingModel.removeAnswer(v.getId());
					}

				}
			});
			if (userSettingModel.getSelectedAnswerList().contains(
					itemView.getId())) {
				itemView.setSelected(true);
			}

			String title = answerList.get(i).getAnswerContent();
			TextView tvTitle = (TextView) itemView
					.findViewById(R.id.toggle_tvTitle);
			tvTitle.setText(title);

			TableRow.LayoutParams itemParams = new TableRow.LayoutParams(
					(int) itemSize, (int) itemSize);
			if (i % displayNumber == 0) {
				itemParams.setMargins(0, 0, margin, 0);
			} else if ((i + 1) % displayNumber == 0) {
				itemParams.setMargins(margin, 0, 0, 0);
			} else {
				itemParams.setMargins(margin, 0, margin, 0);
			}
			itemView.setLayoutParams(itemParams);
			row.addView(itemView);
		}
	}

	private void checkQuestion() {
		Intent prevIntent = getIntent();
		Bundle prevBundle = prevIntent.getBundleExtra("bundle");
		if (prevBundle != null) {
			this.CURRENT_QUESTION = prevBundle.getInt("currentInt");
		} else {
			this.CURRENT_QUESTION = 1;
		}

	}

	private void loadAnswer() {
		if (isConnected == false) {
			connectionAlert();
			return;
		}

		if (CURRENT_QUESTION < 3) {
			Intent answerIntent = new Intent(getApplication(),
					UserFilterActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("currentInt", CURRENT_QUESTION += 1);
			answerIntent.putExtra("bundle", bundle);
			startActivity(answerIntent);
		} else {
			writeUserAnswer();
		}
	}

	private void sendRequestGetMessageToServer() {
		try {
			QuestionMessage questionRequestMsg = new QuestionMessage(
					smsModel.getClientSocketHandler());
			PhoneData phoneData = new PhoneData(getApplicationContext());

			// TODO fix phoneNumber exit in DB
			questionRequestMsg.setPhoneNumber("0936194388");
			questionRequestMsg.setUserAppList(phoneData
					.getCurrentAppInstalling());
			smsModel.getClientSocketHandler().setMsg(questionRequestMsg);
			smsModel.getClientSocketHandler().sendMessage();
		} catch (Exception e) {
			Log.e("err", e.getMessage());
		}
	}

	private void writeUserAnswer() {
		try {
			if (userSettingModel.getSelectedAnswerList() != null
					|| !userSettingModel.getSelectedAnswerList().isEmpty()) {								
				UserAnswerMessage answerMessage = new UserAnswerMessage(
						smsModel.getClientSocketHandler());
				String phoneNumber = userSettingModel.getPhoneNumber();
				answerMessage.setPhoneNumber(phoneNumber);
				answerMessage.setAnswerList(userSettingModel
						.getSelectedAnswerList());

				smsModel.getClientSocketHandler().setMsg(answerMessage);
				smsModel.getClientSocketHandler().sendMessage();
			}

		} catch (Exception e) {
			Log.e("err", e.getMessage(), e);
			connectionAlert();
		}
		Intent answerIntent = new Intent(getApplication(), InvalidWay.class);
		startActivity(answerIntent);
	}
	

	private void connectionAlert() {
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
	}

}
