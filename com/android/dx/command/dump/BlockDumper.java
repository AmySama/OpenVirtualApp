package com.android.dx.command.dump;

import com.android.dx.cf.code.BasicBlocker;
import com.android.dx.cf.code.ByteBlock;
import com.android.dx.cf.code.ByteBlockList;
import com.android.dx.cf.code.ByteCatchList;
import com.android.dx.cf.code.BytecodeArray;
import com.android.dx.cf.code.ConcreteMethod;
import com.android.dx.cf.code.Ropper;
import com.android.dx.cf.direct.AttributeFactory;
import com.android.dx.cf.direct.CodeObserver;
import com.android.dx.cf.direct.DirectClassFile;
import com.android.dx.cf.direct.StdAttributeFactory;
import com.android.dx.cf.iface.ClassFile;
import com.android.dx.cf.iface.Member;
import com.android.dx.cf.iface.Method;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.code.BasicBlock;
import com.android.dx.rop.code.BasicBlockList;
import com.android.dx.rop.code.DexTranslationAdvice;
import com.android.dx.rop.code.InsnList;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.TranslationAdvice;
import com.android.dx.rop.cst.CstType;
import com.android.dx.ssa.Optimizer;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;
import com.android.dx.util.IntList;
import java.io.PrintStream;

public class BlockDumper extends BaseDumper {
  protected DirectClassFile classFile;
  
  private boolean first;
  
  private final boolean optimize;
  
  private final boolean rop;
  
  protected boolean suppressDump;
  
  BlockDumper(byte[] paramArrayOfbyte, PrintStream paramPrintStream, String paramString, boolean paramBoolean, Args paramArgs) {
    super(paramArrayOfbyte, paramPrintStream, paramString, paramArgs);
    this.rop = paramBoolean;
    this.classFile = null;
    this.suppressDump = true;
    this.first = true;
    this.optimize = paramArgs.optimize;
  }
  
  public static void dump(byte[] paramArrayOfbyte, PrintStream paramPrintStream, String paramString, boolean paramBoolean, Args paramArgs) {
    (new BlockDumper(paramArrayOfbyte, paramPrintStream, paramString, paramBoolean, paramArgs)).dump();
  }
  
  private void regularDump(ConcreteMethod paramConcreteMethod) {
    BytecodeArray bytecodeArray = paramConcreteMethod.getCode();
    ByteArray byteArray = bytecodeArray.getBytes();
    ByteBlockList byteBlockList = BasicBlocker.identifyBlocks(paramConcreteMethod);
    int i = byteBlockList.size();
    CodeObserver codeObserver = new CodeObserver(byteArray, this);
    this.suppressDump = false;
    int j = 0;
    int k;
    for (k = 0; j < i; k = n) {
      ByteBlock byteBlock = byteBlockList.get(j);
      int m = byteBlock.getStart();
      int n = byteBlock.getEnd();
      if (k < m) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("dead code ");
        stringBuilder1.append(Hex.u2(k));
        stringBuilder1.append("..");
        stringBuilder1.append(Hex.u2(m));
        parsed(byteArray, k, m - k, stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("block ");
      stringBuilder.append(Hex.u2(byteBlock.getLabel()));
      stringBuilder.append(": ");
      stringBuilder.append(Hex.u2(m));
      stringBuilder.append("..");
      stringBuilder.append(Hex.u2(n));
      parsed(byteArray, m, 0, stringBuilder.toString());
      changeIndent(1);
      for (k = m; k < n; k += m) {
        m = bytecodeArray.parseInstruction(k, (BytecodeArray.Visitor)codeObserver);
        codeObserver.setPreviousOffset(k);
      } 
      IntList intList = byteBlock.getSuccessors();
      m = intList.size();
      if (m == 0) {
        parsed(byteArray, n, 0, "returns");
      } else {
        for (k = 0; k < m; k++) {
          int i1 = intList.get(k);
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("next ");
          stringBuilder1.append(Hex.u2(i1));
          parsed(byteArray, n, 0, stringBuilder1.toString());
        } 
      } 
      ByteCatchList byteCatchList = byteBlock.getCatches();
      m = byteCatchList.size();
      for (k = 0; k < m; k++) {
        String str;
        ByteCatchList.Item item = byteCatchList.get(k);
        CstType cstType = item.getExceptionClass();
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("catch ");
        if (cstType == CstType.OBJECT) {
          str = "<any>";
        } else {
          str = str.toHuman();
        } 
        stringBuilder1.append(str);
        stringBuilder1.append(" -> ");
        stringBuilder1.append(Hex.u2(item.getHandlerPc()));
        parsed(byteArray, n, 0, stringBuilder1.toString());
      } 
      changeIndent(-1);
      j++;
    } 
    j = byteArray.size();
    if (k < j) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("dead code ");
      stringBuilder.append(Hex.u2(k));
      stringBuilder.append("..");
      stringBuilder.append(Hex.u2(j));
      parsed(byteArray, k, j - k, stringBuilder.toString());
    } 
    this.suppressDump = true;
  }
  
  private void ropDump(ConcreteMethod paramConcreteMethod) {
    DexTranslationAdvice dexTranslationAdvice = DexTranslationAdvice.THE_ONE;
    ByteArray byteArray = paramConcreteMethod.getCode().getBytes();
    RopMethod ropMethod1 = Ropper.convert(paramConcreteMethod, (TranslationAdvice)dexTranslationAdvice, this.classFile.getMethods(), this.dexOptions);
    StringBuilder stringBuilder2 = new StringBuilder(2000);
    RopMethod ropMethod2 = ropMethod1;
    if (this.optimize) {
      boolean bool = AccessFlags.isStatic(paramConcreteMethod.getAccessFlags());
      ropMethod2 = Optimizer.optimize(ropMethod1, computeParamWidth(paramConcreteMethod, bool), bool, true, (TranslationAdvice)dexTranslationAdvice);
    } 
    BasicBlockList basicBlockList = ropMethod2.getBlocks();
    int[] arrayOfInt = basicBlockList.getLabelsInOrder();
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("first ");
    stringBuilder1.append(Hex.u2(ropMethod2.getFirstLabel()));
    stringBuilder1.append("\n");
    stringBuilder2.append(stringBuilder1.toString());
    int i = arrayOfInt.length;
    for (byte b = 0; b < i; b++) {
      int j = arrayOfInt[b];
      BasicBlock basicBlock = basicBlockList.get(basicBlockList.indexOfLabel(j));
      stringBuilder2.append("block ");
      stringBuilder2.append(Hex.u2(j));
      stringBuilder2.append("\n");
      IntList intList2 = ropMethod2.labelToPredecessors(j);
      int k = intList2.size();
      for (j = 0; j < k; j++) {
        stringBuilder2.append("  pred ");
        stringBuilder2.append(Hex.u2(intList2.get(j)));
        stringBuilder2.append("\n");
      } 
      InsnList insnList = basicBlock.getInsns();
      k = insnList.size();
      for (j = 0; j < k; j++) {
        insnList.get(j);
        stringBuilder2.append("  ");
        stringBuilder2.append(insnList.get(j).toHuman());
        stringBuilder2.append("\n");
      } 
      IntList intList1 = basicBlock.getSuccessors();
      k = intList1.size();
      if (k == 0) {
        stringBuilder2.append("  returns\n");
      } else {
        int m = basicBlock.getPrimarySuccessor();
        for (j = 0; j < k; j++) {
          int n = intList1.get(j);
          stringBuilder2.append("  next ");
          stringBuilder2.append(Hex.u2(n));
          if (k != 1 && n == m)
            stringBuilder2.append(" *"); 
          stringBuilder2.append("\n");
        } 
      } 
    } 
    this.suppressDump = false;
    parsed(byteArray, 0, byteArray.size(), stringBuilder2.toString());
    this.suppressDump = true;
  }
  
  public void changeIndent(int paramInt) {
    if (!this.suppressDump)
      super.changeIndent(paramInt); 
  }
  
  public void dump() {
    ByteArray byteArray = new ByteArray(getBytes());
    DirectClassFile directClassFile2 = new DirectClassFile(byteArray, getFilePath(), getStrictParse());
    this.classFile = directClassFile2;
    directClassFile2.setAttributeFactory((AttributeFactory)StdAttributeFactory.THE_ONE);
    this.classFile.getMagic();
    DirectClassFile directClassFile1 = new DirectClassFile(byteArray, getFilePath(), getStrictParse());
    directClassFile1.setAttributeFactory((AttributeFactory)StdAttributeFactory.THE_ONE);
    directClassFile1.setObserver(this);
    directClassFile1.getMagic();
  }
  
  public void endParsingMember(ByteArray paramByteArray, int paramInt, String paramString1, String paramString2, Member paramMember) {
    if (!(paramMember instanceof Method))
      return; 
    if (!shouldDumpMethod(paramString1))
      return; 
    if ((paramMember.getAccessFlags() & 0x500) != 0)
      return; 
    ConcreteMethod concreteMethod = new ConcreteMethod((Method)paramMember, (ClassFile)this.classFile, true, true);
    if (this.rop) {
      ropDump(concreteMethod);
    } else {
      regularDump(concreteMethod);
    } 
  }
  
  public void parsed(ByteArray paramByteArray, int paramInt1, int paramInt2, String paramString) {
    if (!this.suppressDump)
      super.parsed(paramByteArray, paramInt1, paramInt2, paramString); 
  }
  
  protected boolean shouldDumpMethod(String paramString) {
    return (this.args.method == null || this.args.method.equals(paramString));
  }
  
  public void startParsingMember(ByteArray paramByteArray, int paramInt, String paramString1, String paramString2) {
    if (paramString2.indexOf('(') < 0)
      return; 
    if (!shouldDumpMethod(paramString1))
      return; 
    this.suppressDump = false;
    if (this.first) {
      this.first = false;
    } else {
      parsed(paramByteArray, paramInt, 0, "\n");
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("method ");
    stringBuilder.append(paramString1);
    stringBuilder.append(" ");
    stringBuilder.append(paramString2);
    parsed(paramByteArray, paramInt, 0, stringBuilder.toString());
    this.suppressDump = true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\dump\BlockDumper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */