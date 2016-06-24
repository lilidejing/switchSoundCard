package com.csw.sax;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class XmlPaser extends DefaultHandler {

	SoundEffectInfo mSoundEffectInfo;
	ArrayList<SoundEffectInfo> mSoundEffectInfoList = new ArrayList<SoundEffectInfo>(); 
	
	
	private static String TAG="XmlPaser";
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		
	}
	
	
	
	public XmlPaser() {
		super();
	}



	public XmlPaser(ArrayList<SoundEffectInfo> mSoundEffectInfoList) {
		super();
		this.mSoundEffectInfoList = mSoundEffectInfoList;
	}



	/*public SoundEffectInfo getSoundEffectInfo() {
		return mSoundEffectInfo;
	}
	public void setmSoundEffectInfo(SoundEffectInfo mSoundEffectInfo) {
		this.mSoundEffectInfo = mSoundEffectInfo;
	}*/
	String tagName=null;
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		if(tagName!=null){
			if("effectId".equals(tagName)){
				String effectId=new String(ch,start,length);
				mSoundEffectInfo.setEffectId(effectId);
			}else if("effectName".equals(tagName)){
				String effectName=new String(ch,start,length);
				mSoundEffectInfo.setEffectName(effectName);
			}
			else if("effectValue".equals(tagName)){
			
				String effectValue=new String(ch,start,length);
				mSoundEffectInfo.setEffectValue(effectValue);
			}
			else if("effectPackage".equals(tagName)){
				
				String effectPackage=new String(ch,start,length);
				mSoundEffectInfo.setEffectPackage(effectPackage);
			}
		}
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		tagName=qName;
		if("sound_effect".equals(tagName)){
			mSoundEffectInfo=new SoundEffectInfo();
			Log.d(TAG, "startElement  ");
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if("sound_effect".equals(qName)) {  
			mSoundEffectInfoList.add(mSoundEffectInfo);  
//			mSoundEffectInfo = null;  
			Log.d(TAG, "endElement  ");
        }  
		tagName=null;
	}
	

	
	
	public void setmSoundEffectInfoList(
			ArrayList<SoundEffectInfo> mSoundEffectInfoList) {
		this.mSoundEffectInfoList = mSoundEffectInfoList;
	}
	public ArrayList<SoundEffectInfo> getSoundEffectInfoList(){
		
		Log.d("", mSoundEffectInfoList.size()+"");
		
		return mSoundEffectInfoList;
	}
}
