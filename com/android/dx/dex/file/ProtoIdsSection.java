package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstProtoRef;
import com.android.dx.rop.type.Prototype;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

public final class ProtoIdsSection extends UniformItemSection {
  private final TreeMap<Prototype, ProtoIdItem> protoIds = new TreeMap<Prototype, ProtoIdItem>();
  
  public ProtoIdsSection(DexFile paramDexFile) {
    super("proto_ids", paramDexFile, 4);
  }
  
  public IndexedItem get(Constant paramConstant) {
    if (paramConstant != null) {
      if (paramConstant instanceof CstProtoRef) {
        throwIfNotPrepared();
        CstProtoRef cstProtoRef = (CstProtoRef)paramConstant;
        IndexedItem indexedItem = this.protoIds.get(cstProtoRef.getPrototype());
        if (indexedItem != null)
          return indexedItem; 
        throw new IllegalArgumentException("not found");
      } 
      throw new IllegalArgumentException("cst not instance of CstProtoRef");
    } 
    throw new NullPointerException("cst == null");
  }
  
  public int indexOf(Prototype paramPrototype) {
    if (paramPrototype != null) {
      throwIfNotPrepared();
      ProtoIdItem protoIdItem = this.protoIds.get(paramPrototype);
      if (protoIdItem != null)
        return protoIdItem.getIndex(); 
      throw new IllegalArgumentException("not found");
    } 
    throw new NullPointerException("prototype == null");
  }
  
  public ProtoIdItem intern(Prototype paramPrototype) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull -> 55
    //   6: aload_0
    //   7: invokevirtual throwIfPrepared : ()V
    //   10: aload_0
    //   11: getfield protoIds : Ljava/util/TreeMap;
    //   14: aload_1
    //   15: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   18: checkcast com/android/dx/dex/file/ProtoIdItem
    //   21: astore_2
    //   22: aload_2
    //   23: astore_3
    //   24: aload_2
    //   25: ifnonnull -> 47
    //   28: new com/android/dx/dex/file/ProtoIdItem
    //   31: astore_3
    //   32: aload_3
    //   33: aload_1
    //   34: invokespecial <init> : (Lcom/android/dx/rop/type/Prototype;)V
    //   37: aload_0
    //   38: getfield protoIds : Ljava/util/TreeMap;
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
    //   60: ldc 'prototype == null'
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
    return this.protoIds.values();
  }
  
  protected void orderItems() {
    Iterator<? extends Item> iterator = items().iterator();
    for (byte b = 0; iterator.hasNext(); b++)
      ((ProtoIdItem)iterator.next()).setIndex(b); 
  }
  
  public void writeHeaderPart(AnnotatedOutput paramAnnotatedOutput) {
    int j;
    throwIfNotPrepared();
    int i = this.protoIds.size();
    if (i == 0) {
      j = 0;
    } else {
      j = getFileOffset();
    } 
    if (i <= 65536) {
      if (paramAnnotatedOutput.annotates()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("proto_ids_size:  ");
        stringBuilder.append(Hex.u4(i));
        paramAnnotatedOutput.annotate(4, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("proto_ids_off:   ");
        stringBuilder.append(Hex.u4(j));
        paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      } 
      paramAnnotatedOutput.writeInt(i);
      paramAnnotatedOutput.writeInt(j);
      return;
    } 
    throw new UnsupportedOperationException("too many proto ids");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\ProtoIdsSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */