package android.arch.persistence.db;

import java.io.Closeable;

public interface SupportSQLiteProgram extends Closeable {
  void bindBlob(int paramInt, byte[] paramArrayOfbyte);
  
  void bindDouble(int paramInt, double paramDouble);
  
  void bindLong(int paramInt, long paramLong);
  
  void bindNull(int paramInt);
  
  void bindString(int paramInt, String paramString);
  
  void clearBindings();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\db\SupportSQLiteProgram.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */