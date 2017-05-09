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
//import android.widget.TextView;
//
//import com.example.coursedesign.R;
//import com.example.coursedesign.bean.Desitination_play_item;
//import com.example.coursedesign.utils.ImageLoaderHelper;
//import com.example.coursedesign.widget.CircularImage;
//
//import java.util.ArrayList;
//
///**
// * @author yuanqiang
// *
// */
//public class DesitinationPlayAdapter extends BaseAdapter {
//
//	private Context context;
//	private ArrayList<Desitination_play_item> des_play_ls;
//
//	public DesitinationPlayAdapter(Context context,
//			ArrayList<Desitination_play_item> des_play_ls) {
//		super();
//		this.context = context;
//		this.des_play_ls = des_play_ls;
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return des_play_ls.size();
//	}
//
//	/* (non-Javadoc)
//	 * @see android.widget.Adapter#getItem(int)
//	 */
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return des_play_ls.get(position);
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
//		if(convertView==null){
//			holder = new ViewHolder();
//			convertView = LayoutInflater.from(context).inflate(R.layout.desitination_item_gv_item, null);
//			holder.iv_play_icon = (CircularImage) convertView.findViewById(R.id.desitination_play_item_iv);
//			holder.tv_play_info  =(TextView) convertView.findViewById(R.id.desitination_play_item_tv);
//			convertView.setTag(holder);
//		}
//		else{
//			holder = (ViewHolder) convertView.getTag();
//		}
//		ImageLoaderHelper.mImageLoader.displayImage("http://f.igeekery.com//1001/b56173dede58422eaf6c74aa8f0b9a80.gif",
//				holder.iv_play_icon, ImageLoaderHelper.mOptions);
//		holder.tv_play_info.setText(des_play_ls.get(position).getPlay_info());
//
//		return convertView;
//	}
//	private static class ViewHolder{
//		CircularImage iv_play_icon;
//		TextView tv_play_info;
//	}
//
//}
