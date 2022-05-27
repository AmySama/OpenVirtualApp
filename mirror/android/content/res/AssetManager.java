package mirror.android.content.res;

import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefMethod;

public class AssetManager {
  public static Class<?> TYPE = RefClass.load(AssetManager.class, android.content.res.AssetManager.class);
  
  @MethodParams({String.class})
  public static RefMethod<Integer> addAssetPath;
  
  public static RefConstructor<android.content.res.AssetManager> ctor;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\res\AssetManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */