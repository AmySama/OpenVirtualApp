package com.android.dx.cf.code;

import com.android.dx.cf.attrib.AttCode;
import com.android.dx.cf.attrib.AttLineNumberTable;
import com.android.dx.cf.attrib.AttLocalVariableTable;
import com.android.dx.cf.attrib.AttLocalVariableTypeTable;
import com.android.dx.cf.iface.Attribute;
import com.android.dx.cf.iface.AttributeList;
import com.android.dx.cf.iface.ClassFile;
import com.android.dx.cf.iface.Method;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Prototype;

public final class ConcreteMethod implements Method {
  private final AttCode attCode;
  
  private final ClassFile classFile;
  
  private final LineNumberList lineNumbers;
  
  private final LocalVariableList localVariables;
  
  private final Method method;
  
  public ConcreteMethod(Method paramMethod, ClassFile paramClassFile, boolean paramBoolean1, boolean paramBoolean2) {
    this.method = paramMethod;
    this.classFile = paramClassFile;
    AttCode attCode = (AttCode)paramMethod.getAttributes().findFirst("Code");
    this.attCode = attCode;
    AttributeList attributeList = attCode.getAttributes();
    LineNumberList lineNumberList1 = LineNumberList.EMPTY;
    LineNumberList lineNumberList2 = lineNumberList1;
    if (paramBoolean1) {
      AttLineNumberTable attLineNumberTable = (AttLineNumberTable)attributeList.findFirst("LineNumberTable");
      while (true) {
        lineNumberList2 = lineNumberList1;
        if (attLineNumberTable != null) {
          lineNumberList1 = LineNumberList.concat(lineNumberList1, attLineNumberTable.getLineNumbers());
          attLineNumberTable = (AttLineNumberTable)attributeList.findNext((Attribute)attLineNumberTable);
          continue;
        } 
        break;
      } 
    } 
    this.lineNumbers = lineNumberList2;
    LocalVariableList localVariableList1 = LocalVariableList.EMPTY;
    LocalVariableList localVariableList2 = localVariableList1;
    if (paramBoolean2) {
      for (AttLocalVariableTable attLocalVariableTable = (AttLocalVariableTable)attributeList.findFirst("LocalVariableTable"); attLocalVariableTable != null; attLocalVariableTable = (AttLocalVariableTable)attributeList.findNext((Attribute)attLocalVariableTable))
        localVariableList1 = LocalVariableList.concat(localVariableList1, attLocalVariableTable.getLocalVariables()); 
      LocalVariableList localVariableList = LocalVariableList.EMPTY;
      for (AttLocalVariableTypeTable attLocalVariableTypeTable = (AttLocalVariableTypeTable)attributeList.findFirst("LocalVariableTypeTable"); attLocalVariableTypeTable != null; attLocalVariableTypeTable = (AttLocalVariableTypeTable)attributeList.findNext((Attribute)attLocalVariableTypeTable))
        localVariableList = LocalVariableList.concat(localVariableList, attLocalVariableTypeTable.getLocalVariables()); 
      localVariableList2 = localVariableList1;
      if (localVariableList.size() != 0)
        localVariableList2 = LocalVariableList.mergeDescriptorsAndSignatures(localVariableList1, localVariableList); 
    } 
    this.localVariables = localVariableList2;
  }
  
  public int getAccessFlags() {
    return this.method.getAccessFlags();
  }
  
  public AttributeList getAttributes() {
    return this.method.getAttributes();
  }
  
  public ByteCatchList getCatches() {
    return this.attCode.getCatches();
  }
  
  public BytecodeArray getCode() {
    return this.attCode.getCode();
  }
  
  public CstType getDefiningClass() {
    return this.method.getDefiningClass();
  }
  
  public CstString getDescriptor() {
    return this.method.getDescriptor();
  }
  
  public Prototype getEffectiveDescriptor() {
    return this.method.getEffectiveDescriptor();
  }
  
  public LineNumberList getLineNumbers() {
    return this.lineNumbers;
  }
  
  public LocalVariableList getLocalVariables() {
    return this.localVariables;
  }
  
  public int getMaxLocals() {
    return this.attCode.getMaxLocals();
  }
  
  public int getMaxStack() {
    return this.attCode.getMaxStack();
  }
  
  public CstString getName() {
    return this.method.getName();
  }
  
  public CstNat getNat() {
    return this.method.getNat();
  }
  
  public CstString getSourceFile() {
    return this.classFile.getSourceFile();
  }
  
  public final boolean isDefaultOrStaticInterfaceMethod() {
    boolean bool;
    if ((this.classFile.getAccessFlags() & 0x200) != 0 && !getNat().isClassInit()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final boolean isStaticMethod() {
    boolean bool;
    if ((getAccessFlags() & 0x8) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public SourcePosition makeSourcePosistion(int paramInt) {
    return new SourcePosition(getSourceFile(), paramInt, this.lineNumbers.pcToLine(paramInt));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\ConcreteMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */