package com.android.dx.util;

import java.util.Arrays;

public class FixedSizeList extends MutabilityControl implements ToHuman {
  private Object[] arr;
  
  public FixedSizeList(int paramInt) {
    super(bool);
    boolean bool;
    try {
      this.arr = new Object[paramInt];
      return;
    } catch (NegativeArraySizeException negativeArraySizeException) {
      throw new IllegalArgumentException("size < 0");
    } 
  }
  
  private Object throwIndex(int paramInt) {
    if (paramInt < 0)
      throw new IndexOutOfBoundsException("n < 0"); 
    throw new IndexOutOfBoundsException("n >= size()");
  }
  
  private String toString0(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
    int i = this.arr.length;
    StringBuilder stringBuilder = new StringBuilder(i * 10 + 10);
    if (paramString1 != null)
      stringBuilder.append(paramString1); 
    for (byte b = 0; b < i; b++) {
      if (b != 0 && paramString2 != null)
        stringBuilder.append(paramString2); 
      if (paramBoolean) {
        stringBuilder.append(((ToHuman)this.arr[b]).toHuman());
      } else {
        stringBuilder.append(this.arr[b]);
      } 
    } 
    if (paramString3 != null)
      stringBuilder.append(paramString3); 
    return stringBuilder.toString();
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    return Arrays.equals(this.arr, ((FixedSizeList)paramObject).arr);
  }
  
  protected final Object get0(int paramInt) {
    try {
      Object object = this.arr[paramInt];
      if (object != null)
        return object; 
      NullPointerException nullPointerException = new NullPointerException();
      object = new StringBuilder();
      super();
      object.append("unset: ");
      object.append(paramInt);
      this(object.toString());
      throw nullPointerException;
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      return throwIndex(paramInt);
    } 
  }
  
  protected final Object getOrNull0(int paramInt) {
    return this.arr[paramInt];
  }
  
  public int hashCode() {
    return Arrays.hashCode(this.arr);
  }
  
  protected final void set0(int paramInt, Object paramObject) {
    throwIfImmutable();
    try {
      this.arr[paramInt] = paramObject;
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throwIndex(paramInt);
    } 
  }
  
  public void shrinkToFit() {
    int i = this.arr.length;
    int j = 0;
    int k = 0;
    int m;
    for (m = 0; k < i; m = i1) {
      int i1 = m;
      if (this.arr[k] != null)
        i1 = m + 1; 
      k++;
    } 
    if (i == m)
      return; 
    throwIfImmutable();
    Object[] arrayOfObject = new Object[m];
    int n = 0;
    k = j;
    while (k < i) {
      Object object = this.arr[k];
      j = n;
      if (object != null) {
        arrayOfObject[n] = object;
        j = n + 1;
      } 
      k++;
      n = j;
    } 
    this.arr = arrayOfObject;
    if (m == 0)
      setImmutable(); 
  }
  
  public final int size() {
    return this.arr.length;
  }
  
  public String toHuman() {
    String str = getClass().getName();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str.substring(str.lastIndexOf('.') + 1));
    stringBuilder.append('{');
    return toString0(stringBuilder.toString(), ", ", "}", true);
  }
  
  public String toHuman(String paramString1, String paramString2, String paramString3) {
    return toString0(paramString1, paramString2, paramString3, true);
  }
  
  public String toString() {
    String str = getClass().getName();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str.substring(str.lastIndexOf('.') + 1));
    stringBuilder.append('{');
    return toString0(stringBuilder.toString(), ", ", "}", false);
  }
  
  public String toString(String paramString1, String paramString2, String paramString3) {
    return toString0(paramString1, paramString2, paramString3, false);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\FixedSizeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */