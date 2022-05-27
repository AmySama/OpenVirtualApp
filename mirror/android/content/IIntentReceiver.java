package mirror.android.content;

import android.content.Intent;
import android.os.Bundle;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;

public class IIntentReceiver {
  public static Class<?> TYPE = RefClass.load(IIntentReceiver.class, "android.content.IIntentReceiver");
  
  @MethodParams({Intent.class, int.class, String.class, Bundle.class, boolean.class, boolean.class})
  public static RefMethod<Void> performReceive;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\IIntentReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */