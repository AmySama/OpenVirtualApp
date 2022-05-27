package com.android.dx.cf.direct;

import com.android.dx.cf.iface.ParseException;
import com.android.dx.cf.iface.ParseObserver;
import com.android.dx.rop.annotation.Annotation;
import com.android.dx.rop.annotation.AnnotationVisibility;
import com.android.dx.rop.annotation.Annotations;
import com.android.dx.rop.annotation.AnnotationsList;
import com.android.dx.rop.annotation.NameValuePair;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.ConstantPool;
import com.android.dx.rop.cst.CstAnnotation;
import com.android.dx.rop.cst.CstArray;
import com.android.dx.rop.cst.CstBoolean;
import com.android.dx.rop.cst.CstByte;
import com.android.dx.rop.cst.CstChar;
import com.android.dx.rop.cst.CstEnumRef;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstShort;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;
import java.io.IOException;

public final class AnnotationParser {
  private final ByteArray bytes;
  
  private final DirectClassFile cf;
  
  private final ByteArray.MyDataInputStream input;
  
  private final ParseObserver observer;
  
  private int parseCursor;
  
  private final ConstantPool pool;
  
  public AnnotationParser(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramDirectClassFile != null) {
      this.cf = paramDirectClassFile;
      this.pool = paramDirectClassFile.getConstantPool();
      this.observer = paramParseObserver;
      ByteArray byteArray = paramDirectClassFile.getBytes().slice(paramInt1, paramInt2 + paramInt1);
      this.bytes = byteArray;
      this.input = byteArray.makeDataInputStream();
      this.parseCursor = 0;
      return;
    } 
    throw new NullPointerException("cf == null");
  }
  
  private void changeIndent(int paramInt) {
    this.observer.changeIndent(paramInt);
  }
  
  private Annotation parseAnnotation(AnnotationVisibility paramAnnotationVisibility) throws IOException {
    requireLength(4);
    int i = this.input.readUnsignedShort();
    int j = this.input.readUnsignedShort();
    CstType cstType = new CstType(Type.intern(((CstString)this.pool.get(i)).getString()));
    if (this.observer != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("type: ");
      stringBuilder.append(cstType.toHuman());
      parsed(2, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("num_elements: ");
      stringBuilder.append(j);
      parsed(2, stringBuilder.toString());
    } 
    Annotation annotation = new Annotation(cstType, paramAnnotationVisibility);
    for (i = 0; i < j; i++) {
      if (this.observer != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("elements[");
        stringBuilder.append(i);
        stringBuilder.append("]:");
        parsed(0, stringBuilder.toString());
        changeIndent(1);
      } 
      annotation.add(parseElement());
      if (this.observer != null)
        changeIndent(-1); 
    } 
    annotation.setImmutable();
    return annotation;
  }
  
  private Annotations parseAnnotations(AnnotationVisibility paramAnnotationVisibility) throws IOException {
    int i = this.input.readUnsignedShort();
    if (this.observer != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("num_annotations: ");
      stringBuilder.append(Hex.u2(i));
      parsed(2, stringBuilder.toString());
    } 
    Annotations annotations = new Annotations();
    for (byte b = 0; b < i; b++) {
      if (this.observer != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("annotations[");
        stringBuilder.append(b);
        stringBuilder.append("]:");
        parsed(0, stringBuilder.toString());
        changeIndent(1);
      } 
      annotations.add(parseAnnotation(paramAnnotationVisibility));
      ParseObserver parseObserver = this.observer;
      if (parseObserver != null)
        parseObserver.changeIndent(-1); 
    } 
    annotations.setImmutable();
    return annotations;
  }
  
  private AnnotationsList parseAnnotationsList(AnnotationVisibility paramAnnotationVisibility) throws IOException {
    int i = this.input.readUnsignedByte();
    if (this.observer != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("num_parameters: ");
      stringBuilder.append(Hex.u1(i));
      parsed(1, stringBuilder.toString());
    } 
    AnnotationsList annotationsList = new AnnotationsList(i);
    for (byte b = 0; b < i; b++) {
      if (this.observer != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("parameter_annotations[");
        stringBuilder.append(b);
        stringBuilder.append("]:");
        parsed(0, stringBuilder.toString());
        changeIndent(1);
      } 
      annotationsList.set(b, parseAnnotations(paramAnnotationVisibility));
      ParseObserver parseObserver = this.observer;
      if (parseObserver != null)
        parseObserver.changeIndent(-1); 
    } 
    annotationsList.setImmutable();
    return annotationsList;
  }
  
  private Constant parseConstant() throws IOException {
    int i = this.input.readUnsignedShort();
    Constant constant = this.pool.get(i);
    if (this.observer != null) {
      String str;
      if (constant instanceof CstString) {
        str = ((CstString)constant).toQuoted();
      } else {
        str = constant.toHuman();
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("constant_value: ");
      stringBuilder.append(str);
      parsed(2, stringBuilder.toString());
    } 
    return constant;
  }
  
  private NameValuePair parseElement() throws IOException {
    requireLength(5);
    int i = this.input.readUnsignedShort();
    CstString cstString = (CstString)this.pool.get(i);
    if (this.observer != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("element_name: ");
      stringBuilder.append(cstString.toHuman());
      parsed(2, stringBuilder.toString());
      parsed(0, "value: ");
      changeIndent(1);
    } 
    Constant constant = parseValue();
    if (this.observer != null)
      changeIndent(-1); 
    return new NameValuePair(cstString, constant);
  }
  
  private Constant parseValue() throws IOException {
    int i = this.input.readUnsignedByte();
    if (this.observer != null) {
      CstString cstString = new CstString(Character.toString((char)i));
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("tag: ");
      stringBuilder.append(cstString.toQuoted());
      parsed(1, stringBuilder.toString());
    } 
    if (i != 64) {
      if (i != 70) {
        if (i != 83) {
          if (i != 99) {
            if (i != 101) {
              if (i != 115) {
                if (i != 73) {
                  if (i != 74) {
                    if (i != 90) {
                      if (i != 91) {
                        StringBuilder stringBuilder;
                        CstInteger cstInteger;
                        switch (i) {
                          default:
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("unknown annotation tag: ");
                            stringBuilder.append(Hex.u1(i));
                            throw new ParseException(stringBuilder.toString());
                          case 68:
                            return parseConstant();
                          case 67:
                            cstInteger = (CstInteger)parseConstant();
                            cstInteger.getValue();
                            return (Constant)CstChar.make(cstInteger.getValue());
                          case 66:
                            break;
                        } 
                        return (Constant)CstByte.make(((CstInteger)parseConstant()).getValue());
                      } 
                      requireLength(2);
                      int k = this.input.readUnsignedShort();
                      CstArray.List list = new CstArray.List(k);
                      if (this.observer != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("num_values: ");
                        stringBuilder.append(k);
                        parsed(2, stringBuilder.toString());
                        changeIndent(1);
                      } 
                      for (i = 0; i < k; i++) {
                        if (this.observer != null) {
                          changeIndent(-1);
                          StringBuilder stringBuilder = new StringBuilder();
                          stringBuilder.append("element_value[");
                          stringBuilder.append(i);
                          stringBuilder.append("]:");
                          parsed(0, stringBuilder.toString());
                          changeIndent(1);
                        } 
                        list.set(i, parseValue());
                      } 
                      if (this.observer != null)
                        changeIndent(-1); 
                      list.setImmutable();
                      return (Constant)new CstArray(list);
                    } 
                    return (Constant)CstBoolean.make(((CstInteger)parseConstant()).getValue());
                  } 
                  return parseConstant();
                } 
                return parseConstant();
              } 
              return parseConstant();
            } 
            requireLength(4);
            i = this.input.readUnsignedShort();
            int j = this.input.readUnsignedShort();
            CstString cstString2 = (CstString)this.pool.get(i);
            CstString cstString1 = (CstString)this.pool.get(j);
            if (this.observer != null) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("type_name: ");
              stringBuilder.append(cstString2.toHuman());
              parsed(2, stringBuilder.toString());
              stringBuilder = new StringBuilder();
              stringBuilder.append("const_name: ");
              stringBuilder.append(cstString1.toHuman());
              parsed(2, stringBuilder.toString());
            } 
            return (Constant)new CstEnumRef(new CstNat(cstString1, cstString2));
          } 
          i = this.input.readUnsignedShort();
          Type type = Type.internReturnType(((CstString)this.pool.get(i)).getString());
          if (this.observer != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("class_info: ");
            stringBuilder.append(type.toHuman());
            parsed(2, stringBuilder.toString());
          } 
          return (Constant)new CstType(type);
        } 
        return (Constant)CstShort.make(((CstInteger)parseConstant()).getValue());
      } 
      return parseConstant();
    } 
    return (Constant)new CstAnnotation(parseAnnotation(AnnotationVisibility.EMBEDDED));
  }
  
  private void parsed(int paramInt, String paramString) {
    this.observer.parsed(this.bytes, this.parseCursor, paramInt, paramString);
    this.parseCursor += paramInt;
  }
  
  private void requireLength(int paramInt) throws IOException {
    if (this.input.available() >= paramInt)
      return; 
    throw new ParseException("truncated annotation attribute");
  }
  
  public Annotations parseAnnotationAttribute(AnnotationVisibility paramAnnotationVisibility) {
    try {
      Annotations annotations = parseAnnotations(paramAnnotationVisibility);
      if (this.input.available() == 0)
        return annotations; 
      ParseException parseException = new ParseException();
      this("extra data in attribute");
      throw parseException;
    } catch (IOException iOException) {
      throw new RuntimeException("shouldn't happen", iOException);
    } 
  }
  
  public AnnotationsList parseParameterAttribute(AnnotationVisibility paramAnnotationVisibility) {
    try {
      AnnotationsList annotationsList = parseAnnotationsList(paramAnnotationVisibility);
      if (this.input.available() == 0)
        return annotationsList; 
      ParseException parseException = new ParseException();
      this("extra data in attribute");
      throw parseException;
    } catch (IOException iOException) {
      throw new RuntimeException("shouldn't happen", iOException);
    } 
  }
  
  public Constant parseValueAttribute() {
    try {
      Constant constant = parseValue();
      if (this.input.available() == 0)
        return constant; 
      ParseException parseException = new ParseException();
      this("extra data in attribute");
      throw parseException;
    } catch (IOException iOException) {
      throw new RuntimeException("shouldn't happen", iOException);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\direct\AnnotationParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */