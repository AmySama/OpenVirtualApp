package jonathanfinerty.once;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class PersistedMap {
  private static final String DELIMITER = ",";
  
  private final Map<String, List<Long>> map = new ConcurrentHashMap<String, List<Long>>();
  
  private final AsyncSharedPreferenceLoader preferenceLoader;
  
  private SharedPreferences preferences;
  
  public PersistedMap(Context paramContext, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(PersistedMap.class.getSimpleName());
    stringBuilder.append(paramString);
    this.preferenceLoader = new AsyncSharedPreferenceLoader(paramContext, stringBuilder.toString());
  }
  
  private String listToString(List<Long> paramList) {
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<Long> iterator = paramList.iterator();
    for (String str = ""; iterator.hasNext(); str = ",") {
      Long long_ = iterator.next();
      stringBuilder.append(str);
      stringBuilder.append(long_);
    } 
    return stringBuilder.toString();
  }
  
  private List<Long> loadFromLegacyStorageFormat(String paramString) {
    long l = this.preferences.getLong(paramString, -1L);
    ArrayList<Long> arrayList = new ArrayList(1);
    arrayList.add(Long.valueOf(l));
    this.preferences.edit().putString(paramString, listToString(arrayList)).apply();
    return arrayList;
  }
  
  private List<Long> stringToList(String paramString) {
    if (paramString == null || paramString.isEmpty())
      return Collections.emptyList(); 
    String[] arrayOfString = paramString.split(",");
    ArrayList<Long> arrayList = new ArrayList(arrayOfString.length);
    int i = arrayOfString.length;
    for (byte b = 0; b < i; b++)
      arrayList.add(Long.valueOf(Long.parseLong(arrayOfString[b]))); 
    return arrayList;
  }
  
  private void waitForLoad() {
    if (this.preferences != null)
      return; 
    SharedPreferences sharedPreferences = this.preferenceLoader.get();
    this.preferences = sharedPreferences;
    for (String str : sharedPreferences.getAll().keySet()) {
      List<Long> list;
      try {
        list = stringToList(this.preferences.getString(str, null));
      } catch (ClassCastException classCastException) {
        list = loadFromLegacyStorageFormat(str);
      } 
      this.map.put(str, list);
    } 
  }
  
  public void clear() {
    waitForLoad();
    this.map.clear();
    SharedPreferences.Editor editor = this.preferences.edit();
    editor.clear();
    editor.apply();
  }
  
  public List<Long> get(String paramString) {
    waitForLoad();
    List<?> list2 = this.map.get(paramString);
    List<?> list1 = list2;
    if (list2 == null)
      list1 = Collections.emptyList(); 
    return (List)list1;
  }
  
  public void put(String paramString, long paramLong) {
    waitForLoad();
    List<Long> list1 = this.map.get(paramString);
    List<Long> list2 = list1;
    if (list1 == null)
      list2 = new ArrayList(1); 
    list2.add(Long.valueOf(paramLong));
    this.map.put(paramString, list2);
    SharedPreferences.Editor editor = this.preferences.edit();
    editor.putString(paramString, listToString(list2));
    editor.apply();
  }
  
  public void remove(String paramString) {
    waitForLoad();
    this.map.remove(paramString);
    SharedPreferences.Editor editor = this.preferences.edit();
    editor.remove(paramString);
    editor.apply();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\jonathanfinerty\once\PersistedMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */