package com.android.dex;

import com.android.dex.util.Unsigned;
import java.io.Serializable;

public final class TypeList implements Comparable<TypeList> {
  public static final TypeList EMPTY = new TypeList(null, Dex.EMPTY_SHORT_ARRAY);
  
  private final Dex dex;
  
  private final short[] types;
  
  public TypeList(Dex paramDex, short[] paramArrayOfshort) {
    this.dex = paramDex;
    this.types = paramArrayOfshort;
  }
  
  public int compareTo(TypeList paramTypeList) {
    byte b = 0;
    while (true) {
      short[] arrayOfShort = this.types;
      if (b < arrayOfShort.length) {
        short[] arrayOfShort1 = paramTypeList.types;
        if (b < arrayOfShort1.length) {
          if (arrayOfShort[b] != arrayOfShort1[b])
            return Unsigned.compare(arrayOfShort[b], arrayOfShort1[b]); 
          b++;
          continue;
        } 
      } 
      break;
    } 
    return Unsigned.compare(this.types.length, paramTypeList.types.length);
  }
  
  public short[] getTypes() {
    return this.types;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("(");
    int i = this.types.length;
    for (byte b = 0; b < i; b++) {
      Serializable serializable;
      Dex dex = this.dex;
      if (dex != null) {
        serializable = dex.typeNames().get(this.types[b]);
      } else {
        serializable = Short.valueOf(this.types[b]);
      } 
      stringBuilder.append(serializable);
    } 
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\TypeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */