package com.wiseweb.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiseweb.movie.R;

public class FilmDetailsActivity extends Activity {
	private View filmDetailBack;
	private Button buyTicket;
	private RatingBar filmRatingBar;
	private TextView filmRatingText;
	private TextView filmWant;
	private TextView rateFilm;
	private TextView clickShare;
	private RelativeLayout wantShare;

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
		buyTicket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FilmDetailsActivity.this,
						BuyTicketSelectCinema.class);
				startActivity(intent);
			}

		});
		//
		filmRatingBar.setRating((float) 7.5);
		//
		filmRatingText.setText(filmRatingBar.getRating() + "");

		Bitmap want = BitmapFactory.decodeResource(getResources(),
				R.drawable.widget_dislike);
		ImageSpan imgSpan = new ImageSpan(this, want);
		SpannableString spanString = new SpannableString("icon");
		spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		filmWant.setText(spanString);
		filmWant.append("想看");
		// filmWant
		//filmWant.setOnClickListener(null);
		filmWant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (wantShare.getVisibility() == View.GONE) {
					wantShare.setVisibility(View.VISIBLE);
					Bitmap want = BitmapFactory.decodeResource(getResources(),
							R.drawable.widget_like);
					ImageSpan imgSpan = new ImageSpan(want);
					SpannableString spanString = new SpannableString("icon");
					spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					filmWant.setText(spanString);
					filmWant.append("想看");
				}else{
					wantShare.setVisibility(View.INVISIBLE);
					Bitmap want = BitmapFactory.decodeResource(getResources(),
							R.drawable.widget_dislike);
					ImageSpan imgSpan = new ImageSpan(want);
					SpannableString spanString = new SpannableString("icon");
					spanString.setSpan(imgSpan, 0, 4,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					filmWant.setText(spanString);
					filmWant.append("想看");
					wantShare.setVisibility(View.VISIBLE);
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
		rateFilm.setOnClickListener(null);

		clickShare.setOnClickListener(null);
	}

	public void initView() {
		filmDetailBack = (View) findViewById(R.id.movie_detail_title_back);
		buyTicket = (Button) findViewById(R.id.buy_ticket);
		filmRatingBar = (RatingBar) findViewById(R.id.film_rating);
		filmRatingText = (TextView) findViewById(R.id.film_rating_text);
		filmWant = (TextView) findViewById(R.id.film_want);
		rateFilm = (TextView) findViewById(R.id.rate_film);
		clickShare = (TextView) findViewById(R.id.click_share);
		wantShare = (RelativeLayout) findViewById(R.id.want_share);
	}
}
