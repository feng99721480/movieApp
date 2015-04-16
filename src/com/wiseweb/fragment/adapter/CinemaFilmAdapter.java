package com.wiseweb.fragment.adapter;

import java.util.List;

import com.wiseweb.activity.BuyTicketSelectCinema;
import com.wiseweb.activity.SelectSeatBuyTicketActivity;
import com.wiseweb.movie.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CinemaFilmAdapter extends BaseAdapter {
	private List<String> start_time = null;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public CinemaFilmAdapter(List<String> start_list,Context context){
		start_time = start_list;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return start_time.size();
	}

	@Override
	public Object getItem(int position) {
		return start_time.get(position);
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
			holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
			holder.startTime.setText(start_time.get(position).toString());//
			
			holder.endTime = (TextView) convertView.findViewById(R.id.end_time);
			holder.endTime.setText("19:20"); // 
			
			holder.lanProperty = (TextView) convertView.findViewById(R.id.lan_property);
			holder.lanProperty.setText("IMAX3D");
			
			holder.hall = (TextView)convertView.findViewById(R.id.hall);
			holder.hall.setText("3号厅");
			
			holder.price = (TextView)convertView.findViewById(R.id.price);
			holder.price.setText("35");
			
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
//			Toast.makeText(mContext, "�������", Toast.LENGTH_SHORT).show();
//			System.out.println("yes  come on baby");
//			Intent intent = new Intent();
//			intent.setClass(mContext, SelectSeatBuyTicketActivity.class);
//			mContext.startActivity(intent);
		}
	}
}
