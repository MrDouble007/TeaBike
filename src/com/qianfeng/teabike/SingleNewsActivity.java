package com.qianfeng.teabike;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.teabike.task.GetSingleContentTask;

public class SingleNewsActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_singlenews);
		// 创建缓冲dialog，在加载消息之前
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setCancelable(false);
		dialog.setIcon(R.drawable.ic_logo);
		dialog.setTitle("消息");
		dialog.setMessage("正在下载中，请稍后...");
		dialog.show();

		Intent intent = getIntent();
		String url = intent.getStringExtra("url");

		WebView webView = (WebView) findViewById(R.id.webview_singlenews);
		TextView singleTitle = (TextView) findViewById(R.id.tv_singlenews_title);
		TextView singleTime = (TextView) findViewById(R.id.tv_singlenews_time);
		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.btn_collect).setOnClickListener(this);

		// WebView webView = singleNewsWebView.getRefreshableView();
		WebSettings settings = webView.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		settings.setAppCacheEnabled(true);
		settings.setBuiltInZoomControls(true);

		GetSingleContentTask task = new GetSingleContentTask(webView,
				singleTitle, singleTime, dialog);
		task.execute(url);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_collect:
			Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}
	}
}
