package com.wiseweb.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wiseweb.bean.CinemaInfo;
import com.wiseweb.fragment.adapter.BuyTicketSelectCinemaListAdapter;
import com.wiseweb.movie.R;


public class BuyTicketSelectCinemaFragment extends Fragment {
	private ListView buyTicketSelectCinemaList;
	private BuyTicketSelectCinemaListAdapter listAdapter;
	private List<CinemaInfo> cinemaInfo = new ArrayList<CinemaInfo>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View contextView = inflater.inflate(R.layout.buy_ticket_select_cinema_fragment_item, null);
		/*TextView mTextView = (TextView) contextView.findViewById(R.id.textview);
		
		//
		Bundle mBundle = getArguments();
		String title = mBundle.getString("arg");
		
		mTextView.setText(title);*/
		initUI();
		listAdapter = new BuyTicketSelectCinemaListAdapter(cinemaInfo,getActivity());
		buyTicketSelectCinemaList.setAdapter(listAdapter);
		return contextView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cinemaInfo.add(new CinemaInfo("UME国际影城", true, true, false, false,
				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));

		cinemaInfo.add(new CinemaInfo("UME国际影城", true, false, false, false,
				19.9, "海淀区双榆树科学院南路44号", "1.5KM"));

		cinemaInfo.add(new CinemaInfo("UME国际影城", false, true, false, false, 19.9,
				"海淀区双榆树科学院南路44号", "1.5KM"));
	}

	public void initUI(){
		buyTicketSelectCinemaList =(ListView) getActivity().findViewById(R.id.buy_ticket_select_cinema_list);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
	}
}