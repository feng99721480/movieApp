package com.wiseweb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.wiseweb.movie.R;

public class SubmitOrderActivity extends Activity {
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_submit_order);
		initView();
		//倒计时15分钟
		mc = new MyCount(15*1000*60,1000);
		mc.start();
	}
	private void initView() {
		countDownText = (TextView)findViewById(R.id.count_down);
		orderCinemaName = (TextView)findViewById(R.id.order_cinema_name);
		orderMovieName = (TextView)findViewById(R.id.order_movie_name);
		orderEvent = (TextView)findViewById(R.id.order_event);
		orderSeat = (TextView)findViewById(R.id.order_seat);
		orderPhone = (EditText)findViewById(R.id.order_phone);
		orderTotalPrice = (TextView)findViewById(R.id.order_total_price);
		orderTotalPayment = (TextView)findViewById(R.id.order_total_payment);
		orderPayBtn = (Button)findViewById(R.id.order_pay_btn);
	}
	/**
	 * 倒计时的内部类
	 */
	class MyCount extends CountDownTimer{

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			countDownText.setText("订单超时");
			
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			countDownText.setText("支付剩余时间："+millisUntilFinished/60000+"分"+ ((millisUntilFinished/1000) % 60) + "秒");
		}
	}
}
