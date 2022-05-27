package com.android.dex;

public final class ClassData {
  private final Method[] directMethods;
  
  private final Field[] instanceFields;
  
  private final Field[] staticFields;
  
  private final Method[] virtualMethods;
  
  public ClassData(Field[] paramArrayOfField1, Field[] paramArrayOfField2, Method[] paramArrayOfMethod1, Method[] paramArrayOfMethod2) {
    this.staticFields = paramArrayOfField1;
    this.instanceFields = paramArrayOfField2;
    this.directMethods = paramArrayOfMethod1;
    this.virtualMethods = paramArrayOfMethod2;
  }
  
  public Field[] allFields() {
    Field[] arrayOfField1 = this.staticFields;
    Field[] arrayOfField2 = new Field[arrayOfField1.length + this.instanceFields.length];
    System.arraycopy(arrayOfField1, 0, arrayOfField2, 0, arrayOfField1.length);
    arrayOfField1 = this.instanceFields;
    System.arraycopy(arrayOfField1, 0, arrayOfField2, this.staticFields.length, arrayOfField1.length);
    return arrayOfField2;
  }
  
  public Method[] allMethods() {
    Method[] arrayOfMethod1 = this.directMethods;
    Method[] arrayOfMethod2 = new Method[arrayOfMethod1.length + this.virtualMethods.length];
    System.arraycopy(arrayOfMethod1, 0, arrayOfMethod2, 0, arrayOfMethod1.length);
    arrayOfMethod1 = this.virtualMethods;
    System.arraycopy(arrayOfMethod1, 0, arrayOfMethod2, this.directMethods.length, arrayOfMethod1.length);
    return arrayOfMethod2;
  }
  
  public Method[] getDirectMethods() {
    return this.directMethods;
  }
  
  public Field[] getInstanceFields() {
    return this.instanceFields;
  }
  
  public Field[] getStaticFields() {
    return this.staticFields;
  }
  
  public Method[] getVirtualMethods() {
    return this.virtualMethods;
  }
  
  public static class Field {
    private final int accessFlags;
    
    private final int fieldIndex;
    
    public Field(int param1Int1, int param1Int2) {
      this.fieldIndex = param1Int1;
      this.accessFlags = param1Int2;
    }
    
    public int getAccessFlags() {
      return this.accessFlags;
    }
    
    public int getFieldIndex() {
      return this.fieldIndex;
    }
  }
  
  public static class Method {
    private final int accessFlags;
    
    private final int codeOffset;
    
    private final int methodIndex;
    
    public Method(int param1Int1, int param1Int2, int param1Int3) {
      this.methodIndex = param1Int1;
      this.accessFlags = param1Int2;
      this.codeOffset = param1Int3;
    }
    
    public int getAccessFlags() {
      return this.accessFlags;
    }
    
    public int getCodeOffset() {
      return this.codeOffset;
    }
    
    public int getMethodIndex() {
      return this.methodIndex;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\ClassData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */