package com.android.dx.ssa;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.RegisterSpecSet;

public abstract class RegisterMapper {
  public abstract int getNewRegisterCount();
  
  public abstract RegisterSpec map(RegisterSpec paramRegisterSpec);
  
  public final RegisterSpecList map(RegisterSpecList paramRegisterSpecList) {
    int i = paramRegisterSpecList.size();
    RegisterSpecList registerSpecList = new RegisterSpecList(i);
    for (byte b = 0; b < i; b++)
      registerSpecList.set(b, map(paramRegisterSpecList.get(b))); 
    registerSpecList.setImmutable();
    if (!registerSpecList.equals(paramRegisterSpecList))
      paramRegisterSpecList = registerSpecList; 
    return paramRegisterSpecList;
  }
  
  public final RegisterSpecSet map(RegisterSpecSet paramRegisterSpecSet) {
    int i = paramRegisterSpecSet.getMaxSize();
    RegisterSpecSet registerSpecSet = new RegisterSpecSet(getNewRegisterCount());
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = paramRegisterSpecSet.get(b);
      if (registerSpec != null)
        registerSpecSet.put(map(registerSpec)); 
    } 
    registerSpecSet.setImmutable();
    if (!registerSpecSet.equals(paramRegisterSpecSet))
      paramRegisterSpecSet = registerSpecSet; 
    return paramRegisterSpecSet;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\RegisterMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */