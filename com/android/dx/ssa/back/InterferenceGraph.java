package com.android.dx.ssa.back;

import com.android.dx.ssa.SetFactory;
import com.android.dx.util.IntSet;
import java.util.ArrayList;

public class InterferenceGraph {
  private final ArrayList<IntSet> interference;
  
  public InterferenceGraph(int paramInt) {
    this.interference = new ArrayList<IntSet>(paramInt);
    for (byte b = 0; b < paramInt; b++)
      this.interference.add(SetFactory.makeInterferenceSet(paramInt)); 
  }
  
  private void ensureCapacity(int paramInt) {
    int i = this.interference.size();
    this.interference.ensureCapacity(paramInt);
    while (i < paramInt) {
      this.interference.add(SetFactory.makeInterferenceSet(paramInt));
      i++;
    } 
  }
  
  public void add(int paramInt1, int paramInt2) {
    ensureCapacity(Math.max(paramInt1, paramInt2) + 1);
    ((IntSet)this.interference.get(paramInt1)).add(paramInt2);
    ((IntSet)this.interference.get(paramInt2)).add(paramInt1);
  }
  
  public void dumpToStdout() {
    int i = this.interference.size();
    for (byte b = 0; b < i; b++) {
      StringBuilder stringBuilder1 = new StringBuilder();
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("Reg ");
      stringBuilder2.append(b);
      stringBuilder2.append(":");
      stringBuilder2.append(((IntSet)this.interference.get(b)).toString());
      stringBuilder1.append(stringBuilder2.toString());
      System.out.println(stringBuilder1.toString());
    } 
  }
  
  public void mergeInterferenceSet(int paramInt, IntSet paramIntSet) {
    if (paramInt < this.interference.size())
      paramIntSet.merge(this.interference.get(paramInt)); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\back\InterferenceGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */