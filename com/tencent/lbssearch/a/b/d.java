package com.tencent.lbssearch.a.b;

import android.content.ContentValues;
import android.net.Uri;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class d implements Serializable {
  protected final ConcurrentHashMap<String, String> a = new ConcurrentHashMap<String, String>();
  
  protected final ConcurrentHashMap<String, Object> b = new ConcurrentHashMap<String, Object>();
  
  public d() {
    this((Map<String, String>)null);
  }
  
  public d(Map<String, String> paramMap) {
    if (paramMap != null)
      for (Map.Entry<String, String> entry : paramMap.entrySet())
        a((String)entry.getKey(), (String)entry.getValue());  
  }
  
  private List<ContentValues> b(String paramString, Object paramObject) {
    LinkedList<ContentValues> linkedList = new LinkedList();
    if (paramObject instanceof Map) {
      Map map = (Map)paramObject;
      paramObject = new ArrayList(map.keySet());
      if (paramObject.size() > 0 && paramObject.get(0) instanceof Comparable)
        Collections.sort((List<Comparable>)paramObject); 
      for (Object paramObject : paramObject) {
        if (paramObject instanceof String) {
          Object object = map.get(paramObject);
          if (object != null) {
            if (paramString == null) {
              paramObject = paramObject;
            } else {
              paramObject = String.format("%s[%s]", new Object[] { paramString, paramObject });
            } 
            linkedList.addAll(b((String)paramObject, object));
          } 
        } 
      } 
    } else if (paramObject instanceof List) {
      paramObject = paramObject;
      int i = paramObject.size();
      for (byte b = 0; b < i; b++) {
        linkedList.addAll(b(String.format("%s[%d]", new Object[] { paramString, Integer.valueOf(b) }), paramObject.get(b)));
      } 
    } else if (paramObject instanceof Object[]) {
      paramObject = paramObject;
      int i = paramObject.length;
      for (byte b = 0; b < i; b++) {
        linkedList.addAll(b(String.format("%s[%d]", new Object[] { paramString, Integer.valueOf(b) }), paramObject[b]));
      } 
    } else if (paramObject instanceof Set) {
      paramObject = ((Set)paramObject).iterator();
      while (paramObject.hasNext())
        linkedList.addAll(b(paramString, paramObject.next())); 
    } else {
      ContentValues contentValues = new ContentValues();
      contentValues.put(paramString, paramObject.toString());
      linkedList.add(contentValues);
    } 
    return linkedList;
  }
  
  protected String a() {
    return Uri.encode(toString(), "=&");
  }
  
  public void a(String paramString, int paramInt) {
    if (paramString != null)
      this.a.put(paramString, String.valueOf(paramInt)); 
  }
  
  public void a(String paramString, Object paramObject) {
    if (paramString != null && paramObject != null)
      this.b.put(paramString, paramObject); 
  }
  
  public void a(String paramString1, String paramString2) {
    if (paramString1 != null && paramString2 != null)
      this.a.put(paramString1, paramString2); 
  }
  
  public void b(String paramString1, String paramString2) {
    if (paramString1 != null && paramString2 != null) {
      Object object1 = this.b.get(paramString1);
      Object object2 = object1;
      if (object1 == null) {
        object2 = new HashSet();
        a(paramString1, object2);
      } 
      if (object2 instanceof List) {
        ((List<String>)object2).add(paramString2);
      } else if (object2 instanceof Set) {
        ((Set<String>)object2).add(paramString2);
      } 
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Map.Entry<String, String> entry : this.a.entrySet()) {
      if (stringBuilder.length() > 0)
        stringBuilder.append("&"); 
      stringBuilder.append((String)entry.getKey());
      stringBuilder.append("=");
      stringBuilder.append((String)entry.getValue());
    } 
    for (ContentValues contentValues : b((String)null, this.b)) {
      if (stringBuilder.length() > 0)
        stringBuilder.append("&"); 
      for (Map.Entry entry : contentValues.valueSet()) {
        stringBuilder.append((String)entry.getKey());
        stringBuilder.append("=");
        stringBuilder.append(entry.getValue().toString());
      } 
    } 
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\b\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */