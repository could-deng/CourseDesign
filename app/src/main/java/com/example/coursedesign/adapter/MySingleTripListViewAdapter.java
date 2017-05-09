package com.example.coursedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.bean.City;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yuanqiang on 2016/4/11.
 */
public class MySingleTripListViewAdapter extends BaseAdapter {
    private List<City> cityList;
    private Date start_date;
    private Context context;

    public MySingleTripListViewAdapter(List<City> cityList, Date start_date, Context context) {
        this.cityList = cityList;
        this.start_date = start_date;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_pull_to_zoom_item, null);
            holder.date_xinqi = (TextView) convertView.findViewById(R.id.date_xinqi);
            holder.day_riqi = (TextView) convertView.findViewById(R.id.day_riqi);
            holder.city_name  =(TextView) convertView.findViewById(R.id.city_name);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(start_date);
        cal.add(Calendar.DATE, position);
//        (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime())
        holder.date_xinqi.setText((new SimpleDateFormat("EEEE")).format(cal.getTime()));
        holder.day_riqi.setText(new SimpleDateFormat("dd").format(cal.getTime()));
        if(position == 0){
            holder.city_name.setText("出发地>"+cityList.get(position).getCity_name());
        }
        else if(position == cityList.size()){
            holder.city_name.setText(cityList.get(position).getCity_name()+">返回地");
        }
        holder.city_name.setText(cityList.get(position).getCity_name());
        return convertView;
    }
    private static class ViewHolder{
        TextView date_xinqi;
        TextView day_riqi;
        TextView city_name;
    }
}
