package de.robv.android.xposed.callbacks;

import android.content.pm.ApplicationInfo;
import com.swift.sandhook.xposedcompat.XposedCompat;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;

public abstract class XC_LoadPackage extends XCallback implements IXposedHookLoadPackage {
  public XC_LoadPackage() {}
  
  public XC_LoadPackage(int paramInt) {
    super(paramInt);
  }
  
  protected void call(XCallback.Param paramParam) throws Throwable {
    if (paramParam instanceof LoadPackageParam)
      handleLoadPackage((LoadPackageParam)paramParam); 
  }
  
  public static final class LoadPackageParam extends XCallback.Param {
    public ApplicationInfo appInfo = XposedCompat.context.getApplicationInfo();
    
    public ClassLoader classLoader = XposedCompat.classLoader;
    
    public boolean isFirstApplication = XposedCompat.isFirstApplication;
    
    public String packageName = XposedCompat.packageName;
    
    public String processName = XposedCompat.processName;
    
    public LoadPackageParam(XposedBridge.CopyOnWriteSortedSet<XC_LoadPackage> param1CopyOnWriteSortedSet) {
      super((XposedBridge.CopyOnWriteSortedSet)param1CopyOnWriteSortedSet);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\callbacks\XC_LoadPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */