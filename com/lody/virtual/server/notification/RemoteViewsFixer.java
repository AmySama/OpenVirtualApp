package com.lody.virtual.server.notification;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.lody.virtual.R;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.helper.utils.VLog;
import mirror.android.widget.RemoteViews;

class RemoteViewsFixer {
  private static final boolean DEBUG = false;
  
  private static final String TAG = NotificationCompat.TAG;
  
  private boolean init = false;
  
  private NotificationCompat mNotificationCompat;
  
  private final WidthCompat mWidthCompat = new WidthCompat();
  
  private int notification_max_height;
  
  private int notification_mid_height;
  
  private int notification_min_height;
  
  private int notification_padding;
  
  private int notification_panel_width;
  
  private int notification_side_padding;
  
  RemoteViewsFixer(NotificationCompat paramNotificationCompat) {
    this.mNotificationCompat = paramNotificationCompat;
  }
  
  private View apply(Context paramContext, RemoteViews paramRemoteViews) {
    try {
      View view = LayoutInflater.from(paramContext).inflate(paramRemoteViews.getLayoutId(), null, false);
      try {
        Reflect.on(view).call("setTagInternal", new Object[] { Reflect.on("com.android.internal.R$id").get("widget_frame"), Integer.valueOf(paramRemoteViews.getLayoutId()) });
      } catch (Exception null) {
        try {
          VLog.w(TAG, "setTagInternal", new Object[] { exception });
        } catch (Exception exception1) {}
      } 
    } catch (Exception exception) {
      paramContext = null;
    } 
    VLog.w(TAG, "inflate", new Object[] { exception });
  }
  
  private View createView(Context paramContext, RemoteViews paramRemoteViews, boolean paramBoolean) {
    int i;
    if (paramRemoteViews == null)
      return null; 
    Context context = this.mNotificationCompat.getHostContext();
    init(context);
    if (paramBoolean) {
      i = this.notification_max_height;
    } else {
      i = this.notification_min_height;
    } 
    int j = this.mWidthCompat.getNotificationWidth(context, this.notification_panel_width, i, this.notification_side_padding);
    FrameLayout frameLayout = new FrameLayout(paramContext);
    View view = apply(paramContext, paramRemoteViews);
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
    layoutParams.gravity = 16;
    frameLayout.addView(view, (ViewGroup.LayoutParams)layoutParams);
    if (view instanceof ViewGroup) {
      VLog.v(TAG, "createView:fixTextView");
      fixTextView((ViewGroup)view);
    } 
    frameLayout.layout(0, 0, j, i);
    frameLayout.measure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), View.MeasureSpec.makeMeasureSpec(i, -2147483648));
    frameLayout.layout(0, 0, j, frameLayout.getMeasuredHeight());
    return (View)frameLayout;
  }
  
  private void fixTextView(ViewGroup paramViewGroup) {
    int i = paramViewGroup.getChildCount();
    for (byte b = 0; b < i; b++) {
      TextView textView;
      View view = paramViewGroup.getChildAt(b);
      if (view instanceof TextView) {
        textView = (TextView)view;
        if (isSingleLine(textView)) {
          textView.setSingleLine(false);
          textView.setMaxLines(1);
        } 
      } else if (textView instanceof ViewGroup) {
        fixTextView((ViewGroup)textView);
      } 
    } 
  }
  
  private int getDimem(Context paramContext1, Context paramContext2, String paramString, int paramInt) {
    if (paramContext2 != null) {
      int i = paramContext2.getResources().getIdentifier(paramString, "dimen", "com.android.systemui");
      if (i != 0)
        try {
          return Math.round(paramContext2.getResources().getDimension(i));
        } catch (Exception exception) {} 
    } 
    if (paramInt == 0) {
      paramInt = 0;
    } else {
      paramInt = Math.round(paramContext1.getResources().getDimension(paramInt));
    } 
    return paramInt;
  }
  
  private void init(Context paramContext) {
    if (this.init)
      return; 
    this.init = true;
    if (this.notification_panel_width == 0) {
      Context context = null;
      try {
        Context context1 = paramContext.createPackageContext("com.android.systemui", 2);
        context = context1;
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {}
      if (Build.VERSION.SDK_INT <= 19) {
        this.notification_side_padding = 0;
      } else {
        this.notification_side_padding = getDimem(paramContext, context, "notification_side_padding", R.dimen.notification_side_padding);
      } 
      int i = getDimem(paramContext, context, "notification_panel_width", R.dimen.notification_panel_width);
      this.notification_panel_width = i;
      if (i <= 0)
        this.notification_panel_width = (paramContext.getResources().getDisplayMetrics()).widthPixels; 
      this.notification_min_height = getDimem(paramContext, context, "notification_min_height", R.dimen.notification_min_height);
      this.notification_max_height = getDimem(paramContext, context, "notification_max_height", R.dimen.notification_max_height);
      this.notification_mid_height = getDimem(paramContext, context, "notification_mid_height", R.dimen.notification_mid_height);
      this.notification_padding = getDimem(paramContext, context, "notification_padding", R.dimen.notification_padding);
    } 
  }
  
  private boolean isSingleLine(TextView paramTextView) {
    boolean bool;
    try {
      bool = ((Boolean)Reflect.on(paramTextView).get("mSingleLine")).booleanValue();
    } catch (Exception exception) {
      if ((paramTextView.getInputType() & 0x20000) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
    } 
    return bool;
  }
  
  Bitmap createBitmap(View paramView) {
    if (paramView == null)
      return null; 
    paramView.setDrawingCacheEnabled(true);
    paramView.buildDrawingCache();
    return paramView.getDrawingCache();
  }
  
  public RemoteViews makeRemoteViews(String paramString, Context paramContext, RemoteViews paramRemoteViews, boolean paramBoolean1, boolean paramBoolean2) {
    RemoteViews remoteViews;
    int i;
    if (paramRemoteViews == null)
      return null; 
    PendIntentCompat pendIntentCompat = new PendIntentCompat(paramRemoteViews);
    if (!paramBoolean2 || pendIntentCompat.findPendIntents() <= 0) {
      i = R.layout.custom_notification_lite;
    } else {
      i = R.layout.custom_notification;
    } 
    if (Build.VERSION.SDK_INT > 19 && RemoteViews.ctor != null) {
      remoteViews = (RemoteViews)RemoteViews.ctor.newInstance(new Object[] { this.mNotificationCompat.getHostContext().getApplicationInfo(), Integer.valueOf(i) });
    } else {
      remoteViews = new RemoteViews(this.mNotificationCompat.getHostContext().getPackageName(), i);
    } 
    View view = toView(paramContext, paramRemoteViews, paramBoolean1);
    Bitmap bitmap = createBitmap(view);
    remoteViews.setImageViewBitmap(R.id.im_main, bitmap);
    if (paramBoolean2 && i == R.layout.custom_notification)
      try {
        pendIntentCompat.setPendIntent(remoteViews, toView(this.mNotificationCompat.getHostContext(), remoteViews, paramBoolean1), view);
      } catch (Exception exception) {
        VLog.e(TAG, "setPendIntent error", new Object[] { exception });
      }  
    return remoteViews;
  }
  
  View toView(Context paramContext, RemoteViews paramRemoteViews, boolean paramBoolean) {
    View view1;
    View view2 = null;
    try {
      View view = createView(paramContext, paramRemoteViews, paramBoolean);
    } finally {
      Exception exception = null;
    } 
    return view1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\notification\RemoteViewsFixer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */