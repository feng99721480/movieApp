package com.wiseweb.fragment.adapter;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiseweb.activity.BuyTicketSelectCinema;
import com.wiseweb.bean.FilmInfo;
import com.wiseweb.movie.R;

public class FilmAdapter extends BaseAdapter {
	private List<FilmInfo> mListFilmBean = null;
	private Context mContext;
	private LayoutInflater mInflater;

	public FilmAdapter(List<FilmInfo> listMsgBean, Context context) {
		mListFilmBean = listMsgBean;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mListFilmBean.size();
	}

	@Override
	public Object getItem(int position) {
		return mListFilmBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		ImageView imageView; // 图片
		TextView nameFilm; // name
		TextView iMax;
		TextView newFilm;
		TextView outline;
		TextView filmShow;
		TextView filmScore;
		Button buyTicket;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		OnClick listener = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.film_item_layout,
					null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.img_film_item);
			holder.nameFilm = (TextView) convertView
					.findViewById(R.id.name_film_item);
			holder.iMax = (TextView) convertView.findViewById(R.id.iMax);
			holder.newFilm = (TextView) convertView.findViewById(R.id.new_film);
			holder.outline = (TextView) convertView
					.findViewById(R.id.film_outline);
			holder.filmShow = (TextView) convertView
					.findViewById(R.id.film_show);
			holder.filmScore = (TextView) convertView
					.findViewById(R.id.film_score);
			holder.buyTicket = (Button) convertView
					.findViewById(R.id.buy_filmticket);
			listener = new OnClick();
			holder.buyTicket.setOnClickListener(listener);
			convertView.setTag(holder);
			convertView.setTag(holder.buyTicket.getId(), listener);//
		} else {
			holder = (ViewHolder) convertView.getTag();
			listener = (OnClick) convertView.getTag(holder.buyTicket.getId());//
		}

		// 电影图片
		if (!(mListFilmBean.get(position).getImgId() == null)) {
			holder.imageView.setImageBitmap(mListFilmBean.get(position)
					.getImgId());
		} else {
			holder.imageView.setImageResource(R.drawable.default_img);
		}
		// 电影名称
		holder.nameFilm.setText(mListFilmBean.get(position).getFilmName());
		// 电影类型
		holder.iMax.setVisibility(View.VISIBLE);
		if (mListFilmBean.get(position).getiMax().equals("iMax3D")) {
			holder.iMax.setText("iMax3D");
			holder.iMax.setBackgroundResource(R.drawable.film_property_imax);
		} else if (mListFilmBean.get(position).getiMax().equals("iMax2D")) {
			holder.iMax.setText("iMax2D");
			holder.iMax.setBackgroundResource(R.drawable.film_property_imax);
		} else if (mListFilmBean.get(position).getiMax().equals("3D")) {
			holder.iMax.setText("3D");
			holder.iMax.setBackgroundResource(R.drawable.film_property_3d);
		} else {
			holder.iMax.setVisibility(View.GONE);
//			holder.iMax.setText("");
		}
		// 是否新电影
		if (mListFilmBean.get(position).isNewMovie() == true) {
			// holder.newFilm.setImageResource(R.drawable.new_movie);
			holder.newFilm.setVisibility(View.VISIBLE);
			holder.newFilm.setText("新");
			holder.newFilm.setBackgroundResource(R.drawable.film_new);
		} else {
			holder.newFilm.setVisibility(View.GONE);
//			holder.newFilm.setText("");
		}
		// 概述
		holder.outline.setText(mListFilmBean.get(position).getOutline());
		// 多少家播放多少场
		holder.filmShow.setText(mListFilmBean.get(position).getShow());
		// 电影评分
		holder.filmScore.setText(mListFilmBean.get(position).getScore()+"分");
		// listener.setPosition(position);
		return convertView;
	}

	class OnClick implements OnClickListener {
		int position;

		public void setPosition(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(mContext, BuyTicketSelectCinema.class);
			mContext.startActivity(intent);
		}
	}
}
