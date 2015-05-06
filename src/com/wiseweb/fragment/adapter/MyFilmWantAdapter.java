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

public class MyFilmWantAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<FilmWant> filmWantList;
	
	public MyFilmWantAdapter(int[] images,String[] filmNames,String[] actionTimes,Context context){
		filmWantList = new ArrayList<FilmWant>();
		inflater = LayoutInflater.from(context);
		for(int i=0;i<images.length;i++){
			FilmWant filmWant = new FilmWant(images[i],filmNames[i],actionTimes[i]);
			filmWantList.add(filmWant);
		}
	}
	@Override
	public int getCount() {
		if(filmWantList!=null){
			return filmWantList.size();
		}
		else{
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return filmWantList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.my_film_want_item,null);
			viewHolder = new ViewHolder();
			viewHolder.filmImage = (ImageView)convertView.findViewById(R.id.film_image);
			viewHolder.filmName = (TextView)convertView.findViewById(R.id.film_name);
			viewHolder.actionTime = (TextView)convertView.findViewById(R.id.action_time);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.filmImage.setImageResource(filmWantList.get(position).image);
		viewHolder.filmName.setText(filmWantList.get(position).filmName);
		viewHolder.actionTime.setText(filmWantList.get(position).actionTime);
		return convertView;
	}
	class ViewHolder{
		public ImageView filmImage;
		public TextView filmName;
		public TextView actionTime;
	}
	
	class FilmWant{
		private int image;
		private String filmName;
		private String actionTime;
		
		public FilmWant() {
			super();
		}
		public FilmWant(int image, String name, String time) {
			super();
			this.image = image;
			this.filmName = name;
			this.actionTime = time;
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
		public String getActionTime() {
			return actionTime;
		}
		public void setActionTime(String actionTime) {
			this.actionTime = actionTime;
		}
		
	}

}
