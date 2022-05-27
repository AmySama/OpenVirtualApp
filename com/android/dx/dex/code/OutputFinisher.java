package com.android.dx.dex.code;

import com.android.dex.DexException;
import com.android.dx.dex.DexOptions;
import com.android.dx.rop.code.LocalItem;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.RegisterSpecSet;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstMemberRef;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.ssa.BasicRegisterMapper;
import com.android.dx.ssa.RegisterMapper;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;

public final class OutputFinisher {
  private final DexOptions dexOptions;
  
  private boolean hasAnyLocalInfo;
  
  private boolean hasAnyPositionInfo;
  
  private ArrayList<DalvInsn> insns;
  
  private final int paramSize;
  
  private int reservedCount;
  
  private int reservedParameterCount;
  
  private final int unreservedRegCount;
  
  public OutputFinisher(DexOptions paramDexOptions, int paramInt1, int paramInt2, int paramInt3) {
    this.dexOptions = paramDexOptions;
    this.unreservedRegCount = paramInt2;
    this.insns = new ArrayList<DalvInsn>(paramInt1);
    this.reservedCount = -1;
    this.hasAnyPositionInfo = false;
    this.hasAnyLocalInfo = false;
    this.paramSize = paramInt3;
  }
  
  private static void addConstants(HashSet<Constant> paramHashSet, DalvInsn paramDalvInsn) {
    if (paramDalvInsn instanceof CstInsn) {
      paramHashSet.add(((CstInsn)paramDalvInsn).getConstant());
    } else {
      boolean bool = paramDalvInsn instanceof MultiCstInsn;
      int i = 0;
      int j = 0;
      if (bool) {
        paramDalvInsn = paramDalvInsn;
        for (i = j; i < paramDalvInsn.getNumberOfConstants(); i++)
          paramHashSet.add(paramDalvInsn.getConstant(i)); 
      } else {
        RegisterSpecSet registerSpecSet;
        if (paramDalvInsn instanceof LocalSnapshot) {
          registerSpecSet = ((LocalSnapshot)paramDalvInsn).getLocals();
          j = registerSpecSet.size();
          while (i < j) {
            addConstants(paramHashSet, registerSpecSet.get(i));
            i++;
          } 
        } else if (registerSpecSet instanceof LocalStart) {
          addConstants(paramHashSet, ((LocalStart)registerSpecSet).getLocal());
        } 
      } 
    } 
  }
  
  private static void addConstants(HashSet<Constant> paramHashSet, RegisterSpec paramRegisterSpec) {
    if (paramRegisterSpec == null)
      return; 
    LocalItem localItem = paramRegisterSpec.getLocalItem();
    CstString cstString2 = localItem.getName();
    CstString cstString1 = localItem.getSignature();
    Type type = paramRegisterSpec.getType();
    if (type != Type.KNOWN_NULL) {
      paramHashSet.add(CstType.intern(type));
    } else {
      paramHashSet.add(CstType.intern(Type.OBJECT));
    } 
    if (cstString2 != null)
      paramHashSet.add(cstString2); 
    if (cstString1 != null)
      paramHashSet.add(cstString1); 
  }
  
  private void addReservedParameters(int paramInt) {
    shiftParameters(paramInt);
    this.reservedParameterCount += paramInt;
  }
  
  private void addReservedRegisters(int paramInt) {
    shiftAllRegisters(paramInt);
    this.reservedCount += paramInt;
  }
  
  private void align64bits(Dop[] paramArrayOfDop) {
    do {
      int i = this.unreservedRegCount;
      int j = this.reservedCount;
      int k = this.reservedParameterCount;
      int m = this.paramSize;
      Iterator<DalvInsn> iterator = this.insns.iterator();
      int n = 0;
      int i1 = 0;
      int i2 = 0;
      int i3 = 0;
      label40: while (iterator.hasNext()) {
        RegisterSpecList registerSpecList = ((DalvInsn)iterator.next()).getRegisters();
        byte b = 0;
        int i4 = i3;
        int i5 = i2;
        int i6 = i1;
        int i7 = n;
        while (true) {
          n = i7;
          i1 = i6;
          i2 = i5;
          i3 = i4;
          if (b < registerSpecList.size()) {
            RegisterSpec registerSpec = registerSpecList.get(b);
            i3 = i7;
            n = i6;
            i2 = i5;
            i1 = i4;
            if (registerSpec.isCategory2()) {
              if (registerSpec.getReg() >= i + j + k - m) {
                i3 = 1;
              } else {
                i3 = 0;
              } 
              if (registerSpec.isEvenRegister()) {
                if (i3) {
                  n = i6 + 1;
                  i3 = i7;
                  i2 = i5;
                  i1 = i4;
                } else {
                  i1 = i4 + 1;
                  i3 = i7;
                  n = i6;
                  i2 = i5;
                } 
              } else if (i3 != 0) {
                i3 = i7 + 1;
                n = i6;
                i2 = i5;
                i1 = i4;
              } else {
                i2 = i5 + 1;
                i1 = i4;
                n = i6;
                i3 = i7;
              } 
            } 
            b++;
            i7 = i3;
            i6 = n;
            i5 = i2;
            i4 = i1;
            continue;
          } 
          continue label40;
        } 
      } 
      if (n > i1 && i2 > i3) {
        addReservedRegisters(1);
      } else if (n > i1) {
        addReservedParameters(1);
      } else if (i2 > i3) {
        addReservedRegisters(1);
        if (this.paramSize != 0 && i1 > n)
          addReservedParameters(1); 
      } else {
        break;
      } 
    } while (reserveRegisters(paramArrayOfDop));
  }
  
  private void assignAddresses() {
    int i = this.insns.size();
    byte b = 0;
    int j = 0;
    while (b < i) {
      DalvInsn dalvInsn = this.insns.get(b);
      dalvInsn.setAddress(j);
      j += dalvInsn.codeSize();
      b++;
    } 
  }
  
  private void assignAddressesAndFixBranches() {
    do {
      assignAddresses();
    } while (fixBranches());
  }
  
  private static void assignIndices(CstInsn paramCstInsn, DalvCode.AssignIndicesCallback paramAssignIndicesCallback) {
    Constant constant = paramCstInsn.getConstant();
    int i = paramAssignIndicesCallback.getIndex(constant);
    if (i >= 0)
      paramCstInsn.setIndex(i); 
    if (constant instanceof CstMemberRef) {
      i = paramAssignIndicesCallback.getIndex((Constant)((CstMemberRef)constant).getDefiningClass());
      if (i >= 0)
        paramCstInsn.setClassIndex(i); 
    } 
  }
  
  private static void assignIndices(MultiCstInsn paramMultiCstInsn, DalvCode.AssignIndicesCallback paramAssignIndicesCallback) {
    for (byte b = 0; b < paramMultiCstInsn.getNumberOfConstants(); b++) {
      Constant constant = paramMultiCstInsn.getConstant(b);
      paramMultiCstInsn.setIndex(b, paramAssignIndicesCallback.getIndex(constant));
      if (constant instanceof CstMemberRef)
        paramMultiCstInsn.setClassIndex(paramAssignIndicesCallback.getIndex((Constant)((CstMemberRef)constant).getDefiningClass())); 
    } 
  }
  
  private int calculateReservedCount(Dop[] paramArrayOfDop) {
    int i = this.insns.size();
    int j = this.reservedCount;
    for (byte b = 0; b < i; b++) {
      int k;
      DalvInsn dalvInsn = this.insns.get(b);
      Dop dop1 = paramArrayOfDop[b];
      Dop dop2 = findOpcodeForInsn(dalvInsn, dop1);
      if (dop2 == null) {
        int m = dalvInsn.getMinimumRegisterRequirement(findExpandedOpcodeForInsn(dalvInsn).getFormat().compatibleRegs(dalvInsn));
        k = j;
        if (m > j)
          k = m; 
      } else {
        k = j;
        if (dop1 == dop2)
          continue; 
      } 
      paramArrayOfDop[b] = dop2;
      j = k;
      continue;
    } 
    return j;
  }
  
  private Dop findExpandedOpcodeForInsn(DalvInsn paramDalvInsn) {
    Dop dop = findOpcodeForInsn(paramDalvInsn.getLowRegVersion(), paramDalvInsn.getOpcode());
    if (dop != null)
      return dop; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No expanded opcode for ");
    stringBuilder.append(paramDalvInsn);
    throw new DexException(stringBuilder.toString());
  }
  
  private Dop findOpcodeForInsn(DalvInsn paramDalvInsn, Dop paramDop) {
    while (paramDop != null && (!paramDop.getFormat().isCompatible(paramDalvInsn) || (this.dexOptions.forceJumbo && paramDop.getOpcode() == 26)))
      paramDop = Dops.getNextOrNull(paramDop, this.dexOptions); 
    return paramDop;
  }
  
  private boolean fixBranches() {
    // Byte code:
    //   0: aload_0
    //   1: getfield insns : Ljava/util/ArrayList;
    //   4: invokevirtual size : ()I
    //   7: istore_1
    //   8: iconst_0
    //   9: istore_2
    //   10: iconst_0
    //   11: istore_3
    //   12: iload_2
    //   13: iload_1
    //   14: if_icmpge -> 240
    //   17: aload_0
    //   18: getfield insns : Ljava/util/ArrayList;
    //   21: iload_2
    //   22: invokevirtual get : (I)Ljava/lang/Object;
    //   25: checkcast com/android/dx/dex/code/DalvInsn
    //   28: astore #4
    //   30: aload #4
    //   32: instanceof com/android/dx/dex/code/TargetInsn
    //   35: ifne -> 41
    //   38: goto -> 208
    //   41: aload #4
    //   43: invokevirtual getOpcode : ()Lcom/android/dx/dex/code/Dop;
    //   46: astore #5
    //   48: aload #4
    //   50: checkcast com/android/dx/dex/code/TargetInsn
    //   53: astore #6
    //   55: aload #5
    //   57: invokevirtual getFormat : ()Lcom/android/dx/dex/code/InsnFormat;
    //   60: aload #6
    //   62: invokevirtual branchFits : (Lcom/android/dx/dex/code/TargetInsn;)Z
    //   65: ifeq -> 71
    //   68: goto -> 208
    //   71: aload #5
    //   73: invokevirtual getFamily : ()I
    //   76: bipush #40
    //   78: if_icmpne -> 126
    //   81: aload_0
    //   82: aload #4
    //   84: aload #5
    //   86: invokespecial findOpcodeForInsn : (Lcom/android/dx/dex/code/DalvInsn;Lcom/android/dx/dex/code/Dop;)Lcom/android/dx/dex/code/Dop;
    //   89: astore #6
    //   91: aload #6
    //   93: ifnull -> 115
    //   96: aload_0
    //   97: getfield insns : Ljava/util/ArrayList;
    //   100: iload_2
    //   101: aload #4
    //   103: aload #6
    //   105: invokevirtual withOpcode : (Lcom/android/dx/dex/code/Dop;)Lcom/android/dx/dex/code/DalvInsn;
    //   108: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   111: pop
    //   112: goto -> 206
    //   115: new java/lang/UnsupportedOperationException
    //   118: dup
    //   119: ldc_w 'method too long'
    //   122: invokespecial <init> : (Ljava/lang/String;)V
    //   125: athrow
    //   126: aload_0
    //   127: getfield insns : Ljava/util/ArrayList;
    //   130: astore #4
    //   132: iload_2
    //   133: iconst_1
    //   134: iadd
    //   135: istore #7
    //   137: aload #4
    //   139: iload #7
    //   141: invokevirtual get : (I)Ljava/lang/Object;
    //   144: checkcast com/android/dx/dex/code/CodeAddress
    //   147: astore #5
    //   149: new com/android/dx/dex/code/TargetInsn
    //   152: dup
    //   153: getstatic com/android/dx/dex/code/Dops.GOTO : Lcom/android/dx/dex/code/Dop;
    //   156: aload #6
    //   158: invokevirtual getPosition : ()Lcom/android/dx/rop/code/SourcePosition;
    //   161: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   164: aload #6
    //   166: invokevirtual getTarget : ()Lcom/android/dx/dex/code/CodeAddress;
    //   169: invokespecial <init> : (Lcom/android/dx/dex/code/Dop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpecList;Lcom/android/dx/dex/code/CodeAddress;)V
    //   172: astore #4
    //   174: aload_0
    //   175: getfield insns : Ljava/util/ArrayList;
    //   178: iload_2
    //   179: aload #4
    //   181: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   184: pop
    //   185: aload_0
    //   186: getfield insns : Ljava/util/ArrayList;
    //   189: iload_2
    //   190: aload #6
    //   192: aload #5
    //   194: invokevirtual withNewTargetAndReversed : (Lcom/android/dx/dex/code/CodeAddress;)Lcom/android/dx/dex/code/TargetInsn;
    //   197: invokevirtual add : (ILjava/lang/Object;)V
    //   200: iinc #1, 1
    //   203: iload #7
    //   205: istore_2
    //   206: iconst_1
    //   207: istore_3
    //   208: iinc #2, 1
    //   211: goto -> 12
    //   214: astore #6
    //   216: new java/lang/IllegalStateException
    //   219: dup
    //   220: ldc_w 'unpaired TargetInsn'
    //   223: invokespecial <init> : (Ljava/lang/String;)V
    //   226: athrow
    //   227: astore #6
    //   229: new java/lang/IllegalStateException
    //   232: dup
    //   233: ldc_w 'unpaired TargetInsn (dangling)'
    //   236: invokespecial <init> : (Ljava/lang/String;)V
    //   239: athrow
    //   240: iload_3
    //   241: ireturn
    // Exception table:
    //   from	to	target	type
    //   126	132	227	java/lang/IndexOutOfBoundsException
    //   126	132	214	java/lang/ClassCastException
    //   137	149	227	java/lang/IndexOutOfBoundsException
    //   137	149	214	java/lang/ClassCastException
  }
  
  private static boolean hasLocalInfo(DalvInsn paramDalvInsn) {
    RegisterSpecSet registerSpecSet;
    if (paramDalvInsn instanceof LocalSnapshot) {
      registerSpecSet = ((LocalSnapshot)paramDalvInsn).getLocals();
      int i = registerSpecSet.size();
      for (byte b = 0; b < i; b++) {
        if (hasLocalInfo(registerSpecSet.get(b)))
          return true; 
      } 
    } else if (registerSpecSet instanceof LocalStart && hasLocalInfo(((LocalStart)registerSpecSet).getLocal())) {
      return true;
    } 
    return false;
  }
  
  private static boolean hasLocalInfo(RegisterSpec paramRegisterSpec) {
    boolean bool;
    if (paramRegisterSpec != null && paramRegisterSpec.getLocalItem().getName() != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private Dop[] makeOpcodesArray() {
    int i = this.insns.size();
    Dop[] arrayOfDop = new Dop[i];
    for (byte b = 0; b < i; b++)
      arrayOfDop[b] = ((DalvInsn)this.insns.get(b)).getOpcode(); 
    return arrayOfDop;
  }
  
  private void massageInstructions(Dop[] paramArrayOfDop) {
    if (this.reservedCount == 0) {
      int i = this.insns.size();
      for (byte b = 0; b < i; b++) {
        DalvInsn dalvInsn = this.insns.get(b);
        Dop dop1 = dalvInsn.getOpcode();
        Dop dop2 = paramArrayOfDop[b];
        if (dop1 != dop2)
          this.insns.set(b, dalvInsn.withOpcode(dop2)); 
      } 
    } else {
      this.insns = performExpansion(paramArrayOfDop);
    } 
  }
  
  private ArrayList<DalvInsn> performExpansion(Dop[] paramArrayOfDop) {
    int i = this.insns.size();
    ArrayList<DalvInsn> arrayList = new ArrayList(i * 2);
    ArrayList<CodeAddress> arrayList1 = new ArrayList();
    for (byte b = 0; b < i; b++) {
      DalvInsn dalvInsn3;
      DalvInsn dalvInsn1 = this.insns.get(b);
      Dop dop1 = dalvInsn1.getOpcode();
      Dop dop2 = paramArrayOfDop[b];
      DalvInsn dalvInsn2 = null;
      if (dop2 != null) {
        dalvInsn3 = null;
      } else {
        dop2 = findExpandedOpcodeForInsn(dalvInsn1);
        BitSet bitSet = dop2.getFormat().compatibleRegs(dalvInsn1);
        dalvInsn2 = dalvInsn1.expandedPrefix(bitSet);
        dalvInsn3 = dalvInsn1.expandedSuffix(bitSet);
        dalvInsn1 = dalvInsn1.expandedVersion(bitSet);
      } 
      if (dalvInsn1 instanceof CodeAddress) {
        CodeAddress codeAddress = (CodeAddress)dalvInsn1;
        if (codeAddress.getBindsClosely()) {
          arrayList1.add(codeAddress);
          continue;
        } 
      } 
      if (dalvInsn2 != null)
        arrayList.add(dalvInsn2); 
      if (!(dalvInsn1 instanceof ZeroSizeInsn) && arrayList1.size() > 0) {
        Iterator<CodeAddress> iterator = arrayList1.iterator();
        while (iterator.hasNext())
          arrayList.add(iterator.next()); 
        arrayList1.clear();
      } 
      dalvInsn2 = dalvInsn1;
      if (dop2 != dop1)
        dalvInsn2 = dalvInsn1.withOpcode(dop2); 
      arrayList.add(dalvInsn2);
      if (dalvInsn3 != null)
        arrayList.add(dalvInsn3); 
      continue;
    } 
    return arrayList;
  }
  
  private boolean reserveRegisters(Dop[] paramArrayOfDop) {
    int i = this.reservedCount;
    int j = i;
    if (i < 0)
      j = 0; 
    boolean bool = false;
    while (true) {
      int k = calculateReservedCount(paramArrayOfDop);
      if (j >= k) {
        this.reservedCount = j;
        return bool;
      } 
      int m = this.insns.size();
      for (i = 0; i < m; i++) {
        DalvInsn dalvInsn = this.insns.get(i);
        if (!(dalvInsn instanceof CodeAddress))
          this.insns.set(i, dalvInsn.withRegisterOffset(k - j)); 
      } 
      bool = true;
      j = k;
    } 
  }
  
  private void shiftAllRegisters(int paramInt) {
    int i = this.insns.size();
    for (byte b = 0; b < i; b++) {
      DalvInsn dalvInsn = this.insns.get(b);
      if (!(dalvInsn instanceof CodeAddress))
        this.insns.set(b, dalvInsn.withRegisterOffset(paramInt)); 
    } 
  }
  
  private void shiftParameters(int paramInt) {
    byte b3;
    int i = this.insns.size();
    int j = this.unreservedRegCount + this.reservedCount + this.reservedParameterCount;
    int k = this.paramSize;
    BasicRegisterMapper basicRegisterMapper = new BasicRegisterMapper(j);
    byte b1 = 0;
    byte b2 = 0;
    while (true) {
      b3 = b1;
      if (b2 < j) {
        if (b2 >= j - k) {
          basicRegisterMapper.addMapping(b2, b2 + paramInt, 1);
        } else {
          basicRegisterMapper.addMapping(b2, b2, 1);
        } 
        b2++;
        continue;
      } 
      break;
    } 
    while (b3 < i) {
      DalvInsn dalvInsn = this.insns.get(b3);
      if (!(dalvInsn instanceof CodeAddress))
        this.insns.set(b3, dalvInsn.withMapper((RegisterMapper)basicRegisterMapper)); 
      b3++;
    } 
  }
  
  private void updateInfo(DalvInsn paramDalvInsn) {
    if (!this.hasAnyPositionInfo && paramDalvInsn.getPosition().getLine() >= 0)
      this.hasAnyPositionInfo = true; 
    if (!this.hasAnyLocalInfo && hasLocalInfo(paramDalvInsn))
      this.hasAnyLocalInfo = true; 
  }
  
  public void add(DalvInsn paramDalvInsn) {
    this.insns.add(paramDalvInsn);
    updateInfo(paramDalvInsn);
  }
  
  public void assignIndices(DalvCode.AssignIndicesCallback paramAssignIndicesCallback) {
    for (DalvInsn dalvInsn : this.insns) {
      if (dalvInsn instanceof CstInsn) {
        assignIndices((CstInsn)dalvInsn, paramAssignIndicesCallback);
        continue;
      } 
      if (dalvInsn instanceof MultiCstInsn)
        assignIndices((MultiCstInsn)dalvInsn, paramAssignIndicesCallback); 
    } 
  }
  
  public DalvInsnList finishProcessingAndGetList() {
    if (this.reservedCount < 0) {
      Dop[] arrayOfDop = makeOpcodesArray();
      reserveRegisters(arrayOfDop);
      if (this.dexOptions.ALIGN_64BIT_REGS_IN_OUTPUT_FINISHER)
        align64bits(arrayOfDop); 
      massageInstructions(arrayOfDop);
      assignAddressesAndFixBranches();
      return DalvInsnList.makeImmutable(this.insns, this.reservedCount + this.unreservedRegCount + this.reservedParameterCount);
    } 
    throw new UnsupportedOperationException("already processed");
  }
  
  public HashSet<Constant> getAllConstants() {
    HashSet<Constant> hashSet = new HashSet(20);
    Iterator<DalvInsn> iterator = this.insns.iterator();
    while (iterator.hasNext())
      addConstants(hashSet, iterator.next()); 
    return hashSet;
  }
  
  public boolean hasAnyLocalInfo() {
    return this.hasAnyLocalInfo;
  }
  
  public boolean hasAnyPositionInfo() {
    return this.hasAnyPositionInfo;
  }
  
  public void insert(int paramInt, DalvInsn paramDalvInsn) {
    this.insns.add(paramInt, paramDalvInsn);
    updateInfo(paramDalvInsn);
  }
  
  public void reverseBranch(int paramInt, CodeAddress paramCodeAddress) {
    paramInt = this.insns.size() - paramInt - 1;
    try {
      TargetInsn targetInsn = (TargetInsn)this.insns.get(paramInt);
      this.insns.set(paramInt, targetInsn.withNewTargetAndReversed(paramCodeAddress));
      return;
    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
      throw new IllegalArgumentException("too few instructions");
    } catch (ClassCastException classCastException) {
      throw new IllegalArgumentException("non-reversible instruction");
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\OutputFinisher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */