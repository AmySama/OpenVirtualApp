package com.android.dx.ssa;

import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.PlainCstInsn;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegOps;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.code.TranslationAdvice;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstLiteralBits;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.rop.type.TypeList;
import java.util.ArrayList;

public class LiteralOpUpgrader {
  private final SsaMethod ssaMeth;
  
  private LiteralOpUpgrader(SsaMethod paramSsaMethod) {
    this.ssaMeth = paramSsaMethod;
  }
  
  private static boolean isConstIntZeroOrKnownNull(RegisterSpec paramRegisterSpec) {
    TypeBearer typeBearer = paramRegisterSpec.getTypeBearer();
    boolean bool = typeBearer instanceof CstLiteralBits;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (bool) {
      bool2 = bool1;
      if (((CstLiteralBits)typeBearer).getLongBits() == 0L)
        bool2 = true; 
    } 
    return bool2;
  }
  
  public static void process(SsaMethod paramSsaMethod) {
    (new LiteralOpUpgrader(paramSsaMethod)).run();
  }
  
  private void replacePlainInsn(NormalSsaInsn paramNormalSsaInsn, RegisterSpecList paramRegisterSpecList, int paramInt, Constant paramConstant) {
    PlainInsn plainInsn;
    PlainCstInsn plainCstInsn;
    Insn insn = paramNormalSsaInsn.getOriginalRopInsn();
    Rop rop = Rops.ropFor(paramInt, (TypeBearer)paramNormalSsaInsn.getResult(), (TypeList)paramRegisterSpecList, paramConstant);
    if (paramConstant == null) {
      plainInsn = new PlainInsn(rop, insn.getPosition(), paramNormalSsaInsn.getResult(), paramRegisterSpecList);
    } else {
      plainCstInsn = new PlainCstInsn(rop, insn.getPosition(), paramNormalSsaInsn.getResult(), (RegisterSpecList)plainInsn, paramConstant);
    } 
    NormalSsaInsn normalSsaInsn = new NormalSsaInsn((Insn)plainCstInsn, paramNormalSsaInsn.getBlock());
    ArrayList<SsaInsn> arrayList = paramNormalSsaInsn.getBlock().getInsns();
    this.ssaMeth.onInsnRemoved(paramNormalSsaInsn);
    arrayList.set(arrayList.lastIndexOf(paramNormalSsaInsn), normalSsaInsn);
    this.ssaMeth.onInsnAdded(normalSsaInsn);
  }
  
  private void run() {
    final TranslationAdvice advice = Optimizer.getAdvice();
    this.ssaMeth.forEachInsn(new SsaInsn.Visitor() {
          public void visitMoveInsn(NormalSsaInsn param1NormalSsaInsn) {}
          
          public void visitNonMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
            Rop rop = param1NormalSsaInsn.getOriginalRopInsn().getOpcode();
            RegisterSpecList registerSpecList = param1NormalSsaInsn.getSources();
            if (LiteralOpUpgrader.this.tryReplacingWithConstant(param1NormalSsaInsn))
              return; 
            if (registerSpecList.size() != 2)
              return; 
            if (rop.getBranchingness() == 4) {
              if (LiteralOpUpgrader.isConstIntZeroOrKnownNull(registerSpecList.get(0))) {
                LiteralOpUpgrader.this.replacePlainInsn(param1NormalSsaInsn, registerSpecList.withoutFirst(), RegOps.flippedIfOpcode(rop.getOpcode()), null);
              } else if (LiteralOpUpgrader.isConstIntZeroOrKnownNull(registerSpecList.get(1))) {
                LiteralOpUpgrader.this.replacePlainInsn(param1NormalSsaInsn, registerSpecList.withoutLast(), rop.getOpcode(), null);
              } 
            } else if (advice.hasConstantOperation(rop, registerSpecList.get(0), registerSpecList.get(1))) {
              param1NormalSsaInsn.upgradeToLiteral();
            } else if (rop.isCommutative() && advice.hasConstantOperation(rop, registerSpecList.get(1), registerSpecList.get(0))) {
              param1NormalSsaInsn.setNewSources(RegisterSpecList.make(registerSpecList.get(1), registerSpecList.get(0)));
              param1NormalSsaInsn.upgradeToLiteral();
            } 
          }
          
          public void visitPhiInsn(PhiInsn param1PhiInsn) {}
        });
  }
  
  private boolean tryReplacingWithConstant(NormalSsaInsn paramNormalSsaInsn) {
    Rop rop = paramNormalSsaInsn.getOriginalRopInsn().getOpcode();
    RegisterSpec registerSpec = paramNormalSsaInsn.getResult();
    if (registerSpec != null && !this.ssaMeth.isRegALocal(registerSpec) && rop.getOpcode() != 5) {
      TypeBearer typeBearer = paramNormalSsaInsn.getResult().getTypeBearer();
      if (typeBearer.isConstant() && typeBearer.getBasicType() == 6) {
        replacePlainInsn(paramNormalSsaInsn, RegisterSpecList.EMPTY, 5, (Constant)typeBearer);
        if (rop.getOpcode() == 56) {
          int i = paramNormalSsaInsn.getBlock().getPredecessors().nextSetBit(0);
          ArrayList<SsaInsn> arrayList = ((SsaBasicBlock)this.ssaMeth.getBlocks().get(i)).getInsns();
          replacePlainInsn((NormalSsaInsn)arrayList.get(arrayList.size() - 1), RegisterSpecList.EMPTY, 6, null);
        } 
        return true;
      } 
    } 
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\LiteralOpUpgrader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */