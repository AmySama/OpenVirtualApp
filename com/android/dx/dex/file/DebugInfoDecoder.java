package com.android.dx.dex.file;

import com.android.dex.Leb128;
import com.android.dex.util.ByteArrayByteInput;
import com.android.dex.util.ByteInput;
import com.android.dex.util.ExceptionWithContext;
import com.android.dx.dex.code.DalvCode;
import com.android.dx.dex.code.DalvInsnList;
import com.android.dx.dex.code.LocalList;
import com.android.dx.dex.code.PositionList;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DebugInfoDecoder {
  private int address = 0;
  
  private final int codesize;
  
  private final Prototype desc;
  
  private final byte[] encoded;
  
  private final DexFile file;
  
  private final boolean isStatic;
  
  private final LocalEntry[] lastEntryForReg;
  
  private int line = 1;
  
  private final ArrayList<LocalEntry> locals;
  
  private final ArrayList<PositionEntry> positions;
  
  private final int regSize;
  
  private final int thisStringIdx;
  
  DebugInfoDecoder(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean, CstMethodRef paramCstMethodRef, DexFile paramDexFile) {
    if (paramArrayOfbyte != null) {
      this.encoded = paramArrayOfbyte;
      this.isStatic = paramBoolean;
      this.desc = paramCstMethodRef.getPrototype();
      this.file = paramDexFile;
      this.regSize = paramInt2;
      this.positions = new ArrayList<PositionEntry>();
      this.locals = new ArrayList<LocalEntry>();
      this.codesize = paramInt1;
      this.lastEntryForReg = new LocalEntry[paramInt2];
      paramInt2 = -1;
      try {
        StringIdsSection stringIdsSection = paramDexFile.getStringIds();
        CstString cstString = new CstString();
        this("this");
        paramInt1 = stringIdsSection.indexOf(cstString);
      } catch (IllegalArgumentException illegalArgumentException) {
        paramInt1 = paramInt2;
      } 
      this.thisStringIdx = paramInt1;
      return;
    } 
    throw new NullPointerException("encoded == null");
  }
  
  private void decode0() throws IOException {
    ByteArrayByteInput byteArrayByteInput = new ByteArrayByteInput(this.encoded);
    this.line = Leb128.readUnsignedLeb128((ByteInput)byteArrayByteInput);
    int i = Leb128.readUnsignedLeb128((ByteInput)byteArrayByteInput);
    StdTypeList stdTypeList = this.desc.getParameterTypes();
    int j = getParamBase();
    if (i == stdTypeList.size()) {
      int k = j;
      if (!this.isStatic) {
        LocalEntry localEntry = new LocalEntry(0, true, j, this.thisStringIdx, 0, 0);
        this.locals.add(localEntry);
        this.lastEntryForReg[j] = localEntry;
        k = j + 1;
      } 
      for (j = 0; j < i; j++) {
        LocalEntry localEntry;
        Type type = stdTypeList.getType(j);
        int m = readStringIndex((ByteInput)byteArrayByteInput);
        if (m == -1) {
          localEntry = new LocalEntry(0, true, k, -1, 0, 0);
        } else {
          localEntry = new LocalEntry(0, true, k, m, 0, 0);
        } 
        this.locals.add(localEntry);
        this.lastEntryForReg[k] = localEntry;
        k += type.getCategory();
      } 
      while (true) {
        StringBuilder stringBuilder1;
        StringBuilder stringBuilder2;
        LocalEntry localEntry;
        int m;
        k = byteArrayByteInput.readByte() & 0xFF;
        switch (k) {
          case 7:
          case 8:
          case 9:
            continue;
          default:
            if (k >= 10) {
              j = k - 10;
              k = this.address + j / 15;
              this.address = k;
              j = this.line + j % 15 - 4;
              this.line = j;
              this.positions.add(new PositionEntry(k, j));
              continue;
            } 
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Invalid extended opcode encountered ");
            stringBuilder2.append(k);
            throw new RuntimeException(stringBuilder2.toString());
          case 6:
            k = Leb128.readUnsignedLeb128((ByteInput)byteArrayByteInput);
            try {
              LocalEntry localEntry1 = this.lastEntryForReg[k];
              if (!localEntry1.isStart) {
                localEntry1 = new LocalEntry(this.address, true, k, localEntry1.nameIndex, localEntry1.typeIndex, 0);
                this.locals.add(localEntry1);
                this.lastEntryForReg[k] = localEntry1;
                continue;
              } 
              RuntimeException runtimeException = new RuntimeException();
              stringBuilder1 = new StringBuilder();
              this();
              stringBuilder1.append("nonsensical RESTART_LOCAL on live register v");
              stringBuilder1.append(k);
              this(stringBuilder1.toString());
              throw runtimeException;
            } catch (NullPointerException nullPointerException) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Encountered RESTART_LOCAL on new v");
              stringBuilder.append(k);
              throw new RuntimeException(stringBuilder.toString());
            } 
          case 5:
            k = Leb128.readUnsignedLeb128((ByteInput)stringBuilder1);
            try {
              LocalEntry localEntry1 = this.lastEntryForReg[k];
              if (localEntry1.isStart) {
                localEntry1 = new LocalEntry(this.address, false, k, localEntry1.nameIndex, localEntry1.typeIndex, localEntry1.signatureIndex);
                this.locals.add(localEntry1);
                this.lastEntryForReg[k] = localEntry1;
                continue;
              } 
              RuntimeException runtimeException = new RuntimeException();
              stringBuilder1 = new StringBuilder();
              this();
              stringBuilder1.append("nonsensical END_LOCAL on dead register v");
              stringBuilder1.append(k);
              this(stringBuilder1.toString());
              throw runtimeException;
            } catch (NullPointerException nullPointerException) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Encountered END_LOCAL on new v");
              stringBuilder.append(k);
              throw new RuntimeException(stringBuilder.toString());
            } 
          case 4:
            m = Leb128.readUnsignedLeb128((ByteInput)stringBuilder1);
            i = readStringIndex((ByteInput)stringBuilder1);
            j = readStringIndex((ByteInput)stringBuilder1);
            k = readStringIndex((ByteInput)stringBuilder1);
            localEntry = new LocalEntry(this.address, true, m, i, j, k);
            this.locals.add(localEntry);
            this.lastEntryForReg[m] = localEntry;
            continue;
          case 3:
            j = Leb128.readUnsignedLeb128((ByteInput)stringBuilder1);
            i = readStringIndex((ByteInput)stringBuilder1);
            k = readStringIndex((ByteInput)stringBuilder1);
            localEntry = new LocalEntry(this.address, true, j, i, k, 0);
            this.locals.add(localEntry);
            this.lastEntryForReg[j] = localEntry;
            continue;
          case 2:
            this.line += Leb128.readSignedLeb128((ByteInput)stringBuilder1);
            continue;
          case 1:
            this.address += Leb128.readUnsignedLeb128((ByteInput)stringBuilder1);
            continue;
          case 0:
            break;
        } 
        return;
      } 
    } 
    throw new RuntimeException("Mismatch between parameters_size and prototype");
  }
  
  private int getParamBase() {
    return this.regSize - this.desc.getParameterTypes().getWordCount() - (this.isStatic ^ true);
  }
  
  private int readStringIndex(ByteInput paramByteInput) throws IOException {
    return Leb128.readUnsignedLeb128(paramByteInput) - 1;
  }
  
  public static void validateEncode(byte[] paramArrayOfbyte, DexFile paramDexFile, CstMethodRef paramCstMethodRef, DalvCode paramDalvCode, boolean paramBoolean) {
    PositionList positionList = paramDalvCode.getPositions();
    LocalList localList = paramDalvCode.getLocals();
    DalvInsnList dalvInsnList = paramDalvCode.getInsns();
    int i = dalvInsnList.codeSize();
    int j = dalvInsnList.getRegistersSize();
    try {
      validateEncode0(paramArrayOfbyte, i, j, paramBoolean, paramCstMethodRef, paramDexFile, positionList, localList);
      return;
    } catch (RuntimeException runtimeException) {
      System.err.println("instructions:");
      dalvInsnList.debugPrint(System.err, "  ", true);
      System.err.println("local list:");
      localList.debugPrint(System.err, "  ");
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("while processing ");
      stringBuilder.append(paramCstMethodRef.toHuman());
      throw ExceptionWithContext.withContext(runtimeException, stringBuilder.toString());
    } 
  }
  
  private static void validateEncode0(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean, CstMethodRef paramCstMethodRef, DexFile paramDexFile, PositionList paramPositionList, LocalList paramLocalList) {
    StringBuilder stringBuilder2;
    DebugInfoDecoder debugInfoDecoder = new DebugInfoDecoder(paramArrayOfbyte, paramInt1, paramInt2, paramBoolean, paramCstMethodRef, paramDexFile);
    debugInfoDecoder.decode();
    List<PositionEntry> list = debugInfoDecoder.getPositionList();
    if (list.size() == paramPositionList.size()) {
      Iterator<PositionEntry> iterator = list.iterator();
      while (true) {
        StringBuilder stringBuilder;
        paramBoolean = iterator.hasNext();
        boolean bool = false;
        int i = 0;
        if (paramBoolean) {
          PositionEntry positionEntry = iterator.next();
          paramInt2 = paramPositionList.size() - 1;
          while (true) {
            paramInt1 = i;
            if (paramInt2 >= 0) {
              PositionList.Entry entry = paramPositionList.get(paramInt2);
              if (positionEntry.line == entry.getPosition().getLine() && positionEntry.address == entry.getAddress()) {
                paramInt1 = 1;
                break;
              } 
              paramInt2--;
              continue;
            } 
            break;
          } 
          if (paramInt1 != 0)
            continue; 
          stringBuilder = new StringBuilder();
          stringBuilder.append("Could not match position entry: ");
          stringBuilder.append(positionEntry.address);
          stringBuilder.append(", ");
          stringBuilder.append(positionEntry.line);
          throw new RuntimeException(stringBuilder.toString());
        } 
        list = (List)stringBuilder.getLocals();
        int j = ((DebugInfoDecoder)stringBuilder).thisStringIdx;
        i = list.size();
        int k = stringBuilder.getParamBase();
        paramInt1 = 0;
        while (true) {
          paramInt1++;
          i = paramInt2;
        } 
        j = paramLocalList.size();
        byte b = 0;
        paramInt1 = 0;
        while (true) {
          paramInt2 = bool;
          if (b < j) {
            LocalList.Entry entry = paramLocalList.get(b);
            paramInt2 = paramInt1;
            if (entry.getDisposition() != LocalList.Disposition.END_REPLACED) {
              LocalEntry localEntry;
              StringBuilder stringBuilder3;
              PrintStream printStream;
              while (true) {
                localEntry = (LocalEntry)list.get(paramInt2);
                if (localEntry.nameIndex >= 0)
                  break; 
                paramInt1 = paramInt2 + 1;
                paramInt2 = paramInt1;
                if (paramInt1 >= i) {
                  paramInt2 = paramInt1;
                  break;
                } 
              } 
              paramInt1 = localEntry.address;
              if (localEntry.reg != entry.getRegister()) {
                printStream = System.err;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("local register mismatch at orig ");
                stringBuilder3.append(b);
                stringBuilder3.append(" / decoded ");
                stringBuilder3.append(paramInt2);
                printStream.println(stringBuilder3.toString());
              } else if (((LocalEntry)stringBuilder3).isStart != printStream.isStart()) {
                printStream = System.err;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("local start/end mismatch at orig ");
                stringBuilder3.append(b);
                stringBuilder3.append(" / decoded ");
                stringBuilder3.append(paramInt2);
                printStream.println(stringBuilder3.toString());
              } else if (paramInt1 != printStream.getAddress() && (paramInt1 != 0 || ((LocalEntry)stringBuilder3).reg < k)) {
                printStream = System.err;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("local address mismatch at orig ");
                stringBuilder3.append(b);
                stringBuilder3.append(" / decoded ");
                stringBuilder3.append(paramInt2);
                printStream.println(stringBuilder3.toString());
              } else {
                paramInt1 = paramInt2 + 1;
                b++;
              } 
              paramInt2 = 1;
              break;
            } 
          } else {
            break;
          } 
          b++;
        } 
        if (paramInt2 != 0) {
          System.err.println("decoded locals:");
          for (LocalEntry localEntry : list) {
            PrintStream printStream = System.err;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("  ");
            stringBuilder2.append(localEntry);
            printStream.println(stringBuilder2.toString());
          } 
          throw new RuntimeException("local table problem");
        } 
        return;
      } 
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("Decoded positions table not same size was ");
    stringBuilder1.append(list.size());
    stringBuilder1.append(" expected ");
    stringBuilder1.append(stringBuilder2.size());
    throw new RuntimeException(stringBuilder1.toString());
  }
  
  public void decode() {
    try {
      decode0();
      return;
    } catch (Exception exception) {
      throw ExceptionWithContext.withContext(exception, "...while decoding debug info");
    } 
  }
  
  public List<LocalEntry> getLocals() {
    return this.locals;
  }
  
  public List<PositionEntry> getPositionList() {
    return this.positions;
  }
  
  private static class LocalEntry {
    public int address;
    
    public boolean isStart;
    
    public int nameIndex;
    
    public int reg;
    
    public int signatureIndex;
    
    public int typeIndex;
    
    public LocalEntry(int param1Int1, boolean param1Boolean, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
      this.address = param1Int1;
      this.isStart = param1Boolean;
      this.reg = param1Int2;
      this.nameIndex = param1Int3;
      this.typeIndex = param1Int4;
      this.signatureIndex = param1Int5;
    }
    
    public String toString() {
      String str;
      int i = this.address;
      if (this.isStart) {
        str = "start";
      } else {
        str = "end";
      } 
      return String.format("[%x %s v%d %04x %04x %04x]", new Object[] { Integer.valueOf(i), str, Integer.valueOf(this.reg), Integer.valueOf(this.nameIndex), Integer.valueOf(this.typeIndex), Integer.valueOf(this.signatureIndex) });
    }
  }
  
  private static class PositionEntry {
    public int address;
    
    public int line;
    
    public PositionEntry(int param1Int1, int param1Int2) {
      this.address = param1Int1;
      this.line = param1Int2;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\DebugInfoDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */