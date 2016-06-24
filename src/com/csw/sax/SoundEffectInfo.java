package com.csw.sax;

public class SoundEffectInfo {
    /**
     * ��ЧID
     */
	String effectId;
	/**
	 * ��Ч����
	 */
	String effectName;
	/**
	 * ��ЧҪ����ֵ
	 */
	String effectValue;
	/**
	 * ����Ч��Ҫ�򿪵�Ӧ�ó������
	 */
	String effectPackage;
	
	public SoundEffectInfo(String effectId, String effectName,
			String effectValue, String effectPackage) {
		super();
		this.effectId = effectId;
		this.effectName = effectName;
		this.effectValue = effectValue;
		this.effectPackage = effectPackage;
	}
	public SoundEffectInfo() {
		super();
	}
	public String getEffectId() {
		return effectId;
	}
	public void setEffectId(String effectId) {
		this.effectId = effectId;
	}
	public String getEffectName() {
		return effectName;
	}
	public void setEffectName(String effectName) {
		this.effectName = effectName;
	}
	public String getEffectValue() {
		return effectValue;
	}
	public void setEffectValue(String effectValue) {
		this.effectValue = effectValue;
	}
	public String getEffectPackage() {
		return effectPackage;
	}
	public void setEffectPackage(String effectPackage) {
		this.effectPackage = effectPackage;
	}
	
	
	
	
}
