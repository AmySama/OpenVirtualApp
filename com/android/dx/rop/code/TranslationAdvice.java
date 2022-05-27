package com.android.dx.rop.code;

public interface TranslationAdvice {
  int getMaxOptimalRegisterCount();
  
  boolean hasConstantOperation(Rop paramRop, RegisterSpec paramRegisterSpec1, RegisterSpec paramRegisterSpec2);
  
  boolean requiresSourcesInOrder(Rop paramRop, RegisterSpecList paramRegisterSpecList);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\TranslationAdvice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */