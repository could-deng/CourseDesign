//package com.example.coursedesign.adapter;
//
//import android.content.Context;
//import android.text.Layout;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.coursedesign.R;
//import com.example.coursedesign.utils.ImageLoaderHelper;
//import com.example.coursedesign.widget.CircularImage;
//
//import org.w3c.dom.Text;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2016/4/9.
// */
//public class DragSortListViewAdapter extends ArrayAdapter {
//
//    private List<String> city_list ;
//    private List<String> city_stay_days;
//
//    private LayoutInflater mInflater;
//    public DragSortListViewAdapter(Context context, int resource,List<String> city_list,List<String>city_stay_days) {
//        super(context, resource);
//        mInflater = LayoutInflater.from(context);
//        this.city_list = city_list;
//        this.city_stay_days = city_stay_days;
//    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return createViewFromResource(mInflater, position, convertView, parent);
//    }
//
//    private View createViewFromResource(LayoutInflater inflater, int position, View convertView,
//                                        ViewGroup parent) {
//        ViewHolder holder = null;
//        if(convertView==null){
//            holder = new ViewHolder();
//            convertView = inflater.inflate(R.layout.list_item_checkable, null);
//            holder.tv_city_name = (TextView) convertView.findViewById(R.id.text);
//            holder.tv_city_stay_days  =(TextView) convertView.findViewById(R.id.tv_city_stay_days);
//            convertView.setTag(holder);
//        }
//        else{
//            holder = (ViewHolder) convertView.getTag();
//        }
//        holder.tv_city_name.setText(city_list.get(position));
//        holder.tv_city_stay_days.setText(city_stay_days.get(position));
//        return convertView;
//    }
//    private static class ViewHolder{
//        TextView tv_city_name;
//        TextView tv_city_stay_days;
//    }
//}
