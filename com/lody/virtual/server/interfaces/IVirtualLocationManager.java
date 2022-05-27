package com.lody.virtual.server.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.lody.virtual.remote.vloc.VCell;
import com.lody.virtual.remote.vloc.VLocation;
import com.lody.virtual.remote.vloc.VWifi;
import java.util.List;

public interface IVirtualLocationManager extends IInterface {
  List<VCell> getAllCell(int paramInt, String paramString) throws RemoteException;
  
  List<VWifi> getAllWifi(int paramInt, String paramString) throws RemoteException;
  
  VCell getCell(int paramInt, String paramString) throws RemoteException;
  
  VLocation getGlobalLocation() throws RemoteException;
  
  VLocation getLocation(int paramInt, String paramString) throws RemoteException;
  
  int getMode(int paramInt, String paramString) throws RemoteException;
  
  List<VCell> getNeighboringCell(int paramInt, String paramString) throws RemoteException;
  
  void setAllCell(int paramInt, String paramString, List<VCell> paramList) throws RemoteException;
  
  void setAllWifi(int paramInt, String paramString, List<VWifi> paramList) throws RemoteException;
  
  void setCell(int paramInt, String paramString, VCell paramVCell) throws RemoteException;
  
  void setGlobalAllCell(List<VCell> paramList) throws RemoteException;
  
  void setGlobalCell(VCell paramVCell) throws RemoteException;
  
  void setGlobalLocation(VLocation paramVLocation) throws RemoteException;
  
  void setGlobalNeighboringCell(List<VCell> paramList) throws RemoteException;
  
  void setLocation(int paramInt, String paramString, VLocation paramVLocation) throws RemoteException;
  
  void setMode(int paramInt1, String paramString, int paramInt2) throws RemoteException;
  
  void setNeighboringCell(int paramInt, String paramString, List<VCell> paramList) throws RemoteException;
  
  public static class Default implements IVirtualLocationManager {
    public IBinder asBinder() {
      return null;
    }
    
    public List<VCell> getAllCell(int param1Int, String param1String) throws RemoteException {
      return null;
    }
    
    public List<VWifi> getAllWifi(int param1Int, String param1String) throws RemoteException {
      return null;
    }
    
    public VCell getCell(int param1Int, String param1String) throws RemoteException {
      return null;
    }
    
    public VLocation getGlobalLocation() throws RemoteException {
      return null;
    }
    
    public VLocation getLocation(int param1Int, String param1String) throws RemoteException {
      return null;
    }
    
    public int getMode(int param1Int, String param1String) throws RemoteException {
      return 0;
    }
    
    public List<VCell> getNeighboringCell(int param1Int, String param1String) throws RemoteException {
      return null;
    }
    
    public void setAllCell(int param1Int, String param1String, List<VCell> param1List) throws RemoteException {}
    
    public void setAllWifi(int param1Int, String param1String, List<VWifi> param1List) throws RemoteException {}
    
    public void setCell(int param1Int, String param1String, VCell param1VCell) throws RemoteException {}
    
    public void setGlobalAllCell(List<VCell> param1List) throws RemoteException {}
    
    public void setGlobalCell(VCell param1VCell) throws RemoteException {}
    
    public void setGlobalLocation(VLocation param1VLocation) throws RemoteException {}
    
    public void setGlobalNeighboringCell(List<VCell> param1List) throws RemoteException {}
    
    public void setLocation(int param1Int, String param1String, VLocation param1VLocation) throws RemoteException {}
    
    public void setMode(int param1Int1, String param1String, int param1Int2) throws RemoteException {}
    
    public void setNeighboringCell(int param1Int, String param1String, List<VCell> param1List) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IVirtualLocationManager {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IVirtualLocationManager";
    
    static final int TRANSACTION_getAllCell = 12;
    
    static final int TRANSACTION_getAllWifi = 6;
    
    static final int TRANSACTION_getCell = 11;
    
    static final int TRANSACTION_getGlobalLocation = 17;
    
    static final int TRANSACTION_getLocation = 15;
    
    static final int TRANSACTION_getMode = 1;
    
    static final int TRANSACTION_getNeighboringCell = 13;
    
    static final int TRANSACTION_setAllCell = 4;
    
    static final int TRANSACTION_setAllWifi = 5;
    
    static final int TRANSACTION_setCell = 3;
    
    static final int TRANSACTION_setGlobalAllCell = 9;
    
    static final int TRANSACTION_setGlobalCell = 8;
    
    static final int TRANSACTION_setGlobalLocation = 16;
    
    static final int TRANSACTION_setGlobalNeighboringCell = 10;
    
    static final int TRANSACTION_setLocation = 14;
    
    static final int TRANSACTION_setMode = 2;
    
    static final int TRANSACTION_setNeighboringCell = 7;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IVirtualLocationManager");
    }
    
    public static IVirtualLocationManager asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
      return (iInterface != null && iInterface instanceof IVirtualLocationManager) ? (IVirtualLocationManager)iInterface : new Proxy(param1IBinder);
    }
    
    public static IVirtualLocationManager getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IVirtualLocationManager param1IVirtualLocationManager) {
      if (Proxy.sDefaultImpl == null && param1IVirtualLocationManager != null) {
        Proxy.sDefaultImpl = param1IVirtualLocationManager;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        VLocation vLocation1;
        List<VCell> list1;
        VCell vCell1;
        List<VWifi> list;
        VCell vCell3;
        String str1;
        VCell vCell2;
        String str3;
        VLocation vLocation2 = null;
        VLocation vLocation3 = null;
        String str2 = null;
        VLocation vLocation4 = null;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 17:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            vLocation1 = getGlobalLocation();
            param1Parcel2.writeNoException();
            if (vLocation1 != null) {
              param1Parcel2.writeInt(1);
              vLocation1.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 16:
            vLocation1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            vLocation2 = vLocation4;
            if (vLocation1.readInt() != 0)
              vLocation2 = (VLocation)VLocation.CREATOR.createFromParcel((Parcel)vLocation1); 
            setGlobalLocation(vLocation2);
            param1Parcel2.writeNoException();
            return true;
          case 15:
            vLocation1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            vLocation1 = getLocation(vLocation1.readInt(), vLocation1.readString());
            param1Parcel2.writeNoException();
            if (vLocation1 != null) {
              param1Parcel2.writeInt(1);
              vLocation1.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 14:
            vLocation1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            param1Int1 = vLocation1.readInt();
            str2 = vLocation1.readString();
            if (vLocation1.readInt() != 0)
              vLocation2 = (VLocation)VLocation.CREATOR.createFromParcel((Parcel)vLocation1); 
            setLocation(param1Int1, str2, vLocation2);
            param1Parcel2.writeNoException();
            return true;
          case 13:
            vLocation1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            list1 = getNeighboringCell(vLocation1.readInt(), vLocation1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list1);
            return true;
          case 12:
            list1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            list1 = getAllCell(list1.readInt(), list1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list1);
            return true;
          case 11:
            list1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            vCell1 = getCell(list1.readInt(), list1.readString());
            param1Parcel2.writeNoException();
            if (vCell1 != null) {
              param1Parcel2.writeInt(1);
              vCell1.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 10:
            vCell1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            setGlobalNeighboringCell(vCell1.createTypedArrayList(VCell.CREATOR));
            param1Parcel2.writeNoException();
            return true;
          case 9:
            vCell1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            setGlobalAllCell(vCell1.createTypedArrayList(VCell.CREATOR));
            param1Parcel2.writeNoException();
            return true;
          case 8:
            vCell1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            vLocation2 = vLocation3;
            if (vCell1.readInt() != 0)
              vCell3 = (VCell)VCell.CREATOR.createFromParcel((Parcel)vCell1); 
            setGlobalCell(vCell3);
            param1Parcel2.writeNoException();
            return true;
          case 7:
            vCell1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            setNeighboringCell(vCell1.readInt(), vCell1.readString(), vCell1.createTypedArrayList(VCell.CREATOR));
            param1Parcel2.writeNoException();
            return true;
          case 6:
            vCell1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            list = getAllWifi(vCell1.readInt(), vCell1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 5:
            list.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            setAllWifi(list.readInt(), list.readString(), list.createTypedArrayList(VWifi.CREATOR));
            param1Parcel2.writeNoException();
            return true;
          case 4:
            list.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            setAllCell(list.readInt(), list.readString(), list.createTypedArrayList(VCell.CREATOR));
            param1Parcel2.writeNoException();
            return true;
          case 3:
            list.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            param1Int1 = list.readInt();
            str3 = list.readString();
            str1 = str2;
            if (list.readInt() != 0)
              vCell2 = (VCell)VCell.CREATOR.createFromParcel((Parcel)list); 
            setCell(param1Int1, str3, vCell2);
            param1Parcel2.writeNoException();
            return true;
          case 2:
            list.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
            setMode(list.readInt(), list.readString(), list.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 1:
            break;
        } 
        list.enforceInterface("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        param1Int1 = getMode(list.readInt(), list.readString());
        param1Parcel2.writeNoException();
        param1Parcel2.writeInt(param1Int1);
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.server.interfaces.IVirtualLocationManager");
      return true;
    }
    
    private static class Proxy implements IVirtualLocationManager {
      public static IVirtualLocationManager sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public List<VCell> getAllCell(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null)
            return IVirtualLocationManager.Stub.getDefaultImpl().getAllCell(param2Int, param2String); 
          parcel2.readException();
          return parcel2.createTypedArrayList(VCell.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<VWifi> getAllWifi(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null)
            return IVirtualLocationManager.Stub.getDefaultImpl().getAllWifi(param2Int, param2String); 
          parcel2.readException();
          return parcel2.createTypedArrayList(VWifi.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public VCell getCell(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null)
            return IVirtualLocationManager.Stub.getDefaultImpl().getCell(param2Int, param2String); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            VCell vCell = (VCell)VCell.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (VCell)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public VLocation getGlobalLocation() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          VLocation vLocation;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            vLocation = IVirtualLocationManager.Stub.getDefaultImpl().getGlobalLocation();
            return vLocation;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            vLocation = (VLocation)VLocation.CREATOR.createFromParcel(parcel2);
          } else {
            vLocation = null;
          } 
          return vLocation;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IVirtualLocationManager";
      }
      
      public VLocation getLocation(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(15, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null)
            return IVirtualLocationManager.Stub.getDefaultImpl().getLocation(param2Int, param2String); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            VLocation vLocation = (VLocation)VLocation.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (VLocation)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getMode(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            param2Int = IVirtualLocationManager.Stub.getDefaultImpl().getMode(param2Int, param2String);
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
      
      public List<VCell> getNeighboringCell(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null)
            return IVirtualLocationManager.Stub.getDefaultImpl().getNeighboringCell(param2Int, param2String); 
          parcel2.readException();
          return parcel2.createTypedArrayList(VCell.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setAllCell(int param2Int, String param2String, List<VCell> param2List) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          parcel1.writeTypedList(param2List);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            IVirtualLocationManager.Stub.getDefaultImpl().setAllCell(param2Int, param2String, param2List);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setAllWifi(int param2Int, String param2String, List<VWifi> param2List) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          parcel1.writeTypedList(param2List);
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            IVirtualLocationManager.Stub.getDefaultImpl().setAllWifi(param2Int, param2String, param2List);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setCell(int param2Int, String param2String, VCell param2VCell) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (param2VCell != null) {
            parcel1.writeInt(1);
            param2VCell.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            IVirtualLocationManager.Stub.getDefaultImpl().setCell(param2Int, param2String, param2VCell);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setGlobalAllCell(List<VCell> param2List) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeTypedList(param2List);
          if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            IVirtualLocationManager.Stub.getDefaultImpl().setGlobalAllCell(param2List);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setGlobalCell(VCell param2VCell) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          if (param2VCell != null) {
            parcel1.writeInt(1);
            param2VCell.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            IVirtualLocationManager.Stub.getDefaultImpl().setGlobalCell(param2VCell);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setGlobalLocation(VLocation param2VLocation) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          if (param2VLocation != null) {
            parcel1.writeInt(1);
            param2VLocation.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(16, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            IVirtualLocationManager.Stub.getDefaultImpl().setGlobalLocation(param2VLocation);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setGlobalNeighboringCell(List<VCell> param2List) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeTypedList(param2List);
          if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            IVirtualLocationManager.Stub.getDefaultImpl().setGlobalNeighboringCell(param2List);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setLocation(int param2Int, String param2String, VLocation param2VLocation) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (param2VLocation != null) {
            parcel1.writeInt(1);
            param2VLocation.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            IVirtualLocationManager.Stub.getDefaultImpl().setLocation(param2Int, param2String, param2VLocation);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setMode(int param2Int1, String param2String, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int1);
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            IVirtualLocationManager.Stub.getDefaultImpl().setMode(param2Int1, param2String, param2Int2);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setNeighboringCell(int param2Int, String param2String, List<VCell> param2List) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          parcel1.writeTypedList(param2List);
          if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
            IVirtualLocationManager.Stub.getDefaultImpl().setNeighboringCell(param2Int, param2String, param2List);
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
  
  private static class Proxy implements IVirtualLocationManager {
    public static IVirtualLocationManager sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public List<VCell> getAllCell(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null)
          return IVirtualLocationManager.Stub.getDefaultImpl().getAllCell(param1Int, param1String); 
        parcel2.readException();
        return parcel2.createTypedArrayList(VCell.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<VWifi> getAllWifi(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null)
          return IVirtualLocationManager.Stub.getDefaultImpl().getAllWifi(param1Int, param1String); 
        parcel2.readException();
        return parcel2.createTypedArrayList(VWifi.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public VCell getCell(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null)
          return IVirtualLocationManager.Stub.getDefaultImpl().getCell(param1Int, param1String); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          VCell vCell = (VCell)VCell.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (VCell)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public VLocation getGlobalLocation() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        VLocation vLocation;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          vLocation = IVirtualLocationManager.Stub.getDefaultImpl().getGlobalLocation();
          return vLocation;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          vLocation = (VLocation)VLocation.CREATOR.createFromParcel(parcel2);
        } else {
          vLocation = null;
        } 
        return vLocation;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IVirtualLocationManager";
    }
    
    public VLocation getLocation(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(15, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null)
          return IVirtualLocationManager.Stub.getDefaultImpl().getLocation(param1Int, param1String); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          VLocation vLocation = (VLocation)VLocation.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (VLocation)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getMode(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          param1Int = IVirtualLocationManager.Stub.getDefaultImpl().getMode(param1Int, param1String);
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
    
    public List<VCell> getNeighboringCell(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null)
          return IVirtualLocationManager.Stub.getDefaultImpl().getNeighboringCell(param1Int, param1String); 
        parcel2.readException();
        return parcel2.createTypedArrayList(VCell.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setAllCell(int param1Int, String param1String, List<VCell> param1List) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        parcel1.writeTypedList(param1List);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          IVirtualLocationManager.Stub.getDefaultImpl().setAllCell(param1Int, param1String, param1List);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setAllWifi(int param1Int, String param1String, List<VWifi> param1List) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        parcel1.writeTypedList(param1List);
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          IVirtualLocationManager.Stub.getDefaultImpl().setAllWifi(param1Int, param1String, param1List);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setCell(int param1Int, String param1String, VCell param1VCell) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (param1VCell != null) {
          parcel1.writeInt(1);
          param1VCell.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          IVirtualLocationManager.Stub.getDefaultImpl().setCell(param1Int, param1String, param1VCell);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setGlobalAllCell(List<VCell> param1List) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeTypedList(param1List);
        if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          IVirtualLocationManager.Stub.getDefaultImpl().setGlobalAllCell(param1List);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setGlobalCell(VCell param1VCell) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        if (param1VCell != null) {
          parcel1.writeInt(1);
          param1VCell.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          IVirtualLocationManager.Stub.getDefaultImpl().setGlobalCell(param1VCell);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setGlobalLocation(VLocation param1VLocation) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        if (param1VLocation != null) {
          parcel1.writeInt(1);
          param1VLocation.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(16, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          IVirtualLocationManager.Stub.getDefaultImpl().setGlobalLocation(param1VLocation);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setGlobalNeighboringCell(List<VCell> param1List) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeTypedList(param1List);
        if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          IVirtualLocationManager.Stub.getDefaultImpl().setGlobalNeighboringCell(param1List);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setLocation(int param1Int, String param1String, VLocation param1VLocation) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (param1VLocation != null) {
          parcel1.writeInt(1);
          param1VLocation.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          IVirtualLocationManager.Stub.getDefaultImpl().setLocation(param1Int, param1String, param1VLocation);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setMode(int param1Int1, String param1String, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int1);
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          IVirtualLocationManager.Stub.getDefaultImpl().setMode(param1Int1, param1String, param1Int2);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setNeighboringCell(int param1Int, String param1String, List<VCell> param1List) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualLocationManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        parcel1.writeTypedList(param1List);
        if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IVirtualLocationManager.Stub.getDefaultImpl() != null) {
          IVirtualLocationManager.Stub.getDefaultImpl().setNeighboringCell(param1Int, param1String, param1List);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IVirtualLocationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */