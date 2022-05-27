package android.support.v7.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

class ActivityChooserModel extends DataSetObservable {
  static final String ATTRIBUTE_ACTIVITY = "activity";
  
  static final String ATTRIBUTE_TIME = "time";
  
  static final String ATTRIBUTE_WEIGHT = "weight";
  
  static final boolean DEBUG = false;
  
  private static final int DEFAULT_ACTIVITY_INFLATION = 5;
  
  private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0F;
  
  public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
  
  public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
  
  private static final String HISTORY_FILE_EXTENSION = ".xml";
  
  private static final int INVALID_INDEX = -1;
  
  static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
  
  static final String TAG_HISTORICAL_RECORD = "historical-record";
  
  static final String TAG_HISTORICAL_RECORDS = "historical-records";
  
  private static final Map<String, ActivityChooserModel> sDataModelRegistry;
  
  private static final Object sRegistryLock = new Object();
  
  private final List<ActivityResolveInfo> mActivities = new ArrayList<ActivityResolveInfo>();
  
  private OnChooseActivityListener mActivityChoserModelPolicy;
  
  private ActivitySorter mActivitySorter = new DefaultSorter();
  
  boolean mCanReadHistoricalData = true;
  
  final Context mContext;
  
  private final List<HistoricalRecord> mHistoricalRecords = new ArrayList<HistoricalRecord>();
  
  private boolean mHistoricalRecordsChanged = true;
  
  final String mHistoryFileName;
  
  private int mHistoryMaxSize = 50;
  
  private final Object mInstanceLock = new Object();
  
  private Intent mIntent;
  
  private boolean mReadShareHistoryCalled = false;
  
  private boolean mReloadActivities = false;
  
  static {
    sDataModelRegistry = new HashMap<String, ActivityChooserModel>();
  }
  
  private ActivityChooserModel(Context paramContext, String paramString) {
    this.mContext = paramContext.getApplicationContext();
    if (!TextUtils.isEmpty(paramString) && !paramString.endsWith(".xml")) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append(".xml");
      this.mHistoryFileName = stringBuilder.toString();
    } else {
      this.mHistoryFileName = paramString;
    } 
  }
  
  private boolean addHistoricalRecord(HistoricalRecord paramHistoricalRecord) {
    boolean bool = this.mHistoricalRecords.add(paramHistoricalRecord);
    if (bool) {
      this.mHistoricalRecordsChanged = true;
      pruneExcessiveHistoricalRecordsIfNeeded();
      persistHistoricalDataIfNeeded();
      sortActivitiesIfNeeded();
      notifyChanged();
    } 
    return bool;
  }
  
  private void ensureConsistentState() {
    boolean bool1 = loadActivitiesIfNeeded();
    boolean bool2 = readHistoricalDataIfNeeded();
    pruneExcessiveHistoricalRecordsIfNeeded();
    if (bool1 | bool2) {
      sortActivitiesIfNeeded();
      notifyChanged();
    } 
  }
  
  public static ActivityChooserModel get(Context paramContext, String paramString) {
    synchronized (sRegistryLock) {
      ActivityChooserModel activityChooserModel1 = sDataModelRegistry.get(paramString);
      ActivityChooserModel activityChooserModel2 = activityChooserModel1;
      if (activityChooserModel1 == null) {
        activityChooserModel2 = new ActivityChooserModel();
        this(paramContext, paramString);
        sDataModelRegistry.put(paramString, activityChooserModel2);
      } 
      return activityChooserModel2;
    } 
  }
  
  private boolean loadActivitiesIfNeeded() {
    boolean bool = this.mReloadActivities;
    byte b = 0;
    if (bool && this.mIntent != null) {
      this.mReloadActivities = false;
      this.mActivities.clear();
      List<ResolveInfo> list = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
      int i = list.size();
      while (b < i) {
        ResolveInfo resolveInfo = list.get(b);
        this.mActivities.add(new ActivityResolveInfo(resolveInfo));
        b++;
      } 
      return true;
    } 
    return false;
  }
  
  private void persistHistoricalDataIfNeeded() {
    if (this.mReadShareHistoryCalled) {
      if (!this.mHistoricalRecordsChanged)
        return; 
      this.mHistoricalRecordsChanged = false;
      if (!TextUtils.isEmpty(this.mHistoryFileName))
        (new PersistHistoryAsyncTask()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[] { new ArrayList<HistoricalRecord>(this.mHistoricalRecords), this.mHistoryFileName }); 
      return;
    } 
    throw new IllegalStateException("No preceding call to #readHistoricalData");
  }
  
  private void pruneExcessiveHistoricalRecordsIfNeeded() {
    int i = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
    if (i <= 0)
      return; 
    this.mHistoricalRecordsChanged = true;
    for (byte b = 0; b < i; b++)
      HistoricalRecord historicalRecord = this.mHistoricalRecords.remove(0); 
  }
  
  private boolean readHistoricalDataIfNeeded() {
    if (this.mCanReadHistoricalData && this.mHistoricalRecordsChanged && !TextUtils.isEmpty(this.mHistoryFileName)) {
      this.mCanReadHistoricalData = false;
      this.mReadShareHistoryCalled = true;
      readHistoricalDataImpl();
      return true;
    } 
    return false;
  }
  
  private void readHistoricalDataImpl() {
    try {
      FileInputStream fileInputStream = this.mContext.openFileInput(this.mHistoryFileName);
      try {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(fileInputStream, "UTF-8");
        int i;
        for (i = 0; i != 1 && i != 2; i = xmlPullParser.next());
        if ("historical-records".equals(xmlPullParser.getName())) {
          List<HistoricalRecord> list = this.mHistoricalRecords;
          list.clear();
          while (true) {
            i = xmlPullParser.next();
            if (i == 1) {
              if (fileInputStream != null) {
                try {
                  fileInputStream.close();
                } catch (IOException null) {}
                return;
              } 
            } else {
              if (i == 3 || i == 4)
                continue; 
              if ("historical-record".equals(xmlPullParser.getName())) {
                String str = xmlPullParser.getAttributeValue(null, "activity");
                long l = Long.parseLong(xmlPullParser.getAttributeValue(null, "time"));
                float f = Float.parseFloat(xmlPullParser.getAttributeValue(null, "weight"));
                HistoricalRecord historicalRecord = new HistoricalRecord();
                this(str, l, f);
                list.add(historicalRecord);
                continue;
              } 
              XmlPullParserException xmlPullParserException1 = new XmlPullParserException();
              this("Share records file not well-formed.");
              throw xmlPullParserException1;
            } 
            return;
          } 
        } 
        XmlPullParserException xmlPullParserException = new XmlPullParserException();
        this("Share records file does not start with historical-records tag.");
        throw xmlPullParserException;
      } catch (XmlPullParserException xmlPullParserException) {
        String str = LOG_TAG;
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("Error reading historical recrod file: ");
        stringBuilder.append(this.mHistoryFileName);
        Log.e(str, stringBuilder.toString(), (Throwable)xmlPullParserException);
        if (iOException != null) {
          try {
            iOException.close();
          } catch (IOException iOException) {}
          return;
        } 
      } catch (IOException iOException1) {
        String str = LOG_TAG;
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("Error reading historical recrod file: ");
        stringBuilder.append(this.mHistoryFileName);
        Log.e(str, stringBuilder.toString(), iOException1);
        if (iOException != null) {
          try {
            iOException.close();
          } catch (IOException iOException2) {}
          return;
        } 
      } finally {
        Exception exception;
      } 
      return;
    } catch (FileNotFoundException fileNotFoundException) {
      return;
    } 
  }
  
  private boolean sortActivitiesIfNeeded() {
    if (this.mActivitySorter != null && this.mIntent != null && !this.mActivities.isEmpty() && !this.mHistoricalRecords.isEmpty()) {
      this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
      return true;
    } 
    return false;
  }
  
  public Intent chooseActivity(int paramInt) {
    synchronized (this.mInstanceLock) {
      if (this.mIntent == null)
        return null; 
      ensureConsistentState();
      ActivityResolveInfo activityResolveInfo = this.mActivities.get(paramInt);
      ComponentName componentName = new ComponentName();
      this(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name);
      Intent intent = new Intent();
      this(this.mIntent);
      intent.setComponent(componentName);
      if (this.mActivityChoserModelPolicy != null) {
        Intent intent1 = new Intent();
        this(intent);
        if (this.mActivityChoserModelPolicy.onChooseActivity(this, intent1))
          return null; 
      } 
      HistoricalRecord historicalRecord = new HistoricalRecord();
      this(componentName, System.currentTimeMillis(), 1.0F);
      addHistoricalRecord(historicalRecord);
      return intent;
    } 
  }
  
  public ResolveInfo getActivity(int paramInt) {
    synchronized (this.mInstanceLock) {
      ensureConsistentState();
      return ((ActivityResolveInfo)this.mActivities.get(paramInt)).resolveInfo;
    } 
  }
  
  public int getActivityCount() {
    synchronized (this.mInstanceLock) {
      ensureConsistentState();
      return this.mActivities.size();
    } 
  }
  
  public int getActivityIndex(ResolveInfo paramResolveInfo) {
    synchronized (this.mInstanceLock) {
      ensureConsistentState();
      List<ActivityResolveInfo> list = this.mActivities;
      int i = list.size();
      for (byte b = 0; b < i; b++) {
        if (((ActivityResolveInfo)list.get(b)).resolveInfo == paramResolveInfo)
          return b; 
      } 
      return -1;
    } 
  }
  
  public ResolveInfo getDefaultActivity() {
    synchronized (this.mInstanceLock) {
      ensureConsistentState();
      if (!this.mActivities.isEmpty())
        return ((ActivityResolveInfo)this.mActivities.get(0)).resolveInfo; 
      return null;
    } 
  }
  
  public int getHistoryMaxSize() {
    synchronized (this.mInstanceLock) {
      return this.mHistoryMaxSize;
    } 
  }
  
  public int getHistorySize() {
    synchronized (this.mInstanceLock) {
      ensureConsistentState();
      return this.mHistoricalRecords.size();
    } 
  }
  
  public Intent getIntent() {
    synchronized (this.mInstanceLock) {
      return this.mIntent;
    } 
  }
  
  public void setActivitySorter(ActivitySorter paramActivitySorter) {
    synchronized (this.mInstanceLock) {
      if (this.mActivitySorter == paramActivitySorter)
        return; 
      this.mActivitySorter = paramActivitySorter;
      if (sortActivitiesIfNeeded())
        notifyChanged(); 
      return;
    } 
  }
  
  public void setDefaultActivity(int paramInt) {
    synchronized (this.mInstanceLock) {
      float f;
      ensureConsistentState();
      ActivityResolveInfo activityResolveInfo1 = this.mActivities.get(paramInt);
      ActivityResolveInfo activityResolveInfo2 = this.mActivities.get(0);
      if (activityResolveInfo2 != null) {
        f = activityResolveInfo2.weight - activityResolveInfo1.weight + 5.0F;
      } else {
        f = 1.0F;
      } 
      ComponentName componentName = new ComponentName();
      this(activityResolveInfo1.resolveInfo.activityInfo.packageName, activityResolveInfo1.resolveInfo.activityInfo.name);
      HistoricalRecord historicalRecord = new HistoricalRecord();
      this(componentName, System.currentTimeMillis(), f);
      addHistoricalRecord(historicalRecord);
      return;
    } 
  }
  
  public void setHistoryMaxSize(int paramInt) {
    synchronized (this.mInstanceLock) {
      if (this.mHistoryMaxSize == paramInt)
        return; 
      this.mHistoryMaxSize = paramInt;
      pruneExcessiveHistoricalRecordsIfNeeded();
      if (sortActivitiesIfNeeded())
        notifyChanged(); 
      return;
    } 
  }
  
  public void setIntent(Intent paramIntent) {
    synchronized (this.mInstanceLock) {
      if (this.mIntent == paramIntent)
        return; 
      this.mIntent = paramIntent;
      this.mReloadActivities = true;
      ensureConsistentState();
      return;
    } 
  }
  
  public void setOnChooseActivityListener(OnChooseActivityListener paramOnChooseActivityListener) {
    synchronized (this.mInstanceLock) {
      this.mActivityChoserModelPolicy = paramOnChooseActivityListener;
      return;
    } 
  }
  
  public static interface ActivityChooserModelClient {
    void setActivityChooserModel(ActivityChooserModel param1ActivityChooserModel);
  }
  
  public static final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
    public final ResolveInfo resolveInfo;
    
    public float weight;
    
    public ActivityResolveInfo(ResolveInfo param1ResolveInfo) {
      this.resolveInfo = param1ResolveInfo;
    }
    
    public int compareTo(ActivityResolveInfo param1ActivityResolveInfo) {
      return Float.floatToIntBits(param1ActivityResolveInfo.weight) - Float.floatToIntBits(this.weight);
    }
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      if (param1Object == null)
        return false; 
      if (getClass() != param1Object.getClass())
        return false; 
      param1Object = param1Object;
      return !(Float.floatToIntBits(this.weight) != Float.floatToIntBits(((ActivityResolveInfo)param1Object).weight));
    }
    
    public int hashCode() {
      return Float.floatToIntBits(this.weight) + 31;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("[");
      stringBuilder.append("resolveInfo:");
      stringBuilder.append(this.resolveInfo.toString());
      stringBuilder.append("; weight:");
      stringBuilder.append(new BigDecimal(this.weight));
      stringBuilder.append("]");
      return stringBuilder.toString();
    }
  }
  
  public static interface ActivitySorter {
    void sort(Intent param1Intent, List<ActivityChooserModel.ActivityResolveInfo> param1List, List<ActivityChooserModel.HistoricalRecord> param1List1);
  }
  
  private static final class DefaultSorter implements ActivitySorter {
    private static final float WEIGHT_DECAY_COEFFICIENT = 0.95F;
    
    private final Map<ComponentName, ActivityChooserModel.ActivityResolveInfo> mPackageNameToActivityMap = new HashMap<ComponentName, ActivityChooserModel.ActivityResolveInfo>();
    
    public void sort(Intent param1Intent, List<ActivityChooserModel.ActivityResolveInfo> param1List, List<ActivityChooserModel.HistoricalRecord> param1List1) {
      Map<ComponentName, ActivityChooserModel.ActivityResolveInfo> map = this.mPackageNameToActivityMap;
      map.clear();
      int i = param1List.size();
      int j;
      for (j = 0; j < i; j++) {
        ActivityChooserModel.ActivityResolveInfo activityResolveInfo = param1List.get(j);
        activityResolveInfo.weight = 0.0F;
        map.put(new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), activityResolveInfo);
      } 
      j = param1List1.size() - 1;
      float f;
      for (f = 1.0F; j >= 0; f = f1) {
        ActivityChooserModel.HistoricalRecord historicalRecord = param1List1.get(j);
        ActivityChooserModel.ActivityResolveInfo activityResolveInfo = map.get(historicalRecord.activity);
        float f1 = f;
        if (activityResolveInfo != null) {
          activityResolveInfo.weight += historicalRecord.weight * f;
          f1 = f * 0.95F;
        } 
        j--;
      } 
      Collections.sort(param1List);
    }
  }
  
  public static final class HistoricalRecord {
    public final ComponentName activity;
    
    public final long time;
    
    public final float weight;
    
    public HistoricalRecord(ComponentName param1ComponentName, long param1Long, float param1Float) {
      this.activity = param1ComponentName;
      this.time = param1Long;
      this.weight = param1Float;
    }
    
    public HistoricalRecord(String param1String, long param1Long, float param1Float) {
      this(ComponentName.unflattenFromString(param1String), param1Long, param1Float);
    }
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      if (param1Object == null)
        return false; 
      if (getClass() != param1Object.getClass())
        return false; 
      HistoricalRecord historicalRecord = (HistoricalRecord)param1Object;
      param1Object = this.activity;
      if (param1Object == null) {
        if (historicalRecord.activity != null)
          return false; 
      } else if (!param1Object.equals(historicalRecord.activity)) {
        return false;
      } 
      return (this.time != historicalRecord.time) ? false : (!(Float.floatToIntBits(this.weight) != Float.floatToIntBits(historicalRecord.weight)));
    }
    
    public int hashCode() {
      int i;
      ComponentName componentName = this.activity;
      if (componentName == null) {
        i = 0;
      } else {
        i = componentName.hashCode();
      } 
      long l = this.time;
      return ((i + 31) * 31 + (int)(l ^ l >>> 32L)) * 31 + Float.floatToIntBits(this.weight);
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("[");
      stringBuilder.append("; activity:");
      stringBuilder.append(this.activity);
      stringBuilder.append("; time:");
      stringBuilder.append(this.time);
      stringBuilder.append("; weight:");
      stringBuilder.append(new BigDecimal(this.weight));
      stringBuilder.append("]");
      return stringBuilder.toString();
    }
  }
  
  public static interface OnChooseActivityListener {
    boolean onChooseActivity(ActivityChooserModel param1ActivityChooserModel, Intent param1Intent);
  }
  
  private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
    public Void doInBackground(Object... param1VarArgs) {
      List<ActivityChooserModel.HistoricalRecord> list = (List)param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      try {
        FileOutputStream fileOutputStream = ActivityChooserModel.this.mContext.openFileOutput(str, 0);
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
          xmlSerializer.setOutput(fileOutputStream, null);
          xmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
          xmlSerializer.startTag(null, "historical-records");
          int i = list.size();
          for (byte b = 0; b < i; b++) {
            ActivityChooserModel.HistoricalRecord historicalRecord = list.remove(0);
            xmlSerializer.startTag(null, "historical-record");
            xmlSerializer.attribute(null, "activity", historicalRecord.activity.flattenToString());
            xmlSerializer.attribute(null, "time", String.valueOf(historicalRecord.time));
            xmlSerializer.attribute(null, "weight", String.valueOf(historicalRecord.weight));
            xmlSerializer.endTag(null, "historical-record");
          } 
          xmlSerializer.endTag(null, "historical-records");
          xmlSerializer.endDocument();
          ActivityChooserModel.this.mCanReadHistoricalData = true;
          if (fileOutputStream != null) {
            try {
              fileOutputStream.close();
            } catch (IOException null) {}
            return null;
          } 
        } catch (IllegalArgumentException illegalArgumentException) {
          String str1 = ActivityChooserModel.LOG_TAG;
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("Error writing historical record file: ");
          stringBuilder.append(ActivityChooserModel.this.mHistoryFileName);
          Log.e(str1, stringBuilder.toString(), illegalArgumentException);
          ActivityChooserModel.this.mCanReadHistoricalData = true;
          if (iOException != null) {
            try {
              iOException.close();
            } catch (IOException null) {}
            return null;
          } 
        } catch (IllegalStateException illegalStateException) {
          String str1 = ActivityChooserModel.LOG_TAG;
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("Error writing historical record file: ");
          stringBuilder.append(ActivityChooserModel.this.mHistoryFileName);
          Log.e(str1, stringBuilder.toString(), illegalStateException);
          ActivityChooserModel.this.mCanReadHistoricalData = true;
          if (iOException != null) {
            try {
              iOException.close();
            } catch (IOException iOException) {}
            return null;
          } 
        } catch (IOException iOException1) {
          str = ActivityChooserModel.LOG_TAG;
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("Error writing historical record file: ");
          stringBuilder.append(ActivityChooserModel.this.mHistoryFileName);
          Log.e(str, stringBuilder.toString(), iOException1);
          ActivityChooserModel.this.mCanReadHistoricalData = true;
          if (iOException != null) {
            try {
              iOException.close();
            } catch (IOException iOException2) {}
            return null;
          } 
        } finally {}
        return null;
      } catch (FileNotFoundException fileNotFoundException) {
        String str1 = ActivityChooserModel.LOG_TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error writing historical record file: ");
        stringBuilder.append(str);
        Log.e(str1, stringBuilder.toString(), fileNotFoundException);
        return null;
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ActivityChooserModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */