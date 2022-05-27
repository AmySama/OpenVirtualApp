package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;

public final class CstMethodHandle extends TypedConstant {
  public static final int METHOD_HANDLE_TYPE_INSTANCE_GET = 3;
  
  public static final int METHOD_HANDLE_TYPE_INSTANCE_PUT = 2;
  
  public static final int METHOD_HANDLE_TYPE_INVOKE_CONSTRUCTOR = 6;
  
  public static final int METHOD_HANDLE_TYPE_INVOKE_DIRECT = 7;
  
  public static final int METHOD_HANDLE_TYPE_INVOKE_INSTANCE = 5;
  
  public static final int METHOD_HANDLE_TYPE_INVOKE_INTERFACE = 8;
  
  public static final int METHOD_HANDLE_TYPE_INVOKE_STATIC = 4;
  
  public static final int METHOD_HANDLE_TYPE_STATIC_GET = 1;
  
  public static final int METHOD_HANDLE_TYPE_STATIC_PUT = 0;
  
  private static final String[] TYPE_NAMES = new String[] { "static-put", "static-get", "instance-put", "instance-get", "invoke-static", "invoke-instance", "invoke-constructor", "invoke-direct", "invoke-interface" };
  
  private final Constant ref;
  
  private final int type;
  
  private CstMethodHandle(int paramInt, Constant paramConstant) {
    this.type = paramInt;
    this.ref = paramConstant;
  }
  
  public static String getMethodHandleTypeName(int paramInt) {
    return TYPE_NAMES[paramInt];
  }
  
  public static boolean isAccessor(int paramInt) {
    return !(paramInt != 0 && paramInt != 1 && paramInt != 2 && paramInt != 3);
  }
  
  public static boolean isInvocation(int paramInt) {
    switch (paramInt) {
      default:
        return false;
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
        break;
    } 
    return true;
  }
  
  public static CstMethodHandle make(int paramInt, Constant paramConstant) {
    StringBuilder stringBuilder;
    if (isAccessor(paramInt)) {
      if (!(paramConstant instanceof CstFieldRef)) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("ref has wrong type: ");
        stringBuilder1.append(paramConstant.getClass());
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
    } else {
      if (isInvocation(paramInt)) {
        if (!(paramConstant instanceof CstBaseMethodRef)) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("ref has wrong type: ");
          stringBuilder1.append(paramConstant.getClass());
          throw new IllegalArgumentException(stringBuilder1.toString());
        } 
        return new CstMethodHandle(paramInt, paramConstant);
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("type is out of range: ");
      stringBuilder.append(paramInt);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return new CstMethodHandle(paramInt, (Constant)stringBuilder);
  }
  
  protected int compareTo0(Constant paramConstant) {
    paramConstant = paramConstant;
    return (getMethodHandleType() == paramConstant.getMethodHandleType()) ? getRef().compareTo(paramConstant.getRef()) : Integer.compare(getMethodHandleType(), paramConstant.getMethodHandleType());
  }
  
  public int getMethodHandleType() {
    return this.type;
  }
  
  public Constant getRef() {
    return this.ref;
  }
  
  public Type getType() {
    return Type.METHOD_HANDLE;
  }
  
  public boolean isAccessor() {
    return isAccessor(this.type);
  }
  
  public boolean isCategory2() {
    return false;
  }
  
  public boolean isInvocation() {
    return isInvocation(this.type);
  }
  
  public String toHuman() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getMethodHandleTypeName(this.type));
    stringBuilder.append(",");
    stringBuilder.append(this.ref.toString());
    return stringBuilder.toString();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("method-handle{");
    stringBuilder.append(toHuman());
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "method handle";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstMethodHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */