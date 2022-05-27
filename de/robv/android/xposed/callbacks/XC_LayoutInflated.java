package de.robv.android.xposed.callbacks;

import android.view.View;
import de.robv.android.xposed.XposedBridge;

public abstract class XC_LayoutInflated extends XCallback {
  public XC_LayoutInflated() {}
  
  public XC_LayoutInflated(int paramInt) {
    super(paramInt);
  }
  
  protected void call(XCallback.Param paramParam) throws Throwable {
    if (paramParam instanceof LayoutInflatedParam)
      handleLayoutInflated((LayoutInflatedParam)paramParam); 
  }
  
  public abstract void handleLayoutInflated(LayoutInflatedParam paramLayoutInflatedParam) throws Throwable;
  
  public static final class LayoutInflatedParam extends XCallback.Param {
    public View view;
    
    public LayoutInflatedParam(XposedBridge.CopyOnWriteSortedSet<XC_LayoutInflated> param1CopyOnWriteSortedSet) {
      super((XposedBridge.CopyOnWriteSortedSet)param1CopyOnWriteSortedSet);
    }
  }
  
  public class Unhook implements IXUnhook<XC_LayoutInflated> {
    private final int id;
    
    private final String resDir;
    
    public Unhook(String param1String, int param1Int) {
      this.resDir = param1String;
      this.id = param1Int;
    }
    
    public XC_LayoutInflated getCallback() {
      return XC_LayoutInflated.this;
    }
    
    public int getId() {
      return this.id;
    }
    
    public void unhook() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\callbacks\XC_LayoutInflated.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */