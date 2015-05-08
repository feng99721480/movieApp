package com.wiseweb.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.google.gson.Gson;
import com.wiseweb.bean.Seat;
import com.wiseweb.bean.SeatInfo;
import com.wiseweb.constant.Constant;
import com.wiseweb.json.MovieResult;
import com.wiseweb.json.MovieResult.Movie;
import com.wiseweb.json.SeatResult;
import com.wiseweb.movie.R;
import com.wiseweb.seatchoose.view.OnSeatClickListener;
import com.wiseweb.seatchoose.view.SSThumView;
import com.wiseweb.seatchoose.view.SSView;
import com.wiseweb.util.GetEnc;
import com.wiseweb.util.Util;

public class SelectSeatBuyTicketActivity extends Activity {

	private static final int ROW = 8;
	private static final int EACH_ROW_COUNT = 20;
	private SSView mSSView;
	private SSThumView mSSThumView;
	private ArrayList<SeatInfo> list_seatInfos = new ArrayList<SeatInfo>();
	private ArrayList<ArrayList<Integer>> list_seat_conditions = new ArrayList<ArrayList<Integer>>();
	private ImageView cinemaFilmShare;
	private View cinemaFilmTitleBack;
	private TextView seat1, seat2, seat3, seat4;
	private TextView totalPrice;
	private List seats;
	private int count = 0;
	private RelativeLayout seatsLayout;
	private String d;
	private double price;
	private Button submitOrder;
	private TextView cinameName;
	private TextView movieName;
	private TextView movieTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_seat_buy_ticket);
		// 初始化shareSDK
		// ShareSDK.initSDK(this);
		init();
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			//从CinameDetailActivity获取影院名称
			SharedPreferences sp1 = getSharedPreferences("cinemaInfo", MODE_PRIVATE);
			String cinameNameStr = sp1.getString("cinameName", "");
			cinameName.setText(cinameNameStr);
			//从CienmaSelectFilmActivity获取电影名称
			SharedPreferences sp2 = getSharedPreferences("movieInfo", MODE_PRIVATE);
			String movieNameStr = sp2.getString("movieName", "");
			movieName.setText(movieNameStr);
			//从CinemaSelectFileActivity获取时间
			SharedPreferences sp3 = getSharedPreferences("moviePlan", MODE_PRIVATE);
			String featureTime = sp3.getString("featureTime", "");
			movieTime.setText(featureTime);
		};
	};
	
	//获取座位数据
	Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			HashMap<String, Object> params = new HashMap<String, Object>();
			// action
			params.put("action", "seat_info");
			// time_stamp
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");
			//从CinemaSelectFilmActivity获取planid
			SharedPreferences sp = getSharedPreferences("planid", MODE_PRIVATE);
			long planId = sp.getLong("plan_id", 0);
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "planId=" + planId
					+  "&" + "enc=" + enc + "&" + "time_stamp=" + time_stamp);
			System.out.println(Constant.baseURL + "action="
					+ params.get("action") + "&" + "planId=" + planId
					+  "&" + "enc=" + enc + "&" + "time_stamp=" + time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					try {
						JSONArray jsonArray = new JSONObject(result).getJSONArray("seats");
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
//					Message msg = new Message();
//					// Bundle data = new Bundle();
//					// msg.setData(data);
//					msg.what = LIST_OF_MOVIES_IN_CINEMA;
//					handler.sendMessage(msg);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// ShareSDK.stopSDK(this);
		super.onDestroy();

	}

	private void init() {
		movieTime = (TextView) findViewById(R.id.movie_time_tv);
		movieName = (TextView) findViewById(R.id.movie_name_tv);
		cinameName = (TextView) findViewById(R.id.cinema_film_name);
		mSSView = (SSView) this.findViewById(R.id.mSSView);
		mSSThumView = (SSThumView) this.findViewById(R.id.ss_ssthumview);
		// mSSView.setXOffset(20);
		setSeatInfo();
		seatsLayout = (RelativeLayout) findViewById(R.id.seats);
		seatsLayout.setVisibility(View.VISIBLE);
		mSSView.init(EACH_ROW_COUNT, ROW, list_seatInfos, list_seat_conditions,
				mSSThumView, 5);
		seats = new ArrayList<String>();
		seat1 = (TextView) findViewById(R.id.seat1);
		seat2 = (TextView) findViewById(R.id.seat2);
		seat3 = (TextView) findViewById(R.id.seat3);
		seat4 = (TextView) findViewById(R.id.seat4);
		totalPrice = (TextView) findViewById(R.id.total_price);
		mSSView.setOnSeatClickListener(new OnSeatClickListener() {

			@Override
			public boolean b(int column_num, int row_num, boolean paramBoolean) {
				String desc = "选择了第" + (row_num + 1) + "排" + "第"
						+ (column_num + 1) + "座";
				Toast.makeText(SelectSeatBuyTicketActivity.this,
						desc.toString(), Toast.LENGTH_SHORT).show();
				String de = "" + (row_num + 1) + "排" + (column_num + 1) + "号";
				count++;
				seats.add(de);
				int i = seats.size();
				// 价格设置
				price = 23.0;
				double total_price = price * i;
				totalPrice.setText("" + total_price + "");
				System.out.println("i------" + i);
				if (i == 0) {
					seat1.setText("");
				} else {
					seat1.setText(seats.get(0).toString());
					i--;
				}
				if (i == 0) {
					seat2.setText("");

				} else {
					seat2.setText(seats.get(1).toString());
					i--;
				}
				if (i == 0) {
					seat3.setText("");

				} else {
					seat3.setText(seats.get(2).toString());
					i--;
				}
				if (i == 0) {
					seat4.setText("");
				} else {
					seat4.setText(seats.get(3).toString());
					i--;
				}
				return false;
			}

			@Override
			public boolean a(int column_num, int row_num, boolean paramBoolean) {
				String desc = "取消了第" + (row_num + 1) + "排" + " 第"
						+ (column_num + 1) + "座";
				Toast.makeText(SelectSeatBuyTicketActivity.this,
						desc.toString(), Toast.LENGTH_SHORT).show();
				String de = "" + (row_num + 1) + "排" + (column_num + 1) + "号";

				seats.remove(de);
				count--;
				int i = seats.size();
				price = 23;
				double total_price = price * i;
				totalPrice.setText("" + total_price + "元");
				if (i == 0) {
					seat1.setText("");

				} else {
					seat1.setText(seats.get(0).toString());
					i--;
				}
				if (i == 0) {
					seat2.setText("");

				} else {
					seat2.setText(seats.get(1).toString());
					i--;
				}
				if (i == 0) {
					seat3.setText("");

				} else {
					seat3.setText(seats.get(2).toString());
					i--;
				}
				if (i == 0) {
					seat4.setText("");
				} else {
					seat4.setText(seats.get(3).toString());
					i--;
				}

				System.out.println("seatsss" + seats);
				return false;
			}

			@Override
			public void a() {
				// TODO Auto-generated method stub

			}
		});
		cinemaFilmShare = (ImageView) findViewById(R.id.cinema_film_share);
		cinemaFilmShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// 显示需要分享到哪里
				showShare();
			}

		});
		/**
		 * 返回按钮
		 */
		cinemaFilmTitleBack = (View) findViewById(R.id.cinema_film_title_back);
		cinemaFilmTitleBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}

		});
		submitOrder = (Button)findViewById(R.id.submit_order);
		submitOrder.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SelectSeatBuyTicketActivity.this, SubmitOrderActivity.class);
				SelectSeatBuyTicketActivity.this.startActivity(intent);
			}
			
		});

	}

	/**
	 * 分享到新浪微博的方法
	 */
	@SuppressLint("SdCardPath")
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher,
				getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		// oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		// 根据点击，得到相关的信息，设置
		oks.setText("这是我要分享的文字");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg"); //

		// url仅在微信（包括好友和朋友圈）中使用
		// oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		// oks.setComment("评论");
		// site是分享此内容的网站名称，仅在QQ空间使用
		// oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		// oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		oks.show(this);
	}

	// ////////////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 获得数据 设置座位信息
	 */
	private void setSeatInfo() {
		for (int i = 0; i < ROW; i++) {//
			SeatInfo mSeatInfo = new SeatInfo();
			ArrayList<Seat> mSeatList = new ArrayList<Seat>();
			ArrayList<Integer> mConditionList = new ArrayList<Integer>();
			for (int j = 0; j < EACH_ROW_COUNT; j++) {//
				Seat mSeat = new Seat();
				if (j < 0) {//
					mSeat.setN("Z");
					mConditionList.add(0);
				} else {
					mSeat.setN(String.valueOf(j - 2));
					if (j > 18) {
						mConditionList.add(2);
					} else {
						mConditionList.add(1);
					}

				}
				mSeat.setSeatState("");
				mSeat.setLoveInd("0");
				mSeatList.add(mSeat);
			}
			mSeatInfo.setDesc(String.valueOf(i + 1));
			mSeatInfo.setRow(String.valueOf(i + 1));
			mSeatInfo.setSeatList(mSeatList);
			list_seatInfos.add(mSeatInfo);
			list_seat_conditions.add(mConditionList);
		}
	}
}
