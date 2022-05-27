package com.android.dx.ssa.back;

import com.android.dx.ssa.BasicRegisterMapper;
import com.android.dx.ssa.RegisterMapper;
import com.android.dx.ssa.SsaMethod;

public class NullRegisterAllocator extends RegisterAllocator {
  public NullRegisterAllocator(SsaMethod paramSsaMethod, InterferenceGraph paramInterferenceGraph) {
    super(paramSsaMethod, paramInterferenceGraph);
  }
  
  public RegisterMapper allocateRegisters() {
    int i = this.ssaMeth.getRegCount();
    BasicRegisterMapper basicRegisterMapper = new BasicRegisterMapper(i);
    for (byte b = 0; b < i; b++)
      basicRegisterMapper.addMapping(b, b * 2, 2); 
    return (RegisterMapper)basicRegisterMapper;
  }
  
  public boolean wantsParamsMovedHigh() {
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\back\NullRegisterAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */