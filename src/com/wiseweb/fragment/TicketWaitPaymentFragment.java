package com.wiseweb.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wiseweb.activity.MyCinemaTicketActivity;
import com.wiseweb.bean.CinemaTicketInfo;
import com.wiseweb.fragment.adapter.CinemaTicketAdapter;
import com.wiseweb.movie.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class TicketWaitPaymentFragment extends Fragment {
	private ListView mListView;
	private CinemaTicketAdapter mCinemaTicketAdapter;
	private List<CinemaTicketInfo> listCinemaTicketInfos = new ArrayList<CinemaTicketInfo>();
	private MyCinemaTicketActivity mMyCinemaTicketActivity;
	private View view;
	private boolean flag = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.fragment_ticket_wait_payment,
					null);
			mListView = (ListView) view
					.findViewById(R.id.fragment_ticket_wait_payment_lv);
			mMyCinemaTicketActivity = (MyCinemaTicketActivity) getActivity();
			mCinemaTicketAdapter = new CinemaTicketAdapter(listCinemaTicketInfos,
					mMyCinemaTicketActivity);
			mListView.setAdapter(mCinemaTicketAdapter);
			
			initDate();
		}
		// 缓存的View需要判断是否已经被加过parent， 如果有parent需要从parent删除
				// ，要不然会发生这个view已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if(parent!= null){
			parent.removeView(view);
		}

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	void initDate(){
		listCinemaTicketInfos.add(new CinemaTicketInfo(R.drawable.delete,R.drawable.ic_ticket,
				"星美影城电影票", "3", "108" ,0));
		listCinemaTicketInfos.add(new CinemaTicketInfo(R.drawable.delete,R.drawable.ic_ticket,
				"UME影城电影票", "3", "108" ,0));
		listCinemaTicketInfos.add(new CinemaTicketInfo(R.drawable.delete,R.drawable.ic_ticket,
				"金逸影城电影票", "3", "108" ,0));
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}
	
	public void onDelClick(boolean change){
		flag = change;
		//if(flag){
			mCinemaTicketAdapter.setDelImage(flag);
			mCinemaTicketAdapter.notifyDataSetChanged();
		/*}else{
			Toast.makeText(mMyCinemaTicketActivity, "flag == false", 0).show();
		}*/
	}
}
