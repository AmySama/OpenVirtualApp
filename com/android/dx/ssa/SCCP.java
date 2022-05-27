package com.android.dx.ssa;

import com.android.dx.rop.code.CstInsn;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.type.TypeBearer;
import java.util.ArrayList;
import java.util.BitSet;

public class SCCP {
  private static final int CONSTANT = 1;
  
  private static final int TOP = 0;
  
  private static final int VARYING = 2;
  
  private final ArrayList<SsaInsn> branchWorklist;
  
  private final ArrayList<SsaBasicBlock> cfgPhiWorklist;
  
  private final ArrayList<SsaBasicBlock> cfgWorklist;
  
  private final BitSet executableBlocks;
  
  private final Constant[] latticeConstants;
  
  private final int[] latticeValues;
  
  private final int regCount;
  
  private final SsaMethod ssaMeth;
  
  private final ArrayList<SsaInsn> ssaWorklist;
  
  private final ArrayList<SsaInsn> varyingWorklist;
  
  private SCCP(SsaMethod paramSsaMethod) {
    this.ssaMeth = paramSsaMethod;
    int i = paramSsaMethod.getRegCount();
    this.regCount = i;
    this.latticeValues = new int[i];
    this.latticeConstants = new Constant[i];
    this.cfgWorklist = new ArrayList<SsaBasicBlock>();
    this.cfgPhiWorklist = new ArrayList<SsaBasicBlock>();
    this.executableBlocks = new BitSet(paramSsaMethod.getBlocks().size());
    this.ssaWorklist = new ArrayList<SsaInsn>();
    this.varyingWorklist = new ArrayList<SsaInsn>();
    this.branchWorklist = new ArrayList<SsaInsn>();
    for (i = 0; i < this.regCount; i++) {
      this.latticeValues[i] = 0;
      this.latticeConstants[i] = null;
    } 
  }
  
  private void addBlockToWorklist(SsaBasicBlock paramSsaBasicBlock) {
    if (!this.executableBlocks.get(paramSsaBasicBlock.getIndex())) {
      this.cfgWorklist.add(paramSsaBasicBlock);
      this.executableBlocks.set(paramSsaBasicBlock.getIndex());
    } else {
      this.cfgPhiWorklist.add(paramSsaBasicBlock);
    } 
  }
  
  private void addUsersToWorklist(int paramInt1, int paramInt2) {
    if (paramInt2 == 2) {
      for (SsaInsn ssaInsn : this.ssaMeth.getUseListForRegister(paramInt1))
        this.varyingWorklist.add(ssaInsn); 
    } else {
      for (SsaInsn ssaInsn : this.ssaMeth.getUseListForRegister(paramInt1))
        this.ssaWorklist.add(ssaInsn); 
    } 
  }
  
  private static String latticeValName(int paramInt) {
    return (paramInt != 0) ? ((paramInt != 1) ? ((paramInt != 2) ? "UNKNOWN" : "VARYING") : "CONSTANT") : "TOP";
  }
  
  public static void process(SsaMethod paramSsaMethod) {
    (new SCCP(paramSsaMethod)).run();
  }
  
  private void replaceBranches() {
    for (SsaInsn ssaInsn : this.branchWorklist) {
      SsaBasicBlock ssaBasicBlock = ssaInsn.getBlock();
      int i = ssaBasicBlock.getSuccessorList().size();
      byte b = 0;
      int j = -1;
      while (b < i) {
        int k = ssaBasicBlock.getSuccessorList().get(b);
        if (!this.executableBlocks.get(k))
          j = k; 
        b++;
      } 
      if (i != 2 || j == -1)
        continue; 
      Insn insn = ssaInsn.getOriginalRopInsn();
      ssaBasicBlock.replaceLastInsn((Insn)new PlainInsn(Rops.GOTO, insn.getPosition(), null, RegisterSpecList.EMPTY));
      ssaBasicBlock.removeSuccessor(j);
    } 
  }
  
  private void replaceConstants() {
    for (byte b = 0; b < this.regCount; b++) {
      if (this.latticeValues[b] == 1 && this.latticeConstants[b] instanceof com.android.dx.rop.cst.TypedConstant) {
        SsaInsn ssaInsn = this.ssaMeth.getDefinitionForRegister(b);
        if (!ssaInsn.getResult().getTypeBearer().isConstant()) {
          ssaInsn.setResult(ssaInsn.getResult().withType((TypeBearer)this.latticeConstants[b]));
          for (SsaInsn ssaInsn1 : this.ssaMeth.getUseListForRegister(b)) {
            if (ssaInsn1.isPhiOrMove())
              continue; 
            ssaInsn = ssaInsn1;
            RegisterSpecList registerSpecList = ssaInsn1.getSources();
            int i = registerSpecList.indexOfRegister(b);
            ssaInsn.changeOneSource(i, registerSpecList.get(i).withType((TypeBearer)this.latticeConstants[b]));
          } 
        } 
      } 
    } 
  }
  
  private void run() {
    addBlockToWorklist(this.ssaMeth.getEntryBlock());
    while (true) {
      if (!this.cfgWorklist.isEmpty() || !this.cfgPhiWorklist.isEmpty() || !this.ssaWorklist.isEmpty() || !this.varyingWorklist.isEmpty()) {
        while (!this.cfgWorklist.isEmpty()) {
          int i = this.cfgWorklist.size();
          simulateBlock(this.cfgWorklist.remove(i - 1));
        } 
        while (!this.cfgPhiWorklist.isEmpty()) {
          int i = this.cfgPhiWorklist.size();
          simulatePhiBlock(this.cfgPhiWorklist.remove(i - 1));
        } 
        while (!this.varyingWorklist.isEmpty()) {
          int i = this.varyingWorklist.size();
          SsaInsn ssaInsn = this.varyingWorklist.remove(i - 1);
          if (!this.executableBlocks.get(ssaInsn.getBlock().getIndex()))
            continue; 
          if (ssaInsn instanceof PhiInsn) {
            simulatePhi((PhiInsn)ssaInsn);
            continue;
          } 
          simulateStmt(ssaInsn);
        } 
        while (!this.ssaWorklist.isEmpty()) {
          int i = this.ssaWorklist.size();
          SsaInsn ssaInsn = this.ssaWorklist.remove(i - 1);
          if (!this.executableBlocks.get(ssaInsn.getBlock().getIndex()))
            continue; 
          if (ssaInsn instanceof PhiInsn) {
            simulatePhi((PhiInsn)ssaInsn);
            continue;
          } 
          simulateStmt(ssaInsn);
        } 
        continue;
      } 
      replaceConstants();
      replaceBranches();
      return;
    } 
  }
  
  private boolean setLatticeValueTo(int paramInt1, int paramInt2, Constant paramConstant) {
    int[] arrayOfInt;
    if (paramInt2 != 1) {
      arrayOfInt = this.latticeValues;
      if (arrayOfInt[paramInt1] != paramInt2) {
        arrayOfInt[paramInt1] = paramInt2;
        return true;
      } 
      return false;
    } 
    if (this.latticeValues[paramInt1] != paramInt2 || !this.latticeConstants[paramInt1].equals(arrayOfInt)) {
      this.latticeValues[paramInt1] = paramInt2;
      this.latticeConstants[paramInt1] = (Constant)arrayOfInt;
      return true;
    } 
    return false;
  }
  
  private void simulateBlock(SsaBasicBlock paramSsaBasicBlock) {
    for (SsaInsn ssaInsn : paramSsaBasicBlock.getInsns()) {
      if (ssaInsn instanceof PhiInsn) {
        simulatePhi((PhiInsn)ssaInsn);
        continue;
      } 
      simulateStmt(ssaInsn);
    } 
  }
  
  private void simulateBranch(SsaInsn paramSsaInsn) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getOpcode : ()Lcom/android/dx/rop/code/Rop;
    //   4: astore_2
    //   5: aload_1
    //   6: invokevirtual getSources : ()Lcom/android/dx/rop/code/RegisterSpecList;
    //   9: astore_3
    //   10: aload_2
    //   11: invokevirtual getBranchingness : ()I
    //   14: istore #4
    //   16: iconst_0
    //   17: istore #5
    //   19: iload #4
    //   21: iconst_4
    //   22: if_icmpne -> 463
    //   25: aload_3
    //   26: iconst_0
    //   27: invokevirtual get : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   30: astore #6
    //   32: aload #6
    //   34: invokevirtual getReg : ()I
    //   37: istore #4
    //   39: aload_0
    //   40: getfield ssaMeth : Lcom/android/dx/ssa/SsaMethod;
    //   43: aload #6
    //   45: invokevirtual isRegALocal : (Lcom/android/dx/rop/code/RegisterSpec;)Z
    //   48: istore #7
    //   50: aconst_null
    //   51: astore #8
    //   53: iload #7
    //   55: ifne -> 81
    //   58: aload_0
    //   59: getfield latticeValues : [I
    //   62: iload #4
    //   64: iaload
    //   65: iconst_1
    //   66: if_icmpne -> 81
    //   69: aload_0
    //   70: getfield latticeConstants : [Lcom/android/dx/rop/cst/Constant;
    //   73: iload #4
    //   75: aaload
    //   76: astore #6
    //   78: goto -> 84
    //   81: aconst_null
    //   82: astore #6
    //   84: aload #8
    //   86: astore #9
    //   88: aload_3
    //   89: invokevirtual size : ()I
    //   92: iconst_2
    //   93: if_icmpne -> 150
    //   96: aload_3
    //   97: iconst_1
    //   98: invokevirtual get : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   101: astore #10
    //   103: aload #10
    //   105: invokevirtual getReg : ()I
    //   108: istore #4
    //   110: aload #8
    //   112: astore #9
    //   114: aload_0
    //   115: getfield ssaMeth : Lcom/android/dx/ssa/SsaMethod;
    //   118: aload #10
    //   120: invokevirtual isRegALocal : (Lcom/android/dx/rop/code/RegisterSpec;)Z
    //   123: ifne -> 150
    //   126: aload #8
    //   128: astore #9
    //   130: aload_0
    //   131: getfield latticeValues : [I
    //   134: iload #4
    //   136: iaload
    //   137: iconst_1
    //   138: if_icmpne -> 150
    //   141: aload_0
    //   142: getfield latticeConstants : [Lcom/android/dx/rop/cst/Constant;
    //   145: iload #4
    //   147: aaload
    //   148: astore #9
    //   150: aload #6
    //   152: ifnull -> 303
    //   155: aload_3
    //   156: invokevirtual size : ()I
    //   159: iconst_1
    //   160: if_icmpne -> 303
    //   163: aload #6
    //   165: checkcast com/android/dx/rop/cst/TypedConstant
    //   168: invokevirtual getBasicType : ()I
    //   171: bipush #6
    //   173: if_icmpeq -> 179
    //   176: goto -> 463
    //   179: aload #6
    //   181: checkcast com/android/dx/rop/cst/CstInteger
    //   184: invokevirtual getValue : ()I
    //   187: istore #4
    //   189: aload_2
    //   190: invokevirtual getOpcode : ()I
    //   193: tableswitch default -> 232, 7 -> 283, 8 -> 275, 9 -> 267, 10 -> 259, 11 -> 251, 12 -> 243
    //   232: new java/lang/RuntimeException
    //   235: dup
    //   236: ldc_w 'Unexpected op'
    //   239: invokespecial <init> : (Ljava/lang/String;)V
    //   242: athrow
    //   243: iload #4
    //   245: ifle -> 294
    //   248: goto -> 288
    //   251: iload #4
    //   253: ifgt -> 294
    //   256: goto -> 288
    //   259: iload #4
    //   261: iflt -> 294
    //   264: goto -> 288
    //   267: iload #4
    //   269: ifge -> 294
    //   272: goto -> 288
    //   275: iload #4
    //   277: ifeq -> 294
    //   280: goto -> 288
    //   283: iload #4
    //   285: ifne -> 294
    //   288: iconst_1
    //   289: istore #4
    //   291: goto -> 297
    //   294: iconst_0
    //   295: istore #4
    //   297: iconst_1
    //   298: istore #11
    //   300: goto -> 469
    //   303: aload #6
    //   305: ifnull -> 463
    //   308: aload #9
    //   310: ifnull -> 463
    //   313: aload #6
    //   315: checkcast com/android/dx/rop/cst/TypedConstant
    //   318: invokevirtual getBasicType : ()I
    //   321: bipush #6
    //   323: if_icmpeq -> 329
    //   326: goto -> 463
    //   329: aload #6
    //   331: checkcast com/android/dx/rop/cst/CstInteger
    //   334: invokevirtual getValue : ()I
    //   337: istore #11
    //   339: aload #9
    //   341: checkcast com/android/dx/rop/cst/CstInteger
    //   344: invokevirtual getValue : ()I
    //   347: istore #4
    //   349: aload_2
    //   350: invokevirtual getOpcode : ()I
    //   353: tableswitch default -> 392, 7 -> 453, 8 -> 443, 9 -> 433, 10 -> 423, 11 -> 413, 12 -> 403
    //   392: new java/lang/RuntimeException
    //   395: dup
    //   396: ldc_w 'Unexpected op'
    //   399: invokespecial <init> : (Ljava/lang/String;)V
    //   402: athrow
    //   403: iload #11
    //   405: iload #4
    //   407: if_icmple -> 294
    //   410: goto -> 288
    //   413: iload #11
    //   415: iload #4
    //   417: if_icmpgt -> 294
    //   420: goto -> 288
    //   423: iload #11
    //   425: iload #4
    //   427: if_icmplt -> 294
    //   430: goto -> 288
    //   433: iload #11
    //   435: iload #4
    //   437: if_icmpge -> 294
    //   440: goto -> 288
    //   443: iload #11
    //   445: iload #4
    //   447: if_icmpeq -> 294
    //   450: goto -> 288
    //   453: iload #11
    //   455: iload #4
    //   457: if_icmpne -> 294
    //   460: goto -> 288
    //   463: iconst_0
    //   464: istore #4
    //   466: iconst_0
    //   467: istore #11
    //   469: aload_1
    //   470: invokevirtual getBlock : ()Lcom/android/dx/ssa/SsaBasicBlock;
    //   473: astore #6
    //   475: iload #11
    //   477: ifeq -> 541
    //   480: iload #4
    //   482: ifeq -> 499
    //   485: aload #6
    //   487: invokevirtual getSuccessorList : ()Lcom/android/dx/util/IntList;
    //   490: iconst_1
    //   491: invokevirtual get : (I)I
    //   494: istore #4
    //   496: goto -> 510
    //   499: aload #6
    //   501: invokevirtual getSuccessorList : ()Lcom/android/dx/util/IntList;
    //   504: iconst_0
    //   505: invokevirtual get : (I)I
    //   508: istore #4
    //   510: aload_0
    //   511: aload_0
    //   512: getfield ssaMeth : Lcom/android/dx/ssa/SsaMethod;
    //   515: invokevirtual getBlocks : ()Ljava/util/ArrayList;
    //   518: iload #4
    //   520: invokevirtual get : (I)Ljava/lang/Object;
    //   523: checkcast com/android/dx/ssa/SsaBasicBlock
    //   526: invokespecial addBlockToWorklist : (Lcom/android/dx/ssa/SsaBasicBlock;)V
    //   529: aload_0
    //   530: getfield branchWorklist : Ljava/util/ArrayList;
    //   533: aload_1
    //   534: invokevirtual add : (Ljava/lang/Object;)Z
    //   537: pop
    //   538: goto -> 591
    //   541: iload #5
    //   543: aload #6
    //   545: invokevirtual getSuccessorList : ()Lcom/android/dx/util/IntList;
    //   548: invokevirtual size : ()I
    //   551: if_icmpge -> 591
    //   554: aload #6
    //   556: invokevirtual getSuccessorList : ()Lcom/android/dx/util/IntList;
    //   559: iload #5
    //   561: invokevirtual get : (I)I
    //   564: istore #4
    //   566: aload_0
    //   567: aload_0
    //   568: getfield ssaMeth : Lcom/android/dx/ssa/SsaMethod;
    //   571: invokevirtual getBlocks : ()Ljava/util/ArrayList;
    //   574: iload #4
    //   576: invokevirtual get : (I)Ljava/lang/Object;
    //   579: checkcast com/android/dx/ssa/SsaBasicBlock
    //   582: invokespecial addBlockToWorklist : (Lcom/android/dx/ssa/SsaBasicBlock;)V
    //   585: iinc #5, 1
    //   588: goto -> 541
    //   591: return
  }
  
  private Constant simulateMath(SsaInsn paramSsaInsn, int paramInt) {
    Constant constant1;
    CstInteger cstInteger;
    Constant constant2;
    Insn insn1 = paramSsaInsn.getOriginalRopInsn();
    int i = paramSsaInsn.getOpcode().getOpcode();
    RegisterSpecList registerSpecList = paramSsaInsn.getSources();
    boolean bool = false;
    int j = registerSpecList.get(0).getReg();
    int k = this.latticeValues[j];
    Insn insn2 = null;
    if (k != 1) {
      constant2 = null;
    } else {
      constant2 = this.latticeConstants[j];
    } 
    if (registerSpecList.size() == 1) {
      constant1 = ((CstInsn)insn1).getConstant();
    } else {
      j = registerSpecList.get(1).getReg();
      if (this.latticeValues[j] != 1) {
        paramSsaInsn = null;
      } else {
        constant1 = this.latticeConstants[j];
      } 
    } 
    insn1 = insn2;
    if (constant2 != null)
      if (constant1 == null) {
        insn1 = insn2;
      } else {
        if (paramInt != 6)
          return null; 
        j = ((CstInteger)constant2).getValue();
        paramInt = ((CstInteger)constant1).getValue();
        switch (i) {
          default:
            throw new RuntimeException("Unexpected op");
          case 25:
            paramInt = j >>> paramInt;
            break;
          case 24:
            paramInt = j >> paramInt;
            break;
          case 23:
            paramInt = j << paramInt;
            break;
          case 22:
            paramInt = j ^ paramInt;
            break;
          case 21:
            paramInt = j | paramInt;
            break;
          case 20:
            paramInt = j & paramInt;
            break;
          case 18:
            if (paramInt != 0) {
              paramInt = j % paramInt;
              break;
            } 
            paramInt = 0;
            bool = true;
            break;
          case 17:
            if (paramInt != 0) {
              paramInt = j / paramInt;
              break;
            } 
            paramInt = 0;
            bool = true;
            break;
          case 16:
            paramInt = j * paramInt;
            break;
          case 15:
            if (registerSpecList.size() == 1) {
              paramInt -= j;
              break;
            } 
            paramInt = j - paramInt;
            break;
          case 14:
            paramInt = j + paramInt;
            break;
        } 
        if (bool) {
          insn1 = insn2;
        } else {
          cstInteger = CstInteger.make(paramInt);
        } 
      }  
    return (Constant)cstInteger;
  }
  
  private void simulatePhi(PhiInsn paramPhiInsn) {
    int i = paramPhiInsn.getResult().getReg();
    int j = this.latticeValues[i];
    byte b1 = 2;
    if (j == 2)
      return; 
    RegisterSpecList registerSpecList = paramPhiInsn.getSources();
    Constant constant = null;
    int k = registerSpecList.size();
    byte b2 = 0;
    j = 0;
    while (b2 < k) {
      Constant constant1;
      int m = paramPhiInsn.predBlockIndexForSourcesIndex(b2);
      int n = registerSpecList.get(b2).getReg();
      int i1 = this.latticeValues[n];
      if (!this.executableBlocks.get(m)) {
        constant1 = constant;
      } else if (i1 == 1) {
        if (constant == null) {
          constant1 = this.latticeConstants[n];
          j = 1;
        } else {
          constant1 = constant;
          if (!this.latticeConstants[n].equals(constant)) {
            j = b1;
            break;
          } 
        } 
      } else {
        j = i1;
        break;
      } 
      b2++;
      constant = constant1;
    } 
    if (setLatticeValueTo(i, j, constant))
      addUsersToWorklist(i, j); 
  }
  
  private void simulatePhiBlock(SsaBasicBlock paramSsaBasicBlock) {
    for (SsaInsn ssaInsn : paramSsaBasicBlock.getInsns()) {
      if (ssaInsn instanceof PhiInsn)
        simulatePhi((PhiInsn)ssaInsn); 
    } 
  }
  
  private void simulateStmt(SsaInsn paramSsaInsn) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getOriginalRopInsn : ()Lcom/android/dx/rop/code/Insn;
    //   4: astore_2
    //   5: aload_2
    //   6: invokevirtual getOpcode : ()Lcom/android/dx/rop/code/Rop;
    //   9: invokevirtual getBranchingness : ()I
    //   12: istore_3
    //   13: iconst_1
    //   14: istore #4
    //   16: iload_3
    //   17: iconst_1
    //   18: if_icmpne -> 31
    //   21: aload_2
    //   22: invokevirtual getOpcode : ()Lcom/android/dx/rop/code/Rop;
    //   25: invokevirtual isCallLike : ()Z
    //   28: ifeq -> 36
    //   31: aload_0
    //   32: aload_1
    //   33: invokespecial simulateBranch : (Lcom/android/dx/ssa/SsaInsn;)V
    //   36: aload_1
    //   37: invokevirtual getOpcode : ()Lcom/android/dx/rop/code/Rop;
    //   40: invokevirtual getOpcode : ()I
    //   43: istore #5
    //   45: aload_1
    //   46: invokevirtual getResult : ()Lcom/android/dx/rop/code/RegisterSpec;
    //   49: astore #6
    //   51: aload #6
    //   53: astore #7
    //   55: aload #6
    //   57: ifnonnull -> 100
    //   60: iload #5
    //   62: bipush #17
    //   64: if_icmpeq -> 78
    //   67: iload #5
    //   69: bipush #18
    //   71: if_icmpne -> 77
    //   74: goto -> 78
    //   77: return
    //   78: aload_1
    //   79: invokevirtual getBlock : ()Lcom/android/dx/ssa/SsaBasicBlock;
    //   82: invokevirtual getPrimarySuccessor : ()Lcom/android/dx/ssa/SsaBasicBlock;
    //   85: invokevirtual getInsns : ()Ljava/util/ArrayList;
    //   88: iconst_0
    //   89: invokevirtual get : (I)Ljava/lang/Object;
    //   92: checkcast com/android/dx/ssa/SsaInsn
    //   95: invokevirtual getResult : ()Lcom/android/dx/rop/code/RegisterSpec;
    //   98: astore #7
    //   100: aload #7
    //   102: invokevirtual getReg : ()I
    //   105: istore_3
    //   106: aconst_null
    //   107: astore #6
    //   109: iload #5
    //   111: iconst_2
    //   112: if_icmpeq -> 274
    //   115: iload #5
    //   117: iconst_5
    //   118: if_icmpeq -> 263
    //   121: iload #5
    //   123: bipush #56
    //   125: if_icmpeq -> 232
    //   128: iload #5
    //   130: tableswitch default -> 164, 14 -> 211, 15 -> 211, 16 -> 211, 17 -> 211, 18 -> 211
    //   164: iload #5
    //   166: tableswitch default -> 204, 20 -> 211, 21 -> 211, 22 -> 211, 23 -> 211, 24 -> 211, 25 -> 211
    //   204: aload #6
    //   206: astore #7
    //   208: goto -> 322
    //   211: aload_0
    //   212: aload_1
    //   213: aload #7
    //   215: invokevirtual getBasicType : ()I
    //   218: invokespecial simulateMath : (Lcom/android/dx/ssa/SsaInsn;I)Lcom/android/dx/rop/cst/Constant;
    //   221: astore_1
    //   222: aload_1
    //   223: astore #7
    //   225: aload_1
    //   226: ifnull -> 322
    //   229: goto -> 328
    //   232: aload_0
    //   233: getfield latticeValues : [I
    //   236: astore_1
    //   237: aload #6
    //   239: astore #7
    //   241: aload_1
    //   242: iload_3
    //   243: iaload
    //   244: iconst_1
    //   245: if_icmpne -> 322
    //   248: aload_1
    //   249: iload_3
    //   250: iaload
    //   251: istore #4
    //   253: aload_0
    //   254: getfield latticeConstants : [Lcom/android/dx/rop/cst/Constant;
    //   257: iload_3
    //   258: aaload
    //   259: astore_1
    //   260: goto -> 328
    //   263: aload_2
    //   264: checkcast com/android/dx/rop/code/CstInsn
    //   267: invokevirtual getConstant : ()Lcom/android/dx/rop/cst/Constant;
    //   270: astore_1
    //   271: goto -> 328
    //   274: aload #6
    //   276: astore #7
    //   278: aload_1
    //   279: invokevirtual getSources : ()Lcom/android/dx/rop/code/RegisterSpecList;
    //   282: invokevirtual size : ()I
    //   285: iconst_1
    //   286: if_icmpne -> 322
    //   289: aload_1
    //   290: invokevirtual getSources : ()Lcom/android/dx/rop/code/RegisterSpecList;
    //   293: iconst_0
    //   294: invokevirtual get : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   297: invokevirtual getReg : ()I
    //   300: istore #5
    //   302: aload_0
    //   303: getfield latticeValues : [I
    //   306: iload #5
    //   308: iaload
    //   309: istore #4
    //   311: aload_0
    //   312: getfield latticeConstants : [Lcom/android/dx/rop/cst/Constant;
    //   315: iload #5
    //   317: aaload
    //   318: astore_1
    //   319: goto -> 328
    //   322: iconst_2
    //   323: istore #4
    //   325: aload #7
    //   327: astore_1
    //   328: aload_0
    //   329: iload_3
    //   330: iload #4
    //   332: aload_1
    //   333: invokespecial setLatticeValueTo : (IILcom/android/dx/rop/cst/Constant;)Z
    //   336: ifeq -> 346
    //   339: aload_0
    //   340: iload_3
    //   341: iload #4
    //   343: invokespecial addUsersToWorklist : (II)V
    //   346: return
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\SCCP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */