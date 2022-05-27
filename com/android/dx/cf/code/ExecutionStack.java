package com.android.dx.cf.code;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.Hex;
import com.android.dx.util.MutabilityControl;

public final class ExecutionStack extends MutabilityControl {
  private final boolean[] local;
  
  private final TypeBearer[] stack;
  
  private int stackPtr;
  
  public ExecutionStack(int paramInt) {
    super(bool);
    boolean bool;
    this.stack = new TypeBearer[paramInt];
    this.local = new boolean[paramInt];
    this.stackPtr = 0;
  }
  
  private static String stackElementString(TypeBearer paramTypeBearer) {
    return (paramTypeBearer == null) ? "<invalid>" : paramTypeBearer.toString();
  }
  
  private static TypeBearer throwSimException(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("stack: ");
    stringBuilder.append(paramString);
    throw new SimException(stringBuilder.toString());
  }
  
  public void annotate(ExceptionWithContext paramExceptionWithContext) {
    int i = this.stackPtr - 1;
    for (byte b = 0; b <= i; b++) {
      String str;
      if (b == i) {
        str = "top0";
      } else {
        str = Hex.u2(i - b);
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("stack[");
      stringBuilder.append(str);
      stringBuilder.append("]: ");
      stringBuilder.append(stackElementString(this.stack[b]));
      paramExceptionWithContext.addContext(stringBuilder.toString());
    } 
  }
  
  public void change(int paramInt, TypeBearer paramTypeBearer) {
    throwIfImmutable();
    try {
      TypeBearer typeBearer = paramTypeBearer.getFrameType();
      paramInt = this.stackPtr - paramInt - 1;
      paramTypeBearer = this.stack[paramInt];
      if (paramTypeBearer == null || paramTypeBearer.getType().getCategory() != typeBearer.getType().getCategory()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("incompatible substitution: ");
        stringBuilder.append(stackElementString(paramTypeBearer));
        stringBuilder.append(" -> ");
        stringBuilder.append(stackElementString(typeBearer));
        throwSimException(stringBuilder.toString());
      } 
      this.stack[paramInt] = typeBearer;
      return;
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("type == null");
    } 
  }
  
  public void clear() {
    throwIfImmutable();
    for (byte b = 0; b < this.stackPtr; b++) {
      this.stack[b] = null;
      this.local[b] = false;
    } 
    this.stackPtr = 0;
  }
  
  public ExecutionStack copy() {
    ExecutionStack executionStack = new ExecutionStack(this.stack.length);
    TypeBearer[] arrayOfTypeBearer = this.stack;
    System.arraycopy(arrayOfTypeBearer, 0, executionStack.stack, 0, arrayOfTypeBearer.length);
    boolean[] arrayOfBoolean = this.local;
    System.arraycopy(arrayOfBoolean, 0, executionStack.local, 0, arrayOfBoolean.length);
    executionStack.stackPtr = this.stackPtr;
    return executionStack;
  }
  
  public int getMaxStack() {
    return this.stack.length;
  }
  
  public void makeInitialized(Type paramType) {
    if (this.stackPtr == 0)
      return; 
    throwIfImmutable();
    Type type = paramType.getInitializedType();
    for (byte b = 0; b < this.stackPtr; b++) {
      TypeBearer[] arrayOfTypeBearer = this.stack;
      if (arrayOfTypeBearer[b] == paramType)
        arrayOfTypeBearer[b] = (TypeBearer)type; 
    } 
  }
  
  public ExecutionStack merge(ExecutionStack paramExecutionStack) {
    try {
      return Merger.mergeStack(this, paramExecutionStack);
    } catch (SimException simException) {
      simException.addContext("underlay stack:");
      annotate(simException);
      simException.addContext("overlay stack:");
      paramExecutionStack.annotate(simException);
      throw simException;
    } 
  }
  
  public TypeBearer peek(int paramInt) {
    if (paramInt >= 0) {
      int i = this.stackPtr;
      return (paramInt >= i) ? throwSimException("underflow") : this.stack[i - paramInt - 1];
    } 
    throw new IllegalArgumentException("n < 0");
  }
  
  public boolean peekLocal(int paramInt) {
    if (paramInt >= 0) {
      int i = this.stackPtr;
      if (paramInt < i)
        return this.local[i - paramInt - 1]; 
      throw new SimException("stack: underflow");
    } 
    throw new IllegalArgumentException("n < 0");
  }
  
  public Type peekType(int paramInt) {
    return peek(paramInt).getType();
  }
  
  public TypeBearer pop() {
    throwIfImmutable();
    TypeBearer typeBearer = peek(0);
    TypeBearer[] arrayOfTypeBearer = this.stack;
    int i = this.stackPtr;
    arrayOfTypeBearer[i - 1] = null;
    this.local[i - 1] = false;
    this.stackPtr = i - typeBearer.getType().getCategory();
    return typeBearer;
  }
  
  public void push(TypeBearer paramTypeBearer) {
    throwIfImmutable();
    try {
      paramTypeBearer = paramTypeBearer.getFrameType();
      int i = paramTypeBearer.getType().getCategory();
      int j = this.stackPtr;
      TypeBearer[] arrayOfTypeBearer = this.stack;
      if (j + i > arrayOfTypeBearer.length) {
        throwSimException("overflow");
        return;
      } 
      if (i == 2) {
        arrayOfTypeBearer[j] = null;
        this.stackPtr = j + 1;
      } 
      arrayOfTypeBearer = this.stack;
      i = this.stackPtr;
      arrayOfTypeBearer[i] = paramTypeBearer;
      this.stackPtr = i + 1;
      return;
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("type == null");
    } 
  }
  
  public void setLocal() {
    throwIfImmutable();
    this.local[this.stackPtr] = true;
  }
  
  public int size() {
    return this.stackPtr;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\ExecutionStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */