package dalvik.system;

public class VMRuntime {
  public static VMRuntime getRuntime() {
    throw new IllegalArgumentException("stub");
  }
  
  public native void setHiddenApiExemptions(String[] paramArrayOfString);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\dalvik\system\VMRuntime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */