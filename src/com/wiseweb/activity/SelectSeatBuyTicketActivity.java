package com.wiseweb.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.wiseweb.bean.Seat;
import com.wiseweb.bean.SeatInfo;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.UpComingFilmAdapter;
import com.wiseweb.movie.R;
import com.wiseweb.seatchoose.view.OnSeatClickListener;
import com.wiseweb.seatchoose.view.SSThumView;
import com.wiseweb.seatchoose.view.SSView;
import com.wiseweb.util.GetEnc;
import com.wiseweb.util.Util;

public class SelectSeatBuyTicketActivity extends Activity {

	private static final int ROW = 8;
	private static final int EACH_ROW_COUNT = 20;
	private static final int ORDER_ADD = 0;
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
	private EditText orderPhone;
	private String mobile;
	private SharedPreferences orderPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_seat_buy_ticket);
		// 初始化shareSDK
		// ShareSDK.initSDK(this);
		initView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// ShareSDK.stopSDK(this);
		super.onDestroy();

	}

	private void initView() {
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
		orderPhone = (EditText) findViewById(R.id.order_phone);

		submitOrder = (Button) findViewById(R.id.submit_order);
		submitOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 验证手机号的合法性
				if (orderPhone.getText() == null) {// 手机号没输入
					AlertDialog.Builder builder = new AlertDialog.Builder(
							SelectSeatBuyTicketActivity.this);
					builder.setTitle("提示");
					builder.setMessage("请输入手机号");
					builder.setPositiveButton("确定", null);
					builder.show();
				} else if (Util.isMobileNum(orderPhone.getText().toString()) == false) { // 输入的手机号不合法
					new AlertDialog.Builder(SelectSeatBuyTicketActivity.this)
							.setTitle("提示").setMessage("请输入正确的电话号码")
							.setPositiveButton("确定", null).show();
				} else {// 合法手机号
					mobile = orderPhone.getText().toString();
					
					// 创建订单
					Thread t = new Thread(createOrderRunnable);
					t.start();
					try {
						t.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

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

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case ORDER_ADD:

				break;

			}
		}
	};
	/**
	 * 创建订单
	 */
	Runnable createOrderRunnable = new Runnable() {

		@Override
		public void run() {
			HashMap<String, Object> params = new HashMap<String, Object>();
			long time_stamp = Util.getTimeStamp();
			params.put("time_stamp", time_stamp);
			params.put("action", "order_add"); // 接口名称
			mobile = "13641094940";
			params.put("mobile", mobile); // 手机号
			String seatNo = "11059401-15-0000000000000001-1-02,2C11059401-15-0000000000000001-1-01";
			params.put("seat_no", seatNo); // 座位id
			long planId = 35346105;
			params.put("plan_id", planId); // 场次id
			int sendMessage = 0;
			params.put("send_message", sendMessage);
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
					"http://test.komovie.cn/api_movie/service?action=order_add&mobile=13641094940&seat_no=11059401-15-0000000000000001-1-01%2C11059401-15-0000000000000001-1-02&plan_id=35346118&send_message=0&time_stamp=1432802355579&enc=8ee345d93d774e999bc6346fc9f78a72");
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {

					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					// 获得信息
					System.out.println("result----" + result);

					JSONObject order = new JSONObject(result)
							.getJSONObject("order");
					String orderId = order.getString("orderId");
					// String activityId = order.getString("activityId");
					String agio = order.getString("agio");// 还需支付的金额
					String ticketNo = order.getString("ticketNo");
					String mobile = order.getString("mobile"); // 手机号
					String money = order.getString("money"); // 订单总额
					int orderStatus = order.getInt("orderStatus");// 订单状态
					String seatInfo = order.getString("seatInfo");
//					String seatDesc = order.getString("chooseseats");
					JSONObject plan = order.getJSONObject("plan");
					JSONObject cinema = plan.getJSONObject("cinema");
					String cinemaName = cinema.getString("cinemaName"); // 影院名称
					// int platform = cinema.getInt("platform");
					String featureTime = plan.getString("featureTime");
					String hallName = plan.getString("hallName"); // 厅名
					JSONObject movie = plan.getJSONObject("movie");
					String movieName = movie.getString("movieName");// 电影名称
					// 汇总需要的信息带到确认订单activity
					orderPreferences = getSharedPreferences("orderConfig",
							Context.MODE_PRIVATE);
					SharedPreferences.Editor e = orderPreferences.edit();
					e.putString("orderId", orderId);
					e.putString("agio", agio);
					e.putString("mobile", mobile);
					e.putString("money", money); // 订单总额
					e.putString("featureTime", featureTime);
					e.putString("cinemaName", cinemaName);
					e.putString("movieName", movieName);
					e.putString("hallName", hallName);
//					e.putString("seatDesc", seatDesc);
					e.commit();

					Message msg = new Message();
					// Bundle data = new Bundle();
					msg.what = ORDER_ADD;
					handler.sendMessage(msg);

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
			Intent intent = new Intent();
			intent.setClass(SelectSeatBuyTicketActivity.this,
					SubmitOrderActivity.class);
			SelectSeatBuyTicketActivity.this.startActivity(intent);
		}

	};

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// JSONObject seatInfoResult = new JSONObject();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("action", "seat_info");
			/**
			 * 得到planId
			 */
			Long planId = 385335l;
			params.put("planId", planId);

			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");

			String enc = GetEnc.getEnc(params, "wiseMovie");

			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "planId=" + planId + "&"
					+ "enc=" + enc + "&" + "time_stamp=" + time_stamp);

			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					JSONArray seatsArray = new JSONObject(result)
							.getJSONArray("seats");
					for (int i = 0; i < seatsArray.length(); i++) {
						// 取得第i个座位
						JSONObject seat = (JSONObject) seatsArray
								.getJSONObject(i);

						Seat mSeat = new Seat();

						int seatType = seat.getInt("seatType"); // 0:普通座 1：情侣座
						int graphCol = seat.getInt("graphCol"); // 相对于屏幕的列号，第四象限坐标系x
						int graphRow = seat.getInt("graphRow"); // 相对于屏幕的排号，第四象限坐标系y
						String hallId = seat.getString("hallId"); // 厅ID
						String seatCol = seat.getString("seatCol"); // 影厅规定的列号，用于打印影票
						String seatNo = seat.getString("seatNo"); // 座位号
						String seatPieceName = seat.getString("seatPieceName");
						String seatPieceNo = seat.getString("seatPieceNo");
						String seatRow = seat.getString("seatRow");
						String seatState = seat.getString("seatState");

						if (seatType == 1) { // 普通座
							Boolean isLoverL = seat.getBoolean("isLoverL");
						}

					}

					Message msg = new Message();
					// Bundle data = new Bundle();
					// msg.setData(data);
					msg.what = 0;
					// handler.sendMessage(msg);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	};

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
				mSeat.setLoveInd(true);
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
