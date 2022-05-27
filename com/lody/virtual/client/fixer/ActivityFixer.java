package com.lody.virtual.client.fixer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import mirror.com.android.internal.R_Hide;

public final class ActivityFixer {
  public static void fixActivity(Activity paramActivity) {
    Context context = paramActivity.getBaseContext();
    try {
      TypedArray typedArray = paramActivity.obtainStyledAttributes((int[])R_Hide.styleable.Window.get());
    } finally {
      Exception exception = null;
    } 
    if (Build.VERSION.SDK_INT >= 21) {
      Intent intent = paramActivity.getIntent();
      ApplicationInfo applicationInfo = context.getApplicationInfo();
      PackageManager packageManager = paramActivity.getPackageManager();
      if (intent != null && paramActivity.isTaskRoot())
        try {
          Bitmap bitmap;
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append(applicationInfo.loadLabel(packageManager));
          stringBuilder.append("");
          String str = stringBuilder.toString();
          stringBuilder = null;
          Drawable drawable = applicationInfo.loadIcon(packageManager);
          if (drawable instanceof BitmapDrawable)
            bitmap = ((BitmapDrawable)drawable).getBitmap(); 
          ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription();
          this(str, bitmap);
        } finally {
          paramActivity = null;
        }  
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\fixer\ActivityFixer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */