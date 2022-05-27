package com.android.dx.ssa;

import com.android.dx.util.BitIntSet;
import com.android.dx.util.IntSet;
import com.android.dx.util.ListIntSet;

public final class SetFactory {
  private static final int DOMFRONT_SET_THRESHOLD_SIZE = 3072;
  
  private static final int INTERFERENCE_SET_THRESHOLD_SIZE = 3072;
  
  private static final int LIVENESS_SET_THRESHOLD_SIZE = 3072;
  
  static IntSet makeDomFrontSet(int paramInt) {
    ListIntSet listIntSet;
    if (paramInt <= 3072) {
      BitIntSet bitIntSet = new BitIntSet(paramInt);
    } else {
      listIntSet = new ListIntSet();
    } 
    return (IntSet)listIntSet;
  }
  
  public static IntSet makeInterferenceSet(int paramInt) {
    ListIntSet listIntSet;
    if (paramInt <= 3072) {
      BitIntSet bitIntSet = new BitIntSet(paramInt);
    } else {
      listIntSet = new ListIntSet();
    } 
    return (IntSet)listIntSet;
  }
  
  static IntSet makeLivenessSet(int paramInt) {
    ListIntSet listIntSet;
    if (paramInt <= 3072) {
      BitIntSet bitIntSet = new BitIntSet(paramInt);
    } else {
      listIntSet = new ListIntSet();
    } 
    return (IntSet)listIntSet;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\SetFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */