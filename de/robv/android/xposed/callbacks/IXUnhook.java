package de.robv.android.xposed.callbacks;

public interface IXUnhook<T> {
  T getCallback();
  
  void unhook();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\callbacks\IXUnhook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */