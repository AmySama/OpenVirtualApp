package com.android.dx.ssa.back;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.ssa.PhiInsn;
import com.android.dx.ssa.SsaBasicBlock;
import com.android.dx.ssa.SsaInsn;
import com.android.dx.ssa.SsaMethod;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class LivenessAnalyzer {
  private SsaBasicBlock blockN;
  
  private final InterferenceGraph interference;
  
  private final BitSet liveOutBlocks;
  
  private NextFunction nextFunction;
  
  private final int regV;
  
  private final SsaMethod ssaMeth;
  
  private int statementIndex;
  
  private final BitSet visitedBlocks;
  
  private LivenessAnalyzer(SsaMethod paramSsaMethod, int paramInt, InterferenceGraph paramInterferenceGraph) {
    int i = paramSsaMethod.getBlocks().size();
    this.ssaMeth = paramSsaMethod;
    this.regV = paramInt;
    this.visitedBlocks = new BitSet(i);
    this.liveOutBlocks = new BitSet(i);
    this.interference = paramInterferenceGraph;
  }
  
  private static void coInterferePhiRegisters(InterferenceGraph paramInterferenceGraph, RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList) {
    int i = paramRegisterSpec.getReg();
    for (byte b = 0; b < paramRegisterSpecList.size(); b++)
      paramInterferenceGraph.add(i, paramRegisterSpecList.get(b).getReg()); 
  }
  
  private static void coInterferePhis(SsaMethod paramSsaMethod, InterferenceGraph paramInterferenceGraph) {
    Iterator<SsaBasicBlock> iterator = paramSsaMethod.getBlocks().iterator();
    while (iterator.hasNext()) {
      List<SsaInsn> list = ((SsaBasicBlock)iterator.next()).getPhiInsns();
      int i = list.size();
      for (byte b = 0; b < i; b++) {
        for (byte b1 = 0; b1 < i; b1++) {
          if (b != b1) {
            SsaInsn ssaInsn1 = list.get(b);
            SsaInsn ssaInsn2 = list.get(b1);
            coInterferePhiRegisters(paramInterferenceGraph, ssaInsn1.getResult(), ssaInsn2.getSources());
            coInterferePhiRegisters(paramInterferenceGraph, ssaInsn2.getResult(), ssaInsn1.getSources());
            paramInterferenceGraph.add(ssaInsn1.getResult().getReg(), ssaInsn2.getResult().getReg());
          } 
        } 
      } 
    } 
  }
  
  public static InterferenceGraph constructInterferenceGraph(SsaMethod paramSsaMethod) {
    int i = paramSsaMethod.getRegCount();
    InterferenceGraph interferenceGraph = new InterferenceGraph(i);
    for (byte b = 0; b < i; b++)
      (new LivenessAnalyzer(paramSsaMethod, b, interferenceGraph)).run(); 
    coInterferePhis(paramSsaMethod, interferenceGraph);
    return interferenceGraph;
  }
  
  private void handleTailRecursion() {
    while (this.nextFunction != NextFunction.DONE) {
      int i = null.$SwitchMap$com$android$dx$ssa$back$LivenessAnalyzer$NextFunction[this.nextFunction.ordinal()];
      if (i != 1) {
        if (i != 2) {
          if (i != 3)
            continue; 
          this.nextFunction = NextFunction.DONE;
          liveOutAtBlock();
          continue;
        } 
        this.nextFunction = NextFunction.DONE;
        liveOutAtStatement();
        continue;
      } 
      this.nextFunction = NextFunction.DONE;
      liveInAtStatement();
    } 
  }
  
  private void liveInAtStatement() {
    int i = this.statementIndex;
    if (i == 0) {
      this.blockN.addLiveIn(this.regV);
      BitSet bitSet = this.blockN.getPredecessors();
      this.liveOutBlocks.or(bitSet);
    } else {
      this.statementIndex = i - 1;
      this.nextFunction = NextFunction.LIVE_OUT_AT_STATEMENT;
    } 
  }
  
  private void liveOutAtBlock() {
    if (!this.visitedBlocks.get(this.blockN.getIndex())) {
      this.visitedBlocks.set(this.blockN.getIndex());
      this.blockN.addLiveOut(this.regV);
      this.statementIndex = this.blockN.getInsns().size() - 1;
      this.nextFunction = NextFunction.LIVE_OUT_AT_STATEMENT;
    } 
  }
  
  private void liveOutAtStatement() {
    SsaInsn ssaInsn = this.blockN.getInsns().get(this.statementIndex);
    RegisterSpec registerSpec = ssaInsn.getResult();
    if (!ssaInsn.isResultReg(this.regV)) {
      if (registerSpec != null)
        this.interference.add(this.regV, registerSpec.getReg()); 
      this.nextFunction = NextFunction.LIVE_IN_AT_STATEMENT;
    } 
  }
  
  public void run() {
    for (SsaInsn ssaInsn : this.ssaMeth.getUseListForRegister(this.regV)) {
      this.nextFunction = NextFunction.DONE;
      if (ssaInsn instanceof PhiInsn) {
        Iterator<SsaBasicBlock> iterator = ((PhiInsn)ssaInsn).predBlocksForReg(this.regV, this.ssaMeth).iterator();
        while (iterator.hasNext()) {
          this.blockN = iterator.next();
          this.nextFunction = NextFunction.LIVE_OUT_AT_BLOCK;
          handleTailRecursion();
        } 
        continue;
      } 
      SsaBasicBlock ssaBasicBlock = ssaInsn.getBlock();
      this.blockN = ssaBasicBlock;
      int i = ssaBasicBlock.getInsns().indexOf(ssaInsn);
      this.statementIndex = i;
      if (i >= 0) {
        this.nextFunction = NextFunction.LIVE_IN_AT_STATEMENT;
        handleTailRecursion();
        continue;
      } 
      throw new RuntimeException("insn not found in it's own block");
    } 
    while (true) {
      int i = this.liveOutBlocks.nextSetBit(0);
      if (i >= 0) {
        this.blockN = this.ssaMeth.getBlocks().get(i);
        this.liveOutBlocks.clear(i);
        this.nextFunction = NextFunction.LIVE_OUT_AT_BLOCK;
        handleTailRecursion();
        continue;
      } 
      break;
    } 
  }
  
  private enum NextFunction {
    DONE, LIVE_IN_AT_STATEMENT, LIVE_OUT_AT_BLOCK, LIVE_OUT_AT_STATEMENT;
    
    static {
      NextFunction nextFunction = new NextFunction("DONE", 3);
      DONE = nextFunction;
      $VALUES = new NextFunction[] { LIVE_IN_AT_STATEMENT, LIVE_OUT_AT_STATEMENT, LIVE_OUT_AT_BLOCK, nextFunction };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\back\LivenessAnalyzer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */