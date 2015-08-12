package src.main.java.com.panwix.classtable.Activitys;

import android.app.Activity;
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
				if (!edtWeekStartStr.equals("")
						&& !edtWeekEndStr.equals("")
						&& !edtWeekStr.equals("")
						&& !edtTimeStr.equals("")) {
					String whereClaus[] = {"week", "classTime", "classStart", "classEnd"};
					Boolean flag = service.deleteClass("week=? and classTime=? and classStart=? and classEnd=?",whereArgs);
					if(flag) {
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
