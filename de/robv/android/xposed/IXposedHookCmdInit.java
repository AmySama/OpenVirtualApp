package de.robv.android.xposed;

public interface IXposedHookCmdInit extends IXposedMod {
  void initCmdApp(StartupParam paramStartupParam) throws Throwable;
  
  public static final class StartupParam {
    public String modulePath;
    
    public String startClassName;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\IXposedHookCmdInit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */