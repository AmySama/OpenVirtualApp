package com.android.dx.rop.code;

import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.ToHuman;

public abstract class Insn implements ToHuman {
  private final Rop opcode;
  
  private final SourcePosition position;
  
  private final RegisterSpec result;
  
  private final RegisterSpecList sources;
  
  public Insn(Rop paramRop, SourcePosition paramSourcePosition, RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList) {
    if (paramRop != null) {
      if (paramSourcePosition != null) {
        if (paramRegisterSpecList != null) {
          this.opcode = paramRop;
          this.position = paramSourcePosition;
          this.result = paramRegisterSpec;
          this.sources = paramRegisterSpecList;
          return;
        } 
        throw new NullPointerException("sources == null");
      } 
      throw new NullPointerException("position == null");
    } 
    throw new NullPointerException("opcode == null");
  }
  
  private static boolean equalsHandleNulls(Object paramObject1, Object paramObject2) {
    return (paramObject1 == paramObject2 || (paramObject1 != null && paramObject1.equals(paramObject2)));
  }
  
  public abstract void accept(Visitor paramVisitor);
  
  public final boolean canThrow() {
    return this.opcode.canThrow();
  }
  
  public boolean contentEquals(Insn paramInsn) {
    boolean bool;
    if (this.opcode == paramInsn.getOpcode() && this.position.equals(paramInsn.getPosition()) && getClass() == paramInsn.getClass() && equalsHandleNulls(this.result, paramInsn.getResult()) && equalsHandleNulls(this.sources, paramInsn.getSources()) && StdTypeList.equalContents(getCatches(), paramInsn.getCatches())) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public Insn copy() {
    return withRegisterOffset(0);
  }
  
  public final boolean equals(Object paramObject) {
    boolean bool;
    if (this == paramObject) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public abstract TypeList getCatches();
  
  public String getInlineString() {
    return null;
  }
  
  public final RegisterSpec getLocalAssignment() {
    RegisterSpec registerSpec;
    if (this.opcode.getOpcode() == 54) {
      registerSpec = this.sources.get(0);
    } else {
      registerSpec = this.result;
    } 
    return (registerSpec == null) ? null : ((registerSpec.getLocalItem() == null) ? null : registerSpec);
  }
  
  public final Rop getOpcode() {
    return this.opcode;
  }
  
  public final SourcePosition getPosition() {
    return this.position;
  }
  
  public final RegisterSpec getResult() {
    return this.result;
  }
  
  public final RegisterSpecList getSources() {
    return this.sources;
  }
  
  public final int hashCode() {
    return System.identityHashCode(this);
  }
  
  public String toHuman() {
    return toHumanWithInline(getInlineString());
  }
  
  protected final String toHumanWithInline(String paramString) {
    StringBuilder stringBuilder = new StringBuilder(80);
    stringBuilder.append(this.position);
    stringBuilder.append(": ");
    stringBuilder.append(this.opcode.getNickname());
    if (paramString != null) {
      stringBuilder.append("(");
      stringBuilder.append(paramString);
      stringBuilder.append(")");
    } 
    if (this.result == null) {
      stringBuilder.append(" .");
    } else {
      stringBuilder.append(" ");
      stringBuilder.append(this.result.toHuman());
    } 
    stringBuilder.append(" <-");
    int i = this.sources.size();
    if (i == 0) {
      stringBuilder.append(" .");
    } else {
      for (byte b = 0; b < i; b++) {
        stringBuilder.append(" ");
        stringBuilder.append(this.sources.get(b).toHuman());
      } 
    } 
    return stringBuilder.toString();
  }
  
  public String toString() {
    return toStringWithInline(getInlineString());
  }
  
  protected final String toStringWithInline(String paramString) {
    StringBuilder stringBuilder = new StringBuilder(80);
    stringBuilder.append("Insn{");
    stringBuilder.append(this.position);
    stringBuilder.append(' ');
    stringBuilder.append(this.opcode);
    if (paramString != null) {
      stringBuilder.append(' ');
      stringBuilder.append(paramString);
    } 
    stringBuilder.append(" :: ");
    RegisterSpec registerSpec = this.result;
    if (registerSpec != null) {
      stringBuilder.append(registerSpec);
      stringBuilder.append(" <- ");
    } 
    stringBuilder.append(this.sources);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public abstract Insn withAddedCatch(Type paramType);
  
  public abstract Insn withNewRegisters(RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList);
  
  public abstract Insn withRegisterOffset(int paramInt);
  
  public Insn withSourceLiteral() {
    return this;
  }
  
  public static class BaseVisitor implements Visitor {
    public void visitFillArrayDataInsn(FillArrayDataInsn param1FillArrayDataInsn) {}
    
    public void visitInvokePolymorphicInsn(InvokePolymorphicInsn param1InvokePolymorphicInsn) {}
    
    public void visitPlainCstInsn(PlainCstInsn param1PlainCstInsn) {}
    
    public void visitPlainInsn(PlainInsn param1PlainInsn) {}
    
    public void visitSwitchInsn(SwitchInsn param1SwitchInsn) {}
    
    public void visitThrowingCstInsn(ThrowingCstInsn param1ThrowingCstInsn) {}
    
    public void visitThrowingInsn(ThrowingInsn param1ThrowingInsn) {}
  }
  
  public static interface Visitor {
    void visitFillArrayDataInsn(FillArrayDataInsn param1FillArrayDataInsn);
    
    void visitInvokePolymorphicInsn(InvokePolymorphicInsn param1InvokePolymorphicInsn);
    
    void visitPlainCstInsn(PlainCstInsn param1PlainCstInsn);
    
    void visitPlainInsn(PlainInsn param1PlainInsn);
    
    void visitSwitchInsn(SwitchInsn param1SwitchInsn);
    
    void visitThrowingCstInsn(ThrowingCstInsn param1ThrowingCstInsn);
    
    void visitThrowingInsn(ThrowingInsn param1ThrowingInsn);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\Insn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */