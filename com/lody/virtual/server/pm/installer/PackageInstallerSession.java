package com.lody.virtual.server.pm.installer;

import android.content.Context;
import android.content.IntentSender;
import android.content.pm.IPackageInstallObserver2;
import android.content.pm.IPackageInstallerSession;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.VAppInstallerParams;
import com.lody.virtual.server.pm.VAppManagerService;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PackageInstallerSession extends IPackageInstallerSession.Stub {
  public static final int INSTALL_FAILED_ABORTED = -115;
  
  public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;
  
  public static final int INSTALL_FAILED_INVALID_APK = -2;
  
  public static final int INSTALL_SUCCEEDED = 1;
  
  private static final int MSG_COMMIT = 0;
  
  private static final String REMOVE_SPLIT_MARKER_EXTENSION = ".removed";
  
  private static final String TAG = "PackageInstaller";
  
  final String installerPackageName;
  
  final int installerUid;
  
  private final AtomicInteger mActiveCount = new AtomicInteger();
  
  private ArrayList<FileBridge> mBridges = new ArrayList<FileBridge>();
  
  private final VPackageInstallerService.InternalCallback mCallback;
  
  private float mClientProgress = 0.0F;
  
  private final Context mContext;
  
  private boolean mDestroyed = false;
  
  private String mFinalMessage;
  
  private int mFinalStatus;
  
  private final Handler mHandler;
  
  private final Handler.Callback mHandlerCallback = new Handler.Callback() {
      public boolean handleMessage(Message param1Message) {
        synchronized (PackageInstallerSession.this.mLock) {
          if (param1Message.obj != null)
            PackageInstallerSession.access$102(PackageInstallerSession.this, (IPackageInstallObserver2)param1Message.obj); 
          try {
            PackageInstallerSession.this.commitLocked();
          } catch (PackageManagerException packageManagerException) {
            String str = PackageInstallerSession.getCompleteMessage(packageManagerException);
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("Commit of session ");
            stringBuilder.append(PackageInstallerSession.this.sessionId);
            stringBuilder.append(" failed: ");
            stringBuilder.append(str);
            VLog.e("PackageInstaller", stringBuilder.toString());
            PackageInstallerSession.this.destroyInternal();
            PackageInstallerSession.this.dispatchSessionFinished(packageManagerException.error, str, null);
          } 
          return true;
        } 
      }
    };
  
  private float mInternalProgress = 0.0F;
  
  private final Object mLock = new Object();
  
  private String mPackageName;
  
  private boolean mPermissionsAccepted;
  
  private boolean mPrepared = false;
  
  private float mProgress = 0.0F;
  
  private IPackageInstallObserver2 mRemoteObserver;
  
  private float mReportedProgress = -1.0F;
  
  private File mResolvedBaseFile;
  
  private File mResolvedStageDir;
  
  private final List<File> mResolvedStagedFiles = new ArrayList<File>();
  
  private boolean mSealed = false;
  
  final SessionParams params;
  
  final int sessionId;
  
  final File stageDir;
  
  final int userId;
  
  public PackageInstallerSession(VPackageInstallerService.InternalCallback paramInternalCallback, Context paramContext, Looper paramLooper, String paramString, int paramInt1, int paramInt2, int paramInt3, SessionParams paramSessionParams, File paramFile) {
    this.mCallback = paramInternalCallback;
    this.mContext = paramContext;
    this.mHandler = new Handler(paramLooper, this.mHandlerCallback);
    this.installerPackageName = paramString;
    this.sessionId = paramInt1;
    this.userId = paramInt2;
    this.installerUid = paramInt3;
    this.mPackageName = paramSessionParams.appPackageName;
    this.params = paramSessionParams;
    this.stageDir = paramFile;
  }
  
  private void assertPreparedAndNotSealed(String paramString) {
    synchronized (this.mLock) {
      if (this.mPrepared) {
        if (!this.mSealed)
          return; 
        SecurityException securityException = new SecurityException();
        StringBuilder stringBuilder1 = new StringBuilder();
        this();
        stringBuilder1.append(paramString);
        stringBuilder1.append(" not allowed after commit");
        this(stringBuilder1.toString());
        throw securityException;
      } 
      IllegalStateException illegalStateException = new IllegalStateException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append(paramString);
      stringBuilder.append(" before prepared");
      this(stringBuilder.toString());
      throw illegalStateException;
    } 
  }
  
  private void commitLocked() throws PackageManagerException {
    if (!this.mDestroyed) {
      if (this.mSealed) {
        try {
          resolveStageDir();
        } catch (IOException iOException) {
          iOException.printStackTrace();
        } 
        validateInstallLocked();
        this.mInternalProgress = 0.5F;
        byte b1 = 1;
        computeProgressLocked(true);
        File[] arrayOfFile = this.stageDir.listFiles();
        int i = arrayOfFile.length;
        byte b2 = 0;
        boolean bool = false;
        while (b2 < i) {
          File file = arrayOfFile[b2];
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("found apk in stage dir: ");
          stringBuilder.append(file.getPath());
          VLog.e("PackageInstaller", stringBuilder.toString());
          if ((VAppManagerService.get().installPackage(Uri.fromFile(file), new VAppInstallerParams())).status == 0)
            bool = true; 
          b2++;
        } 
        destroyInternal();
        if (bool) {
          b2 = b1;
        } else {
          b2 = -115;
        } 
        dispatchSessionFinished(b2, null, null);
        return;
      } 
      throw new PackageManagerException(-110, "Session not sealed");
    } 
    throw new PackageManagerException(-110, "Session destroyed");
  }
  
  private void computeProgressLocked(boolean paramBoolean) {
    float f = constrain(this.mClientProgress * 0.8F, 0.0F, 0.8F) + constrain(this.mInternalProgress * 0.2F, 0.0F, 0.2F);
    this.mProgress = f;
    if (paramBoolean || Math.abs(f - this.mReportedProgress) >= 0.01D) {
      f = this.mProgress;
      this.mReportedProgress = f;
      this.mCallback.onSessionProgressChanged(this, f);
    } 
  }
  
  private static float constrain(float paramFloat1, float paramFloat2, float paramFloat3) {
    if (paramFloat1 >= paramFloat2) {
      paramFloat2 = paramFloat1;
      if (paramFloat1 > paramFloat3)
        paramFloat2 = paramFloat3; 
    } 
    return paramFloat2;
  }
  
  private void createRemoveSplitMarker(String paramString) throws IOException {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append(paramString);
      stringBuilder.append(".removed");
      paramString = stringBuilder.toString();
      if (FileUtils.isValidExtFilename(paramString)) {
        File file = new File();
        this(resolveStageDir(), paramString);
        file.createNewFile();
        Os.chmod(file.getAbsolutePath(), 0);
        return;
      } 
      IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
      stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Invalid marker: ");
      stringBuilder.append(paramString);
      this(stringBuilder.toString());
      throw illegalArgumentException;
    } catch (ErrnoException errnoException) {
      throw new IOException(errnoException);
    } 
  }
  
  private void destroyInternal() {
    synchronized (this.mLock) {
      this.mSealed = true;
      this.mDestroyed = true;
      Iterator<FileBridge> iterator = this.mBridges.iterator();
      while (iterator.hasNext())
        ((FileBridge)iterator.next()).forceClose(); 
      null = this.stageDir;
      if (null != null)
        FileUtils.deleteDir(null.getAbsolutePath()); 
      return;
    } 
  }
  
  private void dispatchSessionFinished(int paramInt, String paramString, Bundle paramBundle) {
    this.mFinalStatus = paramInt;
    this.mFinalMessage = paramString;
    IPackageInstallObserver2 iPackageInstallObserver2 = this.mRemoteObserver;
    if (iPackageInstallObserver2 != null)
      try {
        iPackageInstallObserver2.onPackageInstalled(this.mPackageName, paramInt, paramString, paramBundle);
      } catch (RemoteException remoteException) {} 
    boolean bool = true;
    if (paramInt != 1)
      bool = false; 
    this.mCallback.onSessionFinished(this, bool);
  }
  
  public static String getCompleteMessage(Throwable paramThrowable) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramThrowable.getMessage());
    while (true) {
      paramThrowable = paramThrowable.getCause();
      if (paramThrowable != null) {
        stringBuilder.append(": ");
        stringBuilder.append(paramThrowable.getMessage());
        continue;
      } 
      return stringBuilder.toString();
    } 
  }
  
  private ParcelFileDescriptor openReadInternal(String paramString) throws IOException {
    assertPreparedAndNotSealed("openRead");
    try {
      if (FileUtils.isValidExtFilename(paramString)) {
        File file = new File();
        this(resolveStageDir(), paramString);
        return ParcelFileDescriptor.dup(Os.open(file.getAbsolutePath(), OsConstants.O_RDONLY, 0));
      } 
      IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Invalid name: ");
      stringBuilder.append(paramString);
      this(stringBuilder.toString());
      throw illegalArgumentException;
    } catch (ErrnoException errnoException) {
      throw new IOException(errnoException);
    } 
  }
  
  private ParcelFileDescriptor openWriteInternal(String paramString, long paramLong1, long paramLong2) throws IOException {
    synchronized (this.mLock) {
      assertPreparedAndNotSealed("openWrite");
      FileBridge fileBridge = new FileBridge();
      this();
      this.mBridges.add(fileBridge);
      try {
        null = new File();
        super(resolveStageDir(), paramString);
        FileDescriptor fileDescriptor = Os.open(null.getAbsolutePath(), OsConstants.O_CREAT | OsConstants.O_WRONLY, 420);
        if (paramLong2 > 0L)
          Os.posix_fallocate(fileDescriptor, 0L, paramLong2); 
        if (paramLong1 > 0L)
          Os.lseek(fileDescriptor, paramLong1, OsConstants.SEEK_SET); 
        fileBridge.setTargetFile(fileDescriptor);
        fileBridge.start();
        return ParcelFileDescriptor.dup(fileBridge.getClientSocket());
      } catch (ErrnoException errnoException) {
        throw new IOException(errnoException);
      } 
    } 
  }
  
  private File resolveStageDir() throws IOException {
    synchronized (this.mLock) {
      if (this.mResolvedStageDir == null && this.stageDir != null) {
        this.mResolvedStageDir = this.stageDir;
        if (!this.stageDir.exists())
          this.stageDir.mkdirs(); 
      } 
      return this.mResolvedStageDir;
    } 
  }
  
  private void validateInstallLocked() throws PackageManagerException {
    this.mResolvedBaseFile = null;
    this.mResolvedStagedFiles.clear();
    File[] arrayOfFile = this.mResolvedStageDir.listFiles();
    if (arrayOfFile != null && arrayOfFile.length != 0) {
      int i = arrayOfFile.length;
      for (byte b = 0; b < i; b++) {
        File file = arrayOfFile[b];
        if (!file.isDirectory()) {
          File file1 = new File(this.mResolvedStageDir, "base.apk");
          if (!file.equals(file1))
            file.renameTo(file1); 
          this.mResolvedBaseFile = file1;
          this.mResolvedStagedFiles.add(file1);
        } 
      } 
      if (this.mResolvedBaseFile != null)
        return; 
      throw new PackageManagerException(-2, "Full install must include a base package");
    } 
    throw new PackageManagerException(-2, "No packages staged");
  }
  
  public void abandon() throws RemoteException {
    destroyInternal();
    dispatchSessionFinished(-115, "Session was abandoned", null);
  }
  
  public void addClientProgress(float paramFloat) throws RemoteException {
    synchronized (this.mLock) {
      setClientProgress(this.mClientProgress + paramFloat);
      return;
    } 
  }
  
  public void close() throws RemoteException {
    if (this.mActiveCount.decrementAndGet() == 0)
      this.mCallback.onSessionActiveChanged(this, false); 
  }
  
  public void commit(IntentSender paramIntentSender) throws RemoteException {
    synchronized (this.mLock) {
      SecurityException securityException;
      boolean bool = this.mSealed;
      if (!this.mSealed) {
        Iterator<FileBridge> iterator = this.mBridges.iterator();
        while (iterator.hasNext()) {
          if (((FileBridge)iterator.next()).isClosed())
            continue; 
          securityException = new SecurityException();
          this("Files still open");
          throw securityException;
        } 
        this.mSealed = true;
      } 
      this.mClientProgress = 1.0F;
      computeProgressLocked(true);
      if (!bool)
        this.mCallback.onSessionSealedBlocking(this); 
      this.mActiveCount.incrementAndGet();
      VPackageInstallerService.PackageInstallObserverAdapter packageInstallObserverAdapter = new VPackageInstallerService.PackageInstallObserverAdapter(this.mContext, (IntentSender)securityException, this.sessionId, this.userId);
      this.mHandler.obtainMessage(0, packageInstallObserverAdapter.getBinder()).sendToTarget();
      return;
    } 
  }
  
  public void commit(IntentSender paramIntentSender, boolean paramBoolean) throws RemoteException {
    commit(paramIntentSender);
  }
  
  public SessionInfo generateInfo() {
    SessionInfo sessionInfo = new SessionInfo();
    synchronized (this.mLock) {
      String str;
      boolean bool;
      sessionInfo.sessionId = this.sessionId;
      sessionInfo.installerPackageName = this.installerPackageName;
      if (this.mResolvedBaseFile != null) {
        str = this.mResolvedBaseFile.getAbsolutePath();
      } else {
        str = null;
      } 
      sessionInfo.resolvedBaseCodePath = str;
      sessionInfo.progress = this.mProgress;
      sessionInfo.sealed = this.mSealed;
      if (this.mActiveCount.get() > 0) {
        bool = true;
      } else {
        bool = false;
      } 
      sessionInfo.active = bool;
      sessionInfo.mode = this.params.mode;
      sessionInfo.sizeBytes = this.params.sizeBytes;
      sessionInfo.appPackageName = this.params.appPackageName;
      sessionInfo.appIcon = this.params.appIcon;
      sessionInfo.appLabel = this.params.appLabel;
      return sessionInfo;
    } 
  }
  
  public String[] getNames() throws RemoteException {
    assertPreparedAndNotSealed("getNames");
    try {
      return resolveStageDir().list();
    } catch (IOException iOException) {
      throw new IllegalStateException(iOException);
    } 
  }
  
  public boolean isMultiPackage() throws RemoteException {
    return false;
  }
  
  public void open() throws IOException {
    if (this.mActiveCount.getAndIncrement() == 0)
      this.mCallback.onSessionActiveChanged(this, true); 
    synchronized (this.mLock) {
      if (!this.mPrepared)
        if (this.stageDir != null) {
          this.mPrepared = true;
          this.mCallback.onSessionPrepared(this);
        } else {
          IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
          this("Exactly one of stageDir or stageCid stage must be set");
          throw illegalArgumentException;
        }  
      return;
    } 
  }
  
  public ParcelFileDescriptor openRead(String paramString) throws RemoteException {
    try {
      return openReadInternal(paramString);
    } catch (IOException iOException) {
      throw new IllegalStateException(iOException);
    } 
  }
  
  public ParcelFileDescriptor openWrite(String paramString, long paramLong1, long paramLong2) throws RemoteException {
    try {
      return openWriteInternal(paramString, paramLong1, paramLong2);
    } catch (IOException iOException) {
      throw new IllegalStateException(iOException);
    } 
  }
  
  public void removeSplit(String paramString) throws RemoteException {
    if (!TextUtils.isEmpty(this.params.appPackageName))
      try {
        createRemoveSplitMarker(paramString);
        return;
      } catch (IOException iOException) {
        throw new IllegalStateException(iOException);
      }  
    throw new IllegalStateException("Must specify package name to remove a split");
  }
  
  public void setClientProgress(float paramFloat) throws RemoteException {
    synchronized (this.mLock) {
      boolean bool;
      if (this.mClientProgress == 0.0F) {
        bool = true;
      } else {
        bool = false;
      } 
      this.mClientProgress = paramFloat;
      computeProgressLocked(bool);
      return;
    } 
  }
  
  void setPermissionsResult(boolean paramBoolean) {
    if (this.mSealed) {
      if (paramBoolean) {
        synchronized (this.mLock) {
          this.mPermissionsAccepted = true;
          this.mHandler.obtainMessage(0).sendToTarget();
        } 
      } else {
        destroyInternal();
        dispatchSessionFinished(-115, "User rejected permissions", null);
      } 
      return;
    } 
    throw new SecurityException("Must be sealed to accept permissions");
  }
  
  private class PackageManagerException extends Exception {
    public final int error;
    
    PackageManagerException(int param1Int, String param1String) {
      super(param1String);
      this.error = param1Int;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\installer\PackageInstallerSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */