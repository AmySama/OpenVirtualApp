package com.android.dx.dex.file;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.dex.code.LocalList;
import com.android.dx.dex.code.PositionList;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Prototype;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.ByteArrayAnnotatedOutput;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;

public final class DebugInfoEncoder {
  private static final boolean DEBUG = false;
  
  private int address = 0;
  
  private AnnotatedOutput annotateTo;
  
  private final int codeSize;
  
  private PrintWriter debugPrint;
  
  private final Prototype desc;
  
  private final DexFile file;
  
  private final boolean isStatic;
  
  private final LocalList.Entry[] lastEntryForReg;
  
  private int line = 1;
  
  private final LocalList locals;
  
  private final ByteArrayAnnotatedOutput output;
  
  private final PositionList positions;
  
  private String prefix;
  
  private final int regSize;
  
  private boolean shouldConsume;
  
  public DebugInfoEncoder(PositionList paramPositionList, LocalList paramLocalList, DexFile paramDexFile, int paramInt1, int paramInt2, boolean paramBoolean, CstMethodRef paramCstMethodRef) {
    this.positions = paramPositionList;
    this.locals = paramLocalList;
    this.file = paramDexFile;
    this.desc = paramCstMethodRef.getPrototype();
    this.isStatic = paramBoolean;
    this.codeSize = paramInt1;
    this.regSize = paramInt2;
    this.output = new ByteArrayAnnotatedOutput();
    this.lastEntryForReg = new LocalList.Entry[paramInt2];
  }
  
  private void annotate(int paramInt, String paramString) {
    String str = paramString;
    if (this.prefix != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.prefix);
      stringBuilder.append(paramString);
      str = stringBuilder.toString();
    } 
    AnnotatedOutput annotatedOutput = this.annotateTo;
    if (annotatedOutput != null) {
      if (!this.shouldConsume)
        paramInt = 0; 
      annotatedOutput.annotate(paramInt, str);
    } 
    PrintWriter printWriter = this.debugPrint;
    if (printWriter != null)
      printWriter.println(str); 
  }
  
  private ArrayList<PositionList.Entry> buildSortedPositions() {
    int i;
    PositionList positionList = this.positions;
    byte b = 0;
    if (positionList == null) {
      i = 0;
    } else {
      i = positionList.size();
    } 
    ArrayList<PositionList.Entry> arrayList = new ArrayList(i);
    while (b < i) {
      arrayList.add(this.positions.get(b));
      b++;
    } 
    Collections.sort(arrayList, new Comparator<PositionList.Entry>() {
          public int compare(PositionList.Entry param1Entry1, PositionList.Entry param1Entry2) {
            return param1Entry1.getAddress() - param1Entry2.getAddress();
          }
          
          public boolean equals(Object param1Object) {
            boolean bool;
            if (param1Object == this) {
              bool = true;
            } else {
              bool = false;
            } 
            return bool;
          }
        });
    return arrayList;
  }
  
  private static int computeOpcode(int paramInt1, int paramInt2) {
    if (paramInt1 >= -4 && paramInt1 <= 10)
      return paramInt1 + 4 + paramInt2 * 15 + 10; 
    throw new RuntimeException("Parameter out of range");
  }
  
  private byte[] convert0() throws IOException {
    ArrayList<PositionList.Entry> arrayList = buildSortedPositions();
    emitHeader(arrayList, extractMethodArguments());
    this.output.writeByte(7);
    AnnotatedOutput annotatedOutput = this.annotateTo;
    int i = 0;
    if (annotatedOutput != null || this.debugPrint != null)
      annotate(1, String.format("%04x: prologue end", new Object[] { Integer.valueOf(this.address) })); 
    int j = arrayList.size();
    int k = this.locals.size();
    int m;
    for (m = 0;; m = i1) {
      int n = emitLocalsAtAddress(i);
      int i1 = emitPositionsAtAddress(m, arrayList);
      if (n < k) {
        m = this.locals.get(n).getAddress();
      } else {
        m = Integer.MAX_VALUE;
      } 
      if (i1 < j) {
        i = ((PositionList.Entry)arrayList.get(i1)).getAddress();
      } else {
        i = Integer.MAX_VALUE;
      } 
      int i2 = Math.min(i, m);
      if (i2 == Integer.MAX_VALUE || (i2 == this.codeSize && m == Integer.MAX_VALUE && i == Integer.MAX_VALUE)) {
        emitEndSequence();
        return this.output.toByteArray();
      } 
      if (i2 == i) {
        emitPosition(arrayList.get(i1));
        m = i1 + 1;
        i = n;
        continue;
      } 
      emitAdvancePc(i2 - this.address);
      i = n;
    } 
  }
  
  private void emitAdvanceLine(int paramInt) throws IOException {
    int i = this.output.getCursor();
    this.output.writeByte(2);
    this.output.writeSleb128(paramInt);
    this.line += paramInt;
    if (this.annotateTo != null || this.debugPrint != null)
      annotate(this.output.getCursor() - i, String.format("line = %d", new Object[] { Integer.valueOf(this.line) })); 
  }
  
  private void emitAdvancePc(int paramInt) throws IOException {
    int i = this.output.getCursor();
    this.output.writeByte(1);
    this.output.writeUleb128(paramInt);
    this.address += paramInt;
    if (this.annotateTo != null || this.debugPrint != null)
      annotate(this.output.getCursor() - i, String.format("%04x: advance pc", new Object[] { Integer.valueOf(this.address) })); 
  }
  
  private void emitEndSequence() {
    this.output.writeByte(0);
    if (this.annotateTo != null || this.debugPrint != null)
      annotate(1, "end sequence"); 
  }
  
  private void emitHeader(ArrayList<PositionList.Entry> paramArrayList, ArrayList<LocalList.Entry> paramArrayList1) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: getfield annotateTo : Lcom/android/dx/util/AnnotatedOutput;
    //   4: astore_3
    //   5: iconst_0
    //   6: istore #4
    //   8: aload_3
    //   9: ifnonnull -> 28
    //   12: aload_0
    //   13: getfield debugPrint : Ljava/io/PrintWriter;
    //   16: ifnull -> 22
    //   19: goto -> 28
    //   22: iconst_0
    //   23: istore #5
    //   25: goto -> 31
    //   28: iconst_1
    //   29: istore #5
    //   31: aload_0
    //   32: getfield output : Lcom/android/dx/util/ByteArrayAnnotatedOutput;
    //   35: invokevirtual getCursor : ()I
    //   38: istore #6
    //   40: aload_1
    //   41: invokevirtual size : ()I
    //   44: ifle -> 65
    //   47: aload_0
    //   48: aload_1
    //   49: iconst_0
    //   50: invokevirtual get : (I)Ljava/lang/Object;
    //   53: checkcast com/android/dx/dex/code/PositionList$Entry
    //   56: invokevirtual getPosition : ()Lcom/android/dx/rop/code/SourcePosition;
    //   59: invokevirtual getLine : ()I
    //   62: putfield line : I
    //   65: aload_0
    //   66: getfield output : Lcom/android/dx/util/ByteArrayAnnotatedOutput;
    //   69: aload_0
    //   70: getfield line : I
    //   73: invokevirtual writeUleb128 : (I)I
    //   76: pop
    //   77: iload #5
    //   79: ifeq -> 128
    //   82: aload_0
    //   83: getfield output : Lcom/android/dx/util/ByteArrayAnnotatedOutput;
    //   86: invokevirtual getCursor : ()I
    //   89: istore #7
    //   91: new java/lang/StringBuilder
    //   94: dup
    //   95: invokespecial <init> : ()V
    //   98: astore_1
    //   99: aload_1
    //   100: ldc 'line_start: '
    //   102: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: pop
    //   106: aload_1
    //   107: aload_0
    //   108: getfield line : I
    //   111: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   114: pop
    //   115: aload_0
    //   116: iload #7
    //   118: iload #6
    //   120: isub
    //   121: aload_1
    //   122: invokevirtual toString : ()Ljava/lang/String;
    //   125: invokespecial annotate : (ILjava/lang/String;)V
    //   128: aload_0
    //   129: invokespecial getParamBase : ()I
    //   132: istore #7
    //   134: aload_0
    //   135: getfield desc : Lcom/android/dx/rop/type/Prototype;
    //   138: invokevirtual getParameterTypes : ()Lcom/android/dx/rop/type/StdTypeList;
    //   141: astore_3
    //   142: aload_3
    //   143: invokevirtual size : ()I
    //   146: istore #8
    //   148: iload #7
    //   150: istore #6
    //   152: aload_0
    //   153: getfield isStatic : Z
    //   156: ifne -> 209
    //   159: aload_2
    //   160: invokevirtual iterator : ()Ljava/util/Iterator;
    //   163: astore #9
    //   165: aload #9
    //   167: invokeinterface hasNext : ()Z
    //   172: ifeq -> 203
    //   175: aload #9
    //   177: invokeinterface next : ()Ljava/lang/Object;
    //   182: checkcast com/android/dx/dex/code/LocalList$Entry
    //   185: astore_1
    //   186: iload #7
    //   188: aload_1
    //   189: invokevirtual getRegister : ()I
    //   192: if_icmpne -> 165
    //   195: aload_0
    //   196: getfield lastEntryForReg : [Lcom/android/dx/dex/code/LocalList$Entry;
    //   199: iload #7
    //   201: aload_1
    //   202: aastore
    //   203: iload #7
    //   205: iconst_1
    //   206: iadd
    //   207: istore #6
    //   209: aload_0
    //   210: getfield output : Lcom/android/dx/util/ByteArrayAnnotatedOutput;
    //   213: invokevirtual getCursor : ()I
    //   216: istore #7
    //   218: aload_0
    //   219: getfield output : Lcom/android/dx/util/ByteArrayAnnotatedOutput;
    //   222: iload #8
    //   224: invokevirtual writeUleb128 : (I)I
    //   227: pop
    //   228: iload #5
    //   230: ifeq -> 265
    //   233: aload_0
    //   234: aload_0
    //   235: getfield output : Lcom/android/dx/util/ByteArrayAnnotatedOutput;
    //   238: invokevirtual getCursor : ()I
    //   241: iload #7
    //   243: isub
    //   244: ldc_w 'parameters_size: %04x'
    //   247: iconst_1
    //   248: anewarray java/lang/Object
    //   251: dup
    //   252: iconst_0
    //   253: iload #8
    //   255: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   258: aastore
    //   259: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   262: invokespecial annotate : (ILjava/lang/String;)V
    //   265: iconst_0
    //   266: istore #10
    //   268: iload #6
    //   270: istore #7
    //   272: iload #10
    //   274: istore #6
    //   276: iload #6
    //   278: iload #8
    //   280: if_icmpge -> 505
    //   283: aload_3
    //   284: iload #6
    //   286: invokevirtual get : (I)Lcom/android/dx/rop/type/Type;
    //   289: astore #9
    //   291: aload_0
    //   292: getfield output : Lcom/android/dx/util/ByteArrayAnnotatedOutput;
    //   295: invokevirtual getCursor : ()I
    //   298: istore #10
    //   300: aload_2
    //   301: invokevirtual iterator : ()Ljava/util/Iterator;
    //   304: astore #11
    //   306: aload #11
    //   308: invokeinterface hasNext : ()Z
    //   313: ifeq -> 370
    //   316: aload #11
    //   318: invokeinterface next : ()Ljava/lang/Object;
    //   323: checkcast com/android/dx/dex/code/LocalList$Entry
    //   326: astore_1
    //   327: iload #7
    //   329: aload_1
    //   330: invokevirtual getRegister : ()I
    //   333: if_icmpne -> 306
    //   336: aload_1
    //   337: invokevirtual getSignature : ()Lcom/android/dx/rop/cst/CstString;
    //   340: ifnull -> 351
    //   343: aload_0
    //   344: aconst_null
    //   345: invokespecial emitStringIndex : (Lcom/android/dx/rop/cst/CstString;)V
    //   348: goto -> 359
    //   351: aload_0
    //   352: aload_1
    //   353: invokevirtual getName : ()Lcom/android/dx/rop/cst/CstString;
    //   356: invokespecial emitStringIndex : (Lcom/android/dx/rop/cst/CstString;)V
    //   359: aload_0
    //   360: getfield lastEntryForReg : [Lcom/android/dx/dex/code/LocalList$Entry;
    //   363: iload #7
    //   365: aload_1
    //   366: aastore
    //   367: goto -> 372
    //   370: aconst_null
    //   371: astore_1
    //   372: aload_1
    //   373: ifnonnull -> 381
    //   376: aload_0
    //   377: aconst_null
    //   378: invokespecial emitStringIndex : (Lcom/android/dx/rop/cst/CstString;)V
    //   381: iload #5
    //   383: ifeq -> 489
    //   386: aload_1
    //   387: ifnull -> 411
    //   390: aload_1
    //   391: invokevirtual getSignature : ()Lcom/android/dx/rop/cst/CstString;
    //   394: ifnull -> 400
    //   397: goto -> 411
    //   400: aload_1
    //   401: invokevirtual getName : ()Lcom/android/dx/rop/cst/CstString;
    //   404: invokevirtual toHuman : ()Ljava/lang/String;
    //   407: astore_1
    //   408: goto -> 415
    //   411: ldc_w '<unnamed>'
    //   414: astore_1
    //   415: aload_0
    //   416: getfield output : Lcom/android/dx/util/ByteArrayAnnotatedOutput;
    //   419: invokevirtual getCursor : ()I
    //   422: istore #12
    //   424: new java/lang/StringBuilder
    //   427: dup
    //   428: invokespecial <init> : ()V
    //   431: astore #11
    //   433: aload #11
    //   435: ldc_w 'parameter '
    //   438: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   441: pop
    //   442: aload #11
    //   444: aload_1
    //   445: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   448: pop
    //   449: aload #11
    //   451: ldc_w ' '
    //   454: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   457: pop
    //   458: aload #11
    //   460: ldc_w 'v'
    //   463: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   466: pop
    //   467: aload #11
    //   469: iload #7
    //   471: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   474: pop
    //   475: aload_0
    //   476: iload #12
    //   478: iload #10
    //   480: isub
    //   481: aload #11
    //   483: invokevirtual toString : ()Ljava/lang/String;
    //   486: invokespecial annotate : (ILjava/lang/String;)V
    //   489: iload #7
    //   491: aload #9
    //   493: invokevirtual getCategory : ()I
    //   496: iadd
    //   497: istore #7
    //   499: iinc #6, 1
    //   502: goto -> 276
    //   505: aload_0
    //   506: getfield lastEntryForReg : [Lcom/android/dx/dex/code/LocalList$Entry;
    //   509: astore_2
    //   510: aload_2
    //   511: arraylength
    //   512: istore #5
    //   514: iload #4
    //   516: istore #6
    //   518: iload #6
    //   520: iload #5
    //   522: if_icmpge -> 555
    //   525: aload_2
    //   526: iload #6
    //   528: aaload
    //   529: astore_1
    //   530: aload_1
    //   531: ifnonnull -> 537
    //   534: goto -> 549
    //   537: aload_1
    //   538: invokevirtual getSignature : ()Lcom/android/dx/rop/cst/CstString;
    //   541: ifnull -> 549
    //   544: aload_0
    //   545: aload_1
    //   546: invokespecial emitLocalStartExtended : (Lcom/android/dx/dex/code/LocalList$Entry;)V
    //   549: iinc #6, 1
    //   552: goto -> 518
    //   555: return
  }
  
  private void emitLocalEnd(LocalList.Entry paramEntry) throws IOException {
    int i = this.output.getCursor();
    this.output.writeByte(5);
    this.output.writeUleb128(paramEntry.getRegister());
    if (this.annotateTo != null || this.debugPrint != null)
      annotate(this.output.getCursor() - i, String.format("%04x: -local %s", new Object[] { Integer.valueOf(this.address), entryAnnotationString(paramEntry) })); 
  }
  
  private void emitLocalRestart(LocalList.Entry paramEntry) throws IOException {
    int i = this.output.getCursor();
    this.output.writeByte(6);
    emitUnsignedLeb128(paramEntry.getRegister());
    if (this.annotateTo != null || this.debugPrint != null)
      annotate(this.output.getCursor() - i, String.format("%04x: +local restart %s", new Object[] { Integer.valueOf(this.address), entryAnnotationString(paramEntry) })); 
  }
  
  private void emitLocalStart(LocalList.Entry paramEntry) throws IOException {
    if (paramEntry.getSignature() != null) {
      emitLocalStartExtended(paramEntry);
      return;
    } 
    int i = this.output.getCursor();
    this.output.writeByte(3);
    emitUnsignedLeb128(paramEntry.getRegister());
    emitStringIndex(paramEntry.getName());
    emitTypeIndex(paramEntry.getType());
    if (this.annotateTo != null || this.debugPrint != null)
      annotate(this.output.getCursor() - i, String.format("%04x: +local %s", new Object[] { Integer.valueOf(this.address), entryAnnotationString(paramEntry) })); 
  }
  
  private void emitLocalStartExtended(LocalList.Entry paramEntry) throws IOException {
    int i = this.output.getCursor();
    this.output.writeByte(4);
    emitUnsignedLeb128(paramEntry.getRegister());
    emitStringIndex(paramEntry.getName());
    emitTypeIndex(paramEntry.getType());
    emitStringIndex(paramEntry.getSignature());
    if (this.annotateTo != null || this.debugPrint != null)
      annotate(this.output.getCursor() - i, String.format("%04x: +localx %s", new Object[] { Integer.valueOf(this.address), entryAnnotationString(paramEntry) })); 
  }
  
  private int emitLocalsAtAddress(int paramInt) throws IOException {
    int i = this.locals.size();
    while (paramInt < i && this.locals.get(paramInt).getAddress() == this.address) {
      LocalList.Entry entry1 = this.locals.get(paramInt);
      int j = entry1.getRegister();
      LocalList.Entry[] arrayOfEntry = this.lastEntryForReg;
      LocalList.Entry entry2 = arrayOfEntry[j];
      if (entry1 != entry2) {
        arrayOfEntry[j] = entry1;
        if (entry1.isStart()) {
          if (entry2 != null && entry1.matches(entry2)) {
            if (!entry2.isStart()) {
              emitLocalRestart(entry1);
            } else {
              throw new RuntimeException("shouldn't happen");
            } 
          } else {
            emitLocalStart(entry1);
          } 
        } else if (entry1.getDisposition() != LocalList.Disposition.END_REPLACED) {
          emitLocalEnd(entry1);
        } 
      } 
      paramInt++;
    } 
    return paramInt;
  }
  
  private void emitPosition(PositionList.Entry paramEntry) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getPosition : ()Lcom/android/dx/rop/code/SourcePosition;
    //   4: invokevirtual getLine : ()I
    //   7: istore_2
    //   8: aload_1
    //   9: invokevirtual getAddress : ()I
    //   12: istore_3
    //   13: iload_2
    //   14: aload_0
    //   15: getfield line : I
    //   18: isub
    //   19: istore_2
    //   20: iload_3
    //   21: aload_0
    //   22: getfield address : I
    //   25: isub
    //   26: istore #4
    //   28: iload #4
    //   30: iflt -> 208
    //   33: iload_2
    //   34: bipush #-4
    //   36: if_icmplt -> 47
    //   39: iload_2
    //   40: istore_3
    //   41: iload_2
    //   42: bipush #10
    //   44: if_icmple -> 54
    //   47: aload_0
    //   48: iload_2
    //   49: invokespecial emitAdvanceLine : (I)V
    //   52: iconst_0
    //   53: istore_3
    //   54: iload_3
    //   55: iload #4
    //   57: invokestatic computeOpcode : (II)I
    //   60: istore #5
    //   62: iload_3
    //   63: istore #6
    //   65: iload #5
    //   67: istore #7
    //   69: iload #4
    //   71: istore_2
    //   72: iload #5
    //   74: sipush #-256
    //   77: iand
    //   78: ifle -> 128
    //   81: aload_0
    //   82: iload #4
    //   84: invokespecial emitAdvancePc : (I)V
    //   87: iload_3
    //   88: iconst_0
    //   89: invokestatic computeOpcode : (II)I
    //   92: istore #7
    //   94: iload #7
    //   96: sipush #-256
    //   99: iand
    //   100: ifle -> 123
    //   103: aload_0
    //   104: iload_3
    //   105: invokespecial emitAdvanceLine : (I)V
    //   108: iconst_0
    //   109: iconst_0
    //   110: invokestatic computeOpcode : (II)I
    //   113: istore #7
    //   115: iconst_0
    //   116: istore_2
    //   117: iconst_0
    //   118: istore #6
    //   120: goto -> 128
    //   123: iconst_0
    //   124: istore_2
    //   125: iload_3
    //   126: istore #6
    //   128: aload_0
    //   129: getfield output : Lcom/android/dx/util/ByteArrayAnnotatedOutput;
    //   132: iload #7
    //   134: invokevirtual writeByte : (I)V
    //   137: aload_0
    //   138: aload_0
    //   139: getfield line : I
    //   142: iload #6
    //   144: iadd
    //   145: putfield line : I
    //   148: aload_0
    //   149: aload_0
    //   150: getfield address : I
    //   153: iload_2
    //   154: iadd
    //   155: putfield address : I
    //   158: aload_0
    //   159: getfield annotateTo : Lcom/android/dx/util/AnnotatedOutput;
    //   162: ifnonnull -> 172
    //   165: aload_0
    //   166: getfield debugPrint : Ljava/io/PrintWriter;
    //   169: ifnull -> 207
    //   172: aload_0
    //   173: iconst_1
    //   174: ldc_w '%04x: line %d'
    //   177: iconst_2
    //   178: anewarray java/lang/Object
    //   181: dup
    //   182: iconst_0
    //   183: aload_0
    //   184: getfield address : I
    //   187: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   190: aastore
    //   191: dup
    //   192: iconst_1
    //   193: aload_0
    //   194: getfield line : I
    //   197: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   200: aastore
    //   201: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   204: invokespecial annotate : (ILjava/lang/String;)V
    //   207: return
    //   208: new java/lang/RuntimeException
    //   211: dup
    //   212: ldc_w 'Position entries must be in ascending address order'
    //   215: invokespecial <init> : (Ljava/lang/String;)V
    //   218: athrow
  }
  
  private int emitPositionsAtAddress(int paramInt, ArrayList<PositionList.Entry> paramArrayList) throws IOException {
    int i = paramArrayList.size();
    while (paramInt < i && ((PositionList.Entry)paramArrayList.get(paramInt)).getAddress() == this.address) {
      emitPosition(paramArrayList.get(paramInt));
      paramInt++;
    } 
    return paramInt;
  }
  
  private void emitStringIndex(CstString paramCstString) throws IOException {
    if (paramCstString != null) {
      DexFile dexFile = this.file;
      if (dexFile == null) {
        this.output.writeUleb128(0);
        return;
      } 
      this.output.writeUleb128(dexFile.getStringIds().indexOf(paramCstString) + 1);
      return;
    } 
    this.output.writeUleb128(0);
  }
  
  private void emitTypeIndex(CstType paramCstType) throws IOException {
    if (paramCstType != null) {
      DexFile dexFile = this.file;
      if (dexFile == null) {
        this.output.writeUleb128(0);
        return;
      } 
      this.output.writeUleb128(dexFile.getTypeIds().indexOf(paramCstType) + 1);
      return;
    } 
    this.output.writeUleb128(0);
  }
  
  private void emitUnsignedLeb128(int paramInt) throws IOException {
    if (paramInt >= 0) {
      this.output.writeUleb128(paramInt);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Signed value where unsigned required: ");
    stringBuilder.append(paramInt);
    throw new RuntimeException(stringBuilder.toString());
  }
  
  private String entryAnnotationString(LocalList.Entry paramEntry) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("v");
    stringBuilder.append(paramEntry.getRegister());
    stringBuilder.append(' ');
    CstString cstString2 = paramEntry.getName();
    if (cstString2 == null) {
      stringBuilder.append("null");
    } else {
      stringBuilder.append(cstString2.toHuman());
    } 
    stringBuilder.append(' ');
    CstType cstType = paramEntry.getType();
    if (cstType == null) {
      stringBuilder.append("null");
    } else {
      stringBuilder.append(cstType.toHuman());
    } 
    CstString cstString1 = paramEntry.getSignature();
    if (cstString1 != null) {
      stringBuilder.append(' ');
      stringBuilder.append(cstString1.toHuman());
    } 
    return stringBuilder.toString();
  }
  
  private ArrayList<LocalList.Entry> extractMethodArguments() {
    ArrayList<LocalList.Entry> arrayList = new ArrayList(this.desc.getParameterTypes().size());
    int i = getParamBase();
    BitSet bitSet = new BitSet(this.regSize - i);
    int j = this.locals.size();
    for (byte b = 0; b < j; b++) {
      LocalList.Entry entry = this.locals.get(b);
      int k = entry.getRegister();
      if (k >= i) {
        k -= i;
        if (!bitSet.get(k)) {
          bitSet.set(k);
          arrayList.add(entry);
        } 
      } 
    } 
    Collections.sort(arrayList, new Comparator<LocalList.Entry>() {
          public int compare(LocalList.Entry param1Entry1, LocalList.Entry param1Entry2) {
            return param1Entry1.getRegister() - param1Entry2.getRegister();
          }
          
          public boolean equals(Object param1Object) {
            boolean bool;
            if (param1Object == this) {
              bool = true;
            } else {
              bool = false;
            } 
            return bool;
          }
        });
    return arrayList;
  }
  
  private int getParamBase() {
    return this.regSize - this.desc.getParameterTypes().getWordCount() - (this.isStatic ^ true);
  }
  
  public byte[] convert() {
    try {
      return convert0();
    } catch (IOException iOException) {
      throw ExceptionWithContext.withContext(iOException, "...while encoding debug info");
    } 
  }
  
  public byte[] convertAndAnnotate(String paramString, PrintWriter paramPrintWriter, AnnotatedOutput paramAnnotatedOutput, boolean paramBoolean) {
    this.prefix = paramString;
    this.debugPrint = paramPrintWriter;
    this.annotateTo = paramAnnotatedOutput;
    this.shouldConsume = paramBoolean;
    return convert();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\DebugInfoEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */