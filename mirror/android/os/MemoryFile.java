package mirror.android.os;

import java.io.FileDescriptor;
import mirror.RefClass;
import mirror.RefMethod;

public class MemoryFile {
  public static Class<?> TYPE = RefClass.load(MemoryFile.class, android.os.MemoryFile.class);
  
  public static RefMethod<FileDescriptor> getFileDescriptor;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\os\MemoryFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */