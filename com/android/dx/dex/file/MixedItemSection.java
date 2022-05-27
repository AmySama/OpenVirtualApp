package com.android.dx.dex.file;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public final class MixedItemSection extends Section {
  private static final Comparator<OffsettedItem> TYPE_SORTER = new Comparator<OffsettedItem>() {
      public int compare(OffsettedItem param1OffsettedItem1, OffsettedItem param1OffsettedItem2) {
        return param1OffsettedItem1.itemType().compareTo(param1OffsettedItem2.itemType());
      }
    };
  
  private final HashMap<OffsettedItem, OffsettedItem> interns = new HashMap<OffsettedItem, OffsettedItem>(100);
  
  private final ArrayList<OffsettedItem> items = new ArrayList<OffsettedItem>(100);
  
  private final SortType sort;
  
  private int writeSize;
  
  public MixedItemSection(String paramString, DexFile paramDexFile, int paramInt, SortType paramSortType) {
    super(paramString, paramDexFile, paramInt);
    this.sort = paramSortType;
    this.writeSize = -1;
  }
  
  public void add(OffsettedItem paramOffsettedItem) {
    throwIfPrepared();
    try {
      int i = paramOffsettedItem.getAlignment();
      int j = getAlignment();
      if (i <= j) {
        this.items.add(paramOffsettedItem);
        return;
      } 
      IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
      this("incompatible item alignment");
      throw illegalArgumentException;
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("item == null");
    } 
  }
  
  public <T extends OffsettedItem> T get(T paramT) {
    throwIfNotPrepared();
    OffsettedItem offsettedItem = this.interns.get(paramT);
    if (offsettedItem != null)
      return (T)offsettedItem; 
    throw new NoSuchElementException(paramT.toString());
  }
  
  public int getAbsoluteItemOffset(Item paramItem) {
    return ((OffsettedItem)paramItem).getAbsoluteOffset();
  }
  
  public <T extends OffsettedItem> T intern(T paramT) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual throwIfPrepared : ()V
    //   6: aload_0
    //   7: getfield interns : Ljava/util/HashMap;
    //   10: aload_1
    //   11: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   14: checkcast com/android/dx/dex/file/OffsettedItem
    //   17: astore_2
    //   18: aload_2
    //   19: ifnull -> 26
    //   22: aload_0
    //   23: monitorexit
    //   24: aload_2
    //   25: areturn
    //   26: aload_0
    //   27: aload_1
    //   28: invokevirtual add : (Lcom/android/dx/dex/file/OffsettedItem;)V
    //   31: aload_0
    //   32: getfield interns : Ljava/util/HashMap;
    //   35: aload_1
    //   36: aload_1
    //   37: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   40: pop
    //   41: aload_0
    //   42: monitorexit
    //   43: aload_1
    //   44: areturn
    //   45: astore_1
    //   46: aload_0
    //   47: monitorexit
    //   48: aload_1
    //   49: athrow
    // Exception table:
    //   from	to	target	type
    //   2	18	45	finally
    //   26	41	45	finally
  }
  
  public Collection<? extends Item> items() {
    return (Collection)this.items;
  }
  
  public void placeItems() {
    throwIfNotPrepared();
    int i = null.$SwitchMap$com$android$dx$dex$file$MixedItemSection$SortType[this.sort.ordinal()];
    if (i != 1) {
      if (i == 2)
        Collections.sort(this.items, TYPE_SORTER); 
    } else {
      Collections.sort(this.items);
    } 
    int j = this.items.size();
    i = 0;
    int k = 0;
    while (i < j) {
      OffsettedItem offsettedItem = this.items.get(i);
      try {
        int m = offsettedItem.place(this, k);
        if (m >= k) {
          k = offsettedItem.writeSize() + m;
          i++;
          continue;
        } 
        RuntimeException runtimeException = new RuntimeException();
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("bogus place() result for ");
        stringBuilder.append(offsettedItem);
        this(stringBuilder.toString());
        throw runtimeException;
      } catch (RuntimeException runtimeException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("...while placing ");
        stringBuilder.append(offsettedItem);
        throw ExceptionWithContext.withContext(runtimeException, stringBuilder.toString());
      } 
    } 
    this.writeSize = k;
  }
  
  protected void prepare0() {
    DexFile dexFile = getFile();
    byte b = 0;
    label11: while (true) {
      int i = this.items.size();
      byte b1 = b;
      if (b >= i)
        return; 
      while (true) {
        b = b1;
        if (b1 < i) {
          ((OffsettedItem)this.items.get(b1)).addContents(dexFile);
          b1++;
          continue;
        } 
        continue label11;
      } 
      break;
    } 
  }
  
  public int size() {
    return this.items.size();
  }
  
  public void writeHeaderPart(AnnotatedOutput paramAnnotatedOutput) {
    throwIfNotPrepared();
    int i = this.writeSize;
    if (i != -1) {
      int j;
      if (i == 0) {
        j = 0;
      } else {
        j = getFileOffset();
      } 
      String str2 = getName();
      String str3 = str2;
      if (str2 == null)
        str3 = "<unnamed>"; 
      char[] arrayOfChar = new char[15 - str3.length()];
      Arrays.fill(arrayOfChar, ' ');
      String str1 = new String(arrayOfChar);
      if (paramAnnotatedOutput.annotates()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str3);
        stringBuilder.append("_size:");
        stringBuilder.append(str1);
        stringBuilder.append(Hex.u4(i));
        paramAnnotatedOutput.annotate(4, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(str3);
        stringBuilder.append("_off: ");
        stringBuilder.append(str1);
        stringBuilder.append(Hex.u4(j));
        paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      } 
      paramAnnotatedOutput.writeInt(i);
      paramAnnotatedOutput.writeInt(j);
      return;
    } 
    throw new RuntimeException("write size not yet set");
  }
  
  public void writeIndexAnnotation(AnnotatedOutput paramAnnotatedOutput, ItemType paramItemType, String paramString) {
    throwIfNotPrepared();
    TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
    for (OffsettedItem offsettedItem : this.items) {
      if (offsettedItem.itemType() == paramItemType)
        treeMap.put(offsettedItem.toHuman(), offsettedItem); 
    } 
    if (treeMap.size() == 0)
      return; 
    paramAnnotatedOutput.annotate(0, paramString);
    for (Map.Entry<Object, Object> entry : treeMap.entrySet()) {
      String str = (String)entry.getKey();
      OffsettedItem offsettedItem = (OffsettedItem)entry.getValue();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsettedItem.offsetString());
      stringBuilder.append(' ');
      stringBuilder.append(str);
      stringBuilder.append('\n');
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
    } 
  }
  
  public int writeSize() {
    throwIfNotPrepared();
    return this.writeSize;
  }
  
  protected void writeTo0(AnnotatedOutput paramAnnotatedOutput) {
    boolean bool = paramAnnotatedOutput.annotates();
    DexFile dexFile = getFile();
    Iterator<OffsettedItem> iterator = this.items.iterator();
    int i = 0;
    int j;
    for (j = 1; iterator.hasNext(); j = k) {
      OffsettedItem offsettedItem = iterator.next();
      int k = j;
      if (bool)
        if (j) {
          k = 0;
        } else {
          paramAnnotatedOutput.annotate(0, "\n");
          k = j;
        }  
      j = offsettedItem.getAlignment() - 1;
      int m = j & i + j;
      j = i;
      if (i != m) {
        paramAnnotatedOutput.writeZeroes(m - i);
        j = m;
      } 
      offsettedItem.writeTo(dexFile, paramAnnotatedOutput);
      i = j + offsettedItem.writeSize();
    } 
    if (i == this.writeSize)
      return; 
    throw new RuntimeException("output size mismatch");
  }
  
  enum SortType {
    INSTANCE, NONE, TYPE;
    
    static {
      SortType sortType = new SortType("INSTANCE", 2);
      INSTANCE = sortType;
      $VALUES = new SortType[] { NONE, TYPE, sortType };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\MixedItemSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */