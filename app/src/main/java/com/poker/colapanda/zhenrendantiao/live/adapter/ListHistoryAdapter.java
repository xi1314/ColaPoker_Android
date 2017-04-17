package com.poker.colapanda.zhenrendantiao.live.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.poker.colapanda.zhenrendantiao.R;

/**
 * Created by zhangze on 2017/3/16 16:41
 */
public class ListHistoryAdapter extends BaseAdapter {

    private Context context;
    public String[] card;

    public ListHistoryAdapter(Context context, String[] card) {
        this.context = context;
        this.card = card;
    }



    @Override
    public int getCount() {
        int ret = 0;
        if (card != null && card.length != 0) {
            ret = card.length;
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
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_history,viewGroup, false);
            holder = new ViewHolder();
            holder.itemIv = (ImageView) view.findViewById(R.id.item_iv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
            holder.itemIv.setImageResource(context.getResources().getIdentifier(card[i] + "__0", "drawable", "com.poker.colapanda.zhenrendantiao"));
        return view;
    }
    class ViewHolder {
        ImageView itemIv;
    }
}
