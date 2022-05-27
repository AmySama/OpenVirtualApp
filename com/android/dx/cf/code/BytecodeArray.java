package com.android.dx.cf.code;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.ConstantPool;
import com.android.dx.rop.cst.CstDouble;
import com.android.dx.rop.cst.CstFloat;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.cst.CstKnownNull;
import com.android.dx.rop.cst.CstLong;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.util.Bits;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;
import java.util.ArrayList;

public final class BytecodeArray {
  public static final Visitor EMPTY_VISITOR = new BaseVisitor();
  
  private final ByteArray bytes;
  
  private final ConstantPool pool;
  
  public BytecodeArray(ByteArray paramByteArray, ConstantPool paramConstantPool) {
    if (paramByteArray != null) {
      if (paramConstantPool != null) {
        this.bytes = paramByteArray;
        this.pool = paramConstantPool;
        return;
      } 
      throw new NullPointerException("pool == null");
    } 
    throw new NullPointerException("bytes == null");
  }
  
  private int parseLookupswitch(int paramInt, Visitor paramVisitor) {
    int i = paramInt + 4 & 0xFFFFFFFC;
    int j = paramInt + 1;
    int k = 0;
    int m = 0;
    while (j < i) {
      m = m << 8 | this.bytes.getUnsignedByte(j);
      j++;
    } 
    int n = this.bytes.getInt(i);
    int i1 = this.bytes.getInt(i + 4);
    i += 8;
    SwitchList switchList = new SwitchList(i1);
    j = k;
    k = i;
    while (j < i1) {
      int i2 = this.bytes.getInt(k);
      i = this.bytes.getInt(k + 4);
      k += 8;
      switchList.add(i2, i + paramInt);
      j++;
    } 
    switchList.setDefaultTarget(n + paramInt);
    switchList.removeSuperfluousDefaults();
    switchList.setImmutable();
    j = k - paramInt;
    paramVisitor.visitSwitch(171, paramInt, j, switchList, m);
    return j;
  }
  
  private int parseNewarray(int paramInt, Visitor paramVisitor) {
    // Byte code:
    //   0: aload_0
    //   1: getfield bytes : Lcom/android/dx/util/ByteArray;
    //   4: iload_1
    //   5: iconst_1
    //   6: iadd
    //   7: invokevirtual getUnsignedByte : (I)I
    //   10: istore_3
    //   11: iload_3
    //   12: tableswitch default -> 60, 4 -> 152, 5 -> 144, 6 -> 136, 7 -> 128, 8 -> 120, 9 -> 112, 10 -> 104, 11 -> 96
    //   60: new java/lang/StringBuilder
    //   63: dup
    //   64: invokespecial <init> : ()V
    //   67: astore_2
    //   68: aload_2
    //   69: ldc 'bad newarray code '
    //   71: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: pop
    //   75: aload_2
    //   76: iload_3
    //   77: invokestatic u1 : (I)Ljava/lang/String;
    //   80: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: pop
    //   84: new com/android/dx/cf/code/SimException
    //   87: dup
    //   88: aload_2
    //   89: invokevirtual toString : ()Ljava/lang/String;
    //   92: invokespecial <init> : (Ljava/lang/String;)V
    //   95: athrow
    //   96: getstatic com/android/dx/rop/cst/CstType.LONG_ARRAY : Lcom/android/dx/rop/cst/CstType;
    //   99: astore #4
    //   101: goto -> 157
    //   104: getstatic com/android/dx/rop/cst/CstType.INT_ARRAY : Lcom/android/dx/rop/cst/CstType;
    //   107: astore #4
    //   109: goto -> 157
    //   112: getstatic com/android/dx/rop/cst/CstType.SHORT_ARRAY : Lcom/android/dx/rop/cst/CstType;
    //   115: astore #4
    //   117: goto -> 157
    //   120: getstatic com/android/dx/rop/cst/CstType.BYTE_ARRAY : Lcom/android/dx/rop/cst/CstType;
    //   123: astore #4
    //   125: goto -> 157
    //   128: getstatic com/android/dx/rop/cst/CstType.DOUBLE_ARRAY : Lcom/android/dx/rop/cst/CstType;
    //   131: astore #4
    //   133: goto -> 157
    //   136: getstatic com/android/dx/rop/cst/CstType.FLOAT_ARRAY : Lcom/android/dx/rop/cst/CstType;
    //   139: astore #4
    //   141: goto -> 157
    //   144: getstatic com/android/dx/rop/cst/CstType.CHAR_ARRAY : Lcom/android/dx/rop/cst/CstType;
    //   147: astore #4
    //   149: goto -> 157
    //   152: getstatic com/android/dx/rop/cst/CstType.BOOLEAN_ARRAY : Lcom/android/dx/rop/cst/CstType;
    //   155: astore #4
    //   157: aload_2
    //   158: invokeinterface getPreviousOffset : ()I
    //   163: istore #5
    //   165: new com/android/dx/cf/code/BytecodeArray$ConstantParserVisitor
    //   168: dup
    //   169: aload_0
    //   170: invokespecial <init> : (Lcom/android/dx/cf/code/BytecodeArray;)V
    //   173: astore #6
    //   175: iconst_0
    //   176: istore #7
    //   178: iload #5
    //   180: iflt -> 225
    //   183: aload_0
    //   184: iload #5
    //   186: aload #6
    //   188: invokevirtual parseInstruction : (ILcom/android/dx/cf/code/BytecodeArray$Visitor;)I
    //   191: pop
    //   192: aload #6
    //   194: getfield cst : Lcom/android/dx/rop/cst/Constant;
    //   197: instanceof com/android/dx/rop/cst/CstInteger
    //   200: ifeq -> 225
    //   203: aload #6
    //   205: getfield length : I
    //   208: iload #5
    //   210: iadd
    //   211: iload_1
    //   212: if_icmpne -> 225
    //   215: aload #6
    //   217: getfield value : I
    //   220: istore #8
    //   222: goto -> 228
    //   225: iconst_0
    //   226: istore #8
    //   228: iload_1
    //   229: iconst_2
    //   230: iadd
    //   231: istore #5
    //   233: new java/util/ArrayList
    //   236: dup
    //   237: invokespecial <init> : ()V
    //   240: astore #9
    //   242: iload #5
    //   244: istore #10
    //   246: iload #8
    //   248: ifeq -> 548
    //   251: iconst_0
    //   252: istore #10
    //   254: aload_0
    //   255: getfield bytes : Lcom/android/dx/util/ByteArray;
    //   258: astore #11
    //   260: iload #5
    //   262: iconst_1
    //   263: iadd
    //   264: istore #7
    //   266: aload #11
    //   268: iload #5
    //   270: invokevirtual getUnsignedByte : (I)I
    //   273: bipush #89
    //   275: if_icmpeq -> 281
    //   278: goto -> 540
    //   281: aload_0
    //   282: iload #7
    //   284: aload #6
    //   286: invokevirtual parseInstruction : (ILcom/android/dx/cf/code/BytecodeArray$Visitor;)I
    //   289: pop
    //   290: aload #6
    //   292: getfield length : I
    //   295: ifeq -> 540
    //   298: aload #6
    //   300: getfield cst : Lcom/android/dx/rop/cst/Constant;
    //   303: instanceof com/android/dx/rop/cst/CstInteger
    //   306: ifeq -> 540
    //   309: aload #6
    //   311: getfield value : I
    //   314: iload #10
    //   316: if_icmpeq -> 322
    //   319: goto -> 540
    //   322: iload #7
    //   324: aload #6
    //   326: getfield length : I
    //   329: iadd
    //   330: istore #7
    //   332: aload_0
    //   333: iload #7
    //   335: aload #6
    //   337: invokevirtual parseInstruction : (ILcom/android/dx/cf/code/BytecodeArray$Visitor;)I
    //   340: pop
    //   341: aload #6
    //   343: getfield length : I
    //   346: ifeq -> 540
    //   349: aload #6
    //   351: getfield cst : Lcom/android/dx/rop/cst/Constant;
    //   354: instanceof com/android/dx/rop/cst/CstLiteralBits
    //   357: ifne -> 363
    //   360: goto -> 540
    //   363: iload #7
    //   365: aload #6
    //   367: getfield length : I
    //   370: iadd
    //   371: istore #12
    //   373: aload #9
    //   375: aload #6
    //   377: getfield cst : Lcom/android/dx/rop/cst/Constant;
    //   380: invokevirtual add : (Ljava/lang/Object;)Z
    //   383: pop
    //   384: aload_0
    //   385: getfield bytes : Lcom/android/dx/util/ByteArray;
    //   388: iload #12
    //   390: invokevirtual getUnsignedByte : (I)I
    //   393: istore #13
    //   395: iconst_1
    //   396: istore #7
    //   398: iload_3
    //   399: tableswitch default -> 444, 4 -> 507, 5 -> 497, 6 -> 487, 7 -> 477, 8 -> 507, 9 -> 467, 10 -> 457, 11 -> 447
    //   444: goto -> 520
    //   447: iload #13
    //   449: bipush #80
    //   451: if_icmpeq -> 517
    //   454: goto -> 520
    //   457: iload #13
    //   459: bipush #79
    //   461: if_icmpeq -> 517
    //   464: goto -> 520
    //   467: iload #13
    //   469: bipush #86
    //   471: if_icmpeq -> 517
    //   474: goto -> 520
    //   477: iload #13
    //   479: bipush #82
    //   481: if_icmpeq -> 517
    //   484: goto -> 520
    //   487: iload #13
    //   489: bipush #81
    //   491: if_icmpeq -> 517
    //   494: goto -> 520
    //   497: iload #13
    //   499: bipush #85
    //   501: if_icmpeq -> 517
    //   504: goto -> 520
    //   507: iload #13
    //   509: bipush #84
    //   511: if_icmpeq -> 517
    //   514: goto -> 520
    //   517: iconst_0
    //   518: istore #7
    //   520: iload #7
    //   522: ifeq -> 528
    //   525: goto -> 540
    //   528: iinc #10, 1
    //   531: iload #12
    //   533: iconst_1
    //   534: iadd
    //   535: istore #5
    //   537: goto -> 254
    //   540: iload #10
    //   542: istore #7
    //   544: iload #5
    //   546: istore #10
    //   548: iload #7
    //   550: iconst_2
    //   551: if_icmplt -> 586
    //   554: iload #7
    //   556: iload #8
    //   558: if_icmpeq -> 564
    //   561: goto -> 586
    //   564: iload #10
    //   566: iload_1
    //   567: isub
    //   568: istore #5
    //   570: aload_2
    //   571: iload_1
    //   572: iload #5
    //   574: aload #4
    //   576: aload #9
    //   578: invokeinterface visitNewarray : (IILcom/android/dx/rop/cst/CstType;Ljava/util/ArrayList;)V
    //   583: iload #5
    //   585: ireturn
    //   586: aload_2
    //   587: iload_1
    //   588: iconst_2
    //   589: aload #4
    //   591: aconst_null
    //   592: invokeinterface visitNewarray : (IILcom/android/dx/rop/cst/CstType;Ljava/util/ArrayList;)V
    //   597: iconst_2
    //   598: ireturn
  }
  
  private int parseTableswitch(int paramInt, Visitor paramVisitor) {
    int i = paramInt + 4 & 0xFFFFFFFC;
    int j = paramInt + 1;
    int k = 0;
    int m = 0;
    while (j < i) {
      m = m << 8 | this.bytes.getUnsignedByte(j);
      j++;
    } 
    int n = this.bytes.getInt(i);
    int i1 = this.bytes.getInt(i + 4);
    j = this.bytes.getInt(i + 8);
    int i2 = j - i1 + 1;
    i += 12;
    if (i1 <= j) {
      SwitchList switchList = new SwitchList(i2);
      j = k;
      k = i;
      while (j < i2) {
        i = this.bytes.getInt(k);
        k += 4;
        switchList.add(i1 + j, i + paramInt);
        j++;
      } 
      switchList.setDefaultTarget(n + paramInt);
      switchList.removeSuperfluousDefaults();
      switchList.setImmutable();
      j = k - paramInt;
      paramVisitor.visitSwitch(171, paramInt, j, switchList, m);
      return j;
    } 
    throw new SimException("low / high inversion");
  }
  
  private int parseWide(int paramInt, Visitor paramVisitor) {
    int i = this.bytes.getUnsignedByte(paramInt + 1);
    int j = this.bytes.getUnsignedShort(paramInt + 2);
    if (i != 132) {
      if (i != 169) {
        switch (i) {
          default:
            switch (i) {
              default:
                paramVisitor.visitInvalid(196, paramInt, 1);
                return 1;
              case 58:
                paramVisitor.visitLocal(54, paramInt, 4, j, Type.OBJECT, 0);
                return 4;
              case 57:
                paramVisitor.visitLocal(54, paramInt, 4, j, Type.DOUBLE, 0);
                return 4;
              case 56:
                paramVisitor.visitLocal(54, paramInt, 4, j, Type.FLOAT, 0);
                return 4;
              case 55:
                paramVisitor.visitLocal(54, paramInt, 4, j, Type.LONG, 0);
                return 4;
              case 54:
                break;
            } 
            paramVisitor.visitLocal(54, paramInt, 4, j, Type.INT, 0);
            return 4;
          case 25:
            paramVisitor.visitLocal(21, paramInt, 4, j, Type.OBJECT, 0);
            return 4;
          case 24:
            paramVisitor.visitLocal(21, paramInt, 4, j, Type.DOUBLE, 0);
            return 4;
          case 23:
            paramVisitor.visitLocal(21, paramInt, 4, j, Type.FLOAT, 0);
            return 4;
          case 22:
            paramVisitor.visitLocal(21, paramInt, 4, j, Type.LONG, 0);
            return 4;
          case 21:
            break;
        } 
        paramVisitor.visitLocal(21, paramInt, 4, j, Type.INT, 0);
        return 4;
      } 
      paramVisitor.visitLocal(i, paramInt, 4, j, Type.RETURN_ADDRESS, 0);
      return 4;
    } 
    int k = this.bytes.getShort(paramInt + 4);
    paramVisitor.visitLocal(i, paramInt, 6, j, Type.INT, k);
    return 6;
  }
  
  public int byteLength() {
    return this.bytes.size() + 4;
  }
  
  public void forEach(Visitor paramVisitor) {
    int i = this.bytes.size();
    for (int j = 0; j < i; j += parseInstruction(j, paramVisitor));
  }
  
  public ByteArray getBytes() {
    return this.bytes;
  }
  
  public int[] getInstructionOffsets() {
    int i = this.bytes.size();
    int[] arrayOfInt = Bits.makeBitSet(i);
    for (int j = 0; j < i; j += parseInstruction(j, null))
      Bits.set(arrayOfInt, j, true); 
    return arrayOfInt;
  }
  
  public int parseInstruction(int paramInt, Visitor paramVisitor) {
    Visitor visitor = paramVisitor;
    if (paramVisitor == null)
      visitor = EMPTY_VISITOR; 
    try {
      Constant constant;
      int j;
      int k;
      int m;
      int i = this.bytes.getUnsignedByte(paramInt);
      ByteOps.opInfo(i);
      switch (i) {
        default:
          visitor.visitInvalid(i, paramInt, 1);
          return 1;
        case 200:
        case 201:
          j = this.bytes.getInt(paramInt + 1);
          if (i == 200) {
            i = 167;
          } else {
            i = 168;
          } 
          visitor.visitBranch(i, paramInt, 5, j + paramInt);
          return 5;
        case 197:
          j = this.bytes.getUnsignedShort(paramInt + 1);
          k = this.bytes.getUnsignedByte(paramInt + 3);
          visitor.visitConstant(i, paramInt, 4, this.pool.get(j), k);
          return 4;
        case 196:
          return parseWide(paramInt, visitor);
        case 188:
          return parseNewarray(paramInt, visitor);
        case 186:
          j = this.bytes.getUnsignedShort(paramInt + 1);
          visitor.visitConstant(i, paramInt, 5, this.pool.get(j), 0);
          return 5;
        case 185:
          m = this.bytes.getUnsignedShort(paramInt + 1);
          k = this.bytes.getUnsignedByte(paramInt + 3);
          j = this.bytes.getUnsignedByte(paramInt + 4);
          visitor.visitConstant(i, paramInt, 5, this.pool.get(m), k | j << 8);
          return 5;
        case 178:
        case 179:
        case 180:
        case 181:
        case 182:
        case 183:
        case 184:
        case 187:
        case 189:
        case 192:
        case 193:
          j = this.bytes.getUnsignedShort(paramInt + 1);
          visitor.visitConstant(i, paramInt, 3, this.pool.get(j), 0);
          return 3;
        case 177:
        case 191:
        case 194:
        case 195:
          visitor.visitNoArgs(i, paramInt, 1, Type.VOID);
          return 1;
        case 176:
          visitor.visitNoArgs(172, paramInt, 1, Type.OBJECT);
          return 1;
        case 175:
          visitor.visitNoArgs(172, paramInt, 1, Type.DOUBLE);
          return 1;
        case 174:
          visitor.visitNoArgs(172, paramInt, 1, Type.FLOAT);
          return 1;
        case 173:
          visitor.visitNoArgs(172, paramInt, 1, Type.LONG);
          return 1;
        case 172:
          visitor.visitNoArgs(172, paramInt, 1, Type.INT);
          return 1;
        case 171:
          return parseLookupswitch(paramInt, visitor);
        case 170:
          return parseTableswitch(paramInt, visitor);
        case 169:
          visitor.visitLocal(i, paramInt, 2, this.bytes.getUnsignedByte(paramInt + 1), Type.RETURN_ADDRESS, 0);
          return 2;
        case 153:
        case 154:
        case 155:
        case 156:
        case 157:
        case 158:
        case 159:
        case 160:
        case 161:
        case 162:
        case 163:
        case 164:
        case 165:
        case 166:
        case 167:
        case 168:
        case 198:
        case 199:
          visitor.visitBranch(i, paramInt, 3, this.bytes.getShort(paramInt + 1) + paramInt);
          return 3;
        case 136:
        case 139:
        case 142:
        case 145:
        case 146:
        case 147:
        case 148:
        case 149:
        case 150:
        case 151:
        case 152:
        case 190:
          visitor.visitNoArgs(i, paramInt, 1, Type.INT);
          return 1;
        case 135:
        case 138:
        case 141:
          visitor.visitNoArgs(i, paramInt, 1, Type.DOUBLE);
          return 1;
        case 134:
        case 137:
        case 144:
          visitor.visitNoArgs(i, paramInt, 1, Type.FLOAT);
          return 1;
        case 133:
        case 140:
        case 143:
          visitor.visitNoArgs(i, paramInt, 1, Type.LONG);
          return 1;
        case 132:
          k = this.bytes.getUnsignedByte(paramInt + 1);
          j = this.bytes.getByte(paramInt + 2);
          visitor.visitLocal(i, paramInt, 3, k, Type.INT, j);
          return 3;
        case 99:
        case 103:
        case 107:
        case 111:
        case 115:
        case 119:
          visitor.visitNoArgs(i - 3, paramInt, 1, Type.DOUBLE);
          return 1;
        case 98:
        case 102:
        case 106:
        case 110:
        case 114:
        case 118:
          visitor.visitNoArgs(i - 2, paramInt, 1, Type.FLOAT);
          return 1;
        case 97:
        case 101:
        case 105:
        case 109:
        case 113:
        case 117:
        case 121:
        case 123:
        case 125:
        case 127:
        case 129:
        case 131:
          visitor.visitNoArgs(i - 1, paramInt, 1, Type.LONG);
          return 1;
        case 96:
        case 100:
        case 104:
        case 108:
        case 112:
        case 116:
        case 120:
        case 122:
        case 124:
        case 126:
        case 128:
        case 130:
          visitor.visitNoArgs(i, paramInt, 1, Type.INT);
          return 1;
        case 87:
        case 88:
        case 89:
        case 90:
        case 91:
        case 92:
        case 93:
        case 94:
        case 95:
          visitor.visitNoArgs(i, paramInt, 1, Type.VOID);
          return 1;
        case 86:
          visitor.visitNoArgs(79, paramInt, 1, Type.SHORT);
          return 1;
        case 85:
          visitor.visitNoArgs(79, paramInt, 1, Type.CHAR);
          return 1;
        case 84:
          visitor.visitNoArgs(79, paramInt, 1, Type.BYTE);
          return 1;
        case 83:
          visitor.visitNoArgs(79, paramInt, 1, Type.OBJECT);
          return 1;
        case 82:
          visitor.visitNoArgs(79, paramInt, 1, Type.DOUBLE);
          return 1;
        case 81:
          visitor.visitNoArgs(79, paramInt, 1, Type.FLOAT);
          return 1;
        case 80:
          visitor.visitNoArgs(79, paramInt, 1, Type.LONG);
          return 1;
        case 79:
          visitor.visitNoArgs(79, paramInt, 1, Type.INT);
          return 1;
        case 75:
        case 76:
        case 77:
        case 78:
          visitor.visitLocal(54, paramInt, 1, i - 75, Type.OBJECT, 0);
          return 1;
        case 71:
        case 72:
        case 73:
        case 74:
          visitor.visitLocal(54, paramInt, 1, i - 71, Type.DOUBLE, 0);
          return 1;
        case 67:
        case 68:
        case 69:
        case 70:
          visitor.visitLocal(54, paramInt, 1, i - 67, Type.FLOAT, 0);
          return 1;
        case 63:
        case 64:
        case 65:
        case 66:
          visitor.visitLocal(54, paramInt, 1, i - 63, Type.LONG, 0);
          return 1;
        case 59:
        case 60:
        case 61:
        case 62:
          visitor.visitLocal(54, paramInt, 1, i - 59, Type.INT, 0);
          return 1;
        case 58:
          visitor.visitLocal(54, paramInt, 2, this.bytes.getUnsignedByte(paramInt + 1), Type.OBJECT, 0);
          return 2;
        case 57:
          visitor.visitLocal(54, paramInt, 2, this.bytes.getUnsignedByte(paramInt + 1), Type.DOUBLE, 0);
          return 2;
        case 56:
          visitor.visitLocal(54, paramInt, 2, this.bytes.getUnsignedByte(paramInt + 1), Type.FLOAT, 0);
          return 2;
        case 55:
          visitor.visitLocal(54, paramInt, 2, this.bytes.getUnsignedByte(paramInt + 1), Type.LONG, 0);
          return 2;
        case 54:
          visitor.visitLocal(54, paramInt, 2, this.bytes.getUnsignedByte(paramInt + 1), Type.INT, 0);
          return 2;
        case 53:
          visitor.visitNoArgs(46, paramInt, 1, Type.SHORT);
          return 1;
        case 52:
          visitor.visitNoArgs(46, paramInt, 1, Type.CHAR);
          return 1;
        case 51:
          visitor.visitNoArgs(46, paramInt, 1, Type.BYTE);
          return 1;
        case 50:
          visitor.visitNoArgs(46, paramInt, 1, Type.OBJECT);
          return 1;
        case 49:
          visitor.visitNoArgs(46, paramInt, 1, Type.DOUBLE);
          return 1;
        case 48:
          visitor.visitNoArgs(46, paramInt, 1, Type.FLOAT);
          return 1;
        case 47:
          visitor.visitNoArgs(46, paramInt, 1, Type.LONG);
          return 1;
        case 46:
          visitor.visitNoArgs(46, paramInt, 1, Type.INT);
          return 1;
        case 42:
        case 43:
        case 44:
        case 45:
          visitor.visitLocal(21, paramInt, 1, i - 42, Type.OBJECT, 0);
          return 1;
        case 38:
        case 39:
        case 40:
        case 41:
          visitor.visitLocal(21, paramInt, 1, i - 38, Type.DOUBLE, 0);
          return 1;
        case 34:
        case 35:
        case 36:
        case 37:
          visitor.visitLocal(21, paramInt, 1, i - 34, Type.FLOAT, 0);
          return 1;
        case 30:
        case 31:
        case 32:
        case 33:
          visitor.visitLocal(21, paramInt, 1, i - 30, Type.LONG, 0);
          return 1;
        case 26:
        case 27:
        case 28:
        case 29:
          visitor.visitLocal(21, paramInt, 1, i - 26, Type.INT, 0);
          return 1;
        case 25:
          visitor.visitLocal(21, paramInt, 2, this.bytes.getUnsignedByte(paramInt + 1), Type.OBJECT, 0);
          return 2;
        case 24:
          visitor.visitLocal(21, paramInt, 2, this.bytes.getUnsignedByte(paramInt + 1), Type.DOUBLE, 0);
          return 2;
        case 23:
          visitor.visitLocal(21, paramInt, 2, this.bytes.getUnsignedByte(paramInt + 1), Type.FLOAT, 0);
          return 2;
        case 22:
          visitor.visitLocal(21, paramInt, 2, this.bytes.getUnsignedByte(paramInt + 1), Type.LONG, 0);
          return 2;
        case 21:
          visitor.visitLocal(21, paramInt, 2, this.bytes.getUnsignedByte(paramInt + 1), Type.INT, 0);
          return 2;
        case 20:
          i = this.bytes.getUnsignedShort(paramInt + 1);
          visitor.visitConstant(20, paramInt, 3, this.pool.get(i), 0);
          return 3;
        case 19:
          i = this.bytes.getUnsignedShort(paramInt + 1);
          constant = this.pool.get(i);
          if (constant instanceof CstInteger) {
            i = ((CstInteger)constant).getValue();
          } else {
            i = 0;
          } 
          visitor.visitConstant(18, paramInt, 3, constant, i);
          return 3;
        case 18:
          i = this.bytes.getUnsignedByte(paramInt + 1);
          constant = this.pool.get(i);
          if (constant instanceof CstInteger) {
            i = ((CstInteger)constant).getValue();
          } else {
            i = 0;
          } 
          visitor.visitConstant(18, paramInt, 2, constant, i);
          return 2;
        case 17:
          i = this.bytes.getShort(paramInt + 1);
          visitor.visitConstant(18, paramInt, 3, (Constant)CstInteger.make(i), i);
          return 3;
        case 16:
          i = this.bytes.getByte(paramInt + 1);
          visitor.visitConstant(18, paramInt, 2, (Constant)CstInteger.make(i), i);
          return 2;
        case 15:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstDouble.VALUE_1, 0);
          return 1;
        case 14:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstDouble.VALUE_0, 0);
          return 1;
        case 13:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstFloat.VALUE_2, 0);
          return 1;
        case 12:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstFloat.VALUE_1, 0);
          return 1;
        case 11:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstFloat.VALUE_0, 0);
          return 1;
        case 10:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstLong.VALUE_1, 0);
          return 1;
        case 9:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstLong.VALUE_0, 0);
          return 1;
        case 8:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstInteger.VALUE_5, 5);
          return 1;
        case 7:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstInteger.VALUE_4, 4);
          return 1;
        case 6:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstInteger.VALUE_3, 3);
          return 1;
        case 5:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstInteger.VALUE_2, 2);
          return 1;
        case 4:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstInteger.VALUE_1, 1);
          return 1;
        case 3:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstInteger.VALUE_0, 0);
          return 1;
        case 2:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstInteger.VALUE_M1, -1);
          return 1;
        case 1:
          visitor.visitConstant(18, paramInt, 1, (Constant)CstKnownNull.THE_ONE, 0);
          return 1;
        case 0:
          break;
      } 
      visitor.visitNoArgs(i, paramInt, 1, Type.VOID);
      return 1;
    } catch (SimException simException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("...at bytecode offset ");
      stringBuilder.append(Hex.u4(paramInt));
      simException.addContext(stringBuilder.toString());
      throw simException;
    } catch (RuntimeException runtimeException) {
      SimException simException = new SimException(runtimeException);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("...at bytecode offset ");
      stringBuilder.append(Hex.u4(paramInt));
      simException.addContext(stringBuilder.toString());
      throw simException;
    } 
  }
  
  public void processWorkSet(int[] paramArrayOfint, Visitor paramVisitor) {
    if (paramVisitor != null)
      while (true) {
        int i = Bits.findFirst(paramArrayOfint, 0);
        if (i < 0)
          return; 
        Bits.clear(paramArrayOfint, i);
        parseInstruction(i, paramVisitor);
        paramVisitor.setPreviousOffset(i);
      }  
    throw new NullPointerException("visitor == null");
  }
  
  public int size() {
    return this.bytes.size();
  }
  
  public static class BaseVisitor implements Visitor {
    private int previousOffset = -1;
    
    public int getPreviousOffset() {
      return this.previousOffset;
    }
    
    public void setPreviousOffset(int param1Int) {
      this.previousOffset = param1Int;
    }
    
    public void visitBranch(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {}
    
    public void visitConstant(int param1Int1, int param1Int2, int param1Int3, Constant param1Constant, int param1Int4) {}
    
    public void visitInvalid(int param1Int1, int param1Int2, int param1Int3) {}
    
    public void visitLocal(int param1Int1, int param1Int2, int param1Int3, int param1Int4, Type param1Type, int param1Int5) {}
    
    public void visitNewarray(int param1Int1, int param1Int2, CstType param1CstType, ArrayList<Constant> param1ArrayList) {}
    
    public void visitNoArgs(int param1Int1, int param1Int2, int param1Int3, Type param1Type) {}
    
    public void visitSwitch(int param1Int1, int param1Int2, int param1Int3, SwitchList param1SwitchList, int param1Int4) {}
  }
  
  class ConstantParserVisitor extends BaseVisitor {
    Constant cst;
    
    int length;
    
    int value;
    
    private void clear() {
      this.length = 0;
    }
    
    public int getPreviousOffset() {
      return -1;
    }
    
    public void setPreviousOffset(int param1Int) {}
    
    public void visitBranch(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      clear();
    }
    
    public void visitConstant(int param1Int1, int param1Int2, int param1Int3, Constant param1Constant, int param1Int4) {
      this.cst = param1Constant;
      this.length = param1Int3;
      this.value = param1Int4;
    }
    
    public void visitInvalid(int param1Int1, int param1Int2, int param1Int3) {
      clear();
    }
    
    public void visitLocal(int param1Int1, int param1Int2, int param1Int3, int param1Int4, Type param1Type, int param1Int5) {
      clear();
    }
    
    public void visitNewarray(int param1Int1, int param1Int2, CstType param1CstType, ArrayList<Constant> param1ArrayList) {
      clear();
    }
    
    public void visitNoArgs(int param1Int1, int param1Int2, int param1Int3, Type param1Type) {
      clear();
    }
    
    public void visitSwitch(int param1Int1, int param1Int2, int param1Int3, SwitchList param1SwitchList, int param1Int4) {
      clear();
    }
  }
  
  public static interface Visitor {
    int getPreviousOffset();
    
    void setPreviousOffset(int param1Int);
    
    void visitBranch(int param1Int1, int param1Int2, int param1Int3, int param1Int4);
    
    void visitConstant(int param1Int1, int param1Int2, int param1Int3, Constant param1Constant, int param1Int4);
    
    void visitInvalid(int param1Int1, int param1Int2, int param1Int3);
    
    void visitLocal(int param1Int1, int param1Int2, int param1Int3, int param1Int4, Type param1Type, int param1Int5);
    
    void visitNewarray(int param1Int1, int param1Int2, CstType param1CstType, ArrayList<Constant> param1ArrayList);
    
    void visitNoArgs(int param1Int1, int param1Int2, int param1Int3, Type param1Type);
    
    void visitSwitch(int param1Int1, int param1Int2, int param1Int3, SwitchList param1SwitchList, int param1Int4);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\BytecodeArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */