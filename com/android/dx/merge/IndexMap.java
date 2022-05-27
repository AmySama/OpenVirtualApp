package com.android.dx.merge;

import com.android.dex.Annotation;
import com.android.dex.CallSiteId;
import com.android.dex.ClassDef;
import com.android.dex.Dex;
import com.android.dex.DexException;
import com.android.dex.EncodedValue;
import com.android.dex.EncodedValueCodec;
import com.android.dex.EncodedValueReader;
import com.android.dex.FieldId;
import com.android.dex.Leb128;
import com.android.dex.MethodHandle;
import com.android.dex.MethodId;
import com.android.dex.ProtoId;
import com.android.dex.TableOfContents;
import com.android.dex.TypeList;
import com.android.dex.util.ByteOutput;
import com.android.dx.util.ByteArrayAnnotatedOutput;
import java.util.HashMap;

public final class IndexMap {
  private final HashMap<Integer, Integer> annotationDirectoryOffsets;
  
  private final HashMap<Integer, Integer> annotationOffsets;
  
  private final HashMap<Integer, Integer> annotationSetOffsets;
  
  private final HashMap<Integer, Integer> annotationSetRefListOffsets;
  
  public final int[] callSiteIds;
  
  private final HashMap<Integer, Integer> encodedArrayValueOffset;
  
  public final short[] fieldIds;
  
  public final HashMap<Integer, Integer> methodHandleIds;
  
  public final short[] methodIds;
  
  public final short[] protoIds;
  
  public final int[] stringIds;
  
  private final Dex target;
  
  public final short[] typeIds;
  
  private final HashMap<Integer, Integer> typeListOffsets;
  
  public IndexMap(Dex paramDex, TableOfContents paramTableOfContents) {
    this.target = paramDex;
    this.stringIds = new int[paramTableOfContents.stringIds.size];
    this.typeIds = new short[paramTableOfContents.typeIds.size];
    this.protoIds = new short[paramTableOfContents.protoIds.size];
    this.fieldIds = new short[paramTableOfContents.fieldIds.size];
    this.methodIds = new short[paramTableOfContents.methodIds.size];
    this.callSiteIds = new int[paramTableOfContents.callSiteIds.size];
    this.methodHandleIds = new HashMap<Integer, Integer>();
    this.typeListOffsets = new HashMap<Integer, Integer>();
    this.annotationOffsets = new HashMap<Integer, Integer>();
    this.annotationSetOffsets = new HashMap<Integer, Integer>();
    this.annotationSetRefListOffsets = new HashMap<Integer, Integer>();
    this.annotationDirectoryOffsets = new HashMap<Integer, Integer>();
    this.encodedArrayValueOffset = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> hashMap = this.typeListOffsets;
    Integer integer = Integer.valueOf(0);
    hashMap.put(integer, integer);
    this.annotationSetOffsets.put(integer, integer);
    this.annotationDirectoryOffsets.put(integer, integer);
    this.encodedArrayValueOffset.put(integer, integer);
  }
  
  public Annotation adjust(Annotation paramAnnotation) {
    ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput(32);
    (new EncodedValueTransformer((ByteOutput)byteArrayAnnotatedOutput)).transformAnnotation(paramAnnotation.getReader());
    return new Annotation(this.target, paramAnnotation.getVisibility(), new EncodedValue(byteArrayAnnotatedOutput.toByteArray()));
  }
  
  public CallSiteId adjust(CallSiteId paramCallSiteId) {
    return new CallSiteId(this.target, adjustEncodedArray(paramCallSiteId.getCallSiteOffset()));
  }
  
  public ClassDef adjust(ClassDef paramClassDef) {
    return new ClassDef(this.target, paramClassDef.getOffset(), adjustType(paramClassDef.getTypeIndex()), paramClassDef.getAccessFlags(), adjustType(paramClassDef.getSupertypeIndex()), adjustTypeListOffset(paramClassDef.getInterfacesOffset()), paramClassDef.getSourceFileIndex(), paramClassDef.getAnnotationsOffset(), paramClassDef.getClassDataOffset(), paramClassDef.getStaticValuesOffset());
  }
  
  public FieldId adjust(FieldId paramFieldId) {
    return new FieldId(this.target, adjustType(paramFieldId.getDeclaringClassIndex()), adjustType(paramFieldId.getTypeIndex()), adjustString(paramFieldId.getNameIndex()));
  }
  
  public MethodHandle adjust(MethodHandle paramMethodHandle) {
    int j;
    Dex dex = this.target;
    MethodHandle.MethodHandleType methodHandleType = paramMethodHandle.getMethodHandleType();
    int i = paramMethodHandle.getUnused1();
    if (paramMethodHandle.getMethodHandleType().isField()) {
      j = adjustField(paramMethodHandle.getFieldOrMethodId());
    } else {
      j = adjustMethod(paramMethodHandle.getFieldOrMethodId());
    } 
    return new MethodHandle(dex, methodHandleType, i, j, paramMethodHandle.getUnused2());
  }
  
  public MethodId adjust(MethodId paramMethodId) {
    return new MethodId(this.target, adjustType(paramMethodId.getDeclaringClassIndex()), adjustProto(paramMethodId.getProtoIndex()), adjustString(paramMethodId.getNameIndex()));
  }
  
  public ProtoId adjust(ProtoId paramProtoId) {
    return new ProtoId(this.target, adjustString(paramProtoId.getShortyIndex()), adjustType(paramProtoId.getReturnTypeIndex()), adjustTypeListOffset(paramProtoId.getParametersOffset()));
  }
  
  public SortableType adjust(SortableType paramSortableType) {
    return new SortableType(paramSortableType.getDex(), paramSortableType.getIndexMap(), adjust(paramSortableType.getClassDef()));
  }
  
  public int adjustAnnotation(int paramInt) {
    return ((Integer)this.annotationOffsets.get(Integer.valueOf(paramInt))).intValue();
  }
  
  public int adjustAnnotationDirectory(int paramInt) {
    return ((Integer)this.annotationDirectoryOffsets.get(Integer.valueOf(paramInt))).intValue();
  }
  
  public int adjustAnnotationSet(int paramInt) {
    return ((Integer)this.annotationSetOffsets.get(Integer.valueOf(paramInt))).intValue();
  }
  
  public int adjustAnnotationSetRefList(int paramInt) {
    return ((Integer)this.annotationSetRefListOffsets.get(Integer.valueOf(paramInt))).intValue();
  }
  
  public int adjustCallSite(int paramInt) {
    return this.callSiteIds[paramInt];
  }
  
  public int adjustEncodedArray(int paramInt) {
    return ((Integer)this.encodedArrayValueOffset.get(Integer.valueOf(paramInt))).intValue();
  }
  
  public EncodedValue adjustEncodedArray(EncodedValue paramEncodedValue) {
    ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput(32);
    (new EncodedValueTransformer((ByteOutput)byteArrayAnnotatedOutput)).transformArray(new EncodedValueReader(paramEncodedValue, 28));
    return new EncodedValue(byteArrayAnnotatedOutput.toByteArray());
  }
  
  public EncodedValue adjustEncodedValue(EncodedValue paramEncodedValue) {
    ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput(32);
    (new EncodedValueTransformer((ByteOutput)byteArrayAnnotatedOutput)).transform(new EncodedValueReader(paramEncodedValue));
    return new EncodedValue(byteArrayAnnotatedOutput.toByteArray());
  }
  
  public int adjustField(int paramInt) {
    return this.fieldIds[paramInt] & 0xFFFF;
  }
  
  public int adjustMethod(int paramInt) {
    return this.methodIds[paramInt] & 0xFFFF;
  }
  
  public int adjustMethodHandle(int paramInt) {
    return ((Integer)this.methodHandleIds.get(Integer.valueOf(paramInt))).intValue();
  }
  
  public int adjustProto(int paramInt) {
    return this.protoIds[paramInt] & 0xFFFF;
  }
  
  public int adjustString(int paramInt) {
    byte b = -1;
    if (paramInt == -1) {
      paramInt = b;
    } else {
      paramInt = this.stringIds[paramInt];
    } 
    return paramInt;
  }
  
  public int adjustType(int paramInt) {
    byte b = -1;
    if (paramInt == -1) {
      paramInt = b;
    } else {
      paramInt = 0xFFFF & this.typeIds[paramInt];
    } 
    return paramInt;
  }
  
  public TypeList adjustTypeList(TypeList paramTypeList) {
    if (paramTypeList == TypeList.EMPTY)
      return paramTypeList; 
    short[] arrayOfShort = (short[])paramTypeList.getTypes().clone();
    for (byte b = 0; b < arrayOfShort.length; b++)
      arrayOfShort[b] = (short)(short)adjustType(arrayOfShort[b]); 
    return new TypeList(this.target, arrayOfShort);
  }
  
  public int adjustTypeListOffset(int paramInt) {
    return ((Integer)this.typeListOffsets.get(Integer.valueOf(paramInt))).intValue();
  }
  
  public void putAnnotationDirectoryOffset(int paramInt1, int paramInt2) {
    if (paramInt1 > 0 && paramInt2 > 0) {
      this.annotationDirectoryOffsets.put(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
      return;
    } 
    throw new IllegalArgumentException();
  }
  
  public void putAnnotationOffset(int paramInt1, int paramInt2) {
    if (paramInt1 > 0 && paramInt2 > 0) {
      this.annotationOffsets.put(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
      return;
    } 
    throw new IllegalArgumentException();
  }
  
  public void putAnnotationSetOffset(int paramInt1, int paramInt2) {
    if (paramInt1 > 0 && paramInt2 > 0) {
      this.annotationSetOffsets.put(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
      return;
    } 
    throw new IllegalArgumentException();
  }
  
  public void putAnnotationSetRefListOffset(int paramInt1, int paramInt2) {
    if (paramInt1 > 0 && paramInt2 > 0) {
      this.annotationSetRefListOffsets.put(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
      return;
    } 
    throw new IllegalArgumentException();
  }
  
  public void putEncodedArrayValueOffset(int paramInt1, int paramInt2) {
    if (paramInt1 > 0 && paramInt2 > 0) {
      this.encodedArrayValueOffset.put(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
      return;
    } 
    throw new IllegalArgumentException();
  }
  
  public void putTypeListOffset(int paramInt1, int paramInt2) {
    if (paramInt1 > 0 && paramInt2 > 0) {
      this.typeListOffsets.put(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
      return;
    } 
    throw new IllegalArgumentException();
  }
  
  private final class EncodedValueTransformer {
    private final ByteOutput out;
    
    public EncodedValueTransformer(ByteOutput param1ByteOutput) {
      this.out = param1ByteOutput;
    }
    
    private void transformAnnotation(EncodedValueReader param1EncodedValueReader) {
      int i = param1EncodedValueReader.readAnnotation();
      Leb128.writeUnsignedLeb128(this.out, IndexMap.this.adjustType(param1EncodedValueReader.getAnnotationType()));
      Leb128.writeUnsignedLeb128(this.out, i);
      for (byte b = 0; b < i; b++) {
        Leb128.writeUnsignedLeb128(this.out, IndexMap.this.adjustString(param1EncodedValueReader.readAnnotationName()));
        transform(param1EncodedValueReader);
      } 
    }
    
    private void transformArray(EncodedValueReader param1EncodedValueReader) {
      int i = param1EncodedValueReader.readArray();
      Leb128.writeUnsignedLeb128(this.out, i);
      for (byte b = 0; b < i; b++)
        transform(param1EncodedValueReader); 
    }
    
    private void writeTypeAndArg(int param1Int1, int param1Int2) {
      this.out.writeByte(param1Int1 | param1Int2 << 5);
    }
    
    public void transform(EncodedValueReader param1EncodedValueReader) {
      int i = param1EncodedValueReader.peek();
      if (i != 0) {
        if (i != 6) {
          if (i != 2) {
            if (i != 3) {
              if (i != 4) {
                if (i != 16) {
                  if (i != 17) {
                    StringBuilder stringBuilder;
                    switch (i) {
                      default:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Unexpected type: ");
                        stringBuilder.append(Integer.toHexString(param1EncodedValueReader.peek()));
                        throw new DexException(stringBuilder.toString());
                      case 31:
                        writeTypeAndArg(31, param1EncodedValueReader.readBoolean());
                        return;
                      case 30:
                        param1EncodedValueReader.readNull();
                        writeTypeAndArg(30, 0);
                        return;
                      case 29:
                        writeTypeAndArg(29, 0);
                        transformAnnotation(param1EncodedValueReader);
                        return;
                      case 28:
                        writeTypeAndArg(28, 0);
                        transformArray(param1EncodedValueReader);
                        return;
                      case 27:
                        EncodedValueCodec.writeUnsignedIntegralValue(this.out, 27, IndexMap.this.adjustField(param1EncodedValueReader.readEnum()));
                        return;
                      case 26:
                        EncodedValueCodec.writeUnsignedIntegralValue(this.out, 26, IndexMap.this.adjustMethod(param1EncodedValueReader.readMethod()));
                        return;
                      case 25:
                        EncodedValueCodec.writeUnsignedIntegralValue(this.out, 25, IndexMap.this.adjustField(param1EncodedValueReader.readField()));
                        return;
                      case 24:
                        EncodedValueCodec.writeUnsignedIntegralValue(this.out, 24, IndexMap.this.adjustType(param1EncodedValueReader.readType()));
                        return;
                      case 23:
                        EncodedValueCodec.writeUnsignedIntegralValue(this.out, 23, IndexMap.this.adjustString(param1EncodedValueReader.readString()));
                        return;
                      case 22:
                        EncodedValueCodec.writeUnsignedIntegralValue(this.out, 22, IndexMap.this.adjustMethodHandle(param1EncodedValueReader.readMethodHandle()));
                        return;
                      case 21:
                        break;
                    } 
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 21, IndexMap.this.adjustProto(param1EncodedValueReader.readMethodType()));
                  } else {
                    EncodedValueCodec.writeRightZeroExtendedValue(this.out, 17, Double.doubleToLongBits(param1EncodedValueReader.readDouble()));
                  } 
                } else {
                  long l = Float.floatToIntBits(param1EncodedValueReader.readFloat());
                  EncodedValueCodec.writeRightZeroExtendedValue(this.out, 16, l << 32L);
                } 
              } else {
                EncodedValueCodec.writeSignedIntegralValue(this.out, 4, param1EncodedValueReader.readInt());
              } 
            } else {
              EncodedValueCodec.writeUnsignedIntegralValue(this.out, 3, param1EncodedValueReader.readChar());
            } 
          } else {
            EncodedValueCodec.writeSignedIntegralValue(this.out, 2, param1EncodedValueReader.readShort());
          } 
        } else {
          EncodedValueCodec.writeSignedIntegralValue(this.out, 6, param1EncodedValueReader.readLong());
        } 
      } else {
        EncodedValueCodec.writeSignedIntegralValue(this.out, 0, param1EncodedValueReader.readByte());
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\merge\IndexMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */