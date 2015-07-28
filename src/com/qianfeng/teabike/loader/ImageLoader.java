package com.qianfeng.teabike.loader;

import java.io.File;

import android.content.Context;
import android.widget.ImageView;

import com.qianfeng.teabike.task.GetTeaImgTask;

public class ImageLoader {
	private Context context;
	private static ImageLoader imageLoader;
	private String mCacheDir;
	private final String ImageDir = "ImageCache";

	public ImageLoader(Context context) {
		this.context = context;
	}

	// 单例模式获取唯一的imageloader对象
	public synchronized static ImageLoader getInstance(Context context) {
		if (imageLoader == null) {
			imageLoader = new ImageLoader(context);
			// 建立一个缓存目录 File.separatorChar 等同于 "/"
			imageLoader.mCacheDir = imageLoader.context.getCacheDir()
					.toString() + File.separatorChar + imageLoader.ImageDir;
		}
		return imageLoader;
	}

	public void loadImage(ImageView imageView, String url) {
		// 判断url是否为空为空的话不处理
		if (url == null || url.length() == 0) {
			return;
		}
		// 开启后台任务加载图片
		new GetTeaImgTask(imageView, imageLoader).execute(url);
	}

	/**
	 * 通过图片的网络地址获得缓存文件名称
	 * 
	 * @param imageurl
	 * @return
	 */
	public String getCacheFileName(String url) {
		// 截取图片名称
		return url.substring(url.lastIndexOf("/") + 1, url.length());
	}

	// 获取要缓存的图片的路径
	public String getCacheFilePath(String url) {
		String filePath = mCacheDir + File.separatorChar
				+ getCacheFileName(url);
		return filePath;
	}

	public String getmCacheDir() {
		return mCacheDir;
	}

	public void setmCacheDir(String mCacheDir) {
		this.mCacheDir = mCacheDir;
	}
}
