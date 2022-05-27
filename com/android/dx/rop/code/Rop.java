package com.android.dx.rop.code;

import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.Hex;

public final class Rop {
  public static final int BRANCH_GOTO = 3;
  
  public static final int BRANCH_IF = 4;
  
  public static final int BRANCH_MAX = 6;
  
  public static final int BRANCH_MIN = 1;
  
  public static final int BRANCH_NONE = 1;
  
  public static final int BRANCH_RETURN = 2;
  
  public static final int BRANCH_SWITCH = 5;
  
  public static final int BRANCH_THROW = 6;
  
  private final int branchingness;
  
  private final TypeList exceptions;
  
  private final boolean isCallLike;
  
  private final String nickname;
  
  private final int opcode;
  
  private final Type result;
  
  private final TypeList sources;
  
  public Rop(int paramInt1, Type paramType, TypeList paramTypeList, int paramInt2, String paramString) {
    this(paramInt1, paramType, paramTypeList, (TypeList)StdTypeList.EMPTY, paramInt2, false, paramString);
  }
  
  public Rop(int paramInt1, Type paramType, TypeList paramTypeList1, TypeList paramTypeList2, int paramInt2, String paramString) {
    this(paramInt1, paramType, paramTypeList1, paramTypeList2, paramInt2, false, paramString);
  }
  
  public Rop(int paramInt1, Type paramType, TypeList paramTypeList1, TypeList paramTypeList2, int paramInt2, boolean paramBoolean, String paramString) {
    if (paramType != null) {
      if (paramTypeList1 != null) {
        if (paramTypeList2 != null) {
          if (paramInt2 >= 1 && paramInt2 <= 6) {
            if (paramTypeList2.size() == 0 || paramInt2 == 6) {
              this.opcode = paramInt1;
              this.result = paramType;
              this.sources = paramTypeList1;
              this.exceptions = paramTypeList2;
              this.branchingness = paramInt2;
              this.isCallLike = paramBoolean;
              this.nickname = paramString;
              return;
            } 
            throw new IllegalArgumentException("exceptions / branchingness mismatch");
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("invalid branchingness: ");
          stringBuilder.append(paramInt2);
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        throw new NullPointerException("exceptions == null");
      } 
      throw new NullPointerException("sources == null");
    } 
    throw new NullPointerException("result == null");
  }
  
  public Rop(int paramInt, Type paramType, TypeList paramTypeList1, TypeList paramTypeList2, String paramString) {
    this(paramInt, paramType, paramTypeList1, paramTypeList2, 6, false, paramString);
  }
  
  public Rop(int paramInt, Type paramType, TypeList paramTypeList, String paramString) {
    this(paramInt, paramType, paramTypeList, (TypeList)StdTypeList.EMPTY, 1, false, paramString);
  }
  
  public Rop(int paramInt, TypeList paramTypeList1, TypeList paramTypeList2) {
    this(paramInt, Type.VOID, paramTypeList1, paramTypeList2, 6, true, null);
  }
  
  public final boolean canThrow() {
    boolean bool;
    if (this.exceptions.size() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof Rop))
      return false; 
    paramObject = paramObject;
    if (this.opcode != ((Rop)paramObject).opcode || this.branchingness != ((Rop)paramObject).branchingness || this.result != ((Rop)paramObject).result || !this.sources.equals(((Rop)paramObject).sources) || !this.exceptions.equals(((Rop)paramObject).exceptions))
      bool = false; 
    return bool;
  }
  
  public int getBranchingness() {
    return this.branchingness;
  }
  
  public TypeList getExceptions() {
    return this.exceptions;
  }
  
  public String getNickname() {
    String str = this.nickname;
    return (str != null) ? str : toString();
  }
  
  public int getOpcode() {
    return this.opcode;
  }
  
  public Type getResult() {
    return this.result;
  }
  
  public TypeList getSources() {
    return this.sources;
  }
  
  public int hashCode() {
    return (((this.opcode * 31 + this.branchingness) * 31 + this.result.hashCode()) * 31 + this.sources.hashCode()) * 31 + this.exceptions.hashCode();
  }
  
  public boolean isCallLike() {
    return this.isCallLike;
  }
  
  public boolean isCommutative() {
    int i = this.opcode;
    if (i != 14 && i != 16)
      switch (i) {
        default:
          return false;
        case 20:
        case 21:
        case 22:
          break;
      }  
    return true;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(40);
    stringBuilder.append("Rop{");
    stringBuilder.append(RegOps.opName(this.opcode));
    if (this.result != Type.VOID) {
      stringBuilder.append(" ");
      stringBuilder.append(this.result);
    } else {
      stringBuilder.append(" .");
    } 
    stringBuilder.append(" <-");
    int i = this.sources.size();
    byte b = 0;
    if (i == 0) {
      stringBuilder.append(" .");
    } else {
      for (byte b1 = 0; b1 < i; b1++) {
        stringBuilder.append(' ');
        stringBuilder.append(this.sources.getType(b1));
      } 
    } 
    if (this.isCallLike)
      stringBuilder.append(" call"); 
    i = this.exceptions.size();
    if (i != 0) {
      stringBuilder.append(" throws");
      for (byte b1 = b; b1 < i; b1++) {
        stringBuilder.append(' ');
        if (this.exceptions.getType(b1) == Type.THROWABLE) {
          stringBuilder.append("<any>");
        } else {
          stringBuilder.append(this.exceptions.getType(b1));
        } 
      } 
    } else {
      int j = this.branchingness;
      if (j != 1) {
        if (j != 2) {
          if (j != 3) {
            if (j != 4) {
              if (j != 5) {
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append(" ");
                stringBuilder1.append(Hex.u1(this.branchingness));
                stringBuilder.append(stringBuilder1.toString());
              } else {
                stringBuilder.append(" switches");
              } 
            } else {
              stringBuilder.append(" ifs");
            } 
          } else {
            stringBuilder.append(" gotos");
          } 
        } else {
          stringBuilder.append(" returns");
        } 
      } else {
        stringBuilder.append(" flows");
      } 
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\Rop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */