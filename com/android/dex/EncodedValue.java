package com.android.dex;

import com.android.dex.util.ByteArrayByteInput;
import com.android.dex.util.ByteInput;

public final class EncodedValue implements Comparable<EncodedValue> {
  private final byte[] data;
  
  public EncodedValue(byte[] paramArrayOfbyte) {
    this.data = paramArrayOfbyte;
  }
  
  public ByteInput asByteInput() {
    return (ByteInput)new ByteArrayByteInput(this.data);
  }
  
  public int compareTo(EncodedValue paramEncodedValue) {
    int i = Math.min(this.data.length, paramEncodedValue.data.length);
    for (byte b = 0; b < i; b++) {
      byte[] arrayOfByte1 = this.data;
      byte b1 = arrayOfByte1[b];
      byte[] arrayOfByte2 = paramEncodedValue.data;
      if (b1 != arrayOfByte2[b])
        return (arrayOfByte1[b] & 0xFF) - (arrayOfByte2[b] & 0xFF); 
    } 
    return this.data.length - paramEncodedValue.data.length;
  }
  
  public byte[] getBytes() {
    return this.data;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Integer.toHexString(this.data[0] & 0xFF));
    stringBuilder.append("...(");
    stringBuilder.append(this.data.length);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public void writeTo(Dex.Section paramSection) {
    paramSection.write(this.data);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\EncodedValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */