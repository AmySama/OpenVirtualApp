package com.lody.virtual.helper;

import android.os.Parcel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class PersistenceLayer {
  private File mPersistenceFile;
  
  public PersistenceLayer(File paramFile) {
    this.mPersistenceFile = paramFile;
  }
  
  public abstract int getCurrentVersion();
  
  public final File getPersistenceFile() {
    return this.mPersistenceFile;
  }
  
  public void onPersistenceFileDamage() {}
  
  public void read() {
    File file = this.mPersistenceFile;
    Parcel parcel = Parcel.obtain();
    try {
      FileInputStream fileInputStream = new FileInputStream();
      this(file);
      int i = (int)file.length();
      byte[] arrayOfByte = new byte[i];
      int j = fileInputStream.read(arrayOfByte);
      fileInputStream.close();
      if (j == i) {
        parcel.unmarshall(arrayOfByte, 0, i);
        parcel.setDataPosition(0);
        if (verifyMagic(parcel)) {
          readPersistenceData(parcel, parcel.readInt());
        } else {
          onPersistenceFileDamage();
          IOException iOException = new IOException();
          this("Invalid persistence file.");
          throw iOException;
        } 
      } else {
        IOException iOException = new IOException();
        this("Unable to read Persistence file.");
        throw iOException;
      } 
    } catch (Exception exception) {
      if (!(exception instanceof java.io.FileNotFoundException))
        exception.printStackTrace(); 
    } finally {
      Exception exception;
    } 
    parcel.recycle();
  }
  
  public abstract void readPersistenceData(Parcel paramParcel, int paramInt);
  
  public void save() {
    Parcel parcel = Parcel.obtain();
    try {
      writeMagic(parcel);
      parcel.writeInt(getCurrentVersion());
      writePersistenceData(parcel);
      FileOutputStream fileOutputStream = new FileOutputStream();
      this(this.mPersistenceFile);
      fileOutputStream.write(parcel.marshall());
      fileOutputStream.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      Exception exception;
    } 
    parcel.recycle();
  }
  
  public boolean verifyMagic(Parcel paramParcel) {
    return true;
  }
  
  public void writeMagic(Parcel paramParcel) {}
  
  public abstract void writePersistenceData(Parcel paramParcel);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\PersistenceLayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */