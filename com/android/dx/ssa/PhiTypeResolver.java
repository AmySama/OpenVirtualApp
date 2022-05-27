package com.android.dx.ssa;

import com.android.dx.cf.code.Merger;
import com.android.dx.rop.code.LocalItem;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import java.util.BitSet;
import java.util.List;

public class PhiTypeResolver {
  SsaMethod ssaMeth;
  
  private final BitSet worklist;
  
  private PhiTypeResolver(SsaMethod paramSsaMethod) {
    this.ssaMeth = paramSsaMethod;
    this.worklist = new BitSet(paramSsaMethod.getRegCount());
  }
  
  private static boolean equalsHandlesNulls(LocalItem paramLocalItem1, LocalItem paramLocalItem2) {
    return (paramLocalItem1 == paramLocalItem2 || (paramLocalItem1 != null && paramLocalItem1.equals(paramLocalItem2)));
  }
  
  public static void process(SsaMethod paramSsaMethod) {
    (new PhiTypeResolver(paramSsaMethod)).run();
  }
  
  private void run() {
    int i = this.ssaMeth.getRegCount();
    int j;
    for (j = 0; j < i; j++) {
      SsaInsn ssaInsn = this.ssaMeth.getDefinitionForRegister(j);
      if (ssaInsn != null && ssaInsn.getResult().getBasicType() == 0)
        this.worklist.set(j); 
    } 
    while (true) {
      j = this.worklist.nextSetBit(0);
      if (j >= 0) {
        this.worklist.clear(j);
        if (resolveResultType((PhiInsn)this.ssaMeth.getDefinitionForRegister(j))) {
          List<SsaInsn> list = this.ssaMeth.getUseListForRegister(j);
          i = list.size();
          for (j = 0; j < i; j++) {
            SsaInsn ssaInsn = list.get(j);
            RegisterSpec registerSpec = ssaInsn.getResult();
            if (registerSpec != null && ssaInsn instanceof PhiInsn)
              this.worklist.set(registerSpec.getReg()); 
          } 
        } 
        continue;
      } 
      break;
    } 
  }
  
  boolean resolveResultType(PhiInsn paramPhiInsn) {
    TypeBearer typeBearer;
    paramPhiInsn.updateSourcesToDefinitions(this.ssaMeth);
    RegisterSpecList registerSpecList = paramPhiInsn.getSources();
    int i = registerSpecList.size();
    LocalItem localItem1 = null;
    boolean bool = false;
    byte b = -1;
    RegisterSpec registerSpec = null;
    byte b1;
    for (b1 = 0; b1 < i; b1++) {
      RegisterSpec registerSpec1 = registerSpecList.get(b1);
      if (registerSpec1.getBasicType() != 0) {
        b = b1;
        registerSpec = registerSpec1;
      } 
    } 
    if (registerSpec == null)
      return false; 
    LocalItem localItem2 = registerSpec.getLocalItem();
    Type type = registerSpec.getType();
    byte b2 = 0;
    b1 = 1;
    while (b2 < i) {
      if (b2 != b) {
        RegisterSpec registerSpec1 = registerSpecList.get(b2);
        if (registerSpec1.getBasicType() != 0) {
          if (b1 != 0 && equalsHandlesNulls(localItem2, registerSpec1.getLocalItem())) {
            b1 = 1;
          } else {
            b1 = 0;
          } 
          typeBearer = Merger.mergeType((TypeBearer)type, (TypeBearer)registerSpec1.getType());
        } 
      } 
      b2++;
    } 
    if (typeBearer != null) {
      if (b1 != 0)
        localItem1 = localItem2; 
      RegisterSpec registerSpec1 = paramPhiInsn.getResult();
      if (registerSpec1.getTypeBearer() == typeBearer && equalsHandlesNulls(localItem1, registerSpec1.getLocalItem()))
        return false; 
      paramPhiInsn.changeResultType(typeBearer, localItem1);
      return true;
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    for (b1 = bool; b1 < i; b1++) {
      stringBuilder1.append(registerSpecList.get(b1).toString());
      stringBuilder1.append(' ');
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("Couldn't map types in phi insn:");
    stringBuilder2.append(stringBuilder1);
    throw new RuntimeException(stringBuilder2.toString());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\PhiTypeResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */