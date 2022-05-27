package com.android.dx.dex.file;

import com.android.dx.rop.annotation.Annotation;
import com.android.dx.rop.annotation.AnnotationVisibility;
import com.android.dx.rop.annotation.NameValuePair;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstString;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.ByteArrayAnnotatedOutput;
import java.util.Arrays;
import java.util.Comparator;

public final class AnnotationItem extends OffsettedItem {
  private static final int ALIGNMENT = 1;
  
  private static final TypeIdSorter TYPE_ID_SORTER = new TypeIdSorter();
  
  private static final int VISIBILITY_BUILD = 0;
  
  private static final int VISIBILITY_RUNTIME = 1;
  
  private static final int VISIBILITY_SYSTEM = 2;
  
  private final Annotation annotation;
  
  private byte[] encodedForm;
  
  private TypeIdItem type;
  
  public AnnotationItem(Annotation paramAnnotation, DexFile paramDexFile) {
    super(1, -1);
    if (paramAnnotation != null) {
      this.annotation = paramAnnotation;
      this.type = null;
      this.encodedForm = null;
      addContents(paramDexFile);
      return;
    } 
    throw new NullPointerException("annotation == null");
  }
  
  public static void sortByTypeIdIndex(AnnotationItem[] paramArrayOfAnnotationItem) {
    Arrays.sort(paramArrayOfAnnotationItem, TYPE_ID_SORTER);
  }
  
  public void addContents(DexFile paramDexFile) {
    this.type = paramDexFile.getTypeIds().intern(this.annotation.getType());
    ValueEncoder.addContents(paramDexFile, this.annotation);
  }
  
  public void annotateTo(AnnotatedOutput paramAnnotatedOutput, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("visibility: ");
    stringBuilder.append(this.annotation.getVisibility().toHuman());
    paramAnnotatedOutput.annotate(0, stringBuilder.toString());
    stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("type: ");
    stringBuilder.append(this.annotation.getType().toHuman());
    paramAnnotatedOutput.annotate(0, stringBuilder.toString());
    for (NameValuePair nameValuePair : this.annotation.getNameValuePairs()) {
      CstString cstString = nameValuePair.getName();
      Constant constant = nameValuePair.getValue();
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString);
      stringBuilder1.append(cstString.toHuman());
      stringBuilder1.append(": ");
      stringBuilder1.append(ValueEncoder.constantToHuman(constant));
      paramAnnotatedOutput.annotate(0, stringBuilder1.toString());
    } 
  }
  
  protected int compareTo0(OffsettedItem paramOffsettedItem) {
    paramOffsettedItem = paramOffsettedItem;
    return this.annotation.compareTo(((AnnotationItem)paramOffsettedItem).annotation);
  }
  
  public int hashCode() {
    return this.annotation.hashCode();
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_ANNOTATION_ITEM;
  }
  
  protected void place0(Section paramSection, int paramInt) {
    ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput();
    (new ValueEncoder(paramSection.getFile(), (AnnotatedOutput)byteArrayAnnotatedOutput)).writeAnnotation(this.annotation, false);
    byte[] arrayOfByte = byteArrayAnnotatedOutput.toByteArray();
    this.encodedForm = arrayOfByte;
    setWriteSize(arrayOfByte.length + 1);
  }
  
  public String toHuman() {
    return this.annotation.toHuman();
  }
  
  protected void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    boolean bool = paramAnnotatedOutput.annotates();
    AnnotationVisibility annotationVisibility = this.annotation.getVisibility();
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsetString());
      stringBuilder.append(" annotation");
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  visibility: VISBILITY_");
      stringBuilder.append(annotationVisibility);
      paramAnnotatedOutput.annotate(1, stringBuilder.toString());
    } 
    int i = null.$SwitchMap$com$android$dx$rop$annotation$AnnotationVisibility[annotationVisibility.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i == 3) {
          paramAnnotatedOutput.writeByte(2);
        } else {
          throw new RuntimeException("shouldn't happen");
        } 
      } else {
        paramAnnotatedOutput.writeByte(1);
      } 
    } else {
      paramAnnotatedOutput.writeByte(0);
    } 
    if (bool) {
      (new ValueEncoder(paramDexFile, paramAnnotatedOutput)).writeAnnotation(this.annotation, true);
    } else {
      paramAnnotatedOutput.write(this.encodedForm);
    } 
  }
  
  private static class TypeIdSorter implements Comparator<AnnotationItem> {
    private TypeIdSorter() {}
    
    public int compare(AnnotationItem param1AnnotationItem1, AnnotationItem param1AnnotationItem2) {
      int i = param1AnnotationItem1.type.getIndex();
      int j = param1AnnotationItem2.type.getIndex();
      return (i < j) ? -1 : ((i > j) ? 1 : 0);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\AnnotationItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */