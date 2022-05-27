package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public final class LocalBroadcastManager {
  private static final boolean DEBUG = false;
  
  static final int MSG_EXEC_PENDING_BROADCASTS = 1;
  
  private static final String TAG = "LocalBroadcastManager";
  
  private static LocalBroadcastManager mInstance;
  
  private static final Object mLock = new Object();
  
  private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<String, ArrayList<ReceiverRecord>>();
  
  private final Context mAppContext;
  
  private final Handler mHandler;
  
  private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<BroadcastRecord>();
  
  private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>>();
  
  private LocalBroadcastManager(Context paramContext) {
    this.mAppContext = paramContext;
    this.mHandler = new Handler(paramContext.getMainLooper()) {
        public void handleMessage(Message param1Message) {
          if (param1Message.what != 1) {
            super.handleMessage(param1Message);
          } else {
            LocalBroadcastManager.this.executePendingBroadcasts();
          } 
        }
      };
  }
  
  private void executePendingBroadcasts() {
    while (true) {
      HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> hashMap;
      BroadcastRecord broadcastRecord;
      synchronized (this.mReceivers) {
        int i = this.mPendingBroadcasts.size();
        if (i <= 0)
          return; 
        BroadcastRecord[] arrayOfBroadcastRecord = new BroadcastRecord[i];
        this.mPendingBroadcasts.toArray(arrayOfBroadcastRecord);
        this.mPendingBroadcasts.clear();
        for (byte b = 0; b < i; b++) {
          broadcastRecord = arrayOfBroadcastRecord[b];
          int j = broadcastRecord.receivers.size();
          for (byte b1 = 0; b1 < j; b1++) {
            ReceiverRecord receiverRecord = broadcastRecord.receivers.get(b1);
            if (!receiverRecord.dead)
              receiverRecord.receiver.onReceive(this.mAppContext, broadcastRecord.intent); 
          } 
        } 
      } 
    } 
  }
  
  public static LocalBroadcastManager getInstance(Context paramContext) {
    synchronized (mLock) {
      if (mInstance == null) {
        LocalBroadcastManager localBroadcastManager = new LocalBroadcastManager();
        this(paramContext.getApplicationContext());
        mInstance = localBroadcastManager;
      } 
      return mInstance;
    } 
  }
  
  public void registerReceiver(BroadcastReceiver paramBroadcastReceiver, IntentFilter paramIntentFilter) {
    synchronized (this.mReceivers) {
      ReceiverRecord receiverRecord = new ReceiverRecord();
      this(paramIntentFilter, paramBroadcastReceiver);
      ArrayList<ReceiverRecord> arrayList1 = this.mReceivers.get(paramBroadcastReceiver);
      ArrayList<ReceiverRecord> arrayList2 = arrayList1;
      if (arrayList1 == null) {
        arrayList2 = new ArrayList();
        this(1);
        this.mReceivers.put(paramBroadcastReceiver, arrayList2);
      } 
      arrayList2.add(receiverRecord);
      for (byte b = 0; b < paramIntentFilter.countActions(); b++) {
        String str = paramIntentFilter.getAction(b);
        arrayList2 = this.mActions.get(str);
        ArrayList<ReceiverRecord> arrayList = arrayList2;
        if (arrayList2 == null) {
          arrayList = new ArrayList<ReceiverRecord>();
          this(1);
          this.mActions.put(str, arrayList);
        } 
        arrayList.add(receiverRecord);
      } 
      return;
    } 
  }
  
  public boolean sendBroadcast(Intent paramIntent) {
    synchronized (this.mReceivers) {
      byte b;
      String str1 = paramIntent.getAction();
      String str2 = paramIntent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
      Uri uri = paramIntent.getData();
      String str3 = paramIntent.getScheme();
      Set set = paramIntent.getCategories();
      if ((paramIntent.getFlags() & 0x8) != 0) {
        b = 1;
      } else {
        b = 0;
      } 
      if (b) {
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("Resolving type ");
        stringBuilder.append(str2);
        stringBuilder.append(" scheme ");
        stringBuilder.append(str3);
        stringBuilder.append(" of intent ");
        stringBuilder.append(paramIntent);
        Log.v("LocalBroadcastManager", stringBuilder.toString());
      } 
      ArrayList<ReceiverRecord> arrayList = this.mActions.get(paramIntent.getAction());
      if (arrayList != null) {
        if (b) {
          StringBuilder stringBuilder1 = new StringBuilder();
          this();
          stringBuilder1.append("Action list: ");
          stringBuilder1.append(arrayList);
          Log.v("LocalBroadcastManager", stringBuilder1.toString());
        } 
        StringBuilder stringBuilder = null;
        for (byte b1 = 0; b1 < arrayList.size(); b1++) {
          ReceiverRecord receiverRecord = arrayList.get(b1);
          if (b) {
            StringBuilder stringBuilder1 = new StringBuilder();
            this();
            stringBuilder1.append("Matching against filter ");
            stringBuilder1.append(receiverRecord.filter);
            Log.v("LocalBroadcastManager", stringBuilder1.toString());
          } 
          if (receiverRecord.broadcasting) {
            if (b)
              Log.v("LocalBroadcastManager", "  Filter's target already added"); 
          } else {
            IntentFilter intentFilter = receiverRecord.filter;
            StringBuilder stringBuilder1 = stringBuilder;
            int i = intentFilter.match(str1, str2, str3, uri, set, "LocalBroadcastManager");
            if (i >= 0) {
              if (b) {
                stringBuilder = new StringBuilder();
                this();
                stringBuilder.append("  Filter matched!  match=0x");
                stringBuilder.append(Integer.toHexString(i));
                Log.v("LocalBroadcastManager", stringBuilder.toString());
              } 
              if (stringBuilder1 == null) {
                ArrayList arrayList1 = new ArrayList();
                this();
              } else {
                stringBuilder = stringBuilder1;
              } 
              stringBuilder.add(receiverRecord);
              receiverRecord.broadcasting = true;
            } else if (b) {
              String str;
              if (i != -4) {
                if (i != -3) {
                  if (i != -2) {
                    if (i != -1) {
                      str = "unknown reason";
                    } else {
                      str = "type";
                    } 
                  } else {
                    str = "data";
                  } 
                } else {
                  str = "action";
                } 
              } else {
                str = "category";
              } 
              StringBuilder stringBuilder2 = new StringBuilder();
              this();
              stringBuilder2.append("  Filter did not match: ");
              stringBuilder2.append(str);
              Log.v("LocalBroadcastManager", stringBuilder2.toString());
            } 
          } 
        } 
        if (stringBuilder != null) {
          for (b = 0; b < stringBuilder.size(); b++)
            ((ReceiverRecord)stringBuilder.get(b)).broadcasting = false; 
          ArrayList<BroadcastRecord> arrayList1 = this.mPendingBroadcasts;
          BroadcastRecord broadcastRecord = new BroadcastRecord();
          this(paramIntent, (ArrayList<ReceiverRecord>)stringBuilder);
          arrayList1.add(broadcastRecord);
          if (!this.mHandler.hasMessages(1))
            this.mHandler.sendEmptyMessage(1); 
          return true;
        } 
      } 
      return false;
    } 
  }
  
  public void sendBroadcastSync(Intent paramIntent) {
    if (sendBroadcast(paramIntent))
      executePendingBroadcasts(); 
  }
  
  public void unregisterReceiver(BroadcastReceiver paramBroadcastReceiver) {
    synchronized (this.mReceivers) {
      ArrayList<ReceiverRecord> arrayList = this.mReceivers.remove(paramBroadcastReceiver);
      if (arrayList == null)
        return; 
      for (int i = arrayList.size() - 1; i >= 0; i--) {
        ReceiverRecord receiverRecord = arrayList.get(i);
        receiverRecord.dead = true;
        for (byte b = 0; b < receiverRecord.filter.countActions(); b++) {
          String str = receiverRecord.filter.getAction(b);
          ArrayList<ReceiverRecord> arrayList1 = this.mActions.get(str);
          if (arrayList1 != null) {
            for (int j = arrayList1.size() - 1; j >= 0; j--) {
              ReceiverRecord receiverRecord1 = arrayList1.get(j);
              if (receiverRecord1.receiver == paramBroadcastReceiver) {
                receiverRecord1.dead = true;
                arrayList1.remove(j);
              } 
            } 
            if (arrayList1.size() <= 0)
              this.mActions.remove(str); 
          } 
        } 
      } 
      return;
    } 
  }
  
  private static final class BroadcastRecord {
    final Intent intent;
    
    final ArrayList<LocalBroadcastManager.ReceiverRecord> receivers;
    
    BroadcastRecord(Intent param1Intent, ArrayList<LocalBroadcastManager.ReceiverRecord> param1ArrayList) {
      this.intent = param1Intent;
      this.receivers = param1ArrayList;
    }
  }
  
  private static final class ReceiverRecord {
    boolean broadcasting;
    
    boolean dead;
    
    final IntentFilter filter;
    
    final BroadcastReceiver receiver;
    
    ReceiverRecord(IntentFilter param1IntentFilter, BroadcastReceiver param1BroadcastReceiver) {
      this.filter = param1IntentFilter;
      this.receiver = param1BroadcastReceiver;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder(128);
      stringBuilder.append("Receiver{");
      stringBuilder.append(this.receiver);
      stringBuilder.append(" filter=");
      stringBuilder.append(this.filter);
      if (this.dead)
        stringBuilder.append(" DEAD"); 
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\LocalBroadcastManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */