package com.android.dx.dex.file;

import com.android.dx.util.AnnotatedOutput;
import java.util.Collection;

public abstract class Section {
  private final int alignment;
  
  private final DexFile file;
  
  private int fileOffset;
  
  private final String name;
  
  private boolean prepared;
  
  public Section(String paramString, DexFile paramDexFile, int paramInt) {
    if (paramDexFile != null) {
      validateAlignment(paramInt);
      this.name = paramString;
      this.file = paramDexFile;
      this.alignment = paramInt;
      this.fileOffset = -1;
      this.prepared = false;
      return;
    } 
    throw new NullPointerException("file == null");
  }
  
  public static void validateAlignment(int paramInt) {
    if (paramInt > 0 && (paramInt & paramInt - 1) == 0)
      return; 
    throw new IllegalArgumentException("invalid alignment");
  }
  
  protected final void align(AnnotatedOutput paramAnnotatedOutput) {
    paramAnnotatedOutput.alignTo(this.alignment);
  }
  
  public abstract int getAbsoluteItemOffset(Item paramItem);
  
  public final int getAbsoluteOffset(int paramInt) {
    if (paramInt >= 0) {
      int i = this.fileOffset;
      if (i >= 0)
        return i + paramInt; 
      throw new RuntimeException("fileOffset not yet set");
    } 
    throw new IllegalArgumentException("relative < 0");
  }
  
  public final int getAlignment() {
    return this.alignment;
  }
  
  public final DexFile getFile() {
    return this.file;
  }
  
  public final int getFileOffset() {
    int i = this.fileOffset;
    if (i >= 0)
      return i; 
    throw new RuntimeException("fileOffset not set");
  }
  
  protected final String getName() {
    return this.name;
  }
  
  public abstract Collection<? extends Item> items();
  
  public final void prepare() {
    throwIfPrepared();
    prepare0();
    this.prepared = true;
  }
  
  protected abstract void prepare0();
  
  public final int setFileOffset(int paramInt) {
    if (paramInt >= 0) {
      if (this.fileOffset < 0) {
        int i = this.alignment - 1;
        paramInt = paramInt + i & i;
        this.fileOffset = paramInt;
        return paramInt;
      } 
      throw new RuntimeException("fileOffset already set");
    } 
    throw new IllegalArgumentException("fileOffset < 0");
  }
  
  protected final void throwIfNotPrepared() {
    if (this.prepared)
      return; 
    throw new RuntimeException("not prepared");
  }
  
  protected final void throwIfPrepared() {
    if (!this.prepared)
      return; 
    throw new RuntimeException("already prepared");
  }
  
  public abstract int writeSize();
  
  public final void writeTo(AnnotatedOutput paramAnnotatedOutput) {
    StringBuilder stringBuilder;
    throwIfNotPrepared();
    align(paramAnnotatedOutput);
    int i = paramAnnotatedOutput.getCursor();
    int j = this.fileOffset;
    if (j < 0) {
      this.fileOffset = i;
    } else if (j != i) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("alignment mismatch: for ");
      stringBuilder.append(this);
      stringBuilder.append(", at ");
      stringBuilder.append(i);
      stringBuilder.append(", but expected ");
      stringBuilder.append(this.fileOffset);
      throw new RuntimeException(stringBuilder.toString());
    } 
    if (stringBuilder.annotates())
      if (this.name != null) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("\n");
        stringBuilder1.append(this.name);
        stringBuilder1.append(":");
        stringBuilder.annotate(0, stringBuilder1.toString());
      } else if (i != 0) {
        stringBuilder.annotate(0, "\n");
      }  
    writeTo0((AnnotatedOutput)stringBuilder);
  }
  
  protected abstract void writeTo0(AnnotatedOutput paramAnnotatedOutput);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\Section.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */