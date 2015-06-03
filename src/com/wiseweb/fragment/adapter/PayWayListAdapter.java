package com.wiseweb.fragment.adapter;

import java.util.List;

import com.wiseweb.bean.PayWay;
import com.wiseweb.movie.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class PayWayListAdapter extends BaseAdapter {
	private List<PayWay> mList = null;
	private Context mContext;
	private LayoutInflater mInflater;
	private int lastSelectedIndex = 0;

	// private int lastSelectedIndex;

	public PayWayListAdapter(List<PayWay> payWayList, Context context) {
		mList = payWayList;
		System.out.println("mList--------" + mList);
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		// OnClick listener = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.pay_way_list_item,
					null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.pay_way_img);
			holder.image.setImageResource(mList.get(position).getImageId());

			holder.name = (TextView) convertView
					.findViewById(R.id.pay_way_name);
			holder.name.setText(mList.get(position).getName());

			holder.desc = (TextView) convertView
					.findViewById(R.id.pay_way_desc);
			holder.desc.setText(mList.get(position).getDesc());
			holder.radio = (RadioButton) convertView
					.findViewById(R.id.pay_way_radio);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final int p = position;
		holder.radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					lastSelectedIndex = p;
				}
				// else {
				// lastSelectedIndex = -1;
				// }
				notifyDataSetChanged();
			}
		});

		if (position == lastSelectedIndex) {
			holder.radio.setChecked(true);
		} else {
			holder.radio.setChecked(false);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView image;
		TextView name;
		TextView desc;
		RadioButton radio;
	}

}
