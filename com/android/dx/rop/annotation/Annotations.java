package com.android.dx.rop.annotation;

import com.android.dx.rop.cst.CstType;
import com.android.dx.util.MutabilityControl;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

public final class Annotations extends MutabilityControl implements Comparable<Annotations> {
  public static final Annotations EMPTY;
  
  private final TreeMap<CstType, Annotation> annotations = new TreeMap<CstType, Annotation>();
  
  static {
    Annotations annotations = new Annotations();
    EMPTY = annotations;
    annotations.setImmutable();
  }
  
  public static Annotations combine(Annotations paramAnnotations, Annotation paramAnnotation) {
    Annotations annotations = new Annotations();
    annotations.addAll(paramAnnotations);
    annotations.add(paramAnnotation);
    annotations.setImmutable();
    return annotations;
  }
  
  public static Annotations combine(Annotations paramAnnotations1, Annotations paramAnnotations2) {
    Annotations annotations = new Annotations();
    annotations.addAll(paramAnnotations1);
    annotations.addAll(paramAnnotations2);
    annotations.setImmutable();
    return annotations;
  }
  
  public void add(Annotation paramAnnotation) {
    throwIfImmutable();
    if (paramAnnotation != null) {
      CstType cstType = paramAnnotation.getType();
      if (!this.annotations.containsKey(cstType)) {
        this.annotations.put(cstType, paramAnnotation);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("duplicate type: ");
      stringBuilder.append(cstType.toHuman());
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new NullPointerException("annotation == null");
  }
  
  public void addAll(Annotations paramAnnotations) {
    throwIfImmutable();
    if (paramAnnotations != null) {
      Iterator<Annotation> iterator = paramAnnotations.annotations.values().iterator();
      while (iterator.hasNext())
        add(iterator.next()); 
      return;
    } 
    throw new NullPointerException("toAdd == null");
  }
  
  public int compareTo(Annotations paramAnnotations) {
    Iterator<Annotation> iterator2 = this.annotations.values().iterator();
    Iterator<Annotation> iterator1 = paramAnnotations.annotations.values().iterator();
    while (iterator2.hasNext() && iterator1.hasNext()) {
      int i = ((Annotation)iterator2.next()).compareTo(iterator1.next());
      if (i != 0)
        return i; 
    } 
    return iterator2.hasNext() ? 1 : (iterator1.hasNext() ? -1 : 0);
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof Annotations))
      return false; 
    paramObject = paramObject;
    return this.annotations.equals(((Annotations)paramObject).annotations);
  }
  
  public Collection<Annotation> getAnnotations() {
    return Collections.unmodifiableCollection(this.annotations.values());
  }
  
  public int hashCode() {
    return this.annotations.hashCode();
  }
  
  public int size() {
    return this.annotations.size();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("annotations{");
    Iterator<Annotation> iterator = this.annotations.values().iterator();
    boolean bool = true;
    while (iterator.hasNext()) {
      Annotation annotation = iterator.next();
      if (bool) {
        bool = false;
      } else {
        stringBuilder.append(", ");
      } 
      stringBuilder.append(annotation.toHuman());
    } 
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\annotation\Annotations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */