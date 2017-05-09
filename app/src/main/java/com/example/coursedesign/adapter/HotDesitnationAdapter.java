///**
// *
// */
//package com.example.coursedesign.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.coursedesign.R;
//import com.example.coursedesign.bean.HotPlace;
//import com.example.coursedesign.widget.ExpandGridView;
//
//import java.util.ArrayList;
//
///**
// * @author yuanqiang
// *
// */
//public class HotDesitnationAdapter extends BaseAdapter {
//	private Context context;
//	private ArrayList<HotPlace> hot_place_list;
//
//	public HotDesitnationAdapter(Context context,
//			ArrayList<HotPlace> hot_place_list) {
//		super();
//		this.context = context;
//		this.hot_place_list = hot_place_list;
//	}
//
//	@Override
//	public int getCount() {
//		return hot_place_list.size();
//	}
//
//	/* (non-Javadoc)
//	 * @see android.widget.Adapter#getItem(int)
//	 */
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return hot_place_list.get(position);
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
//		ViewHolder holder=null;
//		if(convertView==null){
//			holder = new ViewHolder();
//			convertView = LayoutInflater.from(context).inflate(R.layout.hot_destination_item, null);
//			holder.tv_title = (TextView) convertView.findViewById(R.id.hot_des_item_title_tv);
//			holder.tv_tip = (TextView) convertView.findViewById(R.id.hot_des_item_tips);
//			holder.gv_pic = (ExpandGridView) convertView.findViewById(R.id.gv_hot_des_item_pic);
//			convertView.setTag(holder);
//		}
//		else{
//			holder=(ViewHolder) convertView.getTag();
//		}
//		final HotPlace itemHotPlace = hot_place_list.get(position);
//		holder.tv_title.setText(itemHotPlace.getTitle());
//		holder.tv_tip.setText(itemHotPlace.getTips());
//		holder.gv_pic.setAdapter(new PlaceAdapter(context, itemHotPlace.getList_place()));
//		holder.gv_pic.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//// 				Intent intent = new Intent(context,DesitinationItemActivity.class);
////				Bundle mBundle = new Bundle();
////		        mBundle.putSerializable(Constants.PLACE_TOPLAY, itemHotPlace.getList_place().get(position));
//////				intent.putExtra(Constants.PLACE_TOPLAY, itemHotPlace.getList_place().get(position));
////		        intent.putExtras(mBundle);
////				context.startActivity(intent);
//			}
//		});
//		return convertView;
//	}
//	private static class ViewHolder {
//		TextView tv_title;
//		TextView tv_tip;
//		ExpandGridView gv_pic;
//	}
//
//}
