package com.lody.virtual.client.ipc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.compat.ContentProviderCompat;
import java.io.Serializable;

public class ProviderCall {
  public static Bundle call(String paramString1, Context paramContext, String paramString2, String paramString3, Bundle paramBundle, int paramInt) throws IllegalAccessException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("content://");
    stringBuilder.append(paramString1);
    return ContentProviderCompat.call(paramContext, Uri.parse(stringBuilder.toString()), paramString2, paramString3, paramBundle, paramInt);
  }
  
  public static Bundle callSafely(String paramString1, String paramString2, String paramString3, Bundle paramBundle) {
    return callSafely(paramString1, paramString2, paramString3, paramBundle, 3);
  }
  
  public static Bundle callSafely(String paramString1, String paramString2, String paramString3, Bundle paramBundle, int paramInt) {
    try {
      return call(paramString1, VirtualCore.get().getContext(), paramString2, paramString3, paramBundle, paramInt);
    } catch (IllegalAccessException illegalAccessException) {
      illegalAccessException.printStackTrace();
      return null;
    } 
  }
  
  public static final class Builder {
    private String arg;
    
    private String auth;
    
    private Bundle bundle = new Bundle();
    
    private Context context;
    
    private String method;
    
    private int retryCount = 5;
    
    public Builder(Context param1Context, String param1String) {
      this.context = param1Context;
      this.auth = param1String;
    }
    
    public Builder addArg(String param1String, Object param1Object) {
      if (param1Object != null)
        if (param1Object instanceof Boolean) {
          this.bundle.putBoolean(param1String, ((Boolean)param1Object).booleanValue());
        } else if (param1Object instanceof Integer) {
          this.bundle.putInt(param1String, ((Integer)param1Object).intValue());
        } else if (param1Object instanceof String) {
          this.bundle.putString(param1String, (String)param1Object);
        } else if (param1Object instanceof Serializable) {
          this.bundle.putSerializable(param1String, (Serializable)param1Object);
        } else if (param1Object instanceof Bundle) {
          this.bundle.putBundle(param1String, (Bundle)param1Object);
        } else if (param1Object instanceof Parcelable) {
          this.bundle.putParcelable(param1String, (Parcelable)param1Object);
        } else if (param1Object instanceof int[]) {
          this.bundle.putIntArray(param1String, (int[])param1Object);
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Unknown type ");
          stringBuilder.append(param1Object.getClass());
          stringBuilder.append(" in Bundle.");
          throw new IllegalArgumentException(stringBuilder.toString());
        }  
      return this;
    }
    
    public Builder arg(String param1String) {
      this.arg = param1String;
      return this;
    }
    
    public Bundle call() throws IllegalAccessException {
      return ProviderCall.call(this.auth, this.context, this.method, this.arg, this.bundle, this.retryCount);
    }
    
    public Bundle callSafely() {
      try {
        return call();
      } catch (IllegalAccessException illegalAccessException) {
        illegalAccessException.printStackTrace();
        return null;
      } 
    }
    
    public Builder methodName(String param1String) {
      this.method = param1String;
      return this;
    }
    
    public Builder retry(int param1Int) {
      this.retryCount = param1Int;
      return this;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\ProviderCall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */