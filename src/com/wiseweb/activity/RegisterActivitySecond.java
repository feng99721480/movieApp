package com.wiseweb.activity;

import com.wiseweb.movie.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegisterActivitySecond extends Activity {

	private RelativeLayout mRegisterTitleBack;
	private Button getCodeAgainBtn;
	private int count;
	private TextView sendCodeTv;
	private String phoneNumber;
	private EditText pullCode;
	private ImageView deleteCode;
	private Button pushCodeBtn;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				int a = msg.arg1;
				getCodeAgainBtn.setText("(" + a + ")" + "重新获取");
				if (a == 0) {
					getCodeAgainBtn.setText("重获验证码");
				}
				break;

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout_second);
		Intent intent = this.getIntent();
		phoneNumber = intent.getStringExtra("phoneNumber");
		init();
		countDown();
	}

	private void init() {
		pushCodeBtn = (Button) findViewById(R.id.push_code);
		// 点击提交验证码按钮监听事件
		pushCodeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String code = pullCode.getText().toString().trim();
				if (TextUtils.isEmpty(code)) {
					dialogForWrongCode();
				}
				if(code.equals("123456")){
					Intent intent = new Intent();
					intent.setClass(RegisterActivitySecond.this, RegisterActivityThree.class);
					startActivity(intent);
				}
			}
		});
		deleteCode = (ImageView) findViewById(R.id.delete_code);
		// 点击图片逐个删除editText里的内容
		deleteCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 动作按下
				int action = KeyEvent.ACTION_DOWN;
				// code:删除，其他code也可以，例如 code = 0
				int code = KeyEvent.KEYCODE_DEL;
				KeyEvent event = new KeyEvent(action, code);
				pullCode.onKeyDown(KeyEvent.KEYCODE_DEL, event);
			}
		});
		// 长按图片全部删除
		deleteCode.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				pullCode.setText("");
				pullCode.clearFocus();
				return false;
			}
		});
		sendCodeTv = (TextView) findViewById(R.id.send_code_tv);
		sendCodeTv.setText("验证码短信已发送到手机" + phoneNumber);
		getCodeAgainBtn = (Button) findViewById(R.id.get_code_again);
		getCodeAgainBtn.setVisibility(View.VISIBLE);
		// 点击重新获取按钮监听事件
		getCodeAgainBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (getCodeAgainBtn.getText().toString().equals("重获验证码")) {
					countDown();
				}
			}
		});
		// 输入框监听事件
		pullCode = (EditText) findViewById(R.id.pull_code);
		pullCode.addTextChangedListener(new TextWatcher() {

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
	public void dialogForWrongCode() {
		AlertDialog.Builder builder = new Builder(RegisterActivitySecond.this);
		builder.setMessage("     验证码输入错误，请重新输入");
		builder.setPositiveButton("确定", null);
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

	// 判断输入框里是否有内容 以确定图片的显示、隐藏
	private void isEmpty() {
		if (TextUtils.isEmpty(pullCode.getText().toString())) {
			deleteCode.setVisibility(View.INVISIBLE);
		} else {
			deleteCode.setVisibility(View.VISIBLE);
		}
	}
}
