package com.wiseweb.activity;

import com.wiseweb.db.UserSQLiteOpenHelper;
import com.wiseweb.movie.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class ModifyPasswordActivity extends Activity implements
		OnClickListener, TextWatcher {

	private RelativeLayout titleBack;
	private EditText pullCurrentPwd;
	private EditText pullNewPwd;
	private EditText pullNewPwdAgain;
	private Button surePwdBtn;
	private UserSQLiteOpenHelper helper;
	private SharedPreferences sp;
	private String nickname;
	private ImageView delCurrentPwd;
	private ImageView delNewPwd;
	private ImageView delNewPwdAgain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_password);
		sp = getSharedPreferences("userInfo", MODE_PRIVATE);
		nickname = sp.getString("nickname", "");
		initUI();
	}

	private void initUI() {
		delNewPwdAgain = (ImageView) findViewById(R.id.delete_new_pwd_again);
		delNewPwd = (ImageView) findViewById(R.id.delete_new_pwd);
		delCurrentPwd = (ImageView) findViewById(R.id.delete_current_pwd);
		helper = new UserSQLiteOpenHelper(ModifyPasswordActivity.this);
		pullCurrentPwd = (EditText) findViewById(R.id.pull_current_pwd);
		pullNewPwd = (EditText) findViewById(R.id.pull_new_pwd);
		pullNewPwdAgain = (EditText) findViewById(R.id.pull_new_pwd_again);
		titleBack = (RelativeLayout) findViewById(R.id.modify_password_title_back);
		surePwdBtn = (Button) findViewById(R.id.sure_password_btn);
		titleBack.setOnClickListener(this);
		surePwdBtn.setOnClickListener(this);
		delCurrentPwd.setOnClickListener(this);
		delNewPwd.setOnClickListener(this);
		delNewPwdAgain.setOnClickListener(this);
		pullCurrentPwd.addTextChangedListener(this);
		pullNewPwd.addTextChangedListener(this);
		pullNewPwdAgain.addTextChangedListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.modify_password_title_back:// 返回
			finish();
			break;

		case R.id.sure_password_btn:// 确定
			String currentPwd = pullCurrentPwd.getText().toString().trim();
			String newPwd = pullNewPwd.getText().toString().trim();
			String newPwdAgain = pullNewPwdAgain.getText().toString().trim();
			if (currentPwd.length() < 6 || currentPwd.length() > 32
					|| newPwd.length() < 6 || newPwd.length() > 32
					|| newPwdAgain.length() < 6 || newPwdAgain.length() > 32) {
				Toast.makeText(ModifyPasswordActivity.this, "密码长度应为6-32个字符", 0)
						.show();
			} else {
				SQLiteDatabase db = helper.getReadableDatabase();
				Cursor cursor = db.query("user", new String[] { "password" },
						"password = ?", new String[] { currentPwd }, null,
						null, null);
				if (cursor.moveToFirst() == false) {
					Toast.makeText(ModifyPasswordActivity.this, "当前密码错误，请重新输入",
							0).show();
				} else {
					if (newPwd.equals(newPwdAgain)) {
						// 修改数据库
						updatePwd(newPwd);
						Toast.makeText(ModifyPasswordActivity.this, "密码已修改", 0)
								.show();
						finish();
					} else {
						Toast.makeText(ModifyPasswordActivity.this,
								"两次输入的新密码不一致", 0).show();
					}
				}
				cursor.close();
				db.close();
			}
			break;
		case R.id.delete_current_pwd:
			pullCurrentPwd.setText("");
			break;
		case R.id.delete_new_pwd:
			pullNewPwd.setText("");
			break;
		case R.id.delete_new_pwd_again:
			pullNewPwdAgain.setText("");
			break;
		}
	}

	// 修改数据库password
	private void updatePwd(String pwdStr) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("password", pwdStr);
		db.update("user", values, "nickname=?", new String[] { nickname });
		db.close();
	}
	//监听输入框里的内容 以确定删除图片的显示、隐藏
	@Override//输入前
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		isEmpty(pullCurrentPwd, delCurrentPwd);
		isEmpty(pullNewPwd, delNewPwd);
		isEmpty(pullNewPwdAgain, delNewPwdAgain);
	}

	@Override//输入后
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		isEmpty(pullCurrentPwd, delCurrentPwd);
		isEmpty(pullNewPwd, delNewPwd);
		isEmpty(pullNewPwdAgain, delNewPwdAgain);
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
}
