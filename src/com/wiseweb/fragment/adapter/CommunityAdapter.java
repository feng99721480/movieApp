package com.wiseweb.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiseweb.bean.CommunityInfo;
import com.wiseweb.movie.R;

public class CommunityAdapter extends BaseAdapter {
	private List<CommunityInfo> mListCommunityBean = null;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public CommunityAdapter(List<CommunityInfo> listCommunityBean, Context context){
		mListCommunityBean = listCommunityBean;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListCommunityBean.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListCommunityBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = mInflater.inflate(R.layout.item_recommend_community, null);
		
		ImageView imgCommunity = (ImageView)v.findViewById(R.id.img_community);
		imgCommunity.setImageResource(mListCommunityBean.get(position).getImgId());
		
		TextView filmName = (TextView)v.findViewById(R.id.film_name);
		filmName.setText(mListCommunityBean.get(position).getCommunityName());
		
		TextView filmOutline = (TextView)v.findViewById(R.id.film_outline);
		filmOutline.setText(mListCommunityBean.get(position).getOutline());
		
		TextView newPost = (TextView)v.findViewById(R.id.new_post);
		newPost.setText("今日新帖"+mListCommunityBean.get(position).getNewPostNum());
		return v;
	}

}
