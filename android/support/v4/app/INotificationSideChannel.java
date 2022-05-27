package android.support.v4.app;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INotificationSideChannel extends IInterface {
  void cancel(String paramString1, int paramInt, String paramString2) throws RemoteException;
  
  void cancelAll(String paramString) throws RemoteException;
  
  void notify(String paramString1, int paramInt, String paramString2, Notification paramNotification) throws RemoteException;
  
  public static abstract class Stub extends Binder implements INotificationSideChannel {
    private static final String DESCRIPTOR = "android.support.v4.app.INotificationSideChannel";
    
    static final int TRANSACTION_cancel = 2;
    
    static final int TRANSACTION_cancelAll = 3;
    
    static final int TRANSACTION_notify = 1;
    
    public Stub() {
      attachInterface(this, "android.support.v4.app.INotificationSideChannel");
    }
    
    public static INotificationSideChannel asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.support.v4.app.INotificationSideChannel");
      return (iInterface != null && iInterface instanceof INotificationSideChannel) ? (INotificationSideChannel)iInterface : new Proxy(param1IBinder);
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 3) {
            if (param1Int1 != 1598968902)
              return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
            param1Parcel2.writeString("android.support.v4.app.INotificationSideChannel");
            return true;
          } 
          param1Parcel1.enforceInterface("android.support.v4.app.INotificationSideChannel");
          cancelAll(param1Parcel1.readString());
          return true;
        } 
        param1Parcel1.enforceInterface("android.support.v4.app.INotificationSideChannel");
        cancel(param1Parcel1.readString(), param1Parcel1.readInt(), param1Parcel1.readString());
        return true;
      } 
      param1Parcel1.enforceInterface("android.support.v4.app.INotificationSideChannel");
      String str1 = param1Parcel1.readString();
      param1Int1 = param1Parcel1.readInt();
      String str2 = param1Parcel1.readString();
      if (param1Parcel1.readInt() != 0) {
        Notification notification = (Notification)Notification.CREATOR.createFromParcel(param1Parcel1);
      } else {
        param1Parcel1 = null;
      } 
      notify(str1, param1Int1, str2, (Notification)param1Parcel1);
      return true;
    }
    
    private static class Proxy implements INotificationSideChannel {
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void cancel(String param2String1, int param2Int, String param2String2) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
          parcel.writeString(param2String1);
          parcel.writeInt(param2Int);
          parcel.writeString(param2String2);
          this.mRemote.transact(2, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void cancelAll(String param2String) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
          parcel.writeString(param2String);
          this.mRemote.transact(3, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "android.support.v4.app.INotificationSideChannel";
      }
      
      public void notify(String param2String1, int param2Int, String param2String2, Notification param2Notification) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
          parcel.writeString(param2String1);
          parcel.writeInt(param2Int);
          parcel.writeString(param2String2);
          if (param2Notification != null) {
            parcel.writeInt(1);
            param2Notification.writeToParcel(parcel, 0);
          } else {
            parcel.writeInt(0);
          } 
          this.mRemote.transact(1, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements INotificationSideChannel {
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void cancel(String param1String1, int param1Int, String param1String2) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
        parcel.writeString(param1String1);
        parcel.writeInt(param1Int);
        parcel.writeString(param1String2);
        this.mRemote.transact(2, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void cancelAll(String param1String) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
        parcel.writeString(param1String);
        this.mRemote.transact(3, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "android.support.v4.app.INotificationSideChannel";
    }
    
    public void notify(String param1String1, int param1Int, String param1String2, Notification param1Notification) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.app.INotificationSideChannel");
        parcel.writeString(param1String1);
        parcel.writeInt(param1Int);
        parcel.writeString(param1String2);
        if (param1Notification != null) {
          parcel.writeInt(1);
          param1Notification.writeToParcel(parcel, 0);
        } else {
          parcel.writeInt(0);
        } 
        this.mRemote.transact(1, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\INotificationSideChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */