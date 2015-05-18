package com.wiseweb.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wiseweb.json.MoviePlan;
import com.wiseweb.movie.R;

public class CinemaFilmAdapter extends BaseAdapter {
//	private List<String> start_time = null;
	private List<MoviePlan> moviePlans = null;
	private Context mContext;
	private LayoutInflater mInflater;
	
//	public CinemaFilmAdapter(List<String> start_list,Context context){
//		start_time = start_list;
//		mContext = context;
//		mInflater = LayoutInflater.from(mContext);
//	}
	
	public CinemaFilmAdapter(List<MoviePlan> moviePlans,Context context){
		this.moviePlans = moviePlans;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return moviePlans.size();
	}

	@Override
	public Object getItem(int position) {
		return moviePlans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CinemaFilmHolder holder = null;
		OnClick Listener =null;
		if(convertView == null){
			convertView = View.inflate(mContext,R.layout.cinema_select_film_item,null);
			holder = new CinemaFilmHolder();
			//场次开始时间
			holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
			holder.startTime.setText(moviePlans.get(position).getFeatureTime().toString());//
			//结束时间 暂时先不写了
			holder.endTime = (TextView) convertView.findViewById(R.id.end_time);
//			holder.endTime.setText("19:20"); // 
			//场次屏幕类型
			holder.lanProperty = (TextView) convertView.findViewById(R.id.lan_property);
			holder.lanProperty.setText(moviePlans.get(position).getScreenType().toString());
			//厅名
			holder.hall = (TextView)convertView.findViewById(R.id.hall);
			holder.hall.setText(moviePlans.get(position).getHallName().toString());
			//场次售价
			holder.price = (TextView)convertView.findViewById(R.id.price);
			holder.price.setText(moviePlans.get(position).getPrice().toString());
			//按钮
			holder.selectSeatBuyTicket = (TextView)convertView.findViewById(R.id.select_seat_buy_ticket);
			holder.selectSeatBuyTicket.setText("选座购票");
		}
		return convertView;
	}
	
	class CinemaFilmHolder{
		TextView startTime;
		TextView endTime;
		TextView lanProperty; //
		TextView hall;   //
		TextView price;
		TextView selectSeatBuyTicket;
	}
	class OnClick implements OnClickListener {
		int position;

		public void setPosition(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
//			Toast.makeText(mContext, "yesyes", Toast.LENGTH_SHORT).show();
//			System.out.println("yes  come on baby");
//			Intent intent = new Intent();
//			intent.setClass(mContext, SelectSeatBuyTicketActivity.class);
//			mContext.startActivity(intent);
		}
	}
}
