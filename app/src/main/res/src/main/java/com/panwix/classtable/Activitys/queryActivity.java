package src.main.java.com.panwix.classtable.Activitys;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.panwix.classtable.R;
import com.panwix.classtable.database.DBhelper;

/**
 * Created by Panwix on 15/7/27.
 */
public class queryActivity extends Activity {
    private Button btn;
    private EditText et;
    private Cursor cursor;
    private DBhelper helper;
    private String sql;
    private SQLiteDatabase database;

    private String className;
    private String weekStart;
    private String weekEnd;
    private String classRoom;
    private String teacher;
    private String teacherRoom;
    private String mail;
    private String tel;
    private String phone;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LinearLayout llm = (LinearLayout) getLayoutInflater().inflate(R.layout.query, null);
        tv = (TextView)findViewById(R.id.tv);
        btn = (Button) findViewById(R.id.btn);
        et = (EditText) findViewById(R.id.et);
        helper = new DBhelper(getBaseContext());
        sql = "select * from Class where class = ?";
        database = helper.getWritableDatabase();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = et.getText().toString();
                cursor = database.rawQuery(sql, new String[]{string});
                int i = cursor.getCount();
                if (i > 0) {
                    cursor.moveToFirst();
                    weekStart = cursor.getString(cursor.getColumnIndex("classStart"));
                    weekEnd = cursor.getString(cursor.getColumnIndex("classEnd"));
                    className = cursor.getString(cursor.getColumnIndex("class"));
                    classRoom = cursor.getString(cursor.getColumnIndex("classroom"));
                    teacher = cursor.getString(cursor.getColumnIndex("teacher"));
                    teacherRoom = cursor.getString(cursor.getColumnIndex("office"));
                    mail = cursor.getString(cursor.getColumnIndex("email"));
                    tel = cursor.getString(cursor.getColumnIndex("tel"));
                    tv.append(className + "\n"
                            + classRoom + "\n"
                            + weekStart + "\n"
                            + weekEnd + "\n"
                            + teacher + "\n"
                            + teacherRoom + "\n"
                            + mail + "\n"
                            + tel + "\n"
                            + phone + "\n");
                    while (cursor.moveToNext()) {
                        weekStart = cursor.getString(cursor.getColumnIndex("classStart"));
                        weekEnd = cursor.getString(cursor.getColumnIndex("classEnd"));
                        className = cursor.getString(cursor.getColumnIndex("class"));
                        classRoom = cursor.getString(cursor.getColumnIndex("classroom"));
                        teacher = cursor.getString(cursor.getColumnIndex("teacher"));
                        teacherRoom = cursor.getString(cursor.getColumnIndex("office"));
                        mail = cursor.getString(cursor.getColumnIndex("email"));
                        tel = cursor.getString(cursor.getColumnIndex("tel"));
                        phone = cursor.getString(cursor.getColumnIndex("phone"));
                        tv.append("-----------------------" + "\n"
                                + className + "\n"
                                + classRoom + "\n"
                                + weekStart + "\n"
                                + weekEnd + "\n"
                                + teacher + "\n"
                                + teacherRoom + "\n"
                                + mail + "\n"
                                + tel + "\n"
                                + phone + "\n");
                    }
                }
            }

        });
    }
}