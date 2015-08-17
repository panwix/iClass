package com.panwix.iclass.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.panwix.iclass.R;
import com.panwix.iclass.database.ClassDao;
import com.panwix.iclass.database.DBService;
import com.panwix.iclass.database.DBhelper;

public class deleteActivity extends Activity {

	private EditText edtWeekStart;
	private EditText edtWeekEnd;
	private Button sureBtn;
	private Button cancelBtn;
	private EditText edtWeek;
	private EditText edtTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);
		// 隐藏状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
				, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		edtWeekStart = (EditText)findViewById(R.id.edtWeedStart);
		edtWeekEnd = (EditText)findViewById(R.id.edtWeedEnd);
		edtWeek = (EditText)findViewById(R.id.edtWeek);
		edtTime = (EditText)findViewById(R.id.edtTime);
		sureBtn=(Button)findViewById(R.id.sureBtn);
		cancelBtn = (Button)findViewById(R.id.cancelBtn);

		sureBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				DBhelper helper = new DBhelper(getBaseContext());
				helper.getWritableDatabase();
				DBService service = new ClassDao(getBaseContext());
				String edtWeekStartStr = edtWeekStart.getText().toString();
				String edtWeekEndStr = edtWeekEnd.getText().toString();
				String edtWeekStr = edtWeek.getText().toString();
				String edtTimeStr = edtTime.getText().toString();
				String whereArgs[] = {edtWeek.getText().toString(), edtTime.getText().toString(),
						edtWeekStart.getText().toString(), edtWeekEnd.getText().toString()};
				if(edtWeekStart.getText().toString().equals("")){
					edtWeekStart.setBackgroundColor(Color.RED);
					Toast.makeText(getApplicationContext(), "忘记输入第几周开始了～",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if(edtWeekEnd.getText().toString().equals("")){
					edtWeekEnd.setBackgroundColor(Color.RED);
					Toast.makeText(getApplicationContext(), "忘记输入第几周结束了～",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if(edtWeek.getText().toString().equals("")){
					edtWeek.setBackgroundColor(Color.RED);
					Toast.makeText(getApplicationContext(), "忘记输入是星期几的课了～",
							Toast.LENGTH_SHORT).show();
					 return;
				}
				if(edtTime.getText().toString().equals("")){
					edtTime.setBackgroundColor(Color.RED);
					Toast.makeText(getApplicationContext(), "忘记输入是第几节课了～",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (!edtWeekStartStr.equals("")
						&& !edtWeekEndStr.equals("")
						&& !edtWeekStr.equals("")
						&& !edtTimeStr.equals("")) {
					String whereClaus[] = {"week", "classTime", "classStart", "classEnd"};
					Boolean flag = service.deleteClass("week=? and classTime=? and classStart=? and classEnd=?",whereArgs);
					if(flag) {
						Toast.makeText(getApplicationContext(), "删除成功",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(deleteActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
				}
		}});

		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(deleteActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});


	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent=new Intent();
			intent.setClass(deleteActivity.this, MainActivity.class);
			startActivity(intent);
			deleteActivity.this.finish();
		}
		return false;
	}
}
