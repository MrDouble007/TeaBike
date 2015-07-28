package com.qianfeng.teabike.adapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.teabike.R;
import com.qianfeng.teabike.bean.TeaNews;
import com.qianfeng.teabike.holder.Holder;
import com.qianfeng.teabike.loader.ImageLoader;
import com.qianfeng.teabike.task.GetTeaImgTask;

public class ContentListAdapter extends BaseAdapter {

	private ArrayList<TeaNews> teaDatas;

	public ContentListAdapter(ArrayList<TeaNews> teaDatas) {
		this.teaDatas = teaDatas;
	}

	@Override
	public int getCount() {
		return teaDatas.size();
	}

	@Override
	public TeaNews getItem(int position) {
		return teaDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.tea_adapter_item, null);
			TextView tvTitle = (TextView) convertView
					.findViewById(R.id.tv_title);
			TextView tvNickName = (TextView) convertView
					.findViewById(R.id.tv_nickname);
			ImageView imvThumb = (ImageView) convertView
					.findViewById(R.id.imv_thumb);
			holder = new Holder(tvTitle, tvNickName, imvThumb);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
			TeaNews teaNews = getItem(position);
			holder.getTvTitle().setText(teaNews.getTitle());
			holder.getTvNickName().setText(
					teaNews.getSource() + " " + teaNews.getNickname() + " "
							+ teaNews.getCreate_time());
			ImageView imvThumb = holder.getImvThumb();
			imvThumb.setImageResource(R.drawable.defaultcovers);
			ImageLoader.getInstance(parent.getContext()).loadImage(imvThumb,teaNews.getWap_thumb());

		return convertView;
	}

}
