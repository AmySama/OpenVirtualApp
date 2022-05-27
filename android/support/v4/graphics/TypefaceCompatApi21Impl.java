package android.support.v4.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.v4.provider.FontsContractCompat;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
  private static final String TAG = "TypefaceCompatApi21Impl";
  
  private File getFile(ParcelFileDescriptor paramParcelFileDescriptor) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("/proc/self/fd/");
      stringBuilder.append(paramParcelFileDescriptor.getFd());
      String str = Os.readlink(stringBuilder.toString());
      if (OsConstants.S_ISREG((Os.stat(str)).st_mode))
        return new File(str); 
    } catch (ErrnoException errnoException) {}
    return null;
  }
  
  public Typeface createFromFontInfo(Context paramContext, CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt) {
    if (paramArrayOfFontInfo.length < 1)
      return null; 
    FontsContractCompat.FontInfo fontInfo = findBestInfo(paramArrayOfFontInfo, paramInt);
    ContentResolver contentResolver = paramContext.getContentResolver();
    try {
      ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(fontInfo.getUri(), "r", paramCancellationSignal);
      try {
        FileInputStream fileInputStream;
        File file = getFile(parcelFileDescriptor);
        if (file == null || !file.canRead()) {
          fileInputStream = new FileInputStream();
          this(parcelFileDescriptor.getFileDescriptor());
          try {
            return createFromInputStream(paramContext, fileInputStream);
          } finally {
            fontInfo = null;
          } 
        } 
        return Typeface.createFromFile((File)fileInputStream);
      } finally {
        contentResolver = null;
      } 
    } catch (IOException iOException) {
      return null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\graphics\TypefaceCompatApi21Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */