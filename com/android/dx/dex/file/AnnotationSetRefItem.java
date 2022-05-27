package com.android.dx.dex.file;

import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;

public final class AnnotationSetRefItem extends OffsettedItem {
  private static final int ALIGNMENT = 4;
  
  private static final int WRITE_SIZE = 4;
  
  private AnnotationSetItem annotations;
  
  public AnnotationSetRefItem(AnnotationSetItem paramAnnotationSetItem) {
    super(4, 4);
    if (paramAnnotationSetItem != null) {
      this.annotations = paramAnnotationSetItem;
      return;
    } 
    throw new NullPointerException("annotations == null");
  }
  
  public void addContents(DexFile paramDexFile) {
    this.annotations = paramDexFile.getWordData().<AnnotationSetItem>intern(this.annotations);
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_ANNOTATION_SET_REF_ITEM;
  }
  
  public String toHuman() {
    return this.annotations.toHuman();
  }
  
  protected void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    int i = this.annotations.getAbsoluteOffset();
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  annotations_off: ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\AnnotationSetRefItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */