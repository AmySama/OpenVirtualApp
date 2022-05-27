package de.robv.android.xposed.callbacks;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.XposedBridge;

public abstract class XC_InitPackageResources extends XCallback implements IXposedHookInitPackageResources {
  public XC_InitPackageResources() {}
  
  public XC_InitPackageResources(int paramInt) {
    super(paramInt);
  }
  
  protected void call(XCallback.Param paramParam) throws Throwable {
    if (paramParam instanceof InitPackageResourcesParam)
      handleInitPackageResources((InitPackageResourcesParam)paramParam); 
  }
  
  public static final class InitPackageResourcesParam extends XCallback.Param {
    public String packageName;
    
    public InitPackageResourcesParam(XposedBridge.CopyOnWriteSortedSet<XC_InitPackageResources> param1CopyOnWriteSortedSet) {
      super((XposedBridge.CopyOnWriteSortedSet)param1CopyOnWriteSortedSet);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\callbacks\XC_InitPackageResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */