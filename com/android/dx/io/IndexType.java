package com.android.dx.io;

public enum IndexType {
  CALL_SITE_REF, FIELD_OFFSET, FIELD_REF, INLINE_METHOD, METHOD_AND_PROTO_REF, METHOD_HANDLE_REF, METHOD_REF, NONE, PROTO_REF, STRING_REF, TYPE_REF, UNKNOWN, VARIES, VTABLE_OFFSET;
  
  static {
    NONE = new IndexType("NONE", 1);
    VARIES = new IndexType("VARIES", 2);
    TYPE_REF = new IndexType("TYPE_REF", 3);
    STRING_REF = new IndexType("STRING_REF", 4);
    METHOD_REF = new IndexType("METHOD_REF", 5);
    FIELD_REF = new IndexType("FIELD_REF", 6);
    METHOD_AND_PROTO_REF = new IndexType("METHOD_AND_PROTO_REF", 7);
    CALL_SITE_REF = new IndexType("CALL_SITE_REF", 8);
    INLINE_METHOD = new IndexType("INLINE_METHOD", 9);
    VTABLE_OFFSET = new IndexType("VTABLE_OFFSET", 10);
    FIELD_OFFSET = new IndexType("FIELD_OFFSET", 11);
    METHOD_HANDLE_REF = new IndexType("METHOD_HANDLE_REF", 12);
    IndexType indexType = new IndexType("PROTO_REF", 13);
    PROTO_REF = indexType;
    $VALUES = new IndexType[] { 
        UNKNOWN, NONE, VARIES, TYPE_REF, STRING_REF, METHOD_REF, FIELD_REF, METHOD_AND_PROTO_REF, CALL_SITE_REF, INLINE_METHOD, 
        VTABLE_OFFSET, FIELD_OFFSET, METHOD_HANDLE_REF, indexType };
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\IndexType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */