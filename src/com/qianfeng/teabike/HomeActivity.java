package com.qianfeng.teabike;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.qianfeng.teabike.adapter.HomeFragmentPagerAdapter;

public class HomeActivity extends FragmentActivity implements TabListener,
		OnPageChangeListener {
	private String[] msgs = { "头条", "百科", "资讯", "经营", "数据" };
	private ViewPager mHomeViewPager;
	private ActionBar appBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_home);
		mHomeViewPager = (ViewPager) findViewById(R.id.viewpager_home);
		HomeFragmentPagerAdapter mAdapter = new HomeFragmentPagerAdapter(
				getSupportFragmentManager(), msgs);
		mHomeViewPager.setAdapter(mAdapter);
		mHomeViewPager.setOnPageChangeListener(this);
		initActionBar();
	}

	// 初始化actionBar
	public void initActionBar() {
		appBar = getActionBar();
		// 设置无返回键
		appBar.setDisplayHomeAsUpEnabled(false);
		// 不显示actionbar的应用名称
		appBar.setDisplayShowTitleEnabled(false);
		// 不显示actionbar的应用图标
		appBar.setDisplayShowHomeEnabled(false);
		// 设置actionbar为导航标签栏模式
		appBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (int i = 0; i < msgs.length; i++) {
			Tab tab = appBar.newTab();
			// 给导航栏设置文本
			tab.setText(msgs[i]);
			// 设置监听事件
			tab.setTabListener(this);
			// 添加到actionbar
			appBar.addTab(tab);
		}
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// Log.i("Tab", tab + "");
		// Log.i("mHomeViewPager", mHomeViewPager + "");
		mHomeViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		// 当viewpager的页面被选择时，actionbar也跳转到相应的导航标签
		appBar.setSelectedNavigationItem(position);
	}
}
