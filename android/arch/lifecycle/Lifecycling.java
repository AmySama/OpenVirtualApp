package android.arch.lifecycle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lifecycling {
  private static final int GENERATED_CALLBACK = 2;
  
  private static final int REFLECTIVE_CALLBACK = 1;
  
  private static Map<Class, Integer> sCallbackCache = (Map)new HashMap<Class<?>, Integer>();
  
  private static Map<Class, List<Constructor<? extends GeneratedAdapter>>> sClassToAdapters = (Map)new HashMap<Class<?>, List<Constructor<? extends GeneratedAdapter>>>();
  
  private static GeneratedAdapter createGeneratedAdapter(Constructor<? extends GeneratedAdapter> paramConstructor, Object paramObject) {
    try {
      return paramConstructor.newInstance(new Object[] { paramObject });
    } catch (IllegalAccessException illegalAccessException) {
      throw new RuntimeException(illegalAccessException);
    } catch (InstantiationException instantiationException) {
      throw new RuntimeException(instantiationException);
    } catch (InvocationTargetException invocationTargetException) {
      throw new RuntimeException(invocationTargetException);
    } 
  }
  
  private static Constructor<? extends GeneratedAdapter> generatedConstructor(Class<?> paramClass) {
    try {
      String str1;
      Package package_ = paramClass.getPackage();
      String str2 = paramClass.getCanonicalName();
      if (package_ != null) {
        str1 = package_.getName();
      } else {
        str1 = "";
      } 
      if (!str1.isEmpty())
        str2 = str2.substring(str1.length() + 1); 
      str2 = getAdapterName(str2);
      if (str1.isEmpty()) {
        str1 = str2;
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append(str1);
        stringBuilder.append(".");
        stringBuilder.append(str2);
        str1 = stringBuilder.toString();
      } 
      Constructor<?> constructor = Class.forName(str1).getDeclaredConstructor(new Class[] { paramClass });
      if (!constructor.isAccessible())
        constructor.setAccessible(true); 
      return (Constructor)constructor;
    } catch (ClassNotFoundException classNotFoundException) {
      return null;
    } catch (NoSuchMethodException noSuchMethodException) {
      throw new RuntimeException(noSuchMethodException);
    } 
  }
  
  public static String getAdapterName(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString.replace(".", "_"));
    stringBuilder.append("_LifecycleAdapter");
    return stringBuilder.toString();
  }
  
  static GenericLifecycleObserver getCallback(Object paramObject) {
    if (paramObject instanceof FullLifecycleObserver)
      return new FullLifecycleObserverAdapter((FullLifecycleObserver)paramObject); 
    if (paramObject instanceof GenericLifecycleObserver)
      return (GenericLifecycleObserver)paramObject; 
    Class<?> clazz = paramObject.getClass();
    if (getObserverConstructorType(clazz) == 2) {
      List<Constructor<? extends GeneratedAdapter>> list = sClassToAdapters.get(clazz);
      int i = list.size();
      byte b = 0;
      if (i == 1)
        return new SingleGeneratedAdapterObserver(createGeneratedAdapter(list.get(0), paramObject)); 
      GeneratedAdapter[] arrayOfGeneratedAdapter = new GeneratedAdapter[list.size()];
      while (b < list.size()) {
        arrayOfGeneratedAdapter[b] = createGeneratedAdapter(list.get(b), paramObject);
        b++;
      } 
      return new CompositeGeneratedAdaptersObserver(arrayOfGeneratedAdapter);
    } 
    return new ReflectiveGenericLifecycleObserver(paramObject);
  }
  
  private static int getObserverConstructorType(Class<?> paramClass) {
    if (sCallbackCache.containsKey(paramClass))
      return ((Integer)sCallbackCache.get(paramClass)).intValue(); 
    int i = resolveObserverCallbackType(paramClass);
    sCallbackCache.put(paramClass, Integer.valueOf(i));
    return i;
  }
  
  private static boolean isLifecycleParent(Class<?> paramClass) {
    boolean bool;
    if (paramClass != null && LifecycleObserver.class.isAssignableFrom(paramClass)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static int resolveObserverCallbackType(Class<?> paramClass) {
    ArrayList<Constructor<? extends GeneratedAdapter>> arrayList;
    if (paramClass.getCanonicalName() == null)
      return 1; 
    Constructor<? extends GeneratedAdapter> constructor = generatedConstructor(paramClass);
    if (constructor != null) {
      sClassToAdapters.put(paramClass, Collections.singletonList(constructor));
      return 2;
    } 
    if (ClassesInfoCache.sInstance.hasLifecycleMethods(paramClass))
      return 1; 
    Class<?> clazz = paramClass.getSuperclass();
    constructor = null;
    if (isLifecycleParent(clazz)) {
      if (getObserverConstructorType(clazz) == 1)
        return 1; 
      arrayList = new ArrayList(sClassToAdapters.get(clazz));
    } 
    for (Class<?> clazz1 : paramClass.getInterfaces()) {
      if (isLifecycleParent(clazz1)) {
        if (getObserverConstructorType(clazz1) == 1)
          return 1; 
        ArrayList<Constructor<? extends GeneratedAdapter>> arrayList1 = arrayList;
        if (arrayList == null)
          arrayList1 = new ArrayList(); 
        arrayList1.addAll(sClassToAdapters.get(clazz1));
        arrayList = arrayList1;
      } 
    } 
    if (arrayList != null) {
      sClassToAdapters.put(paramClass, arrayList);
      return 2;
    } 
    return 1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\Lifecycling.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */