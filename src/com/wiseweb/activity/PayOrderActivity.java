package com.wiseweb.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wiseweb.bean.PayWay;
import com.wiseweb.fragment.adapter.PayWayListAdapter;
import com.wiseweb.movie.R;

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
//	private int lastSelectedIndex = -1; // 初始未选中任何item

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pay_order);
		initView();
		sv.smoothScrollTo(0, 0);
		ways.add("银行卡支付");
		ways.add("支付宝支付");
		ways.add("微信支付");
		setData();
		payAdapter = new PayWayListAdapter(payWayList, this);
		payWayListview.setAdapter(payAdapter);
		payWayListview.setOnItemClickListener(this);
		payOrderTitleBack.setOnClickListener(this);
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
		}
	}
}
