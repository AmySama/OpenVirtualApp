package com.android.dx.ssa.back;

import com.android.dx.rop.code.CstInsn;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.ssa.NormalSsaInsn;
import com.android.dx.ssa.RegisterMapper;
import com.android.dx.ssa.SsaMethod;
import java.util.BitSet;

public class FirstFitAllocator extends RegisterAllocator {
  private static final boolean PRESLOT_PARAMS = true;
  
  private final BitSet mapped;
  
  public FirstFitAllocator(SsaMethod paramSsaMethod, InterferenceGraph paramInterferenceGraph) {
    super(paramSsaMethod, paramInterferenceGraph);
    this.mapped = new BitSet(paramSsaMethod.getRegCount());
  }
  
  private int paramNumberFromMoveParam(NormalSsaInsn paramNormalSsaInsn) {
    return ((CstInteger)((CstInsn)paramNormalSsaInsn.getOriginalRopInsn()).getConstant()).getValue();
  }
  
  public RegisterMapper allocateRegisters() {
    // Byte code:
    //   0: aload_0
    //   1: getfield ssaMeth : Lcom/android/dx/ssa/SsaMethod;
    //   4: invokevirtual getRegCount : ()I
    //   7: istore_1
    //   8: new com/android/dx/ssa/BasicRegisterMapper
    //   11: dup
    //   12: iload_1
    //   13: invokespecial <init> : (I)V
    //   16: astore_2
    //   17: aload_0
    //   18: getfield ssaMeth : Lcom/android/dx/ssa/SsaMethod;
    //   21: invokevirtual getParamWidth : ()I
    //   24: istore_3
    //   25: iconst_0
    //   26: istore #4
    //   28: iload #4
    //   30: iload_1
    //   31: if_icmpge -> 304
    //   34: aload_0
    //   35: getfield mapped : Ljava/util/BitSet;
    //   38: iload #4
    //   40: invokevirtual get : (I)Z
    //   43: ifeq -> 52
    //   46: iload_3
    //   47: istore #5
    //   49: goto -> 295
    //   52: aload_0
    //   53: iload #4
    //   55: invokevirtual getCategoryForSsaReg : (I)I
    //   58: istore #6
    //   60: new com/android/dx/util/BitIntSet
    //   63: dup
    //   64: iload_1
    //   65: invokespecial <init> : (I)V
    //   68: astore #7
    //   70: aload_0
    //   71: getfield interference : Lcom/android/dx/ssa/back/InterferenceGraph;
    //   74: iload #4
    //   76: aload #7
    //   78: invokevirtual mergeInterferenceSet : (ILcom/android/dx/util/IntSet;)V
    //   81: aload_0
    //   82: iload #4
    //   84: invokevirtual isDefinitionMoveParam : (I)Z
    //   87: ifeq -> 124
    //   90: aload_0
    //   91: aload_0
    //   92: getfield ssaMeth : Lcom/android/dx/ssa/SsaMethod;
    //   95: iload #4
    //   97: invokevirtual getDefinitionForRegister : (I)Lcom/android/dx/ssa/SsaInsn;
    //   100: checkcast com/android/dx/ssa/NormalSsaInsn
    //   103: invokespecial paramNumberFromMoveParam : (Lcom/android/dx/ssa/NormalSsaInsn;)I
    //   106: istore #5
    //   108: aload_2
    //   109: iload #4
    //   111: iload #5
    //   113: iload #6
    //   115: invokevirtual addMapping : (III)V
    //   118: iconst_1
    //   119: istore #8
    //   121: goto -> 139
    //   124: aload_2
    //   125: iload #4
    //   127: iload_3
    //   128: iload #6
    //   130: invokevirtual addMapping : (III)V
    //   133: iload_3
    //   134: istore #5
    //   136: iconst_0
    //   137: istore #8
    //   139: iload #4
    //   141: iconst_1
    //   142: iadd
    //   143: istore #9
    //   145: iload #9
    //   147: iload_1
    //   148: if_icmpge -> 272
    //   151: iload #6
    //   153: istore #10
    //   155: aload_0
    //   156: getfield mapped : Ljava/util/BitSet;
    //   159: iload #9
    //   161: invokevirtual get : (I)Z
    //   164: ifne -> 262
    //   167: aload_0
    //   168: iload #9
    //   170: invokevirtual isDefinitionMoveParam : (I)Z
    //   173: ifeq -> 183
    //   176: iload #6
    //   178: istore #10
    //   180: goto -> 262
    //   183: iload #6
    //   185: istore #10
    //   187: aload #7
    //   189: iload #9
    //   191: invokeinterface has : (I)Z
    //   196: ifne -> 262
    //   199: iload #8
    //   201: ifeq -> 219
    //   204: iload #6
    //   206: istore #10
    //   208: iload #6
    //   210: aload_0
    //   211: iload #9
    //   213: invokevirtual getCategoryForSsaReg : (I)I
    //   216: if_icmplt -> 262
    //   219: aload_0
    //   220: getfield interference : Lcom/android/dx/ssa/back/InterferenceGraph;
    //   223: iload #9
    //   225: aload #7
    //   227: invokevirtual mergeInterferenceSet : (ILcom/android/dx/util/IntSet;)V
    //   230: iload #6
    //   232: aload_0
    //   233: iload #9
    //   235: invokevirtual getCategoryForSsaReg : (I)I
    //   238: invokestatic max : (II)I
    //   241: istore #10
    //   243: aload_2
    //   244: iload #9
    //   246: iload #5
    //   248: iload #10
    //   250: invokevirtual addMapping : (III)V
    //   253: aload_0
    //   254: getfield mapped : Ljava/util/BitSet;
    //   257: iload #9
    //   259: invokevirtual set : (I)V
    //   262: iinc #9, 1
    //   265: iload #10
    //   267: istore #6
    //   269: goto -> 145
    //   272: aload_0
    //   273: getfield mapped : Ljava/util/BitSet;
    //   276: iload #4
    //   278: invokevirtual set : (I)V
    //   281: iload_3
    //   282: istore #5
    //   284: iload #8
    //   286: ifne -> 295
    //   289: iload_3
    //   290: iload #6
    //   292: iadd
    //   293: istore #5
    //   295: iinc #4, 1
    //   298: iload #5
    //   300: istore_3
    //   301: goto -> 28
    //   304: aload_2
    //   305: areturn
  }
  
  public boolean wantsParamsMovedHigh() {
    return true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\back\FirstFitAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */