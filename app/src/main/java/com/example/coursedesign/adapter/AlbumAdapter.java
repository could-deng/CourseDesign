package com.example.coursedesign.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.bean.PicNew;
import com.example.coursedesign.utils.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.List;

public class AlbumAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<PicNew> mData;

    public AlbumAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = null;
    }

    public void setList(List<PicNew> list) {
        if ((this.mData != null) && (list != this.mData)) this.mData.clear();
        this.mData = list;
        notifyDataSetChanged();
    }

    private List<PicNew> getList() {
        return mData;
    }


    @Override
    public int getCount() {
        if ((getList() == null) || (getList().size() == 0)) {
            return 0;
        } else {//多返回一个,最后一个默认是创建游记
            return getList().size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getList() == null) return null;
        AlbumViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_album_list_item, parent, false);
            holder = new AlbumViewHolder();
            holder.img_album_cover = (SimpleDraweeView) convertView.findViewById(R.id.img_album_cover);
            holder.tv_album_name = (TextView) convertView.findViewById(R.id.tv_album_name);
            holder.tv_album_subname = (TextView) convertView.findViewById(R.id.tv_album_subname);
            holder.creat_play_list_container = (LinearLayout) convertView.findViewById(R.id.creat_play_list_container);
            convertView.setTag(holder);
        }
        holder = (AlbumViewHolder) convertView.getTag();

        if (position == (getList().size())) {//最后一个
            holder.tv_album_name.setVisibility(View.GONE);
            holder.tv_album_subname.setVisibility(View.GONE);
            //holder.img_album_cover.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.add)).build());
            holder.img_album_cover.setVisibility(View.GONE);
            holder.creat_play_list_container.setVisibility(View.VISIBLE);
        } else {
            holder.tv_album_name.setVisibility(View.VISIBLE);
            holder.tv_album_subname.setVisibility(View.VISIBLE);
            //holder.img_album_cover.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.add)).build());
            holder.img_album_cover.setVisibility(View.VISIBLE);
            setViewHolderByData(holder, getList().get(position));
            holder.creat_play_list_container.setVisibility(View.GONE);
        }

        return convertView;
    }

    private void setViewHolderByData(AlbumViewHolder holder, PicNew info) {
        if (holder == null || info == null) return;

        holder.tv_album_name.setText(info.getNew_title());
        if (info.getTrip_sum() == null) {
            holder.tv_album_subname.setVisibility(View.GONE);
        } else {
            holder.tv_album_subname.setText(info.getTrip_sum());
        }
        if (TextUtils.isEmpty(info.getNew_pic_url())) {//默认
                holder.img_album_cover.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.bg_ugc_personal)).build());
        } else {
                holder.img_album_cover.setImageURI(Uri.parse(info.getNew_pic_url()));
        }
    }

    private class AlbumViewHolder {
        public SimpleDraweeView img_album_cover;
        public TextView tv_album_name;
        public TextView tv_album_subname;
        public LinearLayout creat_play_list_container;
    }

}
