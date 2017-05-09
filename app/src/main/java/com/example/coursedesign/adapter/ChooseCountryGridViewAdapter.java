///**
// *
// */
//package com.example.coursedesign.adapter;
//
//import android.content.Context;
//import android.net.Uri;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.coursedesign.R;
//import com.example.coursedesign.bean.Country;
//import com.example.coursedesign.common.Constants;
//import com.example.coursedesign.utils.ImageLoaderHelper;
//import com.nostra13.universalimageloader.core.assist.ImageSize;
//
//import java.io.File;
//import java.util.ArrayList;
//
///**
// * @author yuanqiang
// *
// */
//public class ChooseCountryGridViewAdapter extends BaseAdapter {
//
//	private ArrayList<Country> country_list;
//	private Context context;
//
//	public ChooseCountryGridViewAdapter(ArrayList<Country> country_list,
//			Context context) {
//		super();
//		this.country_list = country_list;
//		this.context = context;
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return country_list.size();
//	}
//
//	/* (non-Javadoc)
//	 * @see android.widget.Adapter#getItem(int)
//	 */
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return country_list.get(position);
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
//		ViewHolder holder = null;
//		if(convertView == null){
//			holder = new ViewHolder();
//			convertView = LayoutInflater.from(context).inflate(R.layout.choose_country_gv_item_layout, null);
//			holder.country_pic = (ImageView) convertView.findViewById(R.id.choose_country_gv_item_iv);
//			holder.country_text = (TextView) convertView.findViewById(R.id.choose_country_gv_item_tv);
//			convertView.setTag(holder);
//		}
//		else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//		ImageLoaderHelper.mImageLoader.displayImage(country_list.get(position).getCountry_img(),
//				holder.country_pic, ImageLoaderHelper.mOptions);
//
////		holder.country_pic.setImageURI(Uri.fromFile(new File(country_list.get(position).getCountry_img())));
//
//		ImageSize size = new ImageSize((int)(Constants.screenWidth*0.3055), (int)(Constants.screenWidth*0.3055));
//
//
//		holder.country_text.setText(country_list.get(position).getCountry_name());
//
//		return convertView;
//	}
//	private static final class ViewHolder{
//		ImageView country_pic;
//		TextView country_text;
//	}
//
//}
