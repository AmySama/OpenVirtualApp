package com.lody.virtual.client.hook.proxies.wallpaper;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import mirror.android.app.IWallpaperManager;

public class WallpaperManagerStub extends BinderInvocationProxy {
  public WallpaperManagerStub() {
    super(IWallpaperManager.Stub.asInterface, "wallpaper");
  }
  
  public void inject() throws Throwable {
    super.inject();
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getWallpaper"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setWallpaper"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setDimensionHints"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setDisplayPadding"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("clearWallpaper"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setWallpaperComponentChecked"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isWallpaperSupported"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isSetWallpaperAllowed"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\wallpaper\WallpaperManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */