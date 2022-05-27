package mirror.android.graphics.drawable;

import mirror.RefClass;
import mirror.RefObject;

public class Icon {
  public static Class<?> TYPE = RefClass.load(Icon.class, android.graphics.drawable.Icon.class);
  
  public static final int TYPE_BITMAP = 1;
  
  public static final int TYPE_DATA = 3;
  
  public static final int TYPE_RESOURCE = 2;
  
  public static final int TYPE_URI = 4;
  
  public static RefObject<Object> mObj1;
  
  public static RefObject<String> mString1;
  
  public static RefObject<Integer> mType;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\graphics\drawable\Icon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */