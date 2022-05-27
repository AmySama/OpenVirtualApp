package android.arch.persistence.room;

import android.content.Context;

public class Room {
  private static final String CURSOR_CONV_SUFFIX = "_CursorConverter";
  
  static final String LOG_TAG = "ROOM";
  
  public static final String MASTER_TABLE_NAME = "room_master_table";
  
  public static <T extends RoomDatabase> RoomDatabase.Builder<T> databaseBuilder(Context paramContext, Class<T> paramClass, String paramString) {
    if (paramString != null && paramString.trim().length() != 0)
      return new RoomDatabase.Builder<T>(paramContext, paramClass, paramString); 
    throw new IllegalArgumentException("Cannot build a database with null or empty name. If you are trying to create an in memory database, use Room.inMemoryDatabaseBuilder");
  }
  
  static <T, C> T getGeneratedImplementation(Class<C> paramClass, String paramString) {
    String str1 = paramClass.getPackage().getName();
    String str2 = paramClass.getCanonicalName();
    if (!str1.isEmpty())
      str2 = str2.substring(str1.length() + 1); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str2.replace('.', '_'));
    stringBuilder.append(paramString);
    str2 = stringBuilder.toString();
    try {
      if (str1.isEmpty()) {
        paramString = str2;
      } else {
        StringBuilder stringBuilder1 = new StringBuilder();
        this();
        stringBuilder1.append(str1);
        stringBuilder1.append(".");
        stringBuilder1.append(str2);
        null = stringBuilder1.toString();
      } 
      return (T)Class.forName(null).newInstance();
    } catch (ClassNotFoundException classNotFoundException) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("cannot find implementation for ");
      stringBuilder1.append(paramClass.getCanonicalName());
      stringBuilder1.append(". ");
      stringBuilder1.append(str2);
      stringBuilder1.append(" does not exist");
      throw new RuntimeException(stringBuilder1.toString());
    } catch (IllegalAccessException illegalAccessException) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Cannot access the constructor");
      stringBuilder1.append(paramClass.getCanonicalName());
      throw new RuntimeException(stringBuilder1.toString());
    } catch (InstantiationException instantiationException) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Failed to create an instance of ");
      stringBuilder1.append(paramClass.getCanonicalName());
      throw new RuntimeException(stringBuilder1.toString());
    } 
  }
  
  public static <T extends RoomDatabase> RoomDatabase.Builder<T> inMemoryDatabaseBuilder(Context paramContext, Class<T> paramClass) {
    return new RoomDatabase.Builder<T>(paramContext, paramClass, null);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\room\Room.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */