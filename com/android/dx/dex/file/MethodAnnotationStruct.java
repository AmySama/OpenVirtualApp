package com.android.dx.dex.file;

import com.android.dx.rop.annotation.Annotations;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstBaseMethodRef;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import com.android.dx.util.ToHuman;

public final class MethodAnnotationStruct implements ToHuman, Comparable<MethodAnnotationStruct> {
  private AnnotationSetItem annotations;
  
  private final CstMethodRef method;
  
  public MethodAnnotationStruct(CstMethodRef paramCstMethodRef, AnnotationSetItem paramAnnotationSetItem) {
    if (paramCstMethodRef != null) {
      if (paramAnnotationSetItem != null) {
        this.method = paramCstMethodRef;
        this.annotations = paramAnnotationSetItem;
        return;
      } 
      throw new NullPointerException("annotations == null");
    } 
    throw new NullPointerException("method == null");
  }
  
  public void addContents(DexFile paramDexFile) {
    MethodIdsSection methodIdsSection = paramDexFile.getMethodIds();
    MixedItemSection mixedItemSection = paramDexFile.getWordData();
    methodIdsSection.intern((CstBaseMethodRef)this.method);
    this.annotations = mixedItemSection.<AnnotationSetItem>intern(this.annotations);
  }
  
  public int compareTo(MethodAnnotationStruct paramMethodAnnotationStruct) {
    return this.method.compareTo((Constant)paramMethodAnnotationStruct.method);
  }
  
  public boolean equals(Object paramObject) {
    return !(paramObject instanceof MethodAnnotationStruct) ? false : this.method.equals(((MethodAnnotationStruct)paramObject).method);
  }
  
  public Annotations getAnnotations() {
    return this.annotations.getAnnotations();
  }
  
  public CstMethodRef getMethod() {
    return this.method;
  }
  
  public int hashCode() {
    return this.method.hashCode();
  }
  
  public String toHuman() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.method.toHuman());
    stringBuilder.append(": ");
    stringBuilder.append(this.annotations);
    return stringBuilder.toString();
  }
  
  public void writeTo(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    int i = paramDexFile.getMethodIds().indexOf((CstBaseMethodRef)this.method);
    int j = this.annotations.getAbsoluteOffset();
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("    ");
      stringBuilder.append(this.method.toHuman());
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("      method_idx:      ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("      annotations_off: ");
      stringBuilder.append(Hex.u4(j));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(i);
    paramAnnotatedOutput.writeInt(j);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\MethodAnnotationStruct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */