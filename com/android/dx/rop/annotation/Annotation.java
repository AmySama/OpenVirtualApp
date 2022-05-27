package com.android.dx.rop.annotation;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.util.MutabilityControl;
import com.android.dx.util.ToHuman;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

public final class Annotation extends MutabilityControl implements Comparable<Annotation>, ToHuman {
  private final TreeMap<CstString, NameValuePair> elements;
  
  private final CstType type;
  
  private final AnnotationVisibility visibility;
  
  public Annotation(CstType paramCstType, AnnotationVisibility paramAnnotationVisibility) {
    if (paramCstType != null) {
      if (paramAnnotationVisibility != null) {
        this.type = paramCstType;
        this.visibility = paramAnnotationVisibility;
        this.elements = new TreeMap<CstString, NameValuePair>();
        return;
      } 
      throw new NullPointerException("visibility == null");
    } 
    throw new NullPointerException("type == null");
  }
  
  public void add(NameValuePair paramNameValuePair) {
    throwIfImmutable();
    if (paramNameValuePair != null) {
      CstString cstString = paramNameValuePair.getName();
      if (this.elements.get(cstString) == null) {
        this.elements.put(cstString, paramNameValuePair);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("name already added: ");
      stringBuilder.append(cstString);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new NullPointerException("pair == null");
  }
  
  public int compareTo(Annotation paramAnnotation) {
    int i = this.type.compareTo((Constant)paramAnnotation.type);
    if (i != 0)
      return i; 
    i = this.visibility.compareTo(paramAnnotation.visibility);
    if (i != 0)
      return i; 
    Iterator<NameValuePair> iterator2 = this.elements.values().iterator();
    Iterator<NameValuePair> iterator1 = paramAnnotation.elements.values().iterator();
    while (iterator2.hasNext() && iterator1.hasNext()) {
      i = ((NameValuePair)iterator2.next()).compareTo(iterator1.next());
      if (i != 0)
        return i; 
    } 
    return iterator2.hasNext() ? 1 : (iterator1.hasNext() ? -1 : 0);
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof Annotation))
      return false; 
    paramObject = paramObject;
    return (!this.type.equals(((Annotation)paramObject).type) || this.visibility != ((Annotation)paramObject).visibility) ? false : this.elements.equals(((Annotation)paramObject).elements);
  }
  
  public Collection<NameValuePair> getNameValuePairs() {
    return Collections.unmodifiableCollection(this.elements.values());
  }
  
  public CstType getType() {
    return this.type;
  }
  
  public AnnotationVisibility getVisibility() {
    return this.visibility;
  }
  
  public int hashCode() {
    return (this.type.hashCode() * 31 + this.elements.hashCode()) * 31 + this.visibility.hashCode();
  }
  
  public void put(NameValuePair paramNameValuePair) {
    throwIfImmutable();
    if (paramNameValuePair != null) {
      this.elements.put(paramNameValuePair.getName(), paramNameValuePair);
      return;
    } 
    throw new NullPointerException("pair == null");
  }
  
  public String toHuman() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.visibility.toHuman());
    stringBuilder.append("-annotation ");
    stringBuilder.append(this.type.toHuman());
    stringBuilder.append(" {");
    Iterator<NameValuePair> iterator = this.elements.values().iterator();
    boolean bool = true;
    while (iterator.hasNext()) {
      NameValuePair nameValuePair = iterator.next();
      if (bool) {
        bool = false;
      } else {
        stringBuilder.append(", ");
      } 
      stringBuilder.append(nameValuePair.getName().toHuman());
      stringBuilder.append(": ");
      stringBuilder.append(nameValuePair.getValue().toHuman());
    } 
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  public String toString() {
    return toHuman();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\annotation\Annotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */