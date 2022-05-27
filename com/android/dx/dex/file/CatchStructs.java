package com.android.dx.dex.file;

import com.android.dx.dex.code.CatchHandlerList;
import com.android.dx.dex.code.CatchTable;
import com.android.dx.dex.code.DalvCode;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.ByteArrayAnnotatedOutput;
import com.android.dx.util.Hex;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public final class CatchStructs {
  private static final int TRY_ITEM_WRITE_SIZE = 8;
  
  private final DalvCode code;
  
  private int encodedHandlerHeaderSize;
  
  private byte[] encodedHandlers;
  
  private TreeMap<CatchHandlerList, Integer> handlerOffsets;
  
  private CatchTable table;
  
  public CatchStructs(DalvCode paramDalvCode) {
    this.code = paramDalvCode;
    this.table = null;
    this.encodedHandlers = null;
    this.encodedHandlerHeaderSize = 0;
    this.handlerOffsets = null;
  }
  
  private static void annotateAndConsumeHandlers(CatchHandlerList paramCatchHandlerList, int paramInt1, int paramInt2, String paramString, PrintWriter paramPrintWriter, AnnotatedOutput paramAnnotatedOutput) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Hex.u2(paramInt1));
    stringBuilder.append(": ");
    String str = paramCatchHandlerList.toHuman(paramString, stringBuilder.toString());
    if (paramPrintWriter != null)
      paramPrintWriter.println(str); 
    paramAnnotatedOutput.annotate(paramInt2, str);
  }
  
  private void annotateEntries(String paramString, PrintWriter paramPrintWriter, AnnotatedOutput paramAnnotatedOutput) {
    CatchHandlerList catchHandlerList;
    int j;
    boolean bool2;
    finishProcessingIfNecessary();
    boolean bool1 = false;
    if (paramAnnotatedOutput != null) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      j = 6;
    } else {
      j = 0;
    } 
    if (i) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    int k = this.table.size();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append("  ");
    String str = stringBuilder2.toString();
    if (i) {
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(paramString);
      stringBuilder2.append("tries:");
      paramAnnotatedOutput.annotate(0, stringBuilder2.toString());
    } else {
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(paramString);
      stringBuilder2.append("tries:");
      paramPrintWriter.println(stringBuilder2.toString());
    } 
    for (byte b = 0; b < k; b++) {
      CatchTable.Entry entry = this.table.get(b);
      CatchHandlerList catchHandlerList1 = entry.getHandlers();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append("try ");
      stringBuilder.append(Hex.u2or4(entry.getStart()));
      stringBuilder.append("..");
      stringBuilder.append(Hex.u2or4(entry.getEnd()));
      String str2 = stringBuilder.toString();
      String str1 = catchHandlerList1.toHuman(str, "");
      if (i) {
        paramAnnotatedOutput.annotate(j, str2);
        paramAnnotatedOutput.annotate(bool2, str1);
      } else {
        paramPrintWriter.println(str2);
        paramPrintWriter.println(str1);
      } 
    } 
    if (!i)
      return; 
    stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append("handlers:");
    paramAnnotatedOutput.annotate(0, stringBuilder2.toString());
    int i = this.encodedHandlerHeaderSize;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str);
    stringBuilder1.append("size: ");
    stringBuilder1.append(Hex.u2(this.handlerOffsets.size()));
    paramAnnotatedOutput.annotate(i, stringBuilder1.toString());
    stringBuilder1 = null;
    Iterator<Map.Entry> iterator = this.handlerOffsets.entrySet().iterator();
    for (i = bool1; iterator.hasNext(); i = j) {
      Map.Entry entry = iterator.next();
      CatchHandlerList catchHandlerList1 = (CatchHandlerList)entry.getKey();
      j = ((Integer)entry.getValue()).intValue();
      if (stringBuilder1 != null)
        annotateAndConsumeHandlers((CatchHandlerList)stringBuilder1, i, j - i, str, paramPrintWriter, paramAnnotatedOutput); 
      catchHandlerList = catchHandlerList1;
    } 
    annotateAndConsumeHandlers(catchHandlerList, i, this.encodedHandlers.length - i, str, paramPrintWriter, paramAnnotatedOutput);
  }
  
  private void finishProcessingIfNecessary() {
    if (this.table == null)
      this.table = this.code.getCatches(); 
  }
  
  public void debugPrint(PrintWriter paramPrintWriter, String paramString) {
    annotateEntries(paramString, paramPrintWriter, null);
  }
  
  public void encode(DexFile paramDexFile) {
    finishProcessingIfNecessary();
    TypeIdsSection typeIdsSection = paramDexFile.getTypeIds();
    int i = this.table.size();
    this.handlerOffsets = new TreeMap<CatchHandlerList, Integer>();
    int j;
    for (j = 0; j < i; j++)
      this.handlerOffsets.put(this.table.get(j).getHandlers(), null); 
    if (this.handlerOffsets.size() <= 65535) {
      ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput();
      this.encodedHandlerHeaderSize = byteArrayAnnotatedOutput.writeUleb128(this.handlerOffsets.size());
      for (Map.Entry<CatchHandlerList, Integer> entry : this.handlerOffsets.entrySet()) {
        CatchHandlerList catchHandlerList = (CatchHandlerList)entry.getKey();
        j = catchHandlerList.size();
        boolean bool = catchHandlerList.catchesAll();
        entry.setValue(Integer.valueOf(byteArrayAnnotatedOutput.getCursor()));
        if (bool) {
          byteArrayAnnotatedOutput.writeSleb128(-(j - 1));
          j--;
        } else {
          byteArrayAnnotatedOutput.writeSleb128(j);
        } 
        for (i = 0; i < j; i++) {
          CatchHandlerList.Entry entry1 = catchHandlerList.get(i);
          byteArrayAnnotatedOutput.writeUleb128(typeIdsSection.indexOf(entry1.getExceptionType()));
          byteArrayAnnotatedOutput.writeUleb128(entry1.getHandler());
        } 
        if (bool)
          byteArrayAnnotatedOutput.writeUleb128(catchHandlerList.get(j).getHandler()); 
      } 
      this.encodedHandlers = byteArrayAnnotatedOutput.toByteArray();
      return;
    } 
    throw new UnsupportedOperationException("too many catch handlers");
  }
  
  public int triesSize() {
    finishProcessingIfNecessary();
    return this.table.size();
  }
  
  public int writeSize() {
    return triesSize() * 8 + this.encodedHandlers.length;
  }
  
  public void writeTo(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    finishProcessingIfNecessary();
    if (paramAnnotatedOutput.annotates())
      annotateEntries("  ", null, paramAnnotatedOutput); 
    int i = this.table.size();
    byte b = 0;
    while (b < i) {
      CatchTable.Entry entry = this.table.get(b);
      int j = entry.getStart();
      int k = entry.getEnd();
      int m = k - j;
      if (m < 65536) {
        paramAnnotatedOutput.writeInt(j);
        paramAnnotatedOutput.writeShort(m);
        paramAnnotatedOutput.writeShort(((Integer)this.handlerOffsets.get(entry.getHandlers())).intValue());
        b++;
        continue;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("bogus exception range: ");
      stringBuilder.append(Hex.u4(j));
      stringBuilder.append("..");
      stringBuilder.append(Hex.u4(k));
      throw new UnsupportedOperationException(stringBuilder.toString());
    } 
    paramAnnotatedOutput.write(this.encodedHandlers);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\CatchStructs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */