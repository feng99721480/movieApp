package com.wiseweb.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wiseweb.movie.R;

public class BuyTicketTitlePanel extends RelativeLayout {
	private Context mContext;
	private ImageView mHeadBack;
	private TextView mFilmName;
	private ImageView mCinemaSearch;
	
	public BuyTicketTitlePanel(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        // TODO Auto-generated constructor stub  
        mContext = context;  
        View parent = LayoutInflater.from(mContext).inflate(R.layout.buy_ticket_title_panel, this, true);  
        mFilmName = (TextView) parent.findViewById(R.id.film_name);  
    } 

}
