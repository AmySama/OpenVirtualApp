package com.android.dx.dex.file;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.dex.code.DalvCode;
import com.android.dx.dex.code.DalvInsnList;
import com.android.dx.dex.code.LocalList;
import com.android.dx.dex.code.PositionList;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.util.AnnotatedOutput;
import java.io.PrintWriter;

public class DebugInfoItem extends OffsettedItem {
  private static final int ALIGNMENT = 1;
  
  private static final boolean ENABLE_ENCODER_SELF_CHECK = false;
  
  private final DalvCode code;
  
  private byte[] encoded;
  
  private final boolean isStatic;
  
  private final CstMethodRef ref;
  
  public DebugInfoItem(DalvCode paramDalvCode, boolean paramBoolean, CstMethodRef paramCstMethodRef) {
    super(1, -1);
    if (paramDalvCode != null) {
      this.code = paramDalvCode;
      this.isStatic = paramBoolean;
      this.ref = paramCstMethodRef;
      return;
    } 
    throw new NullPointerException("code == null");
  }
  
  private byte[] encode(DexFile paramDexFile, String paramString, PrintWriter paramPrintWriter, AnnotatedOutput paramAnnotatedOutput, boolean paramBoolean) {
    return encode0(paramDexFile, paramString, paramPrintWriter, paramAnnotatedOutput, paramBoolean);
  }
  
  private byte[] encode0(DexFile paramDexFile, String paramString, PrintWriter paramPrintWriter, AnnotatedOutput paramAnnotatedOutput, boolean paramBoolean) {
    byte[] arrayOfByte;
    PositionList positionList = this.code.getPositions();
    LocalList localList = this.code.getLocals();
    DalvInsnList dalvInsnList = this.code.getInsns();
    DebugInfoEncoder debugInfoEncoder = new DebugInfoEncoder(positionList, localList, paramDexFile, dalvInsnList.codeSize(), dalvInsnList.getRegistersSize(), this.isStatic, this.ref);
    if (paramPrintWriter == null && paramAnnotatedOutput == null) {
      arrayOfByte = debugInfoEncoder.convert();
    } else {
      arrayOfByte = arrayOfByte.convertAndAnnotate(paramString, paramPrintWriter, paramAnnotatedOutput, paramBoolean);
    } 
    return arrayOfByte;
  }
  
  public void addContents(DexFile paramDexFile) {}
  
  public void annotateTo(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput, String paramString) {
    encode(paramDexFile, paramString, (PrintWriter)null, paramAnnotatedOutput, false);
  }
  
  public void debugPrint(PrintWriter paramPrintWriter, String paramString) {
    encode((DexFile)null, paramString, paramPrintWriter, (AnnotatedOutput)null, false);
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_DEBUG_INFO_ITEM;
  }
  
  protected void place0(Section paramSection, int paramInt) {
    try {
      byte[] arrayOfByte = encode(paramSection.getFile(), (String)null, (PrintWriter)null, (AnnotatedOutput)null, false);
      this.encoded = arrayOfByte;
      setWriteSize(arrayOfByte.length);
      return;
    } catch (RuntimeException runtimeException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("...while placing debug info for ");
      stringBuilder.append(this.ref.toHuman());
      throw ExceptionWithContext.withContext(runtimeException, stringBuilder.toString());
    } 
  }
  
  public String toHuman() {
    throw new RuntimeException("unsupported");
  }
  
  protected void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsetString());
      stringBuilder.append(" debug info");
      paramAnnotatedOutput.annotate(stringBuilder.toString());
      encode(paramDexFile, (String)null, (PrintWriter)null, paramAnnotatedOutput, true);
    } 
    paramAnnotatedOutput.write(this.encoded);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\DebugInfoItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */