package com.android.dx.dex.file;

import com.android.dex.Leb128;
import com.android.dx.dex.code.DalvCode;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstBaseMethodRef;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.io.PrintWriter;

public final class EncodedMethod extends EncodedMember implements Comparable<EncodedMethod> {
  private final CodeItem code;
  
  private final CstMethodRef method;
  
  public EncodedMethod(CstMethodRef paramCstMethodRef, int paramInt, DalvCode paramDalvCode, TypeList paramTypeList) {
    super(paramInt);
    if (paramCstMethodRef != null) {
      this.method = paramCstMethodRef;
      if (paramDalvCode == null) {
        this.code = null;
      } else {
        boolean bool;
        if ((paramInt & 0x8) != 0) {
          bool = true;
        } else {
          bool = false;
        } 
        this.code = new CodeItem(paramCstMethodRef, paramDalvCode, bool, paramTypeList);
      } 
      return;
    } 
    throw new NullPointerException("method == null");
  }
  
  public void addContents(DexFile paramDexFile) {
    MethodIdsSection methodIdsSection = paramDexFile.getMethodIds();
    MixedItemSection mixedItemSection = paramDexFile.getWordData();
    methodIdsSection.intern((CstBaseMethodRef)this.method);
    CodeItem codeItem = this.code;
    if (codeItem != null)
      mixedItemSection.add(codeItem); 
  }
  
  public int compareTo(EncodedMethod paramEncodedMethod) {
    return this.method.compareTo((Constant)paramEncodedMethod.method);
  }
  
  public void debugPrint(PrintWriter paramPrintWriter, boolean paramBoolean) {
    StringBuilder stringBuilder;
    CodeItem codeItem = this.code;
    if (codeItem == null) {
      stringBuilder = new StringBuilder();
      stringBuilder.append(getRef().toHuman());
      stringBuilder.append(": abstract or native");
      paramPrintWriter.println(stringBuilder.toString());
    } else {
      stringBuilder.debugPrint(paramPrintWriter, "  ", paramBoolean);
    } 
  }
  
  public int encode(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput, int paramInt1, int paramInt2) {
    byte b;
    int i = paramDexFile.getMethodIds().indexOf((CstBaseMethodRef)this.method);
    int j = i - paramInt1;
    int k = getAccessFlags();
    int m = OffsettedItem.getAbsoluteOffsetOr0(this.code);
    if (m != 0) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    if ((k & 0x500) == 0) {
      b = 1;
    } else {
      b = 0;
    } 
    if (paramInt1 == b) {
      if (paramAnnotatedOutput.annotates()) {
        paramAnnotatedOutput.annotate(0, String.format("  [%x] %s", new Object[] { Integer.valueOf(paramInt2), this.method.toHuman() }));
        paramInt1 = Leb128.unsignedLeb128Size(j);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("    method_idx:   ");
        stringBuilder.append(Hex.u4(i));
        paramAnnotatedOutput.annotate(paramInt1, stringBuilder.toString());
        paramInt1 = Leb128.unsignedLeb128Size(k);
        stringBuilder = new StringBuilder();
        stringBuilder.append("    access_flags: ");
        stringBuilder.append(AccessFlags.methodString(k));
        paramAnnotatedOutput.annotate(paramInt1, stringBuilder.toString());
        paramInt1 = Leb128.unsignedLeb128Size(m);
        stringBuilder = new StringBuilder();
        stringBuilder.append("    code_off:     ");
        stringBuilder.append(Hex.u4(m));
        paramAnnotatedOutput.annotate(paramInt1, stringBuilder.toString());
      } 
      paramAnnotatedOutput.writeUleb128(j);
      paramAnnotatedOutput.writeUleb128(k);
      paramAnnotatedOutput.writeUleb128(m);
      return i;
    } 
    throw new UnsupportedOperationException("code vs. access_flags mismatch");
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof EncodedMethod;
    boolean bool1 = false;
    if (!bool)
      return false; 
    if (compareTo((EncodedMethod)paramObject) == 0)
      bool1 = true; 
    return bool1;
  }
  
  public final CstString getName() {
    return this.method.getNat().getName();
  }
  
  public final CstMethodRef getRef() {
    return this.method;
  }
  
  public final String toHuman() {
    return this.method.toHuman();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append(getClass().getName());
    stringBuilder.append('{');
    stringBuilder.append(Hex.u2(getAccessFlags()));
    stringBuilder.append(' ');
    stringBuilder.append(this.method);
    if (this.code != null) {
      stringBuilder.append(' ');
      stringBuilder.append(this.code);
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\EncodedMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */