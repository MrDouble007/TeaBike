package com.qianfeng.teabike.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.jack.pullrefresh.ui.PullToRefreshListView;
import com.qianfeng.teabike.adapter.ContentListAdapter;
import com.qianfeng.teabike.bean.TeaNews;

public class GetTeaDatasTask extends AsyncTask<String, Void, List<TeaNews>> {

	private ContentListAdapter adapter;
	private ArrayList<TeaNews> teaDatas;
	private PullToRefreshListView refreshFragment;

	public GetTeaDatasTask(ArrayList<TeaNews> teaDatas,
			ContentListAdapter adapter, PullToRefreshListView refreshFragment) {
		this.teaDatas = teaDatas;
		this.adapter = adapter;
		this.refreshFragment = refreshFragment;
	}

	@Override
	protected List<TeaNews> doInBackground(String... params) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(params[0]);
		ArrayList<TeaNews> caches = new ArrayList<TeaNews>();
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String jsonString = EntityUtils.toString(entity);
				// System.out.println(jsonString);
				JSONObject allNews = new JSONObject(jsonString);
				if (allNews.getString("errorMessage").equals("success")) {
					JSONArray teaArray = allNews.getJSONArray("data");
					for (int i = 0; i < teaArray.length(); i++) {
						JSONObject singleNews = teaArray.getJSONObject(i);
						String id = singleNews.getString("id");
						String title = singleNews.getString("title");
						String source = singleNews.getString("source");
						String description = singleNews
								.getString("description");
						String wap_thumb = singleNews.getString("wap_thumb");
						String create_time = singleNews
								.getString("create_time");
						String nickname = singleNews.getString("nickname");
						TeaNews singleTeaNews = new TeaNews(id, title, source,
								description, wap_thumb, create_time, nickname);
						caches.add(singleTeaNews);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return caches;
	}

	@Override
	protected void onPostExecute(List<TeaNews> result) {
		super.onPostExecute(result);
		teaDatas.addAll(result);
		adapter.notifyDataSetChanged();
		if (refreshFragment != null) {
			refreshFragment.onPullUpRefreshComplete();
			Toast.makeText(refreshFragment.getContext(), "加载完成",
					Toast.LENGTH_SHORT).show();
		}
	}
}
