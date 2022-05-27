package com.android.dx.dex.code;

import com.android.dx.rop.code.BasicBlock;
import com.android.dx.rop.code.BasicBlockList;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.IntList;
import java.util.ArrayList;
import java.util.HashSet;

public final class StdCatchBuilder implements CatchBuilder {
  private static final int MAX_CATCH_RANGE = 65535;
  
  private final BlockAddresses addresses;
  
  private final RopMethod method;
  
  private final int[] order;
  
  public StdCatchBuilder(RopMethod paramRopMethod, int[] paramArrayOfint, BlockAddresses paramBlockAddresses) {
    if (paramRopMethod != null) {
      if (paramArrayOfint != null) {
        if (paramBlockAddresses != null) {
          this.method = paramRopMethod;
          this.order = paramArrayOfint;
          this.addresses = paramBlockAddresses;
          return;
        } 
        throw new NullPointerException("addresses == null");
      } 
      throw new NullPointerException("order == null");
    } 
    throw new NullPointerException("method == null");
  }
  
  public static CatchTable build(RopMethod paramRopMethod, int[] paramArrayOfint, BlockAddresses paramBlockAddresses) {
    BasicBlock basicBlock1;
    int i = paramArrayOfint.length;
    BasicBlockList basicBlockList = paramRopMethod.getBlocks();
    ArrayList<CatchTable.Entry> arrayList = new ArrayList(i);
    CatchHandlerList catchHandlerList = CatchHandlerList.EMPTY;
    boolean bool = false;
    BasicBlock basicBlock2 = null;
    paramRopMethod = null;
    byte b;
    for (b = 0;; b++) {
      BasicBlock basicBlock;
      CatchHandlerList catchHandlerList1;
      if (b < i) {
        basicBlock = basicBlockList.labelToBlock(paramArrayOfint[b]);
        if (!basicBlock.canThrow())
          continue; 
        catchHandlerList1 = handlersFor(basicBlock, paramBlockAddresses);
        if (catchHandlerList.size() != 0) {
          BasicBlock basicBlock3;
          if (catchHandlerList.equals(catchHandlerList1) && rangeIsValid(basicBlock2, basicBlock, paramBlockAddresses)) {
            basicBlock3 = basicBlock;
          } else {
            if (catchHandlerList.size() != 0)
              arrayList.add(makeEntry(basicBlock2, basicBlock3, catchHandlerList, paramBlockAddresses)); 
            basicBlock2 = basicBlock;
            basicBlock3 = basicBlock2;
            catchHandlerList = catchHandlerList1;
          } 
          continue;
        } 
      } else {
        break;
      } 
      basicBlock2 = basicBlock;
      basicBlock1 = basicBlock2;
      catchHandlerList = catchHandlerList1;
    } 
    if (catchHandlerList.size() != 0)
      arrayList.add(makeEntry(basicBlock2, basicBlock1, catchHandlerList, paramBlockAddresses)); 
    i = arrayList.size();
    if (i == 0)
      return CatchTable.EMPTY; 
    CatchTable catchTable = new CatchTable(i);
    for (b = bool; b < i; b++)
      catchTable.set(b, arrayList.get(b)); 
    catchTable.setImmutable();
    return catchTable;
  }
  
  private static CatchHandlerList handlersFor(BasicBlock paramBasicBlock, BlockAddresses paramBlockAddresses) {
    IntList intList = paramBasicBlock.getSuccessors();
    int i = intList.size();
    int j = paramBasicBlock.getPrimarySuccessor();
    TypeList typeList = paramBasicBlock.getLastInsn().getCatches();
    int k = typeList.size();
    if (k == 0)
      return CatchHandlerList.EMPTY; 
    if ((j != -1 || i == k) && (j == -1 || (i == k + 1 && j == intList.get(k)))) {
      boolean bool = false;
      i = 0;
      while (true) {
        j = k;
        if (i < k) {
          if (typeList.getType(i).equals(Type.OBJECT)) {
            j = i + 1;
            break;
          } 
          i++;
          continue;
        } 
        break;
      } 
      CatchHandlerList catchHandlerList = new CatchHandlerList(j);
      for (i = bool; i < j; i++)
        catchHandlerList.set(i, new CstType(typeList.getType(i)), paramBlockAddresses.getStart(intList.get(i)).getAddress()); 
      catchHandlerList.setImmutable();
      return catchHandlerList;
    } 
    throw new RuntimeException("shouldn't happen: weird successors list");
  }
  
  private static CatchTable.Entry makeEntry(BasicBlock paramBasicBlock1, BasicBlock paramBasicBlock2, CatchHandlerList paramCatchHandlerList, BlockAddresses paramBlockAddresses) {
    CodeAddress codeAddress1 = paramBlockAddresses.getLast(paramBasicBlock1);
    CodeAddress codeAddress2 = paramBlockAddresses.getEnd(paramBasicBlock2);
    return new CatchTable.Entry(codeAddress1.getAddress(), codeAddress2.getAddress(), paramCatchHandlerList);
  }
  
  private static boolean rangeIsValid(BasicBlock paramBasicBlock1, BasicBlock paramBasicBlock2, BlockAddresses paramBlockAddresses) {
    if (paramBasicBlock1 != null) {
      if (paramBasicBlock2 != null) {
        boolean bool;
        int i = paramBlockAddresses.getLast(paramBasicBlock1).getAddress();
        if (paramBlockAddresses.getEnd(paramBasicBlock2).getAddress() - i <= 65535) {
          bool = true;
        } else {
          bool = false;
        } 
        return bool;
      } 
      throw new NullPointerException("end == null");
    } 
    throw new NullPointerException("start == null");
  }
  
  public CatchTable build() {
    return build(this.method, this.order, this.addresses);
  }
  
  public HashSet<Type> getCatchTypes() {
    HashSet<Type> hashSet = new HashSet(20);
    BasicBlockList basicBlockList = this.method.getBlocks();
    int i = basicBlockList.size();
    for (byte b = 0; b < i; b++) {
      TypeList typeList = basicBlockList.get(b).getLastInsn().getCatches();
      int j = typeList.size();
      for (byte b1 = 0; b1 < j; b1++)
        hashSet.add(typeList.getType(b1)); 
    } 
    return hashSet;
  }
  
  public boolean hasAnyCatches() {
    BasicBlockList basicBlockList = this.method.getBlocks();
    int i = basicBlockList.size();
    for (byte b = 0; b < i; b++) {
      if (basicBlockList.get(b).getLastInsn().getCatches().size() != 0)
        return true; 
    } 
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\StdCatchBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */