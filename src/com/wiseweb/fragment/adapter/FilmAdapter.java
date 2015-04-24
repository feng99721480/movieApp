package com.wiseweb.fragment.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
		// TODO Auto-generated method stub
		return mListFilmBean.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListFilmBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
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
			// View v = mInflater.inflate(R.layout.film_item_layout, null);
			convertView = View.inflate(mContext, R.layout.film_item_layout,
					null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.img_film_item);
			if (!(mListFilmBean.get(position).getImgId() == null)) {
				holder.imageView.setImageBitmap(mListFilmBean.get(position)
						.getImgId());
			}else{
				holder.imageView.setBackgroundResource(R.drawable.default_img720);;
			}
			//holder.imageView.setBackgroundResource(R.drawable.runman);
			holder.nameFilm = (TextView) convertView
					.findViewById(R.id.name_film_item);
			holder.nameFilm.setText(mListFilmBean.get(position).getFilmName());

			holder.iMax = (TextView) convertView.findViewById(R.id.iMax);
			if (mListFilmBean.get(position).getiMax().equals("iMax3D")) {
				holder.iMax.setText("iMax3D");
				holder.iMax
						.setBackgroundResource(R.drawable.film_property_imax);
			} else if (mListFilmBean.get(position).getiMax().equals("iMax2D")) {
				holder.iMax.setText("iMax2D");
				holder.iMax
						.setBackgroundResource(R.drawable.film_property_imax);
			} else if (mListFilmBean.get(position).getiMax().equals("3D")) {
				holder.iMax.setText("3D");
				holder.iMax.setBackgroundResource(R.drawable.film_property_3d);
			} else {
				holder.iMax.setText("");
			}

			holder.newFilm = (TextView) convertView.findViewById(R.id.new_film);
			if (mListFilmBean.get(position).isNewMovie() == true) {
				// holder.newFilm.setImageResource(R.drawable.new_movie);
				holder.newFilm.setText("新");
				holder.newFilm.setBackgroundResource(R.drawable.film_new);
			} else {
				holder.newFilm.setText("");

			}

			holder.outline = (TextView) convertView
					.findViewById(R.id.film_outline);
			holder.outline.setText(mListFilmBean.get(position).getOutline());

			holder.filmShow = (TextView) convertView
					.findViewById(R.id.film_show);
			holder.filmShow.setText(mListFilmBean.get(position).getShow());

			holder.filmScore = (TextView) convertView
					.findViewById(R.id.film_score);
			holder.filmScore.setText(mListFilmBean.get(position).getScore());

			holder.buyTicket = (Button) convertView
					.findViewById(R.id.buy_filmticket);
			// holder.buyTicket.setText("");
			listener = new OnClick();//
			holder.buyTicket.setOnClickListener(listener);
			convertView.setTag(holder);
			convertView.setTag(holder.buyTicket.getId(), listener);// �Լ�����󱣴�
		} else {
			holder = (ViewHolder) convertView.getTag();
			listener = (OnClick) convertView.getTag(holder.buyTicket.getId());// ���»�ü������
		}
		FilmInfo value = mListFilmBean.get(position);// ���ü�������ֵ
		// holder.no.setText(value);
		listener.setPosition(position);
		// Log.d("haha",
		// "position is " + position + " listener is "
		// + listener.toString());
		return convertView;
		// // TODO Auto-generated method stub
		// View v = mInflater.inflate(R.layout.film_item_layout, null);
		// // "�ս���","�ÿ�����",R.drawable.ic_photo_1, "9.0��", "show"
		// ImageView imageView = (ImageView) v.findViewById(R.id.img_film_item);
		// imageView.setImageResource(mListFilmBean.get(position).getImgId());
		//
		// TextView nameFilm = (TextView) v.findViewById(R.id.name_film_item);
		// nameFilm.setText(mListFilmBean.get(position).getFilmName());
		//
		// ImageView iMax = (ImageView) v.findViewById(R.id.iMax);
		// if (mListFilmBean.get(position).getiMax().equals("iMax3D")) {
		// iMax.setImageResource(R.drawable.ic_imax3d);
		// } else if (mListFilmBean.get(position).getiMax().equals("iMax2D")) {
		// iMax.setImageResource(R.drawable.ic_imax2d);
		// } else if (mListFilmBean.get(position).getiMax().equals("3D")) {
		// iMax.setImageResource(R.drawable.ic_3d);
		// } else {
		// iMax.setImageResource(R.drawable.ic_blank);
		// }
		//
		// ImageView newFilm = (ImageView) v.findViewById(R.id.new_film);
		// if (mListFilmBean.get(position).isNewMovie() == true) {
		// newFilm.setImageResource(R.drawable.new_movie);
		// } else {
		// newFilm.setImageResource(R.drawable.ic_blank);
		// }
		//
		// TextView outline = (TextView) v.findViewById(R.id.film_outline);
		// outline.setText(mListFilmBean.get(position).getOutline());
		//
		// TextView filmShow = (TextView) v.findViewById(R.id.film_show);
		// filmShow.setText(mListFilmBean.get(position).getShow());
		//
		// TextView filmScore = (TextView) v.findViewById(R.id.film_score);
		// filmScore.setText(mListFilmBean.get(position).getScore());
		//
		// Button buyTicket = (Button) v.findViewById(R.id.buy_filmticket);
		// // ��Ʊ��ť
		// //button��ý���
		// buyTicket.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// System.out.println("button �������");
		// Intent intent = new Intent();
		// intent.setClass(, BuyTicketSelectCinema.class);
		// startActivity(intent);
		// }
		//
		// });
		// return v;
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
