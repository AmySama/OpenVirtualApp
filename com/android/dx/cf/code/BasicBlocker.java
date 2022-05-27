package com.android.dx.cf.code;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.util.Bits;
import com.android.dx.util.IntList;
import java.util.ArrayList;

public final class BasicBlocker implements BytecodeArray.Visitor {
  private final int[] blockSet;
  
  private final ByteCatchList[] catchLists;
  
  private final int[] liveSet;
  
  private final ConcreteMethod method;
  
  private int previousOffset;
  
  private final IntList[] targetLists;
  
  private final int[] workSet;
  
  private BasicBlocker(ConcreteMethod paramConcreteMethod) {
    if (paramConcreteMethod != null) {
      this.method = paramConcreteMethod;
      int i = paramConcreteMethod.getCode().size() + 1;
      this.workSet = Bits.makeBitSet(i);
      this.liveSet = Bits.makeBitSet(i);
      this.blockSet = Bits.makeBitSet(i);
      this.targetLists = new IntList[i];
      this.catchLists = new ByteCatchList[i];
      this.previousOffset = -1;
      return;
    } 
    throw new NullPointerException("method == null");
  }
  
  private void addWorkIfNecessary(int paramInt, boolean paramBoolean) {
    if (!Bits.get(this.liveSet, paramInt))
      Bits.set(this.workSet, paramInt); 
    if (paramBoolean)
      Bits.set(this.blockSet, paramInt); 
  }
  
  private void doit() {
    BytecodeArray bytecodeArray = this.method.getCode();
    ByteCatchList byteCatchList = this.method.getCatches();
    int i = byteCatchList.size();
    Bits.set(this.workSet, 0);
    Bits.set(this.blockSet, 0);
    while (!Bits.isEmpty(this.workSet)) {
      try {
        bytecodeArray.processWorkSet(this.workSet, this);
        for (byte b = 0; b < i; b++) {
          ByteCatchList.Item item = byteCatchList.get(b);
          int j = item.getStartPc();
          int k = item.getEndPc();
          if (Bits.anyInRange(this.liveSet, j, k)) {
            Bits.set(this.blockSet, j);
            Bits.set(this.blockSet, k);
            addWorkIfNecessary(item.getHandlerPc(), true);
          } 
        } 
      } catch (IllegalArgumentException illegalArgumentException) {
        throw new SimException("flow of control falls off end of method", illegalArgumentException);
      } 
    } 
  }
  
  private ByteBlockList getBlockList() {
    // Byte code:
    //   0: aload_0
    //   1: getfield method : Lcom/android/dx/cf/code/ConcreteMethod;
    //   4: invokevirtual getCode : ()Lcom/android/dx/cf/code/BytecodeArray;
    //   7: invokevirtual size : ()I
    //   10: anewarray com/android/dx/cf/code/ByteBlock
    //   13: astore_1
    //   14: iconst_0
    //   15: istore_2
    //   16: iconst_0
    //   17: istore_3
    //   18: iconst_0
    //   19: istore #4
    //   21: aload_0
    //   22: getfield blockSet : [I
    //   25: iload_3
    //   26: iconst_1
    //   27: iadd
    //   28: invokestatic findFirst : ([II)I
    //   31: istore #5
    //   33: iload #5
    //   35: ifge -> 79
    //   38: new com/android/dx/cf/code/ByteBlockList
    //   41: dup
    //   42: iload #4
    //   44: invokespecial <init> : (I)V
    //   47: astore #6
    //   49: iload_2
    //   50: istore #7
    //   52: iload #7
    //   54: iload #4
    //   56: if_icmpge -> 76
    //   59: aload #6
    //   61: iload #7
    //   63: aload_1
    //   64: iload #7
    //   66: aaload
    //   67: invokevirtual set : (ILcom/android/dx/cf/code/ByteBlock;)V
    //   70: iinc #7, 1
    //   73: goto -> 52
    //   76: aload #6
    //   78: areturn
    //   79: iload #4
    //   81: istore #7
    //   83: aload_0
    //   84: getfield liveSet : [I
    //   87: iload_3
    //   88: invokestatic get : ([II)Z
    //   91: ifeq -> 211
    //   94: aconst_null
    //   95: astore #6
    //   97: iload #5
    //   99: iconst_1
    //   100: isub
    //   101: istore #7
    //   103: iload #7
    //   105: iload_3
    //   106: if_icmplt -> 132
    //   109: aload_0
    //   110: getfield targetLists : [Lcom/android/dx/util/IntList;
    //   113: iload #7
    //   115: aaload
    //   116: astore #6
    //   118: aload #6
    //   120: ifnull -> 126
    //   123: goto -> 135
    //   126: iinc #7, -1
    //   129: goto -> 103
    //   132: iconst_m1
    //   133: istore #7
    //   135: aload #6
    //   137: ifnonnull -> 155
    //   140: iload #5
    //   142: invokestatic makeImmutable : (I)Lcom/android/dx/util/IntList;
    //   145: astore #8
    //   147: getstatic com/android/dx/cf/code/ByteCatchList.EMPTY : Lcom/android/dx/cf/code/ByteCatchList;
    //   150: astore #9
    //   152: goto -> 186
    //   155: aload_0
    //   156: getfield catchLists : [Lcom/android/dx/cf/code/ByteCatchList;
    //   159: iload #7
    //   161: aaload
    //   162: astore #10
    //   164: aload #6
    //   166: astore #8
    //   168: aload #10
    //   170: astore #9
    //   172: aload #10
    //   174: ifnonnull -> 186
    //   177: getstatic com/android/dx/cf/code/ByteCatchList.EMPTY : Lcom/android/dx/cf/code/ByteCatchList;
    //   180: astore #9
    //   182: aload #6
    //   184: astore #8
    //   186: aload_1
    //   187: iload #4
    //   189: new com/android/dx/cf/code/ByteBlock
    //   192: dup
    //   193: iload_3
    //   194: iload_3
    //   195: iload #5
    //   197: aload #8
    //   199: aload #9
    //   201: invokespecial <init> : (IIILcom/android/dx/util/IntList;Lcom/android/dx/cf/code/ByteCatchList;)V
    //   204: aastore
    //   205: iload #4
    //   207: iconst_1
    //   208: iadd
    //   209: istore #7
    //   211: iload #5
    //   213: istore_3
    //   214: iload #7
    //   216: istore #4
    //   218: goto -> 21
  }
  
  public static ByteBlockList identifyBlocks(ConcreteMethod paramConcreteMethod) {
    BasicBlocker basicBlocker = new BasicBlocker(paramConcreteMethod);
    basicBlocker.doit();
    return basicBlocker.getBlockList();
  }
  
  private void visitCommon(int paramInt1, int paramInt2, boolean paramBoolean) {
    Bits.set(this.liveSet, paramInt1);
    if (paramBoolean) {
      addWorkIfNecessary(paramInt1 + paramInt2, false);
    } else {
      Bits.set(this.blockSet, paramInt1 + paramInt2);
    } 
  }
  
  private void visitThrowing(int paramInt1, int paramInt2, boolean paramBoolean) {
    paramInt2 += paramInt1;
    if (paramBoolean)
      addWorkIfNecessary(paramInt2, true); 
    ByteCatchList byteCatchList = this.method.getCatches().listFor(paramInt1);
    this.catchLists[paramInt1] = byteCatchList;
    IntList[] arrayOfIntList = this.targetLists;
    if (!paramBoolean)
      paramInt2 = -1; 
    arrayOfIntList[paramInt1] = byteCatchList.toTargetList(paramInt2);
  }
  
  public int getPreviousOffset() {
    return this.previousOffset;
  }
  
  public void setPreviousOffset(int paramInt) {
    this.previousOffset = paramInt;
  }
  
  public void visitBranch(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (paramInt1 != 167) {
      if (paramInt1 == 168)
        addWorkIfNecessary(paramInt2, true); 
      paramInt1 = paramInt2 + paramInt3;
      visitCommon(paramInt2, paramInt3, true);
      addWorkIfNecessary(paramInt1, true);
      this.targetLists[paramInt2] = IntList.makeImmutable(paramInt1, paramInt4);
    } else {
      visitCommon(paramInt2, paramInt3, false);
      this.targetLists[paramInt2] = IntList.makeImmutable(paramInt4);
    } 
    addWorkIfNecessary(paramInt4, true);
  }
  
  public void visitConstant(int paramInt1, int paramInt2, int paramInt3, Constant paramConstant, int paramInt4) {
    visitCommon(paramInt2, paramInt3, true);
    if (paramConstant instanceof com.android.dx.rop.cst.CstMemberRef || paramConstant instanceof CstType || paramConstant instanceof com.android.dx.rop.cst.CstString || paramConstant instanceof com.android.dx.rop.cst.CstInvokeDynamic || paramConstant instanceof com.android.dx.rop.cst.CstMethodHandle || paramConstant instanceof com.android.dx.rop.cst.CstProtoRef)
      visitThrowing(paramInt2, paramInt3, true); 
  }
  
  public void visitInvalid(int paramInt1, int paramInt2, int paramInt3) {
    visitCommon(paramInt2, paramInt3, true);
  }
  
  public void visitLocal(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Type paramType, int paramInt5) {
    if (paramInt1 == 169) {
      visitCommon(paramInt2, paramInt3, false);
      this.targetLists[paramInt2] = IntList.EMPTY;
    } else {
      visitCommon(paramInt2, paramInt3, true);
    } 
  }
  
  public void visitNewarray(int paramInt1, int paramInt2, CstType paramCstType, ArrayList<Constant> paramArrayList) {
    visitCommon(paramInt1, paramInt2, true);
    visitThrowing(paramInt1, paramInt2, true);
  }
  
  public void visitNoArgs(int paramInt1, int paramInt2, int paramInt3, Type paramType) {
    if (paramInt1 != 108 && paramInt1 != 112) {
      if (paramInt1 != 172 && paramInt1 != 177) {
        if (paramInt1 != 190)
          if (paramInt1 != 191) {
            if (paramInt1 != 194 && paramInt1 != 195)
              switch (paramInt1) {
                default:
                  switch (paramInt1) {
                    default:
                      visitCommon(paramInt2, paramInt3, true);
                      return;
                    case 79:
                    case 80:
                    case 81:
                    case 82:
                    case 83:
                    case 84:
                    case 85:
                    case 86:
                      break;
                  } 
                  break;
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                  break;
              }  
          } else {
            visitCommon(paramInt2, paramInt3, false);
            visitThrowing(paramInt2, paramInt3, false);
            return;
          }  
        visitCommon(paramInt2, paramInt3, true);
        visitThrowing(paramInt2, paramInt3, true);
      } else {
        visitCommon(paramInt2, paramInt3, false);
        this.targetLists[paramInt2] = IntList.EMPTY;
      } 
    } else {
      visitCommon(paramInt2, paramInt3, true);
      if (paramType == Type.INT || paramType == Type.LONG)
        visitThrowing(paramInt2, paramInt3, true); 
    } 
  }
  
  public void visitSwitch(int paramInt1, int paramInt2, int paramInt3, SwitchList paramSwitchList, int paramInt4) {
    paramInt1 = 0;
    visitCommon(paramInt2, paramInt3, false);
    addWorkIfNecessary(paramSwitchList.getDefaultTarget(), true);
    paramInt3 = paramSwitchList.size();
    while (paramInt1 < paramInt3) {
      addWorkIfNecessary(paramSwitchList.getTarget(paramInt1), true);
      paramInt1++;
    } 
    this.targetLists[paramInt2] = paramSwitchList.getTargets();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\BasicBlocker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */