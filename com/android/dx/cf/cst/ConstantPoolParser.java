package com.android.dx.cf.cst;

import com.android.dx.cf.iface.ParseException;
import com.android.dx.cf.iface.ParseObserver;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstDouble;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstFloat;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.cst.CstInterfaceMethodRef;
import com.android.dx.rop.cst.CstInvokeDynamic;
import com.android.dx.rop.cst.CstLong;
import com.android.dx.rop.cst.CstMethodHandle;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstProtoRef;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.cst.StdConstantPool;
import com.android.dx.rop.type.Type;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;
import java.util.BitSet;

public final class ConstantPoolParser {
  private final ByteArray bytes;
  
  private int endOffset;
  
  private ParseObserver observer;
  
  private final int[] offsets;
  
  private final StdConstantPool pool;
  
  public ConstantPoolParser(ByteArray paramByteArray) {
    int i = paramByteArray.getUnsignedShort(8);
    this.bytes = paramByteArray;
    this.pool = new StdConstantPool(i);
    this.offsets = new int[i];
    this.endOffset = -1;
  }
  
  private void determineOffsets() {
    // Byte code:
    //   0: bipush #10
    //   2: istore_1
    //   3: iconst_1
    //   4: istore_2
    //   5: aload_0
    //   6: getfield offsets : [I
    //   9: astore_3
    //   10: iload_2
    //   11: aload_3
    //   12: arraylength
    //   13: if_icmpge -> 287
    //   16: aload_3
    //   17: iload_2
    //   18: iload_1
    //   19: iastore
    //   20: aload_0
    //   21: getfield bytes : Lcom/android/dx/util/ByteArray;
    //   24: iload_1
    //   25: invokevirtual getUnsignedByte : (I)I
    //   28: istore #4
    //   30: iload #4
    //   32: tableswitch default -> 120, 1 -> 157, 2 -> 120, 3 -> 148, 4 -> 148, 5 -> 139, 6 -> 139, 7 -> 133, 8 -> 133, 9 -> 148, 10 -> 148, 11 -> 148, 12 -> 148, 13 -> 120, 14 -> 120, 15 -> 127, 16 -> 133, 17 -> 120, 18 -> 148
    //   120: new com/android/dx/cf/iface/ParseException
    //   123: astore_3
    //   124: goto -> 191
    //   127: iinc #1, 4
    //   130: goto -> 151
    //   133: iinc #1, 3
    //   136: goto -> 151
    //   139: iconst_2
    //   140: istore #4
    //   142: iinc #1, 9
    //   145: goto -> 179
    //   148: iinc #1, 5
    //   151: iconst_1
    //   152: istore #4
    //   154: goto -> 179
    //   157: iload_1
    //   158: aload_0
    //   159: getfield bytes : Lcom/android/dx/util/ByteArray;
    //   162: iload_1
    //   163: iconst_1
    //   164: iadd
    //   165: invokevirtual getUnsignedShort : (I)I
    //   168: iconst_3
    //   169: iadd
    //   170: iadd
    //   171: istore #4
    //   173: iload #4
    //   175: istore_1
    //   176: goto -> 151
    //   179: iload_2
    //   180: iload #4
    //   182: iadd
    //   183: istore_2
    //   184: goto -> 5
    //   187: astore_3
    //   188: goto -> 231
    //   191: new java/lang/StringBuilder
    //   194: astore #5
    //   196: aload #5
    //   198: invokespecial <init> : ()V
    //   201: aload #5
    //   203: ldc 'unknown tag byte: '
    //   205: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: pop
    //   209: aload #5
    //   211: iload #4
    //   213: invokestatic u1 : (I)Ljava/lang/String;
    //   216: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   219: pop
    //   220: aload_3
    //   221: aload #5
    //   223: invokevirtual toString : ()Ljava/lang/String;
    //   226: invokespecial <init> : (Ljava/lang/String;)V
    //   229: aload_3
    //   230: athrow
    //   231: new java/lang/StringBuilder
    //   234: dup
    //   235: invokespecial <init> : ()V
    //   238: astore #5
    //   240: aload #5
    //   242: ldc '...while preparsing cst '
    //   244: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   247: pop
    //   248: aload #5
    //   250: iload_2
    //   251: invokestatic u2 : (I)Ljava/lang/String;
    //   254: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   257: pop
    //   258: aload #5
    //   260: ldc ' at offset '
    //   262: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   265: pop
    //   266: aload #5
    //   268: iload_1
    //   269: invokestatic u4 : (I)Ljava/lang/String;
    //   272: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   275: pop
    //   276: aload_3
    //   277: aload #5
    //   279: invokevirtual toString : ()Ljava/lang/String;
    //   282: invokevirtual addContext : (Ljava/lang/String;)V
    //   285: aload_3
    //   286: athrow
    //   287: aload_0
    //   288: iload_1
    //   289: putfield endOffset : I
    //   292: return
    // Exception table:
    //   from	to	target	type
    //   120	124	187	com/android/dx/cf/iface/ParseException
    //   157	173	187	com/android/dx/cf/iface/ParseException
    //   191	231	187	com/android/dx/cf/iface/ParseException
  }
  
  private static int getMethodHandleTypeForKind(int paramInt) {
    StringBuilder stringBuilder;
    switch (paramInt) {
      default:
        stringBuilder = new StringBuilder();
        stringBuilder.append("invalid kind: ");
        stringBuilder.append(paramInt);
        throw new IllegalArgumentException(stringBuilder.toString());
      case 9:
        return 8;
      case 8:
        return 6;
      case 7:
        return 7;
      case 6:
        return 4;
      case 5:
        return 5;
      case 4:
        return 0;
      case 3:
        return 2;
      case 2:
        return 1;
      case 1:
        break;
    } 
    return 3;
  }
  
  private void parse() {
    determineOffsets();
    ParseObserver parseObserver = this.observer;
    int i = 1;
    if (parseObserver != null) {
      ByteArray byteArray = this.bytes;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("constant_pool_count: ");
      stringBuilder.append(Hex.u2(this.offsets.length));
      parseObserver.parsed(byteArray, 8, 2, stringBuilder.toString());
      this.observer.parsed(this.bytes, 10, 0, "\nconstant_pool:");
      this.observer.changeIndent(1);
    } 
    BitSet bitSet = new BitSet(this.offsets.length);
    int j = 1;
    while (true) {
      int[] arrayOfInt = this.offsets;
      if (j < arrayOfInt.length) {
        if (arrayOfInt[j] != 0 && this.pool.getOrNull(j) == null)
          parse0(j, bitSet); 
        j++;
        continue;
      } 
      if (this.observer != null) {
        for (j = i; j < this.offsets.length; j++) {
          Constant constant = this.pool.getOrNull(j);
          if (constant != null) {
            String str;
            int n;
            int k = this.offsets[j];
            int m = this.endOffset;
            i = j + 1;
            while (true) {
              int[] arrayOfInt1 = this.offsets;
              n = m;
              if (i < arrayOfInt1.length) {
                n = arrayOfInt1[i];
                if (n != 0)
                  break; 
                i++;
                continue;
              } 
              break;
            } 
            if (bitSet.get(j)) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(Hex.u2(j));
              stringBuilder.append(": utf8{\"");
              stringBuilder.append(constant.toHuman());
              stringBuilder.append("\"}");
              str = stringBuilder.toString();
            } else {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(Hex.u2(j));
              stringBuilder.append(": ");
              stringBuilder.append(str.toString());
              str = stringBuilder.toString();
            } 
            this.observer.parsed(this.bytes, k, n - k, str);
          } 
        } 
        this.observer.changeIndent(-1);
        this.observer.parsed(this.bytes, this.endOffset, 0, "end constant_pool");
      } 
      return;
    } 
  }
  
  private Constant parse0(int paramInt, BitSet paramBitSet) {
    Constant constant = this.pool.getOrNull(paramInt);
    if (constant != null)
      return constant; 
    int i = this.offsets[paramInt];
    try {
      ParseException parseException2;
      CstInvokeDynamic cstInvokeDynamic;
      CstProtoRef cstProtoRef;
      StringBuilder stringBuilder1;
      CstInterfaceMethodRef cstInterfaceMethodRef2;
      Constant constant2;
      ParseException parseException1;
      CstMethodRef cstMethodRef2;
      CstFieldRef cstFieldRef2;
      CstMethodHandle cstMethodHandle;
      CstNat cstNat;
      CstInterfaceMethodRef cstInterfaceMethodRef1;
      CstMethodRef cstMethodRef1;
      CstFieldRef cstFieldRef1;
      Constant constant1;
      CstType cstType;
      CstDouble cstDouble;
      CstLong cstLong;
      CstFloat cstFloat;
      CstInteger cstInteger;
      StringBuilder stringBuilder2;
      ParseException parseException3;
      Constant constant3;
      int k;
      int j = this.bytes.getUnsignedByte(i);
      switch (j) {
        default:
          parseException2 = new ParseException();
          stringBuilder2 = new StringBuilder();
          this();
          stringBuilder2.append("unknown tag byte: ");
          stringBuilder2.append(Hex.u1(j));
          this(stringBuilder2.toString());
          throw parseException2;
        case 18:
          cstInvokeDynamic = CstInvokeDynamic.make(this.bytes.getUnsignedShort(i + 1), (CstNat)parse0(this.bytes.getUnsignedShort(i + 3), (BitSet)parseException2));
          this.pool.set(paramInt, (Constant)cstInvokeDynamic);
          return (Constant)cstInvokeDynamic;
        case 16:
          cstProtoRef = CstProtoRef.make((CstString)parse0(this.bytes.getUnsignedShort(i + 1), (BitSet)cstInvokeDynamic));
          this.pool.set(paramInt, (Constant)cstProtoRef);
          return (Constant)cstProtoRef;
        case 15:
          k = this.bytes.getUnsignedByte(i + 1);
          j = this.bytes.getUnsignedShort(i + 2);
          switch (k) {
            default:
              parseException3 = new ParseException();
              stringBuilder1 = new StringBuilder();
              this();
              stringBuilder1.append("Unsupported MethodHandle kind: ");
              stringBuilder1.append(k);
              this(stringBuilder1.toString());
              throw parseException3;
            case 9:
              cstInterfaceMethodRef2 = (CstInterfaceMethodRef)parse0(j, (BitSet)stringBuilder1);
              break;
            case 6:
            case 7:
              constant3 = parse0(j, (BitSet)cstInterfaceMethodRef2);
              constant2 = constant3;
              if (!(constant3 instanceof CstMethodRef)) {
                if (constant3 instanceof CstInterfaceMethodRef) {
                  constant2 = constant3;
                  break;
                } 
                parseException1 = new ParseException();
                StringBuilder stringBuilder = new StringBuilder();
                this();
                stringBuilder.append("Unsupported ref constant type for MethodHandle ");
                stringBuilder.append(constant3.getClass());
                this(stringBuilder.toString());
                throw parseException1;
              } 
              break;
            case 5:
            case 8:
              cstMethodRef2 = (CstMethodRef)parse0(j, (BitSet)parseException1);
              break;
            case 1:
            case 2:
            case 3:
            case 4:
              cstFieldRef2 = (CstFieldRef)parse0(j, (BitSet)cstMethodRef2);
              break;
          } 
          cstMethodHandle = CstMethodHandle.make(getMethodHandleTypeForKind(k), (Constant)cstFieldRef2);
          this.pool.set(paramInt, (Constant)cstMethodHandle);
          return (Constant)cstMethodHandle;
        case 12:
          cstNat = new CstNat((CstString)parse0(this.bytes.getUnsignedShort(i + 1), (BitSet)cstMethodHandle), (CstString)parse0(this.bytes.getUnsignedShort(i + 3), (BitSet)cstMethodHandle));
          this.pool.set(paramInt, (Constant)cstNat);
          return (Constant)cstNat;
        case 11:
          cstInterfaceMethodRef1 = new CstInterfaceMethodRef((CstType)parse0(this.bytes.getUnsignedShort(i + 1), (BitSet)cstNat), (CstNat)parse0(this.bytes.getUnsignedShort(i + 3), (BitSet)cstNat));
          this.pool.set(paramInt, (Constant)cstInterfaceMethodRef1);
          return (Constant)cstInterfaceMethodRef1;
        case 10:
          cstMethodRef1 = new CstMethodRef((CstType)parse0(this.bytes.getUnsignedShort(i + 1), (BitSet)cstInterfaceMethodRef1), (CstNat)parse0(this.bytes.getUnsignedShort(i + 3), (BitSet)cstInterfaceMethodRef1));
          this.pool.set(paramInt, (Constant)cstMethodRef1);
          return (Constant)cstMethodRef1;
        case 9:
          cstFieldRef1 = new CstFieldRef((CstType)parse0(this.bytes.getUnsignedShort(i + 1), (BitSet)cstMethodRef1), (CstNat)parse0(this.bytes.getUnsignedShort(i + 3), (BitSet)cstMethodRef1));
          this.pool.set(paramInt, (Constant)cstFieldRef1);
          return (Constant)cstFieldRef1;
        case 8:
          constant1 = parse0(this.bytes.getUnsignedShort(i + 1), (BitSet)cstFieldRef1);
          this.pool.set(paramInt, constant1);
          return constant1;
        case 7:
          cstType = new CstType(Type.internClassName(((CstString)parse0(this.bytes.getUnsignedShort(i + 1), (BitSet)constant1)).getString()));
          this.pool.set(paramInt, (Constant)cstType);
          return (Constant)cstType;
        case 6:
          cstDouble = CstDouble.make(this.bytes.getLong(i + 1));
          this.pool.set(paramInt, (Constant)cstDouble);
          return (Constant)cstDouble;
        case 5:
          cstLong = CstLong.make(this.bytes.getLong(i + 1));
          this.pool.set(paramInt, (Constant)cstLong);
          return (Constant)cstLong;
        case 4:
          cstFloat = CstFloat.make(this.bytes.getInt(i + 1));
          this.pool.set(paramInt, (Constant)cstFloat);
          return (Constant)cstFloat;
        case 3:
          cstInteger = CstInteger.make(this.bytes.getInt(i + 1));
          this.pool.set(paramInt, (Constant)cstInteger);
          return (Constant)cstInteger;
        case 1:
          break;
      } 
      CstString cstString2 = parseUtf8(i);
      cstInteger.set(paramInt);
      CstString cstString1 = cstString2;
      this.pool.set(paramInt, (Constant)cstString1);
      return (Constant)cstString1;
    } catch (ParseException parseException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("...while parsing cst ");
      stringBuilder.append(Hex.u2(paramInt));
      stringBuilder.append(" at offset ");
      stringBuilder.append(Hex.u4(i));
      parseException.addContext(stringBuilder.toString());
      throw parseException;
    } catch (RuntimeException runtimeException) {
      ParseException parseException = new ParseException(runtimeException);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("...while parsing cst ");
      stringBuilder.append(Hex.u2(paramInt));
      stringBuilder.append(" at offset ");
      stringBuilder.append(Hex.u4(i));
      parseException.addContext(stringBuilder.toString());
      throw parseException;
    } 
  }
  
  private void parseIfNecessary() {
    if (this.endOffset < 0)
      parse(); 
  }
  
  private CstString parseUtf8(int paramInt) {
    int i = this.bytes.getUnsignedShort(paramInt + 1);
    paramInt += 3;
    ByteArray byteArray = this.bytes.slice(paramInt, i + paramInt);
    try {
      return new CstString(byteArray);
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new ParseException(illegalArgumentException);
    } 
  }
  
  public int getEndOffset() {
    parseIfNecessary();
    return this.endOffset;
  }
  
  public StdConstantPool getPool() {
    parseIfNecessary();
    return this.pool;
  }
  
  public void setObserver(ParseObserver paramParseObserver) {
    this.observer = paramParseObserver;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\cst\ConstantPoolParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */