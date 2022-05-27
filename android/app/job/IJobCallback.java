package android.app.job;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IJobCallback extends IInterface {
  void acknowledgeStartMessage(int paramInt, boolean paramBoolean) throws RemoteException;
  
  void acknowledgeStopMessage(int paramInt, boolean paramBoolean) throws RemoteException;
  
  boolean completeWork(int paramInt1, int paramInt2) throws RemoteException;
  
  JobWorkItem dequeueWork(int paramInt) throws RemoteException;
  
  void jobFinished(int paramInt, boolean paramBoolean) throws RemoteException;
  
  public static class Default implements IJobCallback {
    public void acknowledgeStartMessage(int param1Int, boolean param1Boolean) throws RemoteException {}
    
    public void acknowledgeStopMessage(int param1Int, boolean param1Boolean) throws RemoteException {}
    
    public IBinder asBinder() {
      return null;
    }
    
    public boolean completeWork(int param1Int1, int param1Int2) throws RemoteException {
      return false;
    }
    
    public JobWorkItem dequeueWork(int param1Int) throws RemoteException {
      return null;
    }
    
    public void jobFinished(int param1Int, boolean param1Boolean) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IJobCallback {
    private static final String DESCRIPTOR = "android.app.job.IJobCallback";
    
    static final int TRANSACTION_acknowledgeStartMessage = 1;
    
    static final int TRANSACTION_acknowledgeStopMessage = 2;
    
    static final int TRANSACTION_completeWork = 4;
    
    static final int TRANSACTION_dequeueWork = 3;
    
    static final int TRANSACTION_jobFinished = 5;
    
    public Stub() {
      attachInterface(this, "android.app.job.IJobCallback");
    }
    
    public static IJobCallback asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.app.job.IJobCallback");
      return (iInterface != null && iInterface instanceof IJobCallback) ? (IJobCallback)iInterface : new Proxy(param1IBinder);
    }
    
    public static IJobCallback getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IJobCallback param1IJobCallback) {
      if (Proxy.sDefaultImpl == null && param1IJobCallback != null) {
        Proxy.sDefaultImpl = param1IJobCallback;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      JobWorkItem jobWorkItem;
      boolean bool1 = false;
      boolean bool2 = false;
      boolean bool3 = false;
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 3) {
            if (param1Int1 != 4) {
              if (param1Int1 != 5) {
                if (param1Int1 != 1598968902)
                  return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
                param1Parcel2.writeString("android.app.job.IJobCallback");
                return true;
              } 
              param1Parcel1.enforceInterface("android.app.job.IJobCallback");
              param1Int1 = param1Parcel1.readInt();
              if (param1Parcel1.readInt() != 0)
                bool3 = true; 
              jobFinished(param1Int1, bool3);
              param1Parcel2.writeNoException();
              return true;
            } 
            param1Parcel1.enforceInterface("android.app.job.IJobCallback");
            boolean bool = completeWork(param1Parcel1.readInt(), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool);
            return true;
          } 
          param1Parcel1.enforceInterface("android.app.job.IJobCallback");
          jobWorkItem = dequeueWork(param1Parcel1.readInt());
          param1Parcel2.writeNoException();
          if (jobWorkItem != null) {
            param1Parcel2.writeInt(1);
            jobWorkItem.writeToParcel(param1Parcel2, 1);
          } else {
            param1Parcel2.writeInt(0);
          } 
          return true;
        } 
        jobWorkItem.enforceInterface("android.app.job.IJobCallback");
        param1Int1 = jobWorkItem.readInt();
        bool3 = bool1;
        if (jobWorkItem.readInt() != 0)
          bool3 = true; 
        acknowledgeStopMessage(param1Int1, bool3);
        param1Parcel2.writeNoException();
        return true;
      } 
      jobWorkItem.enforceInterface("android.app.job.IJobCallback");
      param1Int1 = jobWorkItem.readInt();
      bool3 = bool2;
      if (jobWorkItem.readInt() != 0)
        bool3 = true; 
      acknowledgeStartMessage(param1Int1, bool3);
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IJobCallback {
      public static IJobCallback sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public void acknowledgeStartMessage(int param2Int, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("android.app.job.IJobCallback");
          parcel1.writeInt(param2Int);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IJobCallback.Stub.getDefaultImpl() != null) {
            IJobCallback.Stub.getDefaultImpl().acknowledgeStartMessage(param2Int, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void acknowledgeStopMessage(int param2Int, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("android.app.job.IJobCallback");
          parcel1.writeInt(param2Int);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IJobCallback.Stub.getDefaultImpl() != null) {
            IJobCallback.Stub.getDefaultImpl().acknowledgeStopMessage(param2Int, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public boolean completeWork(int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.app.job.IJobCallback");
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(4, parcel1, parcel2, 0) && IJobCallback.Stub.getDefaultImpl() != null) {
            bool = IJobCallback.Stub.getDefaultImpl().completeWork(param2Int1, param2Int2);
            return bool;
          } 
          parcel2.readException();
          param2Int1 = parcel2.readInt();
          if (param2Int1 != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public JobWorkItem dequeueWork(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          JobWorkItem jobWorkItem;
          parcel1.writeInterfaceToken("android.app.job.IJobCallback");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IJobCallback.Stub.getDefaultImpl() != null) {
            jobWorkItem = IJobCallback.Stub.getDefaultImpl().dequeueWork(param2Int);
            return jobWorkItem;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            jobWorkItem = (JobWorkItem)JobWorkItem.CREATOR.createFromParcel(parcel2);
          } else {
            jobWorkItem = null;
          } 
          return jobWorkItem;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "android.app.job.IJobCallback";
      }
      
      public void jobFinished(int param2Int, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("android.app.job.IJobCallback");
          parcel1.writeInt(param2Int);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IJobCallback.Stub.getDefaultImpl() != null) {
            IJobCallback.Stub.getDefaultImpl().jobFinished(param2Int, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IJobCallback {
    public static IJobCallback sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public void acknowledgeStartMessage(int param1Int, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("android.app.job.IJobCallback");
        parcel1.writeInt(param1Int);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IJobCallback.Stub.getDefaultImpl() != null) {
          IJobCallback.Stub.getDefaultImpl().acknowledgeStartMessage(param1Int, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void acknowledgeStopMessage(int param1Int, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("android.app.job.IJobCallback");
        parcel1.writeInt(param1Int);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IJobCallback.Stub.getDefaultImpl() != null) {
          IJobCallback.Stub.getDefaultImpl().acknowledgeStopMessage(param1Int, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public boolean completeWork(int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.app.job.IJobCallback");
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(4, parcel1, parcel2, 0) && IJobCallback.Stub.getDefaultImpl() != null) {
          bool = IJobCallback.Stub.getDefaultImpl().completeWork(param1Int1, param1Int2);
          return bool;
        } 
        parcel2.readException();
        param1Int1 = parcel2.readInt();
        if (param1Int1 != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public JobWorkItem dequeueWork(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        JobWorkItem jobWorkItem;
        parcel1.writeInterfaceToken("android.app.job.IJobCallback");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IJobCallback.Stub.getDefaultImpl() != null) {
          jobWorkItem = IJobCallback.Stub.getDefaultImpl().dequeueWork(param1Int);
          return jobWorkItem;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          jobWorkItem = (JobWorkItem)JobWorkItem.CREATOR.createFromParcel(parcel2);
        } else {
          jobWorkItem = null;
        } 
        return jobWorkItem;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "android.app.job.IJobCallback";
    }
    
    public void jobFinished(int param1Int, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("android.app.job.IJobCallback");
        parcel1.writeInt(param1Int);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IJobCallback.Stub.getDefaultImpl() != null) {
          IJobCallback.Stub.getDefaultImpl().jobFinished(param1Int, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\app\job\IJobCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */