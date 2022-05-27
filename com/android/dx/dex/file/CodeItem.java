package com.android.dx.dex.file;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.dex.code.DalvCode;
import com.android.dx.dex.code.DalvInsnList;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.io.PrintWriter;
import java.util.Iterator;

public final class CodeItem extends OffsettedItem {
  private static final int ALIGNMENT = 4;
  
  private static final int HEADER_SIZE = 16;
  
  private CatchStructs catches;
  
  private final DalvCode code;
  
  private DebugInfoItem debugInfo;
  
  private final boolean isStatic;
  
  private final CstMethodRef ref;
  
  private final TypeList throwsList;
  
  public CodeItem(CstMethodRef paramCstMethodRef, DalvCode paramDalvCode, boolean paramBoolean, TypeList paramTypeList) {
    super(4, -1);
    if (paramCstMethodRef != null) {
      if (paramDalvCode != null) {
        if (paramTypeList != null) {
          this.ref = paramCstMethodRef;
          this.code = paramDalvCode;
          this.isStatic = paramBoolean;
          this.throwsList = paramTypeList;
          this.catches = null;
          this.debugInfo = null;
          return;
        } 
        throw new NullPointerException("throwsList == null");
      } 
      throw new NullPointerException("code == null");
    } 
    throw new NullPointerException("ref == null");
  }
  
  private int getInsSize() {
    return this.ref.getParameterWordCount(this.isStatic);
  }
  
  private int getOutsSize() {
    return this.code.getInsns().getOutsSize();
  }
  
  private int getRegistersSize() {
    return this.code.getInsns().getRegistersSize();
  }
  
  private void writeCodes(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    DalvInsnList dalvInsnList = this.code.getInsns();
    try {
      dalvInsnList.writeTo(paramAnnotatedOutput);
      return;
    } catch (RuntimeException runtimeException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("...while writing instructions for ");
      stringBuilder.append(this.ref.toHuman());
      throw ExceptionWithContext.withContext(runtimeException, stringBuilder.toString());
    } 
  }
  
  public void addContents(DexFile paramDexFile) {
    MixedItemSection mixedItemSection = paramDexFile.getByteData();
    TypeIdsSection typeIdsSection = paramDexFile.getTypeIds();
    if (this.code.hasPositions() || this.code.hasLocals()) {
      DebugInfoItem debugInfoItem = new DebugInfoItem(this.code, this.isStatic, this.ref);
      this.debugInfo = debugInfoItem;
      mixedItemSection.add(debugInfoItem);
    } 
    if (this.code.hasAnyCatches()) {
      Iterator<Type> iterator1 = this.code.getCatchTypes().iterator();
      while (iterator1.hasNext())
        typeIdsSection.intern(iterator1.next()); 
      this.catches = new CatchStructs(this.code);
    } 
    Iterator<Constant> iterator = this.code.getInsnConstants().iterator();
    while (iterator.hasNext())
      paramDexFile.internIfAppropriate(iterator.next()); 
  }
  
  public void debugPrint(PrintWriter paramPrintWriter, String paramString, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.ref.toHuman());
    stringBuilder.append(":");
    paramPrintWriter.println(stringBuilder.toString());
    DalvInsnList dalvInsnList = this.code.getInsns();
    stringBuilder = new StringBuilder();
    stringBuilder.append("regs: ");
    stringBuilder.append(Hex.u2(getRegistersSize()));
    stringBuilder.append("; ins: ");
    stringBuilder.append(Hex.u2(getInsSize()));
    stringBuilder.append("; outs: ");
    stringBuilder.append(Hex.u2(getOutsSize()));
    paramPrintWriter.println(stringBuilder.toString());
    dalvInsnList.debugPrint(paramPrintWriter, paramString, paramBoolean);
    stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("  ");
    String str = stringBuilder.toString();
    if (this.catches != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("catches");
      this.catches.debugPrint(paramPrintWriter, str);
    } 
    if (this.debugInfo != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("debug info");
      this.debugInfo.debugPrint(paramPrintWriter, str);
    } 
  }
  
  public CstMethodRef getRef() {
    return this.ref;
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_CODE_ITEM;
  }
  
  protected void place0(Section paramSection, int paramInt) {
    final DexFile file = paramSection.getFile();
    this.code.assignIndices(new DalvCode.AssignIndicesCallback() {
          public int getIndex(Constant param1Constant) {
            IndexedItem indexedItem = file.findItemOrNull(param1Constant);
            return (indexedItem == null) ? -1 : indexedItem.getIndex();
          }
        });
    CatchStructs catchStructs = this.catches;
    if (catchStructs != null) {
      catchStructs.encode(dexFile);
      paramInt = this.catches.writeSize();
    } else {
      paramInt = 0;
    } 
    int i = this.code.getInsns().codeSize();
    int j = i;
    if ((i & 0x1) != 0)
      j = i + 1; 
    setWriteSize(j * 2 + 16 + paramInt);
  }
  
  public String toHuman() {
    return this.ref.toHuman();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("CodeItem{");
    stringBuilder.append(toHuman());
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  protected void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    boolean bool1;
    int n;
    int i1;
    boolean bool = paramAnnotatedOutput.annotates();
    int i = getRegistersSize();
    int j = getOutsSize();
    int k = getInsSize();
    int m = this.code.getInsns().codeSize();
    if ((m & 0x1) != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    CatchStructs catchStructs = this.catches;
    if (catchStructs == null) {
      n = 0;
    } else {
      n = catchStructs.triesSize();
    } 
    DebugInfoItem debugInfoItem = this.debugInfo;
    if (debugInfoItem == null) {
      i1 = 0;
    } else {
      i1 = debugInfoItem.getAbsoluteOffset();
    } 
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsetString());
      stringBuilder.append(' ');
      stringBuilder.append(this.ref.toHuman());
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  registers_size: ");
      stringBuilder.append(Hex.u2(i));
      paramAnnotatedOutput.annotate(2, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  ins_size:       ");
      stringBuilder.append(Hex.u2(k));
      paramAnnotatedOutput.annotate(2, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  outs_size:      ");
      stringBuilder.append(Hex.u2(j));
      paramAnnotatedOutput.annotate(2, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  tries_size:     ");
      stringBuilder.append(Hex.u2(n));
      paramAnnotatedOutput.annotate(2, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  debug_off:      ");
      stringBuilder.append(Hex.u4(i1));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("  insns_size:     ");
      stringBuilder.append(Hex.u4(m));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      if (this.throwsList.size() != 0) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("  throws ");
        stringBuilder.append(StdTypeList.toHuman(this.throwsList));
        paramAnnotatedOutput.annotate(0, stringBuilder.toString());
      } 
    } 
    paramAnnotatedOutput.writeShort(i);
    paramAnnotatedOutput.writeShort(k);
    paramAnnotatedOutput.writeShort(j);
    paramAnnotatedOutput.writeShort(n);
    paramAnnotatedOutput.writeInt(i1);
    paramAnnotatedOutput.writeInt(m);
    writeCodes(paramDexFile, paramAnnotatedOutput);
    if (this.catches != null) {
      if (bool1) {
        if (bool)
          paramAnnotatedOutput.annotate(2, "  padding: 0"); 
        paramAnnotatedOutput.writeShort(0);
      } 
      this.catches.writeTo(paramDexFile, paramAnnotatedOutput);
    } 
    if (bool && this.debugInfo != null) {
      paramAnnotatedOutput.annotate(0, "  debug info");
      this.debugInfo.annotateTo(paramDexFile, paramAnnotatedOutput, "    ");
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\CodeItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */