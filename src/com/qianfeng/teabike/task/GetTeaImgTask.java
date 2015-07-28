package com.qianfeng.teabike.task;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.qianfeng.teabike.loader.ImageLoader;

public class GetTeaImgTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView imvThumb;
	private ImageLoader imageLoader;

	public GetTeaImgTask(ImageView imvThumb, ImageLoader imageLoader) {
		this.imvThumb = imvThumb;
		this.imageLoader = imageLoader;
	}

	@Override
	protected Bitmap doInBackground(String... params) {

		Bitmap bitmap = null;
		String cachePath = imageLoader.getmCacheDir();
		File cacheFile = new File(cachePath);
		if (!cacheFile.exists()) {
			cacheFile.mkdirs();
		}
		// 判断本地有没有这张图片 ，有的话直接从本地拿
		File file = new File(imageLoader.getCacheFilePath(params[0]));
		if (file.exists()) {
			// 从本地磁盘加载图片
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		} else {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(params[0]);
			try {
				HttpResponse response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					byte[] bytearray = EntityUtils.toByteArray(entity);
					bitmap = BitmapFactory.decodeByteArray(bytearray, 0,
							bytearray.length);
					// InputStream is = entity.getContent();
					// bitmap = BitmapFactory.decodeStream(is);
					File cacheImg = new File(
							imageLoader.getCacheFilePath(params[0]));
					FileOutputStream fos = new FileOutputStream(cacheImg);
					fos.write(bytearray);
					fos.flush();
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		imvThumb.setImageBitmap(result);
	}
}
