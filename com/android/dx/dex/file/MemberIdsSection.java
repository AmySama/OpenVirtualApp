package com.android.dx.dex.file;

import com.android.dex.DexIndexOverflowException;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MemberIdsSection extends UniformItemSection {
  public MemberIdsSection(String paramString, DexFile paramDexFile) {
    super(paramString, paramDexFile, 4);
  }
  
  private String getTooManyMembersMessage() {
    TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
    Iterator<? extends Item> iterator = items().iterator();
    while (iterator.hasNext()) {
      String str = ((MemberIdItem)iterator.next()).getDefiningClass().getPackageName();
      AtomicInteger atomicInteger1 = (AtomicInteger)treeMap.get(str);
      AtomicInteger atomicInteger2 = atomicInteger1;
      if (atomicInteger1 == null) {
        atomicInteger2 = new AtomicInteger();
        treeMap.put(str, atomicInteger2);
      } 
      atomicInteger2.incrementAndGet();
    } 
    Formatter formatter = new Formatter();
    try {
      if (this instanceof MethodIdsSection) {
        str = "method";
      } else {
        str = "field";
      } 
      formatter.format("Too many %1$s references to fit in one dex file: %2$d; max is %3$d.%nYou may try using multi-dex. If multi-dex is enabled then the list of classes for the main dex list is too large.%nReferences by package:", new Object[] { str, Integer.valueOf(items().size()), Integer.valueOf(65536) });
      for (Map.Entry<Object, Object> entry : treeMap.entrySet()) {
        formatter.format("%n%6d %s", new Object[] { Integer.valueOf(((AtomicInteger)entry.getValue()).get()), entry.getKey() });
      } 
      String str = formatter.toString();
      return str;
    } finally {
      formatter.close();
    } 
  }
  
  protected void orderItems() {
    if (items().size() <= 65536) {
      Iterator<? extends Item> iterator = items().iterator();
      for (byte b = 0; iterator.hasNext(); b++)
        ((MemberIdItem)iterator.next()).setIndex(b); 
      return;
    } 
    throw new DexIndexOverflowException(getTooManyMembersMessage());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\MemberIdsSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */