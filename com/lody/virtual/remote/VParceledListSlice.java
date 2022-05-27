package com.lody.virtual.remote;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class VParceledListSlice<T extends Parcelable> implements Parcelable {
  public static final Parcelable.ClassLoaderCreator<VParceledListSlice> CREATOR = new Parcelable.ClassLoaderCreator<VParceledListSlice>() {
      public VParceledListSlice createFromParcel(Parcel param1Parcel) {
        return new VParceledListSlice<Parcelable>(param1Parcel, null);
      }
      
      public VParceledListSlice createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
        return new VParceledListSlice<Parcelable>(param1Parcel, param1ClassLoader);
      }
      
      public VParceledListSlice[] newArray(int param1Int) {
        return new VParceledListSlice[param1Int];
      }
    };
  
  private static boolean DEBUG = false;
  
  private static final int MAX_FIRST_IPC_SIZE = 131072;
  
  private static final int MAX_IPC_SIZE = 262144;
  
  private static String TAG = "ParceledListSlice";
  
  private final List<T> mList;
  
  static {
    DEBUG = false;
  }
  
  private VParceledListSlice(Parcel paramParcel, ClassLoader paramClassLoader) {
    ClassLoader classLoader = paramClassLoader;
    if (paramClassLoader == null)
      classLoader = VParceledListSlice.class.getClassLoader(); 
    int i = paramParcel.readInt();
    this.mList = new ArrayList<T>(i);
    if (DEBUG) {
      String str = TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Retrieving ");
      stringBuilder.append(i);
      stringBuilder.append(" items");
      Log.d(str, stringBuilder.toString());
    } 
    if (i <= 0)
      return; 
    paramClassLoader = null;
    byte b = 0;
    while (b < i && paramParcel.readInt() != 0) {
      ClassLoader classLoader1;
      Parcelable parcelable = paramParcel.readParcelable(classLoader);
      if (paramClassLoader == null) {
        Class<?> clazz = parcelable.getClass();
      } else {
        classLoader1 = paramClassLoader;
        if (parcelable != null) {
          verifySameType((Class<?>)paramClassLoader, parcelable.getClass());
          classLoader1 = paramClassLoader;
        } 
      } 
      this.mList.add((T)parcelable);
      if (DEBUG) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Read inline #");
        stringBuilder.append(b);
        stringBuilder.append(": ");
        List<T> list = this.mList;
        stringBuilder.append(list.get(list.size() - 1));
        Log.d(str, stringBuilder.toString());
      } 
      b++;
      paramClassLoader = classLoader1;
    } 
    if (b >= i)
      return; 
    IBinder iBinder = paramParcel.readStrongBinder();
    while (b < i) {
      if (DEBUG) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Reading more @");
        stringBuilder.append(b);
        stringBuilder.append(" of ");
        stringBuilder.append(i);
        stringBuilder.append(": retriever=");
        stringBuilder.append(iBinder);
        Log.d(str, stringBuilder.toString());
      } 
      Parcel parcel2 = Parcel.obtain();
      Parcel parcel1 = Parcel.obtain();
      parcel2.writeInt(b);
      try {
        iBinder.transact(1, parcel2, parcel1, 0);
        while (b < i && parcel1.readInt() != 0) {
          Parcelable parcelable = parcel1.readParcelable(classLoader);
          if (parcelable != null)
            verifySameType((Class<?>)paramClassLoader, parcelable.getClass()); 
          this.mList.add((T)parcelable);
          if (DEBUG) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Read extra #");
            stringBuilder.append(b);
            stringBuilder.append(": ");
            List<T> list = this.mList;
            stringBuilder.append(list.get(list.size() - 1));
            Log.d(str, stringBuilder.toString());
          } 
          b++;
        } 
        parcel1.recycle();
        parcel2.recycle();
      } catch (RemoteException remoteException) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failure retrieving array; only received ");
        stringBuilder.append(b);
        stringBuilder.append(" of ");
        stringBuilder.append(i);
        Log.w(str, stringBuilder.toString(), (Throwable)remoteException);
        break;
      } 
    } 
  }
  
  public VParceledListSlice(List<T> paramList) {
    this.mList = paramList;
  }
  
  private static void verifySameType(Class<?> paramClass1, Class<?> paramClass2) {
    if (paramClass2.equals(paramClass1))
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Can't unparcel type ");
    stringBuilder.append(paramClass2.getName());
    stringBuilder.append(" in list of type ");
    stringBuilder.append(paramClass1.getName());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public int describeContents() {
    byte b = 0;
    int i = 0;
    while (b < this.mList.size()) {
      i |= ((Parcelable)this.mList.get(b)).describeContents();
      b++;
    } 
    return i;
  }
  
  public List<T> getList() {
    return this.mList;
  }
  
  public void writeToParcel(Parcel paramParcel, final int callFlags) {
    final int N = this.mList.size();
    paramParcel.writeInt(i);
    if (DEBUG) {
      String str = TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Writing ");
      stringBuilder.append(i);
      stringBuilder.append(" items");
      Log.d(str, stringBuilder.toString());
    } 
    if (i > 0) {
      final Class<?> listElementClass = ((Parcelable)this.mList.get(0)).getClass();
      byte b;
      for (b = 0; b < i && paramParcel.dataSize() < 131072; b++) {
        paramParcel.writeInt(1);
        Parcelable parcelable = (Parcelable)this.mList.get(b);
        if (parcelable == null) {
          paramParcel.writeString(null);
        } else {
          verifySameType(clazz, parcelable.getClass());
          paramParcel.writeParcelable(parcelable, callFlags);
        } 
        if (DEBUG) {
          String str = TAG;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Wrote inline #");
          stringBuilder.append(b);
          stringBuilder.append(": ");
          stringBuilder.append(this.mList.get(b));
          Log.d(str, stringBuilder.toString());
        } 
      } 
      if (b < i) {
        paramParcel.writeInt(0);
        Binder binder = new Binder() {
            protected boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
              if (param1Int1 != 1)
                return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
              param1Int2 = param1Parcel1.readInt();
              param1Int1 = param1Int2;
              if (VParceledListSlice.DEBUG) {
                String str = VParceledListSlice.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Writing more @");
                stringBuilder.append(param1Int2);
                stringBuilder.append(" of ");
                stringBuilder.append(N);
                Log.d(str, stringBuilder.toString());
                param1Int1 = param1Int2;
              } 
              while (param1Int1 < N && param1Parcel2.dataSize() < 262144) {
                param1Parcel2.writeInt(1);
                Parcelable parcelable = VParceledListSlice.this.mList.get(param1Int1);
                VParceledListSlice.verifySameType(listElementClass, parcelable.getClass());
                param1Parcel2.writeParcelable(parcelable, callFlags);
                if (VParceledListSlice.DEBUG) {
                  String str = VParceledListSlice.TAG;
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append("Wrote extra #");
                  stringBuilder.append(param1Int1);
                  stringBuilder.append(": ");
                  stringBuilder.append(VParceledListSlice.this.mList.get(param1Int1));
                  Log.d(str, stringBuilder.toString());
                } 
                param1Int1++;
              } 
              if (param1Int1 < N) {
                if (VParceledListSlice.DEBUG) {
                  String str = VParceledListSlice.TAG;
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append("Breaking @");
                  stringBuilder.append(param1Int1);
                  stringBuilder.append(" of ");
                  stringBuilder.append(N);
                  Log.d(str, stringBuilder.toString());
                } 
                param1Parcel2.writeInt(0);
              } 
              return true;
            }
          };
        if (DEBUG) {
          String str = TAG;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Breaking @");
          stringBuilder.append(b);
          stringBuilder.append(" of ");
          stringBuilder.append(i);
          stringBuilder.append(": retriever=");
          stringBuilder.append(binder);
          Log.d(str, stringBuilder.toString());
        } 
        paramParcel.writeStrongBinder((IBinder)binder);
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\VParceledListSlice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */