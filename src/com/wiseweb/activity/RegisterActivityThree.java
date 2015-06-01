package com.wiseweb.activity;

import com.wiseweb.db.UserSQLiteOpenHelper;
import com.wiseweb.movie.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegisterActivityThree extends Activity {

	private RelativeLayout mRegisterTitleBack;
	private EditText pullPassword;
	private EditText surePassword;
	private Button registerBtn;
	private ImageView delPwd;
	private ImageView delSurePwd;
	private UserSQLiteOpenHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout_three);
		init();
	}

	private void init() {
		helper = new UserSQLiteOpenHelper(RegisterActivityThree.this);
		delPwd = (ImageView) findViewById(R.id.delete_password);
		delSurePwd = (ImageView) findViewById(R.id.delete_sure_password);
		pullPassword = (EditText) findViewById(R.id.pull_password_ok);
		surePassword = (EditText) findViewById(R.id.sure_password);
		registerBtn = (Button) findViewById(R.id.register_btn);
		// 注册按钮监听
		registerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String pullPasswordStr = pullPassword.getText().toString()
						.trim();
				byte[] b = pullPasswordStr.getBytes();
				String surePasswordStr = surePassword.getText().toString()
						.trim();
				if (b.length < 6 || b.length > 32) {
					dialogForWrongPasswordLength();
				} else {
					if (pullPasswordStr.equals(surePasswordStr)) {
						Toast.makeText(RegisterActivityThree.this, "恭喜您注册成功！",
								0).show();
						dialogForsuccess();
						//得到username
						SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
						String usernameStr = sp.getString("username", "");
						//得到nickname
						String nickNameStr = createNickname();
						//将密码存入数据库
						updatePwd(pullPasswordStr, usernameStr);
						//将nickname存入数据库
						updateNickname(nickNameStr, usernameStr);
						//将密码和nickname保存到sharedpreference
						Editor editor = sp.edit();
						editor.putString("password", pullPasswordStr);
						editor.putString("nickname", nickNameStr);
						editor.commit();
						System.out.println("usernameStr="+usernameStr);
						System.out.println("pullPasswordStr="+pullPasswordStr);
						System.out.println("nickNameStr="+nickNameStr);
						
					} else {
						dialogForWrongCode();
					}
				}
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
		delPwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pullPassword.setText("");
				
			}
		});
		delSurePwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				surePassword.setText("");
			}
		});
		//输入密码输入框监听事件
		pullPassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				isEmptyPullPassword();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				isEmptyPullPassword();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		//确定密码输入框监听事件
		surePassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				isEmptySurePassword();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				isEmptySurePassword();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	// 弹出对话框
	public void dialogForWrongCode() {
		AlertDialog.Builder builder = new Builder(RegisterActivityThree.this);
		builder.setMessage("     两次密码不一致，请重新输入");
		builder.setPositiveButton("确定", null);
		builder.show();
	}

	// 弹出对话框
	public void dialogForWrongPasswordLength() {
		AlertDialog.Builder builder = new Builder(RegisterActivityThree.this);
		builder.setMessage("             密码长度需为6-32位");
		builder.setPositiveButton("确定", null);
		builder.show();
	}
	// 弹出对话框
		public void dialogForsuccess() {
			AlertDialog.Builder builder = new Builder(RegisterActivityThree.this);
			builder.setMessage("恭喜您注册成功，可以开始买票啦");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent();
					intent.setClass(RegisterActivityThree.this, MainActivity.class);
					startActivity(intent);
				}
			});
			builder.show();
		}

	// 判断输入框里是否有内容 以确定图片的显示、隐藏
	private void isEmptyPullPassword() {
		if (TextUtils.isEmpty(pullPassword.getText().toString())) {
			delPwd.setVisibility(View.INVISIBLE);
		} else {
			delPwd.setVisibility(View.VISIBLE);
		}
	}

	// 判断输入框里是否有内容 以确定图片的显示、隐藏
	private void isEmptySurePassword() {
		if (TextUtils.isEmpty(surePassword.getText().toString())) {
			delSurePwd.setVisibility(View.INVISIBLE);
		} else {
			delSurePwd.setVisibility(View.VISIBLE);
		}
	}
	//讲密码存入数据库
	private void updatePwd(String password,String username){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("update user set password = ? where username = ?", new Object[]{password,username});
		db.close();
	}
	//讲昵称存入数据库
	private void updateNickname(String nickname,String username){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("update user set nickname = ? where username = ?", new Object[]{nickname,username});
		db.close();
	}
	//生成一个随机的昵称
	private String createNickname(){
		String nickname = "kdy"+System.currentTimeMillis();
		return nickname;
	}
}
