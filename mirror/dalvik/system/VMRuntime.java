package mirror.dalvik.system;

import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;
import mirror.RefStaticMethod;

public class VMRuntime {
  public static Class<?> TYPE = RefClass.load(VMRuntime.class, "dalvik.system.VMRuntime");
  
  public static RefStaticMethod<String> getCurrentInstructionSet;
  
  public static RefStaticMethod<Object> getRuntime;
  
  public static RefMethod<Boolean> is64Bit;
  
  @MethodParams({String.class})
  public static RefStaticMethod<Boolean> is64BitAbi;
  
  public static RefMethod<Boolean> isJavaDebuggable;
  
  @MethodParams({int.class})
  public static RefMethod<Void> setTargetSdkVersion;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\dalvik\system\VMRuntime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */