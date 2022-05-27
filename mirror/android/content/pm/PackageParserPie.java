package mirror.android.content.pm;

import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class PackageParserPie {
  public static Class<?> TYPE = RefClass.load(PackageParserPie.class, "android.content.pm.PackageParser");
  
  @MethodReflectParams({"android.content.pm.PackageParser$Package", "boolean"})
  public static RefStaticMethod<Void> collectCertificates;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\pm\PackageParserPie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */