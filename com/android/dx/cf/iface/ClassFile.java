package com.android.dx.cf.iface;

import com.android.dx.cf.code.BootstrapMethodsList;
import com.android.dx.rop.cst.ConstantPool;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.TypeList;

public interface ClassFile extends HasAttribute {
  int getAccessFlags();
  
  AttributeList getAttributes();
  
  BootstrapMethodsList getBootstrapMethods();
  
  ConstantPool getConstantPool();
  
  FieldList getFields();
  
  TypeList getInterfaces();
  
  int getMagic();
  
  int getMajorVersion();
  
  MethodList getMethods();
  
  int getMinorVersion();
  
  CstString getSourceFile();
  
  CstType getSuperclass();
  
  CstType getThisClass();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\iface\ClassFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */