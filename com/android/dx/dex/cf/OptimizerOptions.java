package com.android.dx.dex.cf;

import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.TranslationAdvice;
import com.android.dx.ssa.Optimizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;

public class OptimizerOptions {
  private HashSet<String> dontOptimizeList;
  
  private HashSet<String> optimizeList;
  
  private boolean optimizeListsLoaded;
  
  private static HashSet<String> loadStringsFromFile(String paramString) {
    HashSet<String> hashSet = new HashSet();
    try {
      FileReader fileReader = new FileReader();
      this(paramString);
      BufferedReader bufferedReader = new BufferedReader();
      this(fileReader);
      while (true) {
        String str = bufferedReader.readLine();
        if (str != null) {
          hashSet.add(str);
          continue;
        } 
        fileReader.close();
        return hashSet;
      } 
    } catch (IOException iOException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Error with optimize list: ");
      stringBuilder.append(paramString);
      throw new RuntimeException(stringBuilder.toString(), iOException);
    } 
  }
  
  public void compareOptimizerStep(RopMethod paramRopMethod1, int paramInt, boolean paramBoolean, CfOptions paramCfOptions, TranslationAdvice paramTranslationAdvice, RopMethod paramRopMethod2) {
    EnumSet<Optimizer.OptionalStep> enumSet = EnumSet.allOf(Optimizer.OptionalStep.class);
    enumSet.remove(Optimizer.OptionalStep.CONST_COLLECTOR);
    paramRopMethod1 = Optimizer.optimize(paramRopMethod1, paramInt, paramBoolean, paramCfOptions.localInfo, paramTranslationAdvice, enumSet);
    paramInt = paramRopMethod2.getBlocks().getEffectiveInstructionCount();
    int i = paramRopMethod1.getBlocks().getEffectiveInstructionCount();
    System.err.printf("optimize step regs:(%d/%d/%.2f%%) insns:(%d/%d/%.2f%%)\n", new Object[] { Integer.valueOf(paramRopMethod2.getBlocks().getRegCount()), Integer.valueOf(paramRopMethod1.getBlocks().getRegCount()), Double.valueOf(((paramRopMethod1.getBlocks().getRegCount() - paramRopMethod2.getBlocks().getRegCount()) / paramRopMethod1.getBlocks().getRegCount()) * 100.0D), Integer.valueOf(paramInt), Integer.valueOf(i), Double.valueOf(((i - paramInt) / i) * 100.0D) });
  }
  
  public void loadOptimizeLists(String paramString1, String paramString2) {
    if (this.optimizeListsLoaded)
      return; 
    if (paramString1 == null || paramString2 == null) {
      if (paramString1 != null)
        this.optimizeList = loadStringsFromFile(paramString1); 
      if (paramString2 != null)
        this.dontOptimizeList = loadStringsFromFile(paramString2); 
      this.optimizeListsLoaded = true;
      return;
    } 
    throw new RuntimeException("optimize and don't optimize lists  are mutually exclusive.");
  }
  
  public boolean shouldOptimize(String paramString) {
    HashSet<String> hashSet = this.optimizeList;
    if (hashSet != null)
      return hashSet.contains(paramString); 
    hashSet = this.dontOptimizeList;
    return (hashSet != null) ? (hashSet.contains(paramString) ^ true) : true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\cf\OptimizerOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */