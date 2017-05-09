/**
 * 
 */
package com.example.coursedesign.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.activity.HotDesitinationActivity;
import com.example.coursedesign.bean.City;
import com.example.coursedesign.common.Constants;
import com.example.coursedesign.utils.ImageLoaderHelper;
import com.example.coursedesign.widget.pulltorefresh.RefreshLayout;
import com.example.coursedesign.widget.pulltorefresh.SwipeRefreshDirection;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.List;

/**
 * @author yuanqiang
 *
 */
public class ChooseCityListViewAdapter extends BaseAdapter {

	private List<City> city_list;
	private Context context;
	
	public ChooseCityListViewAdapter( Context context) {
		super();
		this.context = context;
	}

	public List<City> getCity_list() {
		return city_list;
	}

	public void setCity_list(List<City> city_list) {
		if ((this.city_list != null) && (city_list != this.city_list)) this.city_list.clear();
		this.city_list = city_list;
		notifyDataSetChanged();
	}
	public void updateCity(City city,boolean ischecked){
		for(int i =0;i<getCity_list().size();i++){
			if((getCity_list().get(i).getCity_id()).equals(city.getCity_id())){
				getCity_list().get(i).setSelected(ischecked);
				break;
			}
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if ((getCity_list() == null) || (getCity_list().size() == 0)) {
			return 0;
		} else {
			return getCity_list().size();
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.choose_city_lv_item_layout, null);

			holder.city_pic = (ImageView) convertView.findViewById(R.id.choose_city_lv_item_iv);
			holder.city_text = (TextView) convertView.findViewById(R.id.choose_city_lv_item_ll_tv_name);
			holder.choose_city_lv_item_ll_tv_people_gone = (TextView) convertView.findViewById(R.id.choose_city_lv_item_ll_tv_people_gone);
			holder.rl_city_selected = (RelativeLayout) convertView.findViewById(R.id.rl_city_selected);
			holder.choose_city_lv_item_iv_add = (CheckBox) convertView.findViewById(R.id.choose_city_lv_item_iv_add);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(city_list.get(position).isSelected()){
			holder.rl_city_selected.setVisibility(View.VISIBLE);
		}
		else{
			holder.rl_city_selected.setVisibility(View.INVISIBLE);
		}
		if (TextUtils.isEmpty(getCity_list().get(position).getCity_pic_url())) {//默认
			holder.city_pic.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.loading_pic)).build());
		} else {
			holder.city_pic.setImageURI(Uri.parse(getCity_list().get(position).getCity_pic_url()));
		}
//		ImageSize size = new ImageSize((int)(Constants.screenWidth*0.3055), (int)(Constants.screenWidth*0.3055));
		holder.city_text.setText(getCity_list().get(position).getCity_name());
		holder.choose_city_lv_item_ll_tv_people_gone.setText(getCity_list().get(position).getPeople_gone().toString());
		holder.choose_city_lv_item_iv_add.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//数据库操作更新和缓存的某一条更新
				((HotDesitinationActivity)context).updateCitySelected(getCity_list().get(position),isChecked);
				updateCity(getCity_list().get(position),isChecked);
				//Activity上界面更新（horizontalListview）
				((HotDesitinationActivity)context).uploadSelectedCity(getCity_list().get(position),isChecked);
			}
		});
		return convertView;
	}
	private static final class ViewHolder{
		RelativeLayout rl_city_selected;
		ImageView city_pic;
		TextView city_text;
		TextView choose_city_lv_item_ll_tv_people_gone;
		CheckBox choose_city_lv_item_iv_add;
	}
}
