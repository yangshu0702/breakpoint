package com.open.young.soul.common.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 万能viewholder
 * 
 * @author breakpoint
 * 
 */
public class BPCommonViewHolder {

	private SparseArray<View> viewArray = new SparseArray<>();

	private View convertView;

	private BPCommonViewHolder(Context context, View convertView,
			ViewGroup parent, int layoutId) {
		convertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		convertView.setTag(this);
	}

	public static BPCommonViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (null == convertView.getTag()) {
			return new BPCommonViewHolder(context, convertView, parent,
					layoutId);
		}
		return (BPCommonViewHolder) convertView.getTag();
	}

	public <T extends View> T getView(int id) {
		if (null == viewArray.get(id)) {
			View view = convertView.findViewById(id);
			viewArray.put(id, view);
		}
		return (T) viewArray.get(id);
	}

	public View getConvertView() {
		return convertView;
	}

}
