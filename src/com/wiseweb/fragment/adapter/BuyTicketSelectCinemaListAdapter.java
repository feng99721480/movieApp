package com.wiseweb.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wiseweb.bean.CinemaInfo;
import com.wiseweb.movie.R;

public class BuyTicketSelectCinemaListAdapter extends BaseAdapter {
	private List<CinemaInfo> mListCinemaBean = null;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public BuyTicketSelectCinemaListAdapter(List<CinemaInfo> listCinemaBean, Context context){
		mListCinemaBean = listCinemaBean;
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return mListCinemaBean.size();
	}

	@Override
	public Object getItem(int position) {
		return mListCinemaBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = mInflater.inflate(R.layout.buy_ticket_select_cinema_fragment_item, null);
		//ӰԺ���
		TextView cinemaName = (TextView)v.findViewById(R.id.cinema_name);
		cinemaName.setText(mListCinemaBean.get(position).getCinemaName());
		//�Ƿ����û�ר��
		TextView preferential = (TextView)v.findViewById(R.id.preferential);
		if(mListCinemaBean.get(position).isPreferential()==true){
			preferential.setVisibility(View.VISIBLE);
		}/*else{
			preferential.setImageResource(R.drawable.ic_blank);
		}*/
		
		//�Ƿ���IMAX��
		TextView imax = (TextView)v.findViewById(R.id.imax_hall);
		if(mListCinemaBean.get(position).isImax()==true){
			imax.setVisibility(View.VISIBLE);;
		}/*else{
			imax.setImageResource(R.drawable.ic_blank);
		}*/
		//�Ƿ����ѡ��
		TextView sel_seat = (TextView)v.findViewById(R.id.select_seat);
		if(mListCinemaBean.get(position).isSeat()==true){
			sel_seat.setVisibility(View.VISIBLE);
		}/*else{
			sel_seat.setImageResource(R.drawable.ic_blank);
		}*/
		//�Ƿ�����Ź�
		TextView groupPur = (TextView)v.findViewById(R.id.group_pur);
		if(mListCinemaBean.get(position).isGroupPurchase() ==true){
			groupPur.setVisibility(View.VISIBLE);
		}/*else{
			groupPur.setImageResource(R.drawable.ic_blank);
		}*/
		//è����ͼ�
		TextView lowestPrice = (TextView)v.findViewById(R.id.lowest_price);
		lowestPrice.setText("猫眼价："+mListCinemaBean.get(position).getLowestPrice()+"元");
		//���ڳ���
		TextView cinemaAddress = (TextView)v.findViewById(R.id.cinema_address);
		cinemaAddress.setText("近期场次  09:50|12:10");
		//����
//		TextView cinemaDistance = (TextView)v.findViewById(R.id.cinema_distance);
//		cinemaDistance.setText(mListCinemaBean.get(position).getDistance());
		return v;
	}

}
