package external.org.apache.commons.lang3.tuple;

import external.org.apache.commons.lang3.ObjectUtils;
import external.org.apache.commons.lang3.builder.CompareToBuilder;
import java.io.Serializable;
import java.util.Map;

public abstract class Pair<L, R> implements Map.Entry<L, R>, Comparable<Pair<L, R>>, Serializable {
  private static final long serialVersionUID = 4954918890077093841L;
  
  public static <L, R> Pair<L, R> of(L paramL, R paramR) {
    return new ImmutablePair<L, R>(paramL, paramR);
  }
  
  public int compareTo(Pair<L, R> paramPair) {
    return (new CompareToBuilder()).append(getLeft(), paramPair.getLeft()).append(getRight(), paramPair.getRight()).toComparison();
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (paramObject == this)
      return true; 
    if (paramObject instanceof Map.Entry) {
      paramObject = paramObject;
      if (!ObjectUtils.equals(getKey(), paramObject.getKey()) || !ObjectUtils.equals(getValue(), paramObject.getValue()))
        bool = false; 
      return bool;
    } 
    return false;
  }
  
  public final L getKey() {
    return getLeft();
  }
  
  public abstract L getLeft();
  
  public abstract R getRight();
  
  public R getValue() {
    return getRight();
  }
  
  public int hashCode() {
    int j;
    L l = getKey();
    int i = 0;
    if (l == null) {
      j = 0;
    } else {
      j = getKey().hashCode();
    } 
    if (getValue() != null)
      i = getValue().hashCode(); 
    return j ^ i;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('(');
    stringBuilder.append(getLeft());
    stringBuilder.append(',');
    stringBuilder.append(getRight());
    stringBuilder.append(')');
    return stringBuilder.toString();
  }
  
  public String toString(String paramString) {
    return String.format(paramString, new Object[] { getLeft(), getRight() });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\tuple\Pair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */