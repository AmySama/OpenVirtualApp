package com.android.dx.command.dump;

import com.android.dx.cf.code.ConcreteMethod;
import com.android.dx.cf.code.Ropper;
import com.android.dx.cf.iface.ClassFile;
import com.android.dx.cf.iface.Member;
import com.android.dx.cf.iface.Method;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.code.DexTranslationAdvice;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.TranslationAdvice;
import com.android.dx.ssa.Optimizer;
import com.android.dx.ssa.SsaBasicBlock;
import com.android.dx.ssa.SsaInsn;
import com.android.dx.ssa.SsaMethod;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;
import com.android.dx.util.IntList;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;

public class SsaDumper extends BlockDumper {
  private SsaDumper(byte[] paramArrayOfbyte, PrintStream paramPrintStream, String paramString, Args paramArgs) {
    super(paramArrayOfbyte, paramPrintStream, paramString, true, paramArgs);
  }
  
  public static void dump(byte[] paramArrayOfbyte, PrintStream paramPrintStream, String paramString, Args paramArgs) {
    (new SsaDumper(paramArrayOfbyte, paramPrintStream, paramString, paramArgs)).dump();
  }
  
  public void endParsingMember(ByteArray paramByteArray, int paramInt, String paramString1, String paramString2, Member paramMember) {
    SsaMethod ssaMethod;
    if (!(paramMember instanceof Method))
      return; 
    if (!shouldDumpMethod(paramString1))
      return; 
    if ((paramMember.getAccessFlags() & 0x500) != 0)
      return; 
    ConcreteMethod concreteMethod = new ConcreteMethod((Method)paramMember, (ClassFile)this.classFile, true, true);
    DexTranslationAdvice dexTranslationAdvice = DexTranslationAdvice.THE_ONE;
    RopMethod ropMethod = Ropper.convert(concreteMethod, (TranslationAdvice)dexTranslationAdvice, this.classFile.getMethods(), this.dexOptions);
    paramString1 = null;
    boolean bool = AccessFlags.isStatic(concreteMethod.getAccessFlags());
    paramInt = computeParamWidth(concreteMethod, bool);
    if (this.args.ssaStep == null) {
      ssaMethod = Optimizer.debugNoRegisterAllocation(ropMethod, paramInt, bool, true, (TranslationAdvice)dexTranslationAdvice, EnumSet.allOf(Optimizer.OptionalStep.class));
    } else if ("edge-split".equals(this.args.ssaStep)) {
      ssaMethod = Optimizer.debugEdgeSplit(ropMethod, paramInt, bool, true, (TranslationAdvice)dexTranslationAdvice);
    } else if ("phi-placement".equals(this.args.ssaStep)) {
      ssaMethod = Optimizer.debugPhiPlacement(ropMethod, paramInt, bool, true, (TranslationAdvice)dexTranslationAdvice);
    } else if ("renaming".equals(this.args.ssaStep)) {
      ssaMethod = Optimizer.debugRenaming(ropMethod, paramInt, bool, true, (TranslationAdvice)dexTranslationAdvice);
    } else if ("dead-code".equals(this.args.ssaStep)) {
      ssaMethod = Optimizer.debugDeadCodeRemover(ropMethod, paramInt, bool, true, (TranslationAdvice)dexTranslationAdvice);
    } 
    StringBuilder stringBuilder = new StringBuilder(2000);
    stringBuilder.append("first ");
    stringBuilder.append(Hex.u2(ssaMethod.blockIndexToRopLabel(ssaMethod.getEntryBlockIndex())));
    stringBuilder.append('\n');
    ArrayList<?> arrayList = (ArrayList)ssaMethod.getBlocks().clone();
    Collections.sort(arrayList, SsaBasicBlock.LABEL_COMPARATOR);
    Iterator<?> iterator = arrayList.iterator();
    while (true) {
      bool = iterator.hasNext();
      boolean bool1 = false;
      if (bool) {
        SsaBasicBlock ssaBasicBlock = (SsaBasicBlock)iterator.next();
        stringBuilder.append("block ");
        stringBuilder.append(Hex.u2(ssaBasicBlock.getRopLabel()));
        stringBuilder.append('\n');
        BitSet bitSet = ssaBasicBlock.getPredecessors();
        for (paramInt = bitSet.nextSetBit(0); paramInt >= 0; paramInt = bitSet.nextSetBit(paramInt + 1)) {
          stringBuilder.append("  pred ");
          stringBuilder.append(Hex.u2(ssaMethod.blockIndexToRopLabel(paramInt)));
          stringBuilder.append('\n');
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("  live in:");
        stringBuilder1.append(ssaBasicBlock.getLiveInRegs());
        stringBuilder.append(stringBuilder1.toString());
        stringBuilder.append("\n");
        for (SsaInsn ssaInsn : ssaBasicBlock.getInsns()) {
          stringBuilder.append("  ");
          stringBuilder.append(ssaInsn.toHuman());
          stringBuilder.append('\n');
        } 
        if (ssaBasicBlock.getSuccessors().cardinality() == 0) {
          stringBuilder.append("  returns\n");
        } else {
          int i = ssaBasicBlock.getPrimarySuccessorRopLabel();
          IntList intList = ssaBasicBlock.getRopLabelSuccessorList();
          int j = intList.size();
          for (paramInt = bool1; paramInt < j; paramInt++) {
            stringBuilder.append("  next ");
            stringBuilder.append(Hex.u2(intList.get(paramInt)));
            if (j != 1 && i == intList.get(paramInt))
              stringBuilder.append(" *"); 
            stringBuilder.append('\n');
          } 
        } 
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("  live out:");
        stringBuilder1.append(ssaBasicBlock.getLiveOutRegs());
        stringBuilder.append(stringBuilder1.toString());
        stringBuilder.append("\n");
        continue;
      } 
      this.suppressDump = false;
      parsed(paramByteArray, 0, paramByteArray.size(), stringBuilder.toString());
      this.suppressDump = true;
      return;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\dump\SsaDumper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */