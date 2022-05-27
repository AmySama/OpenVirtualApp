package android.arch.lifecycle;

import java.util.HashMap;
import java.util.Map;

public class MethodCallsLogger {
  private Map<String, Integer> mCalledMethods = new HashMap<String, Integer>();
  
  public boolean approveCall(String paramString, int paramInt) {
    int i;
    Integer integer = this.mCalledMethods.get(paramString);
    boolean bool = false;
    if (integer != null) {
      i = integer.intValue();
    } else {
      i = 0;
    } 
    if ((i & paramInt) != 0)
      bool = true; 
    this.mCalledMethods.put(paramString, Integer.valueOf(paramInt | i));
    return bool ^ true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\MethodCallsLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */