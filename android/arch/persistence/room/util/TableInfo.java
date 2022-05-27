package android.arch.persistence.room.util;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.database.Cursor;
import android.os.Build;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TableInfo {
  public final Map<String, Column> columns;
  
  public final Set<ForeignKey> foreignKeys;
  
  public final Set<Index> indices;
  
  public final String name;
  
  public TableInfo(String paramString, Map<String, Column> paramMap, Set<ForeignKey> paramSet) {
    this(paramString, paramMap, paramSet, Collections.emptySet());
  }
  
  public TableInfo(String paramString, Map<String, Column> paramMap, Set<ForeignKey> paramSet, Set<Index> paramSet1) {
    Set<Index> set;
    this.name = paramString;
    this.columns = Collections.unmodifiableMap(paramMap);
    this.foreignKeys = Collections.unmodifiableSet(paramSet);
    if (paramSet1 == null) {
      paramString = null;
    } else {
      set = Collections.unmodifiableSet(paramSet1);
    } 
    this.indices = set;
  }
  
  public static TableInfo read(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString) {
    return new TableInfo(paramString, readColumns(paramSupportSQLiteDatabase, paramString), readForeignKeys(paramSupportSQLiteDatabase, paramString), readIndices(paramSupportSQLiteDatabase, paramString));
  }
  
  private static Map<String, Column> readColumns(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("PRAGMA table_info(`");
    stringBuilder.append(paramString);
    stringBuilder.append("`)");
    Cursor cursor = paramSupportSQLiteDatabase.query(stringBuilder.toString());
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    try {
      if (cursor.getColumnCount() > 0) {
        int i = cursor.getColumnIndex("name");
        int j = cursor.getColumnIndex("type");
        int k = cursor.getColumnIndex("notnull");
        int m = cursor.getColumnIndex("pk");
        while (cursor.moveToNext()) {
          boolean bool;
          String str1 = cursor.getString(i);
          String str2 = cursor.getString(j);
          if (cursor.getInt(k) != 0) {
            bool = true;
          } else {
            bool = false;
          } 
          int n = cursor.getInt(m);
          Column column = new Column();
          this(str1, str2, bool, n);
          hashMap.put(str1, column);
        } 
      } 
      return (Map)hashMap;
    } finally {
      cursor.close();
    } 
  }
  
  private static List<ForeignKeyWithSequence> readForeignKeyFieldMappings(Cursor paramCursor) {
    int i = paramCursor.getColumnIndex("id");
    int j = paramCursor.getColumnIndex("seq");
    int k = paramCursor.getColumnIndex("from");
    int m = paramCursor.getColumnIndex("to");
    int n = paramCursor.getCount();
    ArrayList<ForeignKeyWithSequence> arrayList = new ArrayList();
    for (byte b = 0; b < n; b++) {
      paramCursor.moveToPosition(b);
      arrayList.add(new ForeignKeyWithSequence(paramCursor.getInt(i), paramCursor.getInt(j), paramCursor.getString(k), paramCursor.getString(m)));
    } 
    Collections.sort(arrayList);
    return arrayList;
  }
  
  private static Set<ForeignKey> readForeignKeys(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString) {
    HashSet<ForeignKey> hashSet = new HashSet();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("PRAGMA foreign_key_list(`");
    stringBuilder.append(paramString);
    stringBuilder.append("`)");
    Cursor cursor = paramSupportSQLiteDatabase.query(stringBuilder.toString());
    try {
      int i = cursor.getColumnIndex("id");
      int j = cursor.getColumnIndex("seq");
      int k = cursor.getColumnIndex("table");
      int m = cursor.getColumnIndex("on_delete");
      int n = cursor.getColumnIndex("on_update");
      List<ForeignKeyWithSequence> list = readForeignKeyFieldMappings(cursor);
      int i1 = cursor.getCount();
      for (byte b = 0; b < i1; b++) {
        cursor.moveToPosition(b);
        if (cursor.getInt(j) == 0) {
          int i2 = cursor.getInt(i);
          ArrayList<String> arrayList1 = new ArrayList();
          this();
          ArrayList<String> arrayList2 = new ArrayList();
          this();
          for (ForeignKeyWithSequence foreignKeyWithSequence : list) {
            if (foreignKeyWithSequence.mId == i2) {
              arrayList1.add(foreignKeyWithSequence.mFrom);
              arrayList2.add(foreignKeyWithSequence.mTo);
            } 
          } 
          ForeignKey foreignKey = new ForeignKey();
          this(cursor.getString(k), cursor.getString(m), cursor.getString(n), arrayList1, arrayList2);
          hashSet.add(foreignKey);
        } 
      } 
      return hashSet;
    } finally {
      cursor.close();
    } 
  }
  
  private static Index readIndex(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("PRAGMA index_xinfo(`");
    stringBuilder.append(paramString);
    stringBuilder.append("`)");
    Cursor cursor = paramSupportSQLiteDatabase.query(stringBuilder.toString());
    try {
      int i = cursor.getColumnIndex("seqno");
      int j = cursor.getColumnIndex("cid");
      int k = cursor.getColumnIndex("name");
      if (i == -1 || j == -1 || k == -1)
        return null; 
      TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
      this();
      while (cursor.moveToNext()) {
        if (cursor.getInt(j) < 0)
          continue; 
        treeMap.put(Integer.valueOf(cursor.getInt(i)), cursor.getString(k));
      } 
      ArrayList<String> arrayList = new ArrayList();
      this(treeMap.size());
      arrayList.addAll(treeMap.values());
      return new Index(paramString, paramBoolean, arrayList);
    } finally {
      cursor.close();
    } 
  }
  
  private static Set<Index> readIndices(SupportSQLiteDatabase paramSupportSQLiteDatabase, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("PRAGMA index_list(`");
    stringBuilder.append(paramString);
    stringBuilder.append("`)");
    Cursor cursor = paramSupportSQLiteDatabase.query(stringBuilder.toString());
    try {
      int i = cursor.getColumnIndex("name");
      int j = cursor.getColumnIndex("origin");
      int k = cursor.getColumnIndex("unique");
      if (i == -1 || j == -1 || k == -1)
        return null; 
      HashSet<Index> hashSet = new HashSet();
      this();
      while (cursor.moveToNext()) {
        if (!"c".equals(cursor.getString(j)))
          continue; 
        String str = cursor.getString(i);
        int m = cursor.getInt(k);
        boolean bool = true;
        if (m != 1)
          bool = false; 
        Index index = readIndex(paramSupportSQLiteDatabase, str, bool);
        if (index == null)
          return null; 
        hashSet.add(index);
      } 
      return hashSet;
    } finally {
      cursor.close();
    } 
  }
  
  public boolean equals(Object<Index> paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    String str = this.name;
    if ((str != null) ? !str.equals(((TableInfo)paramObject).name) : (((TableInfo)paramObject).name != null))
      return false; 
    Map<String, Column> map = this.columns;
    if ((map != null) ? !map.equals(((TableInfo)paramObject).columns) : (((TableInfo)paramObject).columns != null))
      return false; 
    Set<ForeignKey> set1 = this.foreignKeys;
    if ((set1 != null) ? !set1.equals(((TableInfo)paramObject).foreignKeys) : (((TableInfo)paramObject).foreignKeys != null))
      return false; 
    Set<Index> set = this.indices;
    if (set != null) {
      paramObject = (Object<Index>)((TableInfo)paramObject).indices;
      if (paramObject != null)
        return set.equals(paramObject); 
    } 
    return true;
  }
  
  public int hashCode() {
    byte b1;
    byte b2;
    String str = this.name;
    int i = 0;
    if (str != null) {
      b1 = str.hashCode();
    } else {
      b1 = 0;
    } 
    Map<String, Column> map = this.columns;
    if (map != null) {
      b2 = map.hashCode();
    } else {
      b2 = 0;
    } 
    Set<ForeignKey> set = this.foreignKeys;
    if (set != null)
      i = set.hashCode(); 
    return (b1 * 31 + b2) * 31 + i;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("TableInfo{name='");
    stringBuilder.append(this.name);
    stringBuilder.append('\'');
    stringBuilder.append(", columns=");
    stringBuilder.append(this.columns);
    stringBuilder.append(", foreignKeys=");
    stringBuilder.append(this.foreignKeys);
    stringBuilder.append(", indices=");
    stringBuilder.append(this.indices);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public static class Column {
    public final int affinity;
    
    public final String name;
    
    public final boolean notNull;
    
    public final int primaryKeyPosition;
    
    public final String type;
    
    public Column(String param1String1, String param1String2, boolean param1Boolean, int param1Int) {
      this.name = param1String1;
      this.type = param1String2;
      this.notNull = param1Boolean;
      this.primaryKeyPosition = param1Int;
      this.affinity = findAffinity(param1String2);
    }
    
    private static int findAffinity(String param1String) {
      if (param1String == null)
        return 5; 
      param1String = param1String.toUpperCase(Locale.US);
      return param1String.contains("INT") ? 3 : ((param1String.contains("CHAR") || param1String.contains("CLOB") || param1String.contains("TEXT")) ? 2 : (param1String.contains("BLOB") ? 5 : ((param1String.contains("REAL") || param1String.contains("FLOA") || param1String.contains("DOUB")) ? 4 : 1)));
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = true;
      if (this == param1Object)
        return true; 
      if (param1Object == null || getClass() != param1Object.getClass())
        return false; 
      param1Object = param1Object;
      if (Build.VERSION.SDK_INT >= 20) {
        if (this.primaryKeyPosition != ((Column)param1Object).primaryKeyPosition)
          return false; 
      } else if (isPrimaryKey() != param1Object.isPrimaryKey()) {
        return false;
      } 
      if (!this.name.equals(((Column)param1Object).name))
        return false; 
      if (this.notNull != ((Column)param1Object).notNull)
        return false; 
      if (this.affinity != ((Column)param1Object).affinity)
        bool = false; 
      return bool;
    }
    
    public int hashCode() {
      char c;
      int i = this.name.hashCode();
      int j = this.affinity;
      if (this.notNull) {
        c = 'ӏ';
      } else {
        c = 'ӕ';
      } 
      return ((i * 31 + j) * 31 + c) * 31 + this.primaryKeyPosition;
    }
    
    public boolean isPrimaryKey() {
      boolean bool;
      if (this.primaryKeyPosition > 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Column{name='");
      stringBuilder.append(this.name);
      stringBuilder.append('\'');
      stringBuilder.append(", type='");
      stringBuilder.append(this.type);
      stringBuilder.append('\'');
      stringBuilder.append(", affinity='");
      stringBuilder.append(this.affinity);
      stringBuilder.append('\'');
      stringBuilder.append(", notNull=");
      stringBuilder.append(this.notNull);
      stringBuilder.append(", primaryKeyPosition=");
      stringBuilder.append(this.primaryKeyPosition);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
  
  public static class ForeignKey {
    public final List<String> columnNames;
    
    public final String onDelete;
    
    public final String onUpdate;
    
    public final List<String> referenceColumnNames;
    
    public final String referenceTable;
    
    public ForeignKey(String param1String1, String param1String2, String param1String3, List<String> param1List1, List<String> param1List2) {
      this.referenceTable = param1String1;
      this.onDelete = param1String2;
      this.onUpdate = param1String3;
      this.columnNames = Collections.unmodifiableList(param1List1);
      this.referenceColumnNames = Collections.unmodifiableList(param1List2);
    }
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      if (param1Object == null || getClass() != param1Object.getClass())
        return false; 
      param1Object = param1Object;
      return !this.referenceTable.equals(((ForeignKey)param1Object).referenceTable) ? false : (!this.onDelete.equals(((ForeignKey)param1Object).onDelete) ? false : (!this.onUpdate.equals(((ForeignKey)param1Object).onUpdate) ? false : (!this.columnNames.equals(((ForeignKey)param1Object).columnNames) ? false : this.referenceColumnNames.equals(((ForeignKey)param1Object).referenceColumnNames))));
    }
    
    public int hashCode() {
      return (((this.referenceTable.hashCode() * 31 + this.onDelete.hashCode()) * 31 + this.onUpdate.hashCode()) * 31 + this.columnNames.hashCode()) * 31 + this.referenceColumnNames.hashCode();
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("ForeignKey{referenceTable='");
      stringBuilder.append(this.referenceTable);
      stringBuilder.append('\'');
      stringBuilder.append(", onDelete='");
      stringBuilder.append(this.onDelete);
      stringBuilder.append('\'');
      stringBuilder.append(", onUpdate='");
      stringBuilder.append(this.onUpdate);
      stringBuilder.append('\'');
      stringBuilder.append(", columnNames=");
      stringBuilder.append(this.columnNames);
      stringBuilder.append(", referenceColumnNames=");
      stringBuilder.append(this.referenceColumnNames);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
  
  static class ForeignKeyWithSequence implements Comparable<ForeignKeyWithSequence> {
    final String mFrom;
    
    final int mId;
    
    final int mSequence;
    
    final String mTo;
    
    ForeignKeyWithSequence(int param1Int1, int param1Int2, String param1String1, String param1String2) {
      this.mId = param1Int1;
      this.mSequence = param1Int2;
      this.mFrom = param1String1;
      this.mTo = param1String2;
    }
    
    public int compareTo(ForeignKeyWithSequence param1ForeignKeyWithSequence) {
      int i = this.mId - param1ForeignKeyWithSequence.mId;
      int j = i;
      if (i == 0)
        j = this.mSequence - param1ForeignKeyWithSequence.mSequence; 
      return j;
    }
  }
  
  public static class Index {
    public static final String DEFAULT_PREFIX = "index_";
    
    public final List<String> columns;
    
    public final String name;
    
    public final boolean unique;
    
    public Index(String param1String, boolean param1Boolean, List<String> param1List) {
      this.name = param1String;
      this.unique = param1Boolean;
      this.columns = param1List;
    }
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      if (param1Object == null || getClass() != param1Object.getClass())
        return false; 
      param1Object = param1Object;
      return (this.unique != ((Index)param1Object).unique) ? false : (!this.columns.equals(((Index)param1Object).columns) ? false : (this.name.startsWith("index_") ? ((Index)param1Object).name.startsWith("index_") : this.name.equals(((Index)param1Object).name)));
    }
    
    public int hashCode() {
      int i;
      if (this.name.startsWith("index_")) {
        i = -1184239155;
      } else {
        i = this.name.hashCode();
      } 
      return (i * 31 + this.unique) * 31 + this.columns.hashCode();
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Index{name='");
      stringBuilder.append(this.name);
      stringBuilder.append('\'');
      stringBuilder.append(", unique=");
      stringBuilder.append(this.unique);
      stringBuilder.append(", columns=");
      stringBuilder.append(this.columns);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\roo\\util\TableInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */