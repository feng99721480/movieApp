/*
 * 
 */

package com.wiseweb.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.wiseweb.activity.MainActivity;
import com.wiseweb.constant.Constant;
import com.wiseweb.movie.R;

public class BottomControlPanel extends RelativeLayout implements
		View.OnClickListener {
	private Context mContext;
	private ImageText filmBtn = null;
	private ImageText cinemaBtn = null;
	private ImageText communityBtn = null;
	private ImageText mineBtn = null;
	private int DEFALUT_BACKGROUND_COLOR = Color.rgb(243, 243, 243);
	private BottomPanelCallback mBottomCallback = null;
	private List<ImageText> viewList = new ArrayList<ImageText>();
	private SharedPreferences mflag;

	public interface BottomPanelCallback {
		public void onBottomPanelClick(int itemId);
	}

	public BottomControlPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	// ʵ���������Ԫ��
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		filmBtn = (ImageText) findViewById(R.id.btn_film);
		cinemaBtn = (ImageText) findViewById(R.id.btn_cinema);
		communityBtn = (ImageText) findViewById(R.id.btn_community);
		mineBtn = (ImageText) findViewById(R.id.btn_mine);

		setBackgroundColor(DEFALUT_BACKGROUND_COLOR);
		viewList.add(filmBtn);
		viewList.add(cinemaBtn);
		viewList.add(communityBtn);
		viewList.add(mineBtn);

	}

	// 
	public void initBottomPanel() {
		if (filmBtn != null) {
			filmBtn.setImage(R.drawable.ic_tab_movie);
			filmBtn.setText("电影");
		}
		if (cinemaBtn != null) {
			cinemaBtn.setImage(R.drawable.ic_tab_cinema);
			cinemaBtn.setText("影院");
		}
		if (communityBtn != null) {
			communityBtn.setImage(R.drawable.ic_tab_community);
			communityBtn.setText("社区");
		}
		if (mineBtn != null) {
			mineBtn.setImage(R.drawable.ic_tab_mine);
			mineBtn.setText("我的");
		}
		setBtnListener();

	}

	private void setBtnListener() {
		int num = this.getChildCount();
		for (int i = 0; i < num; i++) {
			View v = getChildAt(i);
			if (v != null) {
				v.setOnClickListener(this);
			}
		}
	}

	public void setBottomCallback(BottomPanelCallback bottomCallback) {
		mBottomCallback = bottomCallback;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		initBottomPanel();
		int index = -1;
		switch (v.getId()) {
		// ���id�жϵ�������ĸ�view
		case R.id.btn_film:
			index = Constant.BTN_FLAG_FILM;
			filmBtn.setChecked(Constant.BTN_FLAG_FILM);
			break;
		case R.id.btn_cinema:
			index = Constant.BTN_FLAG_CINEMA;
			cinemaBtn.setChecked(Constant.BTN_FLAG_CINEMA);
			break;
		case R.id.btn_community:
			index = Constant.BTN_FLAG_COMMUNITY;
			communityBtn.setChecked(Constant.BTN_FLAG_COMMUNITY);
			break;
		case R.id.btn_mine:
			index = Constant.BTN_FLAG_MINE;
			mineBtn.setChecked(Constant.BTN_FLAG_MINE);
			break;
		default:
			break;
		}
		// �ϱ�������ĸ�view������
		if (mBottomCallback != null) {
			mBottomCallback.onBottomPanelClick(index);
		}
	}

	// Ĭ�ϵı�ѡ�е��ǵ�һ�� �� ��Ӱ����
	public void defaultBtnChecked() {
		if (filmBtn != null) {
			if (mflag == null) {
				System.out.println("1111111"+mflag==null);
				filmBtn.setChecked(Constant.BTN_FLAG_FILM);
			} else {
				if (mflag.getString("flag", null) == null) {
					filmBtn.setChecked(Constant.BTN_FLAG_FILM);
				} else if (mflag.getString("flag", null).equals("filmFragment")) {
					filmBtn.setChecked(Constant.BTN_FLAG_FILM);
				} else if (mflag.getString("flag", null).equals(
						"cinemaFragment")) {
					cinemaBtn.setChecked(Constant.BTN_FLAG_CINEMA);
				}
			}
		} else {
			mflag = mContext.getSharedPreferences("fragmentflag",
					Context.MODE_PRIVATE);
		}

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		layoutItems(left, top, right, bottom);
	}

	/**
	 * 
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	private void layoutItems(int left, int top, int right, int bottom) {
		int n = getChildCount();
		if (n == 0) {
			return;
		}
		// ���paddingֵ��
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		// Log.i("1111", "paddingLeft = " + paddingLeft + " paddingRight = " +
		// paddingRight);
		int width = right - left;
		int height = bottom - top;
		// Log.i("1111", "width = " + width + " height = " + height);
		int allViewWidth = 0;
		for (int i = 0; i < n; i++) {
			View v = getChildAt(i);
			// Log.i("2222", "v.getWidth() = " + v.getWidth());
			allViewWidth += v.getWidth();
		}
		// 
		int blankWidth = (width - allViewWidth - paddingLeft - paddingRight)
				/ (n - 1);
		// Log.i("1111", "blankV = " + blankWidth );

		LayoutParams params1 = (LayoutParams) viewList.get(1).getLayoutParams();
		params1.leftMargin = blankWidth;
		viewList.get(1).setLayoutParams(params1);

		LayoutParams params2 = (LayoutParams) viewList.get(2).getLayoutParams();
		params2.leftMargin = blankWidth;
		viewList.get(2).setLayoutParams(params2);
	}
}
