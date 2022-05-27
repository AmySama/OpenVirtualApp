package com.android.dex.util;

public final class ByteArrayByteInput implements ByteInput {
  private final byte[] bytes;
  
  private int position;
  
  public ByteArrayByteInput(byte... paramVarArgs) {
    this.bytes = paramVarArgs;
  }
  
  public byte readByte() {
    byte[] arrayOfByte = this.bytes;
    int i = this.position;
    this.position = i + 1;
    return arrayOfByte[i];
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\de\\util\ByteArrayByteInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */