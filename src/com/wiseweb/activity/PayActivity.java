package com.wiseweb.activity;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

import com.wiseweb.movie.R;


public class PayActivity extends Activity {
	private WebView payView;
	private SharedPreferences payPreferences;
	private String payUrl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pay);
		
		initView();
		payPreferences = getSharedPreferences("payInfo",Context.MODE_PRIVATE);
		payUrl = payPreferences.getString("payUrl", "");
		System.out.println("payUrl---"+payUrl);
//		payView.loadUrl(payUrl);
		payView.loadUrl("http://wappaygw.alipay.com/service/rest.htm?sign=03c87c975a39caa0b05d3cf8322ab7e3&v=2.0&sec_id=MD5&call_back_url=%2Findex&req_data=%3Cauth_and_execute_req%3E%3Crequest_token%3E2015052867517886e146837bf2e16c05a4fa00eb%3C%2Frequest_token%3E%3C%2Fauth_and_execute_req%3E&service=alipay.wap.auth.authAndExecute&partner=2088701138547200&format=xml");
	}
	private void initView(){
		payView = (WebView)findViewById(R.id.pay_view);
		
	}
}
