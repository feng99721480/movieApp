package com.wiseweb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.wiseweb.activity.CinemaSelectFilmActivity;
import com.wiseweb.movie.R;

public class BuyTicketSelectCinemaFragment extends Fragment {
	private ListView buyTicketSelectCinemaList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View contextView = inflater.inflate(
				R.layout.buy_ticket_select_cinema_fragment_item, null);
		/*
		 * TextView mTextView = (TextView)
		 * contextView.findViewById(R.id.textview);
		 * 
		 * // Bundle mBundle = getArguments(); String title =
		 * mBundle.getString("arg");
		 * 
		 * mTextView.setText(title);
		 */
		initUI();
		/*listAdapter = new BuyTicketSelectCinemaListAdapter(cinemaInfo,
				getActivity());
		buyTicketSelectCinemaList.setAdapter(listAdapter);*/
		return contextView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	public void initUI() {
		buyTicketSelectCinemaList = (ListView) getActivity().findViewById(
				R.id.buy_ticket_select_cinema_list);
		buyTicketSelectCinemaList
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						// 这个需要带着信息跳过去
						Intent intent = new Intent();
						intent.setClass(getActivity(),
								CinemaSelectFilmActivity.class);
						startActivity(intent);
					}

				});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}