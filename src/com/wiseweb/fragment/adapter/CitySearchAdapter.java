package com.wiseweb.fragment.adapter;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.wiseweb.movie.R;
import com.wiseweb.util.WordUtil;

public class CitySearchAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> datasource; // 源数据
	private ArrayList<String> showdata;// 显示的数据（符合搜索条件的数据）

	public CitySearchAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		if (showdata == null) {
			return 0;
		}
		return showdata.size();
	}

	@Override
	public Object getItem(int arg0) {
		if (showdata != null) {
			return showdata.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ItemViewCache cache = null;
		if (view == null) {
			cache = new ItemViewCache();
			view = LayoutInflater.from(context)
					.inflate(R.layout.city_list_item, null);
			cache.city = (TextView) view.findViewById(R.id.city_list_item_text);
			view.setTag(cache);
		} else {
			cache = (ItemViewCache) view.getTag();
		}
		cache.city.setText(showdata.get(position));
		return view;
	}

	// 缓存类，重用以达到优化列表的效果
	public class ItemViewCache {
		public TextView city;
	}

	public void setDataSource(ArrayList<String> datasource) {
		this.datasource = datasource;
		showdata = (ArrayList<String>) datasource.clone();
		this.notifyDataSetInvalidated();
	}

	// 对源数据进行搜索
	public void searchData(String word) {
		if (word == null || word == "") {
			this.showdata = (ArrayList<String>) this.datasource.clone();
		} else {
			dataFilter(word);
		}
		this.notifyDataSetInvalidated();
		this.notifyDataSetChanged();
	}

	private void dataFilter(String word) {
		showdata.clear();
		int listsize = datasource.size();
		for (int i = 0; i < listsize; i++) {
			String city = datasource.get(i);
			if (city.contains(word)) {
				showdata.add(city);
			} else if (WordUtil.getSpells(city).contains(
					word.toLowerCase())) {
				showdata.add(city);
			}
		}
	}

}
