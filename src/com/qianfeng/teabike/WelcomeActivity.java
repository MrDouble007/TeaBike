package com.qianfeng.teabike;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.qianfeng.teabike.adapter.MyPagerAdapter;

public class WelcomeActivity extends Activity implements OnClickListener {
	private List<ImageView> pagers;
	private MyPagerAdapter mAdapter;
	private int[] images = { R.drawable.slide1, R.drawable.slide2,
			R.drawable.slide3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_welcome);
		ViewPager welcomeViewPager = (ViewPager) findViewById(R.id.viewpager_welcome);
		pagers = new ArrayList<ImageView>();
		init();
		mAdapter = new MyPagerAdapter(pagers);
		welcomeViewPager.setAdapter(mAdapter);
	}

	private void init() {
		ImageView imageView;
		for (int i = 0; i < images.length; i++) {
			imageView = new ImageView(getApplicationContext());
			imageView.setImageResource(images[i]);
			pagers.add(imageView);
			if (i == 2) {
				imageView.setOnClickListener(this);
			}
		}
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(getApplicationContext(), HomeActivity.class));
		finish();
	}
}
