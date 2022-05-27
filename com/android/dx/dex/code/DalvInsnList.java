package com.android.dx.dex.code;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstBaseMethodRef;
import com.android.dx.rop.cst.CstCallSiteRef;
import com.android.dx.rop.cst.CstProtoRef;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.FixedSizeList;
import com.android.dx.util.IndentingWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public final class DalvInsnList extends FixedSizeList {
  private final int regCount;
  
  public DalvInsnList(int paramInt1, int paramInt2) {
    super(paramInt1);
    this.regCount = paramInt2;
  }
  
  public static DalvInsnList makeImmutable(ArrayList<DalvInsn> paramArrayList, int paramInt) {
    int i = paramArrayList.size();
    DalvInsnList dalvInsnList = new DalvInsnList(i, paramInt);
    for (paramInt = 0; paramInt < i; paramInt++)
      dalvInsnList.set(paramInt, paramArrayList.get(paramInt)); 
    dalvInsnList.setImmutable();
    return dalvInsnList;
  }
  
  public int codeSize() {
    int i = size();
    return (i == 0) ? 0 : get(i - 1).getNextAddress();
  }
  
  public void debugPrint(OutputStream paramOutputStream, String paramString, boolean paramBoolean) {
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(paramOutputStream);
    debugPrint(outputStreamWriter, paramString, paramBoolean);
    try {
      outputStreamWriter.flush();
      return;
    } catch (IOException iOException) {
      throw new RuntimeException(iOException);
    } 
  }
  
  public void debugPrint(Writer paramWriter, String paramString, boolean paramBoolean) {
    IndentingWriter indentingWriter = new IndentingWriter(paramWriter, 0, paramString);
    int i = size();
    byte b = 0;
    while (true) {
      if (b < i) {
        try {
          DalvInsn dalvInsn = (DalvInsn)get0(b);
          if (dalvInsn.codeSize() != 0 || paramBoolean) {
            String str = dalvInsn.listingString("", 0, paramBoolean);
          } else {
            dalvInsn = null;
          } 
          if (dalvInsn != null)
            indentingWriter.write((String)dalvInsn); 
          b++;
        } catch (IOException iOException) {
          throw new RuntimeException(iOException);
        } 
        continue;
      } 
      indentingWriter.flush();
      return;
    } 
  }
  
  public DalvInsn get(int paramInt) {
    return (DalvInsn)get0(paramInt);
  }
  
  public int getOutsSize() {
    Object object;
    int i = size();
    byte b = 0;
    boolean bool = false;
    while (b < i) {
      int j;
      DalvInsn dalvInsn = (DalvInsn)get0(b);
      boolean bool1 = dalvInsn instanceof CstInsn;
      boolean bool2 = true;
      if (bool1) {
        CstBaseMethodRef cstBaseMethodRef;
        Constant constant = ((CstInsn)dalvInsn).getConstant();
        if (constant instanceof CstBaseMethodRef) {
          cstBaseMethodRef = (CstBaseMethodRef)constant;
          if (dalvInsn.getOpcode().getFamily() != 113)
            bool2 = false; 
          j = cstBaseMethodRef.getParameterWordCount(bool2);
        } else if (cstBaseMethodRef instanceof CstCallSiteRef) {
          j = ((CstCallSiteRef)cstBaseMethodRef).getPrototype().getParameterTypes().getWordCount();
        } else {
          j = 0;
        } 
      } else {
        Object object2 = object;
        if (dalvInsn instanceof MultiCstInsn) {
          if (dalvInsn.getOpcode().getFamily() == 250) {
            j = ((CstProtoRef)((MultiCstInsn)dalvInsn).getConstant(1)).getPrototype().getParameterTypes().getWordCount() + 1;
          } else {
            throw new RuntimeException("Expecting invoke-polymorphic");
          } 
        } else {
          continue;
        } 
      } 
      Object object1 = object;
      if (j > object)
        int k = j; 
      continue;
      b++;
      object = SYNTHETIC_LOCAL_VARIABLE_9;
    } 
    return object;
  }
  
  public int getRegistersSize() {
    return this.regCount;
  }
  
  public void set(int paramInt, DalvInsn paramDalvInsn) {
    set0(paramInt, paramDalvInsn);
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput) {
    int i = paramAnnotatedOutput.getCursor();
    int j = size();
    boolean bool = paramAnnotatedOutput.annotates();
    byte b = 0;
    int k = b;
    if (bool) {
      bool = paramAnnotatedOutput.isVerbose();
      byte b1 = 0;
      while (true) {
        k = b;
        if (b1 < j) {
          DalvInsn dalvInsn = (DalvInsn)get0(b1);
          k = dalvInsn.codeSize() * 2;
          if (k != 0 || bool) {
            String str = dalvInsn.listingString("  ", paramAnnotatedOutput.getAnnotationWidth(), true);
          } else {
            dalvInsn = null;
          } 
          if (dalvInsn != null) {
            paramAnnotatedOutput.annotate(k, (String)dalvInsn);
          } else if (k != 0) {
            paramAnnotatedOutput.annotate(k, "");
          } 
          b1++;
          continue;
        } 
        break;
      } 
    } 
    while (k < j) {
      DalvInsn dalvInsn = (DalvInsn)get0(k);
      try {
        dalvInsn.writeTo(paramAnnotatedOutput);
        k++;
      } catch (RuntimeException runtimeException) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("...while writing ");
        stringBuilder1.append(dalvInsn);
        throw ExceptionWithContext.withContext(runtimeException, stringBuilder1.toString());
      } 
    } 
    int m = (runtimeException.getCursor() - i) / 2;
    if (m == codeSize())
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("write length mismatch; expected ");
    stringBuilder.append(codeSize());
    stringBuilder.append(" but actually wrote ");
    stringBuilder.append(m);
    throw new RuntimeException(stringBuilder.toString());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\DalvInsnList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */