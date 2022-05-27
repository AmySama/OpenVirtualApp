package android.support.multidex;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.ZipException;

final class ZipUtil {
  private static final int BUFFER_SIZE = 16384;
  
  private static final int ENDHDR = 22;
  
  private static final int ENDSIG = 101010256;
  
  static long computeCrcOfCentralDir(RandomAccessFile paramRandomAccessFile, CentralDirectory paramCentralDirectory) throws IOException {
    CRC32 cRC32 = new CRC32();
    long l = paramCentralDirectory.size;
    paramRandomAccessFile.seek(paramCentralDirectory.offset);
    int i = (int)Math.min(16384L, l);
    byte[] arrayOfByte = new byte[16384];
    for (i = paramRandomAccessFile.read(arrayOfByte, 0, i); i != -1; i = paramRandomAccessFile.read(arrayOfByte, 0, (int)Math.min(16384L, l))) {
      cRC32.update(arrayOfByte, 0, i);
      l -= i;
      if (l == 0L)
        break; 
    } 
    return cRC32.getValue();
  }
  
  static CentralDirectory findCentralDirectory(RandomAccessFile paramRandomAccessFile) throws IOException, ZipException {
    long l1 = paramRandomAccessFile.length() - 22L;
    long l2 = 0L;
    if (l1 >= 0L) {
      long l = l1 - 65536L;
      if (l >= 0L)
        l2 = l; 
      int i = Integer.reverseBytes(101010256);
      while (true) {
        paramRandomAccessFile.seek(l1);
        if (paramRandomAccessFile.readInt() == i) {
          paramRandomAccessFile.skipBytes(2);
          paramRandomAccessFile.skipBytes(2);
          paramRandomAccessFile.skipBytes(2);
          paramRandomAccessFile.skipBytes(2);
          CentralDirectory centralDirectory = new CentralDirectory();
          centralDirectory.size = Integer.reverseBytes(paramRandomAccessFile.readInt()) & 0xFFFFFFFFL;
          centralDirectory.offset = Integer.reverseBytes(paramRandomAccessFile.readInt()) & 0xFFFFFFFFL;
          return centralDirectory;
        } 
        l1--;
        if (l1 >= l2)
          continue; 
        throw new ZipException("End Of Central Directory signature not found");
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("File too short to be a zip file: ");
    stringBuilder.append(paramRandomAccessFile.length());
    throw new ZipException(stringBuilder.toString());
  }
  
  static long getZipCrc(File paramFile) throws IOException {
    RandomAccessFile randomAccessFile = new RandomAccessFile(paramFile, "r");
    try {
      return computeCrcOfCentralDir(randomAccessFile, findCentralDirectory(randomAccessFile));
    } finally {
      randomAccessFile.close();
    } 
  }
  
  static class CentralDirectory {
    long offset;
    
    long size;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\multidex\ZipUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */