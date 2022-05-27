package com.android.dx.cf.code;

import com.android.dx.cf.iface.Method;
import com.android.dx.cf.iface.MethodList;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.InvokePolymorphicInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.code.TranslationAdvice;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import java.util.ArrayList;

final class RopperMachine extends ValueAwareMachine {
  private static final CstType ARRAY_REFLECT_TYPE;
  
  private static final CstMethodRef MULTIANEWARRAY_METHOD;
  
  private final TranslationAdvice advice;
  
  private boolean blockCanThrow;
  
  private TypeList catches;
  
  private boolean catchesUsed;
  
  private int extraBlockCount;
  
  private boolean hasJsr;
  
  private final ArrayList<Insn> insns;
  
  private final int maxLocals;
  
  private final ConcreteMethod method;
  
  private final MethodList methods;
  
  private int primarySuccessorIndex;
  
  private ReturnAddress returnAddress;
  
  private Rop returnOp;
  
  private SourcePosition returnPosition;
  
  private boolean returns;
  
  private final Ropper ropper;
  
  static {
    CstType cstType = new CstType(Type.internClassName("java/lang/reflect/Array"));
    ARRAY_REFLECT_TYPE = cstType;
    MULTIANEWARRAY_METHOD = new CstMethodRef(cstType, new CstNat(new CstString("newInstance"), new CstString("(Ljava/lang/Class;[I)Ljava/lang/Object;")));
  }
  
  public RopperMachine(Ropper paramRopper, ConcreteMethod paramConcreteMethod, TranslationAdvice paramTranslationAdvice, MethodList paramMethodList) {
    super(paramConcreteMethod.getEffectiveDescriptor());
    if (paramMethodList != null) {
      if (paramRopper != null) {
        if (paramTranslationAdvice != null) {
          this.ropper = paramRopper;
          this.method = paramConcreteMethod;
          this.methods = paramMethodList;
          this.advice = paramTranslationAdvice;
          this.maxLocals = paramConcreteMethod.getMaxLocals();
          this.insns = new ArrayList<Insn>(25);
          this.catches = null;
          this.catchesUsed = false;
          this.returns = false;
          this.primarySuccessorIndex = -1;
          this.extraBlockCount = 0;
          this.blockCanThrow = false;
          this.returnOp = null;
          this.returnPosition = null;
          return;
        } 
        throw new NullPointerException("advice == null");
      } 
      throw new NullPointerException("ropper == null");
    } 
    throw new NullPointerException("methods == null");
  }
  
  private RegisterSpecList getSources(int paramInt1, int paramInt2) {
    RegisterSpecList registerSpecList;
    int i = argCount();
    if (i == 0)
      return RegisterSpecList.EMPTY; 
    int j = getLocalIndex();
    if (j >= 0) {
      registerSpecList = new RegisterSpecList(1);
      registerSpecList.set(0, RegisterSpec.make(j, arg(0)));
    } else {
      registerSpecList = new RegisterSpecList(i);
      for (j = 0; j < i; j++) {
        RegisterSpec registerSpec = RegisterSpec.make(paramInt2, arg(j));
        registerSpecList.set(j, registerSpec);
        paramInt2 += registerSpec.getCategory();
      } 
      if (paramInt1 != 79) {
        if (paramInt1 == 181)
          if (i == 2) {
            RegisterSpec registerSpec = registerSpecList.get(0);
            registerSpecList.set(0, registerSpecList.get(1));
            registerSpecList.set(1, registerSpec);
          } else {
            throw new RuntimeException("shouldn't happen");
          }  
      } else {
        if (i == 3) {
          RegisterSpec registerSpec2 = registerSpecList.get(0);
          RegisterSpec registerSpec1 = registerSpecList.get(1);
          registerSpecList.set(0, registerSpecList.get(2));
          registerSpecList.set(1, registerSpec2);
          registerSpecList.set(2, registerSpec1);
          registerSpecList.setImmutable();
          return registerSpecList;
        } 
        throw new RuntimeException("shouldn't happen");
      } 
    } 
    registerSpecList.setImmutable();
    return registerSpecList;
  }
  
  private int jopToRopOpcode(int paramInt, Constant paramConstant) {
    if (paramInt != 0)
      if (paramInt != 20)
        if (paramInt != 21) {
          if (paramInt != 171) {
            if (paramInt != 172) {
              if (paramInt != 198)
                if (paramInt != 199) {
                  CstMethodRef cstMethodRef;
                  switch (paramInt) {
                    default:
                      switch (paramInt) {
                        default:
                          switch (paramInt) {
                            default:
                              throw new RuntimeException("shouldn't happen");
                            case 195:
                              return 37;
                            case 194:
                              return 36;
                            case 193:
                              return 44;
                            case 192:
                              return 43;
                            case 191:
                              return 35;
                            case 190:
                              return 34;
                            case 188:
                            case 189:
                              return 41;
                            case 187:
                              return 40;
                            case 186:
                              return 59;
                            case 185:
                              return 53;
                            case 184:
                              return 49;
                            case 183:
                              cstMethodRef = (CstMethodRef)paramConstant;
                              return (cstMethodRef.isInstanceInit() || cstMethodRef.getDefiningClass().equals(this.method.getDefiningClass())) ? 52 : 51;
                            case 182:
                              cstMethodRef = cstMethodRef;
                              if (cstMethodRef.getDefiningClass().equals(this.method.getDefiningClass()))
                                for (paramInt = 0; paramInt < this.methods.size(); paramInt++) {
                                  Method method = this.methods.get(paramInt);
                                  if (AccessFlags.isPrivate(method.getAccessFlags()) && cstMethodRef.getNat().equals(method.getNat()))
                                    return 52; 
                                }  
                              return cstMethodRef.isSignaturePolymorphic() ? 58 : 50;
                            case 181:
                              return 47;
                            case 180:
                              return 45;
                            case 179:
                              return 48;
                            case 178:
                              return 46;
                            case 177:
                              break;
                          } 
                          return 33;
                        case 167:
                          return 6;
                        case 158:
                        case 164:
                          return 11;
                        case 157:
                        case 163:
                          return 12;
                        case 156:
                        case 162:
                          return 10;
                        case 155:
                        case 161:
                          return 9;
                        case 150:
                        case 152:
                          return 28;
                        case 148:
                        case 149:
                        case 151:
                          return 27;
                        case 147:
                          return 32;
                        case 146:
                          return 31;
                        case 145:
                          return 30;
                        case 133:
                        case 134:
                        case 135:
                        case 136:
                        case 137:
                        case 138:
                        case 139:
                        case 140:
                        case 141:
                        case 142:
                        case 143:
                        case 144:
                          return 29;
                        case 132:
                          return 14;
                        case 154:
                        case 160:
                        case 166:
                          return 8;
                        case 153:
                        case 159:
                        case 165:
                          break;
                      } 
                      return 7;
                    case 130:
                      return 22;
                    case 128:
                      return 21;
                    case 126:
                      return 20;
                    case 124:
                      return 25;
                    case 122:
                      return 24;
                    case 120:
                      return 23;
                    case 116:
                      return 19;
                    case 112:
                      return 18;
                    case 108:
                      return 17;
                    case 104:
                      return 16;
                    case 100:
                      return 15;
                    case 96:
                    
                    case 79:
                      return 39;
                    case 46:
                      return 38;
                    case 54:
                      return 2;
                    case 18:
                      return 5;
                    case 0:
                      break;
                  } 
                  return 1;
                }  
              return 7;
            } 
            return 33;
          } 
          return 13;
        }   
    return 1;
  }
  
  private Insn makeInvokePolymorphicInsn(Rop paramRop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList, TypeList paramTypeList, Constant paramConstant) {
    return (Insn)new InvokePolymorphicInsn(paramRop, paramSourcePosition, paramRegisterSpecList, paramTypeList, (CstMethodRef)paramConstant);
  }
  
  private void updateReturnOp(Rop paramRop, SourcePosition paramSourcePosition) {
    if (paramRop != null) {
      if (paramSourcePosition != null) {
        Rop rop = this.returnOp;
        if (rop == null) {
          this.returnOp = paramRop;
          this.returnPosition = paramSourcePosition;
        } else {
          if (rop == paramRop) {
            if (paramSourcePosition.getLine() > this.returnPosition.getLine())
              this.returnPosition = paramSourcePosition; 
            return;
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("return op mismatch: ");
          stringBuilder.append(paramRop);
          stringBuilder.append(", ");
          stringBuilder.append(this.returnOp);
          throw new SimException(stringBuilder.toString());
        } 
        return;
      } 
      throw new NullPointerException("pos == null");
    } 
    throw new NullPointerException("op == null");
  }
  
  public boolean canThrow() {
    return this.blockCanThrow;
  }
  
  public int getExtraBlockCount() {
    return this.extraBlockCount;
  }
  
  public ArrayList<Insn> getInsns() {
    return this.insns;
  }
  
  public int getPrimarySuccessorIndex() {
    return this.primarySuccessorIndex;
  }
  
  public ReturnAddress getReturnAddress() {
    return this.returnAddress;
  }
  
  public Rop getReturnOp() {
    return this.returnOp;
  }
  
  public SourcePosition getReturnPosition() {
    return this.returnPosition;
  }
  
  public boolean hasJsr() {
    return this.hasJsr;
  }
  
  public boolean hasRet() {
    boolean bool;
    if (this.returnAddress != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean returns() {
    return this.returns;
  }
  
  public void run(Frame paramFrame, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_3
    //   1: istore #4
    //   3: aload_0
    //   4: getfield maxLocals : I
    //   7: aload_1
    //   8: invokevirtual getStack : ()Lcom/android/dx/cf/code/ExecutionStack;
    //   11: invokevirtual size : ()I
    //   14: iadd
    //   15: istore #5
    //   17: aload_0
    //   18: iload #4
    //   20: iload #5
    //   22: invokespecial getSources : (II)Lcom/android/dx/rop/code/RegisterSpecList;
    //   25: astore #6
    //   27: aload #6
    //   29: invokevirtual size : ()I
    //   32: istore #7
    //   34: aload_0
    //   35: aload_1
    //   36: iload_2
    //   37: iload_3
    //   38: invokespecial run : (Lcom/android/dx/cf/code/Frame;II)V
    //   41: aload_0
    //   42: getfield method : Lcom/android/dx/cf/code/ConcreteMethod;
    //   45: iload_2
    //   46: invokevirtual makeSourcePosistion : (I)Lcom/android/dx/rop/code/SourcePosition;
    //   49: astore #8
    //   51: iload #4
    //   53: bipush #54
    //   55: if_icmpne -> 64
    //   58: iconst_1
    //   59: istore #9
    //   61: goto -> 67
    //   64: iconst_0
    //   65: istore #9
    //   67: aload_0
    //   68: iload #9
    //   70: invokevirtual getLocalTarget : (Z)Lcom/android/dx/rop/code/RegisterSpec;
    //   73: astore_1
    //   74: aload_0
    //   75: invokevirtual resultCount : ()I
    //   78: istore_2
    //   79: iload_2
    //   80: ifne -> 103
    //   83: iload #4
    //   85: bipush #87
    //   87: if_icmpeq -> 102
    //   90: iload #4
    //   92: bipush #88
    //   94: if_icmpeq -> 102
    //   97: aconst_null
    //   98: astore_1
    //   99: goto -> 126
    //   102: return
    //   103: aload_1
    //   104: ifnull -> 110
    //   107: goto -> 126
    //   110: iload_2
    //   111: iconst_1
    //   112: if_icmpne -> 1401
    //   115: iload #5
    //   117: aload_0
    //   118: iconst_0
    //   119: invokevirtual result : (I)Lcom/android/dx/rop/type/TypeBearer;
    //   122: invokestatic make : (ILcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/RegisterSpec;
    //   125: astore_1
    //   126: aload_1
    //   127: ifnull -> 136
    //   130: aload_1
    //   131: astore #10
    //   133: goto -> 141
    //   136: getstatic com/android/dx/rop/type/Type.VOID : Lcom/android/dx/rop/type/Type;
    //   139: astore #10
    //   141: aload_0
    //   142: invokevirtual getAuxCst : ()Lcom/android/dx/rop/cst/Constant;
    //   145: astore #11
    //   147: iload #4
    //   149: sipush #197
    //   152: if_icmpne -> 518
    //   155: aload_0
    //   156: iconst_1
    //   157: putfield blockCanThrow : Z
    //   160: aload_0
    //   161: bipush #6
    //   163: putfield extraBlockCount : I
    //   166: aload_1
    //   167: invokevirtual getNextReg : ()I
    //   170: getstatic com/android/dx/rop/type/Type.INT_ARRAY : Lcom/android/dx/rop/type/Type;
    //   173: invokestatic make : (ILcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/RegisterSpec;
    //   176: astore #12
    //   178: new com/android/dx/rop/code/ThrowingCstInsn
    //   181: dup
    //   182: getstatic com/android/dx/rop/type/Type.INT_ARRAY : Lcom/android/dx/rop/type/Type;
    //   185: iload #7
    //   187: invokestatic opFilledNewArray : (Lcom/android/dx/rop/type/TypeBearer;I)Lcom/android/dx/rop/code/Rop;
    //   190: aload #8
    //   192: aload #6
    //   194: aload_0
    //   195: getfield catches : Lcom/android/dx/rop/type/TypeList;
    //   198: getstatic com/android/dx/rop/cst/CstType.INT_ARRAY : Lcom/android/dx/rop/cst/CstType;
    //   201: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpecList;Lcom/android/dx/rop/type/TypeList;Lcom/android/dx/rop/cst/Constant;)V
    //   204: astore #6
    //   206: aload_0
    //   207: getfield insns : Ljava/util/ArrayList;
    //   210: aload #6
    //   212: invokevirtual add : (Ljava/lang/Object;)Z
    //   215: pop
    //   216: new com/android/dx/rop/code/PlainInsn
    //   219: dup
    //   220: getstatic com/android/dx/rop/type/Type.INT_ARRAY : Lcom/android/dx/rop/type/Type;
    //   223: invokestatic opMoveResult : (Lcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/Rop;
    //   226: aload #8
    //   228: aload #12
    //   230: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   233: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;)V
    //   236: astore #6
    //   238: aload_0
    //   239: getfield insns : Ljava/util/ArrayList;
    //   242: aload #6
    //   244: invokevirtual add : (Ljava/lang/Object;)Z
    //   247: pop
    //   248: aload #11
    //   250: checkcast com/android/dx/rop/cst/CstType
    //   253: invokevirtual getClassType : ()Lcom/android/dx/rop/type/Type;
    //   256: astore #6
    //   258: iconst_0
    //   259: istore_2
    //   260: iload_2
    //   261: iload #7
    //   263: if_icmpge -> 279
    //   266: aload #6
    //   268: invokevirtual getComponentType : ()Lcom/android/dx/rop/type/Type;
    //   271: astore #6
    //   273: iinc #2, 1
    //   276: goto -> 260
    //   279: aload_1
    //   280: invokevirtual getReg : ()I
    //   283: getstatic com/android/dx/rop/type/Type.CLASS : Lcom/android/dx/rop/type/Type;
    //   286: invokestatic make : (ILcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/RegisterSpec;
    //   289: astore #13
    //   291: aload #6
    //   293: invokevirtual isPrimitive : ()Z
    //   296: ifeq -> 332
    //   299: aload #6
    //   301: invokestatic forPrimitiveType : (Lcom/android/dx/rop/type/Type;)Lcom/android/dx/rop/cst/CstFieldRef;
    //   304: astore #6
    //   306: new com/android/dx/rop/code/ThrowingCstInsn
    //   309: dup
    //   310: getstatic com/android/dx/rop/code/Rops.GET_STATIC_OBJECT : Lcom/android/dx/rop/code/Rop;
    //   313: aload #8
    //   315: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   318: aload_0
    //   319: getfield catches : Lcom/android/dx/rop/type/TypeList;
    //   322: aload #6
    //   324: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpecList;Lcom/android/dx/rop/type/TypeList;Lcom/android/dx/rop/cst/Constant;)V
    //   327: astore #6
    //   329: goto -> 362
    //   332: new com/android/dx/rop/code/ThrowingCstInsn
    //   335: dup
    //   336: getstatic com/android/dx/rop/code/Rops.CONST_OBJECT : Lcom/android/dx/rop/code/Rop;
    //   339: aload #8
    //   341: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   344: aload_0
    //   345: getfield catches : Lcom/android/dx/rop/type/TypeList;
    //   348: new com/android/dx/rop/cst/CstType
    //   351: dup
    //   352: aload #6
    //   354: invokespecial <init> : (Lcom/android/dx/rop/type/Type;)V
    //   357: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpecList;Lcom/android/dx/rop/type/TypeList;Lcom/android/dx/rop/cst/Constant;)V
    //   360: astore #6
    //   362: aload_0
    //   363: getfield insns : Ljava/util/ArrayList;
    //   366: aload #6
    //   368: invokevirtual add : (Ljava/lang/Object;)Z
    //   371: pop
    //   372: new com/android/dx/rop/code/PlainInsn
    //   375: dup
    //   376: aload #13
    //   378: invokevirtual getType : ()Lcom/android/dx/rop/type/Type;
    //   381: invokestatic opMoveResultPseudo : (Lcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/Rop;
    //   384: aload #8
    //   386: aload #13
    //   388: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   391: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;)V
    //   394: astore #6
    //   396: aload_0
    //   397: getfield insns : Ljava/util/ArrayList;
    //   400: aload #6
    //   402: invokevirtual add : (Ljava/lang/Object;)Z
    //   405: pop
    //   406: aload_1
    //   407: invokevirtual getReg : ()I
    //   410: getstatic com/android/dx/rop/type/Type.OBJECT : Lcom/android/dx/rop/type/Type;
    //   413: invokestatic make : (ILcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/RegisterSpec;
    //   416: astore #6
    //   418: new com/android/dx/rop/code/ThrowingCstInsn
    //   421: dup
    //   422: getstatic com/android/dx/cf/code/RopperMachine.MULTIANEWARRAY_METHOD : Lcom/android/dx/rop/cst/CstMethodRef;
    //   425: invokevirtual getPrototype : ()Lcom/android/dx/rop/type/Prototype;
    //   428: invokestatic opInvokeStatic : (Lcom/android/dx/rop/type/Prototype;)Lcom/android/dx/rop/code/Rop;
    //   431: aload #8
    //   433: aload #13
    //   435: aload #12
    //   437: invokestatic make : (Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpec;)Lcom/android/dx/rop/code/RegisterSpecList;
    //   440: aload_0
    //   441: getfield catches : Lcom/android/dx/rop/type/TypeList;
    //   444: getstatic com/android/dx/cf/code/RopperMachine.MULTIANEWARRAY_METHOD : Lcom/android/dx/rop/cst/CstMethodRef;
    //   447: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpecList;Lcom/android/dx/rop/type/TypeList;Lcom/android/dx/rop/cst/Constant;)V
    //   450: astore #12
    //   452: aload_0
    //   453: getfield insns : Ljava/util/ArrayList;
    //   456: aload #12
    //   458: invokevirtual add : (Ljava/lang/Object;)Z
    //   461: pop
    //   462: new com/android/dx/rop/code/PlainInsn
    //   465: dup
    //   466: getstatic com/android/dx/cf/code/RopperMachine.MULTIANEWARRAY_METHOD : Lcom/android/dx/rop/cst/CstMethodRef;
    //   469: invokevirtual getPrototype : ()Lcom/android/dx/rop/type/Prototype;
    //   472: invokevirtual getReturnType : ()Lcom/android/dx/rop/type/Type;
    //   475: invokestatic opMoveResult : (Lcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/Rop;
    //   478: aload #8
    //   480: aload #6
    //   482: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   485: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;)V
    //   488: astore #12
    //   490: aload_0
    //   491: getfield insns : Ljava/util/ArrayList;
    //   494: aload #12
    //   496: invokevirtual add : (Ljava/lang/Object;)Z
    //   499: pop
    //   500: sipush #192
    //   503: istore_3
    //   504: aload #6
    //   506: invokestatic make : (Lcom/android/dx/rop/code/RegisterSpec;)Lcom/android/dx/rop/code/RegisterSpecList;
    //   509: astore #6
    //   511: aload #11
    //   513: astore #12
    //   515: goto -> 573
    //   518: iload #4
    //   520: sipush #168
    //   523: if_icmpne -> 532
    //   526: aload_0
    //   527: iconst_1
    //   528: putfield hasJsr : Z
    //   531: return
    //   532: iload #4
    //   534: istore_3
    //   535: aload #11
    //   537: astore #12
    //   539: iload #4
    //   541: sipush #169
    //   544: if_icmpne -> 573
    //   547: aload_0
    //   548: aload_0
    //   549: iconst_0
    //   550: invokevirtual arg : (I)Lcom/android/dx/rop/type/TypeBearer;
    //   553: checkcast com/android/dx/cf/code/ReturnAddress
    //   556: putfield returnAddress : Lcom/android/dx/cf/code/ReturnAddress;
    //   559: return
    //   560: astore_1
    //   561: new java/lang/RuntimeException
    //   564: dup
    //   565: ldc_w 'Argument to RET was not a ReturnAddress'
    //   568: aload_1
    //   569: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   572: athrow
    //   573: aload_0
    //   574: iload_3
    //   575: aload #12
    //   577: invokespecial jopToRopOpcode : (ILcom/android/dx/rop/cst/Constant;)I
    //   580: istore_2
    //   581: iload_2
    //   582: aload #10
    //   584: aload #6
    //   586: aload #12
    //   588: invokestatic ropFor : (ILcom/android/dx/rop/type/TypeBearer;Lcom/android/dx/rop/type/TypeList;Lcom/android/dx/rop/cst/Constant;)Lcom/android/dx/rop/code/Rop;
    //   591: astore #14
    //   593: aload_1
    //   594: ifnull -> 673
    //   597: aload #14
    //   599: invokevirtual isCallLike : ()Z
    //   602: ifeq -> 673
    //   605: aload_0
    //   606: aload_0
    //   607: getfield extraBlockCount : I
    //   610: iconst_1
    //   611: iadd
    //   612: putfield extraBlockCount : I
    //   615: aload #14
    //   617: invokevirtual getOpcode : ()I
    //   620: bipush #59
    //   622: if_icmpne -> 638
    //   625: aload #12
    //   627: checkcast com/android/dx/rop/cst/CstCallSiteRef
    //   630: invokevirtual getReturnType : ()Lcom/android/dx/rop/type/Type;
    //   633: astore #11
    //   635: goto -> 651
    //   638: aload #12
    //   640: checkcast com/android/dx/rop/cst/CstMethodRef
    //   643: invokevirtual getPrototype : ()Lcom/android/dx/rop/type/Prototype;
    //   646: invokevirtual getReturnType : ()Lcom/android/dx/rop/type/Type;
    //   649: astore #11
    //   651: new com/android/dx/rop/code/PlainInsn
    //   654: dup
    //   655: aload #11
    //   657: invokestatic opMoveResult : (Lcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/Rop;
    //   660: aload #8
    //   662: aload_1
    //   663: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   666: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;)V
    //   669: astore_1
    //   670: goto -> 716
    //   673: aload_1
    //   674: ifnull -> 725
    //   677: aload #14
    //   679: invokevirtual canThrow : ()Z
    //   682: ifeq -> 725
    //   685: aload_0
    //   686: aload_0
    //   687: getfield extraBlockCount : I
    //   690: iconst_1
    //   691: iadd
    //   692: putfield extraBlockCount : I
    //   695: new com/android/dx/rop/code/PlainInsn
    //   698: dup
    //   699: aload_1
    //   700: invokevirtual getTypeBearer : ()Lcom/android/dx/rop/type/TypeBearer;
    //   703: invokestatic opMoveResultPseudo : (Lcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/Rop;
    //   706: aload #8
    //   708: aload_1
    //   709: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   712: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;)V
    //   715: astore_1
    //   716: aconst_null
    //   717: astore #13
    //   719: aload_1
    //   720: astore #11
    //   722: goto -> 731
    //   725: aconst_null
    //   726: astore #11
    //   728: aload_1
    //   729: astore #13
    //   731: iload_2
    //   732: bipush #41
    //   734: if_icmpne -> 749
    //   737: aload #14
    //   739: invokevirtual getResult : ()Lcom/android/dx/rop/type/Type;
    //   742: invokestatic intern : (Lcom/android/dx/rop/type/Type;)Lcom/android/dx/rop/cst/CstType;
    //   745: astore_1
    //   746: goto -> 934
    //   749: aload #12
    //   751: astore_1
    //   752: aload #12
    //   754: ifnonnull -> 934
    //   757: aload #12
    //   759: astore_1
    //   760: iload #7
    //   762: iconst_2
    //   763: if_icmpne -> 934
    //   766: aload #6
    //   768: iconst_0
    //   769: invokevirtual get : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   772: invokevirtual getTypeBearer : ()Lcom/android/dx/rop/type/TypeBearer;
    //   775: astore #15
    //   777: aload #6
    //   779: iconst_1
    //   780: invokevirtual get : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   783: invokevirtual getTypeBearer : ()Lcom/android/dx/rop/type/TypeBearer;
    //   786: astore #16
    //   788: aload #16
    //   790: invokeinterface isConstant : ()Z
    //   795: ifne -> 811
    //   798: aload #12
    //   800: astore_1
    //   801: aload #15
    //   803: invokeinterface isConstant : ()Z
    //   808: ifeq -> 934
    //   811: aload #12
    //   813: astore_1
    //   814: aload_0
    //   815: getfield advice : Lcom/android/dx/rop/code/TranslationAdvice;
    //   818: aload #14
    //   820: aload #6
    //   822: iconst_0
    //   823: invokevirtual get : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   826: aload #6
    //   828: iconst_1
    //   829: invokevirtual get : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   832: invokeinterface hasConstantOperation : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpec;)Z
    //   837: ifeq -> 934
    //   840: aload #16
    //   842: invokeinterface isConstant : ()Z
    //   847: ifeq -> 900
    //   850: aload #16
    //   852: checkcast com/android/dx/rop/cst/Constant
    //   855: astore_1
    //   856: aload #6
    //   858: invokevirtual withoutLast : ()Lcom/android/dx/rop/code/RegisterSpecList;
    //   861: astore #12
    //   863: aload #12
    //   865: astore #6
    //   867: aload #14
    //   869: invokevirtual getOpcode : ()I
    //   872: bipush #15
    //   874: if_icmpne -> 913
    //   877: aload #16
    //   879: checkcast com/android/dx/rop/cst/CstInteger
    //   882: invokevirtual getValue : ()I
    //   885: ineg
    //   886: invokestatic make : (I)Lcom/android/dx/rop/cst/CstInteger;
    //   889: astore_1
    //   890: bipush #14
    //   892: istore_2
    //   893: aload #12
    //   895: astore #6
    //   897: goto -> 913
    //   900: aload #15
    //   902: checkcast com/android/dx/rop/cst/Constant
    //   905: astore_1
    //   906: aload #6
    //   908: invokevirtual withoutFirst : ()Lcom/android/dx/rop/code/RegisterSpecList;
    //   911: astore #6
    //   913: iload_2
    //   914: aload #10
    //   916: aload #6
    //   918: aload_1
    //   919: invokestatic ropFor : (ILcom/android/dx/rop/type/TypeBearer;Lcom/android/dx/rop/type/TypeList;Lcom/android/dx/rop/cst/Constant;)Lcom/android/dx/rop/code/Rop;
    //   922: astore #12
    //   924: aload #6
    //   926: astore #10
    //   928: aload_1
    //   929: astore #6
    //   931: goto -> 945
    //   934: aload #14
    //   936: astore #12
    //   938: aload #6
    //   940: astore #10
    //   942: aload_1
    //   943: astore #6
    //   945: aload_0
    //   946: invokevirtual getAuxCases : ()Lcom/android/dx/cf/code/SwitchList;
    //   949: astore_1
    //   950: aload_0
    //   951: invokevirtual getInitValues : ()Ljava/util/ArrayList;
    //   954: astore #14
    //   956: aload #12
    //   958: invokevirtual canThrow : ()Z
    //   961: istore #9
    //   963: aload_0
    //   964: aload_0
    //   965: getfield blockCanThrow : Z
    //   968: iload #9
    //   970: ior
    //   971: putfield blockCanThrow : Z
    //   974: aload_1
    //   975: ifnull -> 1046
    //   978: aload_1
    //   979: invokevirtual size : ()I
    //   982: ifne -> 1010
    //   985: new com/android/dx/rop/code/PlainInsn
    //   988: dup
    //   989: getstatic com/android/dx/rop/code/Rops.GOTO : Lcom/android/dx/rop/code/Rop;
    //   992: aload #8
    //   994: aconst_null
    //   995: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   998: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;)V
    //   1001: astore_1
    //   1002: aload_0
    //   1003: iconst_0
    //   1004: putfield primarySuccessorIndex : I
    //   1007: goto -> 1043
    //   1010: aload_1
    //   1011: invokevirtual getValues : ()Lcom/android/dx/util/IntList;
    //   1014: astore #16
    //   1016: new com/android/dx/rop/code/SwitchInsn
    //   1019: dup
    //   1020: aload #12
    //   1022: aload #8
    //   1024: aload #13
    //   1026: aload #10
    //   1028: aload #16
    //   1030: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;Lcom/android/dx/util/IntList;)V
    //   1033: astore_1
    //   1034: aload_0
    //   1035: aload #16
    //   1037: invokevirtual size : ()I
    //   1040: putfield primarySuccessorIndex : I
    //   1043: goto -> 1327
    //   1046: iload_2
    //   1047: bipush #33
    //   1049: if_icmpne -> 1150
    //   1052: aload #10
    //   1054: invokevirtual size : ()I
    //   1057: ifeq -> 1112
    //   1060: aload #10
    //   1062: iconst_0
    //   1063: invokevirtual get : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   1066: astore_1
    //   1067: aload_1
    //   1068: invokevirtual getTypeBearer : ()Lcom/android/dx/rop/type/TypeBearer;
    //   1071: astore #10
    //   1073: aload_1
    //   1074: invokevirtual getReg : ()I
    //   1077: ifeq -> 1112
    //   1080: aload_0
    //   1081: getfield insns : Ljava/util/ArrayList;
    //   1084: new com/android/dx/rop/code/PlainInsn
    //   1087: dup
    //   1088: aload #10
    //   1090: invokestatic opMove : (Lcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/Rop;
    //   1093: aload #8
    //   1095: iconst_0
    //   1096: aload #10
    //   1098: invokestatic make : (ILcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/RegisterSpec;
    //   1101: aload_1
    //   1102: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpec;)V
    //   1105: invokevirtual add : (Ljava/lang/Object;)Z
    //   1108: pop
    //   1109: goto -> 1112
    //   1112: new com/android/dx/rop/code/PlainInsn
    //   1115: dup
    //   1116: getstatic com/android/dx/rop/code/Rops.GOTO : Lcom/android/dx/rop/code/Rop;
    //   1119: aload #8
    //   1121: aconst_null
    //   1122: getstatic com/android/dx/rop/code/RegisterSpecList.EMPTY : Lcom/android/dx/rop/code/RegisterSpecList;
    //   1125: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;)V
    //   1128: astore_1
    //   1129: aload_0
    //   1130: iconst_0
    //   1131: putfield primarySuccessorIndex : I
    //   1134: aload_0
    //   1135: aload #12
    //   1137: aload #8
    //   1139: invokespecial updateReturnOp : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;)V
    //   1142: aload_0
    //   1143: iconst_1
    //   1144: putfield returns : Z
    //   1147: goto -> 1043
    //   1150: aload #6
    //   1152: ifnull -> 1252
    //   1155: iload #9
    //   1157: ifeq -> 1231
    //   1160: aload #12
    //   1162: invokevirtual getOpcode : ()I
    //   1165: bipush #58
    //   1167: if_icmpne -> 1190
    //   1170: aload_0
    //   1171: aload #12
    //   1173: aload #8
    //   1175: aload #10
    //   1177: aload_0
    //   1178: getfield catches : Lcom/android/dx/rop/type/TypeList;
    //   1181: aload #6
    //   1183: invokespecial makeInvokePolymorphicInsn : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpecList;Lcom/android/dx/rop/type/TypeList;Lcom/android/dx/rop/cst/Constant;)Lcom/android/dx/rop/code/Insn;
    //   1186: astore_1
    //   1187: goto -> 1210
    //   1190: new com/android/dx/rop/code/ThrowingCstInsn
    //   1193: dup
    //   1194: aload #12
    //   1196: aload #8
    //   1198: aload #10
    //   1200: aload_0
    //   1201: getfield catches : Lcom/android/dx/rop/type/TypeList;
    //   1204: aload #6
    //   1206: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpecList;Lcom/android/dx/rop/type/TypeList;Lcom/android/dx/rop/cst/Constant;)V
    //   1209: astore_1
    //   1210: aload_0
    //   1211: iconst_1
    //   1212: putfield catchesUsed : Z
    //   1215: aload_0
    //   1216: aload_0
    //   1217: getfield catches : Lcom/android/dx/rop/type/TypeList;
    //   1220: invokeinterface size : ()I
    //   1225: putfield primarySuccessorIndex : I
    //   1228: goto -> 1249
    //   1231: new com/android/dx/rop/code/PlainCstInsn
    //   1234: dup
    //   1235: aload #12
    //   1237: aload #8
    //   1239: aload #13
    //   1241: aload #10
    //   1243: aload #6
    //   1245: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;Lcom/android/dx/rop/cst/Constant;)V
    //   1248: astore_1
    //   1249: goto -> 1327
    //   1252: iload #9
    //   1254: ifeq -> 1311
    //   1257: new com/android/dx/rop/code/ThrowingInsn
    //   1260: dup
    //   1261: aload #12
    //   1263: aload #8
    //   1265: aload #10
    //   1267: aload_0
    //   1268: getfield catches : Lcom/android/dx/rop/type/TypeList;
    //   1271: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpecList;Lcom/android/dx/rop/type/TypeList;)V
    //   1274: astore_1
    //   1275: aload_0
    //   1276: iconst_1
    //   1277: putfield catchesUsed : Z
    //   1280: iload_3
    //   1281: sipush #191
    //   1284: if_icmpne -> 1295
    //   1287: aload_0
    //   1288: iconst_m1
    //   1289: putfield primarySuccessorIndex : I
    //   1292: goto -> 1249
    //   1295: aload_0
    //   1296: aload_0
    //   1297: getfield catches : Lcom/android/dx/rop/type/TypeList;
    //   1300: invokeinterface size : ()I
    //   1305: putfield primarySuccessorIndex : I
    //   1308: goto -> 1249
    //   1311: new com/android/dx/rop/code/PlainInsn
    //   1314: dup
    //   1315: aload #12
    //   1317: aload #8
    //   1319: aload #13
    //   1321: aload #10
    //   1323: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpecList;)V
    //   1326: astore_1
    //   1327: aload_0
    //   1328: getfield insns : Ljava/util/ArrayList;
    //   1331: aload_1
    //   1332: invokevirtual add : (Ljava/lang/Object;)Z
    //   1335: pop
    //   1336: aload #11
    //   1338: ifnull -> 1351
    //   1341: aload_0
    //   1342: getfield insns : Ljava/util/ArrayList;
    //   1345: aload #11
    //   1347: invokevirtual add : (Ljava/lang/Object;)Z
    //   1350: pop
    //   1351: aload #14
    //   1353: ifnull -> 1400
    //   1356: aload_0
    //   1357: aload_0
    //   1358: getfield extraBlockCount : I
    //   1361: iconst_1
    //   1362: iadd
    //   1363: putfield extraBlockCount : I
    //   1366: new com/android/dx/rop/code/FillArrayDataInsn
    //   1369: dup
    //   1370: getstatic com/android/dx/rop/code/Rops.FILL_ARRAY_DATA : Lcom/android/dx/rop/code/Rop;
    //   1373: aload #8
    //   1375: aload #11
    //   1377: invokevirtual getResult : ()Lcom/android/dx/rop/code/RegisterSpec;
    //   1380: invokestatic make : (Lcom/android/dx/rop/code/RegisterSpec;)Lcom/android/dx/rop/code/RegisterSpecList;
    //   1383: aload #14
    //   1385: aload #6
    //   1387: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpecList;Ljava/util/ArrayList;Lcom/android/dx/rop/cst/Constant;)V
    //   1390: astore_1
    //   1391: aload_0
    //   1392: getfield insns : Ljava/util/ArrayList;
    //   1395: aload_1
    //   1396: invokevirtual add : (Ljava/lang/Object;)Z
    //   1399: pop
    //   1400: return
    //   1401: iconst_0
    //   1402: istore_2
    //   1403: aload_0
    //   1404: getfield ropper : Lcom/android/dx/cf/code/Ropper;
    //   1407: invokevirtual getFirstTempStackReg : ()I
    //   1410: istore_3
    //   1411: iload #7
    //   1413: anewarray com/android/dx/rop/code/RegisterSpec
    //   1416: astore_1
    //   1417: iload_2
    //   1418: iload #7
    //   1420: if_icmpge -> 1491
    //   1423: aload #6
    //   1425: iload_2
    //   1426: invokevirtual get : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   1429: astore #11
    //   1431: aload #11
    //   1433: invokevirtual getTypeBearer : ()Lcom/android/dx/rop/type/TypeBearer;
    //   1436: astore #12
    //   1438: aload #11
    //   1440: iload_3
    //   1441: invokevirtual withReg : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   1444: astore #10
    //   1446: aload_0
    //   1447: getfield insns : Ljava/util/ArrayList;
    //   1450: new com/android/dx/rop/code/PlainInsn
    //   1453: dup
    //   1454: aload #12
    //   1456: invokestatic opMove : (Lcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/Rop;
    //   1459: aload #8
    //   1461: aload #10
    //   1463: aload #11
    //   1465: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpec;)V
    //   1468: invokevirtual add : (Ljava/lang/Object;)Z
    //   1471: pop
    //   1472: aload_1
    //   1473: iload_2
    //   1474: aload #10
    //   1476: aastore
    //   1477: iload_3
    //   1478: aload #11
    //   1480: invokevirtual getCategory : ()I
    //   1483: iadd
    //   1484: istore_3
    //   1485: iinc #2, 1
    //   1488: goto -> 1417
    //   1491: aload_0
    //   1492: invokevirtual getAuxInt : ()I
    //   1495: istore_2
    //   1496: iload #5
    //   1498: istore_3
    //   1499: iload_2
    //   1500: ifeq -> 1570
    //   1503: aload_1
    //   1504: iload_2
    //   1505: bipush #15
    //   1507: iand
    //   1508: iconst_1
    //   1509: isub
    //   1510: aaload
    //   1511: astore #11
    //   1513: aload #11
    //   1515: invokevirtual getTypeBearer : ()Lcom/android/dx/rop/type/TypeBearer;
    //   1518: astore #6
    //   1520: aload_0
    //   1521: getfield insns : Ljava/util/ArrayList;
    //   1524: new com/android/dx/rop/code/PlainInsn
    //   1527: dup
    //   1528: aload #6
    //   1530: invokestatic opMove : (Lcom/android/dx/rop/type/TypeBearer;)Lcom/android/dx/rop/code/Rop;
    //   1533: aload #8
    //   1535: aload #11
    //   1537: iload_3
    //   1538: invokevirtual withReg : (I)Lcom/android/dx/rop/code/RegisterSpec;
    //   1541: aload #11
    //   1543: invokespecial <init> : (Lcom/android/dx/rop/code/Rop;Lcom/android/dx/rop/code/SourcePosition;Lcom/android/dx/rop/code/RegisterSpec;Lcom/android/dx/rop/code/RegisterSpec;)V
    //   1546: invokevirtual add : (Ljava/lang/Object;)Z
    //   1549: pop
    //   1550: iload_3
    //   1551: aload #6
    //   1553: invokeinterface getType : ()Lcom/android/dx/rop/type/Type;
    //   1558: invokevirtual getCategory : ()I
    //   1561: iadd
    //   1562: istore_3
    //   1563: iload_2
    //   1564: iconst_4
    //   1565: ishr
    //   1566: istore_2
    //   1567: goto -> 1499
    //   1570: return
    // Exception table:
    //   from	to	target	type
    //   547	559	560	java/lang/ClassCastException
  }
  
  public void startBlock(TypeList paramTypeList) {
    this.catches = paramTypeList;
    this.insns.clear();
    this.catchesUsed = false;
    this.returns = false;
    this.primarySuccessorIndex = 0;
    this.extraBlockCount = 0;
    this.blockCanThrow = false;
    this.hasJsr = false;
    this.returnAddress = null;
  }
  
  public boolean wereCatchesUsed() {
    return this.catchesUsed;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\RopperMachine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */