package jonathanfinerty.once;

import android.content.Context;
import android.content.pm.PackageManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Once {
  public static final int THIS_APP_INSTALL = 0;
  
  public static final int THIS_APP_VERSION = 1;
  
  private static long lastAppUpdatedTime = -1L;
  
  private static PersistedMap tagLastSeenMap;
  
  private static PersistedSet toDoSet;
  
  public static boolean beenDone(int paramInt, String paramString) {
    return beenDone(paramInt, paramString, Amount.moreThan(0));
  }
  
  public static boolean beenDone(int paramInt, String paramString, CountChecker paramCountChecker) {
    List<Long> list = tagLastSeenMap.get(paramString);
    boolean bool = list.isEmpty();
    boolean bool1 = false;
    if (bool)
      return false; 
    if (paramInt == 0)
      return paramCountChecker.check(list.size()); 
    Iterator<Long> iterator = list.iterator();
    paramInt = bool1;
    while (iterator.hasNext()) {
      if (((Long)iterator.next()).longValue() > lastAppUpdatedTime)
        paramInt++; 
    } 
    return paramCountChecker.check(paramInt);
  }
  
  public static boolean beenDone(long paramLong, String paramString) {
    return beenDone(paramLong, paramString, Amount.moreThan(0));
  }
  
  public static boolean beenDone(long paramLong, String paramString, CountChecker paramCountChecker) {
    List<Long> list = tagLastSeenMap.get(paramString);
    boolean bool = list.isEmpty();
    byte b = 0;
    if (bool)
      return false; 
    for (Long long_ : list) {
      long l = (new Date()).getTime();
      if (long_.longValue() > l - paramLong)
        b++; 
    } 
    return paramCountChecker.check(b);
  }
  
  public static boolean beenDone(String paramString) {
    return beenDone(0, paramString, Amount.moreThan(0));
  }
  
  public static boolean beenDone(String paramString, CountChecker paramCountChecker) {
    return beenDone(0, paramString, paramCountChecker);
  }
  
  public static boolean beenDone(TimeUnit paramTimeUnit, long paramLong, String paramString) {
    return beenDone(paramTimeUnit, paramLong, paramString, Amount.moreThan(0));
  }
  
  public static boolean beenDone(TimeUnit paramTimeUnit, long paramLong, String paramString, CountChecker paramCountChecker) {
    return beenDone(paramTimeUnit.toMillis(paramLong), paramString, paramCountChecker);
  }
  
  public static void clearAll() {
    tagLastSeenMap.clear();
  }
  
  public static void clearAllToDos() {
    toDoSet.clear();
  }
  
  public static void clearDone(String paramString) {
    tagLastSeenMap.remove(paramString);
  }
  
  public static void clearToDo(String paramString) {
    toDoSet.remove(paramString);
  }
  
  public static void initialise(Context paramContext) {
    if (tagLastSeenMap == null)
      tagLastSeenMap = new PersistedMap(paramContext, "TagLastSeenMap"); 
    if (toDoSet == null)
      toDoSet = new PersistedSet(paramContext, "ToDoSet"); 
    PackageManager packageManager = paramContext.getPackageManager();
    try {
      lastAppUpdatedTime = (packageManager.getPackageInfo(paramContext.getPackageName(), 0)).lastUpdateTime;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {}
  }
  
  public static void markDone(String paramString) {
    tagLastSeenMap.put(paramString, (new Date()).getTime());
    toDoSet.remove(paramString);
  }
  
  public static boolean needToDo(String paramString) {
    return toDoSet.contains(paramString);
  }
  
  public static void toDo(int paramInt, String paramString) {
    List<Long> list = tagLastSeenMap.get(paramString);
    if (list.isEmpty()) {
      toDoSet.put(paramString);
      return;
    } 
    Long long_ = list.get(list.size() - 1);
    if (paramInt == 1 && long_.longValue() <= lastAppUpdatedTime)
      toDoSet.put(paramString); 
  }
  
  public static void toDo(String paramString) {
    toDoSet.put(paramString);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Scope {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\jonathanfinerty\once\Once.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */