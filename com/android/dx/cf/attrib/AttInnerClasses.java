package com.android.dx.cf.attrib;

import com.android.dx.util.MutabilityException;

public final class AttInnerClasses extends BaseAttribute {
  public static final String ATTRIBUTE_NAME = "InnerClasses";
  
  private final InnerClassList innerClasses;
  
  public AttInnerClasses(InnerClassList paramInnerClassList) {
    super("InnerClasses");
    try {
      boolean bool = paramInnerClassList.isMutable();
      if (!bool) {
        this.innerClasses = paramInnerClassList;
        return;
      } 
      MutabilityException mutabilityException = new MutabilityException();
      this("innerClasses.isMutable()");
      throw mutabilityException;
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("innerClasses == null");
    } 
  }
  
  public int byteLength() {
    return this.innerClasses.size() * 8 + 8;
  }
  
  public InnerClassList getInnerClasses() {
    return this.innerClasses;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttInnerClasses.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */