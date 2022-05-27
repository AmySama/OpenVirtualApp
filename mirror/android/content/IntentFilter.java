package mirror.android.content;

import java.util.List;
import mirror.RefClass;
import mirror.RefObject;

public class IntentFilter {
  public static Class TYPE = RefClass.load(IntentFilter.class, android.content.IntentFilter.class);
  
  public static RefObject<List<String>> mActions;
  
  public static RefObject<List<String>> mCategories;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\IntentFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */