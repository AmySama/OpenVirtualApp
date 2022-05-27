package com.android.dx.util;

public interface AnnotatedOutput extends Output {
  void annotate(int paramInt, String paramString);
  
  void annotate(String paramString);
  
  boolean annotates();
  
  void endAnnotation();
  
  int getAnnotationWidth();
  
  boolean isVerbose();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\AnnotatedOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */