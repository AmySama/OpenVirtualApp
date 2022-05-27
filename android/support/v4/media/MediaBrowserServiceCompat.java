package android.support.v4.media;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class MediaBrowserServiceCompat extends Service {
  static final boolean DEBUG = Log.isLoggable("MBServiceCompat", 3);
  
  private static final float EPSILON = 1.0E-5F;
  
  public static final String KEY_MEDIA_ITEM = "media_item";
  
  public static final String KEY_SEARCH_RESULTS = "search_results";
  
  static final int RESULT_ERROR = -1;
  
  static final int RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED = 2;
  
  static final int RESULT_FLAG_ON_SEARCH_NOT_IMPLEMENTED = 4;
  
  static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
  
  static final int RESULT_OK = 0;
  
  static final int RESULT_PROGRESS_UPDATE = 1;
  
  public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
  
  static final String TAG = "MBServiceCompat";
  
  final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap();
  
  ConnectionRecord mCurConnection;
  
  final ServiceHandler mHandler = new ServiceHandler();
  
  private MediaBrowserServiceImpl mImpl;
  
  MediaSessionCompat.Token mSession;
  
  void addSubscription(String paramString, ConnectionRecord paramConnectionRecord, IBinder paramIBinder, Bundle paramBundle) {
    List<Pair> list1 = (List)paramConnectionRecord.subscriptions.get(paramString);
    List<Pair> list2 = list1;
    if (list1 == null)
      list2 = new ArrayList(); 
    for (Pair pair : list2) {
      if (paramIBinder == pair.first && MediaBrowserCompatUtils.areSameOptions(paramBundle, (Bundle)pair.second))
        return; 
    } 
    list2.add(new Pair(paramIBinder, paramBundle));
    paramConnectionRecord.subscriptions.put(paramString, list2);
    performLoadChildren(paramString, paramConnectionRecord, paramBundle);
  }
  
  List<MediaBrowserCompat.MediaItem> applyOptions(List<MediaBrowserCompat.MediaItem> paramList, Bundle paramBundle) {
    if (paramList == null)
      return null; 
    int i = paramBundle.getInt("android.media.browse.extra.PAGE", -1);
    int j = paramBundle.getInt("android.media.browse.extra.PAGE_SIZE", -1);
    if (i == -1 && j == -1)
      return paramList; 
    int k = j * i;
    int m = k + j;
    if (i < 0 || j < 1 || k >= paramList.size())
      return Collections.EMPTY_LIST; 
    i = m;
    if (m > paramList.size())
      i = paramList.size(); 
    return paramList.subList(k, i);
  }
  
  public void dump(FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {}
  
  public final Bundle getBrowserRootHints() {
    return this.mImpl.getBrowserRootHints();
  }
  
  public MediaSessionCompat.Token getSessionToken() {
    return this.mSession;
  }
  
  boolean isValidPackage(String paramString, int paramInt) {
    if (paramString == null)
      return false; 
    String[] arrayOfString = getPackageManager().getPackagesForUid(paramInt);
    int i = arrayOfString.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      if (arrayOfString[paramInt].equals(paramString))
        return true; 
    } 
    return false;
  }
  
  public void notifyChildrenChanged(String paramString) {
    if (paramString != null) {
      this.mImpl.notifyChildrenChanged(paramString, null);
      return;
    } 
    throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
  }
  
  public void notifyChildrenChanged(String paramString, Bundle paramBundle) {
    if (paramString != null) {
      if (paramBundle != null) {
        this.mImpl.notifyChildrenChanged(paramString, paramBundle);
        return;
      } 
      throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
    } 
    throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
  }
  
  public IBinder onBind(Intent paramIntent) {
    return this.mImpl.onBind(paramIntent);
  }
  
  public void onCreate() {
    super.onCreate();
    if (Build.VERSION.SDK_INT >= 26) {
      this.mImpl = new MediaBrowserServiceImplApi26();
    } else if (Build.VERSION.SDK_INT >= 23) {
      this.mImpl = new MediaBrowserServiceImplApi23();
    } else if (Build.VERSION.SDK_INT >= 21) {
      this.mImpl = new MediaBrowserServiceImplApi21();
    } else {
      this.mImpl = new MediaBrowserServiceImplBase();
    } 
    this.mImpl.onCreate();
  }
  
  public void onCustomAction(String paramString, Bundle paramBundle, Result<Bundle> paramResult) {
    paramResult.sendError(null);
  }
  
  public abstract BrowserRoot onGetRoot(String paramString, int paramInt, Bundle paramBundle);
  
  public abstract void onLoadChildren(String paramString, Result<List<MediaBrowserCompat.MediaItem>> paramResult);
  
  public void onLoadChildren(String paramString, Result<List<MediaBrowserCompat.MediaItem>> paramResult, Bundle paramBundle) {
    paramResult.setFlags(1);
    onLoadChildren(paramString, paramResult);
  }
  
  public void onLoadItem(String paramString, Result<MediaBrowserCompat.MediaItem> paramResult) {
    paramResult.setFlags(2);
    paramResult.sendResult(null);
  }
  
  public void onSearch(String paramString, Bundle paramBundle, Result<List<MediaBrowserCompat.MediaItem>> paramResult) {
    paramResult.setFlags(4);
    paramResult.sendResult(null);
  }
  
  void performCustomAction(String paramString, Bundle paramBundle, ConnectionRecord paramConnectionRecord, final ResultReceiver receiver) {
    Result<Bundle> result = new Result<Bundle>(paramString) {
        void onErrorSent(Bundle param1Bundle) {
          receiver.send(-1, param1Bundle);
        }
        
        void onProgressUpdateSent(Bundle param1Bundle) {
          receiver.send(1, param1Bundle);
        }
        
        void onResultSent(Bundle param1Bundle) {
          receiver.send(0, param1Bundle);
        }
      };
    this.mCurConnection = paramConnectionRecord;
    onCustomAction(paramString, paramBundle, result);
    this.mCurConnection = null;
    if (result.isDone())
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("onCustomAction must call detach() or sendResult() or sendError() before returning for action=");
    stringBuilder.append(paramString);
    stringBuilder.append(" extras=");
    stringBuilder.append(paramBundle);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  void performLoadChildren(final String parentId, final ConnectionRecord connection, final Bundle options) {
    Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(parentId) {
        void onResultSent(List<MediaBrowserCompat.MediaItem> param1List) {
          StringBuilder stringBuilder1;
          List<MediaBrowserCompat.MediaItem> list;
          if (MediaBrowserServiceCompat.this.mConnections.get(connection.callbacks.asBinder()) != connection) {
            if (MediaBrowserServiceCompat.DEBUG) {
              stringBuilder1 = new StringBuilder();
              stringBuilder1.append("Not sending onLoadChildren result for connection that has been disconnected. pkg=");
              stringBuilder1.append(connection.pkg);
              stringBuilder1.append(" id=");
              stringBuilder1.append(parentId);
              Log.d("MBServiceCompat", stringBuilder1.toString());
            } 
            return;
          } 
          StringBuilder stringBuilder2 = stringBuilder1;
          if ((getFlags() & 0x1) != 0)
            list = MediaBrowserServiceCompat.this.applyOptions((List<MediaBrowserCompat.MediaItem>)stringBuilder1, options); 
          try {
            connection.callbacks.onLoadChildren(parentId, list, options);
          } catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Calling onLoadChildren() failed for id=");
            stringBuilder.append(parentId);
            stringBuilder.append(" package=");
            stringBuilder.append(connection.pkg);
            Log.w("MBServiceCompat", stringBuilder.toString());
          } 
        }
      };
    this.mCurConnection = connection;
    if (options == null) {
      onLoadChildren(parentId, result);
    } else {
      onLoadChildren(parentId, result, options);
    } 
    this.mCurConnection = null;
    if (result.isDone())
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("onLoadChildren must call detach() or sendResult() before returning for package=");
    stringBuilder.append(connection.pkg);
    stringBuilder.append(" id=");
    stringBuilder.append(parentId);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  void performLoadItem(String paramString, ConnectionRecord paramConnectionRecord, final ResultReceiver receiver) {
    Result<MediaBrowserCompat.MediaItem> result = new Result<MediaBrowserCompat.MediaItem>(paramString) {
        void onResultSent(MediaBrowserCompat.MediaItem param1MediaItem) {
          if ((getFlags() & 0x2) != 0) {
            receiver.send(-1, null);
            return;
          } 
          Bundle bundle = new Bundle();
          bundle.putParcelable("media_item", param1MediaItem);
          receiver.send(0, bundle);
        }
      };
    this.mCurConnection = paramConnectionRecord;
    onLoadItem(paramString, result);
    this.mCurConnection = null;
    if (result.isDone())
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("onLoadItem must call detach() or sendResult() before returning for id=");
    stringBuilder.append(paramString);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  void performSearch(String paramString, Bundle paramBundle, ConnectionRecord paramConnectionRecord, final ResultReceiver receiver) {
    Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(paramString) {
        void onResultSent(List<MediaBrowserCompat.MediaItem> param1List) {
          if ((getFlags() & 0x4) != 0 || param1List == null) {
            receiver.send(-1, null);
            return;
          } 
          Bundle bundle = new Bundle();
          bundle.putParcelableArray("search_results", param1List.<Parcelable>toArray((Parcelable[])new MediaBrowserCompat.MediaItem[0]));
          receiver.send(0, bundle);
        }
      };
    this.mCurConnection = paramConnectionRecord;
    onSearch(paramString, paramBundle, result);
    this.mCurConnection = null;
    if (result.isDone())
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("onSearch must call detach() or sendResult() before returning for query=");
    stringBuilder.append(paramString);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  boolean removeSubscription(String paramString, ConnectionRecord paramConnectionRecord, IBinder paramIBinder) {
    boolean bool1 = true;
    boolean bool2 = false;
    boolean bool3 = false;
    if (paramIBinder == null) {
      if (paramConnectionRecord.subscriptions.remove(paramString) != null) {
        bool3 = bool1;
      } else {
        bool3 = false;
      } 
      return bool3;
    } 
    List list = paramConnectionRecord.subscriptions.get(paramString);
    bool1 = bool2;
    if (list != null) {
      Iterator iterator = list.iterator();
      while (iterator.hasNext()) {
        if (paramIBinder == ((Pair)iterator.next()).first) {
          iterator.remove();
          bool3 = true;
        } 
      } 
      bool1 = bool3;
      if (list.size() == 0) {
        paramConnectionRecord.subscriptions.remove(paramString);
        bool1 = bool3;
      } 
    } 
    return bool1;
  }
  
  public void setSessionToken(MediaSessionCompat.Token paramToken) {
    if (paramToken != null) {
      if (this.mSession == null) {
        this.mSession = paramToken;
        this.mImpl.setSessionToken(paramToken);
        return;
      } 
      throw new IllegalStateException("The session token has already been set.");
    } 
    throw new IllegalArgumentException("Session token may not be null.");
  }
  
  public static final class BrowserRoot {
    public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
    
    public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
    
    public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
    
    @Deprecated
    public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
    
    private final Bundle mExtras;
    
    private final String mRootId;
    
    public BrowserRoot(String param1String, Bundle param1Bundle) {
      if (param1String != null) {
        this.mRootId = param1String;
        this.mExtras = param1Bundle;
        return;
      } 
      throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
    }
    
    public Bundle getExtras() {
      return this.mExtras;
    }
    
    public String getRootId() {
      return this.mRootId;
    }
  }
  
  private class ConnectionRecord implements IBinder.DeathRecipient {
    MediaBrowserServiceCompat.ServiceCallbacks callbacks;
    
    String pkg;
    
    MediaBrowserServiceCompat.BrowserRoot root;
    
    Bundle rootHints;
    
    HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap<String, List<Pair<IBinder, Bundle>>>();
    
    public void binderDied() {
      MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
            public void run() {
              MediaBrowserServiceCompat.this.mConnections.remove(MediaBrowserServiceCompat.ConnectionRecord.this.callbacks.asBinder());
            }
          });
    }
  }
  
  class null implements Runnable {
    public void run() {
      MediaBrowserServiceCompat.this.mConnections.remove(this.this$1.callbacks.asBinder());
    }
  }
  
  static interface MediaBrowserServiceImpl {
    Bundle getBrowserRootHints();
    
    void notifyChildrenChanged(String param1String, Bundle param1Bundle);
    
    IBinder onBind(Intent param1Intent);
    
    void onCreate();
    
    void setSessionToken(MediaSessionCompat.Token param1Token);
  }
  
  class MediaBrowserServiceImplApi21 implements MediaBrowserServiceImpl, MediaBrowserServiceCompatApi21.ServiceCompatProxy {
    Messenger mMessenger;
    
    final List<Bundle> mRootExtrasList = new ArrayList<Bundle>();
    
    Object mServiceObj;
    
    public Bundle getBrowserRootHints() {
      Messenger messenger = this.mMessenger;
      Bundle bundle = null;
      if (messenger == null)
        return null; 
      if (MediaBrowserServiceCompat.this.mCurConnection != null) {
        if (MediaBrowserServiceCompat.this.mCurConnection.rootHints != null)
          bundle = new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints); 
        return bundle;
      } 
      throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
    }
    
    public void notifyChildrenChanged(String param1String, Bundle param1Bundle) {
      notifyChildrenChangedForFramework(param1String, param1Bundle);
      notifyChildrenChangedForCompat(param1String, param1Bundle);
    }
    
    void notifyChildrenChangedForCompat(final String parentId, final Bundle options) {
      MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
            public void run() {
              for (IBinder iBinder : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
                List list = connectionRecord.subscriptions.get(parentId);
                if (list != null)
                  for (Pair pair : list) {
                    if (MediaBrowserCompatUtils.hasDuplicatedItems(options, (Bundle)pair.second))
                      MediaBrowserServiceCompat.this.performLoadChildren(parentId, connectionRecord, (Bundle)pair.second); 
                  }  
              } 
            }
          });
    }
    
    void notifyChildrenChangedForFramework(String param1String, Bundle param1Bundle) {
      MediaBrowserServiceCompatApi21.notifyChildrenChanged(this.mServiceObj, param1String);
    }
    
    public IBinder onBind(Intent param1Intent) {
      return MediaBrowserServiceCompatApi21.onBind(this.mServiceObj, param1Intent);
    }
    
    public void onCreate() {
      Object object = MediaBrowserServiceCompatApi21.createService((Context)MediaBrowserServiceCompat.this, this);
      this.mServiceObj = object;
      MediaBrowserServiceCompatApi21.onCreate(object);
    }
    
    public MediaBrowserServiceCompatApi21.BrowserRoot onGetRoot(String param1String, int param1Int, Bundle param1Bundle) {
      String str;
      if (param1Bundle != null && param1Bundle.getInt("extra_client_version", 0) != 0) {
        param1Bundle.remove("extra_client_version");
        this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
        Bundle bundle = new Bundle();
        bundle.putInt("extra_service_version", 2);
        BundleCompat.putBinder(bundle, "extra_messenger", this.mMessenger.getBinder());
        if (MediaBrowserServiceCompat.this.mSession != null) {
          IBinder iBinder;
          IMediaSession iMediaSession = MediaBrowserServiceCompat.this.mSession.getExtraBinder();
          if (iMediaSession == null) {
            iMediaSession = null;
          } else {
            iBinder = iMediaSession.asBinder();
          } 
          BundleCompat.putBinder(bundle, "extra_session_binder", iBinder);
          str = (String)bundle;
        } else {
          this.mRootExtrasList.add(bundle);
          str = (String)bundle;
        } 
      } else {
        str = null;
      } 
      MediaBrowserServiceCompat.BrowserRoot browserRoot = MediaBrowserServiceCompat.this.onGetRoot(param1String, param1Int, param1Bundle);
      if (browserRoot == null)
        return null; 
      if (str == null) {
        Bundle bundle = browserRoot.getExtras();
      } else {
        param1String = str;
        if (browserRoot.getExtras() != null) {
          str.putAll(browserRoot.getExtras());
          param1String = str;
        } 
      } 
      return new MediaBrowserServiceCompatApi21.BrowserRoot(browserRoot.getRootId(), (Bundle)param1String);
    }
    
    public void onLoadChildren(String param1String, final MediaBrowserServiceCompatApi21.ResultWrapper<List<Parcel>> resultWrapper) {
      MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>> result = new MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>>(param1String) {
          public void detach() {
            resultWrapper.detach();
          }
          
          void onResultSent(List<MediaBrowserCompat.MediaItem> param2List) {
            if (param2List != null) {
              ArrayList<MediaBrowserCompat.MediaItem> arrayList = new ArrayList();
              Iterator<MediaBrowserCompat.MediaItem> iterator = param2List.iterator();
              while (true) {
                param2List = arrayList;
                if (iterator.hasNext()) {
                  MediaBrowserCompat.MediaItem mediaItem = iterator.next();
                  Parcel parcel = Parcel.obtain();
                  mediaItem.writeToParcel(parcel, 0);
                  arrayList.add(parcel);
                  continue;
                } 
                break;
              } 
            } else {
              param2List = null;
            } 
            resultWrapper.sendResult(param2List);
          }
        };
      MediaBrowserServiceCompat.this.onLoadChildren(param1String, result);
    }
    
    public void setSessionToken(final MediaSessionCompat.Token token) {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
            public void run() {
              if (!MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.this.mRootExtrasList.isEmpty()) {
                IMediaSession iMediaSession = token.getExtraBinder();
                if (iMediaSession != null) {
                  Iterator<Bundle> iterator = MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.this.mRootExtrasList.iterator();
                  while (iterator.hasNext())
                    BundleCompat.putBinder(iterator.next(), "extra_session_binder", iMediaSession.asBinder()); 
                } 
                MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.this.mRootExtrasList.clear();
              } 
              MediaBrowserServiceCompatApi21.setSessionToken(MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.this.mServiceObj, token.getToken());
            }
          });
    }
  }
  
  class null implements Runnable {
    public void run() {
      if (!this.this$1.mRootExtrasList.isEmpty()) {
        IMediaSession iMediaSession = token.getExtraBinder();
        if (iMediaSession != null) {
          Iterator<Bundle> iterator = this.this$1.mRootExtrasList.iterator();
          while (iterator.hasNext())
            BundleCompat.putBinder(iterator.next(), "extra_session_binder", iMediaSession.asBinder()); 
        } 
        this.this$1.mRootExtrasList.clear();
      } 
      MediaBrowserServiceCompatApi21.setSessionToken(this.this$1.mServiceObj, token.getToken());
    }
  }
  
  class null extends Result<List<MediaBrowserCompat.MediaItem>> {
    null(Object param1Object) {
      super(param1Object);
    }
    
    public void detach() {
      resultWrapper.detach();
    }
    
    void onResultSent(List<MediaBrowserCompat.MediaItem> param1List) {
      if (param1List != null) {
        ArrayList<MediaBrowserCompat.MediaItem> arrayList = new ArrayList();
        Iterator<MediaBrowserCompat.MediaItem> iterator = param1List.iterator();
        while (true) {
          param1List = arrayList;
          if (iterator.hasNext()) {
            MediaBrowserCompat.MediaItem mediaItem = iterator.next();
            Parcel parcel = Parcel.obtain();
            mediaItem.writeToParcel(parcel, 0);
            arrayList.add(parcel);
            continue;
          } 
          break;
        } 
      } else {
        param1List = null;
      } 
      resultWrapper.sendResult(param1List);
    }
  }
  
  class null implements Runnable {
    public void run() {
      for (IBinder iBinder : MediaBrowserServiceCompat.this.mConnections.keySet()) {
        MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
        List list = connectionRecord.subscriptions.get(parentId);
        if (list != null)
          for (Pair pair : list) {
            if (MediaBrowserCompatUtils.hasDuplicatedItems(options, (Bundle)pair.second))
              MediaBrowserServiceCompat.this.performLoadChildren(parentId, connectionRecord, (Bundle)pair.second); 
          }  
      } 
    }
  }
  
  class MediaBrowserServiceImplApi23 extends MediaBrowserServiceImplApi21 implements MediaBrowserServiceCompatApi23.ServiceCompatProxy {
    public void onCreate() {
      this.mServiceObj = MediaBrowserServiceCompatApi23.createService((Context)MediaBrowserServiceCompat.this, this);
      MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
    }
    
    public void onLoadItem(String param1String, final MediaBrowserServiceCompatApi21.ResultWrapper<Parcel> resultWrapper) {
      MediaBrowserServiceCompat.Result<MediaBrowserCompat.MediaItem> result = new MediaBrowserServiceCompat.Result<MediaBrowserCompat.MediaItem>(param1String) {
          public void detach() {
            resultWrapper.detach();
          }
          
          void onResultSent(MediaBrowserCompat.MediaItem param2MediaItem) {
            if (param2MediaItem == null) {
              resultWrapper.sendResult(null);
            } else {
              Parcel parcel = Parcel.obtain();
              param2MediaItem.writeToParcel(parcel, 0);
              resultWrapper.sendResult(parcel);
            } 
          }
        };
      MediaBrowserServiceCompat.this.onLoadItem(param1String, result);
    }
  }
  
  class null extends Result<MediaBrowserCompat.MediaItem> {
    null(Object param1Object) {
      super(param1Object);
    }
    
    public void detach() {
      resultWrapper.detach();
    }
    
    void onResultSent(MediaBrowserCompat.MediaItem param1MediaItem) {
      if (param1MediaItem == null) {
        resultWrapper.sendResult(null);
      } else {
        Parcel parcel = Parcel.obtain();
        param1MediaItem.writeToParcel(parcel, 0);
        resultWrapper.sendResult(parcel);
      } 
    }
  }
  
  class MediaBrowserServiceImplApi26 extends MediaBrowserServiceImplApi23 implements MediaBrowserServiceCompatApi26.ServiceCompatProxy {
    public Bundle getBrowserRootHints() {
      if (MediaBrowserServiceCompat.this.mCurConnection != null) {
        Bundle bundle;
        if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
          bundle = null;
        } else {
          bundle = new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        } 
        return bundle;
      } 
      return MediaBrowserServiceCompatApi26.getBrowserRootHints(this.mServiceObj);
    }
    
    void notifyChildrenChangedForFramework(String param1String, Bundle param1Bundle) {
      if (param1Bundle != null) {
        MediaBrowserServiceCompatApi26.notifyChildrenChanged(this.mServiceObj, param1String, param1Bundle);
      } else {
        super.notifyChildrenChangedForFramework(param1String, param1Bundle);
      } 
    }
    
    public void onCreate() {
      this.mServiceObj = MediaBrowserServiceCompatApi26.createService((Context)MediaBrowserServiceCompat.this, this);
      MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
    }
    
    public void onLoadChildren(String param1String, final MediaBrowserServiceCompatApi26.ResultWrapper resultWrapper, Bundle param1Bundle) {
      MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>> result = new MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>>(param1String) {
          public void detach() {
            resultWrapper.detach();
          }
          
          void onResultSent(List<MediaBrowserCompat.MediaItem> param2List) {
            if (param2List != null) {
              ArrayList<MediaBrowserCompat.MediaItem> arrayList = new ArrayList();
              Iterator<MediaBrowserCompat.MediaItem> iterator = param2List.iterator();
              while (true) {
                param2List = arrayList;
                if (iterator.hasNext()) {
                  MediaBrowserCompat.MediaItem mediaItem = iterator.next();
                  Parcel parcel = Parcel.obtain();
                  mediaItem.writeToParcel(parcel, 0);
                  arrayList.add(parcel);
                  continue;
                } 
                break;
              } 
            } else {
              param2List = null;
            } 
            resultWrapper.sendResult((List)param2List, getFlags());
          }
        };
      MediaBrowserServiceCompat.this.onLoadChildren(param1String, result, param1Bundle);
    }
  }
  
  class null extends Result<List<MediaBrowserCompat.MediaItem>> {
    null(Object param1Object) {
      super(param1Object);
    }
    
    public void detach() {
      resultWrapper.detach();
    }
    
    void onResultSent(List<MediaBrowserCompat.MediaItem> param1List) {
      if (param1List != null) {
        ArrayList<MediaBrowserCompat.MediaItem> arrayList = new ArrayList();
        Iterator<MediaBrowserCompat.MediaItem> iterator = param1List.iterator();
        while (true) {
          param1List = arrayList;
          if (iterator.hasNext()) {
            MediaBrowserCompat.MediaItem mediaItem = iterator.next();
            Parcel parcel = Parcel.obtain();
            mediaItem.writeToParcel(parcel, 0);
            arrayList.add(parcel);
            continue;
          } 
          break;
        } 
      } else {
        param1List = null;
      } 
      resultWrapper.sendResult((List)param1List, getFlags());
    }
  }
  
  class MediaBrowserServiceImplBase implements MediaBrowserServiceImpl {
    private Messenger mMessenger;
    
    public Bundle getBrowserRootHints() {
      if (MediaBrowserServiceCompat.this.mCurConnection != null) {
        Bundle bundle;
        if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
          bundle = null;
        } else {
          bundle = new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        } 
        return bundle;
      } 
      throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
    }
    
    public void notifyChildrenChanged(final String parentId, final Bundle options) {
      MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
            public void run() {
              for (IBinder iBinder : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
                List list = connectionRecord.subscriptions.get(parentId);
                if (list != null)
                  for (Pair pair : list) {
                    if (MediaBrowserCompatUtils.hasDuplicatedItems(options, (Bundle)pair.second))
                      MediaBrowserServiceCompat.this.performLoadChildren(parentId, connectionRecord, (Bundle)pair.second); 
                  }  
              } 
            }
          });
    }
    
    public IBinder onBind(Intent param1Intent) {
      return "android.media.browse.MediaBrowserService".equals(param1Intent.getAction()) ? this.mMessenger.getBinder() : null;
    }
    
    public void onCreate() {
      this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
    }
    
    public void setSessionToken(final MediaSessionCompat.Token token) {
      MediaBrowserServiceCompat.this.mHandler.post(new Runnable() {
            public void run() {
              Iterator<MediaBrowserServiceCompat.ConnectionRecord> iterator = MediaBrowserServiceCompat.this.mConnections.values().iterator();
              while (iterator.hasNext()) {
                MediaBrowserServiceCompat.ConnectionRecord connectionRecord = iterator.next();
                try {
                  connectionRecord.callbacks.onConnect(connectionRecord.root.getRootId(), token, connectionRecord.root.getExtras());
                } catch (RemoteException remoteException) {
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append("Connection for ");
                  stringBuilder.append(connectionRecord.pkg);
                  stringBuilder.append(" is no longer valid.");
                  Log.w("MBServiceCompat", stringBuilder.toString());
                  iterator.remove();
                } 
              } 
            }
          });
    }
  }
  
  class null implements Runnable {
    public void run() {
      Iterator<MediaBrowserServiceCompat.ConnectionRecord> iterator = MediaBrowserServiceCompat.this.mConnections.values().iterator();
      while (iterator.hasNext()) {
        MediaBrowserServiceCompat.ConnectionRecord connectionRecord = iterator.next();
        try {
          connectionRecord.callbacks.onConnect(connectionRecord.root.getRootId(), token, connectionRecord.root.getExtras());
        } catch (RemoteException remoteException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Connection for ");
          stringBuilder.append(connectionRecord.pkg);
          stringBuilder.append(" is no longer valid.");
          Log.w("MBServiceCompat", stringBuilder.toString());
          iterator.remove();
        } 
      } 
    }
  }
  
  class null implements Runnable {
    public void run() {
      for (IBinder iBinder : MediaBrowserServiceCompat.this.mConnections.keySet()) {
        MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
        List list = connectionRecord.subscriptions.get(parentId);
        if (list != null)
          for (Pair pair : list) {
            if (MediaBrowserCompatUtils.hasDuplicatedItems(options, (Bundle)pair.second))
              MediaBrowserServiceCompat.this.performLoadChildren(parentId, connectionRecord, (Bundle)pair.second); 
          }  
      } 
    }
  }
  
  public static class Result<T> {
    private final Object mDebug;
    
    private boolean mDetachCalled;
    
    private int mFlags;
    
    private boolean mSendErrorCalled;
    
    private boolean mSendProgressUpdateCalled;
    
    private boolean mSendResultCalled;
    
    Result(Object param1Object) {
      this.mDebug = param1Object;
    }
    
    private void checkExtraFields(Bundle param1Bundle) {
      if (param1Bundle == null)
        return; 
      if (param1Bundle.containsKey("android.media.browse.extra.DOWNLOAD_PROGRESS")) {
        float f = param1Bundle.getFloat("android.media.browse.extra.DOWNLOAD_PROGRESS");
        if (f < -1.0E-5F || f > 1.00001F)
          throw new IllegalArgumentException("The value of the EXTRA_DOWNLOAD_PROGRESS field must be a float number within [0.0, 1.0]."); 
      } 
    }
    
    public void detach() {
      if (!this.mDetachCalled) {
        if (!this.mSendResultCalled) {
          if (!this.mSendErrorCalled) {
            this.mDetachCalled = true;
            return;
          } 
          StringBuilder stringBuilder2 = new StringBuilder();
          stringBuilder2.append("detach() called when sendError() had already been called for: ");
          stringBuilder2.append(this.mDebug);
          throw new IllegalStateException(stringBuilder2.toString());
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("detach() called when sendResult() had already been called for: ");
        stringBuilder1.append(this.mDebug);
        throw new IllegalStateException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("detach() called when detach() had already been called for: ");
      stringBuilder.append(this.mDebug);
      throw new IllegalStateException(stringBuilder.toString());
    }
    
    int getFlags() {
      return this.mFlags;
    }
    
    boolean isDone() {
      return (this.mDetachCalled || this.mSendResultCalled || this.mSendErrorCalled);
    }
    
    void onErrorSent(Bundle param1Bundle) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("It is not supported to send an error for ");
      stringBuilder.append(this.mDebug);
      throw new UnsupportedOperationException(stringBuilder.toString());
    }
    
    void onProgressUpdateSent(Bundle param1Bundle) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("It is not supported to send an interim update for ");
      stringBuilder.append(this.mDebug);
      throw new UnsupportedOperationException(stringBuilder.toString());
    }
    
    void onResultSent(T param1T) {}
    
    public void sendError(Bundle param1Bundle) {
      if (!this.mSendResultCalled && !this.mSendErrorCalled) {
        this.mSendErrorCalled = true;
        onErrorSent(param1Bundle);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("sendError() called when either sendResult() or sendError() had already been called for: ");
      stringBuilder.append(this.mDebug);
      throw new IllegalStateException(stringBuilder.toString());
    }
    
    public void sendProgressUpdate(Bundle param1Bundle) {
      if (!this.mSendResultCalled && !this.mSendErrorCalled) {
        checkExtraFields(param1Bundle);
        this.mSendProgressUpdateCalled = true;
        onProgressUpdateSent(param1Bundle);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("sendProgressUpdate() called when either sendResult() or sendError() had already been called for: ");
      stringBuilder.append(this.mDebug);
      throw new IllegalStateException(stringBuilder.toString());
    }
    
    public void sendResult(T param1T) {
      if (!this.mSendResultCalled && !this.mSendErrorCalled) {
        this.mSendResultCalled = true;
        onResultSent(param1T);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("sendResult() called when either sendResult() or sendError() had already been called for: ");
      stringBuilder.append(this.mDebug);
      throw new IllegalStateException(stringBuilder.toString());
    }
    
    void setFlags(int param1Int) {
      this.mFlags = param1Int;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface ResultFlags {}
  
  private class ServiceBinderImpl {
    public void addSubscription(final String id, final IBinder token, final Bundle options, final MediaBrowserServiceCompat.ServiceCallbacks callbacks) {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
            public void run() {
              StringBuilder stringBuilder;
              IBinder iBinder = callbacks.asBinder();
              MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
              if (connectionRecord == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("addSubscription for callback that isn't registered id=");
                stringBuilder.append(id);
                Log.w("MBServiceCompat", stringBuilder.toString());
                return;
              } 
              MediaBrowserServiceCompat.this.addSubscription(id, (MediaBrowserServiceCompat.ConnectionRecord)stringBuilder, token, options);
            }
          });
    }
    
    public void connect(final String pkg, final int uid, final Bundle rootHints, final MediaBrowserServiceCompat.ServiceCallbacks callbacks) {
      if (MediaBrowserServiceCompat.this.isValidPackage(pkg, uid)) {
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
              public void run() {
                StringBuilder stringBuilder;
                IBinder iBinder = callbacks.asBinder();
                MediaBrowserServiceCompat.this.mConnections.remove(iBinder);
                MediaBrowserServiceCompat.ConnectionRecord connectionRecord = new MediaBrowserServiceCompat.ConnectionRecord();
                connectionRecord.pkg = pkg;
                connectionRecord.rootHints = rootHints;
                connectionRecord.callbacks = callbacks;
                connectionRecord.root = MediaBrowserServiceCompat.this.onGetRoot(pkg, uid, rootHints);
                if (connectionRecord.root == null) {
                  stringBuilder = new StringBuilder();
                  stringBuilder.append("No root for client ");
                  stringBuilder.append(pkg);
                  stringBuilder.append(" from service ");
                  stringBuilder.append(getClass().getName());
                  Log.i("MBServiceCompat", stringBuilder.toString());
                  try {
                    callbacks.onConnectFailed();
                  } catch (RemoteException remoteException) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Calling onConnectFailed() failed. Ignoring. pkg=");
                    stringBuilder.append(pkg);
                    Log.w("MBServiceCompat", stringBuilder.toString());
                  } 
                } else {
                  try {
                    MediaBrowserServiceCompat.this.mConnections.put(stringBuilder, connectionRecord);
                    stringBuilder.linkToDeath(connectionRecord, 0);
                    if (MediaBrowserServiceCompat.this.mSession != null)
                      callbacks.onConnect(connectionRecord.root.getRootId(), MediaBrowserServiceCompat.this.mSession, connectionRecord.root.getExtras()); 
                  } catch (RemoteException remoteException) {
                    StringBuilder stringBuilder1 = new StringBuilder();
                    stringBuilder1.append("Calling onConnect() failed. Dropping client. pkg=");
                    stringBuilder1.append(pkg);
                    Log.w("MBServiceCompat", stringBuilder1.toString());
                    MediaBrowserServiceCompat.this.mConnections.remove(stringBuilder);
                  } 
                } 
              }
            });
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Package/uid mismatch: uid=");
      stringBuilder.append(uid);
      stringBuilder.append(" package=");
      stringBuilder.append(pkg);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public void disconnect(final MediaBrowserServiceCompat.ServiceCallbacks callbacks) {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
            public void run() {
              IBinder iBinder = callbacks.asBinder();
              MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.remove(iBinder);
              if (connectionRecord != null)
                connectionRecord.callbacks.asBinder().unlinkToDeath(connectionRecord, 0); 
            }
          });
    }
    
    public void getMediaItem(final String mediaId, final ResultReceiver receiver, final MediaBrowserServiceCompat.ServiceCallbacks callbacks) {
      if (!TextUtils.isEmpty(mediaId) && receiver != null)
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
              public void run() {
                StringBuilder stringBuilder;
                IBinder iBinder = callbacks.asBinder();
                MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
                if (connectionRecord == null) {
                  stringBuilder = new StringBuilder();
                  stringBuilder.append("getMediaItem for callback that isn't registered id=");
                  stringBuilder.append(mediaId);
                  Log.w("MBServiceCompat", stringBuilder.toString());
                  return;
                } 
                MediaBrowserServiceCompat.this.performLoadItem(mediaId, (MediaBrowserServiceCompat.ConnectionRecord)stringBuilder, receiver);
              }
            }); 
    }
    
    public void registerCallbacks(final MediaBrowserServiceCompat.ServiceCallbacks callbacks, final Bundle rootHints) {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
            public void run() {
              IBinder iBinder = callbacks.asBinder();
              MediaBrowserServiceCompat.this.mConnections.remove(iBinder);
              MediaBrowserServiceCompat.ConnectionRecord connectionRecord = new MediaBrowserServiceCompat.ConnectionRecord();
              connectionRecord.callbacks = callbacks;
              connectionRecord.rootHints = rootHints;
              MediaBrowserServiceCompat.this.mConnections.put(iBinder, connectionRecord);
              try {
                iBinder.linkToDeath(connectionRecord, 0);
              } catch (RemoteException remoteException) {
                Log.w("MBServiceCompat", "IBinder is already dead.");
              } 
            }
          });
    }
    
    public void removeSubscription(final String id, final IBinder token, final MediaBrowserServiceCompat.ServiceCallbacks callbacks) {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
            public void run() {
              StringBuilder stringBuilder;
              IBinder iBinder = callbacks.asBinder();
              MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
              if (connectionRecord == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("removeSubscription for callback that isn't registered id=");
                stringBuilder.append(id);
                Log.w("MBServiceCompat", stringBuilder.toString());
                return;
              } 
              if (!MediaBrowserServiceCompat.this.removeSubscription(id, (MediaBrowserServiceCompat.ConnectionRecord)stringBuilder, token)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("removeSubscription called for ");
                stringBuilder.append(id);
                stringBuilder.append(" which is not subscribed");
                Log.w("MBServiceCompat", stringBuilder.toString());
              } 
            }
          });
    }
    
    public void search(final String query, final Bundle extras, final ResultReceiver receiver, final MediaBrowserServiceCompat.ServiceCallbacks callbacks) {
      if (!TextUtils.isEmpty(query) && receiver != null)
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
              public void run() {
                StringBuilder stringBuilder;
                IBinder iBinder = callbacks.asBinder();
                MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
                if (connectionRecord == null) {
                  stringBuilder = new StringBuilder();
                  stringBuilder.append("search for callback that isn't registered query=");
                  stringBuilder.append(query);
                  Log.w("MBServiceCompat", stringBuilder.toString());
                  return;
                } 
                MediaBrowserServiceCompat.this.performSearch(query, extras, (MediaBrowserServiceCompat.ConnectionRecord)stringBuilder, receiver);
              }
            }); 
    }
    
    public void sendCustomAction(final String action, final Bundle extras, final ResultReceiver receiver, final MediaBrowserServiceCompat.ServiceCallbacks callbacks) {
      if (!TextUtils.isEmpty(action) && receiver != null)
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
              public void run() {
                StringBuilder stringBuilder;
                IBinder iBinder = callbacks.asBinder();
                MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
                if (connectionRecord == null) {
                  stringBuilder = new StringBuilder();
                  stringBuilder.append("sendCustomAction for callback that isn't registered action=");
                  stringBuilder.append(action);
                  stringBuilder.append(", extras=");
                  stringBuilder.append(extras);
                  Log.w("MBServiceCompat", stringBuilder.toString());
                  return;
                } 
                MediaBrowserServiceCompat.this.performCustomAction(action, extras, (MediaBrowserServiceCompat.ConnectionRecord)stringBuilder, receiver);
              }
            }); 
    }
    
    public void unregisterCallbacks(final MediaBrowserServiceCompat.ServiceCallbacks callbacks) {
      MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
            public void run() {
              IBinder iBinder = callbacks.asBinder();
              MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.remove(iBinder);
              if (connectionRecord != null)
                iBinder.unlinkToDeath(connectionRecord, 0); 
            }
          });
    }
  }
  
  class null implements Runnable {
    public void run() {
      StringBuilder stringBuilder;
      IBinder iBinder = callbacks.asBinder();
      MediaBrowserServiceCompat.this.mConnections.remove(iBinder);
      MediaBrowserServiceCompat.ConnectionRecord connectionRecord = new MediaBrowserServiceCompat.ConnectionRecord();
      connectionRecord.pkg = pkg;
      connectionRecord.rootHints = rootHints;
      connectionRecord.callbacks = callbacks;
      connectionRecord.root = MediaBrowserServiceCompat.this.onGetRoot(pkg, uid, rootHints);
      if (connectionRecord.root == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("No root for client ");
        stringBuilder.append(pkg);
        stringBuilder.append(" from service ");
        stringBuilder.append(getClass().getName());
        Log.i("MBServiceCompat", stringBuilder.toString());
        try {
          callbacks.onConnectFailed();
        } catch (RemoteException remoteException) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("Calling onConnectFailed() failed. Ignoring. pkg=");
          stringBuilder.append(pkg);
          Log.w("MBServiceCompat", stringBuilder.toString());
        } 
      } else {
        try {
          MediaBrowserServiceCompat.this.mConnections.put(stringBuilder, connectionRecord);
          stringBuilder.linkToDeath(connectionRecord, 0);
          if (MediaBrowserServiceCompat.this.mSession != null)
            callbacks.onConnect(connectionRecord.root.getRootId(), MediaBrowserServiceCompat.this.mSession, connectionRecord.root.getExtras()); 
        } catch (RemoteException remoteException) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Calling onConnect() failed. Dropping client. pkg=");
          stringBuilder1.append(pkg);
          Log.w("MBServiceCompat", stringBuilder1.toString());
          MediaBrowserServiceCompat.this.mConnections.remove(stringBuilder);
        } 
      } 
    }
  }
  
  class null implements Runnable {
    public void run() {
      IBinder iBinder = callbacks.asBinder();
      MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.remove(iBinder);
      if (connectionRecord != null)
        connectionRecord.callbacks.asBinder().unlinkToDeath(connectionRecord, 0); 
    }
  }
  
  class null implements Runnable {
    public void run() {
      StringBuilder stringBuilder;
      IBinder iBinder = callbacks.asBinder();
      MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
      if (connectionRecord == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("addSubscription for callback that isn't registered id=");
        stringBuilder.append(id);
        Log.w("MBServiceCompat", stringBuilder.toString());
        return;
      } 
      MediaBrowserServiceCompat.this.addSubscription(id, (MediaBrowserServiceCompat.ConnectionRecord)stringBuilder, token, options);
    }
  }
  
  class null implements Runnable {
    public void run() {
      StringBuilder stringBuilder;
      IBinder iBinder = callbacks.asBinder();
      MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
      if (connectionRecord == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("removeSubscription for callback that isn't registered id=");
        stringBuilder.append(id);
        Log.w("MBServiceCompat", stringBuilder.toString());
        return;
      } 
      if (!MediaBrowserServiceCompat.this.removeSubscription(id, (MediaBrowserServiceCompat.ConnectionRecord)stringBuilder, token)) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("removeSubscription called for ");
        stringBuilder.append(id);
        stringBuilder.append(" which is not subscribed");
        Log.w("MBServiceCompat", stringBuilder.toString());
      } 
    }
  }
  
  class null implements Runnable {
    public void run() {
      StringBuilder stringBuilder;
      IBinder iBinder = callbacks.asBinder();
      MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
      if (connectionRecord == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("getMediaItem for callback that isn't registered id=");
        stringBuilder.append(mediaId);
        Log.w("MBServiceCompat", stringBuilder.toString());
        return;
      } 
      MediaBrowserServiceCompat.this.performLoadItem(mediaId, (MediaBrowserServiceCompat.ConnectionRecord)stringBuilder, receiver);
    }
  }
  
  class null implements Runnable {
    public void run() {
      IBinder iBinder = callbacks.asBinder();
      MediaBrowserServiceCompat.this.mConnections.remove(iBinder);
      MediaBrowserServiceCompat.ConnectionRecord connectionRecord = new MediaBrowserServiceCompat.ConnectionRecord();
      connectionRecord.callbacks = callbacks;
      connectionRecord.rootHints = rootHints;
      MediaBrowserServiceCompat.this.mConnections.put(iBinder, connectionRecord);
      try {
        iBinder.linkToDeath(connectionRecord, 0);
      } catch (RemoteException remoteException) {
        Log.w("MBServiceCompat", "IBinder is already dead.");
      } 
    }
  }
  
  class null implements Runnable {
    public void run() {
      IBinder iBinder = callbacks.asBinder();
      MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.remove(iBinder);
      if (connectionRecord != null)
        iBinder.unlinkToDeath(connectionRecord, 0); 
    }
  }
  
  class null implements Runnable {
    public void run() {
      StringBuilder stringBuilder;
      IBinder iBinder = callbacks.asBinder();
      MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
      if (connectionRecord == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("search for callback that isn't registered query=");
        stringBuilder.append(query);
        Log.w("MBServiceCompat", stringBuilder.toString());
        return;
      } 
      MediaBrowserServiceCompat.this.performSearch(query, extras, (MediaBrowserServiceCompat.ConnectionRecord)stringBuilder, receiver);
    }
  }
  
  class null implements Runnable {
    public void run() {
      StringBuilder stringBuilder;
      IBinder iBinder = callbacks.asBinder();
      MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(iBinder);
      if (connectionRecord == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("sendCustomAction for callback that isn't registered action=");
        stringBuilder.append(action);
        stringBuilder.append(", extras=");
        stringBuilder.append(extras);
        Log.w("MBServiceCompat", stringBuilder.toString());
        return;
      } 
      MediaBrowserServiceCompat.this.performCustomAction(action, extras, (MediaBrowserServiceCompat.ConnectionRecord)stringBuilder, receiver);
    }
  }
  
  private static interface ServiceCallbacks {
    IBinder asBinder();
    
    void onConnect(String param1String, MediaSessionCompat.Token param1Token, Bundle param1Bundle) throws RemoteException;
    
    void onConnectFailed() throws RemoteException;
    
    void onLoadChildren(String param1String, List<MediaBrowserCompat.MediaItem> param1List, Bundle param1Bundle) throws RemoteException;
  }
  
  private static class ServiceCallbacksCompat implements ServiceCallbacks {
    final Messenger mCallbacks;
    
    ServiceCallbacksCompat(Messenger param1Messenger) {
      this.mCallbacks = param1Messenger;
    }
    
    private void sendRequest(int param1Int, Bundle param1Bundle) throws RemoteException {
      Message message = Message.obtain();
      message.what = param1Int;
      message.arg1 = 2;
      message.setData(param1Bundle);
      this.mCallbacks.send(message);
    }
    
    public IBinder asBinder() {
      return this.mCallbacks.getBinder();
    }
    
    public void onConnect(String param1String, MediaSessionCompat.Token param1Token, Bundle param1Bundle) throws RemoteException {
      Bundle bundle = param1Bundle;
      if (param1Bundle == null)
        bundle = new Bundle(); 
      bundle.putInt("extra_service_version", 2);
      param1Bundle = new Bundle();
      param1Bundle.putString("data_media_item_id", param1String);
      param1Bundle.putParcelable("data_media_session_token", (Parcelable)param1Token);
      param1Bundle.putBundle("data_root_hints", bundle);
      sendRequest(1, param1Bundle);
    }
    
    public void onConnectFailed() throws RemoteException {
      sendRequest(2, null);
    }
    
    public void onLoadChildren(String param1String, List<MediaBrowserCompat.MediaItem> param1List, Bundle param1Bundle) throws RemoteException {
      Bundle bundle = new Bundle();
      bundle.putString("data_media_item_id", param1String);
      bundle.putBundle("data_options", param1Bundle);
      if (param1List != null) {
        ArrayList<MediaBrowserCompat.MediaItem> arrayList;
        if (param1List instanceof ArrayList) {
          arrayList = (ArrayList)param1List;
        } else {
          arrayList = new ArrayList<MediaBrowserCompat.MediaItem>(param1List);
        } 
        bundle.putParcelableArrayList("data_media_item_list", arrayList);
      } 
      sendRequest(3, bundle);
    }
  }
  
  private final class ServiceHandler extends Handler {
    private final MediaBrowserServiceCompat.ServiceBinderImpl mServiceBinderImpl = new MediaBrowserServiceCompat.ServiceBinderImpl();
    
    public void handleMessage(Message param1Message) {
      StringBuilder stringBuilder;
      Bundle bundle = param1Message.getData();
      switch (param1Message.what) {
        default:
          stringBuilder = new StringBuilder();
          stringBuilder.append("Unhandled message: ");
          stringBuilder.append(param1Message);
          stringBuilder.append("\n  Service version: ");
          stringBuilder.append(2);
          stringBuilder.append("\n  Client version: ");
          stringBuilder.append(param1Message.arg1);
          Log.w("MBServiceCompat", stringBuilder.toString());
          return;
        case 9:
          this.mServiceBinderImpl.sendCustomAction(stringBuilder.getString("data_custom_action"), stringBuilder.getBundle("data_custom_action_extras"), (ResultReceiver)stringBuilder.getParcelable("data_result_receiver"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(param1Message.replyTo));
          return;
        case 8:
          this.mServiceBinderImpl.search(stringBuilder.getString("data_search_query"), stringBuilder.getBundle("data_search_extras"), (ResultReceiver)stringBuilder.getParcelable("data_result_receiver"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(param1Message.replyTo));
          return;
        case 7:
          this.mServiceBinderImpl.unregisterCallbacks(new MediaBrowserServiceCompat.ServiceCallbacksCompat(param1Message.replyTo));
          return;
        case 6:
          this.mServiceBinderImpl.registerCallbacks(new MediaBrowserServiceCompat.ServiceCallbacksCompat(param1Message.replyTo), stringBuilder.getBundle("data_root_hints"));
          return;
        case 5:
          this.mServiceBinderImpl.getMediaItem(stringBuilder.getString("data_media_item_id"), (ResultReceiver)stringBuilder.getParcelable("data_result_receiver"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(param1Message.replyTo));
          return;
        case 4:
          this.mServiceBinderImpl.removeSubscription(stringBuilder.getString("data_media_item_id"), BundleCompat.getBinder((Bundle)stringBuilder, "data_callback_token"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(param1Message.replyTo));
          return;
        case 3:
          this.mServiceBinderImpl.addSubscription(stringBuilder.getString("data_media_item_id"), BundleCompat.getBinder((Bundle)stringBuilder, "data_callback_token"), stringBuilder.getBundle("data_options"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(param1Message.replyTo));
          return;
        case 2:
          this.mServiceBinderImpl.disconnect(new MediaBrowserServiceCompat.ServiceCallbacksCompat(param1Message.replyTo));
          return;
        case 1:
          break;
      } 
      this.mServiceBinderImpl.connect(stringBuilder.getString("data_package_name"), stringBuilder.getInt("data_calling_uid"), stringBuilder.getBundle("data_root_hints"), new MediaBrowserServiceCompat.ServiceCallbacksCompat(param1Message.replyTo));
    }
    
    public void postOrRun(Runnable param1Runnable) {
      if (Thread.currentThread() == getLooper().getThread()) {
        param1Runnable.run();
      } else {
        post(param1Runnable);
      } 
    }
    
    public boolean sendMessageAtTime(Message param1Message, long param1Long) {
      Bundle bundle = param1Message.getData();
      bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
      bundle.putInt("data_calling_uid", Binder.getCallingUid());
      return super.sendMessageAtTime(param1Message, param1Long);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\MediaBrowserServiceCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */