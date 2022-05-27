package com.lody.virtual.remote;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.regex.Pattern;

public class SettingRuleInfo implements Parcelable {
  public static final Parcelable.Creator<SettingRuleInfo> CREATOR = new Parcelable.Creator<SettingRuleInfo>() {
      public SettingRuleInfo createFromParcel(Parcel param1Parcel) {
        return new SettingRuleInfo(param1Parcel);
      }
      
      public SettingRuleInfo[] newArray(int param1Int) {
        return new SettingRuleInfo[param1Int];
      }
    };
  
  private transient Pattern pattern;
  
  public boolean regex;
  
  public int rule;
  
  public String word;
  
  public SettingRuleInfo() {}
  
  public SettingRuleInfo(int paramInt, String paramString, boolean paramBoolean) {
    this.rule = paramInt;
    this.word = paramString;
    this.regex = paramBoolean;
  }
  
  protected SettingRuleInfo(Parcel paramParcel) {
    boolean bool;
    this.rule = paramParcel.readInt();
    this.word = paramParcel.readString();
    if (paramParcel.readByte() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.regex = bool;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    if (this.rule != ((SettingRuleInfo)paramObject).rule || this.regex != ((SettingRuleInfo)paramObject).regex || !TextUtils.equals(this.word, ((SettingRuleInfo)paramObject).word))
      bool = false; 
    return bool;
  }
  
  public int hashCode() {
    return Arrays.hashCode(new Object[] { Integer.valueOf(this.rule), this.word, Boolean.valueOf(this.regex) });
  }
  
  public boolean matches(String paramString) {
    if (!this.regex)
      return TextUtils.equals(paramString, this.word); 
    try {
      if (this.pattern == null)
        this.pattern = Pattern.compile(this.word); 
      return this.pattern.matcher(paramString).matches();
    } catch (Exception exception) {
      return false;
    } 
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.rule);
    paramParcel.writeString(this.word);
    paramParcel.writeByte(this.regex);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\SettingRuleInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */