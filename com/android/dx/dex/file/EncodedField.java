package com.android.dx.dex.file;

import com.android.dex.Leb128;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstString;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.io.PrintWriter;

public final class EncodedField extends EncodedMember implements Comparable<EncodedField> {
  private final CstFieldRef field;
  
  public EncodedField(CstFieldRef paramCstFieldRef, int paramInt) {
    super(paramInt);
    if (paramCstFieldRef != null) {
      this.field = paramCstFieldRef;
      return;
    } 
    throw new NullPointerException("field == null");
  }
  
  public void addContents(DexFile paramDexFile) {
    paramDexFile.getFieldIds().intern(this.field);
  }
  
  public int compareTo(EncodedField paramEncodedField) {
    return this.field.compareTo((Constant)paramEncodedField.field);
  }
  
  public void debugPrint(PrintWriter paramPrintWriter, boolean paramBoolean) {
    paramPrintWriter.println(toString());
  }
  
  public int encode(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput, int paramInt1, int paramInt2) {
    int i = paramDexFile.getFieldIds().indexOf(this.field);
    int j = i - paramInt1;
    paramInt1 = getAccessFlags();
    if (paramAnnotatedOutput.annotates()) {
      paramAnnotatedOutput.annotate(0, String.format("  [%x] %s", new Object[] { Integer.valueOf(paramInt2), this.field.toHuman() }));
      paramInt2 = Leb128.unsignedLeb128Size(j);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("    field_idx:    ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(paramInt2, stringBuilder.toString());
      paramInt2 = Leb128.unsignedLeb128Size(paramInt1);
      stringBuilder = new StringBuilder();
      stringBuilder.append("    access_flags: ");
      stringBuilder.append(AccessFlags.fieldString(paramInt1));
      paramAnnotatedOutput.annotate(paramInt2, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeUleb128(j);
    paramAnnotatedOutput.writeUleb128(paramInt1);
    return i;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof EncodedField;
    boolean bool1 = false;
    if (!bool)
      return false; 
    if (compareTo((EncodedField)paramObject) == 0)
      bool1 = true; 
    return bool1;
  }
  
  public CstString getName() {
    return this.field.getNat().getName();
  }
  
  public CstFieldRef getRef() {
    return this.field;
  }
  
  public int hashCode() {
    return this.field.hashCode();
  }
  
  public String toHuman() {
    return this.field.toHuman();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append(getClass().getName());
    stringBuilder.append('{');
    stringBuilder.append(Hex.u2(getAccessFlags()));
    stringBuilder.append(' ');
    stringBuilder.append(this.field);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\EncodedField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */