package com.wiseweb.fragment.adapter;

import java.util.ArrayList;
import java.util.List;

import com.wiseweb.movie.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FilmSearchResultListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Film> filmList;

	public FilmSearchResultListAdapter(int[] images, String[] filmNames,
			String[] filmTypes, String[] actionTimes, Context context) {
		filmList = new ArrayList<Film>();
		inflater = LayoutInflater.from(context);
		for (int i = 0; i < images.length; i++) {
			Film film = new Film(images[i], filmNames[i], filmTypes[i],
					actionTimes[i]);
			filmList.add(film);
		}
	}

	@Override
	public int getCount() {
		if (filmList != null) {
			return filmList.size();
		} else {
			return 0;
		}

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return filmList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.film_search_result_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.filmImage = (ImageView)convertView.findViewById(R.id.film_image);
			viewHolder.filmName = (TextView)convertView.findViewById(R.id.film_name);
			viewHolder.filmType = (TextView)convertView.findViewById(R.id.film_type);
			viewHolder.actionTime = (TextView)convertView.findViewById(R.id.action_time);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.filmImage.setImageResource(filmList.get(position).image);
		viewHolder.filmName.setText(filmList.get(position).filmName);
		viewHolder.filmType.setText(filmList.get(position).filmType);
		viewHolder.actionTime.setText(filmList.get(position).actionTime);
		return convertView;
	}
	class ViewHolder{
		public ImageView filmImage;
		public TextView filmName;
		public TextView filmType;
		public TextView actionTime;
	}
	class Film {
		private int image;
		private String filmName;
		private String filmType;
		private String actionTime;

		public Film() {
			super();
		}

		public Film(int image, String name, String type, String actionTime) {
			super();
			this.image = image;
			this.filmName = name;
			this.filmType = type;
			this.actionTime = actionTime;
		}

		public int getImage() {
			return image;
		}

		public void setImage(int image) {
			this.image = image;
		}

		public String getFilmName() {
			return filmName;
		}

		public void setFilmName(String filmName) {
			this.filmName = filmName;
		}

		public String getFilmType() {
			return filmType;
		}

		public void setFilmType(String filmType) {
			this.filmType = filmType;
		}

		public String getActionTime() {
			return actionTime;
		}

		public void setActionTime(String actionTime) {
			this.actionTime = actionTime;
		}

	}

}
