package com.android.dx.dex.file;

import com.android.dx.rop.annotation.Annotations;
import com.android.dx.rop.annotation.AnnotationsList;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class AnnotationsDirectoryItem extends OffsettedItem {
  private static final int ALIGNMENT = 4;
  
  private static final int ELEMENT_SIZE = 8;
  
  private static final int HEADER_SIZE = 16;
  
  private AnnotationSetItem classAnnotations = null;
  
  private ArrayList<FieldAnnotationStruct> fieldAnnotations = null;
  
  private ArrayList<MethodAnnotationStruct> methodAnnotations = null;
  
  private ArrayList<ParameterAnnotationStruct> parameterAnnotations = null;
  
  public AnnotationsDirectoryItem() {
    super(4, -1);
  }
  
  private static int listSize(ArrayList<?> paramArrayList) {
    return (paramArrayList == null) ? 0 : paramArrayList.size();
  }
  
  public void addContents(DexFile paramDexFile) {
    MixedItemSection mixedItemSection = paramDexFile.getWordData();
    AnnotationSetItem annotationSetItem = this.classAnnotations;
    if (annotationSetItem != null)
      this.classAnnotations = mixedItemSection.<AnnotationSetItem>intern(annotationSetItem); 
    ArrayList<FieldAnnotationStruct> arrayList2 = this.fieldAnnotations;
    if (arrayList2 != null) {
      Iterator<FieldAnnotationStruct> iterator = arrayList2.iterator();
      while (iterator.hasNext())
        ((FieldAnnotationStruct)iterator.next()).addContents(paramDexFile); 
    } 
    ArrayList<MethodAnnotationStruct> arrayList1 = this.methodAnnotations;
    if (arrayList1 != null) {
      Iterator<MethodAnnotationStruct> iterator = arrayList1.iterator();
      while (iterator.hasNext())
        ((MethodAnnotationStruct)iterator.next()).addContents(paramDexFile); 
    } 
    ArrayList<ParameterAnnotationStruct> arrayList = this.parameterAnnotations;
    if (arrayList != null) {
      Iterator<ParameterAnnotationStruct> iterator = arrayList.iterator();
      while (iterator.hasNext())
        ((ParameterAnnotationStruct)iterator.next()).addContents(paramDexFile); 
    } 
  }
  
  public void addFieldAnnotations(CstFieldRef paramCstFieldRef, Annotations paramAnnotations, DexFile paramDexFile) {
    if (this.fieldAnnotations == null)
      this.fieldAnnotations = new ArrayList<FieldAnnotationStruct>(); 
    this.fieldAnnotations.add(new FieldAnnotationStruct(paramCstFieldRef, new AnnotationSetItem(paramAnnotations, paramDexFile)));
  }
  
  public void addMethodAnnotations(CstMethodRef paramCstMethodRef, Annotations paramAnnotations, DexFile paramDexFile) {
    if (this.methodAnnotations == null)
      this.methodAnnotations = new ArrayList<MethodAnnotationStruct>(); 
    this.methodAnnotations.add(new MethodAnnotationStruct(paramCstMethodRef, new AnnotationSetItem(paramAnnotations, paramDexFile)));
  }
  
  public void addParameterAnnotations(CstMethodRef paramCstMethodRef, AnnotationsList paramAnnotationsList, DexFile paramDexFile) {
    if (this.parameterAnnotations == null)
      this.parameterAnnotations = new ArrayList<ParameterAnnotationStruct>(); 
    this.parameterAnnotations.add(new ParameterAnnotationStruct(paramCstMethodRef, paramAnnotationsList, paramDexFile));
  }
  
  public int compareTo0(OffsettedItem paramOffsettedItem) {
    if (isInternable()) {
      paramOffsettedItem = paramOffsettedItem;
      return this.classAnnotations.compareTo(((AnnotationsDirectoryItem)paramOffsettedItem).classAnnotations);
    } 
    throw new UnsupportedOperationException("uninternable instance");
  }
  
  void debugPrint(PrintWriter paramPrintWriter) {
    if (this.classAnnotations != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  class annotations: ");
      stringBuilder.append(this.classAnnotations);
      paramPrintWriter.println(stringBuilder.toString());
    } 
    if (this.fieldAnnotations != null) {
      paramPrintWriter.println("  field annotations:");
      for (FieldAnnotationStruct fieldAnnotationStruct : this.fieldAnnotations) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("    ");
        stringBuilder.append(fieldAnnotationStruct.toHuman());
        paramPrintWriter.println(stringBuilder.toString());
      } 
    } 
    if (this.methodAnnotations != null) {
      paramPrintWriter.println("  method annotations:");
      for (MethodAnnotationStruct methodAnnotationStruct : this.methodAnnotations) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("    ");
        stringBuilder.append(methodAnnotationStruct.toHuman());
        paramPrintWriter.println(stringBuilder.toString());
      } 
    } 
    if (this.parameterAnnotations != null) {
      paramPrintWriter.println("  parameter annotations:");
      for (ParameterAnnotationStruct parameterAnnotationStruct : this.parameterAnnotations) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("    ");
        stringBuilder.append(parameterAnnotationStruct.toHuman());
        paramPrintWriter.println(stringBuilder.toString());
      } 
    } 
  }
  
  public Annotations getMethodAnnotations(CstMethodRef paramCstMethodRef) {
    ArrayList<MethodAnnotationStruct> arrayList = this.methodAnnotations;
    if (arrayList == null)
      return null; 
    for (MethodAnnotationStruct methodAnnotationStruct : arrayList) {
      if (methodAnnotationStruct.getMethod().equals(paramCstMethodRef))
        return methodAnnotationStruct.getAnnotations(); 
    } 
    return null;
  }
  
  public AnnotationsList getParameterAnnotations(CstMethodRef paramCstMethodRef) {
    ArrayList<ParameterAnnotationStruct> arrayList = this.parameterAnnotations;
    if (arrayList == null)
      return null; 
    for (ParameterAnnotationStruct parameterAnnotationStruct : arrayList) {
      if (parameterAnnotationStruct.getMethod().equals(paramCstMethodRef))
        return parameterAnnotationStruct.getAnnotationsList(); 
    } 
    return null;
  }
  
  public int hashCode() {
    AnnotationSetItem annotationSetItem = this.classAnnotations;
    return (annotationSetItem == null) ? 0 : annotationSetItem.hashCode();
  }
  
  public boolean isEmpty() {
    boolean bool;
    if (this.classAnnotations == null && this.fieldAnnotations == null && this.methodAnnotations == null && this.parameterAnnotations == null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isInternable() {
    boolean bool;
    if (this.classAnnotations != null && this.fieldAnnotations == null && this.methodAnnotations == null && this.parameterAnnotations == null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_ANNOTATIONS_DIRECTORY_ITEM;
  }
  
  protected void place0(Section paramSection, int paramInt) {
    setWriteSize((listSize(this.fieldAnnotations) + listSize(this.methodAnnotations) + listSize(this.parameterAnnotations)) * 8 + 16);
  }
  
  public void setClassAnnotations(Annotations paramAnnotations, DexFile paramDexFile) {
    if (paramAnnotations != null) {
      if (this.classAnnotations == null) {
        this.classAnnotations = new AnnotationSetItem(paramAnnotations, paramDexFile);
        return;
      } 
      throw new UnsupportedOperationException("class annotations already set");
    } 
    throw new NullPointerException("annotations == null");
  }
  
  public String toHuman() {
    throw new RuntimeException("unsupported");
  }
  
  protected void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    boolean bool = paramAnnotatedOutput.annotates();
    int i = OffsettedItem.getAbsoluteOffsetOr0(this.classAnnotations);
    int j = listSize(this.fieldAnnotations);
    int k = listSize(this.methodAnnotations);
    int m = listSize(this.parameterAnnotations);
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsetString());
      stringBuilder.append(" annotations directory");
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  class_annotations_off: ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  fields_size:           ");
      stringBuilder.append(Hex.u4(j));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  methods_size:          ");
      stringBuilder.append(Hex.u4(k));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  parameters_size:       ");
      stringBuilder.append(Hex.u4(m));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(i);
    paramAnnotatedOutput.writeInt(j);
    paramAnnotatedOutput.writeInt(k);
    paramAnnotatedOutput.writeInt(m);
    if (j != 0) {
      Collections.sort(this.fieldAnnotations);
      if (bool)
        paramAnnotatedOutput.annotate(0, "  fields:"); 
      Iterator<FieldAnnotationStruct> iterator = this.fieldAnnotations.iterator();
      while (iterator.hasNext())
        ((FieldAnnotationStruct)iterator.next()).writeTo(paramDexFile, paramAnnotatedOutput); 
    } 
    if (k != 0) {
      Collections.sort(this.methodAnnotations);
      if (bool)
        paramAnnotatedOutput.annotate(0, "  methods:"); 
      Iterator<MethodAnnotationStruct> iterator = this.methodAnnotations.iterator();
      while (iterator.hasNext())
        ((MethodAnnotationStruct)iterator.next()).writeTo(paramDexFile, paramAnnotatedOutput); 
    } 
    if (m != 0) {
      Collections.sort(this.parameterAnnotations);
      if (bool)
        paramAnnotatedOutput.annotate(0, "  parameters:"); 
      Iterator<ParameterAnnotationStruct> iterator = this.parameterAnnotations.iterator();
      while (iterator.hasNext())
        ((ParameterAnnotationStruct)iterator.next()).writeTo(paramDexFile, paramAnnotatedOutput); 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\AnnotationsDirectoryItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */