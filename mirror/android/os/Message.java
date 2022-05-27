package mirror.android.os;

import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class Message {
  public static Class<?> TYPE = RefClass.load(Message.class, android.os.Message.class);
  
  @MethodParams({int.class})
  public static RefStaticMethod<Void> updateCheckRecycle;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\os\Message.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */