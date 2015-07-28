package com.qianfeng.teabike;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LoadingActivity extends Activity {
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Intent welcomeIntent = new Intent(getApplicationContext(),
						WelcomeActivity.class);
				startActivity(welcomeIntent);
			} else if (msg.what == 2) {
				Intent homeIntent = new Intent(getApplicationContext(),
						HomeActivity.class);
				startActivity(homeIntent);
			}
			finish();
		};
	};
	private boolean isFirstTime;
	private SharedPreferences isFirst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_loading);
		isFirst = getSharedPreferences("isFirstOrNot", Context.MODE_PRIVATE);
		isFirstTime = isFirst.getBoolean("isFirstOrNot", true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = new Message();
				if (isFirstTime == true) {
					msg.what = 1;
					Editor editor = isFirst.edit();
					editor.putBoolean("isFirstOrNot", false);
					editor.commit();
				} else if (isFirstTime == false) {
					msg.what = 2;
				}
				mHandler.sendMessage(msg);
			}
		}).start();
	}
}
