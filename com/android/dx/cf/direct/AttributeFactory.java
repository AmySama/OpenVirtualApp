package com.android.dx.cf.direct;

import com.android.dx.cf.attrib.RawAttribute;
import com.android.dx.cf.iface.Attribute;
import com.android.dx.cf.iface.ParseException;
import com.android.dx.cf.iface.ParseObserver;
import com.android.dx.rop.cst.ConstantPool;
import com.android.dx.rop.cst.CstString;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;

public class AttributeFactory {
  public static final int CTX_CLASS = 0;
  
  public static final int CTX_CODE = 3;
  
  public static final int CTX_COUNT = 4;
  
  public static final int CTX_FIELD = 1;
  
  public static final int CTX_METHOD = 2;
  
  public final Attribute parse(DirectClassFile paramDirectClassFile, int paramInt1, int paramInt2, ParseObserver paramParseObserver) {
    if (paramDirectClassFile != null) {
      if (paramInt1 >= 0 && paramInt1 < 4) {
        StringBuilder stringBuilder1;
        String str;
        StringBuilder stringBuilder2 = null;
        try {
          CstString cstString1;
          ByteArray byteArray = paramDirectClassFile.getBytes();
          ConstantPool constantPool = paramDirectClassFile.getConstantPool();
          int i = byteArray.getUnsignedShort(paramInt2);
          int j = paramInt2 + 2;
          int k = byteArray.getInt(j);
          CstString cstString2 = (CstString)constantPool.get(i);
          if (paramParseObserver != null) {
            try {
              stringBuilder2 = new StringBuilder();
              this();
              stringBuilder2.append("name: ");
              stringBuilder2.append(cstString2.toHuman());
              paramParseObserver.parsed(byteArray, paramInt2, 2, stringBuilder2.toString());
              stringBuilder2 = new StringBuilder();
              this();
              stringBuilder2.append("length: ");
              stringBuilder2.append(Hex.u4(k));
              paramParseObserver.parsed(byteArray, j, 4, stringBuilder2.toString());
              return parse0(paramDirectClassFile, paramInt1, cstString2.getString(), paramInt2 + 6, k, paramParseObserver);
            } catch (ParseException null) {
              cstString1 = cstString2;
            } 
          } else {
            return parse0((DirectClassFile)parseException, paramInt1, cstString2.getString(), paramInt2 + 6, k, (ParseObserver)cstString1);
          } 
        } catch (ParseException parseException) {
          stringBuilder1 = stringBuilder2;
        } 
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("...while parsing ");
        if (stringBuilder1 != null) {
          stringBuilder2 = new StringBuilder();
          stringBuilder2.append(stringBuilder1.toHuman());
          stringBuilder2.append(" ");
          str = stringBuilder2.toString();
        } else {
          str = "";
        } 
        stringBuilder3.append(str);
        stringBuilder3.append("attribute at offset ");
        stringBuilder3.append(Hex.u4(paramInt2));
        parseException.addContext(stringBuilder3.toString());
        throw parseException;
      } 
      throw new IllegalArgumentException("bad context");
    } 
    throw new NullPointerException("cf == null");
  }
  
  protected Attribute parse0(DirectClassFile paramDirectClassFile, int paramInt1, String paramString, int paramInt2, int paramInt3, ParseObserver paramParseObserver) {
    ByteArray byteArray = paramDirectClassFile.getBytes();
    RawAttribute rawAttribute = new RawAttribute(paramString, byteArray, paramInt2, paramInt3, paramDirectClassFile.getConstantPool());
    if (paramParseObserver != null)
      paramParseObserver.parsed(byteArray, paramInt2, paramInt3, "attribute data"); 
    return (Attribute)rawAttribute;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\direct\AttributeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */