package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public final class SharedLibraryInfo implements Parcelable {
  public static final Parcelable.Creator<SharedLibraryInfo> CREATOR = new Parcelable.Creator<SharedLibraryInfo>() {
      public SharedLibraryInfo createFromParcel(Parcel param1Parcel) {
        return null;
      }
      
      public SharedLibraryInfo[] newArray(int param1Int) {
        return null;
      }
    };
  
  public static final String PLATFORM_PACKAGE_NAME = "android";
  
  public static final int TYPE_BUILTIN = 0;
  
  public static final int TYPE_DYNAMIC = 1;
  
  public static final int TYPE_STATIC = 2;
  
  public static final int VERSION_UNDEFINED = -1;
  
  public SharedLibraryInfo(String paramString1, String paramString2, List<String> paramList, String paramString3, long paramLong, int paramInt, VersionedPackage paramVersionedPackage, List<VersionedPackage> paramList1, List<SharedLibraryInfo> paramList2, boolean paramBoolean) {
    throw new RuntimeException("Just gamb stub!");
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    throw new RuntimeException("Stub!");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\pm\SharedLibraryInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */