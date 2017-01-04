package com.sypm.shuyushangcheng.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sypm.shuyushangcheng.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */

public class HorizontalListViewAdapter extends BaseAdapter {

    List<String> mData;
    Context mContext;

    public HorizontalListViewAdapter(Context mContext, List<String> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    private ViewHolder vh = new ViewHolder();

    private static class ViewHolder {
        private TextView file;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_hor_list, null);
            vh.file = (TextView) convertView.findViewById(R.id.file);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
