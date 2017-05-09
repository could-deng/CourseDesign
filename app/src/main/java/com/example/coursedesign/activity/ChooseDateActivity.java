/**
 * 
 */
package com.example.coursedesign.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursedesign.R;
import com.example.coursedesign.widget.CalendarView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yuanqiang
 *
 */
public class ChooseDateActivity extends BaseFragmentActivity {

	private CalendarView calendar;
	private ImageButton calendarLeft;
	private TextView calendarCenter;
	private ImageButton calendarRight;
	private SimpleDateFormat format;

	private long date_select;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.choose_date_layout);
		initToolbar();
		initMenu();
		init();
		format = new SimpleDateFormat("yyyy-MM-dd");
		//获取日历控件对象
		calendar = (CalendarView)findViewById(R.id.calendar);
		calendar.setSelectMore(false); //单选

		date_select = calendar.getToday().getTime();
		canNext();//默认时获取

		calendarLeft = (ImageButton)findViewById(R.id.calendarLeft);
		calendarCenter = (TextView)findViewById(R.id.calendarCenter);
		calendarRight = (ImageButton)findViewById(R.id.calendarRight);
		try {
			//设置日历日期
			Date date = format.parse("2015-01-01");
			calendar.setCalendarData(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
		String[] ya = calendar.getYearAndmonth().split("-");
		calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
		calendarLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//点击上一月 同样返回年月
				String leftYearAndmonth = calendar.clickLeftMonth();
				String[] ya = leftYearAndmonth.split("-");
				calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
			}
		});

		calendarRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//点击下一月
				String rightYearAndmonth = calendar.clickRightMonth();
				String[] ya = rightYearAndmonth.split("-");
				calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
			}
		});

		//设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
		calendar.setOnItemClickListener(new CalendarView.OnItemClickListener() {

			@Override
			public void OnItemClick(Date selectedStartDate,
									Date selectedEndDate, Date downDate) {
				if(calendar.isSelectMore()){
					Toast.makeText(getApplicationContext(), format.format(selectedStartDate) + "到" + format.format(selectedEndDate), Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), format.format(downDate), Toast.LENGTH_SHORT).show();
					date_select = downDate.getTime();
					canNext();
				}
			}
		});

	}

	private void canNext(){
		showMenu = true;
		setMenuDrawable(R.drawable.icon_next);
		setMenugoToClass(HotDesitinationActivity.class);
		Bundle bundle = new Bundle();
		bundle.putLong("date", date_select);//日期时间戳
		setBundle_next(bundle);
	}


	private void initMenu() {
		//Menu中间字
		setUiTitle(getResources().getString(R.string.titile_activity_date));
		//Menu右边图标，及跳转的activity
		showMenu = true;
		setMenuDrawable(R.drawable.icon_next);
		setMenugoToClass(HotDesitinationActivity.class);
		//Menu左边图标显示
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_selector);

	}

	private void init() {

	}
}
