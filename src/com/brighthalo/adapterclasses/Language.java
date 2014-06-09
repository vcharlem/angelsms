package com.brighthalo.adapterclasses;

public class Language {
	
	public String language,code;
	public boolean isDefault;
	public String languageId;
	public Language(String language,String code,boolean isDefault,String langid)
	{
		this.language = language;
		this.code = code;
		this.isDefault = isDefault;
		languageId = langid;
	}

}
