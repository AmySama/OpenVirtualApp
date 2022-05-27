package com.android.dx.rop.code;

import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.ToHuman;
import java.util.concurrent.ConcurrentHashMap;

public final class RegisterSpec implements TypeBearer, ToHuman, Comparable<RegisterSpec> {
  public static final String PREFIX = "v";
  
  private static final ThreadLocal<ForComparison> theInterningItem;
  
  private static final ConcurrentHashMap<Object, RegisterSpec> theInterns = new ConcurrentHashMap<Object, RegisterSpec>(10000, 0.75F);
  
  private final LocalItem local;
  
  private final int reg;
  
  private final TypeBearer type;
  
  static {
    theInterningItem = new ThreadLocal<ForComparison>() {
        protected RegisterSpec.ForComparison initialValue() {
          return new RegisterSpec.ForComparison();
        }
      };
  }
  
  private RegisterSpec(int paramInt, TypeBearer paramTypeBearer, LocalItem paramLocalItem) {
    if (paramInt >= 0) {
      if (paramTypeBearer != null) {
        this.reg = paramInt;
        this.type = paramTypeBearer;
        this.local = paramLocalItem;
        return;
      } 
      throw new NullPointerException("type == null");
    } 
    throw new IllegalArgumentException("reg < 0");
  }
  
  public static void clearInternTable() {
    theInterns.clear();
  }
  
  private boolean equals(int paramInt, TypeBearer paramTypeBearer, LocalItem paramLocalItem) {
    if (this.reg == paramInt && this.type.equals(paramTypeBearer)) {
      LocalItem localItem = this.local;
      if (localItem == paramLocalItem || (localItem != null && localItem.equals(paramLocalItem)))
        return true; 
    } 
    return false;
  }
  
  private static int hashCodeOf(int paramInt, TypeBearer paramTypeBearer, LocalItem paramLocalItem) {
    byte b;
    if (paramLocalItem != null) {
      b = paramLocalItem.hashCode();
    } else {
      b = 0;
    } 
    return (b * 31 + paramTypeBearer.hashCode()) * 31 + paramInt;
  }
  
  private static RegisterSpec intern(int paramInt, TypeBearer paramTypeBearer, LocalItem paramLocalItem) {
    ForComparison forComparison = theInterningItem.get();
    forComparison.set(paramInt, paramTypeBearer, paramLocalItem);
    RegisterSpec registerSpec = theInterns.get(forComparison);
    paramTypeBearer = registerSpec;
    if (registerSpec == null) {
      paramTypeBearer = forComparison.toRegisterSpec();
      registerSpec = (RegisterSpec)theInterns.putIfAbsent(paramTypeBearer, paramTypeBearer);
      if (registerSpec != null)
        return registerSpec; 
    } 
    return (RegisterSpec)paramTypeBearer;
  }
  
  public static RegisterSpec make(int paramInt, TypeBearer paramTypeBearer) {
    return intern(paramInt, paramTypeBearer, null);
  }
  
  public static RegisterSpec make(int paramInt, TypeBearer paramTypeBearer, LocalItem paramLocalItem) {
    if (paramLocalItem != null)
      return intern(paramInt, paramTypeBearer, paramLocalItem); 
    throw new NullPointerException("local  == null");
  }
  
  public static RegisterSpec makeLocalOptional(int paramInt, TypeBearer paramTypeBearer, LocalItem paramLocalItem) {
    return intern(paramInt, paramTypeBearer, paramLocalItem);
  }
  
  public static String regString(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("v");
    stringBuilder.append(paramInt);
    return stringBuilder.toString();
  }
  
  private String toString0(boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder(40);
    stringBuilder.append(regString());
    stringBuilder.append(":");
    LocalItem localItem = this.local;
    if (localItem != null)
      stringBuilder.append(localItem.toString()); 
    Type type = this.type.getType();
    stringBuilder.append(type);
    if (type != this.type) {
      stringBuilder.append("=");
      if (paramBoolean) {
        TypeBearer typeBearer = this.type;
        if (typeBearer instanceof CstString) {
          stringBuilder.append(((CstString)typeBearer).toQuoted());
          return stringBuilder.toString();
        } 
      } 
      if (paramBoolean) {
        TypeBearer typeBearer = this.type;
        if (typeBearer instanceof com.android.dx.rop.cst.Constant) {
          stringBuilder.append(typeBearer.toHuman());
          return stringBuilder.toString();
        } 
      } 
      stringBuilder.append(this.type);
    } 
    return stringBuilder.toString();
  }
  
  public int compareTo(RegisterSpec paramRegisterSpec) {
    int i = this.reg;
    int j = paramRegisterSpec.reg;
    byte b = -1;
    if (i < j)
      return -1; 
    if (i > j)
      return 1; 
    if (this == paramRegisterSpec)
      return 0; 
    i = this.type.getType().compareTo(paramRegisterSpec.type.getType());
    if (i != 0)
      return i; 
    LocalItem localItem2 = this.local;
    if (localItem2 == null) {
      if (paramRegisterSpec.local == null)
        b = 0; 
      return b;
    } 
    LocalItem localItem1 = paramRegisterSpec.local;
    return (localItem1 == null) ? 1 : localItem2.compareTo(localItem1);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof RegisterSpec)) {
      if (paramObject instanceof ForComparison) {
        paramObject = paramObject;
        return equals(((ForComparison)paramObject).reg, ((ForComparison)paramObject).type, ((ForComparison)paramObject).local);
      } 
      return false;
    } 
    paramObject = paramObject;
    return equals(((RegisterSpec)paramObject).reg, ((RegisterSpec)paramObject).type, ((RegisterSpec)paramObject).local);
  }
  
  public boolean equalsUsingSimpleType(RegisterSpec paramRegisterSpec) {
    boolean bool = matchesVariable(paramRegisterSpec);
    boolean bool1 = false;
    if (!bool)
      return false; 
    if (this.reg == paramRegisterSpec.reg)
      bool1 = true; 
    return bool1;
  }
  
  public final int getBasicFrameType() {
    return this.type.getBasicFrameType();
  }
  
  public final int getBasicType() {
    return this.type.getBasicType();
  }
  
  public int getCategory() {
    return this.type.getType().getCategory();
  }
  
  public TypeBearer getFrameType() {
    return this.type.getFrameType();
  }
  
  public LocalItem getLocalItem() {
    return this.local;
  }
  
  public int getNextReg() {
    return this.reg + getCategory();
  }
  
  public int getReg() {
    return this.reg;
  }
  
  public Type getType() {
    return this.type.getType();
  }
  
  public TypeBearer getTypeBearer() {
    return this.type;
  }
  
  public int hashCode() {
    return hashCodeOf(this.reg, this.type, this.local);
  }
  
  public RegisterSpec intersect(RegisterSpec paramRegisterSpec, boolean paramBoolean) {
    TypeBearer typeBearer;
    if (this == paramRegisterSpec)
      return this; 
    if (paramRegisterSpec == null || this.reg != paramRegisterSpec.getReg())
      return null; 
    LocalItem localItem = this.local;
    if (localItem == null || !localItem.equals(paramRegisterSpec.getLocalItem())) {
      localItem = null;
    } else {
      localItem = this.local;
    } 
    if (localItem == this.local) {
      i = 1;
    } else {
      i = 0;
    } 
    if (paramBoolean && !i)
      return null; 
    Type type = getType();
    if (type != paramRegisterSpec.getType())
      return null; 
    if (this.type.equals(paramRegisterSpec.getTypeBearer()))
      typeBearer = this.type; 
    if (typeBearer == this.type && i)
      return this; 
    int i = this.reg;
    if (localItem == null) {
      paramRegisterSpec = make(i, typeBearer);
    } else {
      paramRegisterSpec = make(i, typeBearer, localItem);
    } 
    return paramRegisterSpec;
  }
  
  public boolean isCategory1() {
    return this.type.getType().isCategory1();
  }
  
  public boolean isCategory2() {
    return this.type.getType().isCategory2();
  }
  
  public final boolean isConstant() {
    return false;
  }
  
  public boolean isEvenRegister() {
    int i = getReg();
    boolean bool = true;
    if ((i & 0x1) != 0)
      bool = false; 
    return bool;
  }
  
  public boolean matchesVariable(RegisterSpec paramRegisterSpec) {
    boolean bool = false;
    if (paramRegisterSpec == null)
      return false; 
    null = bool;
    if (this.type.getType().equals(paramRegisterSpec.type.getType())) {
      LocalItem localItem2 = this.local;
      LocalItem localItem1 = paramRegisterSpec.local;
      if (localItem2 != localItem1) {
        null = bool;
        if (localItem2 != null) {
          null = bool;
          if (localItem2.equals(localItem1))
            return true; 
        } 
        return null;
      } 
    } else {
      return null;
    } 
    return true;
  }
  
  public String regString() {
    return regString(this.reg);
  }
  
  public String toHuman() {
    return toString0(true);
  }
  
  public String toString() {
    return toString0(false);
  }
  
  public RegisterSpec withLocalItem(LocalItem paramLocalItem) {
    LocalItem localItem = this.local;
    return (localItem == paramLocalItem || (localItem != null && localItem.equals(paramLocalItem))) ? this : makeLocalOptional(this.reg, this.type, paramLocalItem);
  }
  
  public RegisterSpec withOffset(int paramInt) {
    return (paramInt == 0) ? this : withReg(this.reg + paramInt);
  }
  
  public RegisterSpec withReg(int paramInt) {
    return (this.reg == paramInt) ? this : makeLocalOptional(paramInt, this.type, this.local);
  }
  
  public RegisterSpec withSimpleType() {
    Type type1;
    TypeBearer typeBearer = this.type;
    if (typeBearer instanceof Type) {
      type1 = (Type)typeBearer;
    } else {
      type1 = typeBearer.getType();
    } 
    Type type2 = type1;
    if (type1.isUninitialized())
      type2 = type1.getInitializedType(); 
    return (type2 == typeBearer) ? this : makeLocalOptional(this.reg, (TypeBearer)type2, this.local);
  }
  
  public RegisterSpec withType(TypeBearer paramTypeBearer) {
    return makeLocalOptional(this.reg, paramTypeBearer, this.local);
  }
  
  private static class ForComparison {
    private LocalItem local;
    
    private int reg;
    
    private TypeBearer type;
    
    private ForComparison() {}
    
    public boolean equals(Object param1Object) {
      return !(param1Object instanceof RegisterSpec) ? false : ((RegisterSpec)param1Object).equals(this.reg, this.type, this.local);
    }
    
    public int hashCode() {
      return RegisterSpec.hashCodeOf(this.reg, this.type, this.local);
    }
    
    public void set(int param1Int, TypeBearer param1TypeBearer, LocalItem param1LocalItem) {
      this.reg = param1Int;
      this.type = param1TypeBearer;
      this.local = param1LocalItem;
    }
    
    public RegisterSpec toRegisterSpec() {
      return new RegisterSpec(this.reg, this.type, this.local);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\RegisterSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */