package com.wiseweb.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wiseweb.bean.PayWay;
import com.wiseweb.fragment.adapter.PayWayListAdapter;
import com.wiseweb.movie.R;
import com.wiseweb.util.GetEnc;
import com.wiseweb.util.Util;

public class PayOrderActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	private RelativeLayout payOrderTitleBack;
	private TextView countDown;
	private TextView orderName;
	private TextView orderTotalPrice;
	private TextView remainingMoney;
	private TextView orderNeedPay;
	private ListView payWayListview;
	private PayWayListAdapter payAdapter;
	private List<PayWay> payWayList = new ArrayList<PayWay>();
	private PayWay payWay = new PayWay();
	private List<String> ways = new ArrayList<String>(); // 影院支持的支付方式
	private ScrollView sv;
	private int lastSelectedIndex = 0; // 初始未选中任何item
	private MyCount mc;
	private Button payBtn;
	private SharedPreferences orderPreferences;
	private String orderId;
	private SharedPreferences payPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pay_order);
		initView();
		
		Intent intent = getIntent();
		Long time = intent.getLongExtra("time", 0l);
		
		mc = new MyCount(time, 1000);
		mc.start();
		setData();
		sv.smoothScrollTo(0, 0);
//		ways.add("银行卡支付");
		ways.add("支付宝支付");
//		ways.add("微信支付");
		setData();
		payAdapter = new PayWayListAdapter(payWayList, this);
		payWayListview.setAdapter(payAdapter);
		payWayListview.setOnItemClickListener(this);
		payOrderTitleBack.setOnClickListener(this);
		payBtn.setOnClickListener(this);
	}

	/**
	 * 初始化组件
	 */
	public void initView() {
		payOrderTitleBack = (RelativeLayout) findViewById(R.id.pay_order_title_back);
		countDown = (TextView) findViewById(R.id.pay_order_count_down);
		orderName = (TextView) findViewById(R.id.order_name);
		orderTotalPrice = (TextView) findViewById(R.id.order_total_price);
		remainingMoney = (TextView) findViewById(R.id.remaining_money);
		orderNeedPay = (TextView) findViewById(R.id.order_need_pay);
		payWayListview = (ListView) findViewById(R.id.pay_way_list);
		sv = (ScrollView)findViewById(R.id.sv);
		payBtn = (Button)findViewById(R.id.pay_btn);
		
	}

	/**
	 * 根据影院可以选择的支付方式设置支付列表数据
	 */
	public void setData() {
		orderName.setText("订单名称："+"虎啸影视-订单编号：18813085037");
		orderTotalPrice.setText("订单总价："+"88元");
		remainingMoney.setText("账户余额："+"0元");
		orderNeedPay.setText("还需支付："+"88元");
//		payWayList.clear();
		for (int i = 0; i < ways.size(); i++) {
			payWay = new PayWay();
			if (ways.get(i).equals("银行卡支付")) {
				payWay.setImageId(R.drawable.ic_payment_bankcard);
				payWay.setName("银行卡支付");
				payWay.setDesc("支持储蓄卡信用卡、无需开通网银");
			} else if (ways.get(i).equals("支付宝支付")) {
				payWay.setImageId(R.drawable.ic_payment_alipayclient);
				payWay.setName("支付宝支付");
				payWay.setDesc("推荐有支付宝账号的用户使用");
			} else if(ways.get(i).equals("微信支付")) {
				payWay.setImageId(R.drawable.ic_payment_weixinpay);
				payWay.setName("微信支付");
				payWay.setDesc("推荐安装微信5.0及以上版本的使用");
			}
			System.out.println("payWay----"+payWay);
			payWayList.add(payWay);
			System.out.println("payWayList-----"+payWayList);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		lastSelectedIndex = position;
//		payAdapter.setSelectedIndex(position);  
		payAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_order_title_back:
			PayOrderActivity.this.finish();
			break;
		case R.id.pay_btn:
			// 确认订单
			Thread t = new Thread(orderConfirmRunnable);
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Intent intent = new Intent();
			intent.setClass(PayOrderActivity.this, PayActivity.class);
			startActivity(intent);
			break;
		}
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
			countDown.setText("订单超时");
			// 不能再支付
			payBtn.setClickable(false);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			countDown.setText("支付剩余时间：" + millisUntilFinished / 60000 + "分"
					+ ((millisUntilFinished / 1000) % 60) + "秒");
		}
	}
	/**
	 * 确认订单
	 */
	Runnable orderConfirmRunnable = new Runnable() {

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

