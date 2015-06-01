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
import android.widget.RatingBar;
import android.widget.TextView;

public class MyFilmScoreAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<FilmScore> filmScoreList;

	public MyFilmScoreAdapter(int[] images, String[] names, float[] ratings,
			Context context) {
		filmScoreList = new ArrayList<FilmScore>();
		inflater = LayoutInflater.from(context);
		for (int i = 0; i < images.length; i++) {
			FilmScore filmScore = new FilmScore(images[i], names[i], ratings[i]);
			filmScoreList.add(filmScore);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (filmScoreList != null)
			return filmScoreList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return filmScoreList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.my_film_score_item, null);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView)convertView.findViewById(R.id.film_image);
			viewHolder.name = (TextView)convertView.findViewById(R.id.film_name);
			viewHolder.rating = (RatingBar)convertView.findViewById(R.id.my_rating);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.image.setBackgroundResource(filmScoreList.get(position).image);
		viewHolder.name.setText(filmScoreList.get(position).name);
		viewHolder.rating.setRating(filmScoreList.get(position).score/2.0f);
		return convertView;
	}

	class ViewHolder {
		public ImageView image;
		public TextView name;
		public RatingBar rating;
	}

	class FilmScore {
		private int image;
		private String name;
		private float score;

		public FilmScore() {
			super();
		}

		public FilmScore(int image, String name, float score) {
			super();
			this.image = image;
			this.name = name;
			this.score = score;
		}

		public int getImage() {
			return image;
		}

		public void setImage(int image) {
			this.image = image;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public float getScore() {
			return score;
		}

		public void setScore(float score) {
			this.score = score;
		}

	}
}
