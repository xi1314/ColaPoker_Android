package com.poker.colapanda.zhenrendantiao.live.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.poker.colapanda.zhenrendantiao.R;
import com.poker.colapanda.zhenrendantiao.common.widget.MyListView;

import java.util.ArrayList;

/**
 * Created by zhangze on 2017/3/28 12:03
 */
public class HsvHistoryAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<String[]> list;


    public HsvHistoryAdapter(Context context, ArrayList<String[]> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (list != null && list.size() != 0) {
            ret = list.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.scrollview_item_history,viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.myListView = (MyListView) view.findViewById(R.id.room_small_history1);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ListHistoryAdapter listHistoryAdapter = new ListHistoryAdapter(context, list.get(i));
        viewHolder.myListView.setAdapter(listHistoryAdapter);
        return view;
    }
    class ViewHolder {
        MyListView myListView;
    }
}
