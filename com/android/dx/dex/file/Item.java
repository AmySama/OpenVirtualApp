package com.android.dx.dex.file;

import com.android.dx.util.AnnotatedOutput;

public abstract class Item {
  public abstract void addContents(DexFile paramDexFile);
  
  public abstract ItemType itemType();
  
  public final String typeName() {
    return itemType().toHuman();
  }
  
  public abstract int writeSize();
  
  public abstract void writeTo(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\Item.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */