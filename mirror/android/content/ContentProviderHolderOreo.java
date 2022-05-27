package mirror.android.content;

import android.content.pm.ProviderInfo;
import android.os.IInterface;
import mirror.RefBoolean;
import mirror.RefClass;
import mirror.RefObject;

public class ContentProviderHolderOreo {
  public static Class<?> TYPE = RefClass.load(ContentProviderHolderOreo.class, "android.app.ContentProviderHolder");
  
  public static RefObject<ProviderInfo> info;
  
  public static RefBoolean noReleaseNeeded;
  
  public static RefObject<IInterface> provider;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\ContentProviderHolderOreo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */