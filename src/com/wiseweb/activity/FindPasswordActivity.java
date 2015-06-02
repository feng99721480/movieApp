package com.wiseweb.activity;

import com.wiseweb.db.UserSQLiteOpenHelper;
import com.wiseweb.movie.R;
import com.wiseweb.ui.Code;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FindPasswordActivity extends Activity implements OnClickListener {
	
	private ImageView codeImg;
	private Button changeCodeBtn;
	private EditText pullNumOrNickname;
	private EditText pullCode;
	private Button nextBtn;
	private UserSQLiteOpenHelper helper;
	private RelativeLayout titleBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_password);
		
		initUI();
	}

	private void initUI() {
		titleBack = (RelativeLayout) findViewById(R.id.find_password_title_back);
		helper = new UserSQLiteOpenHelper(FindPasswordActivity.this);
		nextBtn = (Button) findViewById(R.id.next_btn);
		pullNumOrNickname = (EditText) findViewById(R.id.pull_phone_number_or_nickname);
		pullCode = (EditText) findViewById(R.id.pull_code_from_img);
		changeCodeBtn = (Button) findViewById(R.id.find_password_change_code);
		codeImg = (ImageView) findViewById(R.id.find_password_code_img);
		codeImg.setImageBitmap(Code.getInstance().createBitmap());
		changeCodeBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
		titleBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.find_password_change_code://改变验证码图片
			codeImg.setImageBitmap(Code.getInstance().createBitmap());
			break;
		case R.id.next_btn://跳转下一个activity
			String numOrNickname = pullNumOrNickname.getText().toString().trim();
			String code = pullCode.getText().toString().trim();
			if(numOrNickname.length() == 0 ){
				Toast.makeText(FindPasswordActivity.this,"请输入用户名" , 0).show();
			}else{
				if(code.length() == 0){
					Toast.makeText(FindPasswordActivity.this,"请输入图片验证码" , 0).show();
				}else{
					SQLiteDatabase db = helper.getReadableDatabase();
					Cursor cursor = db.query("user", new String[] { "username","nickname" },
							"username = ? or nickname = ?", new String[] { numOrNickname,numOrNickname }, null,
							null, null);
					if (cursor.moveToFirst() == false) {
						Toast.makeText(FindPasswordActivity.this, "用户不存在",
								0).show();
					}else{
						System.out.println("用户存在");
						//忽略大小写进行比较
						if(code.equalsIgnoreCase(Code.getInstance().getCode())){
							Intent intent = new Intent();
							intent.putExtra("numOrNickname", numOrNickname);
							intent.setClass(FindPasswordActivity.this, FindPasswordActivitySecond.class);
							startActivity(intent);
						}else{
							Toast.makeText(FindPasswordActivity.this, "验证码输入不正确，请重新输入",
									0).show();
							codeImg.setImageBitmap(Code.getInstance().createBitmap());
						}
					}
					cursor.close();
					db.close();
				}
				
			}
			
			break;
		case R.id.find_password_title_back://返回
			finish();
			break;
	
		}
	}
}
