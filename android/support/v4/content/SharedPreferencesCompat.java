package android.support.v4.content;

import android.content.SharedPreferences;

@Deprecated
public final class SharedPreferencesCompat {
  @Deprecated
  public static final class EditorCompat {
    private static EditorCompat sInstance;
    
    private final Helper mHelper = new Helper();
    
    @Deprecated
    public static EditorCompat getInstance() {
      if (sInstance == null)
        sInstance = new EditorCompat(); 
      return sInstance;
    }
    
    @Deprecated
    public void apply(SharedPreferences.Editor param1Editor) {
      this.mHelper.apply(param1Editor);
    }
    
    private static class Helper {
      public void apply(SharedPreferences.Editor param2Editor) {
        try {
          param2Editor.apply();
        } catch (AbstractMethodError abstractMethodError) {
          param2Editor.commit();
        } 
      }
    }
  }
  
  private static class Helper {
    public void apply(SharedPreferences.Editor param1Editor) {
      try {
        param1Editor.apply();
      } catch (AbstractMethodError abstractMethodError) {
        param1Editor.commit();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\SharedPreferencesCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */