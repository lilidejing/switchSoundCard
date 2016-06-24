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
		
		System.out.println("������ServerService��onCreate����");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		System.out.println("������ServerService��onStart����");
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
				System.out.println("�������k����߳̿���");
				break;
			default:
				break;
			}
		}
	};

	/**
	 * ��⵱ǰ���е�app�Ƿ���ħ����Ƶ
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
	 * whichAppPackage��0����������������������ָ�����棬123����xml�ȱ�ʾ
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
			String currentBasePackageName = info.baseActivity.getPackageName();//��̨Ҳ�����������Ӧ��������

			for(SoundEffectInfo mSoundEffectInfo : mSoundEffectInfoList){
				if(mSoundEffectInfo== null){
					continue;
				}
				String storePackageName=mSoundEffectInfo.getEffectPackage();
//				Log.d(TAG,"currentStorePackageName = "+ storePackageName);
				
				
				if(storePackageName.contains(currentAppPackageName)&&currentAppPackageName.contains(".")){//����Ѵ�İ������������\�Б��õİ���
					String effectName=mSoundEffectInfo.getEffectName();//��Ч���Q
					String effectValue=mSoundEffectInfo.getEffectValue();//Ҫ������Ч��ֵ
					String effectId=mSoundEffectInfo.getEffectId();//��Ч��id
					
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
				String effectName=mSoundEffectInfoTemp.getEffectName();//��Ч���Q
				String effectValue=mSoundEffectInfoTemp.getEffectValue();//Ҫ������Ч��ֵ
				String effectId=mSoundEffectInfoTemp.getEffectId();//��Ч��id
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
