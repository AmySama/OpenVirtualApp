package com.android.dx.cf.attrib;

import com.android.dx.cf.code.LocalVariableList;
import com.android.dx.util.MutabilityException;

public abstract class BaseLocalVariables extends BaseAttribute {
  private final LocalVariableList localVariables;
  
  public BaseLocalVariables(String paramString, LocalVariableList paramLocalVariableList) {
    super(paramString);
    try {
      boolean bool = paramLocalVariableList.isMutable();
      if (!bool) {
        this.localVariables = paramLocalVariableList;
        return;
      } 
      MutabilityException mutabilityException = new MutabilityException();
      this("localVariables.isMutable()");
      throw mutabilityException;
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("localVariables == null");
    } 
  }
  
  public final int byteLength() {
    return this.localVariables.size() * 10 + 8;
  }
  
  public final LocalVariableList getLocalVariables() {
    return this.localVariables;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\BaseLocalVariables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */