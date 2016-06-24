package com.csw.switch_soundcard;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.csw.sax.SoundEffectInfo;
import com.csw.sax.XmlPaser;


import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Log;
import android.widget.Toast;


@SuppressLint("ShowToast")
public class ServerService extends Service {

	String serverIp = null;


	public static Context mContext;

	private static String TAG="switchSoundCard";
	
	
	private ArrayList<SoundEffectInfo> mSoundEffectInfoList=null;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		System.out.println("这里是ServerService的onCreate方法");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		System.out.println("这里是ServerService的onStart方法");
		mContext=this;
		myHandler.sendEmptyMessage(1);

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private final int CREATE_TCP_SERVER = 0;
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				break;

			case 1:
				
				SAXParserFactory spf=SAXParserFactory.newInstance();
			     try {
						SAXParser sp=spf.newSAXParser();
						XmlPaser xmlPar=new XmlPaser();
						InputStream is=this.getClass().getClassLoader().getResourceAsStream("sound_effect.xml");
						sp.parse(is, xmlPar);
                       
						mSoundEffectInfoList=xmlPar.getSoundEffectInfoList();
						Log.d(TAG,"mSoundEffectInfoList.size="+ mSoundEffectInfoList.size()+"");
			     }catch(Exception e){
			        e.printStackTrace();	
			     }
				
				weatherMoLiAppRun();
				System.out.println("检测天籁k歌的线程开启");
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 检测当前运行的app是否是魔力视频
	 */
	private void weatherMoLiAppRun() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						Thread.sleep(3500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					isAppRunning();
				}
			}
		});
		thread.start();
	}

	/**
	 * whichAppPackage，0代表进入主界面或者其他非指定界面，123根據xml等表示
	 */
	private static String whichAppPackage="0";
	
	
	private SoundEffectInfo mSoundEffectInfoTemp;
	private boolean isAppRunning() {
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		if (list == null)
			return false;
		for (RunningTaskInfo info : list) {
			if (info == null)
				continue;
			Log.d(TAG,
					"info.topActivity.getPackageName()="
							+ info.topActivity.getPackageName());

			String currentAppPackageName = info.topActivity.getPackageName();
			String currentBasePackageName = info.baseActivity.getPackageName();//后台也有这个包名的应用在运行

			for(SoundEffectInfo mSoundEffectInfo : mSoundEffectInfoList){
				if(mSoundEffectInfo== null){
					continue;
				}
				String storePackageName=mSoundEffectInfo.getEffectPackage();
//				Log.d(TAG,"currentStorePackageName = "+ storePackageName);
				
				
				if(storePackageName.contains(currentAppPackageName)&&currentAppPackageName.contains(".")){//如果已存的包名中有正在運行應用的包名
					String effectName=mSoundEffectInfo.getEffectName();//音效名稱
					String effectValue=mSoundEffectInfo.getEffectValue();//要傳入音效的值
					String effectId=mSoundEffectInfo.getEffectId();//音效的id
					
					mSoundEffectInfoTemp=new SoundEffectInfo(effectId,effectName,effectValue,storePackageName);
					
					break;
				}else{
					mSoundEffectInfoTemp=null;
				}
			}
			
			
			
			if(mSoundEffectInfoTemp==null){
				
				if(whichAppPackage.equals("0")){
					Log.i(TAG, "Has switch switch_codec");
					
				}else{
					SystemProperties.set("ctl.start", "switch_codec");
					whichAppPackage="0";
					Log.i(TAG, "Going to switch "+"switch_codec");
				}
				
			}else{
				String effectName=mSoundEffectInfoTemp.getEffectName();//音效名稱
				String effectValue=mSoundEffectInfoTemp.getEffectValue();//要傳入音效的值
				String effectId=mSoundEffectInfoTemp.getEffectId();//音效的id
				if(whichAppPackage.equals(effectId)){
					Log.i(TAG, "Has switch "+effectValue+" ,current effectName="+effectName);
					
				}else{
					SystemProperties.set("ctl.start", effectValue);
					whichAppPackage=effectId;
					Log.i(TAG, "Going to switch "+effectValue+" ,to switch "+effectName);
				}
			}
			mSoundEffectInfoTemp=null;
			return true;
		}
		return false;
	}


	

}
