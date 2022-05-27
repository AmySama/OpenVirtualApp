package com.android.dx.io;

import com.android.dex.ClassDef;
import com.android.dex.Dex;
import com.android.dex.FieldId;
import com.android.dex.MethodId;
import com.android.dex.ProtoId;
import com.android.dex.TableOfContents;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

public final class DexIndexPrinter {
  private final Dex dex;
  
  private final TableOfContents tableOfContents;
  
  public DexIndexPrinter(File paramFile) throws IOException {
    Dex dex = new Dex(paramFile);
    this.dex = dex;
    this.tableOfContents = dex.getTableOfContents();
  }
  
  public static void main(String[] paramArrayOfString) throws IOException {
    DexIndexPrinter dexIndexPrinter = new DexIndexPrinter(new File(paramArrayOfString[0]));
    dexIndexPrinter.printMap();
    dexIndexPrinter.printStrings();
    dexIndexPrinter.printTypeIds();
    dexIndexPrinter.printProtoIds();
    dexIndexPrinter.printFieldIds();
    dexIndexPrinter.printMethodIds();
    dexIndexPrinter.printTypeLists();
    dexIndexPrinter.printClassDefs();
  }
  
  private void printClassDefs() {
    Iterator<ClassDef> iterator = this.dex.classDefs().iterator();
    for (byte b = 0; iterator.hasNext(); b++) {
      ClassDef classDef = iterator.next();
      PrintStream printStream = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("class def ");
      stringBuilder.append(b);
      stringBuilder.append(": ");
      stringBuilder.append(classDef);
      printStream.println(stringBuilder.toString());
    } 
  }
  
  private void printFieldIds() throws IOException {
    Iterator<FieldId> iterator = this.dex.fieldIds().iterator();
    for (byte b = 0; iterator.hasNext(); b++) {
      FieldId fieldId = iterator.next();
      PrintStream printStream = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("field ");
      stringBuilder.append(b);
      stringBuilder.append(": ");
      stringBuilder.append(fieldId);
      printStream.println(stringBuilder.toString());
    } 
  }
  
  private void printMap() {
    for (TableOfContents.Section section : this.tableOfContents.sections) {
      if (section.off != -1) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("section ");
        stringBuilder.append(Integer.toHexString(section.type));
        stringBuilder.append(" off=");
        stringBuilder.append(Integer.toHexString(section.off));
        stringBuilder.append(" size=");
        stringBuilder.append(Integer.toHexString(section.size));
        stringBuilder.append(" byteCount=");
        stringBuilder.append(Integer.toHexString(section.byteCount));
        printStream.println(stringBuilder.toString());
      } 
    } 
  }
  
  private void printMethodIds() throws IOException {
    Iterator<MethodId> iterator = this.dex.methodIds().iterator();
    for (byte b = 0; iterator.hasNext(); b++) {
      MethodId methodId = iterator.next();
      PrintStream printStream = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("methodId ");
      stringBuilder.append(b);
      stringBuilder.append(": ");
      stringBuilder.append(methodId);
      printStream.println(stringBuilder.toString());
    } 
  }
  
  private void printProtoIds() throws IOException {
    Iterator<ProtoId> iterator = this.dex.protoIds().iterator();
    for (byte b = 0; iterator.hasNext(); b++) {
      ProtoId protoId = iterator.next();
      PrintStream printStream = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("proto ");
      stringBuilder.append(b);
      stringBuilder.append(": ");
      stringBuilder.append(protoId);
      printStream.println(stringBuilder.toString());
    } 
  }
  
  private void printStrings() throws IOException {
    Iterator<String> iterator = this.dex.strings().iterator();
    for (byte b = 0; iterator.hasNext(); b++) {
      String str = iterator.next();
      PrintStream printStream = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("string ");
      stringBuilder.append(b);
      stringBuilder.append(": ");
      stringBuilder.append(str);
      printStream.println(stringBuilder.toString());
    } 
  }
  
  private void printTypeIds() throws IOException {
    Iterator<Integer> iterator = this.dex.typeIds().iterator();
    for (byte b = 0; iterator.hasNext(); b++) {
      Integer integer = iterator.next();
      PrintStream printStream = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("type ");
      stringBuilder.append(b);
      stringBuilder.append(": ");
      stringBuilder.append(this.dex.strings().get(integer.intValue()));
      printStream.println(stringBuilder.toString());
    } 
  }
  
  private void printTypeLists() throws IOException {
    if (this.tableOfContents.typeLists.off == -1) {
      System.out.println("No type lists");
      return;
    } 
    Dex.Section section = this.dex.open(this.tableOfContents.typeLists.off);
    for (byte b = 0; b < this.tableOfContents.typeLists.size; b++) {
      int i = section.readInt();
      PrintStream printStream = System.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Type list i=");
      stringBuilder.append(b);
      stringBuilder.append(", size=");
      stringBuilder.append(i);
      stringBuilder.append(", elements=");
      printStream.print(stringBuilder.toString());
      for (byte b1 = 0; b1 < i; b1++) {
        PrintStream printStream1 = System.out;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(" ");
        stringBuilder1.append(this.dex.typeNames().get(section.readShort()));
        printStream1.print(stringBuilder1.toString());
      } 
      if (i % 2 == 1)
        section.readShort(); 
      System.out.println();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\DexIndexPrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */