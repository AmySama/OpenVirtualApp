package android.arch.persistence.db;

public final class SimpleSQLiteQuery implements SupportSQLiteQuery {
  private final Object[] mBindArgs;
  
  private final String mQuery;
  
  public SimpleSQLiteQuery(String paramString) {
    this(paramString, null);
  }
  
  public SimpleSQLiteQuery(String paramString, Object[] paramArrayOfObject) {
    this.mQuery = paramString;
    this.mBindArgs = paramArrayOfObject;
  }
  
  private static void bind(SupportSQLiteProgram paramSupportSQLiteProgram, int paramInt, Object paramObject) {
    if (paramObject == null) {
      paramSupportSQLiteProgram.bindNull(paramInt);
    } else if (paramObject instanceof byte[]) {
      paramSupportSQLiteProgram.bindBlob(paramInt, (byte[])paramObject);
    } else if (paramObject instanceof Float) {
      paramSupportSQLiteProgram.bindDouble(paramInt, ((Float)paramObject).floatValue());
    } else if (paramObject instanceof Double) {
      paramSupportSQLiteProgram.bindDouble(paramInt, ((Double)paramObject).doubleValue());
    } else if (paramObject instanceof Long) {
      paramSupportSQLiteProgram.bindLong(paramInt, ((Long)paramObject).longValue());
    } else if (paramObject instanceof Integer) {
      paramSupportSQLiteProgram.bindLong(paramInt, ((Integer)paramObject).intValue());
    } else if (paramObject instanceof Short) {
      paramSupportSQLiteProgram.bindLong(paramInt, ((Short)paramObject).shortValue());
    } else if (paramObject instanceof Byte) {
      paramSupportSQLiteProgram.bindLong(paramInt, ((Byte)paramObject).byteValue());
    } else if (paramObject instanceof String) {
      paramSupportSQLiteProgram.bindString(paramInt, (String)paramObject);
    } else {
      if (paramObject instanceof Boolean) {
        long l;
        if (((Boolean)paramObject).booleanValue()) {
          l = 1L;
        } else {
          l = 0L;
        } 
        paramSupportSQLiteProgram.bindLong(paramInt, l);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Cannot bind ");
      stringBuilder.append(paramObject);
      stringBuilder.append(" at index ");
      stringBuilder.append(paramInt);
      stringBuilder.append(" Supported types: null, byte[], float, double, long, int, short, byte,");
      stringBuilder.append(" string");
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
  }
  
  public static void bind(SupportSQLiteProgram paramSupportSQLiteProgram, Object[] paramArrayOfObject) {
    if (paramArrayOfObject == null)
      return; 
    int i = paramArrayOfObject.length;
    byte b = 0;
    while (b < i) {
      Object object = paramArrayOfObject[b];
      bind(paramSupportSQLiteProgram, ++b, object);
    } 
  }
  
  public void bindTo(SupportSQLiteProgram paramSupportSQLiteProgram) {
    bind(paramSupportSQLiteProgram, this.mBindArgs);
  }
  
  public int getArgCount() {
    int i;
    Object[] arrayOfObject = this.mBindArgs;
    if (arrayOfObject == null) {
      i = 0;
    } else {
      i = arrayOfObject.length;
    } 
    return i;
  }
  
  public String getSql() {
    return this.mQuery;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\db\SimpleSQLiteQuery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */