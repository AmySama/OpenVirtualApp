package com.android.dx.cf.direct;

import com.android.dx.cf.attrib.AttAnnotationDefault;
import com.android.dx.cf.attrib.AttBootstrapMethods;
import com.android.dx.cf.attrib.AttCode;
import com.android.dx.cf.attrib.AttConstantValue;
import com.android.dx.cf.attrib.AttDeprecated;
import com.android.dx.cf.attrib.AttEnclosingMethod;
import com.android.dx.cf.attrib.AttExceptions;
import com.android.dx.cf.attrib.AttInnerClasses;
import com.android.dx.cf.attrib.AttLineNumberTable;
import com.android.dx.cf.attrib.AttLocalVariableTable;
import com.android.dx.cf.attrib.AttLocalVariableTypeTable;
import com.android.dx.cf.attrib.AttRuntimeInvisibleAnnotations;
import com.android.dx.cf.attrib.AttRuntimeInvisibleParameterAnnotations;
import com.android.dx.cf.attrib.AttRuntimeVisibleAnnotations;
import com.android.dx.cf.attrib.AttRuntimeVisibleParameterAnnotations;
import com.android.dx.cf.attrib.AttSignature;
import com.android.dx.cf.attrib.AttSourceDebugExtension;
import com.android.dx.cf.attrib.AttSourceFile;
import com.android.dx.cf.attrib.AttSynthetic;
import com.android.dx.cf.attrib.InnerClassList;
import com.android.dx.cf.code.BootstrapMethodArgumentsList;
import com.android.dx.cf.code.BootstrapMethodsList;
import com.android.dx.cf.code.ByteCatchList;
import com.android.dx.cf.code.BytecodeArray;
import com.android.dx.cf.code.LineNumberList;
import com.android.dx.cf.code.LocalVariableList;
import com.android.dx.cf.iface.Attribute;
import com.android.dx.cf.iface.AttributeList;
import com.android.dx.cf.iface.ParseException;
import com.android.dx.cf.iface.ParseObserver;
import com.android.dx.cf.iface.StdAttributeList;
import com.android.dx.rop.annotation.AnnotationVisibility;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.cst.ConstantPool;
import com.android.dx.rop.cst.CstMethodHandle;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.cst.TypedConstant;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;
import java.io.IOException;

public class StdAttributeFactory extends AttributeFactory {
  public static final StdAttributeFactory THE_ONE = new StdAttributeFactory();
  
