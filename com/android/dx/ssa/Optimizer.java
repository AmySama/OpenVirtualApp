package com.android.dx.ssa;

import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.TranslationAdvice;
import com.android.dx.ssa.back.LivenessAnalyzer;
import com.android.dx.ssa.back.SsaToRop;
import java.util.EnumSet;

public class Optimizer {
  private static TranslationAdvice advice;
  
  private static boolean preserveLocals = true;
  
  public static SsaMethod debugDeadCodeRemover(RopMethod paramRopMethod, int paramInt, boolean paramBoolean1, boolean paramBoolean2, TranslationAdvice paramTranslationAdvice) {
    preserveLocals = paramBoolean2;
    advice = paramTranslationAdvice;
    SsaMethod ssaMethod = SsaConverter.convertToSsaMethod(paramRopMethod, paramInt, paramBoolean1);
    DeadCodeRemover.process(ssaMethod);
    return ssaMethod;
  }
  
  public static SsaMethod debugEdgeSplit(RopMethod paramRopMethod, int paramInt, boolean paramBoolean1, boolean paramBoolean2, TranslationAdvice paramTranslationAdvice) {
    preserveLocals = paramBoolean2;
    advice = paramTranslationAdvice;
    return SsaConverter.testEdgeSplit(paramRopMethod, paramInt, paramBoolean1);
  }
  
  public static SsaMethod debugNoRegisterAllocation(RopMethod paramRopMethod, int paramInt, boolean paramBoolean1, boolean paramBoolean2, TranslationAdvice paramTranslationAdvice, EnumSet<OptionalStep> paramEnumSet) {
    preserveLocals = paramBoolean2;
    advice = paramTranslationAdvice;
    SsaMethod ssaMethod = SsaConverter.convertToSsaMethod(paramRopMethod, paramInt, paramBoolean1);
    runSsaFormSteps(ssaMethod, paramEnumSet);
    LivenessAnalyzer.constructInterferenceGraph(ssaMethod);
    return ssaMethod;
  }
  
  public static SsaMethod debugPhiPlacement(RopMethod paramRopMethod, int paramInt, boolean paramBoolean1, boolean paramBoolean2, TranslationAdvice paramTranslationAdvice) {
    preserveLocals = paramBoolean2;
    advice = paramTranslationAdvice;
    return SsaConverter.testPhiPlacement(paramRopMethod, paramInt, paramBoolean1);
  }
  
  public static SsaMethod debugRenaming(RopMethod paramRopMethod, int paramInt, boolean paramBoolean1, boolean paramBoolean2, TranslationAdvice paramTranslationAdvice) {
    preserveLocals = paramBoolean2;
    advice = paramTranslationAdvice;
    return SsaConverter.convertToSsaMethod(paramRopMethod, paramInt, paramBoolean1);
  }
  
  public static TranslationAdvice getAdvice() {
    return advice;
  }
  
  public static boolean getPreserveLocals() {
    return preserveLocals;
  }
  
  public static RopMethod optimize(RopMethod paramRopMethod, int paramInt, boolean paramBoolean1, boolean paramBoolean2, TranslationAdvice paramTranslationAdvice) {
    return optimize(paramRopMethod, paramInt, paramBoolean1, paramBoolean2, paramTranslationAdvice, EnumSet.allOf(OptionalStep.class));
  }
  
  public static RopMethod optimize(RopMethod paramRopMethod, int paramInt, boolean paramBoolean1, boolean paramBoolean2, TranslationAdvice paramTranslationAdvice, EnumSet<OptionalStep> paramEnumSet) {
    preserveLocals = paramBoolean2;
    advice = paramTranslationAdvice;
    SsaMethod ssaMethod = SsaConverter.convertToSsaMethod(paramRopMethod, paramInt, paramBoolean1);
    runSsaFormSteps(ssaMethod, paramEnumSet);
    RopMethod ropMethod2 = SsaToRop.convertToRopMethod(ssaMethod, false);
    RopMethod ropMethod1 = ropMethod2;
    if (ropMethod2.getBlocks().getRegCount() > advice.getMaxOptimalRegisterCount())
      ropMethod1 = optimizeMinimizeRegisters(paramRopMethod, paramInt, paramBoolean1, paramEnumSet); 
    return ropMethod1;
  }
  
  private static RopMethod optimizeMinimizeRegisters(RopMethod paramRopMethod, int paramInt, boolean paramBoolean, EnumSet<OptionalStep> paramEnumSet) {
    SsaMethod ssaMethod = SsaConverter.convertToSsaMethod(paramRopMethod, paramInt, paramBoolean);
    paramEnumSet = paramEnumSet.clone();
    paramEnumSet.remove(OptionalStep.CONST_COLLECTOR);
    runSsaFormSteps(ssaMethod, paramEnumSet);
    return SsaToRop.convertToRopMethod(ssaMethod, true);
  }
  
  private static void runSsaFormSteps(SsaMethod paramSsaMethod, EnumSet<OptionalStep> paramEnumSet) {
    boolean bool2;
    if (paramEnumSet.contains(OptionalStep.MOVE_PARAM_COMBINER))
      MoveParamCombiner.process(paramSsaMethod); 
    boolean bool = paramEnumSet.contains(OptionalStep.SCCP);
    boolean bool1 = false;
    if (bool) {
      SCCP.process(paramSsaMethod);
      DeadCodeRemover.process(paramSsaMethod);
      bool2 = false;
    } else {
      bool2 = true;
    } 
    if (paramEnumSet.contains(OptionalStep.LITERAL_UPGRADE)) {
      LiteralOpUpgrader.process(paramSsaMethod);
      DeadCodeRemover.process(paramSsaMethod);
      bool2 = false;
    } 
    paramEnumSet.remove(OptionalStep.ESCAPE_ANALYSIS);
    if (paramEnumSet.contains(OptionalStep.ESCAPE_ANALYSIS)) {
      EscapeAnalysis.process(paramSsaMethod);
      DeadCodeRemover.process(paramSsaMethod);
      bool2 = false;
    } 
    if (paramEnumSet.contains(OptionalStep.CONST_COLLECTOR)) {
      ConstCollector.process(paramSsaMethod);
      DeadCodeRemover.process(paramSsaMethod);
      bool2 = bool1;
    } 
    if (bool2)
      DeadCodeRemover.process(paramSsaMethod); 
    PhiTypeResolver.process(paramSsaMethod);
  }
  
  public enum OptionalStep {
    CONST_COLLECTOR, ESCAPE_ANALYSIS, LITERAL_UPGRADE, MOVE_PARAM_COMBINER, SCCP;
    
    static {
      CONST_COLLECTOR = new OptionalStep("CONST_COLLECTOR", 3);
      OptionalStep optionalStep = new OptionalStep("ESCAPE_ANALYSIS", 4);
      ESCAPE_ANALYSIS = optionalStep;
      $VALUES = new OptionalStep[] { MOVE_PARAM_COMBINER, SCCP, LITERAL_UPGRADE, CONST_COLLECTOR, optionalStep };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\Optimizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */