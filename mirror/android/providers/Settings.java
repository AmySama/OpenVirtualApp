package mirror.android.providers;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefObject;
import mirror.RefStaticObject;

public class Settings {
  public static Class<?> TYPE = RefClass.load(Settings.class, android.provider.Settings.class);
  
  public static class ContentProviderHolder {
    public static Class<?> TYPE = RefClass.load(ContentProviderHolder.class, "android.provider.Settings$ContentProviderHolder");
    
    public static RefObject<IInterface> mContentProvider;
  }
  
  public static class Global {
    public static Class<?> TYPE = RefClass.load(Global.class, android.provider.Settings.Global.class);
    
    public static RefStaticObject<Object> sNameValueCache;
  }
  
  public static class NameValueCache {
    public static Class<?> TYPE = RefClass.load(NameValueCache.class, "android.provider.Settings$NameValueCache");
    
    public static RefObject<Object> mContentProvider;
  }
  
  public static class NameValueCacheOreo {
    public static Class<?> TYPE = RefClass.load(NameValueCacheOreo.class, "android.provider.Settings$NameValueCache");
    
    public static RefObject<Object> mProviderHolder;
  }
  
  public static class Secure {
    public static Class<?> TYPE = RefClass.load(Secure.class, android.provider.Settings.Secure.class);
    
    public static RefStaticObject<Object> sNameValueCache;
  }
  
  public static class System {
    public static Class<?> TYPE = RefClass.load(System.class, android.provider.Settings.System.class);
    
    public static RefStaticObject<Object> sNameValueCache;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\providers\Settings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */