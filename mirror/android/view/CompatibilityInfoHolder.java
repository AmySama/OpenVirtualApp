package mirror.android.view;

import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefMethod;

public class CompatibilityInfoHolder {
  public static Class<?> Class = RefClass.load(CompatibilityInfoHolder.class, "android.view.CompatibilityInfoHolder");
  
  @MethodReflectParams({"android.content.res.CompatibilityInfo"})
  public static RefMethod<Void> set;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\view\CompatibilityInfoHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */