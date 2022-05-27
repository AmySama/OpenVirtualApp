package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.Collection;
import java.util.TreeMap;

public final class FieldIdsSection extends MemberIdsSection {
  private final TreeMap<CstFieldRef, FieldIdItem> fieldIds = new TreeMap<CstFieldRef, FieldIdItem>();
  
  public FieldIdsSection(DexFile paramDexFile) {
    super("field_ids", paramDexFile);
  }
  
  public IndexedItem get(Constant paramConstant) {
    if (paramConstant != null) {
      throwIfNotPrepared();
      IndexedItem indexedItem = this.fieldIds.get(paramConstant);
      if (indexedItem != null)
        return indexedItem; 
      throw new IllegalArgumentException("not found");
    } 
    throw new NullPointerException("cst == null");
  }
  
  public int indexOf(CstFieldRef paramCstFieldRef) {
    if (paramCstFieldRef != null) {
      throwIfNotPrepared();
      FieldIdItem fieldIdItem = this.fieldIds.get(paramCstFieldRef);
      if (fieldIdItem != null)
        return fieldIdItem.getIndex(); 
      throw new IllegalArgumentException("not found");
    } 
    throw new NullPointerException("ref == null");
  }
  
  public FieldIdItem intern(CstFieldRef paramCstFieldRef) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull -> 55
    //   6: aload_0
    //   7: invokevirtual throwIfPrepared : ()V
    //   10: aload_0
    //   11: getfield fieldIds : Ljava/util/TreeMap;
    //   14: aload_1
    //   15: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   18: checkcast com/android/dx/dex/file/FieldIdItem
    //   21: astore_2
    //   22: aload_2
    //   23: astore_3
    //   24: aload_2
    //   25: ifnonnull -> 47
    //   28: new com/android/dx/dex/file/FieldIdItem
    //   31: astore_3
    //   32: aload_3
    //   33: aload_1
    //   34: invokespecial <init> : (Lcom/android/dx/rop/cst/CstFieldRef;)V
    //   37: aload_0
    //   38: getfield fieldIds : Ljava/util/TreeMap;
    //   41: aload_1
    //   42: aload_3
    //   43: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   46: pop
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_3
    //   50: areturn
    //   51: astore_1
    //   52: goto -> 67
    //   55: new java/lang/NullPointerException
    //   58: astore_1
    //   59: aload_1
    //   60: ldc 'field == null'
    //   62: invokespecial <init> : (Ljava/lang/String;)V
    //   65: aload_1
    //   66: athrow
    //   67: aload_0
    //   68: monitorexit
    //   69: aload_1
    //   70: athrow
    // Exception table:
    //   from	to	target	type
    //   6	22	51	finally
    //   28	47	51	finally
    //   55	67	51	finally
  }
  
  public Collection<? extends Item> items() {
    return this.fieldIds.values();
  }
  
  public void writeHeaderPart(AnnotatedOutput paramAnnotatedOutput) {
    int j;
    throwIfNotPrepared();
    int i = this.fieldIds.size();
    if (i == 0) {
      j = 0;
    } else {
      j = getFileOffset();
    } 
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("field_ids_size:  ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("field_ids_off:   ");
      stringBuilder.append(Hex.u4(j));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(i);
    paramAnnotatedOutput.writeInt(j);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\FieldIdsSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */