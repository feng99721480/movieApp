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
	private String orderId;
	private SharedPreferences payPreferences;

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
		String seatNo = orderPreferences.getString("seatNo", "");
		String phone = orderPreferences.getString("mobile", "");
		String money = orderPreferences.getString("money", "");
		String agio = orderPreferences.getString("agio", "");
		orderId = orderPreferences.getString("orderId", "");
		orderCinemaName.setText("影院：" + cinemaName);
		orderMovieName.setText("电影：" + movieName);
		orderEvent.setText("场次：" + featureTime);
		orderSeat.setText("座位：" + hallName + " " + seatNo);
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
			// 确认订单
			Thread t = new Thread(createOrderRunnable);
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			Intent intent = new Intent();
//			intent.setClass(SubmitOrderActivity.this, PayActivity.class);
//			startActivity(intent);
			break;
		}
	}

	/**
	 * 确认订单
	 */
	Runnable createOrderRunnable = new Runnable() {

		@Override
		public void run() {
			HashMap<String, Object> params = new HashMap<String, Object>();
			long time_stamp = Util.getTimeStamp();
			params.put("time_stamp", time_stamp);
			params.put("action", "order_confirm"); // 接口名称
			params.put("order_id", orderId);
			params.put("balance", 0); // 座位id
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			// HttpGet getMethod = new HttpGet(Constant.baseURL
			// + "action=order_add" + "&" + "seat_no=" + seatNo + "&"
			// + "plan_id=" + planId + "&" + "mobile=" + mobile + "&"
			// + "send_message=" + sendMessage + "&" + "enc=" + enc + "&"
			// + "time_stamp=" + time_stamp);
			// System.out.println(Constant.baseURL + "action=order_add" + "&"
			// + "seat_no=" + seatNo + "&" + "plan_id=" + planId + "&"
			// + "mobile=" + mobile + "&" + "send_message=" + sendMessage
			// + "&" + "enc=" + enc + "&" + "time_stamp=" + time_stamp);
			HttpGet getMethod = new HttpGet(
					"http://test.komovie.cn/api_movie/service?action=order_Confirm&order_id=a1432807288995531315&balance=0&pay_method=1&callback_url=%2Findex&time_stamp=1432807293494&enc=7d33279da6f61170e0554686d0d4c8fc&");
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					// 获得信息
					JSONObject payInfo = new JSONObject(result)
							.getJSONObject("payInfo");
					String payUrl = payInfo.getString("payUrl");
					payPreferences = getSharedPreferences("payInfo",Context.MODE_PRIVATE);
					SharedPreferences.Editor e = payPreferences.edit();
					e.putString("payUrl", payUrl);
					e.commit();

					// Message msg = new Message();
					// // Bundle data = new Bundle();
					// msg.what = ORDER_ADD;
					// handler.sendMessage(msg);

				} else {
					System.out.println("lalalaalalalalala");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	};

}
