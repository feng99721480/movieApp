package com.wiseweb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.wiseweb.movie.R;

public class CinemaSearchActivity extends Activity {
	private View cinemaSearchBack;
	private EditText cinemaSearchEdit;
	private ImageView cinemaSearchDelText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cinema_search);
		cinemaSearchBack = (View)findViewById(R.id.cinema_search_title);
		cinemaSearchBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				finish();
			}
			
		});
		cinemaSearchDelText = (ImageView)findViewById(R.id.cinema_ivDeleteText);
		cinemaSearchEdit = (EditText)findViewById(R.id.cinema_etSearch);
		cinemaSearchEdit.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()==0){
					cinemaSearchDelText.setVisibility(View.GONE);
				}else{
					cinemaSearchDelText.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
		});
		cinemaSearchDelText.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				cinemaSearchEdit.setText("");
			}
			
		});
		
	}
	
}
