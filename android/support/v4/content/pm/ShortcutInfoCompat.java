package android.support.v4.content.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.graphics.drawable.IconCompat;
import android.text.TextUtils;
import java.util.Arrays;

public class ShortcutInfoCompat {
  private ComponentName mActivity;
  
  private Context mContext;
  
  private CharSequence mDisabledMessage;
  
  private IconCompat mIcon;
  
  private String mId;
  
  private Intent[] mIntents;
  
  private boolean mIsAlwaysBadged;
  
  private CharSequence mLabel;
  
  private CharSequence mLongLabel;
  
  private ShortcutInfoCompat() {}
  
  Intent addToIntent(Intent paramIntent) {
    Intent[] arrayOfIntent = this.mIntents;
    paramIntent.putExtra("android.intent.extra.shortcut.INTENT", (Parcelable)arrayOfIntent[arrayOfIntent.length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.mLabel.toString());
    if (this.mIcon != null) {
      Drawable drawable;
      ComponentName componentName = null;
      Intent[] arrayOfIntent1 = null;
      if (this.mIsAlwaysBadged) {
        Intent[] arrayOfIntent2;
        PackageManager packageManager = this.mContext.getPackageManager();
        componentName = this.mActivity;
        arrayOfIntent = arrayOfIntent1;
        if (componentName != null)
          try {
            Drawable drawable1 = packageManager.getActivityIcon(componentName);
          } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
            arrayOfIntent2 = arrayOfIntent1;
          }  
        Intent[] arrayOfIntent3 = arrayOfIntent2;
        if (arrayOfIntent2 == null)
          drawable = this.mContext.getApplicationInfo().loadIcon(packageManager); 
      } 
      this.mIcon.addToShortcutIntent(paramIntent, drawable);
    } 
    return paramIntent;
  }
  
  public ComponentName getActivity() {
    return this.mActivity;
  }
  
  public CharSequence getDisabledMessage() {
    return this.mDisabledMessage;
  }
  
  public String getId() {
    return this.mId;
  }
  
  public Intent getIntent() {
    Intent[] arrayOfIntent = this.mIntents;
    return arrayOfIntent[arrayOfIntent.length - 1];
  }
  
  public Intent[] getIntents() {
    Intent[] arrayOfIntent = this.mIntents;
    return Arrays.<Intent>copyOf(arrayOfIntent, arrayOfIntent.length);
  }
  
  public CharSequence getLongLabel() {
    return this.mLongLabel;
  }
  
  public CharSequence getShortLabel() {
    return this.mLabel;
  }
  
  public ShortcutInfo toShortcutInfo() {
    ShortcutInfo.Builder builder = (new ShortcutInfo.Builder(this.mContext, this.mId)).setShortLabel(this.mLabel).setIntents(this.mIntents);
    IconCompat iconCompat = this.mIcon;
    if (iconCompat != null)
      builder.setIcon(iconCompat.toIcon()); 
    if (!TextUtils.isEmpty(this.mLongLabel))
      builder.setLongLabel(this.mLongLabel); 
    if (!TextUtils.isEmpty(this.mDisabledMessage))
      builder.setDisabledMessage(this.mDisabledMessage); 
    ComponentName componentName = this.mActivity;
    if (componentName != null)
      builder.setActivity(componentName); 
    return builder.build();
  }
  
  public static class Builder {
    private final ShortcutInfoCompat mInfo;
    
    public Builder(Context param1Context, String param1String) {
      ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat();
      this.mInfo = shortcutInfoCompat;
      ShortcutInfoCompat.access$102(shortcutInfoCompat, param1Context);
      ShortcutInfoCompat.access$202(this.mInfo, param1String);
    }
    
    public ShortcutInfoCompat build() {
      if (!TextUtils.isEmpty(this.mInfo.mLabel)) {
        if (this.mInfo.mIntents != null && this.mInfo.mIntents.length != 0)
          return this.mInfo; 
        throw new IllegalArgumentException("Shortcut much have an intent");
      } 
      throw new IllegalArgumentException("Shortcut much have a non-empty label");
    }
    
    public Builder setActivity(ComponentName param1ComponentName) {
      ShortcutInfoCompat.access$802(this.mInfo, param1ComponentName);
      return this;
    }
    
    public Builder setAlwaysBadged() {
      ShortcutInfoCompat.access$902(this.mInfo, true);
      return this;
    }
    
    public Builder setDisabledMessage(CharSequence param1CharSequence) {
      ShortcutInfoCompat.access$502(this.mInfo, param1CharSequence);
      return this;
    }
    
    public Builder setIcon(IconCompat param1IconCompat) {
      ShortcutInfoCompat.access$702(this.mInfo, param1IconCompat);
      return this;
    }
    
    public Builder setIntent(Intent param1Intent) {
      return setIntents(new Intent[] { param1Intent });
    }
    
    public Builder setIntents(Intent[] param1ArrayOfIntent) {
      ShortcutInfoCompat.access$602(this.mInfo, param1ArrayOfIntent);
      return this;
    }
    
    public Builder setLongLabel(CharSequence param1CharSequence) {
      ShortcutInfoCompat.access$402(this.mInfo, param1CharSequence);
      return this;
    }
    
    public Builder setShortLabel(CharSequence param1CharSequence) {
      ShortcutInfoCompat.access$302(this.mInfo, param1CharSequence);
      return this;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\pm\ShortcutInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */