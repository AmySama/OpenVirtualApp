package com.android.dx.dex.file;

import com.android.dex.EncodedValueCodec;
import com.android.dex.util.ByteOutput;
import com.android.dx.rop.annotation.Annotation;
import com.android.dx.rop.annotation.NameValuePair;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstAnnotation;
import com.android.dx.rop.cst.CstArray;
import com.android.dx.rop.cst.CstBaseMethodRef;
import com.android.dx.rop.cst.CstBoolean;
import com.android.dx.rop.cst.CstDouble;
import com.android.dx.rop.cst.CstEnumRef;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstFloat;
import com.android.dx.rop.cst.CstLiteralBits;
import com.android.dx.rop.cst.CstMethodHandle;
import com.android.dx.rop.cst.CstProtoRef;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.Collection;
import java.util.Iterator;

public final class ValueEncoder {
  private static final int VALUE_ANNOTATION = 29;
  
  private static final int VALUE_ARRAY = 28;
  
  private static final int VALUE_BOOLEAN = 31;
  
  private static final int VALUE_BYTE = 0;
  
  private static final int VALUE_CHAR = 3;
  
  private static final int VALUE_DOUBLE = 17;
  
  private static final int VALUE_ENUM = 27;
  
  private static final int VALUE_FIELD = 25;
  
  private static final int VALUE_FLOAT = 16;
  
  private static final int VALUE_INT = 4;
  
  private static final int VALUE_LONG = 6;
  
  private static final int VALUE_METHOD = 26;
  
  private static final int VALUE_METHOD_HANDLE = 22;
  
  private static final int VALUE_METHOD_TYPE = 21;
  
  private static final int VALUE_NULL = 30;
  
  private static final int VALUE_SHORT = 2;
  
  private static final int VALUE_STRING = 23;
  
  private static final int VALUE_TYPE = 24;
  
  private final DexFile file;
  
  private final AnnotatedOutput out;
  
  public ValueEncoder(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    if (paramDexFile != null) {
      if (paramAnnotatedOutput != null) {
        this.file = paramDexFile;
        this.out = paramAnnotatedOutput;
        return;
      } 
      throw new NullPointerException("out == null");
    } 
    throw new NullPointerException("file == null");
  }
  
  public static void addContents(DexFile paramDexFile, Annotation paramAnnotation) {
    TypeIdsSection typeIdsSection = paramDexFile.getTypeIds();
    StringIdsSection stringIdsSection = paramDexFile.getStringIds();
    typeIdsSection.intern(paramAnnotation.getType());
    for (NameValuePair nameValuePair : paramAnnotation.getNameValuePairs()) {
      stringIdsSection.intern(nameValuePair.getName());
      addContents(paramDexFile, nameValuePair.getValue());
    } 
  }
  
  public static void addContents(DexFile paramDexFile, Constant paramConstant) {
    if (paramConstant instanceof CstAnnotation) {
      addContents(paramDexFile, ((CstAnnotation)paramConstant).getAnnotation());
    } else {
      CstArray.List list;
      if (paramConstant instanceof CstArray) {
        list = ((CstArray)paramConstant).getList();
        int i = list.size();
        for (byte b = 0; b < i; b++)
          addContents(paramDexFile, list.get(b)); 
      } else {
        paramDexFile.internIfAppropriate((Constant)list);
      } 
    } 
  }
  
  public static String constantToHuman(Constant paramConstant) {
    if (constantToValueType(paramConstant) == 30)
      return "null"; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramConstant.typeName());
    stringBuilder.append(' ');
    stringBuilder.append(paramConstant.toHuman());
    return stringBuilder.toString();
  }
  
  private static int constantToValueType(Constant paramConstant) {
    if (paramConstant instanceof com.android.dx.rop.cst.CstByte)
      return 0; 
    if (paramConstant instanceof com.android.dx.rop.cst.CstShort)
      return 2; 
    if (paramConstant instanceof com.android.dx.rop.cst.CstChar)
      return 3; 
    if (paramConstant instanceof com.android.dx.rop.cst.CstInteger)
      return 4; 
    if (paramConstant instanceof com.android.dx.rop.cst.CstLong)
      return 6; 
    if (paramConstant instanceof CstFloat)
      return 16; 
    if (paramConstant instanceof CstDouble)
      return 17; 
    if (paramConstant instanceof CstProtoRef)
      return 21; 
    if (paramConstant instanceof CstMethodHandle)
      return 22; 
    if (paramConstant instanceof CstString)
      return 23; 
    if (paramConstant instanceof CstType)
      return 24; 
    if (paramConstant instanceof CstFieldRef)
      return 25; 
    if (paramConstant instanceof com.android.dx.rop.cst.CstMethodRef)
      return 26; 
    if (paramConstant instanceof CstEnumRef)
      return 27; 
    if (paramConstant instanceof CstArray)
      return 28; 
    if (paramConstant instanceof CstAnnotation)
      return 29; 
    if (paramConstant instanceof com.android.dx.rop.cst.CstKnownNull)
      return 30; 
    if (paramConstant instanceof CstBoolean)
      return 31; 
    throw new RuntimeException("Shouldn't happen");
  }
  
  public void writeAnnotation(Annotation paramAnnotation, boolean paramBoolean) {
    boolean bool;
    if (paramBoolean && this.out.annotates()) {
      bool = true;
    } else {
      bool = false;
    } 
    StringIdsSection stringIdsSection = this.file.getStringIds();
    TypeIdsSection typeIdsSection = this.file.getTypeIds();
    CstType cstType = paramAnnotation.getType();
    int i = typeIdsSection.indexOf(cstType);
    if (bool) {
      AnnotatedOutput annotatedOutput = this.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  type_idx: ");
      stringBuilder.append(Hex.u4(i));
      stringBuilder.append(" // ");
      stringBuilder.append(cstType.toHuman());
      annotatedOutput.annotate(stringBuilder.toString());
    } 
    this.out.writeUleb128(typeIdsSection.indexOf(paramAnnotation.getType()));
    Collection collection = paramAnnotation.getNameValuePairs();
    i = collection.size();
    if (bool) {
      AnnotatedOutput annotatedOutput = this.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  size: ");
      stringBuilder.append(Hex.u4(i));
      annotatedOutput.annotate(stringBuilder.toString());
    } 
    this.out.writeUleb128(i);
    Iterator<NameValuePair> iterator = collection.iterator();
    int j;
    for (j = 0; iterator.hasNext(); j = i) {
      NameValuePair nameValuePair = iterator.next();
      CstString cstString = nameValuePair.getName();
      int k = stringIdsSection.indexOf(cstString);
      Constant constant = nameValuePair.getValue();
      i = j;
      if (bool) {
        AnnotatedOutput annotatedOutput2 = this.out;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("  elements[");
        stringBuilder1.append(j);
        stringBuilder1.append("]:");
        annotatedOutput2.annotate(0, stringBuilder1.toString());
        i = j + 1;
        AnnotatedOutput annotatedOutput1 = this.out;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("    name_idx: ");
        stringBuilder2.append(Hex.u4(k));
        stringBuilder2.append(" // ");
        stringBuilder2.append(cstString.toHuman());
        annotatedOutput1.annotate(stringBuilder2.toString());
      } 
      this.out.writeUleb128(k);
      if (bool) {
        AnnotatedOutput annotatedOutput = this.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("    value: ");
        stringBuilder.append(constantToHuman(constant));
        annotatedOutput.annotate(stringBuilder.toString());
      } 
      writeConstant(constant);
    } 
    if (bool)
      this.out.endAnnotation(); 
  }
  
  public void writeArray(CstArray paramCstArray, boolean paramBoolean) {
    boolean bool;
    byte b = 0;
    if (paramBoolean && this.out.annotates()) {
      bool = true;
    } else {
      bool = false;
    } 
    CstArray.List list = paramCstArray.getList();
    int i = list.size();
    if (bool) {
      AnnotatedOutput annotatedOutput = this.out;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  size: ");
      stringBuilder.append(Hex.u4(i));
      annotatedOutput.annotate(stringBuilder.toString());
    } 
    this.out.writeUleb128(i);
    while (b < i) {
      Constant constant = list.get(b);
      if (bool) {
        AnnotatedOutput annotatedOutput = this.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  [");
        stringBuilder.append(Integer.toHexString(b));
        stringBuilder.append("] ");
        stringBuilder.append(constantToHuman(constant));
        annotatedOutput.annotate(stringBuilder.toString());
      } 
      writeConstant(constant);
      b++;
    } 
    if (bool)
      this.out.endAnnotation(); 
  }
  
  public void writeConstant(Constant paramConstant) {
    CstFieldRef cstFieldRef;
    int i = constantToValueType(paramConstant);
    if (i != 0 && i != 6 && i != 2)
      if (i != 3) {
        if (i != 4) {
          if (i != 16) {
            if (i != 17) {
              switch (i) {
                default:
                  throw new RuntimeException("Shouldn't happen");
                case 31:
                  j = ((CstBoolean)paramConstant).getIntBits();
                  this.out.writeByte(j << 5 | i);
                  return;
                case 30:
                  this.out.writeByte(i);
                  return;
                case 29:
                  this.out.writeByte(i);
                  writeAnnotation(((CstAnnotation)paramConstant).getAnnotation(), false);
                  return;
                case 28:
                  this.out.writeByte(i);
                  writeArray((CstArray)paramConstant, false);
                  return;
                case 27:
                  cstFieldRef = ((CstEnumRef)paramConstant).getFieldRef();
                  j = this.file.getFieldIds().indexOf(cstFieldRef);
                  EncodedValueCodec.writeUnsignedIntegralValue((ByteOutput)this.out, i, j);
                  return;
                case 26:
                  j = this.file.getMethodIds().indexOf((CstBaseMethodRef)cstFieldRef);
                  EncodedValueCodec.writeUnsignedIntegralValue((ByteOutput)this.out, i, j);
                  return;
                case 25:
                  j = this.file.getFieldIds().indexOf(cstFieldRef);
                  EncodedValueCodec.writeUnsignedIntegralValue((ByteOutput)this.out, i, j);
                  return;
                case 24:
                  j = this.file.getTypeIds().indexOf((CstType)cstFieldRef);
                  EncodedValueCodec.writeUnsignedIntegralValue((ByteOutput)this.out, i, j);
                  return;
                case 23:
                  j = this.file.getStringIds().indexOf((CstString)cstFieldRef);
                  EncodedValueCodec.writeUnsignedIntegralValue((ByteOutput)this.out, i, j);
                  return;
                case 22:
                  j = this.file.getMethodHandles().indexOf((CstMethodHandle)cstFieldRef);
                  EncodedValueCodec.writeUnsignedIntegralValue((ByteOutput)this.out, i, j);
                  return;
                case 21:
                  break;
              } 
              int j = this.file.getProtoIds().indexOf(((CstProtoRef)cstFieldRef).getPrototype());
              EncodedValueCodec.writeUnsignedIntegralValue((ByteOutput)this.out, i, j);
            } else {
              long l1 = ((CstDouble)cstFieldRef).getLongBits();
              EncodedValueCodec.writeRightZeroExtendedValue((ByteOutput)this.out, i, l1);
            } 
          } else {
            long l1 = ((CstFloat)cstFieldRef).getLongBits();
            EncodedValueCodec.writeRightZeroExtendedValue((ByteOutput)this.out, i, l1 << 32L);
          } 
          return;
        } 
      } else {
        long l1 = ((CstLiteralBits)cstFieldRef).getLongBits();
        EncodedValueCodec.writeUnsignedIntegralValue((ByteOutput)this.out, i, l1);
        return;
      }  
    long l = ((CstLiteralBits)cstFieldRef).getLongBits();
    EncodedValueCodec.writeSignedIntegralValue((ByteOutput)this.out, i, l);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\ValueEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */