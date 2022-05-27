package com.android.dx;

import com.android.dx.rop.code.BasicBlockList;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.PlainCstInsn;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.code.ThrowingCstInsn;
import com.android.dx.rop.code.ThrowingInsn;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.rop.type.TypeList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Code {
  private final List<Label> catchLabels = new ArrayList<Label>();
  
  private final List<TypeId<?>> catchTypes = new ArrayList<TypeId<?>>();
  
  private StdTypeList catches = StdTypeList.EMPTY;
  
  private Label currentLabel;
  
  private final List<Label> labels = new ArrayList<Label>();
  
  private final List<Local<?>> locals = new ArrayList<Local<?>>();
  
  private boolean localsInitialized;
  
  private final MethodId<?, ?> method;
  
  private final List<Local<?>> parameters = new ArrayList<Local<?>>();
  
  private SourcePosition sourcePosition = SourcePosition.NO_INFO;
  
  private final Local<?> thisLocal;
  
  Code(DexMaker.MethodDeclaration paramMethodDeclaration) {
    this.method = paramMethodDeclaration.method;
    if (paramMethodDeclaration.isStatic()) {
      this.thisLocal = null;
    } else {
      Local<?> local = Local.get(this, this.method.declaringType);
      this.thisLocal = local;
      this.parameters.add(local);
    } 
    for (TypeId<?> typeId : this.method.parameters.types)
      this.parameters.add(Local.get(this, typeId)); 
    Label label = new Label();
    this.currentLabel = label;
    adopt(label);
    this.currentLabel.marked = true;
  }
  
  private void addInstruction(Insn paramInsn) {
    addInstruction(paramInsn, null);
  }
  
  private void addInstruction(Insn paramInsn, Label paramLabel) {
    Label label = this.currentLabel;
    if (label != null && label.marked) {
      this.currentLabel.instructions.add(paramInsn);
      int i = paramInsn.getOpcode().getBranchingness();
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i != 4) {
              if (i == 6) {
                if (paramLabel == null) {
                  splitCurrentLabel(null, new ArrayList<Label>(this.catchLabels));
                } else {
                  StringBuilder stringBuilder1 = new StringBuilder();
                  stringBuilder1.append("unexpected branch: ");
                  stringBuilder1.append(paramLabel);
                  throw new IllegalArgumentException(stringBuilder1.toString());
                } 
              } else {
                throw new IllegalArgumentException();
              } 
            } else if (paramLabel != null) {
              splitCurrentLabel(paramLabel, Collections.emptyList());
            } else {
              throw new IllegalArgumentException("branch == null");
            } 
          } else if (paramLabel != null) {
            this.currentLabel.primarySuccessor = paramLabel;
            this.currentLabel = null;
          } else {
            throw new IllegalArgumentException("branch == null");
          } 
        } else {
          if (paramLabel == null) {
            this.currentLabel = null;
            return;
          } 
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("unexpected branch: ");
          stringBuilder1.append(paramLabel);
          throw new IllegalArgumentException(stringBuilder1.toString());
        } 
        return;
      } 
      if (paramLabel == null)
        return; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unexpected branch: ");
      stringBuilder.append(paramLabel);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new IllegalStateException("no current label");
  }
  
  private void adopt(Label paramLabel) {
    if (paramLabel.code == this)
      return; 
    if (paramLabel.code == null) {
      paramLabel.code = this;
      this.labels.add(paramLabel);
      return;
    } 
    throw new IllegalArgumentException("Cannot adopt label; it belongs to another Code");
  }
  
  private void cleanUpLabels() {
    Iterator<Label> iterator = this.labels.iterator();
    for (byte b = 0; iterator.hasNext(); b++) {
      Label label = iterator.next();
      if (label.isEmpty()) {
        iterator.remove();
        continue;
      } 
      label.compact();
      label.id = b;
    } 
  }
  
  private <T> Local<T> coerce(Local<?> paramLocal, TypeId<T> paramTypeId) {
    if (paramLocal.type.equals(paramTypeId))
      return (Local)paramLocal; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("requested ");
    stringBuilder.append(paramTypeId);
    stringBuilder.append(" but was ");
    stringBuilder.append(paramLocal.type);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private static RegisterSpecList concatenate(Local<?> paramLocal, Local<?>[] paramArrayOfLocal) {
    byte b2;
    byte b1 = 0;
    if (paramLocal != null) {
      b2 = 1;
    } else {
      b2 = 0;
    } 
    RegisterSpecList registerSpecList = new RegisterSpecList(paramArrayOfLocal.length + b2);
    byte b3 = b1;
    if (paramLocal != null) {
      registerSpecList.set(0, paramLocal.spec());
      b3 = b1;
    } 
    while (b3 < paramArrayOfLocal.length) {
      registerSpecList.set(b3 + b2, paramArrayOfLocal[b3].spec());
      b3++;
    } 
    return registerSpecList;
  }
  
  private Rop getCastRop(Type paramType1, Type paramType2) {
    if (paramType1.getBasicType() == 6) {
      int i = paramType2.getBasicType();
      if (i != 2) {
        if (i != 3) {
          if (i == 8)
            return Rops.TO_SHORT; 
        } else {
          return Rops.TO_CHAR;
        } 
      } else {
        return Rops.TO_BYTE;
      } 
    } 
    return Rops.opConv((TypeBearer)paramType2, (TypeBearer)paramType1);
  }
  
  private <D, R> void invoke(Rop paramRop, MethodId<D, R> paramMethodId, Local<? super R> paramLocal, Local<? extends D> paramLocal1, Local<?>... paramVarArgs) {
    addInstruction((Insn)new ThrowingCstInsn(paramRop, this.sourcePosition, concatenate(paramLocal1, paramVarArgs), (TypeList)this.catches, (Constant)paramMethodId.constant));
    if (paramLocal != null)
      moveResult(paramLocal, false); 
  }
  
  private void moveResult(Local<?> paramLocal, boolean paramBoolean) {
    Rop rop;
    if (paramBoolean) {
      rop = Rops.opMoveResultPseudo((TypeBearer)paramLocal.type.ropType);
    } else {
      rop = Rops.opMoveResult((TypeBearer)paramLocal.type.ropType);
    } 
    addInstruction((Insn)new PlainInsn(rop, this.sourcePosition, paramLocal.spec(), RegisterSpecList.EMPTY));
  }
  
  private void splitCurrentLabel(Label paramLabel, List<Label> paramList) {
    Label label = new Label();
    adopt(label);
    this.currentLabel.primarySuccessor = label;
    this.currentLabel.alternateSuccessor = paramLabel;
    this.currentLabel.catchLabels = paramList;
    this.currentLabel = label;
    label.marked = true;
  }
  
  private StdTypeList toTypeList(List<TypeId<?>> paramList) {
    StdTypeList stdTypeList = new StdTypeList(paramList.size());
    for (byte b = 0; b < paramList.size(); b++)
      stdTypeList.set(b, ((TypeId)paramList.get(b)).ropType); 
    return stdTypeList;
  }
  
  public void addCatchClause(TypeId<? extends Throwable> paramTypeId, Label paramLabel) {
    if (!this.catchTypes.contains(paramTypeId)) {
      adopt(paramLabel);
      this.catchTypes.add(paramTypeId);
      this.catches = toTypeList(this.catchTypes);
      this.catchLabels.add(paramLabel);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Already caught: ");
    stringBuilder.append(paramTypeId);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void aget(Local<?> paramLocal1, Local<?> paramLocal2, Local<Integer> paramLocal) {
    addInstruction((Insn)new ThrowingInsn(Rops.opAget((TypeBearer)paramLocal1.type.ropType), this.sourcePosition, RegisterSpecList.make(paramLocal2.spec(), paramLocal.spec()), (TypeList)this.catches));
    moveResult(paramLocal1, true);
  }
  
  public void aput(Local<?> paramLocal1, Local<Integer> paramLocal, Local<?> paramLocal2) {
    addInstruction((Insn)new ThrowingInsn(Rops.opAput((TypeBearer)paramLocal2.type.ropType), this.sourcePosition, RegisterSpecList.make(paramLocal2.spec(), paramLocal1.spec(), paramLocal.spec()), (TypeList)this.catches));
  }
  
  public <T> void arrayLength(Local<Integer> paramLocal, Local<T> paramLocal1) {
    addInstruction((Insn)new ThrowingInsn(Rops.ARRAY_LENGTH, this.sourcePosition, RegisterSpecList.make(paramLocal1.spec()), (TypeList)this.catches));
    moveResult(paramLocal, true);
  }
  
  public void cast(Local<?> paramLocal1, Local<?> paramLocal2) {
    if ((paramLocal2.getType()).ropType.isReference()) {
      addInstruction((Insn)new ThrowingCstInsn(Rops.CHECK_CAST, this.sourcePosition, RegisterSpecList.make(paramLocal2.spec()), (TypeList)this.catches, (Constant)paramLocal1.type.constant));
      moveResult(paramLocal1, true);
    } else {
      addInstruction((Insn)new PlainInsn(getCastRop(paramLocal2.type.ropType, paramLocal1.type.ropType), this.sourcePosition, paramLocal1.spec(), paramLocal2.spec()));
    } 
  }
  
  public <T> void compare(Comparison paramComparison, Label paramLabel, Local<T> paramLocal1, Local<T> paramLocal2) {
    adopt(paramLabel);
    addInstruction((Insn)new PlainInsn(paramComparison.rop((TypeList)StdTypeList.make(paramLocal1.type.ropType, paramLocal2.type.ropType)), this.sourcePosition, null, RegisterSpecList.make(paramLocal1.spec(), paramLocal2.spec())), paramLabel);
  }
  
  public <T extends Number> void compareFloatingPoint(Local<Integer> paramLocal, Local<T> paramLocal1, Local<T> paramLocal2, int paramInt) {
    StringBuilder stringBuilder;
    Rop rop;
    if (paramInt == 1) {
      rop = Rops.opCmpg((TypeBearer)paramLocal1.type.ropType);
    } else if (paramInt == -1) {
      rop = Rops.opCmpl((TypeBearer)paramLocal1.type.ropType);
    } else {
      stringBuilder = new StringBuilder();
      stringBuilder.append("expected 1 or -1 but was ");
      stringBuilder.append(paramInt);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    addInstruction((Insn)new PlainInsn(rop, this.sourcePosition, stringBuilder.spec(), RegisterSpecList.make(paramLocal1.spec(), paramLocal2.spec())));
  }
  
  public void compareLongs(Local<Integer> paramLocal, Local<Long> paramLocal1, Local<Long> paramLocal2) {
    addInstruction((Insn)new PlainInsn(Rops.CMPL_LONG, this.sourcePosition, paramLocal.spec(), RegisterSpecList.make(paramLocal1.spec(), paramLocal2.spec())));
  }
  
  public <T> void compareZ(Comparison paramComparison, Label paramLabel, Local<?> paramLocal) {
    adopt(paramLabel);
    addInstruction((Insn)new PlainInsn(paramComparison.rop((TypeList)StdTypeList.make(paramLocal.type.ropType)), this.sourcePosition, null, RegisterSpecList.make(paramLocal.spec())), paramLabel);
  }
  
  public <T> Local<T> getParameter(int paramInt, TypeId<T> paramTypeId) {
    int i = paramInt;
    if (this.thisLocal != null)
      i = paramInt + 1; 
    return coerce(this.parameters.get(i), paramTypeId);
  }
  
  public <T> Local<T> getThis(TypeId<T> paramTypeId) {
    Local<?> local = this.thisLocal;
    if (local != null)
      return coerce(local, paramTypeId); 
    throw new IllegalStateException("static methods cannot access 'this'");
  }
  
  public <D, V> void iget(FieldId<D, ? extends V> paramFieldId, Local<V> paramLocal, Local<D> paramLocal1) {
    addInstruction((Insn)new ThrowingCstInsn(Rops.opGetField((TypeBearer)paramLocal.type.ropType), this.sourcePosition, RegisterSpecList.make(paramLocal1.spec()), (TypeList)this.catches, (Constant)paramFieldId.constant));
    moveResult(paramLocal, true);
  }
  
  void initializeLocals() {
    if (!this.localsInitialized) {
      this.localsInitialized = true;
      Iterator<Local<?>> iterator1 = this.locals.iterator();
      int i;
      for (i = 0; iterator1.hasNext(); i += ((Local)iterator1.next()).initialize(i));
      ArrayList<PlainCstInsn> arrayList = new ArrayList();
      Iterator<Local<?>> iterator2 = this.parameters.iterator();
      int j = i;
      while (iterator2.hasNext()) {
        Local local = iterator2.next();
        CstInteger cstInteger = CstInteger.make(j - i);
        j += local.initialize(j);
        arrayList.add(new PlainCstInsn(Rops.opMoveParam((TypeBearer)local.type.ropType), this.sourcePosition, local.spec(), RegisterSpecList.EMPTY, (Constant)cstInteger));
      } 
      ((Label)this.labels.get(0)).instructions.addAll(0, arrayList);
      return;
    } 
    throw new AssertionError();
  }
  
  public void instanceOfType(Local<?> paramLocal1, Local<?> paramLocal2, TypeId<?> paramTypeId) {
    addInstruction((Insn)new ThrowingCstInsn(Rops.INSTANCE_OF, this.sourcePosition, RegisterSpecList.make(paramLocal2.spec()), (TypeList)this.catches, (Constant)paramTypeId.constant));
    moveResult(paramLocal1, true);
  }
  
  public <D, R> void invokeDirect(MethodId<D, R> paramMethodId, Local<? super R> paramLocal, Local<? extends D> paramLocal1, Local<?>... paramVarArgs) {
    invoke(Rops.opInvokeDirect(paramMethodId.prototype(true)), paramMethodId, paramLocal, paramLocal1, paramVarArgs);
  }
  
  public <D, R> void invokeInterface(MethodId<D, R> paramMethodId, Local<? super R> paramLocal, Local<? extends D> paramLocal1, Local<?>... paramVarArgs) {
    invoke(Rops.opInvokeInterface(paramMethodId.prototype(true)), paramMethodId, paramLocal, paramLocal1, paramVarArgs);
  }
  
  public <R> void invokeStatic(MethodId<?, R> paramMethodId, Local<? super R> paramLocal, Local<?>... paramVarArgs) {
    invoke(Rops.opInvokeStatic(paramMethodId.prototype(true)), paramMethodId, paramLocal, null, paramVarArgs);
  }
  
  public <D, R> void invokeSuper(MethodId<D, R> paramMethodId, Local<? super R> paramLocal, Local<? extends D> paramLocal1, Local<?>... paramVarArgs) {
    invoke(Rops.opInvokeSuper(paramMethodId.prototype(true)), paramMethodId, paramLocal, paramLocal1, paramVarArgs);
  }
  
  public <D, R> void invokeVirtual(MethodId<D, R> paramMethodId, Local<? super R> paramLocal, Local<? extends D> paramLocal1, Local<?>... paramVarArgs) {
    invoke(Rops.opInvokeVirtual(paramMethodId.prototype(true)), paramMethodId, paramLocal, paramLocal1, paramVarArgs);
  }
  
  public <D, V> void iput(FieldId<D, V> paramFieldId, Local<? extends D> paramLocal, Local<? extends V> paramLocal1) {
    addInstruction((Insn)new ThrowingCstInsn(Rops.opPutField((TypeBearer)paramLocal1.type.ropType), this.sourcePosition, RegisterSpecList.make(paramLocal1.spec(), paramLocal.spec()), (TypeList)this.catches, (Constant)paramFieldId.constant));
  }
  
  public void jump(Label paramLabel) {
    adopt(paramLabel);
    addInstruction((Insn)new PlainInsn(Rops.GOTO, this.sourcePosition, null, RegisterSpecList.EMPTY), paramLabel);
  }
  
  public <T> void loadConstant(Local<T> paramLocal, T paramT) {
    Rop rop;
    if (paramT == null) {
      rop = Rops.CONST_OBJECT_NOTHROW;
    } else {
      rop = Rops.opConst((TypeBearer)paramLocal.type.ropType);
    } 
    if (rop.getBranchingness() == 1) {
      addInstruction((Insn)new PlainCstInsn(rop, this.sourcePosition, paramLocal.spec(), RegisterSpecList.EMPTY, (Constant)Constants.getConstant(paramT)));
    } else {
      addInstruction((Insn)new ThrowingCstInsn(rop, this.sourcePosition, RegisterSpecList.EMPTY, (TypeList)this.catches, (Constant)Constants.getConstant(paramT)));
      moveResult(paramLocal, true);
    } 
  }
  
  public void mark(Label paramLabel) {
    adopt(paramLabel);
    if (!paramLabel.marked) {
      paramLabel.marked = true;
      if (this.currentLabel != null)
        jump(paramLabel); 
      this.currentLabel = paramLabel;
      return;
    } 
    throw new IllegalStateException("already marked");
  }
  
  public void monitorEnter(Local<?> paramLocal) {
    addInstruction((Insn)new ThrowingInsn(Rops.MONITOR_ENTER, this.sourcePosition, RegisterSpecList.make(paramLocal.spec()), (TypeList)this.catches));
  }
  
  public void monitorExit(Local<?> paramLocal) {
    addInstruction((Insn)new ThrowingInsn(Rops.MONITOR_EXIT, this.sourcePosition, RegisterSpecList.make(paramLocal.spec()), (TypeList)this.catches));
  }
  
  public <T> void move(Local<T> paramLocal1, Local<T> paramLocal2) {
    addInstruction((Insn)new PlainInsn(Rops.opMove((TypeBearer)paramLocal2.type.ropType), this.sourcePosition, paramLocal1.spec(), paramLocal2.spec()));
  }
  
  public void moveException(Local<?> paramLocal) {
    addInstruction((Insn)new PlainInsn(Rops.opMoveException((TypeBearer)Type.THROWABLE), SourcePosition.NO_INFO, paramLocal.spec(), RegisterSpecList.EMPTY));
  }
  
  public <T> void newArray(Local<T> paramLocal, Local<Integer> paramLocal1) {
    addInstruction((Insn)new ThrowingCstInsn(Rops.opNewArray((TypeBearer)paramLocal.type.ropType), this.sourcePosition, RegisterSpecList.make(paramLocal1.spec()), (TypeList)this.catches, (Constant)paramLocal.type.constant));
    moveResult(paramLocal, true);
  }
  
  public <T> void newInstance(Local<T> paramLocal, MethodId<T, Void> paramMethodId, Local<?>... paramVarArgs) {
    if (paramLocal != null) {
      addInstruction((Insn)new ThrowingCstInsn(Rops.NEW_INSTANCE, this.sourcePosition, RegisterSpecList.EMPTY, (TypeList)this.catches, (Constant)paramMethodId.declaringType.constant));
      moveResult(paramLocal, true);
      invokeDirect(paramMethodId, null, paramLocal, paramVarArgs);
      return;
    } 
    throw new IllegalArgumentException();
  }
  
  public <T> Local<T> newLocal(TypeId<T> paramTypeId) {
    if (!this.localsInitialized) {
      Local<T> local = Local.get(this, paramTypeId);
      this.locals.add(local);
      return local;
    } 
    throw new IllegalStateException("Cannot allocate locals after adding instructions");
  }
  
  public <T1, T2> void op(BinaryOp paramBinaryOp, Local<T1> paramLocal1, Local<T1> paramLocal2, Local<T2> paramLocal) {
    Rop rop = paramBinaryOp.rop((TypeList)StdTypeList.make(paramLocal2.type.ropType, paramLocal.type.ropType));
    RegisterSpecList registerSpecList = RegisterSpecList.make(paramLocal2.spec(), paramLocal.spec());
    if (rop.getBranchingness() == 1) {
      addInstruction((Insn)new PlainInsn(rop, this.sourcePosition, paramLocal1.spec(), registerSpecList));
    } else {
      addInstruction((Insn)new ThrowingInsn(rop, this.sourcePosition, registerSpecList, (TypeList)this.catches));
      moveResult(paramLocal1, true);
    } 
  }
  
  public <T> void op(UnaryOp paramUnaryOp, Local<T> paramLocal1, Local<T> paramLocal2) {
    addInstruction((Insn)new PlainInsn(paramUnaryOp.rop(paramLocal2.type), this.sourcePosition, paramLocal1.spec(), paramLocal2.spec()));
  }
  
  int paramSize() {
    Iterator<Local<?>> iterator = this.parameters.iterator();
    int i;
    for (i = 0; iterator.hasNext(); i += ((Local)iterator.next()).size());
    return i;
  }
  
  public Label removeCatchClause(TypeId<? extends Throwable> paramTypeId) {
    int i = this.catchTypes.indexOf(paramTypeId);
    if (i != -1) {
      this.catchTypes.remove(i);
      this.catches = toTypeList(this.catchTypes);
      return this.catchLabels.remove(i);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No catch clause: ");
    stringBuilder.append(paramTypeId);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void returnValue(Local<?> paramLocal) {
    if (paramLocal.type.equals(this.method.returnType)) {
      addInstruction((Insn)new PlainInsn(Rops.opReturn((TypeBearer)paramLocal.type.ropType), this.sourcePosition, null, RegisterSpecList.make(paramLocal.spec())));
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("declared ");
    stringBuilder.append(this.method.returnType);
    stringBuilder.append(" but returned ");
    stringBuilder.append(paramLocal.type);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void returnVoid() {
    if (this.method.returnType.equals(TypeId.VOID)) {
      addInstruction((Insn)new PlainInsn(Rops.RETURN_VOID, this.sourcePosition, null, RegisterSpecList.EMPTY));
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("declared ");
    stringBuilder.append(this.method.returnType);
    stringBuilder.append(" but returned void");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public <V> void sget(FieldId<?, ? extends V> paramFieldId, Local<V> paramLocal) {
    addInstruction((Insn)new ThrowingCstInsn(Rops.opGetStatic((TypeBearer)paramLocal.type.ropType), this.sourcePosition, RegisterSpecList.EMPTY, (TypeList)this.catches, (Constant)paramFieldId.constant));
    moveResult(paramLocal, true);
  }
  
  public <V> void sput(FieldId<?, V> paramFieldId, Local<? extends V> paramLocal) {
    addInstruction((Insn)new ThrowingCstInsn(Rops.opPutStatic((TypeBearer)paramLocal.type.ropType), this.sourcePosition, RegisterSpecList.make(paramLocal.spec()), (TypeList)this.catches, (Constant)paramFieldId.constant));
  }
  
  public void throwValue(Local<? extends Throwable> paramLocal) {
    addInstruction((Insn)new ThrowingInsn(Rops.THROW, this.sourcePosition, RegisterSpecList.make(paramLocal.spec()), (TypeList)this.catches));
  }
  
  BasicBlockList toBasicBlocks() {
    if (!this.localsInitialized)
      initializeLocals(); 
    cleanUpLabels();
    BasicBlockList basicBlockList = new BasicBlockList(this.labels.size());
    for (byte b = 0; b < this.labels.size(); b++)
      basicBlockList.set(b, ((Label)this.labels.get(b)).toBasicBlock()); 
    return basicBlockList;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\Code.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */