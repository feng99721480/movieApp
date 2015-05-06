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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.google.gson.Gson;
import com.wiseweb.constant.Constant;
import com.wiseweb.json.MovieDetailResult;
import com.wiseweb.json.MovieDetailResult.MovieDetail;
import com.wiseweb.movie.R;
import com.wiseweb.util.GetEnc;
import com.wiseweb.util.Util;

public class FilmDetailsActivity extends Activity {
	private View filmDetailBack;
	private Button buyTicket;
	private RatingBar filmRatingBar;
	private TextView filmRatingText;
	private TextView filmWant;
	private TextView rateFilm;
	private TextView clickShare;
	private RelativeLayout wantShareLayout;
	private RelativeLayout rateLayout;
	private Boolean wantShareFlag = false;
	private Boolean rateFlag = false;
	private Bitmap want;
	private ImageSpan imgSpan;
	private SpannableString spanString;
	private ImageView movieDetailShare;
	private ImageView movieImg;
	private TextView movieType;
	private TextView country;
	private TextView movieLength;
	private TextView publishTime;
	private TextView movieName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.movie_details);
		initView();
		filmDetailBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}

		});
		//
		buyTicket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(FilmDetailsActivity.this,
						BuyTicketSelectCinema.class);
				startActivity(intent);
			}

		});
		// 设置电影评分
		// 让评分/2才为设置的ratingbar的分数
		filmRatingBar.setRating((float) (7.0 / 2));
		// ratingbar显示的评分的2倍为filmRatingText的值
		// 如果是取得的数据就不用*2
		filmRatingText.setText(filmRatingBar.getRating() * 2 + "分");
		want = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_wish_off);
		imgSpan = new ImageSpan(this, want);
		spanString = new SpannableString("icon");
		spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		filmWant.setText(spanString);
		filmWant.append("想看");
		// filmWant
		// filmWant.setOnClickListener(null);
		filmWant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (wantShareFlag == false) {
					if (rateLayout.getVisibility() == View.VISIBLE) {
						rateLayout.setVisibility(View.GONE);
						rateFlag = false;
					}
					wantShareLayout.setVisibility(View.VISIBLE);
					want = BitmapFactory.decodeResource(getResources(),
							R.drawable.ic_wish_on);
					imgSpan = new ImageSpan(want);
					spanString = new SpannableString("icon");
					spanString.setSpan(imgSpan, 0, 4,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					filmWant.setText(spanString);
					filmWant.append("想看");
					wantShareFlag = true;
				} else {
					wantShareLayout.setVisibility(View.GONE);
					want = BitmapFactory.decodeResource(getResources(),
							R.drawable.ic_wish_off);
					imgSpan = new ImageSpan(want);
					spanString = new SpannableString("icon");
					spanString.setSpan(imgSpan, 0, 4,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					filmWant.setText(spanString);
					filmWant.append("想看");
					wantShareFlag = false;
				}
			}

		});
		//
		Bitmap rate = BitmapFactory.decodeResource(getResources(),
				R.drawable.rating_medium_unselect);
		ImageSpan imgRate = new ImageSpan(this, rate);
		SpannableString spanRate = new SpannableString("icon");
		spanRate.setSpan(imgRate, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		rateFilm.setText(spanRate);
		rateFilm.append("评分");
		//
		rateFilm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (rateFlag == false) {
					if (wantShareLayout.getVisibility() == View.VISIBLE) {
						wantShareLayout.setVisibility(View.GONE);
						wantShareFlag = false;
					}
					rateLayout.setVisibility(View.VISIBLE);
					rateFlag = true;

				} else {

					rateLayout.setVisibility(View.GONE);
					rateFlag = false;
				}
			}

		});

		clickShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showShare();
			}

		});
		movieDetailShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showShare();
			}

		});
	}
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 0:
				//do something
			}
		}
		
	};

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("action", "movie_info");

			Long movieId = 385335l;
			params.put("movieId", movieId);

			Date date = new Date();
			long time_stamp = date.getTime();
			params.put("time_stamp", time_stamp + "");

			String enc = GetEnc.getEnc(params, "wiseMovie");

			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(Constant.baseURL + "action="
					+ params.get("action") + "&" + "movieId=" + movieId + "&"
					+ "enc=" + enc + "&" + "time_stamp=" + time_stamp);
			System.out.println("movie_detail-----" + Constant.baseURL
					+ "action=" + params.get("action") + "&" + "movieId="
					+ movieId + "&" + "enc=" + enc + "&" + "time_stamp="
					+ time_stamp);
			HttpResponse httpResponse;
			String result;
			try {
				httpResponse = httpClient.execute(getMethod);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					result = EntityUtils.toString(entity, "utf-8");
					Gson gson = new Gson();
					MovieDetailResult movieDetailResult = gson.fromJson(result,
							MovieDetailResult.class);
					MovieDetail movie = movieDetailResult.getMovie();
					
					String name = "";
					String score = "无评分";
					String type="";
					String c=""; //国家
					int length=0;
					String actionTime = "无数据";
					String posterPath;
					if (!(movie.getMovieName().equals(null))) {
						name = movie.getMovieName();
						// film.setFilmName(movieName);
						
					}
					movieName.setText(name);
					//评分
					if (!(movie.getScore().equals(null))) {
						score = movie.getScore();
					} 
					filmRatingText.setText(score);
					filmRatingBar.setRating((float) (Integer.getInteger(score)/2));
					// 类型
					if(!(movie.getMovieType().equals(null))){
						type = movie.getMovieType();
					}
					movieType.setText("类型："+type);
					//地区
					if(!(movie.getCountry().equals(null))){
						c = movie.getCountry();
					}
					country.setText("地区："+c);
					//时长
					if(movie.getMovieLength() != 0){
						length = movie.getMovieLength();
					}
					movieLength.setText("时长："+length);
					//上映日期
					if (!(movie.getPublishTime().equals(null))) {
						actionTime = movie.getPublishTime();
					}
					publishTime.setText("上映日期："+actionTime);
					//海报
					posterPath = movie.getPathVerticalS();
					if (!posterPath.equals(null)) {
						Bitmap filmImage = Util.getBitmap(posterPath);
						movieImg.setImageBitmap(filmImage);
					}

					Message msg = new Message();
					// Bundle data = new Bundle();
					// msg.setData(data);
					msg.what = 0;
					handler.sendMessage(msg);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	};

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
		// 根据点击，得到相关的信息，设置
		oks.setText("这是我要分享的文字");
		String imageURL = "http://www.redocn.com/599750/works/pic-144397.html";
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// 只能是本地图片吗？新浪微博是支持通过url方式设置的，模拟器上没有成功，不知道原因，需要用真机测试
		// oks.setImageUrl("http://www.redocn.com/599750/works/pic-144397.html");
		oks.setImagePath("/sdcard/test.jpg");
		// 启动分享GUI
		oks.show(this);
	}

	public void initView() {
		filmDetailBack = (View) findViewById(R.id.movie_detail_title_back);
		buyTicket = (Button) findViewById(R.id.buy_ticket);
		filmRatingBar = (RatingBar) findViewById(R.id.film_rating);
		filmRatingText = (TextView) findViewById(R.id.film_rating_text);
		filmWant = (TextView) findViewById(R.id.film_want);
		rateFilm = (TextView) findViewById(R.id.rate_film);
		clickShare = (TextView) findViewById(R.id.click_share);
		wantShareLayout = (RelativeLayout) findViewById(R.id.want_share);
		rateLayout = (RelativeLayout) findViewById(R.id.rate);
		movieDetailShare = (ImageView) findViewById(R.id.movie_detail_share);
		movieImg = (ImageView) findViewById(R.id.movie_img);
		movieType = (TextView) findViewById(R.id.movie_type);
		country = (TextView) findViewById(R.id.country);
		movieLength = (TextView) findViewById(R.id.movie_length);
		publishTime = (TextView) findViewById(R.id.publish_time);
		movieName = (TextView)findViewById(R.id.movie_detail_back_name);
	}
}
