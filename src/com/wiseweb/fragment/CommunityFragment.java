package com.wiseweb.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.wiseweb.activity.MainActivity;
import com.wiseweb.bean.CommunityInfo;
import com.wiseweb.bean.PostInfo;
import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.CommunityAdapter;
import com.wiseweb.fragment.adapter.PostAdapter;
import com.wiseweb.movie.R;

@SuppressLint("ShowToast")
public class CommunityFragment extends BaseFragment {
	private MainActivity mMainActivity;
	private ListView postList; // 
	private ListView recommendList; // 
	private CommunityAdapter communityAdapter;
	private PostAdapter postAdapter;
	private ImageView ivDeleteText;
	private EditText etSearch;
	// private List<CinemaInfo> cinemaInfo = new ArrayList<CinemaInfo>();
	private List<CommunityInfo> communityInfo = new ArrayList<CommunityInfo>();
	private List<PostInfo> postInfo = new ArrayList<PostInfo>();
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View communityLayout = inflater.inflate(R.layout.community_layout,
				container, false);
		mMainActivity = (MainActivity) getActivity();
		mFragmentManager = getActivity().getFragmentManager();
		recommendList = (ListView) communityLayout
				.findViewById(R.id.listview_recommend_community);
		postList = (ListView) communityLayout
				.findViewById(R.id.listview_hot_post_community);
		communityAdapter = new CommunityAdapter(communityInfo, mMainActivity);
		postAdapter = new PostAdapter(postInfo, mMainActivity);
		recommendList.setAdapter(communityAdapter);
		postList.setAdapter(postAdapter);
		
		recommendList.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(mMainActivity, communityInfo.get(position)
						.toString(), Toast.LENGTH_SHORT);
			}

		});
		postList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(mMainActivity,
						postInfo.get(position).toString(), Toast.LENGTH_SHORT);
			}

		});
		ivDeleteText = (ImageView) communityLayout
				.findViewById(R.id.community_ivDeleteText);
		etSearch = (EditText) communityLayout.findViewById(R.id.community_etSearch);

		// 
		ivDeleteText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				etSearch.setText("");
			}

		});
		// 
		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() == 0) {
					ivDeleteText.setVisibility(View.GONE);
				} else {
					ivDeleteText.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

		});
		return communityLayout;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		MainActivity.currFragTag = Constant.FRAGMENT_FLAG_COMMUNITY;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		communityInfo.add(new CommunityInfo("奔跑吧，兄弟", "看他就是为了笑",
				R.drawable.runman, 127));
		communityInfo.add(new CommunityInfo("奔跑吧，兄弟", "看他就是为了笑",
				R.drawable.runman, 127));
		communityInfo.add(new CommunityInfo("奔跑吧，兄弟", "看他就是为了笑",
				R.drawable.runman, 127));
		communityInfo.add(new CommunityInfo("奔跑吧，兄弟", "看他就是为了笑",
				R.drawable.runman, 127));
		communityInfo.add(new CommunityInfo("奔跑吧，兄弟", "看他就是为了笑",
				R.drawable.runman, 127));
		communityInfo.add(new CommunityInfo("奔跑吧，兄弟", "看他就是为了笑",
				R.drawable.runman, 127));
		postInfo.add(new PostInfo("奔跑吧，兄弟", "真的就那么好吗", "也许就那么好吧",
				"哈哈", 225));

	}

}
