package com.swift.sandhook.xposedcompat.classloaders;

public class ProxyClassLoader extends ClassLoader {
  private final ClassLoader mClassLoader;
  
  public ProxyClassLoader(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) {
    super(paramClassLoader1);
    this.mClassLoader = paramClassLoader2;
  }
  
  protected Class<?> loadClass(String paramString, boolean paramBoolean) throws ClassNotFoundException {
    Class<?> clazz;
    try {
      Class<?> clazz1 = this.mClassLoader.loadClass(paramString);
    } catch (ClassNotFoundException classNotFoundException1) {
      classNotFoundException1 = null;
    } 
    ClassNotFoundException classNotFoundException2 = classNotFoundException1;
    if (classNotFoundException1 == null) {
      clazz = super.loadClass(paramString, paramBoolean);
      if (clazz == null)
        throw new ClassNotFoundException(); 
    } 
    return clazz;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompat\classloaders\ProxyClassLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */