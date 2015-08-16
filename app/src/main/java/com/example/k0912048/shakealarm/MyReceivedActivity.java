package com.example.k0912048.shakealarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceivedActivity extends BroadcastReceiver{
	public void onReceive(Context context, Intent intent){
		Toast.makeText(context,"AlarmManager Test",Toast.LENGTH_SHORT);
//		((MainActivity)context).ring();
	}

}

