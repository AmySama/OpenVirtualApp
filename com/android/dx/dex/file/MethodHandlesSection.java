package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstMethodHandle;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

public final class MethodHandlesSection extends UniformItemSection {
  private final TreeMap<CstMethodHandle, MethodHandleItem> methodHandles = new TreeMap<CstMethodHandle, MethodHandleItem>();
  
  public MethodHandlesSection(DexFile paramDexFile) {
    super("method_handles", paramDexFile, 8);
  }
  
  public IndexedItem get(Constant paramConstant) {
    if (paramConstant != null) {
      throwIfNotPrepared();
      IndexedItem indexedItem = this.methodHandles.get(paramConstant);
      if (indexedItem != null)
        return indexedItem; 
      throw new IllegalArgumentException("not found");
    } 
    throw new NullPointerException("cst == null");
  }
  
  int indexOf(CstMethodHandle paramCstMethodHandle) {
    return ((MethodHandleItem)this.methodHandles.get(paramCstMethodHandle)).getIndex();
  }
  
  public void intern(CstMethodHandle paramCstMethodHandle) {
    if (paramCstMethodHandle != null) {
      throwIfPrepared();
      if ((MethodHandleItem)this.methodHandles.get(paramCstMethodHandle) == null) {
        MethodHandleItem methodHandleItem = new MethodHandleItem(paramCstMethodHandle);
        this.methodHandles.put(paramCstMethodHandle, methodHandleItem);
      } 
      return;
    } 
    throw new NullPointerException("methodHandle == null");
  }
  
  public Collection<? extends Item> items() {
    return this.methodHandles.values();
  }
  
  protected void orderItems() {
    Iterator<MethodHandleItem> iterator = this.methodHandles.values().iterator();
    for (byte b = 0; iterator.hasNext(); b++)
      ((MethodHandleItem)iterator.next()).setIndex(b); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\MethodHandlesSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */