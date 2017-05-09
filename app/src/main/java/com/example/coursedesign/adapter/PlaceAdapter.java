///**
// *
// */
//package com.example.coursedesign.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.coursedesign.R;
//import com.example.coursedesign.bean.Place;
//import com.example.coursedesign.utils.ImageLoaderHelper;
//
//import java.util.ArrayList;
//
///**
// * @author yuanqiang
// *
// */
//public class PlaceAdapter extends BaseAdapter {
//
//	private Context context;
//	private ArrayList<Place> list_place;
//
//	public PlaceAdapter(Context context, ArrayList<Place> list_place) {
//		super();
//		this.context = context;
//		this.list_place = list_place;
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return list_place.size();
//	}
//
//	/* (non-Javadoc)
//	 * @see android.widget.Adapter#getItem(int)
//	 */
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return list_place.get(position);
//	}
//
//	/* (non-Javadoc)
//	 * @see android.widget.Adapter#getItemId(int)
//	 */
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	/* (non-Javadoc)
//	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
//	 */
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//		if(convertView==null){
//			holder = new ViewHolder();
//			convertView = LayoutInflater.from(context).inflate(R.layout.hot_destination_item_place_item	, null);
//			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_place_name_hot_des_item_item);
//			holder.tv_info = (TextView) convertView.findViewById(R.id.tv_place_info_hot_des_item_item);
//			holder.im_pic = (ImageView) convertView.findViewById(R.id.im_hot_des_item_item);
//			convertView.setTag(holder);
//		}
//		else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//		holder.tv_name.setText(list_place.get(position).getName());
//		holder.tv_info.setText(list_place.get(position).getInfo());
//		ImageLoaderHelper.mImageLoader.displayImage(list_place.get(position).getUrl(),
//				holder.im_pic, ImageLoaderHelper.mOptions);
//
//		return convertView;
//	}
//	private static final class ViewHolder{
//		TextView tv_name;
//		TextView tv_info;
//		ImageView im_pic;
//	}
//
//}
