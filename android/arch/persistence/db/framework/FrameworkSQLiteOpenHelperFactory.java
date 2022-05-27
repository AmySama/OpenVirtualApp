package android.arch.persistence.db.framework;

import android.arch.persistence.db.SupportSQLiteOpenHelper;

public final class FrameworkSQLiteOpenHelperFactory implements SupportSQLiteOpenHelper.Factory {
  public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration paramConfiguration) {
    return new FrameworkSQLiteOpenHelper(paramConfiguration.context, paramConfiguration.name, paramConfiguration.callback);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\db\framework\FrameworkSQLiteOpenHelperFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */