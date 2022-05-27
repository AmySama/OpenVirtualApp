package android.support.v13.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.view.inputmethod.InputContentInfo;

public final class InputContentInfoCompat {
  private final InputContentInfoCompatImpl mImpl;
  
  public InputContentInfoCompat(Uri paramUri1, ClipDescription paramClipDescription, Uri paramUri2) {
    if (Build.VERSION.SDK_INT >= 25) {
      this.mImpl = new InputContentInfoCompatApi25Impl(paramUri1, paramClipDescription, paramUri2);
    } else {
      this.mImpl = new InputContentInfoCompatBaseImpl(paramUri1, paramClipDescription, paramUri2);
    } 
  }
  
  private InputContentInfoCompat(InputContentInfoCompatImpl paramInputContentInfoCompatImpl) {
    this.mImpl = paramInputContentInfoCompatImpl;
  }
  
  public static InputContentInfoCompat wrap(Object paramObject) {
    return (paramObject == null) ? null : ((Build.VERSION.SDK_INT < 25) ? null : new InputContentInfoCompat(new InputContentInfoCompatApi25Impl(paramObject)));
  }
  
  public Uri getContentUri() {
    return this.mImpl.getContentUri();
  }
  
  public ClipDescription getDescription() {
    return this.mImpl.getDescription();
  }
  
  public Uri getLinkUri() {
    return this.mImpl.getLinkUri();
  }
  
  public void releasePermission() {
    this.mImpl.releasePermission();
  }
  
  public void requestPermission() {
    this.mImpl.requestPermission();
  }
  
  public Object unwrap() {
    return this.mImpl.getInputContentInfo();
  }
  
  private static final class InputContentInfoCompatApi25Impl implements InputContentInfoCompatImpl {
    final InputContentInfo mObject;
    
    InputContentInfoCompatApi25Impl(Uri param1Uri1, ClipDescription param1ClipDescription, Uri param1Uri2) {
      this.mObject = new InputContentInfo(param1Uri1, param1ClipDescription, param1Uri2);
    }
    
    InputContentInfoCompatApi25Impl(Object param1Object) {
      this.mObject = (InputContentInfo)param1Object;
    }
    
    public Uri getContentUri() {
      return this.mObject.getContentUri();
    }
    
    public ClipDescription getDescription() {
      return this.mObject.getDescription();
    }
    
    public Object getInputContentInfo() {
      return this.mObject;
    }
    
    public Uri getLinkUri() {
      return this.mObject.getLinkUri();
    }
    
    public void releasePermission() {
      this.mObject.releasePermission();
    }
    
    public void requestPermission() {
      this.mObject.requestPermission();
    }
  }
  
  private static final class InputContentInfoCompatBaseImpl implements InputContentInfoCompatImpl {
    private final Uri mContentUri;
    
    private final ClipDescription mDescription;
    
    private final Uri mLinkUri;
    
    InputContentInfoCompatBaseImpl(Uri param1Uri1, ClipDescription param1ClipDescription, Uri param1Uri2) {
      this.mContentUri = param1Uri1;
      this.mDescription = param1ClipDescription;
      this.mLinkUri = param1Uri2;
    }
    
    public Uri getContentUri() {
      return this.mContentUri;
    }
    
    public ClipDescription getDescription() {
      return this.mDescription;
    }
    
    public Object getInputContentInfo() {
      return null;
    }
    
    public Uri getLinkUri() {
      return this.mLinkUri;
    }
    
    public void releasePermission() {}
    
    public void requestPermission() {}
  }
  
  private static interface InputContentInfoCompatImpl {
    Uri getContentUri();
    
    ClipDescription getDescription();
    
    Object getInputContentInfo();
    
    Uri getLinkUri();
    
    void releasePermission();
    
    void requestPermission();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v13\view\inputmethod\InputContentInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */