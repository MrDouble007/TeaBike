package com.qianfeng.teabike.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyHeadViewAdapter extends PagerAdapter {

	private int[] imgs;

	public MyHeadViewAdapter(int[] imgs) {
		this.imgs = imgs;
	}

	@Override
	public int getCount() {
		return imgs.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(container.getContext());
		imageView.setImageResource(imgs[position]);
		container.addView(imageView);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

	}
}
