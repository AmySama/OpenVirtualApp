package com.android.dx.merge;

import com.android.dex.DexException;
import com.android.dex.DexIndexOverflowException;
import com.android.dx.io.CodeReader;
import com.android.dx.io.instructions.CodeOutput;
import com.android.dx.io.instructions.DecodedInstruction;
import com.android.dx.io.instructions.ShortArrayCodeOutput;

final class InstructionTransformer {
  private IndexMap indexMap;
  
  private int mappedAt;
  
  private DecodedInstruction[] mappedInstructions;
  
  private final CodeReader reader;
  
  public InstructionTransformer() {
    CodeReader codeReader = new CodeReader();
    this.reader = codeReader;
    codeReader.setAllVisitors(new GenericVisitor());
    this.reader.setStringVisitor(new StringVisitor());
    this.reader.setTypeVisitor(new TypeVisitor());
    this.reader.setFieldVisitor(new FieldVisitor());
    this.reader.setMethodVisitor(new MethodVisitor());
    this.reader.setMethodAndProtoVisitor(new MethodAndProtoVisitor());
    this.reader.setCallSiteVisitor(new CallSiteVisitor());
  }
  
  private static void jumboCheck(boolean paramBoolean, int paramInt) {
    if (paramBoolean || paramInt <= 65535)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Cannot merge new index ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" into a non-jumbo instruction!");
    throw new DexIndexOverflowException(stringBuilder.toString());
  }
  
  public short[] transform(IndexMap paramIndexMap, short[] paramArrayOfshort) throws DexException {
    DecodedInstruction[] arrayOfDecodedInstruction1 = DecodedInstruction.decodeAll(paramArrayOfshort);
    int i = arrayOfDecodedInstruction1.length;
    this.indexMap = paramIndexMap;
    this.mappedInstructions = new DecodedInstruction[i];
    byte b = 0;
    this.mappedAt = 0;
    this.reader.visitAll(arrayOfDecodedInstruction1);
    ShortArrayCodeOutput shortArrayCodeOutput = new ShortArrayCodeOutput(i);
    DecodedInstruction[] arrayOfDecodedInstruction2 = this.mappedInstructions;
    i = arrayOfDecodedInstruction2.length;
    while (b < i) {
      DecodedInstruction decodedInstruction = arrayOfDecodedInstruction2[b];
      if (decodedInstruction != null)
        decodedInstruction.encode((CodeOutput)shortArrayCodeOutput); 
      b++;
    } 
    this.indexMap = null;
    return shortArrayCodeOutput.getArray();
  }
  
  private class CallSiteVisitor implements CodeReader.Visitor {
    private CallSiteVisitor() {}
    
    public void visit(DecodedInstruction[] param1ArrayOfDecodedInstruction, DecodedInstruction param1DecodedInstruction) {
      int i = param1DecodedInstruction.getIndex();
      i = InstructionTransformer.this.indexMap.adjustCallSite(i);
      InstructionTransformer.this.mappedInstructions[InstructionTransformer.access$808(InstructionTransformer.this)] = param1DecodedInstruction.withIndex(i);
    }
  }
  
  private class FieldVisitor implements CodeReader.Visitor {
    private FieldVisitor() {}
    
    public void visit(DecodedInstruction[] param1ArrayOfDecodedInstruction, DecodedInstruction param1DecodedInstruction) {
      boolean bool;
      int i = param1DecodedInstruction.getIndex();
      i = InstructionTransformer.this.indexMap.adjustField(i);
      if (param1DecodedInstruction.getOpcode() == 27) {
        bool = true;
      } else {
        bool = false;
      } 
      InstructionTransformer.jumboCheck(bool, i);
      InstructionTransformer.this.mappedInstructions[InstructionTransformer.access$808(InstructionTransformer.this)] = param1DecodedInstruction.withIndex(i);
    }
  }
  
  private class GenericVisitor implements CodeReader.Visitor {
    private GenericVisitor() {}
    
    public void visit(DecodedInstruction[] param1ArrayOfDecodedInstruction, DecodedInstruction param1DecodedInstruction) {
      InstructionTransformer.this.mappedInstructions[InstructionTransformer.access$808(InstructionTransformer.this)] = param1DecodedInstruction;
    }
  }
  
  private class MethodAndProtoVisitor implements CodeReader.Visitor {
    private MethodAndProtoVisitor() {}
    
    public void visit(DecodedInstruction[] param1ArrayOfDecodedInstruction, DecodedInstruction param1DecodedInstruction) {
      int i = param1DecodedInstruction.getIndex();
      short s = param1DecodedInstruction.getProtoIndex();
      InstructionTransformer.this.mappedInstructions[InstructionTransformer.access$808(InstructionTransformer.this)] = param1DecodedInstruction.withProtoIndex(InstructionTransformer.this.indexMap.adjustMethod(i), InstructionTransformer.this.indexMap.adjustProto(s));
    }
  }
  
  private class MethodVisitor implements CodeReader.Visitor {
    private MethodVisitor() {}
    
    public void visit(DecodedInstruction[] param1ArrayOfDecodedInstruction, DecodedInstruction param1DecodedInstruction) {
      boolean bool;
      int i = param1DecodedInstruction.getIndex();
      i = InstructionTransformer.this.indexMap.adjustMethod(i);
      if (param1DecodedInstruction.getOpcode() == 27) {
        bool = true;
      } else {
        bool = false;
      } 
      InstructionTransformer.jumboCheck(bool, i);
      InstructionTransformer.this.mappedInstructions[InstructionTransformer.access$808(InstructionTransformer.this)] = param1DecodedInstruction.withIndex(i);
    }
  }
  
  private class StringVisitor implements CodeReader.Visitor {
    private StringVisitor() {}
    
    public void visit(DecodedInstruction[] param1ArrayOfDecodedInstruction, DecodedInstruction param1DecodedInstruction) {
      boolean bool;
      int i = param1DecodedInstruction.getIndex();
      i = InstructionTransformer.this.indexMap.adjustString(i);
      if (param1DecodedInstruction.getOpcode() == 27) {
        bool = true;
      } else {
        bool = false;
      } 
      InstructionTransformer.jumboCheck(bool, i);
      InstructionTransformer.this.mappedInstructions[InstructionTransformer.access$808(InstructionTransformer.this)] = param1DecodedInstruction.withIndex(i);
    }
  }
  
  private class TypeVisitor implements CodeReader.Visitor {
    private TypeVisitor() {}
    
    public void visit(DecodedInstruction[] param1ArrayOfDecodedInstruction, DecodedInstruction param1DecodedInstruction) {
      boolean bool;
      int i = param1DecodedInstruction.getIndex();
      i = InstructionTransformer.this.indexMap.adjustType(i);
      if (param1DecodedInstruction.getOpcode() == 27) {
        bool = true;
      } else {
        bool = false;
      } 
      InstructionTransformer.jumboCheck(bool, i);
      InstructionTransformer.this.mappedInstructions[InstructionTransformer.access$808(InstructionTransformer.this)] = param1DecodedInstruction.withIndex(i);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\merge\InstructionTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */