package com.wiseweb.fragment.adapter;

import java.util.List;

import com.wiseweb.bean.CinemaTicketInfo;
import com.wiseweb.fragment.TicketWaitPaymentFragment;
import com.wiseweb.movie.R;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CinemaTicketAdapter extends BaseAdapter {
	private List<CinemaTicketInfo> mListCinemaTicketBean = null;
	private Context mContext;
	private LayoutInflater mInflater;
	private boolean tag = false;

	public CinemaTicketAdapter(List<CinemaTicketInfo> listCinemaTicketBean,
			Context context) {
		mListCinemaTicketBean = listCinemaTicketBean;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mListCinemaTicketBean.size();
	}

	@Override
	public Object getItem(int position) {
		return mListCinemaTicketBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View v = mInflater.inflate(R.layout.fragment_mine_cinema_ticket_item,
				null);

		// 设置删除图标
		ImageView ticketDel = (ImageView) v
				.findViewById(R.id.fragment_item_delete);
		ticketDel.setImageResource(mListCinemaTicketBean.get(position)
				.getImageDel());

		// 设置图标
		ImageView ticketIcon = (ImageView) v
				.findViewById(R.id.fragment_item_icon);
		ticketIcon.setImageResource(mListCinemaTicketBean.get(position)
				.getImageid());

		// 设置电影票名称
		TextView ticketName = (TextView) v
				.findViewById(R.id.fragment_item_ticket_name);
		ticketName.setText(mListCinemaTicketBean.get(position)
				.getCinemaTicket());

		// 设置票数
		TextView ticketCount = (TextView) v
				.findViewById(R.id.fragment_item_ticket_count);
		ticketCount
				.setText("数量："
						+ mListCinemaTicketBean.get(position).getTicketCount()
						+ "张团购券");

		// 设置金额
		TextView ticketTotlePrice = (TextView) v
				.findViewById(R.id.fragment_item_ticket_totle_price);
		ticketTotlePrice.setText("总价："
				+ mListCinemaTicketBean.get(position).getTicketPrice() + "元");

		// 设置删除图片显示和隐藏
		if (tag) {
			ticketDel.setVisibility(View.VISIBLE);

		} else {
			ticketDel.setVisibility(View.GONE);
		}

		// 删除图片的点击事件 点击删除listview的一个item
		ticketDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListCinemaTicketBean.remove(position);
				CinemaTicketAdapter.this.notifyDataSetChanged();

			}
		});

		v.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int startX = 0;
				int stopX = 0;
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getX();
					int startY = (int) event.getY();
					break;

				case MotionEvent.ACTION_UP:
					stopX = (int) event.getX();
					int stopY = (int) event.getY();
					break;
					
				case MotionEvent.ACTION_MOVE:
					if(stopX - startX > 100){
						
					}
					
					break;
				}
				return false;
			}
		});

		return v;
	}

	public void setDelImage(boolean change) {
		tag = change;
	}

}
