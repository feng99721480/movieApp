package com.wiseweb.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wiseweb.movie.R;

public class CityListAdapter extends ArrayAdapter<String> {
	private List<String> listTag = null;

	public CityListAdapter(Context context, List<String> objects,
			List<String> tags) {
		super(context, 0, objects);
		this.listTag = tags;
	}

	@Override
	public boolean isEnabled(int position) {
		if (listTag.contains(getItem(position))) {
			return false;
		}
		return super.isEnabled(position);
	}

	/*
	 * �÷������adapter��˳��һ��һ�е���֯�б�(non-Javadoc) ����position��ʾ�ڼ��У�Ҳ���ǵ�ǰ����adapter��λ�ã�
	 * convertView��ʾ�ڼ��е�View
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		// ��ݱ�ǩ���ͼ��ز�ͬ�Ĳ���ģ��
		if (listTag.contains(getItem(position))) {
			// ����Ǳ�ǩ��
			// if(listTag.get(position).equals("���ų���")){
			// view =
			// LayoutInflater.from(getContext()).inflate(R.layout.city_list_item_hot,
			// null);
			// TextView
			// textView=(TextView)view.findViewById(R.id.city_list_item_text);
			// textView.setText(getItem(position));
			// }

			view = LayoutInflater.from(getContext()).inflate(
					R.layout.city_list_item_tag, null);

			// ��ʾ���
			TextView textView = (TextView) view
					.findViewById(R.id.city_list_item_tag);

			textView.setText(getItem(position));

		} else {
			// �����
			view = LayoutInflater.from(getContext()).inflate(
					R.layout.city_list_item, null);
			// ��ʾ���
			TextView textView = (TextView) view
					.findViewById(R.id.city_list_item_text);
			textView.setText(getItem(position));
		}

		return view;
	}
}
