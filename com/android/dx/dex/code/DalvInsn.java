package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.ssa.RegisterMapper;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import com.android.dx.util.TwoColumnOutput;
import java.util.BitSet;

public abstract class DalvInsn {
  private int address;
  
  private final Dop opcode;
  
  private final SourcePosition position;
  
  private final RegisterSpecList registers;
  
  public DalvInsn(Dop paramDop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList) {
    if (paramDop != null) {
      if (paramSourcePosition != null) {
        if (paramRegisterSpecList != null) {
          this.address = -1;
          this.opcode = paramDop;
          this.position = paramSourcePosition;
          this.registers = paramRegisterSpecList;
          return;
        } 
        throw new NullPointerException("registers == null");
      } 
      throw new NullPointerException("position == null");
    } 
    throw new NullPointerException("opcode == null");
  }
  
  public static SimpleInsn makeMove(SourcePosition paramSourcePosition, RegisterSpec paramRegisterSpec1, RegisterSpec paramRegisterSpec2) {
    Dop dop;
    int i = paramRegisterSpec1.getCategory();
    boolean bool = true;
    if (i != 1)
      bool = false; 
    boolean bool1 = paramRegisterSpec1.getType().isReference();
    i = paramRegisterSpec1.getReg();
    if ((paramRegisterSpec2.getReg() | i) < 16) {
      if (bool1) {
        dop = Dops.MOVE_OBJECT;
      } else if (bool) {
        dop = Dops.MOVE;
      } else {
        dop = Dops.MOVE_WIDE;
      } 
    } else if (i < 256) {
      if (bool1) {
        dop = Dops.MOVE_OBJECT_FROM16;
      } else if (bool) {
        dop = Dops.MOVE_FROM16;
      } else {
        dop = Dops.MOVE_WIDE_FROM16;
      } 
    } else if (bool1) {
      dop = Dops.MOVE_OBJECT_16;
    } else if (bool) {
      dop = Dops.MOVE_16;
    } else {
      dop = Dops.MOVE_WIDE_16;
    } 
    return new SimpleInsn(dop, paramSourcePosition, RegisterSpecList.make(paramRegisterSpec1, paramRegisterSpec2));
  }
  
  protected abstract String argString();
  
  public abstract int codeSize();
  
  public String cstComment() {
    throw new UnsupportedOperationException("Not supported.");
  }
  
  public String cstString() {
    throw new UnsupportedOperationException("Not supported.");
  }
  
  public DalvInsn expandedPrefix(BitSet paramBitSet) {
    RegisterSpecList registerSpecList = this.registers;
    boolean bool = paramBitSet.get(0);
    if (hasResult())
      paramBitSet.set(0); 
    registerSpecList = registerSpecList.subset(paramBitSet);
    if (hasResult())
      paramBitSet.set(0, bool); 
    return (registerSpecList.size() == 0) ? null : new HighRegisterPrefix(this.position, registerSpecList);
  }
  
  public DalvInsn expandedSuffix(BitSet paramBitSet) {
    if (hasResult() && !paramBitSet.get(0)) {
      RegisterSpec registerSpec = this.registers.get(0);
      return makeMove(this.position, registerSpec, registerSpec.withReg(0));
    } 
    return null;
  }
  
  public DalvInsn expandedVersion(BitSet paramBitSet) {
    return withRegisters(this.registers.withExpandedRegisters(0, hasResult(), paramBitSet));
  }
  
  public final int getAddress() {
    int i = this.address;
    if (i >= 0)
      return i; 
    throw new RuntimeException("address not yet known");
  }
  
  public DalvInsn getLowRegVersion() {
    return withRegisters(this.registers.withExpandedRegisters(0, hasResult(), null));
  }
  
  public final int getMinimumRegisterRequirement(BitSet paramBitSet) {
    boolean bool1;
    boolean bool = hasResult();
    int i = this.registers.size();
    int j = 0;
    if (bool && !paramBitSet.get(0)) {
      bool1 = this.registers.get(0).getCategory();
    } else {
      bool1 = false;
    } 
    while (bool < i) {
      int k = j;
      if (!paramBitSet.get(bool))
        k = j + this.registers.get(bool).getCategory(); 
      bool++;
      j = k;
    } 
    return Math.max(j, bool1);
  }
  
  public final int getNextAddress() {
    return getAddress() + codeSize();
  }
  
  public final Dop getOpcode() {
    return this.opcode;
  }
  
  public final SourcePosition getPosition() {
    return this.position;
  }
  
  public final RegisterSpecList getRegisters() {
    return this.registers;
  }
  
  public final boolean hasAddress() {
    boolean bool;
    if (this.address >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final boolean hasResult() {
    return this.opcode.hasResult();
  }
  
  public final String identifierString() {
    int i = this.address;
    return (i != -1) ? String.format("%04x", new Object[] { Integer.valueOf(i) }) : Hex.u4(System.identityHashCode(this));
  }
  
  public final String listingString(String paramString, int paramInt, boolean paramBoolean) {
    String str = listingString0(paramBoolean);
    if (str == null)
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(identifierString());
    stringBuilder.append(": ");
    paramString = stringBuilder.toString();
    int i = paramString.length();
    if (paramInt == 0) {
      paramInt = str.length();
    } else {
      paramInt -= i;
    } 
    return TwoColumnOutput.toString(paramString, i, "", str, paramInt);
  }
  
  protected abstract String listingString0(boolean paramBoolean);
  
  public final void setAddress(int paramInt) {
    if (paramInt >= 0) {
      this.address = paramInt;
      return;
    } 
    throw new IllegalArgumentException("address < 0");
  }
  
  public final String toString() {
    boolean bool;
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append(identifierString());
    stringBuilder.append(' ');
    stringBuilder.append(this.position);
    stringBuilder.append(": ");
    stringBuilder.append(this.opcode.getName());
    if (this.registers.size() != 0) {
      stringBuilder.append(this.registers.toHuman(" ", ", ", null));
      bool = true;
    } else {
      bool = false;
    } 
    String str = argString();
    if (str != null) {
      if (bool)
        stringBuilder.append(','); 
      stringBuilder.append(' ');
      stringBuilder.append(str);
    } 
    return stringBuilder.toString();
  }
  
  public DalvInsn withMapper(RegisterMapper paramRegisterMapper) {
    return withRegisters(paramRegisterMapper.map(getRegisters()));
  }
  
  public abstract DalvInsn withOpcode(Dop paramDop);
  
  public abstract DalvInsn withRegisterOffset(int paramInt);
  
  public abstract DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList);
  
  public abstract void writeTo(AnnotatedOutput paramAnnotatedOutput);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\DalvInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */