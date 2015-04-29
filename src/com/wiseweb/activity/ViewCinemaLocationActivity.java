package com.wiseweb.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.wiseweb.movie.R;

/**
 * 演示poi搜索功能
 * , OnGetSuggestionResultListener
 */
public class ViewCinemaLocationActivity extends FragmentActivity implements
		OnGetPoiSearchResultListener {

	private PoiSearch mPoiSearch = null;
	//private SuggestionSearch mSuggestionSearch = null;
	private BaiduMap mBaiduMap = null;
	private SharedPreferences cinemaAddressPreferences;
	private SharedPreferences city;
	/**
	 * 搜索关键字输入窗口
	 */
//	private AutoCompleteTextView keyWorldsView = null;
//	private ArrayAdapter<String> sugAdapter = null;
	private int load_Index = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_cinema_location);
		// 初始化搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
				.findFragmentById(R.id.map))).getBaiduMap();
		cinemaAddressPreferences = this.getSharedPreferences("cinemaAddress",Context.MODE_PRIVATE);
		String cinemaAddress = cinemaAddressPreferences.getString("cinemaAddress", null);
		//得到城市名称
		city = this.getSharedPreferences("city",Context.MODE_PRIVATE);
		String cityName = city.getString("city", null);
		/**
		 * 根据地址搜索
		 */
		if(!cinemaAddress.equals(null)){
//			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid("北京市"+cinemaAddress));
					
			mPoiSearch.searchInCity((new PoiCitySearchOption())
					.city(cityName)
					.keyword(cinemaAddress)
					.pageNum(load_Index));	

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mPoiSearch.destroy();
		//mSuggestionSearch.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	public void onGetPoiResult(PoiResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(ViewCinemaLocationActivity.this, "未找到结果",
					Toast.LENGTH_LONG).show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(ViewCinemaLocationActivity.this, strInfo,
					Toast.LENGTH_LONG).show();
		}
	}

	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(ViewCinemaLocationActivity.this, "抱歉，未找到结果",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(ViewCinemaLocationActivity.this,
					result.getName() + ": " + result.getAddress(),
					Toast.LENGTH_SHORT).show();
		}
	}

	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			// if (poi.hasCaterDetails) {
			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
					.poiUid(poi.uid));
			// }
			return true;
		}
	}
}
