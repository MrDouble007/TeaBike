package com.qianfeng.teabike.holder;

import android.widget.ImageView;
import android.widget.TextView;

public class Holder {

	private TextView tvTitle;
	private TextView tvNickName;
	private ImageView imvThumb;

	public Holder(TextView tvTitle, TextView tvNickName, ImageView imvThumb) {
		this.tvTitle = tvTitle;
		this.tvNickName = tvNickName;
		this.imvThumb = imvThumb;
	}

	public Holder() {
		super();
	}

	public TextView getTvTitle() {
		return tvTitle;
	}

	public void setTvTitle(TextView tvTitle) {
		this.tvTitle = tvTitle;
	}

	public TextView getTvNickName() {
		return tvNickName;
	}

	public void setTvNickName(TextView tvNickName) {
		this.tvNickName = tvNickName;
	}

	public ImageView getImvThumb() {
		return imvThumb;
	}

	public void setImvThumb(ImageView imvThumb) {
		this.imvThumb = imvThumb;
	}

}
