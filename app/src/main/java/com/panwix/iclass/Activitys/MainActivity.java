package com.panwix.iclass.Activitys;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.panwix.iclass.R;
import com.panwix.iclass.database.DBhelper;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements Runnable, View.OnClickListener, AdapterView.OnItemClickListener{

	// 侧滑栏
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayList<Integer> menuList;
	private SimpleAdapter adapter;

	// ActionBar
	private ActionBar actionBar = null;

	// 日期
	public TextView date;

	// 第几周
	public TextView week;
	int weekNum;

	// 时间
	public TextView time;

	// 学期开始日期
	public String sDate = "2015-07-01";
	public Date startDate;

	// 周一的课；
	public TextView c11;
	public TextView c12;
	public TextView c13;
	public TextView c14;
	public TextView c15;
	public TextView c16;
	public TextView c17;
	public TextView c18;
	public TextView c19;
	public TextView c110;
	public TextView c111;

	// 周二的课
	public TextView c21;
	public TextView c22;
	public TextView c23;
	public TextView c24;
	public TextView c25;
	public TextView c26;
	public TextView c27;
	public TextView c28;
	public TextView c29;
	public TextView c210;
	public TextView c211;

	// 周三的课
	public TextView c31;
	public TextView c32;
	public TextView c33;
	public TextView c34;
	public TextView c35;
	public TextView c36;
	public TextView c37;
	public TextView c38;
	public TextView c39;
	public TextView c310;
	public TextView c311;

	// 周四的课
	public TextView c41;
	public TextView c42;
	public TextView c43;
	public TextView c44;
	public TextView c45;
	public TextView c46;
	public TextView c47;
	public TextView c48;
	public TextView c49;
	public TextView c410;
	public TextView c411;

	// 周五的课
	public TextView c51;
	public TextView c52;
	public TextView c53;
	public TextView c54;
	public TextView c55;
	public TextView c56;
	public TextView c57;
	public TextView c58;
	public TextView c59;
	public TextView c510;
	public TextView c511;

	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		// 有米广告
		AdManager.getInstance(this).init("46babc13f03ce747", "abc60caa0489856f", false);
		SpotManager.getInstance(this).loadSpotAds();
		SpotManager.getInstance(this).setSpotOrientation(
				SpotManager.ORIENTATION_PORTRAIT);
		SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_ADVANCE);
		// 隐藏状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
				, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// 设置DrawerLayout
		setDrawerList();

		// 自定义ActionBar
		setActionBar();

