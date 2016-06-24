package com.csw.switch_soundcard;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/*
 * 开机自启动广播
 */
public class BootReceiver extends BroadcastReceiver {

	private static final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";  //开机启动广播
	
	@Override
	public void onReceive(Context context, Intent intent) {
//		if (Intent.ACTION_MEDIA_MOUNTED.equals(intent.getAction())) {
			
			if (intent.getAction().equals(BOOT_ACTION)) {
				Intent mBootIntent = new Intent(context, MainActivity.class);
				mBootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(mBootIntent);
				System.out.println("接收到广播，准备启动");
			}
	}

}
