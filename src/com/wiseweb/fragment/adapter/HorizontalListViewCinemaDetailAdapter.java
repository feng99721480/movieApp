package com.wiseweb.fragment.adapter;

import java.util.List;

import com.wiseweb.movie.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class HorizontalListViewCinemaDetailAdapter extends BaseAdapter {
	private List<Bitmap> bps;
	private Context mContext;
	private LayoutInflater mInflater;
	private Bitmap iconBitmap;
	private int selectIndex = -1;
	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param bps
	 *            Bitmap元素
	 */
	public HorizontalListViewCinemaDetailAdapter(Context context, List<Bitmap> bps) {
		this.mContext = context;
		this.bps = bps;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		return bps.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater
					.inflate(R.layout.horizontal_list_item, null);
			holder.mImage = (ImageView) convertView
					.findViewById(R.id.img_list_item);
			// holder.mTitle=(TextView)convertView.findViewById(R.id.text_list_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == selectIndex) {
			convertView.setSelected(true);
		} else {
			convertView.setSelected(false);
		}

		// holder.mTitle.setText(mTitles[position]);
		// 设置图片
		// iconBitmap = getPropThumnail(mIconIDs[position]);
		holder.mImage.setImageBitmap(bps.get(position));

		return convertView;
	}
	private static class ViewHolder {
		// private TextView mTitle ;
		private ImageView mImage;
	}

}
