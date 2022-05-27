package com.android.dx.dex.file;

import com.android.dx.rop.annotation.Annotation;
import com.android.dx.rop.annotation.Annotations;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.Iterator;

public final class AnnotationSetItem extends OffsettedItem {
  private static final int ALIGNMENT = 4;
  
  private static final int ENTRY_WRITE_SIZE = 4;
  
  private final Annotations annotations;
  
  private final AnnotationItem[] items;
  
  public AnnotationSetItem(Annotations paramAnnotations, DexFile paramDexFile) {
    super(4, writeSize(paramAnnotations));
    this.annotations = paramAnnotations;
    this.items = new AnnotationItem[paramAnnotations.size()];
    Iterator<Annotation> iterator = paramAnnotations.getAnnotations().iterator();
    for (byte b = 0; iterator.hasNext(); b++) {
      Annotation annotation = iterator.next();
      this.items[b] = new AnnotationItem(annotation, paramDexFile);
    } 
  }
  
  private static int writeSize(Annotations paramAnnotations) {
    try {
      int i = paramAnnotations.size();
      return i * 4 + 4;
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("list == null");
    } 
  }
  
  public void addContents(DexFile paramDexFile) {
    MixedItemSection mixedItemSection = paramDexFile.getByteData();
    int i = this.items.length;
    for (byte b = 0; b < i; b++) {
      AnnotationItem[] arrayOfAnnotationItem = this.items;
      arrayOfAnnotationItem[b] = mixedItemSection.<AnnotationItem>intern(arrayOfAnnotationItem[b]);
    } 
  }
  
  protected int compareTo0(OffsettedItem paramOffsettedItem) {
    paramOffsettedItem = paramOffsettedItem;
    return this.annotations.compareTo(((AnnotationSetItem)paramOffsettedItem).annotations);
  }
  
  public Annotations getAnnotations() {
    return this.annotations;
  }
  
  public int hashCode() {
    return this.annotations.hashCode();
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_ANNOTATION_SET_ITEM;
  }
  
  protected void place0(Section paramSection, int paramInt) {
    AnnotationItem.sortByTypeIdIndex(this.items);
  }
  
  public String toHuman() {
    return this.annotations.toString();
  }
  
  protected void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    boolean bool = paramAnnotatedOutput.annotates();
    int i = this.items.length;
    byte b = 0;
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsetString());
      stringBuilder.append(" annotation set");
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  size: ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(i);
    while (b < i) {
      int j = this.items[b].getAbsoluteOffset();
      if (bool) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  entries[");
        stringBuilder.append(Integer.toHexString(b));
        stringBuilder.append("]: ");
        stringBuilder.append(Hex.u4(j));
        paramAnnotatedOutput.annotate(4, stringBuilder.toString());
        this.items[b].annotateTo(paramAnnotatedOutput, "    ");
      } 
      paramAnnotatedOutput.writeInt(j);
      b++;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\AnnotationSetItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */