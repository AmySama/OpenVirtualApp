package external.org.apache.commons.lang3.mutable;

public class MutableInt extends Number implements Comparable<MutableInt>, Mutable<Number> {
  private static final long serialVersionUID = 512176391864L;
  
  private int value;
  
  public MutableInt() {}
  
  public MutableInt(int paramInt) {
    this.value = paramInt;
  }
  
  public MutableInt(Number paramNumber) {
    this.value = paramNumber.intValue();
  }
  
  public MutableInt(String paramString) throws NumberFormatException {
    this.value = Integer.parseInt(paramString);
  }
  
  public void add(int paramInt) {
    this.value += paramInt;
  }
  
  public void add(Number paramNumber) {
    this.value += paramNumber.intValue();
  }
  
  public int compareTo(MutableInt paramMutableInt) {
    int i = paramMutableInt.value;
    int j = this.value;
    if (j < i) {
      i = -1;
    } else if (j == i) {
      i = 0;
    } else {
      i = 1;
    } 
    return i;
  }
  
  public void decrement() {
    this.value--;
  }
  
  public double doubleValue() {
    return this.value;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof MutableInt;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (bool) {
      bool2 = bool1;
      if (this.value == ((MutableInt)paramObject).intValue())
        bool2 = true; 
    } 
    return bool2;
  }
  
  public float floatValue() {
    return this.value;
  }
  
  public Integer getValue() {
    return Integer.valueOf(this.value);
  }
  
  public int hashCode() {
    return this.value;
  }
  
  public void increment() {
    this.value++;
  }
  
  public int intValue() {
    return this.value;
  }
  
  public long longValue() {
    return this.value;
  }
  
  public void setValue(int paramInt) {
    this.value = paramInt;
  }
  
  public void setValue(Number paramNumber) {
    this.value = paramNumber.intValue();
  }
  
  public void subtract(int paramInt) {
    this.value -= paramInt;
  }
  
  public void subtract(Number paramNumber) {
    this.value -= paramNumber.intValue();
  }
  
  public Integer toInteger() {
    return Integer.valueOf(intValue());
  }
  
  public String toString() {
    return String.valueOf(this.value);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\mutable\MutableInt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */