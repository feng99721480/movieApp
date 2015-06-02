package com.wiseweb.activity;

import com.wiseweb.db.UserSQLiteOpenHelper;
import com.wiseweb.movie.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

public class FindPasswordActivitySecond extends Activity {

	private String username;
	private EditText pullNumOrNicknameSecond;
	private UserSQLiteOpenHelper helper;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_password_second);
		//接收上个activity传递的参数
		Intent intent = getIntent();
		String numOrNickname = intent.getStringExtra("numOrNickname");
		System.out.println("numOrNickname------------------------------"
				+ numOrNickname);

		initUI();
		// 通过numOrNickname查询数据库username字段
		username = findUsername(numOrNickname);
		pullNumOrNicknameSecond.setText(username);

	}

	private void initUI() {
		helper = new UserSQLiteOpenHelper(FindPasswordActivitySecond.this);
		pullNumOrNicknameSecond = (EditText) findViewById(R.id.pull_phone_number_or_nickname_second);

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
}
