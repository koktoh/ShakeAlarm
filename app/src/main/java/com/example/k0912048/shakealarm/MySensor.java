package com.example.k0912048.shakealarm;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

public class MySensor extends Activity implements SensorEventListener{
	
	private SensorManager manager;
	private Sensor sensor;
	
	private float data=0;
	private float oldData=0;
	private int result=0;
	
	TextView tv;
	
	MediaPlayer mp;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wake_up);
		
		tv=(TextView)findViewById(R.id.parcent);
		
		manager=(SensorManager)getSystemService(SENSOR_SERVICE);
		sensor=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		amanager.setStreamVolume(AudioManager.STREAM_MUSIC, amanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
		mp=MediaPlayer.create(getBaseContext(),R.raw.alarm);
		mp.setLooping(true);
		mp.start();
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		manager.unregisterListener(this);
		mp.stop();
		finish();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event){
		data=event.values[SensorManager.DATA_Y]-oldData;
		oldData=event.values[SensorManager.DATA_Y];
		
		if(Math.abs(data)>20){
			result++;
			tv.setText(String.valueOf(result));
		}
		
		if(result>=100){
			onStop();
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy){
	}
}
