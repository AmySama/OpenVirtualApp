package com.android.dx.cf.direct;

import com.android.dx.cf.attrib.AttBootstrapMethods;
import com.android.dx.cf.attrib.AttSourceFile;
import com.android.dx.cf.code.BootstrapMethodsList;
import com.android.dx.cf.cst.ConstantPoolParser;
import com.android.dx.cf.iface.Attribute;
import com.android.dx.cf.iface.AttributeList;
import com.android.dx.cf.iface.ClassFile;
import com.android.dx.cf.iface.FieldList;
import com.android.dx.cf.iface.MethodList;
import com.android.dx.cf.iface.ParseException;
import com.android.dx.cf.iface.ParseObserver;
import com.android.dx.cf.iface.StdAttributeList;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.cst.ConstantPool;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.cst.StdConstantPool;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;

public class DirectClassFile implements ClassFile {
  private static final int CLASS_FILE_MAGIC = -889275714;
  
  private static final int CLASS_FILE_MAX_MAJOR_VERSION = 53;
  
  private static final int CLASS_FILE_MAX_MINOR_VERSION = 0;
  
  private static final int CLASS_FILE_MIN_MAJOR_VERSION = 45;
  
  private int accessFlags;
  
  private AttributeFactory attributeFactory;
  
  private StdAttributeList attributes;
  
  private final ByteArray bytes;
  
  private FieldList fields;
  
  private final String filePath;
  
  private TypeList interfaces;
  
  private MethodList methods;
  
  private ParseObserver observer;
  
  private StdConstantPool pool;
  
  private final boolean strictParse;
  
  private CstType superClass;
  
  private CstType thisClass;
  
  public DirectClassFile(ByteArray paramByteArray, String paramString, boolean paramBoolean) {
    if (paramByteArray != null) {
      if (paramString != null) {
        this.filePath = paramString;
        this.bytes = paramByteArray;
        this.strictParse = paramBoolean;
        this.accessFlags = -1;
        return;
      } 
      throw new NullPointerException("filePath == null");
    } 
    throw new NullPointerException("bytes == null");
  }
  
  public DirectClassFile(byte[] paramArrayOfbyte, String paramString, boolean paramBoolean) {
    this(new ByteArray(paramArrayOfbyte), paramString, paramBoolean);
  }
  
  private boolean isGoodMagic(int paramInt) {
    boolean bool;
    if (paramInt == -889275714) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean isGoodVersion(int paramInt1, int paramInt2) {
    if (paramInt1 >= 0)
      if (paramInt2 == 53) {
        if (paramInt1 <= 0)
          return true; 
      } else if (paramInt2 < 53 && paramInt2 >= 45) {
        return true;
      }  
    return false;
  }
  
  private void parse() {
    try {
      parse0();
      return;
    } catch (ParseException parseException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("...while parsing ");
      stringBuilder.append(this.filePath);
      parseException.addContext(stringBuilder.toString());
      throw parseException;
    } catch (RuntimeException runtimeException) {
      ParseException parseException = new ParseException(runtimeException);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("...while parsing ");
      stringBuilder.append(this.filePath);
      parseException.addContext(stringBuilder.toString());
      throw parseException;
    } 
  }
  
  private void parse0() {
    if (this.bytes.size() >= 10) {
      ParseObserver parseObserver1 = this.observer;
      if (parseObserver1 != null) {
        parseObserver1.parsed(this.bytes, 0, 0, "begin classfile");
        ParseObserver parseObserver4 = this.observer;
        ByteArray byteArray3 = this.bytes;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("magic: ");
        stringBuilder1.append(Hex.u4(getMagic0()));
        parseObserver4.parsed(byteArray3, 0, 4, stringBuilder1.toString());
        ParseObserver parseObserver5 = this.observer;
        ByteArray byteArray1 = this.bytes;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("minor_version: ");
        stringBuilder2.append(Hex.u2(getMinorVersion0()));
        parseObserver5.parsed(byteArray1, 4, 2, stringBuilder2.toString());
        ParseObserver parseObserver3 = this.observer;
        ByteArray byteArray2 = this.bytes;
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("major_version: ");
        stringBuilder3.append(Hex.u2(getMajorVersion0()));
        parseObserver3.parsed(byteArray2, 6, 2, stringBuilder3.toString());
      } 
      if (this.strictParse)
        if (isGoodMagic(getMagic0())) {
          if (!isGoodVersion(getMinorVersion0(), getMajorVersion0())) {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("unsupported class file version ");
            stringBuilder1.append(getMajorVersion0());
            stringBuilder1.append(".");
            stringBuilder1.append(getMinorVersion0());
            throw new ParseException(stringBuilder1.toString());
          } 
        } else {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("bad class file magic (");
          stringBuilder1.append(Hex.u4(getMagic0()));
          stringBuilder1.append(")");
          throw new ParseException(stringBuilder1.toString());
        }  
      ConstantPoolParser constantPoolParser = new ConstantPoolParser(this.bytes);
      constantPoolParser.setObserver(this.observer);
      StdConstantPool stdConstantPool = constantPoolParser.getPool();
      this.pool = stdConstantPool;
      stdConstantPool.setImmutable();
      int i = constantPoolParser.getEndOffset();
      int j = this.bytes.getUnsignedShort(i);
      ByteArray byteArray = this.bytes;
      int k = i + 2;
      int m = byteArray.getUnsignedShort(k);
      this.thisClass = (CstType)this.pool.get(m);
      byteArray = this.bytes;
      int n = i + 4;
      m = byteArray.getUnsignedShort(n);
      this.superClass = (CstType)this.pool.get0Ok(m);
      byteArray = this.bytes;
      int i1 = i + 6;
      m = byteArray.getUnsignedShort(i1);
      ParseObserver parseObserver2 = this.observer;
      if (parseObserver2 != null) {
        ByteArray byteArray3 = this.bytes;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("access_flags: ");
        stringBuilder1.append(AccessFlags.classString(j));
        parseObserver2.parsed(byteArray3, i, 2, stringBuilder1.toString());
        ParseObserver parseObserver = this.observer;
        byteArray3 = this.bytes;
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("this_class: ");
        stringBuilder3.append(this.thisClass);
        parseObserver.parsed(byteArray3, k, 2, stringBuilder3.toString());
        parseObserver = this.observer;
        ByteArray byteArray1 = this.bytes;
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("super_class: ");
        stringBuilder4.append(stringOrNone(this.superClass));
        parseObserver.parsed(byteArray1, n, 2, stringBuilder4.toString());
        parseObserver = this.observer;
        ByteArray byteArray2 = this.bytes;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("interfaces_count: ");
        stringBuilder2.append(Hex.u2(m));
        parseObserver.parsed(byteArray2, i1, 2, stringBuilder2.toString());
        if (m != 0)
          this.observer.parsed(this.bytes, i + 8, 0, "interfaces:"); 
      } 
      k = i + 8;
      this.interfaces = makeTypeList(k, m);
      if (this.strictParse) {
        String str = this.thisClass.getClassType().getClassName();
        if (!this.filePath.endsWith(".class") || !this.filePath.startsWith(str) || this.filePath.length() != str.length() + 6) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("class name (");
          stringBuilder1.append(str);
          stringBuilder1.append(") does not match path (");
          stringBuilder1.append(this.filePath);
          stringBuilder1.append(")");
          throw new ParseException(stringBuilder1.toString());
        } 
      } 
      this.accessFlags = j;
      FieldListParser fieldListParser = new FieldListParser(this, this.thisClass, k + m * 2, this.attributeFactory);
      fieldListParser.setObserver(this.observer);
      this.fields = (FieldList)fieldListParser.getList();
      j = fieldListParser.getEndOffset();
      MethodListParser methodListParser = new MethodListParser(this, this.thisClass, j, this.attributeFactory);
      methodListParser.setObserver(this.observer);
      this.methods = (MethodList)methodListParser.getList();
      AttributeListParser attributeListParser = new AttributeListParser(this, 0, methodListParser.getEndOffset(), this.attributeFactory);
      attributeListParser.setObserver(this.observer);
      StdAttributeList stdAttributeList = attributeListParser.getList();
      this.attributes = stdAttributeList;
      stdAttributeList.setImmutable();
      j = attributeListParser.getEndOffset();
      if (j == this.bytes.size()) {
        ParseObserver parseObserver = this.observer;
        if (parseObserver != null)
          parseObserver.parsed(this.bytes, j, 0, "end classfile"); 
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("extra bytes at end of class file, at offset ");
      stringBuilder.append(Hex.u4(j));
      throw new ParseException(stringBuilder.toString());
    } 
    throw new ParseException("severely truncated class file");
  }
  
  private void parseToEndIfNecessary() {
    if (this.attributes == null)
      parse(); 
  }
  
  private void parseToInterfacesIfNecessary() {
    if (this.accessFlags == -1)
      parse(); 
  }
  
  public static String stringOrNone(Object paramObject) {
    return (paramObject == null) ? "(none)" : paramObject.toString();
  }
  
  public int getAccessFlags() {
    parseToInterfacesIfNecessary();
    return this.accessFlags;
  }
  
  public AttributeList getAttributes() {
    parseToEndIfNecessary();
    return (AttributeList)this.attributes;
  }
  
  public BootstrapMethodsList getBootstrapMethods() {
    AttBootstrapMethods attBootstrapMethods = (AttBootstrapMethods)getAttributes().findFirst("BootstrapMethods");
    return (attBootstrapMethods != null) ? attBootstrapMethods.getBootstrapMethods() : BootstrapMethodsList.EMPTY;
  }
  
  public ByteArray getBytes() {
    return this.bytes;
  }
  
  public ConstantPool getConstantPool() {
    parseToInterfacesIfNecessary();
    return (ConstantPool)this.pool;
  }
  
  public FieldList getFields() {
    parseToEndIfNecessary();
    return this.fields;
  }
  
  public String getFilePath() {
    return this.filePath;
  }
  
  public TypeList getInterfaces() {
    parseToInterfacesIfNecessary();
    return this.interfaces;
  }
  
  public int getMagic() {
    parseToInterfacesIfNecessary();
    return getMagic0();
  }
  
  public int getMagic0() {
    return this.bytes.getInt(0);
  }
  
  public int getMajorVersion() {
    parseToInterfacesIfNecessary();
    return getMajorVersion0();
  }
  
  public int getMajorVersion0() {
    return this.bytes.getUnsignedShort(6);
  }
  
  public MethodList getMethods() {
    parseToEndIfNecessary();
    return this.methods;
  }
  
  public int getMinorVersion() {
    parseToInterfacesIfNecessary();
    return getMinorVersion0();
  }
  
  public int getMinorVersion0() {
    return this.bytes.getUnsignedShort(4);
  }
  
  public CstString getSourceFile() {
    Attribute attribute = getAttributes().findFirst("SourceFile");
    return (attribute instanceof AttSourceFile) ? ((AttSourceFile)attribute).getSourceFile() : null;
  }
  
  public CstType getSuperclass() {
    parseToInterfacesIfNecessary();
    return this.superClass;
  }
  
  public CstType getThisClass() {
    parseToInterfacesIfNecessary();
    return this.thisClass;
  }
  
  public TypeList makeTypeList(int paramInt1, int paramInt2) {
    if (paramInt2 == 0)
      return (TypeList)StdTypeList.EMPTY; 
    StdConstantPool stdConstantPool = this.pool;
    if (stdConstantPool != null)
      return new DcfTypeList(this.bytes, paramInt1, paramInt2, stdConstantPool, this.observer); 
    throw new IllegalStateException("pool not yet initialized");
  }
  
  public void setAttributeFactory(AttributeFactory paramAttributeFactory) {
    if (paramAttributeFactory != null) {
      this.attributeFactory = paramAttributeFactory;
      return;
    } 
    throw new NullPointerException("attributeFactory == null");
  }
  
  public void setObserver(ParseObserver paramParseObserver) {
    this.observer = paramParseObserver;
  }
  
  private static class DcfTypeList implements TypeList {
    private final ByteArray bytes;
    
    private final StdConstantPool pool;
    
    private final int size;
    
    public DcfTypeList(ByteArray param1ByteArray, int param1Int1, int param1Int2, StdConstantPool param1StdConstantPool, ParseObserver param1ParseObserver) {
      if (param1Int2 >= 0) {
        ByteArray byteArray = param1ByteArray.slice(param1Int1, param1Int2 * 2 + param1Int1);
        this.bytes = byteArray;
        this.size = param1Int2;
        this.pool = param1StdConstantPool;
        param1Int1 = 0;
        while (param1Int1 < param1Int2) {
          int i = param1Int1 * 2;
          int j = byteArray.getUnsignedShort(i);
          try {
            CstType cstType = (CstType)param1StdConstantPool.get(j);
            if (param1ParseObserver != null) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("  ");
              stringBuilder.append(cstType);
              param1ParseObserver.parsed(byteArray, i, 2, stringBuilder.toString());
            } 
            param1Int1++;
          } catch (ClassCastException classCastException) {
            throw new RuntimeException("bogus class cpi", classCastException);
          } 
        } 
        return;
      } 
      throw new IllegalArgumentException("size < 0");
    }
    
    public Type getType(int param1Int) {
      param1Int = this.bytes.getUnsignedShort(param1Int * 2);
      return ((CstType)this.pool.get(param1Int)).getClassType();
    }
    
    public int getWordCount() {
      return this.size;
    }
    
    public boolean isMutable() {
      return false;
    }
    
    public int size() {
      return this.size;
    }
    
    public TypeList withAddedType(Type param1Type) {
      throw new UnsupportedOperationException("unsupported");
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\direct\DirectClassFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */