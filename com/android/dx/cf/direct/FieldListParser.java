package com.android.dx.cf.direct;

import com.android.dx.cf.iface.AttributeList;
import com.android.dx.cf.iface.Field;
import com.android.dx.cf.iface.Member;
import com.android.dx.cf.iface.StdField;
import com.android.dx.cf.iface.StdFieldList;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstType;

final class FieldListParser extends MemberListParser {
  private final StdFieldList fields = new StdFieldList(getCount());
  
  public FieldListParser(DirectClassFile paramDirectClassFile, CstType paramCstType, int paramInt, AttributeFactory paramAttributeFactory) {
    super(paramDirectClassFile, paramCstType, paramInt, paramAttributeFactory);
  }
  
  protected int getAttributeContext() {
    return 1;
  }
  
  public StdFieldList getList() {
    parseIfNecessary();
    return this.fields;
  }
  
  protected String humanAccessFlags(int paramInt) {
    return AccessFlags.fieldString(paramInt);
  }
  
  protected String humanName() {
    return "field";
  }
  
  protected Member set(int paramInt1, int paramInt2, CstNat paramCstNat, AttributeList paramAttributeList) {
    StdField stdField = new StdField(getDefiner(), paramInt2, paramCstNat, paramAttributeList);
    this.fields.set(paramInt1, (Field)stdField);
    return (Member)stdField;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\direct\FieldListParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */