package com.lody.virtual.helper;

public class ComposeClassLoader extends ClassLoader {
  private final ClassLoader mAppClassLoader;
  
  public ComposeClassLoader(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) {
    super(paramClassLoader1);
    this.mAppClassLoader = paramClassLoader2;
  }
  
  protected Class<?> loadClass(String paramString, boolean paramBoolean) throws ClassNotFoundException {
    Class<?> clazz;
    try {
      Class<?> clazz1 = this.mAppClassLoader.loadClass(paramString);
    } catch (ClassNotFoundException classNotFoundException1) {
      classNotFoundException1 = null;
    } 
    ClassNotFoundException classNotFoundException2 = classNotFoundException1;
    if (classNotFoundException1 == null)
      clazz = super.loadClass(paramString, paramBoolean); 
    if (clazz != null)
      return clazz; 
    throw new ClassNotFoundException();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\ComposeClassLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */