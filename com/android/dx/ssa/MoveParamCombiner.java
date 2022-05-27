package com.android.dx.ssa;

import com.android.dx.rop.code.CstInsn;
import com.android.dx.rop.code.LocalItem;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.cst.CstInteger;
import java.util.HashSet;
import java.util.List;

public class MoveParamCombiner {
  private final SsaMethod ssaMeth;
  
  private MoveParamCombiner(SsaMethod paramSsaMethod) {
    this.ssaMeth = paramSsaMethod;
  }
  
  private int getParamIndex(NormalSsaInsn paramNormalSsaInsn) {
    return ((CstInteger)((CstInsn)paramNormalSsaInsn.getOriginalRopInsn()).getConstant()).getValue();
  }
  
  public static void process(SsaMethod paramSsaMethod) {
    (new MoveParamCombiner(paramSsaMethod)).run();
  }
  
  private void run() {
    final RegisterSpec[] paramSpecs = new RegisterSpec[this.ssaMeth.getParamWidth()];
    final HashSet<SsaInsn> deletedInsns = new HashSet();
    this.ssaMeth.forEachInsn(new SsaInsn.Visitor() {
          public void visitMoveInsn(NormalSsaInsn param1NormalSsaInsn) {}
          
          public void visitNonMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
            if (param1NormalSsaInsn.getOpcode().getOpcode() != 3)
              return; 
            int i = MoveParamCombiner.this.getParamIndex(param1NormalSsaInsn);
            RegisterSpec[] arrayOfRegisterSpec = paramSpecs;
            if (arrayOfRegisterSpec[i] == null) {
              arrayOfRegisterSpec[i] = param1NormalSsaInsn.getResult();
            } else {
              final RegisterSpec specA = arrayOfRegisterSpec[i];
              final RegisterSpec specB = param1NormalSsaInsn.getResult();
              LocalItem localItem2 = registerSpec1.getLocalItem();
              LocalItem localItem1 = registerSpec2.getLocalItem();
              if (localItem2 != null)
                if (localItem1 == null) {
                  localItem1 = localItem2;
                } else if (localItem2.equals(localItem1)) {
                  localItem1 = localItem2;
                } else {
                  return;
                }  
              MoveParamCombiner.this.ssaMeth.getDefinitionForRegister(registerSpec1.getReg()).setResultLocal(localItem1);
              RegisterMapper registerMapper = new RegisterMapper() {
                  public int getNewRegisterCount() {
                    return MoveParamCombiner.this.ssaMeth.getRegCount();
                  }
                  
                  public RegisterSpec map(RegisterSpec param2RegisterSpec) {
                    RegisterSpec registerSpec = param2RegisterSpec;
                    if (param2RegisterSpec.getReg() == specB.getReg())
                      registerSpec = specA; 
                    return registerSpec;
                  }
                };
              List<SsaInsn> list = MoveParamCombiner.this.ssaMeth.getUseListForRegister(registerSpec2.getReg());
              for (i = list.size() - 1; i >= 0; i--)
                ((SsaInsn)list.get(i)).mapSourceRegisters(registerMapper); 
              deletedInsns.add(param1NormalSsaInsn);
            } 
          }
          
          public void visitPhiInsn(PhiInsn param1PhiInsn) {}
        });
    this.ssaMeth.deleteInsns(hashSet);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\MoveParamCombiner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */