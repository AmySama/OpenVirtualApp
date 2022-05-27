package com.android.dx;

import com.android.dx.dex.file.ClassDefItem;
import com.android.dx.rop.annotation.Annotation;
import com.android.dx.rop.annotation.AnnotationVisibility;
import com.android.dx.rop.annotation.Annotations;
import com.android.dx.rop.annotation.NameValuePair;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstEnumRef;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Iterator;

public final class AnnotationId<D, V> {
  private final ElementType annotatedElement;
  
  private final TypeId<D> declaringType;
  
  private final HashMap<String, NameValuePair> elements;
  
  private final TypeId<V> type;
  
  private AnnotationId(TypeId<D> paramTypeId, TypeId<V> paramTypeId1, ElementType paramElementType) {
    this.declaringType = paramTypeId;
    this.type = paramTypeId1;
    this.annotatedElement = paramElementType;
    this.elements = new HashMap<String, NameValuePair>();
  }
  
  public static <D, V> AnnotationId<D, V> get(TypeId<D> paramTypeId, TypeId<V> paramTypeId1, ElementType paramElementType) {
    if (paramElementType == ElementType.TYPE || paramElementType == ElementType.METHOD || paramElementType == ElementType.FIELD || paramElementType == ElementType.PARAMETER)
      return new AnnotationId<D, V>(paramTypeId, paramTypeId1, paramElementType); 
    throw new IllegalArgumentException("element type is not supported to annotate yet.");
  }
  
  public void addToMethod(DexMaker paramDexMaker, MethodId<?, ?> paramMethodId) {
    if (this.annotatedElement == ElementType.METHOD) {
      Iterator<NameValuePair> iterator;
      if (paramMethodId.declaringType.equals(this.declaringType)) {
        ClassDefItem classDefItem = paramDexMaker.getTypeDeclaration(this.declaringType).toClassDefItem();
        if (classDefItem != null) {
          CstMethodRef cstMethodRef = paramMethodId.constant;
          if (cstMethodRef != null) {
            Annotation annotation = new Annotation(CstType.intern(this.type.ropType), AnnotationVisibility.RUNTIME);
            Annotations annotations = new Annotations();
            iterator = this.elements.values().iterator();
            while (iterator.hasNext())
              annotation.add(iterator.next()); 
            annotations.add(annotation);
            classDefItem.addMethodAnnotations(cstMethodRef, annotations, paramDexMaker.getDexFile());
            return;
          } 
          throw new NullPointerException("Method reference is NULL");
        } 
        throw new NullPointerException("No class defined item is found");
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Method");
      stringBuilder.append(iterator);
      stringBuilder.append("'s declaring type is inconsistent with");
      stringBuilder.append(this);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new IllegalStateException("This annotation is not for method");
  }
  
  public void set(Element paramElement) {
    if (paramElement != null) {
      NameValuePair nameValuePair = new NameValuePair(new CstString(paramElement.getName()), Element.toConstant(paramElement.getValue()));
      this.elements.put(paramElement.getName(), nameValuePair);
      return;
    } 
    throw new NullPointerException("element == null");
  }
  
  public static final class Element {
    private final String name;
    
    private final Object value;
    
    public Element(String param1String, Object param1Object) {
      if (param1String != null) {
        if (param1Object != null) {
          this.name = param1String;
          this.value = param1Object;
          return;
        } 
        throw new NullPointerException("value == null");
      } 
      throw new NullPointerException("name == null");
    }
    
    static Constant toConstant(Object param1Object) {
      CstString cstString;
      Class<?> clazz = param1Object.getClass();
      if (clazz.isEnum()) {
        cstString = new CstString(TypeId.get(clazz).getName());
        return (Constant)new CstEnumRef(new CstNat(new CstString(((Enum)param1Object).name()), cstString));
      } 
      if (!cstString.isArray()) {
        if (!(param1Object instanceof TypeId))
          return (Constant)Constants.getConstant(param1Object); 
        throw new UnsupportedOperationException("TypeId is not supported yet");
      } 
      throw new UnsupportedOperationException("Array is not supported yet");
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = param1Object instanceof Element;
      boolean bool1 = false;
      if (!bool)
        return false; 
      param1Object = param1Object;
      bool = bool1;
      if (this.name.equals(((Element)param1Object).name)) {
        bool = bool1;
        if (this.value.equals(((Element)param1Object).value))
          bool = true; 
      } 
      return bool;
    }
    
    public String getName() {
      return this.name;
    }
    
    public Object getValue() {
      return this.value;
    }
    
    public int hashCode() {
      return this.name.hashCode() * 31 + this.value.hashCode();
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("[");
      stringBuilder.append(this.name);
      stringBuilder.append(", ");
      stringBuilder.append(this.value);
      stringBuilder.append("]");
      return stringBuilder.toString();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\AnnotationId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */