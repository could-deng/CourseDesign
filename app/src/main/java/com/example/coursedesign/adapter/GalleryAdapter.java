package com.example.coursedesign.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.bean.City;
import com.example.coursedesign.bean.Trip;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by yuanqiang on 2016/4/15.
 */
public class GalleryAdapter extends BaseAdapter{
    private Context context;
    private List<Trip> trip_list;
    private int selectItem;
    private LayoutInflater mInflater;
    private SimpleDateFormat format;
    public GalleryAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        format = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void setSelectItem(int selectItem) {
        if (this.selectItem != selectItem) {
            this.selectItem = selectItem;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if ((getTrip_list() == null) || (getTrip_list().size() == 0)) {
            return 1;
        } else {//多返回一个,最后一个默认是创建行程
            return getTrip_list().size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return trip_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public List<Trip> getTrip_list() {
        return trip_list;
    }

    public void setTrip_list(List<Trip> trip_list) {
        if ((this.trip_list != null) && (trip_list != this.trip_list)) this.trip_list.clear();
        this.trip_list = trip_list;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (getTrip_list() == null) return null;
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gallery_adapter_item, parent, false);
            holder = new ViewHolder();
            holder.rl_gallery_item = (RelativeLayout) convertView.findViewById(R.id.rl_gallery_item);
            holder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.iv_gallery_item);
            holder.tv_sum_date_trip = (TextView) convertView.findViewById(R.id.tv_sum_date_trip);
            holder.tv_city_line = (TextView) convertView.findViewById(R.id.tv_city_line);
            holder.tv_start_date = (TextView) convertView.findViewById(R.id.tv_start_date);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        if(position == getCount()-1){//最后一项
            holder.imageView.setImageResource(R.drawable.bg_ugc_personal);
        }
        else {//不为最后一项
            if (!TextUtils.isEmpty(trip_list.get(position).getCity_line().get(0).getCity_pic_url())) {
                Trip trip = trip_list.get(position);
                holder.imageView.setImageURI(Uri.parse(trip.getCity_line().get(0).getCity_pic_url()));
                holder.tv_sum_date_trip.setText(trip.getCity_line().size()+"天");
                String city_line = trip.getCity_line().get(0).getCity_name();
                List<City>  position_city_line= trip.getCity_line();
                for (int i = 1 ;i<trip_list.size();i++){
                    if(position_city_line.get(i).getCity_id()!=position_city_line.get(i-1).getCity_id()){
                        city_line+="+"+position_city_line.get(i).getCity_name();
                    }
                }
                holder.tv_city_line.setText(city_line);
                holder.tv_start_date.setText(format.format(trip.getStart_time())+"出发");
            } else {
                holder.imageView.setImageResource(R.drawable.loading_pic);
            }
        }


        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(480, 800));   //分辨率自己定
        if(selectItem==position){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.my_scale_action);    //实现动画效果
            holder.rl_gallery_item.setLayoutParams(new Gallery.LayoutParams(770,990));
            holder.rl_gallery_item.startAnimation(animation);  //选中时，这是设置的比较大
        }
        else{
            holder.rl_gallery_item.setLayoutParams(new Gallery.LayoutParams(600,900));//未选中
        }
        return convertView;
    }

    private class ViewHolder{
        public RelativeLayout rl_gallery_item;
        public ImageView imageView;
        public TextView tv_sum_date_trip;
        public TextView tv_city_line;
        public TextView tv_start_date;
    }
}
