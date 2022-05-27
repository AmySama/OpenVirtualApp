package com.android.dx.rop.code;

import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;

public final class ThrowingInsn extends Insn {
  private final TypeList catches;
  
  public ThrowingInsn(Rop paramRop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList, TypeList paramTypeList) {
    super(paramRop, paramSourcePosition, null, paramRegisterSpecList);
    if (paramRop.getBranchingness() == 6) {
      if (paramTypeList != null) {
        this.catches = paramTypeList;
        return;
      } 
      throw new NullPointerException("catches == null");
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("opcode with invalid branchingness: ");
    stringBuilder.append(paramRop.getBranchingness());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static String toCatchString(TypeList paramTypeList) {
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append("catch");
    int i = paramTypeList.size();
    for (byte b = 0; b < i; b++) {
      stringBuilder.append(" ");
      stringBuilder.append(paramTypeList.getType(b).toHuman());
    } 
    return stringBuilder.toString();
  }
  
  public void accept(Insn.Visitor paramVisitor) {
    paramVisitor.visitThrowingInsn(this);
  }
  
  public TypeList getCatches() {
    return this.catches;
  }
  
  public String getInlineString() {
    return toCatchString(this.catches);
  }
  
  public Insn withAddedCatch(Type paramType) {
    return new ThrowingInsn(getOpcode(), getPosition(), getSources(), this.catches.withAddedType(paramType));
  }
  
  public Insn withNewRegisters(RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList) {
    return new ThrowingInsn(getOpcode(), getPosition(), paramRegisterSpecList, this.catches);
  }
  
  public Insn withRegisterOffset(int paramInt) {
    return new ThrowingInsn(getOpcode(), getPosition(), getSources().withOffset(paramInt), this.catches);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\ThrowingInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */