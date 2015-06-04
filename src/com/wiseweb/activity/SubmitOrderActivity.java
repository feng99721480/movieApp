package com.wiseweb.activity;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiseweb.movie.R;
import com.wiseweb.util.GetEnc;
import com.wiseweb.util.Util;

public class SubmitOrderActivity extends Activity implements OnClickListener {
	private TextView countDownText;
	private MyCount mc;
	private TextView orderCinemaName;
	private TextView orderMovieName;
	private TextView orderEvent; // 场次
	private TextView orderSeat;
	private TextView orderPhone;
	private TextView orderTotalPrice;
	private TextView orderTotalPayment;
	private Button orderPayBtn;
	private RelativeLayout submitOrderTitleBack;
	private SharedPreferences orderPreferences;

    
	public static long time = 15 * 1000 * 60L;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_submit_order);
		initView();
		// 倒计时15分钟
		mc = new MyCount(time, 1000);
		mc.start();
		setData();
		orderPreferences = getSharedPreferences("orderConfig",
				Context.MODE_PRIVATE);
		
		/*
		 * 各组件点击事件监听
		 */
		submitOrderTitleBack.setOnClickListener(this);
		orderPayBtn.setOnClickListener(this);

	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		countDownText = (TextView) findViewById(R.id.count_down);
		orderCinemaName = (TextView) findViewById(R.id.order_cinema_name);
		orderMovieName = (TextView) findViewById(R.id.order_movie_name);
		orderEvent = (TextView) findViewById(R.id.order_event);
		orderSeat = (TextView) findViewById(R.id.order_seat);
		orderPhone = (TextView) findViewById(R.id.order_phone);
		orderTotalPrice = (TextView) findViewById(R.id.order_total_price);
		orderTotalPayment = (TextView) findViewById(R.id.order_total_payment);
		orderPayBtn = (Button) findViewById(R.id.order_pay_btn);
		submitOrderTitleBack = (RelativeLayout) findViewById(R.id.submit_order_title_back);
		orderPreferences = getSharedPreferences("orderConfig",
				Context.MODE_PRIVATE);
	}

	/**
	 * 设置订单数据
	 */
	public void setData() {
		String cinemaName = orderPreferences.getString("cinemaName", "");
		String movieName = orderPreferences.getString("movieName", "");
		String featureTime = orderPreferences.getString("featureTime", "");
		String hallName = orderPreferences.getString("hallName", "");
		String seatDesc = orderPreferences.getString("seatDesc", "");
		String phone = orderPreferences.getString("mobile", "");
		String money = orderPreferences.getString("money", "");
		String agio = orderPreferences.getString("agio", "");
		orderCinemaName.setText("影院：" + cinemaName);
		orderMovieName.setText("电影：" + movieName);
		orderEvent.setText("场次：" + featureTime);
		orderSeat.setText("座位：" + hallName + " " + seatDesc);
		orderPhone.setText("手机号：" + phone);
		orderTotalPrice.setText("总价：" + money + "元");
		orderTotalPayment.setText("合计支付：" + agio + "元");
		orderPayBtn.setText("立即支付" + agio + "元");
	}

	/**
	 * 倒计时的内部类
	 */
	class MyCount extends CountDownTimer {

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			countDownText.setText("订单超时");
			// 不能再支付
			orderPayBtn.setClickable(false);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			time = millisUntilFinished;
			countDownText.setText("支付剩余时间：" + millisUntilFinished / 60000 + "分"
					+ ((millisUntilFinished / 1000) % 60) + "秒");
		}
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 返回
		case R.id.submit_order_title_back:
			this.finish();
			break;
		// 支付按钮
		case R.id.order_pay_btn:
			Intent intent = new Intent();
			intent.putExtra("time", time);
			intent.setClass(SubmitOrderActivity.this, PayOrderActivity.class);
			startActivity(intent);
			break;
		}
	}
}
