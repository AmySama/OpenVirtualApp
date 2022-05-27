package jonathanfinerty.once;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class PersistedSet {
  private static final String DELIMITER = ",";
  
  private static final String STRING_SET_KEY = "PersistedSetValues";
  
  private final AsyncSharedPreferenceLoader preferenceLoader;
  
  private SharedPreferences preferences;
  
  private Set<String> set = new HashSet<String>();
  
  public PersistedSet(Context paramContext, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(PersistedSet.class.getSimpleName());
    stringBuilder.append(paramString);
    this.preferenceLoader = new AsyncSharedPreferenceLoader(paramContext, stringBuilder.toString());
  }
  
  private String StringSetToString(Set<String> paramSet) {
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<String> iterator = paramSet.iterator();
    for (String str = ""; iterator.hasNext(); str = ",") {
      String str1 = iterator.next();
      stringBuilder.append(str);
      stringBuilder.append(str1);
    } 
    return stringBuilder.toString();
  }
  
  private Set<String> StringToStringSet(String paramString) {
    return (paramString == null) ? new HashSet<String>() : new HashSet<String>(Arrays.asList(paramString.split(",")));
  }
  
  private void updatePreferences() {
    SharedPreferences.Editor editor = this.preferences.edit();
    if (Build.VERSION.SDK_INT >= 11) {
      editor.putStringSet("PersistedSetValues", this.set);
    } else {
      editor.putString("PersistedSetValues", StringSetToString(this.set));
    } 
    editor.apply();
  }
  
  private void waitForLoad() {
    if (this.preferences == null) {
      this.preferences = this.preferenceLoader.get();
      if (Build.VERSION.SDK_INT >= 11) {
        this.set = this.preferences.getStringSet("PersistedSetValues", new HashSet());
      } else {
        this.set = new HashSet<String>(StringToStringSet(this.preferences.getString("PersistedSetValues", null)));
      } 
    } 
  }
  
  public void clear() {
    waitForLoad();
    this.set.clear();
    updatePreferences();
  }
  
  public boolean contains(String paramString) {
    waitForLoad();
    return this.set.contains(paramString);
  }
  
  public void put(String paramString) {
    waitForLoad();
    this.set.add(paramString);
    updatePreferences();
  }
  
  public void remove(String paramString) {
    waitForLoad();
    this.set.remove(paramString);
    updatePreferences();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\jonathanfinerty\once\PersistedSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */