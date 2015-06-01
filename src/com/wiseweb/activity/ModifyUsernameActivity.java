package com.wiseweb.activity;

import com.wiseweb.db.UserSQLiteOpenHelper;
import com.wiseweb.movie.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.Toast;

public class ModifyUsernameActivity extends Activity implements OnClickListener {

	private RelativeLayout titleBack;
	private Button sureBtn;
	private EditText usernameEdit;
	private SharedPreferences sp;
	private String nickname;
	private String username;
	private ImageView usernameDel;
	private UserSQLiteOpenHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_username);
		sp = getSharedPreferences("userInfo", MODE_PRIVATE);
		nickname = sp.getString("nickname", "");
		username = sp.getString("username", "");
		initUI();
	}

	private void initUI() {
		helper = new UserSQLiteOpenHelper(ModifyUsernameActivity.this);
		usernameDel = (ImageView) findViewById(R.id.modify_username_del);
		titleBack = (RelativeLayout) findViewById(R.id.modify_username_title_back);
		sureBtn = (Button) findViewById(R.id.sure_btn);
		usernameEdit = (EditText) findViewById(R.id.modify_username_et);
		usernameEdit.setText(nickname);
		usernameEdit.setSelection(nickname.length());
		titleBack.setOnClickListener(this);
		sureBtn.setOnClickListener(this);
		usernameDel.setOnClickListener(this);
		// 长按删除全部
		usernameDel.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				usernameEdit.setText("");
				return false;
			}
		});
		// 监听输入框里的内容 没有内容的时候隐藏删除图片
		usernameEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				isEmpty();
			}

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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.modify_username_title_back:// 返回
			finish();
			break;

		case R.id.sure_btn:// 确定
			String newNickname = usernameEdit.getText().toString().trim();
			byte[] b = newNickname.getBytes();
			if (b.length < 4 || b.length > 16) {
				Toast.makeText(ModifyUsernameActivity.this, "用户名长度应为4-16个字符", 1)
						.show();
			} else {
				//修改数据库nickname
				updateNickname(newNickname, username);
				//修改sharedPreference保存的nickname
				Editor editor = sp.edit();
				editor.remove("nickname");
				editor.putString("nickname", newNickname);
				editor.commit();
				//回调方法
				Intent intent = new Intent();
				intent.putExtra("nickname", newNickname);
				setResult(4, intent);
				finish();
			}
			break;
		case R.id.modify_username_del:// 逐个删除字符
			// 动作按下
			int action = KeyEvent.ACTION_DOWN;
			// code:删除，其他code也可以，例如 code = 0
			int code = KeyEvent.KEYCODE_DEL;
			KeyEvent event = new KeyEvent(action, code);
			usernameEdit.onKeyDown(KeyEvent.KEYCODE_DEL, event);
			break;
		}
	}

	// 判断输入框里是否有内容 以确定图片的显示、隐藏
	private void isEmpty() {
		if (TextUtils.isEmpty(usernameEdit.getText().toString())) {
			usernameDel.setVisibility(View.INVISIBLE);
		} else {
			usernameDel.setVisibility(View.VISIBLE);
		}
	}
	//将修改后的昵称存入数据库
		private void updateNickname(String nicknameStr,String usernameStr){
			SQLiteDatabase db = helper.getWritableDatabase();
			db.execSQL("update user set nickname = ? where username = ?", new Object[]{nicknameStr,usernameStr});
			db.close();
		}
}
