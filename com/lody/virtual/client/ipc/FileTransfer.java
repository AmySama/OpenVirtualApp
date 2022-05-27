package com.lody.virtual.client.ipc;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.remote.FileInfo;
import com.lody.virtual.server.fs.IFileTransfer;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileTransfer {
  private static final FileTransfer sInstance = new FileTransfer();
  
  private IFileTransfer mTransfer;
  
  public static FileTransfer get() {
    return sInstance;
  }
  
  private Object getStubInterface() {
    return IFileTransfer.Stub.asInterface(ServiceManagerNative.getService("file-transfer"));
  }
  
  public void copyDirectory(File paramFile1, File paramFile2) {
    FileInfo[] arrayOfFileInfo = listFiles(paramFile1);
    if (arrayOfFileInfo == null)
      return; 
    FileUtils.ensureDirCreate(paramFile2);
    int i = arrayOfFileInfo.length;
    for (byte b = 0; b < i; b++) {
      FileInfo fileInfo = arrayOfFileInfo[b];
      File file1 = new File(fileInfo.path);
      File file2 = new File(paramFile2, file1.getName());
      if (fileInfo.isDirectory) {
        copyDirectory(file1, file2);
      } else {
        copyFile(file1, file2);
      } 
    } 
  }
  
  public void copyFile(File paramFile1, File paramFile2) {
    FileUtils.ensureDirCreate(paramFile2.getParentFile());
    ParcelFileDescriptor parcelFileDescriptor = openFile(paramFile1);
    if (parcelFileDescriptor == null)
      return; 
    ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
    try {
      FileUtils.writeToFile((InputStream)autoCloseInputStream, paramFile2);
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    FileUtils.closeQuietly((Closeable)autoCloseInputStream);
  }
  
  public IFileTransfer getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mTransfer : Lcom/lody/virtual/server/fs/IFileTransfer;
    //   4: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   7: ifne -> 41
    //   10: ldc com/lody/virtual/client/ipc/FileTransfer
    //   12: monitorenter
    //   13: aload_0
    //   14: ldc com/lody/virtual/server/fs/IFileTransfer
    //   16: aload_0
    //   17: invokespecial getStubInterface : ()Ljava/lang/Object;
    //   20: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   23: checkcast com/lody/virtual/server/fs/IFileTransfer
    //   26: putfield mTransfer : Lcom/lody/virtual/server/fs/IFileTransfer;
    //   29: ldc com/lody/virtual/client/ipc/FileTransfer
    //   31: monitorexit
    //   32: goto -> 41
    //   35: astore_1
    //   36: ldc com/lody/virtual/client/ipc/FileTransfer
    //   38: monitorexit
    //   39: aload_1
    //   40: athrow
    //   41: aload_0
    //   42: getfield mTransfer : Lcom/lody/virtual/server/fs/IFileTransfer;
    //   45: areturn
    // Exception table:
    //   from	to	target	type
    //   13	32	35	finally
    //   36	39	35	finally
  }
  
  public FileInfo[] listFiles(File paramFile) {
    return listFiles(paramFile.getPath());
  }
  
  public FileInfo[] listFiles(String paramString) {
    try {
      return getService().listFiles(paramString);
    } catch (RemoteException remoteException) {
      return (FileInfo[])VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public ParcelFileDescriptor openFile(File paramFile) {
    return openFile(paramFile.getAbsolutePath());
  }
  
  public ParcelFileDescriptor openFile(String paramString) {
    try {
      return getService().openFile(paramString);
    } catch (RemoteException remoteException) {
      return (ParcelFileDescriptor)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\FileTransfer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */