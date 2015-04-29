package com.wiseweb.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiseweb.movie.R;

public class SubmitOrderActivity extends Activity implements OnClickListener {
	private TextView countDownText;
	private MyCount mc;
	private TextView orderCinemaName;
	private TextView orderMovieName;
	private TextView orderEvent; // 场次
	private TextView orderSeat;
	private EditText orderPhone;
	private TextView orderTotalPrice;
	private TextView orderTotalPayment;
	private Button orderPayBtn;
	private RelativeLayout submitOrderTitleBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_submit_order);
		initView();
		// 倒计时15分钟
		mc = new MyCount(15 * 1000 * 60, 1000);
		mc.start();
		setData();
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
		orderPhone = (EditText) findViewById(R.id.order_phone);
		orderTotalPrice = (TextView) findViewById(R.id.order_total_price);
		orderTotalPayment = (TextView) findViewById(R.id.order_total_payment);
		orderPayBtn = (Button) findViewById(R.id.order_pay_btn);
		submitOrderTitleBack = (RelativeLayout) findViewById(R.id.submit_order_title_back);
	}

	/**
	 * 设置订单数据
	 */
	public void setData() {
		orderCinemaName.setText("影院：" + "东都影城");
		orderMovieName.setText("电影：" + "左耳");
		orderEvent.setText("场次：" + "2015-04-28 周二 17:25");
		orderSeat.setText("座位：" + "7号厅 5排12座");
		orderTotalPrice.setText("总价：" + "41元");
		orderTotalPayment.setText("合计支付：" + "41元");
		orderPayBtn.setText("立即支付" + "41" + "元");
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

		}

		@Override
		public void onTick(long millisUntilFinished) {
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
			// 判断是否是合法的手机号
			String s = orderPhone.getText().toString();
			Boolean isMobileNum = isMobileNum(s);
			if (isMobileNum == true) { // 手机号合法
				// 转去支付界面
				Intent intent = new Intent();
				intent.setClass(SubmitOrderActivity.this,
						PayOrderActivity.class);
				SubmitOrderActivity.this.startActivity(intent);
			} else { // 不是合法的手机号
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("提示").setMessage("您输入的手机号格式有误")
						.setPositiveButton("确定", null).show();
			}
			break;
		}
	}

	/**
	 * 判断是否是合法的手机号
	 */
	public static boolean isMobileNum(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
