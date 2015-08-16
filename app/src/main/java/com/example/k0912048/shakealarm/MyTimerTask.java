package com.example.k0912048.shakealarm;

import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class MyTimerTask extends TimerTask{
	
	Handler handler;
	Context context;
	Intent intent;
	
	public MyTimerTask(Context context)
	{
		handler =new Handler();
		this.context=context;
		intent=new Intent(context,MySensor.class);
	}
	
	@Override
	public void run(){
		handler.post(new Runnable(){
			@Override
			public void run(){
				((MainActivity)context).ring();
			}
		});
	}
}
