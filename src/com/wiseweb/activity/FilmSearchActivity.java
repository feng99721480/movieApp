package com.wiseweb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.adapter.FilmSearchGridAdapter;
import com.wiseweb.movie.R;

public class FilmSearchActivity extends Activity {
	private GridView filmType;
	private GridView filmArea;
	private EditText etSearch;
	private View filmSearchBack;
	private EditText filmSearchEdit;
	private ImageView filmSearchDelText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.film_search);
		filmType = (GridView) findViewById(R.id.film_type_grid);
		filmArea = (GridView) findViewById(R.id.film_area_grid);
		FilmSearchGridAdapter filmTypeAdapter = new FilmSearchGridAdapter(
				Constant.FILM_TYPE, this);
		filmType.setAdapter(filmTypeAdapter);
		FilmSearchGridAdapter filmAreaAdapter = new FilmSearchGridAdapter(
				Constant.FILM_AREA, this);
		filmArea.setAdapter(filmAreaAdapter);
		filmSearchBack = (View) findViewById(R.id.film_search_title);
		filmSearchBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}

		});
		filmSearchDelText = (ImageView)findViewById(R.id.film_ivDeleteText);
		filmSearchEdit = (EditText) findViewById(R.id.film_etSearch);
		filmSearchEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()==0){
					filmSearchDelText.setVisibility(View.GONE);
				}else{
					filmSearchDelText.setVisibility(View.VISIBLE);
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
		filmSearchDelText.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				filmSearchEdit.setText("");
			}
			
		});
		filmType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}

		});
	}

}
