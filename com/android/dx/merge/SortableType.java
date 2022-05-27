package com.android.dx.merge;

import com.android.dex.ClassDef;
import com.android.dex.Dex;
import com.android.dex.DexException;
import java.util.Comparator;

final class SortableType {
  public static final Comparator<SortableType> NULLS_LAST_ORDER = new Comparator<SortableType>() {
      public int compare(SortableType param1SortableType1, SortableType param1SortableType2) {
        if (param1SortableType1 == param1SortableType2)
          return 0; 
        if (param1SortableType2 == null)
          return -1; 
        if (param1SortableType1 == null)
          return 1; 
        if (param1SortableType1.depth != param1SortableType2.depth) {
          int k = param1SortableType1.depth;
          int m = param1SortableType2.depth;
          return k - m;
        } 
        int i = param1SortableType1.getTypeIndex();
        int j = param1SortableType2.getTypeIndex();
        return i - j;
      }
    };
  
  private final ClassDef classDef;
  
  private int depth = -1;
  
  private final Dex dex;
  
  private final IndexMap indexMap;
  
  public SortableType(Dex paramDex, IndexMap paramIndexMap, ClassDef paramClassDef) {
    this.dex = paramDex;
    this.indexMap = paramIndexMap;
    this.classDef = paramClassDef;
  }
  
  public ClassDef getClassDef() {
    return this.classDef;
  }
  
  public Dex getDex() {
    return this.dex;
  }
  
  public IndexMap getIndexMap() {
    return this.indexMap;
  }
  
  public int getTypeIndex() {
    return this.classDef.getTypeIndex();
  }
  
  public boolean isDepthAssigned() {
    boolean bool;
    if (this.depth != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean tryAssignDepth(SortableType[] paramArrayOfSortableType) {
    StringBuilder stringBuilder;
    int i;
    if (this.classDef.getSupertypeIndex() == -1) {
      i = 0;
    } else if (this.classDef.getSupertypeIndex() != this.classDef.getTypeIndex()) {
      SortableType sortableType = paramArrayOfSortableType[this.classDef.getSupertypeIndex()];
      if (sortableType == null) {
        i = 1;
      } else {
        int k = sortableType.depth;
        i = k;
        if (k == -1)
          return false; 
      } 
    } else {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Class with type index ");
      stringBuilder.append(this.classDef.getTypeIndex());
      stringBuilder.append(" extends itself");
      throw new DexException(stringBuilder.toString());
    } 
    short[] arrayOfShort = this.classDef.getInterfaces();
    int j = arrayOfShort.length;
    for (byte b = 0; b < j; b++) {
      StringBuilder stringBuilder1 = stringBuilder[arrayOfShort[b]];
      if (stringBuilder1 == null) {
        i = Math.max(i, 1);
      } else {
        int k = ((SortableType)stringBuilder1).depth;
        if (k == -1)
          return false; 
        i = Math.max(i, k);
      } 
    } 
    this.depth = i + 1;
    return true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\merge\SortableType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */