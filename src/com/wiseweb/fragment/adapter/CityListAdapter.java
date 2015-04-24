package com.wiseweb.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wiseweb.movie.R;

public class CityListAdapter extends ArrayAdapter<String> {
	private List<String> listTag = null;
	private Context mContext;

	public CityListAdapter(Context context, List<String> objects,
			List<String> tags) {
		super(context, 0, objects);
		this.mContext = context;
		this.listTag = tags;
	}

	// 如果是tag中的值的话是不可以点击的
	@Override
	public boolean isEnabled(int position) {
		if (listTag.contains(getItem(position))) {
			return false;
		}
		if (position == 1) {
			return false;
		}
		return super.isEnabled(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		//
		if (listTag.contains(getItem(position))) {

			view = LayoutInflater.from(getContext()).inflate(
					R.layout.city_list_item_tag, null);

			//
			TextView textView = (TextView) view
					.findViewById(R.id.city_list_item_tag);

			textView.setText(getItem(position));

		} else {
			//
			view = LayoutInflater.from(getContext()).inflate(
					R.layout.city_list_item, null);
			//
			TextView textView = (TextView) view
					.findViewById(R.id.city_list_item_text);
			textView.setText(getItem(position));
		}
		if (position == 1) {
			SharedPreferences locationPreferences = mContext
					.getSharedPreferences("locationCity", Context.MODE_PRIVATE);
			String locationCity = locationPreferences.getString("locationCity",
					null);
			TextView textView = (TextView) view
					.findViewById(R.id.city_list_item_text);

			if (locationCity == null) {
				textView.setText("定位中...");
			} else {
				textView.setText(locationCity);
			}
		}
		return view;
	}
}
