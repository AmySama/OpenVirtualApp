package com.lody.virtual.server.pm.installer;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.FileUtils;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteOrder;

public class FileBridge extends Thread {
  private static final int CMD_CLOSE = 3;
  
  private static final int CMD_FSYNC = 2;
  
  private static final int CMD_WRITE = 1;
  
  private static final int MSG_LENGTH = 8;
  
  private static final String TAG = "FileBridge";
  
  private final FileDescriptor mClient = new FileDescriptor();
  
  private volatile boolean mClosed;
  
  private final FileDescriptor mServer = new FileDescriptor();
  
  private FileDescriptor mTarget;
  
  public FileBridge() {
    try {
      Os.socketpair(OsConstants.AF_UNIX, OsConstants.SOCK_STREAM, 0, this.mServer, this.mClient);
      return;
    } catch (ErrnoException errnoException) {
      throw new RuntimeException("Failed to create bridge");
    } 
  }
  
  public static void closeQuietly(FileDescriptor paramFileDescriptor) {
    if (paramFileDescriptor != null && paramFileDescriptor.valid())
      try {
        Os.close(paramFileDescriptor);
      } catch (ErrnoException errnoException) {
        errnoException.printStackTrace();
      }  
  }
  
  public static int read(FileDescriptor paramFileDescriptor, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    ArrayUtils.checkOffsetAndCount(paramArrayOfbyte.length, paramInt1, paramInt2);
    if (paramInt2 == 0)
      return 0; 
    try {
      paramInt2 = Os.read(paramFileDescriptor, paramArrayOfbyte, paramInt1, paramInt2);
      paramInt1 = paramInt2;
      if (paramInt2 == 0)
        paramInt1 = -1; 
      return paramInt1;
    } catch (ErrnoException errnoException) {
      if (errnoException.errno == OsConstants.EAGAIN)
        return 0; 
      throw new IOException(errnoException);
    } 
  }
  
  public static void write(FileDescriptor paramFileDescriptor, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    ArrayUtils.checkOffsetAndCount(paramArrayOfbyte.length, paramInt1, paramInt2);
    int i = paramInt2;
    if (paramInt2 == 0)
      return; 
    while (i > 0) {
      try {
        paramInt2 = Os.write(paramFileDescriptor, paramArrayOfbyte, paramInt1, i);
        i -= paramInt2;
        paramInt1 += paramInt2;
      } catch (ErrnoException errnoException) {
        throw new IOException(errnoException);
      } 
    } 
  }
  
  public void forceClose() {
    closeQuietly(this.mTarget);
    closeQuietly(this.mServer);
    closeQuietly(this.mClient);
    this.mClosed = true;
  }
  
  public FileDescriptor getClientSocket() {
    return this.mClient;
  }
  
  public boolean isClosed() {
    return this.mClosed;
  }
  
  public void run() {
    byte[] arrayOfByte = new byte[8192];
    try {
      while (read(this.mServer, arrayOfByte, 0, 8) == 8) {
        IOException iOException;
        int i = FileUtils.peekInt(arrayOfByte, 0, ByteOrder.BIG_ENDIAN);
        if (i == 1) {
          i = FileUtils.peekInt(arrayOfByte, 4, ByteOrder.BIG_ENDIAN);
          while (i > 0) {
            int j = read(this.mServer, arrayOfByte, 0, Math.min(8192, i));
            if (j != -1) {
              write(this.mTarget, arrayOfByte, 0, j);
              i -= j;
              continue;
            } 
            iOException = new IOException();
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("Unexpected EOF; still expected ");
            stringBuilder.append(i);
            stringBuilder.append(" bytes");
            this(stringBuilder.toString());
            throw iOException;
          } 
          continue;
        } 
        if (i == 2) {
          Os.fsync(this.mTarget);
          write(this.mServer, (byte[])iOException, 0, 8);
          continue;
        } 
        if (i == 3) {
          Os.fsync(this.mTarget);
          Os.close(this.mTarget);
          this.mClosed = true;
          write(this.mServer, (byte[])iOException, 0, 8);
          break;
        } 
      } 
      forceClose();
    } catch (ErrnoException errnoException) {
      Log.wtf("FileBridge", "Failed during bridge", (Throwable)errnoException);
      forceClose();
    } catch (IOException iOException) {
    
    } finally {}
  }
  
  public void setTargetFile(FileDescriptor paramFileDescriptor) {
    this.mTarget = paramFileDescriptor;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\installer\FileBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */