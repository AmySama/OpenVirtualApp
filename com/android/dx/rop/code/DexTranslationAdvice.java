package com.android.dx.rop.code;

import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.type.Type;

public final class DexTranslationAdvice implements TranslationAdvice {
  private static final int MIN_INVOKE_IN_ORDER = 6;
  
  public static final DexTranslationAdvice NO_SOURCES_IN_ORDER;
  
  public static final DexTranslationAdvice THE_ONE = new DexTranslationAdvice();
  
  private final boolean disableSourcesInOrder = false;
  
  static {
    NO_SOURCES_IN_ORDER = new DexTranslationAdvice(true);
  }
  
  private DexTranslationAdvice() {}
  
  private DexTranslationAdvice(boolean paramBoolean) {}
  
  private int totalRopWidth(RegisterSpecList paramRegisterSpecList) {
    int i = paramRegisterSpecList.size();
    byte b = 0;
    int j = 0;
    while (b < i) {
      j += paramRegisterSpecList.get(b).getCategory();
      b++;
    } 
    return j;
  }
  
  public int getMaxOptimalRegisterCount() {
    return 16;
  }
  
  public boolean hasConstantOperation(Rop paramRop, RegisterSpec paramRegisterSpec1, RegisterSpec paramRegisterSpec2) {
    if (paramRegisterSpec1.getType() != Type.INT)
      return false; 
    if (!(paramRegisterSpec2.getTypeBearer() instanceof CstInteger))
      return (paramRegisterSpec1.getTypeBearer() instanceof CstInteger && paramRop.getOpcode() == 15) ? ((CstInteger)paramRegisterSpec1.getTypeBearer()).fitsIn16Bits() : false; 
    CstInteger cstInteger = (CstInteger)paramRegisterSpec2.getTypeBearer();
    switch (paramRop.getOpcode()) {
      default:
        return false;
      case 23:
      case 24:
      case 25:
        return cstInteger.fitsIn8Bits();
      case 15:
        return CstInteger.make(-cstInteger.getValue()).fitsIn16Bits();
      case 14:
      case 16:
      case 17:
      case 18:
      case 20:
      case 21:
      case 22:
        break;
    } 
    return cstInteger.fitsIn16Bits();
  }
  
  public boolean requiresSourcesInOrder(Rop paramRop, RegisterSpecList paramRegisterSpecList) {
    boolean bool;
    if (!this.disableSourcesInOrder && paramRop.isCallLike() && totalRopWidth(paramRegisterSpecList) >= 6) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\DexTranslationAdvice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */