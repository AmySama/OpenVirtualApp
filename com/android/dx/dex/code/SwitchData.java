package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import com.android.dx.util.IntList;

public final class SwitchData extends VariableSizeInsn {
  private final IntList cases;
  
  private final boolean packed;
  
  private final CodeAddress[] targets;
  
  private final CodeAddress user;
  
  public SwitchData(SourcePosition paramSourcePosition, CodeAddress paramCodeAddress, IntList paramIntList, CodeAddress[] paramArrayOfCodeAddress) {
    super(paramSourcePosition, RegisterSpecList.EMPTY);
    if (paramCodeAddress != null) {
      if (paramIntList != null) {
        if (paramArrayOfCodeAddress != null) {
          int i = paramIntList.size();
          if (i == paramArrayOfCodeAddress.length) {
            if (i <= 65535) {
              this.user = paramCodeAddress;
              this.cases = paramIntList;
              this.targets = paramArrayOfCodeAddress;
              this.packed = shouldPack(paramIntList);
              return;
            } 
            throw new IllegalArgumentException("too many cases");
          } 
          throw new IllegalArgumentException("cases / targets mismatch");
        } 
        throw new NullPointerException("targets == null");
      } 
      throw new NullPointerException("cases == null");
    } 
    throw new NullPointerException("user == null");
  }
  
  private static long packedCodeSize(IntList paramIntList) {
    int i = paramIntList.size();
    long l = paramIntList.get(0);
    l = (paramIntList.get(i - 1) - l + 1L) * 2L + 4L;
    if (l > 2147483647L)
      l = -1L; 
    return l;
  }
  
  private static boolean shouldPack(IntList paramIntList) {
    int i = paramIntList.size();
    boolean bool = true;
    if (i < 2)
      return true; 
    long l1 = packedCodeSize(paramIntList);
    long l2 = sparseCodeSize(paramIntList);
    if (l1 < 0L || l1 > l2 * 5L / 4L)
      bool = false; 
    return bool;
  }
  
  private static long sparseCodeSize(IntList paramIntList) {
    return paramIntList.size() * 4L + 2L;
  }
  
  protected String argString() {
    StringBuilder stringBuilder = new StringBuilder(100);
    int i = this.targets.length;
    for (byte b = 0; b < i; b++) {
      stringBuilder.append("\n    ");
      stringBuilder.append(this.cases.get(b));
      stringBuilder.append(": ");
      stringBuilder.append(this.targets[b]);
    } 
    return stringBuilder.toString();
  }
  
  public int codeSize() {
    long l;
    if (this.packed) {
      l = packedCodeSize(this.cases);
    } else {
      l = sparseCodeSize(this.cases);
    } 
    return (int)l;
  }
  
  public boolean isPacked() {
    return this.packed;
  }
  
  protected String listingString0(boolean paramBoolean) {
    String str;
    int i = this.user.getAddress();
    StringBuilder stringBuilder = new StringBuilder(100);
    int j = this.targets.length;
    if (this.packed) {
      str = "packed";
    } else {
      str = "sparse";
    } 
    stringBuilder.append(str);
    stringBuilder.append("-switch-payload // for switch @ ");
    stringBuilder.append(Hex.u2(i));
    for (byte b = 0; b < j; b++) {
      int k = this.targets[b].getAddress();
      stringBuilder.append("\n  ");
      stringBuilder.append(this.cases.get(b));
      stringBuilder.append(": ");
      stringBuilder.append(Hex.u4(k));
      stringBuilder.append(" // ");
      stringBuilder.append(Hex.s4(k - i));
    } 
    return stringBuilder.toString();
  }
  
  public DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList) {
    return new SwitchData(getPosition(), this.user, this.cases, this.targets);
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput) {
    int i = this.user.getAddress();
    int j = Dops.PACKED_SWITCH.getFormat().codeSize();
    int k = this.targets.length;
    boolean bool = this.packed;
    byte b = 0;
    int m = 0;
    if (bool) {
      int n;
      if (k == 0) {
        n = 0;
      } else {
        n = this.cases.get(0);
      } 
      if (k == 0) {
        i1 = 0;
      } else {
        i1 = this.cases.get(k - 1);
      } 
      k = i1 - n + 1;
      paramAnnotatedOutput.writeShort(256);
      paramAnnotatedOutput.writeShort(k);
      paramAnnotatedOutput.writeInt(n);
      b = 0;
      for (int i1 = m; i1 < k; i1++) {
        if (this.cases.get(b) > n + i1) {
          m = j;
        } else {
          m = this.targets[b].getAddress() - i;
          b++;
        } 
        paramAnnotatedOutput.writeInt(m);
      } 
    } else {
      byte b2;
      paramAnnotatedOutput.writeShort(512);
      paramAnnotatedOutput.writeShort(k);
      byte b1 = 0;
      while (true) {
        b2 = b;
        if (b1 < k) {
          paramAnnotatedOutput.writeInt(this.cases.get(b1));
          b1++;
          continue;
        } 
        break;
      } 
      while (b2 < k) {
        paramAnnotatedOutput.writeInt(this.targets[b2].getAddress() - i);
        b2++;
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\SwitchData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */