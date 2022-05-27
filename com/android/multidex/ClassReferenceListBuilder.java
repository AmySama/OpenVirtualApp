package com.android.multidex;

import com.android.dx.cf.direct.DirectClassFile;
import com.android.dx.cf.iface.FieldList;
import com.android.dx.cf.iface.MethodList;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstBaseMethodRef;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.TypeList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassReferenceListBuilder {
  private static final String CLASS_EXTENSION = ".class";
  
  private final Set<String> classNames = new HashSet<String>();
  
  private final Path path;
  
  public ClassReferenceListBuilder(Path paramPath) {
    this.path = paramPath;
  }
  
  private void addClassWithHierachy(String paramString) {
    if (this.classNames.contains(paramString))
      return; 
    try {
      Path path = this.path;
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append(paramString);
      stringBuilder.append(".class");
      DirectClassFile directClassFile = path.getClass(stringBuilder.toString());
      this.classNames.add(paramString);
      CstType cstType = directClassFile.getSuperclass();
      if (cstType != null)
        addClassWithHierachy(cstType.getClassType().getClassName()); 
      TypeList typeList = directClassFile.getInterfaces();
      int i = typeList.size();
      for (byte b = 0; b < i; b++)
        addClassWithHierachy(typeList.getType(b).getClassName()); 
    } catch (FileNotFoundException fileNotFoundException) {}
  }
  
  private void addDependencies(DirectClassFile paramDirectClassFile) {
    Constant[] arrayOfConstant = paramDirectClassFile.getConstantPool().getEntries();
    int i = arrayOfConstant.length;
    boolean bool = false;
    byte b;
    for (b = 0; b < i; b++) {
      Constant constant = arrayOfConstant[b];
      if (constant instanceof CstType) {
        checkDescriptor(((CstType)constant).getClassType().getDescriptor());
      } else if (constant instanceof CstFieldRef) {
        checkDescriptor(((CstFieldRef)constant).getType().getDescriptor());
      } else if (constant instanceof CstBaseMethodRef) {
        checkPrototype(((CstBaseMethodRef)constant).getPrototype());
      } 
    } 
    FieldList fieldList = paramDirectClassFile.getFields();
    i = fieldList.size();
    for (b = 0; b < i; b++)
      checkDescriptor(fieldList.get(b).getDescriptor().getString()); 
    MethodList methodList = paramDirectClassFile.getMethods();
    i = methodList.size();
    for (b = bool; b < i; b++)
      checkPrototype(Prototype.intern(methodList.get(b).getDescriptor().getString())); 
  }
  
  private void checkDescriptor(String paramString) {
    if (paramString.endsWith(";")) {
      int i = paramString.lastIndexOf('[');
      if (i < 0) {
        addClassWithHierachy(paramString.substring(1, paramString.length() - 1));
      } else {
        addClassWithHierachy(paramString.substring(i + 2, paramString.length() - 1));
      } 
    } 
  }
  
  private void checkPrototype(Prototype paramPrototype) {
    checkDescriptor(paramPrototype.getReturnType().getDescriptor());
    StdTypeList stdTypeList = paramPrototype.getParameterTypes();
    for (byte b = 0; b < stdTypeList.size(); b++)
      checkDescriptor(stdTypeList.get(b).getDescriptor()); 
  }
  
  @Deprecated
  public static void main(String[] paramArrayOfString) {
    MainDexListBuilder.main(paramArrayOfString);
  }
  
  public void addRoots(ZipFile paramZipFile) throws IOException {
    Enumeration<? extends ZipEntry> enumeration1 = paramZipFile.entries();
    while (enumeration1.hasMoreElements()) {
      String str = ((ZipEntry)enumeration1.nextElement()).getName();
      if (str.endsWith(".class"))
        this.classNames.add(str.substring(0, str.length() - 6)); 
    } 
    Enumeration<? extends ZipEntry> enumeration2 = paramZipFile.entries();
    while (enumeration2.hasMoreElements()) {
      String str = ((ZipEntry)enumeration2.nextElement()).getName();
      if (str.endsWith(".class"))
        try {
          DirectClassFile directClassFile = this.path.getClass(str);
          addDependencies(directClassFile);
        } catch (FileNotFoundException fileNotFoundException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Class ");
          stringBuilder.append(str);
          stringBuilder.append(" is missing form original class path ");
          stringBuilder.append(this.path);
          throw new IOException(stringBuilder.toString(), fileNotFoundException);
        }  
    } 
  }
  
  Set<String> getClassNames() {
    return this.classNames;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\multidex\ClassReferenceListBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */