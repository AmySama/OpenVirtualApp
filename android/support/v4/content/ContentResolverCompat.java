package android.support.v4.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.os.CancellationSignal;

public final class ContentResolverCompat {
  public static Cursor query(ContentResolver paramContentResolver, Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2, CancellationSignal paramCancellationSignal) {
    if (Build.VERSION.SDK_INT >= 16) {
      if (paramCancellationSignal != null) {
        try {
          Object object = paramCancellationSignal.getCancellationSignalObject();
        } catch (Exception exception) {}
      } else {
        paramCancellationSignal = null;
      } 
      return exception.query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2, (CancellationSignal)paramCancellationSignal);
    } 
    if (paramCancellationSignal != null)
      paramCancellationSignal.throwIfCanceled(); 
    return paramContentResolver.query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\ContentResolverCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */