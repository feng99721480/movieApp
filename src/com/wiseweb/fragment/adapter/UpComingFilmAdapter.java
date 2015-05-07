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
		OnClick listener = null;
		if (convertView == null) {
			// View v = mInflater.inflate(R.layout.film_item_layout, null);
			convertView = View.inflate(mContext, R.layout.film_item_upcoming,
					null);
			holder = new UpComViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.img_film_item);

			holder.imageView.setImageBitmap(mListFilmBean.get(position)
					.getImgId());
			//holder.imageView.setBackgroundResource(R.drawable.runman);
			holder.nameFilm = (TextView) convertView
					.findViewById(R.id.name_film_item);
			holder.nameFilm.setText(mListFilmBean.get(position).getFilmName());

			holder.iMax = (TextView) convertView.findViewById(R.id.iMax);
			if (mListFilmBean.get(position).getiMax().equals("iMax3D")) {
				// holder.iMax.setImageResource(R.drawable.ic_imax3d);
				holder.iMax.setVisibility(View.VISIBLE);
				holder.iMax.setText("iMax3D");
				holder.iMax
						.setBackgroundResource(R.drawable.film_property_imax);
			} else if (mListFilmBean.get(position).getiMax().equals("iMax2D")) {
				// holder.iMax.setImageResource(R.drawable.ic_imax2d);
				holder.iMax.setVisibility(View.VISIBLE);
				holder.iMax.setText("iMax2D");
				holder.iMax
						.setBackgroundResource(R.drawable.film_property_imax);
			} else if (mListFilmBean.get(position).getiMax().equals("3D")) {
				// holder.iMax.setImageResource(R.drawable.ic_3d);
				holder.iMax.setVisibility(View.VISIBLE);
				holder.iMax.setText("3D");
				holder.iMax.setBackgroundResource(R.drawable.film_property_3d);
			} else {
				// holder.iMax.setImageResource(R.drawable.ic_blank);
				holder.iMax.setText("");
			}

			holder.newFilm = (TextView) convertView.findViewById(R.id.new_film);
			if (mListFilmBean.get(position).isNewMovie() == true) {
				// holder.newFilm.setImageResource(R.drawable.new_movie);
				holder.newFilm.setText("新");
				holder.newFilm.setBackgroundResource(R.drawable.film_new);
			} else {
				holder.iMax.setText("");

			}

			holder.outline = (TextView) convertView
					.findViewById(R.id.film_outline);
			holder.outline.setText(mListFilmBean.get(position).getOutline());

			holder.actionTime = (TextView) convertView
					.findViewById(R.id.action_time);
			holder.actionTime.setText(mListFilmBean.get(position)
					.getActionTime());

			holder.peopleNum = (TextView) convertView
					.findViewById(R.id.people_number);
			holder.peopleNum.setText("1111想看");
			convertView.setTag(holder);

		} else {
			holder = (UpComViewHolder) convertView.getTag();
		}
		FilmInfo value = mListFilmBean.get(position);// ���ü�������ֵ
		// holder.no.setText(value);
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
