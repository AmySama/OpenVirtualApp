package com.lody.virtual.server.notification;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.lody.virtual.helper.compat.BuildCompat;

class WidthCompat {
  private static final String TAG = WidthCompat.class.getSimpleName();
  
  private volatile int mWidth = 0;
  
  private ViewGroup createViewGroup(Context paramContext, int paramInt) {
    try {
      return (ViewGroup)LayoutInflater.from(paramContext).inflate(paramInt, null);
    } finally {
      Exception exception = null;
    } 
  }
  
  private int getDefaultWidth(int paramInt1, int paramInt2) {
    int i = paramInt1;
    if (Build.VERSION.SDK_INT >= 21)
      i = paramInt1 - paramInt2 * 2; 
    return i;
  }
  
  private int getEMUINotificationWidth(Context paramContext, int paramInt1, int paramInt2) {
    try {
      Context context = paramContext.createPackageContext("com.android.systemui", 3);
      int i = getSystemId(context, "time_axis", "layout");
      if (i != 0) {
        View view;
        ViewGroup viewGroup = createViewGroup(context, i);
        layout((View)viewGroup, paramInt1, paramInt2);
        paramInt2 = getSystemId(context, "content_view_group", "id");
        if (paramInt2 != 0) {
          view = viewGroup.findViewById(paramInt2);
          return paramInt1 - view.getLeft() - view.getPaddingLeft() - view.getPaddingRight();
        } 
        i = view.getChildCount();
        for (paramInt2 = 0; paramInt2 < i; paramInt2++) {
          View view1 = view.getChildAt(paramInt2);
          if (LinearLayout.class.isInstance(view1)) {
            i = view1.getLeft();
            int j = view1.getPaddingLeft();
            paramInt2 = view1.getPaddingRight();
            return paramInt1 - i - j - paramInt2;
          } 
        } 
      } 
    } catch (Exception exception) {}
    return paramInt1;
  }
  
  private int getMIUINotificationWidth(Context paramContext, int paramInt1, int paramInt2) {
    try {
      Context context = paramContext.createPackageContext("com.android.systemui", 3);
      int i = getSystemId(context, "status_bar_notification_row", "layout");
      if (i != 0) {
        View view;
        ViewGroup viewGroup = createViewGroup(context, i);
        int j = getSystemId(context, "adaptive", "id");
        if (j == 0) {
          i = getSystemId(context, "content", "id");
        } else {
          View view1 = viewGroup.findViewById(j);
          i = j;
          if (view1 != null) {
            i = j;
            if (view1 instanceof ViewGroup) {
              ViewGroup viewGroup1 = (ViewGroup)view1;
              view1 = new View();
              this(context);
              viewGroup1.addView(view1);
              i = j;
            } 
          } 
        } 
        layout((View)viewGroup, paramInt1, paramInt2);
        if (i != 0) {
          view = viewGroup.findViewById(i);
          if (view != null)
            return paramInt1 - view.getLeft() - view.getPaddingLeft() - view.getPaddingRight(); 
        } else {
          i = view.getChildCount();
          for (paramInt2 = 0; paramInt2 < i; paramInt2++) {
            View view1 = view.getChildAt(paramInt2);
            if (FrameLayout.class.isInstance(view1) || "LatestItemView".equals(view1.getClass().getName()) || "SizeAdaptiveLayout".equals(view1.getClass().getName())) {
              j = view1.getLeft();
              i = view1.getPaddingLeft();
              paramInt2 = view1.getPaddingRight();
              return paramInt1 - j - i - paramInt2;
            } 
          } 
        } 
      } 
    } catch (Exception exception) {}
    return paramInt1;
  }
  
  private int getSystemId(Context paramContext, String paramString1, String paramString2) {
    return paramContext.getResources().getIdentifier(paramString1, paramString2, "com.android.systemui");
  }
  
  private void layout(View paramView, int paramInt1, int paramInt2) {
    paramView.layout(0, 0, paramInt1, paramInt2);
    paramView.measure(View.MeasureSpec.makeMeasureSpec(paramInt1, -2147483648), View.MeasureSpec.makeMeasureSpec(paramInt2, -2147483648));
    paramView.layout(0, 0, paramInt1, paramInt2);
  }
  
  public int getNotificationWidth(Context paramContext, int paramInt1, int paramInt2, int paramInt3) {
    if (this.mWidth > 0)
      return this.mWidth; 
    paramInt3 = getDefaultWidth(paramInt1, paramInt3);
    if (BuildCompat.isEMUI()) {
      paramInt3 = getEMUINotificationWidth(paramContext, paramInt1, paramInt2);
    } else if (BuildCompat.isMIUI()) {
      if (Build.VERSION.SDK_INT >= 21) {
        paramInt3 = getMIUINotificationWidth(paramContext, paramInt1 - Math.round(TypedValue.applyDimension(1, 10.0F, paramContext.getResources().getDisplayMetrics())) * 2, paramInt2);
      } else {
        paramInt3 = getMIUINotificationWidth(paramContext, paramInt1 - Math.round(TypedValue.applyDimension(1, 25.0F, paramContext.getResources().getDisplayMetrics())) * 2, paramInt2);
      } 
    } 
    this.mWidth = paramInt3;
    return paramInt3;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\notification\WidthCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */