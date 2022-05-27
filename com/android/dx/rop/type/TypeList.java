package com.android.dx.rop.type;

public interface TypeList {
  Type getType(int paramInt);
  
  int getWordCount();
  
  boolean isMutable();
  
  int size();
  
  TypeList withAddedType(Type paramType);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\type\TypeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */