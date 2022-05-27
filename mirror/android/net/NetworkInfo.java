package mirror.android.net;

import mirror.MethodParams;
import mirror.RefBoolean;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefInt;
import mirror.RefObject;

public class NetworkInfo {
  public static Class<?> TYPE = RefClass.load(NetworkInfo.class, android.net.NetworkInfo.class);
  
  @MethodParams({int.class, int.class, String.class, String.class})
  public static RefConstructor<android.net.NetworkInfo> ctor;
  
  @MethodParams({int.class})
  public static RefConstructor<android.net.NetworkInfo> ctorOld;
  
  public static RefObject<android.net.NetworkInfo.DetailedState> mDetailedState;
  
  public static RefBoolean mIsAvailable;
  
  public static RefInt mNetworkType;
  
  public static RefObject<android.net.NetworkInfo.State> mState;
  
  public static RefObject<String> mTypeName;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\net\NetworkInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */