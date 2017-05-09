/**
 * 
 */
package com.example.coursedesign.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.example.coursedesign.R;
import com.example.coursedesign.bean.User;
import com.example.coursedesign.utils.LogUtils;
import com.example.coursedesign.utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author yuanqiang
 *
 */
public class RegisterActivity extends BaseFragmentActivity {

	EditText username;
	EditText pwd;
	Button bt_register;
	private boolean progressShow;
	ProgressDialog pd ;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.register_layout);
		initToolbar();
		initMenu();
		Initview();
	}
	//	初始化toolbar
	private void initMenu() {
		//Menu中间字
		setUiTitle(getResources().getString(R.string.register));
		//Menu右边图标，及跳转的activity
		showMenu = false;
//		setMenuDrawable(R.drawable.icon_next);
//		setMenugoToClass(RegisterActivity.class);
		//Menu左边图标显示
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_back);
	}


	/**
	 Description:
	 Input: 
	 */
	private void Initview() {
		username = (EditText) findViewById(R.id.register_et_username);
		pwd = (EditText) findViewById(R.id.register_et_password);
		bt_register = (Button) findViewById(R.id.bt_register);
		bt_register.setOnClickListener(clickListener);

	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_register:
				if(username.length() <= 0){
					return;
				}
				if(pwd.length() <=0 ){
					return;
				}
				progressShow = true;
				pd = new ProgressDialog(RegisterActivity.this);
				pd.setCanceledOnTouchOutside(false);
				pd.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						progressShow = false;
					}
				});
				pd.setMessage(getString(R.string.register_ing));
				pd.show();
				
				register(username.getText().toString(),pwd.getText().toString());
				break;
			default:
				break;
			}
			
			
		}
		//注册
		private void register(String name, final String the_pwd) {
			BmobUser user = new BmobUser();
			user.setUsername(name);
			user.setEmail(name);
			user.setPassword(the_pwd);
			user.signUp(RegisterActivity.this, new SaveListener() {
				@Override
				public void onSuccess() {
					pd.dismiss();
					ToastUtils.toast(RegisterActivity.this, getResources().getString(R.string.register_success));
					username.setText("");
					pwd.setText("");
				}

				@Override
				public void onFailure(int i, String s) {
					pd.dismiss();
					ToastUtils.toast(RegisterActivity.this, getResources().getString(R.string.register_failure).toString()+s);
					LogUtils.i("RegisterActivity",s);
				}
			});
//            EMChatManager.getInstance().login(username.getText().toString(), pwd.getText().toString(), new EMCallBack() {
//                @Override
//                public void onSuccess() {
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
//                        	 pd.dismiss();
//                            Toast.makeText(getApplicationContext(), "登录失败", 0).show();
//                        }
//                    });
//                }
//            });
		}
	};
}
