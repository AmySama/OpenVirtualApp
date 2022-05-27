package com.android.dx.dex.file;

import com.android.dex.DexIndexOverflowException;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

public final class TypeIdsSection extends UniformItemSection {
  private final TreeMap<Type, TypeIdItem> typeIds = new TreeMap<Type, TypeIdItem>();
  
  public TypeIdsSection(DexFile paramDexFile) {
    super("type_ids", paramDexFile, 4);
  }
  
  public IndexedItem get(Constant paramConstant) {
    if (paramConstant != null) {
      throwIfNotPrepared();
      Type type = ((CstType)paramConstant).getClassType();
      IndexedItem indexedItem = this.typeIds.get(type);
      if (indexedItem != null)
        return indexedItem; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("not found: ");
      stringBuilder.append(paramConstant);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new NullPointerException("cst == null");
  }
  
  public int indexOf(CstType paramCstType) {
    if (paramCstType != null)
      return indexOf(paramCstType.getClassType()); 
    throw new NullPointerException("type == null");
  }
  
  public int indexOf(Type paramType) {
    if (paramType != null) {
      throwIfNotPrepared();
      TypeIdItem typeIdItem = this.typeIds.get(paramType);
      if (typeIdItem != null)
        return typeIdItem.getIndex(); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("not found: ");
      stringBuilder.append(paramType);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new NullPointerException("type == null");
  }
  
  public TypeIdItem intern(CstType paramCstType) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull -> 65
    //   6: aload_0
    //   7: invokevirtual throwIfPrepared : ()V
    //   10: aload_1
    //   11: invokevirtual getClassType : ()Lcom/android/dx/rop/type/Type;
    //   14: astore_2
    //   15: aload_0
    //   16: getfield typeIds : Ljava/util/TreeMap;
    //   19: aload_2
    //   20: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   23: checkcast com/android/dx/dex/file/TypeIdItem
    //   26: astore_3
    //   27: aload_3
    //   28: astore #4
    //   30: aload_3
    //   31: ifnonnull -> 56
    //   34: new com/android/dx/dex/file/TypeIdItem
    //   37: astore #4
    //   39: aload #4
    //   41: aload_1
    //   42: invokespecial <init> : (Lcom/android/dx/rop/cst/CstType;)V
    //   45: aload_0
    //   46: getfield typeIds : Ljava/util/TreeMap;
    //   49: aload_2
    //   50: aload #4
    //   52: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   55: pop
    //   56: aload_0
    //   57: monitorexit
    //   58: aload #4
    //   60: areturn
    //   61: astore_1
    //   62: goto -> 77
    //   65: new java/lang/NullPointerException
    //   68: astore_1
    //   69: aload_1
    //   70: ldc 'type == null'
    //   72: invokespecial <init> : (Ljava/lang/String;)V
    //   75: aload_1
    //   76: athrow
    //   77: aload_0
    //   78: monitorexit
    //   79: aload_1
    //   80: athrow
    // Exception table:
    //   from	to	target	type
    //   6	27	61	finally
    //   34	56	61	finally
    //   65	77	61	finally
  }
  
  public TypeIdItem intern(Type paramType) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull -> 64
    //   6: aload_0
    //   7: invokevirtual throwIfPrepared : ()V
    //   10: aload_0
    //   11: getfield typeIds : Ljava/util/TreeMap;
    //   14: aload_1
    //   15: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   18: checkcast com/android/dx/dex/file/TypeIdItem
    //   21: astore_2
    //   22: aload_2
    //   23: astore_3
    //   24: aload_2
    //   25: ifnonnull -> 56
    //   28: new com/android/dx/dex/file/TypeIdItem
    //   31: astore_3
    //   32: new com/android/dx/rop/cst/CstType
    //   35: astore_2
    //   36: aload_2
    //   37: aload_1
    //   38: invokespecial <init> : (Lcom/android/dx/rop/type/Type;)V
    //   41: aload_3
    //   42: aload_2
    //   43: invokespecial <init> : (Lcom/android/dx/rop/cst/CstType;)V
    //   46: aload_0
    //   47: getfield typeIds : Ljava/util/TreeMap;
    //   50: aload_1
    //   51: aload_3
    //   52: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   55: pop
    //   56: aload_0
    //   57: monitorexit
    //   58: aload_3
    //   59: areturn
    //   60: astore_1
    //   61: goto -> 76
    //   64: new java/lang/NullPointerException
    //   67: astore_1
    //   68: aload_1
    //   69: ldc 'type == null'
    //   71: invokespecial <init> : (Ljava/lang/String;)V
    //   74: aload_1
    //   75: athrow
    //   76: aload_0
    //   77: monitorexit
    //   78: aload_1
    //   79: athrow
    // Exception table:
    //   from	to	target	type
    //   6	22	60	finally
    //   28	56	60	finally
    //   64	76	60	finally
  }
  
  public Collection<? extends Item> items() {
    return this.typeIds.values();
  }
  
  protected void orderItems() {
    Iterator<? extends Item> iterator = items().iterator();
    for (byte b = 0; iterator.hasNext(); b++)
      ((TypeIdItem)iterator.next()).setIndex(b); 
  }
  
  public void writeHeaderPart(AnnotatedOutput paramAnnotatedOutput) {
    int j;
    throwIfNotPrepared();
    int i = this.typeIds.size();
    if (i == 0) {
      j = 0;
    } else {
      j = getFileOffset();
    } 
    if (i <= 65536) {
      if (paramAnnotatedOutput.annotates()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("type_ids_size:   ");
        stringBuilder.append(Hex.u4(i));
        paramAnnotatedOutput.annotate(4, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("type_ids_off:    ");
        stringBuilder.append(Hex.u4(j));
        paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      } 
      paramAnnotatedOutput.writeInt(i);
      paramAnnotatedOutput.writeInt(j);
      return;
    } 
    throw new DexIndexOverflowException(String.format("Too many type identifiers to fit in one dex file: %1$d; max is %2$d.%nYou may try using multi-dex. If multi-dex is enabled then the list of classes for the main dex list is too large.", new Object[] { Integer.valueOf(items().size()), Integer.valueOf(65536) }));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\TypeIdsSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */