package external.org.apache.commons.lang3.builder;

import external.org.apache.commons.lang3.ArrayUtils;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ReflectionToStringBuilder extends ToStringBuilder {
  private boolean appendStatics = false;
  
  private boolean appendTransients = false;
  
  protected String[] excludeFieldNames;
  
  private Class<?> upToClass = null;
  
  public ReflectionToStringBuilder(Object paramObject) {
    super(paramObject);
  }
  
  public ReflectionToStringBuilder(Object paramObject, ToStringStyle paramToStringStyle) {
    super(paramObject, paramToStringStyle);
  }
  
  public ReflectionToStringBuilder(Object paramObject, ToStringStyle paramToStringStyle, StringBuffer paramStringBuffer) {
    super(paramObject, paramToStringStyle, paramStringBuffer);
  }
  
  public <T> ReflectionToStringBuilder(T paramT, ToStringStyle paramToStringStyle, StringBuffer paramStringBuffer, Class<? super T> paramClass, boolean paramBoolean1, boolean paramBoolean2) {
    super(paramT, paramToStringStyle, paramStringBuffer);
    setUpToClass(paramClass);
    setAppendTransients(paramBoolean1);
    setAppendStatics(paramBoolean2);
  }
  
  static String[] toNoNullStringArray(Collection<String> paramCollection) {
    return (paramCollection == null) ? ArrayUtils.EMPTY_STRING_ARRAY : toNoNullStringArray(paramCollection.toArray());
  }
  
  static String[] toNoNullStringArray(Object[] paramArrayOfObject) {
    ArrayList<String> arrayList = new ArrayList(paramArrayOfObject.length);
    int i = paramArrayOfObject.length;
    for (byte b = 0; b < i; b++) {
      Object object = paramArrayOfObject[b];
      if (object != null)
        arrayList.add(object.toString()); 
    } 
    return arrayList.<String>toArray(ArrayUtils.EMPTY_STRING_ARRAY);
  }
  
  public static String toString(Object paramObject) {
    return toString(paramObject, (ToStringStyle)null, false, false, (Class<? super Object>)null);
  }
  
  public static String toString(Object paramObject, ToStringStyle paramToStringStyle) {
    return toString(paramObject, paramToStringStyle, false, false, (Class<? super Object>)null);
  }
  
  public static String toString(Object paramObject, ToStringStyle paramToStringStyle, boolean paramBoolean) {
    return toString(paramObject, paramToStringStyle, paramBoolean, false, (Class<? super Object>)null);
  }
  
  public static String toString(Object paramObject, ToStringStyle paramToStringStyle, boolean paramBoolean1, boolean paramBoolean2) {
    return toString(paramObject, paramToStringStyle, paramBoolean1, paramBoolean2, (Class<? super Object>)null);
  }
  
  public static <T> String toString(T paramT, ToStringStyle paramToStringStyle, boolean paramBoolean1, boolean paramBoolean2, Class<? super T> paramClass) {
    return (new ReflectionToStringBuilder(paramT, paramToStringStyle, null, paramClass, paramBoolean1, paramBoolean2)).toString();
  }
  
  public static String toStringExclude(Object paramObject, Collection<String> paramCollection) {
    return toStringExclude(paramObject, toNoNullStringArray(paramCollection));
  }
  
  public static String toStringExclude(Object paramObject, String... paramVarArgs) {
    return (new ReflectionToStringBuilder(paramObject)).setExcludeFieldNames(paramVarArgs).toString();
  }
  
  protected boolean accept(Field paramField) {
    if (paramField.getName().indexOf('$') != -1)
      return false; 
    if (Modifier.isTransient(paramField.getModifiers()) && !isAppendTransients())
      return false; 
    if (Modifier.isStatic(paramField.getModifiers()) && !isAppendStatics())
      return false; 
    String[] arrayOfString = this.excludeFieldNames;
    return !(arrayOfString != null && Arrays.binarySearch((Object[])arrayOfString, paramField.getName()) >= 0);
  }
  
  protected void appendFieldsIn(Class<?> paramClass) {
    if (paramClass.isArray()) {
      reflectionAppendArray(getObject());
      return;
    } 
    Field[] arrayOfField = paramClass.getDeclaredFields();
    AccessibleObject.setAccessible((AccessibleObject[])arrayOfField, true);
    int i = arrayOfField.length;
    for (byte b = 0; b < i; b++) {
      Field field = arrayOfField[b];
      String str = field.getName();
      if (accept(field))
        try {
          append(str, getValue(field));
        } catch (IllegalAccessException illegalAccessException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Unexpected IllegalAccessException: ");
          stringBuilder.append(illegalAccessException.getMessage());
          throw new InternalError(stringBuilder.toString());
        }  
    } 
  }
  
  public String[] getExcludeFieldNames() {
    return (String[])this.excludeFieldNames.clone();
  }
  
  public Class<?> getUpToClass() {
    return this.upToClass;
  }
  
  protected Object getValue(Field paramField) throws IllegalArgumentException, IllegalAccessException {
    return paramField.get(getObject());
  }
  
  public boolean isAppendStatics() {
    return this.appendStatics;
  }
  
  public boolean isAppendTransients() {
    return this.appendTransients;
  }
  
  public ReflectionToStringBuilder reflectionAppendArray(Object paramObject) {
    getStyle().reflectionAppendArrayDetail(getStringBuffer(), null, paramObject);
    return this;
  }
  
  public void setAppendStatics(boolean paramBoolean) {
    this.appendStatics = paramBoolean;
  }
  
  public void setAppendTransients(boolean paramBoolean) {
    this.appendTransients = paramBoolean;
  }
  
  public ReflectionToStringBuilder setExcludeFieldNames(String... paramVarArgs) {
    if (paramVarArgs == null) {
      this.excludeFieldNames = null;
    } else {
      paramVarArgs = toNoNullStringArray((Object[])paramVarArgs);
      this.excludeFieldNames = paramVarArgs;
      Arrays.sort((Object[])paramVarArgs);
    } 
    return this;
  }
  
  public void setUpToClass(Class<?> paramClass) {
    if (paramClass != null) {
      Object object = getObject();
      if (object != null && !paramClass.isInstance(object))
        throw new IllegalArgumentException("Specified class is not a superclass of the object"); 
    } 
    this.upToClass = paramClass;
  }
  
  public String toString() {
    if (getObject() == null)
      return getStyle().getNullText(); 
    Class<?> clazz = getObject().getClass();
    appendFieldsIn(clazz);
    while (clazz.getSuperclass() != null && clazz != getUpToClass()) {
      clazz = clazz.getSuperclass();
      appendFieldsIn(clazz);
    } 
    return super.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\builder\ReflectionToStringBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */