package com.android.dx.ssa.back;

import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.ssa.NormalSsaInsn;
import com.android.dx.ssa.RegisterMapper;
import com.android.dx.ssa.SsaBasicBlock;
import com.android.dx.ssa.SsaInsn;
import com.android.dx.ssa.SsaMethod;
import com.android.dx.util.IntIterator;
import java.util.ArrayList;

public abstract class RegisterAllocator {
  protected final InterferenceGraph interference;
  
  protected final SsaMethod ssaMeth;
  
  public RegisterAllocator(SsaMethod paramSsaMethod, InterferenceGraph paramInterferenceGraph) {
    this.ssaMeth = paramSsaMethod;
    this.interference = paramInterferenceGraph;
  }
  
  public abstract RegisterMapper allocateRegisters();
  
  protected final int getCategoryForSsaReg(int paramInt) {
    SsaInsn ssaInsn = this.ssaMeth.getDefinitionForRegister(paramInt);
    return (ssaInsn == null) ? 1 : ssaInsn.getResult().getCategory();
  }
  
  protected final RegisterSpec getDefinitionSpecForSsaReg(int paramInt) {
    RegisterSpec registerSpec;
    SsaInsn ssaInsn = this.ssaMeth.getDefinitionForRegister(paramInt);
    if (ssaInsn == null) {
      ssaInsn = null;
    } else {
      registerSpec = ssaInsn.getResult();
    } 
    return registerSpec;
  }
  
  protected final RegisterSpec insertMoveBefore(SsaInsn paramSsaInsn, RegisterSpec paramRegisterSpec) {
    SsaBasicBlock ssaBasicBlock = paramSsaInsn.getBlock();
    ArrayList<SsaInsn> arrayList = ssaBasicBlock.getInsns();
    int i = arrayList.indexOf(paramSsaInsn);
    if (i >= 0) {
      RegisterSpecList registerSpecList;
      if (i == arrayList.size() - 1) {
        RegisterSpec registerSpec = RegisterSpec.make(this.ssaMeth.makeNewSsaReg(), paramRegisterSpec.getTypeBearer());
        arrayList.add(i, SsaInsn.makeFromRop((Insn)new PlainInsn(Rops.opMove((TypeBearer)registerSpec.getType()), SourcePosition.NO_INFO, registerSpec, RegisterSpecList.make(paramRegisterSpec)), ssaBasicBlock));
        int j = registerSpec.getReg();
        IntIterator intIterator = ssaBasicBlock.getLiveOutRegs().iterator();
        while (intIterator.hasNext())
          this.interference.add(j, intIterator.next()); 
        registerSpecList = paramSsaInsn.getSources();
        int k = registerSpecList.size();
        for (i = 0; i < k; i++)
          this.interference.add(j, registerSpecList.get(i).getReg()); 
        this.ssaMeth.onInsnsChanged();
        return registerSpec;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Adding move here not supported:");
      stringBuilder.append(registerSpecList.toHuman());
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new IllegalArgumentException("specified insn is not in this block");
  }
  
  protected boolean isDefinitionMoveParam(int paramInt) {
    SsaInsn ssaInsn = this.ssaMeth.getDefinitionForRegister(paramInt);
    boolean bool = ssaInsn instanceof NormalSsaInsn;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (bool) {
      bool2 = bool1;
      if (((NormalSsaInsn)ssaInsn).getOpcode().getOpcode() == 3)
        bool2 = true; 
    } 
    return bool2;
  }
  
  public abstract boolean wantsParamsMovedHigh();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\back\RegisterAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */