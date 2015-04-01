package com.wiseweb.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wiseweb.fragment.adapter.CinemaFilmAdapter;
import com.wiseweb.fragment.adapter.HorizontalListViewAdapter;
import com.wiseweb.movie.R;
import com.wiseweb.ui.HorizontalListView;
import com.wiseweb.ui.ListViewForScrollView;

public class CinemaSelectFilmActivity extends Activity {
	private RelativeLayout toCinemaDetail;
	private ListViewForScrollView cinemaFilmList;
	private HorizontalListView hListView;
	private HorizontalListViewAdapter hListViewAdapter;
	//private ImageView previewImg;
	private View olderSelectView = null;
	private CinemaFilmAdapter cinemaFilmAdapter = null; 
	private List<String> startList;
	private ScrollView cinemaFilmScroll;
	private RadioGroup cinemaFilmDate;
	private TextView filmName,filmProperty,filmScore;
	private RelativeLayout cinemaFilmDetail;
	private RelativeLayout cinemaFilmTittleBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cinema_select_film);
		
		/*WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
		final int width = wm.getDefaultDisplay().getWidth();
		final int height = wm.getDefaultDisplay().getHeight();*/
		initUI();
		cinemaFilmScroll.smoothScrollTo(0, 0);
		toCinemaDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CinemaSelectFilmActivity.this,
						CinemaDetailActivity.class);
				startActivity(intent);
			}
		});
		startList = getData();
		cinemaFilmAdapter = new CinemaFilmAdapter(startList,this);
		cinemaFilmList.setAdapter(cinemaFilmAdapter);
		cinemaFilmList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(CinemaSelectFilmActivity.this, "点击了", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(CinemaSelectFilmActivity.this, SelectSeatBuyTicketActivity.class);
				startActivity(intent);
			}
			
		});
		
		cinemaFilmDate.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==R.id.cinema1){
					//System.out.println("cinema1");
				}
				if(checkedId==R.id.cinema2){
					//System.out.println("cinema2");
				}
				if(checkedId==R.id.cinema3){
					//System.out.println("cinema3");
				}
				if(checkedId==R.id.cinema4){
					//System.out.println("cinema4");
				}
				
			}
			
		});
		cinemaFilmDetail.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent= new Intent();
				intent.setClass(CinemaSelectFilmActivity.this, FilmDetailsActivity.class);
				startActivity(intent);
			}
			
		});
		cinemaFilmTittleBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				finish();
			}
			
		});
	}

	public void initUI() {
		toCinemaDetail = (RelativeLayout) findViewById(R.id.to_cinema_detail);
		/////////////////////////////////////
		hListView = (HorizontalListView)findViewById(R.id.horizon_listview);  
        final int[] ids = {R.drawable.runman, R.drawable.runman,  
                R.drawable.runman, R.drawable.runman,  
                R.drawable.runman, R.drawable.runman};  
        hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(),ids);  
        hListView.setAdapter(hListViewAdapter);  
        hListView.setOnItemClickListener(new OnItemClickListener() {  
        	  
            @Override  
            public void onItemClick(AdapterView<?> parent, View view,  
                    int position, long id) {  
            	
            	
                // TODO Auto-generated method stub  
//              if(olderSelectView == null){  
//                  olderSelectView = view;  
//              }else{  
//                  olderSelectView.setSelected(false);  
//                  olderSelectView = null;  
//              }  
//              olderSelectView = view;  
//              view.setSelected(true);  
               // previewImg.setImageResource(ids[position]);  
                hListViewAdapter.setSelectIndex(position);
                //WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
                //hListView.scrollTo(wm.getDefaultDisplay().getWidth(),0);
                //hListView.scrollTo(300);
                hListViewAdapter.notifyDataSetChanged(); 
                //
                filmName.setText("电影名称");
                //
                filmProperty.setText("iMax3D");
                filmProperty.setBackgroundResource(R.drawable.film_property_imax);
                //
                filmScore.setText("8.3分");
            }  
        }); 
        cinemaFilmList = (ListViewForScrollView)findViewById(R.id.cinema_film_list);
        cinemaFilmScroll = (ScrollView)findViewById(R.id.cinema_film_scroll);
        cinemaFilmDate = (RadioGroup)findViewById(R.id.cinema_film_date);
        filmName = (TextView)findViewById(R.id.film_name);
        filmProperty = (TextView)findViewById(R.id.film_property);
        filmScore = (TextView)findViewById(R.id.film_score);
        cinemaFilmDetail = (RelativeLayout) findViewById(R.id.cinema_film_detail);
        cinemaFilmTittleBack = (RelativeLayout) findViewById(R.id.cinema_film_title_back);
	}
	public List<String> getData(){
		List<String> list = new ArrayList<String>();
		list.add("17:25");
		list.add("17:25");
		list.add("17:25");
		list.add("17:25");
		list.add("17:25");
		list.add("17:25");
		list.add("17:25");
		return list;
		
	}
}
