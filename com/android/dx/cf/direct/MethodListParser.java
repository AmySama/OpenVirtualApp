package com.android.dx.cf.direct;

import com.android.dx.cf.iface.AttributeList;
import com.android.dx.cf.iface.Member;
import com.android.dx.cf.iface.Method;
import com.android.dx.cf.iface.StdMethod;
import com.android.dx.cf.iface.StdMethodList;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstType;

final class MethodListParser extends MemberListParser {
  private final StdMethodList methods = new StdMethodList(getCount());
  
  public MethodListParser(DirectClassFile paramDirectClassFile, CstType paramCstType, int paramInt, AttributeFactory paramAttributeFactory) {
    super(paramDirectClassFile, paramCstType, paramInt, paramAttributeFactory);
  }
  
  protected int getAttributeContext() {
    return 2;
  }
  
  public StdMethodList getList() {
    parseIfNecessary();
    return this.methods;
  }
  
  protected String humanAccessFlags(int paramInt) {
    return AccessFlags.methodString(paramInt);
  }
  
  protected String humanName() {
    return "method";
  }
  
  protected Member set(int paramInt1, int paramInt2, CstNat paramCstNat, AttributeList paramAttributeList) {
    StdMethod stdMethod = new StdMethod(getDefiner(), paramInt2, paramCstNat, paramAttributeList);
    this.methods.set(paramInt1, (Method)stdMethod);
    return (Member)stdMethod;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\direct\MethodListParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */