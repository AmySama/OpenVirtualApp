package com.android.dx.dex.file;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.dex.DexOptions;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstBaseMethodRef;
import com.android.dx.rop.cst.CstEnumRef;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstMethodHandle;
import com.android.dx.rop.cst.CstProtoRef;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.ByteArrayAnnotatedOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Adler32;

public final class DexFile {
  private final MixedItemSection byteData;
  
  private final CallSiteIdsSection callSiteIds;
  
  private final MixedItemSection classData;
  
  private final ClassDefsSection classDefs;
  
  private final DexOptions dexOptions;
  
  private int dumpWidth;
  
  private final FieldIdsSection fieldIds;
  
  private int fileSize;
  
  private final HeaderSection header;
  
  private final MixedItemSection map;
  
  private final MethodHandlesSection methodHandles;
  
  private final MethodIdsSection methodIds;
  
  private final ProtoIdsSection protoIds;
  
  private final Section[] sections;
  
  private final MixedItemSection stringData;
  
  private final StringIdsSection stringIds;
  
  private final TypeIdsSection typeIds;
  
  private final MixedItemSection typeLists;
  
  private final MixedItemSection wordData;
  
  public DexFile(DexOptions paramDexOptions) {
    this.dexOptions = paramDexOptions;
    this.header = new HeaderSection(this);
    this.typeLists = new MixedItemSection(null, this, 4, MixedItemSection.SortType.NONE);
    this.wordData = new MixedItemSection("word_data", this, 4, MixedItemSection.SortType.TYPE);
    this.stringData = new MixedItemSection("string_data", this, 1, MixedItemSection.SortType.INSTANCE);
    this.classData = new MixedItemSection(null, this, 1, MixedItemSection.SortType.NONE);
    this.byteData = new MixedItemSection("byte_data", this, 1, MixedItemSection.SortType.TYPE);
    this.stringIds = new StringIdsSection(this);
    this.typeIds = new TypeIdsSection(this);
    this.protoIds = new ProtoIdsSection(this);
    this.fieldIds = new FieldIdsSection(this);
    this.methodIds = new MethodIdsSection(this);
    this.classDefs = new ClassDefsSection(this);
    this.map = new MixedItemSection("map", this, 4, MixedItemSection.SortType.NONE);
    if (paramDexOptions.apiIsSupported(26)) {
      this.callSiteIds = new CallSiteIdsSection(this);
      MethodHandlesSection methodHandlesSection = new MethodHandlesSection(this);
      this.methodHandles = methodHandlesSection;
      this.sections = new Section[] { 
          this.header, this.stringIds, this.typeIds, this.protoIds, this.fieldIds, this.methodIds, this.classDefs, this.callSiteIds, methodHandlesSection, this.wordData, 
          this.typeLists, this.stringData, this.byteData, this.classData, this.map };
    } else {
      this.callSiteIds = null;
      this.methodHandles = null;
      this.sections = new Section[] { 
          this.header, this.stringIds, this.typeIds, this.protoIds, this.fieldIds, this.methodIds, this.classDefs, this.wordData, this.typeLists, this.stringData, 
          this.byteData, this.classData, this.map };
    } 
    this.fileSize = -1;
    this.dumpWidth = 79;
  }
  
  private static void calcChecksum(byte[] paramArrayOfbyte, int paramInt) {
    Adler32 adler32 = new Adler32();
    adler32.update(paramArrayOfbyte, 12, paramInt - 12);
    paramInt = (int)adler32.getValue();
    paramArrayOfbyte[8] = (byte)(byte)paramInt;
    paramArrayOfbyte[9] = (byte)(byte)(paramInt >> 8);
    paramArrayOfbyte[10] = (byte)(byte)(paramInt >> 16);
    paramArrayOfbyte[11] = (byte)(byte)(paramInt >> 24);
  }
  
  private static void calcSignature(byte[] paramArrayOfbyte, int paramInt) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
      messageDigest.update(paramArrayOfbyte, 32, paramInt - 32);
      try {
        paramInt = messageDigest.digest(paramArrayOfbyte, 12, 20);
        if (paramInt == 20)
          return; 
        RuntimeException runtimeException = new RuntimeException();
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("unexpected digest write: ");
        stringBuilder.append(paramInt);
        stringBuilder.append(" bytes");
        this(stringBuilder.toString());
        throw runtimeException;
      } catch (DigestException digestException) {
        throw new RuntimeException(digestException);
      } 
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new RuntimeException(noSuchAlgorithmException);
    } 
  }
  
  private ByteArrayAnnotatedOutput toDex0(boolean paramBoolean1, boolean paramBoolean2, Storage paramStorage) {
    StringBuilder stringBuilder;
    byte[] arrayOfByte;
    ExceptionWithContext exceptionWithContext;
    this.classDefs.prepare();
    this.classData.prepare();
    this.wordData.prepare();
    if (this.dexOptions.apiIsSupported(26))
      this.callSiteIds.prepare(); 
    this.byteData.prepare();
    if (this.dexOptions.apiIsSupported(26))
      this.methodHandles.prepare(); 
    this.methodIds.prepare();
    this.fieldIds.prepare();
    this.protoIds.prepare();
    this.typeLists.prepare();
    this.typeIds.prepare();
    this.stringIds.prepare();
    this.stringData.prepare();
    this.header.prepare();
    int i = this.sections.length;
    boolean bool = false;
    byte b = 0;
    int j = 0;
    while (true) {
      if (b < i) {
        Section section = this.sections[b];
        if ((section != this.callSiteIds && section != this.methodHandles) || !section.items().isEmpty()) {
          int k = section.setFileOffset(j);
          if (k >= j) {
            try {
              if (section == this.map) {
                MapItem.addMap(this.sections, this.map);
                this.map.prepare();
              } 
              if (section instanceof MixedItemSection)
                ((MixedItemSection)section).placeItems(); 
              j = section.writeSize();
              j += k;
              b++;
            } catch (RuntimeException runtimeException) {
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append("...while writing section ");
              stringBuilder1.append(b);
              throw ExceptionWithContext.withContext(runtimeException, stringBuilder1.toString());
            } 
            continue;
          } 
          stringBuilder = new StringBuilder();
          stringBuilder.append("bogus placement for section ");
          stringBuilder.append(b);
          throw new RuntimeException(stringBuilder.toString());
        } 
      } else {
        break;
      } 
      b++;
    } 
    this.fileSize = j;
    if (stringBuilder == null) {
      arrayOfByte = new byte[j];
    } else {
      arrayOfByte = arrayOfByte.getStorage(j);
    } 
    ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput(arrayOfByte);
    b = bool;
    if (paramBoolean1) {
      byteArrayAnnotatedOutput.enableAnnotations(this.dumpWidth, paramBoolean2);
      b = bool;
    } 
    while (b < i) {
      try {
        Section section = this.sections[b];
        if ((section != this.callSiteIds && section != this.methodHandles) || !section.items().isEmpty()) {
          j = section.getFileOffset() - byteArrayAnnotatedOutput.getCursor();
          if (j >= 0) {
            byteArrayAnnotatedOutput.writeZeroes(j);
            section.writeTo((AnnotatedOutput)byteArrayAnnotatedOutput);
          } else {
            exceptionWithContext = new ExceptionWithContext();
            StringBuilder stringBuilder1 = new StringBuilder();
            this();
            stringBuilder1.append("excess write of ");
            stringBuilder1.append(-j);
            this(stringBuilder1.toString());
            throw exceptionWithContext;
          } 
        } 
        b++;
      } catch (RuntimeException runtimeException) {
        if (runtimeException instanceof ExceptionWithContext) {
          exceptionWithContext = (ExceptionWithContext)runtimeException;
        } else {
          exceptionWithContext = new ExceptionWithContext((Throwable)exceptionWithContext);
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("...while writing section ");
        stringBuilder1.append(b);
        exceptionWithContext.addContext(stringBuilder1.toString());
        throw exceptionWithContext;
      } 
    } 
    if (byteArrayAnnotatedOutput.getCursor() == this.fileSize) {
      calcSignature((byte[])exceptionWithContext, byteArrayAnnotatedOutput.getCursor());
      calcChecksum((byte[])exceptionWithContext, byteArrayAnnotatedOutput.getCursor());
      if (paramBoolean1) {
        this.wordData.writeIndexAnnotation((AnnotatedOutput)byteArrayAnnotatedOutput, ItemType.TYPE_CODE_ITEM, "\nmethod code index:\n\n");
        getStatistics().writeAnnotation((AnnotatedOutput)byteArrayAnnotatedOutput);
        byteArrayAnnotatedOutput.finishAnnotating();
      } 
      return byteArrayAnnotatedOutput;
    } 
    throw new RuntimeException("foreshortened write");
  }
  
  public void add(ClassDefItem paramClassDefItem) {
    this.classDefs.add(paramClassDefItem);
  }
  
  IndexedItem findItemOrNull(Constant paramConstant) {
    return (paramConstant instanceof CstString) ? this.stringIds.get(paramConstant) : ((paramConstant instanceof CstType) ? this.typeIds.get(paramConstant) : ((paramConstant instanceof CstBaseMethodRef) ? this.methodIds.get(paramConstant) : ((paramConstant instanceof CstFieldRef) ? this.fieldIds.get(paramConstant) : ((paramConstant instanceof CstEnumRef) ? this.fieldIds.intern(((CstEnumRef)paramConstant).getFieldRef()) : ((paramConstant instanceof CstProtoRef) ? this.protoIds.get(paramConstant) : ((paramConstant instanceof CstMethodHandle) ? this.methodHandles.get(paramConstant) : ((paramConstant instanceof com.android.dx.rop.cst.CstCallSiteRef) ? this.callSiteIds.get(paramConstant) : null)))))));
  }
  
  MixedItemSection getByteData() {
    return this.byteData;
  }
  
  public CallSiteIdsSection getCallSiteIds() {
    return this.callSiteIds;
  }
  
  MixedItemSection getClassData() {
    return this.classData;
  }
  
  public ClassDefsSection getClassDefs() {
    return this.classDefs;
  }
  
  public ClassDefItem getClassOrNull(String paramString) {
    try {
      Type type = Type.internClassName(paramString);
      ClassDefsSection classDefsSection = this.classDefs;
      CstType cstType = new CstType();
      this(type);
      return (ClassDefItem)classDefsSection.get((Constant)cstType);
    } catch (IllegalArgumentException illegalArgumentException) {
      return null;
    } 
  }
  
  public DexOptions getDexOptions() {
    return this.dexOptions;
  }
  
  public FieldIdsSection getFieldIds() {
    return this.fieldIds;
  }
  
  public int getFileSize() {
    int i = this.fileSize;
    if (i >= 0)
      return i; 
    throw new RuntimeException("file size not yet known");
  }
  
  Section getFirstDataSection() {
    return this.wordData;
  }
  
  Section getLastDataSection() {
    return this.map;
  }
  
  MixedItemSection getMap() {
    return this.map;
  }
  
  public MethodHandlesSection getMethodHandles() {
    return this.methodHandles;
  }
  
  public MethodIdsSection getMethodIds() {
    return this.methodIds;
  }
  
  ProtoIdsSection getProtoIds() {
    return this.protoIds;
  }
  
  public Statistics getStatistics() {
    Statistics statistics = new Statistics();
    Section[] arrayOfSection = this.sections;
    int i = arrayOfSection.length;
    for (byte b = 0; b < i; b++)
      statistics.addAll(arrayOfSection[b]); 
    return statistics;
  }
  
  MixedItemSection getStringData() {
    return this.stringData;
  }
  
  StringIdsSection getStringIds() {
    return this.stringIds;
  }
  
  public TypeIdsSection getTypeIds() {
    return this.typeIds;
  }
  
  MixedItemSection getTypeLists() {
    return this.typeLists;
  }
  
  MixedItemSection getWordData() {
    return this.wordData;
  }
  
  void internIfAppropriate(Constant paramConstant) {
    if (paramConstant != null) {
      if (paramConstant instanceof CstString) {
        this.stringIds.intern((CstString)paramConstant);
      } else if (paramConstant instanceof CstType) {
        this.typeIds.intern((CstType)paramConstant);
      } else if (paramConstant instanceof CstBaseMethodRef) {
        this.methodIds.intern((CstBaseMethodRef)paramConstant);
      } else if (paramConstant instanceof CstFieldRef) {
        this.fieldIds.intern((CstFieldRef)paramConstant);
      } else if (paramConstant instanceof CstEnumRef) {
        this.fieldIds.intern(((CstEnumRef)paramConstant).getFieldRef());
      } else if (paramConstant instanceof CstProtoRef) {
        this.protoIds.intern(((CstProtoRef)paramConstant).getPrototype());
      } else if (paramConstant instanceof CstMethodHandle) {
        this.methodHandles.intern((CstMethodHandle)paramConstant);
      } 
      return;
    } 
    throw new NullPointerException("cst == null");
  }
  
  public boolean isEmpty() {
    return this.classDefs.items().isEmpty();
  }
  
  public void setDumpWidth(int paramInt) {
    if (paramInt >= 40) {
      this.dumpWidth = paramInt;
      return;
    } 
    throw new IllegalArgumentException("dumpWidth < 40");
  }
  
  public byte[] toDex(Writer paramWriter, boolean paramBoolean) throws IOException {
    boolean bool;
    if (paramWriter != null) {
      bool = true;
    } else {
      bool = false;
    } 
    ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = toDex0(bool, paramBoolean, null);
    if (bool)
      byteArrayAnnotatedOutput.writeAnnotationsTo(paramWriter); 
    return byteArrayAnnotatedOutput.getArray();
  }
  
  public ByteArrayAnnotatedOutput writeTo(Storage paramStorage) {
    return toDex0(false, false, paramStorage);
  }
  
  public void writeTo(OutputStream paramOutputStream, Storage paramStorage, Writer paramWriter, boolean paramBoolean) throws IOException {
    boolean bool;
    if (paramWriter != null) {
      bool = true;
    } else {
      bool = false;
    } 
    ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = toDex0(bool, paramBoolean, paramStorage);
    if (paramOutputStream != null)
      paramOutputStream.write(byteArrayAnnotatedOutput.getArray()); 
    if (bool)
      byteArrayAnnotatedOutput.writeAnnotationsTo(paramWriter); 
  }
  
  public void writeTo(OutputStream paramOutputStream, Writer paramWriter, boolean paramBoolean) throws IOException {
    writeTo(paramOutputStream, null, paramWriter, paramBoolean);
  }
  
  public static final class Storage {
    byte[] storage;
    
    public Storage(byte[] param1ArrayOfbyte) {
      this.storage = param1ArrayOfbyte;
    }
    
    public byte[] getStorage(int param1Int) {
      if (this.storage.length < param1Int) {
        Logger logger = Logger.getAnonymousLogger();
        Level level = Level.FINER;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DexFile storage too small  ");
        stringBuilder.append(this.storage.length);
        stringBuilder.append(" vs ");
        stringBuilder.append(param1Int);
        logger.log(level, stringBuilder.toString());
        this.storage = new byte[param1Int];
      } 
      return this.storage;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\DexFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */