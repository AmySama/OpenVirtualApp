package com.android.dx.dex.file;

import com.android.dx.rop.annotation.Annotations;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import com.android.dx.util.ToHuman;

public final class FieldAnnotationStruct implements ToHuman, Comparable<FieldAnnotationStruct> {
  private AnnotationSetItem annotations;
  
  private final CstFieldRef field;
  
  public FieldAnnotationStruct(CstFieldRef paramCstFieldRef, AnnotationSetItem paramAnnotationSetItem) {
    if (paramCstFieldRef != null) {
      if (paramAnnotationSetItem != null) {
        this.field = paramCstFieldRef;
        this.annotations = paramAnnotationSetItem;
        return;
      } 
      throw new NullPointerException("annotations == null");
    } 
    throw new NullPointerException("field == null");
  }
  
  public void addContents(DexFile paramDexFile) {
    FieldIdsSection fieldIdsSection = paramDexFile.getFieldIds();
    MixedItemSection mixedItemSection = paramDexFile.getWordData();
    fieldIdsSection.intern(this.field);
    this.annotations = mixedItemSection.<AnnotationSetItem>intern(this.annotations);
  }
  
  public int compareTo(FieldAnnotationStruct paramFieldAnnotationStruct) {
    return this.field.compareTo((Constant)paramFieldAnnotationStruct.field);
  }
  
  public boolean equals(Object paramObject) {
    return !(paramObject instanceof FieldAnnotationStruct) ? false : this.field.equals(((FieldAnnotationStruct)paramObject).field);
  }
  
  public Annotations getAnnotations() {
    return this.annotations.getAnnotations();
  }
  
  public CstFieldRef getField() {
    return this.field;
  }
  
  public int hashCode() {
    return this.field.hashCode();
  }
  
  public String toHuman() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.field.toHuman());
    stringBuilder.append(": ");
    stringBuilder.append(this.annotations);
    return stringBuilder.toString();
  }
  
  public void writeTo(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    int i = paramDexFile.getFieldIds().indexOf(this.field);
    int j = this.annotations.getAbsoluteOffset();
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("    ");
      stringBuilder.append(this.field.toHuman());
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("      field_idx:       ");
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\FieldAnnotationStruct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */