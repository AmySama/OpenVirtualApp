package com.lody.virtual.helper;

import android.os.Bundle;
import android.os.Parcel;
import java.util.HashMap;
import java.util.Map;

public class ParcelHelper {
  public static Bundle readMeta(Parcel paramParcel) {
    Bundle bundle = new Bundle();
    for (Map.Entry entry : paramParcel.readHashMap(String.class.getClassLoader()).entrySet())
      bundle.putString((String)entry.getKey(), (String)entry.getValue()); 
    return bundle;
  }
  
  public static void writeMeta(Parcel paramParcel, Bundle paramBundle) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    if (paramBundle != null)
      for (String str : paramBundle.keySet())
        hashMap.put(str, paramBundle.getString(str));  
    paramParcel.writeMap(hashMap);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\ParcelHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */