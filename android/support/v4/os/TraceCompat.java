package android.support.v4.os;

import android.os.Build;
import android.os.Trace;

public final class TraceCompat {
  public static void beginSection(String paramString) {
    if (Build.VERSION.SDK_INT >= 18)
      Trace.beginSection(paramString); 
  }
  
  public static void endSection() {
    if (Build.VERSION.SDK_INT >= 18)
      Trace.endSection(); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\os\TraceCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */