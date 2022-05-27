package android.content;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIntentReceiver extends IInterface {
  void performReceive(Intent paramIntent, int paramInt1, String paramString, Bundle paramBundle, boolean paramBoolean1, boolean paramBoolean2, int paramInt2) throws RemoteException;
  
  public static class Default implements IIntentReceiver {
    public IBinder asBinder() {
      return null;
    }
    
    public void performReceive(Intent param1Intent, int param1Int1, String param1String, Bundle param1Bundle, boolean param1Boolean1, boolean param1Boolean2, int param1Int2) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IIntentReceiver {
    private static final String DESCRIPTOR = "android.content.IIntentReceiver";
    
    static final int TRANSACTION_performReceive = 1;
    
    public Stub() {
      attachInterface(this, "android.content.IIntentReceiver");
    }
    
    public static IIntentReceiver asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.content.IIntentReceiver");
      return (iInterface != null && iInterface instanceof IIntentReceiver) ? (IIntentReceiver)iInterface : new Proxy(param1IBinder);
    }
    
    public static IIntentReceiver getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IIntentReceiver param1IIntentReceiver) {
      if (Proxy.sDefaultImpl == null && param1IIntentReceiver != null) {
        Proxy.sDefaultImpl = param1IIntentReceiver;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      Intent intent;
      boolean bool1;
      boolean bool2;
      if (param1Int1 != 1) {
        if (param1Int1 != 1598968902)
          return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
        param1Parcel2.writeString("android.content.IIntentReceiver");
        return true;
      } 
      param1Parcel1.enforceInterface("android.content.IIntentReceiver");
      param1Int1 = param1Parcel1.readInt();
      Bundle bundle = null;
      if (param1Int1 != 0) {
        intent = (Intent)Intent.CREATOR.createFromParcel(param1Parcel1);
      } else {
        intent = null;
      } 
      param1Int1 = param1Parcel1.readInt();
      String str = param1Parcel1.readString();
      if (param1Parcel1.readInt() != 0)
        bundle = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
      if (param1Parcel1.readInt() != 0) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      if (param1Parcel1.readInt() != 0) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      performReceive(intent, param1Int1, str, bundle, bool1, bool2, param1Parcel1.readInt());
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IIntentReceiver {
      public static IIntentReceiver sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.content.IIntentReceiver";
      }
      
      public void performReceive(Intent param2Intent, int param2Int1, String param2String, Bundle param2Bundle, boolean param2Boolean1, boolean param2Boolean2, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("android.content.IIntentReceiver");
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int1);
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2Boolean1) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (param2Boolean2) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          parcel1.writeInt(param2Int2);
          try {
            if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IIntentReceiver.Stub.getDefaultImpl() != null) {
              IIntentReceiver.Stub.getDefaultImpl().performReceive(param2Intent, param2Int1, param2String, param2Bundle, param2Boolean1, param2Boolean2, param2Int2);
              parcel2.recycle();
              parcel1.recycle();
              return;
            } 
            parcel2.readException();
            parcel2.recycle();
            parcel1.recycle();
            return;
          } finally {}
        } finally {}
        parcel2.recycle();
        parcel1.recycle();
        throw param2Intent;
      }
    }
  }
  
  private static class Proxy implements IIntentReceiver {
    public static IIntentReceiver sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.content.IIntentReceiver";
    }
    
    public void performReceive(Intent param1Intent, int param1Int1, String param1String, Bundle param1Bundle, boolean param1Boolean1, boolean param1Boolean2, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("android.content.IIntentReceiver");
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int1);
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1Boolean1) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (param1Boolean2) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        parcel1.writeInt(param1Int2);
        try {
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IIntentReceiver.Stub.getDefaultImpl() != null) {
            IIntentReceiver.Stub.getDefaultImpl().performReceive(param1Intent, param1Int1, param1String, param1Bundle, param1Boolean1, param1Boolean2, param1Int2);
            parcel2.recycle();
            parcel1.recycle();
            return;
          } 
          parcel2.readException();
          parcel2.recycle();
          parcel1.recycle();
          return;
        } finally {}
      } finally {}
      parcel2.recycle();
      parcel1.recycle();
      throw param1Intent;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\IIntentReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */