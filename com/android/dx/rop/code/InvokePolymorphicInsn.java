package com.android.dx.rop.code;

import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstProtoRef;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;

public class InvokePolymorphicInsn extends Insn {
  private static final CstString DEFAULT_DESCRIPTOR = new CstString("([Ljava/lang/Object;)Ljava/lang/Object;");
  
  private static final CstString VARHANDLE_COMPARE_AND_SET_DESCRIPTOR;
  
  private static final CstString VARHANDLE_SET_DESCRIPTOR = new CstString("([Ljava/lang/Object;)V");
  
  private final CstMethodRef callSiteMethod;
  
  private final CstProtoRef callSiteProto;
  
  private final TypeList catches;
  
  private final CstMethodRef polymorphicMethod;
  
  static {
    VARHANDLE_COMPARE_AND_SET_DESCRIPTOR = new CstString("([Ljava/lang/Object;)Z");
  }
  
  public InvokePolymorphicInsn(Rop paramRop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList, TypeList paramTypeList, CstMethodRef paramCstMethodRef) {
    super(paramRop, paramSourcePosition, null, paramRegisterSpecList);
    if (paramRop.getBranchingness() == 6) {
      if (paramTypeList != null) {
        this.catches = paramTypeList;
        if (paramCstMethodRef != null) {
          if (paramCstMethodRef.isSignaturePolymorphic()) {
            this.callSiteMethod = paramCstMethodRef;
            this.polymorphicMethod = makePolymorphicMethod(paramCstMethodRef);
            this.callSiteProto = makeCallSiteProto(paramCstMethodRef);
            return;
          } 
          throw new IllegalArgumentException("callSiteMethod is not signature polymorphic");
        } 
        throw new NullPointerException("callSiteMethod == null");
      } 
      throw new NullPointerException("catches == null");
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("opcode with invalid branchingness: ");
    stringBuilder.append(paramRop.getBranchingness());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private static CstProtoRef makeCallSiteProto(CstMethodRef paramCstMethodRef) {
    return new CstProtoRef(paramCstMethodRef.getPrototype(true));
  }
  
  private static CstMethodRef makePolymorphicMethod(CstMethodRef paramCstMethodRef) {
    CstType cstType = paramCstMethodRef.getDefiningClass();
    CstString cstString = paramCstMethodRef.getNat().getName();
    String str = paramCstMethodRef.getNat().getName().getString();
    if (cstType.equals(CstType.METHOD_HANDLE) && (str.equals("invoke") || str.equals("invokeExact")))
      return new CstMethodRef(cstType, new CstNat(cstString, DEFAULT_DESCRIPTOR)); 
    if (cstType.equals(CstType.VAR_HANDLE)) {
      StringBuilder stringBuilder;
      byte b = -1;
      switch (str.hashCode()) {
        case 2013994287:
          if (str.equals("weakCompareAndSetRelease"))
            b = 30; 
          break;
        case 2002508693:
          if (str.equals("getAndSetAcquire"))
            b = 18; 
          break;
        case 1483964149:
          if (str.equals("compareAndExchange"))
            b = 0; 
          break;
        case 1352153939:
          if (str.equals("getAndBitwiseOr"))
            b = 11; 
          break;
        case 1245632875:
          if (str.equals("getAndBitwiseXorAcquire"))
            b = 15; 
          break;
        case 937077366:
          if (str.equals("getAndAddAcquire"))
            b = 6; 
          break;
        case 748071969:
          if (str.equals("compareAndExchangeAcquire"))
            b = 1; 
          break;
        case 685319959:
          if (str.equals("getOpaque"))
            b = 20; 
          break;
        case 470702883:
          if (str.equals("setOpaque"))
            b = 23; 
          break;
        case 353422447:
          if (str.equals("getAndBitwiseAndAcquire"))
            b = 9; 
          break;
        case 282724865:
          if (str.equals("getAndSet"))
            b = 17; 
          break;
        case 282707520:
          if (str.equals("getAndAdd"))
            b = 5; 
          break;
        case 189872914:
          if (str.equals("getVolatile"))
            b = 21; 
          break;
        case 101293086:
          if (str.equals("setVolatile"))
            b = 25; 
          break;
        case 93645315:
          if (str.equals("getAndBitwiseOrAcquire"))
            b = 12; 
          break;
        case 113762:
          if (str.equals("set"))
            b = 22; 
          break;
        case 102230:
          if (str.equals("get"))
            b = 3; 
          break;
        case -37641530:
          if (str.equals("getAndSetRelease"))
            b = 19; 
          break;
        case -127361888:
          if (str.equals("getAcquire"))
            b = 4; 
          break;
        case -230706875:
          if (str.equals("setRelease"))
            b = 24; 
          break;
        case -240822786:
          if (str.equals("weakCompareAndSetAcquire"))
            b = 28; 
          break;
        case -567150350:
          if (str.equals("weakCompareAndSetPlain"))
            b = 29; 
          break;
        case -794517348:
          if (str.equals("getAndBitwiseXorRelease"))
            b = 16; 
          break;
        case -1032892181:
          if (str.equals("getAndBitwiseXor"))
            b = 14; 
          break;
        case -1032914329:
          if (str.equals("getAndBitwiseAnd"))
            b = 8; 
          break;
        case -1103072857:
          if (str.equals("getAndAddRelease"))
            b = 7; 
          break;
        case -1117944904:
          if (str.equals("weakCompareAndSet"))
            b = 27; 
          break;
        case -1292078254:
          if (str.equals("compareAndExchangeRelease"))
            b = 2; 
          break;
        case -1671098288:
          if (str.equals("compareAndSet"))
            b = 26; 
          break;
        case -1686727776:
          if (str.equals("getAndBitwiseAndRelease"))
            b = 10; 
          break;
        case -1946504908:
          if (str.equals("getAndBitwiseOrRelease"))
            b = 13; 
          break;
      } 
      switch (b) {
        default:
          stringBuilder = new StringBuilder();
          stringBuilder.append("Unknown signature polymorphic method: ");
          stringBuilder.append(paramCstMethodRef.toHuman());
          throw new IllegalArgumentException(stringBuilder.toString());
        case 26:
        case 27:
        case 28:
        case 29:
        case 30:
          return new CstMethodRef(cstType, new CstNat((CstString)stringBuilder, VARHANDLE_COMPARE_AND_SET_DESCRIPTOR));
        case 22:
        case 23:
        case 24:
        case 25:
          return new CstMethodRef(cstType, new CstNat((CstString)stringBuilder, VARHANDLE_SET_DESCRIPTOR));
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
          break;
      } 
      return new CstMethodRef(cstType, new CstNat((CstString)stringBuilder, DEFAULT_DESCRIPTOR));
    } 
  }
  
  public void accept(Insn.Visitor paramVisitor) {
    paramVisitor.visitInvokePolymorphicInsn(this);
  }
  
  public CstMethodRef getCallSiteMethod() {
    return this.callSiteMethod;
  }
  
  public CstProtoRef getCallSiteProto() {
    return this.callSiteProto;
  }
  
  public TypeList getCatches() {
    return this.catches;
  }
  
  public String getInlineString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getPolymorphicMethod().toString());
    stringBuilder.append(" ");
    stringBuilder.append(getCallSiteProto().toString());
    stringBuilder.append(" ");
    stringBuilder.append(ThrowingInsn.toCatchString(this.catches));
    return stringBuilder.toString();
  }
  
  public CstMethodRef getPolymorphicMethod() {
    return this.polymorphicMethod;
  }
  
  public Insn withAddedCatch(Type paramType) {
    return new InvokePolymorphicInsn(getOpcode(), getPosition(), getSources(), this.catches.withAddedType(paramType), getCallSiteMethod());
  }
  
  public Insn withNewRegisters(RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList) {
    return new InvokePolymorphicInsn(getOpcode(), getPosition(), paramRegisterSpecList, this.catches, getCallSiteMethod());
  }
  
  public Insn withRegisterOffset(int paramInt) {
    return new InvokePolymorphicInsn(getOpcode(), getPosition(), getSources().withOffset(paramInt), this.catches, getCallSiteMethod());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\InvokePolymorphicInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */