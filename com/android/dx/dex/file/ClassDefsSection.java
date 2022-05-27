package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

public final class ClassDefsSection extends UniformItemSection {
  private final TreeMap<Type, ClassDefItem> classDefs = new TreeMap<Type, ClassDefItem>();
  
  private ArrayList<ClassDefItem> orderedDefs = null;
  
  public ClassDefsSection(DexFile paramDexFile) {
    super("class_defs", paramDexFile, 4);
  }
  
  private int orderItems0(Type paramType, int paramInt1, int paramInt2) {
    TypeList typeList;
    ClassDefItem classDefItem = this.classDefs.get(paramType);
    if (classDefItem == null || classDefItem.hasIndex())
      return paramInt1; 
    if (paramInt2 >= 0) {
      int i = paramInt2 - 1;
      CstType cstType = classDefItem.getSuperclass();
      paramInt2 = paramInt1;
      if (cstType != null)
        paramInt2 = orderItems0(cstType.getClassType(), paramInt1, i); 
      typeList = classDefItem.getInterfaces();
      int j = typeList.size();
      for (paramInt1 = 0; paramInt1 < j; paramInt1++)
        paramInt2 = orderItems0(typeList.getType(paramInt1), paramInt2, i); 
      classDefItem.setIndex(paramInt2);
      this.orderedDefs.add(classDefItem);
      return paramInt2 + 1;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("class circularity with ");
    stringBuilder.append(typeList);
    throw new RuntimeException(stringBuilder.toString());
  }
  
  public void add(ClassDefItem paramClassDefItem) {
    try {
      Type type = paramClassDefItem.getThisClass().getClassType();
      throwIfPrepared();
      if (this.classDefs.get(type) == null) {
        this.classDefs.put(type, paramClassDefItem);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("already added: ");
      stringBuilder.append(type);
      throw new IllegalArgumentException(stringBuilder.toString());
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("clazz == null");
    } 
  }
  
  public IndexedItem get(Constant paramConstant) {
    if (paramConstant != null) {
      throwIfNotPrepared();
      Type type = ((CstType)paramConstant).getClassType();
      IndexedItem indexedItem = this.classDefs.get(type);
      if (indexedItem != null)
        return indexedItem; 
      throw new IllegalArgumentException("not found");
    } 
    throw new NullPointerException("cst == null");
  }
  
  public Collection<? extends Item> items() {
    ArrayList<ClassDefItem> arrayList = this.orderedDefs;
    return (Collection<? extends Item>)((arrayList != null) ? arrayList : this.classDefs.values());
  }
  
  protected void orderItems() {
    int i = this.classDefs.size();
    this.orderedDefs = new ArrayList<ClassDefItem>(i);
    Iterator<Type> iterator = this.classDefs.keySet().iterator();
    for (int j = 0; iterator.hasNext(); j = orderItems0(iterator.next(), j, i - j));
  }
  
  public void writeHeaderPart(AnnotatedOutput paramAnnotatedOutput) {
    int j;
    throwIfNotPrepared();
    int i = this.classDefs.size();
    if (i == 0) {
      j = 0;
    } else {
      j = getFileOffset();
    } 
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("class_defs_size: ");
      stringBuilder.append(Hex.u4(i));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append("class_defs_off:  ");
      stringBuilder.append(Hex.u4(j));
      paramAnnotatedOutput.annotate(4, stringBuilder.toString());
    } 
    paramAnnotatedOutput.writeInt(i);
    paramAnnotatedOutput.writeInt(j);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\ClassDefsSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */