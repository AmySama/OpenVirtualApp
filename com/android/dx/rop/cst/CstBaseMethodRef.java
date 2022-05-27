package com.android.dx.rop.cst;

import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.Type;

public abstract class CstBaseMethodRef extends CstMemberRef {
  private Prototype instancePrototype;
  
  private final Prototype prototype;
  
  CstBaseMethodRef(CstType paramCstType, CstNat paramCstNat) {
    super(paramCstType, paramCstNat);
    String str = getNat().getDescriptor().getString();
    if (isSignaturePolymorphic()) {
      this.prototype = Prototype.fromDescriptor(str);
    } else {
      this.prototype = Prototype.intern(str);
    } 
    this.instancePrototype = null;
  }
  
  protected final int compareTo0(Constant paramConstant) {
    int i = super.compareTo0(paramConstant);
    if (i != 0)
      return i; 
    paramConstant = paramConstant;
    return this.prototype.compareTo(((CstBaseMethodRef)paramConstant).prototype);
  }
  
  public final int getParameterWordCount(boolean paramBoolean) {
    return getPrototype(paramBoolean).getParameterTypes().getWordCount();
  }
  
  public final Prototype getPrototype() {
    return this.prototype;
  }
  
  public final Prototype getPrototype(boolean paramBoolean) {
    if (paramBoolean)
      return this.prototype; 
    if (this.instancePrototype == null) {
      Type type = getDefiningClass().getClassType();
      this.instancePrototype = this.prototype.withFirstParameter(type);
    } 
    return this.instancePrototype;
  }
  
  public final Type getType() {
    return this.prototype.getReturnType();
  }
  
  public final boolean isClassInit() {
    return getNat().isClassInit();
  }
  
  public final boolean isInstanceInit() {
    return getNat().isInstanceInit();
  }
  
  public final boolean isSignaturePolymorphic() {
    String str;
    CstType cstType = getDefiningClass();
    boolean bool = cstType.equals(CstType.METHOD_HANDLE);
    byte b = -1;
    if (bool) {
      str = getNat().getName().getString();
      int i = str.hashCode();
      if (i != -1183693704) {
        if (i == 941760871 && str.equals("invokeExact"))
          b = 1; 
      } else if (str.equals("invoke")) {
        b = 0;
      } 
      if (b == 0 || b == 1)
        return true; 
    } 
    if (str.equals(CstType.VAR_HANDLE)) {
      str = getNat().getName().getString();
      switch (str.hashCode()) {
        case 2013994287:
          if (str.equals("weakCompareAndSetRelease"))
            b = 30; 
          break;
        case 2002508693:
          if (str.equals("getAndSetAcquire"))
            b = 19; 
          break;
        case 1483964149:
          if (str.equals("compareAndExchange"))
            b = 0; 
          break;
        case 1352153939:
          if (str.equals("getAndBitwiseOr"))
            b = 12; 
          break;
        case 1245632875:
          if (str.equals("getAndBitwiseXorAcquire"))
            b = 16; 
          break;
        case 937077366:
          if (str.equals("getAndAddAcquire"))
            b = 7; 
          break;
        case 748071969:
          if (str.equals("compareAndExchangeAcquire"))
            b = 1; 
          break;
        case 685319959:
          if (str.equals("getOpaque"))
            b = 21; 
          break;
        case 470702883:
          if (str.equals("setOpaque"))
            b = 24; 
          break;
        case 353422447:
          if (str.equals("getAndBitwiseAndAcquire"))
            b = 10; 
          break;
        case 282724865:
          if (str.equals("getAndSet"))
            b = 18; 
          break;
        case 282707520:
          if (str.equals("getAndAdd"))
            b = 6; 
          break;
        case 189872914:
          if (str.equals("getVolatile"))
            b = 22; 
          break;
        case 101293086:
          if (str.equals("setVolatile"))
            b = 26; 
          break;
        case 93645315:
          if (str.equals("getAndBitwiseOrAcquire"))
            b = 13; 
          break;
        case 113762:
          if (str.equals("set"))
            b = 23; 
          break;
        case 102230:
          if (str.equals("get"))
            b = 4; 
          break;
        case -37641530:
          if (str.equals("getAndSetRelease"))
            b = 20; 
          break;
        case -127361888:
          if (str.equals("getAcquire"))
            b = 5; 
          break;
        case -230706875:
          if (str.equals("setRelease"))
            b = 25; 
          break;
        case -240822786:
          if (str.equals("weakCompareAndSetAcquire"))
            b = 28; 
          break;
        case -567150350:
          if (str.equals("weakCompareAndSetPlain"))
            b = 29; 
          break;
        case -794517348:
          if (str.equals("getAndBitwiseXorRelease"))
            b = 17; 
          break;
        case -1032892181:
          if (str.equals("getAndBitwiseXor"))
            b = 15; 
          break;
        case -1032914329:
          if (str.equals("getAndBitwiseAnd"))
            b = 9; 
          break;
        case -1103072857:
          if (str.equals("getAndAddRelease"))
            b = 8; 
          break;
        case -1117944904:
          if (str.equals("weakCompareAndSet"))
            b = 27; 
          break;
        case -1292078254:
          if (str.equals("compareAndExchangeRelease"))
            b = 2; 
          break;
        case -1671098288:
          if (str.equals("compareAndSet"))
            b = 3; 
          break;
        case -1686727776:
          if (str.equals("getAndBitwiseAndRelease"))
            b = 11; 
          break;
        case -1946504908:
          if (str.equals("getAndBitwiseOrRelease"))
            b = 14; 
          break;
      } 
      switch (b) {
        default:
          return false;
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
        case 26:
        case 27:
        case 28:
        case 29:
        case 30:
          break;
      } 
      return true;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstBaseMethodRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */