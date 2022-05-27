package android.support.transition;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.util.SparseArray;
import android.view.View;

class TransitionValuesMaps {
  final SparseArray<View> mIdValues = new SparseArray();
  
  final LongSparseArray<View> mItemIdValues = new LongSparseArray();
  
  final ArrayMap<String, View> mNameValues = new ArrayMap();
  
  final ArrayMap<View, TransitionValues> mViewValues = new ArrayMap();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\TransitionValuesMaps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */