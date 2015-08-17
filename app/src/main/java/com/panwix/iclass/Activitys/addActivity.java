package com.panwix.iclass.Activitys;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.panwix.iclass.R;
import com.panwix.iclass.database.ClassDao;
import com.panwix.iclass.database.DBService;
import com.panwix.iclass.database.DBhelper;

public class addActivity extends Activity{

	private EditText mClass;
	private EditText mClassRoom;
	private EditText mTeacher;
	private EditText mTeacherRoom;
	private EditText mWeekStart;
	private EditText mWeekEnd;
	private EditText classWeekNo;
	private EditText classNo;
	private EditText tEmail;
	private EditText tTel;
	private EditText tPhone;
	private Button sureBtn;
	private Button cancelBtn;

	private TextView className;
	private TextView classRoom;
	private TextView startWeek;
	private TextView endWeek;
	private TextView classWeek;
	private TextView classTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		// 隐藏状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
				, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mClass = (EditText)findViewById(R.id.et0);
		mClassRoom = (EditText)findViewById(R.id.et1);
		mTeacher = (EditText)findViewById(R.id.et2);
		mTeacherRoom = (EditText)findViewById(R.id.et3);
		mWeekStart = (EditText)findViewById(R.id.et41);
		mWeekEnd = (EditText)findViewById(R.id.et42);
		classWeekNo = (EditText)findViewById(R.id.et5);
		classNo = (EditText)findViewById(R.id.et6);
		tEmail = (EditText)findViewById(R.id.et7);
		tTel = (EditText)findViewById(R.id.et8);
		tPhone = (EditText)findViewById(R.id.et9);
		cancelBtn = (Button)findViewById(R.id.btn9);
		sureBtn = (Button)findViewById(R.id.btn10);

		className = (TextView)findViewById(R.id.tv0);
		classRoom = (TextView)findViewById(R.id.tv1);
		startWeek = (TextView)findViewById(R.id.tv41);
		endWeek = (TextView)findViewById(R.id.tv42);
		classWeek = (TextView)findViewById(R.id.tv5);
		classTime = (TextView)findViewById(R.id.tv6);

		String name_str = "科目:<font color='red'>*</font>";
		String room_str = "教室:<font color='red'>*</font>";
		String start_str = "开始周:<font color='red'>*</font>";
		String end_str = "结束周:<font color='red'>*</font>";
		String week_str = "周几上课:<font color='red'>*</font>";
		String time_str = "第几节课:<font color='red'>*</font>";

		className.setText(Html.fromHtml(name_str));
		classRoom.setText(Html.fromHtml(room_str));
		startWeek.setText(Html.fromHtml(start_str));
		endWeek.setText(Html.fromHtml(end_str));
		classWeek.setText(Html.fromHtml(week_str));
		classTime.setText(Html.fromHtml(time_str));

		sureBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DBhelper helper = new DBhelper(getBaseContext());
				helper.getWritableDatabase();
				DBService service = new ClassDao(getBaseContext());
				ContentValues values = new ContentValues();
				String classStr = mClass.getText().toString();
				if (classStr.equals("") || null == classStr) {
					Toast.makeText(getApplicationContext(), "忘记输入课程名了～",
							Toast.LENGTH_SHORT).show();
					mClass.setBackgroundColor(Color.RED);
					return;
				}
				String classRoomStr = mClassRoom.getText().toString();
				if (classRoomStr.equals("") || null == classRoomStr) {
					Toast.makeText(getApplicationContext(), "忘记输入教室了～",
							Toast.LENGTH_SHORT).show();
					mClassRoom.setBackgroundColor(Color.RED);
					return;
				}
				String teacherStr = mTeacher.getText().toString();
				String teacherRoomStr = mTeacherRoom.getText().toString();
				String weekStartStr = mWeekStart.getText().toString();
				if (weekStartStr.equals("") || null == weekStartStr) {
					Toast.makeText(getApplicationContext(), "忘记输入第几周开始了～",
							Toast.LENGTH_SHORT).show();
					mWeekStart.setBackgroundColor(Color.RED);
					return;
				}
				String weekEndStr = mWeekEnd.getText().toString();
				if (weekEndStr.equals("") || null == weekEndStr) {
					Toast.makeText(getApplicationContext(), "忘记输入第几周结束了～",
							Toast.LENGTH_SHORT).show();
					mWeekEnd.setBackgroundColor(Color.RED);
					return;
				}
				String classWeekNoStr = classWeekNo.getText().toString();
				if (classWeekNoStr.equals("") || null == classWeekNoStr) {
					Toast.makeText(getApplicationContext(), "忘记输入是周几的课了～",
							Toast.LENGTH_SHORT).show();
					classWeekNo.setBackgroundColor(Color.RED);
					return;
				}
				String classNoStr = classNo.getText().toString();
				if (classNoStr.equals("") || null == classNoStr) {
					Toast.makeText(getApplicationContext(), "忘记输入是第几节课了～",
							Toast.LENGTH_SHORT).show();
					classNo.setBackgroundColor(Color.RED);
					return;
				}
				String tEmailStr = tEmail.getText().toString();
				String tPhoneStr = tPhone.getText().toString();
				String tTelStr = tTel.getText().toString();


				values.put("class", classStr);
				values.put("classroom", classRoomStr);
				values.put("teacher", teacherStr);
				values.put("tel", tTelStr);
				values.put("phone", tPhoneStr);
				values.put("office", teacherRoomStr);
				values.put("email", tEmailStr);
				values.put("week", classWeekNoStr);
				values.put("classTime", classNoStr);
				values.put("classStart", weekStartStr);
				values.put("classEnd", weekEndStr);

				boolean flag = service.addClass(values);
				if (flag) {
					mClass.setText("");
					mClass.setHint("");
					mClassRoom.setText("");
					mClassRoom.setHint("");
					mTeacher.setText("");
					mTeacher.setHint("");
					mTeacherRoom.setText("");
					mTeacherRoom.setHint("");
					mWeekStart.setText("");
					mWeekStart.setHint("");
					mWeekEnd.setText("");
					mWeekEnd.setHint("");
					classWeekNo.setText("");
					classWeekNo.setHint("");
					classNo.setText("");
					classNo.setHint("");
					tEmail.setText("");
					tEmail.setHint("");
					tTel.setText("");
					tTel.setHint("");
					tPhone.setText("");
					tPhone.setHint("");
					Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_SHORT).show();
				}
			}
		});

		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(addActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent=new Intent();
			intent.setClass(addActivity.this, MainActivity.class);
			startActivity(intent);
			addActivity.this.finish();
		}
		return false;
	}

}
