package com.wiseweb.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiseweb.bean.CinemaInfo;
import com.wiseweb.movie.R;

public class CinemaAdapter extends BaseAdapter{
	private List<CinemaInfo> mListCinemaBean = null;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public CinemaAdapter(List<CinemaInfo> listCinemaBean, Context context){
		mListCinemaBean = listCinemaBean;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListCinemaBean.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListCinemaBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = mInflater.inflate(R.layout.cinema_item_layout, null);
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
		lowestPrice.setText("虎啸价："+mListCinemaBean.get(position).getLowestPrice()+"元");
		//ӰԺ��ַ
		TextView cinemaAddress = (TextView)v.findViewById(R.id.cinema_address);
		cinemaAddress.setText(mListCinemaBean.get(position).getCinemaAddress());
		//����
		TextView cinemaDistance = (TextView)v.findViewById(R.id.cinema_distance);
		cinemaDistance.setText(mListCinemaBean.get(position).getDistance());
		return v;
	}
	
}
