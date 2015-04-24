package com.wiseweb.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiseweb.movie.R;
import com.wiseweb.movie.R.color;

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
	private RelativeLayout cinemaAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cinema_detail);
		initUI();
	}

	void initUI() {
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
		// 设置颜色
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
		cinemaAddress = (RelativeLayout)findViewById(R.id.cinema_address);
		cinemaAddress.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent();
////				intent.setClass(CinemaDetailActivity.this, PoiSearchDemo.class);
//				intent.setClass(CinemaDetailActivity.this, new DemoInfo(R.string.demo_title_poi, R.string.demo_desc_poi,
//						PoiSearchDemo.class).demoClass);
//				CinemaDetailActivity.this.startActivity(intent);
			}
			
		});
	}

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
//		case R.id.cinema_address:
//			Intent intent = new Intent();
//			intent.setClass(this, PoiSearchDemo.class);
//			startActivity(intent);
//			break;
		default: {
			break;
		}
		}
	}
//	private static class DemoInfo {
//		private final int title;
//		private final int desc;
//		private final Class<? extends android.app.Activity> demoClass;
//
//		public DemoInfo(int title, int desc,
//				Class<? extends android.app.Activity> demoClass) {
//			this.title = title;
//			this.desc = desc;
//			this.demoClass = demoClass;
//		}
//	}
}
