package com.android.dx.cf.attrib;

import com.android.dx.rop.type.TypeList;
import com.android.dx.util.MutabilityException;

public final class AttExceptions extends BaseAttribute {
  public static final String ATTRIBUTE_NAME = "Exceptions";
  
  private final TypeList exceptions;
  
  public AttExceptions(TypeList paramTypeList) {
    super("Exceptions");
    try {
      boolean bool = paramTypeList.isMutable();
      if (!bool) {
        this.exceptions = paramTypeList;
        return;
      } 
      MutabilityException mutabilityException = new MutabilityException();
      this("exceptions.isMutable()");
      throw mutabilityException;
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("exceptions == null");
    } 
  }
  
  public int byteLength() {
    return this.exceptions.size() * 2 + 8;
  }
  
  public TypeList getExceptions() {
    return this.exceptions;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttExceptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */