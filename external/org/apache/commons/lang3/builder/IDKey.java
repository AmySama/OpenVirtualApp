package external.org.apache.commons.lang3.builder;

final class IDKey {
  private final int id;
  
  private final Object value;
  
  public IDKey(Object paramObject) {
    this.id = System.identityHashCode(paramObject);
    this.value = paramObject;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof IDKey;
    boolean bool1 = false;
    if (!bool)
      return false; 
    paramObject = paramObject;
    if (this.id != ((IDKey)paramObject).id)
      return false; 
    if (this.value == ((IDKey)paramObject).value)
      bool1 = true; 
    return bool1;
  }
  
  public int hashCode() {
    return this.id;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\builder\IDKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */