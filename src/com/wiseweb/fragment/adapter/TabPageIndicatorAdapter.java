package com.wiseweb.fragment.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wiseweb.constant.Constant;
import com.wiseweb.fragment.BuyTicketSelectCinemaFragment;

public class TabPageIndicatorAdapter extends FragmentPagerAdapter{
	public TabPageIndicatorAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public Fragment getItem(int position) {
		// �½�һ��Fragment��չʾViewPager item�����ݣ������ݲ���
		Fragment fragment = new BuyTicketSelectCinemaFragment();
//		Bundle args = new Bundle();
//		args.putString("arg", Constant.CONTENT[position]);
//		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return Constant.CONTENT[position % Constant.CONTENT.length];
	}

	@Override
	public int getCount() {
		return Constant.CONTENT.length;
	}
}
