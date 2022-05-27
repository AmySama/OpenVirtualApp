package de.robv.android.xposed;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public interface IXposedHookLoadPackage extends IXposedMod {
  void handleLoadPackage(XC_LoadPackage.LoadPackageParam paramLoadPackageParam) throws Throwable;
  
  public static final class Wrapper extends XC_LoadPackage {
    private final IXposedHookLoadPackage instance;
    
    public Wrapper(IXposedHookLoadPackage param1IXposedHookLoadPackage) {
      this.instance = param1IXposedHookLoadPackage;
    }
    
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam param1LoadPackageParam) throws Throwable {
      this.instance.handleLoadPackage(param1LoadPackageParam);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\IXposedHookLoadPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */