package com.android.dx.rop.code;

import com.android.dx.util.Hex;

public final class AccessFlags {
  public static final int ACC_ABSTRACT = 1024;
  
  public static final int ACC_ANNOTATION = 8192;
  
  public static final int ACC_BRIDGE = 64;
  
  public static final int ACC_CONSTRUCTOR = 65536;
  
  public static final int ACC_DECLARED_SYNCHRONIZED = 131072;
  
  public static final int ACC_ENUM = 16384;
  
  public static final int ACC_FINAL = 16;
  
  public static final int ACC_INTERFACE = 512;
  
  public static final int ACC_NATIVE = 256;
  
  public static final int ACC_PRIVATE = 2;
  
  public static final int ACC_PROTECTED = 4;
  
  public static final int ACC_PUBLIC = 1;
  
  public static final int ACC_STATIC = 8;
  
  public static final int ACC_STRICT = 2048;
  
  public static final int ACC_SUPER = 32;
  
  public static final int ACC_SYNCHRONIZED = 32;
  
  public static final int ACC_SYNTHETIC = 4096;
  
  public static final int ACC_TRANSIENT = 128;
  
  public static final int ACC_VARARGS = 128;
  
  public static final int ACC_VOLATILE = 64;
  
  public static final int CLASS_FLAGS = 30257;
  
  private static final int CONV_CLASS = 1;
  
  private static final int CONV_FIELD = 2;
  
  private static final int CONV_METHOD = 3;
  
  public static final int FIELD_FLAGS = 20703;
  
  public static final int INNER_CLASS_FLAGS = 30239;
  
  public static final int METHOD_FLAGS = 204287;
  
  public static String classString(int paramInt) {
    return humanHelper(paramInt, 30257, 1);
  }
  
  public static String fieldString(int paramInt) {
    return humanHelper(paramInt, 20703, 2);
  }
  
  private static String humanHelper(int paramInt1, int paramInt2, int paramInt3) {
    StringBuilder stringBuilder = new StringBuilder(80);
    int i = paramInt2 & paramInt1;
    paramInt1 &= paramInt2;
    if ((paramInt1 & 0x1) != 0)
      stringBuilder.append("|public"); 
    if ((paramInt1 & 0x2) != 0)
      stringBuilder.append("|private"); 
    if ((paramInt1 & 0x4) != 0)
      stringBuilder.append("|protected"); 
    if ((paramInt1 & 0x8) != 0)
      stringBuilder.append("|static"); 
    if ((paramInt1 & 0x10) != 0)
      stringBuilder.append("|final"); 
    if ((paramInt1 & 0x20) != 0)
      if (paramInt3 == 1) {
        stringBuilder.append("|super");
      } else {
        stringBuilder.append("|synchronized");
      }  
    if ((paramInt1 & 0x40) != 0)
      if (paramInt3 == 3) {
        stringBuilder.append("|bridge");
      } else {
        stringBuilder.append("|volatile");
      }  
    if ((paramInt1 & 0x80) != 0)
      if (paramInt3 == 3) {
        stringBuilder.append("|varargs");
      } else {
        stringBuilder.append("|transient");
      }  
    if ((paramInt1 & 0x100) != 0)
      stringBuilder.append("|native"); 
    if ((paramInt1 & 0x200) != 0)
      stringBuilder.append("|interface"); 
    if ((paramInt1 & 0x400) != 0)
      stringBuilder.append("|abstract"); 
    if ((paramInt1 & 0x800) != 0)
      stringBuilder.append("|strictfp"); 
    if ((paramInt1 & 0x1000) != 0)
      stringBuilder.append("|synthetic"); 
    if ((paramInt1 & 0x2000) != 0)
      stringBuilder.append("|annotation"); 
    if ((paramInt1 & 0x4000) != 0)
      stringBuilder.append("|enum"); 
    if ((0x10000 & paramInt1) != 0)
      stringBuilder.append("|constructor"); 
    if ((paramInt1 & 0x20000) != 0)
      stringBuilder.append("|declared_synchronized"); 
    if (i != 0 || stringBuilder.length() == 0) {
      stringBuilder.append('|');
      stringBuilder.append(Hex.u2(i));
    } 
    return stringBuilder.substring(1);
  }
  
  public static String innerClassString(int paramInt) {
    return humanHelper(paramInt, 30239, 1);
  }
  
  public static boolean isAbstract(int paramInt) {
    boolean bool;
    if ((paramInt & 0x400) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isAnnotation(int paramInt) {
    boolean bool;
    if ((paramInt & 0x2000) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isConstructor(int paramInt) {
    boolean bool;
    if ((paramInt & 0x10000) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isDeclaredSynchronized(int paramInt) {
    boolean bool;
    if ((paramInt & 0x20000) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isEnum(int paramInt) {
    boolean bool;
    if ((paramInt & 0x4000) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isInterface(int paramInt) {
    boolean bool;
    if ((paramInt & 0x200) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isNative(int paramInt) {
    boolean bool;
    if ((paramInt & 0x100) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isPrivate(int paramInt) {
    boolean bool;
    if ((paramInt & 0x2) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isProtected(int paramInt) {
    boolean bool;
    if ((paramInt & 0x4) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isPublic(int paramInt) {
    boolean bool = true;
    if ((paramInt & 0x1) == 0)
      bool = false; 
    return bool;
  }
  
  public static boolean isStatic(int paramInt) {
    boolean bool;
    if ((paramInt & 0x8) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isSynchronized(int paramInt) {
    boolean bool;
    if ((paramInt & 0x20) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static String methodString(int paramInt) {
    return humanHelper(paramInt, 204287, 3);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\AccessFlags.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */