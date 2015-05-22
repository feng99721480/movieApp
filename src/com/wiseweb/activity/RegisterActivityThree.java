package com.wiseweb.activity;

import com.wiseweb.movie.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivityThree extends Activity {

	private EditText pullPassword;
	private EditText surePassword;
	private Button registerBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout_three);
		init();
	}

	private void init() {
		System.out.println("*****************************");
		pullPassword = (EditText) findViewById(R.id.pull_password_ok);
		System.out.println("++++++++++++++++++++++++++");
		surePassword = (EditText) findViewById(R.id.sure_password);
		System.out.println("----------------------------");
		registerBtn = (Button) findViewById(R.id.register_btn);
		// 注册按钮监听
		registerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String pullPasswordStr = pullPassword.getText().toString()
						.trim();
				String surePasswordStr = surePassword.getText().toString()
						.trim();
				if (pullPasswordStr.equals(surePasswordStr)) {
					Toast.makeText(RegisterActivityThree.this, "恭喜您注册成功！", 0)
							.show();
				} else {
					dialogForWrongCode();
				}
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
}
