package com.android.dx.cf.direct;

import com.android.dx.cf.iface.AttributeList;
import com.android.dx.cf.iface.Member;
import com.android.dx.cf.iface.ParseException;
import com.android.dx.cf.iface.ParseObserver;
import com.android.dx.cf.iface.StdAttributeList;
import com.android.dx.rop.cst.ConstantPool;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;

abstract class MemberListParser {
  private final AttributeFactory attributeFactory;
  
  private final DirectClassFile cf;
  
  private final CstType definer;
  
  private int endOffset;
  
  private ParseObserver observer;
  
  private final int offset;
  
  public MemberListParser(DirectClassFile paramDirectClassFile, CstType paramCstType, int paramInt, AttributeFactory paramAttributeFactory) {
    if (paramDirectClassFile != null) {
      if (paramInt >= 0) {
        if (paramAttributeFactory != null) {
          this.cf = paramDirectClassFile;
          this.definer = paramCstType;
          this.offset = paramInt;
          this.attributeFactory = paramAttributeFactory;
          this.endOffset = -1;
          return;
        } 
        throw new NullPointerException("attributeFactory == null");
      } 
      throw new IllegalArgumentException("offset < 0");
    } 
    throw new NullPointerException("cf == null");
  }
  
  private void parse() {
    int i = getAttributeContext();
    int j = getCount();
    int k = this.offset + 2;
    ByteArray byteArray = this.cf.getBytes();
    ConstantPool constantPool = this.cf.getConstantPool();
    ParseObserver parseObserver = this.observer;
    if (parseObserver != null) {
      int m = this.offset;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(humanName());
      stringBuilder.append("s_count: ");
      stringBuilder.append(Hex.u2(j));
      parseObserver.parsed(byteArray, m, 2, stringBuilder.toString());
    } 
    byte b = 0;
    while (b < j) {
      try {
        int m = byteArray.getUnsignedShort(k);
        int n = k + 2;
        int i1 = byteArray.getUnsignedShort(n);
        int i2 = k + 4;
        int i3 = byteArray.getUnsignedShort(i2);
        CstString cstString1 = (CstString)constantPool.get(i1);
        CstString cstString2 = (CstString)constantPool.get(i3);
        if (this.observer != null) {
          this.observer.startParsingMember(byteArray, k, cstString1.getString(), cstString2.getString());
          ParseObserver parseObserver2 = this.observer;
          StringBuilder stringBuilder3 = new StringBuilder();
          this();
          stringBuilder3.append("\n");
          stringBuilder3.append(humanName());
          stringBuilder3.append("s[");
          stringBuilder3.append(b);
          stringBuilder3.append("]:\n");
          parseObserver2.parsed(byteArray, k, 0, stringBuilder3.toString());
          this.observer.changeIndent(1);
          parseObserver2 = this.observer;
          stringBuilder3 = new StringBuilder();
          this();
          stringBuilder3.append("access_flags: ");
          stringBuilder3.append(humanAccessFlags(m));
          parseObserver2.parsed(byteArray, k, 2, stringBuilder3.toString());
          ParseObserver parseObserver3 = this.observer;
          StringBuilder stringBuilder1 = new StringBuilder();
          this();
          stringBuilder1.append("name: ");
          stringBuilder1.append(cstString1.toHuman());
          parseObserver3.parsed(byteArray, n, 2, stringBuilder1.toString());
          ParseObserver parseObserver1 = this.observer;
          StringBuilder stringBuilder2 = new StringBuilder();
          this();
          stringBuilder2.append("descriptor: ");
          stringBuilder2.append(cstString2.toHuman());
          parseObserver1.parsed(byteArray, i2, 2, stringBuilder2.toString());
        } 
        AttributeListParser attributeListParser = new AttributeListParser();
        this(this.cf, i, k + 6, this.attributeFactory);
        attributeListParser.setObserver(this.observer);
        k = attributeListParser.getEndOffset();
        StdAttributeList stdAttributeList = attributeListParser.getList();
        stdAttributeList.setImmutable();
        CstNat cstNat = new CstNat();
        this(cstString1, cstString2);
        Member member = set(b, m, cstNat, (AttributeList)stdAttributeList);
        if (this.observer != null) {
          this.observer.changeIndent(-1);
          ParseObserver parseObserver1 = this.observer;
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("end ");
          stringBuilder.append(humanName());
          stringBuilder.append("s[");
          stringBuilder.append(b);
          stringBuilder.append("]\n");
          parseObserver1.parsed(byteArray, k, 0, stringBuilder.toString());
          parseObserver1 = this.observer;
          String str1 = cstString1.getString();
          String str2 = cstString2.getString();
          try {
            parseObserver1.endParsingMember(byteArray, k, str1, str2, member);
          } catch (ParseException parseException) {
          
          } catch (RuntimeException runtimeException) {}
        } 
        b++;
      } catch (ParseException parseException) {
        continue;
      } catch (RuntimeException runtimeException) {
        ParseException parseException = new ParseException(runtimeException);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("...while parsing ");
        stringBuilder.append(humanName());
        stringBuilder.append("s[");
        stringBuilder.append(b);
        stringBuilder.append("]");
        parseException.addContext(stringBuilder.toString());
        throw parseException;
      } 
    } 
    this.endOffset = k;
  }
  
  protected abstract int getAttributeContext();
  
  protected final int getCount() {
    return this.cf.getBytes().getUnsignedShort(this.offset);
  }
  
  protected final CstType getDefiner() {
    return this.definer;
  }
  
  public int getEndOffset() {
    parseIfNecessary();
    return this.endOffset;
  }
  
  protected abstract String humanAccessFlags(int paramInt);
  
  protected abstract String humanName();
  
  protected final void parseIfNecessary() {
    if (this.endOffset < 0)
      parse(); 
  }
  
  protected abstract Member set(int paramInt1, int paramInt2, CstNat paramCstNat, AttributeList paramAttributeList);
  
  public final void setObserver(ParseObserver paramParseObserver) {
    this.observer = paramParseObserver;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\direct\MemberListParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */