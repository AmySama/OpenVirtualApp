package com.android.dx.cf.attrib;

import com.android.dx.rop.annotation.AnnotationsList;
import com.android.dx.util.MutabilityException;

public abstract class BaseParameterAnnotations extends BaseAttribute {
  private final int byteLength;
  
  private final AnnotationsList parameterAnnotations;
  
  public BaseParameterAnnotations(String paramString, AnnotationsList paramAnnotationsList, int paramInt) {
    super(paramString);
    try {
      boolean bool = paramAnnotationsList.isMutable();
      if (!bool) {
        this.parameterAnnotations = paramAnnotationsList;
        this.byteLength = paramInt;
        return;
      } 
      MutabilityException mutabilityException = new MutabilityException();
      this("parameterAnnotations.isMutable()");
      throw mutabilityException;
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("parameterAnnotations == null");
    } 
  }
  
  public final int byteLength() {
    return this.byteLength + 6;
  }
  
  public final AnnotationsList getParameterAnnotations() {
    return this.parameterAnnotations;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\BaseParameterAnnotations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */