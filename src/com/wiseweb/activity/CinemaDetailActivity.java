package com.wiseweb.activity;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wiseweb.constant.Constant;
import com.wiseweb.json.CinemaDetailResult;
import com.wiseweb.json.CinemaDetailResult.CinemaDetail;
import com.wiseweb.movie.R;
import com.wiseweb.movie.R.color;
import com.wiseweb.util.GetEnc;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cinema_detail);
		initUI();
		// 从服务端获取影院详情数据
		// new Thread(runnable).start();
	}

	void initUI() {
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
				// 设置影院名称
				CinemaDetail detail = (CinemaDetail) msg.obj;
				if (!(detail.getCinemaName().equals(null))) {
					//保存影院名称用于SelectSeatBuyTicketActivity获取数据
					SharedPreferences sp = getSharedPreferences("cinemaInfo", Context.MODE_PRIVATE);
					Editor editor = sp.edit();
					String nameStr = detail.getCinemaName();
					editor.putString("cinameName", nameStr);
					editor.commit();
					cinemaName.setText(nameStr);
				}
				// 设置影院评分
				if (!(detail.getScoreCount().equals(null))) {
					scoreCount.setText(detail.getScoreCount());
				}
				// 设置影院视听效果
				if (!(detail.getVisualEffect().equals(null))) {
					visualEffect.setText(detail.getVisualEffect());
				}
				// 设置影院环境
				if (!(detail.getCinemaEnvrionment().equals(null))) {
					cinemaEnvironment.setText(detail.getCinemaEnvrionment());
				}
				// 设置影院周边餐饮
				if (!(detail.getSurrondingRestaurants().equals(null))) {
					surrondingRestaurants.setText(detail
							.getSurrondingRestaurants());
				}
				// 设置影院电话
				if (!(detail.getCinemaTel().equals(null))) {
					phoneNumText.setText(detail.getCinemaTel());
				}
				// 设置影院地址
				if (!(detail.getCinemaAddress().equals(null))) {
					addressText.setText(detail.getCinemaAddress());
				}
				// 设置取票信息
				if (!(detail.getFetchTicket().equals(null))) {
					fetchTicketTv.setText(detail.getFetchTicket());
				} else {
					ticketLayout.setVisibility(View.INVISIBLE);
				}
				// 设置IMAX信息
				if (!(detail.getImax().equals(null))) {
					imaxTv.setText(detail.getImax());
				} else {
					imaxLayout.setVisibility(View.INVISIBLE);
				}
				// 设置3D眼镜信息
				if (!(detail.getGlass().equals(null))) {
					glassTv.setText(detail.getGlass());
				} else {
					glassLayout.setVisibility(View.INVISIBLE);
				}
				// 设置停车信息
				if (!(detail.getPark().equals(null))) {
					parkTv.setText(detail.getPark());
				} else {
					parkLayout.setVisibility(View.INVISIBLE);
				}
				// 设置情侣座信息
				if (!(detail.getLovers().equals(null))) {
					loversTv.setText(detail.getLovers());
				} else {
					loversLayout.setVisibility(View.INVISIBLE);
				}
				// 获取儿童优惠信息
				if (!(detail.getChildren().equals(null))) {
					childrenTv.setText(detail.getChildren());
				} else {
					childrenLayout.setVisibility(View.INVISIBLE);
				}
				// 设置刷卡信息
				if (!(detail.getCard().equals(null))) {
					cardTv.setText(detail.getCard());
				} else {
					cardLayout.setVisibility(View.INVISIBLE);
				}
				// 设置wifi信息
				if (!(detail.getWifi().equals(null))) {
					wifiTv.setText(detail.getWifi());
				} else {
					wifiLayout.setVisibility(View.INVISIBLE);
				}
				// 获取休息区信息
				if (!(detail.getRest().equals(null))) {
					restTv.setText(detail.getRest());
				} else {
					restLayout.setVisibility(View.INVISIBLE);
				}
				// 获取退票退款信息
				if (!(detail.getRefund().equals(null))) {
					refundTv.setText(detail.getRefund());
				} else {
					refundLayout.setVisibility(View.INVISIBLE);
				}
				// 获取优惠信息
				if (!(detail.getCoupon().equals(null))) {
					mContentText.setText(detail.getCoupon());
				}

				// if (msg.what == 0) {
				// String nameStr = (String) msg.obj;
				// cinemaName.setText(nameStr);
				// }
				//
				// if (msg.what == 1) {
				// String scoreStr = (String) msg.obj;
				// scoreCount.setText(scoreStr);
				// }
				// if (msg.what == 2) {
				// String visualStr = (String) msg.obj;
				// visualEffect.setText(visualStr);
				// }
				// if (msg.what == 3) {
				// String environmentStr = (String) msg.obj;
				// cinemaEnvironment.setText(environmentStr);
				// }
				// if (msg.what == 4) {
				// String restaurantsStr = (String) msg.obj;
				// surrondingRestaurants.setText(restaurantsStr);
				// }
				// if (msg.what == 5) {
				// String telStr = (String) msg.obj;
				// phoneNumText.setText(telStr);
				// }
				// if (msg.what == 6) {
				// String addressStr = (String) msg.obj;
				// addressText.setText(addressStr);
				// }
				//
				// if (msg.what == 7) {
				// String fetchTicketStr = (String) msg.obj;
				// fetchTicketTv.setText(fetchTicketStr);
				// }
				// if (msg.what == 8) {
				// String imaxStr = (String) msg.obj;
				// imaxTv.setText(imaxStr);
				// }
				// if (msg.what == 9) {
				// String glassStr = (String) msg.obj;
				// glassTv.setText(glassStr);
				// }
				// if (msg.what == 10) {
				// String parkStr = (String) msg.obj;
				// parkTv.setText(parkStr);
				// }
				// if (msg.what == 11) {
				// String loversStr = (String) msg.obj;
				// loversTv.setText(loversStr);
				// }
				// if (msg.what == 12) {
				// String childrenStr = (String) msg.obj;
				// childrenTv.setText(childrenStr);
				// }
				// if (msg.what == 13) {
				// String cardStr = (String) msg.obj;
				// cardTv.setText(cardStr);
				// }
				// if (msg.what == 14) {
				// String wifiStr = (String) msg.obj;
				// wifiTv.setText(wifiStr);
				// }
				// if (msg.what == 15) {
				// String restStr = (String) msg.obj;
				// restTv.setText(restStr);
				// }
				// if (msg.what == 16) {
				// String refundStr = (String) msg.obj;
				// refundTv.setText(refundStr);
				// }
				// if (msg.what == 17) {
				// String couponStr = (String) msg.obj;
				// mContentText.setText(couponStr);

			}
		};
	};

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
			int cinema_id = sp.getInt("cinemaId", 0);
			params.put("ciname_id", cinema_id);
			String enc = GetEnc.getEnc(params, "wiseMovie");
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "cinema_id=" + cinema_id
					+ "&" + "enc=" + enc + "&" + "time_stamp=" + time_stamp);
			System.out.println(Constant.baseURL + "action="
					+ params.get("action") + "&" + "cinema_id=" + cinema_id
					+ "&" + "enc=" + enc + "&" + "time_stamp=" + time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					Gson gson = new Gson();
					CinemaDetailResult cinemaDetailResult = gson.fromJson(
							result, CinemaDetailResult.class);
					CinemaDetail cinemaDetail = cinemaDetailResult
							.getCinemaDetail();
					Message msg = null;
					if (cinemaDetail != null) {
						msg = handler.obtainMessage(0, cinemaDetail);
						handler.sendMessage(msg);
					} else {
						Toast.makeText(CinemaDetailActivity.this,
								"cinemaDetail为空，无可用信息", 0).show();
					}
					// // 获取影院名称
					// if (!(cinemaDetail.getCinemaName().equals(null))) {
					// String name = cinemaDetail.getCinemaName();
					// msg = handler.obtainMessage(0, name);
					// handler.sendMessage(msg);
					// }
					// // 获取影院评分
					// if (!(cinemaDetail.getScoreCount().equals(null))) {
					// String score = cinemaDetail.getScoreCount();
					// msg = handler.obtainMessage(1, score);
					// handler.sendMessage(msg);
					// }
					// // 获取影院视听效果
					// if (!(cinemaDetail.getVisualEffect().equals(null))) {
					// String visual = cinemaDetail.getVisualEffect();
					// msg = handler.obtainMessage(2, visual);
					// handler.sendMessage(msg);
					// }
					// // 获取影院环境
					// if (!(cinemaDetail.getCinemaEnvrionment().equals(null)))
					// {
					// String envrionment = cinemaDetail
					// .getCinemaEnvrionment();
					// msg = handler.obtainMessage(3, envrionment);
					// handler.sendMessage(msg);
					// }
					// // 获取影院周边餐饮
					// if
					// (!(cinemaDetail.getSurrondingRestaurants().equals(null)))
					// {
					// String restaurants = cinemaDetail
					// .getSurrondingRestaurants();
					// msg = handler.obtainMessage(4, restaurants);
					// handler.sendMessage(msg);
					// }
					// // 获取影院电话
					// if (!(cinemaDetail.getCinemaTel().equals(null))) {
					// String tel = cinemaDetail.getCinemaTel();
					// msg = handler.obtainMessage(5, tel);
					// handler.sendMessage(msg);
					// }
					// // 获取影院地址
					// if (!(cinemaDetail.getCinemaAddress().equals(null))) {
					// String address = cinemaDetail.getCinemaAddress();
					// msg = handler.obtainMessage(6, address);
					// handler.sendMessage(msg);
					// }
					// // 获取取票信息
					// if (!(cinemaDetail.getFetchTicket().equals(null))) {
					// String fetchTicket = cinemaDetail.getFetchTicket();
					// msg = handler.obtainMessage(7, fetchTicket);
					// handler.sendMessage(msg);
					// } else {
					// ticketLayout.setVisibility(View.INVISIBLE);
					// }
					// // 获取IMAX信息
					// if (!(cinemaDetail.getImax().equals(null))) {
					// String imax = cinemaDetail.getImax();
					// msg = handler.obtainMessage(8, imax);
					// handler.sendMessage(msg);
					// } else {
					// imaxLayout.setVisibility(View.INVISIBLE);
					// }
					// // 获取3D眼镜信息
					// if (!(cinemaDetail.getGlass().equals(null))) {
					// String glass = cinemaDetail.getGlass();
					// msg = handler.obtainMessage(9, glass);
					// handler.sendMessage(msg);
					// } else {
					// glassLayout.setVisibility(View.INVISIBLE);
					// }
					// // 获取停车信息
					// if (!(cinemaDetail.getPark().equals(null))) {
					// String park = cinemaDetail.getPark();
					// msg = handler.obtainMessage(10, park);
					// handler.sendMessage(msg);
					// } else {
					// parkLayout.setVisibility(View.INVISIBLE);
					// }
					// // 获取情侣座信息
					// if (!(cinemaDetail.getLovers().equals(null))) {
					// String lovers = cinemaDetail.getLovers();
					// msg = handler.obtainMessage(11, lovers);
					// handler.sendMessage(msg);
					// } else {
					// loversLayout.setVisibility(View.INVISIBLE);
					// }
					// // 获取儿童优惠信息
					// if (!(cinemaDetail.getChildren().equals(null))) {
					// String children = cinemaDetail.getChildren();
					// msg = handler.obtainMessage(12, children);
					// handler.sendMessage(msg);
					// } else {
					// childrenLayout.setVisibility(View.INVISIBLE);
					// }
					// // 获取刷卡信息
					// if (!(cinemaDetail.getCard().equals(null))) {
					// String card = cinemaDetail.getCard();
					// msg = handler.obtainMessage(13, card);
					// handler.sendMessage(msg);
					// } else {
					// cardLayout.setVisibility(View.INVISIBLE);
					// }
					// // 获取wifi信息
					// if (!(cinemaDetail.getWifi().equals(null))) {
					// String wifi = cinemaDetail.getWifi();
					// msg = handler.obtainMessage(14, wifi);
					// handler.sendMessage(msg);
					// } else {
					// wifiLayout.setVisibility(View.INVISIBLE);
					// }
					// // 获取休息区信息
					// if (!(cinemaDetail.getRest().equals(null))) {
					// String rest = cinemaDetail.getRest();
					// msg = handler.obtainMessage(15, rest);
					// handler.sendMessage(msg);
					// } else {
					// restLayout.setVisibility(View.INVISIBLE);
					// }
					// // 获取退票退款信息
					// if (!(cinemaDetail.getRefund().equals(null))) {
					// String refund = cinemaDetail.getRefund();
					// msg = handler.obtainMessage(16, refund);
					// handler.sendMessage(msg);
					// } else {
					// refundLayout.setVisibility(View.INVISIBLE);
					// }
					// // 获取优惠信息
					// if (!(cinemaDetail.getCoupon().equals(null))) {
					// String coupon = cinemaDetail.getCoupon();
					// msg = handler.obtainMessage(17, coupon);
					// handler.sendMessage(msg);
					// }
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
