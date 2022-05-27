package com.android.dex;

import com.android.dex.util.ByteInput;
import com.android.dex.util.ByteOutput;
import com.android.dex.util.FileUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.zip.Adler32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class Dex {
  private static final int CHECKSUM_OFFSET = 8;
  
  private static final int CHECKSUM_SIZE = 4;
  
  static final short[] EMPTY_SHORT_ARRAY = new short[0];
  
  private static final int SIGNATURE_OFFSET = 12;
  
  private static final int SIGNATURE_SIZE = 20;
  
  private ByteBuffer data;
  
  private final FieldIdTable fieldIds;
  
  private final MethodIdTable methodIds;
  
  private int nextSectionStart;
  
  private final ProtoIdTable protoIds;
  
  private final StringTable strings;
  
  private final TableOfContents tableOfContents;
  
  private final TypeIndexToDescriptorIndexTable typeIds;
  
  private final TypeIndexToDescriptorTable typeNames;
  
  public Dex(int paramInt) throws IOException {
    this.tableOfContents = new TableOfContents();
    this.nextSectionStart = 0;
    this.strings = new StringTable();
    this.typeIds = new TypeIndexToDescriptorIndexTable();
    this.typeNames = new TypeIndexToDescriptorTable();
    this.protoIds = new ProtoIdTable();
    this.fieldIds = new FieldIdTable();
    this.methodIds = new MethodIdTable();
    ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[paramInt]);
    this.data = byteBuffer;
    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
  }
  
  public Dex(File paramFile) throws IOException {
    InputStream inputStream;
    this.tableOfContents = new TableOfContents();
    this.nextSectionStart = 0;
    this.strings = new StringTable();
    this.typeIds = new TypeIndexToDescriptorIndexTable();
    this.typeNames = new TypeIndexToDescriptorTable();
    this.protoIds = new ProtoIdTable();
    this.fieldIds = new FieldIdTable();
    this.methodIds = new MethodIdTable();
    if (FileUtils.hasArchiveSuffix(paramFile.getName())) {
      ZipFile zipFile = new ZipFile(paramFile);
      ZipEntry zipEntry = zipFile.getEntry("classes.dex");
      if (zipEntry != null) {
        inputStream = zipFile.getInputStream(zipEntry);
        try {
          loadFrom(inputStream);
          if (inputStream != null)
            inputStream.close(); 
        } finally {
          zipFile = null;
        } 
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected classes.dex in ");
        stringBuilder.append(inputStream);
        throw new DexException(stringBuilder.toString());
      } 
    } else {
      if (inputStream.getName().endsWith(".dex")) {
        FileInputStream fileInputStream = new FileInputStream((File)inputStream);
        try {
          loadFrom(fileInputStream);
          return;
        } finally {
          Exception exception = null;
        } 
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unknown output extension: ");
      stringBuilder.append(inputStream);
      throw new DexException(stringBuilder.toString());
    } 
  }
  
  public Dex(InputStream paramInputStream) throws IOException {
    this.tableOfContents = new TableOfContents();
    this.nextSectionStart = 0;
    this.strings = new StringTable();
    this.typeIds = new TypeIndexToDescriptorIndexTable();
    this.typeNames = new TypeIndexToDescriptorTable();
    this.protoIds = new ProtoIdTable();
    this.fieldIds = new FieldIdTable();
    this.methodIds = new MethodIdTable();
    try {
      loadFrom(paramInputStream);
      return;
    } finally {
      paramInputStream.close();
    } 
  }
  
  private Dex(ByteBuffer paramByteBuffer) throws IOException {
    this.tableOfContents = new TableOfContents();
    this.nextSectionStart = 0;
    this.strings = new StringTable();
    this.typeIds = new TypeIndexToDescriptorIndexTable();
    this.typeNames = new TypeIndexToDescriptorTable();
    this.protoIds = new ProtoIdTable();
    this.fieldIds = new FieldIdTable();
    this.methodIds = new MethodIdTable();
    this.data = paramByteBuffer;
    paramByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    this.tableOfContents.readFrom(this);
  }
  
  public Dex(byte[] paramArrayOfbyte) throws IOException {
    this(ByteBuffer.wrap(paramArrayOfbyte));
  }
  
  private static void checkBounds(int paramInt1, int paramInt2) {
    if (paramInt1 >= 0 && paramInt1 < paramInt2)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("index:");
    stringBuilder.append(paramInt1);
    stringBuilder.append(", length=");
    stringBuilder.append(paramInt2);
    throw new IndexOutOfBoundsException(stringBuilder.toString());
  }
  
  private void loadFrom(InputStream paramInputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[8192];
    while (true) {
      int i = paramInputStream.read(arrayOfByte);
      if (i != -1) {
        byteArrayOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
      this.data = byteBuffer;
      byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
      this.tableOfContents.readFrom(this);
      return;
    } 
  }
  
  public Section appendSection(int paramInt, String paramString) {
    if ((paramInt & 0x3) == 0) {
      paramInt = this.nextSectionStart + paramInt;
      ByteBuffer byteBuffer = this.data.duplicate();
      byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
      byteBuffer.position(this.nextSectionStart);
      byteBuffer.limit(paramInt);
      Section section = new Section(paramString, byteBuffer);
      this.nextSectionStart = paramInt;
      return section;
    } 
    throw new IllegalStateException("Not four byte aligned!");
  }
  
  public Iterable<ClassDef> classDefs() {
    return new ClassDefIterable();
  }
  
  public int computeChecksum() throws IOException {
    Adler32 adler32 = new Adler32();
    byte[] arrayOfByte = new byte[8192];
    ByteBuffer byteBuffer = this.data.duplicate();
    byteBuffer.limit(byteBuffer.capacity());
    byteBuffer.position(12);
    while (byteBuffer.hasRemaining()) {
      int i = Math.min(8192, byteBuffer.remaining());
      byteBuffer.get(arrayOfByte, 0, i);
      adler32.update(arrayOfByte, 0, i);
    } 
    return (int)adler32.getValue();
  }
  
  public byte[] computeSignature() throws IOException {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
      byte[] arrayOfByte = new byte[8192];
      ByteBuffer byteBuffer = this.data.duplicate();
      byteBuffer.limit(byteBuffer.capacity());
      byteBuffer.position(32);
      while (byteBuffer.hasRemaining()) {
        int i = Math.min(8192, byteBuffer.remaining());
        byteBuffer.get(arrayOfByte, 0, i);
        messageDigest.update(arrayOfByte, 0, i);
      } 
      return messageDigest.digest();
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      throw new AssertionError();
    } 
  }
  
  public int descriptorIndexFromTypeIndex(int paramInt) {
    checkBounds(paramInt, this.tableOfContents.typeIds.size);
    int i = this.tableOfContents.typeIds.off;
    return this.data.getInt(i + paramInt * 4);
  }
  
  public List<FieldId> fieldIds() {
    return this.fieldIds;
  }
  
  public byte[] getBytes() {
    ByteBuffer byteBuffer = this.data.duplicate();
    byte[] arrayOfByte = new byte[byteBuffer.capacity()];
    byteBuffer.position(0);
    byteBuffer.get(arrayOfByte);
    return arrayOfByte;
  }
  
  public int getLength() {
    return this.data.capacity();
  }
  
  public int getNextSectionStart() {
    return this.nextSectionStart;
  }
  
  public TableOfContents getTableOfContents() {
    return this.tableOfContents;
  }
  
  public List<MethodId> methodIds() {
    return this.methodIds;
  }
  
  public Section open(int paramInt) {
    if (paramInt >= 0 && paramInt < this.data.capacity()) {
      ByteBuffer byteBuffer = this.data.duplicate();
      byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
      byteBuffer.position(paramInt);
      byteBuffer.limit(this.data.capacity());
      return new Section("section", byteBuffer);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("position=");
    stringBuilder.append(paramInt);
    stringBuilder.append(" length=");
    stringBuilder.append(this.data.capacity());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public List<ProtoId> protoIds() {
    return this.protoIds;
  }
  
  public ClassData readClassData(ClassDef paramClassDef) {
    int i = paramClassDef.getClassDataOffset();
    if (i != 0)
      return open(i).readClassData(); 
    throw new IllegalArgumentException("offset == 0");
  }
  
  public Code readCode(ClassData.Method paramMethod) {
    int i = paramMethod.getCodeOffset();
    if (i != 0)
      return open(i).readCode(); 
    throw new IllegalArgumentException("offset == 0");
  }
  
  public TypeList readTypeList(int paramInt) {
    return (paramInt == 0) ? TypeList.EMPTY : open(paramInt).readTypeList();
  }
  
  public List<String> strings() {
    return this.strings;
  }
  
  public List<Integer> typeIds() {
    return this.typeIds;
  }
  
  public List<String> typeNames() {
    return this.typeNames;
  }
  
  public void writeHashes() throws IOException {
    open(12).write(computeSignature());
    open(8).writeInt(computeChecksum());
  }
  
  public void writeTo(File paramFile) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
    try {
      writeTo(fileOutputStream);
      return;
    } finally {
      paramFile = null;
    } 
  }
  
  public void writeTo(OutputStream paramOutputStream) throws IOException {
    byte[] arrayOfByte = new byte[8192];
    ByteBuffer byteBuffer = this.data.duplicate();
    byteBuffer.clear();
    while (byteBuffer.hasRemaining()) {
      int i = Math.min(8192, byteBuffer.remaining());
      byteBuffer.get(arrayOfByte, 0, i);
      paramOutputStream.write(arrayOfByte, 0, i);
    } 
  }
  
  private final class ClassDefIterable implements Iterable<ClassDef> {
    private ClassDefIterable() {}
    
    public Iterator<ClassDef> iterator() {
      Iterator<ClassDef> iterator;
      if (!Dex.this.tableOfContents.classDefs.exists()) {
        iterator = Collections.emptySet().iterator();
      } else {
        iterator = new Dex.ClassDefIterator();
      } 
      return iterator;
    }
  }
  
  private final class ClassDefIterator implements Iterator<ClassDef> {
    private int count;
    
    private final Dex.Section in;
    
    private ClassDefIterator() {
      Dex.this = Dex.this;
      this.in = Dex.this.open(Dex.this.tableOfContents.classDefs.off);
      this.count = 0;
    }
    
    public boolean hasNext() {
      boolean bool;
      if (this.count < Dex.this.tableOfContents.classDefs.size) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public ClassDef next() {
      if (hasNext()) {
        this.count++;
        return this.in.readClassDef();
      } 
      throw new NoSuchElementException();
    }
    
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
  
  private final class FieldIdTable extends AbstractList<FieldId> implements RandomAccess {
    private FieldIdTable() {}
    
    public FieldId get(int param1Int) {
      Dex.checkBounds(param1Int, Dex.this.tableOfContents.fieldIds.size);
      Dex dex = Dex.this;
      return dex.open(dex.tableOfContents.fieldIds.off + param1Int * 8).readFieldId();
    }
    
    public int size() {
      return Dex.this.tableOfContents.fieldIds.size;
    }
  }
  
  private final class MethodIdTable extends AbstractList<MethodId> implements RandomAccess {
    private MethodIdTable() {}
    
    public MethodId get(int param1Int) {
      Dex.checkBounds(param1Int, Dex.this.tableOfContents.methodIds.size);
      Dex dex = Dex.this;
      return dex.open(dex.tableOfContents.methodIds.off + param1Int * 8).readMethodId();
    }
    
    public int size() {
      return Dex.this.tableOfContents.methodIds.size;
    }
  }
  
  private final class ProtoIdTable extends AbstractList<ProtoId> implements RandomAccess {
    private ProtoIdTable() {}
    
    public ProtoId get(int param1Int) {
      Dex.checkBounds(param1Int, Dex.this.tableOfContents.protoIds.size);
      Dex dex = Dex.this;
      return dex.open(dex.tableOfContents.protoIds.off + param1Int * 12).readProtoId();
    }
    
    public int size() {
      return Dex.this.tableOfContents.protoIds.size;
    }
  }
  
  public final class Section implements ByteInput, ByteOutput {
    private final ByteBuffer data;
    
    private final int initialPosition;
    
    private final String name;
    
    private Section(String param1String, ByteBuffer param1ByteBuffer) {
      this.name = param1String;
      this.data = param1ByteBuffer;
      this.initialPosition = param1ByteBuffer.position();
    }
    
    private int findCatchHandlerIndex(Code.CatchHandler[] param1ArrayOfCatchHandler, int param1Int) {
      for (byte b = 0; b < param1ArrayOfCatchHandler.length; b++) {
        if (param1ArrayOfCatchHandler[b].getOffset() == param1Int)
          return b; 
      } 
      throw new IllegalArgumentException();
    }
    
    private byte[] getBytesFrom(int param1Int) {
      byte[] arrayOfByte = new byte[this.data.position() - param1Int];
      this.data.position(param1Int);
      this.data.get(arrayOfByte);
      return arrayOfByte;
    }
    
    private Code.CatchHandler readCatchHandler(int param1Int) {
      int i = readSleb128();
      int j = Math.abs(i);
      int[] arrayOfInt1 = new int[j];
      int[] arrayOfInt2 = new int[j];
      int k;
      for (k = 0; k < j; k++) {
        arrayOfInt1[k] = readUleb128();
        arrayOfInt2[k] = readUleb128();
      } 
      if (i <= 0) {
        k = readUleb128();
      } else {
        k = -1;
      } 
      return new Code.CatchHandler(arrayOfInt1, arrayOfInt2, k, param1Int);
    }
    
    private Code.CatchHandler[] readCatchHandlers() {
      int i = this.data.position();
      int j = readUleb128();
      Code.CatchHandler[] arrayOfCatchHandler = new Code.CatchHandler[j];
      for (byte b = 0; b < j; b++)
        arrayOfCatchHandler[b] = readCatchHandler(this.data.position() - i); 
      return arrayOfCatchHandler;
    }
    
    private ClassData readClassData() {
      int i = readUleb128();
      int j = readUleb128();
      int k = readUleb128();
      int m = readUleb128();
      return new ClassData(readFields(i), readFields(j), readMethods(k), readMethods(m));
    }
    
    private Code readCode() {
      Code.Try[] arrayOfTry;
      Code.CatchHandler[] arrayOfCatchHandler;
      int i = readUnsignedShort();
      int j = readUnsignedShort();
      int k = readUnsignedShort();
      int m = readUnsignedShort();
      int n = readInt();
      short[] arrayOfShort = readShortArray(readInt());
      if (m > 0) {
        if (arrayOfShort.length % 2 == 1)
          readShort(); 
        Section section = Dex.this.open(this.data.position());
        skip(m * 8);
        arrayOfCatchHandler = readCatchHandlers();
        arrayOfTry = section.readTries(m, arrayOfCatchHandler);
      } else {
        arrayOfTry = new Code.Try[0];
        arrayOfCatchHandler = new Code.CatchHandler[0];
      } 
      return new Code(i, j, k, n, arrayOfShort, arrayOfTry, arrayOfCatchHandler);
    }
    
    private ClassData.Field[] readFields(int param1Int) {
      ClassData.Field[] arrayOfField = new ClassData.Field[param1Int];
      byte b = 0;
      int i = 0;
      while (b < param1Int) {
        i += readUleb128();
        arrayOfField[b] = new ClassData.Field(i, readUleb128());
        b++;
      } 
      return arrayOfField;
    }
    
    private ClassData.Method[] readMethods(int param1Int) {
      ClassData.Method[] arrayOfMethod = new ClassData.Method[param1Int];
      byte b = 0;
      int i = 0;
      while (b < param1Int) {
        i += readUleb128();
        arrayOfMethod[b] = new ClassData.Method(i, readUleb128(), readUleb128());
        b++;
      } 
      return arrayOfMethod;
    }
    
    private Code.Try[] readTries(int param1Int, Code.CatchHandler[] param1ArrayOfCatchHandler) {
      Code.Try[] arrayOfTry = new Code.Try[param1Int];
      for (byte b = 0; b < param1Int; b++)
        arrayOfTry[b] = new Code.Try(readInt(), readUnsignedShort(), findCatchHandlerIndex(param1ArrayOfCatchHandler, readUnsignedShort())); 
      return arrayOfTry;
    }
    
    public void alignToFourBytes() {
      ByteBuffer byteBuffer = this.data;
      byteBuffer.position(byteBuffer.position() + 3 & 0xFFFFFFFC);
    }
    
    public void alignToFourBytesWithZeroFill() {
      while ((this.data.position() & 0x3) != 0)
        this.data.put((byte)0); 
    }
    
    public void assertFourByteAligned() {
      if ((this.data.position() & 0x3) == 0)
        return; 
      throw new IllegalStateException("Not four byte aligned!");
    }
    
    public int getPosition() {
      return this.data.position();
    }
    
    public Annotation readAnnotation() {
      byte b = readByte();
      int i = this.data.position();
      (new EncodedValueReader(this, 29)).skipValue();
      return new Annotation(Dex.this, b, new EncodedValue(getBytesFrom(i)));
    }
    
    public byte readByte() {
      return this.data.get();
    }
    
    public byte[] readByteArray(int param1Int) {
      byte[] arrayOfByte = new byte[param1Int];
      this.data.get(arrayOfByte);
      return arrayOfByte;
    }
    
    public CallSiteId readCallSiteId() {
      int i = readInt();
      return new CallSiteId(Dex.this, i);
    }
    
    public ClassDef readClassDef() {
      int i = getPosition();
      int j = readInt();
      int k = readInt();
      int m = readInt();
      int n = readInt();
      int i1 = readInt();
      int i2 = readInt();
      int i3 = readInt();
      int i4 = readInt();
      return new ClassDef(Dex.this, i, j, k, m, n, i1, i2, i3, i4);
    }
    
    public EncodedValue readEncodedArray() {
      int i = this.data.position();
      (new EncodedValueReader(this, 28)).skipValue();
      return new EncodedValue(getBytesFrom(i));
    }
    
    public FieldId readFieldId() {
      int i = readUnsignedShort();
      int j = readUnsignedShort();
      int k = readInt();
      return new FieldId(Dex.this, i, j, k);
    }
    
    public int readInt() {
      return this.data.getInt();
    }
    
    public MethodHandle readMethodHandle() {
      MethodHandle.MethodHandleType methodHandleType = MethodHandle.MethodHandleType.fromValue(readUnsignedShort());
      int i = readUnsignedShort();
      int j = readUnsignedShort();
      int k = readUnsignedShort();
      return new MethodHandle(Dex.this, methodHandleType, i, j, k);
    }
    
    public MethodId readMethodId() {
      int i = readUnsignedShort();
      int j = readUnsignedShort();
      int k = readInt();
      return new MethodId(Dex.this, i, j, k);
    }
    
    public ProtoId readProtoId() {
      int i = readInt();
      int j = readInt();
      int k = readInt();
      return new ProtoId(Dex.this, i, j, k);
    }
    
    public short readShort() {
      return this.data.getShort();
    }
    
    public short[] readShortArray(int param1Int) {
      if (param1Int == 0)
        return Dex.EMPTY_SHORT_ARRAY; 
      short[] arrayOfShort = new short[param1Int];
      for (byte b = 0; b < param1Int; b++)
        arrayOfShort[b] = readShort(); 
      return arrayOfShort;
    }
    
    public int readSleb128() {
      return Leb128.readSignedLeb128(this);
    }
    
    public String readString() {
      int i = readInt();
      int j = this.data.position();
      int k = this.data.limit();
      this.data.position(i);
      ByteBuffer byteBuffer = this.data;
      byteBuffer.limit(byteBuffer.capacity());
      try {
        i = readUleb128();
        String str = Mutf8.decode(this, new char[i]);
        int m = str.length();
        if (m == i) {
          this.data.position(j);
          this.data.limit(k);
          return str;
        } 
        DexException dexException = new DexException();
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("Declared length ");
        stringBuilder.append(i);
        stringBuilder.append(" doesn't match decoded length of ");
        stringBuilder.append(str.length());
        this(stringBuilder.toString());
        throw dexException;
      } catch (UTFDataFormatException uTFDataFormatException) {
        DexException dexException = new DexException();
        this(uTFDataFormatException);
        throw dexException;
      } finally {}
      this.data.position(j);
      this.data.limit(k);
      throw byteBuffer;
    }
    
    public TypeList readTypeList() {
      short[] arrayOfShort = readShortArray(readInt());
      alignToFourBytes();
      return new TypeList(Dex.this, arrayOfShort);
    }
    
    public int readUleb128() {
      return Leb128.readUnsignedLeb128(this);
    }
    
    public int readUleb128p1() {
      return Leb128.readUnsignedLeb128(this) - 1;
    }
    
    public int readUnsignedShort() {
      return readShort() & 0xFFFF;
    }
    
    public void skip(int param1Int) {
      if (param1Int >= 0) {
        ByteBuffer byteBuffer = this.data;
        byteBuffer.position(byteBuffer.position() + param1Int);
        return;
      } 
      throw new IllegalArgumentException();
    }
    
    public int used() {
      return this.data.position() - this.initialPosition;
    }
    
    public void write(byte[] param1ArrayOfbyte) {
      this.data.put(param1ArrayOfbyte);
    }
    
    public void write(short[] param1ArrayOfshort) {
      int i = param1ArrayOfshort.length;
      for (byte b = 0; b < i; b++)
        writeShort(param1ArrayOfshort[b]); 
    }
    
    public void writeByte(int param1Int) {
      this.data.put((byte)param1Int);
    }
    
    public void writeInt(int param1Int) {
      this.data.putInt(param1Int);
    }
    
    public void writeShort(short param1Short) {
      this.data.putShort(param1Short);
    }
    
    public void writeSleb128(int param1Int) {
      try {
        Leb128.writeSignedLeb128(this, param1Int);
        return;
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Section limit ");
        stringBuilder.append(this.data.limit());
        stringBuilder.append(" exceeded by ");
        stringBuilder.append(this.name);
        throw new DexException(stringBuilder.toString());
      } 
    }
    
    public void writeStringData(String param1String) {
      try {
        writeUleb128(param1String.length());
        write(Mutf8.encode(param1String));
        writeByte(0);
        return;
      } catch (UTFDataFormatException uTFDataFormatException) {
        throw new AssertionError();
      } 
    }
    
    public void writeTypeList(TypeList param1TypeList) {
      short[] arrayOfShort = param1TypeList.getTypes();
      writeInt(arrayOfShort.length);
      int i = arrayOfShort.length;
      for (byte b = 0; b < i; b++)
        writeShort(arrayOfShort[b]); 
      alignToFourBytesWithZeroFill();
    }
    
    public void writeUleb128(int param1Int) {
      try {
        Leb128.writeUnsignedLeb128(this, param1Int);
        return;
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Section limit ");
        stringBuilder.append(this.data.limit());
        stringBuilder.append(" exceeded by ");
        stringBuilder.append(this.name);
        throw new DexException(stringBuilder.toString());
      } 
    }
    
    public void writeUleb128p1(int param1Int) {
      writeUleb128(param1Int + 1);
    }
    
    public void writeUnsignedShort(int param1Int) {
      short s = (short)param1Int;
      if (param1Int == (0xFFFF & s)) {
        writeShort(s);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Expected an unsigned short: ");
      stringBuilder.append(param1Int);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
  }
  
  private final class StringTable extends AbstractList<String> implements RandomAccess {
    private StringTable() {}
    
    public String get(int param1Int) {
      Dex.checkBounds(param1Int, Dex.this.tableOfContents.stringIds.size);
      Dex dex = Dex.this;
      return dex.open(dex.tableOfContents.stringIds.off + param1Int * 4).readString();
    }
    
    public int size() {
      return Dex.this.tableOfContents.stringIds.size;
    }
  }
  
  private final class TypeIndexToDescriptorIndexTable extends AbstractList<Integer> implements RandomAccess {
    private TypeIndexToDescriptorIndexTable() {}
    
    public Integer get(int param1Int) {
      return Integer.valueOf(Dex.this.descriptorIndexFromTypeIndex(param1Int));
    }
    
    public int size() {
      return Dex.this.tableOfContents.typeIds.size;
    }
  }
  
  private final class TypeIndexToDescriptorTable extends AbstractList<String> implements RandomAccess {
    private TypeIndexToDescriptorTable() {}
    
    public String get(int param1Int) {
      return Dex.this.strings.get(Dex.this.descriptorIndexFromTypeIndex(param1Int));
    }
    
    public int size() {
      return Dex.this.tableOfContents.typeIds.size;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\Dex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */