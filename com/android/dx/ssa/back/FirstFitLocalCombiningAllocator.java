package com.android.dx.ssa.back;

import com.android.dx.rop.code.CstInsn;
import com.android.dx.rop.code.LocalItem;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rop;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.ssa.InterferenceRegisterMapper;
import com.android.dx.ssa.NormalSsaInsn;
import com.android.dx.ssa.Optimizer;
import com.android.dx.ssa.PhiInsn;
import com.android.dx.ssa.RegisterMapper;
import com.android.dx.ssa.SsaBasicBlock;
import com.android.dx.ssa.SsaInsn;
import com.android.dx.ssa.SsaMethod;
import com.android.dx.util.IntIterator;
import com.android.dx.util.IntSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class FirstFitLocalCombiningAllocator extends RegisterAllocator {
  private static final boolean DEBUG = false;
  
  private final ArrayList<NormalSsaInsn> invokeRangeInsns;
  
  private final Map<LocalItem, ArrayList<RegisterSpec>> localVariables;
  
  private final InterferenceRegisterMapper mapper;
  
  private final boolean minimizeRegisters;
  
  private final ArrayList<NormalSsaInsn> moveResultPseudoInsns;
  
  private final int paramRangeEnd;
  
  private final ArrayList<PhiInsn> phiInsns;
  
  private final BitSet reservedRopRegs;
  
  private final BitSet ssaRegsMapped;
  
  private final BitSet usedRopRegs;
  
  public FirstFitLocalCombiningAllocator(SsaMethod paramSsaMethod, InterferenceGraph paramInterferenceGraph, boolean paramBoolean) {
    super(paramSsaMethod, paramInterferenceGraph);
    this.ssaRegsMapped = new BitSet(paramSsaMethod.getRegCount());
    this.mapper = new InterferenceRegisterMapper(paramInterferenceGraph, paramSsaMethod.getRegCount());
    this.minimizeRegisters = paramBoolean;
    this.paramRangeEnd = paramSsaMethod.getParamWidth();
    BitSet bitSet = new BitSet(this.paramRangeEnd * 2);
    this.reservedRopRegs = bitSet;
    bitSet.set(0, this.paramRangeEnd);
    this.usedRopRegs = new BitSet(this.paramRangeEnd * 2);
    this.localVariables = new TreeMap<LocalItem, ArrayList<RegisterSpec>>();
    this.moveResultPseudoInsns = new ArrayList<NormalSsaInsn>();
    this.invokeRangeInsns = new ArrayList<NormalSsaInsn>();
    this.phiInsns = new ArrayList<PhiInsn>();
  }
  
  private void addMapping(RegisterSpec paramRegisterSpec, int paramInt) {
    int i = paramRegisterSpec.getReg();
    if (!this.ssaRegsMapped.get(i) && canMapReg(paramRegisterSpec, paramInt)) {
      int j = paramRegisterSpec.getCategory();
      this.mapper.addMapping(paramRegisterSpec.getReg(), paramInt, j);
      this.ssaRegsMapped.set(i);
      this.usedRopRegs.set(paramInt, j + paramInt);
      return;
    } 
    throw new RuntimeException("attempt to add invalid register mapping");
  }
  
  private void adjustAndMapSourceRangeRange(NormalSsaInsn paramNormalSsaInsn) {
    int i = findRangeAndAdjust(paramNormalSsaInsn);
    RegisterSpecList registerSpecList = paramNormalSsaInsn.getSources();
    int j = registerSpecList.size();
    byte b = 0;
    while (b < j) {
      RegisterSpec registerSpec = registerSpecList.get(b);
      int k = registerSpec.getReg();
      int m = registerSpec.getCategory();
      if (!this.ssaRegsMapped.get(k)) {
        LocalItem localItem = getLocalItemForReg(k);
        addMapping(registerSpec, i);
        if (localItem != null) {
          markReserved(i, m);
          ArrayList<RegisterSpec> arrayList = this.localVariables.get(localItem);
          int n = arrayList.size();
          for (k = 0; k < n; k++) {
            RegisterSpec registerSpec1 = arrayList.get(k);
            if (-1 == registerSpecList.indexOfRegister(registerSpec1.getReg()))
              tryMapReg(registerSpec1, i, m); 
          } 
        } 
      } 
      b++;
      i += m;
    } 
  }
  
  private void analyzeInstructions() {
    this.ssaMeth.forEachInsn(new SsaInsn.Visitor() {
          private void processInsn(SsaInsn param1SsaInsn) {
            RegisterSpec registerSpec = param1SsaInsn.getLocalAssignment();
            if (registerSpec != null) {
              LocalItem localItem = registerSpec.getLocalItem();
              ArrayList<RegisterSpec> arrayList1 = (ArrayList)FirstFitLocalCombiningAllocator.this.localVariables.get(localItem);
              ArrayList<RegisterSpec> arrayList2 = arrayList1;
              if (arrayList1 == null) {
                arrayList2 = new ArrayList();
                FirstFitLocalCombiningAllocator.this.localVariables.put(localItem, arrayList2);
              } 
              arrayList2.add(registerSpec);
            } 
            if (param1SsaInsn instanceof NormalSsaInsn) {
              if (param1SsaInsn.getOpcode().getOpcode() == 56) {
                FirstFitLocalCombiningAllocator.this.moveResultPseudoInsns.add((NormalSsaInsn)param1SsaInsn);
              } else if (Optimizer.getAdvice().requiresSourcesInOrder(param1SsaInsn.getOriginalRopInsn().getOpcode(), param1SsaInsn.getSources())) {
                FirstFitLocalCombiningAllocator.this.invokeRangeInsns.add((NormalSsaInsn)param1SsaInsn);
              } 
            } else if (param1SsaInsn instanceof PhiInsn) {
              FirstFitLocalCombiningAllocator.this.phiInsns.add((PhiInsn)param1SsaInsn);
            } 
          }
          
          public void visitMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
            processInsn((SsaInsn)param1NormalSsaInsn);
          }
          
          public void visitNonMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
            processInsn((SsaInsn)param1NormalSsaInsn);
          }
          
          public void visitPhiInsn(PhiInsn param1PhiInsn) {
            processInsn((SsaInsn)param1PhiInsn);
          }
        });
  }
  
  private boolean canMapReg(RegisterSpec paramRegisterSpec, int paramInt) {
    boolean bool;
    if (!spansParamRange(paramInt, paramRegisterSpec.getCategory()) && !this.mapper.interferes(paramRegisterSpec, paramInt)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean canMapRegs(ArrayList<RegisterSpec> paramArrayList, int paramInt) {
    for (RegisterSpec registerSpec : paramArrayList) {
      if (!this.ssaRegsMapped.get(registerSpec.getReg()) && !canMapReg(registerSpec, paramInt))
        return false; 
    } 
    return true;
  }
  
  private int findAnyFittingRange(NormalSsaInsn paramNormalSsaInsn, int paramInt, int[] paramArrayOfint, BitSet paramBitSet) {
    Alignment alignment = Alignment.UNSPECIFIED;
    int i = paramArrayOfint.length;
    byte b1 = 0;
    byte b2 = 0;
    int j = 0;
    byte b3 = 0;
    while (b1 < i) {
      if (paramArrayOfint[b1] == 2) {
        if (isEven(b3)) {
          j++;
        } else {
          b2++;
        } 
        b3 += true;
      } else {
        b3++;
      } 
      b1++;
    } 
    if (b2 > j) {
      if (isEven(this.paramRangeEnd)) {
        alignment = Alignment.ODD;
      } else {
        alignment = Alignment.EVEN;
      } 
    } else if (j > 0) {
      if (isEven(this.paramRangeEnd)) {
        alignment = Alignment.EVEN;
      } else {
        alignment = Alignment.ODD;
      } 
    } 
    j = this.paramRangeEnd;
    while (true) {
      j = findNextUnreservedRopReg(j, paramInt, alignment);
      if (fitPlanForRange(j, paramNormalSsaInsn, paramArrayOfint, paramBitSet) >= 0)
        return j; 
      j++;
      paramBitSet.clear();
    } 
  }
  
  private int findNextUnreservedRopReg(int paramInt1, int paramInt2) {
    return findNextUnreservedRopReg(paramInt1, paramInt2, getAlignment(paramInt2));
  }
  
  private int findNextUnreservedRopReg(int paramInt1, int paramInt2, Alignment paramAlignment) {
    for (paramInt1 = paramAlignment.nextClearBit(this.reservedRopRegs, paramInt1);; paramInt1 = paramAlignment.nextClearBit(this.reservedRopRegs, paramInt1 + b)) {
      byte b;
      for (b = 1; b < paramInt2 && !this.reservedRopRegs.get(paramInt1 + b); b++);
      if (b == paramInt2)
        return paramInt1; 
    } 
  }
  
  private int findRangeAndAdjust(NormalSsaInsn paramNormalSsaInsn) {
    BitSet bitSet2;
    int i1;
    RegisterSpecList registerSpecList = paramNormalSsaInsn.getSources();
    int i = registerSpecList.size();
    int[] arrayOfInt = new int[i];
    int j = 0;
    int k = 0;
    while (j < i) {
      arrayOfInt[j] = registerSpecList.get(j).getCategory();
      k += arrayOfInt[j];
      j++;
    } 
    int m = Integer.MIN_VALUE;
    BitSet bitSet1 = null;
    byte b = 0;
    int n = 0;
    j = -1;
    while (true) {
      bitSet2 = bitSet1;
      i1 = j;
      if (b < i) {
        i1 = registerSpecList.get(b).getReg();
        int i2 = n;
        if (b != 0)
          i2 = n - arrayOfInt[b - 1]; 
        if (!this.ssaRegsMapped.get(i1)) {
          n = m;
          bitSet2 = bitSet1;
          i1 = j;
        } else {
          int i3 = this.mapper.oldToNew(i1) + i2;
          n = m;
          bitSet2 = bitSet1;
          i1 = j;
          if (i3 >= 0)
            if (spansParamRange(i3, k)) {
              n = m;
              bitSet2 = bitSet1;
              i1 = j;
            } else {
              bitSet2 = new BitSet(i);
              int i4 = fitPlanForRange(i3, paramNormalSsaInsn, arrayOfInt, bitSet2);
              if (i4 < 0) {
                n = m;
                bitSet2 = bitSet1;
                i1 = j;
              } else {
                n = i4 - bitSet2.cardinality();
                i1 = m;
                if (n > m) {
                  i1 = n;
                  j = i3;
                  bitSet1 = bitSet2;
                } 
                n = i1;
                bitSet2 = bitSet1;
                i1 = j;
                if (i4 == k) {
                  bitSet2 = bitSet1;
                  i1 = j;
                  break;
                } 
              } 
            }  
        } 
        b++;
        m = n;
        bitSet1 = bitSet2;
        n = i2;
        j = i1;
        continue;
      } 
      break;
    } 
    j = i1;
    if (i1 == -1) {
      bitSet2 = new BitSet(i);
      j = findAnyFittingRange(paramNormalSsaInsn, k, arrayOfInt, bitSet2);
    } 
    for (m = bitSet2.nextSetBit(0); m >= 0; m = bitSet2.nextSetBit(m + 1))
      paramNormalSsaInsn.changeOneSource(m, insertMoveBefore((SsaInsn)paramNormalSsaInsn, registerSpecList.get(m))); 
    return j;
  }
  
  private int findRopRegForLocal(int paramInt1, int paramInt2) {
    Alignment alignment = getAlignment(paramInt2);
    for (paramInt1 = alignment.nextClearBit(this.usedRopRegs, paramInt1);; paramInt1 = alignment.nextClearBit(this.usedRopRegs, paramInt1 + b)) {
      byte b;
      for (b = 1; b < paramInt2 && !this.usedRopRegs.get(paramInt1 + b); b++);
      if (b == paramInt2)
        return paramInt1; 
    } 
  }
  
  private int fitPlanForRange(int paramInt, NormalSsaInsn paramNormalSsaInsn, int[] paramArrayOfint, BitSet paramBitSet) {
    // Byte code:
    //   0: aload_2
    //   1: invokevirtual getSources : ()Lcom/android/dx/rop/code/RegisterSpecList;
    //   4: astore #5
    //   6: aload #5
    //   8: invokevirtual size : ()I
    //   11: istore #6
    //   13: aload_0
    //   14: aload_2
    //   15: invokevirtual getBlock : ()Lcom/android/dx/ssa/SsaBasicBlock;
    //   18: invokevirtual getLiveOutRegs : ()Lcom/android/dx/util/IntSet;
    //   21: invokevirtual ssaSetToSpecs : (Lcom/android/dx/util/IntSet;)Lcom/android/dx/rop/code/RegisterSpecList;
    //   24: astore_2
    //   25: new java/util/BitSet
    //   28: dup
    //   29: aload_0
    //   30: getfield ssaMeth : Lcom/android/dx/ssa/SsaMethod;
    //   33: invokevirtual getRegCount : ()I
    //   36: invokespecial <init> : (I)V
    //   39: astore #7
    //   41: iconst_0
    //   42: istore #8
    //   44: iconst_0
    //   45: istore #9
    //   47: iload_1
    //   48: istore #10
    //   50: iload #9
    //   52: istore_1
    //   53: iload_1
    //   54: istore #9
    //   56: iload #8
    //   58: iload #6
    //   60: if_icmpge -> 247
    //   63: aload #5
    //   65: iload #8
    //   67: invokevirtual get : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   70: astore #11
    //   72: aload #11
    //   74: invokevirtual getReg : ()I
    //   77: istore #12
    //   79: aload_3
    //   80: iload #8
    //   82: iaload
    //   83: istore #13
    //   85: iload #10
    //   87: istore #9
    //   89: iload #8
    //   91: ifeq -> 105
    //   94: iload #10
    //   96: aload_3
    //   97: iload #8
    //   99: iconst_1
    //   100: isub
    //   101: iaload
    //   102: iadd
    //   103: istore #9
    //   105: aload_0
    //   106: getfield ssaRegsMapped : Ljava/util/BitSet;
    //   109: iload #12
    //   111: invokevirtual get : (I)Z
    //   114: ifeq -> 139
    //   117: aload_0
    //   118: getfield mapper : Lcom/android/dx/ssa/InterferenceRegisterMapper;
    //   121: iload #12
    //   123: invokevirtual oldToNew : (I)I
    //   126: iload #9
    //   128: if_icmpne -> 139
    //   131: iload_1
    //   132: iload #13
    //   134: iadd
    //   135: istore_1
    //   136: goto -> 230
    //   139: aload_0
    //   140: iload #9
    //   142: iload #13
    //   144: invokespecial rangeContainsReserved : (II)Z
    //   147: ifeq -> 156
    //   150: iconst_m1
    //   151: istore #9
    //   153: goto -> 247
    //   156: aload_0
    //   157: getfield ssaRegsMapped : Ljava/util/BitSet;
    //   160: iload #12
    //   162: invokevirtual get : (I)Z
    //   165: ifne -> 192
    //   168: aload_0
    //   169: aload #11
    //   171: iload #9
    //   173: invokespecial canMapReg : (Lcom/android/dx/rop/code/RegisterSpec;I)Z
    //   176: ifeq -> 192
    //   179: aload #7
    //   181: iload #12
    //   183: invokevirtual get : (I)Z
    //   186: ifne -> 192
    //   189: goto -> 131
    //   192: aload_0
    //   193: getfield mapper : Lcom/android/dx/ssa/InterferenceRegisterMapper;
    //   196: aload_2
    //   197: iload #9
    //   199: iload #13
    //   201: invokevirtual areAnyPinned : (Lcom/android/dx/rop/code/RegisterSpecList;II)Z
    //   204: ifne -> 150
    //   207: aload_0
    //   208: getfield mapper : Lcom/android/dx/ssa/InterferenceRegisterMapper;
    //   211: aload #5
    //   213: iload #9
    //   215: iload #13
    //   217: invokevirtual areAnyPinned : (Lcom/android/dx/rop/code/RegisterSpecList;II)Z
    //   220: ifne -> 150
    //   223: aload #4
    //   225: iload #8
    //   227: invokevirtual set : (I)V
    //   230: aload #7
    //   232: iload #12
    //   234: invokevirtual set : (I)V
    //   237: iinc #8, 1
    //   240: iload #9
    //   242: istore #10
    //   244: goto -> 53
    //   247: iload #9
    //   249: ireturn
  }
  
  private Alignment getAlignment(int paramInt) {
    Alignment alignment = Alignment.UNSPECIFIED;
    if (paramInt == 2)
      if (isEven(this.paramRangeEnd)) {
        alignment = Alignment.EVEN;
      } else {
        alignment = Alignment.ODD;
      }  
    return alignment;
  }
  
  private LocalItem getLocalItemForReg(int paramInt) {
    for (Map.Entry<LocalItem, ArrayList<RegisterSpec>> entry : this.localVariables.entrySet()) {
      Iterator<RegisterSpec> iterator = ((ArrayList)entry.getValue()).iterator();
      while (iterator.hasNext()) {
        if (((RegisterSpec)iterator.next()).getReg() == paramInt)
          return (LocalItem)entry.getKey(); 
      } 
    } 
    return null;
  }
  
  private int getParameterIndexForReg(int paramInt) {
    SsaInsn ssaInsn = this.ssaMeth.getDefinitionForRegister(paramInt);
    if (ssaInsn == null)
      return -1; 
    Rop rop = ssaInsn.getOpcode();
    return (rop != null && rop.getOpcode() == 3) ? ((CstInteger)((CstInsn)ssaInsn.getOriginalRopInsn()).getConstant()).getValue() : -1;
  }
  
  private void handleCheckCastResults() {
    for (NormalSsaInsn normalSsaInsn : this.moveResultPseudoInsns) {
      RegisterSpec registerSpec1 = normalSsaInsn.getResult();
      int i = registerSpec1.getReg();
      BitSet bitSet = normalSsaInsn.getBlock().getPredecessors();
      int j = bitSet.cardinality();
      int k = 1;
      if (j != 1)
        continue; 
      ArrayList<SsaInsn> arrayList = ((SsaBasicBlock)this.ssaMeth.getBlocks().get(bitSet.nextSetBit(0))).getInsns();
      SsaInsn ssaInsn = arrayList.get(arrayList.size() - 1);
      if (ssaInsn.getOpcode().getOpcode() != 43)
        continue; 
      RegisterSpec registerSpec2 = ssaInsn.getSources().get(0);
      int m = registerSpec2.getReg();
      int n = registerSpec2.getCategory();
      boolean bool1 = this.ssaRegsMapped.get(i);
      boolean bool2 = this.ssaRegsMapped.get(m);
      boolean bool3 = bool2;
      if (((bool2 ^ true) & bool1) != 0)
        bool3 = tryMapReg(registerSpec2, this.mapper.oldToNew(i), n); 
      bool2 = bool1;
      if (((bool1 ^ true) & bool3) != 0)
        bool2 = tryMapReg(registerSpec1, this.mapper.oldToNew(m), n); 
      if (!bool2 || !bool3) {
        j = findNextUnreservedRopReg(this.paramRangeEnd, n);
        ArrayList<RegisterSpec> arrayList1 = new ArrayList(2);
        arrayList1.add(registerSpec1);
        arrayList1.add(registerSpec2);
        while (!tryMapRegs(arrayList1, j, n, false))
          j = findNextUnreservedRopReg(j + 1, n); 
      } 
      if (ssaInsn.getOriginalRopInsn().getCatches().size() != 0) {
        j = k;
      } else {
        j = 0;
      } 
      k = this.mapper.oldToNew(i);
      if (k != this.mapper.oldToNew(m) && j == 0) {
        ((NormalSsaInsn)ssaInsn).changeOneSource(0, insertMoveBefore(ssaInsn, registerSpec2));
        addMapping(ssaInsn.getSources().get(0), k);
      } 
    } 
  }
  
  private void handleInvokeRangeInsns() {
    Iterator<NormalSsaInsn> iterator = this.invokeRangeInsns.iterator();
    while (iterator.hasNext())
      adjustAndMapSourceRangeRange(iterator.next()); 
  }
  
  private void handleLocalAssociatedOther() {
    label23: for (ArrayList<RegisterSpec> arrayList : this.localVariables.values()) {
      int i = this.paramRangeEnd;
      boolean bool = false;
      while (true) {
        int j = arrayList.size();
        byte b = 0;
        int k;
        for (k = 1; b < j; k = n) {
          RegisterSpec registerSpec = arrayList.get(b);
          int m = registerSpec.getCategory();
          int n = k;
          if (!this.ssaRegsMapped.get(registerSpec.getReg())) {
            n = k;
            if (m > k)
              n = m; 
          } 
          b++;
        } 
        i = findRopRegForLocal(i, k);
        boolean bool1 = bool;
        if (canMapRegs(arrayList, i))
          bool1 = tryMapRegs(arrayList, i, k, true); 
        i++;
        bool = bool1;
        if (bool1)
          continue label23; 
      } 
    } 
  }
  
  private void handleLocalAssociatedParams() {
    for (ArrayList<RegisterSpec> arrayList : this.localVariables.values()) {
      int k;
      int i = arrayList.size();
      int j = -1;
      byte b1 = 0;
      byte b2 = 0;
      while (true) {
        k = b1;
        if (b2 < i) {
          RegisterSpec registerSpec = arrayList.get(b2);
          j = getParameterIndexForReg(registerSpec.getReg());
          if (j >= 0) {
            k = registerSpec.getCategory();
            addMapping(registerSpec, j);
            break;
          } 
          b2++;
          continue;
        } 
        break;
      } 
      if (j < 0)
        continue; 
      tryMapRegs(arrayList, j, k, true);
    } 
  }
  
  private void handleNormalUnassociated() {
    int i = this.ssaMeth.getRegCount();
    for (byte b = 0; b < i; b++) {
      if (!this.ssaRegsMapped.get(b)) {
        RegisterSpec registerSpec = getDefinitionSpecForSsaReg(b);
        if (registerSpec != null) {
          int j = registerSpec.getCategory();
          int k;
          for (k = findNextUnreservedRopReg(this.paramRangeEnd, j); !canMapReg(registerSpec, k); k = findNextUnreservedRopReg(k + 1, j));
          addMapping(registerSpec, k);
        } 
      } 
    } 
  }
  
  private void handlePhiInsns() {
    Iterator<PhiInsn> iterator = this.phiInsns.iterator();
    while (iterator.hasNext())
      processPhiInsn(iterator.next()); 
  }
  
  private void handleUnassociatedParameters() {
    int i = this.ssaMeth.getRegCount();
    for (byte b = 0; b < i; b++) {
      if (!this.ssaRegsMapped.get(b)) {
        int j = getParameterIndexForReg(b);
        RegisterSpec registerSpec = getDefinitionSpecForSsaReg(b);
        if (j >= 0)
          addMapping(registerSpec, j); 
      } 
    } 
  }
  
  private static boolean isEven(int paramInt) {
    boolean bool = true;
    if ((paramInt & 0x1) != 0)
      bool = false; 
    return bool;
  }
  
  private boolean isThisPointerReg(int paramInt) {
    boolean bool;
    if (paramInt == 0 && !this.ssaMeth.isStatic()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void markReserved(int paramInt1, int paramInt2) {
    this.reservedRopRegs.set(paramInt1, paramInt2 + paramInt1, true);
  }
  
  private void printLocalVars() {
    System.out.println("Printing local vars");
    for (Map.Entry<LocalItem, ArrayList<RegisterSpec>> entry : this.localVariables.entrySet()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append('{');
      stringBuilder.append(' ');
      for (RegisterSpec registerSpec : entry.getValue()) {
        stringBuilder.append('v');
        stringBuilder.append(registerSpec.getReg());
        stringBuilder.append(' ');
      } 
      stringBuilder.append('}');
      System.out.printf("Local: %s Registers: %s\n", new Object[] { entry.getKey(), stringBuilder });
    } 
  }
  
  private void processPhiInsn(PhiInsn paramPhiInsn) {
    RegisterSpec registerSpec = paramPhiInsn.getResult();
    int i = registerSpec.getReg();
    int j = registerSpec.getCategory();
    RegisterSpecList registerSpecList = paramPhiInsn.getSources();
    int k = registerSpecList.size();
    ArrayList<RegisterSpec> arrayList = new ArrayList();
    Multiset multiset = new Multiset(k + 1);
    if (this.ssaRegsMapped.get(i)) {
      multiset.add(this.mapper.oldToNew(i));
    } else {
      arrayList.add(registerSpec);
    } 
    for (i = 0; i < k; i++) {
      registerSpec = registerSpecList.get(i);
      registerSpec = this.ssaMeth.getDefinitionForRegister(registerSpec.getReg()).getResult();
      int m = registerSpec.getReg();
      if (this.ssaRegsMapped.get(m)) {
        multiset.add(this.mapper.oldToNew(m));
      } else {
        arrayList.add(registerSpec);
      } 
    } 
    for (i = 0; i < multiset.getSize(); i++)
      tryMapRegs(arrayList, multiset.getAndRemoveHighestCount(), j, false); 
    for (i = findNextUnreservedRopReg(this.paramRangeEnd, j); !tryMapRegs(arrayList, i, j, false); i = findNextUnreservedRopReg(i + 1, j));
  }
  
  private boolean rangeContainsReserved(int paramInt1, int paramInt2) {
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++) {
      if (this.reservedRopRegs.get(i))
        return true; 
    } 
    return false;
  }
  
  private boolean spansParamRange(int paramInt1, int paramInt2) {
    boolean bool;
    int i = this.paramRangeEnd;
    if (paramInt1 < i && paramInt1 + paramInt2 > i) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean tryMapReg(RegisterSpec paramRegisterSpec, int paramInt1, int paramInt2) {
    if (paramRegisterSpec.getCategory() <= paramInt2 && !this.ssaRegsMapped.get(paramRegisterSpec.getReg()) && canMapReg(paramRegisterSpec, paramInt1)) {
      addMapping(paramRegisterSpec, paramInt1);
      return true;
    } 
    return false;
  }
  
  private boolean tryMapRegs(ArrayList<RegisterSpec> paramArrayList, int paramInt1, int paramInt2, boolean paramBoolean) {
    Iterator<RegisterSpec> iterator = paramArrayList.iterator();
    boolean bool = false;
    while (iterator.hasNext()) {
      boolean bool2;
      RegisterSpec registerSpec = iterator.next();
      if (this.ssaRegsMapped.get(registerSpec.getReg()))
        continue; 
      boolean bool1 = tryMapReg(registerSpec, paramInt1, paramInt2);
      if (!bool1 || bool) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      bool = bool2;
      if (bool1) {
        bool = bool2;
        if (paramBoolean) {
          markReserved(paramInt1, registerSpec.getCategory());
          bool = bool2;
        } 
      } 
    } 
    return bool ^ true;
  }
  
  public RegisterMapper allocateRegisters() {
    analyzeInstructions();
    handleLocalAssociatedParams();
    handleUnassociatedParameters();
    handleInvokeRangeInsns();
    handleLocalAssociatedOther();
    handleCheckCastResults();
    handlePhiInsns();
    handleNormalUnassociated();
    return (RegisterMapper)this.mapper;
  }
  
  RegisterSpecList ssaSetToSpecs(IntSet paramIntSet) {
    RegisterSpecList registerSpecList = new RegisterSpecList(paramIntSet.elements());
    IntIterator intIterator = paramIntSet.iterator();
    for (byte b = 0; intIterator.hasNext(); b++)
      registerSpecList.set(b, getDefinitionSpecForSsaReg(intIterator.next())); 
    return registerSpecList;
  }
  
  public boolean wantsParamsMovedHigh() {
    return true;
  }
  
  private enum Alignment {
    EVEN {
      int nextClearBit(BitSet param2BitSet, int param2Int) {
        for (param2Int = param2BitSet.nextClearBit(param2Int); !FirstFitLocalCombiningAllocator.isEven(param2Int); param2Int = param2BitSet.nextClearBit(param2Int + 1));
        return param2Int;
      }
    },
    ODD {
      int nextClearBit(BitSet param2BitSet, int param2Int) {
        for (param2Int = param2BitSet.nextClearBit(param2Int); FirstFitLocalCombiningAllocator.isEven(param2Int); param2Int = param2BitSet.nextClearBit(param2Int + 1));
        return param2Int;
      }
    },
    UNSPECIFIED;
    
    static {
      null  = new null("UNSPECIFIED", 2);
      UNSPECIFIED = ;
      $VALUES = new Alignment[] { EVEN, ODD,  };
    }
    
    abstract int nextClearBit(BitSet param1BitSet, int param1Int);
  }
  
  enum null {
    int nextClearBit(BitSet param1BitSet, int param1Int) {
      for (param1Int = param1BitSet.nextClearBit(param1Int); !FirstFitLocalCombiningAllocator.isEven(param1Int); param1Int = param1BitSet.nextClearBit(param1Int + 1));
      return param1Int;
    }
  }
  
  enum null {
    int nextClearBit(BitSet param1BitSet, int param1Int) {
      for (param1Int = param1BitSet.nextClearBit(param1Int); FirstFitLocalCombiningAllocator.isEven(param1Int); param1Int = param1BitSet.nextClearBit(param1Int + 1));
      return param1Int;
    }
  }
  
  enum null {
    int nextClearBit(BitSet param1BitSet, int param1Int) {
      return param1BitSet.nextClearBit(param1Int);
    }
  }
  
  private static class Multiset {
    private final int[] count;
    
    private final int[] reg;
    
    private int size;
    
    public Multiset(int param1Int) {
      this.reg = new int[param1Int];
      this.count = new int[param1Int];
      this.size = 0;
    }
    
    public void add(int param1Int) {
      byte b = 0;
      while (true) {
        int i = this.size;
        if (b < i) {
          if (this.reg[b] == param1Int) {
            int[] arrayOfInt = this.count;
            arrayOfInt[b] = arrayOfInt[b] + 1;
            return;
          } 
          b++;
          continue;
        } 
        this.reg[i] = param1Int;
        this.count[i] = 1;
        this.size = i + 1;
        return;
      } 
    }
    
    public int getAndRemoveHighestCount() {
      byte b = -1;
      int i = -1;
      byte b1 = 0;
      int j;
      for (j = 0; b1 < this.size; j = k) {
        int[] arrayOfInt = this.count;
        int k = j;
        if (j < arrayOfInt[b1]) {
          i = this.reg[b1];
          k = arrayOfInt[b1];
          b = b1;
        } 
        b1++;
      } 
      this.count[b] = 0;
      return i;
    }
    
    public int getSize() {
      return this.size;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\back\FirstFitLocalCombiningAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */