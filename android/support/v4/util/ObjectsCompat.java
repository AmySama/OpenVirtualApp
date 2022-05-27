package android.support.v4.util;

import android.os.Build;
import java.util.Arrays;
import java.util.Objects;

public class ObjectsCompat {
  public static boolean equals(Object paramObject1, Object paramObject2) {
    return (Build.VERSION.SDK_INT >= 19) ? Objects.equals(paramObject1, paramObject2) : ((paramObject1 == paramObject2 || (paramObject1 != null && paramObject1.equals(paramObject2))));
  }
  
  public static int hash(Object... paramVarArgs) {
    return (Build.VERSION.SDK_INT >= 19) ? Objects.hash(paramVarArgs) : Arrays.hashCode(paramVarArgs);
  }
  
  public static int hashCode(Object paramObject) {
    boolean bool;
    if (paramObject != null) {
      bool = paramObject.hashCode();
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\ObjectsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */