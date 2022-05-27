package com.android.dx.ssa;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.ssa.back.InterferenceGraph;
import com.android.dx.util.BitIntSet;
import com.android.dx.util.IntSet;
import java.util.ArrayList;

public class InterferenceRegisterMapper extends BasicRegisterMapper {
  private final ArrayList<BitIntSet> newRegInterference = new ArrayList<BitIntSet>();
  
  private final InterferenceGraph oldRegInterference;
  
  public InterferenceRegisterMapper(InterferenceGraph paramInterferenceGraph, int paramInt) {
    super(paramInt);
    this.oldRegInterference = paramInterferenceGraph;
  }
  
  private void addInterfence(int paramInt1, int paramInt2) {
    ArrayList<BitIntSet> arrayList = this.newRegInterference;
    int i = paramInt1 + 1;
    arrayList.ensureCapacity(i);
    while (paramInt1 >= this.newRegInterference.size())
      this.newRegInterference.add(new BitIntSet(i)); 
    this.oldRegInterference.mergeInterferenceSet(paramInt2, (IntSet)this.newRegInterference.get(paramInt1));
  }
  
  public void addMapping(int paramInt1, int paramInt2, int paramInt3) {
    super.addMapping(paramInt1, paramInt2, paramInt3);
    addInterfence(paramInt2, paramInt1);
    if (paramInt3 == 2)
      addInterfence(paramInt2 + 1, paramInt1); 
  }
  
  public boolean areAnyPinned(RegisterSpecList paramRegisterSpecList, int paramInt1, int paramInt2) {
    int i = paramRegisterSpecList.size();
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = paramRegisterSpecList.get(b);
      int j = oldToNew(registerSpec.getReg());
      if (j == paramInt1 || (registerSpec.getCategory() == 2 && j + 1 == paramInt1) || (paramInt2 == 2 && j == paramInt1 + 1))
        return true; 
    } 
    return false;
  }
  
  public boolean interferes(int paramInt1, int paramInt2, int paramInt3) {
    int i = this.newRegInterference.size();
    boolean bool = false;
    if (paramInt2 >= i)
      return false; 
    IntSet intSet = (IntSet)this.newRegInterference.get(paramInt2);
    if (intSet == null)
      return false; 
    if (paramInt3 == 1)
      return intSet.has(paramInt1); 
    if (intSet.has(paramInt1) || interferes(paramInt1, paramInt2 + 1, paramInt3 - 1))
      bool = true; 
    return bool;
  }
  
  public boolean interferes(RegisterSpec paramRegisterSpec, int paramInt) {
    return interferes(paramRegisterSpec.getReg(), paramInt, paramRegisterSpec.getCategory());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\InterferenceRegisterMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */