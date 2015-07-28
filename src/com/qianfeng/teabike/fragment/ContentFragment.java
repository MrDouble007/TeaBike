package com.qianfeng.teabike.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jack.pullrefresh.ui.PullToRefreshBase;
import com.jack.pullrefresh.ui.PullToRefreshBase.OnRefreshListener;
import com.jack.pullrefresh.ui.PullToRefreshListView;
import com.qianfeng.teabike.R;
import com.qianfeng.teabike.SingleNewsActivity;
import com.qianfeng.teabike.adapter.ContentListAdapter;
import com.qianfeng.teabike.adapter.MyHeadViewAdapter;
import com.qianfeng.teabike.bean.TeaNews;
import com.qianfeng.teabike.task.GetTeaDatasTask;

public class ContentFragment extends Fragment implements OnItemClickListener,
		OnCheckedChangeListener, OnPageChangeListener,
		OnRefreshListener<ListView>, OnClickListener {
	private static final int CHANGEPAPER = 1;
	/**
	 * 
	 */
	private String[] urls = {
			"http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&rows=10&page=",
			"http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&rows=15&type=16&page=",
			"http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&rows=15&type=52&page=",
			"http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&rows=15&type=53&page=",
			"http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&rows=15&type=54&page=",
			"http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id=",
			// 首页展示
			"http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow" };
	private int[] imgs = { R.drawable.a1, R.drawable.a2, R.drawable.a3 };
	private String msg;
	private ArrayList<TeaNews> teaDatas;
	private ContentListAdapter mAdapter;
	private ViewPager headView;
	private RadioGroup radioGroupBg;
	private int num;
	private int pager = 1;

	public ContentFragment(String msg) {
		this.msg = msg;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == CHANGEPAPER) {
				num++;
				int index = num % imgs.length;
				headView.setCurrentItem(index);
				mHandler.sendEmptyMessageDelayed(CHANGEPAPER, 3000);
			}
		};
	};
	private GetTeaDatasTask task;
	private PullToRefreshListView refreshFragment;
	private ListView contentListView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View parentView = inflater.inflate(R.layout.frag_content, null);

		parentView.findViewById(R.id.iv_backtotop).setOnClickListener(this);
		refreshFragment = (PullToRefreshListView) parentView
				.findViewById(R.id.refresh_fragment);
		refreshFragment.setPullLoadEnabled(true);
		refreshFragment.setPullRefreshEnabled(false);

		teaDatas = new ArrayList<TeaNews>();
		contentListView = refreshFragment.getRefreshableView();
		// 设置上拉刷新监听器
		refreshFragment.setOnRefreshListener(this);
		// 创建一个可以添加到listview头部的view
		View view = inflater.inflate(R.layout.head_view_item, null);
		radioGroupBg = (RadioGroup) view.findViewById(R.id.radioGroup_bg);
		radioGroupBg.check(R.id.radio0);
		radioGroupBg.setOnCheckedChangeListener(this);

		// 获得一个头部
		getHeadView(view);
		headView.setOnPageChangeListener(this);
		if (msg.equals("头条")) {
			// 注意：添加头最好放在setAdapter之前去操作
			contentListView.addHeaderView(headView);
		}

		mAdapter = new ContentListAdapter(teaDatas);
		contentListView.setAdapter(mAdapter);
		contentListView.setOnItemClickListener(this);

		task = new GetTeaDatasTask(teaDatas, mAdapter, null);
		// "头条", "百科", "资讯", "经营", "数据"
		switch (msg) {
		case "头条":
			task.execute(urls[0]);
			break;
		case "百科":
			task.execute(urls[1]);
			break;
		case "资讯":
			task.execute(urls[2]);
			break;
		case "经营":
			task.execute(urls[3]);
			break;
		case "数据":
			task.execute(urls[4]);
			break;

		default:
			break;
		}
		return parentView;

	}

	@Override
	public void onResume() {
		super.onResume();
		// 开始定时器
		mHandler.sendEmptyMessageDelayed(CHANGEPAPER, 3000);
	}

	@Override
	public void onStop() {
		super.onStop();
		// 关闭定时器
		mHandler.removeMessages(CHANGEPAPER);
	}

	/**
	 * 获得头的方法
	 * 
	 * @param view
	 */
	public void getHeadView(View view) {
		headView = (ViewPager) view.findViewById(R.id.tv_headview);
		headView.setAdapter(new MyHeadViewAdapter(imgs));
		Options options = new Options();
		// 设置这个属性为ture就不是真的去解码图片，而是获取图片的宽高。如果设置为true，那么BitmapFactory.decodeResource的返回值就是null
		options.inJustDecodeBounds = true;
		// 去获取图片的宽和高
		BitmapFactory.decodeResource(getResources(), R.drawable.a1, options);
		// 计算出图片的宽高比
		float scale = options.outWidth / options.outHeight;

		// 获取屏幕的参数
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		// 获取屏幕的宽的像素值
		int width = displayMetrics.widthPixels;
		// 根据图片的宽高比计算出ViewPager的高
		int height = (int) (width / scale);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				height);
		// 设置布局参数，相当于layout文件中的属性
		headView.setLayoutParams(params);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), SingleNewsActivity.class);
		TeaNews singleNews = mAdapter.getItem(position);
		String newsId = singleNews.getId();
		String url = urls[5] + newsId;
		intent.putExtra("url", url);
		startActivity(intent);
	}

	/*
	 * radioGroup监听器
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int count = group.getChildCount();
		for (int i = 0; i < count; i++) {
			if (checkedId == group.getChildAt(i).getId()) {
				headView.setCurrentItem(i);
			}
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		radioGroupBg.check(radioGroupBg.getChildAt(position).getId());
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		task = new GetTeaDatasTask(teaDatas, mAdapter, refreshFragment);
		// "头条", "百科", "资讯", "经营", "数据"
		switch (msg) {
		case "头条":
			pager++;
			task.execute(urls[0] + pager);
			// Log.i("头条", urls[0] + pager);
			break;
		case "百科":
			pager++;
			task.execute(urls[1] + pager);
			break;
		case "资讯":
			pager++;
			task.execute(urls[2] + pager);
			break;
		case "经营":
			pager++;
			task.execute(urls[3] + pager);
			break;
		case "数据":
			pager++;
			task.execute(urls[4] + pager);
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// 滑动到顶部
		contentListView.smoothScrollToPosition(0);
	}
}
