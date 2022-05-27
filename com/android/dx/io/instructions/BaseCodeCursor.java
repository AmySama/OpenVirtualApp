package com.android.dx.io.instructions;

public abstract class BaseCodeCursor implements CodeCursor {
  private final AddressMap baseAddressMap = new AddressMap();
  
  private int cursor = 0;
  
  protected final void advance(int paramInt) {
    this.cursor += paramInt;
  }
  
  public final int baseAddressForCursor() {
    int i = this.baseAddressMap.get(this.cursor);
    if (i < 0)
      i = this.cursor; 
    return i;
  }
  
  public final int cursor() {
    return this.cursor;
  }
  
  public final void setBaseAddress(int paramInt1, int paramInt2) {
    this.baseAddressMap.put(paramInt1, paramInt2);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\BaseCodeCursor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */