package com.panwix.iclass.Activitys;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.panwix.iclass.R;

import java.util.Calendar;

/**
 * Created by Panwix on 15/7/27.
 */
public class settingActivity extends Activity {
    private DatePicker datePicker;
    private Button btn;
    private TextView tv;
    private String startDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        btn = (Button)findViewById(R.id.btnPost);
        tv = (TextView)findViewById(R.id.tv);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        datePicker.init(2015, 9, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                //calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                startDate = datePicker.getYear() + "-"
                        + (datePicker.getMonth()+1) + "-"
                        + datePicker.getDayOfMonth();
                tv.setText(startDate);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实例化SharedPreferences对象（第一步）
                SharedPreferences mySharedPreferences= getSharedPreferences("date",
                        Activity.MODE_PRIVATE);
                //实例化SharedPreferences.Editor对象（第二步）
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                //用putString的方法保存数据
                editor.putString("date", startDate);
                //提交当前数据
                editor.commit();
                //使用toast信息提示框提示成功写入数据
//                Toast.makeText(getApplicationContext(), "数据成功写入SharedPreferences！",
//                        Toast.LENGTH_LONG).show();
                getIntent().putExtra("date", startDate);
                setResult(R.layout.home_layout, getIntent());
                finish();
            }
        });
    }


}
