package com.csw.switch_soundcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent mIntent=new Intent();
		mIntent.setClass(this, ServerService.class);
		startService(mIntent);
		
		this.finish();
		
		
	}

	
}
