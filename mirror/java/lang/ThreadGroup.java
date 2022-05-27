package mirror.java.lang;

import java.util.List;
import mirror.RefClass;
import mirror.RefObject;

public class ThreadGroup {
  public static Class<?> TYPE = RefClass.load(ThreadGroup.class, java.lang.ThreadGroup.class);
  
  public static RefObject<List<java.lang.ThreadGroup>> groups;
  
  public static RefObject<java.lang.ThreadGroup> parent;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\java\lang\ThreadGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */