package com.android.dx.cf.code;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.Hex;
import java.util.ArrayList;

public class LocalsArraySet extends LocalsArray {
  private final OneLocalsArray primary;
  
  private final ArrayList<LocalsArray> secondaries;
  
  public LocalsArraySet(int paramInt) {
    super(bool);
    boolean bool;
    this.primary = new OneLocalsArray(paramInt);
    this.secondaries = new ArrayList<LocalsArray>();
  }
  
  private LocalsArraySet(LocalsArraySet paramLocalsArraySet) {
    super(bool);
    boolean bool;
    this.primary = paramLocalsArraySet.primary.copy();
    this.secondaries = new ArrayList<LocalsArray>(paramLocalsArraySet.secondaries.size());
    i = paramLocalsArraySet.secondaries.size();
    while (b < i) {
      LocalsArray localsArray = paramLocalsArraySet.secondaries.get(b);
      if (localsArray == null) {
        this.secondaries.add(null);
      } else {
        this.secondaries.add(localsArray.copy());
      } 
      b++;
    } 
  }
  
  public LocalsArraySet(OneLocalsArray paramOneLocalsArray, ArrayList<LocalsArray> paramArrayList) {
    super(bool);
    boolean bool;
    this.primary = paramOneLocalsArray;
    this.secondaries = paramArrayList;
  }
  
  private LocalsArray getSecondaryForLabel(int paramInt) {
    return (paramInt >= this.secondaries.size()) ? null : this.secondaries.get(paramInt);
  }
  
  private LocalsArraySet mergeWithOne(OneLocalsArray paramOneLocalsArray) {
    OneLocalsArray oneLocalsArray = this.primary.merge(paramOneLocalsArray.getPrimary());
    ArrayList<LocalsArray> arrayList = new ArrayList(this.secondaries.size());
    int i = this.secondaries.size();
    byte b = 0;
    boolean bool = false;
    while (b < i) {
      LocalsArray localsArray1 = this.secondaries.get(b);
      LocalsArray localsArray2 = null;
      LocalsArray localsArray3 = localsArray2;
      if (localsArray1 != null)
        try {
          localsArray3 = localsArray1.merge(paramOneLocalsArray);
        } catch (SimException simException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Merging one locals against caller block ");
          stringBuilder.append(Hex.u2(b));
          simException.addContext(stringBuilder.toString());
          localsArray3 = localsArray2;
        }  
      if (bool || localsArray1 != localsArray3) {
        bool = true;
      } else {
        bool = false;
      } 
      arrayList.add(localsArray3);
      b++;
    } 
    return (this.primary == oneLocalsArray && !bool) ? this : new LocalsArraySet(oneLocalsArray, arrayList);
  }
  
  private LocalsArraySet mergeWithSet(LocalsArraySet paramLocalsArraySet) {
    // Byte code:
    //   0: aload_0
    //   1: getfield primary : Lcom/android/dx/cf/code/OneLocalsArray;
    //   4: aload_1
    //   5: invokevirtual getPrimary : ()Lcom/android/dx/cf/code/OneLocalsArray;
    //   8: invokevirtual merge : (Lcom/android/dx/cf/code/OneLocalsArray;)Lcom/android/dx/cf/code/OneLocalsArray;
    //   11: astore_2
    //   12: aload_0
    //   13: getfield secondaries : Ljava/util/ArrayList;
    //   16: invokevirtual size : ()I
    //   19: istore_3
    //   20: aload_1
    //   21: getfield secondaries : Ljava/util/ArrayList;
    //   24: invokevirtual size : ()I
    //   27: istore #4
    //   29: iload_3
    //   30: iload #4
    //   32: invokestatic max : (II)I
    //   35: istore #5
    //   37: new java/util/ArrayList
    //   40: dup
    //   41: iload #5
    //   43: invokespecial <init> : (I)V
    //   46: astore #6
    //   48: iconst_0
    //   49: istore #7
    //   51: iconst_0
    //   52: istore #8
    //   54: iload #7
    //   56: iload #5
    //   58: if_icmpge -> 241
    //   61: aconst_null
    //   62: astore #9
    //   64: iload #7
    //   66: iload_3
    //   67: if_icmpge -> 87
    //   70: aload_0
    //   71: getfield secondaries : Ljava/util/ArrayList;
    //   74: iload #7
    //   76: invokevirtual get : (I)Ljava/lang/Object;
    //   79: checkcast com/android/dx/cf/code/LocalsArray
    //   82: astore #10
    //   84: goto -> 90
    //   87: aconst_null
    //   88: astore #10
    //   90: iload #7
    //   92: iload #4
    //   94: if_icmpge -> 114
    //   97: aload_1
    //   98: getfield secondaries : Ljava/util/ArrayList;
    //   101: iload #7
    //   103: invokevirtual get : (I)Ljava/lang/Object;
    //   106: checkcast com/android/dx/cf/code/LocalsArray
    //   109: astore #11
    //   111: goto -> 117
    //   114: aconst_null
    //   115: astore #11
    //   117: aload #10
    //   119: aload #11
    //   121: if_acmpne -> 127
    //   124: goto -> 140
    //   127: aload #10
    //   129: ifnonnull -> 135
    //   132: goto -> 203
    //   135: aload #11
    //   137: ifnonnull -> 147
    //   140: aload #10
    //   142: astore #11
    //   144: goto -> 203
    //   147: aload #10
    //   149: aload #11
    //   151: invokevirtual merge : (Lcom/android/dx/cf/code/LocalsArray;)Lcom/android/dx/cf/code/LocalsArray;
    //   154: astore #11
    //   156: goto -> 203
    //   159: astore #12
    //   161: new java/lang/StringBuilder
    //   164: dup
    //   165: invokespecial <init> : ()V
    //   168: astore #11
    //   170: aload #11
    //   172: ldc 'Merging locals set for caller block '
    //   174: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   177: pop
    //   178: aload #11
    //   180: iload #7
    //   182: invokestatic u2 : (I)Ljava/lang/String;
    //   185: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: pop
    //   189: aload #12
    //   191: aload #11
    //   193: invokevirtual toString : ()Ljava/lang/String;
    //   196: invokevirtual addContext : (Ljava/lang/String;)V
    //   199: aload #9
    //   201: astore #11
    //   203: iload #8
    //   205: ifne -> 224
    //   208: aload #10
    //   210: aload #11
    //   212: if_acmpeq -> 218
    //   215: goto -> 224
    //   218: iconst_0
    //   219: istore #8
    //   221: goto -> 227
    //   224: iconst_1
    //   225: istore #8
    //   227: aload #6
    //   229: aload #11
    //   231: invokevirtual add : (Ljava/lang/Object;)Z
    //   234: pop
    //   235: iinc #7, 1
    //   238: goto -> 54
    //   241: aload_0
    //   242: getfield primary : Lcom/android/dx/cf/code/OneLocalsArray;
    //   245: aload_2
    //   246: if_acmpne -> 256
    //   249: iload #8
    //   251: ifne -> 256
    //   254: aload_0
    //   255: areturn
    //   256: new com/android/dx/cf/code/LocalsArraySet
    //   259: dup
    //   260: aload_2
    //   261: aload #6
    //   263: invokespecial <init> : (Lcom/android/dx/cf/code/OneLocalsArray;Ljava/util/ArrayList;)V
    //   266: areturn
    // Exception table:
    //   from	to	target	type
    //   147	156	159	com/android/dx/cf/code/SimException
  }
  
  public void annotate(ExceptionWithContext paramExceptionWithContext) {
    paramExceptionWithContext.addContext("(locals array set; primary)");
    this.primary.annotate(paramExceptionWithContext);
    int i = this.secondaries.size();
    for (byte b = 0; b < i; b++) {
      LocalsArray localsArray = this.secondaries.get(b);
      if (localsArray != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(locals array set: primary for caller ");
        stringBuilder.append(Hex.u2(b));
        stringBuilder.append(')');
        paramExceptionWithContext.addContext(stringBuilder.toString());
        localsArray.getPrimary().annotate(paramExceptionWithContext);
      } 
    } 
  }
  
  public LocalsArray copy() {
    return new LocalsArraySet(this);
  }
  
  public TypeBearer get(int paramInt) {
    return this.primary.get(paramInt);
  }
  
  public TypeBearer getCategory1(int paramInt) {
    return this.primary.getCategory1(paramInt);
  }
  
  public TypeBearer getCategory2(int paramInt) {
    return this.primary.getCategory2(paramInt);
  }
  
  public int getMaxLocals() {
    return this.primary.getMaxLocals();
  }
  
  public TypeBearer getOrNull(int paramInt) {
    return this.primary.getOrNull(paramInt);
  }
  
  protected OneLocalsArray getPrimary() {
    return this.primary;
  }
  
  public void invalidate(int paramInt) {
    throwIfImmutable();
    this.primary.invalidate(paramInt);
    for (LocalsArray localsArray : this.secondaries) {
      if (localsArray != null)
        localsArray.invalidate(paramInt); 
    } 
  }
  
  public void makeInitialized(Type paramType) {
    if (this.primary.getMaxLocals() == 0)
      return; 
    throwIfImmutable();
    this.primary.makeInitialized(paramType);
    for (LocalsArray localsArray : this.secondaries) {
      if (localsArray != null)
        localsArray.makeInitialized(paramType); 
    } 
  }
  
  public LocalsArraySet merge(LocalsArray paramLocalsArray) {
    try {
      if (paramLocalsArray instanceof LocalsArraySet) {
        LocalsArraySet localsArraySet = mergeWithSet((LocalsArraySet)paramLocalsArray);
        paramLocalsArray = localsArraySet;
      } else {
        LocalsArraySet localsArraySet = mergeWithOne((OneLocalsArray)paramLocalsArray);
        paramLocalsArray = localsArraySet;
      } 
      paramLocalsArray.setImmutable();
      return (LocalsArraySet)paramLocalsArray;
    } catch (SimException simException) {
      simException.addContext("underlay locals:");
      annotate(simException);
      simException.addContext("overlay locals:");
      paramLocalsArray.annotate(simException);
      throw simException;
    } 
  }
  
  public LocalsArraySet mergeWithSubroutineCaller(LocalsArray paramLocalsArray, int paramInt) {
    LocalsArray localsArray = getSecondaryForLabel(paramInt);
    OneLocalsArray oneLocalsArray1 = this.primary.merge(paramLocalsArray.getPrimary());
    if (localsArray == paramLocalsArray) {
      paramLocalsArray = localsArray;
    } else if (localsArray != null) {
      paramLocalsArray = localsArray.merge(paramLocalsArray);
    } 
    if (paramLocalsArray == localsArray && oneLocalsArray1 == this.primary)
      return this; 
    int i = this.secondaries.size();
    int j = Math.max(paramInt + 1, i);
    ArrayList<LocalsArray> arrayList = new ArrayList(j);
    byte b = 0;
    OneLocalsArray oneLocalsArray2;
    for (oneLocalsArray2 = null; b < j; oneLocalsArray2 = oneLocalsArray1) {
      if (b == paramInt) {
        localsArray = paramLocalsArray;
      } else if (b < i) {
        localsArray = this.secondaries.get(b);
      } else {
        localsArray = null;
      } 
      oneLocalsArray1 = oneLocalsArray2;
      if (localsArray != null)
        if (oneLocalsArray2 == null) {
          oneLocalsArray1 = localsArray.getPrimary();
        } else {
          oneLocalsArray1 = oneLocalsArray2.merge(localsArray.getPrimary());
        }  
      arrayList.add(localsArray);
      b++;
    } 
    paramLocalsArray = new LocalsArraySet(oneLocalsArray2, arrayList);
    paramLocalsArray.setImmutable();
    return (LocalsArraySet)paramLocalsArray;
  }
  
  public void set(int paramInt, TypeBearer paramTypeBearer) {
    throwIfImmutable();
    this.primary.set(paramInt, paramTypeBearer);
    for (LocalsArray localsArray : this.secondaries) {
      if (localsArray != null)
        localsArray.set(paramInt, paramTypeBearer); 
    } 
  }
  
  public void set(RegisterSpec paramRegisterSpec) {
    set(paramRegisterSpec.getReg(), (TypeBearer)paramRegisterSpec);
  }
  
  public void setImmutable() {
    this.primary.setImmutable();
    for (LocalsArray localsArray : this.secondaries) {
      if (localsArray != null)
        localsArray.setImmutable(); 
    } 
    super.setImmutable();
  }
  
  public LocalsArray subArrayForLabel(int paramInt) {
    return getSecondaryForLabel(paramInt);
  }
  
  public String toHuman() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("(locals array set; primary)\n");
    stringBuilder.append(getPrimary().toHuman());
    stringBuilder.append('\n');
    int i = this.secondaries.size();
    for (byte b = 0; b < i; b++) {
      LocalsArray localsArray = this.secondaries.get(b);
      if (localsArray != null) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("(locals array set: primary for caller ");
        stringBuilder1.append(Hex.u2(b));
        stringBuilder1.append(")\n");
        stringBuilder.append(stringBuilder1.toString());
        stringBuilder.append(localsArray.getPrimary().toHuman());
        stringBuilder.append('\n');
      } 
    } 
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\LocalsArraySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */