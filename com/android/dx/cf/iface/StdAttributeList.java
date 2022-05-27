package com.android.dx.cf.iface;

import com.android.dx.util.FixedSizeList;

public final class StdAttributeList extends FixedSizeList implements AttributeList {
  public StdAttributeList(int paramInt) {
    super(paramInt);
  }
  
  public int byteLength() {
    int i = size();
    int j = 2;
    for (byte b = 0; b < i; b++)
      j += get(b).byteLength(); 
    return j;
  }
  
  public Attribute findFirst(String paramString) {
    int i = size();
    for (byte b = 0; b < i; b++) {
      Attribute attribute = get(b);
      if (attribute.getName().equals(paramString))
        return attribute; 
    } 
    return null;
  }
  
  public Attribute findNext(Attribute paramAttribute) {
    int i = size();
    for (byte b = 0; b < i; b++) {
      if (get(b) == paramAttribute) {
        String str = paramAttribute.getName();
        while (++b < i) {
          Attribute attribute = get(b);
          if (attribute.getName().equals(str))
            return attribute; 
        } 
        return null;
      } 
    } 
    return null;
  }
  
  public Attribute get(int paramInt) {
    return (Attribute)get0(paramInt);
  }
  
  public void set(int paramInt, Attribute paramAttribute) {
    set0(paramInt, paramAttribute);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\iface\StdAttributeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */