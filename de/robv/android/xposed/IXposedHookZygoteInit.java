package de.robv.android.xposed;

public interface IXposedHookZygoteInit extends IXposedMod {
  void initZygote(StartupParam paramStartupParam) throws Throwable;
  
  public static final class StartupParam {
    public String modulePath;
    
    public boolean startsSystemServer;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\IXposedHookZygoteInit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */