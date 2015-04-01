package com.wiseweb.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wiseweb.constant.Constant;
import com.wiseweb.movie.R;

public class FilmSearchGridAdapter extends BaseAdapter {
	private String[] listBean;
	private Context mContext;
	private LayoutInflater mLayoutInflater;

	public FilmSearchGridAdapter(String[] list, Context context) {
		listBean = list;
		mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return listBean.size();
		return listBean.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listBean[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = mLayoutInflater.inflate(R.layout.film_search_grid_item, null);
		TextView tv = (TextView) v.findViewById(R.id.film_type_text);
		tv.setText(listBean[position].toString());
		return v;
	}

}
