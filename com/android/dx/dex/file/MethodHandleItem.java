package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstBaseMethodRef;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstInterfaceMethodRef;
import com.android.dx.rop.cst.CstMethodHandle;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;

public final class MethodHandleItem extends IndexedItem {
  private final int ITEM_SIZE = 8;
  
  private final CstMethodHandle methodHandle;
  
  public MethodHandleItem(CstMethodHandle paramCstMethodHandle) {
    this.methodHandle = paramCstMethodHandle;
  }
  
  private int getTargetIndex(DexFile paramDexFile) {
    Constant constant = this.methodHandle.getRef();
    if (this.methodHandle.isAccessor())
      return paramDexFile.getFieldIds().indexOf((CstFieldRef)constant); 
    if (this.methodHandle.isInvocation()) {
      CstMethodRef cstMethodRef;
      Constant constant1 = constant;
      if (constant instanceof CstInterfaceMethodRef)
        cstMethodRef = ((CstInterfaceMethodRef)constant).toMethodRef(); 
      return paramDexFile.getMethodIds().indexOf((CstBaseMethodRef)cstMethodRef);
    } 
    throw new IllegalStateException("Unhandled invocation type");
  }
  
  public void addContents(DexFile paramDexFile) {
    paramDexFile.getMethodHandles().intern(this.methodHandle);
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_METHOD_HANDLE_ITEM;
  }
  
  public int writeSize() {
    return 8;
  }
  
  public void writeTo(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    int i = getTargetIndex(paramDexFile);
    int j = this.methodHandle.getMethodHandleType();
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(indexString());
      stringBuilder2.append(' ');
      stringBuilder2.append(this.methodHandle.toString());
      paramAnnotatedOutput.annotate(0, stringBuilder2.toString());
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(" // ");
      stringBuilder2.append(CstMethodHandle.getMethodHandleTypeName(j));
      String str2 = stringBuilder2.toString();
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append("type:     ");
      stringBuilder2.append(Hex.u2(j));
      stringBuilder2.append(str2);
      paramAnnotatedOutput.annotate(2, stringBuilder2.toString());
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append("reserved: ");
      stringBuilder2.append(Hex.u2(0));
      paramAnnotatedOutput.annotate(2, stringBuilder2.toString());
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append(" // ");
      stringBuilder2.append(this.methodHandle.getRef().toString());
      String str1 = stringBuilder2.toString();
      if (this.methodHandle.isAccessor()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fieldId:  ");
        stringBuilder.append(Hex.u2(i));
        stringBuilder.append(str1);
        paramAnnotatedOutput.annotate(2, stringBuilder.toString());
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("methodId: ");
        stringBuilder.append(Hex.u2(i));
        stringBuilder.append(str1);
        paramAnnotatedOutput.annotate(2, stringBuilder.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("reserved: ");
      stringBuilder1.append(Hex.u2(0));
      paramAnnotatedOutput.annotate(2, stringBuilder1.toString());
    } 
    paramAnnotatedOutput.writeShort(j);
    paramAnnotatedOutput.writeShort(0);
    paramAnnotatedOutput.writeShort(getTargetIndex(paramDexFile));
    paramAnnotatedOutput.writeShort(0);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\MethodHandleItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */