package com.android.dx.cf.attrib;

import com.android.dx.cf.code.LocalVariableList;

public final class AttLocalVariableTable extends BaseLocalVariables {
  public static final String ATTRIBUTE_NAME = "LocalVariableTable";
  
  public AttLocalVariableTable(LocalVariableList paramLocalVariableList) {
    super("LocalVariableTable", paramLocalVariableList);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttLocalVariableTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */