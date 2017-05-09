/**
 * 
 */
package com.example.coursedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.example.coursedesign.CourseDesignAPP;
import com.example.coursedesign.R;
import com.example.coursedesign.service.LocationService;
import com.example.coursedesign.utils.LoginHelper;
import com.example.coursedesign.utils.ToastUtils;

import cn.bmob.v3.BmobUser;

/**
 * @author yuanqiang
 *
 */
public class SettingActivity extends BaseFragmentActivity {
	Button bt_logout;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.setting_layout);
		initToolbar();
		initMenu();
		Init();
	}

	private void initMenu() {
		//Menu中间字
		setUiTitle(getResources().getString(R.string.setting));
		//Menu右边图标，及跳转的activity
//		showMenu = true;
//		setMenu(R.drawable.icon_next, ChooseDesitinationActivity.class);

		//Menu左边图标显示
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_back);
	}

	/**
	 Description:
	 Input: 
	 */
	private void Init() {
		bt_logout = (Button) findViewById(R.id.bt_logout);
		bt_logout.setOnClickListener(clickListener);
		Button ttest = (Button) findViewById(R.id.ttest);
		ttest.setOnClickListener(clickListener);
		
	}
	OnClickListener clickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_logout:
				if (CourseDesignAPP.getInstance().isLOgin()) {
					ToastUtils.toast(SettingActivity.this, getResources().getString(R.string.logout_success).toString());
					BmobUser.logOut(getApplicationContext());   //bmob清除缓存用户对象
					LoginHelper.clearLoginInfo(SettingActivity.this);
					CourseDesignAPP.getInstance().setLOgin(false);//application中将判断是否登录唯一标示设为否
					startActivityFromThisActivity(MainActivity.class);
					finish();
				}
				else{
					ToastUtils.toast(SettingActivity.this, getResources().getString(R.string.have_not_login).toString());
				}
				break;
			default:
				break;
			}
		}
		
	};
}
