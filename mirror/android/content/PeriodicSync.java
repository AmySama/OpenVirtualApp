package mirror.android.content;

import android.os.Bundle;
import com.lody.virtual.helper.compat.ObjectsCompat;
import mirror.RefClass;
import mirror.RefLong;

public class PeriodicSync {
  public static Class<?> TYPE = RefClass.load(PeriodicSync.class, android.content.PeriodicSync.class);
  
  public static RefLong flexTime;
  
  public static android.content.PeriodicSync clone(android.content.PeriodicSync paramPeriodicSync) {
    android.content.PeriodicSync periodicSync = new android.content.PeriodicSync(paramPeriodicSync.account, paramPeriodicSync.authority, paramPeriodicSync.extras, paramPeriodicSync.period);
    RefLong refLong = flexTime;
    refLong.set(periodicSync, refLong.get(paramPeriodicSync));
    return periodicSync;
  }
  
  public static boolean syncExtrasEquals(Bundle paramBundle1, Bundle paramBundle2) {
    if (paramBundle1.size() != paramBundle2.size())
      return false; 
    if (paramBundle1.isEmpty())
      return true; 
    for (String str : paramBundle1.keySet()) {
      if (!paramBundle2.containsKey(str))
        return false; 
      if (!ObjectsCompat.equals(paramBundle1.get(str), paramBundle2.get(str)))
        return false; 
    } 
    return true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\PeriodicSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */