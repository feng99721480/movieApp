package com.wiseweb.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
		filmSearchBack = (View) findViewById(R.id.film_search_title_back);
		filmSearchBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}

		});
		filmSearchDelText = (ImageView) findViewById(R.id.film_ivDeleteText);
		filmSearchEdit = (EditText) findViewById(R.id.film_etSearch);
		filmSearchEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					filmSearchDelText.setVisibility(View.GONE);
				} else {
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
		filmSearchDelText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				filmSearchEdit.setText("");
			}

		});
		// 将gridview本身点击时的背景黄色取消掉
		filmType.setSelector(new ColorDrawable(Color.TRANSPARENT));
		filmType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 带着参数过去下一个页面
				String s = parent.getItemAtPosition(position).toString();
				Intent intent = new Intent();
				switch (position) {
				case 0:
					intent.putExtra("filmType", "剧情");
					break;
				case 1:
					intent.putExtra("filmType", "喜剧");
					break;
				case 2:
					intent.putExtra("filmType", "爱情");
					break;
				case 3:
					intent.putExtra("filmType", "动画");
					break;
				case 4:
					intent.putExtra("filmType", "动作");
					break;
				case 5:
					intent.putExtra("filmType", "恐怖");
					break;
				case 6:
					intent.putExtra("filmType", "惊悚");
					break;
				case 7:
					intent.putExtra("filmType", "悬疑");
					break;
				case 8:
					intent.putExtra("filmType", "冒险");
					break;
				case 9:
					intent.putExtra("filmType", "科幻");
					break;
				case 10:
					intent.putExtra("filmType", "犯罪");
					break;
				case 11:
					intent.putExtra("filmType", "战争");
					break;
				case 12:
					intent.putExtra("filmType", "纪录片");
					break;
				case 13:
					intent.putExtra("filmType", "其它");
					break;
				}
				intent.setClass(FilmSearchActivity.this,
						FilmSearchResultActivity.class);
				startActivity(intent);
			}

		});
		filmArea.setSelector(new ColorDrawable(Color.TRANSPARENT));
		filmArea.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				switch (position) {
				case 0:
					intent.putExtra("filmType", "美国");
					break;
				case 1:
					intent.putExtra("filmType", "大陆");
					break;
				case 2:
					intent.putExtra("filmType", "港台");
					break;
				case 3:
					intent.putExtra("filmType", "法国");
					break;
				case 4:
					intent.putExtra("filmType", "英国");
					break;
				case 5:
					intent.putExtra("filmType", "德国");
					break;
				case 6:
					intent.putExtra("filmType", "日本");
					break;
				case 7:
					intent.putExtra("filmType", "韩国");
					break;
				case 8:
					intent.putExtra("filmType", "印度");
					break;
				case 9:
					intent.putExtra("filmType", "泰国");
					break;
				case 10:
					intent.putExtra("filmType", "其它");
					break;
				}
				intent.setClass(FilmSearchActivity.this,
						FilmSearchResultActivity.class);
				startActivity(intent);
			}

		});
		
	}
}
