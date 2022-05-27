package com.android.dx.dex.file;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstArray;
import com.android.dx.rop.cst.CstLiteralBits;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.cst.Zeroes;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.ByteArrayAnnotatedOutput;
import com.android.dx.util.Writers;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public final class ClassDataItem extends OffsettedItem {
  private final ArrayList<EncodedMethod> directMethods;
  
  private byte[] encodedForm;
  
  private final ArrayList<EncodedField> instanceFields;
  
  private final ArrayList<EncodedField> staticFields;
  
  private final HashMap<EncodedField, Constant> staticValues;
  
  private CstArray staticValuesConstant;
  
  private final CstType thisClass;
  
  private final ArrayList<EncodedMethod> virtualMethods;
  
  public ClassDataItem(CstType paramCstType) {
    super(1, -1);
    if (paramCstType != null) {
      this.thisClass = paramCstType;
      this.staticFields = new ArrayList<EncodedField>(20);
      this.staticValues = new HashMap<EncodedField, Constant>(40);
      this.instanceFields = new ArrayList<EncodedField>(20);
      this.directMethods = new ArrayList<EncodedMethod>(20);
      this.virtualMethods = new ArrayList<EncodedMethod>(20);
      this.staticValuesConstant = null;
      return;
    } 
    throw new NullPointerException("thisClass == null");
  }
  
  private static void encodeList(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput, String paramString, ArrayList<? extends EncodedMember> paramArrayList) {
    int i = paramArrayList.size();
    if (i == 0)
      return; 
    boolean bool = paramAnnotatedOutput.annotates();
    byte b = 0;
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  ");
      stringBuilder.append(paramString);
      stringBuilder.append(":");
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
    } 
    int j = 0;
    while (b < i) {
      j = ((EncodedMember)paramArrayList.get(b)).encode(paramDexFile, paramAnnotatedOutput, j, b);
      b++;
    } 
  }
  
  private void encodeOutput(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    boolean bool = paramAnnotatedOutput.annotates();
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(offsetString());
      stringBuilder.append(" class data for ");
      stringBuilder.append(this.thisClass.toHuman());
      paramAnnotatedOutput.annotate(0, stringBuilder.toString());
    } 
    encodeSize(paramDexFile, paramAnnotatedOutput, "static_fields", this.staticFields.size());
    encodeSize(paramDexFile, paramAnnotatedOutput, "instance_fields", this.instanceFields.size());
    encodeSize(paramDexFile, paramAnnotatedOutput, "direct_methods", this.directMethods.size());
    encodeSize(paramDexFile, paramAnnotatedOutput, "virtual_methods", this.virtualMethods.size());
    encodeList(paramDexFile, paramAnnotatedOutput, "static_fields", (ArrayList)this.staticFields);
    encodeList(paramDexFile, paramAnnotatedOutput, "instance_fields", (ArrayList)this.instanceFields);
    encodeList(paramDexFile, paramAnnotatedOutput, "direct_methods", (ArrayList)this.directMethods);
    encodeList(paramDexFile, paramAnnotatedOutput, "virtual_methods", (ArrayList)this.virtualMethods);
    if (bool)
      paramAnnotatedOutput.endAnnotation(); 
  }
  
  private static void encodeSize(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput, String paramString, int paramInt) {
    if (paramAnnotatedOutput.annotates()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append("_size:");
      paramAnnotatedOutput.annotate(String.format("  %-21s %08x", new Object[] { stringBuilder.toString(), Integer.valueOf(paramInt) }));
    } 
    paramAnnotatedOutput.writeUleb128(paramInt);
  }
  
  private CstArray makeStaticValuesConstant() {
    Collections.sort(this.staticFields);
    int i;
    for (i = this.staticFields.size(); i > 0; i--) {
      EncodedField encodedField = this.staticFields.get(i - 1);
      Constant constant = this.staticValues.get(encodedField);
      if ((constant instanceof CstLiteralBits) ? (((CstLiteralBits)constant).getLongBits() != 0L) : (constant != null))
        break; 
    } 
    if (i == 0)
      return null; 
    CstArray.List list = new CstArray.List(i);
    for (byte b = 0; b < i; b++) {
      EncodedField encodedField = this.staticFields.get(b);
      Constant constant2 = this.staticValues.get(encodedField);
      Constant constant1 = constant2;
      if (constant2 == null)
        constant1 = Zeroes.zeroFor(encodedField.getRef().getType()); 
      list.set(b, constant1);
    } 
    list.setImmutable();
    return new CstArray(list);
  }
  
  public void addContents(DexFile paramDexFile) {
    if (!this.staticFields.isEmpty()) {
      getStaticValuesConstant();
      Iterator<EncodedField> iterator = this.staticFields.iterator();
      while (iterator.hasNext())
        ((EncodedField)iterator.next()).addContents(paramDexFile); 
    } 
    if (!this.instanceFields.isEmpty()) {
      Collections.sort(this.instanceFields);
      Iterator<EncodedField> iterator = this.instanceFields.iterator();
      while (iterator.hasNext())
        ((EncodedField)iterator.next()).addContents(paramDexFile); 
    } 
    if (!this.directMethods.isEmpty()) {
      Collections.sort(this.directMethods);
      Iterator<EncodedMethod> iterator = this.directMethods.iterator();
      while (iterator.hasNext())
        ((EncodedMethod)iterator.next()).addContents(paramDexFile); 
    } 
    if (!this.virtualMethods.isEmpty()) {
      Collections.sort(this.virtualMethods);
      Iterator<EncodedMethod> iterator = this.virtualMethods.iterator();
      while (iterator.hasNext())
        ((EncodedMethod)iterator.next()).addContents(paramDexFile); 
    } 
  }
  
  public void addDirectMethod(EncodedMethod paramEncodedMethod) {
    if (paramEncodedMethod != null) {
      this.directMethods.add(paramEncodedMethod);
      return;
    } 
    throw new NullPointerException("method == null");
  }
  
  public void addInstanceField(EncodedField paramEncodedField) {
    if (paramEncodedField != null) {
      this.instanceFields.add(paramEncodedField);
      return;
    } 
    throw new NullPointerException("field == null");
  }
  
  public void addStaticField(EncodedField paramEncodedField, Constant paramConstant) {
    if (paramEncodedField != null) {
      if (this.staticValuesConstant == null) {
        this.staticFields.add(paramEncodedField);
        this.staticValues.put(paramEncodedField, paramConstant);
        return;
      } 
      throw new UnsupportedOperationException("static fields already sorted");
    } 
    throw new NullPointerException("field == null");
  }
  
  public void addVirtualMethod(EncodedMethod paramEncodedMethod) {
    if (paramEncodedMethod != null) {
      this.virtualMethods.add(paramEncodedMethod);
      return;
    } 
    throw new NullPointerException("method == null");
  }
  
  public void debugPrint(Writer paramWriter, boolean paramBoolean) {
    paramWriter = Writers.printWriterFor(paramWriter);
    int i = this.staticFields.size();
    boolean bool = false;
    byte b;
    for (b = 0; b < i; b++) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  sfields[");
      stringBuilder.append(b);
      stringBuilder.append("]: ");
      stringBuilder.append(this.staticFields.get(b));
      paramWriter.println(stringBuilder.toString());
    } 
    i = this.instanceFields.size();
    for (b = 0; b < i; b++) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  ifields[");
      stringBuilder.append(b);
      stringBuilder.append("]: ");
      stringBuilder.append(this.instanceFields.get(b));
      paramWriter.println(stringBuilder.toString());
    } 
    i = this.directMethods.size();
    for (b = 0; b < i; b++) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  dmeths[");
      stringBuilder.append(b);
      stringBuilder.append("]:");
      paramWriter.println(stringBuilder.toString());
      ((EncodedMethod)this.directMethods.get(b)).debugPrint((PrintWriter)paramWriter, paramBoolean);
    } 
    i = this.virtualMethods.size();
    for (b = bool; b < i; b++) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("  vmeths[");
      stringBuilder.append(b);
      stringBuilder.append("]:");
      paramWriter.println(stringBuilder.toString());
      ((EncodedMethod)this.virtualMethods.get(b)).debugPrint((PrintWriter)paramWriter, paramBoolean);
    } 
  }
  
  public ArrayList<EncodedMethod> getMethods() {
    ArrayList<EncodedMethod> arrayList = new ArrayList(this.directMethods.size() + this.virtualMethods.size());
    arrayList.addAll(this.directMethods);
    arrayList.addAll(this.virtualMethods);
    return arrayList;
  }
  
  public CstArray getStaticValuesConstant() {
    if (this.staticValuesConstant == null && this.staticFields.size() != 0)
      this.staticValuesConstant = makeStaticValuesConstant(); 
    return this.staticValuesConstant;
  }
  
  public boolean isEmpty() {
    boolean bool;
    if (this.staticFields.isEmpty() && this.instanceFields.isEmpty() && this.directMethods.isEmpty() && this.virtualMethods.isEmpty()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public ItemType itemType() {
    return ItemType.TYPE_CLASS_DATA_ITEM;
  }
  
  protected void place0(Section paramSection, int paramInt) {
    ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput();
    encodeOutput(paramSection.getFile(), (AnnotatedOutput)byteArrayAnnotatedOutput);
    byte[] arrayOfByte = byteArrayAnnotatedOutput.toByteArray();
    this.encodedForm = arrayOfByte;
    setWriteSize(arrayOfByte.length);
  }
  
  public String toHuman() {
    return toString();
  }
  
  public void writeTo0(DexFile paramDexFile, AnnotatedOutput paramAnnotatedOutput) {
    if (paramAnnotatedOutput.annotates()) {
      encodeOutput(paramDexFile, paramAnnotatedOutput);
    } else {
      paramAnnotatedOutput.write(this.encodedForm);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\ClassDataItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */