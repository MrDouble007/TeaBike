package com.qianfeng.teabike.task;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.TextView;

import com.qianfeng.teabike.bean.SingleNewsContent;

public class GetSingleContentTask extends
		AsyncTask<String, Void, SingleNewsContent> {

	private TextView singleTitle;
	private WebView webView;
	private TextView singleTime;
	private ProgressDialog dialog;

	public GetSingleContentTask(WebView webView, TextView singleTitle,
			TextView singleTime, ProgressDialog dialog) {
		this.webView = webView;
		this.singleTitle = singleTitle;
		this.singleTime = singleTime;
		this.dialog = dialog;
	}

	@Override
	protected SingleNewsContent doInBackground(String... params) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(params[0]);
		SingleNewsContent singleNewsContent = null;
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String urlContent = EntityUtils.toString(entity);
				JSONObject singleDetails = new JSONObject(urlContent);
				JSONObject data = singleDetails.getJSONObject("data");
				String id = data.getString("id");
				String title = data.getString("title");
				String source = data.getString("source");
				String wap_content = data.getString("wap_content");
				String create_time = data.getString("create_time");
				String author = data.getString("author");
				String weiboUrl = data.getString("weiboUrl");
				singleNewsContent = new SingleNewsContent(id, title, source,
						wap_content, create_time, author, weiboUrl);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return singleNewsContent;
	}

	@Override
	protected void onPostExecute(SingleNewsContent result) {
		super.onPostExecute(result);
		singleTitle.setText(result.getTitle());
		singleTime.setText("时间：" + result.getCreate_time() + " 来源："
				+ result.getAuthor());
		// baseUrl为默认的html代码，data为要加载的html代码，
		// mimeType为文本类型，encoding为编码格式，
		// historyUrl为历史记录的html代码。
		webView.loadDataWithBaseURL(null, result.getWap_content(), "text/html",
				"UTF-8", null);
		// 取消显示dialog
		dialog.dismiss();
	}
}
