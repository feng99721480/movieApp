package com.wiseweb.activity;

import com.wiseweb.db.UserSQLiteOpenHelper;
import com.wiseweb.movie.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FindPasswordActivitySecond extends Activity implements
		OnClickListener {

	private String username;
	private EditText pullNumOrNicknameSecond;
	private UserSQLiteOpenHelper helper;
	private RelativeLayout titleBack;
	private Button sendCode;
	private int count;
	private EditText pullCode;
	private Button nextBtn;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				int a = msg.arg1;
				sendCode.setText("(" + a + ")");
				if (a == 0) {
					sendCode.setText("重获验证码");
				}
				break;

			}
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_password_second);
		// 接收上个activity传递的参数
		Intent intent = getIntent();
		String numOrNickname = intent.getStringExtra("numOrNickname");
		System.out.println("numOrNickname------------------------------"
				+ numOrNickname);

		initUI();
		// 通过numOrNickname查询数据库username字段
		username = findUsername(numOrNickname);
		String newUsername = toXing(username);
		pullNumOrNicknameSecond.setText(newUsername);

	}

	private void initUI() {
		nextBtn = (Button) findViewById(R.id.next_btn);
		pullCode = (EditText) findViewById(R.id.pull_code_from_img);
		sendCode = (Button) findViewById(R.id.send_code_second);
		titleBack = (RelativeLayout) findViewById(R.id.find_password_title_back);
		helper = new UserSQLiteOpenHelper(FindPasswordActivitySecond.this);
		pullNumOrNicknameSecond = (EditText) findViewById(R.id.pull_phone_number_or_nickname_second);
		titleBack.setOnClickListener(this);
		sendCode.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
	}

	// 通过findpasswordactivity传过来的参数查询数据库的username
	private String findUsername(String numOrNicknameStr) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("user", new String[] { "username" },
				"username = ? or nickname = ?", new String[] {
						numOrNicknameStr, numOrNicknameStr }, null, null, null);
		String usernameStr = "";
		while (cursor.moveToNext()) {
			usernameStr = cursor.getString(cursor.getColumnIndex("username"));
		}
		cursor.close();
		db.close();
		return usernameStr;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.find_password_title_back:// 返回
			finish();
			break;

		case R.id.send_code_second:// 获取验证码
			countDown();
			//此处应该实现服务器向用户发送验证码短信
			
			break;
		case R.id.next_btn:
		String code = pullCode.getText().toString().trim();
		if(code.equals("123456")){
			Intent intent = new Intent();
			intent.putExtra("username", username);
			intent.setClass(FindPasswordActivitySecond.this, FindPasswordActivityThree.class);
			startActivity(intent);
		}else{
			Toast.makeText(FindPasswordActivitySecond.this, "验证码不正确，请重新输入", 0).show();
		}
		break;
		}

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
	//将手机号中间4位换成*
		private String toXing(String usernameStr){
			String newUsername = usernameStr;
			String s1 = usernameStr.substring(0, 3);
			String s2 = usernameStr.substring(3, 7);
			String s3 = usernameStr.substring(7, 11);
			StringBuffer b=new StringBuffer();
			for(int i=0;i<s2.length();i++){
				b.append("*");
			}
			newUsername = s1+b.toString()+s3;
			System.out.println("s1="+s1+",s2="+s2+",s3="+s3+",b="+b);
			System.out.println("newUsername============="+newUsername);
			return newUsername;
		}
}
