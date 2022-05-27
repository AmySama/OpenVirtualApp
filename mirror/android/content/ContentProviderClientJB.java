package mirror.android.content;

import android.content.ContentProviderClient;
import mirror.MethodReflectParams;
import mirror.RefClass;
import mirror.RefConstructor;

public class ContentProviderClientJB {
  public static Class TYPE = RefClass.load(ContentProviderClientJB.class, ContentProviderClient.class);
  
  @MethodReflectParams({"android.content.ContentResolver", "android.content.IContentProvider", "boolean"})
  public static RefConstructor<ContentProviderClient> ctor;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\ContentProviderClientJB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */