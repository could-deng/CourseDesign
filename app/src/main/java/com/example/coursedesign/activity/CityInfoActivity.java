package com.example.coursedesign.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coursedesign.R;
import com.example.coursedesign.bean.City;
import com.example.coursedesign.utils.ImageLoaderHelper;
import com.example.coursedesign.utils.ToastUtils;

import java.io.File;

/**
 * Created by yuanqiang on 2016/4/2.
 */
public class CityInfoActivity extends BaseFragmentActivity {
    CheckBox cb_if_show_all_city_info;
    TextView tv_city_info;
    ImageView iv_city_pic;
    TextView tv_city_people_gone;
    TextView tv_city_name;
    City city;
    RelativeLayout rl_cityinfo_selected;
    ImageView city_info_cancel;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_cityinfo);
//        initToolbar();
//        initMenu();
        city = (City) getIntent().getSerializableExtra("city");
        if(city==null){
            return;
        }
        init();
    }

    private void initMenu() {
        //Menu中间字
//        setUiTitle(getResources().getString(R.string.title_activity_des));
        //Menu右边图标，及跳转的activity
//		showMenu = true;
//		setMenu(R.drawable.icon_next, ChooseDesitinationActivity.class);

        //Menu左边图标显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
    }

    private void RequestData(){

    }
    private void init() {
        tv_city_name = (TextView) findViewById(R.id.tv_city_name);
        tv_city_name.setText(city.getCity_name());

        tv_city_people_gone = (TextView) findViewById(R.id.tv_city_people_gone);
        tv_city_people_gone.append(city.getPeople_gone().toString());

        iv_city_pic = (ImageView) findViewById(R.id.iv_city_pic);
//        ImageLoaderHelper.mImageLoader.displayImage(city.getCity_pic_url(),
//                iv_city_pic, ImageLoaderHelper.mOptions);
        if (TextUtils.isEmpty(city.getCity_pic_url())) {//默认
            iv_city_pic.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(R.drawable.loading_pic)).build());
        } else {
            iv_city_pic.setImageURI(Uri.parse(city.getCity_pic_url()));
        }
        tv_city_info = (TextView) findViewById(R.id.tv_city_info);
        tv_city_info.setText(city.getCity_info());
        cb_if_show_all_city_info = (CheckBox) findViewById(R.id.cb_if_show_all_city_info);
        cb_if_show_all_city_info.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){//展开
//                    tv_city_info.setEllipsize(null);

                    tv_city_info.setMaxLines(Integer.MAX_VALUE);
                    tv_city_info.requestLayout();
                }
                else{//收缩
//                    tv_city_info.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                    tv_city_info.setMaxLines(3);
                    tv_city_info.requestLayout();
                }
            }
        });
        rl_cityinfo_selected = (RelativeLayout) findViewById(R.id.rl_cityinfo_selected);
    }

    public void onclick(View view){
        switch (view.getId()){
            case R.id.rl_cityinfo_selected:
                Intent intent=new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("select_city",city);
                intent.putExtras(bundle);
                ToastUtils.toast(CityInfoActivity.this,"成功添加到行程中");
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.city_info_cancel:
                finish();
                break;
        }

    }

}
