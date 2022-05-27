package mirror.android.content.pm;

import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class PackageParserNougat {
  public static Class<?> TYPE = RefClass.load(PackageParserNougat.class, "android.content.pm.PackageParser");
  
  @MethodReflectParams({"android.content.pm.PackageParser$Package", "int"})
  public static RefStaticMethod<Void> collectCertificates;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\pm\PackageParserNougat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */