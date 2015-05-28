package com.wiseweb.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.wiseweb.activity.BuyTicketSelectCinema;
import com.wiseweb.bean.FilmInfo;
import com.wiseweb.movie.R;

public class UpComingFilmAdapter extends BaseAdapter {
	private List<FilmInfo> mListFilmBean = null;
	private Context mContext;
	private LayoutInflater mInflater;

	public UpComingFilmAdapter(List<FilmInfo> listMsgBean, Context context) {
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

	class UpComViewHolder {
		ImageView imageView;
		TextView nameFilm;
		TextView iMax;
		TextView newFilm;
		TextView outline;
		TextView actionTime;
		TextView peopleNum;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UpComViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.film_item_upcoming,
					null);
			holder = new UpComViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.img_film_item);
			holder.nameFilm = (TextView) convertView
					.findViewById(R.id.name_film_item);
			holder.iMax = (TextView) convertView.findViewById(R.id.iMax);
			holder.newFilm = (TextView) convertView.findViewById(R.id.new_film);
			holder.outline = (TextView) convertView
					.findViewById(R.id.film_outline);
			holder.actionTime = (TextView) convertView
					.findViewById(R.id.action_time);
			holder.peopleNum = (TextView) convertView
					.findViewById(R.id.people_number);
			convertView.setTag(holder);
		} else {
			holder = (UpComViewHolder) convertView.getTag();
		}
		if (mListFilmBean.get(position).getImgId() != null) {
			holder.imageView.setImageBitmap(mListFilmBean.get(position)
					.getImgId());
		} else {
			holder.imageView.setImageResource(R.drawable.default_img);
		}
		
		holder.nameFilm.setText(mListFilmBean.get(position).getFilmName());
		
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
		}

		holder.newFilm.setVisibility(View.VISIBLE);
		if (mListFilmBean.get(position).isNewMovie() == true) {
			// holder.newFilm.setImageResource(R.drawable.new_movie);
			holder.newFilm.setText("新");
			holder.newFilm.setBackgroundResource(R.drawable.film_new);
		} else {
			holder.newFilm.setVisibility(View.GONE);
		}
		holder.outline.setText(mListFilmBean.get(position).getOutline());
		holder.actionTime.setText(mListFilmBean.get(position).getActionTime());
		holder.peopleNum.setText("");
		return convertView;
	}
}
