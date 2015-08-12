package src.main.java.com.panwix.classtable.Activitys;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.panwix.classtable.R;
import com.panwix.classtable.database.ClassDao;
import com.panwix.classtable.database.DBService;
import com.panwix.classtable.database.DBhelper;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);

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

		sureBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DBhelper helper = new DBhelper(getBaseContext());
				helper.getWritableDatabase();
				DBService service = new ClassDao(getBaseContext());
				ContentValues values = new ContentValues();
				String classStr = mClass.getText().toString();
				String classRoomStr = mClassRoom.getText().toString();
				String teacherStr = mTeacher.getText().toString();
				String teacherRoomStr = mTeacherRoom.getText().toString();
				String weekStartStr = mWeekStart.getText().toString();
				String weekEndStr = mWeekEnd.getText().toString();
				String classWeekNoStr = classWeekNo.getText().toString();
				String classNoStr = classNo.getText().toString();
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
				if(flag){
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
