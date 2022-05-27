package com.android.dx.command.dump;

import com.android.dx.cf.code.ConcreteMethod;
import com.android.dx.cf.code.Ropper;
import com.android.dx.cf.direct.AttributeFactory;
import com.android.dx.cf.direct.DirectClassFile;
import com.android.dx.cf.direct.StdAttributeFactory;
import com.android.dx.cf.iface.ClassFile;
import com.android.dx.cf.iface.Member;
import com.android.dx.cf.iface.Method;
import com.android.dx.cf.iface.ParseObserver;
import com.android.dx.dex.DexOptions;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.code.BasicBlock;
import com.android.dx.rop.code.BasicBlockList;
import com.android.dx.rop.code.DexTranslationAdvice;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.TranslationAdvice;
import com.android.dx.ssa.Optimizer;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;
import com.android.dx.util.IntList;
import java.io.PrintStream;

public class DotDumper implements ParseObserver {
  private final Args args;
  
  private final byte[] bytes;
  
  private DirectClassFile classFile;
  
  private final DexOptions dexOptions;
  
  private final String filePath;
  
  private final boolean optimize;
  
  private final boolean strictParse;
  
  DotDumper(byte[] paramArrayOfbyte, String paramString, Args paramArgs) {
    this.bytes = paramArrayOfbyte;
    this.filePath = paramString;
    this.strictParse = paramArgs.strictParse;
    this.optimize = paramArgs.optimize;
    this.args = paramArgs;
    this.dexOptions = new DexOptions();
  }
  
  static void dump(byte[] paramArrayOfbyte, String paramString, Args paramArgs) {
    (new DotDumper(paramArrayOfbyte, paramString, paramArgs)).run();
  }
  
  private void run() {
    ByteArray byteArray = new ByteArray(this.bytes);
    DirectClassFile directClassFile = new DirectClassFile(byteArray, this.filePath, this.strictParse);
    this.classFile = directClassFile;
    directClassFile.setAttributeFactory((AttributeFactory)StdAttributeFactory.THE_ONE);
    this.classFile.getMagic();
    directClassFile = new DirectClassFile(byteArray, this.filePath, this.strictParse);
    directClassFile.setAttributeFactory((AttributeFactory)StdAttributeFactory.THE_ONE);
    directClassFile.setObserver(this);
    directClassFile.getMagic();
  }
  
  public void changeIndent(int paramInt) {}
  
  public void endParsingMember(ByteArray paramByteArray, int paramInt, String paramString1, String paramString2, Member paramMember) {
    if (!(paramMember instanceof Method))
      return; 
    if (!shouldDumpMethod(paramString1))
      return; 
    ConcreteMethod concreteMethod = new ConcreteMethod((Method)paramMember, (ClassFile)this.classFile, true, true);
    DexTranslationAdvice dexTranslationAdvice = DexTranslationAdvice.THE_ONE;
    RopMethod ropMethod2 = Ropper.convert(concreteMethod, (TranslationAdvice)dexTranslationAdvice, this.classFile.getMethods(), this.dexOptions);
    RopMethod ropMethod1 = ropMethod2;
    if (this.optimize) {
      boolean bool = AccessFlags.isStatic(concreteMethod.getAccessFlags());
      ropMethod1 = Optimizer.optimize(ropMethod2, BaseDumper.computeParamWidth(concreteMethod, bool), bool, true, (TranslationAdvice)dexTranslationAdvice);
    } 
    PrintStream printStream2 = System.out;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("digraph ");
    stringBuilder2.append(paramString1);
    stringBuilder2.append("{");
    printStream2.println(stringBuilder2.toString());
    PrintStream printStream1 = System.out;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("\tfirst -> n");
    stringBuilder1.append(Hex.u2(ropMethod1.getFirstLabel()));
    stringBuilder1.append(";");
    printStream1.println(stringBuilder1.toString());
    BasicBlockList basicBlockList = ropMethod1.getBlocks();
    int i = basicBlockList.size();
    for (paramInt = 0; paramInt < i; paramInt++) {
      StringBuilder stringBuilder;
      BasicBlock basicBlock = basicBlockList.get(paramInt);
      int j = basicBlock.getLabel();
      IntList intList = basicBlock.getSuccessors();
      if (intList.size() == 0) {
        PrintStream printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("\tn");
        stringBuilder.append(Hex.u2(j));
        stringBuilder.append(" -> returns;");
        printStream.println(stringBuilder.toString());
      } else {
        StringBuilder stringBuilder3;
        if (stringBuilder.size() == 1) {
          printStream2 = System.out;
          stringBuilder3 = new StringBuilder();
          stringBuilder3.append("\tn");
          stringBuilder3.append(Hex.u2(j));
          stringBuilder3.append(" -> n");
          stringBuilder3.append(Hex.u2(stringBuilder.get(0)));
          stringBuilder3.append(";");
          printStream2.println(stringBuilder3.toString());
        } else {
          PrintStream printStream4 = System.out;
          StringBuilder stringBuilder4 = new StringBuilder();
          stringBuilder4.append("\tn");
          stringBuilder4.append(Hex.u2(j));
          stringBuilder4.append(" -> {");
          printStream4.print(stringBuilder4.toString());
          for (byte b = 0; b < stringBuilder.size(); b++) {
            int k = stringBuilder.get(b);
            if (k != stringBuilder3.getPrimarySuccessor()) {
              PrintStream printStream = System.out;
              StringBuilder stringBuilder5 = new StringBuilder();
              stringBuilder5.append(" n");
              stringBuilder5.append(Hex.u2(k));
              stringBuilder5.append(" ");
              printStream.print(stringBuilder5.toString());
            } 
          } 
          System.out.println("};");
          PrintStream printStream3 = System.out;
          stringBuilder4 = new StringBuilder();
          stringBuilder4.append("\tn");
          stringBuilder4.append(Hex.u2(j));
          stringBuilder4.append(" -> n");
          stringBuilder4.append(Hex.u2(stringBuilder3.getPrimarySuccessor()));
          stringBuilder4.append(" [label=\"primary\"];");
          printStream3.println(stringBuilder4.toString());
        } 
      } 
    } 
    System.out.println("}");
  }
  
  public void parsed(ByteArray paramByteArray, int paramInt1, int paramInt2, String paramString) {}
  
  protected boolean shouldDumpMethod(String paramString) {
    return (this.args.method == null || this.args.method.equals(paramString));
  }
  
  public void startParsingMember(ByteArray paramByteArray, int paramInt, String paramString1, String paramString2) {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\dump\DotDumper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */