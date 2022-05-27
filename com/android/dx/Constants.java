package com.android.dx;

import com.android.dx.rop.cst.CstBoolean;
import com.android.dx.rop.cst.CstByte;
import com.android.dx.rop.cst.CstChar;
import com.android.dx.rop.cst.CstDouble;
import com.android.dx.rop.cst.CstFloat;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.cst.CstKnownNull;
import com.android.dx.rop.cst.CstLong;
import com.android.dx.rop.cst.CstShort;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.cst.TypedConstant;

final class Constants {
  static TypedConstant getConstant(Object paramObject) {
    if (paramObject == null)
      return (TypedConstant)CstKnownNull.THE_ONE; 
    if (paramObject instanceof Boolean)
      return (TypedConstant)CstBoolean.make(((Boolean)paramObject).booleanValue()); 
    if (paramObject instanceof Byte)
      return (TypedConstant)CstByte.make(((Byte)paramObject).byteValue()); 
    if (paramObject instanceof Character)
      return (TypedConstant)CstChar.make(((Character)paramObject).charValue()); 
    if (paramObject instanceof Double)
      return (TypedConstant)CstDouble.make(Double.doubleToLongBits(((Double)paramObject).doubleValue())); 
    if (paramObject instanceof Float)
      return (TypedConstant)CstFloat.make(Float.floatToIntBits(((Float)paramObject).floatValue())); 
    if (paramObject instanceof Integer)
      return (TypedConstant)CstInteger.make(((Integer)paramObject).intValue()); 
    if (paramObject instanceof Long)
      return (TypedConstant)CstLong.make(((Long)paramObject).longValue()); 
    if (paramObject instanceof Short)
      return (TypedConstant)CstShort.make(((Short)paramObject).shortValue()); 
    if (paramObject instanceof String)
      return (TypedConstant)new CstString((String)paramObject); 
    if (paramObject instanceof Class)
      return (TypedConstant)new CstType((TypeId.get((Class)paramObject)).ropType); 
    if (paramObject instanceof TypeId)
      return (TypedConstant)new CstType(((TypeId)paramObject).ropType); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Not a constant: ");
    stringBuilder.append(paramObject);
    throw new UnsupportedOperationException(stringBuilder.toString());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\Constants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */