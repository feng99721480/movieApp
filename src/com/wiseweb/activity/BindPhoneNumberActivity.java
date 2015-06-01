package com.wiseweb.activity;

import com.wiseweb.db.UserSQLiteOpenHelper;
import com.wiseweb.movie.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class BindPhoneNumberActivity extends Activity implements
		OnClickListener, TextWatcher {
	private RelativeLayout titleBack;
	private EditText originalPhoneNumber;
	private EditText newPhoneNumber;
	private EditText verificationCode;
	private ImageView delOriginalPhoneNumber;
	private ImageView delNewPhoneNumber;
	private ImageView delVerificationCode;
	private Button getVerifivationCodeBtn;
	private Button bindBtn;
	private String originalPhoneNumberStr;
	private String newPhoneNumberStr;
	private String verifivationCodeStr;
	private SharedPreferences sp;
	private String nickname;
	private UserSQLiteOpenHelper helper;
	private int count;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				int a = msg.arg1;
				getVerifivationCodeBtn.setText("(" + a + ")" + "重新获取");
				if (a == 0) {
					getVerifivationCodeBtn.setText("重获验证码");
				}
				break;

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_phone_number);
		sp = getSharedPreferences("userInfo", MODE_PRIVATE);
		nickname = sp.getString("nickname", "");
		initUI();

	}

	private void initUI() {
		helper = new UserSQLiteOpenHelper(BindPhoneNumberActivity.this);
		bindBtn = (Button) findViewById(R.id.bind_btn);
		getVerifivationCodeBtn = (Button) findViewById(R.id.get_verification_code);
		delOriginalPhoneNumber = (ImageView) findViewById(R.id.delete_original_phone_number);
		delNewPhoneNumber = (ImageView) findViewById(R.id.delete_new_phone_number);
		delVerificationCode = (ImageView) findViewById(R.id.delete_verification_code);
		originalPhoneNumber = (EditText) findViewById(R.id.pull_original_phone_number);
		newPhoneNumber = (EditText) findViewById(R.id.pull_new_phone_number);
		verificationCode = (EditText) findViewById(R.id.pull_verification_code);
		titleBack = (RelativeLayout) findViewById(R.id.bind_phone_number_title_back);
		titleBack.setOnClickListener(this);
		delOriginalPhoneNumber.setOnClickListener(this);
		delNewPhoneNumber.setOnClickListener(this);
		delVerificationCode.setOnClickListener(this);
		getVerifivationCodeBtn.setOnClickListener(this);
		bindBtn.setOnClickListener(this);
		originalPhoneNumber.addTextChangedListener(this);
		newPhoneNumber.addTextChangedListener(this);
		verificationCode.addTextChangedListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bind_phone_number_title_back:
			setResult(0, null);
			finish();
			break;
		case R.id.get_verification_code:
			originalPhoneNumberStr = originalPhoneNumber.getText().toString()
					.trim();
			newPhoneNumberStr = newPhoneNumber.getText().toString().trim();
			if (originalPhoneNumberStr.equals(newPhoneNumberStr)) {
				Toast.makeText(BindPhoneNumberActivity.this, "两个手机号请不要相同", 0)
						.show();
			} else {
				if (originalPhoneNumberStr.length() != 11) {
					Toast.makeText(BindPhoneNumberActivity.this, "原手机号输入有误", 0)
							.show();
				} else {
					SQLiteDatabase db = helper.getReadableDatabase();
					Cursor cursor = db.query("user",
							new String[] { "username" }, "username = ?",
							new String[] { originalPhoneNumberStr }, null,
							null, null);
					if (cursor.moveToFirst() == false) {
						dialogForWrongPhoneNumber();
					} else {
						// 倒计时
						countDown();
						//此处应该通知服务器向手机发送验证码
					}
					cursor.close();
					db.close();
				}

			}

			break;
		case R.id.bind_btn:// 修改数据库username
			verifivationCodeStr = verificationCode.getText().toString().trim();
			//此处应该验证一下验证码时候与服务器发出的一致
			if(verifivationCodeStr.equals("123456")){
				updateUsername(newPhoneNumberStr);

				// 修改sharedPreference保存的username
				Editor editor = sp.edit();
				editor.remove("username");
				editor.putString("username", newPhoneNumberStr);
				editor.commit();
				// 回调方法
				Intent intent = getIntent();
				intent.putExtra("username", newPhoneNumberStr);
				setResult(5, intent);
				finish();
			}else{
				Toast.makeText(BindPhoneNumberActivity.this, "验证码错误", 0)
				.show();
			}

			break;
		case R.id.delete_original_phone_number:
			originalPhoneNumber.setText("");
			break;
		case R.id.delete_new_phone_number:
			newPhoneNumber.setText("");
			break;
		case R.id.delete_verification_code:
			verificationCode.setText("");
			break;
		}
	}

	// 输入框监听
	@Override
	// 输入前
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		isEmpty(originalPhoneNumber, delOriginalPhoneNumber);
		isEmpty(newPhoneNumber, delNewPhoneNumber);
		isEmpty(verificationCode, delVerificationCode);
		getVerifivationCodeBtn.setClickable(false);
		bindBtn.setClickable(false);
	}

	@Override
	// 输入后
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		isEmpty(originalPhoneNumber, delOriginalPhoneNumber);
		isEmpty(newPhoneNumber, delNewPhoneNumber);
		isEmpty(verificationCode, delVerificationCode);
		if (newPhoneNumber.getText().toString().trim().length() == 11) {
			getVerifivationCodeBtn.setClickable(true);
			getVerifivationCodeBtn
					.setBackgroundResource(R.drawable.cashier__button);
		}
		if (verificationCode.getText().toString().trim().length() > 4) {
			bindBtn.setClickable(true);
			bindBtn.setBackgroundResource(R.drawable.btn_view_schedule_and_tickets);
		}
	}

	
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	// 判断输入框里是否有内容 以确定图片的显示、隐藏
	private void isEmpty(EditText v1, ImageView v2) {
		if (TextUtils.isEmpty(v1.getText().toString())) {
			v2.setVisibility(View.INVISIBLE);
		} else {
			v2.setVisibility(View.VISIBLE);
		}
	}

	// 弹出对话框
	public void dialogForWrongPhoneNumber() {
		AlertDialog.Builder builder = new Builder(BindPhoneNumberActivity.this);
		builder.setTitle("获取验证码失败");
		builder.setMessage("已绑定手机号输入错误，请重新输入");
		builder.setPositiveButton("知道了", null);
		builder.show();
	}

	// 倒计时60秒
	public int countDown() {
		Thread t = new Thread() {
			@Override
			public void run() {
				for (int i = 60; i >= 0; i--) {
					count = i;
					Message msg = handler.obtainMessage();
					msg.what = 1;
					msg.arg1 = i;
					handler.sendMessage(msg);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
		// t.join();
		return count;
	}

	// 修改数据库username
	private void updateUsername(String newPhoneNumberStr1) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("username", newPhoneNumberStr1);
		db.update("user", values, "nickname=?", new String[] { nickname });
		db.close();
	}

}