  private Attribute annotationDefault(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 < 2)
      throwSeverelyTruncated(); 
    return (Attribute)new AttAnnotationDefault((new AnnotationParser(paramDirectClassFile, paramInt1, paramInt2, paramParseObserver)).parseValueAttribute(), paramInt2);
  }
  
  private Attribute bootstrapMethods(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 < 2)
      return throwSeverelyTruncated(); 
    ByteArray byteArray = paramDirectClassFile.getBytes();
    int i = byteArray.getUnsignedShort(paramInt1);
    if (paramParseObserver != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("num_boostrap_methods: ");
      stringBuilder.append(Hex.u2(i));
      paramParseObserver.parsed(byteArray, paramInt1, 2, stringBuilder.toString());
    } 
    return (Attribute)new AttBootstrapMethods(parseBootstrapMethods(byteArray, paramDirectClassFile.getConstantPool(), paramDirectClassFile.getThisClass(), i, paramInt1 + 2, paramInt2 - 2, paramParseObserver));
  }
  
  private Attribute code(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    ByteCatchList byteCatchList;
    if (paramInt2 < 12)
      return throwSeverelyTruncated(); 
    ByteArray byteArray = paramDirectClassFile.getBytes();
    ConstantPool constantPool = paramDirectClassFile.getConstantPool();
    int i = byteArray.getUnsignedShort(paramInt1);
    int j = paramInt1 + 2;
    int k = byteArray.getUnsignedShort(j);
    int m = paramInt1 + 4;
    int n = byteArray.getInt(m);
    if (paramParseObserver != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("max_stack: ");
      stringBuilder.append(Hex.u2(i));
      paramParseObserver.parsed(byteArray, paramInt1, 2, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("max_locals: ");
      stringBuilder.append(Hex.u2(k));
      paramParseObserver.parsed(byteArray, j, 2, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("code_length: ");
      stringBuilder.append(Hex.u4(n));
      paramParseObserver.parsed(byteArray, m, 4, stringBuilder.toString());
    } 
    j = paramInt1 + 8;
    paramInt2 -= 8;
    if (paramInt2 < n + 4)
      return throwTruncated(); 
    int i1 = j + n;
    BytecodeArray bytecodeArray = new BytecodeArray(byteArray.slice(j, i1), constantPool);
    if (paramParseObserver != null)
      bytecodeArray.forEach(new CodeObserver(bytecodeArray.getBytes(), paramParseObserver)); 
    m = byteArray.getUnsignedShort(i1);
    if (m == 0) {
      byteCatchList = ByteCatchList.EMPTY;
    } else {
      byteCatchList = new ByteCatchList(m);
    } 
    if (paramParseObserver != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("exception_table_length: ");
      stringBuilder.append(Hex.u2(m));
      paramParseObserver.parsed(byteArray, i1, 2, stringBuilder.toString());
    } 
    j = i1 + 2;
    n = paramInt2 - n - 2;
    if (n < m * 8 + 2)
      return throwTruncated(); 
    for (paramInt2 = 0; paramInt2 < m; paramInt2++) {
      if (paramParseObserver != null)
        paramParseObserver.changeIndent(1); 
      int i2 = byteArray.getUnsignedShort(j);
      i1 = byteArray.getUnsignedShort(j + 2);
      int i3 = byteArray.getUnsignedShort(j + 4);
      CstType cstType = (CstType)constantPool.get0Ok(byteArray.getUnsignedShort(j + 6));
      byteCatchList.set(paramInt2, i2, i1, i3, cstType);
      if (paramParseObserver != null) {
        String str;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Hex.u2(i2));
        stringBuilder.append("..");
        stringBuilder.append(Hex.u2(i1));
        stringBuilder.append(" -> ");
        stringBuilder.append(Hex.u2(i3));
        stringBuilder.append(" ");
        if (cstType == null) {
          str = "<any>";
        } else {
          str = str.toHuman();
        } 
        stringBuilder.append(str);
        paramParseObserver.parsed(byteArray, j, 8, stringBuilder.toString());
      } 
      j += 8;
      n -= 8;
      if (paramParseObserver != null)
        paramParseObserver.changeIndent(-1); 
    } 
    byteCatchList.setImmutable();
    AttributeListParser attributeListParser = new AttributeListParser(paramDirectClassFile, 3, j, this);
    attributeListParser.setObserver(paramParseObserver);
    StdAttributeList stdAttributeList = attributeListParser.getList();
    stdAttributeList.setImmutable();
    paramInt2 = attributeListParser.getEndOffset() - j;
    return (Attribute)((paramInt2 != n) ? throwBadLength(paramInt2 + j - paramInt1) : new AttCode(i, k, bytecodeArray, byteCatchList, (AttributeList)stdAttributeList));
  }
  
  private Attribute constantValue(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 != 2)
      return throwBadLength(2); 
    ByteArray byteArray = paramDirectClassFile.getBytes();
    TypedConstant typedConstant = (TypedConstant)paramDirectClassFile.getConstantPool().get(byteArray.getUnsignedShort(paramInt1));
    AttConstantValue attConstantValue = new AttConstantValue(typedConstant);
    if (paramParseObserver != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("value: ");
      stringBuilder.append(typedConstant);
      paramParseObserver.parsed(byteArray, paramInt1, 2, stringBuilder.toString());
    } 
    return (Attribute)attConstantValue;
  }
  
  private Attribute deprecated(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    return (Attribute)((paramInt2 != 0) ? throwBadLength(0) : new AttDeprecated());
  }
  
  private Attribute enclosingMethod(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 != 4)
      throwBadLength(4); 
    ByteArray byteArray = paramDirectClassFile.getBytes();
    ConstantPool constantPool = paramDirectClassFile.getConstantPool();
    CstType cstType = (CstType)constantPool.get(byteArray.getUnsignedShort(paramInt1));
    paramInt2 = paramInt1 + 2;
    CstNat cstNat = (CstNat)constantPool.get0Ok(byteArray.getUnsignedShort(paramInt2));
    AttEnclosingMethod attEnclosingMethod = new AttEnclosingMethod(cstType, cstNat);
    if (paramParseObserver != null) {
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("class: ");
      stringBuilder2.append(cstType);
      paramParseObserver.parsed(byteArray, paramInt1, 2, stringBuilder2.toString());
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("method: ");
      stringBuilder1.append(DirectClassFile.stringOrNone(cstNat));
      paramParseObserver.parsed(byteArray, paramInt2, 2, stringBuilder1.toString());
    } 
    return (Attribute)attEnclosingMethod;
  }
  
  private Attribute exceptions(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 < 2)
      return throwSeverelyTruncated(); 
    ByteArray byteArray = paramDirectClassFile.getBytes();
    int i = byteArray.getUnsignedShort(paramInt1);
    if (paramParseObserver != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("number_of_exceptions: ");
      stringBuilder.append(Hex.u2(i));
      paramParseObserver.parsed(byteArray, paramInt1, 2, stringBuilder.toString());
    } 
    int j = i * 2;
    if (paramInt2 - 2 != j)
      throwBadLength(j + 2); 
    return (Attribute)new AttExceptions(paramDirectClassFile.makeTypeList(paramInt1 + 2, i));
  }
  
  private Attribute innerClasses(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 < 2)
      return throwSeverelyTruncated(); 
    ByteArray byteArray = paramDirectClassFile.getBytes();
    ConstantPool constantPool = paramDirectClassFile.getConstantPool();
    int i = byteArray.getUnsignedShort(paramInt1);
    if (paramParseObserver != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("number_of_classes: ");
      stringBuilder.append(Hex.u2(i));
      paramParseObserver.parsed(byteArray, paramInt1, 2, stringBuilder.toString());
    } 
    int j = paramInt1 + 2;
    paramInt1 = i * 8;
    if (paramInt2 - 2 != paramInt1)
      throwBadLength(paramInt1 + 2); 
    InnerClassList innerClassList = new InnerClassList(i);
    paramInt1 = 0;
    paramInt2 = j;
    while (paramInt1 < i) {
      j = byteArray.getUnsignedShort(paramInt2);
      int k = paramInt2 + 2;
      int m = byteArray.getUnsignedShort(k);
      int n = paramInt2 + 4;
      int i1 = byteArray.getUnsignedShort(n);
      int i2 = paramInt2 + 6;
      int i3 = byteArray.getUnsignedShort(i2);
      CstType cstType1 = (CstType)constantPool.get(j);
      CstType cstType2 = (CstType)constantPool.get0Ok(m);
      CstString cstString = (CstString)constantPool.get0Ok(i1);
      innerClassList.set(paramInt1, cstType1, cstType2, cstString, i3);
      if (paramParseObserver != null) {
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("inner_class: ");
        stringBuilder4.append(DirectClassFile.stringOrNone(cstType1));
        paramParseObserver.parsed(byteArray, paramInt2, 2, stringBuilder4.toString());
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("  outer_class: ");
        stringBuilder1.append(DirectClassFile.stringOrNone(cstType2));
        paramParseObserver.parsed(byteArray, k, 2, stringBuilder1.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("  name: ");
        stringBuilder2.append(DirectClassFile.stringOrNone(cstString));
        paramParseObserver.parsed(byteArray, n, 2, stringBuilder2.toString());
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("  access_flags: ");
        stringBuilder3.append(AccessFlags.innerClassString(i3));
        paramParseObserver.parsed(byteArray, i2, 2, stringBuilder3.toString());
      } 
      paramInt2 += 8;
      paramInt1++;
    } 
    innerClassList.setImmutable();
    return (Attribute)new AttInnerClasses(innerClassList);
  }
  
  private Attribute lineNumberTable(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 < 2)
      return throwSeverelyTruncated(); 
    ByteArray byteArray = paramDirectClassFile.getBytes();
    int i = byteArray.getUnsignedShort(paramInt1);
    if (paramParseObserver != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("line_number_table_length: ");
      stringBuilder.append(Hex.u2(i));
      paramParseObserver.parsed(byteArray, paramInt1, 2, stringBuilder.toString());
    } 
    paramInt1 += 2;
    int j = i * 4;
    if (paramInt2 - 2 != j)
      throwBadLength(j + 2); 
    LineNumberList lineNumberList = new LineNumberList(i);
    j = 0;
    paramInt2 = paramInt1;
    for (paramInt1 = j; paramInt1 < i; paramInt1++) {
      int k = byteArray.getUnsignedShort(paramInt2);
      j = byteArray.getUnsignedShort(paramInt2 + 2);
      lineNumberList.set(paramInt1, k, j);
      if (paramParseObserver != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Hex.u2(k));
        stringBuilder.append(" ");
        stringBuilder.append(j);
        paramParseObserver.parsed(byteArray, paramInt2, 4, stringBuilder.toString());
      } 
      paramInt2 += 4;
    } 
    lineNumberList.setImmutable();
    return (Attribute)new AttLineNumberTable(lineNumberList);
  }
  
  private Attribute localVariableTable(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 < 2)
      return throwSeverelyTruncated(); 
    ByteArray byteArray = paramDirectClassFile.getBytes();
    int i = byteArray.getUnsignedShort(paramInt1);
    if (paramParseObserver != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("local_variable_table_length: ");
      stringBuilder.append(Hex.u2(i));
      paramParseObserver.parsed(byteArray, paramInt1, 2, stringBuilder.toString());
    } 
    return (Attribute)new AttLocalVariableTable(parseLocalVariables(byteArray.slice(paramInt1 + 2, paramInt1 + paramInt2), paramDirectClassFile.getConstantPool(), paramParseObserver, i, false));
  }
  
  private Attribute localVariableTypeTable(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 < 2)
      return throwSeverelyTruncated(); 
    ByteArray byteArray = paramDirectClassFile.getBytes();
    int i = byteArray.getUnsignedShort(paramInt1);
    if (paramParseObserver != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("local_variable_type_table_length: ");
      stringBuilder.append(Hex.u2(i));
      paramParseObserver.parsed(byteArray, paramInt1, 2, stringBuilder.toString());
    } 
    return (Attribute)new AttLocalVariableTypeTable(parseLocalVariables(byteArray.slice(paramInt1 + 2, paramInt1 + paramInt2), paramDirectClassFile.getConstantPool(), paramParseObserver, i, true));
  }
  
  private BootstrapMethodsList parseBootstrapMethods(ByteArray paramByteArray, ConstantPool paramConstantPool, CstType paramCstType, int paramInt1, int paramInt2, int paramInt3, ParseObserver paramParseObserver) throws ParseException {
    BootstrapMethodsList bootstrapMethodsList = new BootstrapMethodsList(paramInt1);
    int i = paramInt2;
    paramInt2 = paramInt3;
    byte b = 0;
    paramInt3 = i;
    while (b < paramInt1) {
      if (paramInt2 < 4)
        throwTruncated(); 
      int j = paramByteArray.getUnsignedShort(paramInt3);
      i = paramInt3 + 2;
      int k = paramByteArray.getUnsignedShort(i);
      if (paramParseObserver != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bootstrap_method_ref: ");
        stringBuilder.append(Hex.u2(j));
        paramParseObserver.parsed(paramByteArray, paramInt3, 2, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("num_bootstrap_arguments: ");
        stringBuilder.append(Hex.u2(k));
        paramParseObserver.parsed(paramByteArray, i, 2, stringBuilder.toString());
      } 
      paramInt3 += 4;
      paramInt2 -= 4;
      if (paramInt2 < k * 2)
        throwTruncated(); 
      BootstrapMethodArgumentsList bootstrapMethodArgumentsList = new BootstrapMethodArgumentsList(k);
      i = 0;
      while (i < k) {
        int m = paramByteArray.getUnsignedShort(paramInt3);
        if (paramParseObserver != null) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("bootstrap_arguments[");
          stringBuilder.append(i);
          stringBuilder.append("]");
          stringBuilder.append(Hex.u2(m));
          paramParseObserver.parsed(paramByteArray, paramInt3, 2, stringBuilder.toString());
        } 
        bootstrapMethodArgumentsList.set(i, paramConstantPool.get(m));
        i++;
        paramInt3 += 2;
        paramInt2 -= 2;
      } 
      bootstrapMethodArgumentsList.setImmutable();
      bootstrapMethodsList.set(b, paramCstType, (CstMethodHandle)paramConstantPool.get(j), bootstrapMethodArgumentsList);
      b++;
    } 
    bootstrapMethodsList.setImmutable();
    if (paramInt2 != 0)
      throwBadLength(paramInt2); 
    return bootstrapMethodsList;
  }
  
  private LocalVariableList parseLocalVariables(ByteArray paramByteArray, ConstantPool paramConstantPool, ParseObserver paramParseObserver, int paramInt, boolean paramBoolean) {
    int i = paramByteArray.size();
    int j = paramInt * 10;
    if (i != j)
      throwBadLength(j + 2); 
    ByteArray.MyDataInputStream myDataInputStream = paramByteArray.makeDataInputStream();
    LocalVariableList localVariableList = new LocalVariableList(paramInt);
    i = 0;
    while (i < paramInt) {
      try {
        CstString cstString3;
        CstString cstString4;
        j = myDataInputStream.readUnsignedShort();
        int k = myDataInputStream.readUnsignedShort();
        int m = myDataInputStream.readUnsignedShort();
        int n = myDataInputStream.readUnsignedShort();
        int i1 = myDataInputStream.readUnsignedShort();
        CstString cstString1 = (CstString)paramConstantPool.get(m);
        CstString cstString2 = (CstString)paramConstantPool.get(n);
        if (paramBoolean) {
          cstString3 = null;
          CstString cstString = cstString2;
          cstString4 = cstString;
        } else {
          cstString4 = null;
          CstString cstString = cstString2;
          cstString3 = cstString;
        } 
        localVariableList.set(i, j, k, cstString1, cstString3, cstString4, i1);
        if (paramParseObserver != null) {
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append(Hex.u2(j));
          stringBuilder.append("..");
          stringBuilder.append(Hex.u2(j + k));
          stringBuilder.append(" ");
          stringBuilder.append(Hex.u2(i1));
          stringBuilder.append(" ");
          stringBuilder.append(cstString1.toHuman());
          stringBuilder.append(" ");
          stringBuilder.append(cstString2.toHuman());
          paramParseObserver.parsed(paramByteArray, i * 10, 10, stringBuilder.toString());
        } 
        i++;
      } catch (IOException iOException) {
        throw new RuntimeException("shouldn't happen", iOException);
      } 
    } 
    localVariableList.setImmutable();
    return localVariableList;
  }
  
  private Attribute runtimeInvisibleAnnotations(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 < 2)
      throwSeverelyTruncated(); 
    return (Attribute)new AttRuntimeInvisibleAnnotations((new AnnotationParser(paramDirectClassFile, paramInt1, paramInt2, paramParseObserver)).parseAnnotationAttribute(AnnotationVisibility.BUILD), paramInt2);
  }
  
  private Attribute runtimeInvisibleParameterAnnotations(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 < 2)
      throwSeverelyTruncated(); 
    return (Attribute)new AttRuntimeInvisibleParameterAnnotations((new AnnotationParser(paramDirectClassFile, paramInt1, paramInt2, paramParseObserver)).parseParameterAttribute(AnnotationVisibility.BUILD), paramInt2);
  }
  
  private Attribute runtimeVisibleAnnotations(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 < 2)
      throwSeverelyTruncated(); 
    return (Attribute)new AttRuntimeVisibleAnnotations((new AnnotationParser(paramDirectClassFile, paramInt1, paramInt2, paramParseObserver)).parseAnnotationAttribute(AnnotationVisibility.RUNTIME), paramInt2);
  }
  
  private Attribute runtimeVisibleParameterAnnotations(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 < 2)
      throwSeverelyTruncated(); 
    return (Attribute)new AttRuntimeVisibleParameterAnnotations((new AnnotationParser(paramDirectClassFile, paramInt1, paramInt2, paramParseObserver)).parseParameterAttribute(AnnotationVisibility.RUNTIME), paramInt2);
  }
  
  private Attribute signature(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 != 2)
      throwBadLength(2); 
    ByteArray byteArray = paramDirectClassFile.getBytes();
    CstString cstString = (CstString)paramDirectClassFile.getConstantPool().get(byteArray.getUnsignedShort(paramInt1));
    AttSignature attSignature = new AttSignature(cstString);
    if (paramParseObserver != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("signature: ");
      stringBuilder.append(cstString);
      paramParseObserver.parsed(byteArray, paramInt1, 2, stringBuilder.toString());
    } 
    return (Attribute)attSignature;
  }
  
  private Attribute sourceDebugExtension(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    ByteArray byteArray = paramDirectClassFile.getBytes().slice(paramInt1, paramInt1 + paramInt2);
    CstString cstString = new CstString(byteArray);
    AttSourceDebugExtension attSourceDebugExtension = new AttSourceDebugExtension(cstString);
    if (paramParseObserver != null) {
      String str = cstString.getString();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("sourceDebugExtension: ");
      stringBuilder.append(str);
      paramParseObserver.parsed(byteArray, paramInt1, paramInt2, stringBuilder.toString());
    } 
    return (Attribute)attSourceDebugExtension;
  }
  
  private Attribute sourceFile(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramInt2 != 2)
      throwBadLength(2); 
    ByteArray byteArray = paramDirectClassFile.getBytes();
    CstString cstString = (CstString)paramDirectClassFile.getConstantPool().get(byteArray.getUnsignedShort(paramInt1));
    AttSourceFile attSourceFile = new AttSourceFile(cstString);
    if (paramParseObserver != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("source: ");
      stringBuilder.append(cstString);
      paramParseObserver.parsed(byteArray, paramInt1, 2, stringBuilder.toString());
    } 
    return (Attribute)attSourceFile;
  }
  
  private Attribute synthetic(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    return (Attribute)((paramInt2 != 0) ? throwBadLength(0) : new AttSynthetic());
  }
  
  private static Attribute throwBadLength(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bad attribute length; expected length ");
    stringBuilder.append(Hex.u4(paramInt));
    throw new ParseException(stringBuilder.toString());
  }
  
  private static Attribute throwSeverelyTruncated() {
    throw new ParseException("severely truncated attribute");
  }
  
  private static Attribute throwTruncated() {
    throw new ParseException("truncated attribute");
  }
  
  protected Attribute parse0(DirectClassFile paramDirectClassFile, int paramInt1, String paramString, int paramInt2, int paramInt3, ParseObserver paramParseObserver) {
    if (paramInt1 != 0) {
      if (paramInt1 != 1) {
        if (paramInt1 != 2) {
          if (paramInt1 == 3) {
            if (paramString == "LineNumberTable")
              return lineNumberTable(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
            if (paramString == "LocalVariableTable")
              return localVariableTable(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
            if (paramString == "LocalVariableTypeTable")
              return localVariableTypeTable(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
          } 
        } else {
          if (paramString == "AnnotationDefault")
            return annotationDefault(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
          if (paramString == "Code")
            return code(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
          if (paramString == "Deprecated")
            return deprecated(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
          if (paramString == "Exceptions")
            return exceptions(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
          if (paramString == "RuntimeInvisibleAnnotations")
            return runtimeInvisibleAnnotations(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
          if (paramString == "RuntimeVisibleAnnotations")
            return runtimeVisibleAnnotations(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
          if (paramString == "RuntimeInvisibleParameterAnnotations")
            return runtimeInvisibleParameterAnnotations(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
          if (paramString == "RuntimeVisibleParameterAnnotations")
            return runtimeVisibleParameterAnnotations(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
          if (paramString == "Signature")
            return signature(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
          if (paramString == "Synthetic")
            return synthetic(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
        } 
      } else {
        if (paramString == "ConstantValue")
          return constantValue(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
        if (paramString == "Deprecated")
          return deprecated(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
        if (paramString == "RuntimeInvisibleAnnotations")
          return runtimeInvisibleAnnotations(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
        if (paramString == "RuntimeVisibleAnnotations")
          return runtimeVisibleAnnotations(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
        if (paramString == "Signature")
          return signature(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
        if (paramString == "Synthetic")
          return synthetic(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
      } 
    } else {
      if (paramString == "BootstrapMethods")
        return bootstrapMethods(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
      if (paramString == "Deprecated")
        return deprecated(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
      if (paramString == "EnclosingMethod")
        return enclosingMethod(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
      if (paramString == "InnerClasses")
        return innerClasses(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
      if (paramString == "RuntimeInvisibleAnnotations")
        return runtimeInvisibleAnnotations(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
      if (paramString == "RuntimeVisibleAnnotations")
        return runtimeVisibleAnnotations(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
      if (paramString == "Synthetic")
        return synthetic(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
      if (paramString == "Signature")
        return signature(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
      if (paramString == "SourceDebugExtension")
        return sourceDebugExtension(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
      if (paramString == "SourceFile")
        return sourceFile(paramDirectClassFile, paramInt2, paramInt3, paramParseObserver); 
    } 
    return super.parse0(paramDirectClassFile, paramInt1, paramString, paramInt2, paramInt3, paramParseObserver);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\direct\StdAttributeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */