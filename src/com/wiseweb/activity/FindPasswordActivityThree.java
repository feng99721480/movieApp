package com.wiseweb.activity;

import com.wiseweb.db.UserSQLiteOpenHelper;
import com.wiseweb.movie.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FindPasswordActivityThree extends Activity implements OnClickListener {
	
	private RelativeLayout titleBack;
	private EditText setPassword;
	private EditText setPasswordAgain;
	private Button resetPasswordBtn;
	private String username;
	private UserSQLiteOpenHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_password_three);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		
		initUI();
	}

	private void initUI() {
		helper = new UserSQLiteOpenHelper(FindPasswordActivityThree.this);
		resetPasswordBtn = (Button) findViewById(R.id.reset_password_btn);
		setPasswordAgain = (EditText) findViewById(R.id.set_password_again);
		setPassword = (EditText) findViewById(R.id.set_password);
		titleBack = (RelativeLayout) findViewById(R.id.find_password_title_back);
		titleBack.setOnClickListener(this);
		resetPasswordBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.find_password_title_back://返回
			finish();
			break;

		case R.id.reset_password_btn://重置按钮
			String password = setPassword.getText().toString().trim();
			String passwordAgain = setPasswordAgain.getText().toString().trim();
			if(password.length() < 6 || password.length() > 32){
				Toast.makeText(FindPasswordActivityThree.this, "密码长度应在6-32个字符之间", 1).show();
			}else{
				if(passwordAgain.equals(password)){
					//修改数据库password
					updatePassword(password);
					Toast.makeText(FindPasswordActivityThree.this, "密码修改成功", 0).show();
					Intent intent = new Intent();
					intent.setClass(FindPasswordActivityThree.this, LoginActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(FindPasswordActivityThree.this, "两次输入不一致", 0).show();
				}
			}
			break;
		}
	}
	//改变数据库password
	private void updatePassword(String passwordStr){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("password", passwordStr);
		db.update("user", values, "username = ?", new String[] { username });
		db.close();
	}
}
