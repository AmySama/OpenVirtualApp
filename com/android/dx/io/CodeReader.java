package com.android.dx.io;

import com.android.dex.DexException;
import com.android.dx.io.instructions.DecodedInstruction;

public final class CodeReader {
  private Visitor callSiteVisitor = null;
  
  private Visitor fallbackVisitor = null;
  
  private Visitor fieldVisitor = null;
  
  private Visitor methodAndProtoVisitor = null;
  
  private Visitor methodVisitor = null;
  
  private Visitor stringVisitor = null;
  
  private Visitor typeVisitor = null;
  
  private void callVisit(DecodedInstruction[] paramArrayOfDecodedInstruction, DecodedInstruction paramDecodedInstruction) {
    Visitor visitor1;
    switch (OpcodeInfo.getIndexType(paramDecodedInstruction.getOpcode())) {
      default:
        visitor1 = null;
        break;
      case null:
        visitor1 = this.callSiteVisitor;
        break;
      case null:
        visitor1 = this.methodAndProtoVisitor;
        break;
      case null:
        visitor1 = this.methodVisitor;
        break;
      case null:
        visitor1 = this.fieldVisitor;
        break;
      case null:
        visitor1 = this.typeVisitor;
        break;
      case null:
        visitor1 = this.stringVisitor;
        break;
    } 
    Visitor visitor2 = visitor1;
    if (visitor1 == null)
      visitor2 = this.fallbackVisitor; 
    if (visitor2 != null)
      visitor2.visit(paramArrayOfDecodedInstruction, paramDecodedInstruction); 
  }
  
  public void setAllVisitors(Visitor paramVisitor) {
    this.fallbackVisitor = paramVisitor;
    this.stringVisitor = paramVisitor;
    this.typeVisitor = paramVisitor;
    this.fieldVisitor = paramVisitor;
    this.methodVisitor = paramVisitor;
    this.methodAndProtoVisitor = paramVisitor;
    this.callSiteVisitor = paramVisitor;
  }
  
  public void setCallSiteVisitor(Visitor paramVisitor) {
    this.callSiteVisitor = paramVisitor;
  }
  
  public void setFallbackVisitor(Visitor paramVisitor) {
    this.fallbackVisitor = paramVisitor;
  }
  
  public void setFieldVisitor(Visitor paramVisitor) {
    this.fieldVisitor = paramVisitor;
  }
  
  public void setMethodAndProtoVisitor(Visitor paramVisitor) {
    this.methodAndProtoVisitor = paramVisitor;
  }
  
  public void setMethodVisitor(Visitor paramVisitor) {
    this.methodVisitor = paramVisitor;
  }
  
  public void setStringVisitor(Visitor paramVisitor) {
    this.stringVisitor = paramVisitor;
  }
  
  public void setTypeVisitor(Visitor paramVisitor) {
    this.typeVisitor = paramVisitor;
  }
  
  public void visitAll(DecodedInstruction[] paramArrayOfDecodedInstruction) throws DexException {
    int i = paramArrayOfDecodedInstruction.length;
    for (byte b = 0; b < i; b++) {
      DecodedInstruction decodedInstruction = paramArrayOfDecodedInstruction[b];
      if (decodedInstruction != null)
        callVisit(paramArrayOfDecodedInstruction, decodedInstruction); 
    } 
  }
  
  public void visitAll(short[] paramArrayOfshort) throws DexException {
    visitAll(DecodedInstruction.decodeAll(paramArrayOfshort));
  }
  
  public static interface Visitor {
    void visit(DecodedInstruction[] param1ArrayOfDecodedInstruction, DecodedInstruction param1DecodedInstruction);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\CodeReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */