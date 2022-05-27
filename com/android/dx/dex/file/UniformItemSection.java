package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.util.AnnotatedOutput;
import java.util.Collection;
import java.util.Iterator;

public abstract class UniformItemSection extends Section {
  public UniformItemSection(String paramString, DexFile paramDexFile, int paramInt) {
    super(paramString, paramDexFile, paramInt);
  }
  
  public abstract IndexedItem get(Constant paramConstant);
  
  public final int getAbsoluteItemOffset(Item paramItem) {
    paramItem = paramItem;
    return getAbsoluteOffset(paramItem.getIndex() * paramItem.writeSize());
  }
  
  protected abstract void orderItems();
  
  protected final void prepare0() {
    DexFile dexFile = getFile();
    orderItems();
    Iterator<? extends Item> iterator = items().iterator();
    while (iterator.hasNext())
      ((Item)iterator.next()).addContents(dexFile); 
  }
  
  public final int writeSize() {
    Collection<? extends Item> collection = items();
    int i = collection.size();
    return (i == 0) ? 0 : (i * ((Item)collection.iterator().next()).writeSize());
  }
  
  protected final void writeTo0(AnnotatedOutput paramAnnotatedOutput) {
    DexFile dexFile = getFile();
    int i = getAlignment();
    Iterator<? extends Item> iterator = items().iterator();
    while (iterator.hasNext()) {
      ((Item)iterator.next()).writeTo(dexFile, paramAnnotatedOutput);
      paramAnnotatedOutput.alignTo(i);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\UniformItemSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */