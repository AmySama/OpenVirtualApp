package com.tencent.mm.opensdk.openapi;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import com.tencent.mm.opensdk.channel.a.a;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.a;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class MMSharedPreferences implements SharedPreferences {
  private static final String TAG = "MicroMsg.SDK.SharedPreferences";
  
  private final String[] columns = new String[] { "_id", "key", "type", "value" };
  
  private final ContentResolver cr;
  
  private REditor editor = null;
  
  private final HashMap<String, Object> values = new HashMap<String, Object>();
  
  public MMSharedPreferences(Context paramContext) {
    this.cr = paramContext.getContentResolver();
  }
  
  private Object getValue(String paramString) {
    try {
      ContentResolver contentResolver = this.cr;
      Uri uri = a.a;
      String[] arrayOfString = this.columns;
      Cursor cursor = contentResolver.query(uri, arrayOfString, "key = ?", new String[] { paramString }, null);
      if (cursor == null)
        return null; 
      int i = cursor.getColumnIndex("type");
      int j = cursor.getColumnIndex("value");
      if (cursor.moveToFirst()) {
        Object object = a.a(cursor.getInt(i), cursor.getString(j));
      } else {
        paramString = null;
      } 
      cursor.close();
      return paramString;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("getValue exception:");
      stringBuilder.append(exception.getMessage());
      Log.e("MicroMsg.SDK.SharedPreferences", stringBuilder.toString());
      return null;
    } 
  }
  
  public boolean contains(String paramString) {
    boolean bool;
    if (getValue(paramString) != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public SharedPreferences.Editor edit() {
    if (this.editor == null)
      this.editor = new REditor(this.cr); 
    return this.editor;
  }
  
  public Map<String, ?> getAll() {
    try {
      Cursor cursor = this.cr.query(a.a, this.columns, null, null, null);
      if (cursor == null)
        return null; 
      int i = cursor.getColumnIndex("key");
      int j = cursor.getColumnIndex("type");
      int k = cursor.getColumnIndex("value");
      while (cursor.moveToNext()) {
        Object object = a.a(cursor.getInt(j), cursor.getString(k));
        this.values.put(cursor.getString(i), object);
      } 
      cursor.close();
      return this.values;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("getAll exception:");
      stringBuilder.append(exception.getMessage());
      Log.e("MicroMsg.SDK.SharedPreferences", stringBuilder.toString());
      return this.values;
    } 
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean) {
    Object object = getValue(paramString);
    boolean bool = paramBoolean;
    if (object != null) {
      bool = paramBoolean;
      if (object instanceof Boolean)
        bool = ((Boolean)object).booleanValue(); 
    } 
    return bool;
  }
  
  public float getFloat(String paramString, float paramFloat) {
    Object object = getValue(paramString);
    float f = paramFloat;
    if (object != null) {
      f = paramFloat;
      if (object instanceof Float)
        f = ((Float)object).floatValue(); 
    } 
    return f;
  }
  
  public int getInt(String paramString, int paramInt) {
    Object object = getValue(paramString);
    int i = paramInt;
    if (object != null) {
      i = paramInt;
      if (object instanceof Integer)
        i = ((Integer)object).intValue(); 
    } 
    return i;
  }
  
  public long getLong(String paramString, long paramLong) {
    Object object = getValue(paramString);
    long l = paramLong;
    if (object != null) {
      l = paramLong;
      if (object instanceof Long)
        l = ((Long)object).longValue(); 
    } 
    return l;
  }
  
  public String getString(String paramString1, String paramString2) {
    Object object = getValue(paramString1);
    paramString1 = paramString2;
    if (object != null) {
      paramString1 = paramString2;
      if (object instanceof String)
        paramString1 = (String)object; 
    } 
    return paramString1;
  }
  
  public Set<String> getStringSet(String paramString, Set<String> paramSet) {
    return null;
  }
  
  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {}
  
  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {}
  
  private static class REditor implements SharedPreferences.Editor {
    private boolean clear = false;
    
    private ContentResolver cr;
    
    private Set<String> remove = new HashSet<String>();
    
    private Map<String, Object> values = new HashMap<String, Object>();
    
    public REditor(ContentResolver param1ContentResolver) {
      this.cr = param1ContentResolver;
    }
    
    public void apply() {}
    
    public SharedPreferences.Editor clear() {
      this.clear = true;
      return this;
    }
    
    public boolean commit() {
      ContentValues contentValues = new ContentValues();
      if (this.clear) {
        this.cr.delete(a.a, null, null);
        this.clear = false;
      } 
      for (String str : this.remove) {
        this.cr.delete(a.a, "key = ?", new String[] { str });
      } 
      Iterator<Map.Entry> iterator = this.values.entrySet().iterator();
      while (true) {
        String str;
        if (iterator.hasNext()) {
          Map.Entry entry = iterator.next();
          Object object = entry.getValue();
          if (object == null) {
            str = "unresolve failed, null value";
          } else {
            boolean bool1;
            if (object instanceof Integer) {
              bool1 = true;
            } else if (object instanceof Long) {
              bool1 = true;
            } else if (object instanceof String) {
              bool1 = true;
            } else if (object instanceof Boolean) {
              bool1 = true;
            } else if (object instanceof Float) {
              bool1 = true;
            } else if (object instanceof Double) {
              bool1 = true;
            } else {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("unresolve failed, unknown type=");
              stringBuilder.append(object.getClass().toString());
              str = stringBuilder.toString();
              Log.e("MicroMsg.SDK.PluginProvider.Resolver", str);
              bool1 = false;
            } 
            if (!bool1) {
              bool1 = false;
            } else {
              contentValues.put("type", Integer.valueOf(bool1));
              contentValues.put("value", object.toString());
              bool1 = true;
            } 
            if (bool1)
              this.cr.update(a.a, contentValues, "key = ?", new String[] { (String)entry.getKey() }); 
            continue;
          } 
        } else {
          break;
        } 
        Log.e("MicroMsg.SDK.PluginProvider.Resolver", str);
        boolean bool = false;
      } 
      return true;
    }
    
    public SharedPreferences.Editor putBoolean(String param1String, boolean param1Boolean) {
      this.values.put(param1String, Boolean.valueOf(param1Boolean));
      this.remove.remove(param1String);
      return this;
    }
    
    public SharedPreferences.Editor putFloat(String param1String, float param1Float) {
      this.values.put(param1String, Float.valueOf(param1Float));
      this.remove.remove(param1String);
      return this;
    }
    
    public SharedPreferences.Editor putInt(String param1String, int param1Int) {
      this.values.put(param1String, Integer.valueOf(param1Int));
      this.remove.remove(param1String);
      return this;
    }
    
    public SharedPreferences.Editor putLong(String param1String, long param1Long) {
      this.values.put(param1String, Long.valueOf(param1Long));
      this.remove.remove(param1String);
      return this;
    }
    
    public SharedPreferences.Editor putString(String param1String1, String param1String2) {
      this.values.put(param1String1, param1String2);
      this.remove.remove(param1String1);
      return this;
    }
    
    public SharedPreferences.Editor putStringSet(String param1String, Set<String> param1Set) {
      return null;
    }
    
    public SharedPreferences.Editor remove(String param1String) {
      this.remove.add(param1String);
      return this;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\openapi\MMSharedPreferences.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */