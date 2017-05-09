package com.example.coursedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.activity.HotDesitinationActivity;
import com.example.coursedesign.bean.City;

import java.util.List;

/**
 * Created by yuanqiang on 2016/4/2.
 */
public class HorizontalListViewAdapter extends BaseAdapter {
    private List<City> list;
    private Context context;

    public HorizontalListViewAdapter(Context context, List<City> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.horizontal_listview_item, null);
            holder.tv_city_name = (TextView) convertView.findViewById(R.id.tv_city_name);
            holder.iv_city_cancel  =(ImageView) convertView.findViewById(R.id.iv_city_cancel);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        final City city = list.get(position);
        holder.tv_city_name.setText(city.getCity_name());
        holder.iv_city_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据库操作更新和缓存的某一条更新
                ((HotDesitinationActivity)context).updateCitySelected(city, false);
                ((HotDesitinationActivity)context).getChooseCityFragment().getCity_listview_adapter().updateCity(city, false);
                //Activity上界面更新（horizontalListview）
                ((HotDesitinationActivity)context).uploadSelectedCity(city,false);
            }
        });
        return convertView;
    }
    private static class ViewHolder{
        TextView tv_city_name;
        ImageView iv_city_cancel;
    }
}
