package com.android.dx.cf.attrib;

import com.android.dx.rop.cst.ConstantPool;
import com.android.dx.util.ByteArray;

public final class RawAttribute extends BaseAttribute {
  private final ByteArray data;
  
  private final ConstantPool pool;
  
  public RawAttribute(String paramString, ByteArray paramByteArray, int paramInt1, int paramInt2, ConstantPool paramConstantPool) {
    this(paramString, paramByteArray.slice(paramInt1, paramInt2 + paramInt1), paramConstantPool);
  }
  
  public RawAttribute(String paramString, ByteArray paramByteArray, ConstantPool paramConstantPool) {
    super(paramString);
    if (paramByteArray != null) {
      this.data = paramByteArray;
      this.pool = paramConstantPool;
      return;
    } 
    throw new NullPointerException("data == null");
  }
  
  public int byteLength() {
    return this.data.size() + 6;
  }
  
  public ByteArray getData() {
    return this.data;
  }
  
  public ConstantPool getPool() {
    return this.pool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\RawAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */