package com.example.k0912048.shakealarm;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {

	Boolean set=false;
	Boolean run=false;
	Date date;
	TextView clock;
	public Button runButton;
	Timer timer;
	
	AlarmManager alarm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		clock=(TextView)findViewById(R.id.clock1);
		runButton=(Button)findViewById(R.id.runButton);
		
		if(run){
			runButton.setText("stop");
		}
		else
		{
			runButton.setText("start");
		}
		
		if(!set){
			runButton.setEnabled(false);
		}
		
		runButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(run)
				{
					stop();
					run=false;
					runButton.setText("start");
				}
				else
				{
					run();
					run=true;
					runButton.setText("stop");
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClickClock(View view){
		
		Calendar cal=Calendar.getInstance();
		int nowHourOfDay=cal.get(Calendar.HOUR_OF_DAY);
		int nowMin=cal.get(Calendar.MINUTE);
		
		new TimePickerDialog(MainActivity.this,new TimePickerDialog.OnTimeSetListener(){
	
	@Override
	public void onTimeSet(TimePicker view,int hourOfDay,int minute)
	{
		date=new Date();
		Calendar cal=Calendar.getInstance();
		int nowHourOfDay=cal.get(Calendar.HOUR_OF_DAY);
		int nowMin=cal.get(Calendar.MINUTE);
		clock.setText(String.format("%02d:%02d", hourOfDay, minute));
		if(minute<=nowMin){
			hourOfDay++;
			if(hourOfDay>24){
				hourOfDay=0;
			}
		}
		if(hourOfDay<nowHourOfDay){
			date.setDate(cal.get(Calendar.DATE+1));
		}
		date.setHours(hourOfDay);
		date.setMinutes(minute);
		date.setSeconds(0);
	}
	
		},nowHourOfDay,nowMin,true).show();
		runButton.setEnabled(true);
	}
	
	public void run()
	{
		timer=new Timer();
		TimerTask timerTask=new MyTimerTask(MainActivity.this);
		timer.schedule(timerTask, date);
		
/*		Intent intent=new Intent(getApplicationContext(),MyReceivedActivity.class);
		PendingIntent sender=PendingIntent.getBroadcast(this, 0, intent, 0);
		alarm=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarm.set(AlarmManager.RTC_WAKEUP, date.getTime(), sender);
*/	}
	
	public void stop()
	{
		if(timer!=null){
			timer.cancel();
		}
		runButton.setText("start");
		runButton.setEnabled(false);
	}
	
	public void ring(){
		Intent intent=new Intent(this,MySensor.class);
		startActivity(intent);
		stop();
	}
}
