package de.robv.android.xposed;

import de.robv.android.xposed.callbacks.XC_InitPackageResources;

public interface IXposedHookInitPackageResources extends IXposedMod {
  void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam paramInitPackageResourcesParam) throws Throwable;
  
  public static final class Wrapper extends XC_InitPackageResources {
    private final IXposedHookInitPackageResources instance;
    
    public Wrapper(IXposedHookInitPackageResources param1IXposedHookInitPackageResources) {
      this.instance = param1IXposedHookInitPackageResources;
    }
    
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam param1InitPackageResourcesParam) throws Throwable {
      this.instance.handleInitPackageResources(param1InitPackageResourcesParam);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\IXposedHookInitPackageResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */