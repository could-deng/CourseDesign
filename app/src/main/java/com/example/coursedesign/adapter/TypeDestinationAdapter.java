/**
 * 
 */
package com.example.coursedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.bean.PicNew;
import com.example.coursedesign.bean.continents;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanqiang
 *
 */
public class TypeDestinationAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private List<continents> list_place;
	
	public TypeDestinationAdapter(Context context) {
		super();
		this.context = context;
		mInflater = LayoutInflater.from(context);
		list_place = null;
	}

	public List<continents> getList_place() {
		return list_place;
	}

	public void setList(List<continents> list) {
		if ((this.list_place != null) && (list != this.list_place)) this.list_place.clear();
		this.list_place = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if ((getList_place() == null) || (getList_place().size() == 0)) {
			return 0;
		} else {
			return getList_place().size();
		}
	}

	//不用，因为错，list_place可能为null。
	@Override
	public Object getItem(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.type_des_lv_item, null);
			holder.tv_type = (TextView) convertView.findViewById(R.id.type_des_lv_item_tv);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_type.setText(getList_place().get(position).getContinents_name());
		
		return convertView;
	}
	private static final class ViewHolder{
		TextView tv_type;
	}

}
