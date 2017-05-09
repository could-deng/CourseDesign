package com.example.coursedesign.adapter;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.activity.ImagePagerActivity;
import com.example.coursedesign.bean.DailyDiary;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/17.
 */
public class PicNewsListviewAdapter extends BaseAdapter {
    private List<DailyDiary> daily_list;
    private Context context;

    public PicNewsListviewAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<DailyDiary> daily_list) {
        if ((this.daily_list != null) && (daily_list != this.daily_list)) this.daily_list.clear();
        this.daily_list = daily_list;
        notifyDataSetChanged();
    }

    private List<DailyDiary> getList() {
        return daily_list;
    }

    @Override
    public int getCount() {
        if(daily_list == null || daily_list.size() ==0){
            return 0 ;
        }
        else{
            return daily_list.size();
        }
    }

    //不要调用
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_pic_news_lv_item, null);
            holder.pic_news_daily_pic = (SimpleDraweeView) convertView.findViewById(R.id.pic_news_daily_pic);
            holder.pic_news_daily_content  =(TextView) convertView.findViewById(R.id.pic_news_daily_content);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.pic_news_daily_content.setText(daily_list.get(position).getText());
        if (daily_list.size() == 0 || daily_list == null) {//默认
            holder.pic_news_daily_pic.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.bg_ugc_personal)).build());
        } else {
            String ss =daily_list.get(position).getPic_url();
            holder.pic_news_daily_pic.setImageURI(Uri.parse(ss));
            holder.pic_news_daily_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImagePagerActivity.class);
                    ArrayList ss = new ArrayList();
                    for(int i =0;i<daily_list.size();i++){
                        ss.add(daily_list.get(i).getPic_url());
                    }
                    intent.putStringArrayListExtra(ImagePagerActivity.IMAGE_PATHS,ss);
                    intent.putExtra(ImagePagerActivity.IMAGE_FROM, ImagePagerActivity.IMAGE_FROM_NET);
                    intent.putExtra(ImagePagerActivity.IMAGE_INDEX_FIRST,position);
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }
    private static class ViewHolder{
        TextView pic_news_daily_content;
        SimpleDraweeView pic_news_daily_pic;
    }
}
