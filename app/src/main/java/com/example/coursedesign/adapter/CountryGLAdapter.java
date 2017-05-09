package com.example.coursedesign.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.activity.HotDesitinationActivity;
import com.example.coursedesign.bean.Country;
import com.example.coursedesign.bean.continents;
import com.example.coursedesign.controller.AsyncImageLoader;
import com.example.coursedesign.utils.ImageLoaderHelper;
import com.example.coursedesign.utils.ThreadManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanqiang on 2016/3/18.
 */
public class CountryGLAdapter extends BaseAdapter {
    private HotDesitinationActivity context;
    private List<Country> country_ls;
    private LayoutInflater mInflater;

    public CountryGLAdapter( HotDesitinationActivity context) {
        super();
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    public List<Country> getCountry_ls() {
        return country_ls;
    }

    public void setCountry_ls(List<Country> country_ls) {
        if ((this.country_ls != null) && (country_ls != this.country_ls)) this.country_ls.clear();
        this.country_ls = country_ls;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if ((getCountry_ls() == null) || (getCountry_ls().size() == 0)) {
            return 0;
        } else {
            return getCountry_ls().size();
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
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.country_gl_item_layout, null);
            holder.country_gv_item_iv = (SimpleDraweeView) convertView.findViewById(R.id.country_gv_item_iv);
            holder.country_gv_item_tv  =(TextView) convertView.findViewById(R.id.country_gv_item_tv);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        String pic_url = getCountry_ls().get(position).getCountry_img();
        if (TextUtils.isEmpty(pic_url)) {//默认
            holder.country_gv_item_iv.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.loading_pic)).build());
        } else {
            Bitmap bm = compressBitmap(null, null, context, Uri.parse(pic_url), 1, false);
//            bm = ThumbnailUtils.extractThumbnail(bm,200,200);
//            holder.country_gv_item_iv.setImageBitmap(bm);
            holder.country_gv_item_iv.setImageURI(Uri.parse(pic_url));
        }
        holder.country_gv_item_iv.setDrawingCacheEnabled(true);
        holder.country_gv_item_iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.country_gv_item_tv.setText(getCountry_ls().get(position).getCountry_name());

        return convertView;
    }
    private static class ViewHolder{
        SimpleDraweeView country_gv_item_iv;
        TextView country_gv_item_tv;
    }
    /**图片压缩处理，size参数为压缩比，比如size为2，则压缩为1/4**/
    private Bitmap compressBitmap(String path, byte[] data, Context context, Uri uri, int size, boolean width) {
        BitmapFactory.Options options = null;
        if (size > 0) {
            BitmapFactory.Options info = new BitmapFactory.Options();
/**如果设置true的时候，decode时候Bitmap返回的为数据将空*/
            info.inJustDecodeBounds = false;
            decodeBitmap(path, data, context, uri, info);
            int dim = info.outWidth;
            if (!width) dim = Math.max(dim, info.outHeight);
            options = new BitmapFactory.Options();
/**把图片宽高读取放在Options里*/
            options.inSampleSize = size;
        }
        Bitmap bm = null;
        try {
            bm = decodeBitmap(path, data, context, uri, options);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }
    /**把byte数据解析成图片*/
    private Bitmap decodeBitmap(String path, byte[] data, Context context, Uri uri, BitmapFactory.Options options) {
        Bitmap result = null;
        if (path != null) {
            result = BitmapFactory.decodeFile(path, options);
        }
        else if (data != null) {
            result = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        }
        else if (uri != null) {
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;
            try {
                inputStream = cr.openInputStream(uri);
                result = BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
