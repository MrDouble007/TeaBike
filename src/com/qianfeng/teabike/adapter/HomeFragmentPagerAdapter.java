package com.qianfeng.teabike.adapter;

import java.util.ArrayList;

import com.qianfeng.teabike.fragment.ContentFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

	private String[] msgs;
	private ArrayList<Fragment> fragments;

	public HomeFragmentPagerAdapter(FragmentManager fm, String[] msgs) {
		super(fm);
		this.msgs = msgs;
		fragments = new ArrayList<Fragment>();
		for (int i = 0; i < msgs.length; i++) {
			fragments.add(new ContentFragment(msgs[i]));
		}
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return msgs.length;
	}

}
