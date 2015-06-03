package com.wiseweb.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.wiseweb.db.UserSQLiteOpenHelper;
import com.wiseweb.movie.R;
import com.wiseweb.movie.R.color;
import com.wiseweb.util.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private RelativeLayout mRegisterTitleBack;
	private EditText pullPhoneNumber;
	private ImageView deletePhoneNumber;
	private ImageButton imgButton;
	private boolean flag = false;
	private Button getCodeBtn;
	private UserSQLiteOpenHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);
		// 初始化组件
		init();
	}

	private void init() {
		helper = new UserSQLiteOpenHelper(RegisterActivity.this);
		getCodeBtn = (Button) findViewById(R.id.get_code);
		// 点击按钮 获取验证码
		getCodeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String phoneNumberStr = pullPhoneNumber.getText().toString()
						.trim();
				byte[] number = phoneNumberStr.getBytes();

				if (!Util.isMobileNum(phoneNumberStr)) {
					// Toast.makeText(RegisterActivity.this,
					// "请输入11位正确的手机号码",
					// 0).show();
					dialogForWrongNumber();
				} else {
					if (!flag) {
						//此处服务器应该向用户手机发送验证码
						Toast.makeText(RegisterActivity.this, "正在向服务器请求验证码。。。",
								0).show();
						//讲用户名存入数据库
						insert(phoneNumberStr);
						//讲用户名保存 用于RegisterActivityThree
						SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
						Editor editor = sp.edit();
						editor.putString("username", phoneNumberStr);
						editor.commit();
						
						Intent intent = new Intent();
						intent.putExtra("phoneNumber", phoneNumberStr);
						intent.setClass(RegisterActivity.this,
								RegisterActivitySecond.class);
						startActivity(intent);
					} else {
						dialogForAgreement();
					}

				}

			}
		});
		imgButton = (ImageButton) findViewById(R.id.imgButton);
		// 设置imageButton的图片切换
		imgButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!flag) {
					imgButton
							.setBackgroundResource(R.drawable.ic_choose_unselect);
					flag = true;
				} else {
					imgButton
							.setBackgroundResource(R.drawable.ic_checkbox_photo_selected);
					flag = false;
				}
			}
		});
		pullPhoneNumber = (EditText) findViewById(R.id.pull_phone_number);

		deletePhoneNumber = (ImageView) findViewById(R.id.delete_phone_number);
		// 点击图片逐个删除editText里的内容
		deletePhoneNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 动作按下
				int action = KeyEvent.ACTION_DOWN;
				// code:删除，其他code也可以，例如 code = 0
				int code = KeyEvent.KEYCODE_DEL;
				KeyEvent event = new KeyEvent(action, code);
				pullPhoneNumber.onKeyDown(KeyEvent.KEYCODE_DEL, event);
			}
		});
		// 长按图片全部删除
		deletePhoneNumber.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				pullPhoneNumber.setText("");
				pullPhoneNumber.clearFocus();
				return false;
			}
		});
		// 输入框监听事件
		pullPhoneNumber.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				isEmpty();

			}

			// 输入前
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

				isEmpty();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		//点击输入框显示软键盘
//		pullPhoneNumber.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				System.out.println("_______________");
//				pullPhoneNumber.requestFocus();
//
//				Timer timer = new Timer();
//				timer.schedule(new TimerTask() {
//
//					@Override
//					public void run() {
//						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//						// imm.toggleSoftInput(0,
//						// InputMethodManager.HIDE_NOT_ALWAYS);
////						imm.showSoftInput(pullPhoneNumber,
////								InputMethodManager.SHOW_FORCED);
//						imm.showSoftInputFromInputMethod(pullPhoneNumber.getWindowToken(), 0);
//					}
//				}, 998);
//			}
//		});
		mRegisterTitleBack = (RelativeLayout) findViewById(R.id.register_title_back);
		// 点击返回上一个activity
		mRegisterTitleBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	// 弹出对话框
	public void dialogForAgreement() {
		AlertDialog.Builder builder = new Builder(RegisterActivity.this);
		builder.setMessage("您必须同意抠电影用户协议才能进行下一步操作");
		builder.setPositiveButton("确定", null);
		builder.show();
	}

	// 弹出对话框
	public void dialogForWrongNumber() {
		AlertDialog.Builder builder = new Builder(RegisterActivity.this);
		builder.setMessage("      请输入11位正确的手机号码");
		builder.setPositiveButton("确定", null);
		builder.show();
	}

	// 判断输入框里是否有内容 以确定图片的显示、隐藏
	private void isEmpty() {
		if (TextUtils.isEmpty(pullPhoneNumber.getText().toString())) {
			deletePhoneNumber.setVisibility(View.INVISIBLE);
		} else {
			deletePhoneNumber.setVisibility(View.VISIBLE);
		}
	}
	//讲用户名存入数据库
	public void insert(String username){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("insert into user (username) values (?)", new Object[] {username});
		db.close();
	}
}
