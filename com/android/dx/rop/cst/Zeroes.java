package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;

public final class Zeroes {
  public static Constant zeroFor(Type paramType) {
    StringBuilder stringBuilder;
    switch (paramType.getBasicType()) {
      default:
        stringBuilder = new StringBuilder();
        stringBuilder.append("no zero for type: ");
        stringBuilder.append(paramType.toHuman());
        throw new UnsupportedOperationException(stringBuilder.toString());
      case 9:
        return CstKnownNull.THE_ONE;
      case 8:
        return CstShort.VALUE_0;
      case 7:
        return CstLong.VALUE_0;
      case 6:
        return CstInteger.VALUE_0;
      case 5:
        return CstFloat.VALUE_0;
      case 4:
        return CstDouble.VALUE_0;
      case 3:
        return CstChar.VALUE_0;
      case 2:
        return CstByte.VALUE_0;
      case 1:
        break;
    } 
    return CstBoolean.VALUE_FALSE;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\Zeroes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */