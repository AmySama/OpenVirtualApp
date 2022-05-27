package com.android.dex;

import com.android.dex.util.Unsigned;

public class MethodHandle implements Comparable<MethodHandle> {
  private final Dex dex;
  
  private final int fieldOrMethodId;
  
  private final MethodHandleType methodHandleType;
  
  private final int unused1;
  
  private final int unused2;
  
  public MethodHandle(Dex paramDex, MethodHandleType paramMethodHandleType, int paramInt1, int paramInt2, int paramInt3) {
    this.dex = paramDex;
    this.methodHandleType = paramMethodHandleType;
    this.unused1 = paramInt1;
    this.fieldOrMethodId = paramInt2;
    this.unused2 = paramInt3;
  }
  
  public int compareTo(MethodHandle paramMethodHandle) {
    MethodHandleType methodHandleType1 = this.methodHandleType;
    MethodHandleType methodHandleType2 = paramMethodHandle.methodHandleType;
    return (methodHandleType1 != methodHandleType2) ? methodHandleType1.compareTo(methodHandleType2) : Unsigned.compare(this.fieldOrMethodId, paramMethodHandle.fieldOrMethodId);
  }
  
  public int getFieldOrMethodId() {
    return this.fieldOrMethodId;
  }
  
  public MethodHandleType getMethodHandleType() {
    return this.methodHandleType;
  }
  
  public int getUnused1() {
    return this.unused1;
  }
  
  public int getUnused2() {
    return this.unused2;
  }
  
  public String toString() {
    Comparable comparable;
    if (this.dex == null) {
      comparable = new StringBuilder();
      comparable.append(this.methodHandleType);
      comparable.append(" ");
      comparable.append(this.fieldOrMethodId);
      return comparable.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.methodHandleType);
    stringBuilder.append(" ");
    if (this.methodHandleType.isField()) {
      comparable = this.dex.fieldIds().get(this.fieldOrMethodId);
    } else {
      comparable = this.dex.methodIds().get(this.fieldOrMethodId);
    } 
    stringBuilder.append(comparable);
    return stringBuilder.toString();
  }
  
  public void writeTo(Dex.Section paramSection) {
    paramSection.writeUnsignedShort(this.methodHandleType.value);
    paramSection.writeUnsignedShort(this.unused1);
    paramSection.writeUnsignedShort(this.fieldOrMethodId);
    paramSection.writeUnsignedShort(this.unused2);
  }
  
  public enum MethodHandleType {
    METHOD_HANDLE_TYPE_INSTANCE_GET,
    METHOD_HANDLE_TYPE_INSTANCE_PUT,
    METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR,
    METHOD_HANDLE_TYPE_INVOKE_DIRECT,
    METHOD_HANDLE_TYPE_INVOKE_INSTANCE,
    METHOD_HANDLE_TYPE_INVOKE_INTERFACE,
    METHOD_HANDLE_TYPE_INVOKE_STATIC,
    METHOD_HANDLE_TYPE_STATIC_GET,
    METHOD_HANDLE_TYPE_STATIC_PUT(0);
    
    private final int value;
    
    static {
      METHOD_HANDLE_TYPE_INSTANCE_PUT = new MethodHandleType("METHOD_HANDLE_TYPE_INSTANCE_PUT", 2, 2);
      METHOD_HANDLE_TYPE_INSTANCE_GET = new MethodHandleType("METHOD_HANDLE_TYPE_INSTANCE_GET", 3, 3);
      METHOD_HANDLE_TYPE_INVOKE_STATIC = new MethodHandleType("METHOD_HANDLE_TYPE_INVOKE_STATIC", 4, 4);
      METHOD_HANDLE_TYPE_INVOKE_INSTANCE = new MethodHandleType("METHOD_HANDLE_TYPE_INVOKE_INSTANCE", 5, 5);
      METHOD_HANDLE_TYPE_INVOKE_DIRECT = new MethodHandleType("METHOD_HANDLE_TYPE_INVOKE_DIRECT", 6, 6);
      METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR = new MethodHandleType("METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR", 7, 7);
      MethodHandleType methodHandleType = new MethodHandleType("METHOD_HANDLE_TYPE_INVOKE_INTERFACE", 8, 8);
      METHOD_HANDLE_TYPE_INVOKE_INTERFACE = methodHandleType;
      $VALUES = new MethodHandleType[] { METHOD_HANDLE_TYPE_STATIC_PUT, METHOD_HANDLE_TYPE_STATIC_GET, METHOD_HANDLE_TYPE_INSTANCE_PUT, METHOD_HANDLE_TYPE_INSTANCE_GET, METHOD_HANDLE_TYPE_INVOKE_STATIC, METHOD_HANDLE_TYPE_INVOKE_INSTANCE, METHOD_HANDLE_TYPE_INVOKE_DIRECT, METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR, methodHandleType };
    }
    
    MethodHandleType(int param1Int1) {
      this.value = param1Int1;
    }
    
    static MethodHandleType fromValue(int param1Int) {
      for (MethodHandleType methodHandleType : values()) {
        if (methodHandleType.value == param1Int)
          return methodHandleType; 
      } 
      throw new IllegalArgumentException(String.valueOf(param1Int));
    }
    
    public boolean isField() {
      int i = MethodHandle.null.$SwitchMap$com$android$dex$MethodHandle$MethodHandleType[ordinal()];
      return !(i != 1 && i != 2 && i != 3 && i != 4);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\MethodHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */