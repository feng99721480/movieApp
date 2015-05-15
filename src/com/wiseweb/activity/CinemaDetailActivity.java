package com.wiseweb.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.HorizontalListViewAdapter;
import com.wiseweb.fragment.adapter.HorizontalListViewCinemaDetailAdapter;
import com.wiseweb.json.CinemaDetailResult;
import com.wiseweb.json.CinemaDetailResult.CinemaDetail;
import com.wiseweb.movie.R;
import com.wiseweb.movie.R.color;
import com.wiseweb.ui.HorizontalListView;
import com.wiseweb.util.GetEnc;
import com.wiseweb.util.Util;

public class CinemaDetailActivity extends Activity implements OnClickListener {
	private View cinemaDetailBack;
	private static final int VIDEO_CONTENT_DESC_MAX_LINE = 3;// 默认展示最大行数3行
	private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
	private static final int SHRINK_UP_STATE = 3;// 收起状态
	private static final int SPREAD_STATE = 2;// 展开状态
	private static int mState = SHRINK_UP_STATE;// 默认收起状态
	private int lineCount;
	private TextView mContentText;// 展示文本内容
	private RelativeLayout mShowMore;// 展示更多
	private ImageView mImageSpread;// 展开
	private ImageView mImageShrinkUp;// 收起
	private TextView visualEffect; //
	private TextView cinemaEnvironment;
	private TextView surrondingRestaurants;
	private RelativeLayout cinemaPhone;
	private String phoneNum;
	private TextView phoneNumText;
	private RelativeLayout cinemaAddressLayout;
	private TextView addressText;
	private SharedPreferences cinemaAddressPreferences; // 保存影院地址
	private TextView cinemaName, scoreCount, fetchTicketTv, imaxTv, glassTv,
			parkTv, loversTv, childrenTv, cardTv, wifiTv, restTv, refundTv;
	private RelativeLayout ticketLayout;
	private LinearLayout imaxLayout, glassLayout, parkLayout, loversLayout,
			childrenLayout, cardLayout, wifiLayout, restLayout, refundLayout;
	private TextView businessHours;
	private TextView drivePath;
	private RelativeLayout cinemaDrivePath;
	private ImageView drivePathMoreImg;
	private TextView cinemaInfoTv;
	private RelativeLayout cinemaInfo;
	private ImageView infoMoreImg;
	private ImageView logoImg;
	private Bitmap logoBitmap ;
	private HorizontalListView hListView;
	private HorizontalListViewCinemaDetailAdapter hListAdapter;
	private List<Bitmap> bmps = new ArrayList<Bitmap>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cinema_detail);
		initUI();
		// 从服务端获取影院详情数据
		new Thread(runnable).start();
	}

	void initUI() {
		hListView = (HorizontalListView) findViewById(R.id.cinema_hlistview);
		logoImg = (ImageView) findViewById(R.id.cinema_logo_img);
		infoMoreImg = (ImageView) findViewById(R.id.info_more_img);
		cinemaInfo = (RelativeLayout) findViewById(R.id.cinema_info);
		cinemaInfoTv = (TextView) findViewById(R.id.view_cinema_info);
		drivePathMoreImg = (ImageView) findViewById(R.id.drivePath_more_img);
		cinemaDrivePath = (RelativeLayout) findViewById(R.id.cinema_drivePath);
		drivePath = (TextView) findViewById(R.id.view_drivePath);
		businessHours = (TextView) findViewById(R.id.view_business_hours);
		cinemaName = (TextView) findViewById(R.id.cinema_name);
		scoreCount = (TextView) findViewById(R.id.score_count);
		fetchTicketTv = (TextView) findViewById(R.id.cinema_feature_ticket_tv);
		imaxTv = (TextView) findViewById(R.id.cinema_feature_imax_tv);
		glassTv = (TextView) findViewById(R.id.cinema_feature_3dglass_tv);
		parkTv = (TextView) findViewById(R.id.cinema_feature_park_tv);
		loversTv = (TextView) findViewById(R.id.cinema_feature_lovers_tv);
		childrenTv = (TextView) findViewById(R.id.cinema_feature_children_tv);
		cardTv = (TextView) findViewById(R.id.cinema_feature_card_tv);
		wifiTv = (TextView) findViewById(R.id.cinema_feature_wifi_tv);
		restTv = (TextView) findViewById(R.id.cinema_feature_rest_tv);
		refundTv = (TextView) findViewById(R.id.cinema_feature_refund_tv);

		ticketLayout = (RelativeLayout) findViewById(R.id.cinema_feature_ticket);
		imaxLayout = (LinearLayout) findViewById(R.id.cinema_feature_imax);
		glassLayout = (LinearLayout) findViewById(R.id.cinema_feature_3dglass);
		parkLayout = (LinearLayout) findViewById(R.id.cinema_feature_park);
		loversLayout = (LinearLayout) findViewById(R.id.cinema_feature_lovers);
		childrenLayout = (LinearLayout) findViewById(R.id.cinema_feature_children);
		cardLayout = (LinearLayout) findViewById(R.id.cinema_feature_card);
		wifiLayout = (LinearLayout) findViewById(R.id.cinema_feature_wifi);
		restLayout = (LinearLayout) findViewById(R.id.cinema_feature_rest);
		refundLayout = (LinearLayout) findViewById(R.id.cinema_feature_refund);
		cinemaDetailBack = (View) findViewById(R.id.cinema_detail_back);
		cinemaDetailBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}

		});
		hListAdapter = new HorizontalListViewCinemaDetailAdapter(CinemaDetailActivity.this, bmps);
		hListView.setAdapter(hListAdapter);
		//控制影院详情的展开和收缩
		cinemaInfo.setOnClickListener(new OnClickListener() {
			boolean flag = true;
			@Override
			public void onClick(View v) {
				if(flag){
					flag = false;
					cinemaInfoTv.setEllipsize(null);//展开
					cinemaInfoTv.setSingleLine(flag);
					cinemaInfoTv.setTextSize(10);
					infoMoreImg.setVisibility(View.INVISIBLE);
				}else{
					flag = true;
					cinemaInfoTv.setEllipsize(TruncateAt.END);//收缩
					cinemaInfoTv.setSingleLine(flag);
					cinemaInfoTv.setTextSize(18);
					infoMoreImg.setVisibility(View.VISIBLE);
				}
			}
		});
		//控制公交线路的展开和收缩
		cinemaDrivePath.setOnClickListener(new OnClickListener() {
			boolean flag = true;
			@Override
			public void onClick(View v) {
				if(flag){
					flag = false;
					drivePath.setEllipsize(null);//展开
					drivePath.setSingleLine(flag);
					drivePath.setTextSize(10);
					drivePathMoreImg.setVisibility(View.INVISIBLE);
				}else{
					flag = true;
					drivePath.setEllipsize(TruncateAt.END);//收缩
					drivePath.setSingleLine(flag);
					drivePath.setTextSize(18);
					drivePathMoreImg.setVisibility(View.VISIBLE);
				}
			}
		});
		mContentText = (TextView) findViewById(R.id.text_content);

		mContentText
				.setText("这是优惠信息这是优惠信息这是优惠信息这是优惠信息这是优惠信息这是优惠信息这是优惠信息这是优惠信息这是优惠信息这是优惠信息这是优惠信息这是优惠信息");
		// lineCount = mContentText.getLineCount();
		mShowMore = (RelativeLayout) findViewById(R.id.show_more);
		mImageSpread = (ImageView) findViewById(R.id.spread);
		mImageShrinkUp = (ImageView) findViewById(R.id.shrink_up);
		mShowMore.setOnClickListener(this);
		// if (lineCount < VIDEO_CONTENT_DESC_MAX_LINE) {
		// mShowMore.setVisibility(View.GONE);
		// }else{
		// mShowMore.setVisibility(View.VISIBLE);
		// }
		// 设置颜色，显示的是灰色，没有达到预期的效果
		visualEffect = (TextView) findViewById(R.id.visual_effect);
		cinemaEnvironment = (TextView) findViewById(R.id.cinema_envrionment);
		surrondingRestaurants = (TextView) findViewById(R.id.surronding_restaurants);
		SpannableStringBuilder effect = new SpannableStringBuilder(visualEffect
				.getText().toString());
		SpannableStringBuilder environment = new SpannableStringBuilder(
				cinemaEnvironment.getText().toString());
		SpannableStringBuilder restaurants = new SpannableStringBuilder(
				surrondingRestaurants.getText().toString());
		ForegroundColorSpan orangeSpan = new ForegroundColorSpan(
				color.main_color);
		effect.setSpan(orangeSpan, 5, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		environment.setSpan(orangeSpan, 5, 7,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		restaurants.setSpan(orangeSpan, 5, 7,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		visualEffect.setText(effect);
		cinemaEnvironment.setText(environment);
		surrondingRestaurants.setText(restaurants);
		cinemaPhone = (RelativeLayout) findViewById(R.id.cinema_phone);
		phoneNumText = (TextView) findViewById(R.id.phone_number);
		// 电话号码
		phoneNum = phoneNumText.getText().toString();
		cinemaPhone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent call = new Intent();
				// 直接拨出去了
				// call.setAction(Intent.ACTION_CALL);
				// 跳转到拨号页面并已经输入号码
				call.setAction(Intent.ACTION_DIAL);
				call.setData(Uri.parse("tel:" + phoneNum));
				// callPhoneAndSendMessage.this.startActivity(call);
				startActivity(call);
			}

		});
		cinemaAddressLayout = (RelativeLayout) findViewById(R.id.cinema_address);
		cinemaAddressLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CinemaDetailActivity.this,
						ViewCinemaLocationActivity.class);
				// intent.setClass(CinemaDetailActivity.this, new
				// DemoInfo(R.string.demo_title_poi, R.string.demo_desc_poi,
				// PoiSearchDemo.class).demoClass);
				CinemaDetailActivity.this.startActivity(intent);
			}

		});
		addressText = (TextView) findViewById(R.id.address_text);
		cinemaAddressPreferences = this.getSharedPreferences("cinemaAddress",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = cinemaAddressPreferences.edit();
		editor.putString("cinemaAddress", addressText.getText().toString());
		editor.commit();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				CinemaDetail detail = (CinemaDetail) msg.obj;
				
				//设置影院logo
				if(detail.getLogo() != null){
					String logoPath = detail.getLogo();
					System.out.println("logoPath--->"+logoPath);
					Bitmap logo=null;
					try {
						logo = getLogo(logoPath);
						if(logo != null){
							logoImg.setImageBitmap(logo);
						}else{
							System.out.println("没找到LOGO资源");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				//设置影院图库
				if(detail.getGalleries().length > 0){
					String[] galleryPath = detail.getGalleries();
					
					for (int i = 0; i < galleryPath.length; i++) {
						try {
							Bitmap gallery = getLogo(galleryPath[i]);
							System.out.println("gallery------->"+gallery);
							
							System.out.println("该影院有图片");
							bmps.add(gallery);
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}else{
					
					System.out.println("该影院没有图片");
					hListView.setVisibility(View.GONE);
					
				}
//				else {
//					System.out.println("该影院没有图片");
//					hListView.setVisibility(View.INVISIBLE);
//				}
				if(detail.getCityName() != null){
					//保存城市名称用于ViewCinemaLocationActivity
					SharedPreferences sp = getSharedPreferences("cityInfo", Context.MODE_PRIVATE);
					Editor editor = sp.edit();
					String cityNameStr = detail.getCityName();
					editor.putString("cityName", cityNameStr);
					editor.commit();
				}
				// 设置影院名称
				if (detail.getCinemaName() != null) {
					//保存影院名称用于SelectSeatBuyTicketActivity获取数据
					SharedPreferences sp = getSharedPreferences("cinemaInfo", Context.MODE_PRIVATE);
					Editor editor = sp.edit();
					String nameStr = detail.getCinemaName();
					editor.putString("cinameName", nameStr);
					editor.commit();
					cinemaName.setText(nameStr);
					System.out.println("nameStr--------"+nameStr);
				}
				// 设置影院评分
				if (detail.getScore() != null) {
					scoreCount.setText(detail.getScore().length+"人评分");
				}else{
					scoreCount.setText("0人评分");
				}
				// 设置影院视听效果
				if(detail.getVisualEffect() != null){
					visualEffect.setText(detail.getVisualEffect());
				}else{
					System.out.println("视觉效果无数据");
					visualEffect.setText("视觉效果：出色");
				}
				// 设置影院环境
				if (detail.getCinemaEnvrionment() != null) {
					cinemaEnvironment.setText(detail.getCinemaEnvrionment());
				}else{
					System.out.println("影院环境无数据");
					cinemaEnvironment.setText("影院环境：舒适");
				}
				// 设置影院周边餐饮
				if (detail.getSurrondingRestaurants() != null) {
					surrondingRestaurants.setText(detail
							.getSurrondingRestaurants());
				}else{
					System.out.println("周边餐饮无数据");
					surrondingRestaurants.setText("周边餐饮：热闹");
				}
				// 设置影院电话
				if (!(detail.getCinemaTel().equals(null))) {
					phoneNumText.setText(detail.getCinemaTel());
					System.out.println("影院电话---------"+detail.getCinemaTel());
				}else{
					phoneNumText.setText("无电话信息");
				}
				// 设置影院地址
				if (!(detail.getCinemaAddress().equals(null))) {
					System.out.println("影院地址----------"+detail.getCinemaAddress());
					addressText.setText(detail.getCinemaAddress());
				}else{
					addressText.setText("无地址信息");
				}
				//设置营业时间
				if(!(detail.getOpenTime().equals(null))){
					System.out.println("营业时间---------"+detail.getOpenTime());
					businessHours.setText(detail.getOpenTime());
				}else{
					businessHours.setText("无营业时间信息");
				}
				//设置公交线路
				if(!(detail.getDrivePath().equals(null))){
					System.out.println("公交线路---------"+detail.getOpenTime());
					drivePath.setText(detail.getDrivePath());
				}else{
					drivePath.setText("无公交线路信息");
				}
				//设置影院详情
				if(!(detail.getCinemaIntro().equals(null))){
					System.out.println("影院详情---------"+detail.getOpenTime());
					cinemaInfoTv.setText(detail.getCinemaIntro());
				}else{
					cinemaInfoTv.setText("无影院详情信息");
				}
				// 设置取票信息
				if (detail.getFetchTicket() != null) {
					fetchTicketTv.setText(detail.getFetchTicket());
				} else {
					System.out.println("无取票信息数据");
					fetchTicketTv.setText("无取票信息数据");
					//ticketLayout.setVisibility(View.GONE);
				}
				// 设置IMAX信息
				if (detail.getImax() != null) {
					imaxTv.setText(detail.getImax());
				} else {
					System.out.println("无IMAX信息数据");
					imaxTv.setText("无IMAX信息数据");
					//imaxLayout.setVisibility(View.GONE);
				}
				// 设置3D眼镜信息
				if (detail.getGlass() != null) {
					glassTv.setText(detail.getGlass());
				} else {
					System.out.println("无3D眼镜信息数据");
					glassTv.setText("无3D眼镜信息数据");
					//glassLayout.setVisibility(View.GONE);
				}
				// 设置停车信息
				if (detail.getPark() != null) {
					parkTv.setText(detail.getPark());
				} else {
					System.out.println("无停车信息数据");
					parkTv.setText("无停车信息数据");
					//parkLayout.setVisibility(View.GONE);
				}
				// 设置情侣座信息
				if (detail.getLovers() != null) {
					loversTv.setText(detail.getLovers());
				} else {
					System.out.println("无情侣座信息数据");
					loversTv.setText("无情侣座信息数据");
					//loversLayout.setVisibility(View.GONE);
				}
				// 获取儿童优惠信息
				if (detail.getChildren() != null) {
					childrenTv.setText(detail.getChildren());
				} else {
					System.out.println("无儿童优惠座信息数据");
					childrenTv.setText("无儿童优惠座信息数据");
					//childrenLayout.setVisibility(View.GONE);
				}
				// 设置刷卡信息
				if (detail.getCard() != null) {
					cardTv.setText(detail.getCard());
				} else {
					System.out.println("无刷卡信息数据");
					cardTv.setText("无刷卡信息数据");
					//cardLayout.setVisibility(View.GONE);
				}
				// 设置wifi信息
				if (detail.getWifi() != null) {
					wifiTv.setText(detail.getWifi());
				} else {
					System.out.println("无wifi信息数据");
					wifiTv.setText("无wifi信息数据");
					//wifiLayout.setVisibility(View.GONE);
				}
				// 获取休息区信息
				if (detail.getRest() != null) {
					restTv.setText(detail.getRest());
				} else {
					System.out.println("无休息区信息数据");
					restTv.setText("无休息区信息数据");
					//restLayout.setVisibility(View.GONE);
				}
				// 获取退票退款信息
				if (detail.getRefund() != null) {
					refundTv.setText(detail.getRefund());
				} else {
					System.out.println("无退票退款信息数据");
					refundTv.setText("无退票退款信息数据");
					//refundLayout.setVisibility(View.GONE);
				}
				// 获取优惠信息
				if (detail.getCoupon() != null) {
					mContentText.setText(detail.getCoupon());
				}else{
					System.out.println("无优惠信息数据");
					mContentText.setText("无优惠信息");
				}


			}
		};
	};
	//获取影院logo
	
		public  Bitmap getLogo(final String path) throws InterruptedException{
			
			Thread t = new Thread() {
				public void run() {
					// 连接服务器 用get请求获取图片
					try {
						URL url = new URL(path);
						// 根据URL发送http请求
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						// 设置请求的方式
						conn.setRequestMethod("GET");
						// 设置链接超时时间
						conn.setConnectTimeout(60000);
						conn.setDoInput(true);
						// conn.setRequestProperty("encoding","utf-8");
						// 得到服务器的响应码 200(成功) 404(资源没找到) 503(服务器错误)
						int code = conn.getResponseCode();
						System.out.println("code=" + code);
						if (code == 200) {
							InputStream is = conn.getInputStream();
							// 把流里面的内容转化为Bitmap
							logoBitmap = BitmapFactory.decodeStream(is);
							if(logoBitmap != null){
								System.out.println("logoBitmap---->"+logoBitmap);
								
							}else{
								System.out.println("logoBitmap为空");
							}
							
						} else {
							Toast.makeText(CinemaDetailActivity.this, "显示图片失败", 0)
									.show();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				};
			};
			t.start();
			//*加入UI线程 等这个线程执行完 在执行UI线程 要不然UI线程获取不到Bitmap
			t.join();
			return logoBitmap;
		}
	// 获取数据
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("action", "cinema_info");
			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");
			// 从cinameFragment里得到cinameId
			SharedPreferences sp = getSharedPreferences("cinemaConfig",
					MODE_PRIVATE);
			int cinemaId = sp.getInt("cinemaId", 0);
			//int cinema_id = 35;
			params.put("cinemaId", cinemaId);
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "cinemaId=" + cinemaId
					+ "&" + "enc=" + enc + "&" + "time_stamp=" + time_stamp);
			System.out.println("*******************CinemaDetailActivity*********************");
			System.out.println(Constant.baseURL + "action="
					+ params.get("action") + "&" + "cinemaId=" + cinemaId
					+ "&" + "enc=" + enc + "&" + "time_stamp=" + time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				System.out.println("应答码="+httpResponse.getStatusLine().getStatusCode());
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					System.out.println("result="+result);
					Gson gson = new Gson();
					CinemaDetailResult cinemaDetailResult = gson.fromJson(
							result, CinemaDetailResult.class);
					CinemaDetail cinema = cinemaDetailResult
							.getCinema();
					
					System.out.println("cinemaDetailResult="+cinemaDetailResult.toString());
					Message msg = null;
					
						if (cinema != null) {
							System.out.println("cinema==="+cinema);
							msg = handler.obtainMessage(0, cinema);
							handler.sendMessage(msg);
						} else {
							System.out.println("cinema为空");
							Toast.makeText(CinemaDetailActivity.this,
									"cinemaDetail为空，无可用信息", 0).show();
						}
					
					
					
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.show_more:
			if (mState == SPREAD_STATE) {
				mContentText.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
				mContentText.requestLayout();
				mImageShrinkUp.setVisibility(View.GONE);
				mImageSpread.setVisibility(View.VISIBLE);
				mState = SHRINK_UP_STATE;
			} else if (mState == SHRINK_UP_STATE) {
				mContentText.setMaxLines(Integer.MAX_VALUE);
				mContentText.requestLayout();
				mImageShrinkUp.setVisibility(View.VISIBLE);
				mImageSpread.setVisibility(View.GONE);
				mState = SPREAD_STATE;
			}
			// case R.id.cinema_address:
			// Intent intent = new Intent();
			// intent.setClass(CinemaDetailActivity.this, PoiSearchDemo.class);
			// startActivity(intent);
			// break;
		default: {
			break;
		}
		}
	}
}
