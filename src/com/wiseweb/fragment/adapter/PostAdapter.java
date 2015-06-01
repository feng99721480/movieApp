package com.wiseweb.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wiseweb.bean.PostInfo;
import com.wiseweb.movie.R;

public class PostAdapter extends BaseAdapter {
	private List<PostInfo> mListPostBean = null;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public PostAdapter(List<PostInfo> listPostBean, Context context){
		mListPostBean = listPostBean;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListPostBean.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListPostBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = mInflater.inflate(R.layout.item_hot_post_community, null);
		
		TextView postTitle = (TextView)v.findViewById(R.id.post_title);
		postTitle.setText(mListPostBean.get(position).getPostTitle());
		
		TextView postFilmName = (TextView)v.findViewById(R.id.community_name);
		postFilmName.setText(mListPostBean.get(position).getPostFilmName());
		
		TextView postLatest = (TextView)v.findViewById(R.id.latest_reply_time);
		postLatest.setText(mListPostBean.get(position).getPostLatest());

		TextView replyNum = (TextView)v.findViewById(R.id.post_reply_num);
		replyNum.setText(""+mListPostBean.get(position).getPostReplyNum());
		return v;
	}

}
