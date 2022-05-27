package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.cst.CstLiteral64;
import com.android.dx.rop.cst.CstLiteralBits;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.BitSet;

public abstract class InsnFormat {
  public static final boolean ALLOW_EXTENDED_OPCODES = true;
  
  protected static int argIndex(DalvInsn paramDalvInsn) {
    int i = ((CstInteger)((CstInsn)paramDalvInsn).getConstant()).getValue();
    if (i >= 0)
      return i; 
    throw new IllegalArgumentException("bogus insn");
  }
  
  protected static String branchComment(DalvInsn paramDalvInsn) {
    String str;
    int i = ((TargetInsn)paramDalvInsn).getTargetOffset();
    if (i == (short)i) {
      str = Hex.s2(i);
    } else {
      str = Hex.s4(i);
    } 
    return str;
  }
  
  protected static String branchString(DalvInsn paramDalvInsn) {
    String str;
    int i = ((TargetInsn)paramDalvInsn).getTargetAddress();
    if (i == (char)i) {
      str = Hex.u2(i);
    } else {
      str = Hex.u4(i);
    } 
    return str;
  }
  
  protected static short codeUnit(int paramInt1, int paramInt2) {
    if ((paramInt1 & 0xFF) == paramInt1) {
      if ((paramInt2 & 0xFF) == paramInt2)
        return (short)(paramInt1 | paramInt2 << 8); 
      throw new IllegalArgumentException("high out of range 0..255");
    } 
    throw new IllegalArgumentException("low out of range 0..255");
  }
  
  protected static short codeUnit(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if ((paramInt1 & 0xF) == paramInt1) {
      if ((paramInt2 & 0xF) == paramInt2) {
        if ((paramInt3 & 0xF) == paramInt3) {
          if ((paramInt4 & 0xF) == paramInt4)
            return (short)(paramInt1 | paramInt2 << 4 | paramInt3 << 8 | paramInt4 << 12); 
          throw new IllegalArgumentException("n3 out of range 0..15");
        } 
        throw new IllegalArgumentException("n2 out of range 0..15");
      } 
      throw new IllegalArgumentException("n1 out of range 0..15");
    } 
    throw new IllegalArgumentException("n0 out of range 0..15");
  }
  
  protected static boolean isRegListSequential(RegisterSpecList paramRegisterSpecList) {
    int i = paramRegisterSpecList.size();
    if (i < 2)
      return true; 
    int j = paramRegisterSpecList.get(0).getReg();
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = paramRegisterSpecList.get(b);
      if (registerSpec.getReg() != j)
        return false; 
      j += registerSpec.getCategory();
    } 
    return true;
  }
  
  protected static String literalBitsComment(CstLiteralBits paramCstLiteralBits, int paramInt) {
    long l;
    StringBuilder stringBuilder = new StringBuilder(20);
    stringBuilder.append("#");
    if (paramCstLiteralBits instanceof CstLiteral64) {
      l = ((CstLiteral64)paramCstLiteralBits).getLongBits();
    } else {
      l = paramCstLiteralBits.getIntBits();
    } 
    if (paramInt != 4) {
      if (paramInt != 8) {
        if (paramInt != 16) {
          if (paramInt != 32) {
            if (paramInt == 64) {
              stringBuilder.append(Hex.u8(l));
            } else {
              throw new RuntimeException("shouldn't happen");
            } 
          } else {
            stringBuilder.append(Hex.u4((int)l));
          } 
        } else {
          stringBuilder.append(Hex.u2((int)l));
        } 
      } else {
        stringBuilder.append(Hex.u1((int)l));
      } 
    } else {
      stringBuilder.append(Hex.uNibble((int)l));
    } 
    return stringBuilder.toString();
  }
  
  protected static String literalBitsString(CstLiteralBits paramCstLiteralBits) {
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append('#');
    if (paramCstLiteralBits instanceof com.android.dx.rop.cst.CstKnownNull) {
      stringBuilder.append("null");
    } else {
      stringBuilder.append(paramCstLiteralBits.typeName());
      stringBuilder.append(' ');
      stringBuilder.append(paramCstLiteralBits.toHuman());
    } 
    return stringBuilder.toString();
  }
  
  protected static int makeByte(int paramInt1, int paramInt2) {
    if ((paramInt1 & 0xF) == paramInt1) {
      if ((paramInt2 & 0xF) == paramInt2)
        return paramInt1 | paramInt2 << 4; 
      throw new IllegalArgumentException("high out of range 0..15");
    } 
    throw new IllegalArgumentException("low out of range 0..15");
  }
  
  protected static short opcodeUnit(DalvInsn paramDalvInsn) {
    int i = paramDalvInsn.getOpcode().getOpcode();
    if (i >= 256 && i <= 65535)
      return (short)i; 
    throw new IllegalArgumentException("opcode out of range 0..65535");
  }
  
  protected static short opcodeUnit(DalvInsn paramDalvInsn, int paramInt) {
    if ((paramInt & 0xFF) == paramInt) {
      int i = paramDalvInsn.getOpcode().getOpcode();
      if ((i & 0xFF) == i)
        return (short)(i | paramInt << 8); 
      throw new IllegalArgumentException("opcode out of range 0..255");
    } 
    throw new IllegalArgumentException("arg out of range 0..255");
  }
  
  protected static String regListString(RegisterSpecList paramRegisterSpecList) {
    int i = paramRegisterSpecList.size();
    StringBuilder stringBuilder = new StringBuilder(i * 5 + 2);
    stringBuilder.append('{');
    for (byte b = 0; b < i; b++) {
      if (b != 0)
        stringBuilder.append(", "); 
      stringBuilder.append(paramRegisterSpecList.get(b).regString());
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  protected static String regRangeString(RegisterSpecList paramRegisterSpecList) {
    int i = paramRegisterSpecList.size();
    StringBuilder stringBuilder = new StringBuilder(30);
    stringBuilder.append("{");
    if (i != 0)
      if (i != 1) {
        RegisterSpec registerSpec1 = paramRegisterSpecList.get(i - 1);
        RegisterSpec registerSpec2 = registerSpec1;
        if (registerSpec1.getCategory() == 2)
          registerSpec2 = registerSpec1.withOffset(1); 
        stringBuilder.append(paramRegisterSpecList.get(0).regString());
        stringBuilder.append("..");
        stringBuilder.append(registerSpec2.regString());
      } else {
        stringBuilder.append(paramRegisterSpecList.get(0).regString());
      }  
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  protected static boolean signedFitsInByte(int paramInt) {
    boolean bool;
    if ((byte)paramInt == paramInt) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected static boolean signedFitsInNibble(int paramInt) {
    boolean bool;
    if (paramInt >= -8 && paramInt <= 7) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected static boolean signedFitsInShort(int paramInt) {
    boolean bool;
    if ((short)paramInt == paramInt) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected static boolean unsignedFitsInByte(int paramInt) {
    boolean bool;
    if (paramInt == (paramInt & 0xFF)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected static boolean unsignedFitsInNibble(int paramInt) {
    boolean bool;
    if (paramInt == (paramInt & 0xF)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected static boolean unsignedFitsInShort(int paramInt) {
    boolean bool;
    if (paramInt == (0xFFFF & paramInt)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected static void write(AnnotatedOutput paramAnnotatedOutput, short paramShort) {
    paramAnnotatedOutput.writeShort(paramShort);
  }
  
  protected static void write(AnnotatedOutput paramAnnotatedOutput, short paramShort, int paramInt) {
    write(paramAnnotatedOutput, paramShort, (short)paramInt, (short)(paramInt >> 16));
  }
  
  protected static void write(AnnotatedOutput paramAnnotatedOutput, short paramShort1, int paramInt, short paramShort2) {
    write(paramAnnotatedOutput, paramShort1, (short)paramInt, (short)(paramInt >> 16), paramShort2);
  }
  
  protected static void write(AnnotatedOutput paramAnnotatedOutput, short paramShort1, int paramInt, short paramShort2, short paramShort3) {
    write(paramAnnotatedOutput, paramShort1, (short)paramInt, (short)(paramInt >> 16), paramShort2, paramShort3);
  }
  
  protected static void write(AnnotatedOutput paramAnnotatedOutput, short paramShort, long paramLong) {
    write(paramAnnotatedOutput, paramShort, (short)(int)paramLong, (short)(int)(paramLong >> 16L), (short)(int)(paramLong >> 32L), (short)(int)(paramLong >> 48L));
  }
  
  protected static void write(AnnotatedOutput paramAnnotatedOutput, short paramShort1, short paramShort2) {
    paramAnnotatedOutput.writeShort(paramShort1);
    paramAnnotatedOutput.writeShort(paramShort2);
  }
  
  protected static void write(AnnotatedOutput paramAnnotatedOutput, short paramShort1, short paramShort2, short paramShort3) {
    paramAnnotatedOutput.writeShort(paramShort1);
    paramAnnotatedOutput.writeShort(paramShort2);
    paramAnnotatedOutput.writeShort(paramShort3);
  }
  
  protected static void write(AnnotatedOutput paramAnnotatedOutput, short paramShort1, short paramShort2, short paramShort3, short paramShort4) {
    paramAnnotatedOutput.writeShort(paramShort1);
    paramAnnotatedOutput.writeShort(paramShort2);
    paramAnnotatedOutput.writeShort(paramShort3);
    paramAnnotatedOutput.writeShort(paramShort4);
  }
  
  protected static void write(AnnotatedOutput paramAnnotatedOutput, short paramShort1, short paramShort2, short paramShort3, short paramShort4, short paramShort5) {
    paramAnnotatedOutput.writeShort(paramShort1);
    paramAnnotatedOutput.writeShort(paramShort2);
    paramAnnotatedOutput.writeShort(paramShort3);
    paramAnnotatedOutput.writeShort(paramShort4);
    paramAnnotatedOutput.writeShort(paramShort5);
  }
  
  public boolean branchFits(TargetInsn paramTargetInsn) {
    return false;
  }
  
  public abstract int codeSize();
  
  public BitSet compatibleRegs(DalvInsn paramDalvInsn) {
    return new BitSet();
  }
  
  public abstract String insnArgString(DalvInsn paramDalvInsn);
  
  public abstract String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean);
  
  public abstract boolean isCompatible(DalvInsn paramDalvInsn);
  
  public final String listingString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    String str2 = paramDalvInsn.getOpcode().getName();
    String str3 = insnArgString(paramDalvInsn);
    String str1 = insnCommentString(paramDalvInsn, paramBoolean);
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append(str2);
    if (str3.length() != 0) {
      stringBuilder.append(' ');
      stringBuilder.append(str3);
    } 
    if (str1.length() != 0) {
      stringBuilder.append(" // ");
      stringBuilder.append(str1);
    } 
    return stringBuilder.toString();
  }
  
  public abstract void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\InsnFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */