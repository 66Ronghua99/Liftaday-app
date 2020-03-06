package com.example.liftaday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.util.StateSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liftaday.utils.FileUtil;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Setting extends AppCompatActivity {
    public final static String USERNAME = "username.txt";
    PickerView hour_pv;
    PickerView minute_pv;
    String date;
    String hour;
    String minutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initPicker();
        File file = new File(Environment.getExternalStorageDirectory(),USERNAME);
        if(file.exists()){
            EditText editText = findViewById(R.id.editText2);
            editText.setText(SplashScreen.name);
        }
        date = getDate(stampToDate(System.currentTimeMillis()));
        Button btn1 =findViewById(R.id.save);


        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Set the notification
                if(hour==null) hour = SplashScreen.hour;
                if(minutes==null) minutes = SplashScreen.minutes;
                Intent intent = new Intent();
                intent.setAction("com.example.liftaday.Notification_Alarm");
                intent.setComponent( new ComponentName( "com.example.liftaday" ,
                        "com.example.liftaday.AlarmReceiver") );
                PendingIntent sender = PendingIntent.getBroadcast(getApplication(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(getApplication().ALARM_SERVICE);
                long wakeTime = getStringToDate(date+hour+":"+minutes+":00","yyyy MM dd HH:mm:ss");
                Log.i("tag","选择了 " + wakeTime +" "+date+ hour +":"+minutes+" 推送, 当前时间"+System.currentTimeMillis());
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, wakeTime, AlarmManager.INTERVAL_DAY, sender);
                //Set the name of user
                EditText editText = findViewById(R.id.editText2);
                FileUtil.saveFile(String.format("%s\n%s\n%s",editText.getText().toString(),hour,minutes),USERNAME);
                SplashScreen.name = editText.getText().toString();
                Intent intent2= new Intent(Setting.this, MainActivity.class);
                startActivity(intent2);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void goingBack(MenuItem item){
//        Intent intent= new Intent();
//        intent.setAction("com.example.liftaday.Notification_Alarm");
//        intent.setComponent( new ComponentName( "com.example.liftaday" , "com.example.liftaday.AlarmReceiver"));
//        sendBroadcast(intent);
        Intent intent1 = new Intent(Setting.this, MainActivity.class);
        startActivity(intent1);
    }

    private void initPicker(){
        hour_pv = (PickerView) findViewById(R.id.timeMinute);
        minute_pv = (PickerView) findViewById(R.id.timeSecond);
        List<String> hourData = new ArrayList<String>();
        List<String> minuteData = new ArrayList<String>();
        for (int i = 0; i < 10; i++)
        {
            hourData.add("0" + i);
        }
        for (int i = 0; i < 10; i++)
        {
            hourData.add("1" + i);
        }
        for (int i = 0; i < 4; i++)
        {
            hourData.add("2" + i);
        }
        for (int i = 0; i < 60; i++)
        {
            minuteData.add(i < 10 ? "0" + i : "" + i);
        }
        hour_pv.setData(hourData,"hour");
        hour_pv.setOnSelectListener(new PickerView.onSelectListener()
        {
            @Override
            public void onSelect(String text)
            {
                hour = text;
                SplashScreen.hour = hour;
            }
        });
        minute_pv.setData(minuteData,"minutes");
        minute_pv.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                minutes = text;
                SplashScreen.minutes = minutes;
            }
        });
    }

    public static String getDate(String str){
        return str.substring(0,11);
    }
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String stampToDate(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

//————————————————
//    版权声明：本文为CSDN博主「xiaobin92」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/qq_25697993/article/details/53583766
}
