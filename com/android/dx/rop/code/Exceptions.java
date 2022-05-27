package com.android.dx.rop.code;

import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;

public final class Exceptions {
  public static final StdTypeList LIST_Error;
  
  public static final StdTypeList LIST_Error_ArithmeticException;
  
  public static final StdTypeList LIST_Error_ClassCastException;
  
  public static final StdTypeList LIST_Error_NegativeArraySizeException;
  
  public static final StdTypeList LIST_Error_NullPointerException;
  
  public static final StdTypeList LIST_Error_Null_ArrayIndexOutOfBounds;
  
  public static final StdTypeList LIST_Error_Null_ArrayIndex_ArrayStore;
  
  public static final StdTypeList LIST_Error_Null_IllegalMonitorStateException;
  
  public static final Type TYPE_ArithmeticException = Type.intern("Ljava/lang/ArithmeticException;");
  
  public static final Type TYPE_ArrayIndexOutOfBoundsException = Type.intern("Ljava/lang/ArrayIndexOutOfBoundsException;");
  
  public static final Type TYPE_ArrayStoreException = Type.intern("Ljava/lang/ArrayStoreException;");
  
  public static final Type TYPE_ClassCastException = Type.intern("Ljava/lang/ClassCastException;");
  
  public static final Type TYPE_Error = Type.intern("Ljava/lang/Error;");
  
  public static final Type TYPE_IllegalMonitorStateException = Type.intern("Ljava/lang/IllegalMonitorStateException;");
  
  public static final Type TYPE_NegativeArraySizeException = Type.intern("Ljava/lang/NegativeArraySizeException;");
  
  public static final Type TYPE_NullPointerException = Type.intern("Ljava/lang/NullPointerException;");
  
  static {
    LIST_Error = StdTypeList.make(TYPE_Error);
    LIST_Error_ArithmeticException = StdTypeList.make(TYPE_Error, TYPE_ArithmeticException);
    LIST_Error_ClassCastException = StdTypeList.make(TYPE_Error, TYPE_ClassCastException);
    LIST_Error_NegativeArraySizeException = StdTypeList.make(TYPE_Error, TYPE_NegativeArraySizeException);
    LIST_Error_NullPointerException = StdTypeList.make(TYPE_Error, TYPE_NullPointerException);
    LIST_Error_Null_ArrayIndexOutOfBounds = StdTypeList.make(TYPE_Error, TYPE_NullPointerException, TYPE_ArrayIndexOutOfBoundsException);
    LIST_Error_Null_ArrayIndex_ArrayStore = StdTypeList.make(TYPE_Error, TYPE_NullPointerException, TYPE_ArrayIndexOutOfBoundsException, TYPE_ArrayStoreException);
    LIST_Error_Null_IllegalMonitorStateException = StdTypeList.make(TYPE_Error, TYPE_NullPointerException, TYPE_IllegalMonitorStateException);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\Exceptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */