package com.android.dex;

import com.android.dex.util.ByteInput;

public final class EncodedValueReader {
  public static final int ENCODED_ANNOTATION = 29;
  
  public static final int ENCODED_ARRAY = 28;
  
  public static final int ENCODED_BOOLEAN = 31;
  
  public static final int ENCODED_BYTE = 0;
  
  public static final int ENCODED_CHAR = 3;
  
  public static final int ENCODED_DOUBLE = 17;
  
  public static final int ENCODED_ENUM = 27;
  
  public static final int ENCODED_FIELD = 25;
  
  public static final int ENCODED_FLOAT = 16;
  
  public static final int ENCODED_INT = 4;
  
  public static final int ENCODED_LONG = 6;
  
  public static final int ENCODED_METHOD = 26;
  
  public static final int ENCODED_METHOD_HANDLE = 22;
  
  public static final int ENCODED_METHOD_TYPE = 21;
  
  public static final int ENCODED_NULL = 30;
  
  public static final int ENCODED_SHORT = 2;
  
  public static final int ENCODED_STRING = 23;
  
  public static final int ENCODED_TYPE = 24;
  
  private static final int MUST_READ = -1;
  
  private int annotationType;
  
  private int arg;
  
  protected final ByteInput in;
  
  private int type = -1;
  
  public EncodedValueReader(EncodedValue paramEncodedValue) {
    this(paramEncodedValue.asByteInput());
  }
  
  public EncodedValueReader(EncodedValue paramEncodedValue, int paramInt) {
    this(paramEncodedValue.asByteInput(), paramInt);
  }
  
  public EncodedValueReader(ByteInput paramByteInput) {
    this.in = paramByteInput;
  }
  
  public EncodedValueReader(ByteInput paramByteInput, int paramInt) {
    this.in = paramByteInput;
    this.type = paramInt;
  }
  
  private void checkType(int paramInt) {
    if (peek() == paramInt)
      return; 
    throw new IllegalStateException(String.format("Expected %x but was %x", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(peek()) }));
  }
  
  public int getAnnotationType() {
    return this.annotationType;
  }
  
  public int peek() {
    if (this.type == -1) {
      int i = this.in.readByte() & 0xFF;
      this.type = i & 0x1F;
      this.arg = (i & 0xE0) >> 5;
    } 
    return this.type;
  }
  
  public int readAnnotation() {
    checkType(29);
    this.type = -1;
    this.annotationType = Leb128.readUnsignedLeb128(this.in);
    return Leb128.readUnsignedLeb128(this.in);
  }
  
  public int readAnnotationName() {
    return Leb128.readUnsignedLeb128(this.in);
  }
  
  public int readArray() {
    checkType(28);
    this.type = -1;
    return Leb128.readUnsignedLeb128(this.in);
  }
  
  public boolean readBoolean() {
    boolean bool;
    checkType(31);
    this.type = -1;
    if (this.arg != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public byte readByte() {
    checkType(0);
    this.type = -1;
    return (byte)EncodedValueCodec.readSignedInt(this.in, this.arg);
  }
  
  public char readChar() {
    checkType(3);
    this.type = -1;
    return (char)EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
  }
  
  public double readDouble() {
    checkType(17);
    this.type = -1;
    return Double.longBitsToDouble(EncodedValueCodec.readUnsignedLong(this.in, this.arg, true));
  }
  
  public int readEnum() {
    checkType(27);
    this.type = -1;
    return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
  }
  
  public int readField() {
    checkType(25);
    this.type = -1;
    return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
  }
  
  public float readFloat() {
    checkType(16);
    this.type = -1;
    return Float.intBitsToFloat(EncodedValueCodec.readUnsignedInt(this.in, this.arg, true));
  }
  
  public int readInt() {
    checkType(4);
    this.type = -1;
    return EncodedValueCodec.readSignedInt(this.in, this.arg);
  }
  
  public long readLong() {
    checkType(6);
    this.type = -1;
    return EncodedValueCodec.readSignedLong(this.in, this.arg);
  }
  
  public int readMethod() {
    checkType(26);
    this.type = -1;
    return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
  }
  
  public int readMethodHandle() {
    checkType(22);
    this.type = -1;
    return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
  }
  
  public int readMethodType() {
    checkType(21);
    this.type = -1;
    return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
  }
  
  public void readNull() {
    checkType(30);
    this.type = -1;
  }
  
  public short readShort() {
    checkType(2);
    this.type = -1;
    return (short)EncodedValueCodec.readSignedInt(this.in, this.arg);
  }
  
  public int readString() {
    checkType(23);
    this.type = -1;
    return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
  }
  
  public int readType() {
    checkType(24);
    this.type = -1;
    return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
  }
  
  public void skipValue() {
    int i = peek();
    if (i != 0) {
      if (i != 6) {
        if (i != 2) {
          if (i != 3) {
            if (i != 4) {
              if (i != 16) {
                if (i != 17) {
                  StringBuilder stringBuilder;
                  int j = 0;
                  int k = 0;
                  switch (i) {
                    default:
                      stringBuilder = new StringBuilder();
                      stringBuilder.append("Unexpected type: ");
                      stringBuilder.append(Integer.toHexString(this.type));
                      throw new DexException(stringBuilder.toString());
                    case 31:
                      readBoolean();
                      return;
                    case 30:
                      readNull();
                      return;
                    case 29:
                      j = readAnnotation();
                      while (k < j) {
                        readAnnotationName();
                        skipValue();
                        k++;
                      } 
                      return;
                    case 28:
                      i = readArray();
                      for (k = j; k < i; k++)
                        skipValue(); 
                      return;
                    case 27:
                      readEnum();
                      return;
                    case 26:
                      readMethod();
                      return;
                    case 25:
                      readField();
                      return;
                    case 24:
                      readType();
                      return;
                    case 23:
                      readString();
                      return;
                    case 22:
                      readMethodHandle();
                      return;
                    case 21:
                      break;
                  } 
                  readMethodType();
                } else {
                  readDouble();
                } 
              } else {
                readFloat();
              } 
            } else {
              readInt();
            } 
          } else {
            readChar();
          } 
        } else {
          readShort();
        } 
      } else {
        readLong();
      } 
    } else {
      readByte();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\EncodedValueReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */