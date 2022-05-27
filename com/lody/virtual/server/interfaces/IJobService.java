package com.lody.virtual.server.interfaces;

import android.app.job.JobInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.lody.virtual.remote.VJobWorkItem;
import java.util.List;

public interface IJobService extends IInterface {
  void cancel(int paramInt1, int paramInt2) throws RemoteException;
  
  void cancelAll(int paramInt) throws RemoteException;
  
  int enqueue(int paramInt, JobInfo paramJobInfo, VJobWorkItem paramVJobWorkItem) throws RemoteException;
  
  List<JobInfo> getAllPendingJobs(int paramInt) throws RemoteException;
  
  JobInfo getPendingJob(int paramInt1, int paramInt2) throws RemoteException;
  
  int schedule(int paramInt, JobInfo paramJobInfo) throws RemoteException;
  
  public static class Default implements IJobService {
    public IBinder asBinder() {
      return null;
    }
    
    public void cancel(int param1Int1, int param1Int2) throws RemoteException {}
    
    public void cancelAll(int param1Int) throws RemoteException {}
    
    public int enqueue(int param1Int, JobInfo param1JobInfo, VJobWorkItem param1VJobWorkItem) throws RemoteException {
      return 0;
    }
    
    public List<JobInfo> getAllPendingJobs(int param1Int) throws RemoteException {
      return null;
    }
    
    public JobInfo getPendingJob(int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public int schedule(int param1Int, JobInfo param1JobInfo) throws RemoteException {
      return 0;
    }
  }
  
  public static abstract class Stub extends Binder implements IJobService {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IJobService";
    
    static final int TRANSACTION_cancel = 2;
    
    static final int TRANSACTION_cancelAll = 3;
    
    static final int TRANSACTION_enqueue = 6;
    
    static final int TRANSACTION_getAllPendingJobs = 4;
    
    static final int TRANSACTION_getPendingJob = 5;
    
    static final int TRANSACTION_schedule = 1;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IJobService");
    }
    
    public static IJobService asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IJobService");
      return (iInterface != null && iInterface instanceof IJobService) ? (IJobService)iInterface : new Proxy(param1IBinder);
    }
    
    public static IJobService getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IJobService param1IJobService) {
      if (Proxy.sDefaultImpl == null && param1IJobService != null) {
        Proxy.sDefaultImpl = param1IJobService;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        JobInfo jobInfo1;
        List<JobInfo> list;
        JobInfo jobInfo2 = null;
        VJobWorkItem vJobWorkItem = null;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 6:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IJobService");
            param1Int1 = param1Parcel1.readInt();
            if (param1Parcel1.readInt() != 0) {
              jobInfo2 = (JobInfo)JobInfo.CREATOR.createFromParcel(param1Parcel1);
            } else {
              jobInfo2 = null;
            } 
            if (param1Parcel1.readInt() != 0)
              vJobWorkItem = (VJobWorkItem)VJobWorkItem.CREATOR.createFromParcel(param1Parcel1); 
            param1Int1 = enqueue(param1Int1, jobInfo2, vJobWorkItem);
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(param1Int1);
            return true;
          case 5:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IJobService");
            jobInfo1 = getPendingJob(param1Parcel1.readInt(), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            if (jobInfo1 != null) {
              param1Parcel2.writeInt(1);
              jobInfo1.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 4:
            jobInfo1.enforceInterface("com.lody.virtual.server.interfaces.IJobService");
            list = getAllPendingJobs(jobInfo1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 3:
            list.enforceInterface("com.lody.virtual.server.interfaces.IJobService");
            cancelAll(list.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 2:
            list.enforceInterface("com.lody.virtual.server.interfaces.IJobService");
            cancel(list.readInt(), list.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 1:
            break;
        } 
        list.enforceInterface("com.lody.virtual.server.interfaces.IJobService");
        param1Int1 = list.readInt();
        if (list.readInt() != 0)
          jobInfo2 = (JobInfo)JobInfo.CREATOR.createFromParcel((Parcel)list); 
        param1Int1 = schedule(param1Int1, jobInfo2);
        param1Parcel2.writeNoException();
        param1Parcel2.writeInt(param1Int1);
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.server.interfaces.IJobService");
      return true;
    }
    
    private static class Proxy implements IJobService {
      public static IJobService sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void cancel(int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null) {
            IJobService.Stub.getDefaultImpl().cancel(param2Int1, param2Int2);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void cancelAll(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null) {
            IJobService.Stub.getDefaultImpl().cancelAll(param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int enqueue(int param2Int, JobInfo param2JobInfo, VJobWorkItem param2VJobWorkItem) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
          parcel1.writeInt(param2Int);
          if (param2JobInfo != null) {
            parcel1.writeInt(1);
            param2JobInfo.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2VJobWorkItem != null) {
            parcel1.writeInt(1);
            param2VJobWorkItem.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null) {
            param2Int = IJobService.Stub.getDefaultImpl().enqueue(param2Int, param2JobInfo, param2VJobWorkItem);
            return param2Int;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          return param2Int;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<JobInfo> getAllPendingJobs(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null)
            return IJobService.Stub.getDefaultImpl().getAllPendingJobs(param2Int); 
          parcel2.readException();
          return parcel2.createTypedArrayList(JobInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IJobService";
      }
      
      public JobInfo getPendingJob(int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          JobInfo jobInfo;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null) {
            jobInfo = IJobService.Stub.getDefaultImpl().getPendingJob(param2Int1, param2Int2);
            return jobInfo;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            jobInfo = (JobInfo)JobInfo.CREATOR.createFromParcel(parcel2);
          } else {
            jobInfo = null;
          } 
          return jobInfo;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int schedule(int param2Int, JobInfo param2JobInfo) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
          parcel1.writeInt(param2Int);
          if (param2JobInfo != null) {
            parcel1.writeInt(1);
            param2JobInfo.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null) {
            param2Int = IJobService.Stub.getDefaultImpl().schedule(param2Int, param2JobInfo);
            return param2Int;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          return param2Int;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IJobService {
    public static IJobService sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void cancel(int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null) {
          IJobService.Stub.getDefaultImpl().cancel(param1Int1, param1Int2);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void cancelAll(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null) {
          IJobService.Stub.getDefaultImpl().cancelAll(param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int enqueue(int param1Int, JobInfo param1JobInfo, VJobWorkItem param1VJobWorkItem) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
        parcel1.writeInt(param1Int);
        if (param1JobInfo != null) {
          parcel1.writeInt(1);
          param1JobInfo.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1VJobWorkItem != null) {
          parcel1.writeInt(1);
          param1VJobWorkItem.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null) {
          param1Int = IJobService.Stub.getDefaultImpl().enqueue(param1Int, param1JobInfo, param1VJobWorkItem);
          return param1Int;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        return param1Int;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<JobInfo> getAllPendingJobs(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null)
          return IJobService.Stub.getDefaultImpl().getAllPendingJobs(param1Int); 
        parcel2.readException();
        return parcel2.createTypedArrayList(JobInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IJobService";
    }
    
    public JobInfo getPendingJob(int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        JobInfo jobInfo;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null) {
          jobInfo = IJobService.Stub.getDefaultImpl().getPendingJob(param1Int1, param1Int2);
          return jobInfo;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          jobInfo = (JobInfo)JobInfo.CREATOR.createFromParcel(parcel2);
        } else {
          jobInfo = null;
        } 
        return jobInfo;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int schedule(int param1Int, JobInfo param1JobInfo) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IJobService");
        parcel1.writeInt(param1Int);
        if (param1JobInfo != null) {
          parcel1.writeInt(1);
          param1JobInfo.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IJobService.Stub.getDefaultImpl() != null) {
          param1Int = IJobService.Stub.getDefaultImpl().schedule(param1Int, param1JobInfo);
          return param1Int;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        return param1Int;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IJobService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */