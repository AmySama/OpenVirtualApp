package com.android.dx.cf.attrib;

import com.android.dx.cf.code.ByteCatchList;
import com.android.dx.cf.code.BytecodeArray;
import com.android.dx.cf.iface.AttributeList;
import com.android.dx.util.MutabilityException;

public final class AttCode extends BaseAttribute {
  public static final String ATTRIBUTE_NAME = "Code";
  
  private final AttributeList attributes;
  
  private final ByteCatchList catches;
  
  private final BytecodeArray code;
  
  private final int maxLocals;
  
  private final int maxStack;
  
  public AttCode(int paramInt1, int paramInt2, BytecodeArray paramBytecodeArray, ByteCatchList paramByteCatchList, AttributeList paramAttributeList) {
    super("Code");
    if (paramInt1 >= 0) {
      if (paramInt2 >= 0) {
        if (paramBytecodeArray != null)
          try {
            boolean bool = paramByteCatchList.isMutable();
            if (!bool)
              try {
                bool = paramAttributeList.isMutable();
                if (!bool) {
                  this.maxStack = paramInt1;
                  this.maxLocals = paramInt2;
                  this.code = paramBytecodeArray;
                  this.catches = paramByteCatchList;
                  this.attributes = paramAttributeList;
                  return;
                } 
                MutabilityException mutabilityException1 = new MutabilityException();
                this("attributes.isMutable()");
                throw mutabilityException1;
              } catch (NullPointerException nullPointerException) {
                throw new NullPointerException("attributes == null");
              }  
            MutabilityException mutabilityException = new MutabilityException();
            this("catches.isMutable()");
            throw mutabilityException;
          } catch (NullPointerException nullPointerException) {
            throw new NullPointerException("catches == null");
          }  
        throw new NullPointerException("code == null");
      } 
      throw new IllegalArgumentException("maxLocals < 0");
    } 
    throw new IllegalArgumentException("maxStack < 0");
  }
  
  public int byteLength() {
    return this.code.byteLength() + 10 + this.catches.byteLength() + this.attributes.byteLength();
  }
  
  public AttributeList getAttributes() {
    return this.attributes;
  }
  
  public ByteCatchList getCatches() {
    return this.catches;
  }
  
  public BytecodeArray getCode() {
    return this.code;
  }
  
  public int getMaxLocals() {
    return this.maxLocals;
  }
  
  public int getMaxStack() {
    return this.maxStack;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttCode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */