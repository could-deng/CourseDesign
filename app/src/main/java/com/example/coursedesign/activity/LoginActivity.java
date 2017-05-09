/**
 * 
 */
package com.example.coursedesign.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.example.coursedesign.CourseDesignAPP;
import com.example.coursedesign.R;
import com.example.coursedesign.common.Constants;
import com.example.coursedesign.service.LocationService;
import com.example.coursedesign.utils.LoginHelper;
import com.example.coursedesign.utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author yuanqiang
 *
 */
public class LoginActivity extends BaseFragmentActivity {
	EditText username;
	EditText pwd;
	Button bt_login;
	private boolean progressShow;
	ProgressDialog pd ;
	
	ImageView main_header_right_btn;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.login_layout);
		initToolbar();
		initMenu();
		Initview();
		setEditTextDraw();
	}

//	初始化toolbar
	private void initMenu() {
		//Menu中间字
		setUiTitle(getResources().getString(R.string.login));
		//Menu右边图标，及跳转的activity
		showMenu = true;
		setMenuDrawable(R.drawable.icon_next);
		setMenugoToClass(RegisterActivity.class);
		//Menu左边图标显示
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_back);
	}

	private void setEditTextDraw() {
		Drawable drawMobile = getResources().getDrawable(R.drawable.et_mobile);
		drawMobile.setBounds(0, 0, 30, 30);// 第一0是距左边距离，第二0是距上边距离，40分别是长宽
		username.setCompoundDrawables(drawMobile, null, null, null);// 只放左边
		Drawable drawPwd = getResources().getDrawable(R.drawable.et_password);
		drawPwd.setBounds(0, 0, 30, 30);// 第一0是距左边距离，第二0是距上边距离，40分别是长宽
		pwd.setCompoundDrawables(drawPwd, null, null, null);// 只放左边
	}
	
	/**
	 Description:初始化view
	 Input: 
	 */
	private void Initview() {
		username = (EditText) findViewById(R.id.login_et_username);
		pwd = (EditText) findViewById(R.id.login_et_password);
		bt_login = (Button) findViewById(R.id.bt_login);
		bt_login.setOnClickListener(clickListener);
		
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_login:
				if(username.length() <= 0){
					return;
				}
				if(pwd.length() <=0 ){
					return;
				}
				progressShow = true;
				pd = new ProgressDialog(LoginActivity.this);
				pd.setCanceledOnTouchOutside(false);
				pd.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						progressShow = false;
					}
				});
				pd.setMessage(getString(R.string.login_ing));
				pd.show();
				login();
				break;
			default:
				break;
			}
			
			
		}
		//登录
		private void login() {
			BmobUser bu = new BmobUser();
			bu.setUsername(username.getText().toString());
			bu.setPassword(pwd.getText().toString());
			bu.login(LoginActivity.this, new SaveListener() {
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					ToastUtils.toast(LoginActivity.this,getResources().getString(R.string.login_success).toString());
					LoginHelper.saveLoginInfo(LoginActivity.this, username.getText().toString(), pwd.getText().toString());
					CourseDesignAPP.getInstance().setLOgin(true);//标示已登录
					startActivityFromThisActivity(MainActivity.class);
					finish();
				}
				@Override
				public void onFailure(int code, String msg) {
					// TODO Auto-generated method stub
					ToastUtils.toast(LoginActivity.this, getResources().getString(R.string.login_failure).toString()+msg);
				}
			});
//            EMChatManager.getInstance().login(username.getText().toString(), pwd.getText().toString(), new EMCallBack() {
//                @Override
//                public void onSuccess() {
//                	LoginActivity.this.startService(new Intent(LoginActivity.this, LocationService.class));
//
//                	startActivityFromThisActivity(MainActivity.class);
//                    finish();
//                    pd.dismiss();
//                }
//                @Override
//                public void onProgress(int progress, String status) {
//                }
//                @Override
//                public void onError(int code, String error) {
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                        	pd.dismiss();
//                            Toast.makeText(LoginActivity.this, "登录失败", 0).show();
//                        }
//                    });
//                }
//            });
		}
	};

}
