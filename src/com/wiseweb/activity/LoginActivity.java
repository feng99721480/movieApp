package com.wiseweb.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.wiseweb.db.UserSQLiteOpenHelper;
import com.wiseweb.fragment.MineFragment;
import com.wiseweb.movie.R;
import com.wiseweb.util.DatabaseHelper;

public class LoginActivity extends Activity {
	private RelativeLayout loginTitleBack;
	private ImageView sinaLogin;
	private Button login;
	private Platform sinaWeibo;
	private String userName;
	private Intent intent;
	private TextView registerTv;
	private EditText loginUsername;
	private EditText loginPassword;
	private ImageView delLoginUsername;
	private ImageView delLoginPwd;
	private SharedPreferences user;
	// private DatabaseHelper db;
	private UserSQLiteOpenHelper helper;
	private SQLiteDatabase dbRead;
	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initUI();
		ShareSDK.initSDK(this);
		sinaWeibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);
	}

	public void initUI() {
		helper = new UserSQLiteOpenHelper(LoginActivity.this);
		loginUsername = (EditText) findViewById(R.id.login_username_et);
		loginPassword = (EditText) findViewById(R.id.login_password_et);
		delLoginUsername = (ImageView) findViewById(R.id.delete_login_username);
		delLoginPwd = (ImageView) findViewById(R.id.delete_login_password);
		// 用户名输入框监听
		loginUsername.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				isEmptyloginUsername();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				isEmptyloginUsername();
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		// 密码输入框监听
		loginPassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				isEmptyloginPassword();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				isEmptyloginPassword();
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		// 删除用户名图标监听
		delLoginUsername.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginUsername.setText("");
			}
		});
		// 删除密码图标监听
		delLoginPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginPassword.setText("");
			}
		});
		registerTv = (TextView) findViewById(R.id.register_tv);
		// 跳转注册界面
		registerTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
		loginTitleBack = (RelativeLayout) findViewById(R.id.login_title_back);
		loginTitleBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				intent = new Intent();
				intent.putExtra("userName", "立即登录");
				setResult(1, intent);
				LoginActivity.this.finish();
			}

		});
		sinaLogin = (ImageView) findViewById(R.id.sina_login);
		sinaLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// sinaWeibo.getDb().getUserId();
				sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
				// weibo.getDb().getUserId(); //获取授权用户的userid
				sinaWeibo.SSOSetting(false); // 使用SSO方式授权
				sinaWeibo.authorize();
				sinaWeibo.showUser(null); // 执行登录，登录后在回调里面获取用户资料
				userName = sinaWeibo.getDb().getUserName(); // 获取授权用户的用户名

				// 将用户名，密码保存到sharedpreference里
				user = getSharedPreferences("user", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = user.edit();
				editor.putString("userName", userName);
				// editor.putString("userPass", arg1)
				editor.commit();
				// 不再使用sso授权
				// weibo.SSOSetting(true);

				// sinaLogin.setVisibility(View.GONE);
				intent = new Intent();
				intent.putExtra("userName", userName);
				setResult(1, intent);
				finish();
			}

		});
		login = (Button) findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String usernameStr = loginUsername.getText().toString().trim();
				byte[] usernameBytes = usernameStr.getBytes();
				String passwordStr = loginPassword.getText().toString().trim();
				byte[] passwordBytes = passwordStr.getBytes();

				if (login.getText().toString().trim().equals("登录")) {

					if (TextUtils.isEmpty(usernameStr)
							|| usernameBytes.length != 11) {
						dialogForWrongNumber();
					} else if (passwordBytes.length < 6
							|| passwordBytes.length > 32) {
						dialogForWrongPasswordLength();
					} else {
						// dbRead = db.getReadableDatabase();
						dbRead = helper.getReadableDatabase();
						cursor = dbRead.query("user", new String[] {
								"username", "password" },
								"username=? and password=?", new String[] {
										usernameStr, passwordStr }, null, null,
								null);
						if (cursor.moveToFirst() == false) {
							// 不匹配
							Toast.makeText(LoginActivity.this, "用户名或密码错误", 0)
									.show();
						} else {
							Toast.makeText(LoginActivity.this, "登陆成功", 0)
									.show();
							//从数据库得到nickname用于回显
							String nicknameStr = findNickname(usernameStr);
							// 将nickname保存到sharedpreference里 用于回显
//							user = getSharedPreferences("user",
//									Context.MODE_PRIVATE);
//							SharedPreferences.Editor editor = user.edit();
//							editor.putString("nickname", nicknameStr);
//							editor.commit();
							// 用于回调
							Intent intent = new Intent();
							intent.putExtra("nickname", nicknameStr);
							setResult(0, intent);
							finish();
						}
						cursor.close();
						dbRead.close();
					}

				}

				if (login.getText().toString().trim().equals("退出登录")) {
					if (sinaWeibo.isValid()) {
						sinaWeibo.removeAccount();
						SharedPreferences s = getSharedPreferences("user",
								Context.MODE_PRIVATE);
						s.edit().clear().commit();
						// sinaLogin.setVisibility(View.VISIBLE);
						intent = new Intent();
						intent.putExtra("userName", "登录");
						setResult(1, intent);
						finish();
					}
				}

			}

		});
	}

	// 弹出对话框
	public void dialogForWrongNumber() {
		AlertDialog.Builder builder = new Builder(LoginActivity.this);
		builder.setMessage("      请输入11位正确的手机号码");
		builder.setPositiveButton("确定", null);
		builder.show();
	}

	// 弹出对话框
	public void dialogForWrongPasswordLength() {
		AlertDialog.Builder builder = new Builder(LoginActivity.this);
		builder.setMessage("             密码长度需为6-32位");
		builder.setPositiveButton("确定", null);
		builder.show();
	}

	// 判断输入框里是否有内容 以确定图片的显示、隐藏
	private void isEmptyloginUsername() {
		if (TextUtils.isEmpty(loginUsername.getText().toString())) {
			delLoginUsername.setVisibility(View.INVISIBLE);
		} else {
			delLoginUsername.setVisibility(View.VISIBLE);
		}
	}

	// 判断输入框里是否有内容 以确定图片的显示、隐藏
	private void isEmptyloginPassword() {
		if (TextUtils.isEmpty(loginPassword.getText().toString())) {
			delLoginPwd.setVisibility(View.INVISIBLE);
		} else {
			delLoginPwd.setVisibility(View.VISIBLE);
		}
	}
	//查询到nickname 用于mineFragment显示用户名
	private String findNickname(String usernameStr){
		dbRead = helper.getReadableDatabase();
		cursor = dbRead.query("user", new String[]{"nickname"}, "username=?", new String[]{usernameStr}, null, null, null);
		String nickname = null;
		while(cursor.moveToNext()){
			nickname = cursor.getString(cursor.getColumnIndex("nickname"));
			System.out.println("查询nickname方法---nickname="+nickname);
		}
		cursor.close();
		dbRead.close();
		return nickname;
	}
}