//		// 开启ActionBar上APP ICON的功能
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);

		// 初始化界面
		initAcivity();
		SpotManager.getInstance(this).showSpotAds(this);
		// 动态设置时间日期
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what){
					case 100:
						time.setText((String)msg.obj);
						break;
					case 101:
						date.setText((String)msg.obj);
						break;
				}

			}
		};
		new Thread(this).start();
	}

	// 获取当前日期
	public String getDate(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		return year + "-" + (month+1) + "-" + date;
	}
	// 获取当前时间
	public String getTime(){
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		String hourS;
		String minuteS;
		if(hour<10)
			hourS = "0" + hour;
		else
			hourS = "" + hour;
		if(minute<10)
			minuteS = "0" + minute;
		else
			minuteS = "" + minute;
		return hourS + ":" + minuteS;
	}

	// 取得现在的周数
	public int getWeedNo(Date dateS, Date dateD) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		dateS=sdf.parse(sdf.format(dateS));
		dateD=sdf.parse(sdf.format(dateD));
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateS);
		long time1 = cal.getTimeInMillis();
		int week = cal.get(Calendar.DAY_OF_WEEK);
		cal.setTime(dateD);
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);
		if(week == 1)
			week = 8;
		int weekNo = (Integer.parseInt(String.valueOf(between_days)) + week - 1)/7;
		int weekNo2 = (Integer.parseInt(String.valueOf(between_days)) + week - 1)%7;
		if(weekNo2 != 0)
			weekNo++;
		return weekNo;
	}

    // 从数据库中获取课程
    public String getClass(String week, String time, String weekNum){
        DBhelper helper = new DBhelper(getBaseContext());
        String sql = "select * from Class where week = ? and classTime =?";
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{week, time});
        String result = "";
        // 课程开始周
        String weekStart="0";
        // 课程结束周
        String weekEnd="0";
        int i = cursor.getCount();
        if(i>0){
            cursor.moveToFirst();
            result = cursor.getString(cursor.getColumnIndex("class"));
            weekStart = cursor.getString(cursor.getColumnIndex("classStart"));
            weekEnd = cursor.getString(cursor.getColumnIndex("classEnd"));
            while(--i>0 && !(Integer.parseInt(weekStart) <= Integer.parseInt(weekNum))
                    && !(Integer.parseInt(weekEnd)>=Integer.parseInt(weekNum))){
                cursor.moveToNext();
                result = cursor.getString(cursor.getColumnIndex("class"));
                weekStart = cursor.getString(cursor.getColumnIndex("classStart"));
                weekEnd = cursor.getString(cursor.getColumnIndex("classEnd"));
            }
        }
        if(Integer.parseInt(weekStart) <= Integer.parseInt(weekNum)
                && Integer.parseInt(weekEnd)>=Integer.parseInt(weekNum)){
            return result;
        }else{
            return "";
        }
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true){
				String time = getTime();
				String date = getDate();
				handler.sendMessage(handler.obtainMessage(100,time));
				handler.sendMessage(handler.obtainMessage(101,date));
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void goToActivity(String weekNo, String week, String time){
		Intent intent = new Intent();
		intent.setClass(this,showActivity.class);
		intent.putExtra("weekNo", weekNo);
		intent.putExtra("week", week);
		intent.putExtra("time", time);
		startActivity(intent);
	}

	public void onClick(View view){
		String weekNumS;
		switch (view.getId()){
			case R.id.c11:
				if(c11.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "1", "1");
				}
				break;
			case R.id.c12:
				if(c12.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "1", "2");
				}
				break;
			case R.id.c13:
				if(c13.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "1", "3");
				}
				break;
			case R.id.c14:
				if(c14.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "1", "4");
				}
				break;
			case R.id.c15:
				if(c15.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "1", "5");
				}
				break;
			case R.id.c16:
				if(c16.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "1", "6");
				}
				break;
			case R.id.c17:
				if(c17.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "1", "7");
				}
				break;
			case R.id.c18:
				if(c18.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "1", "8");
				}
				break;
			case R.id.c19:
				if(c19.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "1", "9");
				}
				break;
			case R.id.c110:
				if(c110.getText() != "") {
				weekNumS = weekNum + "";
				goToActivity(weekNumS, "1", "10");
			}
				break;
			case R.id.c111:
				if(c111.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "1", "11");
				}
				break;
			case R.id.c21:
				if(c21.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "2", "1");
				}
				break;
			case R.id.c22:
				if(c22.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "2", "2");
				}
				break;
			case R.id.c23:
				if(c23.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "2", "3");
				}
				break;
			case R.id.c24:
				if(c24.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "2", "4");
				}
				break;
			case R.id.c25:
				if(c25.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "2", "5");
				}
				break;
			case R.id.c26:
				if(c26.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "2", "6");
				}
				break;
			case R.id.c27:
				if(c27.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "2", "7");
				}
				break;
			case R.id.c28:
				if(c28.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "2", "8");
				}
				break;
			case R.id.c29:
				if(c29.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "2", "9");
				}
				break;
			case R.id.c210:
				if(c210.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "2", "10");
				}
				break;
			case R.id.c211:
				if(c211.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "2", "11");
				}
				break;
			case R.id.c31:
				if(c31.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "3", "1");
				}
				break;
			case R.id.c32:
				if(c32.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "3", "2");
				}
				break;
			case R.id.c33:
				if(c33.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "3", "3");
				}
				break;
			case R.id.c34:
				if(c34.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "3", "4");
				}
				break;
			case R.id.c35:
				if(c35.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "3", "5");
				}
				break;
			case R.id.c36:
				if(c36.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "3", "6");
				}
				break;
			case R.id.c37:
				if(c37.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "3", "7");
				}
				break;
			case R.id.c38:
				if(c38.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "3", "8");
				}
				break;
			case R.id.c39:
				if(c39.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "3", "9");
				}
				break;
			case R.id.c310:
				if(c310.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "3", "10");
				}
				break;
			case R.id.c311:
				if(c311.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "3", "11");
				}
				break;
			case R.id.c41:
				if(c41.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "4", "1");
				}
				break;
			case R.id.c42:
				if(c42.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "4", "2");
				}
				break;
			case R.id.c43:
				if(c43.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "4", "3");
				}
				break;
			case R.id.c44:
				if(c44.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "4", "4");
				}
				break;
			case R.id.c45:
				if(c45.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "4", "5");
				}
				break;
			case R.id.c46:
				if(c46.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "4", "6");
				}
				break;
			case R.id.c47:
				if(c47.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "4", "7");
				}
				break;
			case R.id.c48:
				if(c48.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "4", "8");
				}
				break;
			case R.id.c49:
				if(c49.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "4", "9");
				}
				break;
			case R.id.c410:
				if(c410.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "4", "10");
				}
				break;
			case R.id.c411:
				if(c411.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "4", "11");
				}
				break;
			case R.id.c51:
				if(c51.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "5", "1");
				}
				break;
			case R.id.c52:
				if(c52.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "5", "2");
				}
				break;
			case R.id.c53:
				if(c53.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "5", "3");
				}
				break;
			case R.id.c54:
				if(c54.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "5", "4");
				}
				break;
			case R.id.c55:
				if(c55.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "5", "5");
				}
				break;
			case R.id.c56:
				if(c56.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "5", "6");
				}
				break;
			case R.id.c57:
				if(c57.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "5", "7");
				}
				break;
			case R.id.c58:
				if(c58.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "5", "8");
				}
				break;
			case R.id.c59:
				if(c59.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "5", "9");
				}
				break;
			case R.id.c510:
				if(c510.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "5", "10");
				}
				break;
			case R.id.c511:
				if(c511.getText() != "") {
					weekNumS = weekNum + "";
					goToActivity(weekNumS, "5", "11");
				}
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position){
			//  跳转到添加课程页面
			case 0:
				Intent intent0 = new Intent();
				intent0.setClass(MainActivity.this, addActivity.class);
				startActivity(intent0);
				finish();
				break;
			// 跳转到删除课程页面
			case 1:
				Intent intent1 = new Intent();
				intent1.setClass(MainActivity.this, deleteActivity.class);
				startActivity(intent1);
				finish();
				break;
			// 查询课程
			case 2:
				Intent intent2 = new Intent();
				intent2.setClass(MainActivity.this, queryActivity.class);
				startActivity(intent2);
				finish();
				break;
			// 设置课程
			case 3:
				Intent intent3 = new Intent();
				intent3.setClass(MainActivity.this, settingActivity.class);
				startActivityForResult(intent3, R.layout.setting);
				break;
			// 退出应用程序
			case 4:
				finish();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
			case R.layout.home_layout:
				Bundle b=data.getExtras(); //data为B中回传的Intent
				sDate=b.getString("date");//str即为回传的值
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if(sDate == null){
						sDate="2015-9-1";
					}
					Date d1=sdf.parse(sDate);
					Date d2=new Date();
					weekNum = getWeedNo(d1,d2);
					String numStr = "第" + weekNum + "周";
					week.setText(numStr);
				} catch (ParseException e){
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
	}

	// DrawerLayout的具体实现
	private void setDrawerList(){
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		mDrawerList = (ListView)findViewById(R.id.left_drawer);

		ArrayList data = new ArrayList();
		HashMap map = new HashMap();
		map.put("itemImage", R.drawable.add);
		data.add(map);

		map = new HashMap();
		map.put("itemImage", R.drawable.delete);
		data.add(map);

		map = new HashMap();
		map.put("itemImage", R.drawable.search);
		data.add(map);

		map = new HashMap();
		map.put("itemImage", R.drawable.setting);
		data.add(map);

		map = new HashMap();
		map.put("itemImage", R.drawable.exit);
		data.add(map);


		adapter = new SimpleAdapter(this, data, R.layout.item_menu,
				new String[]{"itemImage"},new int[]{R.id.item_image});
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);
	}

	// 自定义ActionBar
	private void setActionBar(){
		//自定义ActionBar
		actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.actionbar);//自定义ActionBar布局
		actionBar.getCustomView().setOnClickListener(new View.OnClickListener() {
			//监听事件
			@Override
			public void onClick(View v) {

			}
		});
	}

	// 初始化界面
	private void initAcivity(){
		// 日期TextView
		date = (TextView)findViewById(R.id.date);
		// 第几周TextView
		week = (TextView)findViewById(R.id.weekNo);
		// 时间TextView
		time = (TextView)findViewById(R.id.time);
		// 绑定周一的课的TextView
		c11 = (TextView)findViewById(R.id.c11);
		c12 = (TextView)findViewById(R.id.c12);
		c13 = (TextView)findViewById(R.id.c13);
		c14 = (TextView)findViewById(R.id.c14);
		c15 = (TextView)findViewById(R.id.c15);
		c16 = (TextView)findViewById(R.id.c16);
		c17 = (TextView)findViewById(R.id.c17);
		c18 = (TextView)findViewById(R.id.c18);
		c19 = (TextView)findViewById(R.id.c19);
		c110 = (TextView)findViewById(R.id.c110);
		c111 = (TextView)findViewById(R.id.c111);
		// 绑定周二的课的TextView
		c21 = (TextView)findViewById(R.id.c21);
		c22 = (TextView)findViewById(R.id.c22);
		c23 = (TextView)findViewById(R.id.c23);
		c24 = (TextView)findViewById(R.id.c24);
		c25 = (TextView)findViewById(R.id.c25);
		c26 = (TextView)findViewById(R.id.c26);
		c27 = (TextView)findViewById(R.id.c27);
		c28 = (TextView)findViewById(R.id.c28);
		c29 = (TextView)findViewById(R.id.c29);
		c210 = (TextView)findViewById(R.id.c210);
		c211 = (TextView)findViewById(R.id.c211);
		// 绑定周三的课的TextView
		c31 = (TextView)findViewById(R.id.c31);
		c32 = (TextView)findViewById(R.id.c32);
		c33 = (TextView)findViewById(R.id.c33);
		c34 = (TextView)findViewById(R.id.c34);
		c35 = (TextView)findViewById(R.id.c35);
		c36 = (TextView)findViewById(R.id.c36);
		c37 = (TextView)findViewById(R.id.c37);
		c38 = (TextView)findViewById(R.id.c38);
		c39 = (TextView)findViewById(R.id.c39);
		c310 = (TextView)findViewById(R.id.c310);
		c311 = (TextView)findViewById(R.id.c311);
		// 绑定周四的课的TextView
		c41 = (TextView)findViewById(R.id.c41);
		c42 = (TextView)findViewById(R.id.c42);
		c43 = (TextView)findViewById(R.id.c43);
		c44 = (TextView)findViewById(R.id.c44);
		c45 = (TextView)findViewById(R.id.c45);
		c46 = (TextView)findViewById(R.id.c46);
		c47 = (TextView)findViewById(R.id.c47);
		c48 = (TextView)findViewById(R.id.c48);
		c49 = (TextView)findViewById(R.id.c49);
		c410 = (TextView)findViewById(R.id.c410);
		c411 = (TextView)findViewById(R.id.c411);
		// 绑定周五的课的TextView
		c51 = (TextView)findViewById(R.id.c51);
		c52 = (TextView)findViewById(R.id.c52);
		c53 = (TextView)findViewById(R.id.c53);
		c54 = (TextView)findViewById(R.id.c54);
		c55 = (TextView)findViewById(R.id.c55);
		c56 = (TextView)findViewById(R.id.c56);
		c57 = (TextView)findViewById(R.id.c57);
		c58 = (TextView)findViewById(R.id.c58);
		c59 = (TextView)findViewById(R.id.c59);
		c510 = (TextView)findViewById(R.id.c510);
		c511 = (TextView)findViewById(R.id.c511);

		//startDate = new Date(2015-7-1);
		//同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
		SharedPreferences sharedPreferences= getSharedPreferences("date",
				Activity.MODE_PRIVATE);
		// 使用getString方法获得value，注意第2个参数是value的默认值
		String sDate =sharedPreferences.getString("date", "2015-8-1");
		// 设置第几周
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d1=sdf.parse(sDate);
			Date d2=new Date();
			weekNum = getWeedNo(d1,d2);
			String numStr = "第" + weekNum + "周";
			week.setText(numStr);
		} catch (ParseException e){
			e.printStackTrace();
		}

		// TextView中设置相应的课程
		String mClass[][] = new String[5][11];
		for(int i=0; i<5; i++){
			for(int j=0; j<10; j++){
				String week = Integer.toString(i+1);
				String time = Integer.toString(j+1);
				String weekNumS = weekNum + "";
				mClass[i][j] = getClass(week, time, weekNumS);
			}
		}

		// 给每个单元格绑定相应的文字
		c11.setText(mClass[0][0]);
		c12.setText(mClass[0][1]);
		c13.setText(mClass[0][2]);
		c14.setText(mClass[0][3]);
		c15.setText(mClass[0][4]);
		c16.setText(mClass[0][5]);
		c17.setText(mClass[0][6]);
		c18.setText(mClass[0][7]);
		c19.setText(mClass[0][8]);
		c110.setText(mClass[0][9]);
		c111.setText(mClass[0][10]);
		c21.setText(mClass[1][0]);
		c22.setText(mClass[1][1]);
		c23.setText(mClass[1][2]);
		c24.setText(mClass[1][3]);
		c25.setText(mClass[1][4]);
		c26.setText(mClass[1][5]);
		c27.setText(mClass[1][6]);
		c28.setText(mClass[1][7]);
		c29.setText(mClass[1][8]);
		c210.setText(mClass[1][9]);
		c211.setText(mClass[1][10]);
		c31.setText(mClass[2][0]);
		c32.setText(mClass[2][1]);
		c33.setText(mClass[2][2]);
		c34.setText(mClass[2][3]);
		c35.setText(mClass[2][4]);
		c36.setText(mClass[2][5]);
		c37.setText(mClass[2][6]);
		c38.setText(mClass[2][7]);
		c39.setText(mClass[2][8]);
		c310.setText(mClass[2][9]);
		c311.setText(mClass[2][10]);
		c41.setText(mClass[3][0]);
		c42.setText(mClass[3][1]);
		c43.setText(mClass[3][2]);
		c44.setText(mClass[3][3]);
		c45.setText(mClass[3][4]);
		c46.setText(mClass[3][5]);
		c47.setText(mClass[3][6]);
		c48.setText(mClass[3][7]);
		c49.setText(mClass[3][8]);
		c410.setText(mClass[3][9]);
		c411.setText(mClass[3][10]);
		c51.setText(mClass[4][0]);
		c52.setText(mClass[4][1]);
		c53.setText(mClass[4][2]);
		c54.setText(mClass[4][3]);
		c55.setText(mClass[4][4]);
		c56.setText(mClass[4][5]);
		c57.setText(mClass[4][6]);
		c58.setText(mClass[4][7]);
		c59.setText(mClass[4][8]);
		c510.setText(mClass[4][9]);
		c511.setText(mClass[4][10]);
		c11.setOnClickListener(this);
		c12.setOnClickListener(this);
		c13.setOnClickListener(this);
		c14.setOnClickListener(this);
		c15.setOnClickListener(this);
		c16.setOnClickListener(this);
		c17.setOnClickListener(this);
		c18.setOnClickListener(this);
		c19.setOnClickListener(this);
		c110.setOnClickListener(this);
		c111.setOnClickListener(this);
		c21.setOnClickListener(this);
		c22.setOnClickListener(this);
		c23.setOnClickListener(this);
		c24.setOnClickListener(this);
		c25.setOnClickListener(this);
		c26.setOnClickListener(this);
		c27.setOnClickListener(this);
		c28.setOnClickListener(this);
		c29.setOnClickListener(this);
		c210.setOnClickListener(this);
		c211.setOnClickListener(this);
		c31.setOnClickListener(this);
		c32.setOnClickListener(this);
		c33.setOnClickListener(this);
		c34.setOnClickListener(this);
		c35.setOnClickListener(this);
		c36.setOnClickListener(this);
		c37.setOnClickListener(this);
		c38.setOnClickListener(this);
		c39.setOnClickListener(this);
		c310.setOnClickListener(this);
		c311.setOnClickListener(this);
		c41.setOnClickListener(this);
		c42.setOnClickListener(this);
		c43.setOnClickListener(this);
		c44.setOnClickListener(this);
		c45.setOnClickListener(this);
		c46.setOnClickListener(this);
		c47.setOnClickListener(this);
		c48.setOnClickListener(this);
		c49.setOnClickListener(this);
		c410.setOnClickListener(this);
		c411.setOnClickListener(this);
		c51.setOnClickListener(this);
		c52.setOnClickListener(this);
		c53.setOnClickListener(this);
		c54.setOnClickListener(this);
		c55.setOnClickListener(this);
		c56.setOnClickListener(this);
		c57.setOnClickListener(this);
		c58.setOnClickListener(this);
		c59.setOnClickListener(this);
		c510.setOnClickListener(this);
		c511.setOnClickListener(this);

		// 给有课的单元格添加随机底色
		List<TextView> list = new ArrayList<TextView>();
		list.add(c11);
		list.add(c12);
		list.add(c13);
		list.add(c14);
		list.add(c15);
		list.add(c16);
		list.add(c17);
		list.add(c18);
		list.add(c19);
		list.add(c110);
		list.add(c111);
		list.add(c21);
		list.add(c22);
		list.add(c23);
		list.add(c24);
		list.add(c25);
		list.add(c26);
		list.add(c27);
		list.add(c28);
		list.add(c29);
		list.add(c210);
		list.add(c211);
		list.add(c31);
		list.add(c32);
		list.add(c33);
		list.add(c34);
		list.add(c35);
		list.add(c36);
		list.add(c37);
		list.add(c38);
		list.add(c39);
		list.add(c310);
		list.add(c311);
		list.add(c41);
		list.add(c42);
		list.add(c43);
		list.add(c44);
		list.add(c45);
		list.add(c46);
		list.add(c47);
		list.add(c48);
		list.add(c49);
		list.add(c410);
		list.add(c411);
		list.add(c51);
		list.add(c52);
		list.add(c53);
		list.add(c54);
		list.add(c55);
		list.add(c56);
		list.add(c57);
		list.add(c58);
		list.add(c59);
		list.add(c510);
		list.add(c511);

		for(TextView tv : list){
			if(!(""==tv.getText())||(null ==tv.getText())){
				int num = (int)(Math.random()*0x77999999+0x88555555);
				tv.setBackgroundColor(num);
			}
		}


	}
}
