package com.android.dx.cf.attrib;

import com.android.dx.cf.iface.Attribute;

public abstract class BaseAttribute implements Attribute {
  private final String name;
  
  public BaseAttribute(String paramString) {
    if (paramString != null) {
      this.name = paramString;
      return;
    } 
    throw new NullPointerException("name == null");
  }
  
  public String getName() {
    return this.name;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\BaseAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */