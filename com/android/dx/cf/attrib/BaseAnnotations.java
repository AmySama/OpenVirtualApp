package com.android.dx.cf.attrib;

import com.android.dx.rop.annotation.Annotations;
import com.android.dx.util.MutabilityException;

public abstract class BaseAnnotations extends BaseAttribute {
  private final Annotations annotations;
  
  private final int byteLength;
  
  public BaseAnnotations(String paramString, Annotations paramAnnotations, int paramInt) {
    super(paramString);
    try {
      boolean bool = paramAnnotations.isMutable();
      if (!bool) {
        this.annotations = paramAnnotations;
        this.byteLength = paramInt;
        return;
      } 
      MutabilityException mutabilityException = new MutabilityException();
      this("annotations.isMutable()");
      throw mutabilityException;
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("annotations == null");
    } 
  }
  
  public final int byteLength() {
    return this.byteLength + 6;
  }
  
  public final Annotations getAnnotations() {
    return this.annotations;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\BaseAnnotations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */