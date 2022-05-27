package com.android.dx.cf.code;

import com.android.dx.cf.iface.MethodList;
import com.android.dx.dex.DexOptions;
import com.android.dx.rop.code.BasicBlock;
import com.android.dx.rop.code.BasicBlockList;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.InsnList;
import com.android.dx.rop.code.PlainCstInsn;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.code.ThrowingCstInsn;
import com.android.dx.rop.code.ThrowingInsn;
import com.android.dx.rop.code.TranslationAdvice;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.Bits;
import com.android.dx.util.Hex;
import com.android.dx.util.IntList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class Ropper {
  private static final int PARAM_ASSIGNMENT = -1;
  
  private static final int RETURN = -2;
  
  private static final int SPECIAL_LABEL_COUNT = 7;
  
  private static final int SYNCH_CATCH_1 = -6;
  
  private static final int SYNCH_CATCH_2 = -7;
  
  private static final int SYNCH_RETURN = -3;
  
  private static final int SYNCH_SETUP_1 = -4;
  
  private static final int SYNCH_SETUP_2 = -5;
  
  private final ByteBlockList blocks;
  
  private final CatchInfo[] catchInfos;
  
  private final ExceptionSetupLabelAllocator exceptionSetupLabelAllocator;
  
  private boolean hasSubroutines;
  
  private final RopperMachine machine;
  
  private final int maxLabel;
  
  private final int maxLocals;
  
  private final ConcreteMethod method;
  
  private final ArrayList<BasicBlock> result;
  
  private final ArrayList<IntList> resultSubroutines;
  
  private final Simulator sim;
  
  private final Frame[] startFrames;
  
  private final Subroutine[] subroutines;
  
  private boolean synchNeedsExceptionHandler;
  
  private Ropper(ConcreteMethod paramConcreteMethod, TranslationAdvice paramTranslationAdvice, MethodList paramMethodList, DexOptions paramDexOptions) {
    if (paramConcreteMethod != null) {
      if (paramTranslationAdvice != null) {
        this.method = paramConcreteMethod;
        ByteBlockList byteBlockList = BasicBlocker.identifyBlocks(paramConcreteMethod);
        this.blocks = byteBlockList;
        this.maxLabel = byteBlockList.getMaxLabel();
        this.maxLocals = paramConcreteMethod.getMaxLocals();
        this.machine = new RopperMachine(this, paramConcreteMethod, paramTranslationAdvice, paramMethodList);
        this.sim = new Simulator(this.machine, paramConcreteMethod, paramDexOptions);
        int i = this.maxLabel;
        this.startFrames = new Frame[i];
        this.subroutines = new Subroutine[i];
        this.result = new ArrayList<BasicBlock>(this.blocks.size() * 2 + 10);
        this.resultSubroutines = new ArrayList<IntList>(this.blocks.size() * 2 + 10);
        this.catchInfos = new CatchInfo[this.maxLabel];
        this.synchNeedsExceptionHandler = false;
        this.startFrames[0] = new Frame(this.maxLocals, paramConcreteMethod.getMaxStack());
        this.exceptionSetupLabelAllocator = new ExceptionSetupLabelAllocator();
        return;
      } 
      throw new NullPointerException("advice == null");
    } 
    throw new NullPointerException("method == null");
  }
  
  private void addBlock(BasicBlock paramBasicBlock, IntList paramIntList) {
    if (paramBasicBlock != null) {
      this.result.add(paramBasicBlock);
      paramIntList.throwIfMutable();
      this.resultSubroutines.add(paramIntList);
      return;
    } 
    throw new NullPointerException("block == null");
  }
  
  private void addExceptionSetupBlocks() {
    int i = this.catchInfos.length;
    for (byte b = 0; b < i; b++) {
      CatchInfo catchInfo = this.catchInfos[b];
      if (catchInfo != null)
        for (ExceptionHandlerSetup exceptionHandlerSetup : catchInfo.getSetups()) {
          SourcePosition sourcePosition = labelToBlock(b).getFirstInsn().getPosition();
          InsnList insnList = new InsnList(2);
          insnList.set(0, (Insn)new PlainInsn(Rops.opMoveException((TypeBearer)exceptionHandlerSetup.getCaughtType()), sourcePosition, RegisterSpec.make(this.maxLocals, (TypeBearer)exceptionHandlerSetup.getCaughtType()), RegisterSpecList.EMPTY));
          insnList.set(1, (Insn)new PlainInsn(Rops.GOTO, sourcePosition, null, RegisterSpecList.EMPTY));
          insnList.setImmutable();
          addBlock(new BasicBlock(exceptionHandlerSetup.getLabel(), insnList, IntList.makeImmutable(b), b), this.startFrames[b].getSubroutines());
        }  
    } 
  }
  
  private boolean addOrReplaceBlock(BasicBlock paramBasicBlock, IntList paramIntList) {
    if (paramBasicBlock != null) {
      boolean bool;
      int i = labelToResultIndex(paramBasicBlock.getLabel());
      if (i < 0) {
        bool = false;
      } else {
        removeBlockAndSpecialSuccessors(i);
        bool = true;
      } 
      this.result.add(paramBasicBlock);
      paramIntList.throwIfMutable();
      this.resultSubroutines.add(paramIntList);
      return bool;
    } 
    throw new NullPointerException("block == null");
  }
  
  private boolean addOrReplaceBlockNoDelete(BasicBlock paramBasicBlock, IntList paramIntList) {
    if (paramBasicBlock != null) {
      boolean bool;
      int i = labelToResultIndex(paramBasicBlock.getLabel());
      if (i < 0) {
        bool = false;
      } else {
        this.result.remove(i);
        this.resultSubroutines.remove(i);
        bool = true;
      } 
      this.result.add(paramBasicBlock);
      paramIntList.throwIfMutable();
      this.resultSubroutines.add(paramIntList);
      return bool;
    } 
    throw new NullPointerException("block == null");
  }
  
  private void addReturnBlock() {
    RegisterSpecList registerSpecList;
    Rop rop = this.machine.getReturnOp();
    if (rop == null)
      return; 
    SourcePosition sourcePosition = this.machine.getReturnPosition();
    int i = getSpecialLabel(-2);
    int j = i;
    if (isSynchronized()) {
      InsnList insnList1 = new InsnList(1);
      insnList1.set(0, (Insn)new ThrowingInsn(Rops.MONITOR_EXIT, sourcePosition, RegisterSpecList.make(getSynchReg()), (TypeList)StdTypeList.EMPTY));
      insnList1.setImmutable();
      j = getSpecialLabel(-3);
      addBlock(new BasicBlock(i, insnList1, IntList.makeImmutable(j), j), IntList.EMPTY);
    } 
    InsnList insnList = new InsnList(1);
    TypeList typeList = rop.getSources();
    if (typeList.size() == 0) {
      registerSpecList = RegisterSpecList.EMPTY;
    } else {
      registerSpecList = RegisterSpecList.make(RegisterSpec.make(0, (TypeBearer)registerSpecList.getType(0)));
    } 
    insnList.set(0, (Insn)new PlainInsn(rop, sourcePosition, null, registerSpecList));
    insnList.setImmutable();
    addBlock(new BasicBlock(j, insnList, IntList.EMPTY, -1), IntList.EMPTY);
  }
  
  private void addSetupBlocks() {
    LocalVariableList localVariableList = this.method.getLocalVariables();
    SourcePosition sourcePosition = this.method.makeSourcePosistion(0);
    StdTypeList stdTypeList = this.method.getEffectiveDescriptor().getParameterTypes();
    int i = stdTypeList.size();
    InsnList insnList = new InsnList(i + 1);
    byte b = 0;
    int j = 0;
    while (b < i) {
      RegisterSpec registerSpec;
      Type type = stdTypeList.get(b);
      LocalVariableList.Item item = localVariableList.pcAndIndexToLocal(0, j);
      if (item == null) {
        registerSpec = RegisterSpec.make(j, (TypeBearer)type);
      } else {
        registerSpec = RegisterSpec.makeLocalOptional(j, (TypeBearer)type, registerSpec.getLocalItem());
      } 
      insnList.set(b, (Insn)new PlainCstInsn(Rops.opMoveParam((TypeBearer)type), sourcePosition, registerSpec, RegisterSpecList.EMPTY, (Constant)CstInteger.make(j)));
      j += type.getCategory();
      b++;
    } 
    insnList.set(i, (Insn)new PlainInsn(Rops.GOTO, sourcePosition, null, RegisterSpecList.EMPTY));
    insnList.setImmutable();
    boolean bool = isSynchronized();
    if (bool) {
      j = getSpecialLabel(-4);
    } else {
      j = 0;
    } 
    addBlock(new BasicBlock(getSpecialLabel(-1), insnList, IntList.makeImmutable(j), j), IntList.EMPTY);
    if (bool) {
      RegisterSpec registerSpec = getSynchReg();
      bool = isStatic();
      b = 2;
      if (bool) {
        ThrowingCstInsn throwingCstInsn = new ThrowingCstInsn(Rops.CONST_OBJECT, sourcePosition, RegisterSpecList.EMPTY, (TypeList)StdTypeList.EMPTY, (Constant)this.method.getDefiningClass());
        insnList1 = new InsnList(1);
        insnList1.set(0, (Insn)throwingCstInsn);
      } else {
        insnList1 = new InsnList(2);
        insnList1.set(0, (Insn)new PlainCstInsn(Rops.MOVE_PARAM_OBJECT, sourcePosition, registerSpec, RegisterSpecList.EMPTY, (Constant)CstInteger.VALUE_0));
        insnList1.set(1, (Insn)new PlainInsn(Rops.GOTO, sourcePosition, null, RegisterSpecList.EMPTY));
      } 
      i = getSpecialLabel(-5);
      insnList1.setImmutable();
      addBlock(new BasicBlock(j, insnList1, IntList.makeImmutable(i), i), IntList.EMPTY);
      if (isStatic()) {
        j = b;
      } else {
        j = 1;
      } 
      InsnList insnList1 = new InsnList(j);
      if (isStatic())
        insnList1.set(0, (Insn)new PlainInsn(Rops.opMoveResultPseudo((TypeBearer)registerSpec), sourcePosition, registerSpec, RegisterSpecList.EMPTY)); 
      ThrowingInsn throwingInsn = new ThrowingInsn(Rops.MONITOR_ENTER, sourcePosition, RegisterSpecList.make(registerSpec), (TypeList)StdTypeList.EMPTY);
      insnList1.set(isStatic(), (Insn)throwingInsn);
      insnList1.setImmutable();
      addBlock(new BasicBlock(i, insnList1, IntList.makeImmutable(0), 0), IntList.EMPTY);
    } 
  }
  
  private void addSynchExceptionHandlerBlock() {
    if (!this.synchNeedsExceptionHandler)
      return; 
    SourcePosition sourcePosition = this.method.makeSourcePosistion(0);
    RegisterSpec registerSpec = RegisterSpec.make(0, (TypeBearer)Type.THROWABLE);
    InsnList insnList = new InsnList(2);
    insnList.set(0, (Insn)new PlainInsn(Rops.opMoveException((TypeBearer)Type.THROWABLE), sourcePosition, registerSpec, RegisterSpecList.EMPTY));
    insnList.set(1, (Insn)new ThrowingInsn(Rops.MONITOR_EXIT, sourcePosition, RegisterSpecList.make(getSynchReg()), (TypeList)StdTypeList.EMPTY));
    insnList.setImmutable();
    int i = getSpecialLabel(-7);
    addBlock(new BasicBlock(getSpecialLabel(-6), insnList, IntList.makeImmutable(i), i), IntList.EMPTY);
    insnList = new InsnList(1);
    insnList.set(0, (Insn)new ThrowingInsn(Rops.THROW, sourcePosition, RegisterSpecList.make(registerSpec), (TypeList)StdTypeList.EMPTY));
    insnList.setImmutable();
    addBlock(new BasicBlock(i, insnList, IntList.EMPTY, -1), IntList.EMPTY);
  }
  
  public static RopMethod convert(ConcreteMethod paramConcreteMethod, TranslationAdvice paramTranslationAdvice, MethodList paramMethodList, DexOptions paramDexOptions) {
    try {
      Ropper ropper = new Ropper();
      this(paramConcreteMethod, paramTranslationAdvice, paramMethodList, paramDexOptions);
      ropper.doit();
      return ropper.getRopMethod();
    } catch (SimException simException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("...while working on method ");
      stringBuilder.append(paramConcreteMethod.getNat().toHuman());
      simException.addContext(stringBuilder.toString());
      throw simException;
    } 
  }
  
  private void deleteUnreachableBlocks() {
    final IntList reachableLabels = new IntList(this.result.size());
    this.resultSubroutines.clear();
    forEachNonSubBlockDepthFirst(getSpecialLabel(-1), new BasicBlock.Visitor() {
          public void visitBlock(BasicBlock param1BasicBlock) {
            reachableLabels.add(param1BasicBlock.getLabel());
          }
        });
    intList.sort();
    for (int i = this.result.size() - 1; i >= 0; i--) {
      if (intList.indexOf(((BasicBlock)this.result.get(i)).getLabel()) < 0)
        this.result.remove(i); 
    } 
  }
  
  private void doit() {
    int[] arrayOfInt = Bits.makeBitSet(this.maxLabel);
    Bits.set(arrayOfInt, 0);
    addSetupBlocks();
    setFirstFrame();
    while (true) {
      int i = Bits.findFirst(arrayOfInt, 0);
      if (i < 0) {
        addReturnBlock();
        addSynchExceptionHandlerBlock();
        addExceptionSetupBlocks();
        if (this.hasSubroutines)
          inlineSubroutines(); 
        return;
      } 
      Bits.clear(arrayOfInt, i);
      ByteBlock byteBlock = this.blocks.labelToBlock(i);
      Frame frame = this.startFrames[i];
      try {
        processBlock(byteBlock, frame, arrayOfInt);
      } catch (SimException simException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("...while working on block ");
        stringBuilder.append(Hex.u2(i));
        simException.addContext(stringBuilder.toString());
        throw simException;
      } 
    } 
  }
  
  private InsnList filterMoveReturnAddressInsns(InsnList paramInsnList) {
    int i = paramInsnList.size();
    boolean bool = false;
    int j = 0;
    int k;
    for (k = 0; j < i; k = m) {
      int m = k;
      if (paramInsnList.get(j).getOpcode() != Rops.MOVE_RETURN_ADDRESS)
        m = k + 1; 
      j++;
    } 
    if (k == i)
      return paramInsnList; 
    InsnList insnList = new InsnList(k);
    j = 0;
    k = bool;
    while (k < i) {
      Insn insn = paramInsnList.get(k);
      int m = j;
      if (insn.getOpcode() != Rops.MOVE_RETURN_ADDRESS) {
        insnList.set(j, insn);
        m = j + 1;
      } 
      k++;
      j = m;
    } 
    insnList.setImmutable();
    return insnList;
  }
  
  private void forEachNonSubBlockDepthFirst(int paramInt, BasicBlock.Visitor paramVisitor) {
    forEachNonSubBlockDepthFirst0(labelToBlock(paramInt), paramVisitor, new BitSet(this.maxLabel));
  }
  
  private void forEachNonSubBlockDepthFirst0(BasicBlock paramBasicBlock, BasicBlock.Visitor paramVisitor, BitSet paramBitSet) {
    paramVisitor.visitBlock(paramBasicBlock);
    paramBitSet.set(paramBasicBlock.getLabel());
    IntList intList = paramBasicBlock.getSuccessors();
    int i = intList.size();
    for (byte b = 0; b < i; b++) {
      int j = intList.get(b);
      if (!paramBitSet.get(j) && (!isSubroutineCaller(paramBasicBlock) || b <= 0)) {
        j = labelToResultIndex(j);
        if (j >= 0)
          forEachNonSubBlockDepthFirst0(this.result.get(j), paramVisitor, paramBitSet); 
      } 
    } 
  }
  
  private int getAvailableLabel() {
    int i = getMinimumUnreservedLabel();
    Iterator<BasicBlock> iterator = this.result.iterator();
    while (iterator.hasNext()) {
      int j = ((BasicBlock)iterator.next()).getLabel();
      if (j >= i)
        i = j + 1; 
    } 
    return i;
  }
  
  private int getMinimumUnreservedLabel() {
    return this.maxLabel + this.method.getCatches().size() + 7;
  }
  
  private int getNormalRegCount() {
    return this.maxLocals + this.method.getMaxStack();
  }
  
  private RopMethod getRopMethod() {
    int i = this.result.size();
    BasicBlockList basicBlockList = new BasicBlockList(i);
    for (byte b = 0; b < i; b++)
      basicBlockList.set(b, this.result.get(b)); 
    basicBlockList.setImmutable();
    return new RopMethod(basicBlockList, getSpecialLabel(-1));
  }
  
  private int getSpecialLabel(int paramInt) {
    return this.maxLabel + this.method.getCatches().size() + paramInt;
  }
  
  private RegisterSpec getSynchReg() {
    int i = getNormalRegCount();
    int j = i;
    if (i < 1)
      j = 1; 
    return RegisterSpec.make(j, (TypeBearer)Type.OBJECT);
  }
  
  private void inlineSubroutines() {
    final IntList reachableSubroutineCallerLabels = new IntList(4);
    BasicBlock.Visitor visitor = new BasicBlock.Visitor() {
        public void visitBlock(BasicBlock param1BasicBlock) {
          if (Ropper.this.isSubroutineCaller(param1BasicBlock))
            reachableSubroutineCallerLabels.add(param1BasicBlock.getLabel()); 
        }
      };
    int i = 0;
    forEachNonSubBlockDepthFirst(0, visitor);
    int j = getAvailableLabel();
    ArrayList<IntList> arrayList = new ArrayList(j);
    int k;
    for (k = 0; k < j; k++)
      arrayList.add(null); 
    for (k = 0; k < this.result.size(); k++) {
      BasicBlock basicBlock = this.result.get(k);
      if (basicBlock != null) {
        IntList intList1 = this.resultSubroutines.get(k);
        arrayList.set(basicBlock.getLabel(), intList1);
      } 
    } 
    j = intList.size();
    for (k = i; k < j; k++) {
      i = intList.get(k);
      (new SubroutineInliner(new LabelAllocator(getAvailableLabel()), arrayList)).inlineSubroutineCalledFrom(labelToBlock(i));
    } 
    deleteUnreachableBlocks();
  }
  
  private boolean isStatic() {
    boolean bool;
    if ((this.method.getAccessFlags() & 0x8) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean isSubroutineCaller(BasicBlock paramBasicBlock) {
    IntList intList = paramBasicBlock.getSuccessors();
    int i = intList.size();
    boolean bool1 = false;
    if (i < 2)
      return false; 
    i = intList.get(1);
    Subroutine[] arrayOfSubroutine = this.subroutines;
    boolean bool2 = bool1;
    if (i < arrayOfSubroutine.length) {
      bool2 = bool1;
      if (arrayOfSubroutine[i] != null)
        bool2 = true; 
    } 
    return bool2;
  }
  
  private boolean isSynchronized() {
    boolean bool;
    if ((this.method.getAccessFlags() & 0x20) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private BasicBlock labelToBlock(int paramInt) {
    int i = labelToResultIndex(paramInt);
    if (i >= 0)
      return this.result.get(i); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("no such label ");
    stringBuilder.append(Hex.u2(paramInt));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private int labelToResultIndex(int paramInt) {
    int i = this.result.size();
    for (byte b = 0; b < i; b++) {
      if (((BasicBlock)this.result.get(b)).getLabel() == paramInt)
        return b; 
    } 
    return -1;
  }
  
  private void mergeAndWorkAsNecessary(int paramInt1, int paramInt2, Subroutine paramSubroutine, Frame paramFrame, int[] paramArrayOfint) {
    Frame frame1;
    Frame[] arrayOfFrame = this.startFrames;
    Frame frame2 = arrayOfFrame[paramInt1];
    if (frame2 != null) {
      if (paramSubroutine != null) {
        frame1 = frame2.mergeWithSubroutineCaller(paramFrame, paramSubroutine.getStartBlock(), paramInt2);
      } else {
        frame1 = frame2.mergeWith(paramFrame);
      } 
      if (frame1 != frame2) {
        this.startFrames[paramInt1] = frame1;
        Bits.set(paramArrayOfint, paramInt1);
      } 
    } else {
      if (frame1 != null) {
        arrayOfFrame[paramInt1] = paramFrame.makeNewSubroutineStartFrame(paramInt1, paramInt2);
      } else {
        arrayOfFrame[paramInt1] = paramFrame;
      } 
      Bits.set(paramArrayOfint, paramInt1);
    } 
  }
  
  private void processBlock(ByteBlock paramByteBlock, Frame paramFrame, int[] paramArrayOfint) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getCatches : ()Lcom/android/dx/cf/code/ByteCatchList;
    //   4: astore #4
    //   6: aload_0
    //   7: getfield machine : Lcom/android/dx/cf/code/RopperMachine;
    //   10: aload #4
    //   12: invokevirtual toRopCatchList : ()Lcom/android/dx/rop/type/TypeList;
    //   15: invokevirtual startBlock : (Lcom/android/dx/rop/type/TypeList;)V
    //   18: aload_2
    //   19: invokevirtual copy : ()Lcom/android/dx/cf/code/Frame;
    //   22: astore #5
    //   24: aload_0
    //   25: getfield sim : Lcom/android/dx/cf/code/Simulator;
    //   28: aload_1
    //   29: aload #5
    //   31: invokevirtual simulate : (Lcom/android/dx/cf/code/ByteBlock;Lcom/android/dx/cf/code/Frame;)V
    //   34: aload #5
    //   36: invokevirtual setImmutable : ()V
    //   39: aload_0
    //   40: getfield machine : Lcom/android/dx/cf/code/RopperMachine;
    //   43: invokevirtual getExtraBlockCount : ()I
    //   46: istore #6
    //   48: aload_0
    //   49: getfield machine : Lcom/android/dx/cf/code/RopperMachine;
    //   52: invokevirtual getInsns : ()Ljava/util/ArrayList;
    //   55: astore #7
    //   57: aload #7
    //   59: invokevirtual size : ()I
    //   62: istore #8
    //   64: aload #4
    //   66: invokevirtual size : ()I
    //   69: istore #9
    //   71: aload_1
    //   72: invokevirtual getSuccessors : ()Lcom/android/dx/util/IntList;
    //   75: astore_2
    //   76: aload_0
    //   77: getfield machine : Lcom/android/dx/cf/code/RopperMachine;
    //   80: invokevirtual hasJsr : ()Z
    //   83: ifeq -> 151
    //   86: aload_2
    //   87: iconst_1
    //   88: invokevirtual get : (I)I
    //   91: istore #10
    //   93: aload_0
    //   94: getfield subroutines : [Lcom/android/dx/cf/code/Ropper$Subroutine;
    //   97: astore #11
    //   99: aload #11
    //   101: iload #10
    //   103: aaload
    //   104: ifnonnull -> 122
    //   107: aload #11
    //   109: iload #10
    //   111: new com/android/dx/cf/code/Ropper$Subroutine
    //   114: dup
    //   115: aload_0
    //   116: iload #10
    //   118: invokespecial <init> : (Lcom/android/dx/cf/code/Ropper;I)V
    //   121: aastore
    //   122: aload_0
    //   123: getfield subroutines : [Lcom/android/dx/cf/code/Ropper$Subroutine;
    //   126: iload #10
    //   128: aaload
    //   129: aload_1
    //   130: invokevirtual getLabel : ()I
    //   133: invokevirtual addCallerBlock : (I)V
    //   136: aload_0
    //   137: getfield subroutines : [Lcom/android/dx/cf/code/Ropper$Subroutine;
    //   140: iload #10
    //   142: aaload
    //   143: astore #11
    //   145: iconst_1
    //   146: istore #10
    //   148: goto -> 276
    //   151: aload_0
    //   152: getfield machine : Lcom/android/dx/cf/code/RopperMachine;
    //   155: invokevirtual hasRet : ()Z
    //   158: ifeq -> 250
    //   161: aload_0
    //   162: getfield machine : Lcom/android/dx/cf/code/RopperMachine;
    //   165: invokevirtual getReturnAddress : ()Lcom/android/dx/cf/code/ReturnAddress;
    //   168: invokevirtual getSubroutineAddress : ()I
    //   171: istore #10
    //   173: aload_0
    //   174: getfield subroutines : [Lcom/android/dx/cf/code/Ropper$Subroutine;
    //   177: astore_2
    //   178: aload_2
    //   179: iload #10
    //   181: aaload
    //   182: ifnonnull -> 206
    //   185: aload_2
    //   186: iload #10
    //   188: new com/android/dx/cf/code/Ropper$Subroutine
    //   191: dup
    //   192: aload_0
    //   193: iload #10
    //   195: aload_1
    //   196: invokevirtual getLabel : ()I
    //   199: invokespecial <init> : (Lcom/android/dx/cf/code/Ropper;II)V
    //   202: aastore
    //   203: goto -> 217
    //   206: aload_2
    //   207: iload #10
    //   209: aaload
    //   210: aload_1
    //   211: invokevirtual getLabel : ()I
    //   214: invokevirtual addRetBlock : (I)V
    //   217: aload_0
    //   218: getfield subroutines : [Lcom/android/dx/cf/code/Ropper$Subroutine;
    //   221: iload #10
    //   223: aaload
    //   224: invokevirtual getSuccessors : ()Lcom/android/dx/util/IntList;
    //   227: astore_2
    //   228: aload_0
    //   229: getfield subroutines : [Lcom/android/dx/cf/code/Ropper$Subroutine;
    //   232: iload #10
    //   234: aaload
    //   235: aload #5
    //   237: aload_3
    //   238: invokevirtual mergeToSuccessors : (Lcom/android/dx/cf/code/Frame;[I)V
    //   241: aload_2
    //   242: invokevirtual size : ()I
    //   245: istore #10
    //   247: goto -> 264
    //   250: aload_0
    //   251: getfield machine : Lcom/android/dx/cf/code/RopperMachine;
    //   254: invokevirtual wereCatchesUsed : ()Z
    //   257: ifeq -> 270
    //   260: iload #9
    //   262: istore #10
    //   264: aconst_null
    //   265: astore #11
    //   267: goto -> 276
    //   270: aconst_null
    //   271: astore #11
    //   273: iconst_0
    //   274: istore #10
    //   276: aload_2
    //   277: invokevirtual size : ()I
    //   280: istore #12
    //   282: iload #10
    //   284: istore #13
    //   286: iload #12
    //   288: istore #10
    //   290: iload #13
    //   292: iload #10
    //   294: if_icmpge -> 363
    //   297: aload_2
    //   298: iload #13
    //   300: invokevirtual get : (I)I
    //   303: istore #12
    //   305: aload_0
    //   306: iload #12
    //   308: aload_1
    //   309: invokevirtual getLabel : ()I
    //   312: aload #11
    //   314: aload #5
    //   316: aload_3
    //   317: invokespecial mergeAndWorkAsNecessary : (IILcom/android/dx/cf/code/Ropper$Subroutine;Lcom/android/dx/cf/code/Frame;[I)V
    //   320: iinc #13, 1
    //   323: goto -> 290
    //   326: astore_1
    //   327: new java/lang/StringBuilder
    //   330: dup
    //   331: invokespecial <init> : ()V
    //   334: astore_2
    //   335: aload_2
    //   336: ldc_w '...while merging to block '
    //   339: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   342: pop
    //   343: aload_2
    //   344: iload #12
    //   346: invokestatic u2 : (I)Ljava/lang/String;
    //   349: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   352: pop
    //   353: aload_1
    //   354: aload_2
    //   355: invokevirtual toString : ()Ljava/lang/String;
    //   358: invokevirtual addContext : (Ljava/lang/String;)V
    //   361: aload_1
    //   362: athrow
    //   363: iload #10
    //   365: ifne -> 394
    //   368: aload_0
    //   369: getfield machine : Lcom/android/dx/cf/code/RopperMachine;
    //   372: invokevirtual returns : ()Z
    //   375: ifeq -> 394
    //   378: aload_0
    //   379: bipush #-2
    //   381: invokespecial getSpecialLabel : (I)I
    //   384: invokestatic makeImmutable : (I)Lcom/android/dx/util/IntList;
    //   387: astore_2
    //   388: iconst_1
    //   389: istore #12
    //   391: goto -> 398
    //   394: iload #10
    //   396: istore #12
    //   398: iload #12
    //   400: ifne -> 409
    //   403: iconst_m1
    //   404: istore #10
    //   406: goto -> 435
    //   409: aload_0
    //   410: getfield machine : Lcom/android/dx/cf/code/RopperMachine;
    //   413: invokevirtual getPrimarySuccessorIndex : ()I
    //   416: istore #13
    //   418: iload #13
    //   420: istore #10
    //   422: iload #13
    //   424: iflt -> 435
    //   427: aload_2
    //   428: iload #13
    //   430: invokevirtual get : (I)I
    //   433: istore #10
    //   435: aload_0
    //   436: invokespecial isSynchronized : ()Z
    //   439: ifeq -> 458
    //   442: aload_0
    //   443: getfield machine : Lcom/android/dx/cf/code/RopperMachine;
    //   446: invokevirtual canThrow : ()Z
    //   449: ifeq -> 458
    //   452: iconst_1
    //   453: istore #13
    //   455: goto -> 461
    //   458: iconst_0
    //   459: istore #13
    //   461: iload #13
    //   463: ifne -> 477
    //   466: iload #9
    //   468: ifeq -> 474
    //   471: goto -> 477
    //   474: goto -> 763
    //   477: new com/android/dx/util/IntList
    //   480: dup
    //   481: iload #12
    //   483: invokespecial <init> : (I)V
    //   486: astore_2
    //   487: iconst_0
    //   488: istore #12
    //   490: iconst_0
    //   491: istore #14
    //   493: iload #14
    //   495: iload #9
    //   497: if_icmpge -> 668
    //   500: aload #4
    //   502: iload #14
    //   504: invokevirtual get : (I)Lcom/android/dx/cf/code/ByteCatchList$Item;
    //   507: astore #11
    //   509: aload #11
    //   511: invokevirtual getExceptionClass : ()Lcom/android/dx/rop/cst/CstType;
    //   514: astore #15
    //   516: aload #11
    //   518: invokevirtual getHandlerPc : ()I
    //   521: istore #16
    //   523: aload #15
    //   525: getstatic com/android/dx/rop/cst/CstType.OBJECT : Lcom/android/dx/rop/cst/CstType;
    //   528: if_acmpne -> 537
    //   531: iconst_1
    //   532: istore #17
    //   534: goto -> 540
    //   537: iconst_0
    //   538: istore #17
    //   540: aload #5
    //   542: aload #15
    //   544: invokevirtual makeExceptionHandlerStartFrame : (Lcom/android/dx/rop/cst/CstType;)Lcom/android/dx/cf/code/Frame;
    //   547: astore #11
    //   549: aload_0
    //   550: iload #16
    //   552: aload_1
    //   553: invokevirtual getLabel : ()I
    //   556: aconst_null
    //   557: aload #11
    //   559: aload_3
    //   560: invokespecial mergeAndWorkAsNecessary : (IILcom/android/dx/cf/code/Ropper$Subroutine;Lcom/android/dx/cf/code/Frame;[I)V
    //   563: aload_0
    //   564: getfield catchInfos : [Lcom/android/dx/cf/code/Ropper$CatchInfo;
    //   567: iload #16
    //   569: aaload
    //   570: astore #18
    //   572: aload #18
    //   574: astore #11
    //   576: aload #18
    //   578: ifnonnull -> 601
    //   581: new com/android/dx/cf/code/Ropper$CatchInfo
    //   584: dup
    //   585: aload_0
    //   586: aconst_null
    //   587: invokespecial <init> : (Lcom/android/dx/cf/code/Ropper;Lcom/android/dx/cf/code/Ropper$1;)V
    //   590: astore #11
    //   592: aload_0
    //   593: getfield catchInfos : [Lcom/android/dx/cf/code/Ropper$CatchInfo;
    //   596: iload #16
    //   598: aload #11
    //   600: aastore
    //   601: aload_2
    //   602: aload #11
    //   604: aload #15
    //   606: invokevirtual getClassType : ()Lcom/android/dx/rop/type/Type;
    //   609: invokevirtual getSetup : (Lcom/android/dx/rop/type/Type;)Lcom/android/dx/cf/code/Ropper$ExceptionHandlerSetup;
    //   612: invokevirtual getLabel : ()I
    //   615: invokevirtual add : (I)V
    //   618: iinc #14, 1
    //   621: iload #12
    //   623: iload #17
    //   625: ior
    //   626: istore #12
    //   628: goto -> 493
    //   631: astore_1
    //   632: new java/lang/StringBuilder
    //   635: dup
    //   636: invokespecial <init> : ()V
    //   639: astore_2
    //   640: aload_2
    //   641: ldc_w '...while merging exception to block '
    //   644: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   647: pop
    //   648: aload_2
    //   649: iload #16
    //   651: invokestatic u2 : (I)Ljava/lang/String;
    //   654: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   657: pop
    //   658: aload_1
    //   659: aload_2
    //   660: invokevirtual toString : ()Ljava/lang/String;
    //   663: invokevirtual addContext : (Ljava/lang/String;)V
    //   666: aload_1
    //   667: athrow
    //   668: iload #13
    //   670: ifeq -> 748
    //   673: iload #12
    //   675: ifne -> 748
    //   678: aload_2
    //   679: aload_0
    //   680: bipush #-6
    //   682: invokespecial getSpecialLabel : (I)I
    //   685: invokevirtual add : (I)V
    //   688: aload_0
    //   689: iconst_1
    //   690: putfield synchNeedsExceptionHandler : Z
    //   693: iload #8
    //   695: iload #6
    //   697: isub
    //   698: iconst_1
    //   699: isub
    //   700: istore #13
    //   702: iload #13
    //   704: iload #8
    //   706: if_icmpge -> 748
    //   709: aload #7
    //   711: iload #13
    //   713: invokevirtual get : (I)Ljava/lang/Object;
    //   716: checkcast com/android/dx/rop/code/Insn
    //   719: astore_3
    //   720: aload_3
    //   721: invokevirtual canThrow : ()Z
    //   724: ifeq -> 742
    //   727: aload #7
    //   729: iload #13
    //   731: aload_3
    //   732: getstatic com/android/dx/rop/type/Type.OBJECT : Lcom/android/dx/rop/type/Type;
    //   735: invokevirtual withAddedCatch : (Lcom/android/dx/rop/type/Type;)Lcom/android/dx/rop/code/Insn;
    //   738: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   741: pop
    //   742: iinc #13, 1
    //   745: goto -> 702
    //   748: iload #10
    //   750: iflt -> 759
    //   753: aload_2
    //   754: iload #10
    //   756: invokevirtual add : (I)V
    //   759: aload_2
    //   760: invokevirtual setImmutable : ()V
    //   763: aload_2
    //   764: iload #10
    //   766: invokevirtual indexOf : (I)I
    //   769: istore #9
    //   771: iload #10
    //   773: istore #13
    //   775: iload #8
    //   777: istore #10
    //   779: iload #6
    //   781: istore #12
    //   783: iload #12
    //   785: ifle -> 962
    //   788: iload #10
    //   790: iconst_1
    //   791: isub
    //   792: istore #17
    //   794: aload #7
    //   796: iload #17
    //   798: invokevirtual get : (I)Ljava/lang/Object;
    //   801: checkcast com/android/dx/rop/code/Insn
    //   804: astore_3
    //   805: aload_3
    //   806: invokevirtual getOpcode : ()Lcom/android/dx/rop/code/Rop;
    //   809: invokevirtual getBranchingness : ()I
    //   812: iconst_1
    //   813: if_icmpne -> 822
    //   816: iconst_1
    //   817: istore #10
    //   819: goto -> 825
    //   822: iconst_0
    //   823: istore #10
    //   825: iload #10
    //   827: ifeq -> 836
    //   830: iconst_2
    //   831: istore #14
    //   833: goto -> 839
    //   836: iconst_1
    //   837: istore #14
    //   839: new com/android/dx/rop/code/InsnList
    //   842: dup
    //   843: iload #14
    //   845: invokespecial <init> : (I)V
    //   848: astore #11
    //   850: aload #11
    //   852: iconst_0
    //   853: aload_3
    //   854: invokevirtual set : (ILcom/android/dx/rop/code/Insn;)V
    //   857: iload #10
    //   859: ifeq -> 895
    //   862: aload #11
    //   864: iconst_1
    //   865: new com/android/dx/rop/code/PlainInsn
    //   868: dup
    //   869: getstatic com/android/dx/rop/code/Rops.GOTO : Lcom/android/dx/rop/code/Rop;
    //   872: aload_3
    //   873: invokevirtual getPosition : ()Lcom/android/dx/rop/code/SourcePosition;
    //   876: aconst_null
    //   877: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   880: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;)V
    //   883: invokevirtual set : (ILcom/android/dx/rop/code/Insn;)V
    //   886: iload #13
    //   888: invokestatic makeImmutable : (I)Lcom/android/dx/util/IntList;
    //   891: astore_3
    //   892: goto -> 897
    //   895: aload_2
    //   896: astore_3
    //   897: aload #11
    //   899: invokevirtual setImmutable : ()V
    //   902: aload_0
    //   903: invokespecial getAvailableLabel : ()I
    //   906: istore #10
    //   908: aload_0
    //   909: new com/android/dx/rop/code/BasicBlock
    //   912: dup
    //   913: iload #10
    //   915: aload #11
    //   917: aload_3
    //   918: iload #13
    //   920: invokespecial <init> : (ILcom/android/dx/rop/code/InsnList;Lcom/android/dx/util/IntList;I)V
    //   923: aload #5
    //   925: invokevirtual getSubroutines : ()Lcom/android/dx/util/IntList;
    //   928: invokespecial addBlock : (Lcom/android/dx/rop/code/BasicBlock;Lcom/android/dx/util/IntList;)V
    //   931: aload_2
    //   932: invokevirtual mutableCopy : ()Lcom/android/dx/util/IntList;
    //   935: astore_2
    //   936: aload_2
    //   937: iload #9
    //   939: iload #10
    //   941: invokevirtual set : (II)V
    //   944: aload_2
    //   945: invokevirtual setImmutable : ()V
    //   948: iinc #12, -1
    //   951: iload #10
    //   953: istore #13
    //   955: iload #17
    //   957: istore #10
    //   959: goto -> 783
    //   962: iload #10
    //   964: ifne -> 972
    //   967: aconst_null
    //   968: astore_3
    //   969: goto -> 985
    //   972: aload #7
    //   974: iload #10
    //   976: iconst_1
    //   977: isub
    //   978: invokevirtual get : (I)Ljava/lang/Object;
    //   981: checkcast com/android/dx/rop/code/Insn
    //   984: astore_3
    //   985: aload_3
    //   986: ifnull -> 1004
    //   989: iload #10
    //   991: istore #12
    //   993: aload_3
    //   994: invokevirtual getOpcode : ()Lcom/android/dx/rop/code/Rop;
    //   997: invokevirtual getBranchingness : ()I
    //   1000: iconst_1
    //   1001: if_icmpne -> 1047
    //   1004: aload_3
    //   1005: ifnonnull -> 1015
    //   1008: getstatic com/android/dx/rop/code/SourcePosition.NO_INFO : Lcom/android/dx/rop/code/SourcePosition;
    //   1011: astore_3
    //   1012: goto -> 1020
    //   1015: aload_3
    //   1016: invokevirtual getPosition : ()Lcom/android/dx/rop/code/SourcePosition;
    //   1019: astore_3
    //   1020: aload #7
    //   1022: new com/android/dx/rop/code/PlainInsn
    //   1025: dup
    //   1026: getstatic com/android/dx/rop/code/Rops.GOTO : Lcom/android/dx/rop/code/Rop;
    //   1029: aload_3
    //   1030: aconst_null
    //   1031: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   1034: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;)V
    //   1037: invokevirtual add : (Ljava/lang/Object;)Z
    //   1040: pop
    //   1041: iload #10
    //   1043: iconst_1
    //   1044: iadd
    //   1045: istore #12
    //   1047: new com/android/dx/rop/code/InsnList
    //   1050: dup
    //   1051: iload #12
    //   1053: invokespecial <init> : (I)V
    //   1056: astore_3
    //   1057: iconst_0
    //   1058: istore #10
    //   1060: iload #10
    //   1062: iload #12
    //   1064: if_icmpge -> 1089
    //   1067: aload_3
    //   1068: iload #10
    //   1070: aload #7
    //   1072: iload #10
    //   1074: invokevirtual get : (I)Ljava/lang/Object;
    //   1077: checkcast com/android/dx/rop/code/Insn
    //   1080: invokevirtual set : (ILcom/android/dx/rop/code/Insn;)V
    //   1083: iinc #10, 1
    //   1086: goto -> 1060
    //   1089: aload_3
    //   1090: invokevirtual setImmutable : ()V
    //   1093: aload_0
    //   1094: new com/android/dx/rop/code/BasicBlock
    //   1097: dup
    //   1098: aload_1
    //   1099: invokevirtual getLabel : ()I
    //   1102: aload_3
    //   1103: aload_2
    //   1104: iload #13
    //   1106: invokespecial <init> : (ILcom/android/dx/rop/code/InsnList;Lcom/android/dx/util/IntList;I)V
    //   1109: aload #5
    //   1111: invokevirtual getSubroutines : ()Lcom/android/dx/util/IntList;
    //   1114: invokespecial addOrReplaceBlock : (Lcom/android/dx/rop/code/BasicBlock;Lcom/android/dx/util/IntList;)Z
    //   1117: pop
    //   1118: return
    // Exception table:
    //   from	to	target	type
    //   305	320	326	com/android/dx/cf/code/SimException
    //   549	563	631	com/android/dx/cf/code/SimException
  }
  
  private void removeBlockAndSpecialSuccessors(int paramInt) {
    int i = getMinimumUnreservedLabel();
    IntList intList = ((BasicBlock)this.result.get(paramInt)).getSuccessors();
    int j = intList.size();
    this.result.remove(paramInt);
    this.resultSubroutines.remove(paramInt);
    for (paramInt = 0; paramInt < j; paramInt++) {
      int k = intList.get(paramInt);
      if (k >= i) {
        int m = labelToResultIndex(k);
        if (m >= 0) {
          removeBlockAndSpecialSuccessors(m);
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Invalid label ");
          stringBuilder.append(Hex.u2(k));
          throw new RuntimeException(stringBuilder.toString());
        } 
      } 
    } 
  }
  
  private void setFirstFrame() {
    Prototype prototype = this.method.getEffectiveDescriptor();
    this.startFrames[0].initializeWithParameters(prototype.getParameterTypes());
    this.startFrames[0].setImmutable();
  }
  
  private Subroutine subroutineFromRetBlock(int paramInt) {
    for (int i = this.subroutines.length - 1; i >= 0; i--) {
      Subroutine[] arrayOfSubroutine = this.subroutines;
      if (arrayOfSubroutine[i] != null) {
        Subroutine subroutine = arrayOfSubroutine[i];
        if (subroutine.retBlocks.get(paramInt))
          return subroutine; 
      } 
    } 
    return null;
  }
  
  int getFirstTempStackReg() {
    int i = getNormalRegCount();
    int j = i;
    if (isSynchronized())
      j = i + 1; 
    return j;
  }
  
  private class CatchInfo {
    private final Map<Type, Ropper.ExceptionHandlerSetup> setups = new HashMap<Type, Ropper.ExceptionHandlerSetup>();
    
    private CatchInfo() {}
    
    Ropper.ExceptionHandlerSetup getSetup(Type param1Type) {
      Ropper.ExceptionHandlerSetup exceptionHandlerSetup1 = this.setups.get(param1Type);
      Ropper.ExceptionHandlerSetup exceptionHandlerSetup2 = exceptionHandlerSetup1;
      if (exceptionHandlerSetup1 == null) {
        exceptionHandlerSetup2 = new Ropper.ExceptionHandlerSetup(param1Type, Ropper.this.exceptionSetupLabelAllocator.getNextLabel());
        this.setups.put(param1Type, exceptionHandlerSetup2);
      } 
      return exceptionHandlerSetup2;
    }
    
    Collection<Ropper.ExceptionHandlerSetup> getSetups() {
      return this.setups.values();
    }
  }
  
  private static class ExceptionHandlerSetup {
    private Type caughtType;
    
    private int label;
    
    ExceptionHandlerSetup(Type param1Type, int param1Int) {
      this.caughtType = param1Type;
      this.label = param1Int;
    }
    
    Type getCaughtType() {
      return this.caughtType;
    }
    
    public int getLabel() {
      return this.label;
    }
  }
  
  private class ExceptionSetupLabelAllocator extends LabelAllocator {
    int maxSetupLabel = Ropper.this.maxLabel + Ropper.this.method.getCatches().size();
    
    ExceptionSetupLabelAllocator() {
      super(Ropper.this.maxLabel);
    }
    
    int getNextLabel() {
      if (this.nextAvailableLabel < this.maxSetupLabel) {
        int i = this.nextAvailableLabel;
        this.nextAvailableLabel = i + 1;
        return i;
      } 
      throw new IndexOutOfBoundsException();
    }
  }
  
  private static class LabelAllocator {
    int nextAvailableLabel;
    
    LabelAllocator(int param1Int) {
      this.nextAvailableLabel = param1Int;
    }
    
    int getNextLabel() {
      int i = this.nextAvailableLabel;
      this.nextAvailableLabel = i + 1;
      return i;
    }
  }
  
  private class Subroutine {
    private BitSet callerBlocks;
    
    private BitSet retBlocks;
    
    private int startBlock;
    
    Subroutine(int param1Int) {
      this.startBlock = param1Int;
      this.retBlocks = new BitSet(Ropper.this.maxLabel);
      this.callerBlocks = new BitSet(Ropper.this.maxLabel);
      Ropper.access$202(Ropper.this, true);
    }
    
    Subroutine(int param1Int1, int param1Int2) {
      this(param1Int1);
      addRetBlock(param1Int2);
    }
    
    void addCallerBlock(int param1Int) {
      this.callerBlocks.set(param1Int);
    }
    
    void addRetBlock(int param1Int) {
      this.retBlocks.set(param1Int);
    }
    
    int getStartBlock() {
      return this.startBlock;
    }
    
    IntList getSuccessors() {
      IntList intList = new IntList(this.callerBlocks.size());
      for (int i = this.callerBlocks.nextSetBit(0); i >= 0; i = this.callerBlocks.nextSetBit(i + 1))
        intList.add(Ropper.this.labelToBlock(i).getSuccessors().get(0)); 
      intList.setImmutable();
      return intList;
    }
    
    void mergeToSuccessors(Frame param1Frame, int[] param1ArrayOfint) {
      for (int i = this.callerBlocks.nextSetBit(0); i >= 0; i = this.callerBlocks.nextSetBit(i + 1)) {
        int j = Ropper.this.labelToBlock(i).getSuccessors().get(0);
        Frame frame = param1Frame.subFrameForLabel(this.startBlock, i);
        if (frame != null) {
          Ropper.this.mergeAndWorkAsNecessary(j, -1, null, frame, param1ArrayOfint);
        } else {
          Bits.set(param1ArrayOfint, i);
        } 
      } 
    }
  }
  
  private class SubroutineInliner {
    private final Ropper.LabelAllocator labelAllocator;
    
    private final ArrayList<IntList> labelToSubroutines;
    
    private final HashMap<Integer, Integer> origLabelToCopiedLabel = new HashMap<Integer, Integer>();
    
    private int subroutineStart;
    
    private int subroutineSuccessor;
    
    private final BitSet workList = new BitSet(Ropper.this.maxLabel);
    
    SubroutineInliner(Ropper.LabelAllocator param1LabelAllocator, ArrayList<IntList> param1ArrayList) {
      this.labelAllocator = param1LabelAllocator;
      this.labelToSubroutines = param1ArrayList;
    }
    
    private void copyBlock(int param1Int1, int param1Int2) {
      StringBuilder stringBuilder;
      IntList intList2;
      BasicBlock basicBlock = Ropper.this.labelToBlock(param1Int1);
      IntList intList1 = basicBlock.getSuccessors();
      boolean bool = Ropper.this.isSubroutineCaller(basicBlock);
      int i = 0;
      int j = -1;
      if (bool) {
        intList2 = IntList.makeImmutable(mapOrAllocateLabel(intList1.get(0)), intList1.get(1));
        param1Int1 = j;
      } else {
        Ropper.Subroutine subroutine = Ropper.this.subroutineFromRetBlock(param1Int1);
        if (subroutine != null) {
          if (subroutine.startBlock == this.subroutineStart) {
            intList2 = IntList.makeImmutable(this.subroutineSuccessor);
            param1Int1 = this.subroutineSuccessor;
          } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("ret instruction returns to label ");
            stringBuilder.append(Hex.u2(((Ropper.Subroutine)intList2).startBlock));
            stringBuilder.append(" expected: ");
            stringBuilder.append(Hex.u2(this.subroutineStart));
            throw new RuntimeException(stringBuilder.toString());
          } 
        } else {
          int k = stringBuilder.getPrimarySuccessor();
          int m = intList1.size();
          intList2 = new IntList(m);
          param1Int1 = j;
          for (j = i; j < m; j++) {
            int n = intList1.get(j);
            i = mapOrAllocateLabel(n);
            intList2.add(i);
            if (k == n)
              param1Int1 = i; 
          } 
          intList2.setImmutable();
        } 
      } 
      Ropper ropper = Ropper.this;
      ropper.addBlock(new BasicBlock(param1Int2, ropper.filterMoveReturnAddressInsns(stringBuilder.getInsns()), intList2, param1Int1), this.labelToSubroutines.get(param1Int2));
    }
    
    private boolean involvedInSubroutine(int param1Int1, int param1Int2) {
      boolean bool;
      IntList intList = this.labelToSubroutines.get(param1Int1);
      if (intList != null && intList.size() > 0 && intList.top() == param1Int2) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    private int mapOrAllocateLabel(int param1Int) {
      Integer integer = this.origLabelToCopiedLabel.get(Integer.valueOf(param1Int));
      if (integer != null) {
        param1Int = integer.intValue();
      } else if (involvedInSubroutine(param1Int, this.subroutineStart)) {
        int i = this.labelAllocator.getNextLabel();
        this.workList.set(param1Int);
        this.origLabelToCopiedLabel.put(Integer.valueOf(param1Int), Integer.valueOf(i));
        while (this.labelToSubroutines.size() <= i)
          this.labelToSubroutines.add(null); 
        ArrayList<IntList> arrayList = this.labelToSubroutines;
        arrayList.set(i, arrayList.get(param1Int));
        param1Int = i;
      } 
      return param1Int;
    }
    
    void inlineSubroutineCalledFrom(BasicBlock param1BasicBlock) {
      this.subroutineSuccessor = param1BasicBlock.getSuccessors().get(0);
      int i = param1BasicBlock.getSuccessors().get(1);
      this.subroutineStart = i;
      int j = mapOrAllocateLabel(i);
      for (i = this.workList.nextSetBit(0); i >= 0; i = this.workList.nextSetBit(0)) {
        this.workList.clear(i);
        int k = ((Integer)this.origLabelToCopiedLabel.get(Integer.valueOf(i))).intValue();
        copyBlock(i, k);
        Ropper ropper = Ropper.this;
        if (ropper.isSubroutineCaller(ropper.labelToBlock(i)))
          (new SubroutineInliner(this.labelAllocator, this.labelToSubroutines)).inlineSubroutineCalledFrom(Ropper.this.labelToBlock(k)); 
      } 
      Ropper.this.addOrReplaceBlockNoDelete(new BasicBlock(param1BasicBlock.getLabel(), param1BasicBlock.getInsns(), IntList.makeImmutable(j), j), this.labelToSubroutines.get(param1BasicBlock.getLabel()));
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\Ropper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */