package com.qianfeng.teabike.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyPagerAdapter extends PagerAdapter {
	private List<ImageView> pagers;

	public MyPagerAdapter(List<ImageView> pagers) {
		this.pagers = pagers;
	}

	@Override
	public int getCount() {
		return pagers.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	/**
	 * 应该去释放相关的资源，比如图片，应该把暂时看不到的图片在这个方法中标记为可回收的(bitmap.recycle())
	 * 
	 * @param container
	 * @param position
	 * @param object
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		 container.removeView(pagers.get(position));
	}

	/**
	 * 本方法用来进行页面初始化的，相当于BaseAdapter的getView方法
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = (ImageView) pagers.get(position);
		container.addView(imageView);
		return imageView;
	}

}
