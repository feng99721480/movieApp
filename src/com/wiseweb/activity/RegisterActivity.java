package com.wiseweb.activity;

import com.wiseweb.movie.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	private RelativeLayout mRegisterTitleBack;
	private EditText pullPhoneNumber;
	private ImageView deletePhoneNumber;
	private ImageButton imgButton;
	private boolean flag = false;
	private Button getCodeBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);
		//初始化组件
		init();
	}

	private void init() {
		getCodeBtn = (Button) findViewById(R.id.get_code);
		//点击按钮 获取验证码
		getCodeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String phoneNumber = pullPhoneNumber.getText().toString().trim();
				byte[] number = phoneNumber.getBytes();
				if(TextUtils.isEmpty(phoneNumber) || number.length != 11){
					//Toast.makeText(RegisterActivity.this, "请输入11位正确的手机号码", 0).show();
					alertDialog();
				}else{
					Toast.makeText(RegisterActivity.this, "正在向服务器请求验证码。。。", 0).show();
				}
			}
		});
		imgButton = (ImageButton) findViewById(R.id.imgButton);
		//设置imageButton的图片切换
		imgButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!flag){
					imgButton.setBackgroundResource(R.drawable.ic_checkbox_photo_selected);
					flag = true;
				}else{
					imgButton.setBackgroundResource(R.drawable.ic_choose_unselect);
					flag = false;
				}
			}
		});
		pullPhoneNumber = (EditText) findViewById(R.id.pull_phone_number);
		deletePhoneNumber = (ImageView) findViewById(R.id.delete_phone_number);
		//点击图片逐个删除editText里的内容
		deletePhoneNumber.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//动作按下
			    int action = KeyEvent.ACTION_DOWN;
			    //code:删除，其他code也可以，例如 code = 0
			    int code = KeyEvent.KEYCODE_DEL;
			    KeyEvent event = new KeyEvent(action, code);
			    pullPhoneNumber.onKeyDown(KeyEvent.KEYCODE_DEL, event); 
			}
		});
		//长按图片全部删除
		deletePhoneNumber.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				pullPhoneNumber.setText("");
				pullPhoneNumber.clearFocus();
				return false;
			}
		});
		// 输入框监听事件
		pullPhoneNumber.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				isEmpty();
				
			}
			//输入前
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
		//点击返回上一个activity
		mRegisterTitleBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	//弹出对话框 
	public void alertDialog(){
		AlertDialog.Builder builder = new Builder(RegisterActivity.this);
		builder.setMessage("      请输入11位正确的手机号码");
		builder.setPositiveButton("确定", null);
		builder.show();
	}
	//判断输入框里是否有内容  以确定图片的显示、隐藏
	private void isEmpty(){
		if(TextUtils.isEmpty(pullPhoneNumber.getText().toString())){
			deletePhoneNumber.setVisibility(View.INVISIBLE);
		}else{
			deletePhoneNumber.setVisibility(View.VISIBLE);
		}
	}
}
