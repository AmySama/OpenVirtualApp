package android.support.v13.view.inputmethod;

import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

public final class InputConnectionCompat {
  private static final String COMMIT_CONTENT_ACTION = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
  
  private static final String COMMIT_CONTENT_CONTENT_URI_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
  
  private static final String COMMIT_CONTENT_DESCRIPTION_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
  
  private static final String COMMIT_CONTENT_FLAGS_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
  
  private static final String COMMIT_CONTENT_LINK_URI_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
  
  private static final String COMMIT_CONTENT_OPTS_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
  
  private static final String COMMIT_CONTENT_RESULT_RECEIVER = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
  
  public static int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;
  
  public static boolean commitContent(InputConnection paramInputConnection, EditorInfo paramEditorInfo, InputContentInfoCompat paramInputContentInfoCompat, int paramInt, Bundle paramBundle) {
    ClipDescription clipDescription = paramInputContentInfoCompat.getDescription();
    String[] arrayOfString = EditorInfoCompat.getContentMimeTypes(paramEditorInfo);
    int i = arrayOfString.length;
    byte b = 0;
    while (true) {
      if (b < i) {
        if (clipDescription.hasMimeType(arrayOfString[b])) {
          b = 1;
          break;
        } 
        b++;
        continue;
      } 
      b = 0;
      break;
    } 
    if (b == 0)
      return false; 
    if (Build.VERSION.SDK_INT >= 25)
      return paramInputConnection.commitContent((InputContentInfo)paramInputContentInfoCompat.unwrap(), paramInt, paramBundle); 
    Bundle bundle = new Bundle();
    bundle.putParcelable("android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI", (Parcelable)paramInputContentInfoCompat.getContentUri());
    bundle.putParcelable("android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION", (Parcelable)paramInputContentInfoCompat.getDescription());
    bundle.putParcelable("android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI", (Parcelable)paramInputContentInfoCompat.getLinkUri());
    bundle.putInt("android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS", paramInt);
    bundle.putParcelable("android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS", (Parcelable)paramBundle);
    return paramInputConnection.performPrivateCommand("android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", bundle);
  }
  
  public static InputConnection createWrapper(InputConnection paramInputConnection, EditorInfo paramEditorInfo, final OnCommitContentListener listener) {
    if (paramInputConnection != null) {
      if (paramEditorInfo != null) {
        if (listener != null)
          return (InputConnection)((Build.VERSION.SDK_INT >= 25) ? new InputConnectionWrapper(paramInputConnection, false) {
              public boolean commitContent(InputContentInfo param1InputContentInfo, int param1Int, Bundle param1Bundle) {
                return listener.onCommitContent(InputContentInfoCompat.wrap(param1InputContentInfo), param1Int, param1Bundle) ? true : super.commitContent(param1InputContentInfo, param1Int, param1Bundle);
              }
            } : (((EditorInfoCompat.getContentMimeTypes(paramEditorInfo)).length == 0) ? paramInputConnection : new InputConnectionWrapper(paramInputConnection, false) {
              public boolean performPrivateCommand(String param1String, Bundle param1Bundle) {
                return InputConnectionCompat.handlePerformPrivateCommand(param1String, param1Bundle, listener) ? true : super.performPrivateCommand(param1String, param1Bundle);
              }
            })); 
        throw new IllegalArgumentException("onCommitContentListener must be non-null");
      } 
      throw new IllegalArgumentException("editorInfo must be non-null");
    } 
    throw new IllegalArgumentException("inputConnection must be non-null");
  }
  
  static boolean handlePerformPrivateCommand(String paramString, Bundle paramBundle, OnCommitContentListener paramOnCommitContentListener) {
    if (!TextUtils.equals("android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", paramString))
      return false; 
    if (paramBundle == null)
      return false; 
    try {
      ResultReceiver resultReceiver = (ResultReceiver)paramBundle.getParcelable("android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER");
    } finally {
      paramBundle = null;
    } 
    if (paramString != null)
      paramString.send(0, null); 
    throw paramBundle;
  }
  
  public static interface OnCommitContentListener {
    boolean onCommitContent(InputContentInfoCompat param1InputContentInfoCompat, int param1Int, Bundle param1Bundle);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v13\view\inputmethod\InputConnectionCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */