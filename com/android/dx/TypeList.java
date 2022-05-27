package com.android.dx;

import com.android.dx.rop.type.StdTypeList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class TypeList {
  final StdTypeList ropTypes;
  
  final TypeId<?>[] types;
  
  TypeList(TypeId<?>[] paramArrayOfTypeId) {
    this.types = (TypeId<?>[])paramArrayOfTypeId.clone();
    this.ropTypes = new StdTypeList(paramArrayOfTypeId.length);
    for (byte b = 0; b < paramArrayOfTypeId.length; b++)
      this.ropTypes.set(b, (paramArrayOfTypeId[b]).ropType); 
  }
  
  public List<TypeId<?>> asList() {
    return Collections.unmodifiableList(Arrays.asList(this.types));
  }
  
  public boolean equals(Object paramObject) {
    boolean bool;
    if (paramObject instanceof TypeList && Arrays.equals((Object[])((TypeList)paramObject).types, (Object[])this.types)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int hashCode() {
    return Arrays.hashCode((Object[])this.types);
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < this.types.length; b++) {
      if (b > 0)
        stringBuilder.append(", "); 
      stringBuilder.append(this.types[b]);
    } 
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\TypeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */