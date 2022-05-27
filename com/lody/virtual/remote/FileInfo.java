package com.lody.virtual.remote;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.File;

public class FileInfo implements Parcelable {
  public static final Parcelable.Creator<FileInfo> CREATOR = new Parcelable.Creator<FileInfo>() {
      public FileInfo createFromParcel(Parcel param1Parcel) {
        return new FileInfo(param1Parcel);
      }
      
      public FileInfo[] newArray(int param1Int) {
        return new FileInfo[param1Int];
      }
    };
  
  public boolean isDirectory;
  
  public String path;
  
  private FileInfo() {}
  
  protected FileInfo(Parcel paramParcel) {
    boolean bool;
    if (paramParcel.readByte() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.isDirectory = bool;
    this.path = paramParcel.readString();
  }
  
  public FileInfo(File paramFile) {
    this.isDirectory = paramFile.isDirectory();
    this.path = paramFile.getPath();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeByte(this.isDirectory);
    paramParcel.writeString(this.path);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\FileInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */