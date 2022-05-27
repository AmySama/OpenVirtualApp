package com.android.dx.cf.code;

import com.android.dx.rop.code.LocalItem;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import java.util.ArrayList;

public abstract class BaseMachine implements Machine {
  private int argCount;
  
  private TypeBearer[] args;
  
  private SwitchList auxCases;
  
  private Constant auxCst;
  
  private ArrayList<Constant> auxInitValues;
  
  private int auxInt;
  
  private int auxTarget;
  
  private Type auxType;
  
  private int localIndex;
  
  private boolean localInfo;
  
  private RegisterSpec localTarget;
  
  private final Prototype prototype;
  
  private int resultCount;
  
  private TypeBearer[] results;
  
  public BaseMachine(Prototype paramPrototype) {
    if (paramPrototype != null) {
      this.prototype = paramPrototype;
      this.args = new TypeBearer[10];
      this.results = new TypeBearer[6];
      clearArgs();
      return;
    } 
    throw new NullPointerException("prototype == null");
  }
  
  public static void throwLocalMismatch(TypeBearer paramTypeBearer1, TypeBearer paramTypeBearer2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("local variable type mismatch: attempt to set or access a value of type ");
    stringBuilder.append(paramTypeBearer1.toHuman());
    stringBuilder.append(" using a local variable of type ");
    stringBuilder.append(paramTypeBearer2.toHuman());
    stringBuilder.append(". This is symptomatic of .class transformation tools that ignore local variable information.");
    throw new SimException(stringBuilder.toString());
  }
  
  protected final void addResult(TypeBearer paramTypeBearer) {
    if (paramTypeBearer != null) {
      TypeBearer[] arrayOfTypeBearer = this.results;
      int i = this.resultCount;
      arrayOfTypeBearer[i] = paramTypeBearer;
      this.resultCount = i + 1;
      return;
    } 
    throw new NullPointerException("result == null");
  }
  
  protected final TypeBearer arg(int paramInt) {
    if (paramInt < this.argCount)
      try {
        return this.args[paramInt];
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        throw new IllegalArgumentException("n < 0");
      }  
    throw new IllegalArgumentException("n >= argCount");
  }
  
  protected final int argCount() {
    return this.argCount;
  }
  
  protected final int argWidth() {
    byte b = 0;
    int i = 0;
    while (b < this.argCount) {
      i += this.args[b].getType().getCategory();
      b++;
    } 
    return i;
  }
  
  public final void auxCstArg(Constant paramConstant) {
    if (paramConstant != null) {
      this.auxCst = paramConstant;
      return;
    } 
    throw new NullPointerException("cst == null");
  }
  
  public final void auxInitValues(ArrayList<Constant> paramArrayList) {
    this.auxInitValues = paramArrayList;
  }
  
  public final void auxIntArg(int paramInt) {
    this.auxInt = paramInt;
  }
  
  public final void auxSwitchArg(SwitchList paramSwitchList) {
    if (paramSwitchList != null) {
      this.auxCases = paramSwitchList;
      return;
    } 
    throw new NullPointerException("cases == null");
  }
  
  public final void auxTargetArg(int paramInt) {
    this.auxTarget = paramInt;
  }
  
  public final void auxType(Type paramType) {
    this.auxType = paramType;
  }
  
  public final void clearArgs() {
    this.argCount = 0;
    this.auxType = null;
    this.auxInt = 0;
    this.auxCst = null;
    this.auxTarget = 0;
    this.auxCases = null;
    this.auxInitValues = null;
    this.localIndex = -1;
    this.localInfo = false;
    this.localTarget = null;
    this.resultCount = -1;
  }
  
  protected final void clearResult() {
    this.resultCount = 0;
  }
  
  protected final SwitchList getAuxCases() {
    return this.auxCases;
  }
  
  protected final Constant getAuxCst() {
    return this.auxCst;
  }
  
  protected final int getAuxInt() {
    return this.auxInt;
  }
  
  protected final int getAuxTarget() {
    return this.auxTarget;
  }
  
  protected final Type getAuxType() {
    return this.auxType;
  }
  
  protected final ArrayList<Constant> getInitValues() {
    return this.auxInitValues;
  }
  
  protected final int getLocalIndex() {
    return this.localIndex;
  }
  
  protected final boolean getLocalInfo() {
    return this.localInfo;
  }
  
  protected final RegisterSpec getLocalTarget(boolean paramBoolean) {
    if (this.localTarget == null)
      return null; 
    if (this.resultCount != 1) {
      String str;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("local target with ");
      if (this.resultCount == 0) {
        str = "no";
      } else {
        str = "multiple";
      } 
      stringBuilder.append(str);
      stringBuilder.append(" results");
      throw new SimException(stringBuilder.toString());
    } 
    TypeBearer typeBearer = this.results[0];
    Type type1 = typeBearer.getType();
    Type type2 = this.localTarget.getType();
    if (type1 == type2)
      return paramBoolean ? this.localTarget.withType(typeBearer) : this.localTarget; 
    if (!Merger.isPossiblyAssignableFrom((TypeBearer)type2, (TypeBearer)type1)) {
      throwLocalMismatch((TypeBearer)type1, (TypeBearer)type2);
      return null;
    } 
    if (type2 == Type.OBJECT)
      this.localTarget = this.localTarget.withType(typeBearer); 
    return this.localTarget;
  }
  
  public Prototype getPrototype() {
    return this.prototype;
  }
  
  public final void localArg(Frame paramFrame, int paramInt) {
    clearArgs();
    this.args[0] = paramFrame.getLocals().get(paramInt);
    this.argCount = 1;
    this.localIndex = paramInt;
  }
  
  public final void localInfo(boolean paramBoolean) {
    this.localInfo = paramBoolean;
  }
  
  public final void localTarget(int paramInt, Type paramType, LocalItem paramLocalItem) {
    this.localTarget = RegisterSpec.makeLocalOptional(paramInt, (TypeBearer)paramType, paramLocalItem);
  }
  
  public final void popArgs(Frame paramFrame, int paramInt) {
    ExecutionStack executionStack = paramFrame.getStack();
    clearArgs();
    if (paramInt > this.args.length)
      this.args = new TypeBearer[paramInt + 10]; 
    for (int i = paramInt - 1; i >= 0; i--)
      this.args[i] = executionStack.pop(); 
    this.argCount = paramInt;
  }
  
  public void popArgs(Frame paramFrame, Prototype paramPrototype) {
    StdTypeList stdTypeList = paramPrototype.getParameterTypes();
    int i = stdTypeList.size();
    popArgs(paramFrame, i);
    byte b = 0;
    while (b < i) {
      if (Merger.isPossiblyAssignableFrom((TypeBearer)stdTypeList.getType(b), this.args[b])) {
        b++;
        continue;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("at stack depth ");
      stringBuilder.append(i - 1 - b);
      stringBuilder.append(", expected type ");
      stringBuilder.append(stdTypeList.getType(b).toHuman());
      stringBuilder.append(" but found ");
      stringBuilder.append(this.args[b].getType().toHuman());
      throw new SimException(stringBuilder.toString());
    } 
  }
  
  public final void popArgs(Frame paramFrame, Type paramType) {
    popArgs(paramFrame, 1);
    if (Merger.isPossiblyAssignableFrom((TypeBearer)paramType, this.args[0]))
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("expected type ");
    stringBuilder.append(paramType.toHuman());
    stringBuilder.append(" but found ");
    stringBuilder.append(this.args[0].getType().toHuman());
    throw new SimException(stringBuilder.toString());
  }
  
  public final void popArgs(Frame paramFrame, Type paramType1, Type paramType2) {
    popArgs(paramFrame, 2);
    if (Merger.isPossiblyAssignableFrom((TypeBearer)paramType1, this.args[0])) {
      if (Merger.isPossiblyAssignableFrom((TypeBearer)paramType2, this.args[1]))
        return; 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("expected type ");
      stringBuilder1.append(paramType2.toHuman());
      stringBuilder1.append(" but found ");
      stringBuilder1.append(this.args[1].getType().toHuman());
      throw new SimException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("expected type ");
    stringBuilder.append(paramType1.toHuman());
    stringBuilder.append(" but found ");
    stringBuilder.append(this.args[0].getType().toHuman());
    throw new SimException(stringBuilder.toString());
  }
  
  public final void popArgs(Frame paramFrame, Type paramType1, Type paramType2, Type paramType3) {
    popArgs(paramFrame, 3);
    if (Merger.isPossiblyAssignableFrom((TypeBearer)paramType1, this.args[0])) {
      if (Merger.isPossiblyAssignableFrom((TypeBearer)paramType2, this.args[1])) {
        if (Merger.isPossiblyAssignableFrom((TypeBearer)paramType3, this.args[2]))
          return; 
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("expected type ");
        stringBuilder2.append(paramType3.toHuman());
        stringBuilder2.append(" but found ");
        stringBuilder2.append(this.args[2].getType().toHuman());
        throw new SimException(stringBuilder2.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("expected type ");
      stringBuilder1.append(paramType2.toHuman());
      stringBuilder1.append(" but found ");
      stringBuilder1.append(this.args[1].getType().toHuman());
      throw new SimException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("expected type ");
    stringBuilder.append(paramType1.toHuman());
    stringBuilder.append(" but found ");
    stringBuilder.append(this.args[0].getType().toHuman());
    throw new SimException(stringBuilder.toString());
  }
  
  protected final TypeBearer result(int paramInt) {
    if (paramInt < this.resultCount)
      try {
        return this.results[paramInt];
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        throw new IllegalArgumentException("n < 0");
      }  
    throw new IllegalArgumentException("n >= resultCount");
  }
  
  protected final int resultCount() {
    int i = this.resultCount;
    if (i >= 0)
      return i; 
    throw new SimException("results never set");
  }
  
  protected final int resultWidth() {
    byte b = 0;
    int i = 0;
    while (b < this.resultCount) {
      i += this.results[b].getType().getCategory();
      b++;
    } 
    return i;
  }
  
  protected final void setResult(TypeBearer paramTypeBearer) {
    if (paramTypeBearer != null) {
      this.results[0] = paramTypeBearer;
      this.resultCount = 1;
      return;
    } 
    throw new NullPointerException("result == null");
  }
  
  protected final void storeResults(Frame paramFrame) {
    int i = this.resultCount;
    if (i >= 0) {
      if (i == 0)
        return; 
      RegisterSpec registerSpec = this.localTarget;
      i = 0;
      if (registerSpec != null) {
        paramFrame.getLocals().set(getLocalTarget(false));
      } else {
        ExecutionStack executionStack = paramFrame.getStack();
        while (i < this.resultCount) {
          if (this.localInfo)
            executionStack.setLocal(); 
          executionStack.push(this.results[i]);
          i++;
        } 
      } 
      return;
    } 
    throw new SimException("results never set");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\BaseMachine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */