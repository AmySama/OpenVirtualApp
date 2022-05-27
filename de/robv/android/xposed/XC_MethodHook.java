package de.robv.android.xposed;

import de.robv.android.xposed.callbacks.IXUnhook;
import de.robv.android.xposed.callbacks.XCallback;
import java.lang.reflect.Member;

public abstract class XC_MethodHook extends XCallback {
  public XC_MethodHook() {}
  
  public XC_MethodHook(int paramInt) {
    super(paramInt);
  }
  
  protected void afterHookedMethod(MethodHookParam paramMethodHookParam) throws Throwable {}
  
  protected void beforeHookedMethod(MethodHookParam paramMethodHookParam) throws Throwable {}
  
  public void callAfterHookedMethod(MethodHookParam paramMethodHookParam) throws Throwable {
    afterHookedMethod(paramMethodHookParam);
  }
  
  public void callBeforeHookedMethod(MethodHookParam paramMethodHookParam) throws Throwable {
    beforeHookedMethod(paramMethodHookParam);
  }
  
  public static final class MethodHookParam extends XCallback.Param {
    public Object[] args;
    
    public Member method;
    
    private Object result = null;
    
    public boolean returnEarly = false;
    
    public Object thisObject;
    
    private Throwable throwable = null;
    
    public Object getResult() {
      return this.result;
    }
    
    public Object getResultOrThrowable() throws Throwable {
      Throwable throwable = this.throwable;
      if (throwable == null)
        return this.result; 
      throw throwable;
    }
    
    public Throwable getThrowable() {
      return this.throwable;
    }
    
    public boolean hasThrowable() {
      boolean bool;
      if (this.throwable != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void setResult(Object param1Object) {
      this.result = param1Object;
      this.throwable = null;
      this.returnEarly = true;
    }
    
    public void setThrowable(Throwable param1Throwable) {
      this.throwable = param1Throwable;
      this.result = null;
      this.returnEarly = true;
    }
  }
  
  public class Unhook implements IXUnhook<XC_MethodHook> {
    private final Member hookMethod;
    
    Unhook(Member param1Member) {
      this.hookMethod = param1Member;
    }
    
    public XC_MethodHook getCallback() {
      return XC_MethodHook.this;
    }
    
    public Member getHookedMethod() {
      return this.hookMethod;
    }
    
    public void unhook() {
      XposedBridge.unhookMethod(this.hookMethod, XC_MethodHook.this);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\XC_MethodHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */