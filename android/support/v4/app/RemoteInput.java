package android.support.v4.app;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class RemoteInput {
  private static final String EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData";
  
  public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
  
  public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";
  
  private static final String TAG = "RemoteInput";
  
  private final boolean mAllowFreeFormTextInput;
  
  private final Set<String> mAllowedDataTypes;
  
  private final CharSequence[] mChoices;
  
  private final Bundle mExtras;
  
  private final CharSequence mLabel;
  
  private final String mResultKey;
  
  RemoteInput(String paramString, CharSequence paramCharSequence, CharSequence[] paramArrayOfCharSequence, boolean paramBoolean, Bundle paramBundle, Set<String> paramSet) {
    this.mResultKey = paramString;
    this.mLabel = paramCharSequence;
    this.mChoices = paramArrayOfCharSequence;
    this.mAllowFreeFormTextInput = paramBoolean;
    this.mExtras = paramBundle;
    this.mAllowedDataTypes = paramSet;
  }
  
  public static void addDataResultToIntent(RemoteInput paramRemoteInput, Intent paramIntent, Map<String, Uri> paramMap) {
    if (Build.VERSION.SDK_INT >= 26) {
      android.app.RemoteInput.addDataResultToIntent(fromCompat(paramRemoteInput), paramIntent, paramMap);
    } else if (Build.VERSION.SDK_INT >= 16) {
      Intent intent1 = getClipDataIntentFromIntent(paramIntent);
      Intent intent2 = intent1;
      if (intent1 == null)
        intent2 = new Intent(); 
      for (Map.Entry<String, Uri> entry : paramMap.entrySet()) {
        String str = (String)entry.getKey();
        Uri uri = (Uri)entry.getValue();
        if (str == null)
          continue; 
        Bundle bundle2 = intent2.getBundleExtra(getExtraResultsKeyForData(str));
        Bundle bundle1 = bundle2;
        if (bundle2 == null)
          bundle1 = new Bundle(); 
        bundle1.putString(paramRemoteInput.getResultKey(), uri.toString());
        intent2.putExtra(getExtraResultsKeyForData(str), bundle1);
      } 
      paramIntent.setClipData(ClipData.newIntent("android.remoteinput.results", intent2));
    } else {
      Log.w("RemoteInput", "RemoteInput is only supported from API Level 16");
    } 
  }
  
  public static void addResultsToIntent(RemoteInput[] paramArrayOfRemoteInput, Intent paramIntent, Bundle paramBundle) {
    if (Build.VERSION.SDK_INT >= 26) {
      android.app.RemoteInput.addResultsToIntent(fromCompat(paramArrayOfRemoteInput), paramIntent, paramBundle);
    } else {
      int i = Build.VERSION.SDK_INT;
      byte b = 0;
      if (i >= 20) {
        Bundle bundle = getResultsFromIntent(paramIntent);
        if (bundle != null) {
          bundle.putAll(paramBundle);
          paramBundle = bundle;
        } 
        i = paramArrayOfRemoteInput.length;
        for (b = 0; b < i; b++) {
          RemoteInput remoteInput = paramArrayOfRemoteInput[b];
          Map<String, Uri> map = getDataResultsFromIntent(paramIntent, remoteInput.getResultKey());
          android.app.RemoteInput.addResultsToIntent(fromCompat(new RemoteInput[] { remoteInput }, ), paramIntent, paramBundle);
          if (map != null)
            addDataResultToIntent(remoteInput, paramIntent, map); 
        } 
      } else if (Build.VERSION.SDK_INT >= 16) {
        Intent intent2 = getClipDataIntentFromIntent(paramIntent);
        Intent intent1 = intent2;
        if (intent2 == null)
          intent1 = new Intent(); 
        Bundle bundle2 = intent1.getBundleExtra("android.remoteinput.resultsData");
        Bundle bundle1 = bundle2;
        if (bundle2 == null)
          bundle1 = new Bundle(); 
        i = paramArrayOfRemoteInput.length;
        while (b < i) {
          RemoteInput remoteInput = paramArrayOfRemoteInput[b];
          Object object = paramBundle.get(remoteInput.getResultKey());
          if (object instanceof CharSequence)
            bundle1.putCharSequence(remoteInput.getResultKey(), (CharSequence)object); 
          b++;
        } 
        intent1.putExtra("android.remoteinput.resultsData", bundle1);
        paramIntent.setClipData(ClipData.newIntent("android.remoteinput.results", intent1));
      } else {
        Log.w("RemoteInput", "RemoteInput is only supported from API Level 16");
      } 
    } 
  }
  
  static android.app.RemoteInput fromCompat(RemoteInput paramRemoteInput) {
    return (new android.app.RemoteInput.Builder(paramRemoteInput.getResultKey())).setLabel(paramRemoteInput.getLabel()).setChoices(paramRemoteInput.getChoices()).setAllowFreeFormInput(paramRemoteInput.getAllowFreeFormInput()).addExtras(paramRemoteInput.getExtras()).build();
  }
  
  static android.app.RemoteInput[] fromCompat(RemoteInput[] paramArrayOfRemoteInput) {
    if (paramArrayOfRemoteInput == null)
      return null; 
    android.app.RemoteInput[] arrayOfRemoteInput = new android.app.RemoteInput[paramArrayOfRemoteInput.length];
    for (byte b = 0; b < paramArrayOfRemoteInput.length; b++)
      arrayOfRemoteInput[b] = fromCompat(paramArrayOfRemoteInput[b]); 
    return arrayOfRemoteInput;
  }
  
  private static Intent getClipDataIntentFromIntent(Intent paramIntent) {
    ClipData clipData = paramIntent.getClipData();
    if (clipData == null)
      return null; 
    ClipDescription clipDescription = clipData.getDescription();
    return !clipDescription.hasMimeType("text/vnd.android.intent") ? null : (!clipDescription.getLabel().equals("android.remoteinput.results") ? null : clipData.getItemAt(0).getIntent());
  }
  
  public static Map<String, Uri> getDataResultsFromIntent(Intent paramIntent, String paramString) {
    if (Build.VERSION.SDK_INT >= 26)
      return android.app.RemoteInput.getDataResultsFromIntent(paramIntent, paramString); 
    int i = Build.VERSION.SDK_INT;
    HashMap hashMap = null;
    if (i >= 16) {
      Intent intent = getClipDataIntentFromIntent(paramIntent);
      if (intent == null)
        return null; 
      HashMap<Object, Object> hashMap1 = new HashMap<Object, Object>();
      for (String str : intent.getExtras().keySet()) {
        if (str.startsWith("android.remoteinput.dataTypeResultsData")) {
          String str1 = str.substring(39);
          if (str1.isEmpty())
            continue; 
          str = intent.getBundleExtra(str).getString(paramString);
          if (str == null || str.isEmpty())
            continue; 
          hashMap1.put(str1, Uri.parse(str));
        } 
      } 
      if (hashMap1.isEmpty())
        hashMap1 = hashMap; 
      return (Map)hashMap1;
    } 
    Log.w("RemoteInput", "RemoteInput is only supported from API Level 16");
    return null;
  }
  
  private static String getExtraResultsKeyForData(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("android.remoteinput.dataTypeResultsData");
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  public static Bundle getResultsFromIntent(Intent paramIntent) {
    if (Build.VERSION.SDK_INT >= 20)
      return android.app.RemoteInput.getResultsFromIntent(paramIntent); 
    if (Build.VERSION.SDK_INT >= 16) {
      paramIntent = getClipDataIntentFromIntent(paramIntent);
      return (paramIntent == null) ? null : (Bundle)paramIntent.getExtras().getParcelable("android.remoteinput.resultsData");
    } 
    Log.w("RemoteInput", "RemoteInput is only supported from API Level 16");
    return null;
  }
  
  public boolean getAllowFreeFormInput() {
    return this.mAllowFreeFormTextInput;
  }
  
  public Set<String> getAllowedDataTypes() {
    return this.mAllowedDataTypes;
  }
  
  public CharSequence[] getChoices() {
    return this.mChoices;
  }
  
  public Bundle getExtras() {
    return this.mExtras;
  }
  
  public CharSequence getLabel() {
    return this.mLabel;
  }
  
  public String getResultKey() {
    return this.mResultKey;
  }
  
  public boolean isDataOnly() {
    boolean bool;
    if (!getAllowFreeFormInput() && (getChoices() == null || (getChoices()).length == 0) && getAllowedDataTypes() != null && !getAllowedDataTypes().isEmpty()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static final class Builder {
    private boolean mAllowFreeFormTextInput = true;
    
    private final Set<String> mAllowedDataTypes = new HashSet<String>();
    
    private CharSequence[] mChoices;
    
    private Bundle mExtras = new Bundle();
    
    private CharSequence mLabel;
    
    private final String mResultKey;
    
    public Builder(String param1String) {
      if (param1String != null) {
        this.mResultKey = param1String;
        return;
      } 
      throw new IllegalArgumentException("Result key can't be null");
    }
    
    public Builder addExtras(Bundle param1Bundle) {
      if (param1Bundle != null)
        this.mExtras.putAll(param1Bundle); 
      return this;
    }
    
    public RemoteInput build() {
      return new RemoteInput(this.mResultKey, this.mLabel, this.mChoices, this.mAllowFreeFormTextInput, this.mExtras, this.mAllowedDataTypes);
    }
    
    public Bundle getExtras() {
      return this.mExtras;
    }
    
    public Builder setAllowDataType(String param1String, boolean param1Boolean) {
      if (param1Boolean) {
        this.mAllowedDataTypes.add(param1String);
      } else {
        this.mAllowedDataTypes.remove(param1String);
      } 
      return this;
    }
    
    public Builder setAllowFreeFormInput(boolean param1Boolean) {
      this.mAllowFreeFormTextInput = param1Boolean;
      return this;
    }
    
    public Builder setChoices(CharSequence[] param1ArrayOfCharSequence) {
      this.mChoices = param1ArrayOfCharSequence;
      return this;
    }
    
    public Builder setLabel(CharSequence param1CharSequence) {
      this.mLabel = param1CharSequence;
      return this;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\RemoteInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */