package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstCallSite;
import com.android.dx.rop.cst.CstCallSiteRef;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

public final class CallSiteIdsSection extends UniformItemSection {
  private final TreeMap<CstCallSiteRef, CallSiteIdItem> callSiteIds = new TreeMap<CstCallSiteRef, CallSiteIdItem>();
  
  private final TreeMap<CstCallSite, CallSiteItem> callSites = new TreeMap<CstCallSite, CallSiteItem>();
  
  public CallSiteIdsSection(DexFile paramDexFile) {
    super("call_site_ids", paramDexFile, 4);
  }
  
  void addCallSiteItem(CstCallSite paramCstCallSite, CallSiteItem paramCallSiteItem) {
    if (paramCstCallSite != null) {
      if (paramCallSiteItem != null) {
        this.callSites.put(paramCstCallSite, paramCallSiteItem);
        return;
      } 
      throw new NullPointerException("callSiteItem == null");
    } 
    throw new NullPointerException("callSite == null");
  }
  
  public IndexedItem get(Constant paramConstant) {
    if (paramConstant != null) {
      throwIfNotPrepared();
      IndexedItem indexedItem = this.callSiteIds.get(paramConstant);
      if (indexedItem != null)
        return indexedItem; 
      throw new IllegalArgumentException("not found");
    } 
    throw new NullPointerException("cst == null");
  }
  
  CallSiteItem getCallSiteItem(CstCallSite paramCstCallSite) {
    if (paramCstCallSite != null)
      return this.callSites.get(paramCstCallSite); 
    throw new NullPointerException("callSite == null");
  }
  
  public void intern(CstCallSiteRef paramCstCallSiteRef) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull -> 50
    //   6: aload_0
    //   7: invokevirtual throwIfPrepared : ()V
    //   10: aload_0
    //   11: getfield callSiteIds : Ljava/util/TreeMap;
    //   14: aload_1
    //   15: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   18: checkcast com/android/dx/dex/file/CallSiteIdItem
    //   21: ifnonnull -> 43
    //   24: new com/android/dx/dex/file/CallSiteIdItem
    //   27: astore_2
    //   28: aload_2
    //   29: aload_1
    //   30: invokespecial <init> : (Lcom/android/dx/rop/cst/CstCallSiteRef;)V
    //   33: aload_0
    //   34: getfield callSiteIds : Ljava/util/TreeMap;
    //   37: aload_1
    //   38: aload_2
    //   39: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   42: pop
    //   43: aload_0
    //   44: monitorexit
    //   45: return
    //   46: astore_1
    //   47: goto -> 62
    //   50: new java/lang/NullPointerException
    //   53: astore_1
    //   54: aload_1
    //   55: ldc 'cstRef'
    //   57: invokespecial <init> : (Ljava/lang/String;)V
    //   60: aload_1
    //   61: athrow
    //   62: aload_0
    //   63: monitorexit
    //   64: aload_1
    //   65: athrow
    // Exception table:
    //   from	to	target	type
    //   6	43	46	finally
    //   50	62	46	finally
  }
  
  public Collection<? extends Item> items() {
    return this.callSiteIds.values();
  }
  
  protected void orderItems() {
    Iterator<CallSiteIdItem> iterator = this.callSiteIds.values().iterator();
    for (byte b = 0; iterator.hasNext(); b++)
      ((CallSiteIdItem)iterator.next()).setIndex(b); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\CallSiteIdsSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */