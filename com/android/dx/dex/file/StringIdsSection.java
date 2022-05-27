package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

public final class StringIdsSection extends UniformItemSection {
  private final TreeMap<CstString, StringIdItem> strings = new TreeMap<CstString, StringIdItem>();
  
  public StringIdsSection(DexFile paramDexFile) {
    super("string_ids", paramDexFile, 4);
  }
  
  public IndexedItem get(Constant paramConstant) {
    if (paramConstant != null) {
      throwIfNotPrepared();
      IndexedItem indexedItem = this.strings.get(paramConstant);
      if (indexedItem != null)
        return indexedItem; 
      throw new IllegalArgumentException("not found");
    } 
    throw new NullPointerException("cst == null");
  }
  
  public int indexOf(CstString paramCstString) {
    if (paramCstString != null) {
      throwIfNotPrepared();
      StringIdItem stringIdItem = this.strings.get(paramCstString);
      if (stringIdItem != null)
        return stringIdItem.getIndex(); 
      throw new IllegalArgumentException("not found");
    } 
    throw new NullPointerException("string == null");
  }
  
  public StringIdItem intern(StringIdItem paramStringIdItem) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull -> 53
    //   6: aload_0
    //   7: invokevirtual throwIfPrepared : ()V
    //   10: aload_1
    //   11: invokevirtual getValue : ()Lcom/android/dx/rop/cst/CstString;
    //   14: astore_2
    //   15: aload_0
    //   16: getfield strings : Ljava/util/TreeMap;
    //   19: aload_2
    //   20: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   23: checkcast com/android/dx/dex/file/StringIdItem
    //   26: astore_3
    //   27: aload_3
    //   28: ifnull -> 35
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_3
    //   34: areturn
    //   35: aload_0
    //   36: getfield strings : Ljava/util/TreeMap;
    //   39: aload_2
    //   40: aload_1
    //   41: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   44: pop
    //   45: aload_0
    //   46: monitorexit
    //   47: aload_1
    //   48: areturn
    //   49: astore_1
    //   50: goto -> 65
    //   53: new java/lang/NullPointerException
    //   56: astore_1
    //   57: aload_1
    //   58: ldc 'string == null'
    //   60: invokespecial <init> : (Ljava/lang/String;)V
    //   63: aload_1
    //   64: athrow
    //   65: aload_0
    //   66: monitorexit
    //   67: aload_1
    //   68: athrow
    // Exception table:
    //   from	to	target	type
    //   6	27	49	finally
    //   35	45	49	finally
    //   53	65	49	finally
  }
  
  public StringIdItem intern(CstString paramCstString) {
    return intern(new StringIdItem(paramCstString));
  }
  
  public StringIdItem intern(String paramString) {
    return intern(new StringIdItem(new CstString(paramString)));
  }
  
  public void intern(CstNat paramCstNat) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokevirtual getName : ()Lcom/android/dx/rop/cst/CstString;
    //   7: invokevirtual intern : (Lcom/android/dx/rop/cst/CstString;)Lcom/android/dx/dex/file/StringIdItem;
    //   10: pop
    //   11: aload_0
    //   12: aload_1
    //   13: invokevirtual getDescriptor : ()Lcom/android/dx/rop/cst/CstString;
    //   16: invokevirtual intern : (Lcom/android/dx/rop/cst/CstString;)Lcom/android/dx/dex/file/StringIdItem;
    //   19: pop
    //   20: aload_0
    //   21: monitorexit
    //   22: return
    //   23: astore_1
    //   24: aload_0
    //   25: monitorexit
    //   26: aload_1
    //   27: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	23	finally
  }
  
  public Collection<? extends Item> items() {
    return this.strings.values();
  }
  
  protected void orderItems() {
    Iterator<StringIdItem> iterator = this.strings.values().iterator();
    for (byte b = 0; iterator.hasNext(); b++)
      ((StringIdItem)iterator.next()).setIndex(b); 
  }
  
  public void writeHeaderPart(AnnotatedOutput paramAnnotatedOutput) {
    int j;
    throwIfNotPrepared();
    int i = this.strings.size();
    if (i == 0) {
      j = 0;
    } else {
      j = getFileOffset();
    } 
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("string_ids_size: ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("string_ids_off:  ");
      stringBuilder.append(Hex.u4(j));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(i);
    paramAnnotatedOutput.writeInt(j);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\StringIdsSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */