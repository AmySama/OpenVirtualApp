package com.lody.virtual.server.pm;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import com.lody.virtual.server.app.IAppInstallerSession;
import java.util.ArrayList;
import java.util.List;

public class VAppInstallerSession extends IAppInstallerSession.Stub {
  private VAppManagerService mApp;
  
  private boolean mCacneled = false;
  
  private boolean mCommited = false;
  
  private Context mContext;
  
  private final List<Uri> mSplitUris = new ArrayList<Uri>();
  
  private IntentSender mStatusReceiver = null;
  
  private final List<Uri> mUris = new ArrayList<Uri>();
  
  public VAppInstallerSession(Context paramContext, VAppManagerService paramVAppManagerService) {
    this.mContext = paramContext;
    this.mApp = paramVAppManagerService;
  }
  
  public void addPackage(Uri paramUri) {
    this.mUris.add(paramUri);
  }
  
  public void addSplit(Uri paramUri) {
    this.mSplitUris.add(paramUri);
  }
  
  public void cancel() {
    if (!this.mCommited) {
      this.mCacneled = true;
      return;
    } 
    throw new IllegalStateException("Session that have already been committed cannot be cancelled.");
  }
  
  public void commit(IntentSender paramIntentSender) {
    if (!this.mCacneled) {
      this.mCommited = true;
      this.mStatusReceiver = paramIntentSender;
      try {
        Context context = this.mContext;
        Intent intent = new Intent();
        this();
        paramIntentSender.sendIntent(context, 0, intent, null, null);
      } catch (android.content.IntentSender.SendIntentException sendIntentException) {
        sendIntentException.printStackTrace();
      } 
      return;
    } 
    throw new IllegalStateException("A canceled session cannot be committed.");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\VAppInstallerSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */