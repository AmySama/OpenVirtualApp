package com.android.dx;

import com.android.dx.dex.DexOptions;
import com.android.dx.dex.code.DalvCode;
import com.android.dx.dex.code.RopTranslator;
import com.android.dx.dex.file.ClassDefItem;
import com.android.dx.dex.file.DexFile;
import com.android.dx.dex.file.EncodedField;
import com.android.dx.dex.file.EncodedMethod;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.TypeList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public final class DexMaker {
  private static boolean didWarnBlacklistedMethods;
  
  private static boolean didWarnNonBaseDexClassLoader;
  
  private boolean markAsTrusted;
  
  private DexFile outputDex;
  
  private ClassLoader sharedClassLoader;
  
  private final Map<TypeId<?>, TypeDeclaration> types = new LinkedHashMap<TypeId<?>, TypeDeclaration>();
  
  private void doDeleteOatFiles(File paramFile, String paramString) {
    if (!paramFile.exists())
      return; 
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null)
      return; 
    int i = arrayOfFile.length;
    for (byte b = 0; b < i; b++) {
      File file = arrayOfFile[b];
      if (file.isFile() && file.getName().startsWith(paramString))
        file.delete(); 
    } 
  }
  
  private ClassLoader generateClassLoader(File paramFile1, File paramFile2, ClassLoader paramClassLoader) {
    try {
      boolean bool1;
      if (this.sharedClassLoader != null) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      if (paramClassLoader == null)
        if (this.sharedClassLoader != null) {
          paramClassLoader = this.sharedClassLoader;
        } else {
          paramClassLoader = null;
        }  
      Class<?> clazz = Class.forName("dalvik.system.BaseDexClassLoader");
      boolean bool2 = bool1;
      if (bool1) {
        bool2 = bool1;
        if (!clazz.isAssignableFrom(paramClassLoader.getClass())) {
          if (!paramClassLoader.getClass().getName().equals("java.lang.BootClassLoader") && !didWarnNonBaseDexClassLoader) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("Cannot share classloader as shared classloader '");
            stringBuilder.append(paramClassLoader);
            stringBuilder.append("' is not a subclass of '");
            stringBuilder.append(clazz);
            stringBuilder.append("'");
            printStream.println(stringBuilder.toString());
            didWarnNonBaseDexClassLoader = true;
          } 
          bool2 = false;
        } 
      } 
      boolean bool = this.markAsTrusted;
      if (bool)
        if (bool2) {
          try {
            paramClassLoader.getClass().getMethod("addDexPath", new Class[] { String.class, boolean.class }).invoke(paramClassLoader, new Object[] { paramFile1.getPath(), Boolean.valueOf(true) });
            return paramClassLoader;
          } catch (InvocationTargetException invocationTargetException) {
            if (invocationTargetException.getCause() instanceof SecurityException) {
              if (!didWarnBlacklistedMethods) {
                PrintStream printStream = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                this();
                stringBuilder.append("Cannot allow to call blacklisted super methods. This might break spying on system classes.");
                stringBuilder.append(invocationTargetException.getCause());
                printStream.println(stringBuilder.toString());
                didWarnBlacklistedMethods = true;
              } 
            } else {
              throw invocationTargetException;
            } 
          } 
        } else {
          return invocationTargetException.getConstructor(new Class[] { String.class, File.class, String.class, ClassLoader.class, boolean.class }).newInstance(new Object[] { paramFile1.getPath(), paramFile2.getAbsoluteFile(), null, paramClassLoader, Boolean.valueOf(true) });
        }  
      if (bool2) {
        paramClassLoader.getClass().getMethod("addDexPath", new Class[] { String.class }).invoke(paramClassLoader, new Object[] { paramFile1.getPath() });
        return paramClassLoader;
      } 
      return Class.forName("dalvik.system.DexClassLoader").getConstructor(new Class[] { String.class, String.class, String.class, ClassLoader.class }).newInstance(new Object[] { paramFile1.getPath(), paramFile2.getAbsolutePath(), null, paramClassLoader });
    } catch (ClassNotFoundException classNotFoundException) {
      throw new UnsupportedOperationException("load() requires a Dalvik VM", classNotFoundException);
    } catch (InvocationTargetException invocationTargetException) {
      throw new RuntimeException(invocationTargetException.getCause());
    } catch (InstantiationException instantiationException) {
      throw new AssertionError();
    } catch (NoSuchMethodException noSuchMethodException) {
      throw new AssertionError();
    } catch (IllegalAccessException illegalAccessException) {
      throw new AssertionError();
    } 
  }
  
  private String generateFileName() {
    Set<TypeId<?>> set = this.types.keySet();
    Iterator<TypeId<?>> iterator = set.iterator();
    int i = set.size();
    int[] arrayOfInt = new int[i];
    int j = 0;
    int k = 0;
    while (iterator.hasNext()) {
      TypeDeclaration typeDeclaration = getTypeDeclaration(iterator.next());
      Set set1 = typeDeclaration.methods.keySet();
      if (typeDeclaration.supertype != null) {
        arrayOfInt[k] = (typeDeclaration.supertype.hashCode() * 31 + typeDeclaration.interfaces.hashCode()) * 31 + set1.hashCode();
        k++;
      } 
    } 
    Arrays.sort(arrayOfInt);
    byte b = 1;
    k = j;
    j = b;
    while (k < i) {
      j = j * 31 + arrayOfInt[k];
      k++;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Generated_");
    stringBuilder.append(j);
    stringBuilder.append(".jar");
    return stringBuilder.toString();
  }
  
  public Code declare(MethodId<?, ?> paramMethodId, int paramInt) {
    StringBuilder stringBuilder;
    int i;
    TypeDeclaration typeDeclaration = getTypeDeclaration(paramMethodId.declaringType);
    if (!typeDeclaration.methods.containsKey(paramMethodId)) {
      if ((paramInt & 0xFFFFEF80) == 0) {
        i = paramInt;
        if ((paramInt & 0x20) != 0)
          i = paramInt & 0xFFFFFFDF | 0x20000; 
        if (!paramMethodId.isConstructor()) {
          paramInt = i;
          if (paramMethodId.isStaticInitializer()) {
            paramInt = i | 0x10000;
            MethodDeclaration methodDeclaration2 = new MethodDeclaration(paramMethodId, paramInt);
            typeDeclaration.methods.put(paramMethodId, methodDeclaration2);
            return methodDeclaration2.code;
          } 
          MethodDeclaration methodDeclaration1 = new MethodDeclaration(paramMethodId, paramInt);
          typeDeclaration.methods.put(paramMethodId, methodDeclaration1);
          return methodDeclaration1.code;
        } 
      } else {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected flag: ");
        stringBuilder.append(Integer.toHexString(paramInt));
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
    } else {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("already declared: ");
      stringBuilder1.append(stringBuilder);
      throw new IllegalStateException(stringBuilder1.toString());
    } 
    paramInt = i | 0x10000;
    MethodDeclaration methodDeclaration = new MethodDeclaration((MethodId<?, ?>)stringBuilder, paramInt);
    typeDeclaration.methods.put(stringBuilder, methodDeclaration);
    return methodDeclaration.code;
  }
  
  public void declare(FieldId<?, ?> paramFieldId, int paramInt, Object paramObject) {
    StringBuilder stringBuilder;
    TypeDeclaration typeDeclaration = getTypeDeclaration(paramFieldId.declaringType);
    if (!typeDeclaration.fields.containsKey(paramFieldId)) {
      if ((paramInt & 0xFFFFEF20) == 0) {
        if ((paramInt & 0x8) != 0 || paramObject == null) {
          paramObject = new FieldDeclaration(paramFieldId, paramInt, paramObject);
          typeDeclaration.fields.put(paramFieldId, paramObject);
          return;
        } 
        throw new IllegalArgumentException("staticValue is non-null, but field is not static");
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("Unexpected flag: ");
      stringBuilder.append(Integer.toHexString(paramInt));
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    paramObject = new StringBuilder();
    paramObject.append("already declared: ");
    paramObject.append(stringBuilder);
    throw new IllegalStateException(paramObject.toString());
  }
  
  public void declare(TypeId<?> paramTypeId1, String paramString, int paramInt, TypeId<?> paramTypeId2, TypeId<?>... paramVarArgs) {
    TypeDeclaration typeDeclaration = getTypeDeclaration(paramTypeId1);
    if ((paramInt & 0xFFFFEBEE) == 0) {
      if (!typeDeclaration.declared) {
        TypeDeclaration.access$002(typeDeclaration, true);
        TypeDeclaration.access$102(typeDeclaration, paramInt);
        TypeDeclaration.access$202(typeDeclaration, paramTypeId2);
        TypeDeclaration.access$302(typeDeclaration, paramString);
        TypeDeclaration.access$402(typeDeclaration, new TypeList(paramVarArgs));
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("already declared: ");
      stringBuilder1.append(paramTypeId1);
      throw new IllegalStateException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Unexpected flag: ");
    stringBuilder.append(Integer.toHexString(paramInt));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void deleteOldDex(File paramFile) {
    paramFile.delete();
    File file1 = new File(paramFile.getParent(), "/oat/");
    File file2 = new File(file1, "/arm/");
    File file3 = new File(file1, "/arm64/");
    if (!file1.exists())
      return; 
    String str = paramFile.getName().replaceAll(".jar", "");
    doDeleteOatFiles(file1, str);
    doDeleteOatFiles(file2, str);
    doDeleteOatFiles(file3, str);
  }
  
  public byte[] generate() {
    if (this.outputDex == null) {
      DexOptions dexOptions = new DexOptions();
      dexOptions.minSdkVersion = 13;
      this.outputDex = new DexFile(dexOptions);
    } 
    for (TypeDeclaration typeDeclaration : this.types.values())
      this.outputDex.add(typeDeclaration.toClassDefItem()); 
    try {
      return this.outputDex.toDex(null, false);
    } catch (IOException iOException) {
      throw new RuntimeException(iOException);
    } 
  }
  
  public ClassLoader generateAndLoad(ClassLoader paramClassLoader, File paramFile) throws IOException {
    return generateAndLoad(paramClassLoader, paramFile, generateFileName());
  }
  
  public ClassLoader generateAndLoad(ClassLoader paramClassLoader, File paramFile, String paramString) throws IOException {
    File file2 = paramFile;
    if (paramFile == null) {
      String str = System.getProperty("dexmaker.dexcache");
      if (str != null) {
        file2 = new File(str);
      } else {
        file2 = (new AppDataDirGuesser()).guess();
        if (file2 == null)
          throw new IllegalArgumentException("dexcache == null (and no default could be found; consider setting the 'dexmaker.dexcache' system property)"); 
      } 
    } 
    paramFile = new File(file2, paramString);
    if (paramFile.exists())
      try {
        deleteOldDex(paramFile);
      } finally {} 
    File file1 = paramFile.getParentFile();
    if (!file1.exists())
      file1.mkdirs(); 
    paramFile.createNewFile();
    JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(paramFile));
    JarEntry jarEntry = new JarEntry("classes.dex");
    byte[] arrayOfByte = generate();
    jarEntry.setSize(arrayOfByte.length);
    jarOutputStream.putNextEntry(jarEntry);
    jarOutputStream.write(arrayOfByte);
    jarOutputStream.closeEntry();
    jarOutputStream.close();
    return generateClassLoader(paramFile, file2, paramClassLoader);
  }
  
  DexFile getDexFile() {
    if (this.outputDex == null) {
      DexOptions dexOptions = new DexOptions();
      dexOptions.minSdkVersion = 13;
      this.outputDex = new DexFile(dexOptions);
    } 
    return this.outputDex;
  }
  
  TypeDeclaration getTypeDeclaration(TypeId<?> paramTypeId) {
    TypeDeclaration typeDeclaration1 = this.types.get(paramTypeId);
    TypeDeclaration typeDeclaration2 = typeDeclaration1;
    if (typeDeclaration1 == null) {
      typeDeclaration2 = new TypeDeclaration(paramTypeId);
      this.types.put(paramTypeId, typeDeclaration2);
    } 
    return typeDeclaration2;
  }
  
  public ClassLoader loadClassDirect(ClassLoader paramClassLoader, File paramFile, String paramString) {
    File file = new File(paramFile, paramString);
    return file.exists() ? generateClassLoader(file, paramFile, paramClassLoader) : null;
  }
  
  public void markAsTrusted() {
    this.markAsTrusted = true;
  }
  
  public void setSharedClassLoader(ClassLoader paramClassLoader) {
    this.sharedClassLoader = paramClassLoader;
  }
  
  static class FieldDeclaration {
    private final int accessFlags;
    
    final FieldId<?, ?> fieldId;
    
    private final Object staticValue;
    
    FieldDeclaration(FieldId<?, ?> param1FieldId, int param1Int, Object param1Object) {
      if ((param1Int & 0x8) != 0 || param1Object == null) {
        this.fieldId = param1FieldId;
        this.accessFlags = param1Int;
        this.staticValue = param1Object;
        return;
      } 
      throw new IllegalArgumentException("instance fields may not have a value");
    }
    
    public boolean isStatic() {
      boolean bool;
      if ((this.accessFlags & 0x8) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    EncodedField toEncodedField() {
      return new EncodedField(this.fieldId.constant, this.accessFlags);
    }
  }
  
  static class MethodDeclaration {
    private final Code code;
    
    private final int flags;
    
    final MethodId<?, ?> method;
    
    public MethodDeclaration(MethodId<?, ?> param1MethodId, int param1Int) {
      this.method = param1MethodId;
      this.flags = param1Int;
      this.code = new Code(this);
    }
    
    boolean isDirect() {
      boolean bool;
      if ((this.flags & 0x1000A) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    boolean isStatic() {
      boolean bool;
      if ((this.flags & 0x8) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    EncodedMethod toEncodedMethod(DexOptions param1DexOptions) {
      DalvCode dalvCode = RopTranslator.translate(new RopMethod(this.code.toBasicBlocks(), 0), 1, null, this.code.paramSize(), param1DexOptions);
      return new EncodedMethod(this.method.constant, this.flags, dalvCode, (TypeList)StdTypeList.EMPTY);
    }
  }
  
  static class TypeDeclaration {
    private ClassDefItem classDefItem;
    
    private boolean declared;
    
    private final Map<FieldId, DexMaker.FieldDeclaration> fields = new LinkedHashMap<FieldId, DexMaker.FieldDeclaration>();
    
    private int flags;
    
    private TypeList interfaces;
    
    private final Map<MethodId, DexMaker.MethodDeclaration> methods = new LinkedHashMap<MethodId, DexMaker.MethodDeclaration>();
    
    private String sourceFile;
    
    private TypeId<?> supertype;
    
    private final TypeId<?> type;
    
    TypeDeclaration(TypeId<?> param1TypeId) {
      this.type = param1TypeId;
    }
    
    ClassDefItem toClassDefItem() {
      if (this.declared) {
        DexOptions dexOptions = new DexOptions();
        dexOptions.minSdkVersion = 13;
        CstType cstType = this.type.constant;
        if (this.classDefItem == null) {
          this.classDefItem = new ClassDefItem(cstType, this.flags, this.supertype.constant, (TypeList)this.interfaces.ropTypes, new CstString(this.sourceFile));
          for (DexMaker.MethodDeclaration methodDeclaration : this.methods.values()) {
            EncodedMethod encodedMethod = methodDeclaration.toEncodedMethod(dexOptions);
            if (methodDeclaration.isDirect()) {
              this.classDefItem.addDirectMethod(encodedMethod);
              continue;
            } 
            this.classDefItem.addVirtualMethod(encodedMethod);
          } 
          for (DexMaker.FieldDeclaration fieldDeclaration : this.fields.values()) {
            EncodedField encodedField = fieldDeclaration.toEncodedField();
            if (fieldDeclaration.isStatic()) {
              this.classDefItem.addStaticField(encodedField, (Constant)Constants.getConstant(fieldDeclaration.staticValue));
              continue;
            } 
            this.classDefItem.addInstanceField(encodedField);
          } 
        } 
        return this.classDefItem;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Undeclared type ");
      stringBuilder.append(this.type);
      stringBuilder.append(" declares members: ");
      stringBuilder.append(this.fields.keySet());
      stringBuilder.append(" ");
      stringBuilder.append(this.methods.keySet());
      throw new IllegalStateException(stringBuilder.toString());
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\DexMaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */