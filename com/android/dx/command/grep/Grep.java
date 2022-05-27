package com.android.dx.command.grep;

import com.android.dex.ClassData;
import com.android.dex.ClassDef;
import com.android.dex.Dex;
import com.android.dex.EncodedValueReader;
import com.android.dex.MethodId;
import com.android.dex.util.ByteInput;
import com.android.dx.io.CodeReader;
import com.android.dx.io.instructions.DecodedInstruction;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

public final class Grep {
  private final CodeReader codeReader = new CodeReader();
  
  private int count = 0;
  
  private ClassDef currentClass;
  
  private ClassData.Method currentMethod;
  
  private final Dex dex;
  
  private final PrintWriter out;
  
  private final Set<Integer> stringIds;
  
  public Grep(Dex paramDex, Pattern paramPattern, PrintWriter paramPrintWriter) {
    this.dex = paramDex;
    this.out = paramPrintWriter;
    this.stringIds = getStringIds(paramDex, paramPattern);
    this.codeReader.setStringVisitor(new CodeReader.Visitor() {
          public void visit(DecodedInstruction[] param1ArrayOfDecodedInstruction, DecodedInstruction param1DecodedInstruction) {
            Grep.this.encounterString(param1DecodedInstruction.getIndex());
          }
        });
  }
  
  private void encounterString(int paramInt) {
    if (this.stringIds.contains(Integer.valueOf(paramInt))) {
      PrintWriter printWriter = this.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(location());
      stringBuilder.append(" ");
      stringBuilder.append(this.dex.strings().get(paramInt));
      printWriter.println(stringBuilder.toString());
      this.count++;
    } 
  }
  
  private Set<Integer> getStringIds(Dex paramDex, Pattern paramPattern) {
    HashSet<Integer> hashSet = new HashSet();
    Iterator<String> iterator = paramDex.strings().iterator();
    for (byte b = 0; iterator.hasNext(); b++) {
      if (paramPattern.matcher(iterator.next()).find())
        hashSet.add(Integer.valueOf(b)); 
    } 
    return hashSet;
  }
  
  private String location() {
    String str1 = this.dex.typeNames().get(this.currentClass.getTypeIndex());
    String str2 = str1;
    if (this.currentMethod != null) {
      MethodId methodId = this.dex.methodIds().get(this.currentMethod.getMethodIndex());
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str1);
      stringBuilder.append(".");
      stringBuilder.append(this.dex.strings().get(methodId.getNameIndex()));
      str2 = stringBuilder.toString();
    } 
    return str2;
  }
  
  private void readArray(EncodedValueReader paramEncodedValueReader) {
    int i = paramEncodedValueReader.readArray();
    for (byte b = 0; b < i; b++) {
      int j = paramEncodedValueReader.peek();
      if (j != 23) {
        if (j == 28)
          readArray(paramEncodedValueReader); 
      } else {
        encounterString(paramEncodedValueReader.readString());
      } 
    } 
  }
  
  public int grep() {
    for (ClassDef classDef : this.dex.classDefs()) {
      this.currentClass = classDef;
      this.currentMethod = null;
      if (classDef.getClassDataOffset() == 0)
        continue; 
      ClassData classData = this.dex.readClassData(classDef);
      null = classDef.getStaticValuesOffset();
      if (null != 0)
        readArray(new EncodedValueReader((ByteInput)this.dex.open(null))); 
      for (ClassData.Method method : classData.allMethods()) {
        this.currentMethod = method;
        if (method.getCodeOffset() != 0)
          this.codeReader.visitAll(this.dex.readCode(method).getInstructions()); 
      } 
    } 
    this.currentClass = null;
    this.currentMethod = null;
    return this.count;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\command\grep\Grep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */